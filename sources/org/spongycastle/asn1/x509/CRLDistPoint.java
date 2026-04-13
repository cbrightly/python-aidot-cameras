package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.util.Strings;

public class CRLDistPoint extends ASN1Object {
    ASN1Sequence c = null;

    public static CRLDistPoint f(Object obj) {
        if (obj instanceof CRLDistPoint) {
            return (CRLDistPoint) obj;
        }
        if (obj != null) {
            return new CRLDistPoint(ASN1Sequence.o(obj));
        }
        return null;
    }

    private CRLDistPoint(ASN1Sequence seq) {
        this.c = seq;
    }

    public DistributionPoint[] e() {
        DistributionPoint[] dp = new DistributionPoint[this.c.size()];
        for (int i = 0; i != this.c.size(); i++) {
            dp[i] = DistributionPoint.h(this.c.r(i));
        }
        return dp;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        String sep = Strings.d();
        buf.append("CRLDistPoint:");
        buf.append(sep);
        DistributionPoint[] dp = e();
        for (int i = 0; i != dp.length; i++) {
            buf.append("    ");
            buf.append(dp[i]);
            buf.append(sep);
        }
        return buf.toString();
    }
}
