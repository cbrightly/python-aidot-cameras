package org.spongycastle.asn1.anssi;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECParametersHolder;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.util.Strings;
import org.spongycastle.util.encoders.Hex;

public class ANSSINamedCurves {
    static X9ECParametersHolder a = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters a() {
            BigInteger p = ANSSINamedCurves.e("F1FD178C0B3AD58F10126DE8CE42435B3961ADBCABC8CA6DE8FCF353D86E9C03");
            BigInteger a = ANSSINamedCurves.e("F1FD178C0B3AD58F10126DE8CE42435B3961ADBCABC8CA6DE8FCF353D86E9C00");
            BigInteger b = ANSSINamedCurves.e("EE353FCA5428A9300D4ABA754A44C00FDFEC0C9AE4B1A1803075ED967B7BB73F");
            BigInteger n = ANSSINamedCurves.e("F1FD178C0B3AD58F10126DE8CE42435B53DC67E140D2BF941FFDD459C6D655E1");
            BigInteger h = BigInteger.valueOf(1);
            ECCurve curve = ANSSINamedCurves.c(new ECCurve.Fp(p, a, b, n, h));
            return new X9ECParameters(curve, new X9ECPoint(curve, Hex.a("04B6B3D4C356C139EB31183D4749D423958C27D2DCAF98B70164C97A2DD98F5CFF6142E0F7C8B204911F9271F0F3ECEF8C2701C307E8E4C9E183115A1554062CFB")), n, h, (byte[]) null);
        }
    };
    static final Hashtable b = new Hashtable();
    static final Hashtable c = new Hashtable();
    static final Hashtable d = new Hashtable();

    /* access modifiers changed from: private */
    public static ECCurve c(ECCurve curve) {
        return curve;
    }

    /* access modifiers changed from: private */
    public static BigInteger e(String hex) {
        return new BigInteger(1, Hex.a(hex));
    }

    static {
        d("FRP256v1", ANSSIObjectIdentifiers.a, a);
    }

    static void d(String name, ASN1ObjectIdentifier oid, X9ECParametersHolder holder) {
        b.put(Strings.h(name), oid);
        d.put(oid, name);
        c.put(oid, holder);
    }

    public static X9ECParameters f(String name) {
        ASN1ObjectIdentifier oid = j(name);
        if (oid == null) {
            return null;
        }
        return g(oid);
    }

    public static X9ECParameters g(ASN1ObjectIdentifier oid) {
        X9ECParametersHolder holder = (X9ECParametersHolder) c.get(oid);
        if (holder == null) {
            return null;
        }
        return holder.b();
    }

    public static ASN1ObjectIdentifier j(String name) {
        return (ASN1ObjectIdentifier) b.get(Strings.h(name));
    }

    public static String h(ASN1ObjectIdentifier oid) {
        return (String) d.get(oid);
    }

    public static Enumeration i() {
        return d.elements();
    }
}
