package org.spongycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

public class DEROutputStream extends ASN1OutputStream {
    public DEROutputStream(OutputStream os) {
        super(os);
    }

    public void j(ASN1Encodable obj) {
        if (obj != null) {
            obj.toASN1Primitive().k().f(this);
            return;
        }
        throw new IOException("null object detected");
    }

    /* access modifiers changed from: package-private */
    public ASN1OutputStream a() {
        return this;
    }

    /* access modifiers changed from: package-private */
    public ASN1OutputStream b() {
        return this;
    }
}
