package org.spongycastle.pqc.jcajce.provider.util;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;

public abstract class AsymmetricBlockCipher extends CipherSpiExt {
    protected ByteArrayOutputStream d = new ByteArrayOutputStream();
    protected int f;
    protected int q;

    /* access modifiers changed from: protected */
    public abstract void n(Key key, AlgorithmParameterSpec algorithmParameterSpec);

    /* access modifiers changed from: protected */
    public abstract void o(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom);

    /* access modifiers changed from: protected */
    public abstract byte[] p(byte[] bArr);

    /* access modifiers changed from: protected */
    public abstract byte[] q(byte[] bArr);

    public final int c() {
        return this.c == 1 ? this.f : this.q;
    }

    public final byte[] d() {
        return null;
    }

    public final int f(int inLen) {
        int totalLen = this.d.size() + inLen;
        int maxLen = c();
        if (totalLen > maxLen) {
            return 0;
        }
        return maxLen;
    }

    public final void h(Key key, AlgorithmParameterSpec params, SecureRandom secureRandom) {
        this.c = 1;
        o(key, params, secureRandom);
    }

    public final void g(Key key, AlgorithmParameterSpec params) {
        this.c = 2;
        n(key, params);
    }

    public final byte[] l(byte[] input, int inOff, int inLen) {
        if (inLen != 0) {
            this.d.write(input, inOff, inLen);
        }
        return new byte[0];
    }

    public final int k(byte[] input, int inOff, int inLen, byte[] output, int outOff) {
        l(input, inOff, inLen);
        return 0;
    }

    public final byte[] b(byte[] input, int inOff, int inLen) {
        m(inLen);
        l(input, inOff, inLen);
        byte[] mBytes = this.d.toByteArray();
        this.d.reset();
        switch (this.c) {
            case 1:
                return q(mBytes);
            case 2:
                return p(mBytes);
            default:
                return null;
        }
    }

    public final int a(byte[] input, int inOff, int inLen, byte[] output, int outOff) {
        if (output.length >= f(inLen)) {
            byte[] out = b(input, inOff, inLen);
            System.arraycopy(out, 0, output, outOff, out.length);
            return out.length;
        }
        throw new ShortBufferException("Output buffer too short.");
    }

    /* access modifiers changed from: protected */
    public final void i(String modeName) {
    }

    /* access modifiers changed from: protected */
    public final void j(String paddingName) {
    }

    /* access modifiers changed from: protected */
    public void m(int inLen) {
        int inLength = this.d.size() + inLen;
        int i = this.c;
        if (i == 1) {
            if (inLength > this.f) {
                throw new IllegalBlockSizeException("The length of the plaintext (" + inLength + " bytes) is not supported by the cipher (max. " + this.f + " bytes).");
            }
        } else if (i == 2 && inLength != this.q) {
            throw new IllegalBlockSizeException("Illegal ciphertext length (expected " + this.q + " bytes, was " + inLength + " bytes).");
        }
    }
}
