package org.spongycastle.jcajce.provider.keystore.bc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.generators.PKCS12ParametersGenerator;
import org.spongycastle.crypto.io.DigestInputStream;
import org.spongycastle.crypto.io.DigestOutputStream;
import org.spongycastle.crypto.io.MacInputStream;
import org.spongycastle.crypto.io.MacOutputStream;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.interfaces.BCKeyStore;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.io.Streams;
import org.spongycastle.util.io.TeeOutputStream;

public class BcKeyStoreSpi extends KeyStoreSpi implements BCKeyStore {
    protected Hashtable c = new Hashtable();
    protected SecureRandom d = new SecureRandom();
    protected int f;
    private final JcaJceHelper q = new BCJcaJceHelper();

    public BcKeyStoreSpi(int version) {
        this.f = version;
    }

    public class StoreEntry {
        int a;
        String b;
        Object c;
        Certificate[] d;
        Date e;

        StoreEntry(String alias, Certificate obj) {
            this.e = new Date();
            this.a = 1;
            this.b = alias;
            this.c = obj;
            this.d = null;
        }

        StoreEntry(String alias, byte[] obj, Certificate[] certChain) {
            this.e = new Date();
            this.a = 3;
            this.b = alias;
            this.c = obj;
            this.d = certChain;
        }

        StoreEntry(String alias, Key key, char[] password, Certificate[] certChain) {
            this.e = new Date();
            this.a = 4;
            this.b = alias;
            this.d = certChain;
            byte[] salt = new byte[20];
            BcKeyStoreSpi.this.d.setSeed(System.currentTimeMillis());
            BcKeyStoreSpi.this.d.nextBytes(salt);
            int iterationCount = (BcKeyStoreSpi.this.d.nextInt() & 1023) + 1024;
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            DataOutputStream dOut = new DataOutputStream(bOut);
            dOut.writeInt(salt.length);
            dOut.write(salt);
            dOut.writeInt(iterationCount);
            DataOutputStream dOut2 = new DataOutputStream(new CipherOutputStream(dOut, BcKeyStoreSpi.this.h("PBEWithSHAAnd3-KeyTripleDES-CBC", 1, password, salt, iterationCount)));
            BcKeyStoreSpi.this.f(key, dOut2);
            dOut2.close();
            this.c = bOut.toByteArray();
        }

        StoreEntry(String alias, Date date, int type, Object obj) {
            this.e = new Date();
            this.b = alias;
            this.e = date;
            this.a = type;
            this.c = obj;
        }

        StoreEntry(String alias, Date date, int type, Object obj, Certificate[] certChain) {
            this.e = new Date();
            this.b = alias;
            this.e = date;
            this.a = type;
            this.c = obj;
            this.d = certChain;
        }

        /* access modifiers changed from: package-private */
        public int f() {
            return this.a;
        }

        /* access modifiers changed from: package-private */
        public String a() {
            return this.b;
        }

        /* access modifiers changed from: package-private */
        public Object d() {
            return this.c;
        }

