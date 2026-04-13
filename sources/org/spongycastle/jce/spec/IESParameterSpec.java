package org.spongycastle.jce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.util.Arrays;

public class IESParameterSpec implements AlgorithmParameterSpec {
    private byte[] a;
    private byte[] b;
    private int c;
    private int d;
    private byte[] e;
    private boolean f;

    public IESParameterSpec(byte[] derivation, byte[] encoding, int macKeySize) {
        this(derivation, encoding, macKeySize, -1, (byte[]) null, false);
    }

    public IESParameterSpec(byte[] derivation, byte[] encoding, int macKeySize, int cipherKeySize, byte[] nonce) {
        this(derivation, encoding, macKeySize, cipherKeySize, nonce, false);
    }

    public IESParameterSpec(byte[] derivation, byte[] encoding, int macKeySize, int cipherKeySize, byte[] nonce, boolean usePointCompression) {
        if (derivation != null) {
            byte[] bArr = new byte[derivation.length];
            this.a = bArr;
            System.arraycopy(derivation, 0, bArr, 0, derivation.length);
        } else {
            this.a = null;
        }
        if (encoding != null) {
            byte[] bArr2 = new byte[encoding.length];
            this.b = bArr2;
            System.arraycopy(encoding, 0, bArr2, 0, encoding.length);
        } else {
            this.b = null;
        }
        this.c = macKeySize;
        this.d = cipherKeySize;
        this.e = Arrays.h(nonce);
        this.f = usePointCompression;
    }

    public byte[] b() {
        return Arrays.h(this.a);
    }

    public byte[] c() {
        return Arrays.h(this.b);
    }

    public int d() {
        return this.c;
    }

    public int a() {
        return this.d;
    }

    public byte[] e() {
        return Arrays.h(this.e);
    }

    public boolean f() {
        return this.f;
    }
}
