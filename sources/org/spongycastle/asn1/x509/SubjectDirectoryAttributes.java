package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Vector;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;

public class SubjectDirectoryAttributes extends ASN1Object {
    private Vector c;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vec = new ASN1EncodableVector();
        Enumeration e = this.c.elements();
        while (e.hasMoreElements()) {
            vec.a((Attribute) e.nextElement());
        }
        return new DERSequence(vec);
    }
}
