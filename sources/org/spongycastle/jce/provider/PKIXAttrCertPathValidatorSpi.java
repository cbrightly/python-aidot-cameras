package org.spongycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.PKIXParameters;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.spongycastle.jcajce.PKIXCertStoreSelector;
import org.spongycastle.jcajce.PKIXExtendedParameters;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.exception.ExtCertPathValidatorException;
import org.spongycastle.x509.ExtendedPKIXParameters;
import org.spongycastle.x509.X509AttributeCertStoreSelector;
import org.spongycastle.x509.X509AttributeCertificate;

public class PKIXAttrCertPathValidatorSpi extends CertPathValidatorSpi {
    private final JcaJceHelper a = new BCJcaJceHelper();

    public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters params) {
        PKIXExtendedParameters paramsPKIX;
        Set necessaryACAttributes;
        Set necessaryACAttributes2;
        Set prohibitedACAttrbiutes;
        CertPath certPath2 = certPath;
        CertPathParameters certPathParameters = params;
        if ((certPathParameters instanceof ExtendedPKIXParameters) || (certPathParameters instanceof PKIXExtendedParameters)) {
            Set attrCertCheckers = new HashSet();
            Set prohibitedACAttrbiutes2 = new HashSet();
            Set necessaryACAttributes3 = new HashSet();
            Set trustedACIssuers = new HashSet();
            if (certPathParameters instanceof PKIXParameters) {
                PKIXExtendedParameters.Builder paramsPKIXBldr = new PKIXExtendedParameters.Builder((PKIXParameters) certPathParameters);
                if (certPathParameters instanceof ExtendedPKIXParameters) {
                    ExtendedPKIXParameters extPKIX = (ExtendedPKIXParameters) certPathParameters;
                    paramsPKIXBldr.r(extPKIX.i());
                    paramsPKIXBldr.s(extPKIX.h());
                    attrCertCheckers = extPKIX.b();
                    prohibitedACAttrbiutes2 = extPKIX.d();
                    necessaryACAttributes3 = extPKIX.c();
                }
                paramsPKIX = paramsPKIXBldr.n();
                necessaryACAttributes = necessaryACAttributes3;
                necessaryACAttributes2 = prohibitedACAttrbiutes2;
                prohibitedACAttrbiutes = attrCertCheckers;
            } else {
                paramsPKIX = (PKIXExtendedParameters) certPathParameters;
                necessaryACAttributes = necessaryACAttributes3;
                necessaryACAttributes2 = prohibitedACAttrbiutes2;
                prohibitedACAttrbiutes = attrCertCheckers;
            }
            PKIXCertStoreSelector t = paramsPKIX.t();
            if (t instanceof X509AttributeCertStoreSelector) {
                X509AttributeCertificate attrCert = ((X509AttributeCertStoreSelector) t).a();
                CertPath holderCertPath = RFC3281CertPathUtilities.d(attrCert, paramsPKIX);
                CertPathValidatorResult result = RFC3281CertPathUtilities.e(certPath2, paramsPKIX);
                X509Certificate issuerCert = (X509Certificate) certPath.getCertificates().get(0);
                RFC3281CertPathUtilities.f(issuerCert, paramsPKIX);
                RFC3281CertPathUtilities.g(issuerCert, trustedACIssuers);
                RFC3281CertPathUtilities.h(attrCert, paramsPKIX);
                RFC3281CertPathUtilities.i(attrCert, certPath2, holderCertPath, paramsPKIX, prohibitedACAttrbiutes);
                RFC3281CertPathUtilities.a(attrCert, necessaryACAttributes2, necessaryACAttributes);
                try {
                    Date date = CertPathValidatorUtilities.q(paramsPKIX, (CertPath) null, -1);
                    X509Certificate x509Certificate = issuerCert;
                    CertPath certPath3 = holderCertPath;
                    RFC3281CertPathUtilities.c(attrCert, paramsPKIX, issuerCert, date, certPath.getCertificates(), this.a);
                    return result;
                } catch (AnnotatedException e) {
                    X509Certificate x509Certificate2 = issuerCert;
                    CertPath certPath4 = holderCertPath;
                    throw new ExtCertPathValidatorException("Could not get validity date from attribute certificate.", e);
                }
            } else {
                throw new InvalidAlgorithmParameterException("TargetConstraints must be an instance of " + X509AttributeCertStoreSelector.class.getName() + " for " + getClass().getName() + " class.");
            }
        } else {
            throw new InvalidAlgorithmParameterException("Parameters must be a " + ExtendedPKIXParameters.class.getName() + " instance.");
        }
    }
}
