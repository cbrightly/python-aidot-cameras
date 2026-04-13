package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;

public class AttCertIssuer extends ASN1Object implements ASN1Choice {
    ASN1Encodable c;
    ASN1Primitive d;

    public static AttCertIssuer e(Object obj) {
        if (obj == null || (obj instanceof AttCertIssuer)) {
            return (AttCertIssuer) obj;
        }
        if (obj instanceof V2Form) {
            return new AttCertIssuer(V2Form.f(obj));
        }
        if (obj instanceof GeneralNames) {
            return new AttCertIssuer((GeneralNames) obj);
        }
        if (obj instanceof ASN1TaggedObject) {
            return new AttCertIssuer(V2Form.g((ASN1TaggedObject) obj, false));
        }
        if (obj instanceof ASN1Sequence) {
            return new AttCertIssuer(GeneralNames.e(obj));
        }
        throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public AttCertIssuer(GeneralNames names) {
        this.c = names;
        this.d = names.toASN1Primitive();
    }

    public AttCertIssuer(V2Form v2Form) {
        this.c = v2Form;
        this.d = new DERTaggedObject(false, 0, this.c);
    }

    public ASN1Encodable f() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.d;
    }
}
