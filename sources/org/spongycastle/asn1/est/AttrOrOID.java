package org.spongycastle.asn1.est;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.pkcs.Attribute;

public class AttrOrOID extends ASN1Object implements ASN1Choice {
    private final ASN1ObjectIdentifier c;
    private final Attribute d;

    public ASN1Primitive toASN1Primitive() {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = this.c;
        if (aSN1ObjectIdentifier != null) {
            return aSN1ObjectIdentifier;
        }
        return this.d.toASN1Primitive();
    }
}
