package org.spongycastle.crypto.agreement;

import java.math.BigInteger;
import org.spongycastle.crypto.BasicAgreement;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;

public class DHBasicAgreement implements BasicAgreement {
    private static final BigInteger a = BigInteger.valueOf(1);
    private DHPrivateKeyParameters b;
    private DHParameters c;

    public void a(CipherParameters param) {
        AsymmetricKeyParameter kParam;
        if (param instanceof ParametersWithRandom) {
            kParam = (AsymmetricKeyParameter) ((ParametersWithRandom) param).a();
        } else {
            kParam = (AsymmetricKeyParameter) param;
        }
        if (kParam instanceof DHPrivateKeyParameters) {
            DHPrivateKeyParameters dHPrivateKeyParameters = (DHPrivateKeyParameters) kParam;
            this.b = dHPrivateKeyParameters;
            this.c = dHPrivateKeyParameters.b();
            return;
        }
        throw new IllegalArgumentException("DHEngine expects DHPrivateKeyParameters");
    }

    public int b() {
        return (this.b.b().e().bitLength() + 7) / 8;
    }

    public BigInteger c(CipherParameters pubKey) {
        DHPublicKeyParameters pub2 = (DHPublicKeyParameters) pubKey;
        if (pub2.b().equals(this.c)) {
            BigInteger p = this.c.e();
            BigInteger peerY = pub2.c();
            if (peerY != null) {
                BigInteger bigInteger = a;
                if (peerY.compareTo(bigInteger) > 0 && peerY.compareTo(p.subtract(bigInteger)) < 0) {
                    BigInteger result = peerY.modPow(this.b.c(), p);
                    if (!result.equals(bigInteger)) {
                        return result;
                    }
                    throw new IllegalStateException("Shared key can't be 1");
                }
            }
            throw new IllegalArgumentException("Diffie-Hellman public key is weak");
        }
        throw new IllegalArgumentException("Diffie-Hellman public key has wrong parameters.");
    }
}
