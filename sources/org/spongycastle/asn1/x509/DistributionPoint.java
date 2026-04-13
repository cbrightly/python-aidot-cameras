package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.util.Strings;

public class DistributionPoint extends ASN1Object {
    DistributionPointName c;
    ReasonFlags d;
    GeneralNames f;

    public static DistributionPoint h(Object obj) {
        if (obj == null || (obj instanceof DistributionPoint)) {
            return (DistributionPoint) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new DistributionPoint((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException("Invalid DistributionPoint: " + obj.getClass().getName());
    }

    public DistributionPoint(ASN1Sequence seq) {
        for (int i = 0; i != seq.size(); i++) {
            ASN1TaggedObject t = ASN1TaggedObject.o(seq.r(i));
            switch (t.r()) {
                case 0:
                    this.c = DistributionPointName.g(t, true);
                    break;
                case 1:
                    this.d = new ReasonFlags(DERBitString.y(t, false));
                    break;
                case 2:
                    this.f = GeneralNames.f(t, false);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown tag encountered in structure: " + t.r());
            }
        }
    }

    public DistributionPoint(DistributionPointName distributionPoint, ReasonFlags reasons, GeneralNames cRLIssuer) {
        this.c = distributionPoint;
        this.d = reasons;
        this.f = cRLIssuer;
    }

    public DistributionPointName g() {
        return this.c;
    }

    public ReasonFlags i() {
        return this.d;
    }

    public GeneralNames f() {
        return this.f;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (this.c != null) {
            v.a(new DERTaggedObject(0, this.c));
        }
        if (this.d != null) {
            v.a(new DERTaggedObject(false, 1, this.d));
        }
        if (this.f != null) {
            v.a(new DERTaggedObject(false, 2, this.f));
        }
        return new DERSequence(v);
    }

    public String toString() {
        String sep = Strings.d();
        StringBuffer buf = new StringBuffer();
        buf.append("DistributionPoint: [");
        buf.append(sep);
        DistributionPointName distributionPointName = this.c;
        if (distributionPointName != null) {
            e(buf, sep, "distributionPoint", distributionPointName.toString());
        }
        ReasonFlags reasonFlags = this.d;
        if (reasonFlags != null) {
            e(buf, sep, "reasons", reasonFlags.toString());
        }
        GeneralNames generalNames = this.f;
        if (generalNames != null) {
            e(buf, sep, "cRLIssuer", generalNames.toString());
        }
        buf.append("]");
        buf.append(sep);
        return buf.toString();
    }

    private void e(StringBuffer buf, String sep, String name, String value) {
        buf.append("    ");
        buf.append(name);
        buf.append(":");
        buf.append(sep);
        buf.append("    ");
        buf.append("    ");
        buf.append(value);
        buf.append(sep);
    }
}
