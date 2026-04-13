package org.spongycastle.asn1.x509;

import java.io.IOException;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;

public class V2TBSCertListGenerator {
    private static final ASN1Sequence[] a;
    private ASN1Integer b = new ASN1Integer(1);
    private Time c = null;
    private Extensions d = null;
    private ASN1EncodableVector e = new ASN1EncodableVector();

    static {
        ASN1Sequence[] aSN1SequenceArr = new ASN1Sequence[11];
        a = aSN1SequenceArr;
        aSN1SequenceArr[0] = a(0);
        aSN1SequenceArr[1] = a(1);
        aSN1SequenceArr[2] = a(2);
        aSN1SequenceArr[3] = a(3);
        aSN1SequenceArr[4] = a(4);
        aSN1SequenceArr[5] = a(5);
        aSN1SequenceArr[6] = a(6);
        aSN1SequenceArr[7] = a(7);
        aSN1SequenceArr[8] = a(8);
        aSN1SequenceArr[9] = a(9);
        aSN1SequenceArr[10] = a(10);
    }

    private static ASN1Sequence a(int reasonCode) {
        ASN1EncodableVector v = new ASN1EncodableVector();
        CRLReason crlReason = CRLReason.g(reasonCode);
        try {
            v.a(Extension.a1);
            v.a(new DEROctetString(crlReason.getEncoded()));
            return new DERSequence(v);
        } catch (IOException e2) {
            throw new IllegalArgumentException("error encoding reason: " + e2);
        }
    }
}
