package kotlinx.coroutines;

import kotlin.l;
import kotlinx.coroutines.internal.p;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\nH\u0016J\n\u0010\u000b\u001a\u0004\u0018\u00010\nH\u0005R\u0012\u0010\u0003\u001a\u00020\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\f"}, d2 = {"Lkotlinx/coroutines/MainCoroutineDispatcher;", "Lkotlinx/coroutines/CoroutineDispatcher;", "()V", "immediate", "getImmediate", "()Lkotlinx/coroutines/MainCoroutineDispatcher;", "limitedParallelism", "parallelism", "", "toString", "", "toStringInternalImpl", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: MainCoroutineDispatcher.kt */
public abstract class k2 extends i0 {
    @NotNull
    public abstract k2 W();

    @NotNull
    public String toString() {
        String o0 = o0();
        if (o0 != null) {
            return o0;
        }
        return t0.a(this) + '@' + t0.b(this);
    }

    @NotNull
    public i0 limitedParallelism(int parallelism) {
        p.a(parallelism);
        return this;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final String o0() {
        k2 immediate;
        k2 main = d1.c();
        if (this == main) {
            return "Dispatchers.Main";
        }
        try {
            immediate = main.W();
        } catch (UnsupportedOperationException e) {
            immediate = null;
        }
        if (this == immediate) {
            return "Dispatchers.Main.immediate";
        }
        return null;
    }
}
