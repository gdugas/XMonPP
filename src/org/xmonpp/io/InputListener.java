package org.xmonpp.io;

import org.xmonpp.Daemon;

public interface InputListener {

    void messageReceived(Daemon daemon, Input input);
}
