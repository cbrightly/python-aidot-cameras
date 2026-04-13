package org.spongycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.util.Arrays;

public class GOST28147WrapParameterSpec implements AlgorithmParameterSpec {
    private static Map a;
    private byte[] b;
    private byte[] c;

    public byte[] a() {
        return Arrays.h(this.c);
    }

    public byte[] b() {
        return Arrays.h(this.b);
    }

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        hashMap.put(CryptoProObjectIdentifiers.h, "E-A");
        a.put(CryptoProObjectIdentifiers.i, "E-B");
        a.put(CryptoProObjectIdentifiers.j, "E-C");
        a.put(CryptoProObjectIdentifiers.k, "E-D");
    }
}
