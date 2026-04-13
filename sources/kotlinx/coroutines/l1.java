package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.l;
import kotlinx.coroutines.k1;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b \u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014J\b\u0010\r\u001a\u00020\bH\u0004R\u0012\u0010\u0003\u001a\u00020\u0004X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/EventLoopImplPlatform;", "Lkotlinx/coroutines/EventLoop;", "()V", "thread", "Ljava/lang/Thread;", "getThread", "()Ljava/lang/Thread;", "reschedule", "", "now", "", "delayedTask", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "unpark", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: EventLoop.kt */
public abstract class l1 extends j1 {
    /* access modifiers changed from: protected */
    @NotNull
    public abstract Thread getThread();

    /* access modifiers changed from: protected */
    public final void i1() {
        Thread thread = getThread();
        if (Thread.currentThread() != thread) {
            b a = c.a();
            if (a == null) {
                LockSupport.unpark(thread);
            } else {
                a.f(thread);
                throw null;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void h1(long now, @NotNull k1.c delayedTask) {
        u0.y.r1(now, delayedTask);
    }
}
