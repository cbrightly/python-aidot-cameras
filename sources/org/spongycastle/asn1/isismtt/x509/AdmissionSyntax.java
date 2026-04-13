package org.spongycastle.asn1.isismtt.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.GeneralName;

public class AdmissionSyntax extends ASN1Object {
    private GeneralName c;
    private ASN1Sequence d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vec = new ASN1EncodableVector();
        GeneralName generalName = this.c;
        if (generalName != null) {
            vec.a(generalName);
        }
        vec.a(this.d);
        return new DERSequence(vec);
    }
}
