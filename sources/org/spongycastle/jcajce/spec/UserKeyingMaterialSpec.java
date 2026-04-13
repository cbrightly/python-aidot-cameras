package org.spongycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.util.Arrays;

public class UserKeyingMaterialSpec implements AlgorithmParameterSpec {
    private final byte[] a;

    public byte[] a() {
        return Arrays.h(this.a);
    }
}
