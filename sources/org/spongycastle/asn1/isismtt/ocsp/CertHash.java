package org.spongycastle.asn1.isismtt.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class CertHash extends ASN1Object {
    private AlgorithmIdentifier c;
    private byte[] d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vec = new ASN1EncodableVector();
        vec.a(this.c);
        vec.a(new DEROctetString(this.d));
        return new DERSequence(vec);
    }
}