        /* access modifiers changed from: package-private */
        public Object e(char[] password) {
            Key k;
            char[] cArr = password;
            if (cArr == null || cArr.length == 0) {
                Object obj = this.c;
                if (obj instanceof Key) {
                    return obj;
                }
            }
            if (this.a == 4) {
                DataInputStream dIn = new DataInputStream(new ByteArrayInputStream((byte[]) this.c));
                try {
                    byte[] salt = new byte[dIn.readInt()];
                    dIn.readFully(salt);
                    return BcKeyStoreSpi.this.d(new DataInputStream(new CipherInputStream(dIn, BcKeyStoreSpi.this.h("PBEWithSHAAnd3-KeyTripleDES-CBC", 2, password, salt, dIn.readInt()))));
                } catch (Exception e2) {
                    Exception exc = e2;
                    DataInputStream dIn2 = new DataInputStream(new ByteArrayInputStream((byte[]) this.c));
                    byte[] salt2 = new byte[dIn2.readInt()];
                    dIn2.readFully(salt2);
                    int iterationCount = dIn2.readInt();
                    try {
                        k = BcKeyStoreSpi.this.d(new DataInputStream(new CipherInputStream(dIn2, BcKeyStoreSpi.this.h("BrokenPBEWithSHAAnd3-KeyTripleDES-CBC", 2, password, salt2, iterationCount))));
                    } catch (Exception e3) {
                        DataInputStream dIn3 = new DataInputStream(new ByteArrayInputStream((byte[]) this.c));
                        salt2 = new byte[dIn3.readInt()];
                        dIn3.readFully(salt2);
                        iterationCount = dIn3.readInt();
                        k = BcKeyStoreSpi.this.d(new DataInputStream(new CipherInputStream(dIn3, BcKeyStoreSpi.this.h("OldPBEWithSHAAnd3-KeyTripleDES-CBC", 2, password, salt2, iterationCount))));
                    }
                    if (k != null) {
                        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
                        DataOutputStream dOut = new DataOutputStream(bOut);
                        dOut.writeInt(salt2.length);
                        dOut.write(salt2);
                        dOut.writeInt(iterationCount);
                        int i = iterationCount;
                        DataOutputStream dOut2 = new DataOutputStream(new CipherOutputStream(dOut, BcKeyStoreSpi.this.h("PBEWithSHAAnd3-KeyTripleDES-CBC", 1, password, salt2, iterationCount)));
                        BcKeyStoreSpi.this.f(k, dOut2);
                        dOut2.close();
                        this.c = bOut.toByteArray();
                        return k;
                    }
                    throw new UnrecoverableKeyException("no match");
                } catch (Exception e4) {
                    throw new UnrecoverableKeyException("no match");
                }
            } else {
                throw new RuntimeException("forget something!");
            }
        }

        /* access modifiers changed from: package-private */
        public Certificate[] b() {
            return this.d;
        }

        /* access modifiers changed from: package-private */
        public Date c() {
            return this.e;
        }
    }

    private void e(Certificate cert, DataOutputStream dOut) {
        try {
            byte[] cEnc = cert.getEncoded();
            dOut.writeUTF(cert.getType());
            dOut.writeInt(cEnc.length);
            dOut.write(cEnc);
        } catch (CertificateEncodingException ex) {
            throw new IOException(ex.toString());
        }
    }

    private Certificate c(DataInputStream dIn) {
        String type = dIn.readUTF();
        byte[] cEnc = new byte[dIn.readInt()];
        dIn.readFully(cEnc);
        try {
            return this.q.b(type).generateCertificate(new ByteArrayInputStream(cEnc));
        } catch (NoSuchProviderException ex) {
            throw new IOException(ex.toString());
        } catch (CertificateException ex2) {
            throw new IOException(ex2.toString());
        }
    }

    /* access modifiers changed from: private */
    public void f(Key key, DataOutputStream dOut) {
        byte[] enc = key.getEncoded();
        if (key instanceof PrivateKey) {
            dOut.write(0);
        } else if (key instanceof PublicKey) {
            dOut.write(1);
        } else {
            dOut.write(2);
        }
        dOut.writeUTF(key.getFormat());
        dOut.writeUTF(key.getAlgorithm());
        dOut.writeInt(enc.length);
        dOut.write(enc);
    }

    /* access modifiers changed from: private */
    public Key d(DataInputStream dIn) {
        KeySpec spec;
        int keyType = dIn.read();
        String format = dIn.readUTF();
        String algorithm = dIn.readUTF();
        byte[] enc = new byte[dIn.readInt()];
        dIn.readFully(enc);
        if (format.equals("PKCS#8") || format.equals("PKCS8")) {
            spec = new PKCS8EncodedKeySpec(enc);
        } else if (format.equals("X.509") || format.equals("X509")) {
            spec = new X509EncodedKeySpec(enc);
        } else if (format.equals("RAW")) {
            return new SecretKeySpec(enc, algorithm);
        } else {
            throw new IOException("Key format " + format + " not recognised!");
        }
        switch (keyType) {
            case 0:
                return this.q.e(algorithm).generatePrivate(spec);
            case 1:
                return this.q.e(algorithm).generatePublic(spec);
            case 2:
                return this.q.c(algorithm).generateSecret(spec);
            default:
                try {
                    throw new IOException("Key type " + keyType + " not recognised!");
                } catch (Exception e) {
                    throw new IOException("Exception creating key: " + e.toString());
                }
        }
    }

