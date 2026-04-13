package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.params.DHParameters;

public class PSKTlsClient extends AbstractTlsClient {
    protected TlsPSKIdentity i;

    public TlsKeyExchange a() {
        int keyExchangeAlgorithm = TlsUtils.E(this.g);
        switch (keyExchangeAlgorithm) {
            case 13:
            case 14:
            case 15:
            case 24:
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
        return new TlsPSKKeyExchange(keyExchange, this.c, this.i, (TlsPSKIdentityManager) null, (DHParameters) null, this.d, this.e, this.f);
    }
}
