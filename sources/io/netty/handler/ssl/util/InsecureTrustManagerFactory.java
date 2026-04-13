package io.netty.handler.ssl.util;

import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public final class InsecureTrustManagerFactory extends SimpleTrustManagerFactory {
    public static final TrustManagerFactory INSTANCE = new InsecureTrustManagerFactory();
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) InsecureTrustManagerFactory.class);
    private static final TrustManager tm = new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] chain, String s) {
            InternalLogger access$000 = InsecureTrustManagerFactory.logger;
            access$000.debug("Accepting a client certificate: " + chain[0].getSubjectDN());
        }

        public void checkServerTrusted(X509Certificate[] chain, String s) {
            InternalLogger access$000 = InsecureTrustManagerFactory.logger;
            access$000.debug("Accepting a server certificate: " + chain[0].getSubjectDN());
        }

        public X509Certificate[] getAcceptedIssuers() {
            return EmptyArrays.EMPTY_X509_CERTIFICATES;
        }
    };

    private InsecureTrustManagerFactory() {
    }

    /* access modifiers changed from: protected */
    public void engineInit(KeyStore keyStore) {
    }

    /* access modifiers changed from: protected */
    public void engineInit(ManagerFactoryParameters managerFactoryParameters) {
    }

    /* access modifiers changed from: protected */
    public TrustManager[] engineGetTrustManagers() {
        return new TrustManager[]{tm};
    }
}
