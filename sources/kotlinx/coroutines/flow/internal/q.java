package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.g;
import kotlin.jvm.functions.p;
import kotlin.l;
import kotlinx.coroutines.internal.d0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aN\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022/\b\u0005\u0010\u0003\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0004¢\u0006\u0002\b\tH\bø\u0001\u0000¢\u0006\u0002\u0010\n\u001a\u0018\u0010\u000b\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\f2\u0006\u0010\r\u001a\u00020\u000eH\u0001\u001a\u001b\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u0010\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, d2 = {"unsafeFlow", "Lkotlinx/coroutines/flow/Flow;", "T", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/Flow;", "checkContext", "Lkotlinx/coroutines/flow/internal/SafeCollector;", "currentContext", "Lkotlin/coroutines/CoroutineContext;", "transitiveCoroutineParent", "Lkotlinx/coroutines/Job;", "collectJob", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class q {

    @l(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "count", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke", "(ILkotlin/coroutines/CoroutineContext$Element;)Ljava/lang/Integer;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: SafeCollector.common.kt */
    public static final class a extends kotlin.jvm.internal.l implements p<Integer, g.b, Integer> {
        final /* synthetic */ o<?> $this_checkContext;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(o<?> oVar) {
            super(2);
            this.$this_checkContext = oVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1, Object p2) {
            return invoke(((Number) p1).intValue(), (g.b) p2);
        }

        @NotNull
        public final Integer invoke(int count, @NotNull g.b element) {
            int i;
            g.c key = element.getKey();
            g.b collectElement = this.$this_checkContext.collectContext.get(key);
            if (key != z1.g) {
                if (element != collectElement) {
                    i = Integer.MIN_VALUE;
                } else {
                    i = count + 1;
                }
                return Integer.valueOf(i);
            }
            z1 collectJob = (z1) collectElement;
            z1 emissionParentJob = q.b((z1) element, collectJob);
            if (emissionParentJob == collectJob) {
                return Integer.valueOf(collectJob == null ? count : count + 1);
            }
            throw new IllegalStateException(("Flow invariant is violated:\n\t\tEmission from another coroutine is detected.\n\t\tChild of " + emissionParentJob + ", expected child of " + collectJob + ".\n\t\tFlowCollector is not thread-safe and concurrent emissions are prohibited.\n\t\tTo mitigate this restriction please use 'channelFlow' builder instead of 'flow'").toString());
        }
    }

    public static final void a(@NotNull o<?> $this$checkContext, @NotNull g currentContext) {
        if (((Number) currentContext.fold(0, new a($this$checkContext))).intValue() != $this$checkContext.collectContextSize) {
            throw new IllegalStateException(("Flow invariant is violated:\n\t\tFlow was collected in " + $this$checkContext.collectContext + ",\n\t\tbut emission happened in " + currentContext + ".\n\t\tPlease refer to 'flow' documentation or use 'flowOn' instead").toString());
        }
    }

    @Nullable
    public static final z1 b(@Nullable z1 $this$transitiveCoroutineParent, @Nullable z1 collectJob) {
        z1 z1Var = $this$transitiveCoroutineParent;
        while (z1Var != null) {
            if (z1Var == collectJob || !(z1Var instanceof d0)) {
                return z1Var;
            }
            z1Var = ((d0) z1Var).V0();
        }
        return null;
    }
}
