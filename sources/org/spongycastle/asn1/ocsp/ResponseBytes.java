package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class ResponseBytes extends ASN1Object {
    ASN1ObjectIdentifier c;
    ASN1OctetString d;

    public ResponseBytes(ASN1Sequence seq) {
        this.c = (ASN1ObjectIdentifier) seq.r(0);
        this.d = (ASN1OctetString) seq.r(1);
    }

    public static ResponseBytes f(ASN1TaggedObject obj, boolean explicit) {
        return e(ASN1Sequence.p(obj, explicit));
    }

    public static ResponseBytes e(Object obj) {
        if (obj instanceof ResponseBytes) {
            return (ResponseBytes) obj;
        }
        if (obj != null) {
            return new ResponseBytes(ASN1Sequence.o(obj));
        }
        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        return new DERSequence(v);
    }
}
