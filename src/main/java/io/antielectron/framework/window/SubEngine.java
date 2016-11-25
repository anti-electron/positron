package io.antielectron.framework.window;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import java.util.Hashtable;

/**
 * TODO Document
 * @author Evan Geng
 */
public class SubEngine {

    private final BrowserWindow bw;
    private final Hashtable<String, String> headers = new Hashtable<>();
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
            public void onStartLoadingFrame(StartLoadingEvent startLoadingEvent) {
                // NO-OP
            }

            @Override
            public void onProvisionalLoadingFrame(ProvisionalLoadingEvent provisionalLoadingEvent) {
                // NO-OP
            }

            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent finishLoadingEvent) {
                bw.onPageLoad.accept();
            }

            @Override
            public void onFailLoadingFrame(FailLoadingEvent failLoadingEvent) {
                // NO-OP
            }

            @Override
            public void onDocumentLoadedInFrame(FrameLoadEvent frameLoadEvent) {
                // NO-OP
            }

            @Override
            public void onDocumentLoadedInMainFrame(LoadEvent loadEvent) {
                // NO-OP
            }
        });
        browser.addTitleListener(e -> bw.onPageTitleUpdate.accept(e.getTitle()));
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
        browser.loadURL(url);
    }

    public void loadContent(String content) {
        browser.loadHTML(content);
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

}
