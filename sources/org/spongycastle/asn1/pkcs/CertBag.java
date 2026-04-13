package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class CertBag extends ASN1Object {
    private ASN1ObjectIdentifier c;
    private ASN1Encodable d;

    private CertBag(ASN1Sequence seq) {
        this.c = (ASN1ObjectIdentifier) seq.r(0);
        this.d = ((DERTaggedObject) seq.r(1)).q();
    }

    public static CertBag g(Object o) {
        if (o instanceof CertBag) {
            return (CertBag) o;
        }
        if (o != null) {
            return new CertBag(ASN1Sequence.o(o));
        }
        return null;
    }

    public CertBag(ASN1ObjectIdentifier certId, ASN1Encodable certValue) {
        this.c = certId;
        this.d = certValue;
    }

    public ASN1ObjectIdentifier e() {
        return this.c;
    }

    public ASN1Encodable f() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(new DERTaggedObject(0, this.d));
        return new DERSequence(v);
    }
}
