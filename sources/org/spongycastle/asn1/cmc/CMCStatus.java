package org.spongycastle.asn1.cmc;

import java.util.HashMap;
import java.util.Map;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;

public class CMCStatus extends ASN1Object {
    public static final CMCStatus c;
    public static final CMCStatus d;
    public static final CMCStatus f;
    private static Map p0;
    public static final CMCStatus q;
    public static final CMCStatus x;
    public static final CMCStatus y;
    public static final CMCStatus z;
    private final ASN1Integer a1;

    static {
        CMCStatus cMCStatus = new CMCStatus(new ASN1Integer(0));
        c = cMCStatus;
        CMCStatus cMCStatus2 = new CMCStatus(new ASN1Integer(2));
        d = cMCStatus2;
        CMCStatus cMCStatus3 = new CMCStatus(new ASN1Integer(3));
        f = cMCStatus3;
        CMCStatus cMCStatus4 = new CMCStatus(new ASN1Integer(4));
        q = cMCStatus4;
        CMCStatus cMCStatus5 = new CMCStatus(new ASN1Integer(5));
        x = cMCStatus5;
        CMCStatus cMCStatus6 = new CMCStatus(new ASN1Integer(6));
        y = cMCStatus6;
        CMCStatus cMCStatus7 = new CMCStatus(new ASN1Integer(7));
        z = cMCStatus7;
        HashMap hashMap = new HashMap();
        p0 = hashMap;
        hashMap.put(cMCStatus.a1, cMCStatus);
        p0.put(cMCStatus2.a1, cMCStatus2);
        p0.put(cMCStatus3.a1, cMCStatus3);
        p0.put(cMCStatus4.a1, cMCStatus4);
        p0.put(cMCStatus5.a1, cMCStatus5);
        p0.put(cMCStatus6.a1, cMCStatus6);
        p0.put(cMCStatus7.a1, cMCStatus7);
    }

    private CMCStatus(ASN1Integer value) {
        this.a1 = value;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a1;
    }
}
