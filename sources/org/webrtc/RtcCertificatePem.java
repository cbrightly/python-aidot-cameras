package org.webrtc;

import org.webrtc.PeerConnection;

public class RtcCertificatePem {
    private static final long DEFAULT_EXPIRY = 2592000;
    public final String certificate;
    public final String privateKey;

    private static native RtcCertificatePem nativeGenerateCertificate(PeerConnection.KeyType keyType, long j);

    @CalledByNative
    public RtcCertificatePem(String privateKey2, String certificate2) {
        this.privateKey = privateKey2;
        this.certificate = certificate2;
    }

    /* access modifiers changed from: package-private */
    @CalledByNative
    public String getPrivateKey() {
        return this.privateKey;
    }

    /* access modifiers changed from: package-private */
    @CalledByNative
    public String getCertificate() {
        return this.certificate;
    }

    public static RtcCertificatePem generateCertificate() {
        return nativeGenerateCertificate(PeerConnection.KeyType.ECDSA, DEFAULT_EXPIRY);
    }

    public static RtcCertificatePem generateCertificate(PeerConnection.KeyType keyType) {
        return nativeGenerateCertificate(keyType, DEFAULT_EXPIRY);
    }

    public static RtcCertificatePem generateCertificate(long expires) {
        return nativeGenerateCertificate(PeerConnection.KeyType.ECDSA, expires);
    }

    public static RtcCertificatePem generateCertificate(PeerConnection.KeyType keyType, long expires) {
        return nativeGenerateCertificate(keyType, expires);
    }
}
