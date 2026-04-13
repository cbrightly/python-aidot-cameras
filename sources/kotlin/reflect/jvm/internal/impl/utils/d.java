package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.q;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: functions.kt */
public final class d {
    private static final l<Object, Object> a = f.INSTANCE;
    private static final l<Object, Boolean> b = b.INSTANCE;
    private static final l<Object, Object> c = a.INSTANCE;
    @NotNull
    private static final l<Object, x> d = c.INSTANCE;
    @NotNull
    private static final p<Object, Object, x> e = C0434d.INSTANCE;
    @NotNull
    private static final q<Object, Object, Object, x> f = e.INSTANCE;

    /* compiled from: functions.kt */
    public static final class f extends kotlin.jvm.internal.l implements l<Object, Object> {
        public static final f INSTANCE = new f();

        f() {
            super(1);
        }

        @Nullable
        public final Object invoke(@Nullable Object it) {
            return it;
        }
    }

    /* compiled from: functions.kt */
    public static final class b extends kotlin.jvm.internal.l implements l<Object, Boolean> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        public final boolean invoke(@Nullable Object it) {
            return true;
        }
    }

    @NotNull
    public static final <T> l<T, Boolean> a() {
        return b;
    }

    /* compiled from: functions.kt */
    public static final class a extends kotlin.jvm.internal.l implements l {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @Nullable
        public final Void invoke(@Nullable Object it) {
            return null;
        }
    }

    /* compiled from: functions.kt */
    public static final class c extends kotlin.jvm.internal.l implements l<Object, x> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        public final void invoke(@Nullable Object it) {
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.utils.d$d  reason: collision with other inner class name */
    /* compiled from: functions.kt */
    public static final class C0434d extends kotlin.jvm.internal.l implements p<Object, Object, x> {
        public static final C0434d INSTANCE = new C0434d();

        C0434d() {
            super(2);
        }

        public final void invoke(@Nullable Object $noName_0, @Nullable Object $noName_1) {
        }
    }

    /* compiled from: functions.kt */
    public static final class e extends kotlin.jvm.internal.l implements q<Object, Object, Object, x> {
        public static final e INSTANCE = new e();

        e() {
            super(3);
        }

        public final void invoke(@Nullable Object $noName_0, @Nullable Object $noName_1, @Nullable Object $noName_2) {
        }
    }

    @NotNull
    public static final q<Object, Object, Object, x> b() {
        return f;
    }
}
