package io.ktor.utils.io.internal;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.lang.reflect.Method;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;

/* compiled from: EventLoopExperimental.kt */
public abstract class b {
    public abstract long a();

    private b() {
    }

    public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    /* renamed from: io.ktor.utils.io.internal.b$b  reason: collision with other inner class name */
    /* compiled from: EventLoopExperimental.kt */
    public static final class C0289b extends b {
        public static final C0289b a = new C0289b();

        private C0289b() {
            super((DefaultConstructorMarker) null);
        }

        public long a() {
            return DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
        }
    }

    /* compiled from: EventLoopExperimental.kt */
    public static final class a extends b {
        private static final Class<?> a;
        private static final Method b;
        private static final boolean c;
        public static final a d = new a();

        /* JADX WARNING: Removed duplicated region for block: B:25:0x006a  */
        /* JADX WARNING: Removed duplicated region for block: B:38:0x006f A[SYNTHETIC] */
        static {
            /*
                io.ktor.utils.io.internal.b$a r0 = new io.ktor.utils.io.internal.b$a
                r0.<init>()
                d = r0
                r0 = 0
                java.lang.String r1 = "kotlinx.coroutines.m1"
                java.lang.Class r1 = java.lang.Class.forName(r1)     // Catch:{ all -> 0x0010 }
                goto L_0x0012
            L_0x0010:
                r1 = move-exception
                r1 = r0
            L_0x0012:
                a = r1
                r2 = 1
                r3 = 0
                if (r1 == 0) goto L_0x0078
                java.lang.reflect.Method[] r1 = r1.getMethods()
                if (r1 == 0) goto L_0x0078
                r4 = 0
                r5 = 0
                r6 = 0
                int r7 = r1.length
                r8 = r3
            L_0x0023:
                if (r8 >= r7) goto L_0x0073
                r9 = r1[r8]
                r10 = r9
                r11 = 0
                java.lang.String r12 = "it"
                kotlin.jvm.internal.k.b(r10, r12)
                java.lang.String r12 = r10.getName()
                java.lang.String r13 = "processNextEventInCurrentThread"
                boolean r12 = kotlin.jvm.internal.k.a(r12, r13)
                if (r12 == 0) goto L_0x0067
                java.lang.Class r12 = r10.getReturnType()
                java.lang.Class r13 = java.lang.Long.TYPE
                boolean r12 = kotlin.jvm.internal.k.a(r12, r13)
                if (r12 == 0) goto L_0x0067
                java.lang.Class[] r12 = r10.getParameterTypes()
                java.lang.String r13 = "it.parameterTypes"
                kotlin.jvm.internal.k.b(r12, r13)
                int r12 = r12.length
                if (r12 != 0) goto L_0x0058
                r12 = r2
                goto L_0x0059
            L_0x0058:
                r12 = r3
            L_0x0059:
                if (r12 == 0) goto L_0x0067
                int r12 = r10.getModifiers()
                boolean r12 = java.lang.reflect.Modifier.isStatic(r12)
                if (r12 == 0) goto L_0x0067
                r10 = r2
                goto L_0x0068
            L_0x0067:
                r10 = r3
            L_0x0068:
                if (r10 == 0) goto L_0x006f
                if (r6 == 0) goto L_0x006d
                goto L_0x0079
            L_0x006d:
                r5 = r9
                r6 = 1
            L_0x006f:
                int r8 = r8 + 1
                goto L_0x0023
            L_0x0073:
                if (r6 != 0) goto L_0x0076
                goto L_0x0079
            L_0x0076:
                r0 = r5
                goto L_0x0079
            L_0x0078:
            L_0x0079:
                b = r0
                if (r0 == 0) goto L_0x007e
                goto L_0x007f
            L_0x007e:
                r2 = r3
            L_0x007f:
                c = r2
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.internal.b.a.<clinit>():void");
        }

        private a() {
            super((DefaultConstructorMarker) null);
        }

        public final boolean b() {
            return c;
        }

        public long a() {
            Method method = b;
            if (method == null) {
                k.n();
            }
            Object invoke = method.invoke((Object) null, new Object[0]);
            if (invoke != null) {
                return ((Long) invoke).longValue();
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Long");
        }
    }
}
