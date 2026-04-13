package org.spongycastle.pqc.crypto.sphincs;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.util.Arrays;

public class SPHINCSPublicKeyParameters extends AsymmetricKeyParameter {
    private final byte[] d;

    public SPHINCSPublicKeyParameters(byte[] keyData) {
        super(false);
        this.d = Arrays.h(keyData);
    }

    public byte[] b() {
        return Arrays.h(this.d);
    }
}
