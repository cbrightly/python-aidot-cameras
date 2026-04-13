package org.spongycastle.jcajce.spec;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.util.Arrays;

public class MQVParameterSpec implements AlgorithmParameterSpec {
    private final PublicKey a;
    private final PrivateKey b;
    private final PublicKey c;
    private final byte[] d;

    public PrivateKey a() {
        return this.b;
    }

    public PublicKey b() {
        return this.a;
    }

    public PublicKey c() {
        return this.c;
    }

    public byte[] d() {
        return Arrays.h(this.d);
    }
}
