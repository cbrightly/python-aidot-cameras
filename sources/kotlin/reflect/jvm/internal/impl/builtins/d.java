package kotlin.reflect.jvm.internal.impl.builtins;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.storage.b;
import org.jetbrains.annotations.NotNull;

/* compiled from: DefaultBuiltIns.kt */
public final class d extends g {
    @NotNull
    private static final d o = new d(false, 1, (DefaultConstructorMarker) null);
    public static final a p = new a((DefaultConstructorMarker) null);

    @NotNull
    public static final d L0() {
        return o;
    }

    public d(boolean loadBuiltInsFromCurrentClassLoader) {
        super(new b("DefaultBuiltIns"));
        if (loadBuiltInsFromCurrentClassLoader) {
            g(false);
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ d(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? true : z);
    }

    /* compiled from: DefaultBuiltIns.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
