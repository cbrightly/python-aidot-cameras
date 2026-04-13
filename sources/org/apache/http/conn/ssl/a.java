package org.apache.http.conn.ssl;

import com.didichuxing.doraemonkit.util.SystemUtil;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.commons.logging.h;
import org.apache.http.conn.util.b;
import org.slf4j.e;

@Deprecated
/* compiled from: AbstractVerifier */
public abstract class a implements j {
    static final String[] a;
    private final org.apache.commons.logging.a b = h.n(getClass());

    static {
        String[] strArr = {"ac", "co", "com", "ed", "edu", "go", "gouv", "gov", "info", SystemUtil.PHONE_LG, "ne", "net", "or", "org"};
        a = strArr;
        Arrays.sort(strArr);
    }

    public final void b(String host, SSLSocket ssl) {
        org.apache.http.util.a.i(host, "Host");
        SSLSession session = ssl.getSession();
        if (session == null) {
            ssl.getInputStream().available();
            session = ssl.getSession();
            if (session == null) {
                ssl.startHandshake();
                session = ssl.getSession();
            }
        }
        f(host, (X509Certificate) session.getPeerCertificates()[0]);
    }

    public final boolean verify(String host, SSLSession session) {
        try {
            f(host, (X509Certificate) session.getPeerCertificates()[0]);
            return true;
        } catch (SSLException ex) {
            if (this.b.isDebugEnabled()) {
                this.b.debug(ex.getMessage(), ex);
            }
            return false;
        }
    }

    public final void f(String host, X509Certificate cert) {
        List<i> e = d.e(cert);
        List<String> subjectAlts = new ArrayList<>();
        if (b.a(host) || b.b(host)) {
            for (i subjectName : e) {
                if (subjectName.a() == 7) {
                    subjectAlts.add(subjectName.b());
                }
            }
        } else {
            for (i subjectName2 : e) {
                if (subjectName2.a() == 2) {
                    subjectAlts.add(subjectName2.b());
                }
            }
        }
        String cn = d.d(cert.getSubjectX500Principal().getName("RFC2253"));
        String[] strArr = null;
        String[] strArr2 = cn != null ? new String[]{cn} : null;
        if (!subjectAlts.isEmpty()) {
            strArr = (String[]) subjectAlts.toArray(new String[subjectAlts.size()]);
        }
        a(host, strArr2, strArr);
    }

    public final void g(String host, String[] cns, String[] subjectAlts, boolean strictWithSubDomains) {
        List<String> subjectAltList = null;
        String cn = (cns == null || cns.length <= 0) ? null : cns[0];
        if (subjectAlts != null && subjectAlts.length > 0) {
            subjectAltList = Arrays.asList(subjectAlts);
        }
        String normalizedHost = b.b(host) ? d.m(host.toLowerCase(Locale.ROOT)) : host;
        if (subjectAltList != null) {
            for (String subjectAlt : subjectAltList) {
                if (d(normalizedHost, b.b(subjectAlt) ? d.m(subjectAlt) : subjectAlt, strictWithSubDomains)) {
                    return;
                }
            }
            throw new SSLException("Certificate for <" + host + "> doesn't match any " + "of the subject alternative names: " + subjectAltList);
        } else if (cn != null) {
            if (!d(normalizedHost, b.b(cn) ? d.m(cn) : cn, strictWithSubDomains)) {
                throw new SSLException("Certificate for <" + host + "> doesn't match " + "common name of the certificate subject: " + cn);
            }
        } else {
            throw new SSLException("Certificate subject for <" + host + "> doesn't contain " + "a common name and does not have alternative names");
        }
    }

    private static boolean d(String host, String identity, boolean strict) {
        boolean match;
        if (host == null) {
            return false;
        }
        Locale locale = Locale.ROOT;
        String normalizedHost = host.toLowerCase(locale);
        String normalizedIdentity = identity.toLowerCase(locale);
        String[] parts = normalizedIdentity.split("\\.");
        if (!(parts.length >= 3 && parts[0].endsWith(e.ANY_MARKER) && (!strict || e(parts)))) {
            return normalizedHost.equals(normalizedIdentity);
        }
        String firstpart = parts[0];
        if (firstpart.length() > 1) {
            String prefix = firstpart.substring(0, firstpart.length() - 1);
            match = normalizedHost.startsWith(prefix) && normalizedHost.substring(prefix.length()).endsWith(normalizedIdentity.substring(firstpart.length()));
        } else {
            match = normalizedHost.endsWith(normalizedIdentity.substring(1));
        }
        if (!match) {
            return false;
        }
        if (!strict || c(normalizedHost) == c(normalizedIdentity)) {
            return true;
        }
        return false;
    }

    private static boolean e(String[] parts) {
        if (parts.length == 3 && parts[2].length() == 2 && Arrays.binarySearch(a, parts[1]) >= 0) {
            return false;
        }
        return true;
    }

    public static int c(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') {
                count++;
            }
        }
        return count;
    }
}
