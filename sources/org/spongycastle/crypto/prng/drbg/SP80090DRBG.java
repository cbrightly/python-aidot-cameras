package org.spongycastle.crypto.prng.drbg;

public interface SP80090DRBG {
    int a(byte[] bArr, byte[] bArr2, boolean z);

    void b(byte[] bArr);
}
