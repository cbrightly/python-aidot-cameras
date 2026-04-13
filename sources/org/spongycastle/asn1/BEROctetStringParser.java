package org.spongycastle.asn1;

import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.util.io.Streams;

public class BEROctetStringParser implements ASN1OctetStringParser {
    private ASN1StreamParser c;

    BEROctetStringParser(ASN1StreamParser parser) {
        this.c = parser;
    }

    public InputStream b() {
        return new ConstructedOctetStream(this.c);
    }

    public ASN1Primitive d() {
        return new BEROctetString(Streams.b(b()));
    }

    public ASN1Primitive toASN1Primitive() {
        try {
            return d();
        } catch (IOException e) {
            throw new ASN1ParsingException("IOException converting stream to byte array: " + e.getMessage(), e);
        }
    }
}
