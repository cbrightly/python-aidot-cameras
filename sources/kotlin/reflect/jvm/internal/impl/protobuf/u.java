package kotlin.reflect.jvm.internal.impl.protobuf;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* compiled from: UnmodifiableLazyStringList */
public class u extends AbstractList<String> implements RandomAccess, m {
    /* access modifiers changed from: private */
    public final m c;

    public u(m list) {
        this.c = list;
    }

    /* renamed from: b */
    public String get(int index) {
        return (String) this.c.get(index);
    }

    public int size() {
        return this.c.size();
    }

    public d l(int index) {
        return this.c.l(index);
    }

    public void m(d element) {
        throw new UnsupportedOperationException();
    }

    /* compiled from: UnmodifiableLazyStringList */
    public class a implements ListIterator<String> {
        ListIterator<String> c;
        final /* synthetic */ int d;

        a(int i) {
            this.d = i;
            this.c = u.this.c.listIterator(i);
        }

        public boolean hasNext() {
            return this.c.hasNext();
        }

        /* renamed from: c */
        public String next() {
            return this.c.next();
        }

        public boolean hasPrevious() {
            return this.c.hasPrevious();
        }

        /* renamed from: d */
        public String previous() {
            return this.c.previous();
        }

        public int nextIndex() {
            return this.c.nextIndex();
        }

        public int previousIndex() {
            return this.c.previousIndex();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        /* renamed from: e */
        public void set(String o) {
            throw new UnsupportedOperationException();
        }

        /* renamed from: b */
        public void add(String o) {
            throw new UnsupportedOperationException();
        }
    }

    public ListIterator<String> listIterator(int index) {
        return new a(index);
    }

    /* compiled from: UnmodifiableLazyStringList */
    public class b implements Iterator<String> {
        Iterator<String> c;

        b() {
            this.c = u.this.c.iterator();
        }

        public boolean hasNext() {
            return this.c.hasNext();
        }

        /* renamed from: b */
        public String next() {
            return this.c.next();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<String> iterator() {
        return new b();
    }

    public List<?> j() {
        return this.c.j();
    }

    public m u0() {
        return this;
    }
}
