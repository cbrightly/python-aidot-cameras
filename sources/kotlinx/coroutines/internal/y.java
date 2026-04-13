package kotlinx.coroutines.internal;

import kotlin.KotlinNothingValueException;
import kotlin.coroutines.g;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.f1;
import kotlinx.coroutines.i0;
import kotlinx.coroutines.k2;
import kotlinx.coroutines.n;
import kotlinx.coroutines.y0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u001b\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0019\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH@ø\u0001\u0000¢\u0006\u0002\u0010\u000fJ\u001c\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00122\n\u0010\u0013\u001a\u00060\u0014j\u0002`\u0015H\u0016J$\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000e2\n\u0010\u0013\u001a\u00060\u0014j\u0002`\u00152\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\fH\u0002J\u001e\u0010 \u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u000e2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"H\u0016J\b\u0010$\u001a\u00020\u0006H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\u00018VX\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n\u0002\u0004\n\u0002\b\u0019¨\u0006%"}, d2 = {"Lkotlinx/coroutines/internal/MissingMainCoroutineDispatcher;", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "Lkotlinx/coroutines/Delay;", "cause", "", "errorHint", "", "(Ljava/lang/Throwable;Ljava/lang/String;)V", "immediate", "getImmediate", "()Lkotlinx/coroutines/MainCoroutineDispatcher;", "delay", "", "time", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "dispatch", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "isDispatchNeeded", "", "limitedParallelism", "Lkotlinx/coroutines/CoroutineDispatcher;", "parallelism", "", "missing", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "", "toString", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: MainDispatchers.kt */
public final class y extends k2 implements y0 {
    @Nullable
    private final Throwable c;
    @Nullable
    private final String d;

    public y(@Nullable Throwable cause, @Nullable String errorHint) {
        this.c = cause;
        this.d = errorHint;
    }

    @NotNull
    public k2 W() {
        return this;
    }

    public boolean isDispatchNeeded(@NotNull g context) {
        F0();
        throw new KotlinNothingValueException();
    }

    @NotNull
    public i0 limitedParallelism(int parallelism) {
        F0();
        throw new KotlinNothingValueException();
    }

    @NotNull
    public f1 l(long timeMillis, @NotNull Runnable block, @NotNull g context) {
        F0();
        throw new KotlinNothingValueException();
    }

    @NotNull
    /* renamed from: u0 */
    public Void dispatch(@NotNull g context, @NotNull Runnable block) {
        F0();
        throw new KotlinNothingValueException();
    }

    @NotNull
    /* renamed from: P0 */
    public Void j(long timeMillis, @NotNull n<? super x> continuation) {
        F0();
        throw new KotlinNothingValueException();
    }

    private final Void F0() {
        String it;
        if (this.c != null) {
            String it2 = this.d;
            String str = "";
            if (!(it2 == null || (it = k.l(". ", it2)) == null)) {
                str = it;
            }
            throw new IllegalStateException(k.l("Module with the Main dispatcher had failed to initialize", str), this.c);
        }
        x.d();
        throw new KotlinNothingValueException();
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Dispatchers.Main[missing");
        Throwable th = this.c;
        sb.append(th != null ? k.l(", cause=", th) : "");
        sb.append(']');
        return sb.toString();
    }
}
