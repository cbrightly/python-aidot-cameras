package org.spongycastle.jcajce.provider.asymmetric.util;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.dh.BCDHPublicKey;

public class DHUtil {
    public static AsymmetricKeyParameter b(PublicKey key) {
        if (key instanceof BCDHPublicKey) {
            return ((BCDHPublicKey) key).engineGetKeyParameters();
        }
        if (key instanceof DHPublicKey) {
            DHPublicKey k = (DHPublicKey) key;
            return new DHPublicKeyParameters(k.getY(), new DHParameters(k.getParams().getP(), k.getParams().getG(), (BigInteger) null, k.getParams().getL()));
        }
        throw new InvalidKeyException("can't identify DH public key.");
    }

    public static AsymmetricKeyParameter a(PrivateKey key) {
        if (key instanceof DHPrivateKey) {
            DHPrivateKey k = (DHPrivateKey) key;
            return new DHPrivateKeyParameters(k.getX(), new DHParameters(k.getParams().getP(), k.getParams().getG(), (BigInteger) null, k.getParams().getL()));
        }
        throw new InvalidKeyException("can't identify DH private key.");
    }
}
