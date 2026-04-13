package org.spongycastle.crypto.tls;

public class AbstractTlsCipherFactory implements TlsCipherFactory {
    public TlsCipher a(TlsContext context, int encryptionAlgorithm, int macAlgorithm) {
        throw new TlsFatalAlert(80);
    }
}
