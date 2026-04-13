package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.GeneralName;

public class PKIHeader extends ASN1Object {
    public static final GeneralName c = new GeneralName(X500Name.e(new DERSequence()));
    private ASN1OctetString a1;
    private ASN1OctetString a2;
    private ASN1Integer d;
    private GeneralName f;
    private ASN1OctetString p0;
    private ASN1OctetString p1;
    private PKIFreeText p2;
    private ASN1Sequence p3;
    private GeneralName q;
    private ASN1GeneralizedTime x;
    private AlgorithmIdentifier y;
    private ASN1OctetString z;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.d);
        v.a(this.f);
        v.a(this.q);
        e(v, 0, this.x);
        e(v, 1, this.y);
        e(v, 2, this.z);
        e(v, 3, this.p0);
        e(v, 4, this.a1);
        e(v, 5, this.p1);
        e(v, 6, this.a2);
        e(v, 7, this.p2);
        e(v, 8, this.p3);
        return new DERSequence(v);
    }

    private void e(ASN1EncodableVector v, int tagNo, ASN1Encodable obj) {
        if (obj != null) {
            v.a(new DERTaggedObject(true, tagNo, obj));
        }
    }
}
