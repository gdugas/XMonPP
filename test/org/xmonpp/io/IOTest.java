/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmonpp.io;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import org.xmonpp.test.XmppDaemon;

/**
 *
 * @author guillaume
 */
public class IOTest {

    private static IO io;
    private static String head;
    private static String body;
    private static String message;
    private static HashMap attrs;

    public IOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Chat chat = XmppDaemon.getChatManager().createChat(XmppDaemon.user, null);
        System.out.println("setup");
        IOTest.attrs = new HashMap();
        IOTest.attrs.put("param1", "1");
        IOTest.attrs.put("param2", "2");

        IOTest.head = "#param1=1;param2=2";
        IOTest.body = "my cmdline\nmy second line";
        IOTest.message = IOTest.head.concat("\n").concat(IOTest.body);

        IOTest.io = new IO(chat, IOTest.message);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAttr method, of class IO.
     */
    @Test
    public void testGetAttr() {
        System.out.println("IO.getAttr");
        Object key, expResult, result;

        key = "param1";
        expResult = "1";
        result = IOTest.io.getAttr(key);
        assertEquals(expResult, result);

        key = "param2";
        expResult = "2";
        result = IOTest.io.getAttr(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAttrs method, of class IO.
     */
    @Test
    public void testGetAttrs() {
        System.out.println("IO.getAttrs");
        assertEquals(IOTest.attrs.toString(), IOTest.io.getAttrs().toString());
    }

    /**
     * Test of getMessage method, of class IO.
     */
    @Test
    public void testGetMessage() {
        System.out.println("IO.getMessage");
        Message m = IOTest.io.getMessage();
        
        assertEquals(IOTest.body, IOTest.io.body);
        assertEquals(IOTest.message, m.getBody());
    }

    /**
     * Test of setAttr method, of class IO.
     */
    @Test
    public void testSetAttr() {
        System.out.println("IO.setAttr");
        assertEquals(IOTest.io.getAttr("param3"), null);
        IOTest.io.setAttr("param3", "three");
        assertEquals(IOTest.io.getAttr("param3"), "three");
    }
}
