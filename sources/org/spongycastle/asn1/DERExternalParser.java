package org.spongycastle.asn1;

import java.io.IOException;

public class DERExternalParser implements ASN1Encodable, InMemoryRepresentable {
    private ASN1StreamParser c;

    public DERExternalParser(ASN1StreamParser parser) {
        this.c = parser;
    }

    public ASN1Primitive d() {
        try {
            return new DERExternal(this.c.d());
        } catch (IllegalArgumentException e) {
            throw new ASN1Exception(e.getMessage(), e);
        }
    }

    public ASN1Primitive toASN1Primitive() {
        try {
            return d();
        } catch (IOException ioe) {
            throw new ASN1ParsingException("unable to get DER object", ioe);
        } catch (IllegalArgumentException ioe2) {
            throw new ASN1ParsingException("unable to get DER object", ioe2);
        }
    }
}
