package org.spongycastle.jce.provider;

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
import org.spongycastle.jcajce.provider.symmetric.util.ClassUtil;
import org.spongycastle.jcajce.provider.util.AlgorithmProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.jcajce.provider.mceliece.McElieceCCA2KeyFactorySpi;
import org.spongycastle.pqc.jcajce.provider.mceliece.McElieceKeyFactorySpi;
import org.spongycastle.pqc.jcajce.provider.newhope.NHKeyFactorySpi;
import org.spongycastle.pqc.jcajce.provider.rainbow.RainbowKeyFactorySpi;
import org.spongycastle.pqc.jcajce.provider.sphincs.Sphincs256KeyFactorySpi;
import org.spongycastle.pqc.jcajce.provider.xmss.XMSSKeyFactorySpi;
import org.spongycastle.pqc.jcajce.provider.xmss.XMSSMTKeyFactorySpi;

public final class BouncyCastleProvider extends Provider implements ConfigurableProvider {
    public static final ProviderConfiguration CONFIGURATION = new BouncyCastleProviderConfiguration();
    public static final String PROVIDER_NAME = "SC";
    private static final String[] a1 = {"BC", "BCFKS", "PKCS12"};
    private static String c = "BouncyCastle Security Provider v1.58";
    private static final Map d = new HashMap();
    private static final String[] f = {"PBEPBKDF1", "PBEPBKDF2", "PBEPKCS12", "TLSKDF"};
    private static final String[] p0 = {"GOST3411", "Keccak", "MD2", "MD4", "MD5", "SHA1", "RIPEMD128", "RIPEMD160", "RIPEMD256", "RIPEMD320", "SHA224", "SHA256", "SHA384", "SHA512", "SHA3", "Skein", "SM3", "Tiger", "Whirlpool", "Blake2b", "DSTU7564"};
    private static final String[] p1 = {"DRBG"};
    private static final String[] q = {"SipHash", "Poly1305"};
    private static final String[] x = {"AES", "ARC4", "ARIA", "Blowfish", "Camellia", "CAST5", "CAST6", "ChaCha", "DES", "DESede", "GOST28147", "Grainv1", "Grain128", "HC128", "HC256", "IDEA", "Noekeon", "RC2", "RC5", "RC6", "Rijndael", "Salsa20", "SEED", "Serpent", "Shacal2", "Skipjack", "SM4", "TEA", "Twofish", "Threefish", "VMPC", "VMPCKSA3", "XTEA", "XSalsa20", "OpenSSLPBKDF", "DSTU7624"};
    private static final String[] y = {"X509", "IES"};
    private static final String[] z = {"DSA", "DH", "EC", "RSA", "GOST", "ECGOST", "ElGamal", "DSTU4145", "GM"};

