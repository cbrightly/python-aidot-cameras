package org.spongycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.X509CRL;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1String;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x500.RDN;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x500.style.BCStyle;
import org.spongycastle.asn1.x509.BasicConstraints;
import org.spongycastle.asn1.x509.DistributionPoint;
import org.spongycastle.asn1.x509.DistributionPointName;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.GeneralSubtree;
import org.spongycastle.asn1.x509.IssuingDistributionPoint;
import org.spongycastle.asn1.x509.NameConstraints;
import org.spongycastle.asn1.x509.PolicyInformation;
import org.spongycastle.jcajce.PKIXCertStoreSelector;
import org.spongycastle.jcajce.PKIXExtendedBuilderParameters;
import org.spongycastle.jcajce.PKIXExtendedParameters;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.exception.ExtCertPathValidatorException;
import org.spongycastle.util.Arrays;

public class RFC3280CertPathUtilities {
    private static final PKIXCRLUtil a = new PKIXCRLUtil();
    public static final String b = Extension.B4.s();
    public static final String c = Extension.C4.s();
    public static final String d = Extension.H4.s();
    public static final String e = Extension.p3.s();
    public static final String f = Extension.G4.s();
    public static final String g = Extension.p2.s();
    public static final String h = Extension.E4.s();
    public static final String i = Extension.z.s();
    public static final String j = Extension.A4.s();
    public static final String k = Extension.x.s();
    public static final String l = Extension.z4.s();
    public static final String m = Extension.D4.s();
    public static final String n = Extension.f.s();
    public static final String o = Extension.p0.s();
    protected static final String[] p = {"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise"};

    RFC3280CertPathUtilities() {
    }

    protected static void r(DistributionPoint dp, Object cert, X509CRL crl) {
        GeneralName[] genNames;
        try {
            IssuingDistributionPoint idp = IssuingDistributionPoint.h(CertPathValidatorUtilities.l(crl, e));
            if (idp != null) {
                if (idp.g() != null) {
                    DistributionPointName dpName = IssuingDistributionPoint.h(idp).g();
                    List names = new ArrayList();
                    if (dpName.i() == 0) {
                        GeneralName[] genNames2 = GeneralNames.e(dpName.h()).g();
                        for (GeneralName add : genNames2) {
                            names.add(add);
                        }
                    }
                    if (dpName.i() == 1) {
                        ASN1EncodableVector vec = new ASN1EncodableVector();
                        try {
                            Enumeration e2 = ASN1Sequence.o(PrincipalUtils.c(crl)).s();
                            while (e2.hasMoreElements()) {
                                vec.a((ASN1Encodable) e2.nextElement());
                            }
                            vec.a(dpName.h());
                            names.add(new GeneralName(X500Name.e(new DERSequence(vec))));
                        } catch (Exception e3) {
                            throw new AnnotatedException("Could not read CRL issuer.", e3);
                        }
                    }
                    boolean matches = false;
                    if (dp.g() != null) {
                        DistributionPointName dpName2 = dp.g();
                        GeneralName[] genNames3 = null;
                        if (dpName2.i() == 0) {
                            genNames3 = GeneralNames.e(dpName2.h()).g();
                        }
                        if (dpName2.i() == 1) {
                            if (dp.f() != null) {
                                genNames = dp.f().g();
                            } else {
                                GeneralName[] genNames4 = new GeneralName[1];
                                try {
                                    genNames4[0] = new GeneralName(X500Name.e(PrincipalUtils.b(cert).getEncoded()));
                                    genNames = genNames4;
                                } catch (Exception e4) {
                                    throw new AnnotatedException("Could not read certificate issuer.", e4);
                                }
                            }
                            for (int j2 = 0; j2 < genNames.length; j2++) {
                                Enumeration e5 = ASN1Sequence.o(genNames[j2].h().toASN1Primitive()).s();
                                ASN1EncodableVector vec2 = new ASN1EncodableVector();
                                while (e5.hasMoreElements()) {
                                    vec2.a((ASN1Encodable) e5.nextElement());
                                }
                                vec2.a(dpName2.h());
                                genNames[j2] = new GeneralName(X500Name.e(new DERSequence(vec2)));
                            }
                        }
                        if (genNames != null) {
                            int j3 = 0;
                            while (true) {
                                if (j3 >= genNames.length) {
                                    break;
                                } else if (names.contains(genNames[j3])) {
                                    matches = true;
                                    break;
                                } else {
                                    j3++;
                                }
                            }
                        }
                        if (!matches) {
                            throw new AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
                        }
                    } else if (dp.f() != null) {
                        GeneralName[] genNames5 = dp.f().g();
                        int j4 = 0;
                        while (true) {
                            if (j4 >= genNames5.length) {
                                break;
                            } else if (names.contains(genNames5[j4])) {
                                matches = true;
                                break;
                            } else {
                                j4++;
                            }
                        }
                        if (!matches) {
                            throw new AnnotatedException("No match for certificate CRL issuing distribution point name to cRLIssuer CRL distribution point.");
                        }
                    } else {
                        throw new AnnotatedException("Either the cRLIssuer or the distributionPoint field must be contained in DistributionPoint.");
                    }
                }
                try {
                    BasicConstraints bc = BasicConstraints.e(CertPathValidatorUtilities.l((X509Extension) cert, i));
                    if (cert instanceof X509Certificate) {
                        if (idp.p() && bc != null && bc.g()) {
                            throw new AnnotatedException("CA Cert CRL only contains user certificates.");
                        } else if (idp.o() && (bc == null || !bc.g())) {
                            throw new AnnotatedException("End CRL only contains CA certificates.");
                        }
                    }
                    if (idp.n()) {
                        throw new AnnotatedException("onlyContainsAttributeCerts boolean is asserted.");
                    }
                } catch (Exception e6) {
                    throw new AnnotatedException("Basic constraints extension could not be decoded.", e6);
                }
            }
        } catch (Exception e7) {
            throw new AnnotatedException("Issuing distribution point extension could not be decoded.", e7);
        }
    }

    protected static void q(DistributionPoint dp, Object cert, X509CRL crl) {
        ASN1Primitive idp = CertPathValidatorUtilities.l(crl, e);
        boolean isIndirect = false;
        if (idp != null && IssuingDistributionPoint.h(idp).k()) {
            isIndirect = true;
        }
        try {
            byte[] issuerBytes = PrincipalUtils.c(crl).getEncoded();
            boolean matchIssuer = false;
            if (dp.f() != null) {
                GeneralName[] genNames = dp.f().g();
                for (int j2 = 0; j2 < genNames.length; j2++) {
                    if (genNames[j2].i() == 4) {
                        try {
                            if (Arrays.b(genNames[j2].h().toASN1Primitive().getEncoded(), issuerBytes)) {
                                matchIssuer = true;
                            }
                        } catch (IOException e2) {
                            throw new AnnotatedException("CRL issuer information from distribution point cannot be decoded.", e2);
                        }
                    }
                }
                if (matchIssuer && !isIndirect) {
                    throw new AnnotatedException("Distribution point contains cRLIssuer field but CRL is not indirect.");
                } else if (!matchIssuer) {
                    throw new AnnotatedException("CRL issuer of CRL does not match CRL issuer of distribution point.");
                }
            } else if (PrincipalUtils.c(crl).equals(PrincipalUtils.b(cert))) {
                matchIssuer = true;
            }
            if (!matchIssuer) {
                throw new AnnotatedException("Cannot find matching CRL issuer for certificate.");
            }
        } catch (IOException e3) {
            throw new AnnotatedException("Exception encoding CRL issuer: " + e3.getMessage(), e3);
        }
    }

    protected static ReasonsMask t(X509CRL crl, DistributionPoint dp) {
        ReasonsMask reasonsMask;
        ReasonsMask reasonsMask2;
        try {
            IssuingDistributionPoint idp = IssuingDistributionPoint.h(CertPathValidatorUtilities.l(crl, e));
            if (idp != null && idp.i() != null && dp.i() != null) {
                return new ReasonsMask(dp.i()).d(new ReasonsMask(idp.i()));
            }
            if ((idp == null || idp.i() == null) && dp.i() == null) {
                return ReasonsMask.a;
            }
            if (dp.i() == null) {
                reasonsMask = ReasonsMask.a;
            } else {
                reasonsMask = new ReasonsMask(dp.i());
            }
            if (idp == null) {
                reasonsMask2 = ReasonsMask.a;
            } else {
                reasonsMask2 = new ReasonsMask(idp.i());
            }
            return reasonsMask.d(reasonsMask2);
        } catch (Exception e2) {
            throw new AnnotatedException("Issuing distribution point extension could not be decoded.", e2);
        }
    }

