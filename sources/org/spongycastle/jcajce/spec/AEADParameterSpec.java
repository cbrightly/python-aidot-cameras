package org.spongycastle.jcajce.spec;

import javax.crypto.spec.IvParameterSpec;
import org.spongycastle.util.Arrays;

public class AEADParameterSpec extends IvParameterSpec {
    private final byte[] a;
    private final int b;

    public AEADParameterSpec(byte[] nonce, int macSizeInBits) {
        this(nonce, macSizeInBits, (byte[]) null);
    }

    public AEADParameterSpec(byte[] nonce, int macSizeInBits, byte[] associatedData) {
        super(nonce);
        this.b = macSizeInBits;
        this.a = Arrays.h(associatedData);
    }

    public int b() {
        return this.b;
    }

    public byte[] a() {
        return Arrays.h(this.a);
    }

    public byte[] c() {
        return getIV();
    }
}
