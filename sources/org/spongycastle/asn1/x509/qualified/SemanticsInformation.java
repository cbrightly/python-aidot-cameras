package org.spongycastle.asn1.x509.qualified;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.GeneralName;

public class SemanticsInformation extends ASN1Object {
    private ASN1ObjectIdentifier c;
    private GeneralName[] d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector seq = new ASN1EncodableVector();
        ASN1ObjectIdentifier aSN1ObjectIdentifier = this.c;
        if (aSN1ObjectIdentifier != null) {
            seq.a(aSN1ObjectIdentifier);
        }
        if (this.d != null) {
            ASN1EncodableVector seqname = new ASN1EncodableVector();
            int i = 0;
            while (true) {
                GeneralName[] generalNameArr = this.d;
                if (i >= generalNameArr.length) {
                    break;
                }
                seqname.a(generalNameArr[i]);
                i++;
            }
            seq.a(new DERSequence(seqname));
        }
        return new DERSequence(seq);
    }
}
