package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.util.Strings;

public class DistributionPointName extends ASN1Object implements ASN1Choice {
    ASN1Encodable c;
    int d;

    public static DistributionPointName g(ASN1TaggedObject obj, boolean explicit) {
        return f(ASN1TaggedObject.p(obj, true));
    }

    public static DistributionPointName f(Object obj) {
        if (obj == null || (obj instanceof DistributionPointName)) {
            return (DistributionPointName) obj;
        }
        if (obj instanceof ASN1TaggedObject) {
            return new DistributionPointName((ASN1TaggedObject) obj);
        }
        throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public DistributionPointName(int type, ASN1Encodable name) {
        this.d = type;
        this.c = name;
    }

    public int i() {
        return this.d;
    }

    public ASN1Encodable h() {
        return this.c;
    }

    public DistributionPointName(ASN1TaggedObject obj) {
        int r = obj.r();
        this.d = r;
        if (r == 0) {
            this.c = GeneralNames.f(obj, false);
        } else {
            this.c = ASN1Set.q(obj, false);
        }
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(false, this.d, this.c);
    }

    public String toString() {
        String sep = Strings.d();
        StringBuffer buf = new StringBuffer();
        buf.append("DistributionPointName: [");
        buf.append(sep);
        if (this.d == 0) {
            e(buf, sep, "fullName", this.c.toString());
        } else {
            e(buf, sep, "nameRelativeToCRLIssuer", this.c.toString());
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
