package org.spongycastle.asn1.x500;

import java.util.Vector;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x500.style.BCStyle;

public class X500NameBuilder {
    private X500NameStyle a;
    private Vector b;

    public X500NameBuilder() {
        this(BCStyle.M);
    }

    public X500NameBuilder(X500NameStyle template) {
        this.b = new Vector();
        this.a = template;
    }

    public X500NameBuilder d(ASN1ObjectIdentifier oid, String value) {
        e(oid, this.a.e(oid, value));
        return this;
    }

    public X500NameBuilder e(ASN1ObjectIdentifier oid, ASN1Encodable value) {
        this.b.addElement(new RDN(oid, value));
        return this;
    }

    public X500NameBuilder a(ASN1ObjectIdentifier[] oids, String[] values) {
        ASN1Encodable[] vals = new ASN1Encodable[values.length];
        for (int i = 0; i != vals.length; i++) {
            vals[i] = this.a.e(oids[i], values[i]);
        }
        return b(oids, vals);
    }

    public X500NameBuilder b(ASN1ObjectIdentifier[] oids, ASN1Encodable[] values) {
        AttributeTypeAndValue[] avs = new AttributeTypeAndValue[oids.length];
        for (int i = 0; i != oids.length; i++) {
            avs[i] = new AttributeTypeAndValue(oids[i], values[i]);
        }
        return c(avs);
    }

    public X500NameBuilder c(AttributeTypeAndValue[] attrTAndVs) {
        this.b.addElement(new RDN(attrTAndVs));
        return this;
    }

    public X500Name f() {
        RDN[] vals = new RDN[this.b.size()];
        for (int i = 0; i != vals.length; i++) {
            vals[i] = (RDN) this.b.elementAt(i);
        }
        return new X500Name(this.a, vals);
    }
}
