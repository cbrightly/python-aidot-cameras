package org.spongycastle.asn1.oiw;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class ElGamalParameter extends ASN1Object {
    ASN1Integer c;
    ASN1Integer d;

    public ElGamalParameter(BigInteger p, BigInteger g) {
        this.c = new ASN1Integer(p);
        this.d = new ASN1Integer(g);
    }

    private ElGamalParameter(ASN1Sequence seq) {
        Enumeration e = seq.s();
        this.c = (ASN1Integer) e.nextElement();
        this.d = (ASN1Integer) e.nextElement();
    }

    public static ElGamalParameter f(Object o) {
        if (o instanceof ElGamalParameter) {
            return (ElGamalParameter) o;
        }
        if (o != null) {
            return new ElGamalParameter(ASN1Sequence.o(o));
        }
        return null;
    }

    public BigInteger g() {
        return this.c.q();
    }

    public BigInteger e() {
        return this.d.q();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        return new DERSequence(v);
    }
}
