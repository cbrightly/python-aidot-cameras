package org.spongycastle.asn1.dvcs;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class TargetEtcChain extends ASN1Object {
    private CertEtcToken c;
    private ASN1Sequence d;
    private PathProcInput f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        ASN1Sequence aSN1Sequence = this.d;
        if (aSN1Sequence != null) {
            v.a(aSN1Sequence);
        }
        if (this.f != null) {
            v.a(new DERTaggedObject(false, 0, this.f));
        }
        return new DERSequence(v);
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("TargetEtcChain {\n");
        s.append("target: " + this.c + "\n");
        if (this.d != null) {
            s.append("chain: " + this.d + "\n");
        }
        if (this.f != null) {
            s.append("pathProcInput: " + this.f + "\n");
        }
        s.append("}\n");
        return s.toString();
    }
}
