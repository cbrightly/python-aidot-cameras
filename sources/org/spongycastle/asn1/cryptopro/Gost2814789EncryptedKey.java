package org.spongycastle.asn1.cryptopro;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class Gost2814789EncryptedKey extends ASN1Object {
    private final byte[] c;
    private final byte[] d;
    private final byte[] f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(new DEROctetString(this.c));
        if (this.d != null) {
            v.a(new DERTaggedObject(false, 0, new DEROctetString(this.c)));
        }
        v.a(new DEROctetString(this.f));
        return new DERSequence(v);
    }
}
