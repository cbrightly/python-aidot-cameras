package org.glassfish.grizzly.ssl;

public interface SSLSupport {
    public static final String CERTIFICATE_KEY = "jakarta.servlet.request.X509Certificate";
    public static final String CIPHER_SUITE_KEY = "jakarta.servlet.request.cipher_suite";
    public static final String KEY_SIZE_KEY = "jakarta.servlet.request.key_size";
    public static final String SESSION_ID_KEY = "jakarta.servlet.request.ssl_session_id";

    String getCipherSuite();

    Integer getKeySize();

    Object[] getPeerCertificateChain();

    Object[] getPeerCertificateChain(boolean z);

    String getSessionId();

    public static final class CipherData {
        public int keySize = 0;
        public String phrase = null;

        public CipherData(String phrase2, int keySize2) {
            this.phrase = phrase2;
            this.keySize = keySize2;
        }
    }
}
