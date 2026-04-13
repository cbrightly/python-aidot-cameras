package org.spongycastle.asn1.dvcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Enumerated;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;

public class ServiceType extends ASN1Object {
    public static final ServiceType c = new ServiceType(1);
    public static final ServiceType d = new ServiceType(2);
    public static final ServiceType f = new ServiceType(3);
    public static final ServiceType q = new ServiceType(4);
    private ASN1Enumerated x;

    public ServiceType(int value) {
        this.x = new ASN1Enumerated(value);
    }

    public BigInteger e() {
        return this.x.q();
    }

    public ASN1Primitive toASN1Primitive() {
        return this.x;
    }

    public String toString() {
        String str;
        int num = this.x.q().intValue();
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(num);
        if (num == c.e().intValue()) {
            str = "(CPD)";
        } else if (num == d.e().intValue()) {
            str = "(VSD)";
        } else if (num == f.e().intValue()) {
            str = "(VPKC)";
        } else {
            str = num == q.e().intValue() ? "(CCPD)" : "?";
        }
        sb.append(str);
        return sb.toString();
    }
}
