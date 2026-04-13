package kotlinx.coroutines;

import kotlin.coroutines.b;
import kotlin.coroutines.d;
import kotlin.coroutines.e;
import kotlin.coroutines.g;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.l;
import kotlinx.coroutines.internal.i;
import kotlinx.coroutines.internal.o;
import kotlinx.coroutines.internal.p;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u0000 \u001a2\u00020\u00012\u00020\u0002:\u0001\u001aB\u0005¢\u0006\u0002\u0010\u0003J\u001c\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\tj\u0002`\nH&J\u001c\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u00060\tj\u0002`\nH\u0017J \u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\r\"\u0004\b\u0000\u0010\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\rJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0014H\u0017J\u0011\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0000H\u0002J\u0012\u0010\u0017\u001a\u00020\u00052\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\rJ\b\u0010\u0018\u001a\u00020\u0019H\u0016¨\u0006\u001b"}, d2 = {"Lkotlinx/coroutines/CoroutineDispatcher;", "Lkotlin/coroutines/AbstractCoroutineContextElement;", "Lkotlin/coroutines/ContinuationInterceptor;", "()V", "dispatch", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dispatchYield", "interceptContinuation", "Lkotlin/coroutines/Continuation;", "T", "continuation", "isDispatchNeeded", "", "limitedParallelism", "parallelism", "", "plus", "other", "releaseInterceptedContinuation", "toString", "", "Key", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: CoroutineDispatcher.kt */
public abstract class i0 extends kotlin.coroutines.a implements e {
    @NotNull
    public static final a Key = new a((DefaultConstructorMarker) null);

    public abstract void dispatch(@NotNull g gVar, @NotNull Runnable runnable);

    @Nullable
    public <E extends g.b> E get(@NotNull g.c<E> key) {
        return e.a.a(this, key);
    }

    @NotNull
    public g minusKey(@NotNull g.c<?> key) {
        return e.a.b(this, key);
    }

    public i0() {
        super(e.a);
    }

    @l(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lkotlinx/coroutines/CoroutineDispatcher$Key;", "Lkotlin/coroutines/AbstractCoroutineContextKey;", "Lkotlin/coroutines/ContinuationInterceptor;", "Lkotlinx/coroutines/CoroutineDispatcher;", "()V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: CoroutineDispatcher.kt */
    public static final class a extends b<e, i0> {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
            super(e.a, C0448a.INSTANCE);
        }

        @l(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "Lkotlinx/coroutines/CoroutineDispatcher;", "it", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
        /* renamed from: kotlinx.coroutines.i0$a$a  reason: collision with other inner class name */
        /* compiled from: CoroutineDispatcher.kt */
        public static final class C0448a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<g.b, i0> {
            public static final C0448a INSTANCE = new C0448a();

            C0448a() {
                super(1);
            }

            @Nullable
            public final i0 invoke(@NotNull g.b it) {
                if (it instanceof i0) {
                    return (i0) it;
                }
                return null;
            }
        }
    }

    public boolean isDispatchNeeded(@NotNull g context) {
        return true;
    }

    @NotNull
    public i0 limitedParallelism(int parallelism) {
        p.a(parallelism);
        return new o(this, parallelism);
    }

    public void dispatchYield(@NotNull g context, @NotNull Runnable block) {
        dispatch(context, block);
    }

    @NotNull
    public final <T> d<T> interceptContinuation(@NotNull d<? super T> continuation) {
        return new i(this, continuation);
    }

    public final void releaseInterceptedContinuation(@NotNull d<?> continuation) {
        ((i) continuation).q();
    }

    @NotNull
    public final i0 plus(@NotNull i0 other) {
        return other;
    }

    @NotNull
    public String toString() {
        return t0.a(this) + '@' + t0.b(this);
    }
}
