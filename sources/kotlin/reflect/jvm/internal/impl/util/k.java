package kotlin.reflect.jvm.internal.impl.util;

import com.didichuxing.doraemonkit.constant.SpInputType;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.util.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: modifierChecks.kt */
public abstract class k implements b {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @NotNull
    private final l<g, b0> c;

    private k(String name, l<? super g, ? extends b0> type) {
        this.b = name;
        this.c = type;
        this.a = "must return " + name;
    }

    public /* synthetic */ k(String name, l type, DefaultConstructorMarker $constructor_marker) {
        this(name, type);
    }

    @Nullable
    public String a(@NotNull u functionDescriptor) {
        kotlin.jvm.internal.k.f(functionDescriptor, "functionDescriptor");
        return b.a.a(this, functionDescriptor);
    }

    @NotNull
    public String getDescription() {
        return this.a;
    }

    /* compiled from: modifierChecks.kt */
    public static final class a extends k {
        public static final a d = new a();

        /* renamed from: kotlin.reflect.jvm.internal.impl.util.k$a$a  reason: collision with other inner class name */
        /* compiled from: modifierChecks.kt */
        public static final class C0432a extends kotlin.jvm.internal.l implements l<g, i0> {
            public static final C0432a INSTANCE = new C0432a();

            C0432a() {
                super(1);
            }

            @NotNull
            public final i0 invoke(@NotNull g $receiver) {
                kotlin.jvm.internal.k.f($receiver, "$receiver");
                i0 n = $receiver.n();
                kotlin.jvm.internal.k.b(n, "booleanType");
                return n;
            }
        }

        private a() {
            super(SpInputType.BOOLEAN, C0432a.INSTANCE, (DefaultConstructorMarker) null);
        }
    }

    public boolean b(@NotNull u functionDescriptor) {
        kotlin.jvm.internal.k.f(functionDescriptor, "functionDescriptor");
        return kotlin.jvm.internal.k.a(functionDescriptor.getReturnType(), this.c.invoke(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.h(functionDescriptor)));
    }

    /* compiled from: modifierChecks.kt */
    public static final class b extends k {
        public static final b d = new b();

        /* compiled from: modifierChecks.kt */
        public static final class a extends kotlin.jvm.internal.l implements l<g, i0> {
            public static final a INSTANCE = new a();

            a() {
                super(1);
            }

            @NotNull
            public final i0 invoke(@NotNull g $receiver) {
                kotlin.jvm.internal.k.f($receiver, "$receiver");
                i0 F = $receiver.F();
                kotlin.jvm.internal.k.b(F, "intType");
                return F;
            }
        }

        private b() {
            super("Int", a.INSTANCE, (DefaultConstructorMarker) null);
        }
    }

    /* compiled from: modifierChecks.kt */
    public static final class c extends k {
        public static final c d = new c();

        /* compiled from: modifierChecks.kt */
        public static final class a extends kotlin.jvm.internal.l implements l<g, i0> {
            public static final a INSTANCE = new a();

            a() {
                super(1);
            }

            @NotNull
            public final i0 invoke(@NotNull g $receiver) {
                kotlin.jvm.internal.k.f($receiver, "$receiver");
                i0 b0 = $receiver.b0();
                kotlin.jvm.internal.k.b(b0, "unitType");
                return b0;
            }
        }

        private c() {
            super("Unit", a.INSTANCE, (DefaultConstructorMarker) null);
        }
    }
}
