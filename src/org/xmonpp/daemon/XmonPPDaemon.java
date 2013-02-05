package org.xmonpp.daemon;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;

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

import org.xmonpp.conf.Settings;
import org.xmonpp.logger.Logger;
import org.xmonpp.io.Filter;
import org.xmonpp.io.Filters;
import org.xmonpp.io.Input;
import org.xmonpp.io.InputListener;
import org.xmonpp.io.Output;
import org.xmonpp.io.OutputListener;

public class XmonPPDaemon implements ChatManagerListener, MessageListener {

    private Collection<InputListener> input_listeners = new ArrayList<InputListener>();
    private Collection<OutputListener> output_listeners = new ArrayList<OutputListener>();
    private boolean logged = false;
    private ConnectionConfiguration config;
    private XMPPConnection conn;

    public XmonPPDaemon() {
    }

    public void addInputListener(InputListener listener) {
        this.input_listeners.add(listener);
    }

    public void addOutputListener(OutputListener listener) {
        this.output_listeners.add(listener);
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
            Logger.log("Xmpp connecting attempt on "
                    + Settings.get("xmpp.xmpp_host") + ":" + port);
            this.conn.connect();
            String hostname;
            try {
                InetAddress localMachine = InetAddress.getLocalHost();
                hostname = localMachine.getHostName();
            } catch (Exception e) {
                hostname = "";
            }

            Logger.log("Xmpp login attempt: " + Settings.get("xmpp.xmpp_login")
                    + "@" + Settings.get("xmpp.xmpp_host") + " from "
                    + Settings.get("xmpp.xmpp_ressource", hostname));
            this.conn.login(Settings.get("xmpp.xmpp_login").toString(),
                    Settings.get("xmpp.xmpp_password").toString(),
                    Settings.get("xmpp.xmpp_ressource", hostname).toString());
            this.logged = true;
            Logger.log("Login succeed");

            this.conn.getChatManager().addChatListener(this);

        } catch (XMPPException e) {
            Logger.error("Login failed: " + e.getMessage());
            return false;
        }

        return true;
    }

    public void logout() {
        Logger.log("Xmpp logout");
        this.conn.disconnect();
    }

    // On message received
    @Override
    public void processMessage(Chat chat, Message message) {
        for (Filter filter : Filters.filters) {
            if (!filter.inputFiltering(chat, message)) {
                filter.onInputError(chat, message);
                return;
            }
        }

        for (InputListener listener : this.input_listeners) {
            listener.messageReceived(this, chat, message);
        }
    }

    public void send(Chat chat, Input input) {
        for (Filter filter : Filters.filters) {
            filter.outputFiltering(chat, input);
        }

        try {
            chat.sendMessage(input.toString());
        } catch (XMPPException e) {
            Logger.error("Sending request error: " + e.getMessage());
        }
    }

    public void send(Chat chat, Output output) {
        for (Filter filter : Filters.filters) {
            filter.outputFiltering(chat, output);
        }

        try {
            chat.sendMessage(output.toString());
        } catch (XMPPException e) {
            Logger.error("Sending response error: " + e.getMessage());
        }
    }

    public void sendPresence(Presence p) {
        Logger.log("Xmpp status: " + p.toString());
        this.conn.sendPacket(p);
    }
}
