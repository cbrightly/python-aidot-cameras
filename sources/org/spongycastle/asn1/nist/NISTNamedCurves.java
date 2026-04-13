package org.spongycastle.asn1.nist;

import java.util.Enumeration;
import java.util.Hashtable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.sec.SECObjectIdentifiers;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.util.Strings;

public class NISTNamedCurves {
    static final Hashtable a = new Hashtable();
    static final Hashtable b = new Hashtable();

    static {
        a("B-571", SECObjectIdentifiers.F);
        a("B-409", SECObjectIdentifiers.D);
        a("B-283", SECObjectIdentifiers.n);
        a("B-233", SECObjectIdentifiers.t);
        a("B-163", SECObjectIdentifiers.l);
        a("K-571", SECObjectIdentifiers.E);
        a("K-409", SECObjectIdentifiers.C);
        a("K-283", SECObjectIdentifiers.m);
        a("K-233", SECObjectIdentifiers.s);
        a("K-163", SECObjectIdentifiers.b);
        a("P-521", SECObjectIdentifiers.B);
        a("P-384", SECObjectIdentifiers.A);
        a("P-256", SECObjectIdentifiers.H);
        a("P-224", SECObjectIdentifiers.z);
        a("P-192", SECObjectIdentifiers.G);
    }

    static void a(String name, ASN1ObjectIdentifier oid) {
        a.put(name, oid);
        b.put(oid, name);
    }

    public static X9ECParameters b(String name) {
        ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) a.get(Strings.l(name));
        if (oid != null) {
            return c(oid);
        }
        return null;
    }

    public static X9ECParameters c(ASN1ObjectIdentifier oid) {
        return SECNamedCurves.i(oid);
    }

    public static ASN1ObjectIdentifier f(String name) {
        return (ASN1ObjectIdentifier) a.get(Strings.l(name));
    }

    public static String d(ASN1ObjectIdentifier oid) {
        return (String) b.get(oid);
    }

    public static Enumeration e() {
        return a.keys();
    }
}
