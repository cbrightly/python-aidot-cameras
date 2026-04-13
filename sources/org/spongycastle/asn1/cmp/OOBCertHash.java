package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.crmf.CertId;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class OOBCertHash extends ASN1Object {
    private AlgorithmIdentifier c;
    private CertId d;
    private DERBitString f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        e(v, 0, this.c);
        e(v, 1, this.d);
        v.a(this.f);
        return new DERSequence(v);
    }

    private void e(ASN1EncodableVector v, int tagNo, ASN1Encodable obj) {
        if (obj != null) {
            v.a(new DERTaggedObject(true, tagNo, obj));
        }
    }
}
