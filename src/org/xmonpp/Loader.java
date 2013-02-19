/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmonpp;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;

/**
 *
 * @author Guillaume Dugas
 */
public class Loader {

    private static ClassLoader[] loaders = null;
    protected static final Logger logger = Logger.getLogger("xmonpp");

    public static void setUp() {
        loaders = new ClassLoader[2];

        String[] paths = Settings.get("loader.path", "").toString().split(";");
        URL[] urls = new URL[paths.length];

        int i;
        for (i = 0; i < paths.length; i++) {
            try {
                URL url = new File(paths[i]).toURI().toURL();
                urls[i] = url;
                logger.info("Loading jar: ".concat(url.toString()));
            } catch (MalformedURLException ex) {
                logger.warning(ex.getMessage());
            }
        }

        loaders[0] = URLClassLoader.newInstance(urls);
        loaders[1] = ClassLoader.getSystemClassLoader();
    }

    public static Class loadClass(String classname) throws ClassNotFoundException {
        Class cls = null;

        // Loaders auto init
        if (loaders == null) {
            Loader.setUp();
        }

        int i;
        for (i = 0; i < loaders.length; i++) {
            try {
                cls = loaders[i].loadClass(classname);
            } catch (ClassNotFoundException ex) {
            }
        }

        if (cls == null) {
            throw new ClassNotFoundException();
        }

        return cls;
    }
}
