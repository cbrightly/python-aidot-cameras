package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedValue extends ASN1Object {
    private AlgorithmIdentifier c;
    private AlgorithmIdentifier d;
    private DERBitString f;
    private AlgorithmIdentifier q;
    private ASN1OctetString x;
    private DERBitString y;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        e(v, 0, this.c);
        e(v, 1, this.d);
        e(v, 2, this.f);
        e(v, 3, this.q);
        e(v, 4, this.x);
        v.a(this.y);
        return new DERSequence(v);
    }

    private void e(ASN1EncodableVector v, int tagNo, ASN1Encodable obj) {
        if (obj != null) {
            v.a(new DERTaggedObject(false, tagNo, obj));
        }
    }
}
