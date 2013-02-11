/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmonpp.io;

import java.util.Properties;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.xmonpp.logger.Logger;

/**
 *
 * @author guillaume
 */
public class IO {

    protected Chat chat;
    protected Message message;
    protected Properties properties;

    public IO(Chat chat) {
        this.chat = chat;
        this.message = new Message();
        this.properties = IO.parseProperties(this.message);
    }

    public IO(Chat chat, String message) {
        this.chat = chat;
        this.message = new Message();
        this.message.setBody(message);
        this.properties = IO.parseProperties(this.message);
    }

    public IO(Chat chat, Message message) {
        this.chat = chat;
        this.message = message;
        this.properties = IO.parseProperties(this.message);
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

    public String getProperty(String key, String vdef) {
        return this.properties.getProperty(key, vdef);
    }
    
    public Properties getProperties() {
        return this.properties;
    }

    public Message getMessage() {
        Message m = this.message;
        ByteArrayOutputStream o = new ByteArrayOutputStream();

        try {
            this.properties.store(o, null);
            boolean b = message.removeSubject("xm");
            message.addSubject("xm", o.toString());

        } catch (Exception e) {
            Logger.error("IO.getMessage exception: ".concat(e.getMessage()));
        }

        return message;
    }

    public static Properties parseProperties(Message m) {
        Properties p = new Properties();
        String subject = m.getSubject("xm");
        if (subject != null) {
            try {
                p.load(new ByteArrayInputStream(subject.getBytes()));
            } catch (Exception e) {
            }
        }
        return p;
    }

    public void setProperty(String key, String value) {
        this.properties.put(key, value);
    }
    
    public void removeProperty(String key) {
        this.properties.remove(key);
    }
}
