package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class RSAPrivateKey extends ASN1Object {
    private BigInteger a1;
    private BigInteger c;
    private BigInteger d;
    private BigInteger f;
    private BigInteger p0;
    private ASN1Sequence p1;
    private BigInteger q;
    private BigInteger x;
    private BigInteger y;
    private BigInteger z;

    public static RSAPrivateKey h(Object obj) {
        if (obj instanceof RSAPrivateKey) {
            return (RSAPrivateKey) obj;
        }
        if (obj != null) {
            return new RSAPrivateKey(ASN1Sequence.o(obj));
        }
        return null;
    }

    public RSAPrivateKey(BigInteger modulus, BigInteger publicExponent, BigInteger privateExponent, BigInteger prime1, BigInteger prime2, BigInteger exponent1, BigInteger exponent2, BigInteger coefficient) {
        this.p1 = null;
        this.c = BigInteger.valueOf(0);
        this.d = modulus;
        this.f = publicExponent;
        this.q = privateExponent;
        this.x = prime1;
        this.y = prime2;
        this.z = exponent1;
        this.p0 = exponent2;
        this.a1 = coefficient;
    }

    private RSAPrivateKey(ASN1Sequence seq) {
        this.p1 = null;
        Enumeration e = seq.s();
        BigInteger v = ((ASN1Integer) e.nextElement()).r();
        if (v.intValue() == 0 || v.intValue() == 1) {
            this.c = v;
            this.d = ((ASN1Integer) e.nextElement()).r();
            this.f = ((ASN1Integer) e.nextElement()).r();
            this.q = ((ASN1Integer) e.nextElement()).r();
            this.x = ((ASN1Integer) e.nextElement()).r();
            this.y = ((ASN1Integer) e.nextElement()).r();
            this.z = ((ASN1Integer) e.nextElement()).r();
            this.p0 = ((ASN1Integer) e.nextElement()).r();
            this.a1 = ((ASN1Integer) e.nextElement()).r();
            if (e.hasMoreElements()) {
                this.p1 = (ASN1Sequence) e.nextElement();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("wrong version for RSA private key");
    }

    public BigInteger i() {
        return this.d;
    }

    public BigInteger p() {
        return this.f;
    }

    public BigInteger o() {
        return this.q;
    }

    public BigInteger k() {
        return this.x;
    }

    public BigInteger n() {
        return this.y;
    }

    public BigInteger f() {
        return this.z;
    }

    public BigInteger g() {
        return this.p0;
    }

    public BigInteger e() {
        return this.a1;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer(this.c));
        v.a(new ASN1Integer(i()));
        v.a(new ASN1Integer(p()));
        v.a(new ASN1Integer(o()));
        v.a(new ASN1Integer(k()));
        v.a(new ASN1Integer(n()));
        v.a(new ASN1Integer(f()));
        v.a(new ASN1Integer(g()));
        v.a(new ASN1Integer(e()));
        ASN1Sequence aSN1Sequence = this.p1;
        if (aSN1Sequence != null) {
            v.a(aSN1Sequence);
        }
        return new DERSequence(v);
    }
}
