package org.spongycastle.asn1.x509.qualified;

import androidx.exifinterface.media.ExifInterface;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface ETSIQCObjectIdentifiers {
    public static final ASN1ObjectIdentifier X2 = new ASN1ObjectIdentifier("0.4.0.1862.1.1");
    public static final ASN1ObjectIdentifier Y2 = new ASN1ObjectIdentifier("0.4.0.1862.1.2");
    public static final ASN1ObjectIdentifier Z2 = new ASN1ObjectIdentifier("0.4.0.1862.1.3");
    public static final ASN1ObjectIdentifier a3 = new ASN1ObjectIdentifier("0.4.0.1862.1.4");
    public static final ASN1ObjectIdentifier b3 = new ASN1ObjectIdentifier("0.4.0.1862.1.5");
    public static final ASN1ObjectIdentifier c3;
    public static final ASN1ObjectIdentifier d3;
    public static final ASN1ObjectIdentifier e3;
    public static final ASN1ObjectIdentifier f3;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("0.4.0.1862.1.6");
        c3 = aSN1ObjectIdentifier;
        d3 = aSN1ObjectIdentifier.o("1");
        e3 = aSN1ObjectIdentifier.o(ExifInterface.GPS_MEASUREMENT_2D);
        f3 = aSN1ObjectIdentifier.o(ExifInterface.GPS_MEASUREMENT_3D);
    }
}
