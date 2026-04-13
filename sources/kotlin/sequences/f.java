package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Sequences.kt */
public final class f<T, R, E> implements h<E> {
    /* access modifiers changed from: private */
    public final h<T> a;
    /* access modifiers changed from: private */
    public final l<T, R> b;
    /* access modifiers changed from: private */
    public final l<R, Iterator<E>> c;

    public f(@NotNull h<? extends T> sequence, @NotNull l<? super T, ? extends R> transformer, @NotNull l<? super R, ? extends Iterator<? extends E>> iterator) {
        k.e(sequence, "sequence");
        k.e(transformer, "transformer");
        k.e(iterator, "iterator");
        this.a = sequence;
        this.b = transformer;
        this.c = iterator;
    }

    /* compiled from: Sequences.kt */
    public static final class a implements Iterator<E>, kotlin.jvm.internal.markers.a {
        @NotNull
        private final Iterator<T> c;
        @Nullable
        private Iterator<? extends E> d;
        final /* synthetic */ f f;

        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        a(f this$0) {
            this.f = this$0;
            this.c = this$0.a.iterator();
        }

        public E next() {
            if (b()) {
                Iterator<? extends E> it = this.d;
                k.c(it);
                return it.next();
            }
            throw new NoSuchElementException();
        }

        public boolean hasNext() {
            return b();
        }

        private final boolean b() {
            Iterator<? extends E> it = this.d;
            if (it != null && !it.hasNext()) {
                this.d = null;
            }
            while (this.d == null) {
                if (!this.c.hasNext()) {
                    return false;
                }
                Iterator nextItemIterator = (Iterator) this.f.c.invoke(this.f.b.invoke(this.c.next()));
                if (nextItemIterator.hasNext()) {
                    this.d = nextItemIterator;
                    return true;
                }
            }
            return true;
        }
    }

    @NotNull
    public Iterator<E> iterator() {
        return new a(this);
    }
}
