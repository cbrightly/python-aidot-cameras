package org.spongycastle.jcajce.provider.asymmetric.util;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;

public class KeyUtil {
    public static byte[] c(AlgorithmIdentifier algId, ASN1Encodable keyData) {
        try {
            return d(new SubjectPublicKeyInfo(algId, keyData));
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] d(SubjectPublicKeyInfo info) {
        try {
            return info.getEncoded("DER");
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] b(AlgorithmIdentifier algId, ASN1Encodable privKey) {
        try {
            return a(new PrivateKeyInfo(algId, privKey.toASN1Primitive()));
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] a(PrivateKeyInfo info) {
        try {
            return info.getEncoded("DER");
        } catch (Exception e) {
            return null;
        }
    }
}
