package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class NameConstraints extends ASN1Object {
    private GeneralSubtree[] c;
    private GeneralSubtree[] d;

    public static NameConstraints h(Object obj) {
        if (obj instanceof NameConstraints) {
            return (NameConstraints) obj;
        }
        if (obj != null) {
            return new NameConstraints(ASN1Sequence.o(obj));
        }
        return null;
    }

    private NameConstraints(ASN1Sequence seq) {
        Enumeration e = seq.s();
        while (e.hasMoreElements()) {
            ASN1TaggedObject o = ASN1TaggedObject.o(e.nextElement());
            switch (o.r()) {
                case 0:
                    this.c = f(ASN1Sequence.p(o, false));
                    break;
                case 1:
                    this.d = f(ASN1Sequence.p(o, false));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown tag encountered: " + o.r());
            }
        }
    }

    private GeneralSubtree[] f(ASN1Sequence subtree) {
        GeneralSubtree[] ar = new GeneralSubtree[subtree.size()];
        for (int i = 0; i != ar.length; i++) {
            ar[i] = GeneralSubtree.f(subtree.r(i));
        }
        return ar;
    }

    public GeneralSubtree[] i() {
        return e(this.c);
    }

    public GeneralSubtree[] g() {
        return e(this.d);
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        if (this.c != null) {
            v.a(new DERTaggedObject(false, 0, new DERSequence((ASN1Encodable[]) this.c)));
        }
        if (this.d != null) {
            v.a(new DERTaggedObject(false, 1, new DERSequence((ASN1Encodable[]) this.d)));
        }
        return new DERSequence(v);
    }

    private static GeneralSubtree[] e(GeneralSubtree[] subtrees) {
        if (subtrees == null) {
            return null;
        }
        GeneralSubtree[] rv = new GeneralSubtree[subtrees.length];
        System.arraycopy(subtrees, 0, rv, 0, rv.length);
        return rv;
    }
}
