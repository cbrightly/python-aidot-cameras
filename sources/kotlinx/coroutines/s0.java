package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicLong;
import kotlin.l;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000(\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0017\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0015H\b\u001a\b\u0010\u0016\u001a\u00020\u0013H\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0014\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0014\u0010\b\u001a\u00020\u0001X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0003\"\u000e\u0010\n\u001a\u00020\u000bXT¢\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\u000bXT¢\u0006\u0002\n\u0000\"\u000e\u0010\r\u001a\u00020\u000bXT¢\u0006\u0002\n\u0000\"\u000e\u0010\u000e\u001a\u00020\u000bXT¢\u0006\u0002\n\u0000\"\u0014\u0010\u000f\u001a\u00020\u0001X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0003\"\u000e\u0010\u0011\u001a\u00020\u000bXT¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"ASSERTIONS_ENABLED", "", "getASSERTIONS_ENABLED", "()Z", "COROUTINE_ID", "Ljava/util/concurrent/atomic/AtomicLong;", "getCOROUTINE_ID", "()Ljava/util/concurrent/atomic/AtomicLong;", "DEBUG", "getDEBUG", "DEBUG_PROPERTY_NAME", "", "DEBUG_PROPERTY_VALUE_AUTO", "DEBUG_PROPERTY_VALUE_OFF", "DEBUG_PROPERTY_VALUE_ON", "RECOVER_STACK_TRACES", "getRECOVER_STACK_TRACES", "STACKTRACE_RECOVERY_PROPERTY_NAME", "assert", "", "value", "Lkotlin/Function0;", "resetCoroutineId", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: Debug.kt */
public final class s0 {
    private static final boolean a = false;
    private static final boolean b;
    private static final boolean c;
    @NotNull
    private static final AtomicLong d = new AtomicLong(0);

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002e, code lost:
        if (r1.equals("on") != false) goto L_0x0039;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0037, code lost:
        if (r1.equals("") != false) goto L_0x0039;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0039, code lost:
        r4 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x005a, code lost:
        throw new java.lang.IllegalStateException(("System property 'kotlinx.coroutines.debug' has unrecognized value '" + r1 + '\'').toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001b, code lost:
        if (r1.equals("auto") != false) goto L_0x005b;
     */
    static {
        /*
            r0 = 0
            a = r0
            java.lang.String r1 = "kotlinx.coroutines.debug"
            java.lang.String r1 = kotlinx.coroutines.internal.g0.d(r1)
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L_0x005b
            int r4 = r1.hashCode()
            switch(r4) {
                case 0: goto L_0x0031;
                case 3551: goto L_0x0028;
                case 109935: goto L_0x001e;
                case 3005871: goto L_0x0015;
                default: goto L_0x0014;
            }
        L_0x0014:
            goto L_0x003b
        L_0x0015:
            java.lang.String r4 = "auto"
            boolean r4 = r1.equals(r4)
            if (r4 == 0) goto L_0x0014
            goto L_0x005b
        L_0x001e:
            java.lang.String r4 = "off"
            boolean r4 = r1.equals(r4)
            if (r4 == 0) goto L_0x0014
            r4 = r0
            goto L_0x005f
        L_0x0028:
            java.lang.String r4 = "on"
            boolean r4 = r1.equals(r4)
            if (r4 == 0) goto L_0x0014
            goto L_0x0039
        L_0x0031:
            java.lang.String r4 = ""
            boolean r4 = r1.equals(r4)
            if (r4 == 0) goto L_0x0014
        L_0x0039:
            r4 = r3
            goto L_0x005f
        L_0x003b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "System property 'kotlinx.coroutines.debug' has unrecognized value '"
            r3.append(r4)
            r3.append(r1)
            r4 = 39
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            java.lang.String r3 = r3.toString()
            r0.<init>(r3)
            throw r0
        L_0x005b:
            boolean r4 = a()
        L_0x005f:
            b = r4
            if (r4 == 0) goto L_0x006d
            java.lang.String r1 = "kotlinx.coroutines.stacktrace.recovery"
            boolean r1 = kotlinx.coroutines.internal.g0.e(r1, r3)
            if (r1 == 0) goto L_0x006d
            r0 = r3
        L_0x006d:
            c = r0
            java.util.concurrent.atomic.AtomicLong r0 = new java.util.concurrent.atomic.AtomicLong
            r1 = 0
            r0.<init>(r1)
            d = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.s0.<clinit>():void");
    }

    public static final boolean a() {
        return a;
    }

    public static final boolean c() {
        return b;
    }

    public static final boolean d() {
        return c;
    }

    @NotNull
    public static final AtomicLong b() {
        return d;
    }
}
