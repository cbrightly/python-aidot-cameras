package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class SignerInfo extends ASN1Object {
    private ASN1Integer c;
    private SignerIdentifier d;
    private AlgorithmIdentifier f;
    private ASN1Set q;
    private AlgorithmIdentifier x;
    private ASN1OctetString y;
    private ASN1Set z;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        v.a(this.f);
        if (this.q != null) {
            v.a(new DERTaggedObject(false, 0, this.q));
        }
        v.a(this.x);
        v.a(this.y);
        if (this.z != null) {
            v.a(new DERTaggedObject(false, 1, this.z));
        }
        return new DERSequence(v);
    }
}
