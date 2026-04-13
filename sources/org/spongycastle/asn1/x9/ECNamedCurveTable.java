package org.spongycastle.asn1.x9;

import java.util.Enumeration;
import java.util.Vector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.anssi.ANSSINamedCurves;
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.spongycastle.asn1.gm.GMNamedCurves;
import org.spongycastle.asn1.nist.NISTNamedCurves;
import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.teletrust.TeleTrusTNamedCurves;

public class ECNamedCurveTable {
    public static X9ECParameters b(String name) {
        X9ECParameters ecP = X962NamedCurves.b(name);
        if (ecP == null) {
            ecP = SECNamedCurves.h(name);
        }
        if (ecP == null) {
            ecP = NISTNamedCurves.b(name);
        }
        if (ecP == null) {
            ecP = TeleTrusTNamedCurves.d(name);
        }
        if (ecP == null) {
            ecP = ANSSINamedCurves.f(name);
        }
        if (ecP == null) {
            return GMNamedCurves.f(name);
        }
        return ecP;
    }

    public static ASN1ObjectIdentifier f(String name) {
        ASN1ObjectIdentifier oid = X962NamedCurves.f(name);
        if (oid == null) {
            oid = SECNamedCurves.l(name);
        }
        if (oid == null) {
            oid = NISTNamedCurves.f(name);
        }
        if (oid == null) {
            oid = TeleTrusTNamedCurves.h(name);
        }
        if (oid == null) {
            oid = ANSSINamedCurves.j(name);
        }
        if (oid == null) {
            return GMNamedCurves.j(name);
        }
        return oid;
    }

    public static String d(ASN1ObjectIdentifier oid) {
        String name = NISTNamedCurves.d(oid);
        if (name == null) {
            name = SECNamedCurves.j(oid);
        }
        if (name == null) {
            name = TeleTrusTNamedCurves.f(oid);
        }
        if (name == null) {
            name = X962NamedCurves.d(oid);
        }
        if (name == null) {
            name = ECGOST3410NamedCurves.c(oid);
        }
        if (name == null) {
            return GMNamedCurves.h(oid);
        }
        return name;
    }

    public static X9ECParameters c(ASN1ObjectIdentifier oid) {
        X9ECParameters ecP = X962NamedCurves.c(oid);
        if (ecP == null) {
            ecP = SECNamedCurves.i(oid);
        }
        if (ecP == null) {
            ecP = TeleTrusTNamedCurves.e(oid);
        }
        if (ecP == null) {
            ecP = ANSSINamedCurves.g(oid);
        }
        if (ecP == null) {
            return GMNamedCurves.g(oid);
        }
        return ecP;
    }

    public static Enumeration e() {
        Vector v = new Vector();
        a(v, X962NamedCurves.e());
        a(v, SECNamedCurves.k());
        a(v, NISTNamedCurves.e());
        a(v, TeleTrusTNamedCurves.g());
        a(v, ANSSINamedCurves.i());
        a(v, GMNamedCurves.i());
        return v.elements();
    }

    private static void a(Vector v, Enumeration e) {
        while (e.hasMoreElements()) {
            v.addElement(e.nextElement());
        }
    }
}
