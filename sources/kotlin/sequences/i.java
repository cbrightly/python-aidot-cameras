package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.markers.a;
import kotlin.o;
import kotlin.p;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SequenceBuilder.kt */
public final class i<T> extends j<T> implements Iterator<T>, d<x>, a {
    private int c;
    private T d;
    private Iterator<? extends T> f;
    @Nullable
    private d<? super x> q;

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final void m(@Nullable d<? super x> dVar) {
        this.q = dVar;
    }

    public boolean hasNext() {
        while (true) {
            switch (this.c) {
                case 0:
                    break;
                case 1:
                    Iterator<? extends T> it = this.f;
                    k.c(it);
                    if (!it.hasNext()) {
                        this.f = null;
                        break;
                    } else {
                        this.c = 2;
                        return true;
                    }
                case 2:
                case 3:
                    return true;
                case 4:
                    return false;
                default:
                    throw k();
            }
            this.c = 5;
            d step = this.q;
            k.c(step);
            this.q = null;
            x xVar = x.a;
            o.a aVar = o.Companion;
            step.resumeWith(o.m17constructorimpl(xVar));
        }
    }

    public T next() {
        switch (this.c) {
            case 0:
            case 1:
                return l();
            case 2:
                this.c = 1;
                Iterator<? extends T> it = this.f;
                k.c(it);
                return it.next();
            case 3:
                this.c = 0;
                Object result = this.d;
                this.d = null;
                return result;
            default:
                throw k();
        }
    }

    private final T l() {
        if (hasNext()) {
            return next();
        }
        throw new NoSuchElementException();
    }

    private final Throwable k() {
        switch (this.c) {
            case 4:
                return new NoSuchElementException();
            case 5:
                return new IllegalStateException("Iterator has failed.");
            default:
                return new IllegalStateException("Unexpected state of the iterator: " + this.c);
        }
    }

    @Nullable
    public Object g(T value, @NotNull d<? super x> $completion) {
        this.d = value;
        this.c = 3;
        this.q = $completion;
        Object d2 = c.d();
        if (d2 == c.d()) {
            h.c($completion);
        }
        return d2 == c.d() ? d2 : x.a;
    }

    @Nullable
    public Object i(@NotNull Iterator<? extends T> iterator, @NotNull d<? super x> $completion) {
        if (!iterator.hasNext()) {
            return x.a;
        }
        this.f = iterator;
        this.c = 2;
        this.q = $completion;
        Object d2 = c.d();
        if (d2 == c.d()) {
            h.c($completion);
        }
        return d2 == c.d() ? d2 : x.a;
    }

    public void resumeWith(@NotNull Object result) {
        p.b(result);
        this.c = 4;
    }

    @NotNull
    public g getContext() {
        return kotlin.coroutines.h.INSTANCE;
    }
}
