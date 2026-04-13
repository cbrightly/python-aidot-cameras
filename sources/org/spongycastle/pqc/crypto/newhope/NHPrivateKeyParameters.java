package org.spongycastle.pqc.crypto.newhope;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.util.Arrays;

public class NHPrivateKeyParameters extends AsymmetricKeyParameter {
    final short[] d;

    public NHPrivateKeyParameters(short[] secData) {
        super(true);
        this.d = Arrays.o(secData);
    }

    public short[] b() {
        return Arrays.o(this.d);
    }
}
