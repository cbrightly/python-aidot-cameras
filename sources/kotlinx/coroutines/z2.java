package kotlinx.coroutines;

import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.l;
import kotlinx.coroutines.intrinsics.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0000\u001a_\u0010\u0006\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\b\"\b\b\u0001\u0010\t*\u0002H\b2\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u0002H\t0\n2'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH\u0002ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001aU\u0010\u0011\u001a\u0002H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0012\u001a\u00020\u00032'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH@ø\u0001\u0000\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001¢\u0006\u0002\u0010\u0013\u001a]\u0010\u0011\u001a\u0002H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0014\u001a\u00020\u00152'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH@ø\u0001\u0000ø\u0001\u0000ø\u0001\u0001\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001¢\u0006\u0004\b\u0016\u0010\u0013\u001aJ\u0010\u0017\u001a\u0004\u0018\u0001H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0012\u001a\u00020\u00032'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001aR\u0010\u0017\u001a\u0004\u0018\u0001H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\u0014\u001a\u00020\u00152'\u0010\u000b\u001a#\b\u0001\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\u000fH@ø\u0001\u0000ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0018\u0010\u0013\u0002\u000b\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001¨\u0006\u0019"}, d2 = {"TimeoutCancellationException", "Lkotlinx/coroutines/TimeoutCancellationException;", "time", "", "coroutine", "Lkotlinx/coroutines/Job;", "setupTimeout", "", "U", "T", "Lkotlinx/coroutines/TimeoutCoroutine;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/TimeoutCoroutine;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "withTimeout", "timeMillis", "(JLkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "timeout", "Lkotlin/time/Duration;", "withTimeout-KLykuaI", "withTimeoutOrNull", "withTimeoutOrNull-KLykuaI", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: Timeout.kt */
public final class z2 {

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "kotlinx.coroutines.TimeoutKt", f = "Timeout.kt", l = {100}, m = "withTimeoutOrNull")
    /* compiled from: Timeout.kt */
    public static final class a<T> extends d {
        long J$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        a(kotlin.coroutines.d<? super a> dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return z2.c(0, (p) null, this);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0078 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object c(long r8, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.p<? super kotlinx.coroutines.o0, ? super kotlin.coroutines.d<? super T>, ? extends java.lang.Object> r10, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super T> r11) {
        /*
            boolean r0 = r11 instanceof kotlinx.coroutines.z2.a
            if (r0 == 0) goto L_0x0013
            r0 = r11
            kotlinx.coroutines.z2$a r0 = (kotlinx.coroutines.z2.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            kotlinx.coroutines.z2$a r0 = new kotlinx.coroutines.z2$a
            r0.<init>(r11)
        L_0x0018:
            r11 = r0
            java.lang.Object r0 = r11.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r11.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x003c;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L_0x002d:
            java.lang.Object r8 = r11.L$1
            kotlin.jvm.internal.z r8 = (kotlin.jvm.internal.z) r8
            java.lang.Object r9 = r11.L$0
            kotlin.jvm.functions.p r9 = (kotlin.jvm.functions.p) r9
            kotlin.p.b(r0)     // Catch:{ TimeoutCancellationException -> 0x003a }
            r7 = r0
            goto L_0x006f
        L_0x003a:
            r9 = move-exception
            goto L_0x0072
        L_0x003c:
            kotlin.p.b(r0)
            r4 = 0
            int r2 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r2 > 0) goto L_0x0046
            return r3
        L_0x0046:
            kotlin.jvm.internal.z r2 = new kotlin.jvm.internal.z
            r2.<init>()
            r11.L$0 = r10     // Catch:{ TimeoutCancellationException -> 0x0070 }
            r11.L$1 = r2     // Catch:{ TimeoutCancellationException -> 0x0070 }
            r11.J$0 = r8     // Catch:{ TimeoutCancellationException -> 0x0070 }
            r4 = 1
            r11.label = r4     // Catch:{ TimeoutCancellationException -> 0x0070 }
            r4 = r11
            r5 = 0
            kotlinx.coroutines.y2 r6 = new kotlinx.coroutines.y2     // Catch:{ TimeoutCancellationException -> 0x0070 }
            r6.<init>(r8, r4)     // Catch:{ TimeoutCancellationException -> 0x0070 }
            r2.element = r6     // Catch:{ TimeoutCancellationException -> 0x0070 }
            java.lang.Object r7 = b(r6, r10)     // Catch:{ TimeoutCancellationException -> 0x0070 }
            java.lang.Object r4 = kotlin.coroutines.intrinsics.c.d()     // Catch:{ TimeoutCancellationException -> 0x0070 }
            if (r7 != r4) goto L_0x006b
            kotlin.coroutines.jvm.internal.h.c(r11)     // Catch:{ TimeoutCancellationException -> 0x0070 }
        L_0x006b:
            if (r7 != r1) goto L_0x006e
            return r1
        L_0x006e:
            r8 = r2
        L_0x006f:
            return r7
        L_0x0070:
            r9 = move-exception
            r8 = r2
        L_0x0072:
            kotlinx.coroutines.z1 r10 = r9.coroutine
            T r1 = r8.element
            if (r10 != r1) goto L_0x0079
            return r3
        L_0x0079:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.z2.c(long, kotlin.jvm.functions.p, kotlin.coroutines.d):java.lang.Object");
    }

    private static final <U, T extends U> Object b(y2<U, ? super T> coroutine, p<? super o0, ? super kotlin.coroutines.d<? super T>, ? extends Object> block) {
        c2.h(coroutine, z0.b(coroutine.f.getContext()).l(coroutine.q, coroutine, coroutine.getContext()));
        return b.d(coroutine, coroutine, block);
    }

    @NotNull
    public static final TimeoutCancellationException a(long time, @NotNull z1 coroutine) {
        return new TimeoutCancellationException("Timed out waiting for " + time + " ms", coroutine);
    }
}
