package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DLSequence;
import org.spongycastle.asn1.DLTaggedObject;

public class SafeBag extends ASN1Object {
    private ASN1ObjectIdentifier c;
    private ASN1Encodable d;
    private ASN1Set f;

    public SafeBag(ASN1ObjectIdentifier oid, ASN1Encodable obj, ASN1Set bagAttributes) {
        this.c = oid;
        this.d = obj;
        this.f = bagAttributes;
    }

    public static SafeBag h(Object obj) {
        if (obj instanceof SafeBag) {
            return (SafeBag) obj;
        }
        if (obj != null) {
            return new SafeBag(ASN1Sequence.o(obj));
        }
        return null;
    }

    private SafeBag(ASN1Sequence seq) {
        this.c = (ASN1ObjectIdentifier) seq.r(0);
        this.d = ((ASN1TaggedObject) seq.r(1)).q();
        if (seq.size() == 3) {
            this.f = (ASN1Set) seq.r(2);
        }
    }

    public ASN1ObjectIdentifier f() {
        return this.c;
    }

    public ASN1Encodable g() {
        return this.d;
    }

    public ASN1Set e() {
        return this.f;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(new DLTaggedObject(true, 0, this.d));
        ASN1Set aSN1Set = this.f;
        if (aSN1Set != null) {
            v.a(aSN1Set);
        }
        return new DLSequence(v);
    }
}
