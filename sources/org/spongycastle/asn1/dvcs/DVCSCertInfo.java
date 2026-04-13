package org.spongycastle.asn1.dvcs;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.cmp.PKIStatusInfo;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.PolicyInformation;

public class DVCSCertInfo extends ASN1Object {
    private ASN1Sequence a1;
    private int c;
    private DVCSRequestInformation d;
    private DigestInfo f;
    private ASN1Set p0;
    private Extensions p1;
    private ASN1Integer q;
    private DVCSTime x;
    private PKIStatusInfo y;
    private PolicyInformation z;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        int i = this.c;
        if (i != 1) {
            v.a(new ASN1Integer((long) i));
        }
        v.a(this.d);
        v.a(this.f);
        v.a(this.q);
        v.a(this.x);
        if (this.y != null) {
            v.a(new DERTaggedObject(false, 0, this.y));
        }
        if (this.z != null) {
            v.a(new DERTaggedObject(false, 1, this.z));
        }
        if (this.p0 != null) {
            v.a(new DERTaggedObject(false, 2, this.p0));
        }
        if (this.a1 != null) {
            v.a(new DERTaggedObject(false, 3, this.a1));
        }
        Extensions extensions = this.p1;
        if (extensions != null) {
            v.a(extensions);
        }
        return new DERSequence(v);
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("DVCSCertInfo {\n");
        if (this.c != 1) {
            s.append("version: " + this.c + "\n");
        }
        s.append("dvReqInfo: " + this.d + "\n");
        s.append("messageImprint: " + this.f + "\n");
        s.append("serialNumber: " + this.q + "\n");
        s.append("responseTime: " + this.x + "\n");
        if (this.y != null) {
            s.append("dvStatus: " + this.y + "\n");
        }
        if (this.z != null) {
            s.append("policy: " + this.z + "\n");
        }
        if (this.p0 != null) {
            s.append("reqSignature: " + this.p0 + "\n");
        }
        if (this.a1 != null) {
            s.append("certs: " + this.a1 + "\n");
        }
        if (this.p1 != null) {
            s.append("extensions: " + this.p1 + "\n");
        }
        s.append("}\n");
        return s.toString();
    }
}
