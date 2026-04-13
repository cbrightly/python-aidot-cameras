package org.spongycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

public class LazyConstructionEnumeration implements Enumeration {
    private ASN1InputStream a;
    private Object b = a();

    public LazyConstructionEnumeration(byte[] encoded) {
        this.a = new ASN1InputStream(encoded, true);
    }

    public boolean hasMoreElements() {
        return this.b != null;
    }

    public Object nextElement() {
        Object o = this.b;
        this.b = a();
        return o;
    }

    private Object a() {
        try {
            return this.a.r();
        } catch (IOException e) {
            throw new ASN1ParsingException("malformed DER construction: " + e, e);
        }
    }
}
