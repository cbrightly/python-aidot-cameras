package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECCurve;

public class X9Curve extends ASN1Object implements X9ObjectIdentifiers {
    private ECCurve c;
    private byte[] d;
    private ASN1ObjectIdentifier f = null;

    public X9Curve(ECCurve curve, byte[] seed) {
        this.c = curve;
        this.d = seed;
        f();
    }

    public X9Curve(X9FieldID fieldID, ASN1Sequence seq) {
        int k3;
        int k2;
        int k1;
        ASN1Sequence aSN1Sequence = seq;
        ASN1ObjectIdentifier e = fieldID.e();
        this.f = e;
        if (e.equals(X9ObjectIdentifiers.k3)) {
            BigInteger p = ((ASN1Integer) fieldID.g()).r();
            this.c = new ECCurve.Fp(p, new X9FieldElement(p, (ASN1OctetString) aSN1Sequence.r(0)).e().t(), new X9FieldElement(p, (ASN1OctetString) aSN1Sequence.r(1)).e().t());
        } else if (this.f.equals(X9ObjectIdentifiers.l3)) {
            ASN1Sequence parameters = ASN1Sequence.o(fieldID.g());
            int m = ((ASN1Integer) parameters.r(0)).r().intValue();
            ASN1ObjectIdentifier representation = (ASN1ObjectIdentifier) parameters.r(1);
            if (representation.equals(X9ObjectIdentifiers.n3)) {
                k1 = ASN1Integer.o(parameters.r(2)).r().intValue();
                k2 = 0;
                k3 = 0;
            } else if (representation.equals(X9ObjectIdentifiers.o3)) {
                ASN1Sequence pentanomial = ASN1Sequence.o(parameters.r(2));
                k1 = ASN1Integer.o(pentanomial.r(0)).r().intValue();
                k2 = ASN1Integer.o(pentanomial.r(1)).r().intValue();
                k3 = ASN1Integer.o(pentanomial.r(2)).r().intValue();
            } else {
                throw new IllegalArgumentException("This type of EC basis is not implemented");
            }
            int i = m;
            int i2 = k1;
            int i3 = k2;
            int i4 = k3;
            ECCurve.F2m f2m = r7;
            ECCurve.F2m f2m2 = new ECCurve.F2m(i, i2, i3, i4, new X9FieldElement(i, i2, i3, i4, (ASN1OctetString) aSN1Sequence.r(0)).e().t(), new X9FieldElement(i, i2, i3, i4, (ASN1OctetString) aSN1Sequence.r(1)).e().t());
            this.c = f2m;
        } else {
            throw new IllegalArgumentException("This type of ECCurve is not implemented");
        }
        if (seq.size() == 3) {
            this.d = ((DERBitString) aSN1Sequence.r(2)).q();
        }
    }

    private void f() {
        if (ECAlgorithms.k(this.c)) {
            this.f = X9ObjectIdentifiers.k3;
        } else if (ECAlgorithms.i(this.c)) {
            this.f = X9ObjectIdentifiers.l3;
        } else {
            throw new IllegalArgumentException("This type of ECCurve is not implemented");
        }
    }

    public ECCurve e() {
        return this.c;
    }

    public byte[] getSeed() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (this.f.equals(X9ObjectIdentifiers.k3)) {
            v.a(new X9FieldElement(this.c.n()).toASN1Primitive());
            v.a(new X9FieldElement(this.c.o()).toASN1Primitive());
        } else if (this.f.equals(X9ObjectIdentifiers.l3)) {
            v.a(new X9FieldElement(this.c.n()).toASN1Primitive());
            v.a(new X9FieldElement(this.c.o()).toASN1Primitive());
        }
        if (this.d != null) {
            v.a(new DERBitString(this.d));
        }
        return new DERSequence(v);
    }
}
