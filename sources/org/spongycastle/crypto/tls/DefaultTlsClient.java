package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.params.DHParameters;

public abstract class DefaultTlsClient extends AbstractTlsClient {
    public TlsKeyExchange a() {
        int keyExchangeAlgorithm = TlsUtils.E(this.g);
        switch (keyExchangeAlgorithm) {
            case 1:
                return M();
            case 3:
            case 5:
                return I(keyExchangeAlgorithm);
            case 7:
            case 9:
            case 11:
                return J(keyExchangeAlgorithm);
            case 16:
            case 18:
            case 20:
                return L(keyExchangeAlgorithm);
            case 17:
            case 19:
                return K(keyExchangeAlgorithm);
            default:
                throw new TlsFatalAlert(80);
        }
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange J(int keyExchange) {
        return new TlsDHKeyExchange(keyExchange, this.c, (DHParameters) null);
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange I(int keyExchange) {
        return new TlsDHEKeyExchange(keyExchange, this.c, (DHParameters) null);
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange L(int keyExchange) {
        return new TlsECDHKeyExchange(keyExchange, this.c, this.d, this.e, this.f);
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange K(int keyExchange) {
        return new TlsECDHEKeyExchange(keyExchange, this.c, this.d, this.e, this.f);
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange M() {
        return new TlsRSAKeyExchange(this.c);
    }
}
