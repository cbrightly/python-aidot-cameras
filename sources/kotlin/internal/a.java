package kotlin.internal;

import java.lang.reflect.Method;
import kotlin.jvm.internal.k;
import kotlin.random.b;
import kotlin.random.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PlatformImplementations.kt */
public class a {

    /* renamed from: kotlin.internal.a$a  reason: collision with other inner class name */
    /* compiled from: PlatformImplementations.kt */
    public static final class C0324a {
        @Nullable
        public static final Method a;
        @Nullable
        public static final Method b;
        @NotNull
        public static final C0324a c = new C0324a();

        /* JADX WARNING: Removed duplicated region for block: B:10:0x004a A[LOOP:0: B:1:0x0017->B:10:0x004a, LOOP_END] */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x004e A[EDGE_INSN: B:20:0x004e->B:12:0x004e ?: BREAK  , SYNTHETIC] */
        static {
            /*
                kotlin.internal.a$a r0 = new kotlin.internal.a$a
                r0.<init>()
                c = r0
                java.lang.Class<java.lang.Throwable> r0 = java.lang.Throwable.class
                java.lang.reflect.Method[] r1 = r0.getMethods()
                java.lang.String r2 = "throwableMethods"
                kotlin.jvm.internal.k.d(r1, r2)
                int r2 = r1.length
                r3 = 0
                r4 = r3
            L_0x0017:
                java.lang.String r5 = "it"
                r6 = 0
                if (r4 >= r2) goto L_0x004d
                r7 = r1[r4]
                r8 = r7
                r9 = 0
                kotlin.jvm.internal.k.d(r8, r5)
                java.lang.String r10 = r8.getName()
                java.lang.String r11 = "addSuppressed"
                boolean r10 = kotlin.jvm.internal.k.a(r10, r11)
                if (r10 == 0) goto L_0x0046
                java.lang.Class[] r10 = r8.getParameterTypes()
                java.lang.String r11 = "it.parameterTypes"
                kotlin.jvm.internal.k.d(r10, r11)
                java.lang.Object r10 = kotlin.collections.l.K(r10)
                java.lang.Class r10 = (java.lang.Class) r10
                boolean r10 = kotlin.jvm.internal.k.a(r10, r0)
                if (r10 == 0) goto L_0x0046
                r10 = 1
                goto L_0x0047
            L_0x0046:
                r10 = r3
            L_0x0047:
                if (r10 == 0) goto L_0x004a
                goto L_0x004e
            L_0x004a:
                int r4 = r4 + 1
                goto L_0x0017
            L_0x004d:
                r7 = r6
            L_0x004e:
                a = r7
                int r2 = r1.length
            L_0x0051:
                if (r3 >= r2) goto L_0x006b
                r4 = r1[r3]
                r7 = r4
                r8 = 0
                kotlin.jvm.internal.k.d(r7, r5)
                java.lang.String r9 = r7.getName()
                java.lang.String r10 = "getSuppressed"
                boolean r7 = kotlin.jvm.internal.k.a(r9, r10)
                if (r7 == 0) goto L_0x0068
                r6 = r4
                goto L_0x006b
            L_0x0068:
                int r3 = r3 + 1
                goto L_0x0051
            L_0x006b:
                b = r6
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.internal.a.C0324a.<clinit>():void");
        }

        private C0324a() {
        }
    }

    public void a(@NotNull Throwable cause, @NotNull Throwable exception) {
        k.e(cause, "cause");
        k.e(exception, "exception");
        Method method = C0324a.a;
        if (method != null) {
            method.invoke(cause, new Object[]{exception});
        }
    }

    @NotNull
    public d b() {
        return new b();
    }
}
