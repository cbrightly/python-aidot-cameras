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
@f(c = "androidx.lifecycle.LifecycleCoroutineScope$launchWhenCreated$1", f = "Lifecycle.kt", l = {79}, m = "invokeSuspend")
/* compiled from: Lifecycle.kt */
public final class LifecycleCoroutineScope$launchWhenCreated$1 extends kotlin.coroutines.jvm.internal.l implements p<o0, d<? super x>, Object> {
    final /* synthetic */ p<o0, d<? super x>, Object> $block;
    int label;
    final /* synthetic */ LifecycleCoroutineScope this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    LifecycleCoroutineScope$launchWhenCreated$1(LifecycleCoroutineScope lifecycleCoroutineScope, p<? super o0, ? super d<? super x>, ? extends Object> pVar, d<? super LifecycleCoroutineScope$launchWhenCreated$1> dVar) {
        super(2, dVar);
        this.this$0 = lifecycleCoroutineScope;
        this.$block = pVar;
    }

    @NotNull
    public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
        return new LifecycleCoroutineScope$launchWhenCreated$1(this.this$0, this.$block, dVar);
    }

    @Nullable
    public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super x> dVar) {
        return ((LifecycleCoroutineScope$launchWhenCreated$1) create(o0Var, dVar)).invokeSuspend(x.a);
    }

    @Nullable
    public final Object invokeSuspend(@NotNull Object $result) {
        Object d = c.d();
        switch (this.label) {
            case 0:
                kotlin.p.b($result);
                Lifecycle lifecycle$lifecycle_runtime_ktx_release = this.this$0.getLifecycle$lifecycle_runtime_ktx_release();
                p<o0, d<? super x>, Object> pVar = this.$block;
                this.label = 1;
                if (PausingDispatcherKt.whenCreated(lifecycle$lifecycle_runtime_ktx_release, pVar, this) != d) {
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
