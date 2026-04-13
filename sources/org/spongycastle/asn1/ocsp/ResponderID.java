package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERTaggedObject;

public class ResponderID extends ASN1Object implements ASN1Choice {
    private ASN1Encodable c;

    public ASN1Primitive toASN1Primitive() {
        if (this.c instanceof ASN1OctetString) {
            return new DERTaggedObject(true, 2, this.c);
        }
        return new DERTaggedObject(true, 1, this.c);
    }
}
