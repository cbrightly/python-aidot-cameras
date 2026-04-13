package org.spongycastle.jcajce.provider.asymmetric.elgamal;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ElGamalParameters;
import org.spongycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.spongycastle.crypto.params.ElGamalPublicKeyParameters;
import org.spongycastle.jce.interfaces.ElGamalPrivateKey;
import org.spongycastle.jce.interfaces.ElGamalPublicKey;

public class ElGamalUtil {
    public static AsymmetricKeyParameter b(PublicKey key) {
        if (key instanceof ElGamalPublicKey) {
            ElGamalPublicKey k = (ElGamalPublicKey) key;
            return new ElGamalPublicKeyParameters(k.getY(), new ElGamalParameters(k.getParameters().b(), k.getParameters().a()));
        } else if (key instanceof DHPublicKey) {
            DHPublicKey k2 = (DHPublicKey) key;
            return new ElGamalPublicKeyParameters(k2.getY(), new ElGamalParameters(k2.getParams().getP(), k2.getParams().getG()));
        } else {
            throw new InvalidKeyException("can't identify public key for El Gamal.");
        }
    }

    public static AsymmetricKeyParameter a(PrivateKey key) {
        if (key instanceof ElGamalPrivateKey) {
            ElGamalPrivateKey k = (ElGamalPrivateKey) key;
            return new ElGamalPrivateKeyParameters(k.getX(), new ElGamalParameters(k.getParameters().b(), k.getParameters().a()));
        } else if (key instanceof DHPrivateKey) {
            DHPrivateKey k2 = (DHPrivateKey) key;
            return new ElGamalPrivateKeyParameters(k2.getX(), new ElGamalParameters(k2.getParams().getP(), k2.getParams().getG()));
        } else {
            throw new InvalidKeyException("can't identify private key for El Gamal.");
        }
    }
}
