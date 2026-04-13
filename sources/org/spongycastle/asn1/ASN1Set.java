package org.spongycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Iterable;

public abstract class ASN1Set extends ASN1Primitive implements Iterable<ASN1Encodable> {
    private Vector c;
    private boolean d;

    public static ASN1Set p(Object obj) {
        if (obj == null || (obj instanceof ASN1Set)) {
            return (ASN1Set) obj;
        }
        if (obj instanceof ASN1SetParser) {
            return p(((ASN1SetParser) obj).toASN1Primitive());
        }
        if (obj instanceof byte[]) {
            try {
                return p(ASN1Primitive.h((byte[]) obj));
            } catch (IOException e) {
                throw new IllegalArgumentException("failed to construct set from byte[]: " + e.getMessage());
            }
        } else {
            if (obj instanceof ASN1Encodable) {
                ASN1Primitive primitive = ((ASN1Encodable) obj).toASN1Primitive();
                if (primitive instanceof ASN1Set) {
                    return (ASN1Set) primitive;
                }
            }
            throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
        }
    }

    public static ASN1Set q(ASN1TaggedObject obj, boolean explicit) {
        if (explicit) {
            if (obj.s()) {
                return (ASN1Set) obj.q();
            }
            throw new IllegalArgumentException("object implicit - explicit expected.");
        } else if (obj.s()) {
            if (obj instanceof BERTaggedObject) {
                return new BERSet((ASN1Encodable) obj.q());
            }
            return new DLSet((ASN1Encodable) obj.q());
        } else if (obj.q() instanceof ASN1Set) {
            return (ASN1Set) obj.q();
        } else {
            if (obj.q() instanceof ASN1Sequence) {
                ASN1Sequence s = (ASN1Sequence) obj.q();
                if (obj instanceof BERTaggedObject) {
                    return new BERSet(s.t());
                }
                return new DLSet(s.t());
            }
            throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
        }
    }

    protected ASN1Set() {
        this.c = new Vector();
        this.d = false;
    }

    protected ASN1Set(ASN1Encodable obj) {
        Vector vector = new Vector();
        this.c = vector;
        this.d = false;
        vector.addElement(obj);
    }

    protected ASN1Set(ASN1EncodableVector v, boolean doSort) {
        this.c = new Vector();
        this.d = false;
        for (int i = 0; i != v.c(); i++) {
            this.c.addElement(v.b(i));
        }
        if (doSort) {
            v();
        }
    }

    protected ASN1Set(ASN1Encodable[] array, boolean doSort) {
        this.c = new Vector();
        this.d = false;
        for (int i = 0; i != array.length; i++) {
            this.c.addElement(array[i]);
        }
        if (doSort) {
            v();
        }
    }

    public Enumeration t() {
        return this.c.elements();
    }

    public ASN1Encodable s(int index) {
        return (ASN1Encodable) this.c.elementAt(index);
    }

    public int size() {
        return this.c.size();
    }

    public ASN1Encodable[] w() {
        ASN1Encodable[] values = new ASN1Encodable[size()];
        for (int i = 0; i != size(); i++) {
            values[i] = s(i);
        }
        return values;
    }

    /* renamed from: org.spongycastle.asn1.ASN1Set$1  reason: invalid class name */
    public class AnonymousClass1 implements ASN1SetParser {
        final /* synthetic */ ASN1Set c;

        public ASN1Primitive d() {
            return this.c;
        }

        public ASN1Primitive toASN1Primitive() {
            return this.c;
        }
    }

    public int hashCode() {
        Enumeration e = t();
        int hashCode = size();
        while (e.hasMoreElements()) {
            hashCode = (hashCode * 17) ^ r(e).hashCode();
        }
        return hashCode;
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive k() {
        if (this.d) {
            ASN1Set derSet = new DERSet();
            derSet.c = this.c;
            return derSet;
        }
        Vector v = new Vector();
        for (int i = 0; i != this.c.size(); i++) {
            v.addElement(this.c.elementAt(i));
        }
        ASN1Set derSet2 = new DERSet();
        derSet2.c = v;
        derSet2.v();
        return derSet2;
    }

    /* access modifiers changed from: package-private */
    public ASN1Primitive n() {
        ASN1Set derSet = new DLSet();
        derSet.c = this.c;
        return derSet;
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof ASN1Set)) {
            return false;
        }
        ASN1Set other = (ASN1Set) o;
        if (size() != other.size()) {
            return false;
        }
        Enumeration s1 = t();
        Enumeration s2 = other.t();
        while (s1.hasMoreElements()) {
            ASN1Encodable obj1 = r(s1);
            ASN1Encodable obj2 = r(s2);
            ASN1Primitive o1 = obj1.toASN1Primitive();
            ASN1Primitive o2 = obj2.toASN1Primitive();
            if (o1 != o2 && !o1.equals(o2)) {
                return false;
            }
        }
        return true;
    }

    private ASN1Encodable r(Enumeration e) {
        ASN1Encodable encObj = (ASN1Encodable) e.nextElement();
        if (encObj == null) {
            return DERNull.c;
        }
        return encObj;
    }

    private boolean u(byte[] a, byte[] b) {
        int len = Math.min(a.length, b.length);
        int i = 0;
        while (i != len) {
            if (a[i] == b[i]) {
                i++;
            } else if ((a[i] & 255) < (b[i] & 255)) {
                return true;
            } else {
                return false;
            }
        }
        if (len == a.length) {
            return true;
        }
        return false;
    }

    private byte[] o(ASN1Encodable obj) {
        try {
            return obj.toASN1Primitive().getEncoded("DER");
        } catch (IOException e) {
            throw new IllegalArgumentException("cannot encode object added to SET");
        }
    }

    /* access modifiers changed from: protected */
    public void v() {
        if (!this.d) {
            this.d = true;
            if (this.c.size() > 1) {
                boolean swapped = true;
                int lastSwap = this.c.size() - 1;
                while (swapped) {
                    int swapIndex = 0;
                    byte[] a = o((ASN1Encodable) this.c.elementAt(0));
                    swapped = false;
                    for (int index = 0; index != lastSwap; index++) {
                        byte[] b = o((ASN1Encodable) this.c.elementAt(index + 1));
                        if (u(a, b)) {
                            a = b;
                        } else {
                            Object o = this.c.elementAt(index);
                            Vector vector = this.c;
                            vector.setElementAt(vector.elementAt(index + 1), index);
                            this.c.setElementAt(o, index + 1);
                            swapped = true;
                            swapIndex = index;
                        }
                    }
                    lastSwap = swapIndex;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        return true;
    }

    public String toString() {
        return this.c.toString();
    }

    public Iterator<ASN1Encodable> iterator() {
        return new Arrays.Iterator(w());
    }
}
