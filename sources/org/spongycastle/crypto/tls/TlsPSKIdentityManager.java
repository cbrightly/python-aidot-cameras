package org.spongycastle.crypto.tls;

public interface TlsPSKIdentityManager {
    byte[] a(byte[] bArr);

    byte[] getHint();
}
