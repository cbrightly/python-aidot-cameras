package io.ktor.util.collections;

import io.ktor.util.s;
import java.util.Collection;
import java.util.Iterator;
import kotlin.jvm.internal.f;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.markers.b;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: ConcurrentCollection.kt */
public class a<E> implements Collection<E>, b {
    private final Collection<E> c;
    private final s d;

    public Object[] toArray() {
        return f.a(this);
    }

    public <T> T[] toArray(T[] tArr) {
        return f.b(this, tArr);
    }

    public a(@NotNull Collection<E> delegate, @NotNull s lock) {
        k.f(delegate, "delegate");
        k.f(lock, "lock");
        this.c = delegate;
        this.d = lock;
    }

    public final /* bridge */ int size() {
        return a();
    }

    public int a() {
        s $this$withLock$iv = this.d;
        try {
            $this$withLock$iv.a();
            return this.c.size();
        } finally {
            $this$withLock$iv.b();
        }
    }

    public boolean contains(Object element) {
        s $this$withLock$iv = this.d;
        try {
            $this$withLock$iv.a();
            return this.c.contains(element);
        } finally {
            $this$withLock$iv.b();
        }
    }

    public boolean containsAll(@NotNull Collection<? extends Object> elements) {
        k.f(elements, "elements");
        s $this$withLock$iv = this.d;
        try {
            $this$withLock$iv.a();
            return this.c.containsAll(elements);
        } finally {
            $this$withLock$iv.b();
        }
    }

    public boolean isEmpty() {
        s $this$withLock$iv = this.d;
        try {
            $this$withLock$iv.a();
            return this.c.isEmpty();
        } finally {
            $this$withLock$iv.b();
        }
    }

    public boolean add(E element) {
        s $this$withLock$iv = this.d;
        try {
            $this$withLock$iv.a();
            return this.c.add(element);
        } finally {
            $this$withLock$iv.b();
        }
    }

    public boolean addAll(@NotNull Collection<? extends E> elements) {
        k.f(elements, "elements");
        s $this$withLock$iv = this.d;
        try {
            $this$withLock$iv.a();
            return this.c.addAll(elements);
        } finally {
            $this$withLock$iv.b();
        }
    }

    public void clear() {
        s $this$withLock$iv = this.d;
        try {
            $this$withLock$iv.a();
            this.c.clear();
            x xVar = x.a;
        } finally {
            $this$withLock$iv.b();
        }
    }

    @NotNull
    public Iterator<E> iterator() {
        return this.c.iterator();
    }

    public boolean remove(Object element) {
        s $this$withLock$iv = this.d;
        try {
            $this$withLock$iv.a();
            return this.c.remove(element);
        } finally {
            $this$withLock$iv.b();
        }
    }

    public boolean removeAll(@NotNull Collection<? extends Object> elements) {
        k.f(elements, "elements");
        s $this$withLock$iv = this.d;
        try {
            $this$withLock$iv.a();
            return this.c.removeAll(elements);
        } finally {
            $this$withLock$iv.b();
        }
    }

    public boolean retainAll(@NotNull Collection<? extends Object> elements) {
        k.f(elements, "elements");
        s $this$withLock$iv = this.d;
        try {
            $this$withLock$iv.a();
            return this.c.retainAll(elements);
        } finally {
            $this$withLock$iv.b();
        }
    }
}
