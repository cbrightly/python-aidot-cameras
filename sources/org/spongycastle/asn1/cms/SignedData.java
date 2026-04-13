package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.BERTaggedObject;
import org.spongycastle.asn1.DERTaggedObject;

public class SignedData extends ASN1Object {
    private static final ASN1Integer c = new ASN1Integer(1);
    private static final ASN1Integer d = new ASN1Integer(3);
    private static final ASN1Integer f = new ASN1Integer(4);
    private static final ASN1Integer q = new ASN1Integer(5);
    private ASN1Set a1;
    private boolean a2;
    private ASN1Set p0;
    private ASN1Set p1;
    private boolean p2;
    private ASN1Integer x;
    private ASN1Set y;
    private ContentInfo z;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.x);
        v.a(this.y);
        v.a(this.z);
        ASN1Set aSN1Set = this.p0;
        if (aSN1Set != null) {
            if (this.a2) {
                v.a(new BERTaggedObject(false, 0, aSN1Set));
            } else {
                v.a(new DERTaggedObject(false, 0, this.p0));
            }
        }
        ASN1Set aSN1Set2 = this.a1;
        if (aSN1Set2 != null) {
            if (this.p2) {
                v.a(new BERTaggedObject(false, 1, aSN1Set2));
            } else {
                v.a(new DERTaggedObject(false, 1, this.a1));
            }
        }
        v.a(this.p1);
        return new BERSequence(v);
    }
}
