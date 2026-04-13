package org.spongycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import org.spongycastle.crypto.Digest;

public class DigestInputBuffer extends ByteArrayOutputStream {
    DigestInputBuffer() {
    }

    /* access modifiers changed from: package-private */
    public void a(Digest d) {
        d.update(this.buf, 0, this.count);
    }
}
