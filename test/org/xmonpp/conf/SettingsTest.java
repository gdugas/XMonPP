/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmonpp.conf;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import org.apache.commons.configuration.HierarchicalINIConfiguration;

/**
 *
 * @author guillaume
 */
public class SettingsTest {

    private static HierarchicalINIConfiguration ini;
    private static File inifile;

    public SettingsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        inifile = File.createTempFile("xmonpp", ".ini");
        ini = new HierarchicalINIConfiguration(inifile);
        ini.setProperty("param1", 1);
        ini.setProperty("section.param2", "two");
        ini.save();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        boolean delete = inifile.delete();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of load method, of class Settings.
     */
    @Test
    public void testSettings() {
        Settings.reset();

        System.out.println("Settings.load and get");
        Settings.load(inifile.getPath());
        assertEquals("1", Settings.get("param1"));
        assertEquals("two", Settings.get("section.param2"));


        System.out.println("Settings.get default value");
        assertEquals("vdef", Settings.get("asection.aparam", "vdef"));

        System.out.println("Settings.set");
        Settings.set("param3", "3");
        assertEquals("3", Settings.get("param3"));

        System.out.println("Settings.reset");
        Settings.reset();
        assertNull("1", Settings.get("param1"));
    }
}
