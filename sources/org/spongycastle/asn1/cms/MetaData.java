package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERUTF8String;

public class MetaData extends ASN1Object {
    private ASN1Boolean c;
    private DERUTF8String d;
    private DERIA5String f;
    private Attributes q;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        DERUTF8String dERUTF8String = this.d;
        if (dERUTF8String != null) {
            v.a(dERUTF8String);
        }
        DERIA5String dERIA5String = this.f;
        if (dERIA5String != null) {
            v.a(dERIA5String);
        }
        Attributes attributes = this.q;
        if (attributes != null) {
            v.a(attributes);
        }
        return new DERSequence(v);
    }
}
