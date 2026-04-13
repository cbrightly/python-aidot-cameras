package kotlinx.coroutines.internal;

import kotlin.coroutines.g;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlinx.coroutines.w2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u001a\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0004H\u0000\u001a\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rH\u0000\u001a\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u00042\u0006\u0010\f\u001a\u00020\r2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0004H\u0000\"\u0010\u0010\u0000\u001a\u00020\u00018\u0000X\u0004¢\u0006\u0002\n\u0000\"$\u0010\u0002\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003X\u0004¢\u0006\u0002\n\u0000\",\u0010\u0006\u001a \u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u0007\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00070\u0003X\u0004¢\u0006\u0002\n\u0000\" \u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\t0\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"NO_THREAD_ELEMENTS", "Lkotlinx/coroutines/internal/Symbol;", "countAll", "Lkotlin/Function2;", "", "Lkotlin/coroutines/CoroutineContext$Element;", "findOne", "Lkotlinx/coroutines/ThreadContextElement;", "updateState", "Lkotlinx/coroutines/internal/ThreadState;", "restoreThreadContext", "", "context", "Lkotlin/coroutines/CoroutineContext;", "oldState", "threadContextElements", "updateThreadContext", "countOrElement", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: ThreadContext.kt */
public final class j0 {
    @NotNull
    public static final f0 a = new f0("NO_THREAD_ELEMENTS");
    @NotNull
    private static final p<Object, g.b, Object> b = a.INSTANCE;
    @NotNull
    private static final p<w2<?>, g.b, w2<?>> c = b.INSTANCE;
    @NotNull
    private static final p<m0, g.b, m0> d = c.INSTANCE;

    @l(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, d2 = {"<no name provided>", "", "countOrElement", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: ThreadContext.kt */
    public static final class a extends kotlin.jvm.internal.l implements p<Object, g.b, Object> {
        public static final a INSTANCE = new a();

        a() {
            super(2);
        }

        @Nullable
        public final Object invoke(@Nullable Object countOrElement, @NotNull g.b element) {
            if (!(element instanceof w2)) {
                return countOrElement;
            }
            Integer num = countOrElement instanceof Integer ? (Integer) countOrElement : null;
            int inCount = num == null ? 1 : num.intValue();
            return inCount == 0 ? element : Integer.valueOf(inCount + 1);
        }
    }

    @l(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00012\f\u0010\u0002\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, d2 = {"<no name provided>", "Lkotlinx/coroutines/ThreadContextElement;", "found", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: ThreadContext.kt */
    public static final class b extends kotlin.jvm.internal.l implements p<w2<?>, g.b, w2<?>> {
        public static final b INSTANCE = new b();

        b() {
            super(2);
        }

        @Nullable
        public final w2<?> invoke(@Nullable w2<?> found, @NotNull g.b element) {
            if (found != null) {
                return found;
            }
            if (element instanceof w2) {
                return (w2) element;
            }
            return null;
        }
    }

    @l(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, d2 = {"<no name provided>", "Lkotlinx/coroutines/internal/ThreadState;", "state", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: ThreadContext.kt */
    public static final class c extends kotlin.jvm.internal.l implements p<m0, g.b, m0> {
        public static final c INSTANCE = new c();

        c() {
            super(2);
        }

        @NotNull
        public final m0 invoke(@NotNull m0 state, @NotNull g.b element) {
            if (element instanceof w2) {
                state.a((w2) element, ((w2) element).P(state.a));
            }
            return state;
        }
    }

    @NotNull
    public static final Object b(@NotNull g context) {
        Object fold = context.fold(0, b);
        k.c(fold);
        return fold;
    }

    @Nullable
    public static final Object c(@NotNull g context, @Nullable Object countOrElement) {
        Object countOrElement2 = countOrElement == null ? b(context) : countOrElement;
        if (countOrElement2 == 0) {
            return a;
        }
        if (countOrElement2 instanceof Integer) {
            return context.fold(new m0(context, ((Number) countOrElement2).intValue()), d);
        }
        return ((w2) countOrElement2).P(context);
    }

    public static final void a(@NotNull g context, @Nullable Object oldState) {
        if (oldState != a) {
            if (oldState instanceof m0) {
                ((m0) oldState).b(context);
                return;
            }
            Object fold = context.fold(null, c);
            if (fold != null) {
                ((w2) fold).v(context, oldState);
                return;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.ThreadContextElement<kotlin.Any?>");
        }
    }
}
