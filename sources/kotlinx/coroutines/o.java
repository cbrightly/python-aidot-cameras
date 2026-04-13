package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.KotlinNothingValueException;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.e;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.internal.e0;
import kotlinx.coroutines.internal.f0;
import kotlinx.coroutines.internal.i;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000¶\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0011\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\t\u0012\u0004\u0012\u00028\u00000\u00012\t\u0012\u0004\u0012\u00028\u00000\u00012\u00060tj\u0002`uB\u001d\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u0019\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0002¢\u0006\u0004\b\u000b\u0010\fJ\u001f\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\r2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000f¢\u0006\u0004\b\u0012\u0010\u0013JB\u0010\u0012\u001a\u00020\u00112'\u0010\u000e\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u000f¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u00110\u0014j\u0002`\u00172\b\u0010\u0010\u001a\u0004\u0018\u00010\u000fH\u0002¢\u0006\u0004\b\u0012\u0010\u0018J\u001e\u0010\u001b\u001a\u00020\u00112\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00110\u0019H\b¢\u0006\u0004\b\u001b\u0010\u001cJ8\u0010\u001e\u001a\u00020\u00112!\u0010\u001d\u001a\u001d\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u00110\u00142\u0006\u0010\u0010\u001a\u00020\u000f¢\u0006\u0004\b\u001e\u0010\u0018J\u0019\u0010 \u001a\u00020\u001f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000fH\u0016¢\u0006\u0004\b \u0010!J!\u0010%\u001a\u00020\u00112\b\u0010\"\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0010\u001a\u00020\u000fH\u0010¢\u0006\u0004\b#\u0010$J\u0017\u0010&\u001a\u00020\u001f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0002¢\u0006\u0004\b&\u0010!J\u0017\u0010(\u001a\u00020\u00112\u0006\u0010'\u001a\u00020\bH\u0016¢\u0006\u0004\b(\u0010)J\u000f\u0010,\u001a\u00020\u0011H\u0000¢\u0006\u0004\b*\u0010+J\u000f\u0010-\u001a\u00020\u0011H\u0002¢\u0006\u0004\b-\u0010+J\u0017\u0010/\u001a\u00020\u00112\u0006\u0010.\u001a\u00020\u0004H\u0002¢\u0006\u0004\b/\u00100J\u0017\u00103\u001a\u00020\u000f2\u0006\u00102\u001a\u000201H\u0016¢\u0006\u0004\b3\u00104J\u001b\u00108\u001a\u0004\u0018\u00010\u000f2\b\u00105\u001a\u0004\u0018\u00010\bH\u0010¢\u0006\u0004\b6\u00107J\u0011\u00109\u001a\u0004\u0018\u00010\bH\u0001¢\u0006\u0004\b9\u0010:J\u0017\u0010=\u001a\n\u0018\u00010;j\u0004\u0018\u0001`<H\u0016¢\u0006\u0004\b=\u0010>J\u001f\u0010A\u001a\u00028\u0001\"\u0004\b\u0001\u0010\u00012\b\u00105\u001a\u0004\u0018\u00010\bH\u0010¢\u0006\u0004\b?\u0010@J\u000f\u0010B\u001a\u00020\u0011H\u0016¢\u0006\u0004\bB\u0010+J\u0011\u0010D\u001a\u0004\u0018\u00010CH\u0002¢\u0006\u0004\bD\u0010EJ8\u0010F\u001a\u00020\u00112'\u0010\u000e\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u000f¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u00110\u0014j\u0002`\u0017H\u0016¢\u0006\u0004\bF\u0010GJ\u000f\u0010H\u001a\u00020\u001fH\u0002¢\u0006\u0004\bH\u0010IJ8\u0010J\u001a\u00020\r2'\u0010\u000e\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u000f¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u00110\u0014j\u0002`\u0017H\u0002¢\u0006\u0004\bJ\u0010KJB\u0010L\u001a\u00020\u00112'\u0010\u000e\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u000f¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u00110\u0014j\u0002`\u00172\b\u00105\u001a\u0004\u0018\u00010\bH\u0002¢\u0006\u0004\bL\u0010MJ\u000f\u0010O\u001a\u00020NH\u0014¢\u0006\u0004\bO\u0010PJ\u0017\u0010S\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u000fH\u0000¢\u0006\u0004\bQ\u0010RJ\u000f\u0010T\u001a\u00020\u0011H\u0002¢\u0006\u0004\bT\u0010+J\u000f\u0010U\u001a\u00020\u001fH\u0001¢\u0006\u0004\bU\u0010IJ<\u0010W\u001a\u00020\u00112\u0006\u0010V\u001a\u00028\u00002#\u0010\u001d\u001a\u001f\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0014H\u0016¢\u0006\u0004\bW\u0010XJH\u0010Y\u001a\u00020\u00112\b\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u00042%\b\u0002\u0010\u001d\u001a\u001f\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0014H\u0002¢\u0006\u0004\bY\u0010ZJ \u0010]\u001a\u00020\u00112\f\u0010\\\u001a\b\u0012\u0004\u0012\u00028\u00000[H\u0016ø\u0001\u0000¢\u0006\u0004\b]\u0010)JZ\u0010`\u001a\u0004\u0018\u00010\b2\u0006\u00105\u001a\u00020^2\b\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u00042#\u0010\u001d\u001a\u001f\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u00142\b\u0010_\u001a\u0004\u0018\u00010\bH\u0002¢\u0006\u0004\b`\u0010aJ\u0011\u0010c\u001a\u0004\u0018\u00010\bH\u0010¢\u0006\u0004\bb\u0010:J\u000f\u0010d\u001a\u00020NH\u0016¢\u0006\u0004\bd\u0010PJ\u000f\u0010e\u001a\u00020\u001fH\u0002¢\u0006\u0004\be\u0010IJ#\u0010e\u001a\u0004\u0018\u00010\b2\u0006\u0010V\u001a\u00028\u00002\b\u0010_\u001a\u0004\u0018\u00010\bH\u0016¢\u0006\u0004\be\u0010fJH\u0010e\u001a\u0004\u0018\u00010\b2\u0006\u0010V\u001a\u00028\u00002\b\u0010_\u001a\u0004\u0018\u00010\b2#\u0010\u001d\u001a\u001f\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0014H\u0016¢\u0006\u0004\be\u0010gJJ\u0010i\u001a\u0004\u0018\u00010h2\b\u0010\t\u001a\u0004\u0018\u00010\b2\b\u0010_\u001a\u0004\u0018\u00010\b2#\u0010\u001d\u001a\u001f\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0014H\u0002¢\u0006\u0004\bi\u0010jJ\u0019\u0010l\u001a\u0004\u0018\u00010\b2\u0006\u0010k\u001a\u00020\u000fH\u0016¢\u0006\u0004\bl\u0010mJ\u000f\u0010n\u001a\u00020\u001fH\u0002¢\u0006\u0004\bn\u0010IJ\u001b\u0010p\u001a\u00020\u0011*\u00020o2\u0006\u0010V\u001a\u00028\u0000H\u0016¢\u0006\u0004\bp\u0010qJ\u001b\u0010r\u001a\u00020\u0011*\u00020o2\u0006\u0010k\u001a\u00020\u000fH\u0016¢\u0006\u0004\br\u0010sR\u001c\u0010x\u001a\n\u0018\u00010tj\u0004\u0018\u0001`u8VX\u0004¢\u0006\u0006\u001a\u0004\bv\u0010wR\u001a\u0010z\u001a\u00020y8\u0016X\u0004¢\u0006\f\n\u0004\bz\u0010{\u001a\u0004\b|\u0010}R!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u00028\u0000X\u0004¢\u0006\r\n\u0004\b\u0003\u0010~\u001a\u0005\b\u0010\u0001R\u0016\u0010\u0001\u001a\u00020\u001f8VX\u0004¢\u0006\u0007\u001a\u0005\b\u0001\u0010IR\u0016\u0010\u0001\u001a\u00020\u001f8VX\u0004¢\u0006\u0007\u001a\u0005\b\u0001\u0010IR\u0016\u0010\u0001\u001a\u00020\u001f8VX\u0004¢\u0006\u0007\u001a\u0005\b\u0001\u0010IR\u001b\u0010\u0001\u001a\u0004\u0018\u00010C8\u0002@\u0002X\u000e¢\u0006\b\n\u0006\b\u0001\u0010\u0001R\u0017\u00105\u001a\u0004\u0018\u00010\b8@X\u0004¢\u0006\u0007\u001a\u0005\b\u0001\u0010:R\u0016\u0010\u0001\u001a\u00020N8BX\u0004¢\u0006\u0007\u001a\u0005\b\u0001\u0010P\u0002\u0004\n\u0002\b\u0019¨\u0006\u0001"}, d2 = {"Lkotlinx/coroutines/CancellableContinuationImpl;", "T", "Lkotlin/coroutines/Continuation;", "delegate", "", "resumeMode", "<init>", "(Lkotlin/coroutines/Continuation;I)V", "", "proposedUpdate", "", "alreadyResumedError", "(Ljava/lang/Object;)Ljava/lang/Void;", "Lkotlinx/coroutines/CancelHandler;", "handler", "", "cause", "", "callCancelHandler", "(Lkotlinx/coroutines/CancelHandler;Ljava/lang/Throwable;)V", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;", "(Lkotlin/jvm/functions/Function1;Ljava/lang/Throwable;)V", "Lkotlin/Function0;", "block", "callCancelHandlerSafely", "(Lkotlin/jvm/functions/Function0;)V", "onCancellation", "callOnCancellation", "", "cancel", "(Ljava/lang/Throwable;)Z", "takenState", "cancelCompletedResult$kotlinx_coroutines_core", "(Ljava/lang/Object;Ljava/lang/Throwable;)V", "cancelCompletedResult", "cancelLater", "token", "completeResume", "(Ljava/lang/Object;)V", "detachChild$kotlinx_coroutines_core", "()V", "detachChild", "detachChildIfNonResuable", "mode", "dispatchResume", "(I)V", "Lkotlinx/coroutines/Job;", "parent", "getContinuationCancellationCause", "(Lkotlinx/coroutines/Job;)Ljava/lang/Throwable;", "state", "getExceptionalResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Throwable;", "getExceptionalResult", "getResult", "()Ljava/lang/Object;", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "getStackTraceElement", "()Ljava/lang/StackTraceElement;", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "getSuccessfulResult", "initCancellability", "Lkotlinx/coroutines/DisposableHandle;", "installParentHandle", "()Lkotlinx/coroutines/DisposableHandle;", "invokeOnCancellation", "(Lkotlin/jvm/functions/Function1;)V", "isReusable", "()Z", "makeCancelHandler", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/CancelHandler;", "multipleHandlersError", "(Lkotlin/jvm/functions/Function1;Ljava/lang/Object;)V", "", "nameString", "()Ljava/lang/String;", "parentCancelled$kotlinx_coroutines_core", "(Ljava/lang/Throwable;)V", "parentCancelled", "releaseClaimedReusableContinuation", "resetStateReusable", "value", "resume", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "resumeImpl", "(Ljava/lang/Object;ILkotlin/jvm/functions/Function1;)V", "Lkotlin/Result;", "result", "resumeWith", "Lkotlinx/coroutines/NotCompleted;", "idempotent", "resumedState", "(Lkotlinx/coroutines/NotCompleted;Ljava/lang/Object;ILkotlin/jvm/functions/Function1;Ljava/lang/Object;)Ljava/lang/Object;", "takeState$kotlinx_coroutines_core", "takeState", "toString", "tryResume", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "Lkotlinx/coroutines/internal/Symbol;", "tryResumeImpl", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/internal/Symbol;", "exception", "tryResumeWithException", "(Ljava/lang/Throwable;)Ljava/lang/Object;", "trySuspend", "Lkotlinx/coroutines/CoroutineDispatcher;", "resumeUndispatched", "(Lkotlinx/coroutines/CoroutineDispatcher;Ljava/lang/Object;)V", "resumeUndispatchedWithException", "(Lkotlinx/coroutines/CoroutineDispatcher;Ljava/lang/Throwable;)V", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "callerFrame", "Lkotlin/coroutines/CoroutineContext;", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "Lkotlin/coroutines/Continuation;", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "isActive", "isCancelled", "isCompleted", "parentHandle", "Lkotlinx/coroutines/DisposableHandle;", "getState$kotlinx_coroutines_core", "getStateDebugRepresentation", "stateDebugRepresentation", "kotlinx-coroutines-core", "Lkotlinx/coroutines/DispatchedTask;", "Lkotlinx/coroutines/CancellableContinuation;"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: CancellableContinuationImpl.kt */
public class o<T> extends b1<T> implements n<T>, e {
    private static final /* synthetic */ AtomicIntegerFieldUpdater q = AtomicIntegerFieldUpdater.newUpdater(o.class, "_decision");
    private static final /* synthetic */ AtomicReferenceFieldUpdater x = AtomicReferenceFieldUpdater.newUpdater(o.class, Object.class, "_state");
    @NotNull
    private volatile /* synthetic */ int _decision;
    @NotNull
    private volatile /* synthetic */ Object _state;
    @Nullable
    private f1 p0;
    @NotNull
    private final d<T> y;
    @NotNull
    private final g z;

    @NotNull
    public final d<T> c() {
        return this.y;
    }

    public o(@NotNull d<? super T> delegate, int resumeMode) {
        super(resumeMode);
        this.y = delegate;
        if (s0.a()) {
            if (!(resumeMode != -1)) {
                throw new AssertionError();
            }
        }
        this.z = delegate.getContext();
        this._decision = 0;
        this._state = d.c;
    }

    @NotNull
    public g getContext() {
        return this.z;
    }

    @Nullable
    public final Object u() {
        return this._state;
    }

    public boolean isActive() {
        return u() instanceof n2;
    }

    public boolean y() {
        return !(u() instanceof n2);
    }

    public boolean isCancelled() {
        return u() instanceof r;
    }

    private final String v() {
        Object u = u();
        if (u instanceof n2) {
            return "Active";
        }
        if (u instanceof r) {
            return "Cancelled";
        }
        return "Completed";
    }

    public void w() {
        f1 handle = x();
        if (handle != null && y()) {
            handle.dispose();
            this.p0 = m2.c;
        }
    }

    private final boolean z() {
        return c1.c(this.f) && ((i) this.y).o();
    }

    public final boolean I() {
        if (s0.a()) {
            if ((this.f == 2 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        if (s0.a()) {
            if ((this.p0 != m2.c ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        Object state = this._state;
        if (s0.a() && ((state instanceof n2) ^ 1) == 0) {
            throw new AssertionError();
        } else if (!(state instanceof a0) || ((a0) state).d == null) {
            this._decision = 0;
            this._state = d.c;
            return true;
        } else {
            p();
            return false;
        }
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

    @Nullable
    public Object j() {
        return u();
    }

    public void a(@Nullable Object takenState, @NotNull Throwable cause) {
        while (true) {
            Object state = this._state;
            if (state instanceof n2) {
                Throwable th = cause;
                throw new IllegalStateException("Not completed".toString());
            } else if (!(state instanceof b0)) {
                if (!(state instanceof a0)) {
                    Throwable th2 = cause;
                    if (x.compareAndSet(this, state, new a0(state, (l) null, (kotlin.jvm.functions.l) null, (Object) null, cause, 14, (DefaultConstructorMarker) null))) {
                        return;
                    }
                } else if (!((a0) state).c()) {
                    if (x.compareAndSet(this, state, a0.b((a0) state, (Object) null, (l) null, (kotlin.jvm.functions.l) null, (Object) null, cause, 15, (Object) null))) {
                        ((a0) state).d(this, cause);
                        return;
                    }
                    Throwable th3 = cause;
                } else {
                    Throwable th4 = cause;
                    throw new IllegalStateException("Must be called at most once".toString());
                }
            } else {
                return;
            }
        }
    }

    private final boolean o(Throwable cause) {
        if (!z()) {
            return false;
        }
        return ((i) this.y).p(cause);
    }

    public boolean b(@Nullable Throwable cause) {
        Object state;
        do {
            state = this._state;
            if (!(state instanceof n2)) {
                return false;
            }
        } while (!x.compareAndSet(this, state, new r(this, cause, state instanceof l)));
        l it = state instanceof l ? (l) state : null;
        if (it != null) {
            m(it, cause);
        }
        q();
        r(this.f);
        return true;
    }

    public final void F(@NotNull Throwable cause) {
        if (!o(cause)) {
            b(cause);
            q();
        }
    }

    private final void l(kotlin.jvm.functions.l<? super Throwable, x> handler, Throwable cause) {
        try {
            handler.invoke(cause);
        } catch (Throwable ex$iv) {
            l0.a(getContext(), new CompletionHandlerException(k.l("Exception in invokeOnCancellation handler for ", this), ex$iv));
        }
    }

    public final void m(@NotNull l handler, @Nullable Throwable cause) {
        try {
            handler.a(cause);
        } catch (Throwable ex$iv) {
            l0.a(getContext(), new CompletionHandlerException(k.l("Exception in invokeOnCancellation handler for ", this), ex$iv));
        }
    }

    public final void n(@NotNull kotlin.jvm.functions.l<? super Throwable, x> onCancellation, @NotNull Throwable cause) {
        try {
            onCancellation.invoke(cause);
        } catch (Throwable ex) {
            l0.a(getContext(), new CompletionHandlerException(k.l("Exception in resume onCancellation handler for ", this), ex));
        }
    }

    @NotNull
    public Throwable s(@NotNull z1 parent) {
        return parent.n();
    }

    private final boolean O() {
        do {
            switch (this._decision) {
                case 0:
                    break;
                case 2:
                    return false;
                default:
                    throw new IllegalStateException("Already suspended".toString());
            }
        } while (!q.compareAndSet(this, 0, 1));
        return true;
    }

    private final boolean M() {
        do {
            switch (this._decision) {
                case 0:
                    break;
                case 1:
                    return false;
                default:
                    throw new IllegalStateException("Already resumed".toString());
            }
        } while (!q.compareAndSet(this, 0, 2));
        return true;
    }

    @Nullable
    public final Object t() {
        z1 job;
        Throwable th;
        boolean isReusable = z();
        if (O()) {
            if (this.p0 == null) {
                x();
            }
            if (isReusable) {
                H();
            }
            return c.d();
        }
        if (isReusable) {
            H();
        }
        Object state = u();
        if (state instanceof b0) {
            Throwable exception$iv = ((b0) state).b;
            if (s0.d()) {
                exception$iv = e0.j(exception$iv, this);
            }
            throw exception$iv;
        } else if (!c1.b(this.f) || (job = (z1) getContext().get(z1.g)) == null || job.isActive()) {
            return g(state);
        } else {
            CancellationException cause = job.n();
            a(state, cause);
            if (s0.d()) {
                th = e0.j(cause, this);
            } else {
                th = cause;
            }
            throw th;
        }
    }

    private final f1 x() {
        z1 parent = (z1) getContext().get(z1.g);
        if (parent == null) {
            return null;
        }
        f1 handle = z1.a.d(parent, true, false, new s(this), 2, (Object) null);
        this.p0 = handle;
        return handle;
    }

    private final void H() {
        d<T> dVar = this.y;
        Throwable cancellationCause = null;
        i iVar = dVar instanceof i ? (i) dVar : null;
        if (iVar != null) {
            cancellationCause = iVar.r(this);
        }
        if (cancellationCause != null) {
            p();
            b(cancellationCause);
        }
    }

    public void resumeWith(@NotNull Object result) {
        K(this, e0.c(result, this), this.f, (kotlin.jvm.functions.l) null, 4, (Object) null);
    }

    public void h(T value, @Nullable kotlin.jvm.functions.l<? super Throwable, x> onCancellation) {
        J(value, this.f, onCancellation);
    }

    public void f(@NotNull kotlin.jvm.functions.l<? super Throwable, x> handler) {
        l cancelHandler = C(handler);
        while (true) {
            Object state = this._state;
            if (state instanceof d) {
                if (x.compareAndSet(this, state, cancelHandler)) {
                    return;
                }
            } else if (state instanceof l) {
                D(handler, state);
            } else if (state instanceof b0) {
                if (!((b0) state).b()) {
                    D(handler, state);
                }
                if (state instanceof r) {
                    Throwable th = null;
                    b0 b0Var = state instanceof b0 ? (b0) state : null;
                    if (b0Var != null) {
                        th = b0Var.b;
                    }
                    l(handler, th);
                    return;
                }
                return;
            } else if (state instanceof a0) {
                if (((a0) state).b != null) {
                    D(handler, state);
                }
                if (!(cancelHandler instanceof e)) {
                    if (((a0) state).c()) {
                        l(handler, ((a0) state).e);
                        return;
                    } else {
                        if (x.compareAndSet(this, state, a0.b((a0) state, (Object) null, cancelHandler, (kotlin.jvm.functions.l) null, (Object) null, (Throwable) null, 29, (Object) null))) {
                            return;
                        }
                    }
                } else {
                    return;
                }
            } else if (!(cancelHandler instanceof e)) {
                if (x.compareAndSet(this, state, new a0(state, cancelHandler, (kotlin.jvm.functions.l) null, (Object) null, (Throwable) null, 28, (DefaultConstructorMarker) null))) {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private final void D(kotlin.jvm.functions.l<? super Throwable, x> handler, Object state) {
        throw new IllegalStateException(("It's prohibited to register multiple handlers, tried to register " + handler + ", already has " + state).toString());
    }

    private final l C(kotlin.jvm.functions.l<? super Throwable, x> handler) {
        return handler instanceof l ? (l) handler : new w1(handler);
    }

    private final void r(int mode) {
        if (!M()) {
            c1.a(this, mode);
        }
    }

    private final Object L(n2 state, Object proposedUpdate, int resumeMode, kotlin.jvm.functions.l<? super Throwable, x> onCancellation, Object idempotent) {
        if (proposedUpdate instanceof b0) {
            boolean z2 = true;
            if (s0.a()) {
                if ((idempotent == null ? 1 : 0) == 0) {
                    throw new AssertionError();
                }
            }
            if (s0.a()) {
                if (onCancellation != null) {
                    z2 = false;
                }
                if (!z2) {
                    throw new AssertionError();
                }
            }
        } else if ((c1.b(resumeMode) || idempotent != null) && (onCancellation != null || (((state instanceof l) && !(state instanceof e)) || idempotent != null))) {
            return new a0(proposedUpdate, state instanceof l ? (l) state : null, onCancellation, idempotent, (Throwable) null, 16, (DefaultConstructorMarker) null);
        }
        return proposedUpdate;
    }

    static /* synthetic */ void K(o oVar, Object obj, int i, kotlin.jvm.functions.l lVar, int i2, Object obj2) {
        if (obj2 == null) {
            if ((i2 & 4) != 0) {
                lVar = null;
            }
            oVar.J(obj, i, lVar);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: resumeImpl");
    }

    private final void J(Object proposedUpdate, int resumeMode, kotlin.jvm.functions.l<? super Throwable, x> onCancellation) {
        Object state;
        do {
            state = this._state;
            if (state instanceof n2) {
            } else if (!(state instanceof r) || !((r) state).c()) {
                k(proposedUpdate);
                throw new KotlinNothingValueException();
            } else if (onCancellation != null) {
                n(onCancellation, ((r) state).b);
                return;
            } else {
                return;
            }
        } while (!x.compareAndSet(this, state, L((n2) state, proposedUpdate, resumeMode, onCancellation, (Object) null)));
        q();
        r(resumeMode);
    }

    private final f0 N(Object proposedUpdate, Object idempotent, kotlin.jvm.functions.l<? super Throwable, x> onCancellation) {
        Object state;
        do {
            state = this._state;
            if (state instanceof n2) {
            } else if (!(state instanceof a0) || idempotent == null || ((a0) state).d != idempotent) {
                return null;
            } else {
                if (!s0.a() || k.a(((a0) state).a, proposedUpdate) != 0) {
                    return p.a;
                }
                throw new AssertionError();
            }
        } while (!x.compareAndSet(this, state, L((n2) state, proposedUpdate, this.f, onCancellation, idempotent)));
        q();
        return p.a;
    }

    private final Void k(Object proposedUpdate) {
        throw new IllegalStateException(k.l("Already resumed, but proposed with update ", proposedUpdate).toString());
    }

    private final void q() {
        if (!z()) {
            p();
        }
    }

    public final void p() {
        f1 handle = this.p0;
        if (handle != null) {
            handle.dispose();
            this.p0 = m2.c;
        }
    }

    @Nullable
    public Object e(T value, @Nullable Object idempotent) {
        return N(value, idempotent, (kotlin.jvm.functions.l<? super Throwable, x>) null);
    }

    @Nullable
    public Object A(T value, @Nullable Object idempotent, @Nullable kotlin.jvm.functions.l<? super Throwable, x> onCancellation) {
        return N(value, idempotent, onCancellation);
    }

    public void G(@NotNull Object token) {
        if (s0.a()) {
            if (!(token == p.a)) {
                throw new AssertionError();
            }
        }
        r(this.f);
    }

    public void B(@NotNull i0 $this$resumeUndispatched, T value) {
        d<T> dVar = this.y;
        i0 i0Var = null;
        i dc = dVar instanceof i ? (i) dVar : null;
        if (dc != null) {
            i0Var = dc.x;
        }
        K(this, value, i0Var == $this$resumeUndispatched ? 4 : this.f, (kotlin.jvm.functions.l) null, 4, (Object) null);
    }

    public <T> T g(@Nullable Object state) {
        if (state instanceof a0) {
            return ((a0) state).a;
        }
        return state;
    }

    @Nullable
    public Throwable d(@Nullable Object state) {
        Throwable it;
        Throwable it2 = super.d(state);
        if (it2 == null) {
            return null;
        }
        d continuation$iv = c();
        if (!s0.d() || !(continuation$iv instanceof e)) {
            it = it2;
        } else {
            it = e0.j(it2, (e) continuation$iv);
        }
        return it;
    }

    @NotNull
    public String toString() {
        return E() + '(' + t0.c(this.y) + "){" + v() + "}@" + t0.b(this);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public String E() {
        return "CancellableContinuation";
    }
}
