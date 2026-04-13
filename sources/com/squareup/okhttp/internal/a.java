package com.squareup.okhttp.internal;

import com.squareup.okhttp.k;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.UnknownServiceException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLProtocolException;
import javax.net.ssl.SSLSocket;

/* compiled from: ConnectionSpecSelector */
public final class a {
    private final List<k> a;
    private int b = 0;
    private boolean c;
    private boolean d;

    public a(List<k> connectionSpecs) {
        this.a = connectionSpecs;
    }

    public k a(SSLSocket sslSocket) {
        k tlsConfiguration = null;
        int i = this.b;
        int size = this.a.size();
        while (true) {
            if (i >= size) {
                break;
            }
            k connectionSpec = this.a.get(i);
            if (connectionSpec.g(sslSocket)) {
                tlsConfiguration = connectionSpec;
                this.b = i + 1;
                break;
            }
            i++;
        }
        if (tlsConfiguration != null) {
            this.c = c(sslSocket);
            d.b.b(tlsConfiguration, sslSocket, this.d);
            return tlsConfiguration;
        }
        throw new UnknownServiceException("Unable to find acceptable protocols. isFallback=" + this.d + ", modes=" + this.a + ", supported protocols=" + Arrays.toString(sslSocket.getEnabledProtocols()));
    }

    public boolean b(IOException e) {
        this.d = true;
        if (!this.c || (e instanceof ProtocolException) || (e instanceof InterruptedIOException)) {
            return false;
        }
        if (((e instanceof SSLHandshakeException) && (e.getCause() instanceof CertificateException)) || (e instanceof SSLPeerUnverifiedException)) {
            return false;
        }
        if ((e instanceof SSLHandshakeException) || (e instanceof SSLProtocolException)) {
            return true;
        }
        return false;
    }

    private boolean c(SSLSocket socket) {
        for (int i = this.b; i < this.a.size(); i++) {
            if (this.a.get(i).g(socket)) {
                return true;
            }
        }
        return false;
    }
}
