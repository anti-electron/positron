package io.antielectron.framework.connection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * TODO Document
 * @author Evan Geng
 */
public class ClasspathStreamHandler extends URLStreamHandler {

    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        URL resUrl = getClass().getClassLoader().getResource(u.getHost() + u.getPath());
        if (resUrl == null)
            throw new FileNotFoundException("No such file: " + u.getHost() + u.getPath());
        return resUrl.openConnection();
    }

}
