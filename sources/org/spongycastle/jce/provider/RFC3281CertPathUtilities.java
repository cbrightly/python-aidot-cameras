package org.spongycastle.jce.provider;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.x509.DistributionPoint;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.TargetInformation;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.jcajce.PKIXCertStoreSelector;
import org.spongycastle.jcajce.PKIXExtendedBuilderParameters;
import org.spongycastle.jcajce.PKIXExtendedParameters;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.exception.ExtCertPathValidatorException;
import org.spongycastle.x509.PKIXAttrCertChecker;
import org.spongycastle.x509.X509AttributeCertificate;
import org.spongycastle.x509.X509CertStoreSelector;

public class RFC3281CertPathUtilities {
    private static final String a = Extension.P4.s();
    private static final String b = Extension.O4.s();
    private static final String c = Extension.A4.s();
    private static final String d = Extension.I4.s();

    RFC3281CertPathUtilities() {
    }

    protected static void i(X509AttributeCertificate attrCert, CertPath certPath, CertPath holderCertPath, PKIXExtendedParameters pkixParams, Set attrCertCheckers) {
        Set set = attrCert.getCriticalExtensionOIDs();
        String str = a;
        if (set.contains(str)) {
            try {
                TargetInformation.e(CertPathValidatorUtilities.l(attrCert, str));
            } catch (AnnotatedException e) {
                throw new ExtCertPathValidatorException("Target information extension could not be read.", e);
            } catch (IllegalArgumentException e2) {
                throw new ExtCertPathValidatorException("Target information extension could not be read.", e2);
            }
        }
        set.remove(str);
        Iterator it = attrCertCheckers.iterator();
        while (it.hasNext()) {
            ((PKIXAttrCertChecker) it.next()).a(attrCert, certPath, holderCertPath, set);
        }
        if (!set.isEmpty()) {
            throw new CertPathValidatorException("Attribute certificate contains unsupported critical extensions: " + set);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x00d0  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x016a  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0179  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01d7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static void c(org.spongycastle.x509.X509AttributeCertificate r20, org.spongycastle.jcajce.PKIXExtendedParameters r21, java.security.cert.X509Certificate r22, java.util.Date r23, java.util.List r24, org.spongycastle.jcajce.util.JcaJceHelper r25) {
        /*
            r15 = r20
            boolean r0 = r21.A()
            if (r0 == 0) goto L_0x0217
            java.lang.String r0 = b
            byte[] r0 = r15.getExtensionValue(r0)
            if (r0 != 0) goto L_0x01fb
            r1 = 0
            java.lang.String r0 = c     // Catch:{ AnnotatedException -> 0x01ef }
            org.spongycastle.asn1.ASN1Primitive r0 = org.spongycastle.jce.provider.CertPathValidatorUtilities.l(r15, r0)     // Catch:{ AnnotatedException -> 0x01ef }
            org.spongycastle.asn1.x509.CRLDistPoint r0 = org.spongycastle.asn1.x509.CRLDistPoint.f(r0)     // Catch:{ AnnotatedException -> 0x01ef }
            r14 = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r13 = r0
            java.util.Map r0 = r21.q()     // Catch:{ AnnotatedException -> 0x01df }
            java.util.List r0 = org.spongycastle.jce.provider.CertPathValidatorUtilities.f(r14, r0)     // Catch:{ AnnotatedException -> 0x01df }
            r13.addAll(r0)     // Catch:{ AnnotatedException -> 0x01df }
            org.spongycastle.jcajce.PKIXExtendedParameters$Builder r0 = new org.spongycastle.jcajce.PKIXExtendedParameters$Builder
            r2 = r21
            r0.<init>((org.spongycastle.jcajce.PKIXExtendedParameters) r2)
            r12 = r0
            java.util.Iterator r0 = r13.iterator()
        L_0x003b:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0048
            r1 = r13
            org.spongycastle.jcajce.PKIXCRLStore r1 = (org.spongycastle.jcajce.PKIXCRLStore) r1
            r12.l(r1)
            goto L_0x003b
        L_0x0048:
            org.spongycastle.jcajce.PKIXExtendedParameters r17 = r12.n()
            org.spongycastle.jce.provider.CertStatus r0 = new org.spongycastle.jce.provider.CertStatus
            r0.<init>()
            r11 = r0
            org.spongycastle.jce.provider.ReasonsMask r7 = new org.spongycastle.jce.provider.ReasonsMask
            r7.<init>()
            r10 = 0
            r1 = 0
            java.lang.String r9 = "No valid CRL for distribution point found."
            r8 = 11
            if (r14 == 0) goto L_0x00c6
            r2 = 0
            org.spongycastle.asn1.x509.DistributionPoint[] r0 = r14.e()     // Catch:{ Exception -> 0x00bb }
            r6 = r0
            r0 = 0
            r16 = r1
        L_0x0069:
            int r1 = r6.length     // Catch:{ AnnotatedException -> 0x00ad }
            if (r0 >= r1) goto L_0x00a6
            int r1 = r11.a()     // Catch:{ AnnotatedException -> 0x00ad }
            if (r1 != r8) goto L_0x00a2
            boolean r1 = r7.e()     // Catch:{ AnnotatedException -> 0x00ad }
            if (r1 != 0) goto L_0x00a6
            java.lang.Object r1 = r17.clone()     // Catch:{ AnnotatedException -> 0x00ad }
            r3 = r1
            org.spongycastle.jcajce.PKIXExtendedParameters r3 = (org.spongycastle.jcajce.PKIXExtendedParameters) r3     // Catch:{ AnnotatedException -> 0x00ad }
            r1 = r6[r0]     // Catch:{ AnnotatedException -> 0x00ad }
            r2 = r20
            r4 = r23
            r5 = r22
            r18 = r6
            r6 = r11
            r15 = r8
            r8 = r24
            r15 = r9
            r9 = r25
            b(r1, r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ AnnotatedException -> 0x00a0 }
            r16 = 1
            int r0 = r0 + 1
            r8 = 11
            r9 = r15
            r6 = r18
            r15 = r20
            goto L_0x0069
        L_0x00a0:
            r0 = move-exception
            goto L_0x00b1
        L_0x00a2:
            r18 = r6
            r15 = r9
            goto L_0x00a9
        L_0x00a6:
            r18 = r6
            r15 = r9
        L_0x00a9:
            r2 = r10
            r1 = r16
            goto L_0x00c8
        L_0x00ad:
            r0 = move-exception
            r18 = r6
            r15 = r9
        L_0x00b1:
            org.spongycastle.jce.provider.AnnotatedException r1 = new org.spongycastle.jce.provider.AnnotatedException
            r1.<init>(r15, r0)
            r10 = r1
            r2 = r10
            r1 = r16
            goto L_0x00c8
        L_0x00bb:
            r0 = move-exception
            r3 = r0
            r0 = r3
            org.spongycastle.jce.exception.ExtCertPathValidatorException r3 = new org.spongycastle.jce.exception.ExtCertPathValidatorException
            java.lang.String r4 = "Distribution points could not be read."
            r3.<init>(r4, r0)
            throw r3
        L_0x00c6:
            r15 = r9
            r2 = r10
        L_0x00c8:
            int r0 = r11.a()
            r3 = 11
            if (r0 != r3) goto L_0x016a
            boolean r0 = r7.e()
            if (r0 != 0) goto L_0x015e
            r4 = 0
            org.spongycastle.asn1.ASN1InputStream r0 = new org.spongycastle.asn1.ASN1InputStream     // Catch:{ Exception -> 0x013f }
            org.spongycastle.x509.AttributeCertificateIssuer r5 = r20.c()     // Catch:{ Exception -> 0x013f }
            java.security.Principal[] r5 = r5.b()     // Catch:{ Exception -> 0x013f }
            r6 = 0
            r5 = r5[r6]     // Catch:{ Exception -> 0x013f }
            javax.security.auth.x500.X500Principal r5 = (javax.security.auth.x500.X500Principal) r5     // Catch:{ Exception -> 0x013f }
            byte[] r5 = r5.getEncoded()     // Catch:{ Exception -> 0x013f }
            r0.<init>((byte[]) r5)     // Catch:{ Exception -> 0x013f }
            org.spongycastle.asn1.ASN1Primitive r0 = r0.r()     // Catch:{ Exception -> 0x013f }
            org.spongycastle.asn1.x509.DistributionPoint r8 = new org.spongycastle.asn1.x509.DistributionPoint     // Catch:{ AnnotatedException -> 0x0131 }
            org.spongycastle.asn1.x509.DistributionPointName r4 = new org.spongycastle.asn1.x509.DistributionPointName     // Catch:{ AnnotatedException -> 0x0131 }
            org.spongycastle.asn1.x509.GeneralNames r5 = new org.spongycastle.asn1.x509.GeneralNames     // Catch:{ AnnotatedException -> 0x0131 }
            org.spongycastle.asn1.x509.GeneralName r9 = new org.spongycastle.asn1.x509.GeneralName     // Catch:{ AnnotatedException -> 0x0131 }
            r10 = 4
            r9.<init>((int) r10, (org.spongycastle.asn1.ASN1Encodable) r0)     // Catch:{ AnnotatedException -> 0x0131 }
            r5.<init>((org.spongycastle.asn1.x509.GeneralName) r9)     // Catch:{ AnnotatedException -> 0x0131 }
            r4.<init>(r6, r5)     // Catch:{ AnnotatedException -> 0x0131 }
            r5 = 0
            r8.<init>(r4, r5, r5)     // Catch:{ AnnotatedException -> 0x0131 }
            java.lang.Object r4 = r17.clone()     // Catch:{ AnnotatedException -> 0x0131 }
            r10 = r4
            org.spongycastle.jcajce.PKIXExtendedParameters r10 = (org.spongycastle.jcajce.PKIXExtendedParameters) r10     // Catch:{ AnnotatedException -> 0x0131 }
            r9 = r20
            r5 = r11
            r11 = r23
            r6 = r12
            r12 = r22
            r18 = r13
            r13 = r5
            r19 = r14
            r14 = r7
            r21 = r1
            r1 = r3
            r4 = r15
            r3 = r20
            r15 = r24
            r16 = r25
            b(r8, r9, r10, r11, r12, r13, r14, r15, r16)     // Catch:{ AnnotatedException -> 0x012e }
            r0 = 1
            goto L_0x0177
        L_0x012e:
            r0 = move-exception
            r8 = r4
            goto L_0x0155
        L_0x0131:
            r0 = move-exception
            r21 = r1
            r1 = r3
            r5 = r11
            r6 = r12
            r18 = r13
            r19 = r14
            r3 = r20
            r8 = r15
            goto L_0x0155
        L_0x013f:
            r0 = move-exception
            r21 = r1
            r1 = r3
            r5 = r11
            r6 = r12
            r18 = r13
            r19 = r14
            r8 = r15
            r3 = r20
            org.spongycastle.jce.provider.AnnotatedException r9 = new org.spongycastle.jce.provider.AnnotatedException     // Catch:{ AnnotatedException -> 0x0154 }
            java.lang.String r10 = "Issuer from certificate for CRL could not be reencoded."
            r9.<init>(r10, r0)     // Catch:{ AnnotatedException -> 0x0154 }
            throw r9     // Catch:{ AnnotatedException -> 0x0154 }
        L_0x0154:
            r0 = move-exception
        L_0x0155:
            org.spongycastle.jce.provider.AnnotatedException r4 = new org.spongycastle.jce.provider.AnnotatedException
            r4.<init>(r8, r0)
            r2 = r4
            r0 = r21
            goto L_0x0177
        L_0x015e:
            r21 = r1
            r1 = r3
            r5 = r11
            r6 = r12
            r18 = r13
            r19 = r14
            r3 = r20
            goto L_0x0175
        L_0x016a:
            r21 = r1
            r1 = r3
            r5 = r11
            r6 = r12
            r18 = r13
            r19 = r14
            r3 = r20
        L_0x0175:
            r0 = r21
        L_0x0177:
            if (r0 == 0) goto L_0x01d7
            int r4 = r5.a()
            if (r4 != r1) goto L_0x01a0
            boolean r4 = r7.e()
            r8 = 12
            if (r4 != 0) goto L_0x0190
            int r4 = r5.a()
            if (r4 != r1) goto L_0x0190
            r5.c(r8)
        L_0x0190:
            int r1 = r5.a()
            if (r1 == r8) goto L_0x0198
            goto L_0x021c
        L_0x0198:
            java.security.cert.CertPathValidatorException r1 = new java.security.cert.CertPathValidatorException
            java.lang.String r4 = "Attribute certificate status could not be determined."
            r1.<init>(r4)
            throw r1
        L_0x01a0:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = "Attribute certificate revocation after "
            r1.append(r4)
            java.util.Date r4 = r5.b()
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r1)
            java.lang.String r8 = ", reason: "
            r4.append(r8)
            java.lang.String[] r8 = org.spongycastle.jce.provider.RFC3280CertPathUtilities.p
            int r9 = r5.a()
            r8 = r8[r9]
            r4.append(r8)
            java.lang.String r1 = r4.toString()
            java.security.cert.CertPathValidatorException r4 = new java.security.cert.CertPathValidatorException
            r4.<init>(r1)
            throw r4
        L_0x01d7:
            org.spongycastle.jce.exception.ExtCertPathValidatorException r1 = new org.spongycastle.jce.exception.ExtCertPathValidatorException
            java.lang.String r4 = "No valid CRL found."
            r1.<init>(r4, r2)
            throw r1
        L_0x01df:
            r0 = move-exception
            r2 = r21
            r18 = r13
            r19 = r14
            r3 = r15
            java.security.cert.CertPathValidatorException r1 = new java.security.cert.CertPathValidatorException
            java.lang.String r4 = "No additional CRL locations could be decoded from CRL distribution point extension."
            r1.<init>(r4, r0)
            throw r1
        L_0x01ef:
            r0 = move-exception
            r2 = r21
            r3 = r15
            java.security.cert.CertPathValidatorException r4 = new java.security.cert.CertPathValidatorException
            java.lang.String r5 = "CRL distribution point extension could not be read."
            r4.<init>(r5, r0)
            throw r4
        L_0x01fb:
            r2 = r21
            r3 = r15
            java.lang.String r0 = c
            byte[] r0 = r3.getExtensionValue(r0)
            if (r0 != 0) goto L_0x020f
            java.lang.String r0 = d
            byte[] r0 = r3.getExtensionValue(r0)
            if (r0 != 0) goto L_0x020f
            goto L_0x021a
        L_0x020f:
            java.security.cert.CertPathValidatorException r0 = new java.security.cert.CertPathValidatorException
            java.lang.String r1 = "No rev avail extension is set, but also an AC revocation pointer."
            r0.<init>(r1)
            throw r0
        L_0x0217:
            r2 = r21
            r3 = r15
        L_0x021a:
            r17 = r2
        L_0x021c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jce.provider.RFC3281CertPathUtilities.c(org.spongycastle.x509.X509AttributeCertificate, org.spongycastle.jcajce.PKIXExtendedParameters, java.security.cert.X509Certificate, java.util.Date, java.util.List, org.spongycastle.jcajce.util.JcaJceHelper):void");
    }

    protected static void a(X509AttributeCertificate attrCert, Set prohibitedACAttributes, Set necessaryACAttributes) {
        Iterator it = prohibitedACAttributes.iterator();
        while (it.hasNext()) {
            String oid = (String) it.next();
            if (attrCert.b(oid) != null) {
                throw new CertPathValidatorException("Attribute certificate contains prohibited attribute: " + oid + ".");
            }
        }
        Iterator it2 = necessaryACAttributes.iterator();
        while (it2.hasNext()) {
            String oid2 = (String) it2.next();
            if (attrCert.b(oid2) == null) {
                throw new CertPathValidatorException("Attribute certificate does not contain necessary attribute: " + oid2 + ".");
            }
        }
    }

    protected static void h(X509AttributeCertificate attrCert, PKIXExtendedParameters pkixParams) {
        try {
            attrCert.checkValidity(CertPathValidatorUtilities.r(pkixParams));
        } catch (CertificateExpiredException e) {
            throw new ExtCertPathValidatorException("Attribute certificate is not valid.", e);
        } catch (CertificateNotYetValidException e2) {
            throw new ExtCertPathValidatorException("Attribute certificate is not valid.", e2);
        }
    }

    protected static void g(X509Certificate acIssuerCert, Set trustedACIssuers) {
        boolean trusted = false;
        Iterator it = trustedACIssuers.iterator();
        while (it.hasNext()) {
            TrustAnchor anchor = (TrustAnchor) it.next();
            if (acIssuerCert.getSubjectX500Principal().getName("RFC2253").equals(anchor.getCAName()) || acIssuerCert.equals(anchor.getTrustedCert())) {
                trusted = true;
            }
        }
        if (!trusted) {
            throw new CertPathValidatorException("Attribute certificate issuer is not directly trusted.");
        }
    }

    protected static void f(X509Certificate acIssuerCert, PKIXExtendedParameters pkixParams) {
        if (acIssuerCert.getKeyUsage() != null && !acIssuerCert.getKeyUsage()[0] && !acIssuerCert.getKeyUsage()[1]) {
            throw new CertPathValidatorException("Attribute certificate issuer public key cannot be used to validate digital signatures.");
        } else if (acIssuerCert.getBasicConstraints() != -1) {
            throw new CertPathValidatorException("Attribute certificate issuer is also a public key certificate issuer.");
        }
    }

    protected static CertPathValidatorResult e(CertPath certPath, PKIXExtendedParameters pkixParams) {
        try {
            try {
                return CertPathValidator.getInstance("PKIX", BouncyCastleProvider.PROVIDER_NAME).validate(certPath, pkixParams);
            } catch (CertPathValidatorException e) {
                throw new ExtCertPathValidatorException("Certification path for issuer certificate of attribute certificate could not be validated.", e);
            } catch (InvalidAlgorithmParameterException e2) {
                throw new RuntimeException(e2.getMessage());
            }
        } catch (NoSuchProviderException e3) {
            throw new ExtCertPathValidatorException("Support class could not be created.", e3);
        } catch (NoSuchAlgorithmException e4) {
            throw new ExtCertPathValidatorException("Support class could not be created.", e4);
        }
    }

    protected static CertPath d(X509AttributeCertificate attrCert, PKIXExtendedParameters pkixParams) {
        CertPathBuilderResult result = null;
        Set<X509Certificate> holderPKCs = new HashSet<>();
        if (attrCert.a().d() != null) {
            X509CertSelector selector = new X509CertSelector();
            selector.setSerialNumber(attrCert.a().i());
            Principal[] principals = attrCert.a().d();
            int i = 0;
            while (i < principals.length) {
                try {
                    if (principals[i] instanceof X500Principal) {
                        selector.setIssuer(((X500Principal) principals[i]).getEncoded());
                    }
                    holderPKCs.addAll(CertPathValidatorUtilities.b(new PKIXCertStoreSelector.Builder(selector).a(), pkixParams.m()));
                    i++;
                } catch (AnnotatedException e) {
                    throw new ExtCertPathValidatorException("Public key certificate for attribute certificate cannot be searched.", e);
                } catch (IOException e2) {
                    throw new ExtCertPathValidatorException("Unable to encode X500 principal.", e2);
                }
            }
            if (holderPKCs.isEmpty() != 0) {
                throw new CertPathValidatorException("Public key certificate specified in base certificate ID for attribute certificate cannot be found.");
            }
        }
        if (attrCert.a().c() != null) {
            X509CertStoreSelector selector2 = new X509CertStoreSelector();
            Principal[] principals2 = attrCert.a().c();
            int i2 = 0;
            while (i2 < principals2.length) {
                try {
                    if (principals2[i2] instanceof X500Principal) {
                        selector2.setIssuer(((X500Principal) principals2[i2]).getEncoded());
                    }
                    holderPKCs.addAll(CertPathValidatorUtilities.b(new PKIXCertStoreSelector.Builder(selector2).a(), pkixParams.m()));
                    i2++;
                } catch (AnnotatedException e3) {
                    throw new ExtCertPathValidatorException("Public key certificate for attribute certificate cannot be searched.", e3);
                } catch (IOException e4) {
                    throw new ExtCertPathValidatorException("Unable to encode X500 principal.", e4);
                }
            }
            if (holderPKCs.isEmpty()) {
                throw new CertPathValidatorException("Public key certificate specified in entity name for attribute certificate cannot be found.");
            }
        }
        PKIXExtendedParameters.Builder paramsBldr = new PKIXExtendedParameters.Builder(pkixParams);
        CertPathValidatorException lastException = null;
        for (X509Certificate certificate : holderPKCs) {
            X509CertStoreSelector selector3 = new X509CertStoreSelector();
            selector3.setCertificate(certificate);
            paramsBldr.p(new PKIXCertStoreSelector.Builder(selector3).a());
            try {
                try {
                    result = CertPathBuilder.getInstance("PKIX", BouncyCastleProvider.PROVIDER_NAME).build(new PKIXExtendedBuilderParameters.Builder(paramsBldr.n()).e());
                } catch (CertPathBuilderException e5) {
                    lastException = new ExtCertPathValidatorException("Certification path for public key certificate of attribute certificate could not be build.", e5);
                } catch (InvalidAlgorithmParameterException e6) {
                    throw new RuntimeException(e6.getMessage());
                }
            } catch (NoSuchProviderException e7) {
                throw new ExtCertPathValidatorException("Support class could not be created.", e7);
            } catch (NoSuchAlgorithmException e8) {
                throw new ExtCertPathValidatorException("Support class could not be created.", e8);
            }
        }
        if (lastException == null) {
            return result.getCertPath();
        }
        throw lastException;
    }

    private static void b(DistributionPoint dp, X509AttributeCertificate attrCert, PKIXExtendedParameters paramsPKIX, Date validDate, X509Certificate issuerCert, CertStatus certStatus, ReasonsMask reasonMask, List certPathCerts, JcaJceHelper helper) {
        Set crls;
        DistributionPoint distributionPoint = dp;
        X509AttributeCertificate x509AttributeCertificate = attrCert;
        PKIXExtendedParameters pKIXExtendedParameters = paramsPKIX;
        Date date = validDate;
        CertStatus certStatus2 = certStatus;
        ReasonsMask reasonsMask = reasonMask;
        if (x509AttributeCertificate.getExtensionValue(X509Extensions.O4.s()) == null) {
            Date currentDate = new Date(System.currentTimeMillis());
            if (validDate.getTime() <= currentDate.getTime()) {
                Set crls2 = CertPathValidatorUtilities.j(distributionPoint, x509AttributeCertificate, currentDate, pKIXExtendedParameters);
                Iterator crl_iter = crls2.iterator();
                boolean validCrlFound = false;
                AnnotatedException lastException = null;
                while (true) {
                    if (crl_iter.hasNext()) {
                        if (certStatus.a() == 11) {
                            if (reasonMask.e()) {
                                break;
                            }
                            try {
                                X509CRL crl = (X509CRL) crl_iter.next();
                                ReasonsMask interimReasonsMask = RFC3280CertPathUtilities.t(crl, distributionPoint);
                                if (!interimReasonsMask.c(reasonsMask)) {
                                    continue;
                                } else {
                                    ReasonsMask interimReasonsMask2 = interimReasonsMask;
                                    crls = crls2;
                                    try {
                                        PublicKey key = RFC3280CertPathUtilities.v(crl, RFC3280CertPathUtilities.u(crl, attrCert, (X509Certificate) null, (PublicKey) null, paramsPKIX, certPathCerts, helper));
                                        X509CRL deltaCRL = null;
                                        if (paramsPKIX.B()) {
                                            deltaCRL = RFC3280CertPathUtilities.w(CertPathValidatorUtilities.k(currentDate, crl, paramsPKIX.m(), paramsPKIX.k()), key);
                                        }
                                        if (paramsPKIX.v() != 1) {
                                            if (attrCert.getNotAfter().getTime() < crl.getThisUpdate().getTime()) {
                                                throw new AnnotatedException("No valid CRL for current time found.");
                                            }
                                        }
                                        RFC3280CertPathUtilities.q(distributionPoint, x509AttributeCertificate, crl);
                                        RFC3280CertPathUtilities.r(distributionPoint, x509AttributeCertificate, crl);
                                        RFC3280CertPathUtilities.s(deltaCRL, crl, pKIXExtendedParameters);
                                        RFC3280CertPathUtilities.x(date, deltaCRL, x509AttributeCertificate, certStatus2, pKIXExtendedParameters);
                                        RFC3280CertPathUtilities.y(date, crl, x509AttributeCertificate, certStatus2);
                                        if (certStatus.a() == 8) {
                                            certStatus2.c(11);
                                        }
                                        reasonsMask.a(interimReasonsMask2);
                                        validCrlFound = true;
                                    } catch (AnnotatedException e) {
                                        e = e;
                                        lastException = e;
                                        crls2 = crls;
                                    }
                                    crls2 = crls;
                                }
                            } catch (AnnotatedException e2) {
                                e = e2;
                                crls = crls2;
                                lastException = e;
                                crls2 = crls;
                            }
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (!validCrlFound) {
                    throw lastException;
                }
                return;
            }
            throw new AnnotatedException("Validation time is in future.");
        }
    }
}
