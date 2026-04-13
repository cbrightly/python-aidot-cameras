package kotlinx.coroutines.internal;

import kotlin.coroutines.g;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlinx.coroutines.w2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\u000e\u001a\u00020\u000f2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\t2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001J\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u0003R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0004¢\u0006\u0002\n\u0000R \u0010\u0007\u001a\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0018\u00010\t0\bX\u0004¢\u0006\u0004\n\u0002\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0005X\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\bX\u0004¢\u0006\u0004\n\u0002\u0010\r¨\u0006\u0013"}, d2 = {"Lkotlinx/coroutines/internal/ThreadState;", "", "context", "Lkotlin/coroutines/CoroutineContext;", "n", "", "(Lkotlin/coroutines/CoroutineContext;I)V", "elements", "", "Lkotlinx/coroutines/ThreadContextElement;", "[Lkotlinx/coroutines/ThreadContextElement;", "i", "values", "[Ljava/lang/Object;", "append", "", "element", "value", "restore", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: ThreadContext.kt */
public final class m0 {
    @NotNull
    public final g a;
    @NotNull
    private final Object[] b;
    @NotNull
    private final w2<Object>[] c;
    private int d;

    public m0(@NotNull g context, int n) {
        this.a = context;
        this.b = new Object[n];
        this.c = new w2[n];
    }

    public final void a(@NotNull w2<?> element, @Nullable Object value) {
        Object[] objArr = this.b;
        int i = this.d;
        objArr[i] = value;
        w2<Object>[] w2VarArr = this.c;
        this.d = i + 1;
        w2VarArr[i] = element;
    }

    public final void b(@NotNull g context) {
        int length = this.c.length - 1;
        if (length >= 0) {
            do {
                int i = length;
                length--;
                w2<Object> w2Var = this.c[i];
                k.c(w2Var);
                w2Var.v(context, this.b[i]);
            } while (length >= 0);
        }
    }
}
