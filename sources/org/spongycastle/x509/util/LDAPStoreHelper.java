package org.spongycastle.x509.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.security.auth.x500.X500Principal;
import org.slf4j.e;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.CertificatePair;
import org.spongycastle.jce.X509LDAPCertStoreParameters;
import org.spongycastle.jce.provider.X509AttrCertParser;
import org.spongycastle.jce.provider.X509CRLParser;
import org.spongycastle.jce.provider.X509CertPairParser;
import org.spongycastle.jce.provider.X509CertParser;
import org.spongycastle.util.StoreException;
import org.spongycastle.x509.X509AttributeCertStoreSelector;
import org.spongycastle.x509.X509AttributeCertificate;
import org.spongycastle.x509.X509CRLStoreSelector;
import org.spongycastle.x509.X509CertPairStoreSelector;
import org.spongycastle.x509.X509CertStoreSelector;
import org.spongycastle.x509.X509CertificatePair;

public class LDAPStoreHelper {
    private static String a = "com.sun.jndi.ldap.LdapCtxFactory";
    private static String b = "ignore";
    private static int c = 32;
    private static long d = 60000;
    private X509LDAPCertStoreParameters e;
    private Map f;

    private DirContext e() {
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", a);
        props.setProperty("java.naming.batchsize", "0");
        props.setProperty("java.naming.provider.url", this.e.K());
        props.setProperty("java.naming.factory.url.pkgs", "com.sun.jndi.url");
        props.setProperty("java.naming.referral", b);
        props.setProperty("java.naming.security.authentication", "none");
        return new InitialDirContext(props);
    }

    private String y(String subject, String dNAttributeName) {
        int end;
        String temp = subject;
        String lowerCase = temp.toLowerCase();
        int begin = lowerCase.indexOf(dNAttributeName.toLowerCase() + "=");
        if (begin == -1) {
            return "";
        }
        String temp2 = temp.substring(dNAttributeName.length() + begin);
        int end2 = temp2.indexOf(44);
        if (end2 == -1) {
            end2 = temp2.length();
        }
        while (temp2.charAt(end - 1) == '\\') {
            end = temp2.indexOf(44, end + 1);
            if (end == -1) {
                end = temp2.length();
            }
        }
        String temp3 = temp2.substring(0, end);
        String temp4 = temp3.substring(temp3.indexOf(61) + 1);
        if (temp4.charAt(0) == ' ') {
            temp4 = temp4.substring(1);
        }
        if (temp4.startsWith("\"")) {
            temp4 = temp4.substring(1);
        }
        if (temp4.endsWith("\"")) {
            return temp4.substring(0, temp4.length() - 1);
        }
        return temp4;
    }

    private Set h(List list, X509CertStoreSelector xselector) {
        Set certSet = new HashSet();
        Iterator it = list.iterator();
        X509CertParser parser = new X509CertParser();
        while (it.hasNext()) {
            try {
                parser.a(new ByteArrayInputStream((byte[]) it.next()));
                X509Certificate cert = (X509Certificate) parser.b();
                if (xselector.P0(cert)) {
                    certSet.add(cert);
                }
            } catch (Exception e2) {
            }
        }
        return certSet;
    }

    private List d(X509CertStoreSelector xselector, String[] attrs, String[] attrNames, String[] subjectAttributeNames) {
        List list = new ArrayList();
        String serial = null;
        String subject = w(xselector);
        if (xselector.getSerialNumber() != null) {
            serial = xselector.getSerialNumber().toString();
        }
        if (xselector.getCertificate() != null) {
            subject = xselector.getCertificate().getSubjectX500Principal().getName("RFC1779");
            serial = xselector.getCertificate().getSerialNumber().toString();
        }
        if (subject != null) {
            for (String y : subjectAttributeNames) {
                list.addAll(z(attrNames, e.ANY_MARKER + y(subject, y) + e.ANY_MARKER, attrs));
            }
        }
        if (!(serial == null || this.e.M() == null)) {
            list.addAll(z(A(this.e.M()), serial, attrs));
        }
        if (serial == null && subject == null) {
            list.addAll(z(attrNames, e.ANY_MARKER, attrs));
        }
        return list;
    }

    private List j(X509CertPairStoreSelector xselector, String[] attrs, String[] attrNames, String[] subjectAttributeNames) {
        List list = new ArrayList();
        String subject = null;
        if (xselector.b() != null) {
            subject = w(xselector.b());
        }
        if (!(xselector.a() == null || xselector.a().a() == null)) {
            subject = xselector.a().a().getSubjectX500Principal().getName("RFC1779");
        }
        if (subject != null) {
            for (String y : subjectAttributeNames) {
                list.addAll(z(attrNames, e.ANY_MARKER + y(subject, y) + e.ANY_MARKER, attrs));
            }
        }
        if (subject == null) {
            list.addAll(z(attrNames, e.ANY_MARKER, attrs));
        }
        return list;
    }

