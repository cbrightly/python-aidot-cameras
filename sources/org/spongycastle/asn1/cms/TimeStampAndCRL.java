package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.CertificateList;

public class TimeStampAndCRL extends ASN1Object {
    private ContentInfo c;
    private CertificateList d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        CertificateList certificateList = this.d;
        if (certificateList != null) {
            v.a(certificateList);
        }
        return new DERSequence(v);
    }
}
