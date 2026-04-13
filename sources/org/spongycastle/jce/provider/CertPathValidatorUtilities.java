package org.spongycastle.jce.provider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.PolicyQualifierInfo;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Enumerated;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1OutputStream;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.isismtt.ISISMTTObjectIdentifiers;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x500.style.RFC4519Style;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.AuthorityKeyIdentifier;
import org.spongycastle.asn1.x509.CRLDistPoint;
import org.spongycastle.asn1.x509.DistributionPoint;
import org.spongycastle.asn1.x509.DistributionPointName;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.jcajce.PKIXCRLStore;
import org.spongycastle.jcajce.PKIXCRLStoreSelector;
import org.spongycastle.jcajce.PKIXCertStore;
import org.spongycastle.jcajce.PKIXCertStoreSelector;
import org.spongycastle.jcajce.PKIXExtendedParameters;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.exception.ExtCertPathValidatorException;
import org.spongycastle.util.Store;
import org.spongycastle.util.StoreException;
import org.spongycastle.x509.X509AttributeCertificate;

public class CertPathValidatorUtilities {
    protected static final PKIXCRLUtil a = new PKIXCRLUtil();
    protected static final String b = Extension.B4.s();
    protected static final String c = Extension.z.s();
    protected static final String d = Extension.C4.s();
    protected static final String e = Extension.x.s();
    protected static final String f = Extension.z4.s();
    protected static final String g = Extension.f.s();
    protected static final String h = Extension.H4.s();
    protected static final String i = Extension.p3.s();
    protected static final String j = Extension.p2.s();
    protected static final String k = Extension.E4.s();
    protected static final String l = Extension.G4.s();
    protected static final String m = Extension.A4.s();
    protected static final String n = Extension.D4.s();
    protected static final String o = Extension.p0.s();
    protected static final String[] p = {"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise"};

