package org.spongycastle.asn1.x509;

import com.meituan.robust.Constants;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class PolicyInformation extends ASN1Object {
    private ASN1ObjectIdentifier c;
    private ASN1Sequence d;

    private PolicyInformation(ASN1Sequence seq) {
        if (seq.size() < 1 || seq.size() > 2) {
            throw new IllegalArgumentException("Bad sequence size: " + seq.size());
        }
        this.c = ASN1ObjectIdentifier.t(seq.r(0));
        if (seq.size() > 1) {
            this.d = ASN1Sequence.o(seq.r(1));
        }
    }

    public static PolicyInformation e(Object obj) {
        if (obj == null || (obj instanceof PolicyInformation)) {
            return (PolicyInformation) obj;
        }
        return new PolicyInformation(ASN1Sequence.o(obj));
    }

    public ASN1ObjectIdentifier f() {
        return this.c;
    }

    public ASN1Sequence g() {
        return this.d;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.c);
        ASN1Sequence aSN1Sequence = this.d;
        if (aSN1Sequence != null) {
            v.a(aSN1Sequence);
        }
        return new DERSequence(v);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Policy information: ");
        sb.append(this.c);
        if (this.d != null) {
            StringBuffer p = new StringBuffer();
            for (int i = 0; i < this.d.size(); i++) {
                if (p.length() != 0) {
                    p.append(", ");
                }
                p.append(PolicyQualifierInfo.e(this.d.r(i)));
            }
            sb.append(Constants.ARRAY_TYPE);
            sb.append(p);
            sb.append("]");
        }
        return sb.toString();
    }
}
