package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class Holder extends ASN1Object {
    IssuerSerial c;
    GeneralNames d;
    ObjectDigestInfo f;
    private int q = 1;

    public static Holder g(Object obj) {
        if (obj instanceof Holder) {
            return (Holder) obj;
        }
        if (obj instanceof ASN1TaggedObject) {
            return new Holder(ASN1TaggedObject.o(obj));
        }
        if (obj != null) {
            return new Holder(ASN1Sequence.o(obj));
        }
        return null;
    }

    private Holder(ASN1TaggedObject tagObj) {
        switch (tagObj.r()) {
            case 0:
                this.c = IssuerSerial.f(tagObj, true);
                break;
            case 1:
                this.d = GeneralNames.f(tagObj, true);
                break;
            default:
                throw new IllegalArgumentException("unknown tag in Holder");
        }
        this.q = 0;
    }

    private Holder(ASN1Sequence seq) {
        if (seq.size() <= 3) {
            for (int i = 0; i != seq.size(); i++) {
                ASN1TaggedObject tObj = ASN1TaggedObject.o(seq.r(i));
                switch (tObj.r()) {
                    case 0:
                        this.c = IssuerSerial.f(tObj, false);
                        break;
                    case 1:
                        this.d = GeneralNames.f(tObj, false);
                        break;
                    case 2:
                        this.f = ObjectDigestInfo.h(tObj, false);
                        break;
                    default:
                        throw new IllegalArgumentException("unknown tag in Holder");
                }
            }
            this.q = 1;
            return;
        }
        throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }

    public IssuerSerial e() {
        return this.c;
    }

    public GeneralNames f() {
        return this.d;
    }

    public ObjectDigestInfo h() {
        return this.f;
    }

    public ASN1Primitive toASN1Primitive() {
        if (this.q == 1) {
            ASN1EncodableVector v = new ASN1EncodableVector();
            if (this.c != null) {
                v.a(new DERTaggedObject(false, 0, this.c));
            }
            if (this.d != null) {
                v.a(new DERTaggedObject(false, 1, this.d));
            }
            if (this.f != null) {
                v.a(new DERTaggedObject(false, 2, this.f));
            }
            return new DERSequence(v);
        } else if (this.d != null) {
            return new DERTaggedObject(true, 1, this.d);
        } else {
            return new DERTaggedObject(true, 0, this.c);
        }
    }
}
