package org.spongycastle.crypto.tls;

public abstract class AbstractTlsPeer implements TlsPeer {
    public void m(boolean secureRenegotiation) {
        if (!secureRenegotiation) {
            throw new TlsFatalAlert(40);
        }
    }

    public void p(short alertLevel, short alertDescription, String message, Throwable cause) {
    }

    public void s(short alertLevel, short alertDescription) {
    }

    public void v() {
    }
}
