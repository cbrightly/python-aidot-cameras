package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.signers.ECDSASigner;
import org.spongycastle.crypto.signers.HMacDSAKCalculator;

public class TlsECDSASigner extends TlsDSASigner {
    public boolean e(AsymmetricKeyParameter publicKey) {
        return publicKey instanceof ECPublicKeyParameters;
    }

    /* access modifiers changed from: protected */
    public DSA g(short hashAlgorithm) {
        return new ECDSASigner(new HMacDSAKCalculator(TlsUtils.o(hashAlgorithm)));
    }

    /* access modifiers changed from: protected */
    public short h() {
        return 3;
    }
}
