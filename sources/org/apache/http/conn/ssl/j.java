package org.apache.http.conn.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocket;

@Deprecated
/* compiled from: X509HostnameVerifier */
public interface j extends HostnameVerifier {
    void a(String str, String[] strArr, String[] strArr2);

    void b(String str, SSLSocket sSLSocket);
}