    private List b(X509AttributeCertStoreSelector xselector, String[] attrs, String[] attrNames, String[] subjectAttributeNames) {
        List list = new ArrayList();
        String subject = null;
        Collection<String> serials = new HashSet<>();
        Principal[] principals = null;
        if (xselector.c() != null) {
            if (xselector.c().i() != null) {
                serials.add(xselector.c().i().toString());
            }
            if (xselector.c().c() != null) {
                principals = xselector.c().c();
            }
        }
        if (xselector.a() != null) {
            if (xselector.a().a().c() != null) {
                principals = xselector.a().a().c();
            }
            serials.add(xselector.a().getSerialNumber().toString());
        }
        if (principals != null) {
            if (principals[0] instanceof X500Principal) {
                subject = ((X500Principal) principals[0]).getName("RFC1779");
            } else {
                subject = principals[0].getName();
            }
        }
        if (xselector.d() != null) {
            serials.add(xselector.d().toString());
        }
        if (subject != null) {
            for (String y : subjectAttributeNames) {
                list.addAll(z(attrNames, e.ANY_MARKER + y(subject, y) + e.ANY_MARKER, attrs));
            }
        }
        if (serials.size() > 0 && this.e.M() != null) {
            for (String serial : serials) {
                list.addAll(z(A(this.e.M()), serial, attrs));
            }
        }
        if (serials.size() == 0 && subject == null) {
            list.addAll(z(attrNames, e.ANY_MARKER, attrs));
        }
        return list;
    }

    private List c(X509CRLStoreSelector xselector, String[] attrs, String[] attrNames, String[] issuerAttributeNames) {
        List list = new ArrayList();
        String issuer = null;
        Collection<X500Principal> issuers = new HashSet<>();
        if (xselector.getIssuers() != null) {
            issuers.addAll(xselector.getIssuers());
        }
        if (xselector.getCertificateChecking() != null) {
            issuers.add(r(xselector.getCertificateChecking()));
        }
        if (xselector.a() != null) {
            Principal[] principals = xselector.a().c().b();
            for (int i = 0; i < principals.length; i++) {
                if (principals[i] instanceof X500Principal) {
                    issuers.add(principals[i]);
                }
            }
        }
        for (X500Principal name : issuers) {
            issuer = name.getName("RFC1779");
            for (String y : issuerAttributeNames) {
                list.addAll(z(attrNames, e.ANY_MARKER + y(issuer, y) + e.ANY_MARKER, attrs));
            }
        }
        if (issuer == null) {
            list.addAll(z(attrNames, e.ANY_MARKER, attrs));
        }
        return list;
    }

