package org.spongycastle.asn1.icao;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.x509.Certificate;

public class CscaMasterList extends ASN1Object {
    private ASN1Integer c;
    private Certificate[] d;

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector seq = new ASN1EncodableVector();
        seq.a(this.c);
        ASN1EncodableVector certSet = new ASN1EncodableVector();
        int i = 0;
        while (true) {
            Certificate[] certificateArr = this.d;
            if (i < certificateArr.length) {
                certSet.a(certificateArr[i]);
                i++;
            } else {
                seq.a(new DERSet(certSet));
                return new DERSequence(seq);
            }
        }
    }
}
