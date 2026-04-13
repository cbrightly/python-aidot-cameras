package org.spongycastle.pqc.crypto.newhope;

import org.spongycastle.crypto.CipherParameters;

public class NHAgreement {
    private NHPrivateKeyParameters a;

    public void b(CipherParameters param) {
        this.a = (NHPrivateKeyParameters) param;
    }

    public byte[] a(CipherParameters otherPublicKey) {
        byte[] sharedValue = new byte[32];
        NewHope.h(sharedValue, this.a.d, ((NHPublicKeyParameters) otherPublicKey).d);
        return sharedValue;
    }
}