    public BouncyCastleProvider() {
        super(PROVIDER_NAME, 1.58d, c);
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                BouncyCastleProvider.this.e();
                return null;
            }
        });
    }

    /* access modifiers changed from: private */
    public void e() {
        b("org.spongycastle.jcajce.provider.digest.", p0);
        b("org.spongycastle.jcajce.provider.symmetric.", f);
        b("org.spongycastle.jcajce.provider.symmetric.", q);
        b("org.spongycastle.jcajce.provider.symmetric.", x);
        b("org.spongycastle.jcajce.provider.asymmetric.", y);
        b("org.spongycastle.jcajce.provider.asymmetric.", z);
        b("org.spongycastle.jcajce.provider.keystore.", a1);
        b("org.spongycastle.jcajce.provider.drbg.", p1);
        c();
        put("X509Store.CERTIFICATE/COLLECTION", "org.spongycastle.jce.provider.X509StoreCertCollection");
        put("X509Store.ATTRIBUTECERTIFICATE/COLLECTION", "org.spongycastle.jce.provider.X509StoreAttrCertCollection");
        put("X509Store.CRL/COLLECTION", "org.spongycastle.jce.provider.X509StoreCRLCollection");
        put("X509Store.CERTIFICATEPAIR/COLLECTION", "org.spongycastle.jce.provider.X509StoreCertPairCollection");
        put("X509Store.CERTIFICATE/LDAP", "org.spongycastle.jce.provider.X509StoreLDAPCerts");
        put("X509Store.CRL/LDAP", "org.spongycastle.jce.provider.X509StoreLDAPCRLs");
        put("X509Store.ATTRIBUTECERTIFICATE/LDAP", "org.spongycastle.jce.provider.X509StoreLDAPAttrCerts");
        put("X509Store.CERTIFICATEPAIR/LDAP", "org.spongycastle.jce.provider.X509StoreLDAPCertPairs");
        put("X509StreamParser.CERTIFICATE", "org.spongycastle.jce.provider.X509CertParser");
        put("X509StreamParser.ATTRIBUTECERTIFICATE", "org.spongycastle.jce.provider.X509AttrCertParser");
        put("X509StreamParser.CRL", "org.spongycastle.jce.provider.X509CRLParser");
        put("X509StreamParser.CERTIFICATEPAIR", "org.spongycastle.jce.provider.X509CertPairParser");
        put("Cipher.BROKENPBEWITHMD5ANDDES", "org.spongycastle.jce.provider.BrokenJCEBlockCipher$BrokePBEWithMD5AndDES");
        put("Cipher.BROKENPBEWITHSHA1ANDDES", "org.spongycastle.jce.provider.BrokenJCEBlockCipher$BrokePBEWithSHA1AndDES");
        put("Cipher.OLDPBEWITHSHAANDTWOFISH-CBC", "org.spongycastle.jce.provider.BrokenJCEBlockCipher$OldPBEWithSHAAndTwofish");
        put("CertPathValidator.RFC3281", "org.spongycastle.jce.provider.PKIXAttrCertPathValidatorSpi");
        put("CertPathBuilder.RFC3281", "org.spongycastle.jce.provider.PKIXAttrCertPathBuilderSpi");
        put("CertPathValidator.RFC3280", "org.spongycastle.jce.provider.PKIXCertPathValidatorSpi");
        put("CertPathBuilder.RFC3280", "org.spongycastle.jce.provider.PKIXCertPathBuilderSpi");
        put("CertPathValidator.PKIX", "org.spongycastle.jce.provider.PKIXCertPathValidatorSpi");
        put("CertPathBuilder.PKIX", "org.spongycastle.jce.provider.PKIXCertPathBuilderSpi");
        put("CertStore.Collection", "org.spongycastle.jce.provider.CertStoreCollectionSpi");
        put("CertStore.LDAP", "org.spongycastle.jce.provider.X509LDAPCertStoreSpi");
        put("CertStore.Multi", "org.spongycastle.jce.provider.MultiCertStoreSpi");
        put("Alg.Alias.CertStore.X509LDAP", "LDAP");
    }

    private void b(String packageName, String[] names) {
        for (int i = 0; i != names.length; i++) {
            Class clazz = ClassUtil.a(BouncyCastleProvider.class, packageName + names[i] + "$Mappings");
            if (clazz != null) {
                try {
                    ((AlgorithmProvider) clazz.newInstance()).a(this);
                } catch (Exception e) {
                    throw new InternalError("cannot create instance of " + packageName + names[i] + "$Mappings : " + e);
                }
            }
        }
    }

    private void c() {
        addKeyInfoConverter(PQCObjectIdentifiers.r, new Sphincs256KeyFactorySpi());
        addKeyInfoConverter(PQCObjectIdentifiers.v, new NHKeyFactorySpi());
        addKeyInfoConverter(PQCObjectIdentifiers.w, new XMSSKeyFactorySpi());
        addKeyInfoConverter(PQCObjectIdentifiers.B, new XMSSMTKeyFactorySpi());
        addKeyInfoConverter(PQCObjectIdentifiers.m, new McElieceKeyFactorySpi());
        addKeyInfoConverter(PQCObjectIdentifiers.n, new McElieceCCA2KeyFactorySpi());
        addKeyInfoConverter(PQCObjectIdentifiers.a, new RainbowKeyFactorySpi());
    }

    public void setParameter(String parameterName, Object parameter) {
        ProviderConfiguration providerConfiguration = CONFIGURATION;
        synchronized (providerConfiguration) {
            ((BouncyCastleProviderConfiguration) providerConfiguration).e(parameterName, parameter);
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
        addAlgorithm(type + "." + oid, className);
        addAlgorithm(type + ".OID." + oid, className);
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
}
