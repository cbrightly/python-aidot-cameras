package org.spongycastle.jcajce.provider.asymmetric.util;

import com.alibaba.fastjson.asm.Opcodes;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.crypto.KeyAgreementSpi;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.gnu.GNUObjectIdentifiers;
import org.spongycastle.asn1.kisa.KISAObjectIdentifiers;
import org.spongycastle.asn1.misc.MiscObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.ntt.NTTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.agreement.kdf.DHKDFParameters;
import org.spongycastle.crypto.agreement.kdf.DHKEKGenerator;
import org.spongycastle.crypto.params.DESParameters;
import org.spongycastle.crypto.params.KDFParameters;
import org.spongycastle.util.Integers;
import org.spongycastle.util.Strings;

public abstract class BaseAgreementSpi extends KeyAgreementSpi {
    private static final Map<String, ASN1ObjectIdentifier> a;
    private static final Map<String, Integer> b;
    private static final Map<String, String> c;
    private static final Hashtable d = new Hashtable();
    private static final Hashtable e = new Hashtable();
    private final String f;
    private final DerivationFunction g;
    protected byte[] h;

    /* access modifiers changed from: protected */
    public abstract byte[] a();

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        HashMap hashMap2 = new HashMap();
        b = hashMap2;
        HashMap hashMap3 = new HashMap();
        c = hashMap3;
        Integer i64 = Integers.b(64);
        Integer i128 = Integers.b(128);
        Integer i192 = Integers.b(Opcodes.CHECKCAST);
        Integer i256 = Integers.b(256);
        hashMap2.put("DES", i64);
        hashMap2.put("DESEDE", i192);
        hashMap2.put("BLOWFISH", i128);
        hashMap2.put("AES", i256);
        hashMap2.put(NISTObjectIdentifiers.t.s(), i128);
        hashMap2.put(NISTObjectIdentifiers.B.s(), i192);
        hashMap2.put(NISTObjectIdentifiers.J.s(), i256);
        hashMap2.put(NISTObjectIdentifiers.u.s(), i128);
        hashMap2.put(NISTObjectIdentifiers.C.s(), i192);
        ASN1ObjectIdentifier aSN1ObjectIdentifier = NISTObjectIdentifiers.K;
        hashMap2.put(aSN1ObjectIdentifier.s(), i256);
        hashMap2.put(NISTObjectIdentifiers.w.s(), i128);
        hashMap2.put(NISTObjectIdentifiers.E.s(), i192);
        hashMap2.put(NISTObjectIdentifiers.M.s(), i256);
        hashMap2.put(NISTObjectIdentifiers.v.s(), i128);
        hashMap2.put(NISTObjectIdentifiers.D.s(), i192);
        hashMap2.put(NISTObjectIdentifiers.L.s(), i256);
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = NISTObjectIdentifiers.x;
        hashMap2.put(aSN1ObjectIdentifier2.s(), i128);
        hashMap2.put(NISTObjectIdentifiers.F.s(), i192);
        hashMap2.put(NISTObjectIdentifiers.N.s(), i256);
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = NISTObjectIdentifiers.z;
        hashMap2.put(aSN1ObjectIdentifier3.s(), i128);
        hashMap2.put(NISTObjectIdentifiers.H.s(), i192);
        hashMap2.put(NISTObjectIdentifiers.P.s(), i256);
        hashMap2.put(NISTObjectIdentifiers.y.s(), i128);
        hashMap2.put(NISTObjectIdentifiers.G.s(), i192);
        hashMap2.put(NISTObjectIdentifiers.O.s(), i256);
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = NTTObjectIdentifiers.d;
        hashMap2.put(aSN1ObjectIdentifier4.s(), i128);
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = NTTObjectIdentifiers.e;
        hashMap2.put(aSN1ObjectIdentifier5.s(), i192);
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = NTTObjectIdentifiers.f;
        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = aSN1ObjectIdentifier3;
        hashMap2.put(aSN1ObjectIdentifier6.s(), i256);
        ASN1ObjectIdentifier aSN1ObjectIdentifier8 = KISAObjectIdentifiers.d;
        ASN1ObjectIdentifier aSN1ObjectIdentifier9 = aSN1ObjectIdentifier2;
        hashMap2.put(aSN1ObjectIdentifier8.s(), i128);
        ASN1ObjectIdentifier aSN1ObjectIdentifier10 = PKCSObjectIdentifiers.y2;
        Integer num = i128;
        hashMap2.put(aSN1ObjectIdentifier10.s(), i192);
        ASN1ObjectIdentifier aSN1ObjectIdentifier11 = PKCSObjectIdentifiers.m0;
        ASN1ObjectIdentifier aSN1ObjectIdentifier12 = aSN1ObjectIdentifier8;
        hashMap2.put(aSN1ObjectIdentifier11.s(), i192);
        ASN1ObjectIdentifier aSN1ObjectIdentifier13 = OIWObjectIdentifiers.e;
        Integer num2 = i192;
        hashMap2.put(aSN1ObjectIdentifier13.s(), i64);
        ASN1ObjectIdentifier aSN1ObjectIdentifier14 = CryptoProObjectIdentifiers.f;
        Integer num3 = i64;
        hashMap2.put(aSN1ObjectIdentifier14.s(), i256);
        hashMap2.put(CryptoProObjectIdentifiers.d.s(), i256);
        hashMap2.put(CryptoProObjectIdentifiers.e.s(), i256);
        ASN1ObjectIdentifier aSN1ObjectIdentifier15 = PKCSObjectIdentifiers.u0;
        ASN1ObjectIdentifier aSN1ObjectIdentifier16 = aSN1ObjectIdentifier14;
        ASN1ObjectIdentifier aSN1ObjectIdentifier17 = aSN1ObjectIdentifier6;
        hashMap2.put(aSN1ObjectIdentifier15.s(), Integers.b(Opcodes.IF_ICMPNE));
        ASN1ObjectIdentifier aSN1ObjectIdentifier18 = PKCSObjectIdentifiers.w0;
        hashMap2.put(aSN1ObjectIdentifier18.s(), i256);
        ASN1ObjectIdentifier aSN1ObjectIdentifier19 = PKCSObjectIdentifiers.x0;
        Integer num4 = i256;
        hashMap2.put(aSN1ObjectIdentifier19.s(), Integers.b(384));
        ASN1ObjectIdentifier aSN1ObjectIdentifier20 = PKCSObjectIdentifiers.y0;
        ASN1ObjectIdentifier aSN1ObjectIdentifier21 = aSN1ObjectIdentifier4;
        hashMap2.put(aSN1ObjectIdentifier20.s(), Integers.b(512));
        hashMap.put("DESEDE", aSN1ObjectIdentifier11);
        hashMap.put("AES", aSN1ObjectIdentifier);
        ASN1ObjectIdentifier aSN1ObjectIdentifier22 = NTTObjectIdentifiers.c;
        hashMap.put("CAMELLIA", aSN1ObjectIdentifier22);
        ASN1ObjectIdentifier aSN1ObjectIdentifier23 = KISAObjectIdentifiers.a;
        hashMap.put("SEED", aSN1ObjectIdentifier23);
        hashMap.put("DES", aSN1ObjectIdentifier13);
        hashMap3.put(MiscObjectIdentifiers.u.s(), "CAST5");
        hashMap3.put(MiscObjectIdentifiers.v.s(), "IDEA");
        hashMap3.put(MiscObjectIdentifiers.y.s(), "Blowfish");
        hashMap3.put(MiscObjectIdentifiers.z.s(), "Blowfish");
        hashMap3.put(MiscObjectIdentifiers.A.s(), "Blowfish");
        hashMap3.put(MiscObjectIdentifiers.B.s(), "Blowfish");
        hashMap3.put(OIWObjectIdentifiers.d.s(), "DES");
        hashMap3.put(aSN1ObjectIdentifier13.s(), "DES");
        hashMap3.put(OIWObjectIdentifiers.g.s(), "DES");
        hashMap3.put(OIWObjectIdentifiers.f.s(), "DES");
        hashMap3.put(OIWObjectIdentifiers.h.s(), "DESede");
        hashMap3.put(aSN1ObjectIdentifier11.s(), "DESede");
        hashMap3.put(aSN1ObjectIdentifier10.s(), "DESede");
        hashMap3.put(PKCSObjectIdentifiers.z2.s(), "RC2");
        Map<String, String> map = c;
        map.put(aSN1ObjectIdentifier15.s(), "HmacSHA1");
        map.put(PKCSObjectIdentifiers.v0.s(), "HmacSHA224");
        map.put(aSN1ObjectIdentifier18.s(), "HmacSHA256");
        map.put(aSN1ObjectIdentifier19.s(), "HmacSHA384");
        map.put(aSN1ObjectIdentifier20.s(), "HmacSHA512");
        map.put(NTTObjectIdentifiers.a.s(), "Camellia");
        map.put(NTTObjectIdentifiers.b.s(), "Camellia");
        map.put(aSN1ObjectIdentifier22.s(), "Camellia");
        map.put(aSN1ObjectIdentifier21.s(), "Camellia");
        map.put(aSN1ObjectIdentifier5.s(), "Camellia");
        map.put(aSN1ObjectIdentifier17.s(), "Camellia");
        map.put(aSN1ObjectIdentifier12.s(), "SEED");
        map.put(aSN1ObjectIdentifier23.s(), "SEED");
        map.put(KISAObjectIdentifiers.b.s(), "SEED");
        map.put(aSN1ObjectIdentifier16.s(), "GOST28147");
        map.put(aSN1ObjectIdentifier9.s(), "AES");
        map.put(aSN1ObjectIdentifier7.s(), "AES");
        map.put(aSN1ObjectIdentifier7.s(), "AES");
        Hashtable hashtable = d;
        hashtable.put("DESEDE", aSN1ObjectIdentifier11);
        hashtable.put("AES", aSN1ObjectIdentifier);
        hashtable.put("DES", aSN1ObjectIdentifier13);
        Hashtable hashtable2 = e;
        hashtable2.put("DES", "DES");
        hashtable2.put("DESEDE", "DES");
        hashtable2.put(aSN1ObjectIdentifier13.s(), "DES");
        hashtable2.put(aSN1ObjectIdentifier11.s(), "DES");
        hashtable2.put(aSN1ObjectIdentifier10.s(), "DES");
    }

    public BaseAgreementSpi(String kaAlgorithm, DerivationFunction kdf) {
        this.f = kaAlgorithm;
        this.g = kdf;
    }

    protected static String b(String algDetails) {
        if (algDetails.indexOf(91) > 0) {
            return algDetails.substring(0, algDetails.indexOf(91));
        }
        if (algDetails.startsWith(NISTObjectIdentifiers.s.s())) {
            return "AES";
        }
        if (algDetails.startsWith(GNUObjectIdentifiers.i.s())) {
            return "Serpent";
        }
        String name = c.get(Strings.l(algDetails));
        if (name != null) {
            return name;
        }
        return algDetails;
    }

    protected static int c(String algDetails) {
        if (algDetails.indexOf(91) > 0) {
            return Integer.parseInt(algDetails.substring(algDetails.indexOf(91) + 1, algDetails.indexOf(93)));
        }
        String algKey = Strings.l(algDetails);
        Map<String, Integer> map = b;
        if (!map.containsKey(algKey)) {
            return -1;
        }
        return map.get(algKey).intValue();
    }

    protected static byte[] d(byte[] secret) {
        if (secret[0] != 0) {
            return secret;
        }
        int ind = 0;
        while (ind < secret.length && secret[ind] == 0) {
            ind++;
        }
        byte[] rv = new byte[(secret.length - ind)];
        System.arraycopy(secret, ind, rv, 0, rv.length);
        return rv;
    }

    /* access modifiers changed from: protected */
    public byte[] engineGenerateSecret() {
        if (this.g == null) {
            return a();
        }
        throw new UnsupportedOperationException("KDF can only be used when algorithm is known");
    }

    /* access modifiers changed from: protected */
    public int engineGenerateSecret(byte[] sharedSecret, int offset) {
        byte[] secret = engineGenerateSecret();
        if (sharedSecret.length - offset >= secret.length) {
            System.arraycopy(secret, 0, sharedSecret, offset, secret.length);
            return secret.length;
        }
        throw new ShortBufferException(this.f + " key agreement: need " + secret.length + " bytes");
    }

    /* access modifiers changed from: protected */
    public SecretKey engineGenerateSecret(String algorithm) {
        byte[] secret = a();
        String algKey = Strings.l(algorithm);
        String oidAlgorithm = algorithm;
        Hashtable hashtable = d;
        if (hashtable.containsKey(algKey)) {
            oidAlgorithm = ((ASN1ObjectIdentifier) hashtable.get(algKey)).s();
        }
        int keySize = c(oidAlgorithm);
        DerivationFunction derivationFunction = this.g;
        if (derivationFunction != null) {
            if (keySize >= 0) {
                byte[] keyBytes = new byte[(keySize / 8)];
                if (derivationFunction instanceof DHKEKGenerator) {
                    try {
                        this.g.b(new DHKDFParameters(new ASN1ObjectIdentifier(oidAlgorithm), keySize, secret, this.h));
                    } catch (IllegalArgumentException e2) {
                        throw new NoSuchAlgorithmException("no OID for algorithm: " + oidAlgorithm);
                    }
                } else {
                    this.g.b(new KDFParameters(secret, this.h));
                }
                this.g.a(keyBytes, 0, keyBytes.length);
                secret = keyBytes;
            } else {
                throw new NoSuchAlgorithmException("unknown algorithm encountered: " + oidAlgorithm);
            }
        } else if (keySize > 0) {
            byte[] keyBytes2 = new byte[(keySize / 8)];
            System.arraycopy(secret, 0, keyBytes2, 0, keyBytes2.length);
            secret = keyBytes2;
        }
        String algName = b(algorithm);
        if (e.containsKey(algName)) {
            DESParameters.c(secret);
        }
        return new SecretKeySpec(secret, algName);
    }
}
