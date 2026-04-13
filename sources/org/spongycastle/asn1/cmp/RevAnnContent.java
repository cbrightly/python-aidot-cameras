package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.crmf.CertId;
import org.spongycastle.asn1.x509.Extensions;

public class RevAnnContent extends ASN1Object {
    private PKIStatus c;
    private CertId d;
    private ASN1GeneralizedTime f;
    private ASN1GeneralizedTime q;
    private Extensions x;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        v.a(this.f);
        v.a(this.q);
        Extensions extensions = this.x;
        if (extensions != null) {
            v.a(extensions);
        }
        return new DERSequence(v);
    }
}
