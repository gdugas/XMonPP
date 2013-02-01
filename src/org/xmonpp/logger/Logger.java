package org.xmonpp.logger;

import java.util.Date;

import org.xmonpp.conf.Settings;

public class Logger {

    public static void debug(String msg) {
        if (Settings.get("debug", "false").equals("true")) {
            System.out.print("[debug] " + new Date().toString() + " - ");
            System.out.println(msg);
        }
    }

    public static void error(String msg) {
        System.out.print("[error] " + new Date().toString() + " - ");
        System.err.println(msg);
    }

    public static void log(String msg) {
        System.out.print("[log] " + new Date().toString() + " - ");
        System.out.println(msg);
    }

    public static void warn(String msg) {
        System.out.print("[warning] " + new Date().toString() + " - ");
        System.err.println(msg);
    }
}
