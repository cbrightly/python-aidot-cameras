package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public class DefaultTlsSignerCredentials extends AbstractTlsSignerCredentials {
    protected TlsContext a;
    protected Certificate b;
    protected AsymmetricKeyParameter c;
    protected SignatureAndHashAlgorithm d;
    protected TlsSigner e;

    public Certificate e() {
        return this.b;
    }

    public byte[] d(byte[] hash) {
        try {
            if (TlsUtils.U(this.a)) {
                return this.e.f(this.d, this.c, hash);
            }
            return this.e.c(this.c, hash);
        } catch (CryptoException e2) {
            throw new TlsFatalAlert(80, e2);
        }
    }

    public SignatureAndHashAlgorithm c() {
        return this.d;
    }
}
