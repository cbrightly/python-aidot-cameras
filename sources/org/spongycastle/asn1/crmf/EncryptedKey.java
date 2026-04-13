package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.cms.EnvelopedData;

public class EncryptedKey extends ASN1Object implements ASN1Choice {
    private EnvelopedData c;
    private EncryptedValue d;

    public ASN1Primitive toASN1Primitive() {
        EncryptedValue encryptedValue = this.d;
        if (encryptedValue != null) {
            return encryptedValue.toASN1Primitive();
        }
        return new DERTaggedObject(false, 0, this.c);
    }
}
