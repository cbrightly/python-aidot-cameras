package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class AuthenticatedData extends ASN1Object {
    private ASN1Set a1;
    private ASN1Integer c;
    private OriginatorInfo d;
    private ASN1Set f;
    private ASN1OctetString p0;
    private AlgorithmIdentifier q;
    private AlgorithmIdentifier x;
    private ContentInfo y;
    private ASN1Set z;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        if (this.d != null) {
            v.a(new DERTaggedObject(false, 0, this.d));
        }
        v.a(this.f);
        v.a(this.q);
        if (this.x != null) {
            v.a(new DERTaggedObject(false, 1, this.x));
        }
        v.a(this.y);
        if (this.z != null) {
            v.a(new DERTaggedObject(false, 2, this.z));
        }
        v.a(this.p0);
        if (this.a1 != null) {
            v.a(new DERTaggedObject(false, 3, this.a1));
        }
        return new BERSequence(v);
    }
}
