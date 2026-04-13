package org.glassfish.tyrus.core.coder;

import jakarta.websocket.d;

public class ToStringEncoder extends CoderAdapter implements d.c<Object> {
    public String encode(Object object) {
        return object.toString();
    }
}
