package org.spongycastle.asn1.isismtt.x509;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;

public class DeclarationOfMajority extends ASN1Object implements ASN1Choice {
    private ASN1TaggedObject c;

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}
