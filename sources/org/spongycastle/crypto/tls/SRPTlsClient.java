package org.spongycastle.crypto.tls;

import java.util.Hashtable;

public class SRPTlsClient extends AbstractTlsClient {
    protected TlsSRPGroupVerifier i;
    protected byte[] j;
    protected byte[] k;

    /* access modifiers changed from: protected */
    public boolean J() {
        return false;
    }

    public void i(Hashtable serverExtensions) {
        if (TlsUtils.L(serverExtensions, TlsSRPUtils.a, 47) || !J()) {
            super.i(serverExtensions);
            return;
        }
        throw new TlsFatalAlert(47);
    }

    public TlsKeyExchange a() {
        int keyExchangeAlgorithm = TlsUtils.E(this.g);
        switch (keyExchangeAlgorithm) {
            case 21:
            case 22:
            case 23:
                return I(keyExchangeAlgorithm);
            default:
                throw new TlsFatalAlert(80);
        }
    }

    public TlsAuthentication w() {
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange I(int keyExchange) {
        return new TlsSRPKeyExchange(keyExchange, this.c, this.i, this.j, this.k);
    }
}
