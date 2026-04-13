package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class RSAPrivateCrtKeyParameters extends RSAKeyParameters {
    private BigInteger a1;
    private BigInteger p0;
    private BigInteger p1;
    private BigInteger x;
    private BigInteger y;
    private BigInteger z;

    public RSAPrivateCrtKeyParameters(BigInteger modulus, BigInteger publicExponent, BigInteger privateExponent, BigInteger p, BigInteger q, BigInteger dP, BigInteger dQ, BigInteger qInv) {
        super(true, modulus, privateExponent);
        this.x = publicExponent;
        this.y = p;
        this.z = q;
        this.p0 = dP;
        this.a1 = dQ;
        this.p1 = qInv;
    }

    public BigInteger h() {
        return this.x;
    }

    public BigInteger g() {
        return this.y;
    }

    public BigInteger i() {
        return this.z;
    }

    public BigInteger e() {
        return this.p0;
    }

    public BigInteger f() {
        return this.a1;
    }

    public BigInteger j() {
        return this.p1;
    }
}
