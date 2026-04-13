package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.b;
import kotlin.coroutines.intrinsics.c;
import kotlin.l;
import kotlinx.coroutines.internal.d0;
import kotlinx.coroutines.internal.j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\b\u0000\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\b\u0012\u0004\u0012\u00028\u00000\u0015B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u0019\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0014¢\u0006\u0004\b\u000b\u0010\fJ\u0019\u0010\r\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0014¢\u0006\u0004\b\r\u0010\fJ\u000f\u0010\u000e\u001a\u0004\u0018\u00010\b¢\u0006\u0004\b\u000e\u0010\u000fJ\u000f\u0010\u0011\u001a\u00020\u0010H\u0002¢\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0013\u001a\u00020\u0010H\u0002¢\u0006\u0004\b\u0013\u0010\u0012¨\u0006\u0014"}, d2 = {"Lkotlinx/coroutines/DispatchedCoroutine;", "T", "Lkotlin/coroutines/CoroutineContext;", "context", "Lkotlin/coroutines/Continuation;", "uCont", "<init>", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/Continuation;)V", "", "state", "", "afterCompletion", "(Ljava/lang/Object;)V", "afterResume", "getResult", "()Ljava/lang/Object;", "", "tryResume", "()Z", "trySuspend", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/ScopeCoroutine;"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: Builders.common.kt */
public final class a1<T> extends d0<T> {
    private static final /* synthetic */ AtomicIntegerFieldUpdater q = AtomicIntegerFieldUpdater.newUpdater(a1.class, "_decision");
    @NotNull
    private volatile /* synthetic */ int _decision = 0;

    public a1(@NotNull g context, @NotNull d<? super T> uCont) {
        super(context, uCont);
    }

    private final boolean Y0() {
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

    private final boolean X0() {
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

    /* access modifiers changed from: protected */
    public void N(@Nullable Object state) {
        R0(state);
    }

    /* access modifiers changed from: protected */
    public void R0(@Nullable Object state) {
        if (!X0()) {
            j.c(b.c(this.f), e0.a(state, this.f), (kotlin.jvm.functions.l) null, 2, (Object) null);
        }
    }

    @Nullable
    public final Object W0() {
        if (Y0()) {
            return c.d();
        }
        Object state = h2.h(l0());
        if (!(state instanceof b0)) {
            return state;
        }
        throw ((b0) state).b;
    }
}
