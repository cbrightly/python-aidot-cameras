package kotlinx.coroutines;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.util.concurrent.locks.LockSupport;
import kotlin.coroutines.g;
import kotlin.jvm.internal.k;
import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0012\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014J\u000b\u0010\u0011\u001a\u00028\u0000¢\u0006\u0002\u0010\u0012R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8TX\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\f¨\u0006\u0013"}, d2 = {"Lkotlinx/coroutines/BlockingCoroutine;", "T", "Lkotlinx/coroutines/AbstractCoroutine;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "blockedThread", "Ljava/lang/Thread;", "eventLoop", "Lkotlinx/coroutines/EventLoop;", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Thread;Lkotlinx/coroutines/EventLoop;)V", "isScopedCoroutine", "", "()Z", "afterCompletion", "", "state", "", "joinBlocking", "()Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: Builders.kt */
public final class f<T> extends a<T> {
    @NotNull
    private final Thread f;
    @Nullable
    private final j1 q;

    public f(@NotNull g parentContext, @NotNull Thread blockedThread, @Nullable j1 eventLoop) {
        super(parentContext, true, true);
        this.f = blockedThread;
        this.q = eventLoop;
    }

    /* access modifiers changed from: protected */
    public boolean p0() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void N(@Nullable Object state) {
        if (!k.a(Thread.currentThread(), this.f)) {
            Thread thread = this.f;
            b a = c.a();
            if (a == null) {
                LockSupport.unpark(thread);
            } else {
                a.f(thread);
                throw null;
            }
        }
    }

    public final T V0() {
        b a = c.a();
        b0 it = null;
        if (a == null) {
            try {
                j1 j1Var = this.q;
                if (j1Var != null) {
                    j1.b1(j1Var, false, 1, (Object) null);
                }
                while (!Thread.interrupted()) {
                    j1 j1Var2 = this.q;
                    long parkNanos = j1Var2 == null ? DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE : j1Var2.e1();
                    if (I()) {
                        j1 j1Var3 = this.q;
                        if (j1Var3 != null) {
                            j1.o0(j1Var3, false, 1, (Object) null);
                        }
                        b a2 = c.a();
                        if (a2 == null) {
                            T h = h2.h(l0());
                            if (h instanceof b0) {
                                it = (b0) h;
                            }
                            if (it == null) {
                                return h;
                            }
                            throw it.b;
                        }
                        a2.g();
                        throw null;
                    }
                    b a3 = c.a();
                    if (a3 == null) {
                        LockSupport.parkNanos(this, parkNanos);
                    } else {
                        a3.b(this, parkNanos);
                        throw null;
                    }
                }
                InterruptedException it2 = new InterruptedException();
                R(it2);
                throw it2;
            } catch (Throwable th) {
                b a4 = c.a();
                if (a4 == null) {
                    throw th;
                }
                a4.g();
                throw null;
            }
        } else {
            a.c();
            throw null;
        }
    }
}
