package org.spongycastle.crypto.agreement;

import java.math.BigInteger;
import org.spongycastle.crypto.BasicAgreement;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.math.ec.ECPoint;

public class ECDHBasicAgreement implements BasicAgreement {
    private ECPrivateKeyParameters a;

    public void a(CipherParameters key) {
        this.a = (ECPrivateKeyParameters) key;
    }

    public int b() {
        return (this.a.b().a().t() + 7) / 8;
    }

    public BigInteger c(CipherParameters pubKey) {
        ECPublicKeyParameters pub2 = (ECPublicKeyParameters) pubKey;
        if (pub2.b().equals(this.a.b())) {
            ECPoint P = pub2.c().w(this.a.c()).y();
            if (!P.t()) {
                return P.f().t();
            }
            throw new IllegalStateException("Infinity is not a valid agreement value for ECDH");
        }
        throw new IllegalStateException("ECDH public key has wrong domain parameters");
    }
}
