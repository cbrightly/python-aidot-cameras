package org.spongycastle.asn1.cryptopro;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;

public class Gost2814789KeyWrapParameters extends ASN1Object {
    private final ASN1ObjectIdentifier c;
    private final byte[] d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        byte[] bArr = this.d;
        if (bArr != null) {
            v.a(new DEROctetString(bArr));
        }
        return new DERSequence(v);
    }
}
