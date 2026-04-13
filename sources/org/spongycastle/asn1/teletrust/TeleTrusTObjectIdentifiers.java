package org.spongycastle.asn1.teletrust;

import androidx.exifinterface.media.ExifInterface;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface TeleTrusTObjectIdentifiers {
    public static final ASN1ObjectIdentifier A;
    public static final ASN1ObjectIdentifier B;
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
    public static final ASN1ObjectIdentifier t;
    public static final ASN1ObjectIdentifier u;
    public static final ASN1ObjectIdentifier v;
    public static final ASN1ObjectIdentifier w;
    public static final ASN1ObjectIdentifier x;
    public static final ASN1ObjectIdentifier y;
    public static final ASN1ObjectIdentifier z;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.3.36.3");
        a = aSN1ObjectIdentifier;
        b = aSN1ObjectIdentifier.o("2.1");
        c = aSN1ObjectIdentifier.o("2.2");
        d = aSN1ObjectIdentifier.o("2.3");
        ASN1ObjectIdentifier o2 = aSN1ObjectIdentifier.o("3.1");
        e = o2;
        f = o2.o(ExifInterface.GPS_MEASUREMENT_2D);
        g = o2.o(ExifInterface.GPS_MEASUREMENT_3D);
        h = o2.o("4");
        ASN1ObjectIdentifier o3 = aSN1ObjectIdentifier.o("3.2");
        i = o3;
        j = o3.o("1");
        k = o3.o(ExifInterface.GPS_MEASUREMENT_2D);
        ASN1ObjectIdentifier o4 = aSN1ObjectIdentifier.o("3.2.8");
        l = o4;
        ASN1ObjectIdentifier o5 = o4.o("1");
        m = o5;
        ASN1ObjectIdentifier o6 = o5.o("1");
        n = o6;
        o = o6.o("1");
        p = o6.o(ExifInterface.GPS_MEASUREMENT_2D);
        q = o6.o(ExifInterface.GPS_MEASUREMENT_3D);
        r = o6.o("4");
        s = o6.o("5");
        t = o6.o("6");
        u = o6.o("7");
        v = o6.o("8");
        w = o6.o(DbParams.GZIP_DATA_ENCRYPT);
        x = o6.o("10");
        y = o6.o("11");
        z = o6.o("12");
        A = o6.o("13");
        B = o6.o("14");
    }
}
