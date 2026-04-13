package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class Extensions extends ASN1Object {
    private Hashtable c = new Hashtable();
    private Vector d = new Vector();

    public static Extensions g(Object obj) {
        if (obj instanceof Extensions) {
            return (Extensions) obj;
        }
        if (obj != null) {
            return new Extensions(ASN1Sequence.o(obj));
        }
        return null;
    }

    private Extensions(ASN1Sequence seq) {
        Enumeration e = seq.s();
        while (e.hasMoreElements()) {
            Extension ext = Extension.h(e.nextElement());
            if (!this.c.containsKey(ext.f())) {
                this.c.put(ext.f(), ext);
                this.d.addElement(ext.f());
            } else {
                throw new IllegalArgumentException("repeated extension found: " + ext.f());
            }
        }
    }

    public Enumeration h() {
        return this.d.elements();
    }

    public Extension e(ASN1ObjectIdentifier oid) {
        return (Extension) this.c.get(oid);
    }

    public ASN1Encodable f(ASN1ObjectIdentifier oid) {
        Extension ext = e(oid);
        if (ext != null) {
            return ext.i();
        }
        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector vec = new ASN1EncodableVector();
        Enumeration e = this.d.elements();
        while (e.hasMoreElements()) {
            vec.a((Extension) this.c.get((ASN1ObjectIdentifier) e.nextElement()));
        }
        return new DERSequence(vec);
    }
}
