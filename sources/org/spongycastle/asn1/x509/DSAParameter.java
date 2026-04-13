package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class DSAParameter extends ASN1Object {
    ASN1Integer c;
    ASN1Integer d;
    ASN1Integer f;

    public static DSAParameter f(Object obj) {
        if (obj instanceof DSAParameter) {
            return (DSAParameter) obj;
        }
        if (obj != null) {
            return new DSAParameter(ASN1Sequence.o(obj));
        }
        return null;
    }

    public DSAParameter(BigInteger p, BigInteger q, BigInteger g) {
        this.c = new ASN1Integer(p);
        this.d = new ASN1Integer(q);
        this.f = new ASN1Integer(g);
    }

    private DSAParameter(ASN1Sequence seq) {
        if (seq.size() == 3) {
            Enumeration e = seq.s();
            this.c = ASN1Integer.o(e.nextElement());
            this.d = ASN1Integer.o(e.nextElement());
            this.f = ASN1Integer.o(e.nextElement());
            return;
        }
        throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }

    public BigInteger g() {
        return this.c.q();
    }

    public BigInteger h() {
        return this.d.q();
    }

    public BigInteger e() {
        return this.f.q();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        v.a(this.f);
        return new DERSequence(v);
    }
}
