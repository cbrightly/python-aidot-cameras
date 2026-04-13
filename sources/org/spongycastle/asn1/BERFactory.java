package org.spongycastle.asn1;

public class BERFactory {
    static final BERSequence a = new BERSequence();
    static final BERSet b = new BERSet();

    BERFactory() {
    }

    static BERSequence a(ASN1EncodableVector v) {
        return v.c() < 1 ? a : new BERSequence(v);
    }
}
