package kotlinx.coroutines.flow.internal;

import kotlin.jvm.functions.q;
import kotlin.jvm.internal.e0;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.flow.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\">\u0010\u0000\u001a,\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"emitFun", "Lkotlin/Function3;", "Lkotlinx/coroutines/flow/FlowCollector;", "", "Lkotlin/coroutines/Continuation;", "", "getEmitFun$annotations", "()V", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: SafeCollector.kt */
public final class p {
    /* access modifiers changed from: private */
    @NotNull
    public static final q<d<Object>, Object, kotlin.coroutines.d<? super x>, Object> a = ((q) e0.e(a.INSTANCE, 3));

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: SafeCollector.kt */
    public final /* synthetic */ class a extends i implements q<d<? super Object>, Object, x> {
        public static final a INSTANCE = new a();

        a() {
            super(3, d.class, "emit", "emit(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
        }

        @Nullable
        public final Object invoke(@NotNull d<Object> p0, @Nullable Object p1, @NotNull kotlin.coroutines.d<? super x> $completion) {
            return p0.emit(p1, $completion);
        }
    }
}
