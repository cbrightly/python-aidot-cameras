package org.spongycastle.crypto.signers;

import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.SignerWithRecovery;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.ParametersWithSalt;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.util.Arrays;

public class ISO9796d2PSSSigner implements SignerWithRecovery {
    private Digest a;
    private AsymmetricBlockCipher b;
    private SecureRandom c;
    private byte[] d;
    private int e;
    private int f;
    private int g;
    private byte[] h;
    private byte[] i;
    private int j;
    private int k;
    private boolean l;
    private byte[] m;
    private byte[] n;
    private byte[] o;
    private int p;
    private int q;

    public void a(boolean forSigning, CipherParameters param) {
        RSAKeyParameters kParam;
        int lengthOfSalt = this.k;
        if (param instanceof ParametersWithRandom) {
            ParametersWithRandom p2 = (ParametersWithRandom) param;
            kParam = (RSAKeyParameters) p2.a();
            if (forSigning) {
                this.c = p2.b();
            }
        } else if (param instanceof ParametersWithSalt) {
            ParametersWithSalt p3 = (ParametersWithSalt) param;
            kParam = (RSAKeyParameters) p3.a();
            byte[] b2 = p3.b();
            this.d = b2;
            lengthOfSalt = b2.length;
            if (b2.length != this.k) {
                throw new IllegalArgumentException("Fixed salt is of wrong length");
            }
        } else {
            kParam = (RSAKeyParameters) param;
            if (forSigning) {
                this.c = new SecureRandom();
            }
        }
        this.b.a(forSigning, kParam);
        int bitLength = kParam.c().bitLength();
        this.g = bitLength;
        byte[] bArr = new byte[((bitLength + 7) / 8)];
        this.h = bArr;
        if (this.f == 188) {
            this.i = new byte[((((bArr.length - this.a.e()) - lengthOfSalt) - 1) - 1)];
        } else {
            this.i = new byte[((((bArr.length - this.a.e()) - lengthOfSalt) - 1) - 2)];
        }
        j();
    }

    private boolean h(byte[] a2, byte[] b2) {
        boolean isOkay = true;
        if (this.j != b2.length) {
            isOkay = false;
        }
        for (int i2 = 0; i2 != b2.length; i2++) {
            if (a2[i2] != b2[i2]) {
                isOkay = false;
            }
        }
        return isOkay;
    }

    private void g(byte[] block) {
        for (int i2 = 0; i2 != block.length; i2++) {
            block[i2] = 0;
        }
    }

    public void k(byte[] signature) {
        int sigTrail;
        byte[] block = this.b.d(signature, 0, signature.length);
        int length = block.length;
        int i2 = this.g;
        if (length < (i2 + 7) / 8) {
            byte[] tmp = new byte[((i2 + 7) / 8)];
            System.arraycopy(block, 0, tmp, tmp.length - block.length, block.length);
            g(block);
            block = tmp;
        }
        boolean z = true;
        if (((block[block.length - 1] & 255) ^ 188) == 0) {
            sigTrail = 1;
        } else {
            int sigTrail2 = ((block[block.length - 2] & 255) << 8) | (block[block.length - 1] & 255);
            Integer trailerObj = ISOTrailers.a(this.a);
            if (trailerObj == null) {
                throw new IllegalArgumentException("unrecognised hash in signature");
            } else if (sigTrail2 == trailerObj.intValue()) {
                sigTrail = 2;
            } else {
                throw new IllegalStateException("signer initialised with wrong digest for trailer " + sigTrail2);
            }
        }
        this.a.c(new byte[this.e], 0);
        int length2 = block.length;
        int i3 = this.e;
        byte[] dbMask = i(block, (length2 - i3) - sigTrail, i3, (block.length - i3) - sigTrail);
        for (int i4 = 0; i4 != dbMask.length; i4++) {
            block[i4] = (byte) (block[i4] ^ dbMask[i4]);
        }
        block[0] = (byte) (block[0] & Byte.MAX_VALUE);
        int mStart = 0;
        while (mStart != block.length && block[mStart] != 1) {
            mStart++;
        }
        int mStart2 = mStart + 1;
        if (mStart2 >= block.length) {
            g(block);
        }
        if (mStart2 <= 1) {
            z = false;
        }
        this.l = z;
        byte[] bArr = new byte[((dbMask.length - mStart2) - this.k)];
        this.m = bArr;
        System.arraycopy(block, mStart2, bArr, 0, bArr.length);
        byte[] bArr2 = this.m;
        System.arraycopy(bArr2, 0, this.i, 0, bArr2.length);
        this.n = signature;
        this.o = block;
        this.p = mStart2;
        this.q = sigTrail;
    }

