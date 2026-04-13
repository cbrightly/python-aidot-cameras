package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.BERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedContentInfo extends ASN1Object {
    private ASN1ObjectIdentifier c;
    private AlgorithmIdentifier d;
    private ASN1OctetString f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(this.d);
        ASN1OctetString aSN1OctetString = this.f;
        if (aSN1OctetString != null) {
            v.a(new BERTaggedObject(false, 0, aSN1OctetString));
        }
        return new BERSequence(v);
    }
}
