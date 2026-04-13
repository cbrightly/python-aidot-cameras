package org.glassfish.tyrus.core.coder;

import jakarta.websocket.Decoder;
import jakarta.websocket.d;

public class NoOpTextCoder extends CoderAdapter implements Decoder.Text<String>, d.c<String> {
    public boolean willDecode(String s) {
        return true;
    }

    public String decode(String s) {
        return s;
    }

    public String encode(String object) {
        return object;
    }
}