    protected static Set u(X509CRL crl, Object cert, X509Certificate defaultCRLSignCert, PublicKey defaultCRLSignKey, PKIXExtendedParameters paramsPKIX, List certPathCerts, JcaJceHelper helper) {
        List certs;
        X509Certificate x509Certificate = defaultCRLSignCert;
        X509CertSelector certSelector = new X509CertSelector();
        try {
            certSelector.setSubject(PrincipalUtils.c(crl).getEncoded());
            PKIXCertStoreSelector selector = new PKIXCertStoreSelector.Builder(certSelector).a();
            try {
                Collection<X509Certificate> coll = CertPathValidatorUtilities.b(selector, paramsPKIX.n());
                coll.addAll(CertPathValidatorUtilities.b(selector, paramsPKIX.m()));
                coll.add(x509Certificate);
                List validCerts = new ArrayList();
                List validKeys = new ArrayList();
                for (X509Certificate signingCert : coll) {
                    if (signingCert.equals(x509Certificate)) {
                        validCerts.add(signingCert);
                        validKeys.add(defaultCRLSignKey);
                    } else {
                        PublicKey publicKey = defaultCRLSignKey;
                        try {
                            PKIXCertPathBuilderSpi builder = new PKIXCertPathBuilderSpi();
                            X509CertSelector tmpCertSelector = new X509CertSelector();
                            tmpCertSelector.setCertificate(signingCert);
                            try {
                                PKIXExtendedParameters.Builder paramsBuilder = new PKIXExtendedParameters.Builder(paramsPKIX).p(new PKIXCertStoreSelector.Builder(tmpCertSelector).a());
                                try {
                                    if (certPathCerts.contains(signingCert)) {
                                        paramsBuilder.o(false);
                                    } else {
                                        paramsBuilder.o(true);
                                    }
                                    certs = builder.engineBuild(new PKIXExtendedBuilderParameters.Builder(paramsBuilder.n()).e()).getCertPath().getCertificates();
                                    validCerts.add(signingCert);
                                    PKIXCertPathBuilderSpi pKIXCertPathBuilderSpi = builder;
                                } catch (CertPathBuilderException e2) {
                                    e = e2;
                                    JcaJceHelper jcaJceHelper = helper;
                                    throw new AnnotatedException("CertPath for CRL signer failed to validate.", e);
                                } catch (CertPathValidatorException e3) {
                                    e = e3;
                                    JcaJceHelper jcaJceHelper2 = helper;
                                    throw new AnnotatedException("Public key of issuer certificate of CRL could not be retrieved.", e);
                                } catch (Exception e4) {
                                    e = e4;
                                    JcaJceHelper jcaJceHelper3 = helper;
                                    throw new AnnotatedException(e.getMessage());
                                }
                                try {
                                    validKeys.add(CertPathValidatorUtilities.m(certs, 0, helper));
                                    x509Certificate = defaultCRLSignCert;
                                } catch (CertPathBuilderException e5) {
                                    e = e5;
                                    throw new AnnotatedException("CertPath for CRL signer failed to validate.", e);
                                } catch (CertPathValidatorException e6) {
                                    e = e6;
                                    throw new AnnotatedException("Public key of issuer certificate of CRL could not be retrieved.", e);
                                } catch (Exception e7) {
                                    e = e7;
                                    throw new AnnotatedException(e.getMessage());
                                }
                            } catch (CertPathBuilderException e8) {
                                e = e8;
                                List list = certPathCerts;
                                JcaJceHelper jcaJceHelper4 = helper;
                                throw new AnnotatedException("CertPath for CRL signer failed to validate.", e);
                            } catch (CertPathValidatorException e9) {
                                e = e9;
                                List list2 = certPathCerts;
                                JcaJceHelper jcaJceHelper22 = helper;
                                throw new AnnotatedException("Public key of issuer certificate of CRL could not be retrieved.", e);
                            } catch (Exception e10) {
                                e = e10;
                                List list3 = certPathCerts;
                                JcaJceHelper jcaJceHelper32 = helper;
                                throw new AnnotatedException(e.getMessage());
                            }
                        } catch (CertPathBuilderException e11) {
                            e = e11;
                            PKIXExtendedParameters pKIXExtendedParameters = paramsPKIX;
                            List list4 = certPathCerts;
                            JcaJceHelper jcaJceHelper42 = helper;
                            throw new AnnotatedException("CertPath for CRL signer failed to validate.", e);
                        } catch (CertPathValidatorException e12) {
                            e = e12;
                            PKIXExtendedParameters pKIXExtendedParameters2 = paramsPKIX;
                            List list22 = certPathCerts;
                            JcaJceHelper jcaJceHelper222 = helper;
                            throw new AnnotatedException("Public key of issuer certificate of CRL could not be retrieved.", e);
                        } catch (Exception e13) {
                            e = e13;
                            PKIXExtendedParameters pKIXExtendedParameters3 = paramsPKIX;
                            List list32 = certPathCerts;
                            JcaJceHelper jcaJceHelper322 = helper;
                            throw new AnnotatedException(e.getMessage());
                        }
                    }
                }
                PublicKey publicKey2 = defaultCRLSignKey;
                PKIXExtendedParameters pKIXExtendedParameters4 = paramsPKIX;
                List list5 = certPathCerts;
                JcaJceHelper jcaJceHelper5 = helper;
                Set checkKeys = new HashSet();
                AnnotatedException lastException = null;
                int i2 = 0;
                while (i2 < validCerts.size()) {
                    boolean[] keyusage = ((X509Certificate) validCerts.get(i2)).getKeyUsage();
                    if (keyusage == null || (keyusage.length >= 7 && keyusage[6])) {
                        checkKeys.add(validKeys.get(i2));
                    } else {
                        lastException = new AnnotatedException("Issuer certificate key usage extension does not permit CRL signing.");
                    }
                    i2++;
                    JcaJceHelper jcaJceHelper6 = helper;
                }
                if (checkKeys.isEmpty() && lastException == null) {
                    throw new AnnotatedException("Cannot find a valid issuer certificate.");
                } else if (!checkKeys.isEmpty() || lastException == null) {
                    return checkKeys;
                } else {
                    throw lastException;
                }
            } catch (AnnotatedException e14) {
                PublicKey publicKey3 = defaultCRLSignKey;
                PKIXExtendedParameters pKIXExtendedParameters5 = paramsPKIX;
                List list6 = certPathCerts;
                throw new AnnotatedException("Issuer certificate for CRL cannot be searched.", e14);
            }
        } catch (IOException e15) {
            PublicKey publicKey4 = defaultCRLSignKey;
            PKIXExtendedParameters pKIXExtendedParameters6 = paramsPKIX;
            List list7 = certPathCerts;
            throw new AnnotatedException("Subject criteria for certificate selector to find issuer certificate for CRL could not be set.", e15);
        }
    }

