package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.RSAKeyParameters;

public class DefaultTlsEncryptionCredentials extends AbstractTlsEncryptionCredentials {
    protected TlsContext a;
    protected Certificate b;
    protected AsymmetricKeyParameter c;

    public Certificate e() {
        return this.b;
    }

    public byte[] b(byte[] encryptedPreMasterSecret) {
        return TlsRSAUtils.b(this.a, (RSAKeyParameters) this.c, encryptedPreMasterSecret);
    }
}
