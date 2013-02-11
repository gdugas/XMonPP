/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmonpp.io;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

/**
 *
 * @author guillaume
 */
public class Input extends IO {
    
    public Input(Chat chat, Message message) {
        super(chat, message);
    }
    
    public Input(Chat chat, String message) {
        super(chat, message);
    }
}
