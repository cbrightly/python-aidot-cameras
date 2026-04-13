package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class X509Extensions extends ASN1Object {
    public static final ASN1ObjectIdentifier A4 = new ASN1ObjectIdentifier("2.5.29.31");
    public static final ASN1ObjectIdentifier B4 = new ASN1ObjectIdentifier("2.5.29.32");
    public static final ASN1ObjectIdentifier C4 = new ASN1ObjectIdentifier("2.5.29.33");
    public static final ASN1ObjectIdentifier D4 = new ASN1ObjectIdentifier("2.5.29.35");
    public static final ASN1ObjectIdentifier E4 = new ASN1ObjectIdentifier("2.5.29.36");
    public static final ASN1ObjectIdentifier F4 = new ASN1ObjectIdentifier("2.5.29.37");
    public static final ASN1ObjectIdentifier G4 = new ASN1ObjectIdentifier("2.5.29.46");
    public static final ASN1ObjectIdentifier H4 = new ASN1ObjectIdentifier("2.5.29.54");
    public static final ASN1ObjectIdentifier I4 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.1");
    public static final ASN1ObjectIdentifier J4 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.11");
    public static final ASN1ObjectIdentifier K4 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.12");
    public static final ASN1ObjectIdentifier L4 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.2");
    public static final ASN1ObjectIdentifier M4 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.3");
    public static final ASN1ObjectIdentifier N4 = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.4");
    public static final ASN1ObjectIdentifier O4 = new ASN1ObjectIdentifier("2.5.29.56");
    public static final ASN1ObjectIdentifier P4 = new ASN1ObjectIdentifier("2.5.29.55");
    public static final ASN1ObjectIdentifier a1 = new ASN1ObjectIdentifier("2.5.29.21");
    public static final ASN1ObjectIdentifier a2 = new ASN1ObjectIdentifier("2.5.29.24");
    public static final ASN1ObjectIdentifier c = new ASN1ObjectIdentifier("2.5.29.9");
    public static final ASN1ObjectIdentifier d = new ASN1ObjectIdentifier("2.5.29.14");
    public static final ASN1ObjectIdentifier f = new ASN1ObjectIdentifier("2.5.29.15");
    public static final ASN1ObjectIdentifier p0 = new ASN1ObjectIdentifier("2.5.29.20");
    public static final ASN1ObjectIdentifier p1 = new ASN1ObjectIdentifier("2.5.29.23");
    public static final ASN1ObjectIdentifier p2 = new ASN1ObjectIdentifier("2.5.29.27");
    public static final ASN1ObjectIdentifier p3 = new ASN1ObjectIdentifier("2.5.29.28");
    public static final ASN1ObjectIdentifier p4 = new ASN1ObjectIdentifier("2.5.29.29");
    public static final ASN1ObjectIdentifier q = new ASN1ObjectIdentifier("2.5.29.16");
    public static final ASN1ObjectIdentifier x = new ASN1ObjectIdentifier("2.5.29.17");
    public static final ASN1ObjectIdentifier y = new ASN1ObjectIdentifier("2.5.29.18");
    public static final ASN1ObjectIdentifier z = new ASN1ObjectIdentifier("2.5.29.19");
    public static final ASN1ObjectIdentifier z4 = new ASN1ObjectIdentifier("2.5.29.30");
    private Hashtable Q4 = new Hashtable();
    private Vector R4 = new Vector();

    public static X509Extensions e(Object obj) {
        if (obj == null || (obj instanceof X509Extensions)) {
            return (X509Extensions) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new X509Extensions((ASN1Sequence) obj);
        }
        if (obj instanceof Extensions) {
            return new X509Extensions((ASN1Sequence) ((Extensions) obj).toASN1Primitive());
        }
        if (obj instanceof ASN1TaggedObject) {
            return e(((ASN1TaggedObject) obj).q());
        }
        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public X509Extensions(ASN1Sequence seq) {
        Enumeration e = seq.s();
        while (e.hasMoreElements()) {
            ASN1Sequence s = ASN1Sequence.o(e.nextElement());
            if (s.size() == 3) {
                this.Q4.put(s.r(0), new X509Extension(ASN1Boolean.p(s.r(1)), ASN1OctetString.o(s.r(2))));
            } else if (s.size() == 2) {
                this.Q4.put(s.r(0), new X509Extension(false, ASN1OctetString.o(s.r(1))));
            } else {
                throw new IllegalArgumentException("Bad sequence size: " + s.size());
            }
            this.R4.addElement(s.r(0));
        }
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vec = new ASN1EncodableVector();
        Enumeration e = this.R4.elements();
        while (e.hasMoreElements()) {
            ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) e.nextElement();
            X509Extension ext = (X509Extension) this.Q4.get(oid);
            ASN1EncodableVector v = new ASN1EncodableVector();
            v.a(oid);
            if (ext.c()) {
                v.a(ASN1Boolean.q);
            }
            v.a(ext.b());
            vec.a(new DERSequence(v));
        }
        return new DERSequence(vec);
    }
}
