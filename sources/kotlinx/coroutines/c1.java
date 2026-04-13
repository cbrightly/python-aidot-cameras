package kotlinx.coroutines;

import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.l;
import kotlin.o;
import kotlin.p;
import kotlin.x;
import kotlinx.coroutines.internal.i;
import kotlinx.coroutines.internal.j0;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000<\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a \u0010\f\u001a\u00020\r\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\u000f2\u0006\u0010\u0010\u001a\u00020\u0001H\u0000\u001a.\u0010\u0011\u001a\u00020\r\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\u000f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u00132\u0006\u0010\u0014\u001a\u00020\tH\u0000\u001a\u0010\u0010\u0015\u001a\u00020\r*\u0006\u0012\u0002\b\u00030\u000fH\u0002\u001a\u0019\u0010\u0016\u001a\u00020\r*\u0006\u0012\u0002\b\u00030\u00132\u0006\u0010\u0017\u001a\u00020\u0018H\b\u001a'\u0010\u0019\u001a\u00020\r*\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\u001a\u001a\u00020\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\r0\u001dH\b\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u0016\u0010\u0002\u001a\u00020\u00018\u0000XT¢\u0006\b\n\u0000\u0012\u0004\b\u0003\u0010\u0004\"\u000e\u0010\u0005\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u0018\u0010\b\u001a\u00020\t*\u00020\u00018@X\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\n\"\u0018\u0010\u000b\u001a\u00020\t*\u00020\u00018@X\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\n¨\u0006\u001e"}, d2 = {"MODE_ATOMIC", "", "MODE_CANCELLABLE", "getMODE_CANCELLABLE$annotations", "()V", "MODE_CANCELLABLE_REUSABLE", "MODE_UNDISPATCHED", "MODE_UNINITIALIZED", "isCancellableMode", "", "(I)Z", "isReusableMode", "dispatch", "", "T", "Lkotlinx/coroutines/DispatchedTask;", "mode", "resume", "delegate", "Lkotlin/coroutines/Continuation;", "undispatched", "resumeUnconfined", "resumeWithStackTrace", "exception", "", "runUnconfinedEventLoop", "eventLoop", "Lkotlinx/coroutines/EventLoop;", "block", "Lkotlin/Function0;", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: DispatchedTask.kt */
public final class c1 {
    public static final boolean b(int $this$isCancellableMode) {
        return $this$isCancellableMode == 1 || $this$isCancellableMode == 2;
    }

    public static final boolean c(int $this$isReusableMode) {
        return $this$isReusableMode == 2;
    }

    public static final <T> void a(@NotNull b1<? super T> $this$dispatch, int mode) {
        boolean undispatched = true;
        if (s0.a()) {
            if ((mode != -1 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        d<? super T> c = $this$dispatch.c();
        if (mode != 4) {
            undispatched = false;
        }
        if (undispatched || !(c instanceof i) || b(mode) != b($this$dispatch.f)) {
            d($this$dispatch, c, undispatched);
            return;
        }
        i0 dispatcher = ((i) c).x;
        g context = c.getContext();
        if (dispatcher.isDispatchNeeded(context)) {
            dispatcher.dispatch(context, $this$dispatch);
        } else {
            e($this$dispatch);
        }
    }

    public static final <T> void d(@NotNull b1<? super T> $this$resume, @NotNull d<? super T> delegate, boolean undispatched) {
        b3 undispatchedCompletion$iv$iv;
        Object state = $this$resume.j();
        Throwable exception = $this$resume.d(state);
        o.a aVar = o.Companion;
        Object result = o.m17constructorimpl(exception != null ? p.a(exception) : $this$resume.g(state));
        if (undispatched) {
            i this_$iv = (i) delegate;
            d continuation$iv$iv = this_$iv.y;
            Object countOrElement$iv$iv = this_$iv.p0;
            g context$iv$iv = continuation$iv$iv.getContext();
            Object oldValue$iv$iv = j0.c(context$iv$iv, countOrElement$iv$iv);
            if (oldValue$iv$iv != j0.a) {
                undispatchedCompletion$iv$iv = h0.g(continuation$iv$iv, context$iv$iv, oldValue$iv$iv);
            } else {
                undispatchedCompletion$iv$iv = null;
            }
            try {
                this_$iv.y.resumeWith(result);
                x xVar = x.a;
            } finally {
                if (undispatchedCompletion$iv$iv == null || undispatchedCompletion$iv$iv.W0()) {
                    j0.a(context$iv$iv, oldValue$iv$iv);
                }
            }
        } else {
            delegate.resumeWith(result);
        }
    }

    private static final void e(b1<?> $this$resumeUnconfined) {
        j1 eventLoop = x2.a.b();
        if (eventLoop.c1()) {
            eventLoop.F0($this$resumeUnconfined);
            return;
        }
        b1 $this$runUnconfinedEventLoop$iv = $this$resumeUnconfined;
        eventLoop.a1(true);
        try {
            d($this$resumeUnconfined, $this$resumeUnconfined.c(), true);
            do {
            } while (eventLoop.f1());
        } catch (Throwable th) {
            eventLoop.W(true);
            throw th;
        }
        eventLoop.W(true);
    }
}
