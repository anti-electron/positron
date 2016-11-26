package io.antielectron.framework.app;

import io.antielectron.framework.js.JSGlobals;
import org.slf4j.Logger;

import java.io.Writer;

/**
 * TODO Document
 * @author Evan Geng
 */
public interface IDependencyProvider {

    void injectJs(JSGlobals globals, Logger log);

    void injectCss(Writer css, Logger log);

}
