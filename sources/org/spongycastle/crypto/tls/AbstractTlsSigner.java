package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public abstract class AbstractTlsSigner implements TlsSigner {
    protected TlsContext a;

    public void a(TlsContext context) {
        this.a = context;
    }

    public byte[] c(AsymmetricKeyParameter privateKey, byte[] md5AndSha1) {
        return f((SignatureAndHashAlgorithm) null, privateKey, md5AndSha1);
    }
}
