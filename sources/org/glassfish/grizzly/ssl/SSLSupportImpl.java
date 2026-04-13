package org.glassfish.grizzly.ssl;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.ssl.SSLSupport;

public class SSLSupportImpl implements SSLSupport {
    public static final String KEY_SIZE_KEY = "SSL_KEY_SIZE";
    private static final SSLSupport.CipherData[] ciphers = {new SSLSupport.CipherData("_WITH_NULL_", 0), new SSLSupport.CipherData("_WITH_IDEA_CBC_", 128), new SSLSupport.CipherData("_WITH_RC2_CBC_40_", 40), new SSLSupport.CipherData("_WITH_RC4_40_", 40), new SSLSupport.CipherData("_WITH_RC4_128_", 128), new SSLSupport.CipherData("_WITH_DES40_CBC_", 40), new SSLSupport.CipherData("_WITH_DES_CBC_", 56), new SSLSupport.CipherData("_WITH_3DES_EDE_CBC_", 168), new SSLSupport.CipherData("_WITH_AES_128_", 128), new SSLSupport.CipherData("_WITH_AES_256_", 256)};
    private static final Logger logger = Grizzly.logger(SSLSupportImpl.class);
    private final SSLEngine engine;
    private volatile SSLSession session;

    public SSLSupportImpl(Connection connection) {
        SSLEngine sSLEngine = SSLUtils.getSSLEngine(connection);
        this.engine = sSLEngine;
        if (sSLEngine != null) {
            this.session = sSLEngine.getSession();
            return;
        }
        throw new IllegalStateException("SSLEngine is null");
    }

    public String getCipherSuite() {
        if (this.session == null) {
            return null;
        }
        return this.session.getCipherSuite();
    }

    public Object[] getPeerCertificateChain() {
        return getPeerCertificateChain(false);
    }

    /* access modifiers changed from: protected */
    public X509Certificate[] getX509Certificates(SSLSession session2) {
        javax.security.cert.X509Certificate[] jsseCerts = null;
        try {
            jsseCerts = session2.getPeerCertificateChain();
        } catch (Throwable th) {
        }
        if (jsseCerts == null) {
            jsseCerts = new javax.security.cert.X509Certificate[0];
        }
        X509Certificate[] x509Certs = new X509Certificate[jsseCerts.length];
        int i = 0;
        while (i < x509Certs.length) {
            try {
                x509Certs[i] = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(jsseCerts[i].getEncoded()));
                Logger logger2 = logger;
                Level level = Level.FINE;
                if (logger2.isLoggable(level)) {
                    logger2.log(level, "Cert #" + i + " = " + x509Certs[i]);
                }
                i++;
            } catch (Exception ex) {
                Logger logger3 = logger;
                Level level2 = Level.INFO;
                logger3.log(level2, "Error translating " + jsseCerts[i], ex);
                return null;
            }
        }
        if (x509Certs.length < 1) {
            return null;
        }
        return x509Certs;
    }

    public Object[] getPeerCertificateChain(boolean force) {
        if (this.session == null) {
            return null;
        }
        javax.security.cert.X509Certificate[] jsseCerts = null;
        try {
            jsseCerts = this.session.getPeerCertificateChain();
        } catch (Exception e) {
        }
        if (jsseCerts == null) {
            jsseCerts = new javax.security.cert.X509Certificate[0];
        }
        if (jsseCerts.length <= 0 && force) {
            this.session.invalidate();
            this.session = this.engine.getSession();
        }
        return getX509Certificates(this.session);
    }

    public Integer getKeySize() {
        SSLSupport.CipherData[] c_aux = ciphers;
        if (this.session == null) {
            return null;
        }
        Integer keySize = (Integer) this.session.getValue(KEY_SIZE_KEY);
        if (keySize != null) {
            return keySize;
        }
        int size = 0;
        String cipherSuite = this.session.getCipherSuite();
        int i = 0;
        while (true) {
            if (i >= c_aux.length) {
                break;
            } else if (cipherSuite.contains(c_aux[i].phrase)) {
                size = c_aux[i].keySize;
                break;
            } else {
                i++;
            }
        }
        Integer keySize2 = Integer.valueOf(size);
        this.session.putValue(KEY_SIZE_KEY, keySize2);
        return keySize2;
    }

    public String getSessionId() {
        byte[] ssl_session;
        if (this.session == null || (ssl_session = this.session.getId()) == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder("");
        for (byte hexString : ssl_session) {
            String digit = Integer.toHexString(hexString);
            if (digit.length() < 2) {
                buf.append('0');
            }
            if (digit.length() > 2) {
                digit = digit.substring(digit.length() - 2);
            }
            buf.append(digit);
        }
        return buf.toString();
    }
}
