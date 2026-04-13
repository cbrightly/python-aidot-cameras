package org.spongycastle.asn1;

import java.io.IOException;

public class BERTaggedObjectParser implements ASN1TaggedObjectParser {
    private boolean c;
    private int d;
    private ASN1StreamParser f;

    BERTaggedObjectParser(boolean constructed, int tagNumber, ASN1StreamParser parser) {
        this.c = constructed;
        this.d = tagNumber;
        this.f = parser;
    }

    public ASN1Primitive d() {
        return this.f.c(this.c, this.d);
    }

    public ASN1Primitive toASN1Primitive() {
        try {
            return d();
        } catch (IOException e) {
            throw new ASN1ParsingException(e.getMessage());
        }
    }
}
