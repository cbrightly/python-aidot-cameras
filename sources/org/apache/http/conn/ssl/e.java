package org.apache.http.conn.ssl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.security.auth.x500.X500Principal;
import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.conn.socket.b;
import org.apache.http.conn.util.f;
import org.apache.http.l;

/* compiled from: SSLConnectionSocketFactory */
public class e implements b {
    @Deprecated
    public static final j a = b.c;
    @Deprecated
    public static final j b = c.c;
    @Deprecated
    public static final j c = h.c;
    private final a d;
    private final SSLSocketFactory e;
    private final HostnameVerifier f;
    private final String[] g;
    private final String[] h;

    public static HostnameVerifier a() {
        return new d(f.a());
    }

    public e(SSLContext sslContext, HostnameVerifier hostnameVerifier) {
        this(((SSLContext) org.apache.http.util.a.i(sslContext, "SSL context")).getSocketFactory(), (String[]) null, (String[]) null, hostnameVerifier);
    }

    public e(SSLContext sslContext, String[] supportedProtocols, String[] supportedCipherSuites, HostnameVerifier hostnameVerifier) {
        this(((SSLContext) org.apache.http.util.a.i(sslContext, "SSL context")).getSocketFactory(), supportedProtocols, supportedCipherSuites, hostnameVerifier);
    }

    public e(SSLSocketFactory socketfactory, String[] supportedProtocols, String[] supportedCipherSuites, HostnameVerifier hostnameVerifier) {
        this.d = h.n(getClass());
        this.e = (SSLSocketFactory) org.apache.http.util.a.i(socketfactory, "SSL socket factory");
        this.g = supportedProtocols;
        this.h = supportedCipherSuites;
        this.f = hostnameVerifier != null ? hostnameVerifier : a();
    }

    /* access modifiers changed from: protected */
    public void b(SSLSocket socket) {
    }

    public Socket i(org.apache.http.protocol.f context) {
        return SocketFactory.getDefault().createSocket();
    }

    public Socket k(int connectTimeout, Socket socket, l host, InetSocketAddress remoteAddress, InetSocketAddress localAddress, org.apache.http.protocol.f context) {
        org.apache.http.util.a.i(host, "HTTP host");
        org.apache.http.util.a.i(remoteAddress, "Remote address");
        Socket sock = socket != null ? socket : i(context);
        if (localAddress != null) {
            sock.bind(localAddress);
        }
        if (connectTimeout > 0) {
            try {
                if (sock.getSoTimeout() == 0) {
                    sock.setSoTimeout(connectTimeout);
                }
            } catch (IOException ex) {
                try {
                    sock.close();
                } catch (IOException e2) {
                }
                throw ex;
            }
        }
        if (this.d.isDebugEnabled()) {
            a aVar = this.d;
            aVar.debug("Connecting socket to " + remoteAddress + " with timeout " + connectTimeout);
        }
        sock.connect(remoteAddress, connectTimeout);
        if (!(sock instanceof SSLSocket)) {
            return h(sock, host.getHostName(), remoteAddress.getPort(), context);
        }
        SSLSocket sslsock = (SSLSocket) sock;
        this.d.debug("Starting handshake");
        sslsock.startHandshake();
        c(sslsock, host.getHostName());
        return sock;
    }

    public Socket h(Socket socket, String target, int port, org.apache.http.protocol.f context) {
        SSLSocket sslsock = (SSLSocket) this.e.createSocket(socket, target, port, true);
        String[] strArr = this.g;
        if (strArr != null) {
            sslsock.setEnabledProtocols(strArr);
        } else {
            String[] allProtocols = sslsock.getEnabledProtocols();
            List<String> enabledProtocols = new ArrayList<>(allProtocols.length);
            for (String protocol : allProtocols) {
                if (!protocol.startsWith("SSL")) {
                    enabledProtocols.add(protocol);
                }
            }
            if (!enabledProtocols.isEmpty()) {
                sslsock.setEnabledProtocols((String[]) enabledProtocols.toArray(new String[enabledProtocols.size()]));
            }
        }
        String[] allProtocols2 = this.h;
        if (allProtocols2 != null) {
            sslsock.setEnabledCipherSuites(allProtocols2);
        }
        if (this.d.isDebugEnabled()) {
            this.d.debug("Enabled protocols: " + Arrays.asList(sslsock.getEnabledProtocols()));
            this.d.debug("Enabled cipher suites:" + Arrays.asList(sslsock.getEnabledCipherSuites()));
        }
        b(sslsock);
        this.d.debug("Starting handshake");
        sslsock.startHandshake();
        c(sslsock, target);
        return sslsock;
    }

    private void c(SSLSocket sslsock, String hostname) {
        try {
            SSLSession session = sslsock.getSession();
            if (session == null) {
                sslsock.getInputStream().available();
                session = sslsock.getSession();
                if (session == null) {
                    sslsock.startHandshake();
                    session = sslsock.getSession();
                }
            }
            if (session != null) {
                if (this.d.isDebugEnabled()) {
                    this.d.debug("Secure session established");
                    a aVar = this.d;
                    aVar.debug(" negotiated protocol: " + session.getProtocol());
                    a aVar2 = this.d;
                    aVar2.debug(" negotiated cipher suite: " + session.getCipherSuite());
                    try {
                        X509Certificate x509 = (X509Certificate) session.getPeerCertificates()[0];
                        X500Principal peer = x509.getSubjectX500Principal();
                        a aVar3 = this.d;
                        aVar3.debug(" peer principal: " + peer.toString());
                        Collection<List<?>> altNames1 = x509.getSubjectAlternativeNames();
                        if (altNames1 != null) {
                            List<String> altNames = new ArrayList<>();
                            for (List<?> aC : altNames1) {
                                if (!aC.isEmpty()) {
                                    altNames.add((String) aC.get(1));
                                }
                            }
                            a aVar4 = this.d;
                            aVar4.debug(" peer alternative names: " + altNames);
                        }
                        X500Principal issuer = x509.getIssuerX500Principal();
                        a aVar5 = this.d;
                        aVar5.debug(" issuer principal: " + issuer.toString());
                        Collection<List<?>> altNames2 = x509.getIssuerAlternativeNames();
                        if (altNames2 != null) {
                            List<String> altNames3 = new ArrayList<>();
                            for (List<?> aC2 : altNames2) {
                                if (!aC2.isEmpty()) {
                                    altNames3.add((String) aC2.get(1));
                                }
                            }
                            a aVar6 = this.d;
                            aVar6.debug(" issuer alternative names: " + altNames3);
                        }
                    } catch (Exception e2) {
                    }
                }
                if (!this.f.verify(hostname, session)) {
                    List<i> e3 = d.e((X509Certificate) session.getPeerCertificates()[0]);
                    throw new SSLPeerUnverifiedException("Certificate for <" + hostname + "> doesn't match any " + "of the subject alternative names: " + e3);
                }
                return;
            }
            throw new SSLHandshakeException("SSL session not available");
        } catch (IOException iox) {
            try {
                sslsock.close();
            } catch (Exception e4) {
            }
            throw iox;
        }
    }
}
