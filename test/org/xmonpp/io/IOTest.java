/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmonpp.io;

import org.jivesoftware.smack.Chat;
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

    private IO io;
    private String head;
    private String body;
    private String message;
    private HashMap attrs;
    
    public IOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        Chat chat = XmppDaemon.getChatManager().createChat("xmonpp01@localhost", null);
        
        this.attrs = new HashMap();
        this.attrs.put("param1", "1");
        this.attrs.put("param2", "2");
        
        this.head = "#param1=1;param2=2";
        this.body = "my cmdline\nmy second line";
        this.message = this.head.concat("\n").concat(this.body);
        
        this.io = new IO(chat, this.message);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAttr method, of class IO.
     */
    @Test
    public void testGetAttr() {
        Object key,expResult,result;
        
        key = "param1";
        expResult = "1";
        result = this.io.getAttr(key);
        assertEquals(expResult, result);
        
        key = "param2";
        expResult = "2";
        result = this.io.getAttr(key);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAttrs method, of class IO.
     */
    @Test
    public void testGetAttrs() {
        assertEquals(this.attrs.toString(), this.io.getAttrs().toString());
    }
    
    /**
     * Test of getMessage method, of class IO.
     */
    @Test
    public void testGetMessage() {
        assertEquals(this.body, this.io.body);
    }

    /**
     * Test of setAttr method, of class IO.
     */
    @Test
    public void testSetAttr() {
        assertEquals(this.io.getAttr("param3"), null);
        this.io.setAttr("param3", "three");
        assertEquals(this.io.getAttr("param3"), "three");
    }
}
