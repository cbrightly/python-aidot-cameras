package org.spongycastle.jcajce.provider.keystore.pkcs12;

import com.alibaba.fastjson.asm.Opcodes;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.GOST28147Parameters;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.ntt.NTTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PBES2Parameters;
import org.spongycastle.asn1.pkcs.PBKDF2Params;
import org.spongycastle.asn1.pkcs.PKCS12PBEParams;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.AuthorityKeyIdentifier;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.SubjectKeyIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.jcajce.PKCS12Key;
import org.spongycastle.jcajce.PKCS12StoreParameter;
import org.spongycastle.jcajce.spec.GOST28147ParameterSpec;
import org.spongycastle.jcajce.spec.PBKDF2KeySpec;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.interfaces.BCKeyStore;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.provider.JDKPKCS12StoreParameter;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Integers;
import org.spongycastle.util.Properties;
import org.spongycastle.util.Strings;

public class PKCS12KeyStoreSpi extends KeyStoreSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers, BCKeyStore {
    private static final DefaultSecretKeyProvider c = new DefaultSecretKeyProvider();
    private CertificateFactory a1;
    private ASN1ObjectIdentifier a2;
    private final JcaJceHelper d = new BCJcaJceHelper();
    private IgnoresCaseHashtable f = new IgnoresCaseHashtable();
    protected SecureRandom p0 = new SecureRandom();
    private ASN1ObjectIdentifier p1;
    private AlgorithmIdentifier p2 = new AlgorithmIdentifier(OIWObjectIdentifiers.i, DERNull.c);
    private int p3 = 1024;
    private int p4 = 20;
    private Hashtable q = new Hashtable();
    private IgnoresCaseHashtable x = new IgnoresCaseHashtable();
    private Hashtable y = new Hashtable();
    private Hashtable z = new Hashtable();

    public class CertId {
        byte[] a;

        CertId(PublicKey key) {
            this.a = PKCS12KeyStoreSpi.this.d(key).e();
        }

        CertId(byte[] id) {
            this.a = id;
        }

