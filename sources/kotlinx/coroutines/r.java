package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.l;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\b\u0000\u0018\u00002\u00020\fB%\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0001\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bJ\r\u0010\t\u001a\u00020\u0005¢\u0006\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lkotlinx/coroutines/CancelledContinuation;", "Lkotlin/coroutines/Continuation;", "continuation", "", "cause", "", "handled", "<init>", "(Lkotlin/coroutines/Continuation;Ljava/lang/Throwable;Z)V", "makeResumed", "()Z", "kotlinx-coroutines-core", "Lkotlinx/coroutines/CompletedExceptionally;"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: CompletionState.kt */
public final class r extends b0 {
    private static final /* synthetic */ AtomicIntegerFieldUpdater c = AtomicIntegerFieldUpdater.newUpdater(r.class, "_resumed");
    @NotNull
    private volatile /* synthetic */ int _resumed;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public r(@org.jetbrains.annotations.NotNull kotlin.coroutines.d<?> r4, @org.jetbrains.annotations.Nullable java.lang.Throwable r5, boolean r6) {
        /*
            r3 = this;
            if (r5 != 0) goto L_0x001e
            java.util.concurrent.CancellationException r0 = new java.util.concurrent.CancellationException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Continuation "
            r1.append(r2)
            r1.append(r4)
            java.lang.String r2 = " was cancelled normally"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            goto L_0x001f
        L_0x001e:
            r0 = r5
        L_0x001f:
            r3.<init>(r0, r6)
            r0 = 0
            r3._resumed = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.r.<init>(kotlin.coroutines.d, java.lang.Throwable, boolean):void");
    }

    public final boolean c() {
        return c.compareAndSet(this, 0, 1);
    }
}
