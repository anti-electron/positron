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
            globalsOf(e.getKey()).setProperty(name, value);
            return false;
        });
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
            injectTo(globalsOf(browser));
        }
    }

    public void injectTo(JSObject object) {
        globals.forEach(object::setProperty);
    }

    private static JSObject globalsOf(Browser browser) {
        return browser.executeJavaScriptAndReturnValue("window").asObject();
    }

}
