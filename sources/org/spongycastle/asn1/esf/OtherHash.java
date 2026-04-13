package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;

public class OtherHash extends ASN1Object implements ASN1Choice {
    private ASN1OctetString c;
    private OtherHashAlgAndValue d;

    public ASN1Primitive toASN1Primitive() {
        OtherHashAlgAndValue otherHashAlgAndValue = this.d;
        if (otherHashAlgAndValue == null) {
            return this.c;
        }
        return otherHashAlgAndValue.toASN1Primitive();
    }
}
