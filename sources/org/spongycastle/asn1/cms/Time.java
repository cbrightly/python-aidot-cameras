package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;

public class Time extends ASN1Object implements ASN1Choice {
    ASN1Primitive c;

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}
