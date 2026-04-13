package org.spongycastle.asn1;

public class DERFactory {
    static final ASN1Sequence a = new DERSequence();
    static final ASN1Set b = new DERSet();

    DERFactory() {
    }

    static ASN1Sequence a(ASN1EncodableVector v) {
        return v.c() < 1 ? a : new DLSequence(v);
    }

    static ASN1Set b(ASN1EncodableVector v) {
        return v.c() < 1 ? b : new DLSet(v);
    }
}
