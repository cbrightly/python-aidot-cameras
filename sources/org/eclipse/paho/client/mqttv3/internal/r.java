package org.eclipse.paho.client.mqttv3.internal;

import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.logging.a;
import org.eclipse.paho.client.mqttv3.logging.b;

/* compiled from: SSLNetworkModule */
public class r extends u {
    private static final String h = r.class.getName();
    private a i;
    private String[] j;
    private int k;
    private HostnameVerifier l;
    private boolean m = false;
    private String n;
    private int o;

    public r(SSLSocketFactory factory, String host, int port, String resourceContext) {
        super(factory, host, port, resourceContext);
        a a = b.a("org.eclipse.paho.client.mqttv3.internal.nls.logcat", h);
        this.i = a;
        this.n = host;
        this.o = port;
        a.setResourceName(resourceContext);
    }

    public void c(String[] enabledCiphers) {
        if (enabledCiphers != null) {
            this.j = (String[]) enabledCiphers.clone();
        }
        if (this.c != null && this.j != null) {
            if (this.i.isLoggable(5)) {
                String ciphers = "";
                for (int i2 = 0; i2 < this.j.length; i2++) {
                    if (i2 > 0) {
                        ciphers = String.valueOf(ciphers) + ",";
                    }
                    ciphers = String.valueOf(ciphers) + this.j[i2];
                }
                this.i.fine(h, "setEnabledCiphers", "260", new Object[]{ciphers});
            }
            ((SSLSocket) this.c).setEnabledCipherSuites(this.j);
        }
    }

    public void f(int timeout) {
        super.b(timeout);
        this.k = timeout;
    }

    public void e(HostnameVerifier hostnameVerifier) {
        this.l = hostnameVerifier;
    }

    public void d(boolean httpsHostnameVerificationEnabled) {
        this.m = httpsHostnameVerificationEnabled;
    }

    public void start() {
        super.start();
        c(this.j);
        int soTimeout = this.c.getSoTimeout();
        this.c.setSoTimeout(this.k * 1000);
        try {
            SSLParameters sslParameters = new SSLParameters();
            List<SNIServerName> sniHostNames = new ArrayList<>(1);
            sniHostNames.add(new SNIHostName(this.n));
            sslParameters.setServerNames(sniHostNames);
            ((SSLSocket) this.c).setSSLParameters(sslParameters);
        } catch (NoClassDefFoundError e) {
        }
        if (this.m) {
            try {
                SSLParameters sslParams = new SSLParameters();
                sslParams.setEndpointIdentificationAlgorithm("HTTPS");
                ((SSLSocket) this.c).setSSLParameters(sslParams);
            } catch (NoSuchMethodError e2) {
            }
        }
        ((SSLSocket) this.c).startHandshake();
        if (this.l != null && !this.m) {
            SSLSession session = ((SSLSocket) this.c).getSession();
            if (!this.l.verify(this.n, session)) {
                session.invalidate();
                this.c.close();
                throw new SSLPeerUnverifiedException("Host: " + this.n + ", Peer Host: " + session.getPeerHost());
            }
        }
        this.c.setSoTimeout(soTimeout);
    }

    public String a() {
        return "ssl://" + this.n + ":" + this.o;
    }
}
