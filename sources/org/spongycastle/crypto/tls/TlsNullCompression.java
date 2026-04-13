package org.spongycastle.crypto.tls;

import java.io.OutputStream;

public class TlsNullCompression implements TlsCompression {
    public OutputStream b(OutputStream output) {
        return output;
    }

    public OutputStream a(OutputStream output) {
        return output;
    }
}
