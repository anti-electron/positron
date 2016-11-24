package io.antielectron.framework.connection;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

/**
 * TODO Document
 * @author Evan Geng
 */
public class PositronStreamHandlerFactory implements URLStreamHandlerFactory {

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        if (protocol.equals("cp"))
            return new ClasspathStreamHandler();
        throw new UnsupportedOperationException();
    }

}
