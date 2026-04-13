package org.spongycastle.asn1.ess;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.IssuerSerial;

public class OtherCertID extends ASN1Object {
    private ASN1Encodable c;
    private IssuerSerial d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        IssuerSerial issuerSerial = this.d;
        if (issuerSerial != null) {
            v.a(issuerSerial);
        }
        return new DERSequence(v);
    }
}
