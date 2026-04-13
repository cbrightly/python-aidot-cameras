package org.spongycastle.asn1.gm;

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

public class GMNamedCurves {
    static X9ECParametersHolder a = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters a() {
            BigInteger p = GMNamedCurves.e("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF");
            BigInteger a = GMNamedCurves.e("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC");
            BigInteger b = GMNamedCurves.e("28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93");
            BigInteger n = GMNamedCurves.e("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123");
            BigInteger h = BigInteger.valueOf(1);
            ECCurve curve = GMNamedCurves.c(new ECCurve.Fp(p, a, b, n, h));
            return new X9ECParameters(curve, new X9ECPoint(curve, Hex.a("0432C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0")), n, h, (byte[]) null);
        }
    };
    static X9ECParametersHolder b = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters a() {
            BigInteger p = GMNamedCurves.e("BDB6F4FE3E8B1D9E0DA8C0D46F4C318CEFE4AFE3B6B8551F");
            BigInteger a = GMNamedCurves.e("BB8E5E8FBC115E139FE6A814FE48AAA6F0ADA1AA5DF91985");
            BigInteger b = GMNamedCurves.e("1854BEBDC31B21B7AEFC80AB0ECD10D5B1B3308E6DBF11C1");
            BigInteger n = GMNamedCurves.e("BDB6F4FE3E8B1D9E0DA8C0D40FC962195DFAE76F56564677");
            BigInteger h = BigInteger.valueOf(1);
            ECCurve curve = GMNamedCurves.c(new ECCurve.Fp(p, a, b, n, h));
            return new X9ECParameters(curve, new X9ECPoint(curve, Hex.a("044AD5F7048DE709AD51236DE65E4D4B482C836DC6E410664002BB3A02D4AAADACAE24817A4CA3A1B014B5270432DB27D2")), n, h, (byte[]) null);
        }
    };
    static final Hashtable c = new Hashtable();
    static final Hashtable d = new Hashtable();
    static final Hashtable e = new Hashtable();

    /* access modifiers changed from: private */
    public static ECCurve c(ECCurve curve) {
        return curve;
    }

    /* access modifiers changed from: private */
    public static BigInteger e(String hex) {
        return new BigInteger(1, Hex.a(hex));
    }

    static {
        d("wapip192v1", GMObjectIdentifiers.J, b);
        d("sm2p256v1", GMObjectIdentifiers.F, a);
    }

    static void d(String name, ASN1ObjectIdentifier oid, X9ECParametersHolder holder) {
        c.put(Strings.h(name), oid);
        e.put(oid, name);
        d.put(oid, holder);
    }

    public static X9ECParameters f(String name) {
        ASN1ObjectIdentifier oid = j(name);
        if (oid == null) {
            return null;
        }
        return g(oid);
    }

    public static X9ECParameters g(ASN1ObjectIdentifier oid) {
        X9ECParametersHolder holder = (X9ECParametersHolder) d.get(oid);
        if (holder == null) {
            return null;
        }
        return holder.b();
    }

    public static ASN1ObjectIdentifier j(String name) {
        return (ASN1ObjectIdentifier) c.get(Strings.h(name));
    }

    public static String h(ASN1ObjectIdentifier oid) {
        return (String) e.get(oid);
    }

    public static Enumeration i() {
        return e.elements();
    }
}
