package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.c;
import org.jetbrains.annotations.NotNull;

/* compiled from: JvmBuiltInsSettings.kt */
public final class b extends g {
    /* access modifiers changed from: private */
    @NotNull
    public static final g o = new b();
    public static final a p = new a((DefaultConstructorMarker) null);

    private b() {
        super(new kotlin.reflect.jvm.internal.impl.storage.b("FallbackBuiltIns"));
        g(true);
    }

    /* compiled from: JvmBuiltInsSettings.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final g a() {
            return b.o;
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: M0 */
    public c.a O() {
        return c.a.a;
    }
}
