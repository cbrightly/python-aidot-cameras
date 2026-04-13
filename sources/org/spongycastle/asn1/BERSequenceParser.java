package org.spongycastle.asn1;

import java.io.IOException;

public class BERSequenceParser implements ASN1SequenceParser {
    private ASN1StreamParser c;

    BERSequenceParser(ASN1StreamParser parser) {
        this.c = parser;
    }

    public ASN1Primitive d() {
        return new BERSequence(this.c.d());
    }

    public ASN1Primitive toASN1Primitive() {
        try {
            return d();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
