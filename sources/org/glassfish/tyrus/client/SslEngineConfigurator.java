package org.glassfish.tyrus.client;

import com.google.maps.android.BuildConfig;
import java.util.ArrayList;
import java.util.Arrays;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

public class SslEngineConfigurator {
    protected boolean clientMode;
    protected String[] enabledCipherSuites;
    protected String[] enabledProtocols;
    private boolean hostVerificationEnabled;
    private HostnameVerifier hostnameVerifier;
    private boolean isCipherConfigured;
    private boolean isProtocolConfigured;
    protected boolean needClientAuth;
    protected volatile SSLContext sslContext;
    protected volatile SslContextConfigurator sslContextConfiguration;
    private final Object sync;
    protected boolean wantClientAuth;

    public SslEngineConfigurator(SSLContext sslContext2) {
        this(sslContext2, true, false, false);
    }

    public SslEngineConfigurator(SSLContext sslContext2, boolean clientMode2, boolean needClientAuth2, boolean wantClientAuth2) {
        this.sync = new Object();
        this.enabledCipherSuites = null;
        this.enabledProtocols = null;
        this.isProtocolConfigured = false;
        this.isCipherConfigured = false;
        this.hostVerificationEnabled = true;
        this.hostnameVerifier = null;
        if (sslContext2 != null) {
            this.sslContextConfiguration = null;
            this.sslContext = sslContext2;
            this.clientMode = clientMode2;
            this.needClientAuth = needClientAuth2;
            this.wantClientAuth = wantClientAuth2;
            return;
        }
        throw new IllegalArgumentException("SSLContext can not be null");
    }

    public SslEngineConfigurator(SslContextConfigurator sslContextConfiguration2) {
        this(sslContextConfiguration2, true, false, false);
    }

    public SslEngineConfigurator(SslContextConfigurator sslContextConfiguration2, boolean clientMode2, boolean needClientAuth2, boolean wantClientAuth2) {
        this.sync = new Object();
        this.enabledCipherSuites = null;
        this.enabledProtocols = null;
        this.isProtocolConfigured = false;
        this.isCipherConfigured = false;
        this.hostVerificationEnabled = true;
        this.hostnameVerifier = null;
        if (sslContextConfiguration2 != null) {
            this.sslContextConfiguration = sslContextConfiguration2;
            this.clientMode = clientMode2;
            this.needClientAuth = needClientAuth2;
            this.wantClientAuth = wantClientAuth2;
            return;
        }
        throw new IllegalArgumentException("SSLContextConfigurator can not be null");
    }

    public SslEngineConfigurator(SslEngineConfigurator original) {
        this.sync = new Object();
        this.enabledCipherSuites = null;
        this.enabledProtocols = null;
        this.isProtocolConfigured = false;
        this.isCipherConfigured = false;
        this.hostVerificationEnabled = true;
        this.hostnameVerifier = null;
        this.sslContextConfiguration = original.sslContextConfiguration;
        this.sslContext = original.sslContext;
        this.clientMode = original.clientMode;
        this.needClientAuth = original.needClientAuth;
        this.wantClientAuth = original.wantClientAuth;
        this.enabledCipherSuites = original.enabledCipherSuites;
        this.enabledProtocols = original.enabledProtocols;
        this.isCipherConfigured = original.isCipherConfigured;
        this.isProtocolConfigured = original.isProtocolConfigured;
    }

    protected SslEngineConfigurator() {
        this.sync = new Object();
        this.enabledCipherSuites = null;
        this.enabledProtocols = null;
        this.isProtocolConfigured = false;
        this.isCipherConfigured = false;
        this.hostVerificationEnabled = true;
        this.hostnameVerifier = null;
    }

    public SSLEngine createSSLEngine(String serverHost) {
        if (this.sslContext == null) {
            synchronized (this.sync) {
                if (this.sslContext == null) {
                    this.sslContext = this.sslContextConfiguration.createSSLContext();
                }
            }
        }
        SSLEngine sslEngine = this.sslContext.createSSLEngine(serverHost, -1);
        configure(sslEngine);
        return sslEngine;
    }

    public SSLEngine configure(SSLEngine sslEngine) {
        String[] strArr = this.enabledCipherSuites;
        if (strArr != null) {
            if (!this.isCipherConfigured) {
                this.enabledCipherSuites = configureEnabledCiphers(sslEngine, strArr);
                this.isCipherConfigured = true;
            }
            sslEngine.setEnabledCipherSuites(this.enabledCipherSuites);
        }
        String[] strArr2 = this.enabledProtocols;
        if (strArr2 != null) {
            if (!this.isProtocolConfigured) {
                this.enabledProtocols = configureEnabledProtocols(sslEngine, strArr2);
                this.isProtocolConfigured = true;
            }
            sslEngine.setEnabledProtocols(this.enabledProtocols);
        }
        sslEngine.setUseClientMode(this.clientMode);
        if (this.wantClientAuth) {
            sslEngine.setWantClientAuth(true);
        }
        if (this.needClientAuth) {
            sslEngine.setNeedClientAuth(true);
        }
        return sslEngine;
    }

