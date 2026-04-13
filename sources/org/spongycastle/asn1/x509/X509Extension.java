package org.spongycastle.asn1.x509;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;

public class X509Extension {
    public static final ASN1ObjectIdentifier A = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.2");
    public static final ASN1ObjectIdentifier B = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.3");
    public static final ASN1ObjectIdentifier C = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.4");
    public static final ASN1ObjectIdentifier D = new ASN1ObjectIdentifier("2.5.29.56");
    public static final ASN1ObjectIdentifier E = new ASN1ObjectIdentifier("2.5.29.55");
    public static final ASN1ObjectIdentifier a = new ASN1ObjectIdentifier("2.5.29.9");
    public static final ASN1ObjectIdentifier b = new ASN1ObjectIdentifier("2.5.29.14");
    public static final ASN1ObjectIdentifier c = new ASN1ObjectIdentifier("2.5.29.15");
    public static final ASN1ObjectIdentifier d = new ASN1ObjectIdentifier("2.5.29.16");
    public static final ASN1ObjectIdentifier e = new ASN1ObjectIdentifier("2.5.29.17");
    public static final ASN1ObjectIdentifier f = new ASN1ObjectIdentifier("2.5.29.18");
    public static final ASN1ObjectIdentifier g = new ASN1ObjectIdentifier("2.5.29.19");
    public static final ASN1ObjectIdentifier h = new ASN1ObjectIdentifier("2.5.29.20");
    public static final ASN1ObjectIdentifier i = new ASN1ObjectIdentifier("2.5.29.21");
    public static final ASN1ObjectIdentifier j = new ASN1ObjectIdentifier("2.5.29.23");
    public static final ASN1ObjectIdentifier k = new ASN1ObjectIdentifier("2.5.29.24");
    public static final ASN1ObjectIdentifier l = new ASN1ObjectIdentifier("2.5.29.27");
    public static final ASN1ObjectIdentifier m = new ASN1ObjectIdentifier("2.5.29.28");
    public static final ASN1ObjectIdentifier n = new ASN1ObjectIdentifier("2.5.29.29");
    public static final ASN1ObjectIdentifier o = new ASN1ObjectIdentifier("2.5.29.30");
    public static final ASN1ObjectIdentifier p = new ASN1ObjectIdentifier("2.5.29.31");
    public static final ASN1ObjectIdentifier q = new ASN1ObjectIdentifier("2.5.29.32");
    public static final ASN1ObjectIdentifier r = new ASN1ObjectIdentifier("2.5.29.33");
    public static final ASN1ObjectIdentifier s = new ASN1ObjectIdentifier("2.5.29.35");
    public static final ASN1ObjectIdentifier t = new ASN1ObjectIdentifier("2.5.29.36");
    public static final ASN1ObjectIdentifier u = new ASN1ObjectIdentifier("2.5.29.37");
    public static final ASN1ObjectIdentifier v = new ASN1ObjectIdentifier("2.5.29.46");
    public static final ASN1ObjectIdentifier w = new ASN1ObjectIdentifier("2.5.29.54");
    public static final ASN1ObjectIdentifier x = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.1");
    public static final ASN1ObjectIdentifier y = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.11");
    public static final ASN1ObjectIdentifier z = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.12");
    boolean F;
    ASN1OctetString G;

    public X509Extension(ASN1Boolean critical, ASN1OctetString value) {
        this.F = critical.s();
        this.G = value;
    }

    public X509Extension(boolean critical, ASN1OctetString value) {
        this.F = critical;
        this.G = value;
    }

    public boolean c() {
        return this.F;
    }

    public ASN1OctetString b() {
        return this.G;
    }

    public int hashCode() {
        if (c()) {
            return b().hashCode();
        }
        return ~b().hashCode();
    }

    public boolean equals(Object o2) {
        if (!(o2 instanceof X509Extension)) {
            return false;
        }
        X509Extension other = (X509Extension) o2;
        if (!other.b().equals(b()) || other.c() != c()) {
            return false;
        }
        return true;
    }

    public static ASN1Primitive a(X509Extension ext) {
        try {
            return ASN1Primitive.h(ext.b().q());
        } catch (IOException e2) {
            throw new IllegalArgumentException("can't convert extension: " + e2);
        }
    }
}
