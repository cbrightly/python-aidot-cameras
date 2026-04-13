package org.spongycastle.x509;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.RSASSAPSSparams;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;

public class X509Util {
    private static Hashtable a = new Hashtable();
    private static Hashtable b = new Hashtable();
    private static Set c = new HashSet();

    public static class Implementation {
    }

    X509Util() {
    }

    static {
        Hashtable hashtable = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.L;
        hashtable.put("MD2WITHRSAENCRYPTION", aSN1ObjectIdentifier);
        a.put("MD2WITHRSA", aSN1ObjectIdentifier);
        Hashtable hashtable2 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = PKCSObjectIdentifiers.N;
        hashtable2.put("MD5WITHRSAENCRYPTION", aSN1ObjectIdentifier2);
        a.put("MD5WITHRSA", aSN1ObjectIdentifier2);
        Hashtable hashtable3 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = PKCSObjectIdentifiers.O;
        hashtable3.put("SHA1WITHRSAENCRYPTION", aSN1ObjectIdentifier3);
        a.put("SHA1WITHRSA", aSN1ObjectIdentifier3);
        Hashtable hashtable4 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = PKCSObjectIdentifiers.X;
        hashtable4.put("SHA224WITHRSAENCRYPTION", aSN1ObjectIdentifier4);
        a.put("SHA224WITHRSA", aSN1ObjectIdentifier4);
        Hashtable hashtable5 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = PKCSObjectIdentifiers.U;
        hashtable5.put("SHA256WITHRSAENCRYPTION", aSN1ObjectIdentifier5);
        a.put("SHA256WITHRSA", aSN1ObjectIdentifier5);
        Hashtable hashtable6 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = PKCSObjectIdentifiers.V;
        hashtable6.put("SHA384WITHRSAENCRYPTION", aSN1ObjectIdentifier6);
        a.put("SHA384WITHRSA", aSN1ObjectIdentifier6);
        Hashtable hashtable7 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = PKCSObjectIdentifiers.W;
        hashtable7.put("SHA512WITHRSAENCRYPTION", aSN1ObjectIdentifier7);
        a.put("SHA512WITHRSA", aSN1ObjectIdentifier7);
        Hashtable hashtable8 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier8 = PKCSObjectIdentifiers.T;
        hashtable8.put("SHA1WITHRSAANDMGF1", aSN1ObjectIdentifier8);
        a.put("SHA224WITHRSAANDMGF1", aSN1ObjectIdentifier8);
        a.put("SHA256WITHRSAANDMGF1", aSN1ObjectIdentifier8);
        a.put("SHA384WITHRSAANDMGF1", aSN1ObjectIdentifier8);
        a.put("SHA512WITHRSAANDMGF1", aSN1ObjectIdentifier8);
        Hashtable hashtable9 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier9 = TeleTrusTObjectIdentifiers.f;
        hashtable9.put("RIPEMD160WITHRSAENCRYPTION", aSN1ObjectIdentifier9);
        a.put("RIPEMD160WITHRSA", aSN1ObjectIdentifier9);
        Hashtable hashtable10 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier10 = TeleTrusTObjectIdentifiers.g;
        hashtable10.put("RIPEMD128WITHRSAENCRYPTION", aSN1ObjectIdentifier10);
        a.put("RIPEMD128WITHRSA", aSN1ObjectIdentifier10);
        Hashtable hashtable11 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier11 = TeleTrusTObjectIdentifiers.h;
        hashtable11.put("RIPEMD256WITHRSAENCRYPTION", aSN1ObjectIdentifier11);
        a.put("RIPEMD256WITHRSA", aSN1ObjectIdentifier11);
        Hashtable hashtable12 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier12 = X9ObjectIdentifiers.e4;
        hashtable12.put("SHA1WITHDSA", aSN1ObjectIdentifier12);
        a.put("DSAWITHSHA1", aSN1ObjectIdentifier12);
        Hashtable hashtable13 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier13 = NISTObjectIdentifiers.T;
        hashtable13.put("SHA224WITHDSA", aSN1ObjectIdentifier13);
        Hashtable hashtable14 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier14 = NISTObjectIdentifiers.U;
        hashtable14.put("SHA256WITHDSA", aSN1ObjectIdentifier14);
        Hashtable hashtable15 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier15 = NISTObjectIdentifiers.V;
        hashtable15.put("SHA384WITHDSA", aSN1ObjectIdentifier15);
        Hashtable hashtable16 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier16 = NISTObjectIdentifiers.W;
        hashtable16.put("SHA512WITHDSA", aSN1ObjectIdentifier16);
        Hashtable hashtable17 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier17 = X9ObjectIdentifiers.r3;
        hashtable17.put("SHA1WITHECDSA", aSN1ObjectIdentifier17);
        a.put("ECDSAWITHSHA1", aSN1ObjectIdentifier17);
        Hashtable hashtable18 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier18 = X9ObjectIdentifiers.v3;
        hashtable18.put("SHA224WITHECDSA", aSN1ObjectIdentifier18);
        Hashtable hashtable19 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier19 = X9ObjectIdentifiers.w3;
        hashtable19.put("SHA256WITHECDSA", aSN1ObjectIdentifier19);
        Hashtable hashtable20 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier20 = X9ObjectIdentifiers.x3;
        hashtable20.put("SHA384WITHECDSA", aSN1ObjectIdentifier20);
        Hashtable hashtable21 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier21 = X9ObjectIdentifiers.y3;
        hashtable21.put("SHA512WITHECDSA", aSN1ObjectIdentifier21);
        Hashtable hashtable22 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier22 = CryptoProObjectIdentifiers.n;
        hashtable22.put("GOST3411WITHGOST3410", aSN1ObjectIdentifier22);
        a.put("GOST3411WITHGOST3410-94", aSN1ObjectIdentifier22);
        Hashtable hashtable23 = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier23 = CryptoProObjectIdentifiers.o;
        hashtable23.put("GOST3411WITHECGOST3410", aSN1ObjectIdentifier23);
        a.put("GOST3411WITHECGOST3410-2001", aSN1ObjectIdentifier23);
        a.put("GOST3411WITHGOST3410-2001", aSN1ObjectIdentifier23);
        c.add(aSN1ObjectIdentifier17);
        c.add(aSN1ObjectIdentifier18);
        c.add(aSN1ObjectIdentifier19);
        c.add(aSN1ObjectIdentifier20);
        c.add(aSN1ObjectIdentifier21);
        c.add(aSN1ObjectIdentifier12);
        c.add(aSN1ObjectIdentifier13);
        c.add(aSN1ObjectIdentifier14);
        c.add(aSN1ObjectIdentifier15);
        c.add(aSN1ObjectIdentifier16);
        c.add(aSN1ObjectIdentifier22);
        c.add(aSN1ObjectIdentifier23);
        ASN1ObjectIdentifier aSN1ObjectIdentifier24 = OIWObjectIdentifiers.i;
        DERNull dERNull = DERNull.c;
        b.put("SHA1WITHRSAANDMGF1", a(new AlgorithmIdentifier(aSN1ObjectIdentifier24, dERNull), 20));
        b.put("SHA224WITHRSAANDMGF1", a(new AlgorithmIdentifier(NISTObjectIdentifiers.f, dERNull), 28));
        Object obj = "SHA256WITHRSAANDMGF1";
        b.put(obj, a(new AlgorithmIdentifier(NISTObjectIdentifiers.c, dERNull), 32));
        Object obj2 = "SHA384WITHRSAANDMGF1";
        b.put(obj2, a(new AlgorithmIdentifier(NISTObjectIdentifiers.d, dERNull), 48));
        Object obj3 = "SHA512WITHRSAANDMGF1";
        b.put(obj3, a(new AlgorithmIdentifier(NISTObjectIdentifiers.e, dERNull), 64));
    }

    private static RSASSAPSSparams a(AlgorithmIdentifier hashAlgId, int saltSize) {
        return new RSASSAPSSparams(hashAlgId, new AlgorithmIdentifier(PKCSObjectIdentifiers.R, hashAlgId), new ASN1Integer((long) saltSize), new ASN1Integer(1));
    }
}
