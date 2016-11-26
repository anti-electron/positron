package io.antielectron.framework.window;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.LoadHTMLParams;
import com.teamdev.jxbrowser.chromium.events.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import io.antielectron.framework.js.JSGlobals;

import java.util.Hashtable;

/**
 * TODO Document
 * @author Evan Geng
 */
public class SubEngine {

    private final BrowserWindow bw;
    private final Hashtable<String, String> headers = new Hashtable<>();
    private final JSGlobals engineGlobals = new JSGlobals(), pageGlobals = new JSGlobals();
    private Browser browser;
    private BrowserView view;
    private boolean active = true;

    public SubEngine(BrowserWindow bw) {
        this.bw = bw;
        this.browser = new Browser();
        this.view = new BrowserView(browser);
    }

    void initListeners() {
        browser.addConsoleListener(e -> bw.onConsoleMessage.accept(e.getMessage()));
        browser.addLoadListener(new LoadListener() {
            @Override
            public void onStartLoadingFrame(StartLoadingEvent event) {
                // NO-OP
            }

            @Override
            public void onProvisionalLoadingFrame(ProvisionalLoadingEvent event) {
                // NO-OP
            }

            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                pageGlobals.clear();
                SubEngine.this.insertCss(bw.getParentApp().getGlobalCss());
                bw.onPageLoad.accept(event.getValidatedURL());
            }

            @Override
            public void onFailLoadingFrame(FailLoadingEvent event) {
                // NO-OP
            }

            @Override
            public void onDocumentLoadedInFrame(FrameLoadEvent event) {
                // NO-OP
            }

            @Override
            public void onDocumentLoadedInMainFrame(LoadEvent event) {
                // NO-OP
            }
        });
        browser.addTitleListener(e -> bw.onPageTitleUpdate.accept(e.getTitle()));
        browser.addScriptContextListener(new ScriptContextListener() {
            @Override
            public void onScriptContextCreated(ScriptContextEvent event) {
                bw.getParentApp().getDefaultGlobals().bind(SubEngine.this::isActive, event.getBrowser());
                engineGlobals.bind(SubEngine.this::isActive, event.getBrowser());
                pageGlobals.bind(SubEngine.this::isActive, event.getBrowser());
            }

            @Override
            public void onScriptContextDestroyed(ScriptContextEvent event) {
                // NO-OP
            }
        });
    }

    public void cleanUp() {
        active = false;
        browser.dispose();
    }

    public boolean isActive() {
        return active;
    }

    public BrowserView getBrowserView() {
        return view;
    }

    public SubEngine addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public SubEngine clearHeaders() {
        headers.clear();
        return this;
    }

    public void loadUrl(String url) {
        if (url.toLowerCase().startsWith("pos://"))
            browser.loadURL("file://" + bw.getParentApp().getWebContentDir().getAbsolutePath() + "/" + url.substring(6));
        else
            browser.loadURL(url);
    }

    public void loadContent(String content) {
        browser.loadHTML(content);
    }

    public void loadContent(String content, String url) {
        browser.loadHTML(new LoadHTMLParams(content, "UTF-8", url));
    }

    public String getUrl() {
        return browser.getURL();
    }

    public String getTitle() {
        return browser.getTitle();
    }

    public boolean isLoading() {
        return browser.isLoading();
    }

    public void reload() {
        browser.reload();
    }

    public void goBack() {
        browser.goBack();
    }

    public void goForward() {
        browser.goForward();
    }

    public void insertCss(String css) {
        browser.getDocument().createElement("style").setTextContent(css);
    }

    public JSValue executeJs(String code) {
        return browser.executeJavaScriptAndReturnValue(code);
    }

    public JSGlobals getEngineGlobals() {
        return engineGlobals;
    }

    public JSGlobals getPageGlobals() {
        return pageGlobals;
    }

}
