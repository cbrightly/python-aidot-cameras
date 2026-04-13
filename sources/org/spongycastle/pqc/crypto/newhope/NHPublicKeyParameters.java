package org.spongycastle.pqc.crypto.newhope;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.util.Arrays;

public class NHPublicKeyParameters extends AsymmetricKeyParameter {
    final byte[] d;

    public NHPublicKeyParameters(byte[] pubData) {
        super(false);
        this.d = Arrays.h(pubData);
    }

    public byte[] b() {
        return Arrays.h(this.d);
    }
}
