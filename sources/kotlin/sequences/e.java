package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Sequences.kt */
public final class e<T> implements h<T> {
    /* access modifiers changed from: private */
    public final h<T> a;
    /* access modifiers changed from: private */
    public final boolean b;
    /* access modifiers changed from: private */
    public final l<T, Boolean> c;

    public e(@NotNull h<? extends T> sequence, boolean sendWhen, @NotNull l<? super T, Boolean> predicate) {
        k.e(sequence, "sequence");
        k.e(predicate, "predicate");
        this.a = sequence;
        this.b = sendWhen;
        this.c = predicate;
    }

    /* compiled from: Sequences.kt */
    public static final class a implements Iterator<T>, kotlin.jvm.internal.markers.a {
        @NotNull
        private final Iterator<T> c;
        private int d = -1;
        @Nullable
        private T f;
        final /* synthetic */ e q;

        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        a(e this$0) {
            this.q = this$0;
            this.c = this$0.a.iterator();
        }

        private final void b() {
            while (this.c.hasNext()) {
                Object item = this.c.next();
                if (((Boolean) this.q.c.invoke(item)).booleanValue() == this.q.b) {
                    this.f = item;
                    this.d = 1;
                    return;
                }
            }
            this.d = 0;
        }

        public T next() {
            if (this.d == -1) {
                b();
            }
            if (this.d != 0) {
                Object result = this.f;
                this.f = null;
                this.d = -1;
                return result;
            }
            throw new NoSuchElementException();
        }

        public boolean hasNext() {
            if (this.d == -1) {
                b();
            }
            return this.d == 1;
        }
    }

    @NotNull
    public Iterator<T> iterator() {
        return new a(this);
    }
}
