package org.spongycastle.jcajce.provider.asymmetric.x509;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.Provider;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PSSParameterSpec;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Null;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.RSASSAPSSparams;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.jcajce.util.MessageDigestUtils;
import org.spongycastle.jce.provider.BouncyCastleProvider;

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
                return a((ASN1ObjectIdentifier) ecDsaParams.r(0)) + "withECDSA";
            }
        }
        Provider prov = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (prov != null) {
            String algName = prov.getProperty("Alg.Alias.Signature." + sigAlgId.e().s());
            if (algName != null) {
                return algName;
            }
        }
        Provider[] provs = Security.getProviders();
        for (int i = 0; i != provs.length; i++) {
            Provider provider = provs[i];
            String algName2 = provider.getProperty("Alg.Alias.Signature." + sigAlgId.e().s());
            if (algName2 != null) {
                return algName2;
            }
        }
        return sigAlgId.e().s();
    }

    private static String a(ASN1ObjectIdentifier digestAlgOID) {
        String name = MessageDigestUtils.a(digestAlgOID);
        int dIndex = name.indexOf(45);
        if (dIndex <= 0 || name.startsWith("SHA3")) {
            return MessageDigestUtils.a(digestAlgOID);
        }
        return name.substring(0, dIndex) + name.substring(dIndex + 1);
    }
}
