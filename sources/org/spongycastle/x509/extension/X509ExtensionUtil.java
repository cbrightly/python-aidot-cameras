package org.spongycastle.x509.extension;

import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;

public class X509ExtensionUtil {
    public static ASN1Primitive a(byte[] encodedValue) {
        return ASN1Primitive.h(((ASN1OctetString) ASN1Primitive.h(encodedValue)).q());
    }
}
