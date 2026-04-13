package coil;

import coil.intercept.c;
import coil.request.i;
import coil.request.j;
import kotlin.coroutines.d;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.x;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@kotlin.coroutines.jvm.internal.f(c = "coil.RealImageLoader$executeChain$2", f = "RealImageLoader.kt", l = {233}, m = "invokeSuspend")
/* compiled from: RealImageLoader.kt */
public final class f extends l implements p<o0, d<? super j>, Object> {
    final /* synthetic */ c $chain;
    final /* synthetic */ i $request;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public f(c cVar, i iVar, d<? super f> dVar) {
        super(2, dVar);
        this.$chain = cVar;
        this.$request = iVar;
    }

    @NotNull
    public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
        return new f(this.$chain, this.$request, dVar);
    }

    @Nullable
    public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super j> dVar) {
        return ((f) create(o0Var, dVar)).invokeSuspend(x.a);
    }

    @Nullable
    public final Object invokeSuspend(@NotNull Object $result) {
        Object d = kotlin.coroutines.intrinsics.c.d();
        switch (this.label) {
            case 0:
                kotlin.p.b($result);
                c cVar = this.$chain;
                i iVar = this.$request;
                this.label = 1;
                Object i = cVar.i(iVar, this);
                if (i == d) {
                    return d;
                }
                return i;
            case 1:
                kotlin.p.b($result);
                return $result;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
