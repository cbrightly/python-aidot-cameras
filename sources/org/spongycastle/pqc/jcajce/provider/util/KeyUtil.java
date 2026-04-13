package org.spongycastle.pqc.jcajce.provider.util;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;

public class KeyUtil {
    public static byte[] a(AlgorithmIdentifier algId, ASN1Encodable keyData) {
        try {
            return b(new SubjectPublicKeyInfo(algId, keyData));
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] b(SubjectPublicKeyInfo info) {
        try {
            return info.getEncoded("DER");
        } catch (Exception e) {
            return null;
        }
    }
}
