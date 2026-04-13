package org.spongycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.pqc.crypto.MessageEncryptor;
import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;
import org.spongycastle.pqc.math.linearalgebra.GF2Vector;
import org.spongycastle.pqc.math.linearalgebra.GF2mField;
import org.spongycastle.pqc.math.linearalgebra.GoppaCode;
import org.spongycastle.pqc.math.linearalgebra.Permutation;
import org.spongycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public class McElieceCipher implements MessageEncryptor {
    private SecureRandom a;
    private int b;
    private int c;
    private int d;
    public int e;
    public int f;
    private McElieceKeyParameters g;
    private boolean h;

    public void d(boolean forEncryption, CipherParameters param) {
        this.h = forEncryption;
        if (!forEncryption) {
            McEliecePrivateKeyParameters mcEliecePrivateKeyParameters = (McEliecePrivateKeyParameters) param;
            this.g = mcEliecePrivateKeyParameters;
            e(mcEliecePrivateKeyParameters);
        } else if (param instanceof ParametersWithRandom) {
            ParametersWithRandom rParam = (ParametersWithRandom) param;
            this.a = rParam.b();
            McEliecePublicKeyParameters mcEliecePublicKeyParameters = (McEliecePublicKeyParameters) rParam.a();
            this.g = mcEliecePublicKeyParameters;
            f(mcEliecePublicKeyParameters);
        } else {
            this.a = new SecureRandom();
            McEliecePublicKeyParameters mcEliecePublicKeyParameters2 = (McEliecePublicKeyParameters) param;
            this.g = mcEliecePublicKeyParameters2;
            f(mcEliecePublicKeyParameters2);
        }
    }

    public int c(McElieceKeyParameters key) {
        if (key instanceof McEliecePublicKeyParameters) {
            return ((McEliecePublicKeyParameters) key).d();
        }
        if (key instanceof McEliecePrivateKeyParameters) {
            return ((McEliecePrivateKeyParameters) key).f();
        }
        throw new IllegalArgumentException("unsupported type");
    }

    private void f(McEliecePublicKeyParameters pubKey) {
        SecureRandom secureRandom = this.a;
        if (secureRandom == null) {
            secureRandom = new SecureRandom();
        }
        this.a = secureRandom;
        this.b = pubKey.d();
        this.c = pubKey.c();
        this.d = pubKey.e();
        this.f = this.b >> 3;
        this.e = this.c >> 3;
    }

    private void e(McEliecePrivateKeyParameters privKey) {
        this.b = privKey.f();
        int e2 = privKey.e();
        this.c = e2;
        this.e = e2 >> 3;
        this.f = this.b >> 3;
    }

    public byte[] h(byte[] input) {
        if (this.h) {
            GF2Vector m = b(input);
            return ((GF2Vector) ((McEliecePublicKeyParameters) this.g).b().p(m).a(new GF2Vector(this.b, this.d, this.a))).e();
        }
        throw new IllegalStateException("cipher initialised for decryption");
    }

    private GF2Vector b(byte[] input) {
        byte[] data = new byte[(this.e + ((this.c & 7) != 0 ? 1 : 0))];
        System.arraycopy(input, 0, data, 0, input.length);
        data[input.length] = 1;
        return GF2Vector.c(this.c, data);
    }

    public byte[] g(byte[] input) {
        if (!this.h) {
            GF2Vector vec = GF2Vector.c(this.b, input);
            McEliecePrivateKeyParameters privKey = (McEliecePrivateKeyParameters) this.g;
            GF2mField field = privKey.b();
            PolynomialGF2mSmallM gp = privKey.c();
            GF2Matrix sInv = privKey.j();
            Permutation p1 = privKey.g();
            Permutation p2 = privKey.h();
            GF2Matrix h2 = privKey.d();
            PolynomialGF2mSmallM[] qInv = privKey.i();
            Permutation p = p1.e(p2);
            GF2Vector cPInv = (GF2Vector) vec.i(p.a());
            GF2Vector z = GoppaCode.c((GF2Vector) h2.t(cPInv), field, gp, qInv);
            GF2Vector gF2Vector = vec;
            GF2Vector mSG = (GF2Vector) ((GF2Vector) cPInv.a(z)).i(p1);
            GF2Vector z2 = (GF2Vector) z.i(p);
            GF2Vector gF2Vector2 = mSG;
            return a((GF2Vector) sInv.p(mSG.d(this.c)));
        }
        throw new IllegalStateException("cipher initialised for decryption");
    }

    private byte[] a(GF2Vector mr) {
        byte[] mrBytes = mr.e();
        int index = mrBytes.length - 1;
        while (index >= 0 && mrBytes[index] == 0) {
            index--;
        }
        if (index < 0 || mrBytes[index] != 1) {
            throw new InvalidCipherTextException("Bad Padding: invalid ciphertext");
        }
        byte[] mBytes = new byte[index];
        System.arraycopy(mrBytes, 0, mBytes, 0, index);
        return mBytes;
    }
}
