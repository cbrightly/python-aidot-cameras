package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x500.DirectoryString;

public class SignerLocation extends ASN1Object {
    private DirectoryString c;
    private DirectoryString d;
    private ASN1Sequence f;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (this.c != null) {
            v.a(new DERTaggedObject(true, 0, this.c));
        }
        if (this.d != null) {
            v.a(new DERTaggedObject(true, 1, this.d));
        }
        if (this.f != null) {
            v.a(new DERTaggedObject(true, 2, this.f));
        }
        return new DERSequence(v);
    }
}
