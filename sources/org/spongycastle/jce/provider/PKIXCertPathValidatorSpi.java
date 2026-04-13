package org.spongycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathParameters;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPathValidatorSpi;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.PolicyNode;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.TBSCertificate;
import org.spongycastle.jcajce.PKIXExtendedBuilderParameters;
import org.spongycastle.jcajce.PKIXExtendedParameters;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.exception.ExtCertPathValidatorException;
import org.spongycastle.x509.ExtendedPKIXParameters;

public class PKIXCertPathValidatorSpi extends CertPathValidatorSpi {
    private final JcaJceHelper a = new BCJcaJceHelper();

    public CertPathValidatorResult engineValidate(CertPath certPath, CertPathParameters params) {
        PKIXExtendedParameters paramsPKIX;
        List certs;
        CertPath certPath2;
        int explicitPolicy;
        int inhibitAnyPolicy;
        int policyMapping;
        CertPath certPath3;
        PublicKey workingPublicKey;
        X500Name workingIssuerName;
        HashSet hashSet;
        X509Certificate cert;
        int inhibitAnyPolicy2;
        int policyMapping2;
        List[] policyNodes;
        PKIXCertPathValidatorSpi pKIXCertPathValidatorSpi;
        List pathCheckers;
        int inhibitAnyPolicy3;
        int maxPathLength;
        int i;
        Set set;
        PKIXCertPathValidatorSpi pKIXCertPathValidatorSpi2 = this;
        CertPath certPath4 = certPath;
        CertPathParameters certPathParameters = params;
        if (certPathParameters instanceof PKIXParameters) {
            PKIXExtendedParameters.Builder paramsPKIXBldr = new PKIXExtendedParameters.Builder((PKIXParameters) certPathParameters);
            if (certPathParameters instanceof ExtendedPKIXParameters) {
                ExtendedPKIXParameters extPKIX = (ExtendedPKIXParameters) certPathParameters;
                paramsPKIXBldr.r(extPKIX.i());
                paramsPKIXBldr.s(extPKIX.h());
            }
            paramsPKIX = paramsPKIXBldr.n();
        } else if (certPathParameters instanceof PKIXExtendedBuilderParameters) {
            paramsPKIX = ((PKIXExtendedBuilderParameters) certPathParameters).a();
        } else if (certPathParameters instanceof PKIXExtendedParameters) {
            paramsPKIX = (PKIXExtendedParameters) certPathParameters;
        } else {
            CertPath certPath5 = certPath4;
            throw new InvalidAlgorithmParameterException("Parameters must be a " + PKIXParameters.class.getName() + " instance.");
        }
        if (paramsPKIX.u() != null) {
            List certificates = certPath.getCertificates();
            int n = certificates.size();
            Throwable th = null;
            if (!certificates.isEmpty()) {
                Set userInitialPolicySet = paramsPKIX.p();
                try {
                    TrustAnchor trust = CertPathValidatorUtilities.d((X509Certificate) certificates.get(certificates.size() - 1), paramsPKIX.u(), paramsPKIX.s());
                    if (trust != null) {
                        a(trust.getTrustedCert());
                        PKIXExtendedParameters paramsPKIX2 = new PKIXExtendedParameters.Builder(paramsPKIX).q(trust).n();
                        List[] listArr = new ArrayList[(n + 1)];
                        for (int j = 0; j < listArr.length; j++) {
                            listArr[j] = new ArrayList();
                        }
                        Set policySet = new HashSet();
                        policySet.add("2.5.29.32.0");
                        PKIXPolicyNode validPolicyTree = new PKIXPolicyNode(new ArrayList(), 0, policySet, (PolicyNode) null, new HashSet(), "2.5.29.32.0", false);
                        listArr[0].add(validPolicyTree);
                        PKIXNameConstraintValidator nameConstraintValidator = new PKIXNameConstraintValidator();
                        Set hashSet2 = new HashSet();
                        if (paramsPKIX2.y()) {
                            explicitPolicy = 0;
                        } else {
                            explicitPolicy = n + 1;
                        }
                        if (paramsPKIX2.w()) {
                            inhibitAnyPolicy = 0;
                        } else {
                            inhibitAnyPolicy = n + 1;
                        }
                        if (paramsPKIX2.z()) {
                            policyMapping = 0;
                        } else {
                            policyMapping = n + 1;
                        }
                        X509Certificate sign = trust.getTrustedCert();
                        if (sign != null) {
                            try {
                                workingIssuerName = PrincipalUtils.e(sign);
                                workingPublicKey = sign.getPublicKey();
                            } catch (IllegalArgumentException e) {
                                ex = e;
                                HashSet hashSet3 = policySet;
                                PKIXNameConstraintValidator pKIXNameConstraintValidator = nameConstraintValidator;
                                ArrayList[] arrayListArr = listArr;
                                TrustAnchor trustAnchor = trust;
                                int i2 = n;
                                List list = certificates;
                                certPath3 = certPath4;
                                throw new ExtCertPathValidatorException("Subject of trust anchor could not be (re)encoded.", ex, certPath3, -1);
                            }
                        } else {
                            try {
                                workingIssuerName = PrincipalUtils.a(trust);
                                workingPublicKey = trust.getCAPublicKey();
                            } catch (IllegalArgumentException e2) {
                                ex = e2;
                                HashSet hashSet4 = policySet;
                                PKIXNameConstraintValidator pKIXNameConstraintValidator2 = nameConstraintValidator;
                                ArrayList[] arrayListArr2 = listArr;
                                TrustAnchor trustAnchor2 = trust;
                                int i3 = n;
                                List list2 = certificates;
                                certPath3 = certPath4;
                                throw new ExtCertPathValidatorException("Subject of trust anchor could not be (re)encoded.", ex, certPath3, -1);
                            }
                        }
                        try {
                            AlgorithmIdentifier workingAlgId = CertPathValidatorUtilities.g(workingPublicKey);
                            ASN1ObjectIdentifier e3 = workingAlgId.e();
                            ASN1Encodable h = workingAlgId.h();
                            int maxPathLength2 = n;
                            if (paramsPKIX2.t() != null) {
                                if (!paramsPKIX2.t().P0((X509Certificate) certificates.get(0))) {
                                    throw new ExtCertPathValidatorException("Target certificate in certification path does not match targetConstraints.", (Throwable) null, certPath4, 0);
                                }
                            }
                            List<PKIXCertPathChecker> pathCheckers2 = paramsPKIX2.l();
                            for (PKIXCertPathChecker init : pathCheckers2) {
                                init.init(false);
                            }
                            boolean z = false;
                            X509Certificate cert2 = null;
                            boolean z2 = true;
                            int policyMapping3 = policyMapping;
                            int maxPathLength3 = maxPathLength2;
                            Set set2 = hashSet2;
                            int index = certificates.size() - 1;
                            int i4 = explicitPolicy;
                            AlgorithmIdentifier algorithmIdentifier = workingAlgId;
                            int inhibitAnyPolicy4 = inhibitAnyPolicy;
                            PKIXPolicyNode validPolicyTree2 = validPolicyTree;
                            int index2 = i4;
                            while (index >= 0) {
                                int i5 = n - index;
                                X509Certificate cert3 = (X509Certificate) certificates.get(index);
                                boolean z3 = index == certificates.size() + -1 ? z2 : z;
                                List<? extends Certificate> list3 = certificates;
                                PKIXNameConstraintValidator nameConstraintValidator2 = nameConstraintValidator;
                                boolean verificationAlreadyPerformed = z3;
                                try {
                                    a(cert3);
                                    JcaJceHelper jcaJceHelper = pKIXCertPathValidatorSpi2.a;
                                    boolean z4 = z;
                                    int inhibitAnyPolicy5 = inhibitAnyPolicy4;
                                    int explicitPolicy2 = index2;
                                    int index3 = index;
                                    Set policySet2 = policySet;
                                    List[] listArr2 = listArr;
                                    TrustAnchor trust2 = trust;
                                    List pathCheckers3 = pathCheckers2;
                                    RFC3280CertPathUtilities.z(certPath, paramsPKIX2, index3, workingPublicKey, verificationAlreadyPerformed, workingIssuerName, sign, jcaJceHelper);
                                    RFC3280CertPathUtilities.A(certPath4, index, nameConstraintValidator2);
                                    Throwable th2 = th;
                                    int n2 = n;
                                    PKIXNameConstraintValidator nameConstraintValidator3 = nameConstraintValidator2;
                                    List<? extends Certificate> list4 = list3;
                                    X509Certificate cert4 = cert3;
                                    int i6 = i5;
                                    CertPath certPath6 = certPath4;
                                    Set acceptablePolicies = set2;
                                    PKIXPolicyNode validPolicyTree3 = RFC3280CertPathUtilities.C(certPath6, index3, RFC3280CertPathUtilities.B(certPath, index, acceptablePolicies, validPolicyTree2, listArr2, inhibitAnyPolicy5));
                                    RFC3280CertPathUtilities.D(certPath6, index3, validPolicyTree3, explicitPolicy2);
                                    if (i6 != n2) {
                                        if (cert4 != null) {
                                            if (cert4.getVersion() == 1) {
                                                if (i6 != 1 || !cert4.equals(trust2.getTrustedCert())) {
                                                    throw new CertPathValidatorException("Version 1 certificates can't be used as CA ones.", th2, certPath6, index3);
                                                }
                                                pKIXCertPathValidatorSpi = this;
                                                int i7 = i6;
                                                policyMapping2 = policyMapping3;
                                                maxPathLength = maxPathLength3;
                                                inhibitAnyPolicy3 = inhibitAnyPolicy5;
                                                policyNodes = listArr2;
                                                i = explicitPolicy2;
                                                cert = cert4;
                                                pathCheckers = pathCheckers3;
                                            }
                                        }
                                        RFC3280CertPathUtilities.d(certPath6, index3);
                                        int policyMapping4 = policyMapping3;
                                        policyNodes = listArr2;
                                        PKIXPolicyNode validPolicyTree4 = RFC3280CertPathUtilities.c(certPath6, index3, policyNodes, validPolicyTree3, policyMapping4);
                                        RFC3280CertPathUtilities.e(certPath6, index3, nameConstraintValidator3);
                                        int explicitPolicy3 = RFC3280CertPathUtilities.f(certPath6, index3, explicitPolicy2);
                                        int policyMapping5 = RFC3280CertPathUtilities.g(certPath6, index3, policyMapping4);
                                        int inhibitAnyPolicy6 = RFC3280CertPathUtilities.h(certPath6, index3, inhibitAnyPolicy5);
                                        inhibitAnyPolicy2 = RFC3280CertPathUtilities.i(certPath6, index3, explicitPolicy3);
                                        int policyMapping6 = RFC3280CertPathUtilities.j(certPath6, index3, policyMapping5);
                                        int inhibitAnyPolicy7 = RFC3280CertPathUtilities.k(certPath6, index3, inhibitAnyPolicy6);
                                        RFC3280CertPathUtilities.l(certPath6, index3);
                                        int maxPathLength4 = RFC3280CertPathUtilities.n(certPath6, index3, RFC3280CertPathUtilities.m(certPath6, index3, maxPathLength3));
                                        RFC3280CertPathUtilities.o(certPath6, index3);
                                        Set criticalExtensions = cert4.getCriticalExtensionOIDs();
                                        if (criticalExtensions != null) {
                                            HashSet hashSet5 = new HashSet(criticalExtensions);
                                            hashSet5.remove(RFC3280CertPathUtilities.n);
                                            hashSet5.remove(RFC3280CertPathUtilities.b);
                                            hashSet5.remove(RFC3280CertPathUtilities.c);
                                            hashSet5.remove(RFC3280CertPathUtilities.d);
                                            hashSet5.remove(RFC3280CertPathUtilities.e);
                                            hashSet5.remove(RFC3280CertPathUtilities.g);
                                            hashSet5.remove(RFC3280CertPathUtilities.h);
                                            hashSet5.remove(RFC3280CertPathUtilities.i);
                                            hashSet5.remove(RFC3280CertPathUtilities.k);
                                            hashSet5.remove(RFC3280CertPathUtilities.l);
                                            set = hashSet5;
                                        } else {
                                            set = new HashSet();
                                            Set criticalExtensions2 = set;
                                        }
                                        int policyMapping7 = policyMapping6;
                                        pathCheckers = pathCheckers3;
                                        RFC3280CertPathUtilities.p(certPath6, index3, set, pathCheckers);
                                        sign = cert4;
                                        workingIssuerName = PrincipalUtils.e(sign);
                                        try {
                                            int i8 = i6;
                                            HashSet hashSet6 = set;
                                            pKIXCertPathValidatorSpi = this;
                                            try {
                                                PublicKey workingPublicKey2 = CertPathValidatorUtilities.m(certPath.getCertificates(), index3, pKIXCertPathValidatorSpi.a);
                                                AlgorithmIdentifier workingAlgId2 = CertPathValidatorUtilities.g(workingPublicKey2);
                                                cert = cert4;
                                                maxPathLength3 = maxPathLength4;
                                                inhibitAnyPolicy4 = inhibitAnyPolicy7;
                                                ASN1ObjectIdentifier e4 = workingAlgId2.e();
                                                policyMapping2 = policyMapping7;
                                                ASN1Encodable workingPublicKeyParameters = workingAlgId2.h();
                                                workingPublicKey = workingPublicKey2;
                                                AlgorithmIdentifier algorithmIdentifier2 = workingAlgId2;
                                                validPolicyTree2 = validPolicyTree4;
                                                int i9 = index3 - 1;
                                                CertPathParameters certPathParameters2 = params;
                                                nameConstraintValidator = nameConstraintValidator3;
                                                n = n2;
                                                listArr = policyNodes;
                                                set2 = acceptablePolicies;
                                                index2 = inhibitAnyPolicy2;
                                                z = z4;
                                                trust = trust2;
                                                th = null;
                                                index = i9;
                                                certPath4 = certPath6;
                                                cert2 = cert;
                                                policySet = policySet2;
                                                policyMapping3 = policyMapping2;
                                                certificates = list4;
                                                z2 = true;
                                                PKIXCertPathValidatorSpi pKIXCertPathValidatorSpi3 = pKIXCertPathValidatorSpi;
                                                pathCheckers2 = pathCheckers;
                                                pKIXCertPathValidatorSpi2 = pKIXCertPathValidatorSpi3;
                                            } catch (CertPathValidatorException e5) {
                                                e = e5;
                                                X509Certificate x509Certificate = cert4;
                                                throw new CertPathValidatorException("Next working key could not be retrieved.", e, certPath6, index3);
                                            }
                                        } catch (CertPathValidatorException e6) {
                                            e = e6;
                                            int i10 = i6;
                                            HashSet hashSet7 = set;
                                            X509Certificate x509Certificate2 = cert4;
                                            throw new CertPathValidatorException("Next working key could not be retrieved.", e, certPath6, index3);
                                        }
                                    } else {
                                        pKIXCertPathValidatorSpi = this;
                                        int i11 = i6;
                                        policyMapping2 = policyMapping3;
                                        maxPathLength = maxPathLength3;
                                        inhibitAnyPolicy3 = inhibitAnyPolicy5;
                                        policyNodes = listArr2;
                                        i = explicitPolicy2;
                                        cert = cert4;
                                        pathCheckers = pathCheckers3;
                                    }
                                    validPolicyTree2 = validPolicyTree3;
                                    maxPathLength3 = maxPathLength;
                                    inhibitAnyPolicy4 = inhibitAnyPolicy3;
                                    inhibitAnyPolicy2 = i;
                                    int i92 = index3 - 1;
                                    CertPathParameters certPathParameters22 = params;
                                    nameConstraintValidator = nameConstraintValidator3;
                                    n = n2;
                                    listArr = policyNodes;
                                    set2 = acceptablePolicies;
                                    index2 = inhibitAnyPolicy2;
                                    z = z4;
                                    trust = trust2;
                                    th = null;
                                    index = i92;
                                    certPath4 = certPath6;
                                    cert2 = cert;
                                    policySet = policySet2;
                                    policyMapping3 = policyMapping2;
                                    certificates = list4;
                                    z2 = true;
                                    PKIXCertPathValidatorSpi pKIXCertPathValidatorSpi32 = pKIXCertPathValidatorSpi;
                                    pathCheckers2 = pathCheckers;
                                    pKIXCertPathValidatorSpi2 = pKIXCertPathValidatorSpi32;
                                } catch (AnnotatedException e7) {
                                    int i12 = index2;
                                    Set set3 = policySet;
                                    List[] listArr3 = listArr;
                                    TrustAnchor trustAnchor3 = trust;
                                    int i13 = n;
                                    int explicitPolicy4 = index;
                                    PKIXNameConstraintValidator pKIXNameConstraintValidator3 = nameConstraintValidator2;
                                    CertPath certPath7 = certPath4;
                                    Set set4 = set2;
                                    List<? extends Certificate> list5 = list3;
                                    int i14 = policyMapping3;
                                    int n3 = maxPathLength3;
                                    int i15 = inhibitAnyPolicy4;
                                    X509Certificate x509Certificate3 = cert3;
                                    int i16 = i5;
                                    List list6 = pathCheckers2;
                                    PKIXCertPathValidatorSpi pKIXCertPathValidatorSpi4 = pKIXCertPathValidatorSpi2;
                                    List list7 = list6;
                                    AnnotatedException e8 = e7;
                                    boolean z5 = verificationAlreadyPerformed;
                                    throw new CertPathValidatorException(e8.getMessage(), e8.getUnderlyingException(), certPath7, explicitPolicy4);
                                }
                            }
                            int explicitPolicy5 = index2;
                            Set set5 = policySet;
                            List[] policyNodes2 = listArr;
                            TrustAnchor trust3 = trust;
                            int n4 = n;
                            int explicitPolicy6 = index;
                            List<? extends Certificate> list8 = certificates;
                            CertPath certPath8 = certPath4;
                            Set acceptablePolicies2 = set2;
                            int i17 = policyMapping3;
                            int n5 = maxPathLength3;
                            int i18 = inhibitAnyPolicy4;
                            PKIXNameConstraintValidator nameConstraintValidator4 = nameConstraintValidator;
                            List list9 = pathCheckers2;
                            PKIXCertPathValidatorSpi pKIXCertPathValidatorSpi5 = pKIXCertPathValidatorSpi2;
                            List pathCheckers4 = list9;
                            int explicitPolicy7 = RFC3280CertPathUtilities.F(certPath8, explicitPolicy6 + 1, RFC3280CertPathUtilities.E(explicitPolicy5, cert2));
                            Set criticalExtensions3 = cert2.getCriticalExtensionOIDs();
                            if (criticalExtensions3 != null) {
                                HashSet hashSet8 = new HashSet(criticalExtensions3);
                                hashSet8.remove(RFC3280CertPathUtilities.n);
                                hashSet8.remove(RFC3280CertPathUtilities.b);
                                hashSet8.remove(RFC3280CertPathUtilities.c);
                                hashSet8.remove(RFC3280CertPathUtilities.d);
                                hashSet8.remove(RFC3280CertPathUtilities.e);
                                hashSet8.remove(RFC3280CertPathUtilities.g);
                                hashSet8.remove(RFC3280CertPathUtilities.h);
                                hashSet8.remove(RFC3280CertPathUtilities.i);
                                hashSet8.remove(RFC3280CertPathUtilities.k);
                                hashSet8.remove(RFC3280CertPathUtilities.l);
                                hashSet8.remove(RFC3280CertPathUtilities.j);
                                hashSet8.remove(Extension.F4.s());
                                hashSet = hashSet8;
                            } else {
                                hashSet = new HashSet();
                            }
                            RFC3280CertPathUtilities.G(certPath8, explicitPolicy6 + 1, pathCheckers4, hashSet);
                            List list10 = pathCheckers4;
                            int index4 = explicitPolicy6;
                            CertPath certPath9 = certPath8;
                            HashSet hashSet9 = hashSet;
                            PKIXNameConstraintValidator pKIXNameConstraintValidator4 = nameConstraintValidator4;
                            int i19 = n4;
                            PKIXPolicyNode intersection = RFC3280CertPathUtilities.H(certPath, paramsPKIX2, userInitialPolicySet, explicitPolicy6 + 1, policyNodes2, validPolicyTree2, acceptablePolicies2);
                            if (explicitPolicy7 > 0 || intersection != null) {
                                return new PKIXCertPathValidatorResult(trust3, intersection, cert2.getPublicKey());
                            }
                            throw new CertPathValidatorException("Path processing failed on policy.", (Throwable) null, certPath9, index4);
                        } catch (CertPathValidatorException e9) {
                            Set set6 = policySet;
                            PKIXNameConstraintValidator pKIXNameConstraintValidator5 = nameConstraintValidator;
                            List[] listArr4 = listArr;
                            TrustAnchor trustAnchor4 = trust;
                            int i20 = n;
                            List list11 = certificates;
                            throw new ExtCertPathValidatorException("Algorithm identifier of public key of trust anchor could not be read.", e9, certPath4, -1);
                        }
                    } else {
                        int i21 = n;
                        certs = certificates;
                        certPath2 = certPath4;
                        try {
                            throw new CertPathValidatorException("Trust anchor for certification path not found.", (Throwable) null, certPath2, -1);
                        } catch (AnnotatedException e10) {
                            e = e10;
                            throw new CertPathValidatorException(e.getMessage(), e.getUnderlyingException(), certPath2, certs.size() - 1);
                        }
                    }
                } catch (AnnotatedException e11) {
                    e = e11;
                    int i22 = n;
                    certs = certificates;
                    certPath2 = certPath4;
                    throw new CertPathValidatorException(e.getMessage(), e.getUnderlyingException(), certPath2, certs.size() - 1);
                }
            } else {
                throw new CertPathValidatorException("Certification path is empty.", (Throwable) null, certPath4, -1);
            }
        } else {
            CertPath certPath10 = certPath4;
            throw new InvalidAlgorithmParameterException("trustAnchors is null, this is not allowed for certification path validation.");
        }
    }

    static void a(X509Certificate cert) {
        try {
            TBSCertificate.g(cert.getTBSCertificate());
        } catch (CertificateEncodingException e) {
            throw new AnnotatedException("unable to process TBSCertificate");
        } catch (IllegalArgumentException e2) {
            throw new AnnotatedException(e2.getMessage());
        }
    }
}
