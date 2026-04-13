package zendesk.conversationkit.android.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AuthenticationType.kt */
public abstract class f {
    public /* synthetic */ f(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private f() {
    }

    /* compiled from: AuthenticationType.kt */
    public static final class b extends f {
        @NotNull
        private final String a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof b) && k.a(this.a, ((b) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "SessionToken(value=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull String value) {
            super((DefaultConstructorMarker) null);
            k.e(value, "value");
            this.a = value;
        }

        @NotNull
        public final String a() {
            return this.a;
        }
    }

    /* compiled from: AuthenticationType.kt */
    public static final class a extends f {
        @NotNull
        private final String a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof a) && k.a(this.a, ((a) obj).a);
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        @NotNull
        public String toString() {
            return "Jwt(value=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull String value) {
            super((DefaultConstructorMarker) null);
            k.e(value, "value");
            this.a = value;
        }

        @NotNull
        public final String a() {
            return this.a;
        }
    }

    /* compiled from: AuthenticationType.kt */
    public static final class c extends f {
        @NotNull
        public static final c a = new c();

        private c() {
            super((DefaultConstructorMarker) null);
        }
    }
}
