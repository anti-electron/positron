package io.antielectron.framework.js;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

/**
 * TODO Document
 * @author Evan Geng
 */
public class JSGlobals {

    private final Map<String, Object> globals = new HashMap<>();
    private final Map<Browser, BooleanSupplier> boundBrowsers = new HashMap<>();

    public void put(String name, Object value) {
        globals.put(name, value);
        boundBrowsers.entrySet().removeIf(e -> {
            if (!e.getValue().getAsBoolean())
                return true;
            inject(e.getKey(), name, value);
            return false;
        });
    }

    public void putExecution(String code) {
        put(Integer.toString(code.hashCode()), new ScriptExecution(code));
    }

    public void clear() {
        globals.clear();
    }

    public void clearBindings() {
        boundBrowsers.clear();
    }

    public void bind(BooleanSupplier activeCheck, Browser browser) {
        if (activeCheck.getAsBoolean()) {
            boundBrowsers.put(browser, activeCheck);
            injectTo(browser);
        }
    }

    public void injectTo(Browser browser) {
        globals.forEach((k, v) -> inject(browser, k, v));
    }

    private void inject(Browser browser, String key, Object value) {
        if (value instanceof ScriptExecution)
            browser.executeJavaScript(((ScriptExecution)value).script);
        else
            globalsOf(browser).setProperty(key, value);
    }

    private static JSObject globalsOf(Browser browser) {
        return browser.executeJavaScriptAndReturnValue("window").asObject();
    }

    private static class ScriptExecution {

        final String script;

        ScriptExecution(String script) {
            this.script = script;
        }

    }

}
