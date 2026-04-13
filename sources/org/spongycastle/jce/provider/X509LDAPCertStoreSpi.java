package org.spongycastle.jce.provider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStoreException;
import java.security.cert.CertStoreSpi;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.security.auth.x500.X500Principal;
import org.slf4j.e;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.x509.CertificatePair;
import org.spongycastle.jce.X509LDAPCertStoreParameters;

public class X509LDAPCertStoreSpi extends CertStoreSpi {
    private static String a = "com.sun.jndi.ldap.LdapCtxFactory";
    private static String b = "ignore";
    private X509LDAPCertStoreParameters c;

    private DirContext b() {
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", a);
        props.setProperty("java.naming.batchsize", "0");
        props.setProperty("java.naming.provider.url", this.c.K());
        props.setProperty("java.naming.factory.url.pkgs", "com.sun.jndi.url");
        props.setProperty("java.naming.referral", b);
        props.setProperty("java.naming.security.authentication", "none");
        return new InitialDirContext(props);
    }

    private String f(String subject, String subjectAttributeName) {
        int end;
        String temp = subject;
        String temp2 = temp.substring(subjectAttributeName.length() + temp.toLowerCase().indexOf(subjectAttributeName.toLowerCase()));
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

    public Collection engineGetCertificates(CertSelector selector) {
        if (selector instanceof X509CertSelector) {
            X509CertSelector xselector = (X509CertSelector) selector;
            Set certSet = new HashSet();
            Set<byte[]> set = e(xselector);
            set.addAll(c(xselector));
            set.addAll(d(xselector));
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
                for (byte[] bytes : set) {
                    if (bytes != null) {
                        if (bytes.length != 0) {
                            List<byte[]> bytesList = new ArrayList<>();
                            bytesList.add(bytes);
                            try {
                                CertificatePair pair = CertificatePair.f(new ASN1InputStream(bytes).r());
                                bytesList.clear();
                                if (pair.e() != null) {
                                    bytesList.add(pair.e().getEncoded());
                                }
                                if (pair.g() != null) {
                                    bytesList.add(pair.g().getEncoded());
                                }
                            } catch (IOException | IllegalArgumentException e) {
                            }
                            for (byte[] byteArrayInputStream : bytesList) {
                                try {
                                    Certificate cert = cf.generateCertificate(new ByteArrayInputStream(byteArrayInputStream));
                                    if (xselector.match(cert)) {
                                        certSet.add(cert);
                                    }
                                } catch (Exception e2) {
                                }
                            }
                        }
                    }
                }
                return certSet;
            } catch (Exception e3) {
                throw new CertStoreException("certificate cannot be constructed from LDAP result: " + e3);
            }
        } else {
            throw new CertStoreException("selector is not a X509CertSelector");
        }
    }

    private Set a(X509CertSelector xselector, String[] attrs, String attrName, String subjectAttributeName) {
        String subject;
        Set set = new HashSet();
        try {
            if (xselector.getSubjectAsBytes() == null) {
                if (xselector.getSubjectAsString() == null) {
                    if (xselector.getCertificate() == null) {
                        set.addAll(g(attrName, e.ANY_MARKER, attrs));
                        return set;
                    }
                }
            }
            String serial = null;
            if (xselector.getCertificate() != null) {
                subject = xselector.getCertificate().getSubjectX500Principal().getName("RFC1779");
                serial = xselector.getCertificate().getSerialNumber().toString();
            } else if (xselector.getSubjectAsBytes() != null) {
                subject = new X500Principal(xselector.getSubjectAsBytes()).getName("RFC1779");
            } else {
                subject = xselector.getSubjectAsString();
            }
            String attrValue = f(subject, subjectAttributeName);
            set.addAll(g(attrName, e.ANY_MARKER + attrValue + e.ANY_MARKER, attrs));
            if (!(serial == null || this.c.M() == null)) {
                String attrName2 = this.c.M();
                set.addAll(g(attrName2, e.ANY_MARKER + serial + e.ANY_MARKER, attrs));
            }
            return set;
        } catch (IOException e) {
            throw new CertStoreException("exception processing selector: " + e);
        }
    }

    private Set e(X509CertSelector xselector) {
        return a(xselector, new String[]{this.c.N()}, this.c.L(), this.c.O());
    }

    private Set c(X509CertSelector xselector) {
        String[] attrs = {this.c.p()};
        Set set = a(xselector, attrs, this.c.F(), this.c.q());
        if (set.isEmpty()) {
            set.addAll(g((String) null, e.ANY_MARKER, attrs));
        }
        return set;
    }

    private Set d(X509CertSelector xselector) {
        String[] attrs = {this.c.t()};
        Set set = a(xselector, attrs, this.c.H(), this.c.u());
        if (set.isEmpty()) {
            set.addAll(g((String) null, e.ANY_MARKER, attrs));
        }
        return set;
    }

    public Collection engineGetCRLs(CRLSelector selector) {
        String attrValue;
        String[] attrs = {this.c.r()};
        if (selector instanceof X509CRLSelector) {
            X509CRLSelector xselector = (X509CRLSelector) selector;
            Set crlSet = new HashSet();
            String attrName = this.c.G();
            Set<byte[]> set = new HashSet<>();
            if (xselector.getIssuerNames() != null) {
                for (Object o : xselector.getIssuerNames()) {
                    if (o instanceof String) {
                        attrValue = f((String) o, this.c.s());
                    } else {
                        attrValue = f(new X500Principal((byte[]) o).getName("RFC1779"), this.c.s());
                    }
                    set.addAll(g(attrName, e.ANY_MARKER + attrValue + e.ANY_MARKER, attrs));
                }
            } else {
                set.addAll(g(attrName, e.ANY_MARKER, attrs));
            }
            set.addAll(g((String) null, e.ANY_MARKER, attrs));
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
                for (byte[] byteArrayInputStream : set) {
                    CRL crl = cf.generateCRL(new ByteArrayInputStream(byteArrayInputStream));
                    if (xselector.match(crl)) {
                        crlSet.add(crl);
                    }
                }
                return crlSet;
            } catch (Exception e) {
                throw new CertStoreException("CRL cannot be constructed from LDAP result " + e);
            }
        } else {
            throw new CertStoreException("selector is not a X509CRLSelector");
        }
    }

    private Set g(String attributeName, String attributeValue, String[] attrs) {
        String filter = attributeName + "=" + attributeValue;
        if (attributeName == null) {
            filter = null;
        }
        DirContext ctx = null;
        Set set = new HashSet();
        try {
            DirContext ctx2 = b();
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(2);
            constraints.setCountLimit(0);
            for (int i = 0; i < attrs.length; i++) {
                String[] temp = {attrs[i]};
                constraints.setReturningAttributes(temp);
                String filter2 = "(&(" + filter + ")(" + temp[0] + "=*))";
                if (filter == null) {
                    filter2 = "(" + temp[0] + "=*)";
                }
                NamingEnumeration results = ctx2.search(this.c.o(), filter2, constraints);
                while (results.hasMoreElements()) {
                    NamingEnumeration enumeration = ((Attribute) ((SearchResult) results.next()).getAttributes().getAll().next()).getAll();
                    while (enumeration.hasMore()) {
                        set.add(enumeration.next());
                    }
                }
            }
            if (ctx2 != null) {
                try {
                    ctx2.close();
                } catch (Exception e) {
                }
            }
            return set;
        } catch (Exception e2) {
            throw new CertStoreException("Error getting results from LDAP directory " + e2);
        } catch (Throwable th) {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Exception e3) {
                }
            }
            throw th;
        }
    }
}
