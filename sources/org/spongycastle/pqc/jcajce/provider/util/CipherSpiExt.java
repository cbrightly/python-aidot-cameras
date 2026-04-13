package org.spongycastle.pqc.jcajce.provider.util;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.CipherSpi;

public abstract class CipherSpiExt extends CipherSpi {
    protected int c;

    public abstract int a(byte[] bArr, int i, int i2, byte[] bArr2, int i3);

    public abstract byte[] b(byte[] bArr, int i, int i2);

    public abstract int c();

    public abstract byte[] d();

    public abstract int e(Key key);

    public abstract int f(int i);

    public abstract void g(Key key, AlgorithmParameterSpec algorithmParameterSpec);

    public abstract void h(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom);

    /* access modifiers changed from: protected */
    public abstract void i(String str);

    /* access modifiers changed from: protected */
    public abstract void j(String str);

    public abstract int k(byte[] bArr, int i, int i2, byte[] bArr2, int i3);

    public abstract byte[] l(byte[] bArr, int i, int i2);

    /* access modifiers changed from: protected */
    public final void engineInit(int opMode, Key key, SecureRandom random) {
        try {
            engineInit(opMode, key, (AlgorithmParameterSpec) null, random);
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidParameterException(e.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public final void engineInit(int opMode, Key key, AlgorithmParameters algParams, SecureRandom random) {
        if (algParams == null) {
            engineInit(opMode, key, random);
        } else {
            engineInit(opMode, key, (AlgorithmParameterSpec) null, random);
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(int opMode, Key key, AlgorithmParameterSpec params, SecureRandom javaRand) {
        if (params != null) {
        }
        if (key != null) {
            this.c = opMode;
            if (opMode == 1) {
                h(key, params, javaRand);
            } else if (opMode == 2) {
                g(key, params);
            }
        } else {
            throw new InvalidKeyException();
        }
    }

    /* access modifiers changed from: protected */
    public final byte[] engineDoFinal(byte[] input, int inOff, int inLen) {
        return b(input, inOff, inLen);
    }

    /* access modifiers changed from: protected */
    public final int engineDoFinal(byte[] input, int inOff, int inLen, byte[] output, int outOff) {
        return a(input, inOff, inLen, output, outOff);
    }

    /* access modifiers changed from: protected */
    public final int engineGetBlockSize() {
        return c();
    }

    /* access modifiers changed from: protected */
    public final int engineGetKeySize(Key key) {
        if (key instanceof Key) {
            return e(key);
        }
        throw new InvalidKeyException("Unsupported key.");
    }

    /* access modifiers changed from: protected */
    public final byte[] engineGetIV() {
        return d();
    }

    /* access modifiers changed from: protected */
    public final int engineGetOutputSize(int inLen) {
        return f(inLen);
    }

    /* access modifiers changed from: protected */
    public final AlgorithmParameters engineGetParameters() {
        return null;
    }

    /* access modifiers changed from: protected */
    public final void engineSetMode(String modeName) {
        i(modeName);
    }

    /* access modifiers changed from: protected */
    public final void engineSetPadding(String paddingName) {
        j(paddingName);
    }

    /* access modifiers changed from: protected */
    public final byte[] engineUpdate(byte[] input, int inOff, int inLen) {
        return l(input, inOff, inLen);
    }

    /* access modifiers changed from: protected */
    public final int engineUpdate(byte[] input, int inOff, int inLen, byte[] output, int outOff) {
        return k(input, inOff, inLen, output, outOff);
    }
}
