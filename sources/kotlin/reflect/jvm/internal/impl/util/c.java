package kotlin.reflect.jvm.internal.impl.util;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: modifierChecks.kt */
public abstract class c {
    private final boolean a;

    /* compiled from: modifierChecks.kt */
    public static final class b extends c {
        @NotNull
        private final String b;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull String error) {
            super(false, (DefaultConstructorMarker) null);
            k.f(error, "error");
            this.b = error;
        }
    }

    private c(boolean isSuccess) {
        this.a = isSuccess;
    }

    public /* synthetic */ c(boolean isSuccess, DefaultConstructorMarker $constructor_marker) {
        this(isSuccess);
    }

    public final boolean a() {
        return this.a;
    }

    /* compiled from: modifierChecks.kt */
    public static final class a extends c {
        public static final a b = new a();

        private a() {
            super(false, (DefaultConstructorMarker) null);
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.util.c$c  reason: collision with other inner class name */
    /* compiled from: modifierChecks.kt */
    public static final class C0431c extends c {
        public static final C0431c b = new C0431c();

        private C0431c() {
            super(true, (DefaultConstructorMarker) null);
        }
    }
}
