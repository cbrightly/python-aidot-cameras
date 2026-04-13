package okhttp3;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Authenticator.kt */
public interface b {
    @NotNull
    public static final b a = new a.C0455a();
    @NotNull
    public static final b b = new okhttp3.internal.authenticator.b((q) null, 1, (DefaultConstructorMarker) null);
    public static final a c = new a((DefaultConstructorMarker) null);

    @Nullable
    b0 a(@Nullable f0 f0Var, @NotNull d0 d0Var);

    /* compiled from: Authenticator.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* renamed from: okhttp3.b$a$a  reason: collision with other inner class name */
        /* compiled from: Authenticator.kt */
        public static final class C0455a implements b {
            @Nullable
            public b0 a(@Nullable f0 route, @NotNull d0 response) {
                k.f(response, "response");
                return null;
            }
        }
    }
}
