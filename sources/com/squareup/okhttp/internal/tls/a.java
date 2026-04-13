package com.squareup.okhttp.internal.tls;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/* compiled from: AndroidTrustRootIndex */
public final class a implements f {
    private final X509TrustManager a;
    private final Method b;

    public a(X509TrustManager trustManager, Method findByIssuerAndSignatureMethod) {
        this.b = findByIssuerAndSignatureMethod;
        this.a = trustManager;
    }

    public X509Certificate a(X509Certificate cert) {
        try {
            TrustAnchor trustAnchor = (TrustAnchor) this.b.invoke(this.a, new Object[]{cert});
            if (trustAnchor != null) {
                return trustAnchor.getTrustedCert();
            }
            return null;
        } catch (IllegalAccessException e) {
            throw new AssertionError();
        } catch (InvocationTargetException e2) {
            return null;
        }
    }

    public static f b(X509TrustManager trustManager) {
        try {
            Method method = trustManager.getClass().getDeclaredMethod("findTrustAnchorByIssuerAndSignature", new Class[]{X509Certificate.class});
            method.setAccessible(true);
            return new a(trustManager, method);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
