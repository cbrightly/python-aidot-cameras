package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.Digest;

public interface TlsHandshakeHash extends Digest {
    TlsHandshakeHash a();

    Digest f();

    TlsHandshakeHash g();

    void h(short s);

    byte[] i(short s);

    void l();
}
