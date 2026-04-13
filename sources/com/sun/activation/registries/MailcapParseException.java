package com.sun.activation.registries;

public class MailcapParseException extends Exception {
    private static final long serialVersionUID = -1445946122972156790L;

    public MailcapParseException() {
    }

    public MailcapParseException(String inInfo) {
        super(inInfo);
    }
}
