package okhttp3.internal.tls;

import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import kotlin.TypeCastException;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import kotlin.text.x;
import okhttp3.internal.a;
import okhttp3.internal.b;
import org.jetbrains.annotations.NotNull;
import org.slf4j.e;

/* compiled from: OkHostnameVerifier.kt */
public final class d implements HostnameVerifier {
    public static final d a = new d();

    private d() {
    }

    public boolean verify(@NotNull String host, @NotNull SSLSession session) {
        k.f(host, SerializableCookie.HOST);
        k.f(session, "session");
        try {
            Certificate certificate = session.getPeerCertificates()[0];
            if (certificate != null) {
                return e(host, (X509Certificate) certificate);
            }
            throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
        } catch (SSLException e) {
            return false;
        }
    }

    public final boolean e(@NotNull String host, @NotNull X509Certificate certificate) {
        k.f(host, SerializableCookie.HOST);
        k.f(certificate, "certificate");
        if (b.f(host)) {
            return h(host, certificate);
        }
        return g(host, certificate);
    }

    private final boolean h(String ipAddress, X509Certificate certificate) {
        String canonicalIpAddress = a.e(ipAddress);
        List<String> d = d(certificate, 7);
        if ((d instanceof Collection) && d.isEmpty()) {
            return false;
        }
        for (String it : d) {
            if (k.a(canonicalIpAddress, a.e(it))) {
                return true;
            }
        }
        return false;
    }

    private final boolean g(String hostname, X509Certificate certificate) {
        Locale locale = Locale.US;
        k.b(locale, "Locale.US");
        if (hostname != null) {
            String hostname2 = hostname.toLowerCase(locale);
            k.b(hostname2, "(this as java.lang.String).toLowerCase(locale)");
            List<String> d = d(certificate, 2);
            if ((d instanceof Collection) && d.isEmpty()) {
                return false;
            }
            for (String it : d) {
                if (a.f(hostname2, it)) {
                    return true;
                }
            }
            return false;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    private final boolean f(String hostname, String pattern) {
        String hostname2 = hostname;
        String pattern2 = pattern;
        if ((hostname2 == null || hostname2.length() == 0) || w.N(hostname2, ".", false, 2, (Object) null) || w.x(hostname2, "..", false, 2, (Object) null)) {
            return false;
        }
        if ((pattern2 == null || pattern2.length() == 0) || w.N(pattern2, ".", false, 2, (Object) null) || w.x(pattern2, "..", false, 2, (Object) null)) {
            return false;
        }
        if (!w.x(hostname2, ".", false, 2, (Object) null)) {
            hostname2 = hostname2 + ".";
        }
        if (!w.x(pattern2, ".", false, 2, (Object) null)) {
            pattern2 = pattern2 + ".";
        }
        Locale locale = Locale.US;
        k.b(locale, "Locale.US");
        if (pattern2 != null) {
            String lowerCase = pattern2.toLowerCase(locale);
            k.b(lowerCase, "(this as java.lang.String).toLowerCase(locale)");
            String pattern3 = lowerCase;
            if (!x.S(pattern3, e.ANY_MARKER, false, 2, (Object) null)) {
                return k.a(hostname2, pattern3);
            }
            if (!w.N(pattern3, "*.", false, 2, (Object) null) || x.e0(pattern3, '*', 1, false, 4, (Object) null) != -1 || hostname2.length() < pattern3.length() || k.a("*.", pattern3)) {
                return false;
            }
            String suffix = pattern3.substring(1);
            k.b(suffix, "(this as java.lang.String).substring(startIndex)");
            if (!w.x(hostname2, suffix, false, 2, (Object) null)) {
                return false;
            }
            int suffixStartIndexInHostname = hostname2.length() - suffix.length();
            if (suffixStartIndexInHostname > 0) {
                if (x.j0(hostname2, '.', suffixStartIndexInHostname - 1, false, 4, (Object) null) != -1) {
                    return false;
                }
            }
            return true;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    @NotNull
    public final List<String> c(@NotNull X509Certificate certificate) {
        k.f(certificate, "certificate");
        return y.n0(d(certificate, 7), d(certificate, 2));
    }

    private final List<String> d(X509Certificate certificate, int type) {
        try {
            Collection subjectAltNames = certificate.getSubjectAlternativeNames();
            if (subjectAltNames == null) {
                return q.g();
            }
            List result = new ArrayList();
            for (List subjectAltName : subjectAltNames) {
                if (subjectAltName != null) {
                    if (subjectAltName.size() >= 2) {
                        if (!(!k.a(subjectAltName.get(0), Integer.valueOf(type)))) {
                            Object altName = subjectAltName.get(1);
                            if (altName != null) {
                                result.add((String) altName);
                            }
                        }
                    }
                }
            }
            return result;
        } catch (CertificateParsingException e) {
            return q.g();
        }
    }
}
