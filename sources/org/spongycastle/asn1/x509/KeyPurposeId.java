package org.spongycastle.asn1.x509;

import androidx.exifinterface.media.ExifInterface;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;

public class KeyPurposeId extends ASN1Object {
    public static final KeyPurposeId A4;
    public static final KeyPurposeId B4;
    public static final KeyPurposeId C4;
    public static final KeyPurposeId D4;
    public static final KeyPurposeId E4;
    public static final KeyPurposeId F4;
    public static final KeyPurposeId G4 = new KeyPurposeId(new ASN1ObjectIdentifier("1.3.6.1.4.1.311.20.2.2"));
    public static final KeyPurposeId H4 = new KeyPurposeId(new ASN1ObjectIdentifier("1.3.6.1.1.1.1.22"));
    public static final KeyPurposeId I4 = new KeyPurposeId(new ASN1ObjectIdentifier("1.3.6.1.4.1.311.10.3.3"));
    public static final KeyPurposeId J4 = new KeyPurposeId(new ASN1ObjectIdentifier("2.16.840.1.113730.4.1"));
    public static final KeyPurposeId a1;
    public static final KeyPurposeId a2;
    private static final ASN1ObjectIdentifier c;
    public static final KeyPurposeId d = new KeyPurposeId(Extension.F4.o("0"));
    public static final KeyPurposeId f;
    public static final KeyPurposeId p0;
    public static final KeyPurposeId p1;
    public static final KeyPurposeId p2;
    public static final KeyPurposeId p3;
    public static final KeyPurposeId p4;
    public static final KeyPurposeId q;
    public static final KeyPurposeId x;
    public static final KeyPurposeId y;
    public static final KeyPurposeId z;
    public static final KeyPurposeId z4;
    private ASN1ObjectIdentifier K4;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.3");
        c = aSN1ObjectIdentifier;
        f = new KeyPurposeId(aSN1ObjectIdentifier.o("1"));
        q = new KeyPurposeId(aSN1ObjectIdentifier.o(ExifInterface.GPS_MEASUREMENT_2D));
        x = new KeyPurposeId(aSN1ObjectIdentifier.o(ExifInterface.GPS_MEASUREMENT_3D));
        y = new KeyPurposeId(aSN1ObjectIdentifier.o("4"));
        z = new KeyPurposeId(aSN1ObjectIdentifier.o("5"));
        p0 = new KeyPurposeId(aSN1ObjectIdentifier.o("6"));
        a1 = new KeyPurposeId(aSN1ObjectIdentifier.o("7"));
        p1 = new KeyPurposeId(aSN1ObjectIdentifier.o("8"));
        a2 = new KeyPurposeId(aSN1ObjectIdentifier.o(DbParams.GZIP_DATA_ENCRYPT));
        p2 = new KeyPurposeId(aSN1ObjectIdentifier.o("10"));
        p3 = new KeyPurposeId(aSN1ObjectIdentifier.o("11"));
        p4 = new KeyPurposeId(aSN1ObjectIdentifier.o("12"));
        z4 = new KeyPurposeId(aSN1ObjectIdentifier.o("13"));
        A4 = new KeyPurposeId(aSN1ObjectIdentifier.o("14"));
        B4 = new KeyPurposeId(aSN1ObjectIdentifier.o("15"));
        C4 = new KeyPurposeId(aSN1ObjectIdentifier.o("16"));
        D4 = new KeyPurposeId(aSN1ObjectIdentifier.o("17"));
        E4 = new KeyPurposeId(aSN1ObjectIdentifier.o("18"));
        F4 = new KeyPurposeId(aSN1ObjectIdentifier.o("19"));
    }

    private KeyPurposeId(ASN1ObjectIdentifier id) {
        this.K4 = id;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.K4;
    }

    public String toString() {
        return this.K4.toString();
    }
}
