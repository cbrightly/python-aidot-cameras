package kotlinx.coroutines;

import kotlin.b;
import kotlin.coroutines.d;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlinx.coroutines.scheduling.j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u000e\b \u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u00060\u0002j\u0002`\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001f\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0010¢\u0006\u0002\b\u0011J\u0019\u0010\u0012\u001a\u0004\u0018\u00010\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u000eH\u0010¢\u0006\u0002\b\u0014J\u001f\u0010\u0015\u001a\u0002H\u0001\"\u0004\b\u0001\u0010\u00012\b\u0010\u0013\u001a\u0004\u0018\u00010\u000eH\u0010¢\u0006\u0004\b\u0016\u0010\u0017J\u001a\u0010\u0018\u001a\u00020\f2\b\u0010\u0019\u001a\u0004\u0018\u00010\u00102\b\u0010\u001a\u001a\u0004\u0018\u00010\u0010J\u0006\u0010\u001b\u001a\u00020\fJ\u000f\u0010\u001c\u001a\u0004\u0018\u00010\u000eH ¢\u0006\u0002\b\u001dR\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX \u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lkotlinx/coroutines/DispatchedTask;", "T", "Lkotlinx/coroutines/scheduling/Task;", "Lkotlinx/coroutines/SchedulerTask;", "resumeMode", "", "(I)V", "delegate", "Lkotlin/coroutines/Continuation;", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "cancelCompletedResult", "", "takenState", "", "cause", "", "cancelCompletedResult$kotlinx_coroutines_core", "getExceptionalResult", "state", "getExceptionalResult$kotlinx_coroutines_core", "getSuccessfulResult", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "handleFatalException", "exception", "finallyException", "run", "takeState", "takeState$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: DispatchedTask.kt */
public abstract class b1<T> extends j {
    public int f;

    @NotNull
    public abstract d<T> c();

    @Nullable
    public abstract Object j();

    public b1(int resumeMode) {
        this.f = resumeMode;
    }

    public void a(@Nullable Object takenState, @NotNull Throwable cause) {
    }

    public <T> T g(@Nullable Object state) {
        return state;
    }

    @Nullable
    public Throwable d(@Nullable Object state) {
        b0 b0Var = state instanceof b0 ? (b0) state : null;
        if (b0Var == null) {
            return null;
        }
        return b0Var.b;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00d6, code lost:
        if (r11.W0() != false) goto L_0x00d8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00f6, code lost:
        if (r11.W0() != false) goto L_0x00f8;
     */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00d2 A[SYNTHETIC, Splitter:B:46:0x00d2] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00f2 A[SYNTHETIC, Splitter:B:57:0x00f2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r20 = this;
            r1 = r20
            boolean r0 = kotlinx.coroutines.s0.a()
            if (r0 == 0) goto L_0x001a
            r0 = 0
            int r2 = r1.f
            r3 = -1
            if (r2 == r3) goto L_0x0010
            r2 = 1
            goto L_0x0011
        L_0x0010:
            r2 = 0
        L_0x0011:
            if (r2 == 0) goto L_0x0014
            goto L_0x001a
        L_0x0014:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x001a:
            kotlinx.coroutines.scheduling.k r2 = r1.d
            r3 = 0
            kotlin.coroutines.d r0 = r20.c()     // Catch:{ all -> 0x00fd }
            kotlinx.coroutines.internal.i r0 = (kotlinx.coroutines.internal.i) r0     // Catch:{ all -> 0x00fd }
            r4 = r0
            kotlin.coroutines.d<T> r0 = r4.y     // Catch:{ all -> 0x00fd }
            r5 = r0
            java.lang.Object r0 = r4.p0     // Catch:{ all -> 0x00fd }
            r6 = r0
            r7 = 0
            kotlin.coroutines.g r0 = r5.getContext()     // Catch:{ all -> 0x00fd }
            r8 = r0
            java.lang.Object r0 = kotlinx.coroutines.internal.j0.c(r8, r6)     // Catch:{ all -> 0x00fd }
            r9 = r0
            kotlinx.coroutines.internal.f0 r0 = kotlinx.coroutines.internal.j0.a     // Catch:{ all -> 0x00fd }
            r10 = 0
            if (r9 == r0) goto L_0x0040
            kotlinx.coroutines.b3 r0 = kotlinx.coroutines.h0.g(r5, r8, r9)     // Catch:{ all -> 0x00fd }
            goto L_0x0041
        L_0x0040:
            r0 = r10
        L_0x0041:
            r11 = r0
            r0 = 0
            kotlin.coroutines.g r12 = r5.getContext()     // Catch:{ all -> 0x00ed }
            java.lang.Object r13 = r20.j()     // Catch:{ all -> 0x00ed }
            java.lang.Throwable r14 = r1.d(r13)     // Catch:{ all -> 0x00ed }
            if (r14 != 0) goto L_0x0068
            int r15 = r1.f     // Catch:{ all -> 0x0063 }
            boolean r15 = kotlinx.coroutines.c1.b(r15)     // Catch:{ all -> 0x0063 }
            if (r15 == 0) goto L_0x0068
            kotlinx.coroutines.z1$b r10 = kotlinx.coroutines.z1.g     // Catch:{ all -> 0x0063 }
            kotlin.coroutines.g$b r10 = r12.get(r10)     // Catch:{ all -> 0x0063 }
            kotlinx.coroutines.z1 r10 = (kotlinx.coroutines.z1) r10     // Catch:{ all -> 0x0063 }
            goto L_0x0068
        L_0x0063:
            r0 = move-exception
            r16 = r4
            goto L_0x00f0
        L_0x0068:
            if (r10 == 0) goto L_0x00ac
            boolean r15 = r10.isActive()     // Catch:{ all -> 0x00ed }
            if (r15 != 0) goto L_0x00ac
            java.util.concurrent.CancellationException r15 = r10.n()     // Catch:{ all -> 0x00ed }
            r1.a(r13, r15)     // Catch:{ all -> 0x00ed }
            r16 = r5
            r17 = 0
            kotlin.o$a r18 = kotlin.o.Companion     // Catch:{ all -> 0x00ed }
            r18 = 0
            boolean r19 = kotlinx.coroutines.s0.d()     // Catch:{ all -> 0x00ed }
            if (r19 == 0) goto L_0x0098
            r19 = r0
            r0 = r16
            r16 = r4
            boolean r4 = r0 instanceof kotlin.coroutines.jvm.internal.e     // Catch:{ all -> 0x00eb }
            if (r4 != 0) goto L_0x0090
            goto L_0x009e
        L_0x0090:
            r4 = r0
            kotlin.coroutines.jvm.internal.e r4 = (kotlin.coroutines.jvm.internal.e) r4     // Catch:{ all -> 0x00eb }
            java.lang.Throwable r4 = kotlinx.coroutines.internal.e0.j(r15, r4)     // Catch:{ all -> 0x00eb }
            goto L_0x009f
        L_0x0098:
            r19 = r0
            r0 = r16
            r16 = r4
        L_0x009e:
            r4 = r15
        L_0x009f:
            java.lang.Object r4 = kotlin.p.a(r4)     // Catch:{ all -> 0x00eb }
            java.lang.Object r4 = kotlin.o.m17constructorimpl(r4)     // Catch:{ all -> 0x00eb }
            r0.resumeWith(r4)     // Catch:{ all -> 0x00eb }
            goto L_0x00cd
        L_0x00ac:
            r19 = r0
            r16 = r4
            if (r14 == 0) goto L_0x00c0
            kotlin.o$a r0 = kotlin.o.Companion     // Catch:{ all -> 0x00eb }
            java.lang.Object r0 = kotlin.p.a(r14)     // Catch:{ all -> 0x00eb }
            java.lang.Object r0 = kotlin.o.m17constructorimpl(r0)     // Catch:{ all -> 0x00eb }
            r5.resumeWith(r0)     // Catch:{ all -> 0x00eb }
            goto L_0x00cd
        L_0x00c0:
            java.lang.Object r0 = r1.g(r13)     // Catch:{ all -> 0x00eb }
            kotlin.o$a r4 = kotlin.o.Companion     // Catch:{ all -> 0x00eb }
            java.lang.Object r0 = kotlin.o.m17constructorimpl(r0)     // Catch:{ all -> 0x00eb }
            r5.resumeWith(r0)     // Catch:{ all -> 0x00eb }
        L_0x00cd:
            kotlin.x r0 = kotlin.x.a     // Catch:{ all -> 0x00eb }
            if (r11 == 0) goto L_0x00d8
            boolean r4 = r11.W0()     // Catch:{ all -> 0x00fd }
            if (r4 == 0) goto L_0x00db
        L_0x00d8:
            kotlinx.coroutines.internal.j0.a(r8, r9)     // Catch:{ all -> 0x00fd }
        L_0x00db:
            kotlin.o$a r4 = kotlin.o.Companion     // Catch:{ all -> 0x00e9 }
            r4 = r20
            r5 = 0
            r2.o()     // Catch:{ all -> 0x00e9 }
            java.lang.Object r0 = kotlin.o.m17constructorimpl(r0)     // Catch:{ all -> 0x00e9 }
            goto L_0x0119
        L_0x00e9:
            r0 = move-exception
            goto L_0x010f
        L_0x00eb:
            r0 = move-exception
            goto L_0x00f0
        L_0x00ed:
            r0 = move-exception
            r16 = r4
        L_0x00f0:
            if (r11 == 0) goto L_0x00f8
            boolean r4 = r11.W0()     // Catch:{ all -> 0x00fd }
            if (r4 == 0) goto L_0x00fb
        L_0x00f8:
            kotlinx.coroutines.internal.j0.a(r8, r9)     // Catch:{ all -> 0x00fd }
        L_0x00fb:
            throw r0     // Catch:{ all -> 0x00fd }
        L_0x00fd:
            r0 = move-exception
            r3 = r0
            kotlin.o$a r0 = kotlin.o.Companion     // Catch:{ all -> 0x010e }
            r0 = r20
            r4 = 0
            r2.o()     // Catch:{ all -> 0x010e }
            kotlin.x r0 = kotlin.x.a     // Catch:{ all -> 0x010e }
            java.lang.Object r0 = kotlin.o.m17constructorimpl(r0)     // Catch:{ all -> 0x010e }
            goto L_0x0119
        L_0x010e:
            r0 = move-exception
        L_0x010f:
            kotlin.o$a r4 = kotlin.o.Companion
            java.lang.Object r0 = kotlin.p.a(r0)
            java.lang.Object r0 = kotlin.o.m17constructorimpl(r0)
        L_0x0119:
            java.lang.Throwable r4 = kotlin.o.m20exceptionOrNullimpl(r0)
            r1.i(r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.b1.run():void");
    }

    public final void i(@Nullable Throwable exception, @Nullable Throwable finallyException) {
        if (exception != null || finallyException != null) {
            if (!(exception == null || finallyException == null)) {
                b.a(exception, finallyException);
            }
            Throwable cause = exception == null ? finallyException : exception;
            k.c(cause);
            l0.a(c().getContext(), new r0("Fatal exception in coroutines machinery for " + this + ". Please read KDoc to 'handleFatalException' method and report this incident to maintainers", cause));
        }
    }
}
