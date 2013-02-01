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
public interface FilteringErrorListener {

    void onInputError(Filter filter, Chat chat, Message message);

    void onOutputError(Filter filter, Chat chat, Input input);

    void onOutputError(Filter filter, Chat chat, Output output);
}
