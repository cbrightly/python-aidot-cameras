package org.spongycastle.pqc.jcajce.provider.util;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.ShortBufferException;

public abstract class AsymmetricHybridCipher extends CipherSpiExt {
    /* access modifiers changed from: protected */
    public abstract int m(int i);

    /* access modifiers changed from: protected */
    public abstract int n(int i);

    /* access modifiers changed from: protected */
    public abstract void o(Key key, AlgorithmParameterSpec algorithmParameterSpec);

    /* access modifiers changed from: protected */
    public abstract void p(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom);

    /* access modifiers changed from: protected */
    public final void i(String modeName) {
    }

    /* access modifiers changed from: protected */
    public final void j(String paddingName) {
    }

    public final byte[] d() {
        return null;
    }

    public final int c() {
        return 0;
    }

    public final int f(int inLen) {
        if (this.c == 1) {
            return n(inLen);
        }
        return m(inLen);
    }

    public final void h(Key key, AlgorithmParameterSpec params, SecureRandom random) {
        this.c = 1;
        p(key, params, random);
    }

    public final void g(Key key, AlgorithmParameterSpec params) {
        this.c = 2;
        o(key, params);
    }

    public final int k(byte[] input, int inOff, int inLen, byte[] output, int outOff) {
        if (output.length >= f(inLen)) {
            byte[] out = l(input, inOff, inLen);
            System.arraycopy(out, 0, output, outOff, out.length);
            return out.length;
        }
        throw new ShortBufferException("output");
    }

    public final int a(byte[] input, int inOff, int inLen, byte[] output, int outOff) {
        if (output.length >= f(inLen)) {
            byte[] out = b(input, inOff, inLen);
            System.arraycopy(out, 0, output, outOff, out.length);
            return out.length;
        }
        throw new ShortBufferException("Output buffer too short.");
    }
}
