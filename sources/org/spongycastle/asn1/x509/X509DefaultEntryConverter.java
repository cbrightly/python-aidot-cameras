package org.spongycastle.asn1.x509;

import java.io.IOException;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.DERUTF8String;

public class X509DefaultEntryConverter extends X509NameEntryConverter {
    public ASN1Primitive b(ASN1ObjectIdentifier oid, String value) {
        if (value.length() == 0 || value.charAt(0) != '#') {
            if (value.length() != 0 && value.charAt(0) == '\\') {
                value = value.substring(1);
            }
            if (oid.equals(X509Name.O4) || oid.equals(X509Name.S4)) {
                return new DERIA5String(value);
            }
            if (oid.equals(X509Name.E4)) {
                return new DERGeneralizedTime(value);
            }
            if (oid.equals(X509Name.c) || oid.equals(X509Name.y) || oid.equals(X509Name.C4) || oid.equals(X509Name.M4)) {
                return new DERPrintableString(value);
            }
            return new DERUTF8String(value);
        }
        try {
            return a(value, 1);
        } catch (IOException e) {
            throw new RuntimeException("can't recode value for oid " + oid.s());
        }
    }
}
