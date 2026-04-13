package org.spongycastle.asn1.cmc;

import java.util.HashMap;
import java.util.Map;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;

public class CMCFailInfo extends ASN1Object {
    public static final CMCFailInfo a1;
    public static final CMCFailInfo a2;
    public static final CMCFailInfo c;
    public static final CMCFailInfo d;
    public static final CMCFailInfo f;
    public static final CMCFailInfo p0;
    public static final CMCFailInfo p1;
    public static final CMCFailInfo p2;
    public static final CMCFailInfo p3;
    public static final CMCFailInfo p4;
    public static final CMCFailInfo q;
    public static final CMCFailInfo x;
    public static final CMCFailInfo y;
    public static final CMCFailInfo z;
    private static Map z4;
    private final ASN1Integer A4;

    static {
        CMCFailInfo cMCFailInfo = new CMCFailInfo(new ASN1Integer(0));
        c = cMCFailInfo;
        CMCFailInfo cMCFailInfo2 = new CMCFailInfo(new ASN1Integer(1));
        d = cMCFailInfo2;
        CMCFailInfo cMCFailInfo3 = new CMCFailInfo(new ASN1Integer(2));
        f = cMCFailInfo3;
        CMCFailInfo cMCFailInfo4 = new CMCFailInfo(new ASN1Integer(3));
        q = cMCFailInfo4;
        CMCFailInfo cMCFailInfo5 = new CMCFailInfo(new ASN1Integer(4));
        x = cMCFailInfo5;
        CMCFailInfo cMCFailInfo6 = new CMCFailInfo(new ASN1Integer(5));
        y = cMCFailInfo6;
        CMCFailInfo cMCFailInfo7 = new CMCFailInfo(new ASN1Integer(6));
        z = cMCFailInfo7;
        CMCFailInfo cMCFailInfo8 = new CMCFailInfo(new ASN1Integer(7));
        p0 = cMCFailInfo8;
        CMCFailInfo cMCFailInfo9 = new CMCFailInfo(new ASN1Integer(8));
        a1 = cMCFailInfo9;
        CMCFailInfo cMCFailInfo10 = new CMCFailInfo(new ASN1Integer(9));
        p1 = cMCFailInfo10;
        CMCFailInfo cMCFailInfo11 = new CMCFailInfo(new ASN1Integer(10));
        a2 = cMCFailInfo11;
        CMCFailInfo cMCFailInfo12 = new CMCFailInfo(new ASN1Integer(11));
        p2 = cMCFailInfo12;
        CMCFailInfo cMCFailInfo13 = new CMCFailInfo(new ASN1Integer(12));
        p3 = cMCFailInfo13;
        CMCFailInfo cMCFailInfo14 = cMCFailInfo12;
        CMCFailInfo cMCFailInfo15 = new CMCFailInfo(new ASN1Integer(13));
        p4 = cMCFailInfo15;
        HashMap hashMap = new HashMap();
        z4 = hashMap;
        hashMap.put(cMCFailInfo.A4, cMCFailInfo);
        z4.put(cMCFailInfo2.A4, cMCFailInfo2);
        z4.put(cMCFailInfo3.A4, cMCFailInfo3);
        z4.put(cMCFailInfo4.A4, cMCFailInfo4);
        z4.put(cMCFailInfo5.A4, cMCFailInfo5);
        z4.put(cMCFailInfo9.A4, cMCFailInfo9);
        z4.put(cMCFailInfo6.A4, cMCFailInfo6);
        z4.put(cMCFailInfo7.A4, cMCFailInfo7);
        z4.put(cMCFailInfo8.A4, cMCFailInfo8);
        z4.put(cMCFailInfo9.A4, cMCFailInfo9);
        z4.put(cMCFailInfo10.A4, cMCFailInfo10);
        z4.put(cMCFailInfo5.A4, cMCFailInfo5);
        z4.put(cMCFailInfo9.A4, cMCFailInfo9);
        z4.put(cMCFailInfo11.A4, cMCFailInfo11);
        z4.put(cMCFailInfo14.A4, cMCFailInfo14);
        CMCFailInfo cMCFailInfo16 = cMCFailInfo13;
        z4.put(cMCFailInfo16.A4, cMCFailInfo16);
        z4.put(cMCFailInfo15.A4, cMCFailInfo15);
    }

    private CMCFailInfo(ASN1Integer value) {
        this.A4 = value;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.A4;
    }
}
