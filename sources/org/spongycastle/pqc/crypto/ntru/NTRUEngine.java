package org.spongycastle.pqc.crypto.ntru;

import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.pqc.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.Polynomial;
import org.spongycastle.pqc.math.ntru.polynomial.ProductFormPolynomial;
import org.spongycastle.pqc.math.ntru.polynomial.SparseTernaryPolynomial;
import org.spongycastle.util.Arrays;

public class NTRUEngine implements AsymmetricBlockCipher {
    private boolean a;
    private NTRUEncryptionParameters b;
    private NTRUEncryptionPublicKeyParameters c;
    private NTRUEncryptionPrivateKeyParameters d;
    private SecureRandom e;

    public void a(boolean forEncryption, CipherParameters parameters) {
        this.a = forEncryption;
        if (forEncryption) {
            if (parameters instanceof ParametersWithRandom) {
                ParametersWithRandom p = (ParametersWithRandom) parameters;
                this.e = p.b();
                this.c = (NTRUEncryptionPublicKeyParameters) p.a();
            } else {
                this.e = new SecureRandom();
                this.c = (NTRUEncryptionPublicKeyParameters) parameters;
            }
            this.b = this.c.b();
            return;
        }
        NTRUEncryptionPrivateKeyParameters nTRUEncryptionPrivateKeyParameters = (NTRUEncryptionPrivateKeyParameters) parameters;
        this.d = nTRUEncryptionPrivateKeyParameters;
        this.b = nTRUEncryptionPrivateKeyParameters.b();
    }

    public int c() {
        return this.b.p3;
    }

    public int b() {
        NTRUEncryptionParameters nTRUEncryptionParameters = this.b;
        return ((nTRUEncryptionParameters.c * o(nTRUEncryptionParameters.d)) + 7) / 8;
    }

    public byte[] d(byte[] in, int inOff, int len) {
        byte[] tmp = new byte[len];
        System.arraycopy(in, inOff, tmp, 0, len);
        if (this.a) {
            return l(tmp, this.c);
        }
        return k(tmp, this.d);
    }

