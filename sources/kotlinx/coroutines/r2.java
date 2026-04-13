package kotlinx.coroutines;

import kotlin.coroutines.d;
import kotlin.l;
import kotlin.o;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0013\u0010\u0006\u001a\u00020\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0002R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lkotlinx/coroutines/ResumeOnCompletion;", "Lkotlinx/coroutines/JobNode;", "continuation", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/coroutines/Continuation;)V", "invoke", "cause", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: JobSupport.kt */
public final class r2 extends f2 {
    @NotNull
    private final d<x> x;

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        y((Throwable) p1);
        return x.a;
    }

    public r2(@NotNull d<? super x> continuation) {
        this.x = continuation;
    }

    public void y(@Nullable Throwable cause) {
        d<x> dVar = this.x;
        o.a aVar = o.Companion;
        dVar.resumeWith(o.m17constructorimpl(x.a));
    }
}
