package org.spongycastle.crypto.signers;

import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.SignerWithRecovery;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.util.Arrays;

public class ISO9796d2Signer implements SignerWithRecovery {
    private Digest a;
    private AsymmetricBlockCipher b;
    private int c;
    private int d;
    private byte[] e;
    private byte[] f;
    private int g;
    private boolean h;
    private byte[] i;
    private byte[] j;
    private byte[] k;

    public ISO9796d2Signer(AsymmetricBlockCipher cipher, Digest digest, boolean implicit) {
        this.b = cipher;
        this.a = digest;
        if (implicit) {
            this.c = 188;
            return;
        }
        Integer trailerObj = ISOTrailers.a(digest);
        if (trailerObj != null) {
            this.c = trailerObj.intValue();
            return;
        }
        throw new IllegalArgumentException("no valid trailer for digest: " + digest.b());
    }

    public void a(boolean forSigning, CipherParameters param) {
        RSAKeyParameters kParam = (RSAKeyParameters) param;
        this.b.a(forSigning, kParam);
        int bitLength = kParam.c().bitLength();
        this.d = bitLength;
        byte[] bArr = new byte[((bitLength + 7) / 8)];
        this.e = bArr;
        if (this.c == 188) {
            this.f = new byte[((bArr.length - this.a.e()) - 2)];
        } else {
            this.f = new byte[((bArr.length - this.a.e()) - 3)];
        }
        g();
    }

    private boolean f(byte[] a2, byte[] b2) {
        boolean isOkay = true;
        int i2 = this.g;
        byte[] bArr = this.f;
        if (i2 > bArr.length) {
            if (bArr.length > b2.length) {
                isOkay = false;
            }
            for (int i3 = 0; i3 != this.f.length; i3++) {
                if (a2[i3] != b2[i3]) {
                    isOkay = false;
                }
            }
        } else {
            if (i2 != b2.length) {
                isOkay = false;
            }
            for (int i4 = 0; i4 != b2.length; i4++) {
                if (a2[i4] != b2[i4]) {
                    isOkay = false;
                }
            }
        }
        return isOkay;
    }

    private void e(byte[] block) {
        for (int i2 = 0; i2 != block.length; i2++) {
            block[i2] = 0;
        }
    }

    public void d(byte b2) {
        this.a.d(b2);
        int i2 = this.g;
        byte[] bArr = this.f;
        if (i2 < bArr.length) {
            bArr[i2] = b2;
        }
        this.g = i2 + 1;
    }

    public void update(byte[] in, int off, int len) {
        while (len > 0 && this.g < this.f.length) {
            d(in[off]);
            off++;
            len--;
        }
        this.a.update(in, off, len);
        this.g += len;
    }

    public void g() {
        this.a.reset();
        this.g = 0;
        e(this.f);
        byte[] bArr = this.i;
        if (bArr != null) {
            e(bArr);
        }
        this.i = null;
        this.h = false;
        if (this.j != null) {
            this.j = null;
            e(this.k);
            this.k = null;
        }
    }

    public byte[] c() {
        int delta;
        int t;
        int delta2;
        byte header;
        int digSize = this.a.e();
        boolean z = true;
        if (this.c == 188) {
            t = 8;
            byte[] bArr = this.e;
            delta = (bArr.length - digSize) - 1;
            this.a.c(bArr, delta);
            byte[] bArr2 = this.e;
            bArr2[bArr2.length - 1] = -68;
        } else {
            t = 16;
            byte[] bArr3 = this.e;
            delta = (bArr3.length - digSize) - 2;
            this.a.c(bArr3, delta);
            byte[] bArr4 = this.e;
            int i2 = this.c;
            bArr4[bArr4.length - 2] = (byte) (i2 >>> 8);
            bArr4[bArr4.length - 1] = (byte) i2;
        }
        int i3 = this.g;
        int x = ((((digSize + i3) * 8) + t) + 4) - this.d;
        if (x > 0) {
            int mR = i3 - ((x + 7) / 8);
            header = 96;
            delta2 = delta - mR;
            System.arraycopy(this.f, 0, this.e, delta2, mR);
            this.i = new byte[mR];
        } else {
            header = 64;
            delta2 = delta - i3;
            System.arraycopy(this.f, 0, this.e, delta2, i3);
            this.i = new byte[this.g];
        }
        if (delta2 - 1 > 0) {
            for (int i4 = delta2 - 1; i4 != 0; i4--) {
                this.e[i4] = -69;
            }
            byte[] bArr5 = this.e;
            int i5 = delta2 - 1;
            bArr5[i5] = (byte) (bArr5[i5] ^ 1);
            bArr5[0] = 11;
            bArr5[0] = (byte) (bArr5[0] | header);
        } else {
            byte[] bArr6 = this.e;
            bArr6[0] = 10;
            bArr6[0] = (byte) (bArr6[0] | header);
        }
        AsymmetricBlockCipher asymmetricBlockCipher = this.b;
        byte[] bArr7 = this.e;
        byte[] b2 = asymmetricBlockCipher.d(bArr7, 0, bArr7.length);
        if ((header & 32) != 0) {
            z = false;
        }
        this.h = z;
        byte[] bArr8 = this.f;
        byte[] bArr9 = this.i;
        System.arraycopy(bArr8, 0, bArr9, 0, bArr9.length);
        this.g = 0;
        e(this.f);
        e(this.e);
        return b2;
    }

