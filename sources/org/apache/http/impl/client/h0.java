package org.apache.http.impl.client;

import java.net.Authenticator;
import java.net.InetAddress;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.auth.l;
import org.apache.http.auth.n;
import org.apache.http.auth.p;
import org.apache.http.client.g;
import org.apache.http.util.a;

/* compiled from: SystemDefaultCredentialsProvider */
public class h0 implements g {
    private static final Map<String, String> a;
    private final g b = new g();

    static {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        a = concurrentHashMap;
        Locale locale = Locale.ROOT;
        concurrentHashMap.put("Basic".toUpperCase(locale), "Basic");
        concurrentHashMap.put("Digest".toUpperCase(locale), "Digest");
        concurrentHashMap.put("NTLM".toUpperCase(locale), "NTLM");
        concurrentHashMap.put("Negotiate".toUpperCase(locale), "SPNEGO");
        concurrentHashMap.put("Kerberos".toUpperCase(locale), "Kerberos");
    }

    private static String d(String key) {
        if (key == null) {
            return null;
        }
        String s = a.get(key);
        return s != null ? s : key;
    }

    public void a(org.apache.http.auth.g authscope, l credentials) {
        this.b.a(authscope, credentials);
    }

    private static PasswordAuthentication c(org.apache.http.auth.g authscope, Authenticator.RequestorType requestorType) {
        String hostname = authscope.a();
        int port = authscope.c();
        org.apache.http.l origin = authscope.b();
        return Authenticator.requestPasswordAuthentication(hostname, (InetAddress) null, port, origin != null ? origin.getSchemeName() : port == 443 ? "https" : org.apache.http.l.DEFAULT_SCHEME_NAME, (String) null, d(authscope.d()), (URL) null, requestorType);
    }

    public l b(org.apache.http.auth.g authscope) {
        String proxyHost;
        String proxyPort;
        String proxyUser;
        a.i(authscope, "Auth scope");
        l localcreds = this.b.b(authscope);
        if (localcreds != null) {
            return localcreds;
        }
        if (authscope.a() != null) {
            PasswordAuthentication systemcreds = c(authscope, Authenticator.RequestorType.SERVER);
            if (systemcreds == null) {
                systemcreds = c(authscope, Authenticator.RequestorType.PROXY);
            }
            if (!(systemcreds != null || (proxyHost = System.getProperty("http.proxyHost")) == null || (proxyPort = System.getProperty("http.proxyPort")) == null)) {
                try {
                    if (authscope.e(new org.apache.http.auth.g(proxyHost, Integer.parseInt(proxyPort))) >= 0 && (proxyUser = System.getProperty("http.proxyUser")) != null) {
                        String proxyPassword = System.getProperty("http.proxyPassword");
                        systemcreds = new PasswordAuthentication(proxyUser, proxyPassword != null ? proxyPassword.toCharArray() : new char[0]);
                    }
                } catch (NumberFormatException e) {
                }
            }
            if (systemcreds != null) {
                String domain = System.getProperty("http.auth.ntlm.domain");
                if (domain != null) {
                    return new n(systemcreds.getUserName(), new String(systemcreds.getPassword()), (String) null, domain);
                }
                if ("NTLM".equalsIgnoreCase(authscope.d())) {
                    return new n(systemcreds.getUserName(), new String(systemcreds.getPassword()), (String) null, (String) null);
                }
                return new p(systemcreds.getUserName(), new String(systemcreds.getPassword()));
            }
        }
        return null;
    }
}
