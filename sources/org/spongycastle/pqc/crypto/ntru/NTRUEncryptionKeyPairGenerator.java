package org.spongycastle.pqc.crypto.ntru;

import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.pqc.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.Polynomial;
import org.spongycastle.pqc.math.ntru.polynomial.ProductFormPolynomial;
import org.spongycastle.pqc.math.ntru.util.Util;

public class NTRUEncryptionKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private NTRUEncryptionKeyGenerationParameters g;

    public AsymmetricCipherKeyPair a() {
        IntegerPolynomial f;
        Polynomial t;
        IntegerPolynomial fq;
        IntegerPolynomial fp;
        Polynomial t2;
        NTRUEncryptionKeyGenerationParameters nTRUEncryptionKeyGenerationParameters = this.g;
        int N = nTRUEncryptionKeyGenerationParameters.p1;
        int q = nTRUEncryptionKeyGenerationParameters.a2;
        int df = nTRUEncryptionKeyGenerationParameters.p2;
        int df1 = nTRUEncryptionKeyGenerationParameters.p3;
        int df2 = nTRUEncryptionKeyGenerationParameters.p4;
        int df3 = nTRUEncryptionKeyGenerationParameters.z4;
        int dg = nTRUEncryptionKeyGenerationParameters.E4;
        boolean fastFp = nTRUEncryptionKeyGenerationParameters.S4;
        boolean sparse = nTRUEncryptionKeyGenerationParameters.R4;
        IntegerPolynomial fp2 = null;
        while (true) {
            if (fastFp) {
                NTRUEncryptionKeyGenerationParameters nTRUEncryptionKeyGenerationParameters2 = this.g;
                if (nTRUEncryptionKeyGenerationParameters2.T4 == 0) {
                    t = Util.a(N, df, df, sparse, nTRUEncryptionKeyGenerationParameters2.a());
                } else {
                    t = ProductFormPolynomial.g(N, df1, df2, df3, df3, nTRUEncryptionKeyGenerationParameters2.a());
                }
                f = t.a();
                f.B(3);
                int[] iArr = f.c;
                iArr[0] = iArr[0] + 1;
            } else {
                NTRUEncryptionKeyGenerationParameters nTRUEncryptionKeyGenerationParameters3 = this.g;
                if (nTRUEncryptionKeyGenerationParameters3.T4 == 0) {
                    t2 = Util.a(N, df, df - 1, sparse, nTRUEncryptionKeyGenerationParameters3.a());
                } else {
                    t2 = ProductFormPolynomial.g(N, df1, df2, df3, df3 - 1, nTRUEncryptionKeyGenerationParameters3.a());
                }
                f = t.a();
                fp2 = f.u();
                if (fp2 == null) {
                    continue;
                }
            }
            fq = f.v(q);
            if (fq != null) {
                break;
            }
        }
        if (fastFp) {
            fp = new IntegerPolynomial(N);
            fp.c[0] = 1;
        } else {
            fp = fp2;
        }
        while (true) {
            DenseTernaryPolynomial g2 = DenseTernaryPolynomial.S(N, dg, dg - 1, this.g.a());
            if (g2.v(q) != null) {
                IntegerPolynomial h = g2.c(fq, q);
                h.D(q);
                h.o(q);
                g2.l();
                fq.l();
                boolean z = sparse;
                Polynomial polynomial = t;
                return new AsymmetricCipherKeyPair(new NTRUEncryptionPublicKeyParameters(h, this.g.d()), new NTRUEncryptionPrivateKeyParameters(h, t, fp, this.g.d()));
            }
            Polynomial polynomial2 = t;
        }
    }
}
