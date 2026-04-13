package zendesk.ui.android.conversation.connectionbanner;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ConnectionBannerState.kt */
public final class e {
    @NotNull
    private final a a;

    public e() {
        this((a) null, 1, (DefaultConstructorMarker) null);
    }

    @NotNull
    public final e a(@NotNull a aVar) {
        k.e(aVar, "connectionState");
        return new e(aVar);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof e) && k.a(this.a, ((e) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @NotNull
    public String toString() {
        return "ConnectionBannerState(connectionState=" + this.a + ')';
    }

    public e(@NotNull a connectionState) {
        k.e(connectionState, "connectionState");
        this.a = connectionState;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ e(a aVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? a.C0559a.b : aVar);
    }

    @NotNull
    public final a b() {
        return this.a;
    }

    /* compiled from: ConnectionBannerState.kt */
    public static abstract class a {
        @NotNull
        private final String a;

        public /* synthetic */ a(String str, DefaultConstructorMarker defaultConstructorMarker) {
            this(str);
        }

        /* compiled from: ConnectionBannerState.kt */
        public static final class d extends a {
            @NotNull
            public static final d b = new d();

            private d() {
                super("Reconnecting", (DefaultConstructorMarker) null);
            }
        }

        private a(String stateValue) {
            this.a = stateValue;
        }

        @NotNull
        public final String a() {
            return this.a;
        }

        /* compiled from: ConnectionBannerState.kt */
        public static final class c extends a {
            @NotNull
            public static final c b = new c();

            private c() {
                super("Reconnected", (DefaultConstructorMarker) null);
            }
        }

        /* compiled from: ConnectionBannerState.kt */
        public static final class b extends a {
            @NotNull
            public static final b b = new b();

            private b() {
                super("Disconnected", (DefaultConstructorMarker) null);
            }
        }

        /* renamed from: zendesk.ui.android.conversation.connectionbanner.e$a$a  reason: collision with other inner class name */
        /* compiled from: ConnectionBannerState.kt */
        public static final class C0559a extends a {
            @NotNull
            public static final C0559a b = new C0559a();

            private C0559a() {
                super("Connected", (DefaultConstructorMarker) null);
            }
        }
    }
}
