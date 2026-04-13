package org.spongycastle.asn1.x500;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x500.style.BCStyle;

public class X500Name extends ASN1Object implements ASN1Choice {
    private static X500NameStyle c = BCStyle.M;
    private boolean d;
    private int f;
    private X500NameStyle q;
    private RDN[] x;

    public X500Name(X500NameStyle style, X500Name name) {
        this.x = name.x;
        this.q = style;
    }

    public static X500Name f(ASN1TaggedObject obj, boolean explicit) {
        return e(ASN1Sequence.p(obj, true));
    }

    public static X500Name e(Object obj) {
        if (obj instanceof X500Name) {
            return (X500Name) obj;
        }
        if (obj != null) {
            return new X500Name(ASN1Sequence.o(obj));
        }
        return null;
    }

    public static X500Name g(X500NameStyle style, Object obj) {
        if (obj instanceof X500Name) {
            return new X500Name(style, (X500Name) obj);
        }
        if (obj != null) {
            return new X500Name(style, ASN1Sequence.o(obj));
        }
        return null;
    }

    private X500Name(ASN1Sequence seq) {
        this(c, seq);
    }

    private X500Name(X500NameStyle style, ASN1Sequence seq) {
        this.q = style;
        this.x = new RDN[seq.size()];
        int index = 0;
        Enumeration e = seq.s();
        while (e.hasMoreElements()) {
            this.x[index] = RDN.f(e.nextElement());
            index++;
        }
    }

    public X500Name(RDN[] rDNs) {
        this(c, rDNs);
    }

    public X500Name(X500NameStyle style, RDN[] rDNs) {
        this.x = rDNs;
        this.q = style;
    }

    public X500Name(String dirName) {
        this(c, dirName);
    }

    public X500Name(X500NameStyle style, String dirName) {
        this(style.b(dirName));
        this.q = style;
    }

    public RDN[] h() {
        RDN[] rdnArr = this.x;
        RDN[] tmp = new RDN[rdnArr.length];
        System.arraycopy(rdnArr, 0, tmp, 0, tmp.length);
        return tmp;
    }

    public RDN[] i(ASN1ObjectIdentifier attributeType) {
        RDN[] res = new RDN[this.x.length];
        int count = 0;
        int i = 0;
        while (true) {
            RDN[] rdnArr = this.x;
            if (i != rdnArr.length) {
                RDN rdn = rdnArr[i];
                if (rdn.h()) {
                    AttributeTypeAndValue[] attr = rdn.g();
                    int j = 0;
                    while (true) {
                        if (j == attr.length) {
                            break;
                        } else if (attr[j].f().equals(attributeType)) {
                            res[count] = rdn;
                            count++;
                            break;
                        } else {
                            j++;
                        }
                    }
                } else if (rdn.e().f().equals(attributeType)) {
                    res[count] = rdn;
                    count++;
                }
                i++;
            } else {
                RDN[] tmp = new RDN[count];
                System.arraycopy(res, 0, tmp, 0, tmp.length);
                return tmp;
            }
        }
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence((ASN1Encodable[]) this.x);
    }

    public int hashCode() {
        if (this.d) {
            return this.f;
        }
        this.d = true;
        int f2 = this.q.f(this);
        this.f = f2;
        return f2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof X500Name) && !(obj instanceof ASN1Sequence)) {
            return false;
        }
        if (toASN1Primitive().equals(((ASN1Encodable) obj).toASN1Primitive())) {
            return true;
        }
        try {
            return this.q.d(this, new X500Name(ASN1Sequence.o(((ASN1Encodable) obj).toASN1Primitive())));
        } catch (Exception e) {
            return false;
        }
    }

    public String toString() {
        return this.q.a(this);
    }
}
