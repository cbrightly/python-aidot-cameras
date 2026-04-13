package org.spongycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import org.spongycastle.crypto.Signer;

public class SignerInputBuffer extends ByteArrayOutputStream {
    SignerInputBuffer() {
    }

    /* access modifiers changed from: package-private */
    public void a(Signer s) {
        s.update(this.buf, 0, this.count);
    }
}
