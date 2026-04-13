package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;

public class PKIStatus extends ASN1Object {
    public static final PKIStatus c = new PKIStatus(0);
    public static final PKIStatus d = new PKIStatus(1);
    public static final PKIStatus f = new PKIStatus(2);
    public static final PKIStatus q = new PKIStatus(3);
    public static final PKIStatus x = new PKIStatus(4);
    public static final PKIStatus y = new PKIStatus(5);
    public static final PKIStatus z = new PKIStatus(6);
    private ASN1Integer p0;

    private PKIStatus(int value) {
        this(new ASN1Integer((long) value));
    }

    private PKIStatus(ASN1Integer value) {
        this.p0 = value;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.p0;
    }
}
