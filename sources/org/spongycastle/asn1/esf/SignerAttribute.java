package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Attribute;
import org.spongycastle.asn1.x509.AttributeCertificate;

public class SignerAttribute extends ASN1Object {
    private Object[] c;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        int i = 0;
        while (true) {
            Object[] objArr = this.c;
            if (i == objArr.length) {
                return new DERSequence(v);
            }
            if (objArr[i] instanceof Attribute[]) {
                v.a(new DERTaggedObject(0, new DERSequence((ASN1Encodable[]) (Attribute[]) this.c[i])));
            } else {
                v.a(new DERTaggedObject(1, (AttributeCertificate) this.c[i]));
            }
            i++;
        }
    }
}
