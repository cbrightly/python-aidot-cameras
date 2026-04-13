package org.apache.http.ssl;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/* compiled from: SSLContexts */
public class b {
    public static SSLContext a() {
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init((KeyManager[]) null, (TrustManager[]) null, (SecureRandom) null);
            return sslcontext;
        } catch (NoSuchAlgorithmException ex) {
            throw new SSLInitializationException(ex.getMessage(), ex);
        } catch (KeyManagementException ex2) {
            throw new SSLInitializationException(ex2.getMessage(), ex2);
        }
    }
}
