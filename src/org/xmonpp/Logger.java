package org.xmonpp;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

public class Logger extends java.util.logging.Logger {

    protected static Logger xmonlogger;

    protected Logger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    public static void setUp() {
        xmonlogger = (Logger) Logger.getLogger("xmonpp");
        xmonlogger.addHandler(new ConsoleHandler());
        xmonlogger.setLevel(Level.parse(Settings.get("logging.level", "INFO").toString()));
    }

    public static Logger getLogger() {
        if (xmonlogger == null) {
            Logger.setUp();
        }
        return xmonlogger;
    }
}
