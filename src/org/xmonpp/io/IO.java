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
    public String content = "";

    public IO(Chat chat, String message) {
        this.chat = chat;
        List<String> rq = Arrays.asList(message.split(
                System.getProperty("line.separator")));
        Iterator<String> lines = rq.iterator();
        if (lines.hasNext()) {
            this.attrs.putAll(IO.parseAttrs(lines.next()));
            rq.remove(0);
        }
    }

    public Object getAttr(Object key) {
        return this.attrs.get(key);
    }

    public Message getMessage() {
        Message message = new Message();
        
        List<String> subattrs = new ArrayList();
        Iterator<String> it = this.attrs.entrySet().iterator();
        while (it.hasNext()) {
            subattrs.add(it.next());
        }
        
        String body = StringUtils.join(subattrs, ";");
        body.concat("\n");
        body.concat(this.content);
        
        message.setBody(body);
        return message;
    }

    public static HashMap parseAttrs(String str) {
        HashMap map = new HashMap();

        List<String> rq = Arrays.asList(str.split(";"));
        Iterator<String> values = rq.iterator();
        if (values.hasNext()) {
            String kv = values.next();
            if (kv.startsWith("#")) {
                List<String> kvl = Arrays.asList(kv.split("="));
                String k = kvl.get(0);
                String v = "";
                if (kvl.size() > 1) {
                    v = kvl.get(1);
                }
                map.put(k, v);
            }
        }

        return map;
    }

    public void setAttr(Object key, Object value) {
        this.attrs.put(key, value);
    }
}
