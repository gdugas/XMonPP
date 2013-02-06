/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmonpp.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

/**
 *
 * @author guillaume
 */
public class IO {

    protected HashMap attrs = new HashMap();
    public Chat chat;
    public String body = "";

    public IO(Chat chat, String message) {
        this.chat = chat;
        this.parse(message);
    }

    public Object getAttr(Object key) {
        return this.attrs.get(key);
    }

    public HashMap getAttrs() {
        return this.attrs;
    }

    public Message getMessage() {
        Message message = new Message();

        List<String> subattrs = new ArrayList();
        Iterator<String> it = this.attrs.entrySet().iterator();
        while (it.hasNext()) {
            subattrs.add(it.next());
        }

        String text = StringUtils.join(subattrs, ";");
        text.concat("\n");
        text.concat(this.body);

        message.setBody(body);
        return message;
    }

    private void parse(String message) {
        HashMap map = new HashMap();

        List<String> rq = Arrays.asList(message.split("\n"));
        Iterator<String> lines = rq.iterator();

        if (lines.hasNext()) {
            String line = lines.next();
            if (line.length() > 0 && line.charAt(0) == '#') {
                line = line.substring(1);
                List<String> params = Arrays.asList(line.split(";"));
                Iterator<String> values = params.iterator();

                while (values.hasNext()) {
                    String kv = values.next();
                    List<String> kvl = Arrays.asList(kv.split("="));
                    String k = kvl.get(0);
                    String v = "";
                    if (kvl.size() > 1) {
                        v = kvl.get(1);
                    }
                    map.put(k, v);
                }
            }
        }

        String text = "";
        Integer i = 0;
        while (lines.hasNext()) {
            String line = lines.next();
            if (i > 0) {
                text = text.concat("\n").concat(line);
            } else {
                text = text.concat(line);
            }
            i++;
        }
        this.body = text;

        this.attrs.putAll(map);
    }

    public void setAttr(Object key, Object value) {
        this.attrs.put(key, value);
    }
}
