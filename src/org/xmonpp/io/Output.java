package org.xmonpp.io;

import org.jivesoftware.smack.Chat;

public class Output extends IO {

    public Output(Chat chat) {
        super(chat, "");
    }

    public Output(Chat chat, String message) {
        super(chat, message);
    }
}
