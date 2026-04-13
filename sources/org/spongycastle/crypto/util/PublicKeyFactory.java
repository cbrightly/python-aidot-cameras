package org.spongycastle.crypto.util;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.oiw.ElGamalParameter;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.DHParameter;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.RSAPublicKey;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DSAParameter;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.asn1.x9.DHPublicKey;
import org.spongycastle.asn1.x9.DomainParameters;
import org.spongycastle.asn1.x9.ECNamedCurveTable;
import org.spongycastle.asn1.x9.ValidationParams;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.ec.CustomNamedCurves;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.crypto.params.DHValidationParameters;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECNamedDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.ElGamalParameters;
import org.spongycastle.crypto.params.ElGamalPublicKeyParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;

public class PublicKeyFactory {
    public static AsymmetricKeyParameter a(SubjectPublicKeyInfo keyInfo) {
        X9ECParameters x9;
        ECDomainParameters dParams;
        BigInteger j;
        AlgorithmIdentifier algId = keyInfo.e();
        int l = 0;
        if (algId.e().equals(PKCSObjectIdentifiers.K) || algId.e().equals(X509ObjectIdentifiers.O2)) {
            RSAPublicKey pubKey = RSAPublicKey.e(keyInfo.i());
            return new RSAKeyParameters(false, pubKey.f(), pubKey.g());
        } else if (algId.e().equals(X9ObjectIdentifiers.k4)) {
            DHPublicKey dhPublicKey = DHPublicKey.e(keyInfo.i());
            BigInteger y = dhPublicKey.f();
            DomainParameters dhParams = DomainParameters.f(algId.h());
            BigInteger p = dhParams.i();
            BigInteger g = dhParams.e();
            BigInteger q = dhParams.k();
            if (dhParams.g() != null) {
                j = dhParams.g();
            } else {
                j = null;
            }
            ValidationParams dhValidationParms = dhParams.n();
            DHPublicKey dHPublicKey = dhPublicKey;
            DHParameters dHParameters = r4;
            DomainParameters domainParameters = dhParams;
            DHParameters dHParameters2 = new DHParameters(p, g, q, j, dhValidationParms != null ? new DHValidationParameters(dhValidationParms.getSeed(), dhValidationParms.getPgenCounter().intValue()) : null);
            return new DHPublicKeyParameters(y, dHParameters);
        } else if (algId.e().equals(PKCSObjectIdentifiers.b0)) {
            DHParameter params = DHParameter.f(algId.h());
            ASN1Integer derY = (ASN1Integer) keyInfo.i();
            BigInteger lVal = params.g();
            if (lVal != null) {
                l = lVal.intValue();
            }
            return new DHPublicKeyParameters(derY.r(), new DHParameters(params.h(), params.e(), (BigInteger) null, l));
        } else if (algId.e().equals(OIWObjectIdentifiers.l)) {
            ElGamalParameter params2 = ElGamalParameter.f(algId.h());
            return new ElGamalPublicKeyParameters(((ASN1Integer) keyInfo.i()).r(), new ElGamalParameters(params2.g(), params2.e()));
        } else if (algId.e().equals(X9ObjectIdentifiers.d4) || algId.e().equals(OIWObjectIdentifiers.j)) {
            ASN1Integer derY2 = (ASN1Integer) keyInfo.i();
            ASN1Encodable de = algId.h();
            DSAParameters parameters = null;
            if (de != null) {
                DSAParameter params3 = DSAParameter.f(de.toASN1Primitive());
                parameters = new DSAParameters(params3.g(), params3.h(), params3.e());
            }
            return new DSAPublicKeyParameters(derY2.r(), parameters);
        } else if (algId.e().equals(X9ObjectIdentifiers.t3)) {
            X962Parameters params4 = X962Parameters.e(algId.h());
            if (params4.isNamedCurve()) {
                ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) params4.f();
                X9ECParameters x92 = CustomNamedCurves.i(oid);
                if (x92 == null) {
                    x9 = ECNamedCurveTable.c(oid);
                } else {
                    x9 = x92;
                }
                dParams = new ECNamedDomainParameters(oid, x9.e(), x9.f(), x9.i(), x9.g(), x9.getSeed());
            } else {
                x9 = X9ECParameters.h(params4.f());
                dParams = new ECDomainParameters(x9.e(), x9.f(), x9.i(), x9.g(), x9.getSeed());
            }
            return new ECPublicKeyParameters(new X9ECPoint(x9.e(), new DEROctetString(keyInfo.h().q())).e(), dParams);
        } else {
            throw new RuntimeException("algorithm identifier in key not recognised");
        }
    }
}
