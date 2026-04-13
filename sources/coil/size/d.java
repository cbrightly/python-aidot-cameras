package coil.size;

import android.view.View;
import coil.decode.l;
import coil.size.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RealViewSizeResolver.kt */
public final class d<T extends View> implements g<T> {
    @NotNull
    private final T c;
    private final boolean d;

    public d(@NotNull T view, boolean subtractPadding) {
        k.e(view, "view");
        this.c = view;
        this.d = subtractPadding;
    }

    @Nullable
    public Object b(@NotNull kotlin.coroutines.d<? super Size> $completion) {
        return g.b.h(this, $completion);
    }

    @NotNull
    public T getView() {
        return this.c;
    }

    public boolean a() {
        return this.d;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof d) || !k.a(getView(), ((d) other).getView()) || a() != ((d) other).a()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (getView().hashCode() * 31) + l.a(a());
    }

    @NotNull
    public String toString() {
        return "RealViewSizeResolver(view=" + getView() + ", subtractPadding=" + a() + ')';
    }
}
