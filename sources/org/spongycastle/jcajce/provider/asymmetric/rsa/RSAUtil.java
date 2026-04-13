package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Fingerprint;

public class RSAUtil {
    public static final ASN1ObjectIdentifier[] a = {PKCSObjectIdentifiers.K, X509ObjectIdentifiers.O2, PKCSObjectIdentifiers.Q, PKCSObjectIdentifiers.T};

    public static boolean d(ASN1ObjectIdentifier algOid) {
        int i = 0;
        while (true) {
            ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr = a;
            if (i == aSN1ObjectIdentifierArr.length) {
                return false;
            }
            if (algOid.equals(aSN1ObjectIdentifierArr[i])) {
                return true;
            }
            i++;
        }
    }

    static RSAKeyParameters c(RSAPublicKey key) {
        return new RSAKeyParameters(false, key.getModulus(), key.getPublicExponent());
    }

    static RSAKeyParameters b(RSAPrivateKey key) {
        if (key instanceof RSAPrivateCrtKey) {
            RSAPrivateCrtKey k = (RSAPrivateCrtKey) key;
            return new RSAPrivateCrtKeyParameters(k.getModulus(), k.getPublicExponent(), k.getPrivateExponent(), k.getPrimeP(), k.getPrimeQ(), k.getPrimeExponentP(), k.getPrimeExponentQ(), k.getCrtCoefficient());
        }
        RSAPrivateKey k2 = key;
        return new RSAKeyParameters(true, k2.getModulus(), k2.getPrivateExponent());
    }

    static String a(BigInteger modulus, BigInteger publicExponent) {
        return new Fingerprint(Arrays.r(modulus.toByteArray(), publicExponent.toByteArray())).toString();
    }
}
