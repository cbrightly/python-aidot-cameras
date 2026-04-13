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
import org.spongycastle.pqc.math.linearalgebra.IntegerFunctions;

public class McElieceKobaraImaiCipher implements MessageEncryptor {
    public static final byte[] a = "a predetermined public constant".getBytes();
    private Digest b;
    private SecureRandom c;
    McElieceCCA2KeyParameters d;
    private int e;
    private int f;
    private int g;
    private boolean h;

    public void b(boolean forEncryption, CipherParameters param) {
        this.h = forEncryption;
        if (!forEncryption) {
            McElieceCCA2PrivateKeyParameters mcElieceCCA2PrivateKeyParameters = (McElieceCCA2PrivateKeyParameters) param;
            this.d = mcElieceCCA2PrivateKeyParameters;
            c(mcElieceCCA2PrivateKeyParameters);
        } else if (param instanceof ParametersWithRandom) {
            ParametersWithRandom rParam = (ParametersWithRandom) param;
            this.c = rParam.b();
            McElieceCCA2PublicKeyParameters mcElieceCCA2PublicKeyParameters = (McElieceCCA2PublicKeyParameters) rParam.a();
            this.d = mcElieceCCA2PublicKeyParameters;
            d(mcElieceCCA2PublicKeyParameters);
        } else {
            this.c = new SecureRandom();
            McElieceCCA2PublicKeyParameters mcElieceCCA2PublicKeyParameters2 = (McElieceCCA2PublicKeyParameters) param;
            this.d = mcElieceCCA2PublicKeyParameters2;
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
        this.b = Utils.a(pubKey.b());
        this.e = pubKey.e();
        this.f = pubKey.d();
        this.g = pubKey.f();
    }

    private void c(McElieceCCA2PrivateKeyParameters privKey) {
        this.b = Utils.a(privKey.b());
        this.e = privKey.g();
        this.f = privKey.f();
        this.g = privKey.j();
    }

    public byte[] f(byte[] input) {
        byte[] bArr = input;
        if (this.h) {
            int c2Len = this.b.e();
            int c4Len = this.f >> 3;
            int c5Len = (IntegerFunctions.a(this.e, this.g).bitLength() - 1) >> 3;
            byte[] bArr2 = a;
            int mLen = ((c4Len + c5Len) - c2Len) - bArr2.length;
            if (bArr.length > mLen) {
                mLen = bArr.length;
            }
            int c1Len = bArr2.length + mLen;
            int c6Len = ((c1Len + c2Len) - c4Len) - c5Len;
            byte[] mConst = new byte[c1Len];
            System.arraycopy(bArr, 0, mConst, 0, bArr.length);
            System.arraycopy(bArr2, 0, mConst, mLen, bArr2.length);
            byte[] r = new byte[c2Len];
            this.c.nextBytes(r);
            DigestRandomGenerator sr0 = new DigestRandomGenerator(new SHA1Digest());
            sr0.c(r);
            byte[] c1 = new byte[c1Len];
            sr0.a(c1);
            for (int i = c1Len - 1; i >= 0; i--) {
                c1[i] = (byte) (c1[i] ^ mConst[i]);
            }
            byte[] c2 = new byte[this.b.e()];
            this.b.update(c1, 0, c1.length);
            this.b.c(c2, 0);
            for (int i2 = c2Len - 1; i2 >= 0; i2--) {
                c2[i2] = (byte) (c2[i2] ^ r[i2]);
            }
            byte[] c2c1 = ByteUtils.a(c2, c1);
            byte[] c6 = new byte[0];
            if (c6Len > 0) {
                c6 = new byte[c6Len];
                System.arraycopy(c2c1, 0, c6, 0, c6Len);
            }
            byte[] c5 = new byte[c5Len];
            System.arraycopy(c2c1, c6Len, c5, 0, c5Len);
            byte[] c4 = new byte[c4Len];
            int i3 = c2Len;
            int i4 = c5Len;
            System.arraycopy(c2c1, c6Len + c5Len, c4, 0, c4Len);
            int i5 = c4Len;
            byte[] encC4 = McElieceCCA2Primitives.b((McElieceCCA2PublicKeyParameters) this.d, GF2Vector.c(this.f, c4), Conversions.b(this.e, this.g, c5)).e();
            if (c6Len > 0) {
                return ByteUtils.a(c6, encC4);
            }
            return encC4;
        }
        throw new IllegalStateException("cipher initialised for decryption");
    }

    public byte[] e(byte[] input) {
        byte[] c6;
        byte[] encC4;
        byte[] bArr = input;
        if (!this.h) {
            int nDiv8 = this.e >> 3;
            if (bArr.length >= nDiv8) {
                int c2Len = this.b.e();
                int c4Len = this.f >> 3;
                int c6Len = bArr.length - nDiv8;
                if (c6Len > 0) {
                    byte[][] c6EncC4 = ByteUtils.c(bArr, c6Len);
                    c6 = c6EncC4[0];
                    encC4 = c6EncC4[1];
                } else {
                    c6 = new byte[0];
                    encC4 = input;
                }
                GF2Vector[] c4z = McElieceCCA2Primitives.a((McElieceCCA2PrivateKeyParameters) this.d, GF2Vector.c(this.e, encC4));
                byte[] c4 = c4z[0].e();
                GF2Vector z = c4z[1];
                if (c4.length > c4Len) {
                    c4 = ByteUtils.d(c4, 0, c4Len);
                }
                byte[] c6c5c4 = ByteUtils.a(ByteUtils.a(c6, Conversions.a(this.e, this.g, z)), c4);
                int c1Len = c6c5c4.length - c2Len;
                byte[][] c2c1 = ByteUtils.c(c6c5c4, c2Len);
                byte[] c2 = c2c1[0];
                byte[] c1 = c2c1[1];
                byte[] rPrime = new byte[this.b.e()];
                int i = nDiv8;
                int i2 = c4Len;
                int i3 = c6Len;
                this.b.update(c1, 0, c1.length);
                this.b.c(rPrime, 0);
                for (int i4 = c2Len - 1; i4 >= 0; i4--) {
                    rPrime[i4] = (byte) (rPrime[i4] ^ c2[i4]);
                }
                DigestRandomGenerator sr0 = new DigestRandomGenerator(new SHA1Digest());
                sr0.c(rPrime);
                byte[] mConstPrime = new byte[c1Len];
                sr0.a(mConstPrime);
                int i5 = c1Len - 1;
                while (i5 >= 0) {
                    mConstPrime[i5] = (byte) (mConstPrime[i5] ^ c1[i5]);
                    i5--;
                }
                if (mConstPrime.length >= c1Len) {
                    byte[] bArr2 = a;
                    byte[] bArr3 = rPrime;
                    byte[][] temp = ByteUtils.c(mConstPrime, c1Len - bArr2.length);
                    byte[] mr = temp[0];
                    DigestRandomGenerator digestRandomGenerator = sr0;
                    if (ByteUtils.b(temp[1], bArr2)) {
                        return mr;
                    }
                    throw new InvalidCipherTextException("Bad Padding: invalid ciphertext");
                }
                throw new InvalidCipherTextException("Bad Padding: invalid ciphertext");
            }
            throw new InvalidCipherTextException("Bad Padding: Ciphertext too short.");
        }
        throw new IllegalStateException("cipher initialised for decryption");
    }
}
