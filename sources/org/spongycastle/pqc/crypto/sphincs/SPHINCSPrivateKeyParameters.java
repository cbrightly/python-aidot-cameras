package org.spongycastle.pqc.crypto.sphincs;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.util.Arrays;

public class SPHINCSPrivateKeyParameters extends AsymmetricKeyParameter {
    private final byte[] d;

    public SPHINCSPrivateKeyParameters(byte[] keyData) {
        super(true);
        this.d = Arrays.h(keyData);
    }

    public byte[] b() {
        return Arrays.h(this.d);
    }
}
