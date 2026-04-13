package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DERIA5String;

public class TimeStampedData extends ASN1Object {
    private ASN1Integer c;
    private DERIA5String d;
    private MetaData f;
    private ASN1OctetString q;
    private Evidence x;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        DERIA5String dERIA5String = this.d;
        if (dERIA5String != null) {
            v.a(dERIA5String);
        }
        MetaData metaData = this.f;
        if (metaData != null) {
            v.a(metaData);
        }
        ASN1OctetString aSN1OctetString = this.q;
        if (aSN1OctetString != null) {
            v.a(aSN1OctetString);
        }
        v.a(this.x);
        return new BERSequence(v);
    }
}