    public boolean isClientMode() {
        return this.clientMode;
    }

    public SslEngineConfigurator setClientMode(boolean clientMode2) {
        this.clientMode = clientMode2;
        return this;
    }

    public boolean isNeedClientAuth() {
        return this.needClientAuth;
    }

    public SslEngineConfigurator setNeedClientAuth(boolean needClientAuth2) {
        this.needClientAuth = needClientAuth2;
        return this;
    }

    public boolean isWantClientAuth() {
        return this.wantClientAuth;
    }

    public SslEngineConfigurator setWantClientAuth(boolean wantClientAuth2) {
        this.wantClientAuth = wantClientAuth2;
        return this;
    }

    public String[] getEnabledCipherSuites() {
        return (String[]) this.enabledCipherSuites.clone();
    }

    public SslEngineConfigurator setEnabledCipherSuites(String[] enabledCipherSuites2) {
        this.enabledCipherSuites = (String[]) enabledCipherSuites2.clone();
        return this;
    }

    public String[] getEnabledProtocols() {
        return (String[]) this.enabledProtocols.clone();
    }

    public SslEngineConfigurator setEnabledProtocols(String[] enabledProtocols2) {
        this.enabledProtocols = (String[]) enabledProtocols2.clone();
        return this;
    }

    public boolean isCipherConfigured() {
        return this.isCipherConfigured;
    }

    public SslEngineConfigurator setCipherConfigured(boolean isCipherConfigured2) {
        this.isCipherConfigured = isCipherConfigured2;
        return this;
    }

    public boolean isProtocolConfigured() {
        return this.isProtocolConfigured;
    }

    public SslEngineConfigurator setProtocolConfigured(boolean isProtocolConfigured2) {
        this.isProtocolConfigured = isProtocolConfigured2;
        return this;
    }

    public boolean isHostVerificationEnabled() {
        return this.hostVerificationEnabled;
    }

    public SslEngineConfigurator setHostVerificationEnabled(boolean hostVerificationEnabled2) {
        this.hostVerificationEnabled = hostVerificationEnabled2;
        return this;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public SslEngineConfigurator setHostnameVerifier(HostnameVerifier hostnameVerifier2) {
        this.hostnameVerifier = hostnameVerifier2;
        return this;
    }

    public SSLContext getSslContext() {
        if (this.sslContext == null) {
            synchronized (this.sync) {
                if (this.sslContext == null) {
                    this.sslContext = this.sslContextConfiguration.createSSLContext();
                }
            }
        }
        return this.sslContext;
    }

    private static String[] configureEnabledProtocols(SSLEngine sslEngine, String[] requestedProtocols) {
        ArrayList<String> list = null;
        for (String supportedProtocol : sslEngine.getSupportedProtocols()) {
            int length = requestedProtocols.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                String protocol = requestedProtocols[i].trim();
                if (supportedProtocol.equals(protocol)) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(protocol);
                } else {
                    i++;
                }
            }
        }
        if (list != null) {
            return (String[]) list.toArray(new String[list.size()]);
        }
        return null;
    }

    private static String[] configureEnabledCiphers(SSLEngine sslEngine, String[] requestedCiphers) {
        ArrayList<String> list = null;
        for (String supportedCipher : sslEngine.getSupportedCipherSuites()) {
            int length = requestedCiphers.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                String cipher = requestedCiphers[i].trim();
                if (supportedCipher.equals(cipher)) {
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(cipher);
                } else {
                    i++;
                }
            }
        }
        if (list != null) {
            return (String[]) list.toArray(new String[list.size()]);
        }
        return null;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("SSLEngineConfigurator");
        sb.append("{clientMode=");
        sb.append(this.clientMode);
        sb.append(", enabledCipherSuites=");
        String[] strArr = this.enabledCipherSuites;
        String str2 = BuildConfig.TRAVIS;
        if (strArr == null) {
            str = str2;
        } else {
            str = Arrays.asList(strArr).toString();
        }
        sb.append(str);
        sb.append(", enabledProtocols=");
        String[] strArr2 = this.enabledProtocols;
        if (strArr2 != null) {
            str2 = Arrays.asList(strArr2).toString();
        }
        sb.append(str2);
        sb.append(", needClientAuth=");
        sb.append(this.needClientAuth);
        sb.append(", wantClientAuth=");
        sb.append(this.wantClientAuth);
        sb.append(", isProtocolConfigured=");
        sb.append(this.isProtocolConfigured);
        sb.append(", isCipherConfigured=");
        sb.append(this.isCipherConfigured);
        sb.append(", hostVerificationEnabled=");
        sb.append(this.hostVerificationEnabled);
        sb.append('}');
        return sb.toString();
    }

    public SslEngineConfigurator copy() {
        return new SslEngineConfigurator(this);
    }
}
