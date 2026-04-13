package org.spongycastle.crypto.tls;

public class TlsFatalAlertReceived extends TlsException {
    protected short alertDescription;

    public TlsFatalAlertReceived(short alertDescription2) {
        super(AlertDescription.b(alertDescription2), (Throwable) null);
        this.alertDescription = alertDescription2;
    }

    public short getAlertDescription() {
        return this.alertDescription;
    }
}
