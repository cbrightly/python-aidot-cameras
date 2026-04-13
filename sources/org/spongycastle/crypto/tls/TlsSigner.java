package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public interface TlsSigner {
    void a(TlsContext tlsContext);

    boolean b(SignatureAndHashAlgorithm signatureAndHashAlgorithm, byte[] bArr, AsymmetricKeyParameter asymmetricKeyParameter, byte[] bArr2);

    byte[] c(AsymmetricKeyParameter asymmetricKeyParameter, byte[] bArr);

    Signer d(SignatureAndHashAlgorithm signatureAndHashAlgorithm, AsymmetricKeyParameter asymmetricKeyParameter);

    boolean e(AsymmetricKeyParameter asymmetricKeyParameter);

    byte[] f(SignatureAndHashAlgorithm signatureAndHashAlgorithm, AsymmetricKeyParameter asymmetricKeyParameter, byte[] bArr);
}
