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

public class McElieceCCA2KeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private McElieceCCA2KeyGenerationParameters g;
    private int h;
    private int i;
    private int j;
    private int k;
    private SecureRandom l;
    private boolean m = false;

    private void c() {
        b(new McElieceCCA2KeyGenerationParameters(new SecureRandom(), new McElieceCCA2Parameters()));
    }

    public void b(KeyGenerationParameters param) {
        this.g = (McElieceCCA2KeyGenerationParameters) param;
        this.l = new SecureRandom();
        this.h = this.g.c().b();
        this.i = this.g.c().c();
        this.j = this.g.c().d();
        this.k = this.g.c().a();
        this.m = true;
    }

    public AsymmetricCipherKeyPair a() {
        if (!this.m) {
            c();
        }
        GF2mField field = new GF2mField(this.h, this.k);
        PolynomialGF2mSmallM gp = new PolynomialGF2mSmallM(field, this.j, 'I', this.l);
        GoppaCode.MaMaPe mmp = GoppaCode.a(GoppaCode.b(field, gp), this.l);
        GF2Matrix shortH = mmp.b();
        Permutation p = mmp.a();
        GF2Matrix shortG = (GF2Matrix) shortH.j();
        int k2 = shortG.b();
        return new AsymmetricCipherKeyPair(new McElieceCCA2PublicKeyParameters(this.i, this.j, shortG, this.g.c().e()), new McElieceCCA2PrivateKeyParameters(this.i, k2, field, gp, p, this.g.c().e()));
    }
}
