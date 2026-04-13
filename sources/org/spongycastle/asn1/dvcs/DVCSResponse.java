package org.spongycastle.asn1.dvcs;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERTaggedObject;

public class DVCSResponse extends ASN1Object implements ASN1Choice {
    private DVCSCertInfo c;
    private DVCSErrorNotice d;

    public ASN1Primitive toASN1Primitive() {
        DVCSCertInfo dVCSCertInfo = this.c;
        if (dVCSCertInfo != null) {
            return dVCSCertInfo.toASN1Primitive();
        }
        return new DERTaggedObject(false, 0, this.d);
    }

    public String toString() {
        if (this.c != null) {
            return "DVCSResponse {\ndvCertInfo: " + this.c.toString() + "}\n";
        }
        return "DVCSResponse {\ndvErrorNote: " + this.d.toString() + "}\n";
    }
}