    /* access modifiers changed from: protected */
    public Cipher h(String algorithm, int mode, char[] password, byte[] salt, int iterationCount) {
        try {
            PBEKeySpec pbeSpec = new PBEKeySpec(password);
            SecretKeyFactory keyFact = this.q.c(algorithm);
            PBEParameterSpec defParams = new PBEParameterSpec(salt, iterationCount);
            Cipher cipher = this.q.a(algorithm);
            cipher.init(mode, keyFact.generateSecret(pbeSpec), defParams);
            return cipher;
        } catch (Exception e) {
            throw new IOException("Error initialising store of key store: " + e);
        }
    }

    public Enumeration engineAliases() {
        return this.c.keys();
    }

    public boolean engineContainsAlias(String alias) {
        return this.c.get(alias) != null;
    }

    public void engineDeleteEntry(String alias) {
        if (this.c.get(alias) != null) {
            this.c.remove(alias);
        }
    }

    public Certificate engineGetCertificate(String alias) {
        StoreEntry entry = (StoreEntry) this.c.get(alias);
        if (entry == null) {
            return null;
        }
        if (entry.f() == 1) {
            return (Certificate) entry.d();
        }
        Certificate[] chain = entry.b();
        if (chain != null) {
            return chain[0];
        }
        return null;
    }

    public String engineGetCertificateAlias(Certificate cert) {
        Enumeration e = this.c.elements();
        while (e.hasMoreElements()) {
            StoreEntry entry = (StoreEntry) e.nextElement();
            if (!(entry.d() instanceof Certificate)) {
                Certificate[] chain = entry.b();
                if (chain != null && chain[0].equals(cert)) {
                    return entry.a();
                }
            } else if (((Certificate) entry.d()).equals(cert)) {
                return entry.a();
            }
        }
        return null;
    }

    public Certificate[] engineGetCertificateChain(String alias) {
        StoreEntry entry = (StoreEntry) this.c.get(alias);
        if (entry != null) {
            return entry.b();
        }
        return null;
    }

    public Date engineGetCreationDate(String alias) {
        StoreEntry entry = (StoreEntry) this.c.get(alias);
        if (entry != null) {
            return entry.c();
        }
        return null;
    }

    public Key engineGetKey(String alias, char[] password) {
        StoreEntry entry = (StoreEntry) this.c.get(alias);
        if (entry == null || entry.f() == 1) {
            return null;
        }
        return (Key) entry.e(password);
    }

    public boolean engineIsCertificateEntry(String alias) {
        StoreEntry entry = (StoreEntry) this.c.get(alias);
        if (entry == null || entry.f() != 1) {
            return false;
        }
        return true;
    }

    public boolean engineIsKeyEntry(String alias) {
        StoreEntry entry = (StoreEntry) this.c.get(alias);
        if (entry == null || entry.f() == 1) {
            return false;
        }
        return true;
    }

    public void engineSetCertificateEntry(String alias, Certificate cert) {
        StoreEntry entry = (StoreEntry) this.c.get(alias);
        if (entry == null || entry.f() == 1) {
            this.c.put(alias, new StoreEntry(alias, cert));
            return;
        }
        throw new KeyStoreException("key store already has a key entry with alias " + alias);
    }

    public void engineSetKeyEntry(String alias, byte[] key, Certificate[] chain) {
        this.c.put(alias, new StoreEntry(alias, key, chain));
    }

    public void engineSetKeyEntry(String alias, Key key, char[] password, Certificate[] chain) {
        if (!(key instanceof PrivateKey) || chain != null) {
            try {
                this.c.put(alias, new StoreEntry(alias, key, password, chain));
            } catch (Exception e) {
                throw new KeyStoreException(e.toString());
            }
        } else {
            throw new KeyStoreException("no certificate chain for private key");
        }
    }

