package org.spongycastle.pqc.crypto.ntru;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.pqc.crypto.ntru.NTRUSigningPrivateKeyParameters;
import org.spongycastle.pqc.math.ntru.euclid.BigIntEuclidean;
import org.spongycastle.pqc.math.ntru.polynomial.BigDecimalPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.BigIntPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.Polynomial;
import org.spongycastle.pqc.math.ntru.polynomial.ProductFormPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.Resultant;

public class NTRUSigningKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private NTRUSigningKeyGenerationParameters g;

    public AsymmetricCipherKeyPair a() {
        NTRUSigningPublicKeyParameters pub2 = null;
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<NTRUSigningPrivateKeyParameters.Basis>> bases = new ArrayList<>();
        for (int k = this.g.z4; k >= 0; k--) {
            bases.add(executor.submit(new BasisGenerationTask()));
        }
        executor.shutdown();
        List<NTRUSigningPrivateKeyParameters.Basis> basises = new ArrayList<>();
        int k2 = this.g.z4;
        while (k2 >= 0) {
            Future<NTRUSigningPrivateKeyParameters.Basis> basis = bases.get(k2);
            try {
                basises.add(basis.get());
                if (k2 == this.g.z4) {
                    pub2 = new NTRUSigningPublicKeyParameters(basis.get().c, this.g.d());
                }
                k2--;
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return new AsymmetricCipherKeyPair(pub2, new NTRUSigningPrivateKeyParameters(basises, pub2));
    }

    private void d(IntegerPolynomial f, IntegerPolynomial g2, IntegerPolynomial F, IntegerPolynomial G, int N) {
        IntegerPolynomial integerPolynomial = f;
        IntegerPolynomial integerPolynomial2 = g2;
        IntegerPolynomial integerPolynomial3 = F;
        IntegerPolynomial integerPolynomial4 = G;
        int i = N;
        int E = 0;
        for (int j = 0; j < i; j++) {
            int[] iArr = integerPolynomial.c;
            int i2 = iArr[j] * iArr[j];
            int[] iArr2 = integerPolynomial2.c;
            E += i * 2 * (i2 + (iArr2[j] * iArr2[j]));
        }
        int E2 = E - 4;
        IntegerPolynomial u = (IntegerPolynomial) f.clone();
        IntegerPolynomial v = (IntegerPolynomial) g2.clone();
        int j2 = 0;
        int k = 0;
        int maxAdjustment = N;
        while (k < maxAdjustment && j2 < i) {
            int D = 0;
            for (int i3 = 0; i3 < i; i3++) {
                D += i * 4 * ((integerPolynomial3.c[i3] * integerPolynomial.c[i3]) + (integerPolynomial4.c[i3] * integerPolynomial2.c[i3]));
            }
            int D2 = D - ((F.O() + G.O()) * 4);
            if (D2 > E2) {
                integerPolynomial3.M(u);
                integerPolynomial4.M(v);
                k++;
                j2 = 0;
            } else if (D2 < (-E2)) {
                integerPolynomial3.h(u);
                integerPolynomial4.h(v);
                k++;
                j2 = 0;
            }
            j2++;
            u.I();
            v.I();
        }
    }

    private FGBasis b() {
        Polynomial polynomial;
        Polynomial g2;
        IntegerPolynomial gInt;
        IntegerPolynomial fq;
        int d3;
        int d1;
        int d;
        int d2;
        Resultant rf;
        IntegerPolynomial fq2;
        IntegerPolynomial fInt;
        Polynomial f;
        Polynomial polynomial2;
        Polynomial g3;
        IntegerPolynomial gInt2;
        Resultant rg;
        BigIntEuclidean r;
        BigIntPolynomial C;
        Resultant rg2;
        IntegerPolynomial h;
        IntegerPolynomial FInt;
        Polynomial fPrime;
        NTRUSigningKeyGenerationParameters nTRUSigningKeyGenerationParameters = this.g;
        int N = nTRUSigningKeyGenerationParameters.a1;
        int q = nTRUSigningKeyGenerationParameters.p1;
        int d4 = nTRUSigningKeyGenerationParameters.a2;
        int d12 = nTRUSigningKeyGenerationParameters.p2;
        int d22 = nTRUSigningKeyGenerationParameters.p3;
        int d32 = nTRUSigningKeyGenerationParameters.p4;
        int basisType = nTRUSigningKeyGenerationParameters.I4;
        int _2n1 = (N * 2) + 1;
        boolean primeCheck = nTRUSigningKeyGenerationParameters.H4;
        while (true) {
            if (this.g.N4 == 0) {
                polynomial = DenseTernaryPolynomial.S(N, d4 + 1, d4, new SecureRandom());
            } else {
                polynomial = ProductFormPolynomial.g(N, d12, d22, d32 + 1, d32, new SecureRandom());
            }
            g2 = polynomial;
            gInt = g2.a();
            if (!primeCheck || !gInt.G(_2n1).b.equals(BigInteger.ZERO)) {
                fq = gInt.v(q);
                if (fq != null) {
                    break;
                }
                IntegerPolynomial integerPolynomial = fq;
                IntegerPolynomial integerPolynomial2 = gInt;
                Polynomial polynomial3 = g2;
                int i = _2n1;
                int i2 = basisType;
                int i3 = d32;
                int i4 = d22;
                int i5 = d12;
                int i6 = d4;
            }
        }
        Resultant rf2 = gInt.H();
        while (true) {
            if (this.g.N4 == 0) {
                polynomial2 = DenseTernaryPolynomial.S(N, d4 + 1, d4, new SecureRandom());
                d3 = d32;
                d2 = d22;
                d1 = d12;
                d = d4;
                rf = rf2;
                fq2 = fq;
                fInt = gInt;
                f = g2;
            } else {
                int i7 = d12;
                d = d4;
                rf = rf2;
                int i8 = d22;
                d1 = d12;
                fq2 = fq;
                int i9 = d32 + 1;
                d2 = d22;
                fInt = gInt;
                int i10 = d32;
                d3 = d32;
                f = g2;
                polynomial2 = ProductFormPolynomial.g(N, i7, i8, i9, i10, new SecureRandom());
            }
            g3 = polynomial2;
            gInt2 = g3.a();
            if (primeCheck && gInt2.G(_2n1).b.equals(BigInteger.ZERO)) {
                g2 = f;
                gInt = fInt;
                fq = fq2;
                rf2 = rf;
                d22 = d2;
                d4 = d;
                d12 = d1;
                d32 = d3;
            } else if (gInt2.v(q) != null) {
                rg = gInt2.H();
                r = BigIntEuclidean.a(rf.b, rg.b);
                if (r.c.equals(BigInteger.ONE)) {
                    break;
                }
                BigIntEuclidean bigIntEuclidean = r;
                Resultant resultant = rg;
                IntegerPolynomial integerPolynomial3 = gInt2;
                Polynomial polynomial4 = g3;
                int i11 = _2n1;
                int i12 = basisType;
                Polynomial f2 = f;
                IntegerPolynomial fInt2 = fInt;
                IntegerPolynomial fq3 = fq2;
                Resultant rf3 = rf;
                d22 = d2;
                d4 = d;
                d12 = d1;
                d32 = d3;
                rf2 = rf3;
                g2 = f2;
                gInt = fInt2;
                fq = fq3;
            } else {
                IntegerPolynomial integerPolynomial4 = gInt2;
                Polynomial polynomial5 = g3;
                int i13 = _2n1;
                int i14 = basisType;
                Polynomial f3 = f;
                IntegerPolynomial fInt3 = fInt;
                IntegerPolynomial fq4 = fq2;
                Resultant rf4 = rf;
                d22 = d2;
                d4 = d;
                d12 = d1;
                d32 = d3;
                rf2 = rf4;
                g2 = f3;
                gInt = fInt3;
                fq = fq4;
            }
        }
        BigIntPolynomial A = (BigIntPolynomial) rf.a.clone();
        int i15 = _2n1;
        int basisType2 = basisType;
        A.h(r.a.multiply(BigInteger.valueOf((long) q)));
        BigIntPolynomial B = (BigIntPolynomial) rg.a.clone();
        BigIntEuclidean bigIntEuclidean2 = r;
        Resultant rg3 = rg;
        B.h(r.b.multiply(BigInteger.valueOf((long) (-q))));
        if (this.g.L4 == 0) {
            int[] fRevCoeffs = new int[N];
            int[] gRevCoeffs = new int[N];
            fRevCoeffs[0] = fInt.c[0];
            gRevCoeffs[0] = gInt2.c[0];
            for (int i16 = 1; i16 < N; i16++) {
                fRevCoeffs[i16] = fInt.c[N - i16];
                gRevCoeffs[i16] = gInt2.c[N - i16];
            }
            IntegerPolynomial fRev = new IntegerPolynomial(fRevCoeffs);
            IntegerPolynomial gRev = new IntegerPolynomial(gRevCoeffs);
            boolean z = primeCheck;
            IntegerPolynomial t = f.e(fRev);
            int[] iArr = fRevCoeffs;
            t.h(g3.e(gRev));
            Resultant rt = t.H();
            IntegerPolynomial integerPolynomial5 = t;
            BigIntPolynomial C2 = fRev.b(B);
            int[] iArr2 = gRevCoeffs;
            C2.a(gRev.b(A));
            BigIntPolynomial C3 = C2.g(rt.a);
            C3.c(rt.b);
            rg2 = rg3;
            Resultant rg4 = rf;
            C = C3;
        } else {
            int log10N = 0;
            for (int i17 = 1; i17 < N; i17 *= 10) {
                log10N++;
            }
            BigDecimalPolynomial fInv = rf.a.b(new BigDecimal(rf.b), B.d() + 1 + log10N);
            rg2 = rg3;
            Resultant resultant2 = rf;
            BigDecimalPolynomial gInv = rg2.a.b(new BigDecimal(rg2.b), A.d() + 1 + log10N);
            BigDecimalPolynomial Cdec = fInv.f(B);
            Cdec.a(gInv.f(A));
            Cdec.d();
            C = Cdec.h();
        }
        BigIntPolynomial F = (BigIntPolynomial) B.clone();
        F.j(f.b(C));
        BigIntPolynomial G = (BigIntPolynomial) A.clone();
        G.j(g3.b(C));
        Polynomial FInt2 = new IntegerPolynomial(F);
        IntegerPolynomial GInt = new IntegerPolynomial(G);
        Polynomial FInt3 = FInt2;
        BigIntPolynomial bigIntPolynomial = A;
        BigIntPolynomial bigIntPolynomial2 = G;
        Resultant resultant3 = rg2;
        IntegerPolynomial integerPolynomial6 = gInt2;
        d(fInt, gInt2, FInt3, GInt, N);
        if (basisType2 == 0) {
            fPrime = FInt3;
            h = g3.c(fq2, q);
            FInt = FInt3;
        } else {
            fPrime = g3;
            FInt = FInt3;
            h = FInt.c(fq2, q);
        }
        h.A(q);
        Polynomial polynomial6 = g3;
        BigIntPolynomial bigIntPolynomial3 = F;
        BigIntPolynomial bigIntPolynomial4 = B;
        Polynomial polynomial7 = f;
        IntegerPolynomial integerPolynomial7 = fInt;
        IntegerPolynomial integerPolynomial8 = fq2;
        BigIntPolynomial bigIntPolynomial5 = C;
        return new FGBasis(f, fPrime, h, FInt, GInt, this.g);
    }

    public NTRUSigningPrivateKeyParameters.Basis c() {
        FGBasis basis;
        do {
            basis = b();
        } while (!basis.a());
        return basis;
    }

    public class BasisGenerationTask implements Callable<NTRUSigningPrivateKeyParameters.Basis> {
        private BasisGenerationTask() {
        }

        /* renamed from: a */
        public NTRUSigningPrivateKeyParameters.Basis call() {
            return NTRUSigningKeyPairGenerator.this.c();
        }
    }

    public class FGBasis extends NTRUSigningPrivateKeyParameters.Basis {
        public IntegerPolynomial e;
        public IntegerPolynomial f;

        FGBasis(Polynomial f2, Polynomial fPrime, IntegerPolynomial h, IntegerPolynomial F, IntegerPolynomial G, NTRUSigningKeyGenerationParameters params) {
            super(f2, fPrime, h, params);
            this.e = F;
            this.f = G;
        }

        /* access modifiers changed from: package-private */
        public boolean a() {
            NTRUSigningKeyGenerationParameters nTRUSigningKeyGenerationParameters = this.d;
            double keyNormBoundSq = nTRUSigningKeyGenerationParameters.G4;
            int q = nTRUSigningKeyGenerationParameters.p1;
            return ((double) this.e.k(q)) < keyNormBoundSq && ((double) this.f.k(q)) < keyNormBoundSq;
        }
    }
}
