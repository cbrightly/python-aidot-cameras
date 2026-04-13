package org.spongycastle.asn1.x509;

import androidx.exifinterface.media.ExifInterface;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface X509ObjectIdentifiers {
    public static final ASN1ObjectIdentifier C2 = new ASN1ObjectIdentifier("2.5.4.3").v();
    public static final ASN1ObjectIdentifier D2 = new ASN1ObjectIdentifier("2.5.4.6").v();
    public static final ASN1ObjectIdentifier E2 = new ASN1ObjectIdentifier("2.5.4.7").v();
    public static final ASN1ObjectIdentifier F2 = new ASN1ObjectIdentifier("2.5.4.8").v();
    public static final ASN1ObjectIdentifier G2 = new ASN1ObjectIdentifier("2.5.4.10").v();
    public static final ASN1ObjectIdentifier H2 = new ASN1ObjectIdentifier("2.5.4.11").v();
    public static final ASN1ObjectIdentifier I2 = new ASN1ObjectIdentifier("2.5.4.20").v();
    public static final ASN1ObjectIdentifier J2 = new ASN1ObjectIdentifier("2.5.4.41").v();
    public static final ASN1ObjectIdentifier K2 = new ASN1ObjectIdentifier("2.5.4.97").v();
    public static final ASN1ObjectIdentifier L2 = new ASN1ObjectIdentifier("1.3.14.3.2.26").v();
    public static final ASN1ObjectIdentifier M2 = new ASN1ObjectIdentifier("1.3.36.3.2.1").v();
    public static final ASN1ObjectIdentifier N2 = new ASN1ObjectIdentifier("1.3.36.3.3.1.2").v();
    public static final ASN1ObjectIdentifier O2 = new ASN1ObjectIdentifier("2.5.8.1.1").v();
    public static final ASN1ObjectIdentifier P2;
    public static final ASN1ObjectIdentifier Q2;
    public static final ASN1ObjectIdentifier R2 = new ASN1ObjectIdentifier("2.5.29");
    public static final ASN1ObjectIdentifier S2;
    public static final ASN1ObjectIdentifier T2;
    public static final ASN1ObjectIdentifier U2;
    public static final ASN1ObjectIdentifier V2;
    public static final ASN1ObjectIdentifier W2;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.3.6.1.5.5.7");
        P2 = aSN1ObjectIdentifier;
        Q2 = aSN1ObjectIdentifier.o("1");
        ASN1ObjectIdentifier o = aSN1ObjectIdentifier.o("48");
        S2 = o;
        ASN1ObjectIdentifier v = o.o(ExifInterface.GPS_MEASUREMENT_2D).v();
        T2 = v;
        ASN1ObjectIdentifier v2 = o.o("1").v();
        U2 = v2;
        V2 = v2;
        W2 = v;
    }
}
