package org.spongycastle.crypto.tls;

import java.math.BigInteger;
import org.spongycastle.crypto.BasicAgreement;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.util.BigIntegers;

public class DefaultTlsAgreementCredentials extends AbstractTlsAgreementCredentials {
    protected Certificate a;
    protected AsymmetricKeyParameter b;
    protected BasicAgreement c;
    protected boolean d;

    public Certificate e() {
        return this.a;
    }

    public byte[] a(AsymmetricKeyParameter peerPublicKey) {
        this.c.a(this.b);
        BigInteger agreementValue = this.c.c(peerPublicKey);
        if (this.d) {
            return BigIntegers.b(agreementValue);
        }
        return BigIntegers.a(this.c.b(), agreementValue);
    }
}
