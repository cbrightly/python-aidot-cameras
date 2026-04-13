package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class IetfAttrSyntax extends ASN1Object {
    GeneralNames c;
    Vector d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (this.c != null) {
            v.a(new DERTaggedObject(0, this.c));
        }
        ASN1EncodableVector v2 = new ASN1EncodableVector();
        Enumeration i = this.d.elements();
        while (i.hasMoreElements()) {
            v2.a((ASN1Encodable) i.nextElement());
        }
        v.a(new DERSequence(v2));
        return new DERSequence(v);
    }
}
