package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.Wrapper;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;

public class RFC5649WrapEngine implements Wrapper {
    private BlockCipher a;
    private KeyParameter b;
    private boolean c;
    private byte[] d;
    private byte[] e;
    private byte[] f = null;

    public RFC5649WrapEngine(BlockCipher engine) {
        byte[] bArr = {-90, 89, 89, -90};
        this.d = bArr;
        this.e = bArr;
        this.a = engine;
    }

    public void a(boolean forWrapping, CipherParameters param) {
        this.c = forWrapping;
        if (param instanceof ParametersWithRandom) {
            param = ((ParametersWithRandom) param).a();
        }
        if (param instanceof KeyParameter) {
            this.b = (KeyParameter) param;
            this.e = this.d;
        } else if (param instanceof ParametersWithIV) {
            this.e = ((ParametersWithIV) param).a();
            this.b = (KeyParameter) ((ParametersWithIV) param).b();
            if (this.e.length != 4) {
                throw new IllegalArgumentException("IV length not equal to 4");
            }
        }
    }

    public String b() {
        return this.a.b();
    }

    private byte[] d(byte[] plaintext) {
        int plaintextLength = plaintext.length;
        int numOfZerosToAppend = (8 - (plaintextLength % 8)) % 8;
        byte[] paddedPlaintext = new byte[(plaintextLength + numOfZerosToAppend)];
        System.arraycopy(plaintext, 0, paddedPlaintext, 0, plaintextLength);
        if (numOfZerosToAppend != 0) {
            System.arraycopy(new byte[numOfZerosToAppend], 0, paddedPlaintext, plaintextLength, numOfZerosToAppend);
        }
        return paddedPlaintext;
    }

    public byte[] wrap(byte[] in, int inOff, int inLen) {
        if (this.c) {
            byte[] iv = new byte[8];
            byte[] mli = Pack.f(inLen);
            byte[] bArr = this.e;
            System.arraycopy(bArr, 0, iv, 0, bArr.length);
            System.arraycopy(mli, 0, iv, this.e.length, mli.length);
            byte[] relevantPlaintext = new byte[inLen];
            System.arraycopy(in, inOff, relevantPlaintext, 0, inLen);
            byte[] paddedPlaintext = d(relevantPlaintext);
            if (paddedPlaintext.length == 8) {
                byte[] paddedPlainTextWithIV = new byte[(paddedPlaintext.length + iv.length)];
                System.arraycopy(iv, 0, paddedPlainTextWithIV, 0, iv.length);
                System.arraycopy(paddedPlaintext, 0, paddedPlainTextWithIV, iv.length, paddedPlaintext.length);
                this.a.a(true, this.b);
                int i = 0;
                while (i < paddedPlainTextWithIV.length) {
                    this.a.f(paddedPlainTextWithIV, i, paddedPlainTextWithIV, i);
                    i += this.a.c();
                }
                return paddedPlainTextWithIV;
            }
            Wrapper wrapper = new RFC3394WrapEngine(this.a);
            wrapper.a(true, new ParametersWithIV(this.b, iv));
            return wrapper.wrap(paddedPlaintext, 0, paddedPlaintext.length);
        }
        throw new IllegalStateException("not set for wrapping");
    }

    public byte[] c(byte[] in, int inOff, int inLen) {
        byte[] paddedPlaintext;
        int i = inLen;
        if (!this.c) {
            int n = i / 8;
            if (n * 8 != i) {
                throw new InvalidCipherTextException("unwrap data must be a multiple of 8 bytes");
            } else if (n != 1) {
                byte[] relevantCiphertext = new byte[i];
                System.arraycopy(in, inOff, relevantCiphertext, 0, i);
                byte[] decrypted = new byte[i];
                if (n == 2) {
                    this.a.a(false, this.b);
                    for (int i2 = 0; i2 < relevantCiphertext.length; i2 += this.a.c()) {
                        this.a.f(relevantCiphertext, i2, decrypted, i2);
                    }
                    byte[] bArr = new byte[8];
                    this.f = bArr;
                    System.arraycopy(decrypted, 0, bArr, 0, bArr.length);
                    int length = decrypted.length;
                    byte[] bArr2 = this.f;
                    paddedPlaintext = new byte[(length - bArr2.length)];
                    System.arraycopy(decrypted, bArr2.length, paddedPlaintext, 0, paddedPlaintext.length);
                } else {
                    paddedPlaintext = e(in, inOff, inLen);
                }
                byte[] extractedHighOrderAIV = new byte[4];
                byte[] mliBytes = new byte[4];
                System.arraycopy(this.f, 0, extractedHighOrderAIV, 0, extractedHighOrderAIV.length);
                System.arraycopy(this.f, extractedHighOrderAIV.length, mliBytes, 0, mliBytes.length);
                int mli = Pack.a(mliBytes, 0);
                boolean isValid = true;
                if (!Arrays.u(extractedHighOrderAIV, this.e)) {
                    isValid = false;
                }
                int upperBound = paddedPlaintext.length;
                if (mli <= upperBound - 8) {
                    isValid = false;
                }
                if (mli > upperBound) {
                    isValid = false;
                }
                int expectedZeros = upperBound - mli;
                if (expectedZeros >= paddedPlaintext.length) {
                    isValid = false;
                    expectedZeros = paddedPlaintext.length;
                }
                byte[] pad = new byte[expectedZeros];
                int i3 = n;
                System.arraycopy(paddedPlaintext, paddedPlaintext.length - expectedZeros, pad, 0, expectedZeros);
                if (!Arrays.u(pad, new byte[expectedZeros])) {
                    isValid = false;
                }
                if (isValid) {
                    byte[] plaintext = new byte[mli];
                    byte[] bArr3 = pad;
                    System.arraycopy(paddedPlaintext, 0, plaintext, 0, plaintext.length);
                    return plaintext;
                }
                throw new InvalidCipherTextException("checksum failed");
            } else {
                throw new InvalidCipherTextException("unwrap data must be at least 16 bytes");
            }
        } else {
            byte[] bArr4 = in;
            int i4 = inOff;
            throw new IllegalStateException("not set for unwrapping");
        }
    }

    private byte[] e(byte[] in, int inOff, int inLen) {
        byte[] bArr = in;
        int i = inOff;
        int i2 = 8;
        byte[] iv = new byte[8];
        byte[] block = new byte[(inLen - iv.length)];
        byte[] a2 = new byte[iv.length];
        byte[] buf = new byte[(iv.length + 8)];
        System.arraycopy(bArr, i, a2, 0, iv.length);
        System.arraycopy(bArr, iv.length + i, block, 0, inLen - iv.length);
        this.a.a(false, this.b);
        int i3 = 1;
        int n = (inLen / 8) - 1;
        int j = 5;
        while (j >= 0) {
            int i4 = n;
            while (i4 >= i3) {
                System.arraycopy(a2, 0, buf, 0, iv.length);
                System.arraycopy(block, (i4 - 1) * i2, buf, iv.length, i2);
                int t = (n * j) + i4;
                int k = 1;
                while (t != 0) {
                    int length = iv.length - k;
                    buf[length] = (byte) (buf[length] ^ ((byte) t));
                    t >>>= 8;
                    k++;
                }
                this.a.f(buf, 0, buf, 0);
                i2 = 8;
                System.arraycopy(buf, 0, a2, 0, 8);
                System.arraycopy(buf, 8, block, (i4 - 1) * 8, 8);
                i4--;
                i3 = 1;
            }
            j--;
            i3 = 1;
        }
        this.f = a2;
        return block;
    }
}
