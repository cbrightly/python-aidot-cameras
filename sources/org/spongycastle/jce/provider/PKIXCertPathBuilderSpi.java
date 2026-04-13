package org.spongycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathParameters;
import java.security.cert.CertificateParsingException;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.jcajce.PKIXCertStore;
import org.spongycastle.jcajce.PKIXCertStoreSelector;
import org.spongycastle.jcajce.PKIXExtendedBuilderParameters;
import org.spongycastle.jcajce.PKIXExtendedParameters;
import org.spongycastle.jcajce.provider.asymmetric.x509.CertificateFactory;
import org.spongycastle.jce.exception.ExtCertPathBuilderException;
import org.spongycastle.x509.ExtendedPKIXBuilderParameters;
import org.spongycastle.x509.ExtendedPKIXParameters;

public class PKIXCertPathBuilderSpi extends CertPathBuilderSpi {
    private Exception a;

    public CertPathBuilderResult engineBuild(CertPathParameters params) {
        PKIXExtendedBuilderParameters paramsPKIX;
        Exception exc;
        PKIXExtendedBuilderParameters.Builder paramsBldrPKIXBldr;
        if (params instanceof PKIXBuilderParameters) {
            PKIXExtendedParameters.Builder paramsPKIXBldr = new PKIXExtendedParameters.Builder((PKIXParameters) (PKIXBuilderParameters) params);
            if (params instanceof ExtendedPKIXParameters) {
                ExtendedPKIXBuilderParameters extPKIX = (ExtendedPKIXBuilderParameters) params;
                for (PKIXCertStore m : extPKIX.a()) {
                    paramsPKIXBldr.m(m);
                }
                paramsBldrPKIXBldr = new PKIXExtendedBuilderParameters.Builder(paramsPKIXBldr.n());
                paramsBldrPKIXBldr.d(extPKIX.l());
                paramsBldrPKIXBldr.f(extPKIX.m());
            } else {
                paramsBldrPKIXBldr = new PKIXExtendedBuilderParameters.Builder((PKIXBuilderParameters) params);
            }
            paramsPKIX = paramsBldrPKIXBldr.e();
        } else if (params instanceof PKIXExtendedBuilderParameters) {
            paramsPKIX = (PKIXExtendedBuilderParameters) params;
        } else {
            throw new InvalidAlgorithmParameterException("Parameters must be an instance of " + PKIXBuilderParameters.class.getName() + " or " + PKIXExtendedBuilderParameters.class.getName() + ".");
        }
        List certPathList = new ArrayList();
        PKIXCertStoreSelector certSelect = paramsPKIX.a().t();
        try {
            Collection targets = CertPathValidatorUtilities.b(certSelect, paramsPKIX.a().n());
            targets.addAll(CertPathValidatorUtilities.b(certSelect, paramsPKIX.a().m()));
            if (!targets.isEmpty()) {
                CertPathBuilderResult result = null;
                Iterator targetIter = targets.iterator();
                while (targetIter.hasNext() && result == null) {
                    result = a((X509Certificate) targetIter.next(), paramsPKIX, certPathList);
                }
                if (result != null || (exc = this.a) == null) {
                    if (result != null || this.a != null) {
                        return result;
                    }
                    throw new CertPathBuilderException("Unable to find certificate chain.");
                } else if (exc instanceof AnnotatedException) {
                    throw new CertPathBuilderException(this.a.getMessage(), this.a.getCause());
                } else {
                    throw new CertPathBuilderException("Possible certificate chain could not be validated.", this.a);
                }
            } else {
                throw new CertPathBuilderException("No certificate found matching targetContraints.");
            }
        } catch (AnnotatedException e) {
            throw new ExtCertPathBuilderException("Error finding target certificate.", e);
        }
    }

    /* access modifiers changed from: protected */
    public CertPathBuilderResult a(X509Certificate tbvCert, PKIXExtendedBuilderParameters pkixParams, List tbvPath) {
        if (tbvPath.contains(tbvCert) || pkixParams.b().contains(tbvCert)) {
            return null;
        }
        if (pkixParams.c() != -1 && tbvPath.size() - 1 > pkixParams.c()) {
            return null;
        }
        tbvPath.add(tbvCert);
        CertPathBuilderResult builderResult = null;
        try {
            CertificateFactory cFact = new CertificateFactory();
            PKIXCertPathValidatorSpi validator = new PKIXCertPathValidatorSpi();
            try {
                if (CertPathValidatorUtilities.u(tbvCert, pkixParams.a().u(), pkixParams.a().s())) {
                    CertPath certPath = cFact.engineGenerateCertPath(tbvPath);
                    PKIXCertPathValidatorResult result = (PKIXCertPathValidatorResult) validator.engineValidate(certPath, pkixParams);
                    return new PKIXCertPathBuilderResult(certPath, result.getTrustAnchor(), result.getPolicyTree(), result.getPublicKey());
                }
                List stores = new ArrayList();
                stores.addAll(pkixParams.a().n());
                stores.addAll(CertPathValidatorUtilities.e(tbvCert.getExtensionValue(Extension.y.s()), pkixParams.a().r()));
                Collection issuers = new HashSet();
                issuers.addAll(CertPathValidatorUtilities.c(tbvCert, pkixParams.a().m(), stores));
                if (!issuers.isEmpty()) {
                    Iterator it = issuers.iterator();
                    while (it.hasNext() && builderResult == null) {
                        builderResult = a((X509Certificate) it.next(), pkixParams, tbvPath);
                    }
                    if (builderResult == null) {
                        tbvPath.remove(tbvCert);
                    }
                    return builderResult;
                }
                throw new AnnotatedException("No issuer certificate for certificate in certification path found.");
            } catch (CertificateParsingException e) {
                throw new AnnotatedException("No additional X.509 stores can be added from certificate locations.", e);
            } catch (AnnotatedException e2) {
                throw new AnnotatedException("Cannot find issuer certificate for certificate in certification path.", e2);
            } catch (Exception e3) {
                throw new AnnotatedException("Certification path could not be constructed from certificate list.", e3);
            } catch (Exception e4) {
                throw new AnnotatedException("Certification path could not be validated.", e4);
            } catch (AnnotatedException e5) {
                this.a = e5;
            }
        } catch (Exception e6) {
            throw new RuntimeException("Exception creating support classes.");
        }
    }
}
