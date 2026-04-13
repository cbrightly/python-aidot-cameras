package org.glassfish.grizzly.ssl;

import com.google.maps.android.BuildConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import org.glassfish.grizzly.Grizzly;

public class SSLEngineConfigurator implements SSLEngineFactory {
    private static final Logger LOGGER = Grizzly.logger(SSLEngineConfigurator.class);
    protected boolean clientMode;
    protected String[] enabledCipherSuites;
    protected String[] enabledProtocols;
    private boolean isCipherConfigured;
    private boolean isProtocolConfigured;
    protected boolean needClientAuth;
    protected volatile SSLContext sslContext;
    protected volatile SSLContextConfigurator sslContextConfiguration;
    private final Object sync;
    protected boolean wantClientAuth;

    public SSLEngineConfigurator(SSLContext sslContext2) {
        this(sslContext2, true, false, false);
    }

    public SSLEngineConfigurator(SSLContext sslContext2, boolean clientMode2, boolean needClientAuth2, boolean wantClientAuth2) {
        this.sync = new Object();
        this.enabledCipherSuites = null;
        this.enabledProtocols = null;
        this.isProtocolConfigured = false;
        this.isCipherConfigured = false;
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

    public SSLEngineConfigurator(SSLContextConfigurator sslContextConfiguration2) {
        this(sslContextConfiguration2, true, false, false);
    }

    public SSLEngineConfigurator(SSLContextConfigurator sslContextConfiguration2, boolean clientMode2, boolean needClientAuth2, boolean wantClientAuth2) {
        this.sync = new Object();
        this.enabledCipherSuites = null;
        this.enabledProtocols = null;
        this.isProtocolConfigured = false;
        this.isCipherConfigured = false;
        if (sslContextConfiguration2 != null) {
            this.sslContextConfiguration = sslContextConfiguration2;
            this.clientMode = clientMode2;
            this.needClientAuth = needClientAuth2;
            this.wantClientAuth = wantClientAuth2;
            return;
        }
        throw new IllegalArgumentException("SSLContextConfigurator can not be null");
    }

    public SSLEngineConfigurator(SSLEngineConfigurator pattern) {
        this.sync = new Object();
        String[] strArr = null;
        this.enabledCipherSuites = null;
        this.enabledProtocols = null;
        this.isProtocolConfigured = false;
        this.isCipherConfigured = false;
        this.sslContextConfiguration = pattern.sslContextConfiguration;
        this.sslContext = pattern.sslContext;
        this.clientMode = pattern.clientMode;
        this.needClientAuth = pattern.needClientAuth;
        this.wantClientAuth = pattern.wantClientAuth;
        String[] strArr2 = pattern.enabledCipherSuites;
        this.enabledCipherSuites = strArr2 != null ? (String[]) Arrays.copyOf(strArr2, strArr2.length) : null;
        String[] strArr3 = pattern.enabledProtocols;
        this.enabledProtocols = strArr3 != null ? (String[]) Arrays.copyOf(strArr3, strArr3.length) : strArr;
        this.isCipherConfigured = pattern.isCipherConfigured;
        this.isProtocolConfigured = pattern.isProtocolConfigured;
    }

    protected SSLEngineConfigurator() {
        this.sync = new Object();
        this.enabledCipherSuites = null;
        this.enabledProtocols = null;
        this.isProtocolConfigured = false;
        this.isCipherConfigured = false;
    }

    public SSLEngine createSSLEngine() {
        return createSSLEngine((String) null, -1);
    }

    public SSLEngine createSSLEngine(String peerHost, int peerPort) {
        if (this.sslContext == null) {
            synchronized (this.sync) {
                if (this.sslContext == null) {
                    this.sslContext = this.sslContextConfiguration.createSSLContext(true);
                }
            }
        }
        SSLEngine sslEngine = this.sslContext.createSSLEngine(peerHost, peerPort);
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
        boolean z = this.wantClientAuth;
        if (z) {
            sslEngine.setWantClientAuth(z);
        }
        boolean z2 = this.needClientAuth;
        if (z2) {
            sslEngine.setNeedClientAuth(z2);
        }
        return sslEngine;
    }

    public boolean isClientMode() {
        return this.clientMode;
    }

    public SSLEngineConfigurator setClientMode(boolean clientMode2) {
        this.clientMode = clientMode2;
        return this;
    }

    public boolean isNeedClientAuth() {
        return this.needClientAuth;
    }

    public SSLEngineConfigurator setNeedClientAuth(boolean needClientAuth2) {
        this.needClientAuth = needClientAuth2;
        return this;
    }

    public boolean isWantClientAuth() {
        return this.wantClientAuth;
    }

    public SSLEngineConfigurator setWantClientAuth(boolean wantClientAuth2) {
        this.wantClientAuth = wantClientAuth2;
        return this;
    }

    public String[] getEnabledCipherSuites() {
        String[] strArr = this.enabledCipherSuites;
        if (strArr != null) {
            return (String[]) Arrays.copyOf(strArr, strArr.length);
        }
        return null;
    }

    public SSLEngineConfigurator setEnabledCipherSuites(String[] enabledCipherSuites2) {
        this.enabledCipherSuites = enabledCipherSuites2 != null ? (String[]) Arrays.copyOf(enabledCipherSuites2, enabledCipherSuites2.length) : null;
        return this;
    }

    public String[] getEnabledProtocols() {
        String[] strArr = this.enabledProtocols;
        if (strArr != null) {
            return (String[]) Arrays.copyOf(strArr, strArr.length);
        }
        return null;
    }

    public SSLEngineConfigurator setEnabledProtocols(String[] enabledProtocols2) {
        this.enabledProtocols = enabledProtocols2 != null ? (String[]) Arrays.copyOf(enabledProtocols2, enabledProtocols2.length) : null;
        return this;
    }

    public boolean isCipherConfigured() {
        return this.isCipherConfigured;
    }

    public SSLEngineConfigurator setCipherConfigured(boolean isCipherConfigured2) {
        this.isCipherConfigured = isCipherConfigured2;
        return this;
    }

    public boolean isProtocolConfigured() {
        return this.isProtocolConfigured;
    }

    public SSLEngineConfigurator setProtocolConfigured(boolean isProtocolConfigured2) {
        this.isProtocolConfigured = isProtocolConfigured2;
        return this;
    }

    public SSLContext getSslContext() {
        if (this.sslContext == null) {
            synchronized (this.sync) {
                if (this.sslContext == null) {
                    this.sslContext = this.sslContextConfiguration.createSSLContext(true);
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
        StringBuilder sb = new StringBuilder();
        sb.append("SSLEngineConfigurator");
        sb.append("{clientMode=");
        sb.append(this.clientMode);
        sb.append(", enabledCipherSuites=");
        String[] strArr = this.enabledCipherSuites;
        String str = BuildConfig.TRAVIS;
        sb.append(strArr == null ? str : Arrays.asList(strArr).toString());
        sb.append(", enabledProtocols=");
        String[] strArr2 = this.enabledProtocols;
        if (strArr2 != null) {
            str = Arrays.asList(strArr2).toString();
        }
        sb.append(str);
        sb.append(", needClientAuth=");
        sb.append(this.needClientAuth);
        sb.append(", wantClientAuth=");
        sb.append(this.wantClientAuth);
        sb.append(", isProtocolConfigured=");
        sb.append(this.isProtocolConfigured);
        sb.append(", isCipherConfigured=");
        sb.append(this.isCipherConfigured);
        sb.append('}');
        return sb.toString();
    }

    public SSLEngineConfigurator copy() {
        return new SSLEngineConfigurator(this);
    }
}
