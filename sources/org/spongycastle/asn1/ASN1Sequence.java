package org.spongycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Iterable;

public abstract class ASN1Sequence extends ASN1Primitive implements Iterable<ASN1Encodable> {
    protected Vector c;

    public static ASN1Sequence o(Object obj) {
        if (obj == null || (obj instanceof ASN1Sequence)) {
            return (ASN1Sequence) obj;
        }
        if (obj instanceof ASN1SequenceParser) {
            return o(((ASN1SequenceParser) obj).toASN1Primitive());
        }
        if (obj instanceof byte[]) {
            try {
                return o(ASN1Primitive.h((byte[]) obj));
            } catch (IOException e) {
                throw new IllegalArgumentException("failed to construct sequence from byte[]: " + e.getMessage());
            }
        } else {
            if (obj instanceof ASN1Encodable) {
                ASN1Primitive primitive = ((ASN1Encodable) obj).toASN1Primitive();
                if (primitive instanceof ASN1Sequence) {
                    return (ASN1Sequence) primitive;
                }
            }
            throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
        }
    }

    public static ASN1Sequence p(ASN1TaggedObject obj, boolean explicit) {
        if (explicit) {
            if (obj.s()) {
                return o(obj.q().toASN1Primitive());
            }
            throw new IllegalArgumentException("object implicit - explicit expected.");
        } else if (obj.s()) {
            if (obj instanceof BERTaggedObject) {
                return new BERSequence((ASN1Encodable) obj.q());
            }
            return new DLSequence((ASN1Encodable) obj.q());
        } else if (obj.q() instanceof ASN1Sequence) {
            return (ASN1Sequence) obj.q();
        } else {
            throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
        }
    }

    protected ASN1Sequence() {
        this.c = new Vector();
    }

    protected ASN1Sequence(ASN1Encodable obj) {
        Vector vector = new Vector();
        this.c = vector;
        vector.addElement(obj);
    }

    protected ASN1Sequence(ASN1EncodableVector v) {
        this.c = new Vector();
        for (int i = 0; i != v.c(); i++) {
            this.c.addElement(v.b(i));
        }
    }

    protected ASN1Sequence(ASN1Encodable[] array) {
        this.c = new Vector();
        for (int i = 0; i != array.length; i++) {
            this.c.addElement(array[i]);
        }
    }

    public ASN1Encodable[] t() {
        ASN1Encodable[] values = new ASN1Encodable[size()];
        for (int i = 0; i != size(); i++) {
            values[i] = r(i);
        }
        return values;
    }

    public Enumeration s() {
        return this.c.elements();
    }

    /* renamed from: org.spongycastle.asn1.ASN1Sequence$1  reason: invalid class name */
    public class AnonymousClass1 implements ASN1SequenceParser {
        final /* synthetic */ ASN1Sequence c;

        public ASN1Primitive d() {
            return this.c;
        }

        public ASN1Primitive toASN1Primitive() {
            return this.c;
        }
    }

    public ASN1Encodable r(int index) {
        return (ASN1Encodable) this.c.elementAt(index);
    }

    public int size() {
        return this.c.size();
    }

    public int hashCode() {
        Enumeration e = s();
        int hashCode = size();
        while (e.hasMoreElements()) {
            hashCode = (hashCode * 17) ^ q(e).hashCode();
        }
        return hashCode;
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof ASN1Sequence)) {
            return false;
        }
        ASN1Sequence other = (ASN1Sequence) o;
        if (size() != other.size()) {
            return false;
        }
        Enumeration s1 = s();
        Enumeration s2 = other.s();
        while (s1.hasMoreElements()) {
            ASN1Encodable obj1 = q(s1);
            ASN1Encodable obj2 = q(s2);
            ASN1Primitive o1 = obj1.toASN1Primitive();
            ASN1Primitive o2 = obj2.toASN1Primitive();
            if (o1 != o2 && !o1.equals(o2)) {
                return false;
            }
        }
        return true;
    }

    private ASN1Encodable q(Enumeration e) {
        return (ASN1Encodable) e.nextElement();
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive k() {
        ASN1Sequence derSeq = new DERSequence();
        derSeq.c = this.c;
        return derSeq;
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive n() {
        ASN1Sequence dlSeq = new DLSequence();
        dlSeq.c = this.c;
        return dlSeq;
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        return true;
    }

    public String toString() {
        return this.c.toString();
    }

    public Iterator<ASN1Encodable> iterator() {
        return new Arrays.Iterator(t());
    }
}
