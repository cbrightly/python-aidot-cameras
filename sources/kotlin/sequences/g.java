package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Sequences.kt */
public final class g<T> implements h<T> {
    /* access modifiers changed from: private */
    public final kotlin.jvm.functions.a<T> a;
    /* access modifiers changed from: private */
    public final l<T, T> b;

    /* compiled from: Sequences.kt */
    public static final class a implements Iterator<T>, kotlin.jvm.internal.markers.a {
        @Nullable
        private T c;
        private int d = -2;
        final /* synthetic */ g f;

        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        a(g this$0) {
            this.f = this$0;
        }

        private final void b() {
            T t;
            if (this.d == -2) {
                t = this.f.a.invoke();
            } else {
                l c2 = this.f.b;
                T t2 = this.c;
                k.c(t2);
                t = c2.invoke(t2);
            }
            this.c = t;
            this.d = t == null ? 0 : 1;
        }

        @NotNull
        public T next() {
            if (this.d < 0) {
                b();
            }
            if (this.d != 0) {
                Object result = this.c;
                if (result != null) {
                    this.d = -1;
                    return result;
                }
                throw new NullPointerException("null cannot be cast to non-null type T");
            }
            throw new NoSuchElementException();
        }

        public boolean hasNext() {
            if (this.d < 0) {
                b();
            }
            return this.d == 1;
        }
    }

    public g(@NotNull kotlin.jvm.functions.a<? extends T> getInitialValue, @NotNull l<? super T, ? extends T> getNextValue) {
        k.e(getInitialValue, "getInitialValue");
        k.e(getNextValue, "getNextValue");
        this.a = getInitialValue;
        this.b = getNextValue;
    }

    @NotNull
    public Iterator<T> iterator() {
        return new a(this);
    }
}
