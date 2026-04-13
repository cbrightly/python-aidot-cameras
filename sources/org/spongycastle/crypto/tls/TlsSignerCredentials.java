package org.spongycastle.crypto.tls;

public interface TlsSignerCredentials extends TlsCredentials {
    SignatureAndHashAlgorithm c();

    byte[] d(byte[] bArr);
}
