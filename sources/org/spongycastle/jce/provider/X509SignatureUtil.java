package org.spongycastle.jce.provider;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PSSParameterSpec;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Null;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.RSASSAPSSparams;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;

public class X509SignatureUtil {
    private static final ASN1Null a = DERNull.c;

    X509SignatureUtil() {
    }

    static void c(Signature signature, ASN1Encodable params) {
        if (params != null && !a.equals(params)) {
            AlgorithmParameters sigParams = AlgorithmParameters.getInstance(signature.getAlgorithm(), signature.getProvider());
            try {
                sigParams.init(params.toASN1Primitive().getEncoded());
                if (signature.getAlgorithm().endsWith("MGF1")) {
                    try {
                        signature.setParameter(sigParams.getParameterSpec(PSSParameterSpec.class));
                    } catch (GeneralSecurityException e) {
                        throw new SignatureException("Exception extracting parameters: " + e.getMessage());
                    }
                }
            } catch (IOException e2) {
                throw new SignatureException("IOException decoding parameters: " + e2.getMessage());
            }
        }
    }

    static String b(AlgorithmIdentifier sigAlgId) {
        ASN1Encodable params = sigAlgId.h();
        if (params != null && !a.equals(params)) {
            if (sigAlgId.e().equals(PKCSObjectIdentifiers.T)) {
                RSASSAPSSparams rsaParams = RSASSAPSSparams.f(params);
                return a(rsaParams.e().e()) + "withRSAandMGF1";
            } else if (sigAlgId.e().equals(X9ObjectIdentifiers.u3)) {
                ASN1Sequence ecDsaParams = ASN1Sequence.o(params);
                return a(ASN1ObjectIdentifier.t(ecDsaParams.r(0))) + "withECDSA";
            }
        }
        return sigAlgId.e().s();
    }

    private static String a(ASN1ObjectIdentifier digestAlgOID) {
        if (PKCSObjectIdentifiers.t0.equals(digestAlgOID)) {
            return "MD5";
        }
        if (OIWObjectIdentifiers.i.equals(digestAlgOID)) {
            return "SHA1";
        }
        if (NISTObjectIdentifiers.f.equals(digestAlgOID)) {
            return "SHA224";
        }
        if (NISTObjectIdentifiers.c.equals(digestAlgOID)) {
            return "SHA256";
        }
        if (NISTObjectIdentifiers.d.equals(digestAlgOID)) {
            return "SHA384";
        }
        if (NISTObjectIdentifiers.e.equals(digestAlgOID)) {
            return "SHA512";
        }
        if (TeleTrusTObjectIdentifiers.c.equals(digestAlgOID)) {
            return "RIPEMD128";
        }
        if (TeleTrusTObjectIdentifiers.b.equals(digestAlgOID)) {
            return "RIPEMD160";
        }
        if (TeleTrusTObjectIdentifiers.d.equals(digestAlgOID)) {
            return "RIPEMD256";
        }
        if (CryptoProObjectIdentifiers.b.equals(digestAlgOID)) {
            return "GOST3411";
        }
        return digestAlgOID.s();
    }
}
