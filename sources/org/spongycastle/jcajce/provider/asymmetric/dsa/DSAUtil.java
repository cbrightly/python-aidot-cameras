package org.spongycastle.jcajce.provider.asymmetric.dsa;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Fingerprint;

public class DSAUtil {
    public static final ASN1ObjectIdentifier[] a = {X9ObjectIdentifiers.d4, OIWObjectIdentifiers.j};

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

    static DSAParameters e(DSAParams spec) {
        if (spec != null) {
            return new DSAParameters(spec.getP(), spec.getQ(), spec.getG());
        }
        return null;
    }

    public static AsymmetricKeyParameter c(PublicKey key) {
        if (key instanceof BCDSAPublicKey) {
            return ((BCDSAPublicKey) key).engineGetKeyParameters();
        }
        if (key instanceof DSAPublicKey) {
            return new BCDSAPublicKey((DSAPublicKey) key).engineGetKeyParameters();
        }
        try {
            return new BCDSAPublicKey(SubjectPublicKeyInfo.g(key.getEncoded())).engineGetKeyParameters();
        } catch (Exception e) {
            throw new InvalidKeyException("can't identify DSA public key: " + key.getClass().getName());
        }
    }

    public static AsymmetricKeyParameter b(PrivateKey key) {
        if (key instanceof DSAPrivateKey) {
            DSAPrivateKey k = (DSAPrivateKey) key;
            return new DSAPrivateKeyParameters(k.getX(), new DSAParameters(k.getParams().getP(), k.getParams().getQ(), k.getParams().getG()));
        }
        throw new InvalidKeyException("can't identify DSA private key.");
    }

    static String a(BigInteger y, DSAParams params) {
        return new Fingerprint(Arrays.t(y.toByteArray(), params.getP().toByteArray(), params.getQ().toByteArray(), params.getG().toByteArray())).toString();
    }
}
