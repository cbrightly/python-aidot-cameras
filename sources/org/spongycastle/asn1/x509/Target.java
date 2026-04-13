package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;

public class Target extends ASN1Object implements ASN1Choice {
    private GeneralName c;
    private GeneralName d;

    public static Target e(Object obj) {
        if (obj == null || (obj instanceof Target)) {
            return (Target) obj;
        }
        if (obj instanceof ASN1TaggedObject) {
            return new Target((ASN1TaggedObject) obj);
        }
        throw new IllegalArgumentException("unknown object in factory: " + obj.getClass());
    }

    private Target(ASN1TaggedObject tagObj) {
        switch (tagObj.r()) {
            case 0:
                this.c = GeneralName.g(tagObj, true);
                return;
            case 1:
                this.d = GeneralName.g(tagObj, true);
                return;
            default:
                throw new IllegalArgumentException("unknown tag: " + tagObj.r());
        }
    }

    public GeneralName f() {
        return this.d;
    }

    public GeneralName g() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        if (this.c != null) {
            return new DERTaggedObject(true, 0, this.c);
        }
        return new DERTaggedObject(true, 1, this.d);
    }
}
