package org.spongycastle.asn1.x509;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class Extension extends ASN1Object {
    public static final ASN1ObjectIdentifier A4 = new ASN1ObjectIdentifier("2.5.29.31").v();
    public static final ASN1ObjectIdentifier B4 = new ASN1ObjectIdentifier("2.5.29.32").v();
    public static final ASN1ObjectIdentifier C4 = new ASN1ObjectIdentifier("2.5.29.33").v();
    public static final ASN1ObjectIdentifier D4 = new ASN1ObjectIdentifier("2.5.29.35").v();
    public static final ASN1ObjectIdentifier E4 = new ASN1ObjectIdentifier("2.5.29.36").v();
    public static final ASN1ObjectIdentifier F4 = new ASN1ObjectIdentifier("2.5.29.37").v();
    public static final ASN1ObjectIdentifier G4 = new ASN1ObjectIdentifier("2.5.29.46").v();
    public static final ASN1ObjectIdentifier H4 = new ASN1ObjectIdentifier("2.5.29.54").v();
    public static final ASN1ObjectIdentifier I4 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.1").v();
    public static final ASN1ObjectIdentifier J4 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.11").v();
    public static final ASN1ObjectIdentifier K4 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.12").v();
    public static final ASN1ObjectIdentifier L4 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.2").v();
    public static final ASN1ObjectIdentifier M4 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.3").v();
    public static final ASN1ObjectIdentifier N4 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.4").v();
    public static final ASN1ObjectIdentifier O4 = new ASN1ObjectIdentifier("2.5.29.56").v();
    public static final ASN1ObjectIdentifier P4 = new ASN1ObjectIdentifier("2.5.29.55").v();
    public static final ASN1ObjectIdentifier Q4 = new ASN1ObjectIdentifier("2.5.29.60").v();
    public static final ASN1ObjectIdentifier a1 = new ASN1ObjectIdentifier("2.5.29.21").v();
    public static final ASN1ObjectIdentifier a2 = new ASN1ObjectIdentifier("2.5.29.24").v();
    public static final ASN1ObjectIdentifier c = new ASN1ObjectIdentifier("2.5.29.9").v();
    public static final ASN1ObjectIdentifier d = new ASN1ObjectIdentifier("2.5.29.14").v();
    public static final ASN1ObjectIdentifier f = new ASN1ObjectIdentifier("2.5.29.15").v();
    public static final ASN1ObjectIdentifier p0 = new ASN1ObjectIdentifier("2.5.29.20").v();
    public static final ASN1ObjectIdentifier p1 = new ASN1ObjectIdentifier("2.5.29.23").v();
    public static final ASN1ObjectIdentifier p2 = new ASN1ObjectIdentifier("2.5.29.27").v();
    public static final ASN1ObjectIdentifier p3 = new ASN1ObjectIdentifier("2.5.29.28").v();
    public static final ASN1ObjectIdentifier p4 = new ASN1ObjectIdentifier("2.5.29.29").v();
    public static final ASN1ObjectIdentifier q = new ASN1ObjectIdentifier("2.5.29.16").v();
    public static final ASN1ObjectIdentifier x = new ASN1ObjectIdentifier("2.5.29.17").v();
    public static final ASN1ObjectIdentifier y = new ASN1ObjectIdentifier("2.5.29.18").v();
    public static final ASN1ObjectIdentifier z = new ASN1ObjectIdentifier("2.5.29.19").v();
    public static final ASN1ObjectIdentifier z4 = new ASN1ObjectIdentifier("2.5.29.30").v();
    private ASN1ObjectIdentifier R4;
    private boolean S4;
    private ASN1OctetString T4;

    private Extension(ASN1Sequence seq) {
        if (seq.size() == 2) {
            this.R4 = ASN1ObjectIdentifier.t(seq.r(0));
            this.S4 = false;
            this.T4 = ASN1OctetString.o(seq.r(1));
        } else if (seq.size() == 3) {
            this.R4 = ASN1ObjectIdentifier.t(seq.r(0));
            this.S4 = ASN1Boolean.p(seq.r(1)).s();
            this.T4 = ASN1OctetString.o(seq.r(2));
        } else {
            throw new IllegalArgumentException("Bad sequence size: " + seq.size());
        }
    }

    public static Extension h(Object obj) {
        if (obj instanceof Extension) {
            return (Extension) obj;
        }
        if (obj != null) {
            return new Extension(ASN1Sequence.o(obj));
        }
        return null;
    }

    public ASN1ObjectIdentifier f() {
        return this.R4;
    }

    public boolean k() {
        return this.S4;
    }

    public ASN1OctetString g() {
        return this.T4;
    }

    public ASN1Encodable i() {
        return e(this);
    }

    public int hashCode() {
        if (k()) {
            return g().hashCode() ^ f().hashCode();
        }
        return ~(g().hashCode() ^ f().hashCode());
    }

    public boolean equals(Object o) {
        if (!(o instanceof Extension)) {
            return false;
        }
        Extension other = (Extension) o;
        if (!other.f().equals(f()) || !other.g().equals(g()) || other.k() != k()) {
            return false;
        }
        return true;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.a(this.R4);
        if (this.S4) {
            v.a(ASN1Boolean.r(true));
        }
        v.a(this.T4);
        return new DERSequence(v);
    }

    private static ASN1Primitive e(Extension ext) {
        try {
            return ASN1Primitive.h(ext.g().q());
        } catch (IOException e) {
            throw new IllegalArgumentException("can't convert extension: " + e);
        }
    }
}