        public int hashCode() {
            return Arrays.J(this.a);
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof CertId)) {
                return false;
            }
            return Arrays.b(this.a, ((CertId) o).a);
        }
    }

    public PKCS12KeyStoreSpi(Provider provider, ASN1ObjectIdentifier keyAlgorithm, ASN1ObjectIdentifier certAlgorithm) {
        this.p1 = keyAlgorithm;
        this.a2 = certAlgorithm;
        if (provider != null) {
            try {
                this.a1 = CertificateFactory.getInstance("X.509", provider);
            } catch (Exception e) {
                throw new IllegalArgumentException("can't create cert factory - " + e.toString());
            }
        } else {
            this.a1 = CertificateFactory.getInstance("X.509");
        }
    }

    /* access modifiers changed from: private */
    public SubjectKeyIdentifier d(PublicKey pubKey) {
        try {
            return new SubjectKeyIdentifier(g(SubjectPublicKeyInfo.g(pubKey.getEncoded())));
        } catch (Exception e) {
            throw new RuntimeException("error creating key");
        }
    }

    private static byte[] g(SubjectPublicKeyInfo spki) {
        Digest digest = DigestFactory.b();
        byte[] resBuf = new byte[digest.e()];
        byte[] bytes = spki.h().q();
        digest.update(bytes, 0, bytes.length);
        digest.c(resBuf, 0);
        return resBuf;
    }

    public Enumeration engineAliases() {
        Hashtable tab = new Hashtable();
        Enumeration e = this.x.c();
        while (e.hasMoreElements()) {
            tab.put(e.nextElement(), "cert");
        }
        Enumeration e2 = this.f.c();
        while (e2.hasMoreElements()) {
            String a = (String) e2.nextElement();
            if (tab.get(a) == null) {
                tab.put(a, CacheEntity.KEY);
            }
        }
        return tab.keys();
    }

    public boolean engineContainsAlias(String alias) {
        return (this.x.b(alias) == null && this.f.b(alias) == null) ? false : true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: java.security.cert.Certificate} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void engineDeleteEntry(java.lang.String r7) {
        /*
            r6 = this;
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r0 = r6.f
            java.lang.Object r0 = r0.e(r7)
            java.security.Key r0 = (java.security.Key) r0
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r1 = r6.x
            java.lang.Object r1 = r1.e(r7)
            java.security.cert.Certificate r1 = (java.security.cert.Certificate) r1
            if (r1 == 0) goto L_0x0020
            java.util.Hashtable r2 = r6.y
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId r3 = new org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId
            java.security.PublicKey r4 = r1.getPublicKey()
            r3.<init>((java.security.PublicKey) r4)
            r2.remove(r3)
        L_0x0020:
            if (r0 == 0) goto L_0x0045
            java.util.Hashtable r2 = r6.q
            java.lang.Object r2 = r2.remove(r7)
            java.lang.String r2 = (java.lang.String) r2
            if (r2 == 0) goto L_0x0035
            java.util.Hashtable r3 = r6.z
            java.lang.Object r3 = r3.remove(r2)
            r1 = r3
            java.security.cert.Certificate r1 = (java.security.cert.Certificate) r1
        L_0x0035:
            if (r1 == 0) goto L_0x0045
            java.util.Hashtable r3 = r6.y
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId r4 = new org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId
            java.security.PublicKey r5 = r1.getPublicKey()
            r4.<init>((java.security.PublicKey) r5)
            r3.remove(r4)
        L_0x0045:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.engineDeleteEntry(java.lang.String):void");
    }

    public Certificate engineGetCertificate(String alias) {
        if (alias != null) {
            Certificate c2 = (Certificate) this.x.b(alias);
            if (c2 != null) {
                return c2;
            }
            String id = (String) this.q.get(alias);
            if (id != null) {
                return (Certificate) this.z.get(id);
            }
            return (Certificate) this.z.get(alias);
        }
        throw new IllegalArgumentException("null alias passed to getCertificate.");
    }

    public String engineGetCertificateAlias(Certificate cert) {
        Enumeration c2 = this.x.a();
        Enumeration k = this.x.c();
        while (c2.hasMoreElements()) {
            String ta = (String) k.nextElement();
            if (((Certificate) c2.nextElement()).equals(cert)) {
                return ta;
            }
        }
        Enumeration c3 = this.z.elements();
        Enumeration k2 = this.z.keys();
        while (c3.hasMoreElements()) {
            String ta2 = (String) k2.nextElement();
            if (((Certificate) c3.nextElement()).equals(cert)) {
                return ta2;
            }
        }
        return null;
    }

    public Certificate[] engineGetCertificateChain(String alias) {
        Certificate c2;
        if (alias == null) {
            throw new IllegalArgumentException("null alias passed to getCertificateChain.");
        } else if (!engineIsKeyEntry(alias) || (c2 = engineGetCertificate(alias)) == null) {
            return null;
        } else {
            Vector cs = new Vector();
            while (c2 != null) {
                X509Certificate x509c = (X509Certificate) c2;
                Certificate nextC = null;
                byte[] bytes = x509c.getExtensionValue(Extension.D4.s());
                if (bytes != null) {
                    try {
                        AuthorityKeyIdentifier id = AuthorityKeyIdentifier.e(new ASN1InputStream(((ASN1OctetString) new ASN1InputStream(bytes).r()).q()).r());
                        if (id.f() != null) {
                            nextC = (Certificate) this.y.get(new CertId(id.f()));
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e.toString());
                    }
                }
                if (nextC == null) {
                    Principal i = x509c.getIssuerDN();
                    if (!i.equals(x509c.getSubjectDN())) {
                        Enumeration e2 = this.y.keys();
                        while (true) {
                            if (!e2.hasMoreElements()) {
                                break;
                            }
                            X509Certificate crt = (X509Certificate) this.y.get(e2.nextElement());
                            if (crt.getSubjectDN().equals(i)) {
                                try {
                                    x509c.verify(crt.getPublicKey());
                                    nextC = crt;
                                    break;
                                } catch (Exception e3) {
                                }
                            }
                        }
                    }
                }
                if (cs.contains(c2)) {
                    c2 = null;
                } else {
                    cs.addElement(c2);
                    if (nextC != c2) {
                        c2 = nextC;
                    } else {
                        c2 = null;
                    }
                }
            }
            Certificate[] certChain = new Certificate[cs.size()];
            for (int i2 = 0; i2 != certChain.length; i2++) {
                certChain[i2] = (Certificate) cs.elementAt(i2);
            }
            return certChain;
        }
    }

    public Date engineGetCreationDate(String alias) {
        if (alias == null) {
            throw new NullPointerException("alias == null");
        } else if (this.f.b(alias) == null && this.x.b(alias) == null) {
            return null;
        } else {
            return new Date();
        }
    }

    public Key engineGetKey(String alias, char[] password) {
        if (alias != null) {
            return (Key) this.f.b(alias);
        }
        throw new IllegalArgumentException("null alias passed to getKey.");
    }

    public boolean engineIsCertificateEntry(String alias) {
        return this.x.b(alias) != null && this.f.b(alias) == null;
    }

    public boolean engineIsKeyEntry(String alias) {
        return this.f.b(alias) != null;
    }

    public void engineSetCertificateEntry(String alias, Certificate cert) {
        if (this.f.b(alias) == null) {
            this.x.d(alias, cert);
            this.y.put(new CertId(cert.getPublicKey()), cert);
            return;
        }
        throw new KeyStoreException("There is a key entry with the name " + alias + ".");
    }

    public void engineSetKeyEntry(String alias, byte[] key, Certificate[] chain) {
        throw new RuntimeException("operation not supported");
    }

    public void engineSetKeyEntry(String alias, Key key, char[] password, Certificate[] chain) {
        if (!(key instanceof PrivateKey)) {
            throw new KeyStoreException("PKCS12 does not support non-PrivateKeys");
        } else if (!(key instanceof PrivateKey) || chain != null) {
            if (this.f.b(alias) != null) {
                engineDeleteEntry(alias);
            }
            this.f.d(alias, key);
            if (chain != null) {
                this.x.d(alias, chain[0]);
                for (int i = 0; i != chain.length; i++) {
                    this.y.put(new CertId(chain[i].getPublicKey()), chain[i]);
                }
            }
        } else {
            throw new KeyStoreException("no certificate chain for private key");
        }
    }

    public int engineSize() {
        Hashtable tab = new Hashtable();
        Enumeration e = this.x.c();
        while (e.hasMoreElements()) {
            tab.put(e.nextElement(), "cert");
        }
        Enumeration e2 = this.f.c();
        while (e2.hasMoreElements()) {
            String a = (String) e2.nextElement();
            if (tab.get(a) == null) {
                tab.put(a, CacheEntity.KEY);
            }
        }
        return tab.size();
    }

    /* access modifiers changed from: protected */
    public PrivateKey i(AlgorithmIdentifier algId, byte[] data, char[] password, boolean wrongPKCS12Zero) {
        ASN1ObjectIdentifier algorithm = algId.e();
        try {
            if (algorithm.y(PKCSObjectIdentifiers.q2)) {
                PKCS12PBEParams pbeParams = PKCS12PBEParams.getInstance(algId.h());
                PBEParameterSpec defParams = new PBEParameterSpec(pbeParams.getIV(), j(pbeParams.getIterations()));
                Cipher cipher = this.d.a(algorithm.s());
                cipher.init(4, new PKCS12Key(password, wrongPKCS12Zero), defParams);
                return (PrivateKey) cipher.unwrap(data, "", 2);
            } else if (algorithm.equals(PKCSObjectIdentifiers.j0)) {
                return (PrivateKey) c(4, password, algId).unwrap(data, "", 2);
            } else {
                throw new IOException("exception unwrapping private key - cannot recognise: " + algorithm);
            }
        } catch (Exception e) {
            throw new IOException("exception unwrapping private key - " + e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public byte[] k(String algorithm, Key key, PKCS12PBEParams pbeParams, char[] password) {
        PBEKeySpec pbeSpec = new PBEKeySpec(password);
        try {
            SecretKeyFactory keyFact = this.d.c(algorithm);
            PBEParameterSpec defParams = new PBEParameterSpec(pbeParams.getIV(), pbeParams.getIterations().intValue());
            Cipher cipher = this.d.a(algorithm);
            cipher.init(3, keyFact.generateSecret(pbeSpec), defParams);
            return cipher.wrap(key);
        } catch (Exception e) {
            throw new IOException("exception encrypting data - " + e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public byte[] e(boolean forEncryption, AlgorithmIdentifier algId, char[] password, boolean wrongPKCS12Zero, byte[] data) {
        ASN1ObjectIdentifier algorithm = algId.e();
        int mode = forEncryption ? 1 : 2;
        if (algorithm.y(PKCSObjectIdentifiers.q2)) {
            PKCS12PBEParams pbeParams = PKCS12PBEParams.getInstance(algId.h());
            try {
                PBEParameterSpec defParams = new PBEParameterSpec(pbeParams.getIV(), pbeParams.getIterations().intValue());
                PKCS12Key key = new PKCS12Key(password, wrongPKCS12Zero);
                Cipher cipher = this.d.a(algorithm.s());
                cipher.init(mode, key, defParams);
                return cipher.doFinal(data);
            } catch (Exception e) {
                throw new IOException("exception decrypting data - " + e.toString());
            }
        } else if (algorithm.equals(PKCSObjectIdentifiers.j0)) {
            try {
                return c(mode, password, algId).doFinal(data);
            } catch (Exception e2) {
                throw new IOException("exception decrypting data - " + e2.toString());
            }
        } else {
            throw new IOException("unknown PBE algorithm: " + algorithm);
        }
    }

    private Cipher c(int mode, char[] password, AlgorithmIdentifier algId) {
        SecretKey key;
        PBES2Parameters alg = PBES2Parameters.f(algId.h());
        PBKDF2Params func = PBKDF2Params.getInstance(alg.g().g());
        AlgorithmIdentifier encScheme = AlgorithmIdentifier.f(alg.e());
        SecretKeyFactory keyFact = this.d.c(alg.g().e().s());
        if (func.isDefaultPrf()) {
            key = keyFact.generateSecret(new PBEKeySpec(password, func.getSalt(), j(func.getIterationCount()), c.a(encScheme)));
        } else {
            key = keyFact.generateSecret(new PBKDF2KeySpec(password, func.getSalt(), j(func.getIterationCount()), c.a(encScheme), func.getPrf()));
        }
        Cipher cipher = Cipher.getInstance(alg.e().e().s());
        ASN1Encodable encParams = alg.e().g();
        if (encParams instanceof ASN1OctetString) {
            cipher.init(mode, key, new IvParameterSpec(ASN1OctetString.o(encParams).q()));
        } else {
            GOST28147Parameters gParams = GOST28147Parameters.f(encParams);
            cipher.init(mode, key, new GOST28147ParameterSpec(gParams.e(), gParams.getIV()));
        }
        return cipher;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v64, resolved type: org.spongycastle.asn1.DERBMPString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v56, resolved type: org.spongycastle.asn1.DERBMPString} */
    /* JADX WARNING: type inference failed for: r11v5, types: [org.spongycastle.asn1.ASN1Primitive, org.spongycastle.asn1.ASN1Encodable, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r10v16, types: [org.spongycastle.asn1.ASN1Primitive, org.spongycastle.asn1.ASN1Encodable, java.lang.Object] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void engineLoad(java.io.InputStream r37, char[] r38) {
        /*
            r36 = this;
            r8 = r36
            r9 = r37
            r10 = r38
            if (r9 != 0) goto L_0x0009
            return
        L_0x0009:
            if (r10 == 0) goto L_0x0752
            java.io.BufferedInputStream r0 = new java.io.BufferedInputStream
            r0.<init>(r9)
            r11 = r0
            r0 = 10
            r11.mark(r0)
            int r12 = r11.read()
            r0 = 48
            if (r12 != r0) goto L_0x074a
            r11.reset()
            org.spongycastle.asn1.ASN1InputStream r0 = new org.spongycastle.asn1.ASN1InputStream
            r0.<init>((java.io.InputStream) r11)
            r13 = r0
            org.spongycastle.asn1.ASN1Primitive r0 = r13.r()
            r14 = r0
            org.spongycastle.asn1.ASN1Sequence r14 = (org.spongycastle.asn1.ASN1Sequence) r14
            org.spongycastle.asn1.pkcs.Pfx r15 = org.spongycastle.asn1.pkcs.Pfx.f(r14)
            org.spongycastle.asn1.pkcs.ContentInfo r16 = r15.e()
            java.util.Vector r0 = new java.util.Vector
            r0.<init>()
            r7 = r0
            r17 = 0
            r18 = 0
            org.spongycastle.asn1.pkcs.MacData r0 = r15.g()
            if (r0 == 0) goto L_0x010d
            org.spongycastle.asn1.pkcs.MacData r19 = r15.g()
            org.spongycastle.asn1.x509.DigestInfo r20 = r19.f()
            org.spongycastle.asn1.x509.AlgorithmIdentifier r0 = r20.e()
            r8.p2 = r0
            byte[] r6 = r19.getSalt()
            java.math.BigInteger r0 = r19.getIterationCount()
            int r0 = r8.j(r0)
            r8.p3 = r0
            int r0 = r6.length
            r8.p4 = r0
            org.spongycastle.asn1.ASN1Encodable r0 = r16.e()
            org.spongycastle.asn1.ASN1OctetString r0 = (org.spongycastle.asn1.ASN1OctetString) r0
            byte[] r21 = r0.q()
            org.spongycastle.asn1.x509.AlgorithmIdentifier r0 = r8.p2     // Catch:{ IOException -> 0x0106, Exception -> 0x00e5 }
            org.spongycastle.asn1.ASN1ObjectIdentifier r2 = r0.e()     // Catch:{ IOException -> 0x0106, Exception -> 0x00e5 }
            int r4 = r8.p3     // Catch:{ IOException -> 0x0106, Exception -> 0x00e5 }
            r0 = 0
            r1 = r36
            r3 = r6
            r5 = r38
            r22 = r6
            r6 = r0
            r9 = r7
            r7 = r21
            byte[] r0 = r1.b(r2, r3, r4, r5, r6, r7)     // Catch:{ IOException -> 0x00e1, Exception -> 0x00dd }
            byte[] r1 = r20.f()     // Catch:{ IOException -> 0x00e1, Exception -> 0x00dd }
            r7 = r1
            boolean r1 = org.spongycastle.util.Arrays.u(r0, r7)     // Catch:{ IOException -> 0x00e1, Exception -> 0x00dd }
            if (r1 != 0) goto L_0x00d5
            int r1 = r10.length     // Catch:{ IOException -> 0x00e1, Exception -> 0x00dd }
            java.lang.String r6 = "PKCS12 key store mac invalid - wrong password or corrupted file."
            if (r1 > 0) goto L_0x00c5
            org.spongycastle.asn1.x509.AlgorithmIdentifier r1 = r8.p2     // Catch:{ IOException -> 0x00e1, Exception -> 0x00dd }
            org.spongycastle.asn1.ASN1ObjectIdentifier r2 = r1.e()     // Catch:{ IOException -> 0x00e1, Exception -> 0x00dd }
            int r4 = r8.p3     // Catch:{ IOException -> 0x00e1, Exception -> 0x00dd }
            r23 = 1
            r1 = r36
            r3 = r22
            r5 = r38
            r24 = r0
            r0 = r6
            r6 = r23
            r23 = r11
            r11 = r7
            r7 = r21
            byte[] r1 = r1.b(r2, r3, r4, r5, r6, r7)     // Catch:{ IOException -> 0x00d3, Exception -> 0x00d1 }
            boolean r2 = org.spongycastle.util.Arrays.u(r1, r11)     // Catch:{ IOException -> 0x00d3, Exception -> 0x00d1 }
            if (r2 == 0) goto L_0x00bf
            r0 = 1
            r18 = r0
            goto L_0x00da
        L_0x00bf:
            java.io.IOException r2 = new java.io.IOException     // Catch:{ IOException -> 0x00d3, Exception -> 0x00d1 }
            r2.<init>(r0)     // Catch:{ IOException -> 0x00d3, Exception -> 0x00d1 }
            throw r2     // Catch:{ IOException -> 0x00d3, Exception -> 0x00d1 }
        L_0x00c5:
            r24 = r0
            r0 = r6
            r23 = r11
            r11 = r7
            java.io.IOException r1 = new java.io.IOException     // Catch:{ IOException -> 0x00d3, Exception -> 0x00d1 }
            r1.<init>(r0)     // Catch:{ IOException -> 0x00d3, Exception -> 0x00d1 }
            throw r1     // Catch:{ IOException -> 0x00d3, Exception -> 0x00d1 }
        L_0x00d1:
            r0 = move-exception
            goto L_0x00eb
        L_0x00d3:
            r0 = move-exception
            goto L_0x010c
        L_0x00d5:
            r24 = r0
            r23 = r11
            r11 = r7
        L_0x00da:
            r7 = r18
            goto L_0x0112
        L_0x00dd:
            r0 = move-exception
            r23 = r11
            goto L_0x00eb
        L_0x00e1:
            r0 = move-exception
            r23 = r11
            goto L_0x010c
        L_0x00e5:
            r0 = move-exception
            r22 = r6
            r9 = r7
            r23 = r11
        L_0x00eb:
            java.io.IOException r1 = new java.io.IOException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "error constructing MAC: "
            r2.append(r3)
            java.lang.String r3 = r0.toString()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0106:
            r0 = move-exception
            r22 = r6
            r9 = r7
            r23 = r11
        L_0x010c:
            throw r0
        L_0x010d:
            r9 = r7
            r23 = r11
            r7 = r18
        L_0x0112:
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r0 = new org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable
            r11 = 0
            r0.<init>()
            r8.f = r0
            java.util.Hashtable r0 = new java.util.Hashtable
            r0.<init>()
            r8.q = r0
            org.spongycastle.asn1.ASN1ObjectIdentifier r0 = r16.f()
            org.spongycastle.asn1.ASN1ObjectIdentifier r1 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.A0
            boolean r0 = r0.equals(r1)
            java.lang.String r6 = "unmarked"
            java.lang.String r5 = "attempt to add existing attribute with different value"
            if (r0 == 0) goto L_0x05d0
            org.spongycastle.asn1.ASN1InputStream r0 = new org.spongycastle.asn1.ASN1InputStream
            org.spongycastle.asn1.ASN1Encodable r1 = r16.e()
            org.spongycastle.asn1.ASN1OctetString r1 = (org.spongycastle.asn1.ASN1OctetString) r1
            byte[] r1 = r1.q()
            r0.<init>((byte[]) r1)
            r13 = r0
            org.spongycastle.asn1.ASN1Primitive r0 = r13.r()
            org.spongycastle.asn1.pkcs.AuthenticatedSafe r0 = org.spongycastle.asn1.pkcs.AuthenticatedSafe.f(r0)
            org.spongycastle.asn1.pkcs.ContentInfo[] r2 = r0.e()
            r1 = 0
        L_0x014e:
            int r11 = r2.length
            if (r1 == r11) goto L_0x05bc
            r11 = r2[r1]
            org.spongycastle.asn1.ASN1ObjectIdentifier r11 = r11.f()
            org.spongycastle.asn1.ASN1ObjectIdentifier r3 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.A0
            boolean r3 = r11.equals(r3)
            if (r3 == 0) goto L_0x02ed
            org.spongycastle.asn1.ASN1InputStream r3 = new org.spongycastle.asn1.ASN1InputStream
            r11 = r2[r1]
            org.spongycastle.asn1.ASN1Encodable r11 = r11.e()
            org.spongycastle.asn1.ASN1OctetString r11 = (org.spongycastle.asn1.ASN1OctetString) r11
            byte[] r11 = r11.q()
            r3.<init>((byte[]) r11)
            org.spongycastle.asn1.ASN1Primitive r11 = r3.r()
            org.spongycastle.asn1.ASN1Sequence r11 = (org.spongycastle.asn1.ASN1Sequence) r11
            r20 = 0
            r4 = r20
        L_0x017a:
            r21 = r0
            int r0 = r11.size()
            if (r4 == r0) goto L_0x02d8
            org.spongycastle.asn1.ASN1Encodable r0 = r11.r(r4)
            org.spongycastle.asn1.pkcs.SafeBag r0 = org.spongycastle.asn1.pkcs.SafeBag.h(r0)
            r22 = r3
            org.spongycastle.asn1.ASN1ObjectIdentifier r3 = r0.f()
            r24 = r11
            org.spongycastle.asn1.ASN1ObjectIdentifier r11 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.k2
            boolean r3 = r3.equals(r11)
            if (r3 == 0) goto L_0x028b
            org.spongycastle.asn1.ASN1Encodable r3 = r0.g()
            org.spongycastle.asn1.pkcs.EncryptedPrivateKeyInfo r3 = org.spongycastle.asn1.pkcs.EncryptedPrivateKeyInfo.g(r3)
            org.spongycastle.asn1.x509.AlgorithmIdentifier r11 = r3.f()
            r25 = r12
            byte[] r12 = r3.e()
            java.security.PrivateKey r11 = r8.i(r11, r12, r10, r7)
            r12 = r11
            org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier r12 = (org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier) r12
            r26 = 0
            r27 = 0
            org.spongycastle.asn1.ASN1Set r28 = r0.e()
            if (r28 == 0) goto L_0x025c
            org.spongycastle.asn1.ASN1Set r28 = r0.e()
            java.util.Enumeration r28 = r28.t()
        L_0x01c5:
            boolean r29 = r28.hasMoreElements()
            if (r29 == 0) goto L_0x0251
            java.lang.Object r29 = r28.nextElement()
            r30 = r3
            r3 = r29
            org.spongycastle.asn1.ASN1Sequence r3 = (org.spongycastle.asn1.ASN1Sequence) r3
            r29 = r13
            r13 = 0
            org.spongycastle.asn1.ASN1Encodable r20 = r3.r(r13)
            r13 = r20
            org.spongycastle.asn1.ASN1ObjectIdentifier r13 = (org.spongycastle.asn1.ASN1ObjectIdentifier) r13
            r20 = r14
            r14 = 1
            org.spongycastle.asn1.ASN1Encodable r19 = r3.r(r14)
            r14 = r19
            org.spongycastle.asn1.ASN1Set r14 = (org.spongycastle.asn1.ASN1Set) r14
            r19 = 0
            int r32 = r14.size()
            if (r32 <= 0) goto L_0x021f
            r32 = r15
            r15 = 0
            org.spongycastle.asn1.ASN1Encodable r31 = r14.s(r15)
            r15 = r31
            org.spongycastle.asn1.ASN1Primitive r15 = (org.spongycastle.asn1.ASN1Primitive) r15
            org.spongycastle.asn1.ASN1Encodable r19 = r12.getBagAttribute(r13)
            if (r19 == 0) goto L_0x0217
            r31 = r3
            org.spongycastle.asn1.ASN1Primitive r3 = r19.toASN1Primitive()
            boolean r3 = r3.equals(r15)
            if (r3 == 0) goto L_0x0211
            goto L_0x021c
        L_0x0211:
            java.io.IOException r3 = new java.io.IOException
            r3.<init>(r5)
            throw r3
        L_0x0217:
            r31 = r3
            r12.setBagAttribute(r13, r15)
        L_0x021c:
            r19 = r15
            goto L_0x0223
        L_0x021f:
            r31 = r3
            r32 = r15
        L_0x0223:
            org.spongycastle.asn1.ASN1ObjectIdentifier r3 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.U0
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x023b
            r3 = r19
            org.spongycastle.asn1.DERBMPString r3 = (org.spongycastle.asn1.DERBMPString) r3
            java.lang.String r3 = r3.a()
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r15 = r8.f
            r15.d(r3, r11)
            r26 = r3
            goto L_0x0247
        L_0x023b:
            org.spongycastle.asn1.ASN1ObjectIdentifier r3 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.V0
            boolean r3 = r13.equals(r3)
            if (r3 == 0) goto L_0x0247
            r27 = r19
            org.spongycastle.asn1.ASN1OctetString r27 = (org.spongycastle.asn1.ASN1OctetString) r27
        L_0x0247:
            r14 = r20
            r13 = r29
            r3 = r30
            r15 = r32
            goto L_0x01c5
        L_0x0251:
            r30 = r3
            r29 = r13
            r20 = r14
            r32 = r15
            r3 = r26
            goto L_0x0266
        L_0x025c:
            r30 = r3
            r29 = r13
            r20 = r14
            r32 = r15
            r3 = r26
        L_0x0266:
            if (r27 == 0) goto L_0x0283
            java.lang.String r13 = new java.lang.String
            byte[] r14 = r27.q()
            byte[] r14 = org.spongycastle.util.encoders.Hex.b(r14)
            r13.<init>(r14)
            if (r3 != 0) goto L_0x027d
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r14 = r8.f
            r14.d(r13, r11)
            goto L_0x0282
        L_0x027d:
            java.util.Hashtable r14 = r8.q
            r14.put(r3, r13)
        L_0x0282:
            goto L_0x028a
        L_0x0283:
            r17 = 1
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r13 = r8.f
            r13.d(r6, r11)
        L_0x028a:
            goto L_0x02c6
        L_0x028b:
            r25 = r12
            r29 = r13
            r20 = r14
            r32 = r15
            org.spongycastle.asn1.ASN1ObjectIdentifier r3 = r0.f()
            org.spongycastle.asn1.ASN1ObjectIdentifier r11 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.l2
            boolean r3 = r3.equals(r11)
            if (r3 == 0) goto L_0x02a3
            r9.addElement(r0)
            goto L_0x02c6
        L_0x02a3:
            java.io.PrintStream r3 = java.lang.System.out
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "extra in data "
            r11.append(r12)
            org.spongycastle.asn1.ASN1ObjectIdentifier r12 = r0.f()
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            r3.println(r11)
            java.io.PrintStream r3 = java.lang.System.out
            java.lang.String r11 = org.spongycastle.asn1.util.ASN1Dump.c(r0)
            r3.println(r11)
        L_0x02c6:
            int r4 = r4 + 1
            r14 = r20
            r0 = r21
            r3 = r22
            r11 = r24
            r12 = r25
            r13 = r29
            r15 = r32
            goto L_0x017a
        L_0x02d8:
            r22 = r3
            r24 = r11
            r25 = r12
            r29 = r13
            r20 = r14
            r32 = r15
            r12 = r1
            r13 = r2
            r2 = r5
            r34 = r6
            r24 = r7
            goto L_0x05a5
        L_0x02ed:
            r21 = r0
            r25 = r12
            r29 = r13
            r20 = r14
            r32 = r15
            r0 = r2[r1]
            org.spongycastle.asn1.ASN1ObjectIdentifier r0 = r0.f()
            org.spongycastle.asn1.ASN1ObjectIdentifier r3 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.F0
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0560
            r0 = r2[r1]
            org.spongycastle.asn1.ASN1Encodable r0 = r0.e()
            org.spongycastle.asn1.pkcs.EncryptedData r0 = org.spongycastle.asn1.pkcs.EncryptedData.g(r0)
            r3 = 0
            org.spongycastle.asn1.x509.AlgorithmIdentifier r4 = r0.f()
            org.spongycastle.asn1.ASN1OctetString r11 = r0.e()
            byte[] r11 = r11.q()
            r12 = r1
            r1 = r36
            r13 = r2
            r2 = r3
            r14 = 1
            r3 = r4
            r15 = 0
            r4 = r38
            r33 = r5
            r5 = r7
            r34 = r6
            r6 = r11
            byte[] r1 = r1.e(r2, r3, r4, r5, r6)
            org.spongycastle.asn1.ASN1Primitive r2 = org.spongycastle.asn1.ASN1Primitive.h(r1)
            org.spongycastle.asn1.ASN1Sequence r2 = (org.spongycastle.asn1.ASN1Sequence) r2
            r3 = 0
        L_0x0337:
            int r4 = r2.size()
            if (r3 == r4) goto L_0x0555
            org.spongycastle.asn1.ASN1Encodable r4 = r2.r(r3)
            org.spongycastle.asn1.pkcs.SafeBag r4 = org.spongycastle.asn1.pkcs.SafeBag.h(r4)
            org.spongycastle.asn1.ASN1ObjectIdentifier r5 = r4.f()
            org.spongycastle.asn1.ASN1ObjectIdentifier r6 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.l2
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0360
            r9.addElement(r4)
            r19 = r0
            r27 = r1
            r30 = r2
            r24 = r7
            r2 = r33
            goto L_0x0543
        L_0x0360:
            org.spongycastle.asn1.ASN1ObjectIdentifier r5 = r4.f()
            org.spongycastle.asn1.ASN1ObjectIdentifier r6 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.k2
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0454
            org.spongycastle.asn1.ASN1Encodable r5 = r4.g()
            org.spongycastle.asn1.pkcs.EncryptedPrivateKeyInfo r5 = org.spongycastle.asn1.pkcs.EncryptedPrivateKeyInfo.g(r5)
            org.spongycastle.asn1.x509.AlgorithmIdentifier r6 = r5.f()
            byte[] r11 = r5.e()
            java.security.PrivateKey r6 = r8.i(r6, r11, r10, r7)
            r11 = r6
            org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier r11 = (org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier) r11
            r19 = 0
            r22 = 0
            org.spongycastle.asn1.ASN1Set r24 = r4.e()
            java.util.Enumeration r24 = r24.t()
            r35 = r19
        L_0x0391:
            boolean r19 = r24.hasMoreElements()
            if (r19 == 0) goto L_0x042c
            java.lang.Object r19 = r24.nextElement()
            r14 = r19
            org.spongycastle.asn1.ASN1Sequence r14 = (org.spongycastle.asn1.ASN1Sequence) r14
            org.spongycastle.asn1.ASN1Encodable r19 = r14.r(r15)
            r15 = r19
            org.spongycastle.asn1.ASN1ObjectIdentifier r15 = (org.spongycastle.asn1.ASN1ObjectIdentifier) r15
            r19 = r0
            r0 = 1
            org.spongycastle.asn1.ASN1Encodable r26 = r14.r(r0)
            r0 = r26
            org.spongycastle.asn1.ASN1Set r0 = (org.spongycastle.asn1.ASN1Set) r0
            r26 = 0
            int r27 = r0.size()
            if (r27 <= 0) goto L_0x03f2
            r27 = r1
            r1 = 0
            org.spongycastle.asn1.ASN1Encodable r28 = r0.s(r1)
            r1 = r28
            org.spongycastle.asn1.ASN1Primitive r1 = (org.spongycastle.asn1.ASN1Primitive) r1
            org.spongycastle.asn1.ASN1Encodable r26 = r11.getBagAttribute(r15)
            if (r26 == 0) goto L_0x03e6
            r28 = r0
            org.spongycastle.asn1.ASN1Primitive r0 = r26.toASN1Primitive()
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x03dc
            r30 = r2
            r2 = r33
            goto L_0x03ef
        L_0x03dc:
            java.io.IOException r0 = new java.io.IOException
            r30 = r2
            r2 = r33
            r0.<init>(r2)
            throw r0
        L_0x03e6:
            r28 = r0
            r30 = r2
            r2 = r33
            r11.setBagAttribute(r15, r1)
        L_0x03ef:
            r26 = r1
            goto L_0x03fa
        L_0x03f2:
            r28 = r0
            r27 = r1
            r30 = r2
            r2 = r33
        L_0x03fa:
            org.spongycastle.asn1.ASN1ObjectIdentifier r0 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.U0
            boolean r0 = r15.equals(r0)
            if (r0 == 0) goto L_0x0412
            r0 = r26
            org.spongycastle.asn1.DERBMPString r0 = (org.spongycastle.asn1.DERBMPString) r0
            java.lang.String r0 = r0.a()
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r1 = r8.f
            r1.d(r0, r6)
            r35 = r0
            goto L_0x0420
        L_0x0412:
            org.spongycastle.asn1.ASN1ObjectIdentifier r0 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.V0
            boolean r0 = r15.equals(r0)
            if (r0 == 0) goto L_0x0420
            r0 = r26
            org.spongycastle.asn1.ASN1OctetString r0 = (org.spongycastle.asn1.ASN1OctetString) r0
            r22 = r0
        L_0x0420:
            r33 = r2
            r0 = r19
            r1 = r27
            r2 = r30
            r14 = 1
            r15 = 0
            goto L_0x0391
        L_0x042c:
            r19 = r0
            r27 = r1
            r30 = r2
            r2 = r33
            java.lang.String r0 = new java.lang.String
            byte[] r1 = r22.q()
            byte[] r1 = org.spongycastle.util.encoders.Hex.b(r1)
            r0.<init>(r1)
            r1 = r35
            if (r1 != 0) goto L_0x044b
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r14 = r8.f
            r14.d(r0, r6)
            goto L_0x0450
        L_0x044b:
            java.util.Hashtable r14 = r8.q
            r14.put(r1, r0)
        L_0x0450:
            r24 = r7
            goto L_0x0543
        L_0x0454:
            r19 = r0
            r27 = r1
            r30 = r2
            r2 = r33
            org.spongycastle.asn1.ASN1ObjectIdentifier r0 = r4.f()
            org.spongycastle.asn1.ASN1ObjectIdentifier r1 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.j2
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x051e
            org.spongycastle.asn1.ASN1Encodable r0 = r4.g()
            org.spongycastle.asn1.pkcs.PrivateKeyInfo r0 = org.spongycastle.asn1.pkcs.PrivateKeyInfo.f(r0)
            java.security.PrivateKey r1 = org.spongycastle.jce.provider.BouncyCastleProvider.getPrivateKey(r0)
            r5 = r1
            org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier r5 = (org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier) r5
            r6 = 0
            r11 = 0
            org.spongycastle.asn1.ASN1Set r14 = r4.e()
            java.util.Enumeration r14 = r14.t()
        L_0x0481:
            boolean r15 = r14.hasMoreElements()
            if (r15 == 0) goto L_0x04ff
            java.lang.Object r15 = r14.nextElement()
            org.spongycastle.asn1.ASN1Sequence r15 = org.spongycastle.asn1.ASN1Sequence.o(r15)
            r22 = r0
            r0 = 0
            org.spongycastle.asn1.ASN1Encodable r24 = r15.r(r0)
            org.spongycastle.asn1.ASN1ObjectIdentifier r0 = org.spongycastle.asn1.ASN1ObjectIdentifier.t(r24)
            r24 = r7
            r7 = 1
            org.spongycastle.asn1.ASN1Encodable r26 = r15.r(r7)
            org.spongycastle.asn1.ASN1Set r7 = org.spongycastle.asn1.ASN1Set.p(r26)
            r26 = 0
            int r28 = r7.size()
            if (r28 <= 0) goto L_0x04f6
            r10 = 0
            org.spongycastle.asn1.ASN1Encodable r28 = r7.s(r10)
            r10 = r28
            org.spongycastle.asn1.ASN1Primitive r10 = (org.spongycastle.asn1.ASN1Primitive) r10
            org.spongycastle.asn1.ASN1Encodable r26 = r5.getBagAttribute(r0)
            if (r26 == 0) goto L_0x04cf
            r28 = r7
            org.spongycastle.asn1.ASN1Primitive r7 = r26.toASN1Primitive()
            boolean r7 = r7.equals(r10)
            if (r7 == 0) goto L_0x04c9
            goto L_0x04d4
        L_0x04c9:
            java.io.IOException r7 = new java.io.IOException
            r7.<init>(r2)
            throw r7
        L_0x04cf:
            r28 = r7
            r5.setBagAttribute(r0, r10)
        L_0x04d4:
            org.spongycastle.asn1.ASN1ObjectIdentifier r7 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.U0
            boolean r7 = r0.equals(r7)
            if (r7 == 0) goto L_0x04e9
            r7 = r10
            org.spongycastle.asn1.DERBMPString r7 = (org.spongycastle.asn1.DERBMPString) r7
            java.lang.String r6 = r7.a()
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r7 = r8.f
            r7.d(r6, r1)
            goto L_0x04f8
        L_0x04e9:
            org.spongycastle.asn1.ASN1ObjectIdentifier r7 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.V0
            boolean r7 = r0.equals(r7)
            if (r7 == 0) goto L_0x04f8
            r7 = r10
            org.spongycastle.asn1.ASN1OctetString r7 = (org.spongycastle.asn1.ASN1OctetString) r7
            r11 = r7
            goto L_0x04f8
        L_0x04f6:
            r28 = r7
        L_0x04f8:
            r10 = r38
            r0 = r22
            r7 = r24
            goto L_0x0481
        L_0x04ff:
            r22 = r0
            r24 = r7
            java.lang.String r0 = new java.lang.String
            byte[] r7 = r11.q()
            byte[] r7 = org.spongycastle.util.encoders.Hex.b(r7)
            r0.<init>(r7)
            if (r6 != 0) goto L_0x0518
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r7 = r8.f
            r7.d(r0, r1)
            goto L_0x051d
        L_0x0518:
            java.util.Hashtable r7 = r8.q
            r7.put(r6, r0)
        L_0x051d:
            goto L_0x0543
        L_0x051e:
            r24 = r7
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r5 = "extra in encryptedData "
            r1.append(r5)
            org.spongycastle.asn1.ASN1ObjectIdentifier r5 = r4.f()
            r1.append(r5)
            java.lang.String r1 = r1.toString()
            r0.println(r1)
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.String r1 = org.spongycastle.asn1.util.ASN1Dump.c(r4)
            r0.println(r1)
        L_0x0543:
            int r3 = r3 + 1
            r10 = r38
            r33 = r2
            r0 = r19
            r7 = r24
            r1 = r27
            r2 = r30
            r14 = 1
            r15 = 0
            goto L_0x0337
        L_0x0555:
            r19 = r0
            r27 = r1
            r30 = r2
            r24 = r7
            r2 = r33
            goto L_0x05a5
        L_0x0560:
            r12 = r1
            r13 = r2
            r2 = r5
            r34 = r6
            r24 = r7
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "extra "
            r1.append(r3)
            r4 = r13[r12]
            org.spongycastle.asn1.ASN1ObjectIdentifier r4 = r4.f()
            java.lang.String r4 = r4.s()
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            r0.println(r1)
            java.io.PrintStream r0 = java.lang.System.out
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r3)
            r3 = r13[r12]
            org.spongycastle.asn1.ASN1Encodable r3 = r3.e()
            java.lang.String r3 = org.spongycastle.asn1.util.ASN1Dump.c(r3)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            r0.println(r1)
        L_0x05a5:
            int r1 = r12 + 1
            r10 = r38
            r5 = r2
            r2 = r13
            r14 = r20
            r0 = r21
            r7 = r24
            r12 = r25
            r13 = r29
            r15 = r32
            r6 = r34
            r11 = 0
            goto L_0x014e
        L_0x05bc:
            r21 = r0
            r34 = r6
            r24 = r7
            r25 = r12
            r29 = r13
            r20 = r14
            r32 = r15
            r12 = r1
            r13 = r2
            r2 = r5
            r13 = r29
            goto L_0x05db
        L_0x05d0:
            r2 = r5
            r34 = r6
            r24 = r7
            r25 = r12
            r20 = r14
            r32 = r15
        L_0x05db:
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r0 = new org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable
            r1 = 0
            r0.<init>()
            r8.x = r0
            java.util.Hashtable r0 = new java.util.Hashtable
            r0.<init>()
            r8.y = r0
            java.util.Hashtable r0 = new java.util.Hashtable
            r0.<init>()
            r8.z = r0
            r0 = 0
            r1 = r0
        L_0x05f3:
            int r0 = r9.size()
            if (r1 == r0) goto L_0x0749
            java.lang.Object r0 = r9.elementAt(r1)
            r3 = r0
            org.spongycastle.asn1.pkcs.SafeBag r3 = (org.spongycastle.asn1.pkcs.SafeBag) r3
            org.spongycastle.asn1.ASN1Encodable r0 = r3.g()
            org.spongycastle.asn1.pkcs.CertBag r4 = org.spongycastle.asn1.pkcs.CertBag.g(r0)
            org.spongycastle.asn1.ASN1ObjectIdentifier r0 = r4.e()
            org.spongycastle.asn1.ASN1ObjectIdentifier r5 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.Y0
            boolean r0 = r0.equals(r5)
            if (r0 == 0) goto L_0x072c
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x071f }
            org.spongycastle.asn1.ASN1Encodable r5 = r4.f()     // Catch:{ Exception -> 0x071f }
            org.spongycastle.asn1.ASN1OctetString r5 = (org.spongycastle.asn1.ASN1OctetString) r5     // Catch:{ Exception -> 0x071f }
            byte[] r5 = r5.q()     // Catch:{ Exception -> 0x071f }
            r0.<init>(r5)     // Catch:{ Exception -> 0x071f }
            java.security.cert.CertificateFactory r5 = r8.a1     // Catch:{ Exception -> 0x071f }
            java.security.cert.Certificate r5 = r5.generateCertificate(r0)     // Catch:{ Exception -> 0x071f }
            r0 = r5
            r5 = 0
            r6 = 0
            org.spongycastle.asn1.ASN1Set r7 = r3.e()
            if (r7 == 0) goto L_0x06b9
            org.spongycastle.asn1.ASN1Set r7 = r3.e()
            java.util.Enumeration r7 = r7.t()
        L_0x063b:
            boolean r10 = r7.hasMoreElements()
            if (r10 == 0) goto L_0x06b6
            java.lang.Object r10 = r7.nextElement()
            org.spongycastle.asn1.ASN1Sequence r10 = org.spongycastle.asn1.ASN1Sequence.o(r10)
            r11 = 0
            org.spongycastle.asn1.ASN1Encodable r12 = r10.r(r11)
            org.spongycastle.asn1.ASN1ObjectIdentifier r12 = org.spongycastle.asn1.ASN1ObjectIdentifier.t(r12)
            r14 = 1
            org.spongycastle.asn1.ASN1Encodable r15 = r10.r(r14)
            org.spongycastle.asn1.ASN1Set r15 = org.spongycastle.asn1.ASN1Set.p(r15)
            int r18 = r15.size()
            if (r18 <= 0) goto L_0x06b1
            org.spongycastle.asn1.ASN1Encodable r18 = r15.s(r11)
            r11 = r18
            org.spongycastle.asn1.ASN1Primitive r11 = (org.spongycastle.asn1.ASN1Primitive) r11
            r18 = 0
            boolean r14 = r0 instanceof org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier
            if (r14 == 0) goto L_0x0693
            r14 = r0
            org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier r14 = (org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier) r14
            org.spongycastle.asn1.ASN1Encodable r18 = r14.getBagAttribute(r12)
            if (r18 == 0) goto L_0x068b
            r21 = r3
            org.spongycastle.asn1.ASN1Primitive r3 = r18.toASN1Primitive()
            boolean r3 = r3.equals(r11)
            if (r3 == 0) goto L_0x0685
            goto L_0x0690
        L_0x0685:
            java.io.IOException r3 = new java.io.IOException
            r3.<init>(r2)
            throw r3
        L_0x068b:
            r21 = r3
            r14.setBagAttribute(r12, r11)
        L_0x0690:
            r18 = r14
            goto L_0x0695
        L_0x0693:
            r21 = r3
        L_0x0695:
            org.spongycastle.asn1.ASN1ObjectIdentifier r3 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.U0
            boolean r3 = r12.equals(r3)
            if (r3 == 0) goto L_0x06a5
            r3 = r11
            org.spongycastle.asn1.DERBMPString r3 = (org.spongycastle.asn1.DERBMPString) r3
            java.lang.String r6 = r3.a()
            goto L_0x06b3
        L_0x06a5:
            org.spongycastle.asn1.ASN1ObjectIdentifier r3 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.V0
            boolean r3 = r12.equals(r3)
            if (r3 == 0) goto L_0x06b3
            r5 = r11
            org.spongycastle.asn1.ASN1OctetString r5 = (org.spongycastle.asn1.ASN1OctetString) r5
            goto L_0x06b3
        L_0x06b1:
            r21 = r3
        L_0x06b3:
            r3 = r21
            goto L_0x063b
        L_0x06b6:
            r21 = r3
            goto L_0x06bb
        L_0x06b9:
            r21 = r3
        L_0x06bb:
            java.util.Hashtable r3 = r8.y
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId r7 = new org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId
            java.security.PublicKey r10 = r0.getPublicKey()
            r7.<init>((java.security.PublicKey) r10)
            r3.put(r7, r0)
            if (r17 == 0) goto L_0x06fc
            java.util.Hashtable r3 = r8.z
            boolean r3 = r3.isEmpty()
            if (r3 == 0) goto L_0x06f9
            java.lang.String r3 = new java.lang.String
            java.security.PublicKey r7 = r0.getPublicKey()
            org.spongycastle.asn1.x509.SubjectKeyIdentifier r7 = r8.d(r7)
            byte[] r7 = r7.e()
            byte[] r7 = org.spongycastle.util.encoders.Hex.b(r7)
            r3.<init>(r7)
            java.util.Hashtable r7 = r8.z
            r7.put(r3, r0)
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r7 = r8.f
            r10 = r34
            java.lang.Object r11 = r7.e(r10)
            r7.d(r3, r11)
            goto L_0x0719
        L_0x06f9:
            r10 = r34
            goto L_0x0719
        L_0x06fc:
            r10 = r34
            if (r5 == 0) goto L_0x0712
            java.lang.String r3 = new java.lang.String
            byte[] r7 = r5.q()
            byte[] r7 = org.spongycastle.util.encoders.Hex.b(r7)
            r3.<init>(r7)
            java.util.Hashtable r7 = r8.z
            r7.put(r3, r0)
        L_0x0712:
            if (r6 == 0) goto L_0x0719
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r3 = r8.x
            r3.d(r6, r0)
        L_0x0719:
            int r1 = r1 + 1
            r34 = r10
            goto L_0x05f3
        L_0x071f:
            r0 = move-exception
            r21 = r3
            java.lang.RuntimeException r2 = new java.lang.RuntimeException
            java.lang.String r3 = r0.toString()
            r2.<init>(r3)
            throw r2
        L_0x072c:
            r21 = r3
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Unsupported certificate type: "
            r2.append(r3)
            org.spongycastle.asn1.ASN1ObjectIdentifier r3 = r4.e()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            throw r0
        L_0x0749:
            return
        L_0x074a:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "stream does not represent a PKCS12 key store"
            r0.<init>(r1)
            throw r0
        L_0x0752:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "No password supplied for PKCS#12 KeyStore."
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.engineLoad(java.io.InputStream, char[]):void");
    }

    private int j(BigInteger i) {
        int count = i.intValue();
        if (count >= 0) {
            BigInteger maxValue = Properties.b("org.spongycastle.pkcs12.max_it_count");
            if (maxValue == null || maxValue.intValue() >= count) {
                return count;
            }
            throw new IllegalStateException("iteration count " + count + " greater than " + maxValue.intValue());
        }
        throw new IllegalStateException("negative iteration count found");
    }

    public void engineStore(KeyStore.LoadStoreParameter param) {
        PKCS12StoreParameter bcParam;
        char[] password;
        if (param == null) {
            throw new IllegalArgumentException("'param' arg cannot be null");
        } else if ((param instanceof PKCS12StoreParameter) || (param instanceof JDKPKCS12StoreParameter)) {
            if (param instanceof PKCS12StoreParameter) {
                bcParam = (PKCS12StoreParameter) param;
            } else {
                bcParam = new PKCS12StoreParameter(((JDKPKCS12StoreParameter) param).a(), param.getProtectionParameter(), ((JDKPKCS12StoreParameter) param).b());
            }
            KeyStore.ProtectionParameter protParam = param.getProtectionParameter();
            if (protParam == null) {
                password = null;
            } else if (protParam instanceof KeyStore.PasswordProtection) {
                password = ((KeyStore.PasswordProtection) protParam).getPassword();
            } else {
                throw new IllegalArgumentException("No support for protection parameter of type " + protParam.getClass().getName());
            }
            f(bcParam.a(), password, bcParam.b());
        } else {
            throw new IllegalArgumentException("No support for 'param' of type " + param.getClass().getName());
        }
    }

    public void engineStore(OutputStream stream, char[] password) {
        f(stream, password, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0074, code lost:
        if (r16.a().equals(r2) == false) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x01c2, code lost:
        if (r21.a().equals(r0) == false) goto L_0x01cc;
     */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x034f A[Catch:{ CertificateEncodingException -> 0x03cc }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void f(java.io.OutputStream r32, char[] r33, boolean r34) {
        /*
            r31 = this;
            r8 = r31
            r9 = r32
            r10 = r33
            if (r10 == 0) goto L_0x05d9
            org.spongycastle.asn1.ASN1EncodableVector r0 = new org.spongycastle.asn1.ASN1EncodableVector
            r0.<init>()
            r11 = r0
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r0 = r8.f
            java.util.Enumeration r12 = r0.c()
        L_0x0014:
            boolean r0 = r12.hasMoreElements()
            r1 = 1024(0x400, float:1.435E-42)
            r2 = 20
            if (r0 == 0) goto L_0x013b
            byte[] r0 = new byte[r2]
            java.security.SecureRandom r2 = r8.p0
            r2.nextBytes(r0)
            java.lang.Object r2 = r12.nextElement()
            java.lang.String r2 = (java.lang.String) r2
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r3 = r8.f
            java.lang.Object r3 = r3.b(r2)
            java.security.PrivateKey r3 = (java.security.PrivateKey) r3
            org.spongycastle.asn1.pkcs.PKCS12PBEParams r4 = new org.spongycastle.asn1.pkcs.PKCS12PBEParams
            r4.<init>(r0, r1)
            r1 = r4
            org.spongycastle.asn1.ASN1ObjectIdentifier r4 = r8.p1
            java.lang.String r4 = r4.s()
            byte[] r4 = r8.k(r4, r3, r1, r10)
            org.spongycastle.asn1.x509.AlgorithmIdentifier r5 = new org.spongycastle.asn1.x509.AlgorithmIdentifier
            org.spongycastle.asn1.ASN1ObjectIdentifier r6 = r8.p1
            org.spongycastle.asn1.ASN1Primitive r7 = r1.toASN1Primitive()
            r5.<init>(r6, r7)
            org.spongycastle.asn1.pkcs.EncryptedPrivateKeyInfo r6 = new org.spongycastle.asn1.pkcs.EncryptedPrivateKeyInfo
            r6.<init>(r5, r4)
            r7 = 0
            org.spongycastle.asn1.ASN1EncodableVector r13 = new org.spongycastle.asn1.ASN1EncodableVector
            r13.<init>()
            boolean r14 = r3 instanceof org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier
            if (r14 == 0) goto L_0x00d8
            r14 = r3
            org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier r14 = (org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier) r14
            org.spongycastle.asn1.ASN1ObjectIdentifier r15 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.U0
            org.spongycastle.asn1.ASN1Encodable r16 = r14.getBagAttribute(r15)
            org.spongycastle.asn1.DERBMPString r16 = (org.spongycastle.asn1.DERBMPString) r16
            if (r16 == 0) goto L_0x0077
            r17 = r0
            java.lang.String r0 = r16.a()
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x0081
            goto L_0x0079
        L_0x0077:
            r17 = r0
        L_0x0079:
            org.spongycastle.asn1.DERBMPString r0 = new org.spongycastle.asn1.DERBMPString
            r0.<init>((java.lang.String) r2)
            r14.setBagAttribute(r15, r0)
        L_0x0081:
            org.spongycastle.asn1.ASN1ObjectIdentifier r0 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.V0
            org.spongycastle.asn1.ASN1Encodable r15 = r14.getBagAttribute(r0)
            if (r15 != 0) goto L_0x009b
            java.security.cert.Certificate r15 = r8.engineGetCertificate(r2)
            r18 = r1
            java.security.PublicKey r1 = r15.getPublicKey()
            org.spongycastle.asn1.x509.SubjectKeyIdentifier r1 = r8.d(r1)
            r14.setBagAttribute(r0, r1)
            goto L_0x009d
        L_0x009b:
            r18 = r1
        L_0x009d:
            java.util.Enumeration r0 = r14.getBagAttributeKeys()
        L_0x00a1:
            boolean r1 = r0.hasMoreElements()
            if (r1 == 0) goto L_0x00d3
            java.lang.Object r1 = r0.nextElement()
            org.spongycastle.asn1.ASN1ObjectIdentifier r1 = (org.spongycastle.asn1.ASN1ObjectIdentifier) r1
            org.spongycastle.asn1.ASN1EncodableVector r15 = new org.spongycastle.asn1.ASN1EncodableVector
            r15.<init>()
            r15.a(r1)
            r19 = r0
            org.spongycastle.asn1.DERSet r0 = new org.spongycastle.asn1.DERSet
            r20 = r3
            org.spongycastle.asn1.ASN1Encodable r3 = r14.getBagAttribute(r1)
            r0.<init>((org.spongycastle.asn1.ASN1Encodable) r3)
            r15.a(r0)
            r7 = 1
            org.spongycastle.asn1.DERSequence r0 = new org.spongycastle.asn1.DERSequence
            r0.<init>((org.spongycastle.asn1.ASN1EncodableVector) r15)
            r13.a(r0)
            r0 = r19
            r3 = r20
            goto L_0x00a1
        L_0x00d3:
            r19 = r0
            r20 = r3
            goto L_0x00de
        L_0x00d8:
            r17 = r0
            r18 = r1
            r20 = r3
        L_0x00de:
            if (r7 != 0) goto L_0x0126
            org.spongycastle.asn1.ASN1EncodableVector r0 = new org.spongycastle.asn1.ASN1EncodableVector
            r0.<init>()
            java.security.cert.Certificate r1 = r8.engineGetCertificate(r2)
            org.spongycastle.asn1.ASN1ObjectIdentifier r3 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.V0
            r0.a(r3)
            org.spongycastle.asn1.DERSet r3 = new org.spongycastle.asn1.DERSet
            java.security.PublicKey r14 = r1.getPublicKey()
            org.spongycastle.asn1.x509.SubjectKeyIdentifier r14 = r8.d(r14)
            r3.<init>((org.spongycastle.asn1.ASN1Encodable) r14)
            r0.a(r3)
            org.spongycastle.asn1.DERSequence r3 = new org.spongycastle.asn1.DERSequence
            r3.<init>((org.spongycastle.asn1.ASN1EncodableVector) r0)
            r13.a(r3)
            org.spongycastle.asn1.ASN1EncodableVector r3 = new org.spongycastle.asn1.ASN1EncodableVector
            r3.<init>()
            r0 = r3
            org.spongycastle.asn1.ASN1ObjectIdentifier r3 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.U0
            r0.a(r3)
            org.spongycastle.asn1.DERSet r3 = new org.spongycastle.asn1.DERSet
            org.spongycastle.asn1.DERBMPString r14 = new org.spongycastle.asn1.DERBMPString
            r14.<init>((java.lang.String) r2)
            r3.<init>((org.spongycastle.asn1.ASN1Encodable) r14)
            r0.a(r3)
            org.spongycastle.asn1.DERSequence r3 = new org.spongycastle.asn1.DERSequence
            r3.<init>((org.spongycastle.asn1.ASN1EncodableVector) r0)
            r13.a(r3)
        L_0x0126:
            org.spongycastle.asn1.pkcs.SafeBag r0 = new org.spongycastle.asn1.pkcs.SafeBag
            org.spongycastle.asn1.ASN1ObjectIdentifier r1 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.k2
            org.spongycastle.asn1.ASN1Primitive r3 = r6.toASN1Primitive()
            org.spongycastle.asn1.DERSet r14 = new org.spongycastle.asn1.DERSet
            r14.<init>((org.spongycastle.asn1.ASN1EncodableVector) r13)
            r0.<init>(r1, r3, r14)
            r11.a(r0)
            goto L_0x0014
        L_0x013b:
            org.spongycastle.asn1.DERSequence r0 = new org.spongycastle.asn1.DERSequence
            r0.<init>((org.spongycastle.asn1.ASN1EncodableVector) r11)
            java.lang.String r3 = "DER"
            byte[] r13 = r0.getEncoded(r3)
            org.spongycastle.asn1.BEROctetString r0 = new org.spongycastle.asn1.BEROctetString
            r0.<init>((byte[]) r13)
            r14 = r0
            byte[] r15 = new byte[r2]
            java.security.SecureRandom r0 = r8.p0
            r0.nextBytes(r15)
            org.spongycastle.asn1.ASN1EncodableVector r0 = new org.spongycastle.asn1.ASN1EncodableVector
            r0.<init>()
            r7 = r0
            org.spongycastle.asn1.pkcs.PKCS12PBEParams r0 = new org.spongycastle.asn1.pkcs.PKCS12PBEParams
            r0.<init>(r15, r1)
            r16 = r0
            org.spongycastle.asn1.x509.AlgorithmIdentifier r0 = new org.spongycastle.asn1.x509.AlgorithmIdentifier
            org.spongycastle.asn1.ASN1ObjectIdentifier r1 = r8.a2
            org.spongycastle.asn1.ASN1Primitive r2 = r16.toASN1Primitive()
            r0.<init>(r1, r2)
            r6 = r0
            java.util.Hashtable r0 = new java.util.Hashtable
            r0.<init>()
            r5 = r0
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r0 = r8.f
            java.util.Enumeration r1 = r0.c()
        L_0x0178:
            boolean r0 = r1.hasMoreElements()
            java.lang.String r2 = "Error encoding certificate: "
            if (r0 == 0) goto L_0x02da
            java.lang.Object r0 = r1.nextElement()     // Catch:{ CertificateEncodingException -> 0x02b6 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ CertificateEncodingException -> 0x02b6 }
            java.security.cert.Certificate r4 = r8.engineGetCertificate(r0)     // Catch:{ CertificateEncodingException -> 0x02b6 }
            r17 = 0
            r18 = r1
            org.spongycastle.asn1.pkcs.CertBag r1 = new org.spongycastle.asn1.pkcs.CertBag     // Catch:{ CertificateEncodingException -> 0x02ac }
            org.spongycastle.asn1.ASN1ObjectIdentifier r10 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.Y0     // Catch:{ CertificateEncodingException -> 0x02ac }
            r19 = r11
            org.spongycastle.asn1.DEROctetString r11 = new org.spongycastle.asn1.DEROctetString     // Catch:{ CertificateEncodingException -> 0x02a4 }
            r20 = r12
            byte[] r12 = r4.getEncoded()     // Catch:{ CertificateEncodingException -> 0x029e }
            r11.<init>(r12)     // Catch:{ CertificateEncodingException -> 0x029e }
            r1.<init>(r10, r11)     // Catch:{ CertificateEncodingException -> 0x029e }
            org.spongycastle.asn1.ASN1EncodableVector r10 = new org.spongycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x029e }
            r10.<init>()     // Catch:{ CertificateEncodingException -> 0x029e }
            boolean r11 = r4 instanceof org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier     // Catch:{ CertificateEncodingException -> 0x029e }
            if (r11 == 0) goto L_0x022e
            r11 = r4
            org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier r11 = (org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier) r11     // Catch:{ CertificateEncodingException -> 0x029e }
            org.spongycastle.asn1.ASN1ObjectIdentifier r12 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.U0     // Catch:{ CertificateEncodingException -> 0x029e }
            org.spongycastle.asn1.ASN1Encodable r21 = r11.getBagAttribute(r12)     // Catch:{ CertificateEncodingException -> 0x029e }
            org.spongycastle.asn1.DERBMPString r21 = (org.spongycastle.asn1.DERBMPString) r21     // Catch:{ CertificateEncodingException -> 0x029e }
            if (r21 == 0) goto L_0x01ca
            r22 = r13
            java.lang.String r13 = r21.a()     // Catch:{ CertificateEncodingException -> 0x01c5 }
            boolean r13 = r13.equals(r0)     // Catch:{ CertificateEncodingException -> 0x01c5 }
            if (r13 != 0) goto L_0x01d4
            goto L_0x01cc
        L_0x01c5:
            r0 = move-exception
            r24 = r15
            goto L_0x02c1
        L_0x01ca:
            r22 = r13
        L_0x01cc:
            org.spongycastle.asn1.DERBMPString r13 = new org.spongycastle.asn1.DERBMPString     // Catch:{ CertificateEncodingException -> 0x0229 }
            r13.<init>((java.lang.String) r0)     // Catch:{ CertificateEncodingException -> 0x0229 }
            r11.setBagAttribute(r12, r13)     // Catch:{ CertificateEncodingException -> 0x0229 }
        L_0x01d4:
            org.spongycastle.asn1.ASN1ObjectIdentifier r12 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.V0     // Catch:{ CertificateEncodingException -> 0x0229 }
            org.spongycastle.asn1.ASN1Encodable r13 = r11.getBagAttribute(r12)     // Catch:{ CertificateEncodingException -> 0x0229 }
            if (r13 != 0) goto L_0x01e7
            java.security.PublicKey r13 = r4.getPublicKey()     // Catch:{ CertificateEncodingException -> 0x01c5 }
            org.spongycastle.asn1.x509.SubjectKeyIdentifier r13 = r8.d(r13)     // Catch:{ CertificateEncodingException -> 0x01c5 }
            r11.setBagAttribute(r12, r13)     // Catch:{ CertificateEncodingException -> 0x01c5 }
        L_0x01e7:
            java.util.Enumeration r12 = r11.getBagAttributeKeys()     // Catch:{ CertificateEncodingException -> 0x0229 }
        L_0x01eb:
            boolean r13 = r12.hasMoreElements()     // Catch:{ CertificateEncodingException -> 0x0229 }
            if (r13 == 0) goto L_0x0224
            java.lang.Object r13 = r12.nextElement()     // Catch:{ CertificateEncodingException -> 0x0229 }
            org.spongycastle.asn1.ASN1ObjectIdentifier r13 = (org.spongycastle.asn1.ASN1ObjectIdentifier) r13     // Catch:{ CertificateEncodingException -> 0x0229 }
            org.spongycastle.asn1.ASN1EncodableVector r23 = new org.spongycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x0229 }
            r23.<init>()     // Catch:{ CertificateEncodingException -> 0x0229 }
            r24 = r23
            r23 = r12
            r12 = r24
            r12.a(r13)     // Catch:{ CertificateEncodingException -> 0x0229 }
            r24 = r15
            org.spongycastle.asn1.DERSet r15 = new org.spongycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x029c }
            org.spongycastle.asn1.ASN1Encodable r9 = r11.getBagAttribute(r13)     // Catch:{ CertificateEncodingException -> 0x029c }
            r15.<init>((org.spongycastle.asn1.ASN1Encodable) r9)     // Catch:{ CertificateEncodingException -> 0x029c }
            r12.a(r15)     // Catch:{ CertificateEncodingException -> 0x029c }
            org.spongycastle.asn1.DERSequence r9 = new org.spongycastle.asn1.DERSequence     // Catch:{ CertificateEncodingException -> 0x029c }
            r9.<init>((org.spongycastle.asn1.ASN1EncodableVector) r12)     // Catch:{ CertificateEncodingException -> 0x029c }
            r10.a(r9)     // Catch:{ CertificateEncodingException -> 0x029c }
            r17 = 1
            r9 = r32
            r12 = r23
            r15 = r24
            goto L_0x01eb
        L_0x0224:
            r23 = r12
            r24 = r15
            goto L_0x0232
        L_0x0229:
            r0 = move-exception
            r24 = r15
            goto L_0x02c1
        L_0x022e:
            r22 = r13
            r24 = r15
        L_0x0232:
            if (r17 != 0) goto L_0x0276
            org.spongycastle.asn1.ASN1EncodableVector r9 = new org.spongycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x029c }
            r9.<init>()     // Catch:{ CertificateEncodingException -> 0x029c }
            org.spongycastle.asn1.ASN1ObjectIdentifier r11 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.V0     // Catch:{ CertificateEncodingException -> 0x029c }
            r9.a(r11)     // Catch:{ CertificateEncodingException -> 0x029c }
            org.spongycastle.asn1.DERSet r11 = new org.spongycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x029c }
            java.security.PublicKey r12 = r4.getPublicKey()     // Catch:{ CertificateEncodingException -> 0x029c }
            org.spongycastle.asn1.x509.SubjectKeyIdentifier r12 = r8.d(r12)     // Catch:{ CertificateEncodingException -> 0x029c }
            r11.<init>((org.spongycastle.asn1.ASN1Encodable) r12)     // Catch:{ CertificateEncodingException -> 0x029c }
            r9.a(r11)     // Catch:{ CertificateEncodingException -> 0x029c }
            org.spongycastle.asn1.DERSequence r11 = new org.spongycastle.asn1.DERSequence     // Catch:{ CertificateEncodingException -> 0x029c }
            r11.<init>((org.spongycastle.asn1.ASN1EncodableVector) r9)     // Catch:{ CertificateEncodingException -> 0x029c }
            r10.a(r11)     // Catch:{ CertificateEncodingException -> 0x029c }
            org.spongycastle.asn1.ASN1EncodableVector r11 = new org.spongycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x029c }
            r11.<init>()     // Catch:{ CertificateEncodingException -> 0x029c }
            r9 = r11
            org.spongycastle.asn1.ASN1ObjectIdentifier r11 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.U0     // Catch:{ CertificateEncodingException -> 0x029c }
            r9.a(r11)     // Catch:{ CertificateEncodingException -> 0x029c }
            org.spongycastle.asn1.DERSet r11 = new org.spongycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x029c }
            org.spongycastle.asn1.DERBMPString r12 = new org.spongycastle.asn1.DERBMPString     // Catch:{ CertificateEncodingException -> 0x029c }
            r12.<init>((java.lang.String) r0)     // Catch:{ CertificateEncodingException -> 0x029c }
            r11.<init>((org.spongycastle.asn1.ASN1Encodable) r12)     // Catch:{ CertificateEncodingException -> 0x029c }
            r9.a(r11)     // Catch:{ CertificateEncodingException -> 0x029c }
            org.spongycastle.asn1.DERSequence r11 = new org.spongycastle.asn1.DERSequence     // Catch:{ CertificateEncodingException -> 0x029c }
            r11.<init>((org.spongycastle.asn1.ASN1EncodableVector) r9)     // Catch:{ CertificateEncodingException -> 0x029c }
            r10.a(r11)     // Catch:{ CertificateEncodingException -> 0x029c }
        L_0x0276:
            org.spongycastle.asn1.pkcs.SafeBag r9 = new org.spongycastle.asn1.pkcs.SafeBag     // Catch:{ CertificateEncodingException -> 0x029c }
            org.spongycastle.asn1.ASN1ObjectIdentifier r11 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.l2     // Catch:{ CertificateEncodingException -> 0x029c }
            org.spongycastle.asn1.ASN1Primitive r12 = r1.toASN1Primitive()     // Catch:{ CertificateEncodingException -> 0x029c }
            org.spongycastle.asn1.DERSet r13 = new org.spongycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x029c }
            r13.<init>((org.spongycastle.asn1.ASN1EncodableVector) r10)     // Catch:{ CertificateEncodingException -> 0x029c }
            r9.<init>(r11, r12, r13)     // Catch:{ CertificateEncodingException -> 0x029c }
            r7.a(r9)     // Catch:{ CertificateEncodingException -> 0x029c }
            r5.put(r4, r4)     // Catch:{ CertificateEncodingException -> 0x029c }
            r9 = r32
            r10 = r33
            r1 = r18
            r11 = r19
            r12 = r20
            r13 = r22
            r15 = r24
            goto L_0x0178
        L_0x029c:
            r0 = move-exception
            goto L_0x02c1
        L_0x029e:
            r0 = move-exception
            r22 = r13
            r24 = r15
            goto L_0x02c1
        L_0x02a4:
            r0 = move-exception
            r20 = r12
            r22 = r13
            r24 = r15
            goto L_0x02c1
        L_0x02ac:
            r0 = move-exception
            r19 = r11
            r20 = r12
            r22 = r13
            r24 = r15
            goto L_0x02c1
        L_0x02b6:
            r0 = move-exception
            r18 = r1
            r19 = r11
            r20 = r12
            r22 = r13
            r24 = r15
        L_0x02c1:
            java.io.IOException r1 = new java.io.IOException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            java.lang.String r2 = r0.toString()
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            r1.<init>(r2)
            throw r1
        L_0x02da:
            r18 = r1
            r19 = r11
            r20 = r12
            r22 = r13
            r24 = r15
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r0 = r8.x
            java.util.Enumeration r1 = r0.c()
        L_0x02ea:
            boolean r0 = r1.hasMoreElements()
            if (r0 == 0) goto L_0x03ea
            java.lang.Object r0 = r1.nextElement()     // Catch:{ CertificateEncodingException -> 0x03ce }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ CertificateEncodingException -> 0x03ce }
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r4 = r8.x     // Catch:{ CertificateEncodingException -> 0x03ce }
            java.lang.Object r4 = r4.b(r0)     // Catch:{ CertificateEncodingException -> 0x03ce }
            java.security.cert.Certificate r4 = (java.security.cert.Certificate) r4     // Catch:{ CertificateEncodingException -> 0x03ce }
            r9 = 0
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$IgnoresCaseHashtable r10 = r8.f     // Catch:{ CertificateEncodingException -> 0x03ce }
            java.lang.Object r10 = r10.b(r0)     // Catch:{ CertificateEncodingException -> 0x03ce }
            if (r10 == 0) goto L_0x0308
            goto L_0x02ea
        L_0x0308:
            org.spongycastle.asn1.pkcs.CertBag r10 = new org.spongycastle.asn1.pkcs.CertBag     // Catch:{ CertificateEncodingException -> 0x03ce }
            org.spongycastle.asn1.ASN1ObjectIdentifier r11 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.Y0     // Catch:{ CertificateEncodingException -> 0x03ce }
            org.spongycastle.asn1.DEROctetString r12 = new org.spongycastle.asn1.DEROctetString     // Catch:{ CertificateEncodingException -> 0x03ce }
            byte[] r13 = r4.getEncoded()     // Catch:{ CertificateEncodingException -> 0x03ce }
            r12.<init>(r13)     // Catch:{ CertificateEncodingException -> 0x03ce }
            r10.<init>(r11, r12)     // Catch:{ CertificateEncodingException -> 0x03ce }
            org.spongycastle.asn1.ASN1EncodableVector r11 = new org.spongycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x03ce }
            r11.<init>()     // Catch:{ CertificateEncodingException -> 0x03ce }
            boolean r12 = r4 instanceof org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier     // Catch:{ CertificateEncodingException -> 0x03ce }
            if (r12 == 0) goto L_0x038f
            r12 = r4
            org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier r12 = (org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier) r12     // Catch:{ CertificateEncodingException -> 0x03ce }
            org.spongycastle.asn1.ASN1ObjectIdentifier r13 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.U0     // Catch:{ CertificateEncodingException -> 0x03ce }
            org.spongycastle.asn1.ASN1Encodable r15 = r12.getBagAttribute(r13)     // Catch:{ CertificateEncodingException -> 0x03ce }
            org.spongycastle.asn1.DERBMPString r15 = (org.spongycastle.asn1.DERBMPString) r15     // Catch:{ CertificateEncodingException -> 0x03ce }
            if (r15 == 0) goto L_0x033b
            r17 = r1
            java.lang.String r1 = r15.a()     // Catch:{ CertificateEncodingException -> 0x03cc }
            boolean r1 = r1.equals(r0)     // Catch:{ CertificateEncodingException -> 0x03cc }
            if (r1 != 0) goto L_0x0345
            goto L_0x033d
        L_0x033b:
            r17 = r1
        L_0x033d:
            org.spongycastle.asn1.DERBMPString r1 = new org.spongycastle.asn1.DERBMPString     // Catch:{ CertificateEncodingException -> 0x03cc }
            r1.<init>((java.lang.String) r0)     // Catch:{ CertificateEncodingException -> 0x03cc }
            r12.setBagAttribute(r13, r1)     // Catch:{ CertificateEncodingException -> 0x03cc }
        L_0x0345:
            java.util.Enumeration r1 = r12.getBagAttributeKeys()     // Catch:{ CertificateEncodingException -> 0x03cc }
        L_0x0349:
            boolean r13 = r1.hasMoreElements()     // Catch:{ CertificateEncodingException -> 0x03cc }
            if (r13 == 0) goto L_0x0388
            java.lang.Object r13 = r1.nextElement()     // Catch:{ CertificateEncodingException -> 0x03cc }
            org.spongycastle.asn1.ASN1ObjectIdentifier r13 = (org.spongycastle.asn1.ASN1ObjectIdentifier) r13     // Catch:{ CertificateEncodingException -> 0x03cc }
            r18 = r1
            org.spongycastle.asn1.ASN1ObjectIdentifier r1 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.V0     // Catch:{ CertificateEncodingException -> 0x03cc }
            boolean r1 = r13.equals(r1)     // Catch:{ CertificateEncodingException -> 0x03cc }
            if (r1 == 0) goto L_0x0362
            r1 = r18
            goto L_0x0349
        L_0x0362:
            org.spongycastle.asn1.ASN1EncodableVector r1 = new org.spongycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x03cc }
            r1.<init>()     // Catch:{ CertificateEncodingException -> 0x03cc }
            r1.a(r13)     // Catch:{ CertificateEncodingException -> 0x03cc }
            r21 = r9
            org.spongycastle.asn1.DERSet r9 = new org.spongycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x03cc }
            r23 = r15
            org.spongycastle.asn1.ASN1Encodable r15 = r12.getBagAttribute(r13)     // Catch:{ CertificateEncodingException -> 0x03cc }
            r9.<init>((org.spongycastle.asn1.ASN1Encodable) r15)     // Catch:{ CertificateEncodingException -> 0x03cc }
            r1.a(r9)     // Catch:{ CertificateEncodingException -> 0x03cc }
            org.spongycastle.asn1.DERSequence r9 = new org.spongycastle.asn1.DERSequence     // Catch:{ CertificateEncodingException -> 0x03cc }
            r9.<init>((org.spongycastle.asn1.ASN1EncodableVector) r1)     // Catch:{ CertificateEncodingException -> 0x03cc }
            r11.a(r9)     // Catch:{ CertificateEncodingException -> 0x03cc }
            r9 = 1
            r1 = r18
            r15 = r23
            goto L_0x0349
        L_0x0388:
            r18 = r1
            r21 = r9
            r23 = r15
            goto L_0x0391
        L_0x038f:
            r17 = r1
        L_0x0391:
            if (r9 != 0) goto L_0x03b2
            org.spongycastle.asn1.ASN1EncodableVector r1 = new org.spongycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x03cc }
            r1.<init>()     // Catch:{ CertificateEncodingException -> 0x03cc }
            org.spongycastle.asn1.ASN1ObjectIdentifier r12 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.U0     // Catch:{ CertificateEncodingException -> 0x03cc }
            r1.a(r12)     // Catch:{ CertificateEncodingException -> 0x03cc }
            org.spongycastle.asn1.DERSet r12 = new org.spongycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x03cc }
            org.spongycastle.asn1.DERBMPString r13 = new org.spongycastle.asn1.DERBMPString     // Catch:{ CertificateEncodingException -> 0x03cc }
            r13.<init>((java.lang.String) r0)     // Catch:{ CertificateEncodingException -> 0x03cc }
            r12.<init>((org.spongycastle.asn1.ASN1Encodable) r13)     // Catch:{ CertificateEncodingException -> 0x03cc }
            r1.a(r12)     // Catch:{ CertificateEncodingException -> 0x03cc }
            org.spongycastle.asn1.DERSequence r12 = new org.spongycastle.asn1.DERSequence     // Catch:{ CertificateEncodingException -> 0x03cc }
            r12.<init>((org.spongycastle.asn1.ASN1EncodableVector) r1)     // Catch:{ CertificateEncodingException -> 0x03cc }
            r11.a(r12)     // Catch:{ CertificateEncodingException -> 0x03cc }
        L_0x03b2:
            org.spongycastle.asn1.pkcs.SafeBag r1 = new org.spongycastle.asn1.pkcs.SafeBag     // Catch:{ CertificateEncodingException -> 0x03cc }
            org.spongycastle.asn1.ASN1ObjectIdentifier r12 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.l2     // Catch:{ CertificateEncodingException -> 0x03cc }
            org.spongycastle.asn1.ASN1Primitive r13 = r10.toASN1Primitive()     // Catch:{ CertificateEncodingException -> 0x03cc }
            org.spongycastle.asn1.DERSet r15 = new org.spongycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x03cc }
            r15.<init>((org.spongycastle.asn1.ASN1EncodableVector) r11)     // Catch:{ CertificateEncodingException -> 0x03cc }
            r1.<init>(r12, r13, r15)     // Catch:{ CertificateEncodingException -> 0x03cc }
            r7.a(r1)     // Catch:{ CertificateEncodingException -> 0x03cc }
            r5.put(r4, r4)     // Catch:{ CertificateEncodingException -> 0x03cc }
            r1 = r17
            goto L_0x02ea
        L_0x03cc:
            r0 = move-exception
            goto L_0x03d1
        L_0x03ce:
            r0 = move-exception
            r17 = r1
        L_0x03d1:
            java.io.IOException r1 = new java.io.IOException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            java.lang.String r2 = r0.toString()
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            r1.<init>(r2)
            throw r1
        L_0x03ea:
            r17 = r1
            java.util.Set r9 = r31.h()
            java.util.Hashtable r0 = r8.y
            java.util.Enumeration r10 = r0.keys()
        L_0x03f6:
            boolean r0 = r10.hasMoreElements()
            if (r0 == 0) goto L_0x04ba
            java.lang.Object r0 = r10.nextElement()     // Catch:{ CertificateEncodingException -> 0x049e }
            org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$CertId r0 = (org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.CertId) r0     // Catch:{ CertificateEncodingException -> 0x049e }
            java.util.Hashtable r1 = r8.y     // Catch:{ CertificateEncodingException -> 0x049e }
            java.lang.Object r1 = r1.get(r0)     // Catch:{ CertificateEncodingException -> 0x049e }
            java.security.cert.Certificate r1 = (java.security.cert.Certificate) r1     // Catch:{ CertificateEncodingException -> 0x049e }
            boolean r4 = r9.contains(r1)     // Catch:{ CertificateEncodingException -> 0x049e }
            if (r4 != 0) goto L_0x0411
            goto L_0x03f6
        L_0x0411:
            java.lang.Object r4 = r5.get(r1)     // Catch:{ CertificateEncodingException -> 0x049e }
            if (r4 == 0) goto L_0x0418
            goto L_0x03f6
        L_0x0418:
            org.spongycastle.asn1.pkcs.CertBag r4 = new org.spongycastle.asn1.pkcs.CertBag     // Catch:{ CertificateEncodingException -> 0x049e }
            org.spongycastle.asn1.ASN1ObjectIdentifier r11 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.Y0     // Catch:{ CertificateEncodingException -> 0x049e }
            org.spongycastle.asn1.DEROctetString r12 = new org.spongycastle.asn1.DEROctetString     // Catch:{ CertificateEncodingException -> 0x049e }
            byte[] r13 = r1.getEncoded()     // Catch:{ CertificateEncodingException -> 0x049e }
            r12.<init>(r13)     // Catch:{ CertificateEncodingException -> 0x049e }
            r4.<init>(r11, r12)     // Catch:{ CertificateEncodingException -> 0x049e }
            org.spongycastle.asn1.ASN1EncodableVector r11 = new org.spongycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x049e }
            r11.<init>()     // Catch:{ CertificateEncodingException -> 0x049e }
            boolean r12 = r1 instanceof org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier     // Catch:{ CertificateEncodingException -> 0x049e }
            if (r12 == 0) goto L_0x047f
            r12 = r1
            org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier r12 = (org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier) r12     // Catch:{ CertificateEncodingException -> 0x049e }
            java.util.Enumeration r13 = r12.getBagAttributeKeys()     // Catch:{ CertificateEncodingException -> 0x049e }
        L_0x0438:
            boolean r15 = r13.hasMoreElements()     // Catch:{ CertificateEncodingException -> 0x049e }
            if (r15 == 0) goto L_0x0478
            java.lang.Object r15 = r13.nextElement()     // Catch:{ CertificateEncodingException -> 0x049e }
            org.spongycastle.asn1.ASN1ObjectIdentifier r15 = (org.spongycastle.asn1.ASN1ObjectIdentifier) r15     // Catch:{ CertificateEncodingException -> 0x049e }
            r17 = r0
            org.spongycastle.asn1.ASN1ObjectIdentifier r0 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.V0     // Catch:{ CertificateEncodingException -> 0x049e }
            boolean r0 = r15.equals(r0)     // Catch:{ CertificateEncodingException -> 0x049e }
            if (r0 == 0) goto L_0x0451
            r0 = r17
            goto L_0x0438
        L_0x0451:
            org.spongycastle.asn1.ASN1EncodableVector r0 = new org.spongycastle.asn1.ASN1EncodableVector     // Catch:{ CertificateEncodingException -> 0x049e }
            r0.<init>()     // Catch:{ CertificateEncodingException -> 0x049e }
            r0.a(r15)     // Catch:{ CertificateEncodingException -> 0x049e }
            r18 = r1
            org.spongycastle.asn1.DERSet r1 = new org.spongycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x049e }
            r21 = r5
            org.spongycastle.asn1.ASN1Encodable r5 = r12.getBagAttribute(r15)     // Catch:{ CertificateEncodingException -> 0x049c }
            r1.<init>((org.spongycastle.asn1.ASN1Encodable) r5)     // Catch:{ CertificateEncodingException -> 0x049c }
            r0.a(r1)     // Catch:{ CertificateEncodingException -> 0x049c }
            org.spongycastle.asn1.DERSequence r1 = new org.spongycastle.asn1.DERSequence     // Catch:{ CertificateEncodingException -> 0x049c }
            r1.<init>((org.spongycastle.asn1.ASN1EncodableVector) r0)     // Catch:{ CertificateEncodingException -> 0x049c }
            r11.a(r1)     // Catch:{ CertificateEncodingException -> 0x049c }
            r0 = r17
            r1 = r18
            r5 = r21
            goto L_0x0438
        L_0x0478:
            r17 = r0
            r18 = r1
            r21 = r5
            goto L_0x0485
        L_0x047f:
            r17 = r0
            r18 = r1
            r21 = r5
        L_0x0485:
            org.spongycastle.asn1.pkcs.SafeBag r0 = new org.spongycastle.asn1.pkcs.SafeBag     // Catch:{ CertificateEncodingException -> 0x049c }
            org.spongycastle.asn1.ASN1ObjectIdentifier r1 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.l2     // Catch:{ CertificateEncodingException -> 0x049c }
            org.spongycastle.asn1.ASN1Primitive r5 = r4.toASN1Primitive()     // Catch:{ CertificateEncodingException -> 0x049c }
            org.spongycastle.asn1.DERSet r12 = new org.spongycastle.asn1.DERSet     // Catch:{ CertificateEncodingException -> 0x049c }
            r12.<init>((org.spongycastle.asn1.ASN1EncodableVector) r11)     // Catch:{ CertificateEncodingException -> 0x049c }
            r0.<init>(r1, r5, r12)     // Catch:{ CertificateEncodingException -> 0x049c }
            r7.a(r0)     // Catch:{ CertificateEncodingException -> 0x049c }
            r5 = r21
            goto L_0x03f6
        L_0x049c:
            r0 = move-exception
            goto L_0x04a1
        L_0x049e:
            r0 = move-exception
            r21 = r5
        L_0x04a1:
            java.io.IOException r1 = new java.io.IOException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            java.lang.String r2 = r0.toString()
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            r1.<init>(r2)
            throw r1
        L_0x04ba:
            r21 = r5
            org.spongycastle.asn1.DERSequence r0 = new org.spongycastle.asn1.DERSequence
            r0.<init>((org.spongycastle.asn1.ASN1EncodableVector) r7)
            byte[] r11 = r0.getEncoded(r3)
            r2 = 1
            r5 = 0
            r1 = r31
            r3 = r6
            r4 = r33
            r12 = r21
            r13 = r6
            r6 = r11
            byte[] r15 = r1.e(r2, r3, r4, r5, r6)
            org.spongycastle.asn1.pkcs.EncryptedData r0 = new org.spongycastle.asn1.pkcs.EncryptedData
            org.spongycastle.asn1.ASN1ObjectIdentifier r1 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.A0
            org.spongycastle.asn1.BEROctetString r2 = new org.spongycastle.asn1.BEROctetString
            r2.<init>((byte[]) r15)
            r0.<init>(r1, r13, r2)
            r17 = r0
            r0 = 2
            org.spongycastle.asn1.pkcs.ContentInfo[] r0 = new org.spongycastle.asn1.pkcs.ContentInfo[r0]
            r2 = 0
            org.spongycastle.asn1.pkcs.ContentInfo r3 = new org.spongycastle.asn1.pkcs.ContentInfo
            r3.<init>(r1, r14)
            r0[r2] = r3
            r2 = 1
            org.spongycastle.asn1.pkcs.ContentInfo r3 = new org.spongycastle.asn1.pkcs.ContentInfo
            org.spongycastle.asn1.ASN1ObjectIdentifier r4 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.F0
            org.spongycastle.asn1.ASN1Primitive r5 = r17.toASN1Primitive()
            r3.<init>(r4, r5)
            r0[r2] = r3
            r6 = r0
            org.spongycastle.asn1.pkcs.AuthenticatedSafe r0 = new org.spongycastle.asn1.pkcs.AuthenticatedSafe
            r0.<init>((org.spongycastle.asn1.pkcs.ContentInfo[]) r6)
            r5 = r0
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            r4 = r0
            if (r34 == 0) goto L_0x0511
            org.spongycastle.asn1.DEROutputStream r0 = new org.spongycastle.asn1.DEROutputStream
            r0.<init>(r4)
            r3 = r0
            goto L_0x0517
        L_0x0511:
            org.spongycastle.asn1.BEROutputStream r0 = new org.spongycastle.asn1.BEROutputStream
            r0.<init>(r4)
            r3 = r0
        L_0x0517:
            r3.j(r5)
            byte[] r2 = r4.toByteArray()
            org.spongycastle.asn1.pkcs.ContentInfo r0 = new org.spongycastle.asn1.pkcs.ContentInfo
            r18 = r3
            org.spongycastle.asn1.BEROctetString r3 = new org.spongycastle.asn1.BEROctetString
            r3.<init>((byte[]) r2)
            r0.<init>(r1, r3)
            r3 = r0
            int r0 = r8.p4
            byte[] r1 = new byte[r0]
            java.security.SecureRandom r0 = r8.p0
            r0.nextBytes(r1)
            org.spongycastle.asn1.ASN1Encodable r0 = r3.e()
            org.spongycastle.asn1.ASN1OctetString r0 = (org.spongycastle.asn1.ASN1OctetString) r0
            byte[] r21 = r0.q()
            org.spongycastle.asn1.x509.AlgorithmIdentifier r0 = r8.p2     // Catch:{ Exception -> 0x05af }
            org.spongycastle.asn1.ASN1ObjectIdentifier r0 = r0.e()     // Catch:{ Exception -> 0x05af }
            r23 = r4
            int r4 = r8.p3     // Catch:{ Exception -> 0x05a1 }
            r25 = 0
            r26 = r1
            r1 = r31
            r27 = r2
            r2 = r0
            r28 = r3
            r3 = r26
            r29 = r5
            r5 = r33
            r30 = r6
            r6 = r25
            r25 = r7
            r7 = r21
            byte[] r0 = r1.b(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0599 }
            org.spongycastle.asn1.x509.DigestInfo r1 = new org.spongycastle.asn1.x509.DigestInfo     // Catch:{ Exception -> 0x0599 }
            org.spongycastle.asn1.x509.AlgorithmIdentifier r2 = r8.p2     // Catch:{ Exception -> 0x0599 }
            r1.<init>(r2, r0)     // Catch:{ Exception -> 0x0599 }
            org.spongycastle.asn1.pkcs.MacData r2 = new org.spongycastle.asn1.pkcs.MacData     // Catch:{ Exception -> 0x0599 }
            int r3 = r8.p3     // Catch:{ Exception -> 0x0599 }
            r4 = r26
            r2.<init>(r1, r4, r3)     // Catch:{ Exception -> 0x0593 }
            r0 = r2
            org.spongycastle.asn1.pkcs.Pfx r1 = new org.spongycastle.asn1.pkcs.Pfx
            r2 = r28
            r1.<init>(r2, r0)
            if (r34 == 0) goto L_0x0588
            org.spongycastle.asn1.DEROutputStream r3 = new org.spongycastle.asn1.DEROutputStream
            r5 = r32
            r3.<init>(r5)
            goto L_0x058f
        L_0x0588:
            r5 = r32
            org.spongycastle.asn1.BEROutputStream r3 = new org.spongycastle.asn1.BEROutputStream
            r3.<init>(r5)
        L_0x058f:
            r3.j(r1)
            return
        L_0x0593:
            r0 = move-exception
            r5 = r32
            r2 = r28
            goto L_0x05be
        L_0x0599:
            r0 = move-exception
            r5 = r32
            r4 = r26
            r2 = r28
            goto L_0x05be
        L_0x05a1:
            r0 = move-exception
            r4 = r1
            r27 = r2
            r2 = r3
            r29 = r5
            r30 = r6
            r25 = r7
            r5 = r32
            goto L_0x05be
        L_0x05af:
            r0 = move-exception
            r27 = r2
            r2 = r3
            r23 = r4
            r29 = r5
            r30 = r6
            r25 = r7
            r5 = r32
            r4 = r1
        L_0x05be:
            java.io.IOException r1 = new java.io.IOException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r6 = "error constructing MAC: "
            r3.append(r6)
            java.lang.String r6 = r0.toString()
            r3.append(r6)
            java.lang.String r3 = r3.toString()
            r1.<init>(r3)
            throw r1
        L_0x05d9:
            r5 = r9
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "No password supplied for PKCS#12 KeyStore."
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.f(java.io.OutputStream, char[], boolean):void");
    }

    private Set h() {
        Set usedSet = new HashSet();
        Enumeration en = this.f.c();
        while (en.hasMoreElements()) {
            Certificate[] certs = engineGetCertificateChain((String) en.nextElement());
            for (int i = 0; i != certs.length; i++) {
                usedSet.add(certs[i]);
            }
        }
        Enumeration en2 = this.x.c();
        while (en2.hasMoreElements()) {
            usedSet.add(engineGetCertificate((String) en2.nextElement()));
        }
        return usedSet;
    }

    private byte[] b(ASN1ObjectIdentifier oid, byte[] salt, int itCount, char[] password, boolean wrongPkcs12Zero, byte[] data) {
        PBEParameterSpec defParams = new PBEParameterSpec(salt, itCount);
        Mac mac = this.d.f(oid.s());
        mac.init(new PKCS12Key(password, wrongPkcs12Zero), defParams);
        mac.update(data);
        return mac.doFinal();
    }

    public static class BCPKCS12KeyStore extends PKCS12KeyStoreSpi {
        public BCPKCS12KeyStore() {
            super(new BouncyCastleProvider(), PKCSObjectIdentifiers.t2, PKCSObjectIdentifiers.w2);
        }
    }

    public static class BCPKCS12KeyStore3DES extends PKCS12KeyStoreSpi {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public BCPKCS12KeyStore3DES() {
            /*
                r2 = this;
                org.spongycastle.jce.provider.BouncyCastleProvider r0 = new org.spongycastle.jce.provider.BouncyCastleProvider
                r0.<init>()
                org.spongycastle.asn1.ASN1ObjectIdentifier r1 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.t2
                r2.<init>(r0, r1, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.BCPKCS12KeyStore3DES.<init>():void");
        }
    }

    public static class DefPKCS12KeyStore extends PKCS12KeyStoreSpi {
        public DefPKCS12KeyStore() {
            super((Provider) null, PKCSObjectIdentifiers.t2, PKCSObjectIdentifiers.w2);
        }
    }

    public static class DefPKCS12KeyStore3DES extends PKCS12KeyStoreSpi {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public DefPKCS12KeyStore3DES() {
            /*
                r2 = this;
                org.spongycastle.asn1.ASN1ObjectIdentifier r0 = org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers.t2
                r1 = 0
                r2.<init>(r1, r0, r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi.DefPKCS12KeyStore3DES.<init>():void");
        }
    }

    public static class IgnoresCaseHashtable {
        private Hashtable a;
        private Hashtable b;

        private IgnoresCaseHashtable() {
            this.a = new Hashtable();
            this.b = new Hashtable();
        }

        public void d(String key, Object value) {
            String lower = key == null ? null : Strings.h(key);
            String k = (String) this.b.get(lower);
            if (k != null) {
                this.a.remove(k);
            }
            this.b.put(lower, key);
            this.a.put(key, value);
        }

        public Enumeration c() {
            return this.a.keys();
        }

        public Object e(String alias) {
            String k = (String) this.b.remove(alias == null ? null : Strings.h(alias));
            if (k == null) {
                return null;
            }
            return this.a.remove(k);
        }

        public Object b(String alias) {
            String k = (String) this.b.get(alias == null ? null : Strings.h(alias));
            if (k == null) {
                return null;
            }
            return this.a.get(k);
        }

        public Enumeration a() {
            return this.a.elements();
        }
    }

    public static class DefaultSecretKeyProvider {
        private final Map a;

        DefaultSecretKeyProvider() {
            Map keySizes = new HashMap();
            keySizes.put(new ASN1ObjectIdentifier("1.2.840.113533.7.66.10"), Integers.b(128));
            keySizes.put(PKCSObjectIdentifiers.m0, Integers.b(Opcodes.CHECKCAST));
            keySizes.put(NISTObjectIdentifiers.u, Integers.b(128));
            keySizes.put(NISTObjectIdentifiers.C, Integers.b(Opcodes.CHECKCAST));
            keySizes.put(NISTObjectIdentifiers.K, Integers.b(256));
            keySizes.put(NTTObjectIdentifiers.a, Integers.b(128));
            keySizes.put(NTTObjectIdentifiers.b, Integers.b(Opcodes.CHECKCAST));
            keySizes.put(NTTObjectIdentifiers.c, Integers.b(256));
            keySizes.put(CryptoProObjectIdentifiers.f, Integers.b(256));
            this.a = Collections.unmodifiableMap(keySizes);
        }

        public int a(AlgorithmIdentifier algorithmIdentifier) {
            Integer keySize = (Integer) this.a.get(algorithmIdentifier.e());
            if (keySize != null) {
                return keySize.intValue();
            }
            return -1;
        }
    }
}
