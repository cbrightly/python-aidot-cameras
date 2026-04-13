package org.spongycastle.asn1.tsp;

import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralName;

public class TSTInfo extends ASN1Object {
    private GeneralName a1;
    private ASN1Integer c;
    private ASN1ObjectIdentifier d;
    private MessageImprint f;
    private ASN1Integer p0;
    private Extensions p1;
    private ASN1Integer q;
    private ASN1GeneralizedTime x;
    private Accuracy y;
    private ASN1Boolean z;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector seq = new ASN1EncodableVector();
        seq.a(this.c);
        seq.a(this.d);
        seq.a(this.f);
        seq.a(this.q);
        seq.a(this.x);
        Accuracy accuracy = this.y;
        if (accuracy != null) {
            seq.a(accuracy);
        }
        ASN1Boolean aSN1Boolean = this.z;
        if (aSN1Boolean != null && aSN1Boolean.s()) {
            seq.a(this.z);
        }
        ASN1Integer aSN1Integer = this.p0;
        if (aSN1Integer != null) {
            seq.a(aSN1Integer);
        }
        if (this.a1 != null) {
            seq.a(new DERTaggedObject(true, 0, this.a1));
        }
        if (this.p1 != null) {
            seq.a(new DERTaggedObject(false, 1, this.p1));
        }
        return new DERSequence(seq);
    }
}
