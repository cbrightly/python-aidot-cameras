package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class DomainParameters extends ASN1Object {
    private final ASN1Integer c;
    private final ASN1Integer d;
    private final ASN1Integer f;
    private final ASN1Integer q;
    private final ValidationParams x;

    public static DomainParameters f(Object obj) {
        if (obj instanceof DomainParameters) {
            return (DomainParameters) obj;
        }
        if (obj != null) {
            return new DomainParameters(ASN1Sequence.o(obj));
        }
        return null;
    }

    private DomainParameters(ASN1Sequence seq) {
        if (seq.size() < 3 || seq.size() > 5) {
            throw new IllegalArgumentException("Bad sequence size: " + seq.size());
        }
        Enumeration e = seq.s();
        this.c = ASN1Integer.o(e.nextElement());
        this.d = ASN1Integer.o(e.nextElement());
        this.f = ASN1Integer.o(e.nextElement());
        ASN1Encodable next = h(e);
        if (next == null || !(next instanceof ASN1Integer)) {
            this.q = null;
        } else {
            this.q = ASN1Integer.o(next);
            next = h(e);
        }
        if (next != null) {
            this.x = ValidationParams.getInstance(next.toASN1Primitive());
        } else {
            this.x = null;
        }
    }

    private static ASN1Encodable h(Enumeration e) {
        if (e.hasMoreElements()) {
            return (ASN1Encodable) e.nextElement();
        }
        return null;
    }

    public BigInteger i() {
        return this.c.q();
    }

    public BigInteger e() {
        return this.d.q();
    }

    public BigInteger k() {
        return this.f.q();
    }

    public BigInteger g() {
        ASN1Integer aSN1Integer = this.q;
        if (aSN1Integer == null) {
            return null;
        }
        return aSN1Integer.q();
    }

    public ValidationParams n() {
        return this.x;
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
        ValidationParams validationParams = this.x;
        if (validationParams != null) {
            v.a(validationParams);
        }
        return new DERSequence(v);
    }
}
