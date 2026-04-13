package org.spongycastle.asn1.x500;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERSet;

public class RDN extends ASN1Object {
    private ASN1Set c;

    private RDN(ASN1Set values) {
        this.c = values;
    }

    public static RDN f(Object obj) {
        if (obj instanceof RDN) {
            return (RDN) obj;
        }
        if (obj != null) {
            return new RDN(ASN1Set.p(obj));
        }
        return null;
    }

    public RDN(ASN1ObjectIdentifier oid, ASN1Encodable value) {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(oid);
        v.a(value);
        this.c = new DERSet((ASN1Encodable) new DERSequence(v));
    }

    public RDN(AttributeTypeAndValue[] aAndVs) {
        this.c = new DERSet((ASN1Encodable[]) aAndVs);
    }

    public boolean h() {
        return this.c.size() > 1;
    }

    public AttributeTypeAndValue e() {
        if (this.c.size() == 0) {
            return null;
        }
        return AttributeTypeAndValue.e(this.c.s(0));
    }

    public AttributeTypeAndValue[] g() {
        AttributeTypeAndValue[] tmp = new AttributeTypeAndValue[this.c.size()];
        for (int i = 0; i != tmp.length; i++) {
            tmp[i] = AttributeTypeAndValue.e(this.c.s(i));
        }
        return tmp;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}
