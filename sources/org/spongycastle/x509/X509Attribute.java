package org.spongycastle.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.x509.Attribute;

public class X509Attribute extends ASN1Object {
    Attribute c;

    X509Attribute(ASN1Encodable at) {
        this.c = Attribute.f(at);
    }

    public String e() {
        return this.c.e().s();
    }

    public ASN1Primitive toASN1Primitive() {
        return this.c.toASN1Primitive();
    }
}
