package org.spongycastle.asn1.x9;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Null;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;

public class X962Parameters extends ASN1Object implements ASN1Choice {
    private ASN1Primitive c = null;

    public static X962Parameters e(Object obj) {
        if (obj == null || (obj instanceof X962Parameters)) {
            return (X962Parameters) obj;
        }
        if (obj instanceof ASN1Primitive) {
            return new X962Parameters((ASN1Primitive) obj);
        }
        if (obj instanceof byte[]) {
            try {
                return new X962Parameters(ASN1Primitive.h((byte[]) obj));
            } catch (Exception e) {
                throw new IllegalArgumentException("unable to parse encoded data: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("unknown object in getInstance()");
        }
    }

    public X962Parameters(X9ECParameters ecParameters) {
        this.c = ecParameters.toASN1Primitive();
    }

    public X962Parameters(ASN1ObjectIdentifier namedCurve) {
        this.c = namedCurve;
    }

    public X962Parameters(ASN1Null obj) {
        this.c = obj;
    }

    public X962Parameters(ASN1Primitive obj) {
        this.c = obj;
    }

    public boolean isNamedCurve() {
        return this.c instanceof ASN1ObjectIdentifier;
    }

    public boolean g() {
        return this.c instanceof ASN1Null;
    }

    public ASN1Primitive f() {
        return this.c;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }
}
