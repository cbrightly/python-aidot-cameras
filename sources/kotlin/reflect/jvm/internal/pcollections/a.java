package kotlin.reflect.jvm.internal.pcollections;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: ConsPStack */
public final class a<E> implements Iterable<E> {
    private static final a<Object> c = new a<>();
    final E d;
    final a<E> f;
    /* access modifiers changed from: private */
    public final int q;

    public static <E> a<E> b() {
        return c;
    }

    private a() {
        this.q = 0;
        this.d = null;
        this.f = null;
    }

    private a(E first, a<E> rest) {
        this.d = first;
        this.f = rest;
        this.q = rest.q + 1;
    }

    public E get(int index) {
        if (index < 0 || index > this.q) {
            throw new IndexOutOfBoundsException();
        }
        try {
            return d(index).next();
        } catch (NoSuchElementException e) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }

    public Iterator<E> iterator() {
        return d(0);
    }

    public int size() {
        return this.q;
    }

    private Iterator<E> d(int index) {
        return new C0436a(h(index));
    }

    /* renamed from: kotlin.reflect.jvm.internal.pcollections.a$a  reason: collision with other inner class name */
    /* compiled from: ConsPStack */
    public static class C0436a<E> implements Iterator<E> {
        private a<E> c;

        public C0436a(a<E> first) {
            this.c = first;
        }

        public boolean hasNext() {
            return this.c.q > 0;
        }

        public E next() {
            a<E> aVar = this.c;
            E e = aVar.d;
            this.c = aVar.f;
            return e;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public a<E> g(E e) {
        return new a<>(e, this);
    }

    private a<E> f(Object e) {
        if (this.q == 0) {
            return this;
        }
        if (this.d.equals(e)) {
            return this.f;
        }
        ConsPStack<E> newRest = this.f.f(e);
        if (newRest == this.f) {
            return this;
        }
        return new a<>(this.d, newRest);
    }

    public a<E> e(int i) {
        return f(get(i));
    }

    private a<E> h(int start) {
        if (start < 0 || start > this.q) {
            throw new IndexOutOfBoundsException();
        } else if (start == 0) {
            return this;
        } else {
            return this.f.h(start - 1);
        }
    }
}
