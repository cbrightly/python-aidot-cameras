package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.coroutines.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\b\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u0002H@"}, d2 = {"<anonymous>", "T", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = 48)
@f(c = "androidx.lifecycle.PausingDispatcherKt$whenStateAtLeast$2", f = "PausingDispatcher.kt", l = {162}, m = "invokeSuspend")
/* compiled from: PausingDispatcher.kt */
public final class PausingDispatcherKt$whenStateAtLeast$2 extends kotlin.coroutines.jvm.internal.l implements p<o0, d<? super T>, Object> {
    final /* synthetic */ p<o0, d<? super T>, Object> $block;
    final /* synthetic */ Lifecycle.State $minState;
    final /* synthetic */ Lifecycle $this_whenStateAtLeast;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    PausingDispatcherKt$whenStateAtLeast$2(Lifecycle lifecycle, Lifecycle.State state, p<? super o0, ? super d<? super T>, ? extends Object> pVar, d<? super PausingDispatcherKt$whenStateAtLeast$2> dVar) {
        super(2, dVar);
        this.$this_whenStateAtLeast = lifecycle;
        this.$minState = state;
        this.$block = pVar;
    }

    @NotNull
    public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
        PausingDispatcherKt$whenStateAtLeast$2 pausingDispatcherKt$whenStateAtLeast$2 = new PausingDispatcherKt$whenStateAtLeast$2(this.$this_whenStateAtLeast, this.$minState, this.$block, dVar);
        pausingDispatcherKt$whenStateAtLeast$2.L$0 = obj;
        return pausingDispatcherKt$whenStateAtLeast$2;
    }

    @Nullable
    public final Object invoke(@NotNull o0 o0Var, @Nullable d<? super T> dVar) {
        return ((PausingDispatcherKt$whenStateAtLeast$2) create(o0Var, dVar)).invokeSuspend(x.a);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0058, code lost:
        r2.finish();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005c, code lost:
        return r10;
     */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r1 = r9.label
            switch(r1) {
                case 0: goto L_0x001f;
                case 1: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x0011:
            r0 = r9
            java.lang.Object r1 = r0.L$0
            androidx.lifecycle.LifecycleController r1 = (androidx.lifecycle.LifecycleController) r1
            kotlin.p.b(r10)     // Catch:{ all -> 0x001d }
            r2 = r1
            r1 = r0
            r0 = r10
            goto L_0x0058
        L_0x001d:
            r2 = move-exception
            goto L_0x0062
        L_0x001f:
            kotlin.p.b(r10)
            r1 = r9
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.o0 r2 = (kotlinx.coroutines.o0) r2
            kotlin.coroutines.g r3 = r2.getCoroutineContext()
            kotlinx.coroutines.z1$b r4 = kotlinx.coroutines.z1.g
            kotlin.coroutines.g$b r3 = r3.get(r4)
            kotlinx.coroutines.z1 r3 = (kotlinx.coroutines.z1) r3
            if (r3 == 0) goto L_0x0066
            r2 = r3
            androidx.lifecycle.PausingDispatcher r3 = new androidx.lifecycle.PausingDispatcher
            r3.<init>()
            androidx.lifecycle.LifecycleController r4 = new androidx.lifecycle.LifecycleController
            androidx.lifecycle.Lifecycle r5 = r1.$this_whenStateAtLeast
            androidx.lifecycle.Lifecycle$State r6 = r1.$minState
            androidx.lifecycle.DispatchQueue r7 = r3.dispatchQueue
            r4.<init>(r5, r6, r7, r2)
            r2 = r4
            kotlin.jvm.functions.p<kotlinx.coroutines.o0, kotlin.coroutines.d<? super T>, java.lang.Object> r4 = r1.$block     // Catch:{ all -> 0x005d }
            r1.L$0 = r2     // Catch:{ all -> 0x005d }
            r5 = 1
            r1.label = r5     // Catch:{ all -> 0x005d }
            java.lang.Object r4 = kotlinx.coroutines.h.g(r3, r4, r1)     // Catch:{ all -> 0x005d }
            if (r4 != r0) goto L_0x0056
            return r0
        L_0x0056:
            r0 = r10
            r10 = r4
        L_0x0058:
            r2.finish()
            return r10
        L_0x005d:
            r0 = move-exception
            r8 = r2
            r2 = r0
            r0 = r1
            r1 = r8
        L_0x0062:
            r1.finish()
            throw r2
        L_0x0066:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "when[State] methods should have a parent job"
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.PausingDispatcherKt$whenStateAtLeast$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
