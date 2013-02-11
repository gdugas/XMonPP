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

import java.util.Properties;
import org.xmonpp.test.XmppDaemon;

/**
 *
 * @author guillaume
 */
public class IOTest {

    private static Chat chat;
    private IO io;
    
    public IOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        IOTest.chat = XmppDaemon.getChatManager().createChat(XmppDaemon.user, null);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        Message message = new Message();
        message.setBody("my cmdline\nmy second line");
        message.addSubject("xm", "param1=1\nparam2=2");
        
        this.io = new IO(chat, message);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getMessage method, of class IO.
     */
    @Test
    public void testGetMessage() {
        System.out.println("IO.getMessage");
        
        io.setProperty("param3", "3");
        Message message = io.getMessage();
        String subject = message.getSubject("xm");
        
        assertEquals("my cmdline\nmy second line", message.getBody());
        assertEquals("param3=3\nparam2=2\nparam1=1\n", subject.substring(subject.indexOf('\n') + 1));
    }
    
    /**
     * Test of getProperty method, of class IO.
     */
    @Test
    public void testGetProperty() {
        System.out.println("IO.getProperty");
        String key, expResult, result;
        
        key = "param1";
        expResult = "1";
        result = this.io.getProperty(key);
        assertEquals(expResult, result);

        key = "param2";
        expResult = "2";
        result = this.io.getProperty(key);
        assertEquals(expResult, result);
        
        key = "paramTest";
        expResult = "test";
        result = this.io.getProperty(key, "test");
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getParseProperties method, of class IO.
     */
    @Test
    public void testParseProperties() {
        System.out.println("IO.parseProperties");
        
        Properties props = new Properties();
        props.setProperty("param1", "1");
        props.setProperty("param2", "2");
        
        assertEquals(props.toString(), this.io.getProperties().toString());
    }
    
    /**
     * Test of getRemoveProperty method, of class IO.
     */
    @Test
    public void testRemoveProperty() {
        System.out.println("IO.removeProperty");
        this.io.removeProperty("param1");
        assertEquals(null, this.io.getProperty("param1"));
    }

    /**
     * Test of setProperty method, of class IO.
     */
    @Test
    public void testSetProperty() {
        System.out.println("IO.setProperty");
        String key, expResult, result;
        
        this.io.setProperty("param1", "un");
        key = "param1";
        expResult = "un";
        result = this.io.getProperty(key);
        assertEquals(expResult, result);

        key = "param2";
        expResult = "2";
        result = this.io.getProperty(key);
        assertEquals(expResult, result);
    }
}
