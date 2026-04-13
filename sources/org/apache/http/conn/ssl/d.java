package org.apache.http.conn.ssl;

import com.meituan.robust.Constants;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import javax.naming.InvalidNameException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import org.apache.commons.logging.h;
import org.apache.http.conn.util.e;

/* compiled from: DefaultHostnameVerifier */
public final class d implements HostnameVerifier {
    private final org.apache.commons.logging.a a = h.n(getClass());
    private final e b;

    /* compiled from: DefaultHostnameVerifier */
    public enum b {
        IPv4(7),
        IPv6(7),
        DNS(2);
        
        final int subjectType;

        private b(int subjectType2) {
            this.subjectType = subjectType2;
        }
    }

    public d(e publicSuffixMatcher) {
        this.b = publicSuffixMatcher;
    }

    public boolean verify(String host, SSLSession session) {
        try {
            n(host, (X509Certificate) session.getPeerCertificates()[0]);
            return true;
        } catch (SSLException ex) {
            if (this.a.isDebugEnabled()) {
                this.a.debug(ex.getMessage(), ex);
            }
            return false;
        }
    }

    public void n(String host, X509Certificate cert) {
        b hostType = c(host);
        List<i> e = e(cert);
        if (e == null || e.isEmpty()) {
            String cn = d(cert.getSubjectX500Principal().getName("RFC2253"));
            if (cn != null) {
                f(host, cn, this.b);
                return;
            }
            throw new SSLException("Certificate subject for <" + host + "> doesn't contain " + "a common name and does not have alternative names");
        }
        switch (a.a[hostType.ordinal()]) {
            case 1:
                i(host, e);
                return;
            case 2:
                j(host, e);
                return;
            default:
                g(host, e, this.b);
                return;
        }
    }

    /* compiled from: DefaultHostnameVerifier */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[b.values().length];
            a = iArr;
            try {
                iArr[b.IPv4.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[b.IPv6.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static void i(String host, List<i> subjectAlts) {
        int i = 0;
        while (i < subjectAlts.size()) {
            i subjectAlt = subjectAlts.get(i);
            if (subjectAlt.a() != 7 || !host.equals(subjectAlt.b())) {
                i++;
            } else {
                return;
            }
        }
        throw new SSLPeerUnverifiedException("Certificate for <" + host + "> doesn't match any " + "of the subject alternative names: " + subjectAlts);
    }

    static void j(String host, List<i> subjectAlts) {
        String normalisedHost = m(host);
        int i = 0;
        while (i < subjectAlts.size()) {
            i subjectAlt = subjectAlts.get(i);
            if (subjectAlt.a() != 7 || !normalisedHost.equals(m(subjectAlt.b()))) {
                i++;
            } else {
                return;
            }
        }
        throw new SSLPeerUnverifiedException("Certificate for <" + host + "> doesn't match any " + "of the subject alternative names: " + subjectAlts);
    }

    static void g(String host, List<i> subjectAlts, e publicSuffixMatcher) {
        String normalizedHost = host.toLowerCase(Locale.ROOT);
        int i = 0;
        while (i < subjectAlts.size()) {
            i subjectAlt = subjectAlts.get(i);
            if (subjectAlt.a() != 2 || !l(normalizedHost, subjectAlt.b().toLowerCase(Locale.ROOT), publicSuffixMatcher)) {
                i++;
            } else {
                return;
            }
        }
        throw new SSLPeerUnverifiedException("Certificate for <" + host + "> doesn't match any " + "of the subject alternative names: " + subjectAlts);
    }

    static void f(String host, String cn, e publicSuffixMatcher) {
        Locale locale = Locale.ROOT;
        if (!l(host.toLowerCase(locale), cn.toLowerCase(locale), publicSuffixMatcher)) {
            throw new SSLPeerUnverifiedException("Certificate for <" + host + "> doesn't match " + "common name of the certificate subject: " + cn);
        }
    }

    static boolean h(String host, String domainRoot) {
        if (domainRoot == null || !host.endsWith(domainRoot)) {
            return false;
        }
        if (host.length() == domainRoot.length() || host.charAt((host.length() - domainRoot.length()) - 1) == '.') {
            return true;
        }
        return false;
    }

    private static boolean k(String host, String identity, e publicSuffixMatcher, boolean strict) {
        if (publicSuffixMatcher != null && host.contains(".") && !h(host, publicSuffixMatcher.a(identity, org.apache.http.conn.util.a.ICANN))) {
            return false;
        }
        int asteriskIdx = identity.indexOf(42);
        if (asteriskIdx == -1) {
            return host.equalsIgnoreCase(identity);
        }
        String prefix = identity.substring(0, asteriskIdx);
        String suffix = identity.substring(asteriskIdx + 1);
        if (!prefix.isEmpty() && !host.startsWith(prefix)) {
            return false;
        }
        if (!suffix.isEmpty() && !host.endsWith(suffix)) {
            return false;
        }
        if (!strict || !host.substring(prefix.length(), host.length() - suffix.length()).contains(".")) {
            return true;
        }
        return false;
    }

    static boolean l(String host, String identity, e publicSuffixMatcher) {
        return k(host, identity, publicSuffixMatcher, true);
    }

    static String d(String subjectPrincipal) {
        if (subjectPrincipal == null) {
            return null;
        }
        try {
            List<Rdn> rdns = new LdapName(subjectPrincipal).getRdns();
            for (int i = rdns.size() - 1; i >= 0; i--) {
                Attribute cn = rdns.get(i).toAttributes().get("cn");
                if (cn != null) {
                    try {
                        Object value = cn.get();
                        if (value != null) {
                            return value.toString();
                        }
                    } catch (NoSuchElementException | NamingException e) {
                    }
                }
            }
            return null;
        } catch (InvalidNameException e2) {
            throw new SSLException(subjectPrincipal + " is not a valid X500 distinguished name");
        }
    }

    static b c(String host) {
        if (org.apache.http.conn.util.b.a(host)) {
            return b.IPv4;
        }
        String s = host;
        if (s.startsWith(Constants.ARRAY_TYPE) && s.endsWith("]")) {
            s = host.substring(1, host.length() - 1);
        }
        if (org.apache.http.conn.util.b.b(s)) {
            return b.IPv6;
        }
        return b.DNS;
    }

    static List<i> e(X509Certificate cert) {
        try {
            Collection<List<?>> entries = cert.getSubjectAlternativeNames();
            if (entries == null) {
                return Collections.emptyList();
            }
            List<SubjectName> result = new ArrayList<>();
            for (List<?> entry : entries) {
                Integer type = entry.size() >= 2 ? (Integer) entry.get(0) : null;
                if (type != null) {
                    result.add(new i((String) entry.get(1), type.intValue()));
                }
            }
            return result;
        } catch (CertificateParsingException e) {
            return Collections.emptyList();
        }
    }

    static String m(String hostname) {
        if (hostname == null) {
            return hostname;
        }
        try {
            return InetAddress.getByName(hostname).getHostAddress();
        } catch (UnknownHostException e) {
            return hostname;
        }
    }
}
