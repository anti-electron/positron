package io.antielectron.framework.window;

import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.w3c.dom.Document;

import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TODO Document
 * @author Evan Geng
 */
public class SubEngine {

    private final BrowserWindow bw;
    private final Hashtable<String, String> headers = new Hashtable<>();
    private WebView webView;
    private Stage stage;
    private AtomicBoolean loading = new AtomicBoolean(false);
    private boolean active = true;

    public SubEngine(BrowserWindow bw) {
        this.bw = bw;
        this.webView = new WebView();
    }

    public void cleanUp() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public WebView getWebView() {
        return webView;
    }

    public SubEngine setUserAgent(String agent) {
        webView.getEngine().setUserAgent(agent);
        return this;
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
        webView.getEngine().load(url);
    }

    public void loadContent(String content) {
        webView.getEngine().loadContent(content);
    }

    public void loadContent(String content, String contentType) {
        webView.getEngine().loadContent(content, contentType);
    }

    public String getUrl() {
        return webView.getEngine().getLocation();
    }

    public String getTitle() {
        return webView.getEngine().getTitle();
    }

    public boolean isLoading() {
        return loading.get();
    }

    public void reload() {
        webView.getEngine().reload();
    }

    public void goBack() {
        webView.getEngine().getHistory().go(-1);
    }

    public void goForward() {
        webView.getEngine().getHistory().go(1);
    }

    public void insertCss(String css) {
        webView.getEngine().getDocument().createElement("style").setTextContent(css);
    }

    public Object executeJs(String code) {
        return webView.getEngine().executeScript(code);
    }

    public Document getDocument() {
        return webView.getEngine().getDocument();
    }

}