    CertPathValidatorUtilities() {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.security.cert.TrustAnchor} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static java.security.cert.TrustAnchor d(java.security.cert.X509Certificate r8, java.util.Set r9, java.lang.String r10) {
        /*
            r0 = 0
            r1 = 0
            r2 = 0
            java.security.cert.X509CertSelector r3 = new java.security.cert.X509CertSelector
            r3.<init>()
            org.spongycastle.asn1.x500.X500Name r4 = org.spongycastle.jce.provider.PrincipalUtils.b(r8)
            byte[] r5 = r4.getEncoded()     // Catch:{ IOException -> 0x007d }
            r3.setSubject(r5)     // Catch:{ IOException -> 0x007d }
            java.util.Iterator r5 = r9.iterator()
        L_0x0018:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x006f
            if (r0 != 0) goto L_0x006f
            java.lang.Object r6 = r5.next()
            r0 = r6
            java.security.cert.TrustAnchor r0 = (java.security.cert.TrustAnchor) r0
            java.security.cert.X509Certificate r6 = r0.getTrustedCert()
            if (r6 == 0) goto L_0x0042
            java.security.cert.X509Certificate r6 = r0.getTrustedCert()
            boolean r6 = r3.match(r6)
            if (r6 == 0) goto L_0x0040
            java.security.cert.X509Certificate r6 = r0.getTrustedCert()
            java.security.PublicKey r1 = r6.getPublicKey()
            goto L_0x0064
        L_0x0040:
            r0 = 0
            goto L_0x0064
        L_0x0042:
            java.lang.String r6 = r0.getCAName()
            if (r6 == 0) goto L_0x0063
            java.security.PublicKey r6 = r0.getCAPublicKey()
            if (r6 == 0) goto L_0x0063
            org.spongycastle.asn1.x500.X500Name r6 = org.spongycastle.jce.provider.PrincipalUtils.a(r0)     // Catch:{ IllegalArgumentException -> 0x0060 }
            boolean r7 = r4.equals(r6)     // Catch:{ IllegalArgumentException -> 0x0060 }
            if (r7 == 0) goto L_0x005e
            java.security.PublicKey r7 = r0.getCAPublicKey()     // Catch:{ IllegalArgumentException -> 0x0060 }
            r1 = r7
            goto L_0x0062
        L_0x005e:
            r0 = 0
            goto L_0x0062
        L_0x0060:
            r6 = move-exception
            r0 = 0
        L_0x0062:
            goto L_0x0064
        L_0x0063:
            r0 = 0
        L_0x0064:
            if (r1 == 0) goto L_0x0018
            A(r8, r1, r10)     // Catch:{ Exception -> 0x006a }
            goto L_0x006e
        L_0x006a:
            r6 = move-exception
            r2 = r6
            r0 = 0
            r1 = 0
        L_0x006e:
            goto L_0x0018
        L_0x006f:
            if (r0 != 0) goto L_0x007c
            if (r2 != 0) goto L_0x0074
            goto L_0x007c
        L_0x0074:
            org.spongycastle.jce.provider.AnnotatedException r6 = new org.spongycastle.jce.provider.AnnotatedException
            java.lang.String r7 = "TrustAnchor found but certificate validation failed."
            r6.<init>(r7, r2)
            throw r6
        L_0x007c:
            return r0
        L_0x007d:
            r5 = move-exception
            org.spongycastle.jce.provider.AnnotatedException r6 = new org.spongycastle.jce.provider.AnnotatedException
            java.lang.String r7 = "Cannot set subject search criteria for trust anchor."
            r6.<init>(r7, r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jce.provider.CertPathValidatorUtilities.d(java.security.cert.X509Certificate, java.util.Set, java.lang.String):java.security.cert.TrustAnchor");
    }

    static boolean u(X509Certificate cert, Set trustAnchors, String sigProvider) {
        try {
            return d(cert, trustAnchors, sigProvider) != null;
        } catch (Exception e2) {
            return false;
        }
    }

    static List<PKIXCertStore> e(byte[] issuerAlternativeName, Map<GeneralName, PKIXCertStore> altNameCertStoreMap) {
        if (issuerAlternativeName == null) {
            return Collections.EMPTY_LIST;
        }
        GeneralName[] names = GeneralNames.e(ASN1OctetString.o(issuerAlternativeName).q()).g();
        List<PKIXCertStore> stores = new ArrayList<>();
        for (int i2 = 0; i2 != names.length; i2++) {
            PKIXCertStore altStore = altNameCertStoreMap.get(names[i2]);
            if (altStore != null) {
                stores.add(altStore);
            }
        }
        return stores;
    }

    protected static Date r(PKIXExtendedParameters paramsPKIX) {
        Date validDate = paramsPKIX.o();
        if (validDate == null) {
            return new Date();
        }
        return validDate;
    }

    protected static boolean v(X509Certificate cert) {
        return cert.getSubjectDN().equals(cert.getIssuerDN());
    }

    protected static ASN1Primitive l(X509Extension ext, String oid) {
        byte[] bytes = ext.getExtensionValue(oid);
        if (bytes == null) {
            return null;
        }
        return n(oid, bytes);
    }

    private static ASN1Primitive n(String oid, byte[] ext) {
        try {
            return new ASN1InputStream(((ASN1OctetString) new ASN1InputStream(ext).r()).q()).r();
        } catch (Exception e2) {
            throw new AnnotatedException("exception processing extension " + oid, e2);
        }
    }

    protected static AlgorithmIdentifier g(PublicKey key) {
        try {
            return SubjectPublicKeyInfo.g(new ASN1InputStream(key.getEncoded()).r()).e();
        } catch (Exception e2) {
            throw new ExtCertPathValidatorException("Subject public key cannot be decoded.", e2);
        }
    }

    protected static final Set o(ASN1Sequence qualifiers) {
        Set pq = new HashSet();
        if (qualifiers == null) {
            return pq;
        }
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        ASN1OutputStream aOut = new ASN1OutputStream(bOut);
        Enumeration e2 = qualifiers.s();
        while (e2.hasMoreElements()) {
            try {
                aOut.j((ASN1Encodable) e2.nextElement());
                pq.add(new PolicyQualifierInfo(bOut.toByteArray()));
                bOut.reset();
            } catch (IOException ex) {
                throw new ExtCertPathValidatorException("Policy qualifier info cannot be decoded.", ex);
            }
        }
        return pq;
    }

    protected static PKIXPolicyNode y(PKIXPolicyNode validPolicyTree, List[] policyNodes, PKIXPolicyNode _node) {
        PKIXPolicyNode _parent = (PKIXPolicyNode) _node.getParent();
        if (validPolicyTree == null) {
            return null;
        }
        if (_parent == null) {
            for (int j2 = 0; j2 < policyNodes.length; j2++) {
                policyNodes[j2] = new ArrayList();
            }
            return null;
        }
        _parent.d(_node);
        z(policyNodes, _node);
        return validPolicyTree;
    }

    private static void z(List[] policyNodes, PKIXPolicyNode _node) {
        policyNodes[_node.getDepth()].remove(_node);
        if (_node.c()) {
            Iterator _iter = _node.getChildren();
            while (_iter.hasNext()) {
                z(policyNodes, (PKIXPolicyNode) _iter.next());
            }
        }
    }

    protected static boolean w(int index, List[] policyNodes, ASN1ObjectIdentifier pOid, Set pq) {
        List policyNodeVec = policyNodes[index - 1];
        for (int j2 = 0; j2 < policyNodeVec.size(); j2++) {
            PKIXPolicyNode node = (PKIXPolicyNode) policyNodeVec.get(j2);
            if (node.getExpectedPolicies().contains(pOid.s())) {
                Set childExpectedPolicies = new HashSet();
                childExpectedPolicies.add(pOid.s());
                PKIXPolicyNode child = new PKIXPolicyNode(new ArrayList(), index, childExpectedPolicies, node, pq, pOid.s(), false);
                node.a(child);
                policyNodes[index].add(child);
                return true;
            }
        }
        return false;
    }

    protected static void x(int index, List[] policyNodes, ASN1ObjectIdentifier _poid, Set _pq) {
        List policyNodeVec = policyNodes[index - 1];
        for (int j2 = 0; j2 < policyNodeVec.size(); j2++) {
            PKIXPolicyNode _node = (PKIXPolicyNode) policyNodeVec.get(j2);
            if ("2.5.29.32.0".equals(_node.getValidPolicy())) {
                Set _childExpectedPolicies = new HashSet();
                _childExpectedPolicies.add(_poid.s());
                PKIXPolicyNode _child = new PKIXPolicyNode(new ArrayList(), index, _childExpectedPolicies, _node, _pq, _poid.s(), false);
                _node.a(_child);
                policyNodes[index].add(_child);
                return;
            }
        }
    }

    protected static boolean s(Set policySet) {
        return policySet == null || policySet.contains("2.5.29.32.0") || policySet.isEmpty();
    }

    protected static Collection b(PKIXCertStoreSelector certSelect, List certStores) {
        Set certs = new LinkedHashSet();
        for (Object obj : certStores) {
            if (obj instanceof Store) {
                try {
                    certs.addAll(((Store) obj).a(certSelect));
                } catch (StoreException e2) {
                    throw new AnnotatedException("Problem while picking certificates from X.509 store.", e2);
                }
            } else {
                try {
                    certs.addAll(PKIXCertStoreSelector.b(certSelect, (CertStore) obj));
                } catch (CertStoreException e3) {
                    throw new AnnotatedException("Problem while picking certificates from certificate store.", e3);
                }
            }
        }
        return certs;
    }

    static List<PKIXCRLStore> f(CRLDistPoint crldp, Map<GeneralName, PKIXCRLStore> namedCRLStoreMap) {
        if (crldp == null) {
            return Collections.EMPTY_LIST;
        }
        try {
            DistributionPoint[] dps = crldp.e();
            List<PKIXCRLStore> stores = new ArrayList<>();
            for (DistributionPoint g2 : dps) {
                DistributionPointName dpn = g2.g();
                if (dpn != null && dpn.i() == 0) {
                    GeneralName[] genNames = GeneralNames.e(dpn.h()).g();
                    for (GeneralName generalName : genNames) {
                        PKIXCRLStore store = namedCRLStoreMap.get(generalName);
                        if (store != null) {
                            stores.add(store);
                        }
                    }
                }
            }
            return stores;
        } catch (Exception e2) {
            throw new AnnotatedException("Distribution points could not be read.", e2);
        }
    }

    protected static void h(DistributionPoint dp, Collection issuerPrincipals, X509CRLSelector selector) {
        List<X500Name> issuers = new ArrayList<>();
        if (dp.f() != null) {
            GeneralName[] genNames = dp.f().g();
            for (int j2 = 0; j2 < genNames.length; j2++) {
                if (genNames[j2].i() == 4) {
                    try {
                        issuers.add(X500Name.e(genNames[j2].h().toASN1Primitive().getEncoded()));
                    } catch (IOException e2) {
                        throw new AnnotatedException("CRL issuer information from distribution point cannot be decoded.", e2);
                    }
                }
            }
        } else if (dp.g() != null) {
            for (Object add : issuerPrincipals) {
                issuers.add(add);
            }
        } else {
            throw new AnnotatedException("CRL issuer is omitted from distribution point but no distributionPoint field present.");
        }
        for (X500Name encoded : issuers) {
            try {
                selector.addIssuerName(encoded.getEncoded());
            } catch (IOException ex) {
                throw new AnnotatedException("Cannot decode CRL issuer information.", ex);
            }
        }
    }

    private static BigInteger p(Object cert) {
        return ((X509Certificate) cert).getSerialNumber();
    }

    protected static void i(Date validDate, X509CRL crl, Object cert, CertStatus certStatus) {
        X509CRLEntry crl_entry;
        X500Name certIssuer;
        try {
            if (X509CRLObject.f(crl)) {
                crl_entry = crl.getRevokedCertificate(p(cert));
                if (crl_entry != null) {
                    X500Principal certificateIssuer = crl_entry.getCertificateIssuer();
                    if (certificateIssuer == null) {
                        certIssuer = PrincipalUtils.c(crl);
                    } else {
                        certIssuer = X500Name.e(certificateIssuer.getEncoded());
                    }
                    if (!PrincipalUtils.b(cert).equals(certIssuer)) {
                        return;
                    }
                } else {
                    return;
                }
            } else if (!PrincipalUtils.b(cert).equals(PrincipalUtils.c(crl)) || (crl_entry = crl.getRevokedCertificate(p(cert))) == null) {
                return;
            }
            ASN1Enumerated reasonCode = null;
            if (crl_entry.hasExtensions()) {
                try {
                    reasonCode = ASN1Enumerated.p(l(crl_entry, Extension.a1.s()));
                } catch (Exception e2) {
                    throw new AnnotatedException("Reason code CRL entry extension could not be decoded.", e2);
                }
            }
            if (validDate.getTime() >= crl_entry.getRevocationDate().getTime() || reasonCode == null || reasonCode.q().intValue() == 0 || reasonCode.q().intValue() == 1 || reasonCode.q().intValue() == 2 || reasonCode.q().intValue() == 8) {
                if (reasonCode != null) {
                    certStatus.c(reasonCode.q().intValue());
                } else {
                    certStatus.c(0);
                }
                certStatus.d(crl_entry.getRevocationDate());
            }
        } catch (CRLException exception) {
            throw new AnnotatedException("Failed check for indirect CRL.", exception);
        }
    }

    protected static Set k(Date validityDate, X509CRL completeCRL, List<CertStore> certStores, List<PKIXCRLStore> pkixCrlStores) {
        BigInteger bigInteger;
        X509CRLSelector baseDeltaSelect = new X509CRLSelector();
        try {
            baseDeltaSelect.addIssuerName(PrincipalUtils.c(completeCRL).getEncoded());
            BigInteger completeCRLNumber = null;
            try {
                ASN1Primitive derObject = l(completeCRL, o);
                if (derObject != null) {
                    completeCRLNumber = ASN1Integer.o(derObject).q();
                }
                try {
                    byte[] idp = completeCRL.getExtensionValue(i);
                    if (completeCRLNumber == null) {
                        bigInteger = null;
                    } else {
                        bigInteger = completeCRLNumber.add(BigInteger.valueOf(1));
                    }
                    baseDeltaSelect.setMinCRLNumber(bigInteger);
                    PKIXCRLStoreSelector.Builder selBuilder = new PKIXCRLStoreSelector.Builder(baseDeltaSelect);
                    selBuilder.i(idp);
                    selBuilder.j(true);
                    selBuilder.k(completeCRLNumber);
                    Set<X509CRL> temp = a.b(selBuilder.g(), validityDate, certStores, pkixCrlStores);
                    Set result = new HashSet();
                    for (X509CRL crl : temp) {
                        if (t(crl)) {
                            result.add(crl);
                        }
                    }
                    return result;
                } catch (Exception e2) {
                    throw new AnnotatedException("Issuing distribution point extension value could not be read.", e2);
                }
            } catch (Exception e3) {
                throw new AnnotatedException("CRL number extension could not be extracted from CRL.", e3);
            }
        } catch (IOException e4) {
            throw new AnnotatedException("Cannot extract issuer from CRL.", e4);
        }
    }

    private static boolean t(X509CRL crl) {
        Set critical = crl.getCriticalExtensionOIDs();
        if (critical == null) {
            return false;
        }
        return critical.contains(RFC3280CertPathUtilities.g);
    }

    protected static Set j(DistributionPoint dp, Object cert, Date currentDate, PKIXExtendedParameters paramsPKIX) {
        X509CRLSelector baseCrlSelect = new X509CRLSelector();
        try {
            Set issuers = new HashSet();
            issuers.add(PrincipalUtils.b(cert));
            h(dp, issuers, baseCrlSelect);
            if (cert instanceof X509Certificate) {
                baseCrlSelect.setCertificateChecking((X509Certificate) cert);
            }
            PKIXCRLStoreSelector crlSelect = new PKIXCRLStoreSelector.Builder(baseCrlSelect).h(true).g();
            Date validityDate = currentDate;
            if (paramsPKIX.o() != null) {
                validityDate = paramsPKIX.o();
            }
            Set crls = a.b(crlSelect, validityDate, paramsPKIX.m(), paramsPKIX.k());
            a(crls, cert);
            return crls;
        } catch (AnnotatedException e2) {
            throw new AnnotatedException("Could not get issuer information from distribution point.", e2);
        }
    }

    protected static Date q(PKIXExtendedParameters paramsPKIX, CertPath certPath, int index) {
        if (paramsPKIX.v() != 1) {
            return r(paramsPKIX);
        }
        if (index <= 0) {
            return r(paramsPKIX);
        }
        if (index - 1 != 0) {
            return ((X509Certificate) certPath.getCertificates().get(index - 1)).getNotBefore();
        }
        ASN1GeneralizedTime dateOfCertgen = null;
        try {
            byte[] extBytes = ((X509Certificate) certPath.getCertificates().get(index - 1)).getExtensionValue(ISISMTTObjectIdentifiers.e.s());
            if (extBytes != null) {
                dateOfCertgen = ASN1GeneralizedTime.r(ASN1Primitive.h(extBytes));
            }
            if (dateOfCertgen == null) {
                return ((X509Certificate) certPath.getCertificates().get(index - 1)).getNotBefore();
            }
            try {
                return dateOfCertgen.q();
            } catch (ParseException e2) {
                throw new AnnotatedException("Date from date of cert gen extension could not be parsed.", e2);
            }
        } catch (IOException e3) {
            throw new AnnotatedException("Date of cert gen extension could not be read.");
        } catch (IllegalArgumentException e4) {
            throw new AnnotatedException("Date of cert gen extension could not be read.");
        }
    }

    protected static PublicKey m(List certs, int index, JcaJceHelper helper) {
        PublicKey pubKey = ((Certificate) certs.get(index)).getPublicKey();
        if (!(pubKey instanceof DSAPublicKey)) {
            return pubKey;
        }
        DSAPublicKey dsaPubKey = (DSAPublicKey) pubKey;
        if (dsaPubKey.getParams() != null) {
            return dsaPubKey;
        }
        int i2 = index + 1;
        while (i2 < certs.size()) {
            PublicKey pubKey2 = ((X509Certificate) certs.get(i2)).getPublicKey();
            if (pubKey2 instanceof DSAPublicKey) {
                DSAPublicKey prevDSAPubKey = (DSAPublicKey) pubKey2;
                if (prevDSAPubKey.getParams() == null) {
                    i2++;
                } else {
                    DSAParams dsaParams = prevDSAPubKey.getParams();
                    try {
                        return helper.e("DSA").generatePublic(new DSAPublicKeySpec(dsaPubKey.getY(), dsaParams.getP(), dsaParams.getQ(), dsaParams.getG()));
                    } catch (Exception exception) {
                        throw new RuntimeException(exception.getMessage());
                    }
                }
            } else {
                throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
            }
        }
        throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
    }

    static Collection c(X509Certificate cert, List<CertStore> certStores, List<PKIXCertStore> pkixCertStores) {
        byte[] authorityKeyIdentifier;
        X509CertSelector selector = new X509CertSelector();
        try {
            selector.setSubject(PrincipalUtils.d(cert).getEncoded());
            try {
                byte[] akiExtensionValue = cert.getExtensionValue(n);
                if (!(akiExtensionValue == null || (authorityKeyIdentifier = AuthorityKeyIdentifier.e(ASN1OctetString.o(akiExtensionValue).q()).f()) == null)) {
                    selector.setSubjectKeyIdentifier(new DEROctetString(authorityKeyIdentifier).getEncoded());
                }
            } catch (Exception e2) {
            }
            PKIXCertStoreSelector certSelect = new PKIXCertStoreSelector.Builder(selector).a();
            Set certs = new LinkedHashSet();
            try {
                List<X509Certificate> matches = new ArrayList<>();
                matches.addAll(b(certSelect, certStores));
                matches.addAll(b(certSelect, pkixCertStores));
                for (X509Certificate issuer : matches) {
                    certs.add(issuer);
                }
                return certs;
            } catch (AnnotatedException e3) {
                throw new AnnotatedException("Issuer certificate cannot be searched.", e3);
            }
        } catch (IOException e4) {
            throw new AnnotatedException("Subject criteria for certificate selector to find issuer certificate could not be set.", e4);
        }
    }

    protected static void A(X509Certificate cert, PublicKey publicKey, String sigProvider) {
        if (sigProvider == null) {
            cert.verify(publicKey);
        } else {
            cert.verify(publicKey, sigProvider);
        }
    }

    static void a(Set crls, Object cert) {
        if (!crls.isEmpty()) {
            return;
        }
        if (cert instanceof X509AttributeCertificate) {
            throw new AnnotatedException("No CRLs found for issuer \"" + ((X509AttributeCertificate) cert).c().b()[0] + "\"");
        }
        throw new AnnotatedException("No CRLs found for issuer \"" + RFC4519Style.T.a(PrincipalUtils.d((X509Certificate) cert)) + "\"");
    }
}
