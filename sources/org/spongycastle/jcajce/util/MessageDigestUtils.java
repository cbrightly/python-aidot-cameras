package org.spongycastle.jcajce.util;

import java.util.HashMap;
import java.util.Map;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.gm.GMObjectIdentifiers;
import org.spongycastle.asn1.gnu.GNUObjectIdentifiers;
import org.spongycastle.asn1.iso.ISOIECObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;

public class MessageDigestUtils {
    private static Map<ASN1ObjectIdentifier, String> a;

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        hashMap.put(PKCSObjectIdentifiers.r0, "MD2");
        a.put(PKCSObjectIdentifiers.s0, "MD4");
        a.put(PKCSObjectIdentifiers.t0, "MD5");
        a.put(OIWObjectIdentifiers.i, "SHA-1");
        a.put(NISTObjectIdentifiers.f, "SHA-224");
        a.put(NISTObjectIdentifiers.c, "SHA-256");
        a.put(NISTObjectIdentifiers.d, "SHA-384");
        a.put(NISTObjectIdentifiers.e, "SHA-512");
        a.put(TeleTrusTObjectIdentifiers.c, "RIPEMD-128");
        a.put(TeleTrusTObjectIdentifiers.b, "RIPEMD-160");
        a.put(TeleTrusTObjectIdentifiers.d, "RIPEMD-128");
        a.put(ISOIECObjectIdentifiers.d, "RIPEMD-128");
        a.put(ISOIECObjectIdentifiers.c, "RIPEMD-160");
        a.put(CryptoProObjectIdentifiers.b, "GOST3411");
        a.put(GNUObjectIdentifiers.g, "Tiger");
        a.put(ISOIECObjectIdentifiers.e, "Whirlpool");
        a.put(NISTObjectIdentifiers.i, "SHA3-224");
        a.put(NISTObjectIdentifiers.j, "SHA3-256");
        a.put(NISTObjectIdentifiers.k, "SHA3-384");
        a.put(NISTObjectIdentifiers.l, "SHA3-512");
        a.put(GMObjectIdentifiers.b0, "SM3");
    }

    public static String a(ASN1ObjectIdentifier digestAlgOID) {
        String name = a.get(digestAlgOID);
        if (name != null) {
            return name;
        }
        return digestAlgOID.s();
    }
}
