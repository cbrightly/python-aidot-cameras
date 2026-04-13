package kotlin.reflect.jvm.internal.impl.util;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.util.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: modifierChecks.kt */
public abstract class l implements b {
    @NotNull
    private final String a;

    /* compiled from: modifierChecks.kt */
    public static final class c extends l {
        public static final c b = new c();

        private c() {
            super("must have no value parameters", (DefaultConstructorMarker) null);
        }

        public boolean b(@NotNull u functionDescriptor) {
            k.f(functionDescriptor, "functionDescriptor");
            return functionDescriptor.g().isEmpty();
        }
    }

    private l(String description) {
        this.a = description;
    }

    public /* synthetic */ l(String description, DefaultConstructorMarker $constructor_marker) {
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
    public static final class d extends l {
        public static final d b = new d();

        private d() {
            super("must have a single value parameter", (DefaultConstructorMarker) null);
        }

        public boolean b(@NotNull u functionDescriptor) {
            k.f(functionDescriptor, "functionDescriptor");
            return functionDescriptor.g().size() == 1;
        }
    }

    /* compiled from: modifierChecks.kt */
    public static final class a extends l {
        private final int b;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public a(int r3) {
            /*
                r2 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "must have at least "
                r0.append(r1)
                r0.append(r3)
                java.lang.String r1 = " value parameter"
                r0.append(r1)
                r1 = 1
                if (r3 <= r1) goto L_0x0019
                java.lang.String r1 = "s"
                goto L_0x001b
            L_0x0019:
                java.lang.String r1 = ""
            L_0x001b:
                r0.append(r1)
                java.lang.String r0 = r0.toString()
                r1 = 0
                r2.<init>(r0, r1)
                r2.b = r3
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.util.l.a.<init>(int):void");
        }

        public boolean b(@NotNull u functionDescriptor) {
            k.f(functionDescriptor, "functionDescriptor");
            return functionDescriptor.g().size() >= this.b;
        }
    }

    /* compiled from: modifierChecks.kt */
    public static final class b extends l {
        private final int b;

        public b(int n) {
            super("must have exactly " + n + " value parameters", (DefaultConstructorMarker) null);
            this.b = n;
        }

        public boolean b(@NotNull u functionDescriptor) {
            k.f(functionDescriptor, "functionDescriptor");
            return functionDescriptor.g().size() == this.b;
        }
    }
}
