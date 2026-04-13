package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.field.PolynomialExtensionField;

public class X9ECParameters extends ASN1Object implements X9ObjectIdentifiers {
    private static final BigInteger c = BigInteger.valueOf(1);
    private X9FieldID d;
    private ECCurve f;
    private X9ECPoint q;
    private BigInteger x;
    private BigInteger y;
    private byte[] z;

    private X9ECParameters(ASN1Sequence seq) {
        if (!(seq.r(0) instanceof ASN1Integer) || !((ASN1Integer) seq.r(0)).r().equals(c)) {
            throw new IllegalArgumentException("bad version in X9ECParameters");
        }
        X9Curve x9c = new X9Curve(X9FieldID.f(seq.r(1)), ASN1Sequence.o(seq.r(2)));
        this.f = x9c.e();
        ASN1Encodable r = seq.r(3);
        if (r instanceof X9ECPoint) {
            this.q = (X9ECPoint) r;
        } else {
            this.q = new X9ECPoint(this.f, (ASN1OctetString) r);
        }
        this.x = ((ASN1Integer) seq.r(4)).r();
        this.z = x9c.getSeed();
        if (seq.size() == 6) {
            this.y = ((ASN1Integer) seq.r(5)).r();
        }
    }

    public static X9ECParameters h(Object obj) {
        if (obj instanceof X9ECParameters) {
            return (X9ECParameters) obj;
        }
        if (obj != null) {
            return new X9ECParameters(ASN1Sequence.o(obj));
        }
        return null;
    }

    public X9ECParameters(ECCurve curve, X9ECPoint g, BigInteger n, BigInteger h) {
        this(curve, g, n, h, (byte[]) null);
    }

    public X9ECParameters(ECCurve curve, ECPoint g, BigInteger n, BigInteger h, byte[] seed) {
        this(curve, new X9ECPoint(g), n, h, seed);
    }

    public X9ECParameters(ECCurve curve, X9ECPoint g, BigInteger n, BigInteger h, byte[] seed) {
        this.f = curve;
        this.q = g;
        this.x = n;
        this.y = h;
        this.z = seed;
        if (ECAlgorithms.k(curve)) {
            this.d = new X9FieldID(curve.s().b());
        } else if (ECAlgorithms.i(curve)) {
            int[] exponents = ((PolynomialExtensionField) curve.s()).c().a();
            if (exponents.length == 3) {
                this.d = new X9FieldID(exponents[2], exponents[1]);
            } else if (exponents.length == 5) {
                this.d = new X9FieldID(exponents[4], exponents[1], exponents[2], exponents[3]);
            } else {
                throw new IllegalArgumentException("Only trinomial and pentomial curves are supported");
            }
        } else {
            throw new IllegalArgumentException("'curve' is of an unsupported type");
        }
    }

    public ECCurve e() {
        return this.f;
    }

    public ECPoint f() {
        return this.q.e();
    }

    public BigInteger i() {
        return this.x;
    }

    public BigInteger g() {
        return this.y;
    }

    public byte[] getSeed() {
        return this.z;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new ASN1Integer(c));
        v.a(this.d);
        v.a(new X9Curve(this.f, this.z));
        v.a(this.q);
        v.a(new ASN1Integer(this.x));
        BigInteger bigInteger = this.y;
        if (bigInteger != null) {
            v.a(new ASN1Integer(bigInteger));
        }
        return new DERSequence(v);
    }
}
