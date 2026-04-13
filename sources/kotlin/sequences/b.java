package kotlin.sequences;

import java.util.Iterator;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Sequences.kt */
public final class b<T> implements h<T>, c<T> {
    /* access modifiers changed from: private */
    public final h<T> a;
    /* access modifiers changed from: private */
    public final int b;

    public b(@NotNull h<? extends T> sequence, int count) {
        k.e(sequence, "sequence");
        this.a = sequence;
        this.b = count;
        if (!(count >= 0)) {
            throw new IllegalArgumentException(("count must be non-negative, but was " + count + '.').toString());
        }
    }

    @NotNull
    public h<T> a(int n) {
        b bVar;
        int n1 = this.b + n;
        if (n1 >= 0) {
            bVar = new b(this.a, n1);
        }
        return bVar;
    }

    /* compiled from: Sequences.kt */
    public static final class a implements Iterator<T>, kotlin.jvm.internal.markers.a {
        @NotNull
        private final Iterator<T> c;
        private int d;
        final /* synthetic */ b f;

        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        a(b this$0) {
            this.f = this$0;
            this.c = this$0.a.iterator();
            this.d = this$0.b;
        }

        private final void b() {
            while (this.d > 0 && this.c.hasNext()) {
                this.c.next();
                this.d--;
            }
        }

        public T next() {
            b();
            return this.c.next();
        }

        public boolean hasNext() {
            b();
            return this.c.hasNext();
        }
    }

    @NotNull
    public Iterator<T> iterator() {
        return new a(this);
    }
}
