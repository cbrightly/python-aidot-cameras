package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import kotlin.reflect.jvm.internal.impl.resolve.scopes.d;

/* compiled from: MemberScope.kt */
public abstract class c {
    public abstract int a();

    public String toString() {
        return getClass().getSimpleName();
    }

    /* compiled from: MemberScope.kt */
    public static final class a extends c {
        private static final int a;
        public static final a b = new a();

        static {
            d.a aVar = d.x;
            a = (~(aVar.i() | aVar.d())) & aVar.b();
        }

        private a() {
        }

        public int a() {
            return a;
        }
    }

    /* compiled from: MemberScope.kt */
    public static final class b extends c {
        public static final b a = new b();

        private b() {
        }

        public int a() {
            return 0;
        }
    }
}
