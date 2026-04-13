package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class KeyAgreeRecipientInfo extends ASN1Object {
    private ASN1Integer c;
    private OriginatorIdentifierOrKey d;
    private ASN1OctetString f;
    private AlgorithmIdentifier q;
    private ASN1Sequence x;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(new DERTaggedObject(true, 0, this.d));
        if (this.f != null) {
            v.a(new DERTaggedObject(true, 1, this.f));
        }
        v.a(this.q);
        v.a(this.x);
        return new DERSequence(v);
    }
}
