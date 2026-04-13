package org.spongycastle.asn1.rosstandart;

import androidx.exifinterface.media.ExifInterface;
import com.amazonaws.kinesisvideo.util.VersionUtil;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface RosstandartObjectIdentifiers {
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

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.2.643.7");
        a = aSN1ObjectIdentifier;
        ASN1ObjectIdentifier o2 = aSN1ObjectIdentifier.o("1");
        b = o2;
        c = o2.o("1.2.2");
        d = o2.o(VersionUtil.AWS_SDK_KVS_PRODUCER_VERSION_STRING);
        e = o2.o("1.4.1");
        f = o2.o("1.4.2");
        g = o2.o("1.1.1");
        h = o2.o("1.1.2");
        i = o2.o("1.3.2");
        j = o2.o("1.3.3");
        ASN1ObjectIdentifier o3 = o2.o("1.6");
        k = o3;
        l = o3.o("1");
        m = o3.o(ExifInterface.GPS_MEASUREMENT_2D);
        n = o2.o("2.1.1.1");
        o = o2.o("2.1.2.1");
        p = o2.o("2.1.2.2");
        q = o2.o("2.1.2.3");
        r = o2.o("2.5.1.1");
    }
}
