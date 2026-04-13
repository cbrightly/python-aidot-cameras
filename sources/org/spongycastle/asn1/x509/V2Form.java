package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class V2Form extends ASN1Object {
    GeneralNames c;
    IssuerSerial d;
    ObjectDigestInfo f;

    public static V2Form g(ASN1TaggedObject obj, boolean explicit) {
        return f(ASN1Sequence.p(obj, explicit));
    }

    public static V2Form f(Object obj) {
        if (obj instanceof V2Form) {
            return (V2Form) obj;
        }
        if (obj != null) {
            return new V2Form(ASN1Sequence.o(obj));
        }
        return null;
    }

    public V2Form(ASN1Sequence seq) {
        if (seq.size() <= 3) {
            int index = 0;
            if (!(seq.r(0) instanceof ASN1TaggedObject)) {
                index = 0 + 1;
                this.c = GeneralNames.e(seq.r(0));
            }
            for (int i = index; i != seq.size(); i++) {
                ASN1TaggedObject o = ASN1TaggedObject.o(seq.r(i));
                if (o.r() == 0) {
                    this.d = IssuerSerial.f(o, false);
                } else if (o.r() == 1) {
                    this.f = ObjectDigestInfo.h(o, false);
                } else {
                    throw new IllegalArgumentException("Bad tag number: " + o.r());
                }
            }
            return;
        }
        throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }

    public GeneralNames h() {
        return this.c;
    }

    public IssuerSerial e() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        GeneralNames generalNames = this.c;
        if (generalNames != null) {
            v.a(generalNames);
        }
        if (this.d != null) {
            v.a(new DERTaggedObject(false, 0, this.d));
        }
        if (this.f != null) {
            v.a(new DERTaggedObject(false, 1, this.f));
        }
        return new DERSequence(v);
    }
}
