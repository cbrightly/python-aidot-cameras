package org.spongycastle.asn1.isismtt.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.GeneralName;

public class Admissions extends ASN1Object {
    private GeneralName c;
    private NamingAuthority d;
    private ASN1Sequence f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vec = new ASN1EncodableVector();
        if (this.c != null) {
            vec.a(new DERTaggedObject(true, 0, this.c));
        }
        if (this.d != null) {
            vec.a(new DERTaggedObject(true, 1, this.d));
        }
        vec.a(this.f);
        return new DERSequence(vec);
    }
}
