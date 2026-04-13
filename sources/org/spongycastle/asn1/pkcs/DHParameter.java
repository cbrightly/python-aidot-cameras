package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class DHParameter extends ASN1Object {
    ASN1Integer c;
    ASN1Integer d;
    ASN1Integer f;

    public DHParameter(BigInteger p, BigInteger g, int l) {
        this.c = new ASN1Integer(p);
        this.d = new ASN1Integer(g);
        if (l != 0) {
            this.f = new ASN1Integer((long) l);
        } else {
            this.f = null;
        }
    }

    public static DHParameter f(Object obj) {
        if (obj instanceof DHParameter) {
            return (DHParameter) obj;
        }
        if (obj != null) {
            return new DHParameter(ASN1Sequence.o(obj));
        }
        return null;
    }

    private DHParameter(ASN1Sequence seq) {
        Enumeration e = seq.s();
        this.c = ASN1Integer.o(e.nextElement());
        this.d = ASN1Integer.o(e.nextElement());
        if (e.hasMoreElements()) {
            this.f = (ASN1Integer) e.nextElement();
        } else {
            this.f = null;
        }
    }

    public BigInteger h() {
        return this.c.q();
    }

    public BigInteger e() {
        return this.d.q();
    }

    public BigInteger g() {
        ASN1Integer aSN1Integer = this.f;
        if (aSN1Integer == null) {
            return null;
        }
        return aSN1Integer.q();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        if (g() != null) {
            v.a(this.f);
        }
        return new DERSequence(v);
    }
}
