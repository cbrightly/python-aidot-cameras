package org.spongycastle.pqc.asn1;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;

public class GMSSPublicKey extends ASN1Object {
    private ASN1Integer c = new ASN1Integer(0);
    private byte[] d;

    public GMSSPublicKey(byte[] publicKeyBytes) {
        this.d = publicKeyBytes;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        v.a(new DEROctetString(this.d));
        return new DERSequence(v);
    }
}
