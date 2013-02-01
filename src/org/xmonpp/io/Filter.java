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
public interface Filter {
    
    public boolean inputFiltering(Chat chat, Message message);

    public boolean outputFiltering(Chat chat, Input input);

    public boolean outputFiltering(Chat chat, Output output);
}
