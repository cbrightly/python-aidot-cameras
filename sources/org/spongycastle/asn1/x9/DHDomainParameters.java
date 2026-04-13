package org.spongycastle.asn1.x9;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class DHDomainParameters extends ASN1Object {
    private ASN1Integer c;
    private ASN1Integer d;
    private ASN1Integer f;
    private ASN1Integer q;
    private DHValidationParms x;

    public static DHDomainParameters f(Object obj) {
        if (obj == null || (obj instanceof DHDomainParameters)) {
            return (DHDomainParameters) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new DHDomainParameters((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException("Invalid DHDomainParameters: " + obj.getClass().getName());
    }

    private DHDomainParameters(ASN1Sequence seq) {
        if (seq.size() < 3 || seq.size() > 5) {
            throw new IllegalArgumentException("Bad sequence size: " + seq.size());
        }
        Enumeration e = seq.s();
        this.c = ASN1Integer.o(e.nextElement());
        this.d = ASN1Integer.o(e.nextElement());
        this.f = ASN1Integer.o(e.nextElement());
        ASN1Encodable next = g(e);
        if (next != null && (next instanceof ASN1Integer)) {
            this.q = ASN1Integer.o(next);
            next = g(e);
        }
        if (next != null) {
            this.x = DHValidationParms.e(next.toASN1Primitive());
        }
    }

    private static ASN1Encodable g(Enumeration e) {
        if (e.hasMoreElements()) {
            return (ASN1Encodable) e.nextElement();
        }
        return null;
    }

    public ASN1Integer h() {
        return this.c;
    }

    public ASN1Integer e() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        v.a(this.f);
        ASN1Integer aSN1Integer = this.q;
        if (aSN1Integer != null) {
            v.a(aSN1Integer);
        }
        DHValidationParms dHValidationParms = this.x;
        if (dHValidationParms != null) {
            v.a(dHValidationParms);
        }
        return new DERSequence(v);
    }
}
