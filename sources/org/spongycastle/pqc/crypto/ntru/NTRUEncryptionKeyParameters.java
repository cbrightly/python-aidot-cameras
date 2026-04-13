package org.spongycastle.pqc.crypto.ntru;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public class NTRUEncryptionKeyParameters extends AsymmetricKeyParameter {
    protected final NTRUEncryptionParameters d;

    public NTRUEncryptionKeyParameters(boolean privateKey, NTRUEncryptionParameters params) {
        super(privateKey);
        this.d = params;
    }

    public NTRUEncryptionParameters b() {
        return this.d;
    }
}