    private List z(String[] attributeNames, String attributeValue, String[] attrs) {
        String filter;
        if (attributeNames == null) {
            filter = null;
        } else {
            String filter2 = "";
            if (attributeValue.equals("**")) {
                attributeValue = e.ANY_MARKER;
            }
            for (int i = 0; i < attributeNames.length; i++) {
                filter2 = filter2 + "(" + attributeNames[i] + "=" + attributeValue + ")";
            }
            filter = "(|" + filter2 + ")";
        }
        String filter22 = "";
        for (int i2 = 0; i2 < attrs.length; i2++) {
            filter22 = filter22 + "(" + attrs[i2] + "=*)";
        }
        String filter23 = "(|" + filter22 + ")";
        String filter3 = "(&" + filter + "" + filter23 + ")";
        if (filter == null) {
            filter3 = filter23;
        }
        List list = v(filter3);
        if (list != null) {
            return list;
        }
        DirContext ctx = null;
        List list2 = new ArrayList();
        try {
            DirContext ctx2 = e();
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(2);
            constraints.setCountLimit(0);
            constraints.setReturningAttributes(attrs);
            NamingEnumeration results = ctx2.search(this.e.o(), filter3, constraints);
            while (results.hasMoreElements()) {
                NamingEnumeration enumeration = ((Attribute) ((SearchResult) results.next()).getAttributes().getAll().next()).getAll();
                while (enumeration.hasMore()) {
                    list2.add(enumeration.next());
                }
            }
            a(filter3, list2);
            try {
                ctx2.close();
            } catch (Exception e2) {
            }
        } catch (NamingException e3) {
            if (ctx != null) {
                ctx.close();
            }
        } catch (Throwable th) {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e4) {
                }
            }
            throw th;
        }
        return list2;
    }

    private Set g(List list, X509CRLStoreSelector xselector) {
        Set crlSet = new HashSet();
        X509CRLParser parser = new X509CRLParser();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            try {
                parser.a(new ByteArrayInputStream((byte[]) it.next()));
                X509CRL crl = (X509CRL) parser.b();
                if (xselector.P0(crl)) {
                    crlSet.add(crl);
                }
            } catch (StreamParsingException e2) {
            }
        }
        return crlSet;
    }

    private Set i(List list, X509CertPairStoreSelector xselector) {
        X509CertificatePair pair;
        Set certPairSet = new HashSet();
        int i = 0;
        while (i < list.size()) {
            try {
                X509CertPairParser parser = new X509CertPairParser();
                parser.a(new ByteArrayInputStream((byte[]) list.get(i)));
                pair = (X509CertificatePair) parser.b();
            } catch (StreamParsingException e2) {
                try {
                    i++;
                    pair = new X509CertificatePair(new CertificatePair(Certificate.f(new ASN1InputStream((byte[]) list.get(i)).r()), Certificate.f(new ASN1InputStream((byte[]) list.get(i + 1)).r())));
                } catch (IOException | CertificateParsingException e3) {
                }
            }
            if (xselector.P0(pair)) {
                certPairSet.add(pair);
            }
            i++;
        }
        return certPairSet;
    }

    private Set f(List list, X509AttributeCertStoreSelector xselector) {
        Set certSet = new HashSet();
        Iterator it = list.iterator();
        X509AttrCertParser parser = new X509AttrCertParser();
        while (it.hasNext()) {
            try {
                parser.a(new ByteArrayInputStream((byte[]) it.next()));
                X509AttributeCertificate cert = (X509AttributeCertificate) parser.b();
                if (xselector.P0(cert)) {
                    certSet.add(cert);
                }
            } catch (StreamParsingException e2) {
            }
        }
        return certSet;
    }

    public Collection p(X509CRLStoreSelector selector) {
        String[] attrs = A(this.e.m());
        String[] attrNames = A(this.e.D());
        String[] issuerAttributeNames = A(this.e.n());
        Set resultSet = g(c(selector, attrs, attrNames, issuerAttributeNames), selector);
        if (resultSet.size() == 0) {
            resultSet.addAll(g(c(new X509CRLStoreSelector(), attrs, attrNames, issuerAttributeNames), selector));
        }
        return resultSet;
    }

    public Collection n(X509CRLStoreSelector selector) {
        String[] attrs = A(this.e.i());
        String[] attrNames = A(this.e.B());
        String[] issuerAttributeNames = A(this.e.j());
        Set resultSet = g(c(selector, attrs, attrNames, issuerAttributeNames), selector);
        if (resultSet.size() == 0) {
            resultSet.addAll(g(c(new X509CRLStoreSelector(), attrs, attrNames, issuerAttributeNames), selector));
        }
        return resultSet;
    }

    public Collection l(X509CRLStoreSelector selector) {
        String[] attrs = A(this.e.d());
        String[] attrNames = A(this.e.z());
        String[] issuerAttributeNames = A(this.e.e());
        Set resultSet = g(c(selector, attrs, attrNames, issuerAttributeNames), selector);
        if (resultSet.size() == 0) {
            resultSet.addAll(g(c(new X509CRLStoreSelector(), attrs, attrNames, issuerAttributeNames), selector));
        }
        return resultSet;
    }

    public Collection t(X509CertPairStoreSelector selector) {
        String[] attrs = A(this.e.t());
        String[] attrNames = A(this.e.H());
        String[] subjectAttributeNames = A(this.e.u());
        Set resultSet = i(j(selector, attrs, attrNames, subjectAttributeNames), selector);
        if (resultSet.size() == 0) {
            X509CertStoreSelector emptyCertselector = new X509CertStoreSelector();
            X509CertPairStoreSelector emptySelector = new X509CertPairStoreSelector();
            emptySelector.c(emptyCertselector);
            emptySelector.d(emptyCertselector);
            resultSet.addAll(i(j(emptySelector, attrs, attrNames, subjectAttributeNames), selector));
        }
        return resultSet;
    }

    public Collection x(X509CertStoreSelector selector) {
        String[] attrs = A(this.e.N());
        String[] attrNames = A(this.e.L());
        String[] subjectAttributeNames = A(this.e.O());
        Set resultSet = h(d(selector, attrs, attrNames, subjectAttributeNames), selector);
        if (resultSet.size() == 0) {
            resultSet.addAll(h(d(new X509CertStoreSelector(), attrs, attrNames, subjectAttributeNames), selector));
        }
        return resultSet;
    }

    public Collection k(X509AttributeCertStoreSelector selector) {
        String[] attrs = A(this.e.b());
        String[] attrNames = A(this.e.y());
        String[] subjectAttributeNames = A(this.e.c());
        Set resultSet = f(b(selector, attrs, attrNames, subjectAttributeNames), selector);
        if (resultSet.size() == 0) {
            resultSet.addAll(f(b(new X509AttributeCertStoreSelector(), attrs, attrNames, subjectAttributeNames), selector));
        }
        return resultSet;
    }

    public Collection o(X509AttributeCertStoreSelector selector) {
        String[] attrs = A(this.e.k());
        String[] attrNames = A(this.e.C());
        String[] subjectAttributeNames = A(this.e.l());
        Set resultSet = f(b(selector, attrs, attrNames, subjectAttributeNames), selector);
        if (resultSet.size() == 0) {
            resultSet.addAll(f(b(new X509AttributeCertStoreSelector(), attrs, attrNames, subjectAttributeNames), selector));
        }
        return resultSet;
    }

    public Collection q(X509CertStoreSelector selector) {
        String[] attrs = A(this.e.p());
        String[] attrNames = A(this.e.F());
        String[] subjectAttributeNames = A(this.e.q());
        Set resultSet = h(d(selector, attrs, attrNames, subjectAttributeNames), selector);
        if (resultSet.size() == 0) {
            resultSet.addAll(h(d(new X509CertStoreSelector(), attrs, attrNames, subjectAttributeNames), selector));
        }
        return resultSet;
    }

    public Collection u(X509CRLStoreSelector selector) {
        String[] attrs = A(this.e.v());
        String[] attrNames = A(this.e.I());
        String[] issuerAttributeNames = A(this.e.w());
        Set resultSet = g(c(selector, attrs, attrNames, issuerAttributeNames), selector);
        if (resultSet.size() == 0) {
            resultSet.addAll(g(c(new X509CRLStoreSelector(), attrs, attrNames, issuerAttributeNames), selector));
        }
        return resultSet;
    }

    public Collection m(X509AttributeCertStoreSelector selector) {
        String[] attrs = A(this.e.f());
        String[] attrNames = A(this.e.A());
        String[] subjectAttributeNames = A(this.e.h());
        Set resultSet = f(b(selector, attrs, attrNames, subjectAttributeNames), selector);
        if (resultSet.size() == 0) {
            resultSet.addAll(f(b(new X509AttributeCertStoreSelector(), attrs, attrNames, subjectAttributeNames), selector));
        }
        return resultSet;
    }

    public Collection s(X509CRLStoreSelector selector) {
        String[] attrs = A(this.e.r());
        String[] attrNames = A(this.e.G());
        String[] issuerAttributeNames = A(this.e.s());
        Set resultSet = g(c(selector, attrs, attrNames, issuerAttributeNames), selector);
        if (resultSet.size() == 0) {
            resultSet.addAll(g(c(new X509CRLStoreSelector(), attrs, attrNames, issuerAttributeNames), selector));
        }
        return resultSet;
    }

    private synchronized void a(String searchCriteria, List list) {
        Date now = new Date(System.currentTimeMillis());
        List cacheEntry = new ArrayList();
        cacheEntry.add(now);
        cacheEntry.add(list);
        if (this.f.containsKey(searchCriteria)) {
            this.f.put(searchCriteria, cacheEntry);
        } else {
            if (this.f.size() >= c) {
                long oldest = now.getTime();
                Object replace = null;
                for (Map.Entry entry : this.f.entrySet()) {
                    long current = ((Date) ((List) entry.getValue()).get(0)).getTime();
                    if (current < oldest) {
                        oldest = current;
                        replace = entry.getKey();
                    }
                }
                this.f.remove(replace);
            }
            this.f.put(searchCriteria, cacheEntry);
        }
    }

    private List v(String searchCriteria) {
        List entry = (List) this.f.get(searchCriteria);
        long now = System.currentTimeMillis();
        if (entry == null || ((Date) entry.get(0)).getTime() < now - d) {
            return null;
        }
        return (List) entry.get(1);
    }

    private String[] A(String str) {
        return str.split("\\s+");
    }

    private String w(X509CertStoreSelector xselector) {
        try {
            byte[] encSubject = xselector.getSubjectAsBytes();
            if (encSubject != null) {
                return new X500Principal(encSubject).getName("RFC1779");
            }
            return null;
        } catch (IOException e2) {
            throw new StoreException("exception processing name: " + e2.getMessage(), e2);
        }
    }

    private X500Principal r(X509Certificate cert) {
        return cert.getIssuerX500Principal();
    }
}
