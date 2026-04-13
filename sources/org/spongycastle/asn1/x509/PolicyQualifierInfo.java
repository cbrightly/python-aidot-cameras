package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class PolicyQualifierInfo extends ASN1Object {
    private ASN1ObjectIdentifier c;
    private ASN1Encodable d;

    public PolicyQualifierInfo(ASN1Sequence as) {
        if (as.size() == 2) {
            this.c = ASN1ObjectIdentifier.t(as.r(0));
            this.d = as.r(1);
            return;
        }
        throw new IllegalArgumentException("Bad sequence size: " + as.size());
    }

    public static PolicyQualifierInfo e(Object obj) {
        if (obj instanceof PolicyQualifierInfo) {
            return (PolicyQualifierInfo) obj;
        }
        if (obj != null) {
            return new PolicyQualifierInfo(ASN1Sequence.o(obj));
        }
        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector dev = new ASN1EncodableVector();
        dev.a(this.c);
        dev.a(this.d);
        return new DERSequence(dev);
    }
}
