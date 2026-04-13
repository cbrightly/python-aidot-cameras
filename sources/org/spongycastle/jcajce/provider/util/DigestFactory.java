package org.spongycastle.jcajce.provider.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.crypto.Digest;
import org.spongycastle.util.Strings;

public class DigestFactory {
    private static Set a = new HashSet();
    private static Set b = new HashSet();
    private static Set c = new HashSet();
    private static Set d = new HashSet();
    private static Set e = new HashSet();
    private static Set f = new HashSet();
    private static Set g = new HashSet();
    private static Set h = new HashSet();
    private static Set i = new HashSet();
    private static Set j = new HashSet();
    private static Set k = new HashSet();
    private static Set l = new HashSet();
    private static Map m = new HashMap();

    static {
        a.add("MD5");
        Set set = a;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.t0;
        set.add(aSN1ObjectIdentifier.s());
        b.add("SHA1");
        b.add("SHA-1");
        Set set2 = b;
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = OIWObjectIdentifiers.i;
        set2.add(aSN1ObjectIdentifier2.s());
        c.add("SHA224");
        c.add("SHA-224");
        Set set3 = c;
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = NISTObjectIdentifiers.f;
        set3.add(aSN1ObjectIdentifier3.s());
        d.add("SHA256");
        d.add("SHA-256");
        Set set4 = d;
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = NISTObjectIdentifiers.c;
        set4.add(aSN1ObjectIdentifier4.s());
        e.add("SHA384");
        e.add("SHA-384");
        Set set5 = e;
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = NISTObjectIdentifiers.d;
        set5.add(aSN1ObjectIdentifier5.s());
        f.add("SHA512");
        f.add("SHA-512");
        Set set6 = f;
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = NISTObjectIdentifiers.e;
        set6.add(aSN1ObjectIdentifier6.s());
        g.add("SHA512(224)");
        g.add("SHA-512(224)");
        Set set7 = g;
        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = NISTObjectIdentifiers.g;
        ASN1ObjectIdentifier aSN1ObjectIdentifier8 = aSN1ObjectIdentifier6;
        set7.add(aSN1ObjectIdentifier7.s());
        h.add("SHA512(256)");
        h.add("SHA-512(256)");
        Set set8 = h;
        ASN1ObjectIdentifier aSN1ObjectIdentifier9 = NISTObjectIdentifiers.h;
        ASN1ObjectIdentifier aSN1ObjectIdentifier10 = aSN1ObjectIdentifier7;
        set8.add(aSN1ObjectIdentifier9.s());
        i.add("SHA3-224");
        Set set9 = i;
        ASN1ObjectIdentifier aSN1ObjectIdentifier11 = NISTObjectIdentifiers.i;
        ASN1ObjectIdentifier aSN1ObjectIdentifier12 = aSN1ObjectIdentifier9;
        set9.add(aSN1ObjectIdentifier11.s());
        j.add("SHA3-256");
        Set set10 = j;
        ASN1ObjectIdentifier aSN1ObjectIdentifier13 = NISTObjectIdentifiers.j;
        ASN1ObjectIdentifier aSN1ObjectIdentifier14 = aSN1ObjectIdentifier11;
        set10.add(aSN1ObjectIdentifier13.s());
        k.add("SHA3-384");
        Set set11 = k;
        ASN1ObjectIdentifier aSN1ObjectIdentifier15 = NISTObjectIdentifiers.k;
        ASN1ObjectIdentifier aSN1ObjectIdentifier16 = aSN1ObjectIdentifier13;
        set11.add(aSN1ObjectIdentifier15.s());
        l.add("SHA3-512");
        Set set12 = l;
        ASN1ObjectIdentifier aSN1ObjectIdentifier17 = NISTObjectIdentifiers.l;
        set12.add(aSN1ObjectIdentifier17.s());
        m.put("MD5", aSN1ObjectIdentifier);
        m.put(aSN1ObjectIdentifier.s(), aSN1ObjectIdentifier);
        m.put("SHA1", aSN1ObjectIdentifier2);
        m.put("SHA-1", aSN1ObjectIdentifier2);
        m.put(aSN1ObjectIdentifier2.s(), aSN1ObjectIdentifier2);
        m.put("SHA224", aSN1ObjectIdentifier3);
        m.put("SHA-224", aSN1ObjectIdentifier3);
        m.put(aSN1ObjectIdentifier3.s(), aSN1ObjectIdentifier3);
        m.put("SHA256", aSN1ObjectIdentifier4);
        m.put("SHA-256", aSN1ObjectIdentifier4);
        m.put(aSN1ObjectIdentifier4.s(), aSN1ObjectIdentifier4);
        m.put("SHA384", aSN1ObjectIdentifier5);
        m.put("SHA-384", aSN1ObjectIdentifier5);
        m.put(aSN1ObjectIdentifier5.s(), aSN1ObjectIdentifier5);
        ASN1ObjectIdentifier aSN1ObjectIdentifier18 = aSN1ObjectIdentifier8;
        m.put("SHA512", aSN1ObjectIdentifier18);
        m.put("SHA-512", aSN1ObjectIdentifier18);
        m.put(aSN1ObjectIdentifier18.s(), aSN1ObjectIdentifier18);
        ASN1ObjectIdentifier aSN1ObjectIdentifier19 = aSN1ObjectIdentifier10;
        m.put("SHA512(224)", aSN1ObjectIdentifier19);
        m.put("SHA-512(224)", aSN1ObjectIdentifier19);
        m.put(aSN1ObjectIdentifier19.s(), aSN1ObjectIdentifier19);
        ASN1ObjectIdentifier aSN1ObjectIdentifier20 = aSN1ObjectIdentifier12;
        m.put("SHA512(256)", aSN1ObjectIdentifier20);
        m.put("SHA-512(256)", aSN1ObjectIdentifier20);
        m.put(aSN1ObjectIdentifier20.s(), aSN1ObjectIdentifier20);
        ASN1ObjectIdentifier aSN1ObjectIdentifier21 = aSN1ObjectIdentifier14;
        m.put("SHA3-224", aSN1ObjectIdentifier21);
        m.put(aSN1ObjectIdentifier21.s(), aSN1ObjectIdentifier21);
        ASN1ObjectIdentifier aSN1ObjectIdentifier22 = aSN1ObjectIdentifier16;
        m.put("SHA3-256", aSN1ObjectIdentifier22);
        m.put(aSN1ObjectIdentifier22.s(), aSN1ObjectIdentifier22);
        ASN1ObjectIdentifier aSN1ObjectIdentifier23 = aSN1ObjectIdentifier15;
        m.put("SHA3-384", aSN1ObjectIdentifier23);
        m.put(aSN1ObjectIdentifier23.s(), aSN1ObjectIdentifier23);
        m.put("SHA3-512", aSN1ObjectIdentifier17);
        m.put(aSN1ObjectIdentifier17.s(), aSN1ObjectIdentifier17);
    }

