package kotlin.coroutines;

import kotlin.coroutines.intrinsics.b;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.o;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: Continuation.kt */
public final class f {
    public static final <T> void a(@NotNull l<? super d<? super T>, ? extends Object> $this$startCoroutine, @NotNull d<? super T> completion) {
        k.e($this$startCoroutine, "$this$startCoroutine");
        k.e(completion, "completion");
        d<x> c = b.c(b.a($this$startCoroutine, completion));
        x xVar = x.a;
        o.a aVar = o.Companion;
        c.resumeWith(o.m17constructorimpl(xVar));
    }

    public static final <R, T> void b(@NotNull p<? super R, ? super d<? super T>, ? extends Object> $this$startCoroutine, R receiver, @NotNull d<? super T> completion) {
        k.e($this$startCoroutine, "$this$startCoroutine");
        k.e(completion, "completion");
        d<x> c = b.c(b.b($this$startCoroutine, receiver, completion));
        x xVar = x.a;
        o.a aVar = o.Companion;
        c.resumeWith(o.m17constructorimpl(xVar));
    }
}
