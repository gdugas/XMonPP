package org.xmonpp.io;

import org.xmonpp.daemon.XmonPPDaemon;

public interface InputListener {
    void messageReceived(XmonPPDaemon daemon, Input input);
}
