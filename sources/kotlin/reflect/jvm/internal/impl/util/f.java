package kotlin.reflect.jvm.internal.impl.util;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.util.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: modifierChecks.kt */
public abstract class f implements b {
    @NotNull
    private final String a;

    /* compiled from: modifierChecks.kt */
    public static final class b extends f {
        public static final b b = new b();

        private b() {
            super("must be a member or an extension function", (DefaultConstructorMarker) null);
        }

        public boolean b(@NotNull u functionDescriptor) {
            k.f(functionDescriptor, "functionDescriptor");
            return (functionDescriptor.I() == null && functionDescriptor.L() == null) ? false : true;
        }
    }

    private f(String description) {
        this.a = description;
    }

    public /* synthetic */ f(String description, DefaultConstructorMarker $constructor_marker) {
        this(description);
    }

    @Nullable
    public String a(@NotNull u functionDescriptor) {
        k.f(functionDescriptor, "functionDescriptor");
        return b.a.a(this, functionDescriptor);
    }

    @NotNull
    public String getDescription() {
        return this.a;
    }

    /* compiled from: modifierChecks.kt */
    public static final class a extends f {
        public static final a b = new a();

        private a() {
            super("must be a member function", (DefaultConstructorMarker) null);
        }

        public boolean b(@NotNull u functionDescriptor) {
            k.f(functionDescriptor, "functionDescriptor");
            return functionDescriptor.I() != null;
        }
    }
}
