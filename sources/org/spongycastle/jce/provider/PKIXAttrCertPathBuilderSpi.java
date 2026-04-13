package org.spongycastle.jce.provider;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.Principal;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidator;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.jcajce.PKIXCertStoreSelector;
import org.spongycastle.jcajce.PKIXExtendedBuilderParameters;
import org.spongycastle.jce.exception.ExtCertPathBuilderException;
import org.spongycastle.util.Store;
import org.spongycastle.util.StoreException;
import org.spongycastle.x509.ExtendedPKIXBuilderParameters;
import org.spongycastle.x509.ExtendedPKIXParameters;
import org.spongycastle.x509.X509AttributeCertStoreSelector;
import org.spongycastle.x509.X509AttributeCertificate;
import org.spongycastle.x509.X509CertStoreSelector;

public class PKIXAttrCertPathBuilderSpi extends CertPathBuilderSpi {
    private Exception a;

    public CertPathBuilderResult engineBuild(CertPathParameters params) {
        PKIXExtendedBuilderParameters paramsPKIX;
        List targetStores;
        CertPathParameters certPathParameters = params;
        if ((certPathParameters instanceof PKIXBuilderParameters) || (certPathParameters instanceof ExtendedPKIXBuilderParameters) || (certPathParameters instanceof PKIXExtendedBuilderParameters)) {
            List targetStores2 = new ArrayList();
            if (certPathParameters instanceof PKIXBuilderParameters) {
                PKIXExtendedBuilderParameters.Builder paramsPKIXBldr = new PKIXExtendedBuilderParameters.Builder((PKIXBuilderParameters) certPathParameters);
                if (certPathParameters instanceof ExtendedPKIXParameters) {
                    ExtendedPKIXBuilderParameters extPKIX = (ExtendedPKIXBuilderParameters) certPathParameters;
                    paramsPKIXBldr.d(extPKIX.l());
                    paramsPKIXBldr.f(extPKIX.m());
                    targetStores2 = extPKIX.e();
                }
                paramsPKIX = paramsPKIXBldr.e();
                targetStores = targetStores2;
            } else {
                paramsPKIX = (PKIXExtendedBuilderParameters) certPathParameters;
                targetStores = targetStores2;
            }
            List certPathList = new ArrayList();
            PKIXCertStoreSelector t = paramsPKIX.a().t();
            if (t instanceof X509AttributeCertStoreSelector) {
                try {
                    Collection targets = b((X509AttributeCertStoreSelector) t, targetStores);
                    if (!targets.isEmpty()) {
                        Iterator targetIter = targets.iterator();
                        CertPathBuilderResult result = null;
                        while (targetIter.hasNext() && result == null) {
                            X509AttributeCertificate cert = (X509AttributeCertificate) targetIter.next();
                            X509CertStoreSelector selector = new X509CertStoreSelector();
                            Principal[] principals = cert.c().b();
                            Set issuers = new HashSet();
                            int i = 0;
                            while (i < principals.length) {
                                try {
                                    if (principals[i] instanceof X500Principal) {
                                        selector.setSubject(((X500Principal) principals[i]).getEncoded());
                                    }
                                    PKIXCertStoreSelector certStoreSelector = new PKIXCertStoreSelector.Builder(selector).a();
                                    issuers.addAll(CertPathValidatorUtilities.b(certStoreSelector, paramsPKIX.a().m()));
                                    issuers.addAll(CertPathValidatorUtilities.b(certStoreSelector, paramsPKIX.a().n()));
                                    i++;
                                } catch (AnnotatedException e) {
                                    throw new ExtCertPathBuilderException("Public key certificate for attribute certificate cannot be searched.", e);
                                } catch (IOException e2) {
                                    throw new ExtCertPathBuilderException("cannot encode X500Principal.", e2);
                                }
                            }
                            if (!issuers.isEmpty()) {
                                Iterator it = issuers.iterator();
                                while (it.hasNext() && result == null) {
                                    result = a(cert, (X509Certificate) it.next(), paramsPKIX, certPathList);
                                }
                                CertPathParameters certPathParameters2 = params;
                            } else {
                                throw new CertPathBuilderException("Public key certificate for attribute certificate cannot be found.");
                            }
                        }
                        if (result == null && this.a != null) {
                            throw new ExtCertPathBuilderException("Possible certificate chain could not be validated.", this.a);
                        } else if (result != null || this.a != null) {
                            return result;
                        } else {
                            throw new CertPathBuilderException("Unable to find certificate chain.");
                        }
                    } else {
                        throw new CertPathBuilderException("No attribute certificate found matching targetContraints.");
                    }
                } catch (AnnotatedException e3) {
                    throw new ExtCertPathBuilderException("Error finding target attribute certificate.", e3);
                }
            } else {
                throw new CertPathBuilderException("TargetConstraints must be an instance of " + X509AttributeCertStoreSelector.class.getName() + " for " + getClass().getName() + " class.");
            }
        } else {
            throw new InvalidAlgorithmParameterException("Parameters must be an instance of " + PKIXBuilderParameters.class.getName() + " or " + PKIXExtendedBuilderParameters.class.getName() + ".");
        }
    }

    private CertPathBuilderResult a(X509AttributeCertificate attrCert, X509Certificate tbvCert, PKIXExtendedBuilderParameters pkixParams, List tbvPath) {
        if (tbvPath.contains(tbvCert) || pkixParams.b().contains(tbvCert)) {
            return null;
        }
        if (pkixParams.c() != -1 && tbvPath.size() - 1 > pkixParams.c()) {
            return null;
        }
        tbvPath.add(tbvCert);
        CertPathBuilderResult builderResult = null;
        try {
            CertificateFactory cFact = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
            CertPathValidator validator = CertPathValidator.getInstance("RFC3281", BouncyCastleProvider.PROVIDER_NAME);
            try {
                if (CertPathValidatorUtilities.u(tbvCert, pkixParams.a().u(), pkixParams.a().s())) {
                    CertPath certPath = cFact.generateCertPath(tbvPath);
                    PKIXCertPathValidatorResult result = (PKIXCertPathValidatorResult) validator.validate(certPath, pkixParams);
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
                        X509Certificate issuer = (X509Certificate) it.next();
                        if (!issuer.getIssuerX500Principal().equals(issuer.getSubjectX500Principal())) {
                            builderResult = a(attrCert, issuer, pkixParams, tbvPath);
                        }
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
                this.a = new AnnotatedException("No valid certification path could be build.", e5);
            }
        } catch (Exception e6) {
            throw new RuntimeException("Exception creating support classes.");
        }
    }

    protected static Collection b(X509AttributeCertStoreSelector certSelect, List certStores) {
        Set certs = new HashSet();
        for (Object obj : certStores) {
            if (obj instanceof Store) {
                try {
                    certs.addAll(((Store) obj).a(certSelect));
                } catch (StoreException e) {
                    throw new AnnotatedException("Problem while picking certificates from X.509 store.", e);
                }
            }
        }
        return certs;
    }
}
