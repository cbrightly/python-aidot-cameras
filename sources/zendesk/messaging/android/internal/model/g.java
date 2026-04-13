package zendesk.messaging.android.internal.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypingUser.kt */
public abstract class g {
    public /* synthetic */ g(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private g() {
    }

    /* compiled from: TypingUser.kt */
    public static final class b extends g {
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
            return "User(avatarUrl=" + this.a + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull String avatarUrl) {
            super((DefaultConstructorMarker) null);
            k.e(avatarUrl, "avatarUrl");
            this.a = avatarUrl;
        }

        @NotNull
        public final String a() {
            return this.a;
        }
    }

    /* compiled from: TypingUser.kt */
    public static final class a extends g {
        @NotNull
        public static final a a = new a();

        private a() {
            super((DefaultConstructorMarker) null);
        }
    }
}
