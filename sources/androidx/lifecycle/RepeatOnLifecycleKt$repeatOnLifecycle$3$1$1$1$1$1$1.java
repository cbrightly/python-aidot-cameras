package androidx.lifecycle;

import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
@f(c = "androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1", f = "RepeatOnLifecycle.kt", l = {111}, m = "invokeSuspend")
/* compiled from: RepeatOnLifecycle.kt */
public final class RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1 extends kotlin.coroutines.jvm.internal.l implements p<o0, d<? super x>, Object> {
    final /* synthetic */ p<o0, d<? super x>, Object> $block;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1(p<? super o0, ? super d<? super x>, ? extends Object> pVar, d<? super RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1> dVar) {
        super(2, dVar);
        this.$block = pVar;
    }

    @NotNull
    public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
        RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1 repeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1 = new RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1(this.$block, dVar);
        repeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1.L$0 = obj;
        return repeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1;
    }

    @Nullable
    public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
        return ((RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1) create(o0Var, dVar)).invokeSuspend(x.a);
    }

    @Nullable
    public final Object invokeSuspend(@NotNull Object $result) {
        Object d = c.d();
        switch (this.label) {
            case 0:
                kotlin.p.b($result);
                p<o0, d<? super x>, Object> pVar = this.$block;
                this.label = 1;
                if (pVar.invoke((o0) this.L$0, this) != d) {
                    break;
                } else {
                    return d;
                }
            case 1:
                kotlin.p.b($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return x.a;
    }
}
