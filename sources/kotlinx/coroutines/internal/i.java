package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.e;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.b1;
import kotlinx.coroutines.c0;
import kotlinx.coroutines.e0;
import kotlinx.coroutines.i0;
import kotlinx.coroutines.j1;
import kotlinx.coroutines.n;
import kotlinx.coroutines.o;
import kotlinx.coroutines.s0;
import kotlinx.coroutines.t0;
import kotlinx.coroutines.x2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\b\u0000\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\b\u0012\u0004\u0012\u00028\u00000O2\u00060?j\u0002`@2\b\u0012\u0004\u0012\u00028\u00000\u0004B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\r\u0010\t\u001a\u00020\b¢\u0006\u0004\b\t\u0010\nJ!\u0010\u0011\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u000e\u001a\u00020\rH\u0010¢\u0006\u0004\b\u000f\u0010\u0010J\u0015\u0010\u0013\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0012¢\u0006\u0004\b\u0013\u0010\u0014J\u001f\u0010\u001a\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00028\u0000H\u0000¢\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u001d\u001a\n\u0018\u00010\u001bj\u0004\u0018\u0001`\u001cH\u0016¢\u0006\u0004\b\u001d\u0010\u001eJ\r\u0010 \u001a\u00020\u001f¢\u0006\u0004\b \u0010!J\u0015\u0010\"\u001a\u00020\u001f2\u0006\u0010\u000e\u001a\u00020\r¢\u0006\u0004\b\"\u0010#J\r\u0010$\u001a\u00020\b¢\u0006\u0004\b$\u0010\nJH\u0010+\u001a\u00020\b2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000%2%\b\b\u0010*\u001a\u001f\u0012\u0013\u0012\u00110\r¢\u0006\f\b(\u0012\b\b)\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\b\u0018\u00010'H\bø\u0001\u0000¢\u0006\u0004\b+\u0010,J\u001a\u0010.\u001a\u00020\u001f2\b\u0010-\u001a\u0004\u0018\u00010\u000bH\b¢\u0006\u0004\b.\u0010/J!\u00100\u001a\u00020\b2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000%H\bø\u0001\u0000¢\u0006\u0004\b0\u00101J \u00102\u001a\u00020\b2\f\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000%H\u0016ø\u0001\u0000¢\u0006\u0004\b2\u00101J\u0011\u00105\u001a\u0004\u0018\u00010\u000bH\u0010¢\u0006\u0004\b3\u00104J\u000f\u00107\u001a\u000206H\u0016¢\u0006\u0004\b7\u00108J\u001b\u0010:\u001a\u0004\u0018\u00010\r2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u000309¢\u0006\u0004\b:\u0010;R\u001e\u0010<\u001a\u0004\u0018\u00010\u000b8\u0000@\u0000X\u000e¢\u0006\f\n\u0004\b<\u0010=\u0012\u0004\b>\u0010\nR\u001c\u0010C\u001a\n\u0018\u00010?j\u0004\u0018\u0001`@8VX\u0004¢\u0006\u0006\u001a\u0004\bA\u0010BR\u0014\u0010\u0016\u001a\u00020\u00158\u0016X\u0005¢\u0006\u0006\u001a\u0004\bD\u0010ER\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00048\u0006X\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010FR\u0014\u0010G\u001a\u00020\u000b8\u0000X\u0004¢\u0006\u0006\n\u0004\bG\u0010=R\u001a\u0010J\u001a\b\u0012\u0004\u0012\u00028\u00000\u00048PX\u0004¢\u0006\u0006\u001a\u0004\bH\u0010IR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010KR\u001a\u0010M\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00128BX\u0004¢\u0006\u0006\u001a\u0004\bL\u0010\u0014\u0002\u0004\n\u0002\b\u0019¨\u0006N"}, d2 = {"Lkotlinx/coroutines/internal/DispatchedContinuation;", "T", "Lkotlinx/coroutines/CoroutineDispatcher;", "dispatcher", "Lkotlin/coroutines/Continuation;", "continuation", "<init>", "(Lkotlinx/coroutines/CoroutineDispatcher;Lkotlin/coroutines/Continuation;)V", "", "awaitReusability", "()V", "", "takenState", "", "cause", "cancelCompletedResult$kotlinx_coroutines_core", "(Ljava/lang/Object;Ljava/lang/Throwable;)V", "cancelCompletedResult", "Lkotlinx/coroutines/CancellableContinuationImpl;", "claimReusableCancellableContinuation", "()Lkotlinx/coroutines/CancellableContinuationImpl;", "Lkotlin/coroutines/CoroutineContext;", "context", "value", "dispatchYield$kotlinx_coroutines_core", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V", "dispatchYield", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "getStackTraceElement", "()Ljava/lang/StackTraceElement;", "", "isReusable", "()Z", "postponeCancellation", "(Ljava/lang/Throwable;)Z", "release", "Lkotlin/Result;", "result", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "onCancellation", "resumeCancellableWith", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "state", "resumeCancelled", "(Ljava/lang/Object;)Z", "resumeUndispatchedWith", "(Ljava/lang/Object;)V", "resumeWith", "takeState$kotlinx_coroutines_core", "()Ljava/lang/Object;", "takeState", "", "toString", "()Ljava/lang/String;", "Lkotlinx/coroutines/CancellableContinuation;", "tryReleaseClaimedContinuation", "(Lkotlinx/coroutines/CancellableContinuation;)Ljava/lang/Throwable;", "_state", "Ljava/lang/Object;", "get_state$kotlinx_coroutines_core$annotations", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "callerFrame", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "Lkotlin/coroutines/Continuation;", "countOrElement", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "delegate", "Lkotlinx/coroutines/CoroutineDispatcher;", "getReusableCancellableContinuation", "reusableCancellableContinuation", "kotlinx-coroutines-core", "Lkotlinx/coroutines/DispatchedTask;"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: DispatchedContinuation.kt */
public final class i<T> extends b1<T> implements e, d<T> {
    private static final /* synthetic */ AtomicReferenceFieldUpdater q = AtomicReferenceFieldUpdater.newUpdater(i.class, Object.class, "_reusableCancellableContinuation");
    @NotNull
    private volatile /* synthetic */ Object _reusableCancellableContinuation = null;
    @NotNull
    public final Object p0 = j0.b(getContext());
    @NotNull
    public final i0 x;
    @NotNull
    public final d<T> y;
    @Nullable
    public Object z = j.a;

    @NotNull
    public g getContext() {
        return this.y.getContext();
    }

    public i(@NotNull i0 dispatcher, @NotNull d<? super T> continuation) {
        super(-1);
        this.x = dispatcher;
        this.y = continuation;
    }

    @Nullable
    public e getCallerFrame() {
        d<T> dVar = this.y;
        if (dVar instanceof e) {
            return (e) dVar;
        }
        return null;
    }

    @Nullable
    public StackTraceElement getStackTraceElement() {
        return null;
    }

    private final o<?> n() {
        Object obj = this._reusableCancellableContinuation;
        if (obj instanceof o) {
            return (o) obj;
        }
        return null;
    }

    public final boolean o() {
        return this._reusableCancellableContinuation != null;
    }

    public final void k() {
        do {
        } while (this._reusableCancellableContinuation == j.b);
    }

    public final void q() {
        k();
        o<?> n = n();
        if (n != null) {
            n.p();
        }
    }

    @Nullable
    public final o<T> l() {
        while (true) {
            Object state = this._reusableCancellableContinuation;
            if (state == null) {
                this._reusableCancellableContinuation = j.b;
                return null;
            } else if (state instanceof o) {
                if (q.compareAndSet(this, state, j.b)) {
                    return (o) state;
                }
            } else if (state != j.b && !(state instanceof Throwable)) {
                throw new IllegalStateException(k.l("Inconsistent state ", state).toString());
            }
        }
    }

    @Nullable
    public final Throwable r(@NotNull n<?> continuation) {
        f0 f0Var;
        do {
            Object state = this._reusableCancellableContinuation;
            f0Var = j.b;
            if (state != f0Var) {
                if (!(state instanceof Throwable)) {
                    throw new IllegalStateException(k.l("Inconsistent state ", state).toString());
                } else if (q.compareAndSet(this, state, (Object) null)) {
                    return (Throwable) state;
                } else {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
            }
        } while (!q.compareAndSet(this, f0Var, continuation));
        return null;
    }

    public final boolean p(@NotNull Throwable cause) {
        while (true) {
            Object state = this._reusableCancellableContinuation;
            f0 f0Var = j.b;
            if (k.a(state, f0Var)) {
                if (q.compareAndSet(this, f0Var, cause)) {
                    return true;
                }
            } else if (state instanceof Throwable) {
                return true;
            } else {
                if (q.compareAndSet(this, state, (Object) null)) {
                    return false;
                }
            }
        }
    }

    @Nullable
    public Object j() {
        Object state = this.z;
        if (s0.a()) {
            if (!(state != j.a)) {
                throw new AssertionError();
            }
        }
        this.z = j.a;
        return state;
    }

    @NotNull
    public d<T> c() {
        return this;
    }

    public void resumeWith(@NotNull Object result) {
        g context$iv;
        Object oldValue$iv;
        Object obj = result;
        g context = this.y.getContext();
        Object state = e0.d(obj, (kotlin.jvm.functions.l) null, 1, (Object) null);
        if (this.x.isDispatchNeeded(context)) {
            this.z = state;
            this.f = 0;
            this.x.dispatch(context, this);
            return;
        }
        if (s0.a()) {
        }
        j1 eventLoop$iv = x2.a.b();
        if (eventLoop$iv.c1()) {
            this.z = state;
            this.f = 0;
            eventLoop$iv.F0(this);
            return;
        }
        eventLoop$iv.a1(true);
        try {
            context$iv = getContext();
            oldValue$iv = j0.c(context$iv, this.p0);
            this.y.resumeWith(obj);
            x xVar = x.a;
            j0.a(context$iv, oldValue$iv);
            do {
            } while (eventLoop$iv.f1());
        } catch (Throwable e$iv$iv) {
            try {
                i(e$iv$iv, (Throwable) null);
            } catch (Throwable th) {
                Throwable th2 = th;
                eventLoop$iv.W(true);
                throw th2;
            }
        }
        eventLoop$iv.W(true);
    }

    public void a(@Nullable Object takenState, @NotNull Throwable cause) {
        if (takenState instanceof c0) {
            ((c0) takenState).b.invoke(cause);
        }
    }

    public final void m(@NotNull g context, T value) {
        this.z = value;
        this.f = 1;
        this.x.dispatchYield(context, this);
    }

    @NotNull
    public String toString() {
        return "DispatchedContinuation[" + this.x + ", " + t0.c(this.y) + ']';
    }
}
