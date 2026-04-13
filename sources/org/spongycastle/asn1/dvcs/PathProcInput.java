package org.spongycastle.asn1.dvcs;

import java.util.Arrays;
import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.PolicyInformation;

public class PathProcInput extends ASN1Object {
    private PolicyInformation[] c;
    private boolean d;
    private boolean f;
    private boolean q;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        ASN1EncodableVector pV = new ASN1EncodableVector();
        int i = 0;
        while (true) {
            PolicyInformation[] policyInformationArr = this.c;
            if (i == policyInformationArr.length) {
                break;
            }
            pV.a(policyInformationArr[i]);
            i++;
        }
        v.a(new DERSequence(pV));
        boolean z = this.d;
        if (z) {
            v.a(ASN1Boolean.r(z));
        }
        if (this.f) {
            v.a(new DERTaggedObject(false, 0, ASN1Boolean.r(this.f)));
        }
        if (this.q) {
            v.a(new DERTaggedObject(false, 1, ASN1Boolean.r(this.q)));
        }
        return new DERSequence(v);
    }

    public String toString() {
        return "PathProcInput: {\nacceptablePolicySet: " + Arrays.asList(this.c) + "\ninhibitPolicyMapping: " + this.d + "\nexplicitPolicyReqd: " + this.f + "\ninhibitAnyPolicy: " + this.q + "\n}\n";
    }
}
