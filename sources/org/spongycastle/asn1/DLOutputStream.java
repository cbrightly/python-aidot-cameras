package org.spongycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

public class DLOutputStream extends ASN1OutputStream {
    public DLOutputStream(OutputStream os) {
        super(os);
    }

    public void j(ASN1Encodable obj) {
        if (obj != null) {
            obj.toASN1Primitive().n().f(this);
            return;
        }
        throw new IOException("null object detected");
    }
}
