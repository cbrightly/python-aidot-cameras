package org.spongycastle.asn1.dvcs;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extension;

public class CertEtcToken extends ASN1Object implements ASN1Choice {
    private static final boolean[] c = {false, true, false, true, false, true, false, false, true};
    private int d;
    private ASN1Encodable f;
    private Extension q;

    public ASN1Primitive toASN1Primitive() {
        Extension extension = this.q;
        if (extension != null) {
            return extension.toASN1Primitive();
        }
        boolean[] zArr = c;
        int i = this.d;
        return new DERTaggedObject(zArr[i], i, this.f);
    }

    public String toString() {
        return "CertEtcToken {\n" + this.f + "}\n";
    }
}
