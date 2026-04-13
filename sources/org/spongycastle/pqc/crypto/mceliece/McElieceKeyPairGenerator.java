package org.spongycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;
import org.spongycastle.pqc.math.linearalgebra.GF2mField;
import org.spongycastle.pqc.math.linearalgebra.GoppaCode;
import org.spongycastle.pqc.math.linearalgebra.Permutation;
import org.spongycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;
import org.spongycastle.pqc.math.linearalgebra.PolynomialRingGF2m;

public class McElieceKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private McElieceKeyGenerationParameters g;
    private int h;
    private int i;
    private int j;
    private int k;
    private SecureRandom l;
    private boolean m = false;

    private void e() {
        d(new McElieceKeyGenerationParameters(new SecureRandom(), new McElieceParameters()));
    }

    private void d(KeyGenerationParameters param) {
        this.g = (McElieceKeyGenerationParameters) param;
        this.l = new SecureRandom();
        this.h = this.g.c().b();
        this.i = this.g.c().c();
        this.j = this.g.c().d();
        this.k = this.g.c().a();
        this.m = true;
    }

    private AsymmetricCipherKeyPair b() {
        if (!this.m) {
            e();
        }
        GF2mField field = new GF2mField(this.h, this.k);
        PolynomialGF2mSmallM gp = new PolynomialGF2mSmallM(field, this.j, 'I', this.l);
        PolynomialGF2mSmallM[] c = new PolynomialRingGF2m(field, gp).c();
        GoppaCode.MaMaPe mmp = GoppaCode.a(GoppaCode.b(field, gp), this.l);
        GF2Matrix shortH = mmp.b();
        Permutation p1 = mmp.a();
        GF2Matrix shortG = (GF2Matrix) shortH.j();
        GF2Matrix gPrime = shortG.l();
        int k2 = shortG.b();
        GF2Matrix[] matrixSandInverse = GF2Matrix.k(k2, this.l);
        Permutation p2 = new Permutation(this.i, this.l);
        GF2Matrix g2 = (GF2Matrix) ((GF2Matrix) matrixSandInverse[0].r(gPrime)).s(p2);
        GF2Matrix gF2Matrix = g2;
        int i2 = k2;
        GF2Matrix gF2Matrix2 = gPrime;
        return new AsymmetricCipherKeyPair(new McEliecePublicKeyParameters(this.i, this.j, g2), new McEliecePrivateKeyParameters(this.i, k2, field, gp, p1, p2, matrixSandInverse[1]));
    }

    public void c(KeyGenerationParameters param) {
        d(param);
    }

    public AsymmetricCipherKeyPair a() {
        return b();
    }
}