    protected static PublicKey v(X509CRL crl, Set keys) {
        Exception lastException = null;
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            PublicKey key = (PublicKey) it.next();
            try {
                crl.verify(key);
                return key;
            } catch (Exception e2) {
                lastException = e2;
            }
        }
        throw new AnnotatedException("Cannot verify CRL.", lastException);
    }

    protected static X509CRL w(Set deltacrls, PublicKey key) {
        Exception lastException = null;
        Iterator it = deltacrls.iterator();
        while (it.hasNext()) {
            X509CRL crl = (X509CRL) it.next();
            try {
                crl.verify(key);
                return crl;
            } catch (Exception e2) {
                lastException = e2;
            }
        }
        if (lastException == null) {
            return null;
        }
        throw new AnnotatedException("Cannot verify delta CRL.", lastException);
    }

    protected static void s(X509CRL deltaCRL, X509CRL completeCRL, PKIXExtendedParameters pkixParams) {
        if (deltaCRL != null) {
            try {
                String str = e;
                IssuingDistributionPoint completeidp = IssuingDistributionPoint.h(CertPathValidatorUtilities.l(completeCRL, str));
                if (!pkixParams.B()) {
                    return;
                }
                if (PrincipalUtils.c(deltaCRL).equals(PrincipalUtils.c(completeCRL))) {
                    try {
                        IssuingDistributionPoint deltaidp = IssuingDistributionPoint.h(CertPathValidatorUtilities.l(deltaCRL, str));
                        boolean match = false;
                        if (completeidp == null) {
                            if (deltaidp == null) {
                                match = true;
                            }
                        } else if (completeidp.equals(deltaidp)) {
                            match = true;
                        }
                        if (match) {
                            try {
                                String str2 = m;
                                ASN1Primitive completeKeyIdentifier = CertPathValidatorUtilities.l(completeCRL, str2);
                                try {
                                    ASN1Primitive deltaKeyIdentifier = CertPathValidatorUtilities.l(deltaCRL, str2);
                                    if (completeKeyIdentifier == null) {
                                        throw new AnnotatedException("CRL authority key identifier is null.");
                                    } else if (deltaKeyIdentifier == null) {
                                        throw new AnnotatedException("Delta CRL authority key identifier is null.");
                                    } else if (!completeKeyIdentifier.equals(deltaKeyIdentifier)) {
                                        throw new AnnotatedException("Delta CRL authority key identifier does not match complete CRL authority key identifier.");
                                    }
                                } catch (AnnotatedException e2) {
                                    throw new AnnotatedException("Authority key identifier extension could not be extracted from delta CRL.", e2);
                                }
                            } catch (AnnotatedException e3) {
                                throw new AnnotatedException("Authority key identifier extension could not be extracted from complete CRL.", e3);
                            }
                        } else {
                            throw new AnnotatedException("Issuing distribution point extension from delta CRL and complete CRL does not match.");
                        }
                    } catch (Exception e4) {
                        throw new AnnotatedException("Issuing distribution point extension from delta CRL could not be decoded.", e4);
                    }
                } else {
                    throw new AnnotatedException("Complete CRL issuer does not match delta CRL issuer.");
                }
            } catch (Exception e5) {
                throw new AnnotatedException("Issuing distribution point extension could not be decoded.", e5);
            }
        }
    }

    protected static void x(Date validDate, X509CRL deltacrl, Object cert, CertStatus certStatus, PKIXExtendedParameters pkixParams) {
        if (pkixParams.B() && deltacrl != null) {
            CertPathValidatorUtilities.i(validDate, deltacrl, cert, certStatus);
        }
    }

    protected static void y(Date validDate, X509CRL completecrl, Object cert, CertStatus certStatus) {
        if (certStatus.a() == 11) {
            CertPathValidatorUtilities.i(validDate, completecrl, cert, certStatus);
        }
    }

    protected static PKIXPolicyNode c(CertPath certPath, int index, List[] policyNodes, PKIXPolicyNode validPolicyTree, int policyMapping) {
        ASN1Sequence mappings;
        Map m_idp;
        Set s_idp;
        boolean idp_found;
        Set pq;
        boolean ci;
        CertPath certPath2 = certPath;
        int i2 = index;
        List[] listArr = policyNodes;
        List certs = certPath.getCertificates();
        X509Certificate cert = (X509Certificate) certs.get(i2);
        int i3 = certs.size() - i2;
        try {
            ASN1Sequence pm = ASN1Sequence.o(CertPathValidatorUtilities.l(cert, c));
            PKIXPolicyNode _validPolicyTree = validPolicyTree;
            if (pm == null) {
                return _validPolicyTree;
            }
            ASN1Sequence mappings2 = pm;
            Map hashMap = new HashMap();
            Set<String> hashSet = new HashSet<>();
            for (int j2 = 0; j2 < mappings2.size(); j2++) {
                ASN1Sequence mapping = (ASN1Sequence) mappings2.r(j2);
                String id_p = ((ASN1ObjectIdentifier) mapping.r(0)).s();
                String sd_p = ((ASN1ObjectIdentifier) mapping.r(1)).s();
                if (!hashMap.containsKey(id_p)) {
                    Set tmp = new HashSet();
                    tmp.add(sd_p);
                    hashMap.put(id_p, tmp);
                    hashSet.add(id_p);
                } else {
                    ((Set) hashMap.get(id_p)).add(sd_p);
                }
            }
            PKIXPolicyNode _validPolicyTree2 = _validPolicyTree;
            for (String id_p2 : hashSet) {
                if (policyMapping > 0) {
                    Iterator nodes_i = listArr[i3].iterator();
                    while (true) {
                        if (!nodes_i.hasNext()) {
                            idp_found = false;
                            break;
                        }
                        PKIXPolicyNode node = (PKIXPolicyNode) nodes_i.next();
                        if (node.getValidPolicy().equals(id_p2)) {
                            node.c = (Set) hashMap.get(id_p2);
                            idp_found = true;
                            break;
                        }
                    }
                    if (!idp_found) {
                        Iterator nodes_i2 = listArr[i3].iterator();
                        while (true) {
                            if (!nodes_i2.hasNext()) {
                                s_idp = hashSet;
                                m_idp = hashMap;
                                mappings = mappings2;
                                break;
                            }
                            PKIXPolicyNode node2 = (PKIXPolicyNode) nodes_i2.next();
                            if ("2.5.29.32.0".equals(node2.getValidPolicy())) {
                                try {
                                    Enumeration e2 = ((ASN1Sequence) CertPathValidatorUtilities.l(cert, b)).s();
                                    while (true) {
                                        if (!e2.hasMoreElements()) {
                                            pq = null;
                                            break;
                                        }
                                        try {
                                            PolicyInformation pinfo = PolicyInformation.e(e2.nextElement());
                                            if ("2.5.29.32.0".equals(pinfo.f().s())) {
                                                try {
                                                    pq = CertPathValidatorUtilities.o(pinfo.g());
                                                    break;
                                                } catch (CertPathValidatorException ex) {
                                                    throw new ExtCertPathValidatorException("Policy qualifier info set could not be decoded.", ex, certPath2, i2);
                                                }
                                            }
                                        } catch (Exception ex2) {
                                            throw new CertPathValidatorException("Policy information could not be decoded.", ex2, certPath2, i2);
                                        }
                                    }
                                    if (cert.getCriticalExtensionOIDs() != null) {
                                        ci = cert.getCriticalExtensionOIDs().contains(b);
                                    } else {
                                        ci = false;
                                    }
                                    PKIXPolicyNode p_node = (PKIXPolicyNode) node2.getParent();
                                    if ("2.5.29.32.0".equals(p_node.getValidPolicy())) {
                                        PKIXPolicyNode p_node2 = p_node;
                                        s_idp = hashSet;
                                        m_idp = hashMap;
                                        mappings = mappings2;
                                        PKIXPolicyNode c_node = new PKIXPolicyNode(new ArrayList(), i3, (Set) hashMap.get(id_p2), p_node2, pq, id_p2, ci);
                                        p_node2.a(c_node);
                                        listArr[i3].add(c_node);
                                    } else {
                                        String str = id_p2;
                                        s_idp = hashSet;
                                        m_idp = hashMap;
                                        mappings = mappings2;
                                    }
                                } catch (AnnotatedException e3) {
                                    String str2 = id_p2;
                                    Set set = hashSet;
                                    Map map = hashMap;
                                    ASN1Sequence aSN1Sequence = mappings2;
                                    throw new ExtCertPathValidatorException("Certificate policies extension could not be decoded.", e3, certPath2, i2);
                                }
                            } else {
                                Set set2 = hashSet;
                                Map map2 = hashMap;
                                ASN1Sequence aSN1Sequence2 = mappings2;
                            }
                        }
                    } else {
                        s_idp = hashSet;
                        m_idp = hashMap;
                        mappings = mappings2;
                    }
                } else {
                    String id_p3 = id_p2;
                    s_idp = hashSet;
                    m_idp = hashMap;
                    mappings = mappings2;
                    if (policyMapping <= 0) {
                        Iterator nodes_i3 = listArr[i3].iterator();
                        while (nodes_i3.hasNext()) {
                            PKIXPolicyNode node3 = (PKIXPolicyNode) nodes_i3.next();
                            String id_p4 = id_p3;
                            if (node3.getValidPolicy().equals(id_p4)) {
                                ((PKIXPolicyNode) node3.getParent()).d(node3);
                                nodes_i3.remove();
                                for (int k2 = i3 - 1; k2 >= 0; k2--) {
                                    List nodes = listArr[k2];
                                    PKIXPolicyNode _validPolicyTree3 = _validPolicyTree2;
                                    for (int l2 = 0; l2 < nodes.size(); l2++) {
                                        PKIXPolicyNode node22 = (PKIXPolicyNode) nodes.get(l2);
                                        if (!node22.c() && (_validPolicyTree3 = CertPathValidatorUtilities.y(_validPolicyTree3, listArr, node22)) == null) {
                                            break;
                                        }
                                    }
                                    _validPolicyTree2 = _validPolicyTree3;
                                }
                            }
                            id_p3 = id_p4;
                        }
                    }
                }
                hashSet = s_idp;
                hashMap = m_idp;
                mappings2 = mappings;
            }
            Set s_idp2 = hashSet;
            Map map3 = hashMap;
            ASN1Sequence aSN1Sequence3 = mappings2;
            return _validPolicyTree2;
        } catch (AnnotatedException ex3) {
            throw new ExtCertPathValidatorException("Policy mappings extension could not be decoded.", ex3, certPath2, i2);
        }
    }

    protected static void d(CertPath certPath, int index) {
        try {
            ASN1Sequence pm = ASN1Sequence.o(CertPathValidatorUtilities.l((X509Certificate) certPath.getCertificates().get(index), c));
            if (pm != null) {
                ASN1Sequence mappings = pm;
                int j2 = 0;
                while (j2 < mappings.size()) {
                    try {
                        ASN1Sequence mapping = ASN1Sequence.o(mappings.r(j2));
                        ASN1ObjectIdentifier issuerDomainPolicy = ASN1ObjectIdentifier.t(mapping.r(0));
                        ASN1ObjectIdentifier subjectDomainPolicy = ASN1ObjectIdentifier.t(mapping.r(1));
                        if ("2.5.29.32.0".equals(issuerDomainPolicy.s())) {
                            throw new CertPathValidatorException("IssuerDomainPolicy is anyPolicy", (Throwable) null, certPath, index);
                        } else if (!"2.5.29.32.0".equals(subjectDomainPolicy.s())) {
                            j2++;
                        } else {
                            throw new CertPathValidatorException("SubjectDomainPolicy is anyPolicy,", (Throwable) null, certPath, index);
                        }
                    } catch (Exception e2) {
                        throw new ExtCertPathValidatorException("Policy mappings extension contents could not be decoded.", e2, certPath, index);
                    }
                }
            }
        } catch (AnnotatedException ex) {
            throw new ExtCertPathValidatorException("Policy mappings extension could not be decoded.", ex, certPath, index);
        }
    }

    protected static void D(CertPath certPath, int index, PKIXPolicyNode validPolicyTree, int explicitPolicy) {
        if (explicitPolicy <= 0 && validPolicyTree == null) {
            throw new ExtCertPathValidatorException("No valid policy tree found when one expected.", (Throwable) null, certPath, index);
        }
    }

    protected static PKIXPolicyNode C(CertPath certPath, int index, PKIXPolicyNode validPolicyTree) {
        try {
            if (ASN1Sequence.o(CertPathValidatorUtilities.l((X509Certificate) certPath.getCertificates().get(index), b)) == null) {
                return null;
            }
            return validPolicyTree;
        } catch (AnnotatedException e2) {
            throw new ExtCertPathValidatorException("Could not read certificate policies extension from certificate.", e2, certPath, index);
        }
    }

    protected static void A(CertPath certPath, int index, PKIXNameConstraintValidator nameConstraintValidator) {
        CertPath certPath2 = certPath;
        int i2 = index;
        PKIXNameConstraintValidator pKIXNameConstraintValidator = nameConstraintValidator;
        List certificates = certPath.getCertificates();
        X509Certificate cert = (X509Certificate) certificates.get(i2);
        int n2 = certificates.size();
        int i3 = n2 - i2;
        if (!CertPathValidatorUtilities.v(cert) || i3 >= n2) {
            try {
                ASN1Sequence dns = ASN1Sequence.o(PrincipalUtils.e(cert).getEncoded());
                try {
                    pKIXNameConstraintValidator.k(dns);
                    pKIXNameConstraintValidator.d(dns);
                    try {
                        GeneralNames altName = GeneralNames.e(CertPathValidatorUtilities.l(cert, k));
                        RDN[] emails = X500Name.e(dns).i(BCStyle.E);
                        int eI = 0;
                        while (eI != emails.length) {
                            GeneralName emailAsGeneralName = new GeneralName(1, ((ASN1String) emails[eI].e().g()).a());
                            try {
                                pKIXNameConstraintValidator.i(emailAsGeneralName);
                                pKIXNameConstraintValidator.b(emailAsGeneralName);
                                eI++;
                            } catch (PKIXNameConstraintValidatorException ex) {
                                List<? extends Certificate> list = certificates;
                                throw new CertPathValidatorException("Subtree check for certificate subject alternative email failed.", ex, certPath2, i2);
                            }
                        }
                        List certs = certificates;
                        if (altName != null) {
                            try {
                                GeneralName[] genNames = altName.g();
                                int j2 = 0;
                                while (j2 < genNames.length) {
                                    try {
                                        pKIXNameConstraintValidator.i(genNames[j2]);
                                        pKIXNameConstraintValidator.b(genNames[j2]);
                                        j2++;
                                    } catch (PKIXNameConstraintValidatorException e2) {
                                        throw new CertPathValidatorException("Subtree check for certificate subject alternative name failed.", e2, certPath2, i2);
                                    }
                                }
                            } catch (Exception e3) {
                                throw new CertPathValidatorException("Subject alternative name contents could not be decoded.", e3, certPath2, i2);
                            }
                        }
                    } catch (Exception e4) {
                        List list2 = certificates;
                        throw new CertPathValidatorException("Subject alternative name extension could not be decoded.", e4, certPath2, i2);
                    }
                } catch (PKIXNameConstraintValidatorException e5) {
                    List list3 = certificates;
                    throw new CertPathValidatorException("Subtree check for certificate subject failed.", e5, certPath2, i2);
                }
            } catch (Exception e6) {
                List list4 = certificates;
                throw new CertPathValidatorException("Exception extracting subject name when checking subtrees.", e6, certPath2, i2);
            }
        } else {
            List<? extends Certificate> list5 = certificates;
        }
    }

    protected static PKIXPolicyNode B(CertPath certPath, int index, Set acceptablePolicies, PKIXPolicyNode validPolicyTree, List[] policyNodes, int inhibitAnyPolicy) {
        int i2;
        Enumeration e2;
        String _policy;
        int i3;
        HashSet hashSet;
        List _nodes;
        int k2;
        PKIXPolicyNode _node;
        CertPath certPath2 = certPath;
        int i4 = index;
        Set set = acceptablePolicies;
        List[] listArr = policyNodes;
        List certs = certPath.getCertificates();
        X509Certificate cert = (X509Certificate) certs.get(i4);
        int n2 = certs.size();
        int i5 = n2 - i4;
        try {
            ASN1Sequence certPolicies = ASN1Sequence.o(CertPathValidatorUtilities.l(cert, b));
            if (certPolicies == null || validPolicyTree == null) {
                return null;
            }
            Enumeration e3 = certPolicies.s();
            HashSet hashSet2 = new HashSet();
            while (e3.hasMoreElements()) {
                PolicyInformation pInfo = PolicyInformation.e(e3.nextElement());
                ASN1ObjectIdentifier pOid = pInfo.f();
                hashSet2.add(pOid.s());
                if (!"2.5.29.32.0".equals(pOid.s())) {
                    try {
                        Set pq = CertPathValidatorUtilities.o(pInfo.g());
                        if (!CertPathValidatorUtilities.w(i5, listArr, pOid, pq)) {
                            CertPathValidatorUtilities.x(i5, listArr, pOid, pq);
                        }
                    } catch (CertPathValidatorException ex) {
                        throw new ExtCertPathValidatorException("Policy qualifier info set could not be build.", ex, certPath2, i4);
                    }
                }
            }
            if (acceptablePolicies.isEmpty() || set.contains("2.5.29.32.0")) {
                acceptablePolicies.clear();
                set.addAll(hashSet2);
            } else {
                Set t1 = new HashSet();
                for (Object o2 : acceptablePolicies) {
                    if (hashSet2.contains(o2)) {
                        t1.add(o2);
                    }
                }
                acceptablePolicies.clear();
                set.addAll(t1);
            }
            if (inhibitAnyPolicy > 0 || (i5 < n2 && CertPathValidatorUtilities.v(cert))) {
                Enumeration e4 = certPolicies.s();
                while (true) {
                    if (!e4.hasMoreElements()) {
                        e2 = e4;
                        HashSet hashSet3 = hashSet2;
                        i2 = i5;
                        break;
                    }
                    PolicyInformation pInfo2 = PolicyInformation.e(e4.nextElement());
                    if ("2.5.29.32.0".equals(pInfo2.f().s())) {
                        Set _apq = CertPathValidatorUtilities.o(pInfo2.g());
                        List _nodes2 = listArr[i5 - 1];
                        int k3 = 0;
                        while (k3 < _nodes2.size()) {
                            PKIXPolicyNode _node2 = (PKIXPolicyNode) _nodes2.get(k3);
                            for (Object _tmp : _node2.getExpectedPolicies()) {
                                if (_tmp instanceof String) {
                                    _policy = (String) _tmp;
                                } else if (_tmp instanceof ASN1ObjectIdentifier) {
                                    _policy = ((ASN1ObjectIdentifier) _tmp).s();
                                } else {
                                    Object obj = _tmp;
                                    PKIXPolicyNode pKIXPolicyNode = _node2;
                                    int i6 = k3;
                                    List list = _nodes2;
                                    HashSet hashSet4 = hashSet2;
                                    int i7 = i5;
                                }
                                Iterator _childrenIter = _node2.getChildren();
                                boolean _found = false;
                                while (_childrenIter.hasNext()) {
                                    Enumeration e5 = e4;
                                    if (_policy.equals(((PKIXPolicyNode) _childrenIter.next()).getValidPolicy())) {
                                        _found = true;
                                    }
                                    e4 = e5;
                                }
                                Enumeration e6 = e4;
                                if (!_found) {
                                    Set _newChildExpectedPolicies = new HashSet();
                                    _newChildExpectedPolicies.add(_policy);
                                    Object obj2 = _tmp;
                                    PKIXPolicyNode _node3 = _node2;
                                    k2 = k3;
                                    _nodes = _nodes2;
                                    hashSet = hashSet2;
                                    i3 = i5;
                                    PKIXPolicyNode _newChild = new PKIXPolicyNode(new ArrayList(), i5, _newChildExpectedPolicies, _node3, _apq, _policy, false);
                                    _node = _node3;
                                    _node.a(_newChild);
                                    listArr[i3].add(_newChild);
                                } else {
                                    Object obj3 = _tmp;
                                    _node = _node2;
                                    k2 = k3;
                                    _nodes = _nodes2;
                                    hashSet = hashSet2;
                                    i3 = i5;
                                }
                                _node2 = _node;
                                e4 = e6;
                                k3 = k2;
                                _nodes2 = _nodes;
                                hashSet2 = hashSet;
                                i5 = i3;
                            }
                            PKIXPolicyNode pKIXPolicyNode2 = _node2;
                            List list2 = _nodes2;
                            HashSet hashSet5 = hashSet2;
                            int i8 = i5;
                            k3++;
                        }
                        e2 = e4;
                        int i9 = k3;
                        List list3 = _nodes2;
                        HashSet hashSet6 = hashSet2;
                        i2 = i5;
                    } else {
                        HashSet hashSet7 = hashSet2;
                        int i10 = i5;
                    }
                }
                Enumeration enumeration = e2;
            } else {
                HashSet hashSet8 = hashSet2;
                i2 = i5;
            }
            PKIXPolicyNode _validPolicyTree = validPolicyTree;
            for (int j2 = i2 - 1; j2 >= 0; j2--) {
                List nodes = listArr[j2];
                for (int k4 = 0; k4 < nodes.size(); k4++) {
                    PKIXPolicyNode node = (PKIXPolicyNode) nodes.get(k4);
                    if (!node.c() && (_validPolicyTree = CertPathValidatorUtilities.y(_validPolicyTree, listArr, node)) == null) {
                        break;
                    }
                }
            }
            Set criticalExtensionOids = cert.getCriticalExtensionOIDs();
            if (criticalExtensionOids != null) {
                boolean critical = criticalExtensionOids.contains(b);
                List nodes2 = listArr[i2];
                for (int j3 = 0; j3 < nodes2.size(); j3++) {
                    ((PKIXPolicyNode) nodes2.get(j3)).e(critical);
                }
            }
            return _validPolicyTree;
        } catch (AnnotatedException e7) {
            int i11 = i5;
            throw new ExtCertPathValidatorException("Could not read certificate policies extension from certificate.", e7, certPath2, i4);
        }
    }

    protected static void z(CertPath certPath, PKIXExtendedParameters paramsPKIX, int index, PublicKey workingPublicKey, boolean verificationAlreadyPerformed, X500Name workingIssuerName, X509Certificate sign, JcaJceHelper helper) {
        CertPath certPath2 = certPath;
        PKIXExtendedParameters pKIXExtendedParameters = paramsPKIX;
        int i2 = index;
        X500Name x500Name = workingIssuerName;
        List<? extends Certificate> certificates = certPath.getCertificates();
        X509Certificate cert = (X509Certificate) certificates.get(i2);
        if (!verificationAlreadyPerformed) {
            try {
                try {
                    CertPathValidatorUtilities.A(cert, workingPublicKey, paramsPKIX.s());
                } catch (GeneralSecurityException e2) {
                    e = e2;
                }
            } catch (GeneralSecurityException e3) {
                e = e3;
                PublicKey publicKey = workingPublicKey;
                throw new ExtCertPathValidatorException("Could not validate certificate signature.", e, certPath, i2);
            }
        } else {
            PublicKey publicKey2 = workingPublicKey;
        }
        try {
            cert.checkValidity(CertPathValidatorUtilities.q(pKIXExtendedParameters, certPath, i2));
            if (paramsPKIX.A()) {
                try {
                    b(paramsPKIX, cert, CertPathValidatorUtilities.q(pKIXExtendedParameters, certPath, i2), sign, workingPublicKey, certificates, helper);
                } catch (AnnotatedException e4) {
                    Throwable cause = e4;
                    if (e4.getCause() != null) {
                        cause = e4.getCause();
                    }
                    throw new ExtCertPathValidatorException(e4.getMessage(), cause, certPath, i2);
                }
            }
            if (!PrincipalUtils.b(cert).equals(x500Name)) {
                throw new ExtCertPathValidatorException("IssuerName(" + PrincipalUtils.b(cert) + ") does not match SubjectName(" + x500Name + ") of signing certificate.", (Throwable) null, certPath, i2);
            }
        } catch (CertificateExpiredException e5) {
            throw new ExtCertPathValidatorException("Could not validate certificate: " + e5.getMessage(), e5, certPath, i2);
        } catch (CertificateNotYetValidException e6) {
            throw new ExtCertPathValidatorException("Could not validate certificate: " + e6.getMessage(), e6, certPath, i2);
        } catch (AnnotatedException e7) {
            throw new ExtCertPathValidatorException("Could not validate time of certificate.", e7, certPath, i2);
        }
    }

    protected static int i(CertPath certPath, int index, int explicitPolicy) {
        try {
            ASN1Sequence pc = ASN1Sequence.o(CertPathValidatorUtilities.l((X509Certificate) certPath.getCertificates().get(index), h));
            if (pc != null) {
                Enumeration policyConstraints = pc.s();
                while (true) {
                    if (!policyConstraints.hasMoreElements()) {
                        break;
                    }
                    try {
                        ASN1TaggedObject constraint = ASN1TaggedObject.o(policyConstraints.nextElement());
                        if (constraint.r() == 0) {
                            int tmpInt = ASN1Integer.p(constraint, false).r().intValue();
                            if (tmpInt < explicitPolicy) {
                                return tmpInt;
                            }
                        }
                    } catch (IllegalArgumentException e2) {
                        throw new ExtCertPathValidatorException("Policy constraints extension contents cannot be decoded.", e2, certPath, index);
                    }
                }
            }
            return explicitPolicy;
        } catch (Exception e3) {
            throw new ExtCertPathValidatorException("Policy constraints extension cannot be decoded.", e3, certPath, index);
        }
    }

    protected static int j(CertPath certPath, int index, int policyMapping) {
        try {
            ASN1Sequence pc = ASN1Sequence.o(CertPathValidatorUtilities.l((X509Certificate) certPath.getCertificates().get(index), h));
            if (pc != null) {
                Enumeration policyConstraints = pc.s();
                while (true) {
                    if (!policyConstraints.hasMoreElements()) {
                        break;
                    }
                    try {
                        ASN1TaggedObject constraint = ASN1TaggedObject.o(policyConstraints.nextElement());
                        if (constraint.r() == 1) {
                            int tmpInt = ASN1Integer.p(constraint, false).r().intValue();
                            if (tmpInt < policyMapping) {
                                return tmpInt;
                            }
                        }
                    } catch (IllegalArgumentException e2) {
                        throw new ExtCertPathValidatorException("Policy constraints extension contents cannot be decoded.", e2, certPath, index);
                    }
                }
            }
            return policyMapping;
        } catch (Exception e3) {
            throw new ExtCertPathValidatorException("Policy constraints extension cannot be decoded.", e3, certPath, index);
        }
    }

    protected static void e(CertPath certPath, int index, PKIXNameConstraintValidator nameConstraintValidator) {
        NameConstraints nc = null;
        try {
            ASN1Sequence ncSeq = ASN1Sequence.o(CertPathValidatorUtilities.l((X509Certificate) certPath.getCertificates().get(index), l));
            if (ncSeq != null) {
                nc = NameConstraints.h(ncSeq);
            }
            if (nc != null) {
                GeneralSubtree[] permitted = nc.i();
                if (permitted != null) {
                    try {
                        nameConstraintValidator.D(permitted);
                    } catch (Exception ex) {
                        throw new ExtCertPathValidatorException("Permitted subtrees cannot be build from name constraints extension.", ex, certPath, index);
                    }
                }
                GeneralSubtree[] excluded = nc.g();
                if (excluded != null) {
                    int i2 = 0;
                    while (i2 != excluded.length) {
                        try {
                            nameConstraintValidator.a(excluded[i2]);
                            i2++;
                        } catch (Exception ex2) {
                            throw new ExtCertPathValidatorException("Excluded subtrees cannot be build from name constraints extension.", ex2, certPath, index);
                        }
                    }
                }
            }
        } catch (Exception e2) {
            throw new ExtCertPathValidatorException("Name constraints extension could not be decoded.", e2, certPath, index);
        }
    }

    private static void a(DistributionPoint dp, PKIXExtendedParameters paramsPKIX, X509Certificate cert, Date validDate, X509Certificate defaultCRLSignCert, PublicKey defaultCRLSignKey, CertStatus certStatus, ReasonsMask reasonMask, List certPathCerts, JcaJceHelper helper) {
        Set crls;
        ReasonsMask reasonsMask;
        Set criticalExtensions;
        DistributionPoint distributionPoint = dp;
        PKIXExtendedParameters pKIXExtendedParameters = paramsPKIX;
        X509Certificate x509Certificate = cert;
        Date date = validDate;
        CertStatus certStatus2 = certStatus;
        ReasonsMask reasonsMask2 = reasonMask;
        Date currentDate = new Date(System.currentTimeMillis());
        if (validDate.getTime() <= currentDate.getTime()) {
            Set crls2 = CertPathValidatorUtilities.j(distributionPoint, x509Certificate, currentDate, pKIXExtendedParameters);
            Iterator crl_iter = crls2.iterator();
            boolean validCrlFound = false;
            AnnotatedException lastException = null;
            while (crl_iter.hasNext() && certStatus.a() == 11 && !reasonMask.e()) {
                try {
                    X509CRL crl = (X509CRL) crl_iter.next();
                    ReasonsMask interimReasonsMask = t(crl, distributionPoint);
                    if (!interimReasonsMask.c(reasonsMask2)) {
                        continue;
                    } else {
                        crls = crls2;
                        ReasonsMask interimReasonsMask2 = interimReasonsMask;
                        try {
                            PublicKey key = v(crl, u(crl, cert, defaultCRLSignCert, defaultCRLSignKey, paramsPKIX, certPathCerts, helper));
                            X509CRL deltaCRL = null;
                            Date validityDate = currentDate;
                            if (paramsPKIX.o() != null) {
                                validityDate = paramsPKIX.o();
                            }
                            if (paramsPKIX.B()) {
                                deltaCRL = w(CertPathValidatorUtilities.k(validityDate, crl, paramsPKIX.m(), paramsPKIX.k()), key);
                            }
                            if (paramsPKIX.v() != 1) {
                                if (cert.getNotAfter().getTime() < crl.getThisUpdate().getTime()) {
                                    throw new AnnotatedException("No valid CRL for current time found.");
                                }
                            }
                            q(distributionPoint, x509Certificate, crl);
                            r(distributionPoint, x509Certificate, crl);
                            s(deltaCRL, crl, pKIXExtendedParameters);
                            x(date, deltaCRL, x509Certificate, certStatus2, pKIXExtendedParameters);
                            y(date, crl, x509Certificate, certStatus2);
                            if (certStatus.a() == 8) {
                                certStatus2.c(11);
                            }
                            reasonsMask = reasonMask;
                            try {
                                reasonsMask.a(interimReasonsMask2);
                                Set criticalExtensions2 = crl.getCriticalExtensionOIDs();
                                if (criticalExtensions2 != null) {
                                    Set criticalExtensions3 = new HashSet(criticalExtensions2);
                                    criticalExtensions3.remove(Extension.p3.s());
                                    criticalExtensions3.remove(Extension.p2.s());
                                    if (!criticalExtensions3.isEmpty()) {
                                        throw new AnnotatedException("CRL contains unsupported critical extensions.");
                                    }
                                }
                                if (!(deltaCRL == null || (criticalExtensions = deltaCRL.getCriticalExtensionOIDs()) == null)) {
                                    Set criticalExtensions4 = new HashSet(criticalExtensions);
                                    criticalExtensions4.remove(Extension.p3.s());
                                    criticalExtensions4.remove(Extension.p2.s());
                                    if (!criticalExtensions4.isEmpty()) {
                                        throw new AnnotatedException("Delta CRL contains unsupported critical extension.");
                                    }
                                }
                                validCrlFound = true;
                            } catch (AnnotatedException e2) {
                                e = e2;
                                lastException = e;
                                reasonsMask2 = reasonsMask;
                                crls2 = crls;
                            }
                        } catch (AnnotatedException e3) {
                            e = e3;
                            reasonsMask = reasonMask;
                            lastException = e;
                            reasonsMask2 = reasonsMask;
                            crls2 = crls;
                        }
                        reasonsMask2 = reasonsMask;
                        crls2 = crls;
                    }
                } catch (AnnotatedException e4) {
                    e = e4;
                    reasonsMask = reasonsMask2;
                    crls = crls2;
                    lastException = e;
                    reasonsMask2 = reasonsMask;
                    crls2 = crls;
                }
            }
            ReasonsMask reasonsMask3 = reasonsMask2;
            Set set = crls2;
            if (!validCrlFound) {
                throw lastException;
            }
            return;
        }
        ReasonsMask reasonsMask4 = reasonsMask2;
        throw new AnnotatedException("Validation time is in future.");
    }

    /* JADX WARNING: Removed duplicated region for block: B:64:0x0131  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x013e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static void b(org.spongycastle.jcajce.PKIXExtendedParameters r24, java.security.cert.X509Certificate r25, java.util.Date r26, java.security.cert.X509Certificate r27, java.security.PublicKey r28, java.util.List r29, org.spongycastle.jcajce.util.JcaJceHelper r30) {
        /*
            r1 = 0
            r2 = 0
            java.lang.String r0 = j     // Catch:{ Exception -> 0x01c2 }
            r15 = r25
            org.spongycastle.asn1.ASN1Primitive r0 = org.spongycastle.jce.provider.CertPathValidatorUtilities.l(r15, r0)     // Catch:{ Exception -> 0x01c2 }
            org.spongycastle.asn1.x509.CRLDistPoint r0 = org.spongycastle.asn1.x509.CRLDistPoint.f(r0)     // Catch:{ Exception -> 0x01c2 }
            r2 = r0
            org.spongycastle.jcajce.PKIXExtendedParameters$Builder r0 = new org.spongycastle.jcajce.PKIXExtendedParameters$Builder
            r14 = r24
            r0.<init>((org.spongycastle.jcajce.PKIXExtendedParameters) r14)
            r13 = r0
            java.util.Map r0 = r24.q()     // Catch:{ AnnotatedException -> 0x01b6 }
            java.util.List r0 = org.spongycastle.jce.provider.CertPathValidatorUtilities.f(r2, r0)     // Catch:{ AnnotatedException -> 0x01b6 }
            java.util.Iterator r3 = r0.iterator()     // Catch:{ AnnotatedException -> 0x01b6 }
        L_0x0024:
            boolean r4 = r3.hasNext()     // Catch:{ AnnotatedException -> 0x01b6 }
            if (r4 == 0) goto L_0x003a
            java.lang.Object r4 = r3.next()     // Catch:{ AnnotatedException -> 0x0034 }
            org.spongycastle.jcajce.PKIXCRLStore r4 = (org.spongycastle.jcajce.PKIXCRLStore) r4     // Catch:{ AnnotatedException -> 0x0034 }
            r13.l(r4)     // Catch:{ AnnotatedException -> 0x0034 }
            goto L_0x0024
        L_0x0034:
            r0 = move-exception
            r22 = r2
            r5 = r13
            goto L_0x01ba
        L_0x003a:
            org.spongycastle.jce.provider.CertStatus r0 = new org.spongycastle.jce.provider.CertStatus
            r0.<init>()
            r12 = r0
            org.spongycastle.jce.provider.ReasonsMask r10 = new org.spongycastle.jce.provider.ReasonsMask
            r10.<init>()
            org.spongycastle.jcajce.PKIXExtendedParameters r21 = r13.n()
            r3 = 0
            r11 = 11
            if (r2 == 0) goto L_0x00c6
            r4 = 0
            org.spongycastle.asn1.x509.DistributionPoint[] r0 = r2.e()     // Catch:{ Exception -> 0x00b7 }
            r9 = r0
            if (r9 == 0) goto L_0x00af
            r0 = 0
            r16 = r1
            r17 = r3
            r1 = r0
        L_0x005e:
            int r0 = r9.length
            if (r1 >= r0) goto L_0x00a3
            int r0 = r12.a()
            if (r0 != r11) goto L_0x00a3
            boolean r0 = r10.e()
            if (r0 != 0) goto L_0x00a3
            r3 = r9[r1]     // Catch:{ AnnotatedException -> 0x008e }
            r4 = r21
            r5 = r25
            r6 = r26
            r7 = r27
            r8 = r28
            r18 = r9
            r9 = r12
            r22 = r2
            r2 = r11
            r11 = r29
            r23 = r12
            r12 = r30
            a(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)     // Catch:{ AnnotatedException -> 0x008c }
            r0 = 1
            r17 = r0
            goto L_0x0099
        L_0x008c:
            r0 = move-exception
            goto L_0x0096
        L_0x008e:
            r0 = move-exception
            r22 = r2
            r18 = r9
            r2 = r11
            r23 = r12
        L_0x0096:
            r3 = r0
            r16 = r3
        L_0x0099:
            int r1 = r1 + 1
            r11 = r2
            r9 = r18
            r2 = r22
            r12 = r23
            goto L_0x005e
        L_0x00a3:
            r22 = r2
            r18 = r9
            r2 = r11
            r23 = r12
            r1 = r16
            r3 = r17
            goto L_0x00cb
        L_0x00af:
            r22 = r2
            r18 = r9
            r2 = r11
            r23 = r12
            goto L_0x00cb
        L_0x00b7:
            r0 = move-exception
            r22 = r2
            r23 = r12
            r2 = r0
            r0 = r2
            org.spongycastle.jce.provider.AnnotatedException r2 = new org.spongycastle.jce.provider.AnnotatedException
            java.lang.String r5 = "Distribution points could not be read."
            r2.<init>(r5, r0)
            throw r2
        L_0x00c6:
            r22 = r2
            r2 = r11
            r23 = r12
        L_0x00cb:
            int r0 = r23.a()
            if (r0 != r2) goto L_0x012e
            boolean r0 = r10.e()
            if (r0 != 0) goto L_0x012e
            r4 = 0
            org.spongycastle.asn1.ASN1InputStream r0 = new org.spongycastle.asn1.ASN1InputStream     // Catch:{ Exception -> 0x0121 }
            org.spongycastle.asn1.x500.X500Name r5 = org.spongycastle.jce.provider.PrincipalUtils.b(r25)     // Catch:{ Exception -> 0x0121 }
            byte[] r5 = r5.getEncoded()     // Catch:{ Exception -> 0x0121 }
            r0.<init>((byte[]) r5)     // Catch:{ Exception -> 0x0121 }
            org.spongycastle.asn1.ASN1Primitive r0 = r0.r()     // Catch:{ Exception -> 0x0121 }
            org.spongycastle.asn1.x509.DistributionPoint r11 = new org.spongycastle.asn1.x509.DistributionPoint     // Catch:{ AnnotatedException -> 0x011e }
            org.spongycastle.asn1.x509.DistributionPointName r4 = new org.spongycastle.asn1.x509.DistributionPointName     // Catch:{ AnnotatedException -> 0x011e }
            r5 = 0
            org.spongycastle.asn1.x509.GeneralNames r6 = new org.spongycastle.asn1.x509.GeneralNames     // Catch:{ AnnotatedException -> 0x011e }
            org.spongycastle.asn1.x509.GeneralName r7 = new org.spongycastle.asn1.x509.GeneralName     // Catch:{ AnnotatedException -> 0x011e }
            r8 = 4
            r7.<init>((int) r8, (org.spongycastle.asn1.ASN1Encodable) r0)     // Catch:{ AnnotatedException -> 0x011e }
            r6.<init>((org.spongycastle.asn1.x509.GeneralName) r7)     // Catch:{ AnnotatedException -> 0x011e }
            r4.<init>(r5, r6)     // Catch:{ AnnotatedException -> 0x011e }
            r5 = 0
            r11.<init>(r4, r5, r5)     // Catch:{ AnnotatedException -> 0x011e }
            java.lang.Object r4 = r24.clone()     // Catch:{ AnnotatedException -> 0x011e }
            r12 = r4
            org.spongycastle.jcajce.PKIXExtendedParameters r12 = (org.spongycastle.jcajce.PKIXExtendedParameters) r12     // Catch:{ AnnotatedException -> 0x011e }
            r5 = r13
            r13 = r25
            r14 = r26
            r15 = r27
            r16 = r28
            r17 = r23
            r18 = r10
            r19 = r29
            r20 = r30
            a(r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)     // Catch:{ AnnotatedException -> 0x012b }
            r3 = 1
            goto L_0x012f
        L_0x011e:
            r0 = move-exception
            r5 = r13
            goto L_0x012c
        L_0x0121:
            r0 = move-exception
            r5 = r13
            org.spongycastle.jce.provider.AnnotatedException r6 = new org.spongycastle.jce.provider.AnnotatedException     // Catch:{ AnnotatedException -> 0x012b }
            java.lang.String r7 = "Issuer from certificate for CRL could not be reencoded."
            r6.<init>(r7, r0)     // Catch:{ AnnotatedException -> 0x012b }
            throw r6     // Catch:{ AnnotatedException -> 0x012b }
        L_0x012b:
            r0 = move-exception
        L_0x012c:
            r1 = r0
            goto L_0x012f
        L_0x012e:
            r5 = r13
        L_0x012f:
            if (r3 != 0) goto L_0x013e
            boolean r0 = r1 instanceof org.spongycastle.jce.provider.AnnotatedException
            if (r0 == 0) goto L_0x0136
            throw r1
        L_0x0136:
            org.spongycastle.jce.provider.AnnotatedException r0 = new org.spongycastle.jce.provider.AnnotatedException
            java.lang.String r2 = "No valid CRL found."
            r0.<init>(r2, r1)
            throw r0
        L_0x013e:
            int r0 = r23.a()
            if (r0 != r2) goto L_0x0169
            boolean r0 = r10.e()
            r4 = 12
            if (r0 != 0) goto L_0x0158
            int r0 = r23.a()
            if (r0 != r2) goto L_0x0158
            r2 = r23
            r2.c(r4)
            goto L_0x015a
        L_0x0158:
            r2 = r23
        L_0x015a:
            int r0 = r2.a()
            if (r0 == r4) goto L_0x0161
            return
        L_0x0161:
            org.spongycastle.jce.provider.AnnotatedException r0 = new org.spongycastle.jce.provider.AnnotatedException
            java.lang.String r4 = "Certificate status could not be determined."
            r0.<init>(r4)
            throw r0
        L_0x0169:
            r2 = r23
            java.text.SimpleDateFormat r0 = new java.text.SimpleDateFormat
            java.lang.String r4 = "yyyy-MM-dd HH:mm:ss Z"
            r0.<init>(r4)
            java.lang.String r4 = "UTC"
            java.util.TimeZone r4 = java.util.TimeZone.getTimeZone(r4)
            r0.setTimeZone(r4)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "Certificate revocation after "
            r4.append(r6)
            java.util.Date r6 = r2.b()
            java.lang.String r6 = r0.format(r6)
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r4)
            java.lang.String r7 = ", reason: "
            r6.append(r7)
            java.lang.String[] r7 = p
            int r8 = r2.a()
            r7 = r7[r8]
            r6.append(r7)
            java.lang.String r4 = r6.toString()
            org.spongycastle.jce.provider.AnnotatedException r6 = new org.spongycastle.jce.provider.AnnotatedException
            r6.<init>(r4)
            throw r6
        L_0x01b6:
            r0 = move-exception
            r22 = r2
            r5 = r13
        L_0x01ba:
            org.spongycastle.jce.provider.AnnotatedException r2 = new org.spongycastle.jce.provider.AnnotatedException
            java.lang.String r3 = "No additional CRL locations could be decoded from CRL distribution point extension."
            r2.<init>(r3, r0)
            throw r2
        L_0x01c2:
            r0 = move-exception
            org.spongycastle.jce.provider.AnnotatedException r3 = new org.spongycastle.jce.provider.AnnotatedException
            java.lang.String r4 = "CRL distribution point extension could not be read."
            r3.<init>(r4, r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jce.provider.RFC3280CertPathUtilities.b(org.spongycastle.jcajce.PKIXExtendedParameters, java.security.cert.X509Certificate, java.util.Date, java.security.cert.X509Certificate, java.security.PublicKey, java.util.List, org.spongycastle.jcajce.util.JcaJceHelper):void");
    }

    protected static int k(CertPath certPath, int index, int inhibitAnyPolicy) {
        int _inhibitAnyPolicy;
        try {
            ASN1Integer iap = ASN1Integer.o(CertPathValidatorUtilities.l((X509Certificate) certPath.getCertificates().get(index), d));
            if (iap == null || (_inhibitAnyPolicy = iap.r().intValue()) >= inhibitAnyPolicy) {
                return inhibitAnyPolicy;
            }
            return _inhibitAnyPolicy;
        } catch (Exception e2) {
            throw new ExtCertPathValidatorException("Inhibit any-policy extension cannot be decoded.", e2, certPath, index);
        }
    }

    protected static void l(CertPath certPath, int index) {
        try {
            BasicConstraints bc = BasicConstraints.e(CertPathValidatorUtilities.l((X509Certificate) certPath.getCertificates().get(index), i));
            if (bc == null) {
                throw new CertPathValidatorException("Intermediate certificate lacks BasicConstraints");
            } else if (!bc.g()) {
                throw new CertPathValidatorException("Not a CA certificate");
            }
        } catch (Exception e2) {
            throw new ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", e2, certPath, index);
        }
    }

    protected static int m(CertPath certPath, int index, int maxPathLength) {
        if (CertPathValidatorUtilities.v((X509Certificate) certPath.getCertificates().get(index))) {
            return maxPathLength;
        }
        if (maxPathLength > 0) {
            return maxPathLength - 1;
        }
        throw new ExtCertPathValidatorException("Max path length not greater than zero", (Throwable) null, certPath, index);
    }

    protected static int n(CertPath certPath, int index, int maxPathLength) {
        BigInteger _pathLengthConstraint;
        int _plc;
        try {
            BasicConstraints bc = BasicConstraints.e(CertPathValidatorUtilities.l((X509Certificate) certPath.getCertificates().get(index), i));
            if (bc == null || (_pathLengthConstraint = bc.f()) == null || (_plc = _pathLengthConstraint.intValue()) >= maxPathLength) {
                return maxPathLength;
            }
            return _plc;
        } catch (Exception e2) {
            throw new ExtCertPathValidatorException("Basic constraints extension cannot be decoded.", e2, certPath, index);
        }
    }

    protected static void o(CertPath certPath, int index) {
        boolean[] _usage = ((X509Certificate) certPath.getCertificates().get(index)).getKeyUsage();
        if (_usage != null && !_usage[5]) {
            throw new ExtCertPathValidatorException("Issuer certificate keyusage extension is critical and does not permit key signing.", (Throwable) null, certPath, index);
        }
    }

    protected static void p(CertPath certPath, int index, Set criticalExtensions, List pathCheckers) {
        X509Certificate cert = (X509Certificate) certPath.getCertificates().get(index);
        Iterator tmpIter = pathCheckers.iterator();
        while (tmpIter.hasNext()) {
            try {
                ((PKIXCertPathChecker) tmpIter.next()).check(cert, criticalExtensions);
            } catch (CertPathValidatorException e2) {
                throw new CertPathValidatorException(e2.getMessage(), e2.getCause(), certPath, index);
            }
        }
        if (!criticalExtensions.isEmpty()) {
            throw new ExtCertPathValidatorException("Certificate has unsupported critical extension: " + criticalExtensions, (Throwable) null, certPath, index);
        }
    }

    protected static int f(CertPath certPath, int index, int explicitPolicy) {
        if (CertPathValidatorUtilities.v((X509Certificate) certPath.getCertificates().get(index)) || explicitPolicy == 0) {
            return explicitPolicy;
        }
        return explicitPolicy - 1;
    }

    protected static int g(CertPath certPath, int index, int policyMapping) {
        if (CertPathValidatorUtilities.v((X509Certificate) certPath.getCertificates().get(index)) || policyMapping == 0) {
            return policyMapping;
        }
        return policyMapping - 1;
    }

    protected static int h(CertPath certPath, int index, int inhibitAnyPolicy) {
        if (CertPathValidatorUtilities.v((X509Certificate) certPath.getCertificates().get(index)) || inhibitAnyPolicy == 0) {
            return inhibitAnyPolicy;
        }
        return inhibitAnyPolicy - 1;
    }

    protected static int E(int explicitPolicy, X509Certificate cert) {
        if (CertPathValidatorUtilities.v(cert) || explicitPolicy == 0) {
            return explicitPolicy;
        }
        return explicitPolicy - 1;
    }

    protected static int F(CertPath certPath, int index, int explicitPolicy) {
        try {
            ASN1Sequence pc = ASN1Sequence.o(CertPathValidatorUtilities.l((X509Certificate) certPath.getCertificates().get(index), h));
            if (pc != null) {
                Enumeration policyConstraints = pc.s();
                while (policyConstraints.hasMoreElements()) {
                    ASN1TaggedObject constraint = (ASN1TaggedObject) policyConstraints.nextElement();
                    switch (constraint.r()) {
                        case 0:
                            try {
                                if (ASN1Integer.p(constraint, false).r().intValue() != 0) {
                                    break;
                                } else {
                                    return 0;
                                }
                            } catch (Exception e2) {
                                throw new ExtCertPathValidatorException("Policy constraints requireExplicitPolicy field could not be decoded.", e2, certPath, index);
                            }
                    }
                }
            }
            return explicitPolicy;
        } catch (AnnotatedException e3) {
            throw new ExtCertPathValidatorException("Policy constraints could not be decoded.", e3, certPath, index);
        }
    }

    protected static void G(CertPath certPath, int index, List pathCheckers, Set criticalExtensions) {
        X509Certificate cert = (X509Certificate) certPath.getCertificates().get(index);
        Iterator tmpIter = pathCheckers.iterator();
        while (tmpIter.hasNext()) {
            try {
                ((PKIXCertPathChecker) tmpIter.next()).check(cert, criticalExtensions);
            } catch (CertPathValidatorException e2) {
                throw new ExtCertPathValidatorException("Additional certificate path checker failed.", e2, certPath, index);
            }
        }
        if (!criticalExtensions.isEmpty()) {
            throw new ExtCertPathValidatorException("Certificate has unsupported critical extension: " + criticalExtensions, (Throwable) null, certPath, index);
        }
    }

    protected static PKIXPolicyNode H(CertPath certPath, PKIXExtendedParameters paramsPKIX, Set userInitialPolicySet, int index, List[] policyNodes, PKIXPolicyNode validPolicyTree, Set acceptablePolicies) {
        PKIXPolicyNode validPolicyTree2;
        CertPath certPath2 = certPath;
        int i2 = index;
        List[] listArr = policyNodes;
        int n2 = certPath.getCertificates().size();
        if (validPolicyTree == null) {
            if (!paramsPKIX.y()) {
                Set set = userInitialPolicySet;
                PKIXPolicyNode pKIXPolicyNode = validPolicyTree;
                Set set2 = acceptablePolicies;
                return null;
            }
            throw new ExtCertPathValidatorException("Explicit policy requested but none available.", (Throwable) null, certPath, i2);
        } else if (CertPathValidatorUtilities.s(userInitialPolicySet)) {
            if (!paramsPKIX.y()) {
                Set set3 = acceptablePolicies;
                validPolicyTree2 = validPolicyTree;
            } else if (!acceptablePolicies.isEmpty()) {
                Set<PKIXPolicyNode> _validPolicyNodeSet = new HashSet<>();
                for (List _nodeDepth : listArr) {
                    for (int k2 = 0; k2 < _nodeDepth.size(); k2++) {
                        PKIXPolicyNode _node = (PKIXPolicyNode) _nodeDepth.get(k2);
                        if ("2.5.29.32.0".equals(_node.getValidPolicy())) {
                            Iterator _iter = _node.getChildren();
                            while (_iter.hasNext()) {
                                _validPolicyNodeSet.add(_iter.next());
                            }
                        }
                    }
                }
                for (PKIXPolicyNode _node2 : _validPolicyNodeSet) {
                    acceptablePolicies.contains(_node2.getValidPolicy());
                }
                Set set4 = acceptablePolicies;
                validPolicyTree2 = validPolicyTree;
                for (int j2 = n2 - 1; j2 >= 0; j2--) {
                    List nodes = listArr[j2];
                    for (int k3 = 0; k3 < nodes.size(); k3++) {
                        PKIXPolicyNode node = (PKIXPolicyNode) nodes.get(k3);
                        if (!node.c()) {
                            validPolicyTree2 = CertPathValidatorUtilities.y(validPolicyTree2, listArr, node);
                        }
                    }
                }
            } else {
                Set set5 = acceptablePolicies;
                throw new ExtCertPathValidatorException("Explicit policy requested but none available.", (Throwable) null, certPath, i2);
            }
            Set set6 = userInitialPolicySet;
            return validPolicyTree2;
        } else {
            Set set7 = acceptablePolicies;
            Set<PKIXPolicyNode> _validPolicyNodeSet2 = new HashSet<>();
            for (List _nodeDepth2 : listArr) {
                for (int k4 = 0; k4 < _nodeDepth2.size(); k4++) {
                    PKIXPolicyNode _node3 = (PKIXPolicyNode) _nodeDepth2.get(k4);
                    if ("2.5.29.32.0".equals(_node3.getValidPolicy())) {
                        Iterator _iter2 = _node3.getChildren();
                        while (_iter2.hasNext()) {
                            PKIXPolicyNode _c_node = (PKIXPolicyNode) _iter2.next();
                            if (!"2.5.29.32.0".equals(_c_node.getValidPolicy())) {
                                _validPolicyNodeSet2.add(_c_node);
                            }
                        }
                    }
                }
            }
            PKIXPolicyNode validPolicyTree3 = validPolicyTree;
            for (PKIXPolicyNode _node4 : _validPolicyNodeSet2) {
                if (!userInitialPolicySet.contains(_node4.getValidPolicy())) {
                    validPolicyTree3 = CertPathValidatorUtilities.y(validPolicyTree3, listArr, _node4);
                }
            }
            Set set8 = userInitialPolicySet;
            if (validPolicyTree3 != null) {
                for (int j3 = n2 - 1; j3 >= 0; j3--) {
                    List nodes2 = listArr[j3];
                    for (int k5 = 0; k5 < nodes2.size(); k5++) {
                        PKIXPolicyNode node2 = (PKIXPolicyNode) nodes2.get(k5);
                        if (!node2.c()) {
                            validPolicyTree3 = CertPathValidatorUtilities.y(validPolicyTree3, listArr, node2);
                        }
                    }
                }
            }
            return validPolicyTree3;
        }
    }
}
