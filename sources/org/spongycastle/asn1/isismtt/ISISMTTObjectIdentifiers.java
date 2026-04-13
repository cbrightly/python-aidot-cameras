package org.spongycastle.asn1.isismtt;

import androidx.exifinterface.media.ExifInterface;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface ISISMTTObjectIdentifiers {
    public static final ASN1ObjectIdentifier a;
    public static final ASN1ObjectIdentifier b;
    public static final ASN1ObjectIdentifier c;
    public static final ASN1ObjectIdentifier d;
    public static final ASN1ObjectIdentifier e;
    public static final ASN1ObjectIdentifier f;
    public static final ASN1ObjectIdentifier g;
    public static final ASN1ObjectIdentifier h;
    public static final ASN1ObjectIdentifier i;
    public static final ASN1ObjectIdentifier j;
    public static final ASN1ObjectIdentifier k;
    public static final ASN1ObjectIdentifier l;
    public static final ASN1ObjectIdentifier m;
    public static final ASN1ObjectIdentifier n;
    public static final ASN1ObjectIdentifier o;
    public static final ASN1ObjectIdentifier p;
    public static final ASN1ObjectIdentifier q;
    public static final ASN1ObjectIdentifier r;
    public static final ASN1ObjectIdentifier s;
    public static final ASN1ObjectIdentifier t = new ASN1ObjectIdentifier("0.2.262.1.10.12.0");

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.3.36.8");
        a = aSN1ObjectIdentifier;
        ASN1ObjectIdentifier o2 = aSN1ObjectIdentifier.o("1");
        b = o2;
        c = o2.o("1");
        ASN1ObjectIdentifier o3 = aSN1ObjectIdentifier.o(ExifInterface.GPS_MEASUREMENT_3D);
        d = o3;
        e = o3.o("1");
        f = o3.o(ExifInterface.GPS_MEASUREMENT_2D);
        g = o3.o(ExifInterface.GPS_MEASUREMENT_3D);
        h = o3.o("4");
        i = o3.o("5");
        j = o3.o("6");
        k = o3.o("7");
        l = o3.o("8");
        m = o3.o(DbParams.GZIP_DATA_ENCRYPT);
        n = o3.o("10");
        o = o3.o("11");
        p = o3.o("12");
        q = o3.o("13");
        r = o3.o("14");
        s = o3.o("15");
    }
}