    public boolean b(byte[] signature) {
        byte[] block;
        int delta;
        byte[] bArr = this.j;
        if (bArr == null) {
            try {
                block = this.b.d(signature, 0, signature.length);
            } catch (Exception e2) {
                return false;
            }
        } else if (Arrays.b(bArr, signature)) {
            block = this.k;
            this.j = null;
            this.k = null;
        } else {
            throw new IllegalStateException("updateWithRecoveredMessage called on different signature");
        }
        if (((block[0] & 192) ^ 64) != 0) {
            return h(block);
        }
        if (((block[block.length - 1] & 15) ^ 12) != 0) {
            return h(block);
        }
        if (((block[block.length - 1] & 255) ^ 188) == 0) {
            delta = 1;
        } else {
            int sigTrail = ((block[block.length - 2] & 255) << 8) | (block[block.length - 1] & 255);
            Integer trailerObj = ISOTrailers.a(this.a);
            if (trailerObj == null) {
                throw new IllegalArgumentException("unrecognised hash in signature");
            } else if (sigTrail == trailerObj.intValue()) {
                delta = 2;
            } else {
                throw new IllegalStateException("signer initialised with wrong digest for trailer " + sigTrail);
            }
        }
        int mStart = 0;
        while (mStart != block.length && ((block[mStart] & 15) ^ 10) != 0) {
            mStart++;
        }
        int mStart2 = mStart + 1;
        byte[] hash = new byte[this.a.e()];
        int off = (block.length - delta) - hash.length;
        if (off - mStart2 <= 0) {
            return h(block);
        }
        if ((block[0] & 32) == 0) {
            this.h = true;
            if (this.g > off - mStart2) {
                return h(block);
            }
            this.a.reset();
            this.a.update(block, mStart2, off - mStart2);
            this.a.c(hash, 0);
            boolean isOkay = true;
            for (int i2 = 0; i2 != hash.length; i2++) {
                int i3 = off + i2;
                block[i3] = (byte) (block[i3] ^ hash[i2]);
                if (block[off + i2] != 0) {
                    isOkay = false;
                }
            }
            if (!isOkay) {
                return h(block);
            }
            byte[] bArr2 = new byte[(off - mStart2)];
            this.i = bArr2;
            System.arraycopy(block, mStart2, bArr2, 0, bArr2.length);
        } else {
            this.h = false;
            this.a.c(hash, 0);
            boolean isOkay2 = true;
            for (int i4 = 0; i4 != hash.length; i4++) {
                int i5 = off + i4;
                block[i5] = (byte) (block[i5] ^ hash[i4]);
                if (block[off + i4] != 0) {
                    isOkay2 = false;
                }
            }
            if (!isOkay2) {
                return h(block);
            }
            byte[] bArr3 = new byte[(off - mStart2)];
            this.i = bArr3;
            System.arraycopy(block, mStart2, bArr3, 0, bArr3.length);
        }
        if (this.g != 0 && !f(this.f, this.i)) {
            return h(block);
        }
        e(this.f);
        e(block);
        this.g = 0;
        return true;
    }

    private boolean h(byte[] block) {
        this.g = 0;
        e(this.f);
        e(block);
        return false;
    }
}
