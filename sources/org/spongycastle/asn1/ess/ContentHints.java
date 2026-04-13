package org.spongycastle.asn1.ess;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERUTF8String;

public class ContentHints extends ASN1Object {
    private DERUTF8String c;
    private ASN1ObjectIdentifier d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        DERUTF8String dERUTF8String = this.c;
        if (dERUTF8String != null) {
            v.a(dERUTF8String);
        }
        v.a(this.d);
        return new DERSequence(v);
    }
}
