package org.spongycastle.asn1.x509.sigi;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x500.DirectoryString;

public class NameOrPseudonym extends ASN1Object implements ASN1Choice {
    private DirectoryString c;
    private DirectoryString d;
    private ASN1Sequence f;

    public ASN1Primitive toASN1Primitive() {
        DirectoryString directoryString = this.c;
        if (directoryString != null) {
            return directoryString.toASN1Primitive();
        }
        ASN1EncodableVector vec1 = new ASN1EncodableVector();
        vec1.a(this.d);
        vec1.a(this.f);
        return new DERSequence(vec1);
    }
}
