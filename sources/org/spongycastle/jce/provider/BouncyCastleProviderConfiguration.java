package org.spongycastle.jce.provider;

import java.security.Permission;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jcajce.provider.config.ProviderConfigurationPermission;
import org.spongycastle.jce.spec.ECParameterSpec;

public class BouncyCastleProviderConfiguration implements ProviderConfiguration {
    private static Permission a = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, "threadLocalEcImplicitlyCa");
    private static Permission b = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, "ecImplicitlyCa");
    private static Permission c = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, "threadLocalDhDefaultParams");
    private static Permission d = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, "DhDefaultParams");
    private static Permission e = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, "acceptableEcCurves");
    private static Permission f = new ProviderConfigurationPermission(BouncyCastleProvider.PROVIDER_NAME, "additionalEcParameters");
    private ThreadLocal g = new ThreadLocal();
    private ThreadLocal h = new ThreadLocal();
    private volatile ECParameterSpec i;
    private volatile Object j;
    private volatile Set k = new HashSet();
    private volatile Map l = new HashMap();

    BouncyCastleProviderConfiguration() {
    }

    /* access modifiers changed from: package-private */
    public void e(String parameterName, Object parameter) {
        ECParameterSpec curveSpec;
        SecurityManager securityManager = System.getSecurityManager();
        if (parameterName.equals("threadLocalEcImplicitlyCa")) {
            if (securityManager != null) {
                securityManager.checkPermission(a);
            }
            if ((parameter instanceof ECParameterSpec) || parameter == null) {
                curveSpec = (ECParameterSpec) parameter;
            } else {
                curveSpec = EC5Util.g((java.security.spec.ECParameterSpec) parameter, false);
            }
            if (curveSpec == null) {
                this.g.remove();
            } else {
                this.g.set(curveSpec);
            }
        } else if (parameterName.equals("ecImplicitlyCa")) {
            if (securityManager != null) {
                securityManager.checkPermission(b);
            }
            if ((parameter instanceof ECParameterSpec) || parameter == null) {
                this.i = (ECParameterSpec) parameter;
            } else {
                this.i = EC5Util.g((java.security.spec.ECParameterSpec) parameter, false);
            }
        } else if (parameterName.equals("threadLocalDhDefaultParams")) {
            if (securityManager != null) {
                securityManager.checkPermission(c);
            }
            if ((parameter instanceof DHParameterSpec) || (parameter instanceof DHParameterSpec[]) || parameter == null) {
                Object dhSpec = parameter;
                if (dhSpec == null) {
                    this.h.remove();
                } else {
                    this.h.set(dhSpec);
                }
            } else {
                throw new IllegalArgumentException("not a valid DHParameterSpec");
            }
        } else if (parameterName.equals("DhDefaultParams")) {
            if (securityManager != null) {
                securityManager.checkPermission(d);
            }
            if ((parameter instanceof DHParameterSpec) || (parameter instanceof DHParameterSpec[]) || parameter == null) {
                this.j = parameter;
                return;
            }
            throw new IllegalArgumentException("not a valid DHParameterSpec or DHParameterSpec[]");
        } else if (parameterName.equals("acceptableEcCurves")) {
            if (securityManager != null) {
                securityManager.checkPermission(e);
            }
            this.k = (Set) parameter;
        } else if (parameterName.equals("additionalEcParameters")) {
            if (securityManager != null) {
                securityManager.checkPermission(f);
            }
            this.l = (Map) parameter;
        }
    }

    public ECParameterSpec b() {
        ECParameterSpec spec = (ECParameterSpec) this.g.get();
        if (spec != null) {
            return spec;
        }
        return this.i;
    }

    public DHParameterSpec d(int keySize) {
        Object params = this.h.get();
        if (params == null) {
            params = this.j;
        }
        if (params instanceof DHParameterSpec) {
            DHParameterSpec spec = (DHParameterSpec) params;
            if (spec.getP().bitLength() == keySize) {
                return spec;
            }
            return null;
        } else if (!(params instanceof DHParameterSpec[])) {
            return null;
        } else {
            DHParameterSpec[] specs = (DHParameterSpec[]) params;
            for (int i2 = 0; i2 != specs.length; i2++) {
                if (specs[i2].getP().bitLength() == keySize) {
                    return specs[i2];
                }
            }
            return null;
        }
    }

    public Set c() {
        return Collections.unmodifiableSet(this.k);
    }

    public Map a() {
        return Collections.unmodifiableMap(this.l);
    }
}
