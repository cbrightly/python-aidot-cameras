package org.spongycastle.crypto.signers;

import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSABlindingParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;

public class PSSSigner implements Signer {
    private Digest a;
    private Digest b;
    private AsymmetricBlockCipher c;
    private SecureRandom d;
    private int e;
    private int f;
    private boolean g = false;
    private int h;
    private int i;
    private byte[] j;
    private byte[] k;
    private byte[] l;
    private byte m;

    public PSSSigner(AsymmetricBlockCipher cipher, Digest contentDigest, Digest mgfDigest, int sLen, byte trailer) {
        this.c = cipher;
        this.a = contentDigest;
        this.b = mgfDigest;
        this.e = contentDigest.e();
        this.f = mgfDigest.e();
        this.h = sLen;
        this.j = new byte[sLen];
        this.k = new byte[(sLen + 8 + this.e)];
        this.m = trailer;
    }

    public void a(boolean forSigning, CipherParameters param) {
        CipherParameters params;
        RSAKeyParameters kParam;
        if (param instanceof ParametersWithRandom) {
            ParametersWithRandom p = (ParametersWithRandom) param;
            params = p.a();
            this.d = p.b();
        } else {
            params = param;
            if (forSigning) {
                this.d = new SecureRandom();
            }
        }
        if (params instanceof RSABlindingParameters) {
            kParam = ((RSABlindingParameters) params).b();
            this.c.a(forSigning, param);
        } else {
            kParam = (RSAKeyParameters) params;
            this.c.a(forSigning, params);
        }
        int bitLength = kParam.c().bitLength() - 1;
        this.i = bitLength;
        if (bitLength >= (this.e * 8) + (this.h * 8) + 9) {
            this.l = new byte[((bitLength + 7) / 8)];
            h();
            return;
        }
        throw new IllegalArgumentException("key too small for specified hash and salt lengths");
    }

    private void f(byte[] block) {
        for (int i2 = 0; i2 != block.length; i2++) {
            block[i2] = 0;
        }
    }

    public void d(byte b2) {
        this.a.d(b2);
    }

    public void update(byte[] in, int off, int len) {
        this.a.update(in, off, len);
    }

    public void h() {
        this.a.reset();
    }

    public byte[] c() {
        Digest digest = this.a;
        byte[] bArr = this.k;
        digest.c(bArr, (bArr.length - this.e) - this.h);
        if (this.h != 0) {
            if (!this.g) {
                this.d.nextBytes(this.j);
            }
            byte[] bArr2 = this.j;
            byte[] bArr3 = this.k;
            int length = bArr3.length;
            int i2 = this.h;
            System.arraycopy(bArr2, 0, bArr3, length - i2, i2);
        }
        byte[] h2 = new byte[this.e];
        Digest digest2 = this.a;
        byte[] bArr4 = this.k;
        digest2.update(bArr4, 0, bArr4.length);
        this.a.c(h2, 0);
        byte[] bArr5 = this.l;
        int length2 = bArr5.length;
        int i3 = this.h;
        int i4 = this.e;
        bArr5[(((length2 - i3) - 1) - i4) - 1] = 1;
        System.arraycopy(this.j, 0, bArr5, ((bArr5.length - i3) - i4) - 1, i3);
        byte[] dbMask = g(h2, 0, h2.length, (this.l.length - this.e) - 1);
        for (int i5 = 0; i5 != dbMask.length; i5++) {
            byte[] bArr6 = this.l;
            bArr6[i5] = (byte) (bArr6[i5] ^ dbMask[i5]);
        }
        byte[] bArr7 = this.l;
        bArr7[0] = (byte) (bArr7[0] & (255 >> ((bArr7.length * 8) - this.i)));
        int length3 = bArr7.length;
        int i6 = this.e;
        System.arraycopy(h2, 0, bArr7, (length3 - i6) - 1, i6);
        byte[] bArr8 = this.l;
        bArr8[bArr8.length - 1] = this.m;
        byte[] b2 = this.c.d(bArr8, 0, bArr8.length);
        f(this.l);
        return b2;
    }

