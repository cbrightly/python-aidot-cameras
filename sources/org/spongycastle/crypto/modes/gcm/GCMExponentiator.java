package org.spongycastle.crypto.modes.gcm;

public interface GCMExponentiator {
    void a(byte[] bArr);

    void b(long j, byte[] bArr);
}
