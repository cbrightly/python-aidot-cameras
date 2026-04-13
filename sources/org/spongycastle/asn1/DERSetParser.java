package org.spongycastle.asn1;

import java.io.IOException;

public class DERSetParser implements ASN1SetParser {
    private ASN1StreamParser c;

    DERSetParser(ASN1StreamParser parser) {
        this.c = parser;
    }

    public ASN1Primitive d() {
        return new DERSet(this.c.d(), false);
    }

    public ASN1Primitive toASN1Primitive() {
        try {
            return d();
        } catch (IOException e) {
            throw new ASN1ParsingException(e.getMessage(), e);
        }
    }
}
