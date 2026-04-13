package org.spongycastle.crypto.tls;

import org.spongycastle.util.Arrays;

public class TlsSessionImpl implements TlsSession {
    final byte[] a;
    SessionParameters b;

    TlsSessionImpl(byte[] sessionID, SessionParameters sessionParameters) {
        if (sessionID == null) {
            throw new IllegalArgumentException("'sessionID' cannot be null");
        } else if (sessionID.length < 1 || sessionID.length > 32) {
            throw new IllegalArgumentException("'sessionID' must have length between 1 and 32 bytes, inclusive");
        } else {
            this.a = Arrays.h(sessionID);
            this.b = sessionParameters;
        }
    }

    public synchronized byte[] a() {
        return this.a;
    }

    public synchronized void invalidate() {
        SessionParameters sessionParameters = this.b;
        if (sessionParameters != null) {
            sessionParameters.a();
            this.b = null;
        }
    }
}