    public int engineSize() {
        return this.c.size();
    }

    /* access modifiers changed from: protected */
    public void g(InputStream in) {
        Certificate[] chain;
        DataInputStream dIn = new DataInputStream(in);
        for (int type = dIn.read(); type > 0; type = dIn.read()) {
            String alias = dIn.readUTF();
            Date date = new Date(dIn.readLong());
            int chainLength = dIn.readInt();
            if (chainLength != 0) {
                Certificate[] chain2 = new Certificate[chainLength];
                for (int i = 0; i != chainLength; i++) {
                    chain2[i] = c(dIn);
                }
                chain = chain2;
            } else {
                chain = null;
            }
            switch (type) {
                case 1:
                    this.c.put(alias, new StoreEntry(alias, date, 1, (Object) c(dIn)));
                    break;
                case 2:
                    int i2 = chainLength;
                    this.c.put(alias, new StoreEntry(alias, date, 2, d(dIn), chain));
                    break;
                case 3:
                case 4:
                    byte[] b = new byte[dIn.readInt()];
                    dIn.readFully(b);
                    this.c.put(alias, new StoreEntry(alias, date, type, b, chain));
                    int i3 = chainLength;
                    break;
                default:
                    throw new RuntimeException("Unknown object type in store.");
            }
        }
    }

    /* access modifiers changed from: protected */
    public void i(OutputStream out) {
        Enumeration e = this.c.elements();
        DataOutputStream dOut = new DataOutputStream(out);
        while (e.hasMoreElements()) {
            StoreEntry entry = (StoreEntry) e.nextElement();
            dOut.write(entry.f());
            dOut.writeUTF(entry.a());
            dOut.writeLong(entry.c().getTime());
            Certificate[] chain = entry.b();
            if (chain == null) {
                dOut.writeInt(0);
            } else {
                dOut.writeInt(chain.length);
                for (int i = 0; i != chain.length; i++) {
                    e(chain[i], dOut);
                }
            }
            switch (entry.f()) {
                case 1:
                    e((Certificate) entry.d(), dOut);
                    break;
                case 2:
                    f((Key) entry.d(), dOut);
                    break;
                case 3:
                case 4:
                    byte[] b = (byte[]) entry.d();
                    dOut.writeInt(b.length);
                    dOut.write(b);
                    break;
                default:
                    throw new RuntimeException("Unknown object type in store.");
            }
        }
        dOut.write(0);
    }

    public void engineLoad(InputStream stream, char[] password) {
        CipherParameters macParams;
        InputStream inputStream = stream;
        char[] cArr = password;
        this.c.clear();
        if (inputStream != null) {
            DataInputStream dIn = new DataInputStream(inputStream);
            int version = dIn.readInt();
            if (version == 2 || version == 0 || version == 1) {
                int saltLength = dIn.readInt();
                if (saltLength > 0) {
                    byte[] salt = new byte[saltLength];
                    dIn.readFully(salt);
                    int iterationCount = dIn.readInt();
                    HMac hMac = new HMac(new SHA1Digest());
                    if (cArr == null || cArr.length == 0) {
                        g(dIn);
                        dIn.readFully(new byte[hMac.e()]);
                        return;
                    }
                    byte[] passKey = PBEParametersGenerator.a(password);
                    PBEParametersGenerator pbeGen = new PKCS12ParametersGenerator(new SHA1Digest());
                    pbeGen.g(passKey, salt, iterationCount);
                    if (version != 2) {
                        macParams = pbeGen.d(hMac.e());
                    } else {
                        macParams = pbeGen.d(hMac.e() * 8);
                    }
                    Arrays.F(passKey, (byte) 0);
                    hMac.a(macParams);
                    g(new MacInputStream(dIn, hMac));
                    byte[] mac = new byte[hMac.e()];
                    hMac.c(mac, 0);
                    byte[] oldMac = new byte[hMac.e()];
                    dIn.readFully(oldMac);
                    if (!Arrays.u(mac, oldMac)) {
                        this.c.clear();
                        throw new IOException("KeyStore integrity check failed.");
                    }
                    return;
                }
                throw new IOException("Invalid salt detected");
            }
            throw new IOException("Wrong version of key store.");
        }
    }

