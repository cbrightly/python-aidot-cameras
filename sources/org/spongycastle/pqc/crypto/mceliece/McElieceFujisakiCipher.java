package org.spongycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.prng.DigestRandomGenerator;
import org.spongycastle.pqc.crypto.MessageEncryptor;
import org.spongycastle.pqc.math.linearalgebra.ByteUtils;
import org.spongycastle.pqc.math.linearalgebra.GF2Vector;

public class McElieceFujisakiCipher implements MessageEncryptor {
    private Digest a;
    private SecureRandom b;
    private int c;
    private int d;
    private int e;
    McElieceCCA2KeyParameters f;
    private boolean g;

    public void b(boolean forEncryption, CipherParameters param) {
        this.g = forEncryption;
        if (!forEncryption) {
            McElieceCCA2PrivateKeyParameters mcElieceCCA2PrivateKeyParameters = (McElieceCCA2PrivateKeyParameters) param;
            this.f = mcElieceCCA2PrivateKeyParameters;
            c(mcElieceCCA2PrivateKeyParameters);
        } else if (param instanceof ParametersWithRandom) {
            ParametersWithRandom rParam = (ParametersWithRandom) param;
            this.b = rParam.b();
            McElieceCCA2PublicKeyParameters mcElieceCCA2PublicKeyParameters = (McElieceCCA2PublicKeyParameters) rParam.a();
            this.f = mcElieceCCA2PublicKeyParameters;
            d(mcElieceCCA2PublicKeyParameters);
        } else {
            this.b = new SecureRandom();
            McElieceCCA2PublicKeyParameters mcElieceCCA2PublicKeyParameters2 = (McElieceCCA2PublicKeyParameters) param;
            this.f = mcElieceCCA2PublicKeyParameters2;
            d(mcElieceCCA2PublicKeyParameters2);
        }
    }

    public int a(McElieceCCA2KeyParameters key) {
        if (key instanceof McElieceCCA2PublicKeyParameters) {
            return ((McElieceCCA2PublicKeyParameters) key).e();
        }
        if (key instanceof McElieceCCA2PrivateKeyParameters) {
            return ((McElieceCCA2PrivateKeyParameters) key).g();
        }
        throw new IllegalArgumentException("unsupported type");
    }

    private void d(McElieceCCA2PublicKeyParameters pubKey) {
        SecureRandom secureRandom = this.b;
        if (secureRandom == null) {
            secureRandom = new SecureRandom();
        }
        this.b = secureRandom;
        this.a = Utils.a(pubKey.b());
        this.c = pubKey.e();
        this.d = pubKey.d();
        this.e = pubKey.f();
    }

    private void c(McElieceCCA2PrivateKeyParameters privKey) {
        this.a = Utils.a(privKey.b());
        this.c = privKey.g();
        this.e = privKey.j();
    }

    public byte[] f(byte[] input) {
        if (this.g) {
            GF2Vector r = new GF2Vector(this.d, this.b);
            byte[] rBytes = r.e();
            byte[] rm = ByteUtils.a(rBytes, input);
            this.a.update(rm, 0, rm.length);
            byte[] hrm = new byte[this.a.e()];
            this.a.c(hrm, 0);
            byte[] c1 = McElieceCCA2Primitives.b((McElieceCCA2PublicKeyParameters) this.f, r, Conversions.b(this.c, this.e, hrm)).e();
            DigestRandomGenerator sr0 = new DigestRandomGenerator(new SHA1Digest());
            sr0.c(rBytes);
            byte[] c2 = new byte[input.length];
            sr0.a(c2);
            for (int i = 0; i < input.length; i++) {
                c2[i] = (byte) (c2[i] ^ input[i]);
            }
            return ByteUtils.a(c1, c2);
        }
        throw new IllegalStateException("cipher initialised for decryption");
    }

    public byte[] e(byte[] input) {
        byte[] bArr = input;
        if (!this.g) {
            int c1Len = (this.c + 7) >> 3;
            int c2Len = bArr.length - c1Len;
            byte[][] c1c2 = ByteUtils.c(bArr, c1Len);
            byte[] c1 = c1c2[0];
            byte[] c2 = c1c2[1];
            GF2Vector[] decC1 = McElieceCCA2Primitives.a((McElieceCCA2PrivateKeyParameters) this.f, GF2Vector.c(this.c, c1));
            byte[] rBytes = decC1[0].e();
            GF2Vector z = decC1[1];
            DigestRandomGenerator sr0 = new DigestRandomGenerator(new SHA1Digest());
            sr0.c(rBytes);
            byte[] mBytes = new byte[c2Len];
            sr0.a(mBytes);
            for (int i = 0; i < c2Len; i++) {
                mBytes[i] = (byte) (mBytes[i] ^ c2[i]);
            }
            byte[] rmBytes = ByteUtils.a(rBytes, mBytes);
            byte[] hrm = new byte[this.a.e()];
            int i2 = c1Len;
            this.a.update(rmBytes, 0, rmBytes.length);
            this.a.c(hrm, 0);
            if (Conversions.b(this.c, this.e, hrm).equals(z)) {
                return mBytes;
            }
            throw new InvalidCipherTextException("Bad Padding: invalid ciphertext");
        }
        throw new IllegalStateException("cipher initialised for decryption");
    }
}
