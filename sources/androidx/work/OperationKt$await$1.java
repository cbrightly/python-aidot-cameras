package androidx.work;

import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {}, d2 = {}, k = 3, mv = {1, 5, 1})
@f(c = "androidx.work.OperationKt", f = "Operation.kt", l = {39}, m = "await")
/* compiled from: Operation.kt */
public final class OperationKt$await$1 extends d {
    Object L$0;
    int label;
    /* synthetic */ Object result;

    OperationKt$await$1(kotlin.coroutines.d<? super OperationKt$await$1> dVar) {
        super(dVar);
    }

    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return OperationKt.await((Operation) null, this);
    }
}
