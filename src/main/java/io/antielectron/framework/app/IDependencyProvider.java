package io.antielectron.framework.app;

import io.antielectron.framework.js.JSGlobals;

import java.io.Writer;

/**
 * TODO Document
 * @author Evan Geng
 */
public interface IDependencyProvider {

    void injectJs(JSGlobals globals);

    void injectCss(Writer css);

}