    public void d(byte b2) {
        if (this.n == null) {
            int i2 = this.j;
            byte[] bArr = this.i;
            if (i2 < bArr.length) {
                this.j = i2 + 1;
                bArr[i2] = b2;
                return;
            }
        }
        this.a.d(b2);
    }

    public void update(byte[] in, int off, int len) {
        if (this.n == null) {
            while (len > 0 && this.j < this.i.length) {
                d(in[off]);
                off++;
                len--;
            }
        }
        if (len > 0) {
            this.a.update(in, off, len);
        }
    }

    public void j() {
        this.a.reset();
        this.j = 0;
        byte[] bArr = this.i;
        if (bArr != null) {
            g(bArr);
        }
        byte[] bArr2 = this.m;
        if (bArr2 != null) {
            g(bArr2);
            this.m = null;
        }
        this.l = false;
        if (this.n != null) {
            this.n = null;
            g(this.o);
            this.o = null;
        }
    }

    public byte[] c() {
        byte[] salt;
        byte[] m2Hash = new byte[this.a.e()];
        this.a.c(m2Hash, 0);
        byte[] C = new byte[8];
        f((long) (this.j * 8), C);
        this.a.update(C, 0, C.length);
        this.a.update(this.i, 0, this.j);
        this.a.update(m2Hash, 0, m2Hash.length);
        if (this.d != null) {
            salt = this.d;
        } else {
            salt = new byte[this.k];
            this.c.nextBytes(salt);
        }
        this.a.update(salt, 0, salt.length);
        byte[] hash = new byte[this.a.e()];
        this.a.c(hash, 0);
        int tLength = 2;
        if (this.f == 188) {
            tLength = 1;
        }
        byte[] bArr = this.h;
        int length = bArr.length;
        int i2 = this.j;
        boolean z = true;
        int off = ((((length - i2) - salt.length) - this.e) - tLength) - 1;
        bArr[off] = 1;
        System.arraycopy(this.i, 0, bArr, off + 1, i2);
        System.arraycopy(salt, 0, this.h, off + 1 + this.j, salt.length);
        byte[] dbMask = i(hash, 0, hash.length, (this.h.length - this.e) - tLength);
        for (int i3 = 0; i3 != dbMask.length; i3++) {
            byte[] bArr2 = this.h;
            bArr2[i3] = (byte) (bArr2[i3] ^ dbMask[i3]);
        }
        byte[] bArr3 = this.h;
        int length2 = bArr3.length;
        int i4 = this.e;
        System.arraycopy(hash, 0, bArr3, (length2 - i4) - tLength, i4);
        int i5 = this.f;
        if (i5 == 188) {
            byte[] bArr4 = this.h;
            bArr4[bArr4.length - 1] = -68;
        } else {
            byte[] bArr5 = this.h;
            bArr5[bArr5.length - 2] = (byte) (i5 >>> 8);
            bArr5[bArr5.length - 1] = (byte) i5;
        }
        byte[] bArr6 = this.h;
        bArr6[0] = (byte) (bArr6[0] & Byte.MAX_VALUE);
        byte[] b2 = this.b.d(bArr6, 0, bArr6.length);
        int i6 = this.j;
        byte[] bArr7 = new byte[i6];
        this.m = bArr7;
        byte[] bArr8 = this.i;
        if (i6 > bArr8.length) {
            z = false;
        }
        this.l = z;
        System.arraycopy(bArr8, 0, bArr7, 0, bArr7.length);
        g(this.i);
        g(this.h);
        this.j = 0;
        return b2;
    }

