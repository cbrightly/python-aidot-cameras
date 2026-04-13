package org.spongycastle.asn1.icao;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class LDSSecurityObject extends ASN1Object implements ICAOObjectIdentifiers {
    private ASN1Integer c;
    private AlgorithmIdentifier d;
    private DataGroupHash[] f;
    private LDSVersionInfo q;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector seq = new ASN1EncodableVector();
        seq.a(this.c);
        seq.a(this.d);
        ASN1EncodableVector seqname = new ASN1EncodableVector();
        int i = 0;
        while (true) {
            DataGroupHash[] dataGroupHashArr = this.f;
            if (i >= dataGroupHashArr.length) {
                break;
            }
            seqname.a(dataGroupHashArr[i]);
            i++;
        }
        seq.a(new DERSequence(seqname));
        LDSVersionInfo lDSVersionInfo = this.q;
        if (lDSVersionInfo != null) {
            seq.a(lDSVersionInfo);
        }
        return new DERSequence(seq);
    }
}
