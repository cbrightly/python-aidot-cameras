package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.RSAKeyGenerationParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.spongycastle.math.Primes;
import org.spongycastle.math.ec.WNafUtil;

public class RSAKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private static final BigInteger g = BigInteger.valueOf(1);
    private RSAKeyGenerationParameters h;
    private int i;

    public void d(KeyGenerationParameters param) {
        RSAKeyGenerationParameters rSAKeyGenerationParameters = (RSAKeyGenerationParameters) param;
        this.h = rSAKeyGenerationParameters;
        this.i = c(rSAKeyGenerationParameters.b(), this.h.c());
    }

    public AsymmetricCipherKeyPair a() {
        BigInteger q;
        boolean p;
        BigInteger n;
        boolean done;
        BigInteger q2;
        BigInteger gcd;
        RSAKeyPairGenerator rSAKeyPairGenerator = this;
        AsymmetricCipherKeyPair result = null;
        boolean p2 = false;
        int strength = rSAKeyPairGenerator.h.b();
        int pbitlength = (strength + 1) / 2;
        int qbitlength = strength - pbitlength;
        int mindiffbits = (strength / 2) - 100;
        if (mindiffbits < strength / 3) {
            mindiffbits = strength / 3;
        }
        int minWeight = strength >> 2;
        BigInteger dLowerBound = BigInteger.valueOf(2).pow(strength / 2);
        BigInteger bigInteger = g;
        BigInteger squaredBound = bigInteger.shiftLeft(strength - 1);
        BigInteger minDiff = bigInteger.shiftLeft(mindiffbits);
        while (!p2) {
            BigInteger e = rSAKeyPairGenerator.h.d();
            BigInteger p3 = rSAKeyPairGenerator.b(pbitlength, e, squaredBound);
            while (true) {
                q = rSAKeyPairGenerator.b(qbitlength, e, squaredBound);
                BigInteger diff = q.subtract(p3).abs();
                if (diff.bitLength() >= mindiffbits && diff.compareTo(minDiff) > 0) {
                    n = p3.multiply(q);
                    done = p2;
                    if (n.bitLength() == strength) {
                        if (WNafUtil.e(n) >= minWeight) {
                            break;
                        }
                        p3 = rSAKeyPairGenerator.b(pbitlength, e, squaredBound);
                        p = done;
                    } else {
                        p3 = p3.max(q);
                        p = done;
                    }
                } else {
                    rSAKeyPairGenerator = this;
                    p = p2;
                    strength = strength;
                    pbitlength = pbitlength;
                    qbitlength = qbitlength;
                }
            }
            if (p3.compareTo(q) < 0) {
                BigInteger gcd2 = p3;
                BigInteger p4 = q;
                BigInteger q3 = gcd2;
                gcd = p4;
                q2 = q3;
            } else {
                gcd = p3;
                q2 = q;
            }
            BigInteger p5 = g;
            BigInteger pSub1 = gcd.subtract(p5);
            BigInteger qSub1 = q2.subtract(p5);
            BigInteger gcd3 = pSub1.gcd(qSub1);
            int strength2 = strength;
            BigInteger lcm = pSub1.divide(gcd3).multiply(qSub1);
            BigInteger bigInteger2 = gcd3;
            BigInteger gcd4 = e.modInverse(lcm);
            if (gcd4.compareTo(dLowerBound) <= 0) {
                rSAKeyPairGenerator = this;
                p2 = done;
                strength = strength2;
            } else {
                BigInteger bigInteger3 = lcm;
                BigInteger bigInteger4 = qSub1;
                BigInteger bigInteger5 = pSub1;
                BigInteger bigInteger6 = n;
                result = new AsymmetricCipherKeyPair(new RSAKeyParameters(false, n, e), new RSAPrivateCrtKeyParameters(n, e, gcd4, gcd, q2, gcd4.remainder(pSub1), gcd4.remainder(qSub1), q2.modInverse(gcd)));
                rSAKeyPairGenerator = this;
                strength = strength2;
                p2 = true;
                pbitlength = pbitlength;
                qbitlength = qbitlength;
            }
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public BigInteger b(int bitlength, BigInteger e, BigInteger sqrdBound) {
        for (int i2 = 0; i2 != bitlength * 5; i2++) {
            BigInteger p = new BigInteger(bitlength, 1, this.h.a());
            BigInteger mod = p.mod(e);
            BigInteger bigInteger = g;
            if (!mod.equals(bigInteger) && p.multiply(p).compareTo(sqrdBound) >= 0 && e(p) && e.gcd(p.subtract(bigInteger)).equals(bigInteger)) {
                return p;
            }
        }
        throw new IllegalStateException("unable to generate prime number for RSA key");
    }

    /* access modifiers changed from: protected */
    public boolean e(BigInteger x) {
        return !Primes.b(x) && Primes.e(x, this.h.a(), this.i);
    }

    private static int c(int bits, int certainty) {
        if (bits >= 1536) {
            if (certainty <= 100) {
                return 3;
            }
            if (certainty <= 128) {
                return 4;
            }
            return 4 + (((certainty - 128) + 1) / 2);
        } else if (bits >= 1024) {
            if (certainty <= 100) {
                return 4;
            }
            if (certainty <= 112) {
                return 5;
            }
            return (((certainty - 112) + 1) / 2) + 5;
        } else if (bits >= 512) {
            if (certainty <= 80) {
                return 5;
            }
            if (certainty <= 100) {
                return 7;
            }
            return (((certainty - 100) + 1) / 2) + 7;
        } else if (certainty <= 80) {
            return 40;
        } else {
            return 40 + (((certainty - 80) + 1) / 2);
        }
    }
}
