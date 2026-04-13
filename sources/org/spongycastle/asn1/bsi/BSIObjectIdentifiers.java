package org.spongycastle.asn1.bsi;

import androidx.exifinterface.media.ExifInterface;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface BSIObjectIdentifiers {
    public static final ASN1ObjectIdentifier a;
    public static final ASN1ObjectIdentifier b;
    public static final ASN1ObjectIdentifier c;
    public static final ASN1ObjectIdentifier d;
    public static final ASN1ObjectIdentifier e;
    public static final ASN1ObjectIdentifier f;
    public static final ASN1ObjectIdentifier g;
    public static final ASN1ObjectIdentifier h;
    public static final ASN1ObjectIdentifier i;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("0.4.0.127.0.7");
        a = aSN1ObjectIdentifier;
        ASN1ObjectIdentifier o = aSN1ObjectIdentifier.o("1.1");
        b = o;
        ASN1ObjectIdentifier o2 = o.o("4.1");
        c = o2;
        d = o2.o("1");
        e = o2.o(ExifInterface.GPS_MEASUREMENT_2D);
        f = o2.o(ExifInterface.GPS_MEASUREMENT_3D);
        g = o2.o("4");
        h = o2.o("5");
        i = o2.o("6");
    }
}
