package com.amazonaws;

import org.apache.http.l;

public enum Protocol {
    HTTP(l.DEFAULT_SCHEME_NAME),
    HTTPS("https");
    
    private final String protocol;

    private Protocol(String protocol2) {
        this.protocol = protocol2;
    }

    public String toString() {
        return this.protocol;
    }
}