    public void engineStore(OutputStream stream, char[] password) {
        DataOutputStream dOut = new DataOutputStream(stream);
        byte[] salt = new byte[20];
        int iterationCount = (this.d.nextInt() & 1023) + 1024;
        this.d.nextBytes(salt);
        dOut.writeInt(this.f);
        dOut.writeInt(salt.length);
        dOut.write(salt);
        dOut.writeInt(iterationCount);
        HMac hMac = new HMac(new SHA1Digest());
        MacOutputStream mOut = new MacOutputStream(hMac);
        PBEParametersGenerator pbeGen = new PKCS12ParametersGenerator(new SHA1Digest());
        byte[] passKey = PBEParametersGenerator.a(password);
        pbeGen.g(passKey, salt, iterationCount);
        if (this.f < 2) {
            hMac.a(pbeGen.d(hMac.e()));
        } else {
            hMac.a(pbeGen.d(hMac.e() * 8));
        }
        for (int i = 0; i != passKey.length; i++) {
            passKey[i] = 0;
        }
        i(new TeeOutputStream(dOut, mOut));
        byte[] mac = new byte[hMac.e()];
        hMac.c(mac, 0);
        dOut.write(mac);
        dOut.close();
    }

    public static class BouncyCastleStore extends BcKeyStoreSpi {
        public BouncyCastleStore() {
            super(1);
        }

        public void engineLoad(InputStream stream, char[] password) {
            String cipherAlg;
            this.c.clear();
            if (stream != null) {
                DataInputStream dIn = new DataInputStream(stream);
                int version = dIn.readInt();
                if (version == 2 || version == 0 || version == 1) {
                    byte[] salt = new byte[dIn.readInt()];
                    if (salt.length == 20) {
                        dIn.readFully(salt);
                        int iterationCount = dIn.readInt();
                        if (iterationCount < 0 || iterationCount > 65536) {
                            throw new IOException("Key store corrupted.");
                        }
                        if (version == 0) {
                            cipherAlg = "OldPBEWithSHAAndTwofish-CBC";
                        } else {
                            cipherAlg = "PBEWithSHAAndTwofish-CBC";
                        }
                        CipherInputStream cIn = new CipherInputStream(dIn, h(cipherAlg, 2, password, salt, iterationCount));
                        Digest dig = new SHA1Digest();
                        g(new DigestInputStream(cIn, dig));
                        byte[] hash = new byte[dig.e()];
                        dig.c(hash, 0);
                        byte[] oldHash = new byte[dig.e()];
                        Streams.c(cIn, oldHash);
                        if (!Arrays.u(hash, oldHash)) {
                            this.c.clear();
                            throw new IOException("KeyStore integrity check failed.");
                        }
                        return;
                    }
                    throw new IOException("Key store corrupted.");
                }
                throw new IOException("Wrong version of key store.");
            }
        }

        public void engineStore(OutputStream stream, char[] password) {
            DataOutputStream dOut = new DataOutputStream(stream);
            byte[] salt = new byte[20];
            int iterationCount = (this.d.nextInt() & 1023) + 1024;
            this.d.nextBytes(salt);
            dOut.writeInt(this.f);
            dOut.writeInt(salt.length);
            dOut.write(salt);
            dOut.writeInt(iterationCount);
            CipherOutputStream cOut = new CipherOutputStream(dOut, h("PBEWithSHAAndTwofish-CBC", 1, password, salt, iterationCount));
            DigestOutputStream dgOut = new DigestOutputStream(new SHA1Digest());
            i(new TeeOutputStream(cOut, dgOut));
            cOut.write(dgOut.a());
            cOut.close();
        }
    }

    public static class Std extends BcKeyStoreSpi {
        public Std() {
            super(2);
        }
    }

    public static class Version1 extends BcKeyStoreSpi {
        public Version1() {
            super(1);
        }
    }
}
