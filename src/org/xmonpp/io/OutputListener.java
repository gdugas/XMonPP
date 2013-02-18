package org.xmonpp.io;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.xmonpp.Daemon;

public interface OutputListener {

    void messageSent(Daemon daemon, Chat chat, Message message);
}
