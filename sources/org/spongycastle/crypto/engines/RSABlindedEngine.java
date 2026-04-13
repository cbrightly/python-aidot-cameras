package org.spongycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.spongycastle.util.BigIntegers;

public class RSABlindedEngine implements AsymmetricBlockCipher {
    private static final BigInteger a = BigInteger.valueOf(1);
    private RSACoreEngine b = new RSACoreEngine();
    private RSAKeyParameters c;
    private SecureRandom d;

    public void a(boolean forEncryption, CipherParameters param) {
        this.b.e(forEncryption, param);
        if (param instanceof ParametersWithRandom) {
            ParametersWithRandom rParam = (ParametersWithRandom) param;
            this.c = (RSAKeyParameters) rParam.a();
            this.d = rParam.b();
            return;
        }
        this.c = (RSAKeyParameters) param;
        this.d = new SecureRandom();
    }

    public int c() {
        return this.b.c();
    }

    public int b() {
        return this.b.d();
    }

    public byte[] d(byte[] in, int inOff, int inLen) {
        BigInteger result;
        if (this.c != null) {
            BigInteger input = this.b.a(in, inOff, inLen);
            RSAKeyParameters rSAKeyParameters = this.c;
            if (rSAKeyParameters instanceof RSAPrivateCrtKeyParameters) {
                RSAPrivateCrtKeyParameters k = (RSAPrivateCrtKeyParameters) rSAKeyParameters;
                BigInteger e = k.h();
                if (e != null) {
                    BigInteger m = k.c();
                    BigInteger bigInteger = a;
                    BigInteger r = BigIntegers.c(bigInteger, m.subtract(bigInteger), this.d);
                    result = this.b.f(r.modPow(e, m).multiply(input).mod(m)).multiply(r.modInverse(m)).mod(m);
                    if (!input.equals(result.modPow(e, m))) {
                        throw new IllegalStateException("RSA engine faulty decryption/signing detected");
                    }
                } else {
                    result = this.b.f(input);
                }
            } else {
                result = this.b.f(input);
            }
            return this.b.b(result);
        }
        throw new IllegalStateException("RSA engine not initialised");
    }
}
