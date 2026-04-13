package org.spongycastle.asn1.cryptopro;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;

public class GostR3410TransportParameters extends ASN1Object {
    private final ASN1ObjectIdentifier c;
    private final SubjectPublicKeyInfo d;
    private final byte[] f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        if (this.d != null) {
            v.a(new DERTaggedObject(false, 0, this.d));
        }
        v.a(new DEROctetString(this.f));
        return new DERSequence(v);
    }
}
