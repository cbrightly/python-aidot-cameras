package kotlin.collections;

import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReversedViews.kt */
public final class m0<T> extends f<T> {
    private final List<T> c;

    public m0(@NotNull List<T> delegate) {
        k.e(delegate, "delegate");
        this.c = delegate;
    }

    public int a() {
        return this.c.size();
    }

    public T get(int index) {
        return this.c.get(w.I(this, index));
    }

    public void clear() {
        this.c.clear();
    }

    public T b(int index) {
        return this.c.remove(w.I(this, index));
    }

    public T set(int index, T element) {
        return this.c.set(w.I(this, index), element);
    }

    public void add(int index, T element) {
        this.c.add(w.J(this, index), element);
    }
}