    public boolean b(byte[] signature) {
        byte[] m2Hash = new byte[this.e];
        this.a.c(m2Hash, 0);
        byte[] bArr = this.n;
        if (bArr == null) {
            try {
                k(signature);
            } catch (Exception e2) {
                return false;
            }
        } else if (!Arrays.b(bArr, signature)) {
            throw new IllegalStateException("updateWithRecoveredMessage called on different signature");
        }
        byte[] block = this.o;
        int mStart = this.p;
        int tLength = this.q;
        this.n = null;
        this.o = null;
        byte[] C = new byte[8];
        f((long) (this.m.length * 8), C);
        this.a.update(C, 0, C.length);
        byte[] bArr2 = this.m;
        if (bArr2.length != 0) {
            this.a.update(bArr2, 0, bArr2.length);
        }
        this.a.update(m2Hash, 0, m2Hash.length);
        byte[] bArr3 = this.d;
        if (bArr3 != null) {
            this.a.update(bArr3, 0, bArr3.length);
        } else {
            this.a.update(block, this.m.length + mStart, this.k);
        }
        byte[] hash = new byte[this.a.e()];
        this.a.c(hash, 0);
        int off = (block.length - tLength) - hash.length;
        boolean isOkay = true;
        for (int i2 = 0; i2 != hash.length; i2++) {
            if (hash[i2] != block[off + i2]) {
                isOkay = false;
            }
        }
        g(block);
        g(hash);
        if (!isOkay) {
            this.l = false;
            this.j = 0;
            g(this.m);
            return false;
        } else if (this.j == 0 || h(this.i, this.m)) {
            this.j = 0;
            g(this.i);
            return true;
        } else {
            this.j = 0;
            g(this.i);
            return false;
        }
    }

    private void e(int i2, byte[] sp) {
        sp[0] = (byte) (i2 >>> 24);
        sp[1] = (byte) (i2 >>> 16);
        sp[2] = (byte) (i2 >>> 8);
        sp[3] = (byte) (i2 >>> 0);
    }

    private void f(long l2, byte[] sp) {
        sp[0] = (byte) ((int) (l2 >>> 56));
        sp[1] = (byte) ((int) (l2 >>> 48));
        sp[2] = (byte) ((int) (l2 >>> 40));
        sp[3] = (byte) ((int) (l2 >>> 32));
        sp[4] = (byte) ((int) (l2 >>> 24));
        sp[5] = (byte) ((int) (l2 >>> 16));
        sp[6] = (byte) ((int) (l2 >>> 8));
        sp[7] = (byte) ((int) (l2 >>> 0));
    }

    private byte[] i(byte[] Z, int zOff, int zLen, int length) {
        int i2;
        byte[] mask = new byte[length];
        byte[] hashBuf = new byte[this.e];
        byte[] C = new byte[4];
        int counter = 0;
        this.a.reset();
        while (true) {
            i2 = this.e;
            if (counter >= length / i2) {
                break;
            }
            e(counter, C);
            this.a.update(Z, zOff, zLen);
            this.a.update(C, 0, C.length);
            this.a.c(hashBuf, 0);
            int i3 = this.e;
            System.arraycopy(hashBuf, 0, mask, counter * i3, i3);
            counter++;
        }
        if (i2 * counter < length) {
            e(counter, C);
            this.a.update(Z, zOff, zLen);
            this.a.update(C, 0, C.length);
            this.a.c(hashBuf, 0);
            int i4 = this.e;
            System.arraycopy(hashBuf, 0, mask, counter * i4, mask.length - (i4 * counter));
        }
        return mask;
    }
}