    private byte[] l(byte[] m, NTRUEncryptionPublicKeyParameters pubKey) {
        byte[] bArr = m;
        IntegerPolynomial pub2 = pubKey.f;
        NTRUEncryptionParameters nTRUEncryptionParameters = this.b;
        int N = nTRUEncryptionParameters.c;
        int q = nTRUEncryptionParameters.d;
        int maxLenBytes = nTRUEncryptionParameters.p3;
        int minCallsMask = nTRUEncryptionParameters.p4;
        int bufferLenBits = nTRUEncryptionParameters.z4;
        int dm0 = nTRUEncryptionParameters.B4;
        int pkLen = nTRUEncryptionParameters.C4;
        int minCallsMask2 = nTRUEncryptionParameters.F4;
        boolean hashSeed = nTRUEncryptionParameters.G4;
        byte[] oid = nTRUEncryptionParameters.H4;
        int l = bArr.length;
        if (maxLenBytes > 255) {
            int i = l;
            throw new IllegalArgumentException("llen values bigger than 1 are not supported");
        } else if (l <= maxLenBytes) {
            while (true) {
                byte[] b2 = new byte[(minCallsMask / 8)];
                boolean hashSeed2 = hashSeed;
                this.e.nextBytes(b2);
                byte[] p0 = new byte[((maxLenBytes + 1) - l)];
                int minCallsMask3 = minCallsMask2;
                byte[] M = new byte[(bufferLenBits / 8)];
                int db = minCallsMask;
                System.arraycopy(b2, 0, M, 0, b2.length);
                M[b2.length] = (byte) l;
                System.arraycopy(bArr, 0, M, b2.length + 1, bArr.length);
                System.arraycopy(p0, 0, M, b2.length + 1 + bArr.length, p0.length);
                IntegerPolynomial mTrin = IntegerPolynomial.t(M, N);
                byte[] bh = pub2.P(q);
                byte[] hTrunc = i(bh, pkLen / 8);
                int l2 = l;
                byte[] oid2 = oid;
                boolean hashSeed3 = hashSeed2;
                byte[] bArr2 = p0;
                byte[] bArr3 = bh;
                int minCallsMask4 = minCallsMask3;
                int bufferLenBits2 = bufferLenBits;
                int pkLen2 = pkLen;
                byte[] sData = f(oid, m, l2, b2, hTrunc);
                IntegerPolynomial R = n(sData, M).c(pub2, q);
                IntegerPolynomial R4 = (IntegerPolynomial) R.clone();
                R4.A(4);
                mTrin.h(e(R4.P(4), N, minCallsMask4, hashSeed3));
                mTrin.y();
                byte[] bArr4 = sData;
                if (mTrin.m(-1) < dm0) {
                    NTRUEncryptionPublicKeyParameters nTRUEncryptionPublicKeyParameters = pubKey;
                    hashSeed = hashSeed3;
                    minCallsMask2 = minCallsMask4;
                    bufferLenBits = bufferLenBits2;
                    minCallsMask = db;
                    oid = oid2;
                    l = l2;
                    pkLen = pkLen2;
                    bArr = m;
                } else if (mTrin.m(0) < dm0) {
                    NTRUEncryptionPublicKeyParameters nTRUEncryptionPublicKeyParameters2 = pubKey;
                    hashSeed = hashSeed3;
                    minCallsMask2 = minCallsMask4;
                    bufferLenBits = bufferLenBits2;
                    minCallsMask = db;
                    oid = oid2;
                    l = l2;
                    pkLen = pkLen2;
                    bArr = m;
                } else if (mTrin.m(1) < dm0) {
                    NTRUEncryptionPublicKeyParameters nTRUEncryptionPublicKeyParameters3 = pubKey;
                    hashSeed = hashSeed3;
                    minCallsMask2 = minCallsMask4;
                    bufferLenBits = bufferLenBits2;
                    minCallsMask = db;
                    oid = oid2;
                    l = l2;
                    pkLen = pkLen2;
                    bArr = m;
                } else {
                    R.i(mTrin, q);
                    R.o(q);
                    return R.P(q);
                }
            }
        } else {
            int l3 = l;
            byte[] bArr5 = oid;
            boolean z = hashSeed;
            throw new DataLengthException("Message too long: " + l3 + ">" + maxLenBytes);
        }
    }

    private byte[] f(byte[] oid, byte[] m, int l, byte[] b2, byte[] hTrunc) {
        byte[] sData = new byte[(oid.length + l + b2.length + hTrunc.length)];
        System.arraycopy(oid, 0, sData, 0, oid.length);
        System.arraycopy(m, 0, sData, oid.length, m.length);
        System.arraycopy(b2, 0, sData, oid.length + m.length, b2.length);
        System.arraycopy(hTrunc, 0, sData, oid.length + m.length + b2.length, hTrunc.length);
        return sData;
    }

    private Polynomial n(byte[] seed, byte[] M) {
        IndexGenerator ig = new IndexGenerator(seed, this.b);
        NTRUEncryptionParameters nTRUEncryptionParameters = this.b;
        if (nTRUEncryptionParameters.K4 == 1) {
            return new ProductFormPolynomial(new SparseTernaryPolynomial(m(ig, nTRUEncryptionParameters.p0)), new SparseTernaryPolynomial(m(ig, this.b.a1)), new SparseTernaryPolynomial(m(ig, this.b.p1)));
        }
        int dr = nTRUEncryptionParameters.z;
        boolean sparse = nTRUEncryptionParameters.I4;
        int[] r = m(ig, dr);
        if (sparse) {
            return new SparseTernaryPolynomial(r);
        }
        return new DenseTernaryPolynomial(r);
    }

    private int[] m(IndexGenerator ig, int dr) {
        int[] r = new int[this.b.c];
        for (int coeff = -1; coeff <= 1; coeff += 2) {
            int t = 0;
            while (t < dr) {
                int i = ig.d();
                if (r[i] == 0) {
                    r[i] = coeff;
                    t++;
                }
            }
        }
        return r;
    }

