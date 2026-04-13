package kotlinx.coroutines;

import kotlin.coroutines.d;
import kotlin.coroutines.e;
import kotlin.coroutines.g;
import kotlin.coroutines.h;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.z;
import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000>\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a \u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH\u0002\u001a8\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\f0\u0012H\b¢\u0006\u0002\u0010\u0013\u001a4\u0010\u0014\u001a\u0002H\f\"\u0004\b\u0000\u0010\f2\u0006\u0010\u0015\u001a\u00020\u00032\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\f0\u0012H\b¢\u0006\u0002\u0010\u0016\u001a\f\u0010\u0017\u001a\u00020\n*\u00020\u0003H\u0002\u001a\u0014\u0010\u0018\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0003H\u0007\u001a\u0014\u0010\u0018\u001a\u00020\u0003*\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u0003H\u0007\u001a\u0013\u0010\u001b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001c*\u00020\u001dH\u0010\u001a(\u0010\u001e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001c*\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u0015\u001a\u00020\u00032\b\u0010\u001f\u001a\u0004\u0018\u00010\u0010H\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0001*\u00020\u00038@X\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006 "}, d2 = {"DEBUG_THREAD_NAME_SEPARATOR", "", "coroutineName", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineName", "(Lkotlin/coroutines/CoroutineContext;)Ljava/lang/String;", "foldCopies", "originalContext", "appendContext", "isNewCoroutine", "", "withContinuationContext", "T", "continuation", "Lkotlin/coroutines/Continuation;", "countOrElement", "", "block", "Lkotlin/Function0;", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withCoroutineContext", "context", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "hasCopyableElements", "newCoroutineContext", "addedContext", "Lkotlinx/coroutines/CoroutineScope;", "undispatchedCompletion", "Lkotlinx/coroutines/UndispatchedCoroutine;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "updateUndispatchedCompletion", "oldValue", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: CoroutineContext.kt */
public final class h0 {
    @NotNull
    public static final g e(@NotNull o0 $this$newCoroutineContext, @NotNull g context) {
        g combined = a($this$newCoroutineContext.getCoroutineContext(), context, true);
        g debug = s0.c() ? combined.plus(new m0(s0.b().incrementAndGet())) : combined;
        return (combined == d1.a() || combined.get(e.a) != null) ? debug : debug.plus(d1.a());
    }

    @NotNull
    public static final g d(@NotNull g $this$newCoroutineContext, @NotNull g addedContext) {
        if (!c(addedContext)) {
            return $this$newCoroutineContext.plus(addedContext);
        }
        return a($this$newCoroutineContext, addedContext, false);
    }

    @l(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "result", "it", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke", "(ZLkotlin/coroutines/CoroutineContext$Element;)Ljava/lang/Boolean;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: CoroutineContext.kt */
    public static final class c extends kotlin.jvm.internal.l implements p<Boolean, g.b, Boolean> {
        public static final c INSTANCE = new c();

        c() {
            super(2);
        }

        @NotNull
        public final Boolean invoke(boolean result, @NotNull g.b it) {
            return Boolean.valueOf(result || (it instanceof f0));
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1, Object p2) {
            return invoke(((Boolean) p1).booleanValue(), (g.b) p2);
        }
    }

    private static final boolean c(g $this$hasCopyableElements) {
        return ((Boolean) $this$hasCopyableElements.fold(false, c.INSTANCE)).booleanValue();
    }

    private static final g a(g originalContext, g appendContext, boolean isNewCoroutine) {
        boolean hasElementsLeft = c(originalContext);
        boolean hasElementsRight = c(appendContext);
        if (!hasElementsLeft && !hasElementsRight) {
            return originalContext.plus(appendContext);
        }
        z leftoverContext = new z();
        leftoverContext.element = appendContext;
        h hVar = h.INSTANCE;
        g folded = (g) originalContext.fold(hVar, new b(leftoverContext, isNewCoroutine));
        if (hasElementsRight) {
            leftoverContext.element = ((g) leftoverContext.element).fold(hVar, a.INSTANCE);
        }
        return folded.plus((g) leftoverContext.element);
    }

    @l(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "Lkotlin/coroutines/CoroutineContext;", "result", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: CoroutineContext.kt */
    public static final class b extends kotlin.jvm.internal.l implements p<g, g.b, g> {
        final /* synthetic */ boolean $isNewCoroutine;
        final /* synthetic */ z<g> $leftoverContext;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(z<g> zVar, boolean z) {
            super(2);
            this.$leftoverContext = zVar;
            this.$isNewCoroutine = z;
        }

        @NotNull
        public final g invoke(@NotNull g result, @NotNull g.b element) {
            if (!(element instanceof f0)) {
                return result.plus(element);
            }
            g.b newElement = ((g) this.$leftoverContext.element).get(element.getKey());
            if (newElement == null) {
                return result.plus(this.$isNewCoroutine ? ((f0) element).u() : (f0) element);
            }
            z<g> zVar = this.$leftoverContext;
            zVar.element = ((g) zVar.element).minusKey(element.getKey());
            return result.plus(((f0) element).i(newElement));
        }
    }

    @l(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "Lkotlin/coroutines/CoroutineContext;", "result", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: CoroutineContext.kt */
    public static final class a extends kotlin.jvm.internal.l implements p<g, g.b, g> {
        public static final a INSTANCE = new a();

        a() {
            super(2);
        }

        @NotNull
        public final g invoke(@NotNull g result, @NotNull g.b element) {
            if (element instanceof f0) {
                return result.plus(((f0) element).u());
            }
            return result.plus(element);
        }
    }

    @Nullable
    public static final b3<?> g(@NotNull d<?> $this$updateUndispatchedCompletion, @NotNull g context, @Nullable Object oldValue) {
        if (!($this$updateUndispatchedCompletion instanceof kotlin.coroutines.jvm.internal.e)) {
            return null;
        }
        if (!(context.get(c3.c) != null)) {
            return null;
        }
        b3 completion = f((kotlin.coroutines.jvm.internal.e) $this$updateUndispatchedCompletion);
        if (completion != null) {
            completion.X0(context, oldValue);
        }
        return completion;
    }

    @Nullable
    public static final b3<?> f(@NotNull kotlin.coroutines.jvm.internal.e $this$undispatchedCompletion) {
        kotlin.coroutines.jvm.internal.e completion = $this$undispatchedCompletion;
        while (!(completion instanceof a1) && (completion = completion.getCallerFrame()) != null) {
            if (completion instanceof b3) {
                return (b3) completion;
            }
        }
        return null;
    }

    @Nullable
    public static final String b(@NotNull g $this$coroutineName) {
        m0 coroutineId;
        String name;
        if (!s0.c() || (coroutineId = (m0) $this$coroutineName.get(m0.c)) == null) {
            return null;
        }
        n0 n0Var = (n0) $this$coroutineName.get(n0.c);
        String str = "coroutine";
        if (!(n0Var == null || (name = n0Var.getName()) == null)) {
            str = name;
        }
        String coroutineName = str;
        return coroutineName + '#' + coroutineId.W();
    }
}
