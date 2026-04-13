package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERSequence;

public class Attribute extends ASN1Object {
    private ASN1ObjectIdentifier c;
    private ASN1Set d;

    public static Attribute f(Object o) {
        if (o instanceof Attribute) {
            return (Attribute) o;
        }
        if (o != null) {
            return new Attribute(ASN1Sequence.o(o));
        }
        return null;
    }

    private Attribute(ASN1Sequence seq) {
        if (seq.size() == 2) {
            this.c = ASN1ObjectIdentifier.t(seq.r(0));
            this.d = ASN1Set.p(seq.r(1));
            return;
        }
        throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }

    public ASN1ObjectIdentifier e() {
        return new ASN1ObjectIdentifier(this.c.s());
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        return new DERSequence(v);
    }
}
