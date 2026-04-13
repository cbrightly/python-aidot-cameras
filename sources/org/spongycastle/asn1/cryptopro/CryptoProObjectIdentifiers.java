package org.spongycastle.asn1.cryptopro;

import androidx.exifinterface.media.ExifInterface;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface CryptoProObjectIdentifiers {
    public static final ASN1ObjectIdentifier A;
    public static final ASN1ObjectIdentifier B;
    public static final ASN1ObjectIdentifier C;
    public static final ASN1ObjectIdentifier D;
    public static final ASN1ObjectIdentifier E;
    public static final ASN1ObjectIdentifier F;
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
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.2.643.2.2");
        a = aSN1ObjectIdentifier;
        b = aSN1ObjectIdentifier.o(DbParams.GZIP_DATA_ENCRYPT);
        c = aSN1ObjectIdentifier.o("10");
        d = aSN1ObjectIdentifier.o("13.0");
        e = aSN1ObjectIdentifier.o("13.1");
        f = aSN1ObjectIdentifier.o("21");
        g = aSN1ObjectIdentifier.o("31.0");
        h = aSN1ObjectIdentifier.o("31.1");
        i = aSN1ObjectIdentifier.o("31.2");
        j = aSN1ObjectIdentifier.o("31.3");
        k = aSN1ObjectIdentifier.o("31.4");
        l = aSN1ObjectIdentifier.o("20");
        m = aSN1ObjectIdentifier.o("19");
        n = aSN1ObjectIdentifier.o("4");
        o = aSN1ObjectIdentifier.o(ExifInterface.GPS_MEASUREMENT_3D);
        p = aSN1ObjectIdentifier.o("30.1");
        q = aSN1ObjectIdentifier.o("32.2");
        r = aSN1ObjectIdentifier.o("32.3");
        s = aSN1ObjectIdentifier.o("32.4");
        t = aSN1ObjectIdentifier.o("32.5");
        u = aSN1ObjectIdentifier.o("33.1");
        v = aSN1ObjectIdentifier.o("33.2");
        w = aSN1ObjectIdentifier.o("33.3");
        x = aSN1ObjectIdentifier.o("35.1");
        y = aSN1ObjectIdentifier.o("35.2");
        z = aSN1ObjectIdentifier.o("35.3");
        A = aSN1ObjectIdentifier.o("36.0");
        B = aSN1ObjectIdentifier.o("36.1");
        C = aSN1ObjectIdentifier.o("36.0");
        D = aSN1ObjectIdentifier.o("36.1");
        E = aSN1ObjectIdentifier.o("96");
        F = aSN1ObjectIdentifier.o("98");
    }
}
