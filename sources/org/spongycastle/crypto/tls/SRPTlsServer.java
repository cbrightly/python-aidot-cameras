package org.spongycastle.crypto.tls;

import java.util.Hashtable;

public class SRPTlsServer extends AbstractTlsServer {
    protected TlsSRPIdentityManager s;
    protected byte[] t;
    protected TlsSRPLoginParameters u;

    /* access modifiers changed from: protected */
    public TlsSignerCredentials O() {
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public TlsSignerCredentials P() {
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public int[] I() {
        return new int[]{49186, 49183, 49185, 49182, 49184, 49181};
    }

    public void l(Hashtable clientExtensions) {
        super.l(clientExtensions);
        this.t = TlsSRPUtils.a(clientExtensions);
    }

    public int B() {
        int cipherSuite = super.B();
        if (TlsSRPUtils.b(cipherSuite)) {
            byte[] bArr = this.t;
            if (bArr != null) {
                this.u = this.s.a(bArr);
            }
            if (this.u == null) {
                throw new TlsFatalAlert(115);
            }
        }
        return cipherSuite;
    }

    public TlsCredentials getCredentials() {
        switch (TlsUtils.E(this.p)) {
            case 21:
                return null;
            case 22:
                return O();
            case 23:
                return P();
            default:
                throw new TlsFatalAlert(80);
        }
    }

    public TlsKeyExchange a() {
        int keyExchangeAlgorithm = TlsUtils.E(this.p);
        switch (keyExchangeAlgorithm) {
            case 21:
            case 22:
            case 23:
                return N(keyExchangeAlgorithm);
            default:
                throw new TlsFatalAlert(80);
        }
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange N(int keyExchange) {
        return new TlsSRPKeyExchange(keyExchange, this.j, this.t, this.u);
    }
}
