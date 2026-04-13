package kotlin.reflect.jvm.internal.impl.protobuf;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: LazyStringArrayList */
public class l extends AbstractList<String> implements RandomAccess, m {
    public static final m c = new l().u0();
    private final List<Object> d;

    public l() {
        this.d = new ArrayList();
    }

    public l(m from) {
        this.d = new ArrayList(from.size());
        addAll(from);
    }

    /* renamed from: e */
    public String get(int index) {
        Object o = this.d.get(index);
        if (o instanceof String) {
            return (String) o;
        }
        if (o instanceof d) {
            d bs = (d) o;
            String s = bs.x();
            if (bs.o()) {
                this.d.set(index, s);
            }
            return s;
        }
        byte[] ba = (byte[]) o;
        String s2 = i.b(ba);
        if (i.a(ba)) {
            this.d.set(index, s2);
        }
        return s2;
    }

    public int size() {
        return this.d.size();
    }

    /* renamed from: g */
    public String set(int index, String s) {
        return d(this.d.set(index, s));
    }

    /* renamed from: a */
    public void add(int index, String element) {
        this.d.add(index, element);
        this.modCount++;
    }

    public boolean addAll(Collection<? extends String> c2) {
        return addAll(size(), c2);
    }

    public boolean addAll(int index, Collection<? extends String> c2) {
        boolean ret = this.d.addAll(index, c2 instanceof m ? ((m) c2).j() : c2);
        this.modCount++;
        return ret;
    }

    /* renamed from: f */
    public String remove(int index) {
        Object o = this.d.remove(index);
        this.modCount++;
        return d(o);
    }

    public void clear() {
        this.d.clear();
        this.modCount++;
    }

    public void m(d element) {
        this.d.add(element);
        this.modCount++;
    }

    public d l(int index) {
        Object o = this.d.get(index);
        d b = b(o);
        if (b != o) {
            this.d.set(index, b);
        }
        return b;
    }

    private static String d(Object o) {
        if (o instanceof String) {
            return (String) o;
        }
        if (o instanceof d) {
            return ((d) o).x();
        }
        return i.b((byte[]) o);
    }

    private static d b(Object o) {
        if (o instanceof d) {
            return (d) o;
        }
        if (o instanceof String) {
            return d.g((String) o);
        }
        return d.e((byte[]) o);
    }

    public List<?> j() {
        return Collections.unmodifiableList(this.d);
    }

    public m u0() {
        return new u(this);
    }
}
