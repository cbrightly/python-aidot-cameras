package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Sequences.kt */
public final class q<T, R> implements h<R> {
    /* access modifiers changed from: private */
    public final h<T> a;
    /* access modifiers changed from: private */
    public final l<T, R> b;

    /* compiled from: Sequences.kt */
    public static final class a implements Iterator<R>, kotlin.jvm.internal.markers.a {
        @NotNull
        private final Iterator<T> c;
        final /* synthetic */ q d;

        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        a(q this$0) {
            this.d = this$0;
            this.c = this$0.a.iterator();
        }

        public R next() {
            return this.d.b.invoke(this.c.next());
        }

        public boolean hasNext() {
            return this.c.hasNext();
        }
    }

    public q(@NotNull h<? extends T> sequence, @NotNull l<? super T, ? extends R> transformer) {
        k.e(sequence, "sequence");
        k.e(transformer, "transformer");
        this.a = sequence;
        this.b = transformer;
    }

    @NotNull
    public Iterator<R> iterator() {
        return new a(this);
    }

    @NotNull
    public final <E> h<E> d(@NotNull l<? super R, ? extends Iterator<? extends E>> iterator) {
        k.e(iterator, "iterator");
        return new f(this.a, this.b, iterator);
    }
}
