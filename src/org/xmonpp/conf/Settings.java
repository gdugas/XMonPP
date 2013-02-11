package org.xmonpp.conf;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.configuration.HierarchicalINIConfiguration;

public class Settings {

    private static HierarchicalINIConfiguration ini = new HierarchicalINIConfiguration();
    private static Collection<SettingsLoadedListener> onloadingListeners = new ArrayList<SettingsLoadedListener>();
    private static Collection<SettingDefinedListener> onsetListeners = new ArrayList<SettingDefinedListener>();

    public static boolean load(String filename) {
        // Loading settings
        try {
            FileReader file = new FileReader(filename);
            Settings.ini.load(file);
        } catch (Exception e) {
            return false;
        }

        // Calling "loading" event
        for (Iterator<SettingsLoadedListener> iter = onloadingListeners.iterator(); iter.hasNext();) {
            SettingsLoadedListener listener = (SettingsLoadedListener) iter.next();
            listener.onLoading();
        }

        return true;
    }

    public static void onLoading(SettingsLoadedListener listener) {
        onloadingListeners.add(listener);
    }

    public static Object get(String key) {
        return Settings.ini.getProperty(key);
    }

    public static Object get(String key, Object defaultvalue) {
        Object value = Settings.ini.getProperty(key);
        if (value == null) {
            value = defaultvalue;
        }
        return value;
    }

    public static void reset() {
        Settings.ini = new HierarchicalINIConfiguration();
    }

    public static void set(String key, Object value) {
        Settings.ini.setProperty(key, value);

        // Calling "set" event
        for (Iterator<SettingDefinedListener> iter = onsetListeners.iterator(); iter.hasNext();) {
            SettingDefinedListener listener = (SettingDefinedListener) iter.next();
            listener.onSet(key, value);
        }
    }
}
