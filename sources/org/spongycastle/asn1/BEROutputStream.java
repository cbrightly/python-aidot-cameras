package org.spongycastle.asn1;

import java.io.OutputStream;

public class BEROutputStream extends DEROutputStream {
    public BEROutputStream(OutputStream os) {
        super(os);
    }
}
