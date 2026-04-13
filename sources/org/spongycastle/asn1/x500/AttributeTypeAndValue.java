package org.spongycastle.asn1.x500;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class AttributeTypeAndValue extends ASN1Object {
    private ASN1ObjectIdentifier c;
    private ASN1Encodable d;

    private AttributeTypeAndValue(ASN1Sequence seq) {
        this.c = (ASN1ObjectIdentifier) seq.r(0);
        this.d = seq.r(1);
    }

    public static AttributeTypeAndValue e(Object o) {
        if (o instanceof AttributeTypeAndValue) {
            return (AttributeTypeAndValue) o;
        }
        if (o != null) {
            return new AttributeTypeAndValue(ASN1Sequence.o(o));
        }
        throw new IllegalArgumentException("null value in getInstance()");
    }

    public AttributeTypeAndValue(ASN1ObjectIdentifier type, ASN1Encodable value) {
        this.c = type;
        this.d = value;
    }

    public ASN1ObjectIdentifier f() {
        return this.c;
    }

    public ASN1Encodable g() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        return new DERSequence(v);
    }
}
