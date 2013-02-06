/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmonpp.test;

import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 *
 * @author guillaume
 */
public class XmppDaemon {

    private static ChatManager chatManager = null;
    private static XMPPConnection conn = null;

    public static void login() {
        if (XmppDaemon.conn == null) {
            ConnectionConfiguration conf = new ConnectionConfiguration("localhost", 5222);
            XmppDaemon.conn = new XMPPConnection(conf);

            try {
                XmppDaemon.conn.connect();
                XmppDaemon.conn.login("xmonpp01@localhost", "xmonpp", "unittest");
                XmppDaemon.chatManager = XmppDaemon.conn.getChatManager();

            } catch (XMPPException e) {
                System.out.println("Unable to connect to xmpp server: xmonpp01@localhost:5222");
                System.exit(1);
            }
        }
    }

    public static void logout() {
        System.out.println("Xmpp logout");
        XmppDaemon.conn = null;
        XmppDaemon.chatManager = null;
    }

    public static XMPPConnection getConnection() {
        if (XmppDaemon.conn == null) {
            XmppDaemon.login();
        }
        return XmppDaemon.conn;
    }

    public static ChatManager getChatManager() {
        if (XmppDaemon.chatManager == null) {
            XmppDaemon.login();
        }
        return XmppDaemon.chatManager;
    }
}
