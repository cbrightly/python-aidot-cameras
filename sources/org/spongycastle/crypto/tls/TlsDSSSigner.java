package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;
import org.spongycastle.crypto.signers.DSASigner;
import org.spongycastle.crypto.signers.HMacDSAKCalculator;

public class TlsDSSSigner extends TlsDSASigner {
    public boolean e(AsymmetricKeyParameter publicKey) {
        return publicKey instanceof DSAPublicKeyParameters;
    }

    /* access modifiers changed from: protected */
    public DSA g(short hashAlgorithm) {
        return new DSASigner(new HMacDSAKCalculator(TlsUtils.o(hashAlgorithm)));
    }

    /* access modifiers changed from: protected */
    public short h() {
        return 2;
    }
}
