package org.spongycastle.jcajce.provider.keystore.bcfks;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.bc.EncryptedObjectStoreData;
import org.spongycastle.asn1.bc.EncryptedPrivateKeyData;
import org.spongycastle.asn1.bc.EncryptedSecretKeyData;
import org.spongycastle.asn1.bc.ObjectData;
import org.spongycastle.asn1.bc.ObjectDataSequence;
import org.spongycastle.asn1.bc.ObjectStore;
import org.spongycastle.asn1.bc.ObjectStoreData;
import org.spongycastle.asn1.bc.ObjectStoreIntegrityCheck;
import org.spongycastle.asn1.bc.PbkdMacIntegrityCheck;
import org.spongycastle.asn1.bc.SecretKeyData;
import org.spongycastle.asn1.cms.CCMParameters;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.spongycastle.asn1.pkcs.EncryptionScheme;
import org.spongycastle.asn1.pkcs.KeyDerivationFunc;
import org.spongycastle.asn1.pkcs.PBES2Parameters;
import org.spongycastle.asn1.pkcs.PBKDF2Params;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class BcFKSKeyStoreSpi extends KeyStoreSpi {
    private static final Map<String, ASN1ObjectIdentifier> a;
    private static final Map<ASN1ObjectIdentifier, String> b;
    private static final BigInteger c = BigInteger.valueOf(0);
    private static final BigInteger d = BigInteger.valueOf(1);
    private static final BigInteger e = BigInteger.valueOf(2);
    private static final BigInteger f = BigInteger.valueOf(3);
    private static final BigInteger g = BigInteger.valueOf(4);
    private final BouncyCastleProvider h;
    private final Map<String, ObjectData> i = new HashMap();
    private final Map<String, PrivateKey> j = new HashMap();
    private AlgorithmIdentifier k;
    private KeyDerivationFunc l;
    private Date m;
    private Date n;

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        HashMap hashMap2 = new HashMap();
        b = hashMap2;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = OIWObjectIdentifiers.h;
        hashMap.put("DESEDE", aSN1ObjectIdentifier);
        hashMap.put("TRIPLEDES", aSN1ObjectIdentifier);
        hashMap.put("TDEA", aSN1ObjectIdentifier);
        hashMap.put("HMACSHA1", PKCSObjectIdentifiers.u0);
        hashMap.put("HMACSHA224", PKCSObjectIdentifiers.v0);
        hashMap.put("HMACSHA256", PKCSObjectIdentifiers.w0);
        hashMap.put("HMACSHA384", PKCSObjectIdentifiers.x0);
        hashMap.put("HMACSHA512", PKCSObjectIdentifiers.y0);
        hashMap2.put(PKCSObjectIdentifiers.K, "RSA");
        hashMap2.put(X9ObjectIdentifiers.t3, "EC");
        hashMap2.put(OIWObjectIdentifiers.l, "DH");
        hashMap2.put(PKCSObjectIdentifiers.b0, "DH");
        hashMap2.put(X9ObjectIdentifiers.d4, "DSA");
    }

    private static String i(ASN1ObjectIdentifier oid) {
        String algName = b.get(oid);
        if (algName != null) {
            return algName;
        }
        return oid.s();
    }

    BcFKSKeyStoreSpi(BouncyCastleProvider provider) {
        this.h = provider;
    }

    public Key engineGetKey(String alias, char[] password) {
        KeyFactory kFact;
        SecretKeyFactory kFact2;
        ObjectData ent = this.i.get(alias);
        if (ent == null) {
            return null;
        }
        if (ent.k().equals(d) || ent.k().equals(f)) {
            PrivateKey cachedKey = this.j.get(alias);
            if (cachedKey != null) {
                return cachedKey;
            }
            EncryptedPrivateKeyInfo encInfo = EncryptedPrivateKeyInfo.g(EncryptedPrivateKeyData.g(ent.f()).f());
            try {
                PrivateKeyInfo pInfo = PrivateKeyInfo.f(d("PRIVATE_KEY_ENCRYPTION", encInfo.f(), password, encInfo.e()));
                if (this.h != null) {
                    kFact = KeyFactory.getInstance(pInfo.g().e().s(), this.h);
                } else {
                    kFact = KeyFactory.getInstance(i(pInfo.g().e()));
                }
                PrivateKey privateKey = kFact.generatePrivate(new PKCS8EncodedKeySpec(pInfo.getEncoded()));
                this.j.put(alias, privateKey);
                return privateKey;
            } catch (Exception e2) {
                throw new UnrecoverableKeyException("BCFKS KeyStore unable to recover private key (" + alias + "): " + e2.getMessage());
            }
        } else if (ent.k().equals(e) || ent.k().equals(g)) {
            EncryptedSecretKeyData encKeyData = EncryptedSecretKeyData.f(ent.f());
            try {
                SecretKeyData keyData = SecretKeyData.e(d("SECRET_KEY_ENCRYPTION", encKeyData.g(), password, encKeyData.e()));
                if (this.h != null) {
                    kFact2 = SecretKeyFactory.getInstance(keyData.f().s(), this.h);
                } else {
                    kFact2 = SecretKeyFactory.getInstance(keyData.f().s());
                }
                return kFact2.generateSecret(new SecretKeySpec(keyData.g(), keyData.f().s()));
            } catch (Exception e3) {
                throw new UnrecoverableKeyException("BCFKS KeyStore unable to recover secret key (" + alias + "): " + e3.getMessage());
            }
        } else {
            throw new UnrecoverableKeyException("BCFKS KeyStore unable to recover secret key (" + alias + "): type not recognized");
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.security.cert.X509Certificate[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.security.cert.Certificate[] engineGetCertificateChain(java.lang.String r7) {
        /*
            r6 = this;
            java.util.Map<java.lang.String, org.spongycastle.asn1.bc.ObjectData> r0 = r6.i
            java.lang.Object r0 = r0.get(r7)
            org.spongycastle.asn1.bc.ObjectData r0 = (org.spongycastle.asn1.bc.ObjectData) r0
            if (r0 == 0) goto L_0x0041
            java.math.BigInteger r1 = r0.k()
            java.math.BigInteger r2 = d
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x0022
            java.math.BigInteger r1 = r0.k()
            java.math.BigInteger r2 = f
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0041
        L_0x0022:
            byte[] r1 = r0.f()
            org.spongycastle.asn1.bc.EncryptedPrivateKeyData r1 = org.spongycastle.asn1.bc.EncryptedPrivateKeyData.g(r1)
            org.spongycastle.asn1.x509.Certificate[] r2 = r1.e()
            int r3 = r2.length
            java.security.cert.X509Certificate[] r3 = new java.security.cert.X509Certificate[r3]
            r4 = 0
        L_0x0032:
            int r5 = r3.length
            if (r4 == r5) goto L_0x0040
            r5 = r2[r4]
            java.security.cert.Certificate r5 = r6.c(r5)
            r3[r4] = r5
            int r4 = r4 + 1
            goto L_0x0032
        L_0x0040:
            return r3
        L_0x0041:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jcajce.provider.keystore.bcfks.BcFKSKeyStoreSpi.engineGetCertificateChain(java.lang.String):java.security.cert.Certificate[]");
    }

    public Certificate engineGetCertificate(String s) {
        ObjectData ent = this.i.get(s);
        if (ent == null) {
            return null;
        }
        if (ent.k().equals(d) || ent.k().equals(f)) {
            return c(EncryptedPrivateKeyData.g(ent.f()).e()[0]);
        }
        if (ent.k().equals(c)) {
            return c(ent.f());
        }
        return null;
    }

    private Certificate c(Object cert) {
        BouncyCastleProvider bouncyCastleProvider = this.h;
        if (bouncyCastleProvider != null) {
            try {
                return CertificateFactory.getInstance("X.509", bouncyCastleProvider).generateCertificate(new ByteArrayInputStream(org.spongycastle.asn1.x509.Certificate.f(cert).getEncoded()));
            } catch (Exception e2) {
                return null;
            }
        } else {
            try {
                return CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(org.spongycastle.asn1.x509.Certificate.f(cert).getEncoded()));
            } catch (Exception e3) {
                return null;
            }
        }
    }

    public Date engineGetCreationDate(String s) {
        ObjectData ent = this.i.get(s);
        if (ent == null) {
            return null;
        }
        try {
            return ent.i().q();
        } catch (ParseException e2) {
            return new Date();
        }
    }

    public void engineSetKeyEntry(String alias, Key key, char[] password, Certificate[] chain) {
        Date creationDate;
        Cipher c2;
        byte[] encryptedKey;
        Cipher c3;
        String str = alias;
        Key key2 = key;
        Certificate[] certificateArr = chain;
        Date creationDate2 = new Date();
        Date lastEditDate = creationDate2;
        ObjectData entry = this.i.get(str);
        if (entry != null) {
            creationDate = e(entry, creationDate2);
        } else {
            creationDate = creationDate2;
        }
        this.j.remove(str);
        if (key2 instanceof PrivateKey) {
            if (certificateArr != null) {
                try {
                    byte[] encodedKey = key.getEncoded();
                    KeyDerivationFunc pbkdAlgId = g(32);
                    byte[] keyBytes = f(pbkdAlgId, "PRIVATE_KEY_ENCRYPTION", password != null ? password : new char[0]);
                    BouncyCastleProvider bouncyCastleProvider = this.h;
                    if (bouncyCastleProvider == null) {
                        c3 = Cipher.getInstance("AES/CCM/NoPadding");
                    } else {
                        c3 = Cipher.getInstance("AES/CCM/NoPadding", bouncyCastleProvider);
                    }
                    c3.init(1, new SecretKeySpec(keyBytes, "AES"));
                    byte[] encryptedKey2 = c3.doFinal(encodedKey);
                    byte[] bArr = encodedKey;
                    EncryptedPrivateKeyInfo keyInfo = new EncryptedPrivateKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.j0, new PBES2Parameters(pbkdAlgId, new EncryptionScheme(NISTObjectIdentifiers.P, CCMParameters.f(c3.getParameters().getEncoded())))), encryptedKey2);
                    ObjectData objectData = r2;
                    Map<String, ObjectData> map = this.i;
                    EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = keyInfo;
                    byte[] bArr2 = encryptedKey2;
                    Cipher cipher = c3;
                    byte[] bArr3 = keyBytes;
                    KeyDerivationFunc keyDerivationFunc = pbkdAlgId;
                    ObjectData objectData2 = new ObjectData(d, alias, creationDate, lastEditDate, b(keyInfo, certificateArr).getEncoded(), (String) null);
                    map.put(str, objectData);
                } catch (Exception e2) {
                    throw new ExtKeyStoreException("BCFKS KeyStore exception storing private key: " + e2.toString(), e2);
                }
            } else {
                throw new KeyStoreException("BCFKS KeyStore requires a certificate chain for private key storage.");
            }
        } else if (!(key2 instanceof SecretKey)) {
            throw new KeyStoreException("BCFKS KeyStore unable to recognize key.");
        } else if (certificateArr == null) {
            try {
                byte[] encodedKey2 = key.getEncoded();
                KeyDerivationFunc pbkdAlgId2 = g(32);
                byte[] keyBytes2 = f(pbkdAlgId2, "SECRET_KEY_ENCRYPTION", password != null ? password : new char[0]);
                BouncyCastleProvider bouncyCastleProvider2 = this.h;
                if (bouncyCastleProvider2 == null) {
                    c2 = Cipher.getInstance("AES/CCM/NoPadding");
                } else {
                    c2 = Cipher.getInstance("AES/CCM/NoPadding", bouncyCastleProvider2);
                }
                c2.init(1, new SecretKeySpec(keyBytes2, "AES"));
                String keyAlg = Strings.l(key.getAlgorithm());
                if (keyAlg.indexOf("AES") > -1) {
                    encryptedKey = c2.doFinal(new SecretKeyData(NISTObjectIdentifiers.s, encodedKey2).getEncoded());
                } else {
                    ASN1ObjectIdentifier algOid = a.get(keyAlg);
                    if (algOid != null) {
                        encryptedKey = c2.doFinal(new SecretKeyData(algOid, encodedKey2).getEncoded());
                    } else {
                        byte[] bArr4 = encodedKey2;
                        String keyAlg2 = keyAlg;
                        Cipher cipher2 = c2;
                        byte[] bArr5 = keyBytes2;
                        KeyDerivationFunc keyDerivationFunc2 = pbkdAlgId2;
                        throw new KeyStoreException("BCFKS KeyStore cannot recognize secret key (" + keyAlg2 + ") for storage.");
                    }
                }
                byte[] bArr6 = encodedKey2;
                String keyAlg3 = keyAlg;
                ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.j0;
                Map<String, ObjectData> map2 = this.i;
                ObjectData objectData3 = r2;
                String str2 = keyAlg3;
                byte[] bArr7 = encryptedKey;
                Cipher cipher3 = c2;
                byte[] bArr8 = keyBytes2;
                KeyDerivationFunc keyDerivationFunc3 = pbkdAlgId2;
                ObjectData objectData4 = new ObjectData(e, alias, creationDate, lastEditDate, new EncryptedSecretKeyData(new AlgorithmIdentifier(aSN1ObjectIdentifier, new PBES2Parameters(pbkdAlgId2, new EncryptionScheme(NISTObjectIdentifiers.P, CCMParameters.f(c2.getParameters().getEncoded())))), encryptedKey).getEncoded(), (String) null);
                map2.put(str, objectData3);
            } catch (Exception e3) {
                throw new ExtKeyStoreException("BCFKS KeyStore exception storing private key: " + e3.toString(), e3);
            }
        } else {
            throw new KeyStoreException("BCFKS KeyStore cannot store certificate chain with secret key.");
        }
        this.n = lastEditDate;
    }

    private SecureRandom h() {
        return new SecureRandom();
    }

    private EncryptedPrivateKeyData b(EncryptedPrivateKeyInfo encryptedPrivateKeyInfo, Certificate[] chain) {
        org.spongycastle.asn1.x509.Certificate[] certChain = new org.spongycastle.asn1.x509.Certificate[chain.length];
        for (int i2 = 0; i2 != chain.length; i2++) {
            certChain[i2] = org.spongycastle.asn1.x509.Certificate.f(chain[i2].getEncoded());
        }
        return new EncryptedPrivateKeyData(encryptedPrivateKeyInfo, certChain);
    }

    public void engineSetKeyEntry(String alias, byte[] keyBytes, Certificate[] chain) {
        Date creationDate;
        String str = alias;
        Certificate[] certificateArr = chain;
        Date creationDate2 = new Date();
        Date lastEditDate = creationDate2;
        ObjectData entry = this.i.get(str);
        if (entry != null) {
            creationDate = e(entry, creationDate2);
        } else {
            creationDate = creationDate2;
        }
        if (certificateArr != null) {
            try {
                EncryptedPrivateKeyInfo encInfo = EncryptedPrivateKeyInfo.g(keyBytes);
                try {
                    this.j.remove(str);
                    Map<String, ObjectData> map = this.i;
                    ObjectData objectData = r2;
                    ObjectData objectData2 = new ObjectData(f, alias, creationDate, lastEditDate, b(encInfo, certificateArr).getEncoded(), (String) null);
                    map.put(str, objectData);
                } catch (Exception e2) {
                    throw new ExtKeyStoreException("BCFKS KeyStore exception storing protected private key: " + e2.toString(), e2);
                }
            } catch (Exception e3) {
                throw new ExtKeyStoreException("BCFKS KeyStore private key encoding must be an EncryptedPrivateKeyInfo.", e3);
            }
        } else {
            try {
                this.i.put(str, new ObjectData(g, alias, creationDate, lastEditDate, keyBytes, (String) null));
            } catch (Exception e4) {
                throw new ExtKeyStoreException("BCFKS KeyStore exception storing protected private key: " + e4.toString(), e4);
            }
        }
        this.n = lastEditDate;
    }

    public void engineSetCertificateEntry(String alias, Certificate certificate) {
        ObjectData entry = this.i.get(alias);
        Date creationDate = new Date();
        Date lastEditDate = creationDate;
        if (entry != null) {
            if (entry.k().equals(c)) {
                creationDate = e(entry, creationDate);
            } else {
                throw new KeyStoreException("BCFKS KeyStore already has a key entry with alias " + alias);
            }
        }
        try {
            this.i.put(alias, new ObjectData(c, alias, creationDate, lastEditDate, certificate.getEncoded(), (String) null));
            this.n = lastEditDate;
        } catch (CertificateEncodingException e2) {
            throw new ExtKeyStoreException("BCFKS KeyStore unable to handle certificate: " + e2.getMessage(), e2);
        }
    }

    private Date e(ObjectData entry, Date creationDate) {
        try {
            return entry.e().q();
        } catch (ParseException e2) {
            return creationDate;
        }
    }

    public void engineDeleteEntry(String alias) {
        if (this.i.get(alias) != null) {
            this.j.remove(alias);
            this.i.remove(alias);
            this.n = new Date();
        }
    }

    public Enumeration<String> engineAliases() {
        final Iterator<String> it = new HashSet(this.i.keySet()).iterator();
        return new Enumeration() {
            public boolean hasMoreElements() {
                return it.hasNext();
            }

            public Object nextElement() {
                return it.next();
            }
        };
    }

    public boolean engineContainsAlias(String alias) {
        if (alias != null) {
            return this.i.containsKey(alias);
        }
        throw new NullPointerException("alias value is null");
    }

    public int engineSize() {
        return this.i.size();
    }

    public boolean engineIsKeyEntry(String alias) {
        ObjectData ent = this.i.get(alias);
        if (ent == null) {
            return false;
        }
        BigInteger entryType = ent.k();
        if (entryType.equals(d) || entryType.equals(e) || entryType.equals(f) || entryType.equals(g)) {
            return true;
        }
        return false;
    }

    public boolean engineIsCertificateEntry(String alias) {
        ObjectData ent = this.i.get(alias);
        if (ent != null) {
            return ent.k().equals(c);
        }
        return false;
    }

    public String engineGetCertificateAlias(Certificate certificate) {
        if (certificate == null) {
            return null;
        }
        try {
            byte[] encodedCert = certificate.getEncoded();
            for (String alias : this.i.keySet()) {
                ObjectData ent = this.i.get(alias);
                if (ent.k().equals(c)) {
                    if (Arrays.b(ent.f(), encodedCert)) {
                        return alias;
                    }
                } else if (ent.k().equals(d) || ent.k().equals(f)) {
                    try {
                        if (Arrays.b(EncryptedPrivateKeyData.g(ent.f()).e()[0].toASN1Primitive().getEncoded(), encodedCert)) {
                            return alias;
                        }
                    } catch (IOException e2) {
                    }
                }
            }
            return null;
        } catch (CertificateEncodingException e3) {
            return null;
        }
    }

    private byte[] f(KeyDerivationFunc pbkdAlgorithm, String purpose, char[] password) {
        byte[] encPassword = PBEParametersGenerator.a(password);
        byte[] differentiator = PBEParametersGenerator.a(purpose.toCharArray());
        PKCS5S2ParametersGenerator pGen = new PKCS5S2ParametersGenerator(new SHA512Digest());
        if (pbkdAlgorithm.e().equals(PKCSObjectIdentifiers.k0)) {
            PBKDF2Params pbkdf2Params = PBKDF2Params.getInstance(pbkdAlgorithm.g());
            if (pbkdf2Params.getPrf().e().equals(PKCSObjectIdentifiers.y0)) {
                pGen.g(Arrays.r(encPassword, differentiator), pbkdf2Params.getSalt(), pbkdf2Params.getIterationCount().intValue());
                return ((KeyParameter) pGen.e(pbkdf2Params.getKeyLength().intValue() * 8)).a();
            }
            throw new IOException("BCFKS KeyStore: unrecognized MAC PBKD PRF.");
        }
        throw new IOException("BCFKS KeyStore: unrecognized MAC PBKD.");
    }

    private void j(byte[] content, PbkdMacIntegrityCheck integrityCheck, char[] password) {
        if (!Arrays.u(a(content, integrityCheck.g(), integrityCheck.h(), password), integrityCheck.f())) {
            throw new IOException("BCFKS KeyStore corrupted: MAC calculation failed.");
        }
    }

    private byte[] a(byte[] content, AlgorithmIdentifier algorithm, KeyDerivationFunc pbkdAlgorithm, char[] password) {
        Mac mac;
        String algorithmId = algorithm.e().s();
        BouncyCastleProvider bouncyCastleProvider = this.h;
        if (bouncyCastleProvider != null) {
            mac = Mac.getInstance(algorithmId, bouncyCastleProvider);
        } else {
            mac = Mac.getInstance(algorithmId);
        }
        try {
            mac.init(new SecretKeySpec(f(pbkdAlgorithm, "INTEGRITY_CHECK", password != null ? password : new char[0]), algorithmId));
            return mac.doFinal(content);
        } catch (InvalidKeyException e2) {
            throw new IOException("Cannot set up MAC calculation: " + e2.getMessage());
        }
    }

    public void engineStore(OutputStream outputStream, char[] password) {
        Cipher c2;
        ObjectData[] dataArray = (ObjectData[]) this.i.values().toArray(new ObjectData[this.i.size()]);
        KeyDerivationFunc pbkdAlgId = g(32);
        byte[] keyBytes = f(pbkdAlgId, "STORE_ENCRYPTION", password != null ? password : new char[0]);
        ObjectStoreData storeData = new ObjectStoreData(this.k, this.m, this.n, new ObjectDataSequence(dataArray), (String) null);
        try {
            BouncyCastleProvider bouncyCastleProvider = this.h;
            if (bouncyCastleProvider == null) {
                c2 = Cipher.getInstance("AES/CCM/NoPadding");
            } else {
                c2 = Cipher.getInstance("AES/CCM/NoPadding", bouncyCastleProvider);
            }
            c2.init(1, new SecretKeySpec(keyBytes, "AES"));
            EncryptedObjectStoreData encStoreData = new EncryptedObjectStoreData(new AlgorithmIdentifier(PKCSObjectIdentifiers.j0, new PBES2Parameters(pbkdAlgId, new EncryptionScheme(NISTObjectIdentifiers.P, CCMParameters.f(c2.getParameters().getEncoded())))), c2.doFinal(storeData.getEncoded()));
            PBKDF2Params pbkdf2Params = PBKDF2Params.getInstance(this.l.g());
            byte[] pbkdSalt = new byte[pbkdf2Params.getSalt().length];
            h().nextBytes(pbkdSalt);
            this.l = new KeyDerivationFunc(this.l.e(), new PBKDF2Params(pbkdSalt, pbkdf2Params.getIterationCount().intValue(), pbkdf2Params.getKeyLength().intValue(), pbkdf2Params.getPrf()));
            outputStream.write(new ObjectStore(encStoreData, new ObjectStoreIntegrityCheck(new PbkdMacIntegrityCheck(this.k, this.l, a(encStoreData.getEncoded(), this.k, this.l, password)))).getEncoded());
            outputStream.flush();
        } catch (NoSuchPaddingException e2) {
            throw new NoSuchAlgorithmException(e2.toString());
        } catch (BadPaddingException e3) {
            throw new IOException(e3.toString());
        } catch (IllegalBlockSizeException e4) {
            throw new IOException(e4.toString());
        } catch (InvalidKeyException e5) {
            throw new IOException(e5.toString());
        }
    }

    public void engineLoad(InputStream inputStream, char[] password) {
        ObjectStoreData storeData;
        this.i.clear();
        this.j.clear();
        this.m = null;
        this.n = null;
        this.k = null;
        if (inputStream == null) {
            Date date = new Date();
            this.m = date;
            this.n = date;
            this.k = new AlgorithmIdentifier(PKCSObjectIdentifiers.y0, DERNull.c);
            this.l = g(64);
            return;
        }
        ObjectStore store = ObjectStore.e(new ASN1InputStream(inputStream).r());
        ObjectStoreIntegrityCheck integrityCheck = store.f();
        if (integrityCheck.g() == 0) {
            PbkdMacIntegrityCheck pbkdMacIntegrityCheck = PbkdMacIntegrityCheck.e(integrityCheck.f());
            this.k = pbkdMacIntegrityCheck.g();
            this.l = pbkdMacIntegrityCheck.h();
            j(store.g().toASN1Primitive().getEncoded(), pbkdMacIntegrityCheck, password);
            ASN1Encodable sData = store.g();
            if (sData instanceof EncryptedObjectStoreData) {
                EncryptedObjectStoreData encryptedStoreData = (EncryptedObjectStoreData) sData;
                storeData = ObjectStoreData.f(d("STORE_ENCRYPTION", encryptedStoreData.f(), password, encryptedStoreData.e().q()));
            } else {
                storeData = ObjectStoreData.f(sData);
            }
            try {
                this.m = storeData.e().q();
                this.n = storeData.h().q();
                if (storeData.g().equals(this.k)) {
                    Iterator it = storeData.i().iterator();
                    while (it.hasNext()) {
                        ObjectData objData = ObjectData.h(it.next());
                        this.i.put(objData.g(), objData);
                    }
                    return;
                }
                throw new IOException("BCFKS KeyStore storeData integrity algorithm does not match store integrity algorithm.");
            } catch (ParseException e2) {
                throw new IOException("BCFKS KeyStore unable to parse store data information.");
            }
        } else {
            throw new IOException("BCFKS KeyStore unable to recognize integrity check.");
        }
    }

    private byte[] d(String purpose, AlgorithmIdentifier protectAlgId, char[] password, byte[] encryptedData) {
        AlgorithmParameters algParams;
        Cipher c2;
        if (protectAlgId.e().equals(PKCSObjectIdentifiers.j0)) {
            PBES2Parameters pbes2Parameters = PBES2Parameters.f(protectAlgId.h());
            EncryptionScheme algId = pbes2Parameters.e();
            if (algId.e().equals(NISTObjectIdentifiers.P)) {
                try {
                    CCMParameters ccmParameters = CCMParameters.f(algId.g());
                    BouncyCastleProvider bouncyCastleProvider = this.h;
                    if (bouncyCastleProvider == null) {
                        c2 = Cipher.getInstance("AES/CCM/NoPadding");
                        algParams = AlgorithmParameters.getInstance("CCM");
                    } else {
                        c2 = Cipher.getInstance("AES/CCM/NoPadding", bouncyCastleProvider);
                        algParams = AlgorithmParameters.getInstance("CCM", this.h);
                    }
                    algParams.init(ccmParameters.getEncoded());
                    c2.init(2, new SecretKeySpec(f(pbes2Parameters.g(), purpose, password != null ? password : new char[0]), "AES"), algParams);
                    return c2.doFinal(encryptedData);
                } catch (Exception e2) {
                    throw new IOException(e2.toString());
                }
            } else {
                throw new IOException("BCFKS KeyStore cannot recognize protection encryption algorithm.");
            }
        } else {
            throw new IOException("BCFKS KeyStore cannot recognize protection algorithm.");
        }
    }

    private KeyDerivationFunc g(int keySizeInBytes) {
        byte[] pbkdSalt = new byte[64];
        h().nextBytes(pbkdSalt);
        return new KeyDerivationFunc(PKCSObjectIdentifiers.k0, new PBKDF2Params(pbkdSalt, 1024, keySizeInBytes, new AlgorithmIdentifier(PKCSObjectIdentifiers.y0, DERNull.c)));
    }

    public static class Std extends BcFKSKeyStoreSpi {
        public /* bridge */ /* synthetic */ Enumeration engineAliases() {
            return BcFKSKeyStoreSpi.super.engineAliases();
        }

        public /* bridge */ /* synthetic */ boolean engineContainsAlias(String str) {
            return BcFKSKeyStoreSpi.super.engineContainsAlias(str);
        }

        public /* bridge */ /* synthetic */ void engineDeleteEntry(String str) {
            BcFKSKeyStoreSpi.super.engineDeleteEntry(str);
        }

        public /* bridge */ /* synthetic */ Certificate engineGetCertificate(String str) {
            return BcFKSKeyStoreSpi.super.engineGetCertificate(str);
        }

        public /* bridge */ /* synthetic */ String engineGetCertificateAlias(Certificate certificate) {
            return BcFKSKeyStoreSpi.super.engineGetCertificateAlias(certificate);
        }

        public /* bridge */ /* synthetic */ Certificate[] engineGetCertificateChain(String str) {
            return BcFKSKeyStoreSpi.super.engineGetCertificateChain(str);
        }

        public /* bridge */ /* synthetic */ Date engineGetCreationDate(String str) {
            return BcFKSKeyStoreSpi.super.engineGetCreationDate(str);
        }

        public /* bridge */ /* synthetic */ Key engineGetKey(String str, char[] cArr) {
            return BcFKSKeyStoreSpi.super.engineGetKey(str, cArr);
        }

        public /* bridge */ /* synthetic */ boolean engineIsCertificateEntry(String str) {
            return BcFKSKeyStoreSpi.super.engineIsCertificateEntry(str);
        }

        public /* bridge */ /* synthetic */ boolean engineIsKeyEntry(String str) {
            return BcFKSKeyStoreSpi.super.engineIsKeyEntry(str);
        }

        public /* bridge */ /* synthetic */ void engineLoad(InputStream inputStream, char[] cArr) {
            BcFKSKeyStoreSpi.super.engineLoad(inputStream, cArr);
        }

        public /* bridge */ /* synthetic */ void engineSetCertificateEntry(String str, Certificate certificate) {
            BcFKSKeyStoreSpi.super.engineSetCertificateEntry(str, certificate);
        }

        public /* bridge */ /* synthetic */ void engineSetKeyEntry(String str, Key key, char[] cArr, Certificate[] certificateArr) {
            BcFKSKeyStoreSpi.super.engineSetKeyEntry(str, key, cArr, certificateArr);
        }

        public /* bridge */ /* synthetic */ void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr) {
            BcFKSKeyStoreSpi.super.engineSetKeyEntry(str, bArr, certificateArr);
        }

        public /* bridge */ /* synthetic */ int engineSize() {
            return BcFKSKeyStoreSpi.super.engineSize();
        }

        public /* bridge */ /* synthetic */ void engineStore(OutputStream outputStream, char[] cArr) {
            BcFKSKeyStoreSpi.super.engineStore(outputStream, cArr);
        }

        public Std() {
            super(new BouncyCastleProvider());
        }
    }

    public static class Def extends BcFKSKeyStoreSpi {
        public /* bridge */ /* synthetic */ Enumeration engineAliases() {
            return BcFKSKeyStoreSpi.super.engineAliases();
        }

        public /* bridge */ /* synthetic */ boolean engineContainsAlias(String str) {
            return BcFKSKeyStoreSpi.super.engineContainsAlias(str);
        }

        public /* bridge */ /* synthetic */ void engineDeleteEntry(String str) {
            BcFKSKeyStoreSpi.super.engineDeleteEntry(str);
        }

        public /* bridge */ /* synthetic */ Certificate engineGetCertificate(String str) {
            return BcFKSKeyStoreSpi.super.engineGetCertificate(str);
        }

        public /* bridge */ /* synthetic */ String engineGetCertificateAlias(Certificate certificate) {
            return BcFKSKeyStoreSpi.super.engineGetCertificateAlias(certificate);
        }

        public /* bridge */ /* synthetic */ Certificate[] engineGetCertificateChain(String str) {
            return BcFKSKeyStoreSpi.super.engineGetCertificateChain(str);
        }

        public /* bridge */ /* synthetic */ Date engineGetCreationDate(String str) {
            return BcFKSKeyStoreSpi.super.engineGetCreationDate(str);
        }

        public /* bridge */ /* synthetic */ Key engineGetKey(String str, char[] cArr) {
            return BcFKSKeyStoreSpi.super.engineGetKey(str, cArr);
        }

        public /* bridge */ /* synthetic */ boolean engineIsCertificateEntry(String str) {
            return BcFKSKeyStoreSpi.super.engineIsCertificateEntry(str);
        }

        public /* bridge */ /* synthetic */ boolean engineIsKeyEntry(String str) {
            return BcFKSKeyStoreSpi.super.engineIsKeyEntry(str);
        }

        public /* bridge */ /* synthetic */ void engineLoad(InputStream inputStream, char[] cArr) {
            BcFKSKeyStoreSpi.super.engineLoad(inputStream, cArr);
        }

        public /* bridge */ /* synthetic */ void engineSetCertificateEntry(String str, Certificate certificate) {
            BcFKSKeyStoreSpi.super.engineSetCertificateEntry(str, certificate);
        }

        public /* bridge */ /* synthetic */ void engineSetKeyEntry(String str, Key key, char[] cArr, Certificate[] certificateArr) {
            BcFKSKeyStoreSpi.super.engineSetKeyEntry(str, key, cArr, certificateArr);
        }

        public /* bridge */ /* synthetic */ void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr) {
            BcFKSKeyStoreSpi.super.engineSetKeyEntry(str, bArr, certificateArr);
        }

        public /* bridge */ /* synthetic */ int engineSize() {
            return BcFKSKeyStoreSpi.super.engineSize();
        }

        public /* bridge */ /* synthetic */ void engineStore(OutputStream outputStream, char[] cArr) {
            BcFKSKeyStoreSpi.super.engineStore(outputStream, cArr);
        }

        public Def() {
            super((BouncyCastleProvider) null);
        }
    }

    public static class ExtKeyStoreException extends KeyStoreException {
        private final Throwable cause;

        ExtKeyStoreException(String msg, Throwable cause2) {
            super(msg);
            this.cause = cause2;
        }

        public Throwable getCause() {
            return this.cause;
        }
    }
}
