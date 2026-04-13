package kotlinx.coroutines.flow;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.coroutines.d;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.o;
import kotlin.x;
import kotlinx.coroutines.flow.internal.b;
import kotlinx.coroutines.flow.internal.c;
import kotlinx.coroutines.n;
import kotlinx.coroutines.s0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\b\u0002\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0013B\u0007¢\u0006\u0004\b\u0001\u0010\u0002J\u001b\u0010\u0006\u001a\u00020\u00052\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0016¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\t\u001a\u00020\bH@ø\u0001\u0000¢\u0006\u0004\b\t\u0010\nJ)\u0010\r\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\b\u0018\u00010\f0\u000b2\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0016¢\u0006\u0004\b\r\u0010\u000eJ\r\u0010\u000f\u001a\u00020\b¢\u0006\u0004\b\u000f\u0010\u0002J\r\u0010\u0010\u001a\u00020\u0005¢\u0006\u0004\b\u0010\u0010\u0011\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, d2 = {"Lkotlinx/coroutines/flow/StateFlowSlot;", "<init>", "()V", "Lkotlinx/coroutines/flow/StateFlowImpl;", "flow", "", "allocateLocked", "(Lkotlinx/coroutines/flow/StateFlowImpl;)Z", "", "awaitPending", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "", "Lkotlin/coroutines/Continuation;", "freeLocked", "(Lkotlinx/coroutines/flow/StateFlowImpl;)[Lkotlin/coroutines/Continuation;", "makePending", "takePending", "()Z", "kotlinx-coroutines-core", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: StateFlow.kt */
public final class a0 extends c<y<?>> {
    static final /* synthetic */ AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(a0.class, Object.class, "_state");
    @NotNull
    volatile /* synthetic */ Object _state = null;

    /* renamed from: c */
    public boolean a(@NotNull y<?> flow) {
        if (this._state != null) {
            return false;
        }
        this._state = z.a;
        return true;
    }

    @NotNull
    /* renamed from: e */
    public d<x>[] b(@NotNull y<?> flow) {
        this._state = null;
        return b.a;
    }

    public final void f() {
        while (true) {
            Object state = this._state;
            if (state != null && state != z.b) {
                if (state == z.a) {
                    if (a.compareAndSet(this, state, z.b)) {
                        return;
                    }
                } else if (a.compareAndSet(this, state, z.a)) {
                    o.a aVar = o.Companion;
                    ((kotlinx.coroutines.o) state).resumeWith(o.m17constructorimpl(x.a));
                    return;
                }
            } else {
                return;
            }
        }
    }

    public final boolean g() {
        Object state = a.getAndSet(this, z.a);
        k.c(state);
        if (s0.a() && ((state instanceof kotlinx.coroutines.o) ^ 1) == 0) {
            throw new AssertionError();
        } else if (state == z.b) {
            return true;
        } else {
            return false;
        }
    }

    @Nullable
    public final Object d(@NotNull d<? super x> $completion) {
        boolean z = true;
        kotlinx.coroutines.o cancellable$iv = new kotlinx.coroutines.o(kotlin.coroutines.intrinsics.b.c($completion), 1);
        cancellable$iv.w();
        n cont = cancellable$iv;
        if (!s0.a() || ((this._state instanceof kotlinx.coroutines.o) ^ 1) != 0) {
            if (!a.compareAndSet(this, z.a, cont)) {
                if (s0.a()) {
                    if (this._state != z.b) {
                        z = false;
                    }
                    if (!z) {
                        throw new AssertionError();
                    }
                }
                o.a aVar = o.Companion;
                cont.resumeWith(o.m17constructorimpl(x.a));
            }
            Object t = cancellable$iv.t();
            if (t == kotlin.coroutines.intrinsics.c.d()) {
                h.c($completion);
            }
            return t == kotlin.coroutines.intrinsics.c.d() ? t : x.a;
        }
        throw new AssertionError();
    }
}
