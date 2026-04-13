package org.spongycastle.pqc.jcajce.provider;

import java.security.AccessController;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;

public class BouncyCastlePQCProvider extends Provider implements ConfigurableProvider {
    public static final ProviderConfiguration CONFIGURATION = null;
    public static String PROVIDER_NAME = "BCPQC";
    private static String c = "BouncyCastle Post-Quantum Security Provider v1.58";
    private static final Map d = new HashMap();
    private static final String[] f = {"Rainbow", "McEliece", "SPHINCS", "NH", "XMSS"};

    public BouncyCastlePQCProvider() {
        super(PROVIDER_NAME, 1.58d, c);
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                BouncyCastlePQCProvider.this.c();
                return null;
            }
        });
    }

    /* access modifiers changed from: private */
    public void c() {
        b("org.spongycastle.pqc.jcajce.provider.", f);
    }

    private void b(String packageName, String[] names) {
        for (int i = 0; i != names.length; i++) {
            Class clazz = loadClass(BouncyCastlePQCProvider.class, packageName + names[i] + "$Mappings");
            if (clazz != null) {
                try {
                    ((AlgorithmProvider) clazz.newInstance()).a(this);
                } catch (Exception e) {
                    throw new InternalError("cannot create instance of " + packageName + names[i] + "$Mappings : " + e);
                }
            }
        }
    }

    public void setParameter(String parameterName, Object parameter) {
        synchronized (CONFIGURATION) {
        }
    }

    public boolean hasAlgorithm(String type, String name) {
        if (!containsKey(type + "." + name)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Alg.Alias.");
            sb.append(type);
            sb.append(".");
            sb.append(name);
            return containsKey(sb.toString());
        }
    }

    public void addAlgorithm(String key, String value) {
        if (!containsKey(key)) {
            put(key, value);
            return;
        }
        throw new IllegalStateException("duplicate provider key (" + key + ") found");
    }

    public void addAlgorithm(String type, ASN1ObjectIdentifier oid, String className) {
        if (containsKey(type + "." + className)) {
            addAlgorithm(type + "." + oid, className);
            addAlgorithm(type + ".OID." + oid, className);
            return;
        }
        throw new IllegalStateException("primary key (" + type + "." + className + ") not found");
    }

    public void addKeyInfoConverter(ASN1ObjectIdentifier oid, AsymmetricKeyInfoConverter keyInfoConverter) {
        Map map = d;
        synchronized (map) {
            map.put(oid, keyInfoConverter);
        }
    }

    public void addAttributes(String key, Map<String, String> attributeMap) {
        for (String attributeName : attributeMap.keySet()) {
            String attributeKey = key + " " + attributeName;
            if (!containsKey(attributeKey)) {
                put(attributeKey, attributeMap.get(attributeName));
            } else {
                throw new IllegalStateException("duplicate provider attribute key (" + attributeKey + ") found");
            }
        }
    }

    private static AsymmetricKeyInfoConverter a(ASN1ObjectIdentifier algorithm) {
        AsymmetricKeyInfoConverter asymmetricKeyInfoConverter;
        Map map = d;
        synchronized (map) {
            asymmetricKeyInfoConverter = (AsymmetricKeyInfoConverter) map.get(algorithm);
        }
        return asymmetricKeyInfoConverter;
    }

    public static PublicKey getPublicKey(SubjectPublicKeyInfo publicKeyInfo) {
        AsymmetricKeyInfoConverter converter = a(publicKeyInfo.e().e());
        if (converter == null) {
            return null;
        }
        return converter.b(publicKeyInfo);
    }

    public static PrivateKey getPrivateKey(PrivateKeyInfo privateKeyInfo) {
        AsymmetricKeyInfoConverter converter = a(privateKeyInfo.g().e());
        if (converter == null) {
            return null;
        }
        return converter.a(privateKeyInfo);
    }

    static Class loadClass(Class sourceClass, final String className) {
        try {
            ClassLoader loader = sourceClass.getClassLoader();
            if (loader != null) {
                return loader.loadClass(className);
            }
            return (Class) AccessController.doPrivileged(new PrivilegedAction() {
                public Object run() {
                    try {
                        return Class.forName(className);
                    } catch (Exception e) {
                        return null;
                    }
                }
            });
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
