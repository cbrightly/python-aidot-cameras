package org.spongycastle.asn1;

import java.io.IOException;

public class BERApplicationSpecificParser implements ASN1ApplicationSpecificParser {
    private final int c;
    private final ASN1StreamParser d;

    BERApplicationSpecificParser(int tag, ASN1StreamParser parser) {
        this.c = tag;
        this.d = parser;
    }

    public ASN1Primitive d() {
        return new BERApplicationSpecific(this.c, this.d.d());
    }

    public ASN1Primitive toASN1Primitive() {
        try {
            return d();
        } catch (IOException e) {
            throw new ASN1ParsingException(e.getMessage(), e);
        }
    }
}
