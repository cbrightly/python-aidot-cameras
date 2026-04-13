package io.netty.handler.ssl.util;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.PlatformDependent;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Provider;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

public abstract class SimpleTrustManagerFactory extends TrustManagerFactory {
    private static final FastThreadLocal<SimpleTrustManagerFactorySpi> CURRENT_SPI = new FastThreadLocal<SimpleTrustManagerFactorySpi>() {
        /* access modifiers changed from: protected */
        public SimpleTrustManagerFactorySpi initialValue() {
            return new SimpleTrustManagerFactorySpi();
        }
    };
    private static final Provider PROVIDER = new Provider("", 0.0d, "") {
        private static final long serialVersionUID = -2680540247105807895L;
    };

    /* access modifiers changed from: protected */
    public abstract TrustManager[] engineGetTrustManagers();

    /* access modifiers changed from: protected */
    public abstract void engineInit(KeyStore keyStore);

    /* access modifiers changed from: protected */
    public abstract void engineInit(ManagerFactoryParameters managerFactoryParameters);

    protected SimpleTrustManagerFactory() {
        this("");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected SimpleTrustManagerFactory(java.lang.String r4) {
        /*
            r3 = this;
            io.netty.util.concurrent.FastThreadLocal<io.netty.handler.ssl.util.SimpleTrustManagerFactory$SimpleTrustManagerFactorySpi> r0 = CURRENT_SPI
            java.lang.Object r1 = r0.get()
            javax.net.ssl.TrustManagerFactorySpi r1 = (javax.net.ssl.TrustManagerFactorySpi) r1
            java.security.Provider r2 = PROVIDER
            r3.<init>(r1, r2, r4)
            java.lang.Object r1 = r0.get()
            io.netty.handler.ssl.util.SimpleTrustManagerFactory$SimpleTrustManagerFactorySpi r1 = (io.netty.handler.ssl.util.SimpleTrustManagerFactory.SimpleTrustManagerFactorySpi) r1
            r1.init(r3)
            r0.remove()
            if (r4 == 0) goto L_0x001c
            return
        L_0x001c:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "name"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.ssl.util.SimpleTrustManagerFactory.<init>(java.lang.String):void");
    }

    public static final class SimpleTrustManagerFactorySpi extends TrustManagerFactorySpi {
        private SimpleTrustManagerFactory parent;
        private volatile TrustManager[] trustManagers;

        SimpleTrustManagerFactorySpi() {
        }

        /* access modifiers changed from: package-private */
        public void init(SimpleTrustManagerFactory parent2) {
            this.parent = parent2;
        }

        /* access modifiers changed from: protected */
        public void engineInit(KeyStore keyStore) {
            try {
                this.parent.engineInit(keyStore);
            } catch (KeyStoreException e) {
                throw e;
            } catch (Exception e2) {
                throw new KeyStoreException(e2);
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(ManagerFactoryParameters managerFactoryParameters) {
            try {
                this.parent.engineInit(managerFactoryParameters);
            } catch (InvalidAlgorithmParameterException e) {
                throw e;
            } catch (Exception e2) {
                throw new InvalidAlgorithmParameterException(e2);
            }
        }

        /* access modifiers changed from: protected */
        public TrustManager[] engineGetTrustManagers() {
            TrustManager[] trustManagers2 = this.trustManagers;
            if (trustManagers2 == null) {
                trustManagers2 = this.parent.engineGetTrustManagers();
                if (PlatformDependent.javaVersion() >= 7) {
                    for (int i = 0; i < trustManagers2.length; i++) {
                        TrustManager tm = trustManagers2[i];
                        if ((tm instanceof X509TrustManager) && !(tm instanceof X509ExtendedTrustManager)) {
                            trustManagers2[i] = new X509TrustManagerWrapper((X509TrustManager) tm);
                        }
                    }
                }
                this.trustManagers = trustManagers2;
            }
            return (TrustManager[]) trustManagers2.clone();
        }
    }
}
