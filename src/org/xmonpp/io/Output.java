package org.xmonpp.io;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;

import java.util.logging.Logger;

public class Output extends IO {

    public Output(Chat chat) {
        super(chat, "");
    }

    public Output(Chat chat, String message) {
        super(chat, message);
    }

    public void send() {
        for (Filter filter : Filters.filters) {
            if (!filter.outputFiltering(this)) {
                filter.onOutputError(this);
                return;
            }
        }

        try {
            this.chat.sendMessage(this.getMessage());
        } catch (XMPPException e) {
            Logger.getLogger("xmonpp").severe("Sending response error: ".concat(e.getMessage()));
        }
    }
}
