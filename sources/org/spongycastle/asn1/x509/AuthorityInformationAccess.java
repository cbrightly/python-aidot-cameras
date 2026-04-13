package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class AuthorityInformationAccess extends ASN1Object {
    private AccessDescription[] c;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vec = new ASN1EncodableVector();
        int i = 0;
        while (true) {
            AccessDescription[] accessDescriptionArr = this.c;
            if (i == accessDescriptionArr.length) {
                return new DERSequence(vec);
            }
            vec.a(accessDescriptionArr[i]);
            i++;
        }
    }

    public String toString() {
        return "AuthorityInformationAccess: Oid(" + this.c[0].e().s() + ")";
    }
}
