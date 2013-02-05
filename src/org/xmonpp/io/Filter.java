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

    public void onInputError(Chat chat, Message message);

    public void outputFiltering(Chat chat, Input input);

    public void outputFiltering(Chat chat, Output output);
}