    private IntegerPolynomial e(byte[] seed, int N, int minCallsR, boolean hashSeed) {
        int i = N;
        int i2 = minCallsR;
        Digest hashAlg = this.b.L4;
        int hashLen = hashAlg.e();
        byte[] buf = new byte[(i2 * hashLen)];
        byte[] Z = hashSeed ? h(hashAlg, seed) : seed;
        int counter = 0;
        while (counter < i2) {
            hashAlg.update(Z, 0, Z.length);
            p(hashAlg, counter);
            System.arraycopy(g(hashAlg), 0, buf, counter * hashLen, hashLen);
            counter++;
        }
        IntegerPolynomial i3 = new IntegerPolynomial(i);
        while (true) {
            int cur = 0;
            for (int index = 0; index != buf.length; index++) {
                int O = buf[index] & 255;
                if (O < 243) {
                    for (int terIdx = 0; terIdx < 4; terIdx++) {
                        int rem3 = O % 3;
                        i3.c[cur] = rem3 - 1;
                        cur++;
                        if (cur == i) {
                            return i3;
                        }
                        O = (O - rem3) / 3;
                    }
                    i3.c[cur] = O - 1;
                    cur++;
                    if (cur == i) {
                        return i3;
                    }
                }
            }
            if (cur >= i) {
                return i3;
            }
            hashAlg.update(Z, 0, Z.length);
            p(hashAlg, counter);
            buf = g(hashAlg);
            counter++;
        }
    }

    private void p(Digest hashAlg, int counter) {
        hashAlg.d((byte) (counter >> 24));
        hashAlg.d((byte) (counter >> 16));
        hashAlg.d((byte) (counter >> 8));
        hashAlg.d((byte) counter);
    }

    private byte[] g(Digest hashAlg) {
        byte[] tmp = new byte[hashAlg.e()];
        hashAlg.c(tmp, 0);
        return tmp;
    }

    private byte[] h(Digest hashAlg, byte[] input) {
        byte[] tmp = new byte[hashAlg.e()];
        hashAlg.update(input, 0, input.length);
        hashAlg.c(tmp, 0);
        return tmp;
    }

