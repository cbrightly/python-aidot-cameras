package kotlinx.coroutines;

import kotlin.l;
import kotlin.o;
import kotlin.p;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0002\u0010\u0005J\u0013\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lkotlinx/coroutines/ResumeAwaitOnCompletion;", "T", "Lkotlinx/coroutines/JobNode;", "continuation", "Lkotlinx/coroutines/CancellableContinuationImpl;", "(Lkotlinx/coroutines/CancellableContinuationImpl;)V", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: JobSupport.kt */
public final class q2<T> extends f2 {
    @NotNull
    private final o<T> x;

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        y((Throwable) p1);
        return x.a;
    }

    public q2(@NotNull o<? super T> continuation) {
        this.x = continuation;
    }

    public void y(@Nullable Throwable cause) {
        Object state = z().l0();
        if (s0.a() && ((state instanceof u1) ^ 1) == 0) {
            throw new AssertionError();
        } else if (state instanceof b0) {
            o<T> oVar = this.x;
            o.a aVar = o.Companion;
            oVar.resumeWith(o.m17constructorimpl(p.a(((b0) state).b)));
        } else {
            o<T> oVar2 = this.x;
            o.a aVar2 = o.Companion;
            oVar2.resumeWith(o.m17constructorimpl(h2.h(state)));
        }
    }
}
