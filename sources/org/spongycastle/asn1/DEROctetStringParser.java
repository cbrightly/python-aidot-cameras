package org.spongycastle.asn1;

import java.io.IOException;
import java.io.InputStream;

public class DEROctetStringParser implements ASN1OctetStringParser {
    private DefiniteLengthInputStream c;

    DEROctetStringParser(DefiniteLengthInputStream stream) {
        this.c = stream;
    }

    public InputStream b() {
        return this.c;
    }

    public ASN1Primitive d() {
        return new DEROctetString(this.c.g());
    }

    public ASN1Primitive toASN1Primitive() {
        try {
            return d();
        } catch (IOException e) {
            throw new ASN1ParsingException("IOException converting stream to byte array: " + e.getMessage(), e);
        }
    }
}
