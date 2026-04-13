package org.spongycastle.asn1.x509;

import com.tencent.bugly.Bugly;
import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.util.Strings;

public class IssuingDistributionPoint extends ASN1Object {
    private DistributionPointName c;
    private boolean d;
    private boolean f;
    private ReasonFlags q;
    private boolean x;
    private boolean y;
    private ASN1Sequence z;

    public static IssuingDistributionPoint h(Object obj) {
        if (obj instanceof IssuingDistributionPoint) {
            return (IssuingDistributionPoint) obj;
        }
        if (obj != null) {
            return new IssuingDistributionPoint(ASN1Sequence.o(obj));
        }
        return null;
    }

    private IssuingDistributionPoint(ASN1Sequence seq) {
        this.z = seq;
        for (int i = 0; i != seq.size(); i++) {
            ASN1TaggedObject o = ASN1TaggedObject.o(seq.r(i));
            switch (o.r()) {
                case 0:
                    this.c = DistributionPointName.g(o, true);
                    break;
                case 1:
                    this.d = ASN1Boolean.q(o, false).s();
                    break;
                case 2:
                    this.f = ASN1Boolean.q(o, false).s();
                    break;
                case 3:
                    this.q = new ReasonFlags(DERBitString.y(o, false));
                    break;
                case 4:
                    this.x = ASN1Boolean.q(o, false).s();
                    break;
                case 5:
                    this.y = ASN1Boolean.q(o, false).s();
                    break;
                default:
                    throw new IllegalArgumentException("unknown tag in IssuingDistributionPoint");
            }
        }
    }

    public boolean p() {
        return this.d;
    }

    public boolean o() {
        return this.f;
    }

    public boolean k() {
        return this.x;
    }

    public boolean n() {
        return this.y;
    }

    public DistributionPointName g() {
        return this.c;
    }

    public ReasonFlags i() {
        return this.q;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.z;
    }

    public String toString() {
        String sep = Strings.d();
        StringBuffer buf = new StringBuffer();
        buf.append("IssuingDistributionPoint: [");
        buf.append(sep);
        DistributionPointName distributionPointName = this.c;
        if (distributionPointName != null) {
            e(buf, sep, "distributionPoint", distributionPointName.toString());
        }
        boolean z2 = this.d;
        if (z2) {
            e(buf, sep, "onlyContainsUserCerts", f(z2));
        }
        boolean z3 = this.f;
        if (z3) {
            e(buf, sep, "onlyContainsCACerts", f(z3));
        }
        ReasonFlags reasonFlags = this.q;
        if (reasonFlags != null) {
            e(buf, sep, "onlySomeReasons", reasonFlags.toString());
        }
        boolean z4 = this.y;
        if (z4) {
            e(buf, sep, "onlyContainsAttributeCerts", f(z4));
        }
        boolean z5 = this.x;
        if (z5) {
            e(buf, sep, "indirectCRL", f(z5));
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

    private String f(boolean value) {
        return value ? "true" : Bugly.SDK_IS_DEV;
    }
}
