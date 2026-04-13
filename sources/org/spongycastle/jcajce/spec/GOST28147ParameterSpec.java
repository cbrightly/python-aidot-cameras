package org.spongycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.crypto.engines.GOST28147Engine;
import org.spongycastle.util.Arrays;

public class GOST28147ParameterSpec implements AlgorithmParameterSpec {
    private static Map a;
    private byte[] b;
    private byte[] c;

    public GOST28147ParameterSpec(byte[] sBox) {
        this.b = null;
        this.c = null;
        byte[] bArr = new byte[sBox.length];
        this.c = bArr;
        System.arraycopy(sBox, 0, bArr, 0, sBox.length);
    }

    public GOST28147ParameterSpec(byte[] sBox, byte[] iv) {
        this(sBox);
        byte[] bArr = new byte[iv.length];
        this.b = bArr;
        System.arraycopy(iv, 0, bArr, 0, iv.length);
    }

    public GOST28147ParameterSpec(String sBoxName) {
        this.b = null;
        this.c = null;
        this.c = GOST28147Engine.j(sBoxName);
    }

    public GOST28147ParameterSpec(ASN1ObjectIdentifier sBoxName, byte[] iv) {
        this(b(sBoxName));
        this.b = Arrays.h(iv);
    }

    public byte[] d() {
        return Arrays.h(this.c);
    }

    public byte[] c() {
        return Arrays.h(this.c);
    }

    public byte[] a() {
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

    private static String b(ASN1ObjectIdentifier sBoxOid) {
        String sBoxName = (String) a.get(sBoxOid);
        if (sBoxName != null) {
            return sBoxName;
        }
        throw new IllegalArgumentException("unknown OID: " + sBoxOid);
    }
}
