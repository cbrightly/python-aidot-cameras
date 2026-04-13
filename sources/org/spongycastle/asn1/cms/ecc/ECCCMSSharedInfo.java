package org.spongycastle.asn1.cms.ecc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class ECCCMSSharedInfo extends ASN1Object {
    private final AlgorithmIdentifier c;
    private final byte[] d;
    private final byte[] f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        if (this.d != null) {
            v.a(new DERTaggedObject(true, 0, new DEROctetString(this.d)));
        }
        v.a(new DERTaggedObject(true, 2, new DEROctetString(this.f)));
        return new DERSequence(v);
    }
}
