package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERTaggedObject;

public class KeyAgreeRecipientIdentifier extends ASN1Object implements ASN1Choice {
    private IssuerAndSerialNumber c;
    private RecipientKeyIdentifier d;

    public ASN1Primitive toASN1Primitive() {
        IssuerAndSerialNumber issuerAndSerialNumber = this.c;
        if (issuerAndSerialNumber != null) {
            return issuerAndSerialNumber.toASN1Primitive();
        }
        return new DERTaggedObject(false, 0, this.d);
    }
}