    private byte[] k(byte[] data, NTRUEncryptionPrivateKeyParameters privKey) {
        NTRUEncryptionPrivateKeyParameters nTRUEncryptionPrivateKeyParameters = privKey;
        Polynomial priv_t = nTRUEncryptionPrivateKeyParameters.f;
        IntegerPolynomial priv_fp = nTRUEncryptionPrivateKeyParameters.q;
        IntegerPolynomial pub2 = nTRUEncryptionPrivateKeyParameters.x;
        NTRUEncryptionParameters nTRUEncryptionParameters = this.b;
        int N = nTRUEncryptionParameters.c;
        int q = nTRUEncryptionParameters.d;
        int db = nTRUEncryptionParameters.p4;
        int maxMsgLenBytes = nTRUEncryptionParameters.p3;
        int dm0 = nTRUEncryptionParameters.B4;
        int pkLen = nTRUEncryptionParameters.C4;
        int minCallsMask = nTRUEncryptionParameters.F4;
        boolean hashSeed = nTRUEncryptionParameters.G4;
        byte[] oid = nTRUEncryptionParameters.H4;
        if (maxMsgLenBytes <= 255) {
            int bLen = db / 8;
            IntegerPolynomial e2 = IntegerPolynomial.s(data, N, q);
            int i = db;
            IntegerPolynomial ci = j(e2, priv_t, priv_fp);
            if (ci.m(-1) >= dm0) {
                Polynomial polynomial = priv_t;
                if (ci.m(0) < dm0) {
                    throw new InvalidCipherTextException("Less than dm0 coefficients equal 0");
                } else if (ci.m(1) >= dm0) {
                    IntegerPolynomial cR = (IntegerPolynomial) e2.clone();
                    cR.M(ci);
                    cR.A(q);
                    IntegerPolynomial cR4 = (IntegerPolynomial) cR.clone();
                    IntegerPolynomial integerPolynomial = e2;
                    cR4.A(4);
                    byte[] coR4 = cR4.P(4);
                    IntegerPolynomial integerPolynomial2 = priv_fp;
                    IntegerPolynomial mask = e(coR4, N, minCallsMask, hashSeed);
                    byte[] bArr = coR4;
                    IntegerPolynomial cMTrin = ci;
                    cMTrin.M(mask);
                    cMTrin.y();
                    IntegerPolynomial integerPolynomial3 = mask;
                    byte[] cM = cMTrin.Q();
                    IntegerPolynomial integerPolynomial4 = cMTrin;
                    byte[] cb = new byte[bLen];
                    IntegerPolynomial integerPolynomial5 = cR4;
                    System.arraycopy(cM, 0, cb, 0, bLen);
                    boolean z = hashSeed;
                    int cl = 255 & cM[bLen];
                    if (cl <= maxMsgLenBytes) {
                        byte[] cm = new byte[cl];
                        int i2 = minCallsMask;
                        int i3 = N;
                        System.arraycopy(cM, bLen + 1, cm, 0, cl);
                        byte[] p0 = new byte[(cM.length - ((bLen + 1) + cl))];
                        byte[] cm2 = cm;
                        int bLen2 = bLen;
                        System.arraycopy(cM, bLen + 1 + cl, p0, 0, p0.length);
                        if (Arrays.u(p0, new byte[p0.length])) {
                            byte[] bh = pub2.P(q);
                            byte[] cm3 = cm2;
                            int i4 = bLen2;
                            byte[] cm4 = cm3;
                            byte[] bArr2 = oid;
                            int i5 = cl;
                            byte[] bArr3 = bh;
                            int i6 = pkLen;
                            byte[] cm5 = cm4;
                            IntegerPolynomial cRPrime = n(f(oid, cm4, cl, cb, i(bh, pkLen / 8)), cm5).e(pub2);
                            cRPrime.A(q);
                            if (cRPrime.equals(cR)) {
                                return cm5;
                            }
                            throw new InvalidCipherTextException("Invalid message encoding");
                        }
                        throw new InvalidCipherTextException("The message is not followed by zeroes");
                    }
                    int i7 = bLen;
                    byte[] bArr4 = oid;
                    throw new InvalidCipherTextException("Message too long: " + cl + ">" + maxMsgLenBytes);
                } else {
                    throw new InvalidCipherTextException("Less than dm0 coefficients equal 1");
                }
            } else {
                throw new InvalidCipherTextException("Less than dm0 coefficients equal -1");
            }
        } else {
            throw new DataLengthException("maxMsgLenBytes values bigger than 255 are not supported");
        }
    }

    /* access modifiers changed from: protected */
    public IntegerPolynomial j(IntegerPolynomial e2, Polynomial priv_t, IntegerPolynomial priv_fp) {
        IntegerPolynomial a2;
        NTRUEncryptionParameters nTRUEncryptionParameters = this.b;
        if (nTRUEncryptionParameters.J4) {
            a2 = priv_t.c(e2, nTRUEncryptionParameters.d);
            a2.B(3);
            a2.h(e2);
        } else {
            a2 = priv_t.c(e2, nTRUEncryptionParameters.d);
        }
        a2.j(this.b.d);
        a2.y();
        IntegerPolynomial c2 = this.b.J4 ? a2 : new DenseTernaryPolynomial(a2).c(priv_fp, 3);
        c2.j(3);
        return c2;
    }

    private byte[] i(byte[] src, int len) {
        byte[] tmp = new byte[len];
        System.arraycopy(src, 0, tmp, 0, len < src.length ? len : src.length);
        return tmp;
    }

    private int o(int value) {
        if (value == 2048) {
            return 11;
        }
        throw new IllegalStateException("log2 not fully implemented");
    }
}
