package org.spongycastle.crypto.tls;

import java.io.IOException;

public class TlsException extends IOException {
    protected Throwable cause;

    public TlsException(String message, Throwable cause2) {
        super(message);
        this.cause = cause2;
    }

    public Throwable getCause() {
        return this.cause;
    }
}
