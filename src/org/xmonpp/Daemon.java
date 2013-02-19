package org.xmonpp;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import org.xmonpp.io.Filter;
import org.xmonpp.io.Filters;
import org.xmonpp.io.Input;
import org.xmonpp.io.InputListener;

public class Daemon implements ChatManagerListener, MessageListener {

    private Collection<InputListener> input_listeners = new ArrayList<InputListener>();
    private boolean logged = false;
    private ConnectionConfiguration config;
    private XMPPConnection conn;
    protected static final Logger logger = Logger.getLogger("xmonpp");

    public Daemon() {
    }

    public void addInputListener(InputListener listener) {
        this.input_listeners.add(listener);
    }

    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        chat.addMessageListener(this);
    }

    public boolean addUser(String id, String name, String[] groups) {
        if (!this.isLogged()) {
            return false;
        }

        Roster r = this.conn.getRoster();
        try {
            r.createEntry(id, name, groups);
        } catch (XMPPException e) {
            return false;
        }
        return true;
    }

    public RosterEntry getUser(String name) {
        Roster r = this.conn.getRoster();
        return r.getEntry(name);
    }

    public boolean hasUser(String name) {
        Roster r = this.conn.getRoster();
        return r.getEntry(name) != null;
    }

    public boolean isLogged() {
        return this.logged;
    }

    public boolean login() {
        String host = Settings.get("xmpp.xmpp_host").toString();
        Integer port = new Integer(Settings.get("xmpp.xmpp_port", 5222).toString());

        this.config = new ConnectionConfiguration(host, port);
        this.conn = new XMPPConnection(this.config);

        try {
            logger.info("Xmpp connecting attempt on ".concat(Settings.get("xmpp.xmpp_host").toString()).concat(":").concat(port.toString()));
            this.conn.connect();
            String hostname;
            try {
                InetAddress localMachine = InetAddress.getLocalHost();
                hostname = localMachine.getHostName();
            } catch (Exception e) {
                hostname = "";
            }

            String msg = "";
            msg = msg.concat("Xmpp login attempt: ").concat(Settings.get("xmpp.xmpp_login", "").toString());
            msg = msg.concat("@").concat(Settings.get("xmpp.xmpp_host", "").toString());
            msg = msg.concat(" from ").concat(Settings.get("xmpp.xmpp_ressource", hostname).toString());
            logger.info(msg);

            this.conn.login(Settings.get("xmpp.xmpp_login").toString(),
                    Settings.get("xmpp.xmpp_password").toString(),
                    Settings.get("xmpp.xmpp_ressource", hostname).toString());
            this.logged = true;
            logger.info("Login succeed");

            this.conn.getChatManager().addChatListener(this);

        } catch (XMPPException e) {
            logger.severe("Login failed: ".concat(e.getMessage()));
            return false;
        }

        return true;
    }

    public void logout() {
        logger.info("Xmpp logout");
        this.conn.disconnect();
    }

    // On message received
    @Override
    public void processMessage(Chat chat, Message message) {
        Message.Type t = message.getType();
        if (t.equals(Message.Type.normal) || t.equals(Message.Type.chat)) {
            logger.finest("Message received: ".concat(chat.getParticipant()).concat(": ").concat(message.getBody()));
            Input input = new Input(chat, message);
            for (Filter filter : Filters.filters) {
                if (!filter.inputFiltering(input)) {
                    filter.onInputError(input);
                    return;
                }
            }

            for (InputListener listener : this.input_listeners) {
                listener.messageReceived(this, input);
            }
        }
    }

    public void sendPresence(Presence p) {
        logger.info("Xmpp status: ".concat(p.toString()));
        this.conn.sendPacket(p);
    }
}
