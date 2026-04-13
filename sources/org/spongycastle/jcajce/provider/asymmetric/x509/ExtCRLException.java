package org.spongycastle.jcajce.provider.asymmetric.x509;

import java.security.cert.CRLException;

public class ExtCRLException extends CRLException {
    Throwable cause;

    ExtCRLException(String message, Throwable cause2) {
        super(message);
        this.cause = cause2;
    }

    public Throwable getCause() {
        return this.cause;
    }
}
