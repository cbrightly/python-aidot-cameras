package org.spongycastle.crypto.tls;

import com.alibaba.fastjson.asm.Opcodes;
import org.spongycastle.crypto.agreement.DHStandardGroups;
import org.spongycastle.crypto.params.DHParameters;

public abstract class DefaultTlsServer extends AbstractTlsServer {
    /* access modifiers changed from: protected */
    public TlsSignerCredentials T() {
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public TlsSignerCredentials U() {
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public TlsEncryptionCredentials V() {
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public TlsSignerCredentials W() {
        throw new TlsFatalAlert(80);
    }

    /* access modifiers changed from: protected */
    public DHParameters S() {
        return DHStandardGroups.q;
    }

    /* access modifiers changed from: protected */
    public int[] I() {
        return new int[]{49200, 49199, 49192, 49191, 49172, 49171, Opcodes.IF_ICMPEQ, Opcodes.IFLE, 107, 103, 57, 51, 157, 156, 61, 60, 53, 47};
    }

    public TlsCredentials getCredentials() {
        switch (TlsUtils.E(this.p)) {
            case 1:
                return V();
            case 3:
                return T();
            case 5:
            case 19:
                return W();
            case 11:
            case 20:
                return null;
            case 17:
                return U();
            default:
                throw new TlsFatalAlert(80);
        }
    }

    public TlsKeyExchange a() {
        int keyExchangeAlgorithm = TlsUtils.E(this.p);
        switch (keyExchangeAlgorithm) {
            case 1:
                return R();
            case 3:
            case 5:
                return N(keyExchangeAlgorithm);
            case 7:
            case 9:
            case 11:
                return O(keyExchangeAlgorithm);
            case 16:
            case 18:
            case 20:
                return Q(keyExchangeAlgorithm);
            case 17:
            case 19:
                return P(keyExchangeAlgorithm);
            default:
                throw new TlsFatalAlert(80);
        }
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange O(int keyExchange) {
        return new TlsDHKeyExchange(keyExchange, this.j, S());
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange N(int keyExchange) {
        return new TlsDHEKeyExchange(keyExchange, this.j, S());
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange Q(int keyExchange) {
        return new TlsECDHKeyExchange(keyExchange, this.j, this.l, this.m, this.n);
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange P(int keyExchange) {
        return new TlsECDHEKeyExchange(keyExchange, this.j, this.l, this.m, this.n);
    }

    /* access modifiers changed from: protected */
    public TlsKeyExchange R() {
        return new TlsRSAKeyExchange(this.j);
    }
}
