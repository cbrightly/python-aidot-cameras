package kotlinx.coroutines;

import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.l;
import kotlin.n;
import kotlin.t;
import kotlin.x;
import kotlinx.coroutines.internal.d0;
import kotlinx.coroutines.internal.j0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\u0010\u0007J\u0012\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000bH\u0014J\u0006\u0010\u000f\u001a\u00020\u0010J\u0018\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\u00042\b\u0010\u0012\u001a\u0004\u0018\u00010\u000bR\"\u0010\b\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n0\tX\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lkotlinx/coroutines/UndispatchedCoroutine;", "T", "Lkotlinx/coroutines/internal/ScopeCoroutine;", "context", "Lkotlin/coroutines/CoroutineContext;", "uCont", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/Continuation;)V", "threadStateToRecover", "Ljava/lang/ThreadLocal;", "Lkotlin/Pair;", "", "afterResume", "", "state", "clearThreadContext", "", "saveThreadContext", "oldValue", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: CoroutineContext.kt */
public final class b3<T> extends d0<T> {
    @NotNull
    private ThreadLocal<n<g, Object>> q;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public b3(@org.jetbrains.annotations.NotNull kotlin.coroutines.g r3, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super T> r4) {
        /*
            r2 = this;
            kotlinx.coroutines.c3 r0 = kotlinx.coroutines.c3.c
            kotlin.coroutines.g$b r1 = r3.get(r0)
            if (r1 != 0) goto L_0x000d
            kotlin.coroutines.g r0 = r3.plus(r0)
            goto L_0x000e
        L_0x000d:
            r0 = r3
        L_0x000e:
            r2.<init>(r0, r4)
            java.lang.ThreadLocal r0 = new java.lang.ThreadLocal
            r0.<init>()
            r2.q = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.b3.<init>(kotlin.coroutines.g, kotlin.coroutines.d):void");
    }

    public final void X0(@NotNull g context, @Nullable Object oldValue) {
        this.q.set(t.a(context, oldValue));
    }

    public final boolean W0() {
        if (this.q.get() == null) {
            return false;
        }
        this.q.set((Object) null);
        return true;
    }

    /* access modifiers changed from: protected */
    public void R0(@Nullable Object state) {
        n $dstr$ctx$value = this.q.get();
        b3 undispatchedCompletion$iv = null;
        if ($dstr$ctx$value != null) {
            j0.a((g) $dstr$ctx$value.component1(), $dstr$ctx$value.component2());
            this.q.set(undispatchedCompletion$iv);
        }
        Object result = e0.a(state, this.f);
        d continuation$iv = this.f;
        g context$iv = continuation$iv.getContext();
        Object oldValue$iv = j0.c(context$iv, (Object) null);
        if (oldValue$iv != j0.a) {
            undispatchedCompletion$iv = h0.g(continuation$iv, context$iv, oldValue$iv);
        }
        try {
            this.f.resumeWith(result);
            x xVar = x.a;
        } finally {
            if (undispatchedCompletion$iv == null || undispatchedCompletion$iv.W0()) {
                j0.a(context$iv, oldValue$iv);
            }
        }
    }
}
