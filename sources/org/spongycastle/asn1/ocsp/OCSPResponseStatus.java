package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1Enumerated;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;

public class OCSPResponseStatus extends ASN1Object {
    private ASN1Enumerated c;

    private OCSPResponseStatus(ASN1Enumerated value) {
        this.c = value;
    }

    public static OCSPResponseStatus e(Object obj) {
        if (obj instanceof OCSPResponseStatus) {
            return (OCSPResponseStatus) obj;
        }
        if (obj != null) {
            return new OCSPResponseStatus(ASN1Enumerated.p(obj));
        }
        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}
