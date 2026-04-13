package org.spongycastle.jcajce.provider.asymmetric;

import java.util.HashMap;
import java.util.Map;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.jcajce.provider.asymmetric.rsa.KeyFactorySpi;
import org.spongycastle.jcajce.provider.config.ConfigurableProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;
import org.spongycastle.jcajce.provider.util.AsymmetricKeyInfoConverter;

public class RSA {
    /* access modifiers changed from: private */
    public static final Map<String, String> a;

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        hashMap.put("SupportedKeyClasses", "javax.crypto.interfaces.RSAPublicKey|javax.crypto.interfaces.RSAPrivateKey");
        hashMap.put("SupportedKeyFormats", "PKCS#8|X.509");
    }

    public static class Mappings extends AsymmetricAlgorithmProvider {
        public void a(ConfigurableProvider provider) {
            provider.addAlgorithm("AlgorithmParameters.OAEP", "org.spongycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi$OAEP");
            provider.addAlgorithm("AlgorithmParameters.PSS", "org.spongycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi$PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.RSAPSS", "PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.RSASSA-PSS", "PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA224withRSA/PSS", "PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA256withRSA/PSS", "PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA384withRSA/PSS", "PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA512withRSA/PSS", "PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA224WITHRSAANDMGF1", "PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA256WITHRSAANDMGF1", "PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA384WITHRSAANDMGF1", "PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA512WITHRSAANDMGF1", "PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA3-224WITHRSAANDMGF1", "PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA3-256WITHRSAANDMGF1", "PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA3-384WITHRSAANDMGF1", "PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA3-512WITHRSAANDMGF1", "PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.RAWRSAPSS", "PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.NONEWITHRSAPSS", "PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.NONEWITHRSASSA-PSS", "PSS");
            provider.addAlgorithm("Alg.Alias.AlgorithmParameters.NONEWITHRSAANDMGF1", "PSS");
            provider.addAttributes("Cipher.RSA", RSA.a);
            provider.addAlgorithm("Cipher.RSA", "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$NoPadding");
            provider.addAlgorithm("Cipher.RSA/RAW", "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$NoPadding");
            provider.addAlgorithm("Cipher.RSA/PKCS1", "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$PKCS1v1_5Padding");
            ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.K;
            provider.addAlgorithm("Cipher", aSN1ObjectIdentifier, "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$PKCS1v1_5Padding");
            ASN1ObjectIdentifier aSN1ObjectIdentifier2 = X509ObjectIdentifiers.O2;
            provider.addAlgorithm("Cipher", aSN1ObjectIdentifier2, "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$PKCS1v1_5Padding");
            provider.addAlgorithm("Cipher.RSA/1", "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$PKCS1v1_5Padding_PrivateOnly");
            provider.addAlgorithm("Cipher.RSA/2", "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$PKCS1v1_5Padding_PublicOnly");
            provider.addAlgorithm("Cipher.RSA/OAEP", "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$OAEPPadding");
            ASN1ObjectIdentifier aSN1ObjectIdentifier3 = PKCSObjectIdentifiers.Q;
            provider.addAlgorithm("Cipher", aSN1ObjectIdentifier3, "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$OAEPPadding");
            provider.addAlgorithm("Cipher.RSA/ISO9796-1", "org.spongycastle.jcajce.provider.asymmetric.rsa.CipherSpi$ISO9796d1Padding");
            provider.addAlgorithm("Alg.Alias.Cipher.RSA//RAW", "RSA");
            provider.addAlgorithm("Alg.Alias.Cipher.RSA//NOPADDING", "RSA");
            provider.addAlgorithm("Alg.Alias.Cipher.RSA//PKCS1PADDING", "RSA/PKCS1");
            provider.addAlgorithm("Alg.Alias.Cipher.RSA//OAEPPADDING", "RSA/OAEP");
            provider.addAlgorithm("Alg.Alias.Cipher.RSA//ISO9796-1PADDING", "RSA/ISO9796-1");
            provider.addAlgorithm("KeyFactory.RSA", "org.spongycastle.jcajce.provider.asymmetric.rsa.KeyFactorySpi");
            provider.addAlgorithm("KeyPairGenerator.RSA", "org.spongycastle.jcajce.provider.asymmetric.rsa.KeyPairGeneratorSpi");
            AsymmetricKeyInfoConverter keyFact = new KeyFactorySpi();
            c(provider, aSN1ObjectIdentifier, "RSA", keyFact);
            c(provider, aSN1ObjectIdentifier2, "RSA", keyFact);
            c(provider, aSN1ObjectIdentifier3, "RSA", keyFact);
            ASN1ObjectIdentifier aSN1ObjectIdentifier4 = PKCSObjectIdentifiers.T;
            c(provider, aSN1ObjectIdentifier4, "RSA", keyFact);
            e(provider, aSN1ObjectIdentifier, "RSA");
            e(provider, aSN1ObjectIdentifier2, "RSA");
            e(provider, aSN1ObjectIdentifier3, "OAEP");
            e(provider, aSN1ObjectIdentifier4, "PSS");
            provider.addAlgorithm("Signature.RSASSA-PSS", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$PSSwithRSA");
            provider.addAlgorithm("Signature." + aSN1ObjectIdentifier4, "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$PSSwithRSA");
            provider.addAlgorithm("Signature.OID." + aSN1ObjectIdentifier4, "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$PSSwithRSA");
            provider.addAlgorithm("Signature.RSA", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$noneRSA");
            provider.addAlgorithm("Signature.RAWRSASSA-PSS", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$nonePSS");
            provider.addAlgorithm("Alg.Alias.Signature.RAWRSA", "RSA");
            provider.addAlgorithm("Alg.Alias.Signature.NONEWITHRSA", "RSA");
            provider.addAlgorithm("Alg.Alias.Signature.RAWRSAPSS", "RAWRSASSA-PSS");
            provider.addAlgorithm("Alg.Alias.Signature.NONEWITHRSAPSS", "RAWRSASSA-PSS");
            provider.addAlgorithm("Alg.Alias.Signature.NONEWITHRSASSA-PSS", "RAWRSASSA-PSS");
            provider.addAlgorithm("Alg.Alias.Signature.NONEWITHRSAANDMGF1", "RAWRSASSA-PSS");
            provider.addAlgorithm("Alg.Alias.Signature.RSAPSS", "RSASSA-PSS");
            h(provider, "SHA224", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA224withRSA");
            h(provider, "SHA256", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA256withRSA");
            h(provider, "SHA384", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA384withRSA");
            h(provider, "SHA512", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA512withRSA");
            h(provider, "SHA512(224)", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA512_224withRSA");
            h(provider, "SHA512(256)", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA512_256withRSA");
            h(provider, "SHA3-224", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA3_224withRSA");
            h(provider, "SHA3-256", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA3_256withRSA");
            h(provider, "SHA3-384", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA3_384withRSA");
            h(provider, "SHA3-512", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA3_512withRSA");
            if (provider.hasAlgorithm("MessageDigest", "MD2")) {
                f(provider, "MD2", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$MD2", PKCSObjectIdentifiers.L);
            }
            if (provider.hasAlgorithm("MessageDigest", "MD4")) {
                f(provider, "MD4", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$MD4", PKCSObjectIdentifiers.M);
            }
            if (provider.hasAlgorithm("MessageDigest", "MD5")) {
                f(provider, "MD5", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$MD5", PKCSObjectIdentifiers.N);
                g(provider, "MD5", "org.spongycastle.jcajce.provider.asymmetric.rsa.ISOSignatureSpi$MD5WithRSAEncryption");
            }
            if (provider.hasAlgorithm("MessageDigest", "SHA1")) {
                provider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA1withRSA/PSS", "PSS");
                provider.addAlgorithm("Alg.Alias.AlgorithmParameters.SHA1WITHRSAANDMGF1", "PSS");
                h(provider, "SHA1", "org.spongycastle.jcajce.provider.asymmetric.rsa.PSSSignatureSpi$SHA1withRSA");
                f(provider, "SHA1", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA1", PKCSObjectIdentifiers.O);
                g(provider, "SHA1", "org.spongycastle.jcajce.provider.asymmetric.rsa.ISOSignatureSpi$SHA1WithRSAEncryption");
                StringBuilder sb = new StringBuilder();
                sb.append("Alg.Alias.Signature.");
                ASN1ObjectIdentifier aSN1ObjectIdentifier5 = OIWObjectIdentifiers.k;
                sb.append(aSN1ObjectIdentifier5);
                provider.addAlgorithm(sb.toString(), "SHA1WITHRSA");
                provider.addAlgorithm("Alg.Alias.Signature.OID." + aSN1ObjectIdentifier5, "SHA1WITHRSA");
                i(provider, "SHA1", "org.spongycastle.jcajce.provider.asymmetric.rsa.X931SignatureSpi$SHA1WithRSAEncryption");
            }
            f(provider, "SHA224", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA224", PKCSObjectIdentifiers.X);
            f(provider, "SHA256", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA256", PKCSObjectIdentifiers.U);
            f(provider, "SHA384", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA384", PKCSObjectIdentifiers.V);
            f(provider, "SHA512", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA512", PKCSObjectIdentifiers.W);
            f(provider, "SHA512(224)", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA512_224", PKCSObjectIdentifiers.Y);
            f(provider, "SHA512(256)", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA512_256", PKCSObjectIdentifiers.Z);
            f(provider, "SHA3-224", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA3_224", NISTObjectIdentifiers.f0);
            f(provider, "SHA3-256", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA3_256", NISTObjectIdentifiers.g0);
            f(provider, "SHA3-384", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA3_384", NISTObjectIdentifiers.h0);
            f(provider, "SHA3-512", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$SHA3_512", NISTObjectIdentifiers.i0);
            g(provider, "SHA224", "org.spongycastle.jcajce.provider.asymmetric.rsa.ISOSignatureSpi$SHA224WithRSAEncryption");
            g(provider, "SHA256", "org.spongycastle.jcajce.provider.asymmetric.rsa.ISOSignatureSpi$SHA256WithRSAEncryption");
            g(provider, "SHA384", "org.spongycastle.jcajce.provider.asymmetric.rsa.ISOSignatureSpi$SHA384WithRSAEncryption");
            g(provider, "SHA512", "org.spongycastle.jcajce.provider.asymmetric.rsa.ISOSignatureSpi$SHA512WithRSAEncryption");
            g(provider, "SHA512(224)", "org.spongycastle.jcajce.provider.asymmetric.rsa.ISOSignatureSpi$SHA512_224WithRSAEncryption");
            g(provider, "SHA512(256)", "org.spongycastle.jcajce.provider.asymmetric.rsa.ISOSignatureSpi$SHA512_256WithRSAEncryption");
            i(provider, "SHA224", "org.spongycastle.jcajce.provider.asymmetric.rsa.X931SignatureSpi$SHA224WithRSAEncryption");
            i(provider, "SHA256", "org.spongycastle.jcajce.provider.asymmetric.rsa.X931SignatureSpi$SHA256WithRSAEncryption");
            i(provider, "SHA384", "org.spongycastle.jcajce.provider.asymmetric.rsa.X931SignatureSpi$SHA384WithRSAEncryption");
            i(provider, "SHA512", "org.spongycastle.jcajce.provider.asymmetric.rsa.X931SignatureSpi$SHA512WithRSAEncryption");
            i(provider, "SHA512(224)", "org.spongycastle.jcajce.provider.asymmetric.rsa.X931SignatureSpi$SHA512_224WithRSAEncryption");
            i(provider, "SHA512(256)", "org.spongycastle.jcajce.provider.asymmetric.rsa.X931SignatureSpi$SHA512_256WithRSAEncryption");
            if (provider.hasAlgorithm("MessageDigest", "RIPEMD128")) {
                f(provider, "RIPEMD128", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD128", TeleTrusTObjectIdentifiers.g);
                f(provider, "RMD128", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD128", (ASN1ObjectIdentifier) null);
                i(provider, "RMD128", "org.spongycastle.jcajce.provider.asymmetric.rsa.X931SignatureSpi$RIPEMD128WithRSAEncryption");
                i(provider, "RIPEMD128", "org.spongycastle.jcajce.provider.asymmetric.rsa.X931SignatureSpi$RIPEMD128WithRSAEncryption");
            }
            if (provider.hasAlgorithm("MessageDigest", "RIPEMD160")) {
                f(provider, "RIPEMD160", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD160", TeleTrusTObjectIdentifiers.f);
                f(provider, "RMD160", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD160", (ASN1ObjectIdentifier) null);
                provider.addAlgorithm("Alg.Alias.Signature.RIPEMD160WithRSA/ISO9796-2", "RIPEMD160withRSA/ISO9796-2");
                provider.addAlgorithm("Signature.RIPEMD160withRSA/ISO9796-2", "org.spongycastle.jcajce.provider.asymmetric.rsa.ISOSignatureSpi$RIPEMD160WithRSAEncryption");
                i(provider, "RMD160", "org.spongycastle.jcajce.provider.asymmetric.rsa.X931SignatureSpi$RIPEMD160WithRSAEncryption");
                i(provider, "RIPEMD160", "org.spongycastle.jcajce.provider.asymmetric.rsa.X931SignatureSpi$RIPEMD160WithRSAEncryption");
            }
            if (provider.hasAlgorithm("MessageDigest", "RIPEMD256")) {
                f(provider, "RIPEMD256", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD256", TeleTrusTObjectIdentifiers.h);
                f(provider, "RMD256", "org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi$RIPEMD256", (ASN1ObjectIdentifier) null);
            }
            if (provider.hasAlgorithm("MessageDigest", "WHIRLPOOL")) {
                g(provider, "Whirlpool", "org.spongycastle.jcajce.provider.asymmetric.rsa.ISOSignatureSpi$WhirlpoolWithRSAEncryption");
                g(provider, "WHIRLPOOL", "org.spongycastle.jcajce.provider.asymmetric.rsa.ISOSignatureSpi$WhirlpoolWithRSAEncryption");
                i(provider, "Whirlpool", "org.spongycastle.jcajce.provider.asymmetric.rsa.X931SignatureSpi$WhirlpoolWithRSAEncryption");
                i(provider, "WHIRLPOOL", "org.spongycastle.jcajce.provider.asymmetric.rsa.X931SignatureSpi$WhirlpoolWithRSAEncryption");
            }
        }

        private void f(ConfigurableProvider provider, String digest, String className, ASN1ObjectIdentifier oid) {
            String mainName = digest + "WITHRSA";
            provider.addAlgorithm("Signature." + mainName, className);
            provider.addAlgorithm("Alg.Alias.Signature." + (digest + "withRSA"), mainName);
            provider.addAlgorithm("Alg.Alias.Signature." + (digest + "WithRSA"), mainName);
            provider.addAlgorithm("Alg.Alias.Signature." + (digest + "WITHRSAENCRYPTION"), mainName);
            provider.addAlgorithm("Alg.Alias.Signature." + (digest + "withRSAEncryption"), mainName);
            provider.addAlgorithm("Alg.Alias.Signature." + (digest + "WithRSAEncryption"), mainName);
            provider.addAlgorithm("Alg.Alias.Signature." + (digest + "/RSA"), mainName);
            if (oid != null) {
                provider.addAlgorithm("Alg.Alias.Signature." + oid, mainName);
                provider.addAlgorithm("Alg.Alias.Signature.OID." + oid, mainName);
            }
        }

        private void g(ConfigurableProvider provider, String digest, String className) {
            provider.addAlgorithm("Alg.Alias.Signature." + digest + "withRSA/ISO9796-2", digest + "WITHRSA/ISO9796-2");
            provider.addAlgorithm("Alg.Alias.Signature." + digest + "WithRSA/ISO9796-2", digest + "WITHRSA/ISO9796-2");
            provider.addAlgorithm("Signature." + digest + "WITHRSA/ISO9796-2", className);
        }

        private void h(ConfigurableProvider provider, String digest, String className) {
            provider.addAlgorithm("Alg.Alias.Signature." + digest + "withRSA/PSS", digest + "WITHRSAANDMGF1");
            provider.addAlgorithm("Alg.Alias.Signature." + digest + "WithRSA/PSS", digest + "WITHRSAANDMGF1");
            provider.addAlgorithm("Alg.Alias.Signature." + digest + "withRSAandMGF1", digest + "WITHRSAANDMGF1");
            provider.addAlgorithm("Alg.Alias.Signature." + digest + "WithRSAAndMGF1", digest + "WITHRSAANDMGF1");
            provider.addAlgorithm("Signature." + digest + "WITHRSAANDMGF1", className);
        }

        private void i(ConfigurableProvider provider, String digest, String className) {
            provider.addAlgorithm("Alg.Alias.Signature." + digest + "withRSA/X9.31", digest + "WITHRSA/X9.31");
            provider.addAlgorithm("Alg.Alias.Signature." + digest + "WithRSA/X9.31", digest + "WITHRSA/X9.31");
            provider.addAlgorithm("Signature." + digest + "WITHRSA/X9.31", className);
        }
    }
}