    public static Digest a(String digestName) {
        String digestName2 = Strings.l(digestName);
        if (b.contains(digestName2)) {
            return org.spongycastle.crypto.util.DigestFactory.b();
        }
        if (a.contains(digestName2)) {
            return org.spongycastle.crypto.util.DigestFactory.a();
        }
        if (c.contains(digestName2)) {
            return org.spongycastle.crypto.util.DigestFactory.c();
        }
        if (d.contains(digestName2)) {
            return org.spongycastle.crypto.util.DigestFactory.d();
        }
        if (e.contains(digestName2)) {
            return org.spongycastle.crypto.util.DigestFactory.e();
        }
        if (f.contains(digestName2)) {
            return org.spongycastle.crypto.util.DigestFactory.j();
        }
        if (g.contains(digestName2)) {
            return org.spongycastle.crypto.util.DigestFactory.k();
        }
        if (h.contains(digestName2)) {
            return org.spongycastle.crypto.util.DigestFactory.l();
        }
        if (i.contains(digestName2)) {
            return org.spongycastle.crypto.util.DigestFactory.f();
        }
        if (j.contains(digestName2)) {
            return org.spongycastle.crypto.util.DigestFactory.g();
        }
        if (k.contains(digestName2)) {
            return org.spongycastle.crypto.util.DigestFactory.h();
        }
        if (l.contains(digestName2)) {
            return org.spongycastle.crypto.util.DigestFactory.i();
        }
        return null;
    }

    public static boolean c(String digest1, String digest2) {
        return (b.contains(digest1) && b.contains(digest2)) || (c.contains(digest1) && c.contains(digest2)) || ((d.contains(digest1) && d.contains(digest2)) || ((e.contains(digest1) && e.contains(digest2)) || ((f.contains(digest1) && f.contains(digest2)) || ((g.contains(digest1) && g.contains(digest2)) || ((h.contains(digest1) && h.contains(digest2)) || ((i.contains(digest1) && i.contains(digest2)) || ((j.contains(digest1) && j.contains(digest2)) || ((k.contains(digest1) && k.contains(digest2)) || ((l.contains(digest1) && l.contains(digest2)) || (a.contains(digest1) && a.contains(digest2)))))))))));
    }

    public static ASN1ObjectIdentifier b(String digestName) {
        return (ASN1ObjectIdentifier) m.get(digestName);
    }
}
