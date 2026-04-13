package com.squareup.okhttp.internal.tls;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import org.slf4j.e;

/* compiled from: OkHostnameVerifier */
public final class d implements HostnameVerifier {
    public static final d a = new d();
    private static final Pattern b = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");

    private d() {
    }

    public boolean verify(String host, SSLSession session) {
        try {
            return e(host, (X509Certificate) session.getPeerCertificates()[0]);
        } catch (SSLException e) {
            return false;
        }
    }

    public boolean e(String host, X509Certificate certificate) {
        if (f(host)) {
            return i(host, certificate);
        }
        return h(host, certificate);
    }

    static boolean f(String host) {
        return b.matcher(host).matches();
    }

    private boolean i(String ipAddress, X509Certificate certificate) {
        List<String> altNames = d(certificate, 7);
        int size = altNames.size();
        for (int i = 0; i < size; i++) {
            if (ipAddress.equalsIgnoreCase(altNames.get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean h(String hostName, X509Certificate certificate) {
        String cn;
        String hostName2 = hostName.toLowerCase(Locale.US);
        boolean hasDns = false;
        List<String> altNames = d(certificate, 2);
        int size = altNames.size();
        for (int i = 0; i < size; i++) {
            hasDns = true;
            if (g(hostName2, altNames.get(i))) {
                return true;
            }
        }
        if (hasDns || (cn = new c(certificate.getSubjectX500Principal()).b("cn")) == null) {
            return false;
        }
        return g(hostName2, cn);
    }

    public static List<String> c(X509Certificate certificate) {
        List<String> altIpaNames = d(certificate, 7);
        List<String> altDnsNames = d(certificate, 2);
        List<String> result = new ArrayList<>(altIpaNames.size() + altDnsNames.size());
        result.addAll(altIpaNames);
        result.addAll(altDnsNames);
        return result;
    }

    private static List<String> d(X509Certificate certificate, int type) {
        String altName;
        List<String> result = new ArrayList<>();
        try {
            Collection<List<?>> subjectAlternativeNames = certificate.getSubjectAlternativeNames();
            if (subjectAlternativeNames == null) {
                return Collections.emptyList();
            }
            for (List<?> entry : subjectAlternativeNames) {
                if (entry != null) {
                    if (entry.size() >= 2) {
                        Integer altNameType = (Integer) entry.get(0);
                        if (altNameType != null) {
                            if (altNameType.intValue() == type && (altName = (String) entry.get(1)) != null) {
                                result.add(altName);
                            }
                        }
                    }
                }
            }
            return result;
        } catch (CertificateParsingException e) {
            return Collections.emptyList();
        }
    }

    private boolean g(String hostName, String pattern) {
        if (hostName == null || hostName.length() == 0 || hostName.startsWith(".") || hostName.endsWith("..") || pattern == null || pattern.length() == 0 || pattern.startsWith(".") || pattern.endsWith("..")) {
            return false;
        }
        if (!hostName.endsWith(".")) {
            hostName = hostName + '.';
        }
        if (!pattern.endsWith(".")) {
            pattern = pattern + '.';
        }
        String pattern2 = pattern.toLowerCase(Locale.US);
        if (!pattern2.contains(e.ANY_MARKER)) {
            return hostName.equals(pattern2);
        }
        if (!pattern2.startsWith("*.") || pattern2.indexOf(42, 1) != -1 || hostName.length() < pattern2.length() || "*.".equals(pattern2)) {
            return false;
        }
        String suffix = pattern2.substring(1);
        if (!hostName.endsWith(suffix)) {
            return false;
        }
        int suffixStartIndexInHostName = hostName.length() - suffix.length();
        if (suffixStartIndexInHostName <= 0 || hostName.lastIndexOf(46, suffixStartIndexInHostName - 1) == -1) {
            return true;
        }
        return false;
    }
}
