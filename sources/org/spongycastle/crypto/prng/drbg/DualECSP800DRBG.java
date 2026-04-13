package org.spongycastle.crypto.prng.drbg;

import com.alibaba.fastjson.asm.Opcodes;
import java.math.BigInteger;
import org.spongycastle.asn1.nist.NISTNamedCurves;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.prng.EntropySource;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class DualECSP800DRBG implements SP80090DRBG {
    private static final BigInteger a;
    private static final BigInteger b;
    private static final BigInteger c;
    private static final BigInteger d;
    private static final BigInteger e;
    private static final BigInteger f;
    private static final BigInteger g;
    private static final BigInteger h;
    private static final BigInteger i;
    private static final BigInteger j;
    private static final BigInteger k;
    private static final BigInteger l;
    private static final DualECPoints[] m;
    private Digest n;
    private long o;
    private EntropySource p;
    private int q;
    private int r;
    private int s;
    private ECPoint t;
    private ECPoint u;
    private byte[] v;
    private int w;
    private ECMultiplier x;

    static {
        BigInteger bigInteger = new BigInteger("6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296", 16);
        a = bigInteger;
        BigInteger bigInteger2 = new BigInteger("4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5", 16);
        b = bigInteger2;
        BigInteger bigInteger3 = new BigInteger("c97445f45cdef9f0d3e05e1e585fc297235b82b5be8ff3efca67c59852018192", 16);
        c = bigInteger3;
        BigInteger bigInteger4 = new BigInteger("b28ef557ba31dfcbdd21ac46e2a91e3c304f44cb87058ada2cb815151e610046", 16);
        d = bigInteger4;
        BigInteger bigInteger5 = new BigInteger("aa87ca22be8b05378eb1c71ef320ad746e1d3b628ba79b9859f741e082542a385502f25dbf55296c3a545e3872760ab7", 16);
        e = bigInteger5;
        BigInteger bigInteger6 = new BigInteger("3617de4a96262c6f5d9e98bf9292dc29f8f41dbd289a147ce9da3113b5f0b8c00a60b1ce1d7e819d7a431d7c90ea0e5f", 16);
        f = bigInteger6;
        BigInteger bigInteger7 = new BigInteger("8e722de3125bddb05580164bfe20b8b432216a62926c57502ceede31c47816edd1e89769124179d0b695106428815065", 16);
        g = bigInteger7;
        BigInteger bigInteger8 = new BigInteger("023b1660dd701d0839fd45eec36f9ee7b32e13b315dc02610aa1b636e346df671f790f84c5e09b05674dbb7e45c803dd", 16);
        h = bigInteger8;
        BigInteger bigInteger9 = new BigInteger("c6858e06b70404e9cd9e3ecb662395b4429c648139053fb521f828af606b4d3dbaa14b5e77efe75928fe1dc127a2ffa8de3348b3c1856a429bf97e7e31c2e5bd66", 16);
        i = bigInteger9;
        BigInteger bigInteger10 = new BigInteger("11839296a789a3bc0045c8a5fb42c7d1bd998f54449579b446817afbd17273e662c97ee72995ef42640c550b9013fad0761353c7086a272c24088be94769fd16650", 16);
        j = bigInteger10;
        BigInteger bigInteger11 = new BigInteger("1b9fa3e518d683c6b65763694ac8efbaec6fab44f2276171a42726507dd08add4c3b3f4c1ebc5b1222ddba077f722943b24c3edfa0f85fe24d0c8c01591f0be6f63", 16);
        k = bigInteger11;
        BigInteger bigInteger12 = new BigInteger("1f3bdba585295d9a1110d1df1f9430ef8442c5018976ff3437ef91b81dc0b8132c8d5c39c32d0e004a3092b7d327c0e7a4d26d2c7b69b58f9066652911e457779de", 16);
        l = bigInteger12;
        DualECPoints[] dualECPointsArr = new DualECPoints[3];
        m = dualECPointsArr;
        ECCurve.Fp curve = (ECCurve.Fp) NISTNamedCurves.b("P-256").e();
        dualECPointsArr[0] = new DualECPoints(128, curve.f(bigInteger, bigInteger2), curve.f(bigInteger3, bigInteger4), 1);
        ECCurve.Fp curve2 = (ECCurve.Fp) NISTNamedCurves.b("P-384").e();
        dualECPointsArr[1] = new DualECPoints(Opcodes.CHECKCAST, curve2.f(bigInteger5, bigInteger6), curve2.f(bigInteger7, bigInteger8), 1);
        ECCurve.Fp curve3 = (ECCurve.Fp) NISTNamedCurves.b("P-521").e();
        dualECPointsArr[2] = new DualECPoints(256, curve3.f(bigInteger9, bigInteger10), curve3.f(bigInteger11, bigInteger12), 1);
    }

    public int a(byte[] output, byte[] additionalInput, boolean predictionResistant) {
        BigInteger s2;
        int numberOfBits = output.length * 8;
        int m2 = output.length / this.s;
        if (Utils.d(additionalInput, 512)) {
            throw new IllegalArgumentException("Additional input too large");
        } else if (this.o + ((long) m2) > IjkMediaMeta.AV_CH_WIDE_LEFT) {
            return -1;
        } else {
            if (predictionResistant) {
                b(additionalInput);
                additionalInput = null;
            }
            if (additionalInput != null) {
                s2 = new BigInteger(1, f(this.v, Utils.c(this.n, additionalInput, this.r)));
            } else {
                s2 = new BigInteger(1, this.v);
            }
            Arrays.F(output, (byte) 0);
            int outOffset = 0;
            for (int i2 = 0; i2 < m2; i2++) {
                s2 = d(this.t, s2);
                byte[] r2 = d(this.u, s2).toByteArray();
                int length = r2.length;
                int i3 = this.s;
                if (length > i3) {
                    System.arraycopy(r2, r2.length - i3, output, outOffset, i3);
                } else {
                    System.arraycopy(r2, 0, output, (i3 - r2.length) + outOffset, r2.length);
                }
                outOffset += this.s;
                this.o++;
            }
            if (outOffset < output.length) {
                s2 = d(this.t, s2);
                byte[] r3 = d(this.u, s2).toByteArray();
                int required = output.length - outOffset;
                int length2 = r3.length;
                int i4 = this.s;
                if (length2 > i4) {
                    System.arraycopy(r3, r3.length - i4, output, outOffset, required);
                } else {
                    System.arraycopy(r3, 0, output, (i4 - r3.length) + outOffset, required);
                }
                this.o++;
            }
            this.v = BigIntegers.a(this.w, d(this.t, s2));
            return numberOfBits;
        }
    }

    public void b(byte[] additionalInput) {
        if (!Utils.d(additionalInput, 512)) {
            this.v = Utils.c(this.n, Arrays.s(e(this.v, this.r), c(), additionalInput), this.r);
            this.o = 0;
            return;
        }
        throw new IllegalArgumentException("Additional input string too large");
    }

    private byte[] c() {
        byte[] entropy = this.p.a();
        if (entropy.length >= (this.q + 7) / 8) {
            return entropy;
        }
        throw new IllegalStateException("Insufficient entropy provided by entropy source");
    }

    private byte[] f(byte[] a2, byte[] b2) {
        if (b2 == null) {
            return a2;
        }
        byte[] rv = new byte[a2.length];
        for (int i2 = 0; i2 != rv.length; i2++) {
            rv[i2] = (byte) (a2[i2] ^ b2[i2]);
        }
        return rv;
    }

    private byte[] e(byte[] s2, int seedlen) {
        if (seedlen % 8 == 0) {
            return s2;
        }
        int shift = 8 - (seedlen % 8);
        int carry = 0;
        for (int i2 = s2.length - 1; i2 >= 0; i2--) {
            int b2 = s2[i2] & 255;
            s2[i2] = (byte) ((b2 << shift) | (carry >> (8 - shift)));
            carry = b2;
        }
        return s2;
    }

    private BigInteger d(ECPoint p2, BigInteger s2) {
        return this.x.a(p2, s2).y().f().t();
    }
}
