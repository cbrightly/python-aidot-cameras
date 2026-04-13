package org.spongycastle.crypto.tls;

public class TlsFatalAlert extends TlsException {
    protected short alertDescription;

    public TlsFatalAlert(short alertDescription2) {
        this(alertDescription2, (Throwable) null);
    }

    public TlsFatalAlert(short alertDescription2, Throwable alertCause) {
        super(AlertDescription.b(alertDescription2), alertCause);
        this.alertDescription = alertDescription2;
    }

    public short getAlertDescription() {
        return this.alertDescription;
    }
}
