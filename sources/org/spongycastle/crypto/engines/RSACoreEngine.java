package org.spongycastle.crypto.engines;

import java.math.BigInteger;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;

public class RSACoreEngine {
    private RSAKeyParameters a;
    private boolean b;

    RSACoreEngine() {
    }

    public void e(boolean forEncryption, CipherParameters param) {
        if (param instanceof ParametersWithRandom) {
            this.a = (RSAKeyParameters) ((ParametersWithRandom) param).a();
        } else {
            this.a = (RSAKeyParameters) param;
        }
        this.b = forEncryption;
    }

    public int c() {
        int bitSize = this.a.c().bitLength();
        if (this.b) {
            return ((bitSize + 7) / 8) - 1;
        }
        return (bitSize + 7) / 8;
    }

    public int d() {
        int bitSize = this.a.c().bitLength();
        if (this.b) {
            return (bitSize + 7) / 8;
        }
        return ((bitSize + 7) / 8) - 1;
    }

    public BigInteger a(byte[] in, int inOff, int inLen) {
        byte[] block;
        if (inLen > c() + 1) {
            throw new DataLengthException("input too large for RSA cipher.");
        } else if (inLen != c() + 1 || this.b) {
            if (inOff == 0 && inLen == in.length) {
                block = in;
            } else {
                block = new byte[inLen];
                System.arraycopy(in, inOff, block, 0, inLen);
            }
            BigInteger res = new BigInteger(1, block);
            if (res.compareTo(this.a.c()) < 0) {
                return res;
            }
            throw new DataLengthException("input too large for RSA cipher.");
        } else {
            throw new DataLengthException("input too large for RSA cipher.");
        }
    }

    public byte[] b(BigInteger result) {
        byte[] output = result.toByteArray();
        if (this.b) {
            if (output[0] == 0 && output.length > d()) {
                byte[] tmp = new byte[(output.length - 1)];
                System.arraycopy(output, 1, tmp, 0, tmp.length);
                return tmp;
            } else if (output.length < d()) {
                byte[] tmp2 = new byte[d()];
                System.arraycopy(output, 0, tmp2, tmp2.length - output.length, output.length);
                return tmp2;
            }
        } else if (output[0] == 0) {
            byte[] tmp3 = new byte[(output.length - 1)];
            System.arraycopy(output, 1, tmp3, 0, tmp3.length);
            return tmp3;
        }
        return output;
    }

    public BigInteger f(BigInteger input) {
        RSAKeyParameters rSAKeyParameters = this.a;
        if (!(rSAKeyParameters instanceof RSAPrivateCrtKeyParameters)) {
            return input.modPow(rSAKeyParameters.b(), this.a.c());
        }
        RSAPrivateCrtKeyParameters crtKey = (RSAPrivateCrtKeyParameters) rSAKeyParameters;
        BigInteger p = crtKey.g();
        BigInteger q = crtKey.i();
        BigInteger dP = crtKey.e();
        BigInteger dQ = crtKey.f();
        BigInteger qInv = crtKey.j();
        BigInteger mP = input.remainder(p).modPow(dP, p);
        BigInteger mQ = input.remainder(q).modPow(dQ, q);
        return mP.subtract(mQ).multiply(qInv).mod(p).multiply(q).add(mQ);
    }
}
