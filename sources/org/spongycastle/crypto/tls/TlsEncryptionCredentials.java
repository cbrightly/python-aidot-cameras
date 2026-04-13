package org.spongycastle.crypto.tls;

public interface TlsEncryptionCredentials extends TlsCredentials {
    byte[] b(byte[] bArr);
}
