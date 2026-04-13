package org.spongycastle.asn1.dvcs;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.DigestInfo;

public class Data extends ASN1Object implements ASN1Choice {
    private ASN1OctetString c;
    private DigestInfo d;
    private ASN1Sequence f;

    public ASN1Primitive toASN1Primitive() {
        ASN1OctetString aSN1OctetString = this.c;
        if (aSN1OctetString != null) {
            return aSN1OctetString.toASN1Primitive();
        }
        DigestInfo digestInfo = this.d;
        if (digestInfo != null) {
            return digestInfo.toASN1Primitive();
        }
        return new DERTaggedObject(false, 0, this.f);
    }

    public String toString() {
        if (this.c != null) {
            return "Data {\n" + this.c + "}\n";
        } else if (this.d != null) {
            return "Data {\n" + this.d + "}\n";
        } else {
            return "Data {\n" + this.f + "}\n";
        }
    }
}
