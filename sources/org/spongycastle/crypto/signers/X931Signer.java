package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;

public class X931Signer implements Signer {
    private Digest a;
    private AsymmetricBlockCipher b;
    private RSAKeyParameters c;
    private int d;
    private int e;
    private byte[] f;

    public X931Signer(AsymmetricBlockCipher cipher, Digest digest, boolean implicit) {
        this.b = cipher;
        this.a = digest;
        if (implicit) {
            this.d = 188;
            return;
        }
        Integer trailerObj = ISOTrailers.a(digest);
        if (trailerObj != null) {
            this.d = trailerObj.intValue();
            return;
        }
        throw new IllegalArgumentException("no valid trailer for digest: " + digest.b());
    }

    public X931Signer(AsymmetricBlockCipher cipher, Digest digest) {
        this(cipher, digest, false);
    }

    public void a(boolean forSigning, CipherParameters param) {
        RSAKeyParameters rSAKeyParameters = (RSAKeyParameters) param;
        this.c = rSAKeyParameters;
        this.b.a(forSigning, rSAKeyParameters);
        int bitLength = this.c.c().bitLength();
        this.e = bitLength;
        this.f = new byte[((bitLength + 7) / 8)];
        g();
    }

    private void e(byte[] block) {
        for (int i = 0; i != block.length; i++) {
            block[i] = 0;
        }
    }

    public void d(byte b2) {
        this.a.d(b2);
    }

    public void update(byte[] in, int off, int len) {
        this.a.update(in, off, len);
    }

    public void g() {
        this.a.reset();
    }

    public byte[] c() {
        f();
        AsymmetricBlockCipher asymmetricBlockCipher = this.b;
        byte[] bArr = this.f;
        BigInteger t = new BigInteger(1, asymmetricBlockCipher.d(bArr, 0, bArr.length));
        e(this.f);
        return BigIntegers.a((this.c.c().bitLength() + 7) / 8, t.min(this.c.c().subtract(t)));
    }

    private void f() {
        int delta;
        int digSize = this.a.e();
        if (this.d == 188) {
            byte[] bArr = this.f;
            delta = (bArr.length - digSize) - 1;
            this.a.c(bArr, delta);
            byte[] bArr2 = this.f;
            bArr2[bArr2.length - 1] = -68;
        } else {
            byte[] bArr3 = this.f;
            delta = (bArr3.length - digSize) - 2;
            this.a.c(bArr3, delta);
            byte[] bArr4 = this.f;
            int i = this.d;
            bArr4[bArr4.length - 2] = (byte) (i >>> 8);
            bArr4[bArr4.length - 1] = (byte) i;
        }
        this.f[0] = 107;
        for (int i2 = delta - 2; i2 != 0; i2--) {
            this.f[i2] = -69;
        }
        this.f[delta - 1] = -70;
    }

    public boolean b(byte[] signature) {
        BigInteger f2;
        try {
            this.f = this.b.d(signature, 0, signature.length);
            BigInteger t = new BigInteger(1, this.f);
            if ((t.intValue() & 15) == 12) {
                f2 = t;
            } else {
                BigInteger t2 = this.c.c().subtract(t);
                if ((t2.intValue() & 15) != 12) {
                    return false;
                }
                f2 = t2;
            }
            f();
            byte[] fBlock = BigIntegers.a(this.f.length, f2);
            boolean rv = Arrays.u(this.f, fBlock);
            e(this.f);
            e(fBlock);
            return rv;
        } catch (Exception e2) {
            return false;
        }
    }
}
