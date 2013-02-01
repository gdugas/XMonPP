package org.xmonpp.io;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.xmonpp.daemon.XmonPPDaemon;

public interface InputListener {

    void messageReceived(XmonPPDaemon daemon, Chat chat, Message message);
}