    public boolean b(byte[] signature) {
        Digest digest = this.a;
        byte[] bArr = this.k;
        digest.c(bArr, (bArr.length - this.e) - this.h);
        try {
            byte[] b2 = this.c.d(signature, 0, signature.length);
            byte[] bArr2 = this.l;
            System.arraycopy(b2, 0, bArr2, bArr2.length - b2.length, b2.length);
            byte[] bArr3 = this.l;
            if (bArr3[bArr3.length - 1] != this.m) {
                f(bArr3);
                return false;
            }
            int length = bArr3.length;
            int i2 = this.e;
            byte[] dbMask = g(bArr3, (length - i2) - 1, i2, (bArr3.length - i2) - 1);
            for (int i3 = 0; i3 != dbMask.length; i3++) {
                byte[] bArr4 = this.l;
                bArr4[i3] = (byte) (bArr4[i3] ^ dbMask[i3]);
            }
            byte[] bArr5 = this.l;
            bArr5[0] = (byte) (bArr5[0] & (255 >> ((bArr5.length * 8) - this.i)));
            int i4 = 0;
            while (true) {
                byte[] bArr6 = this.l;
                int length2 = bArr6.length;
                int i5 = this.e;
                int i6 = this.h;
                if (i4 != ((length2 - i5) - i6) - 2) {
                    if (bArr6[i4] != 0) {
                        f(bArr6);
                        return false;
                    }
                    i4++;
                } else if (bArr6[((bArr6.length - i5) - i6) - 2] != 1) {
                    f(bArr6);
                    return false;
                } else {
                    if (this.g) {
                        byte[] bArr7 = this.j;
                        byte[] bArr8 = this.k;
                        System.arraycopy(bArr7, 0, bArr8, bArr8.length - i6, i6);
                    } else {
                        byte[] bArr9 = this.k;
                        System.arraycopy(bArr6, ((bArr6.length - i6) - i5) - 1, bArr9, bArr9.length - i6, i6);
                    }
                    Digest digest2 = this.a;
                    byte[] bArr10 = this.k;
                    digest2.update(bArr10, 0, bArr10.length);
                    Digest digest3 = this.a;
                    byte[] bArr11 = this.k;
                    digest3.c(bArr11, bArr11.length - this.e);
                    int length3 = this.l.length;
                    int i7 = this.e;
                    int i8 = (length3 - i7) - 1;
                    int j2 = this.k.length - i7;
                    while (true) {
                        byte[] bArr12 = this.k;
                        if (j2 == bArr12.length) {
                            f(bArr12);
                            f(this.l);
                            return true;
                        } else if ((this.l[i8] ^ bArr12[j2]) != 0) {
                            f(bArr12);
                            f(this.l);
                            return false;
                        } else {
                            i8++;
                            j2++;
                        }
                    }
                }
            }
        } catch (Exception e2) {
            return false;
        }
    }

    private void e(int i2, byte[] sp) {
        sp[0] = (byte) (i2 >>> 24);
        sp[1] = (byte) (i2 >>> 16);
        sp[2] = (byte) (i2 >>> 8);
        sp[3] = (byte) (i2 >>> 0);
    }

    private byte[] g(byte[] Z, int zOff, int zLen, int length) {
        int i2;
        byte[] mask = new byte[length];
        byte[] hashBuf = new byte[this.f];
        byte[] C = new byte[4];
        int counter = 0;
        this.b.reset();
        while (true) {
            i2 = this.f;
            if (counter >= length / i2) {
                break;
            }
            e(counter, C);
            this.b.update(Z, zOff, zLen);
            this.b.update(C, 0, C.length);
            this.b.c(hashBuf, 0);
            int i3 = this.f;
            System.arraycopy(hashBuf, 0, mask, counter * i3, i3);
            counter++;
        }
        if (i2 * counter < length) {
            e(counter, C);
            this.b.update(Z, zOff, zLen);
            this.b.update(C, 0, C.length);
            this.b.c(hashBuf, 0);
            int i4 = this.f;
            System.arraycopy(hashBuf, 0, mask, counter * i4, mask.length - (i4 * counter));
        }
        return mask;
    }
}
