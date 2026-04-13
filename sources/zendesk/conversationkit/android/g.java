package zendesk.conversationkit.android;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ConversationKitResult.kt */
public abstract class g<T> {
    public /* synthetic */ g(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private g() {
    }

    /* compiled from: ConversationKitResult.kt */
    public static final class b<T> extends g<T> {
        private final T a;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof b) && k.a(this.a, ((b) obj).a);
        }

        public int hashCode() {
            T t = this.a;
            if (t == null) {
                return 0;
            }
            return t.hashCode();
        }

        @NotNull
        public String toString() {
            return "Success(value=" + this.a + ')';
        }

        public b(T value) {
            super((DefaultConstructorMarker) null);
            this.a = value;
        }

        public final T a() {
            return this.a;
        }
    }

    /* compiled from: ConversationKitResult.kt */
    public static final class a extends g {
        @NotNull
        private final Throwable a;
        @NotNull
        private final String b;

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
            return "Failure(cause=" + this.a + ')';
        }

        @NotNull
        public final Throwable a() {
            return this.a;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull Throwable cause) {
            super((DefaultConstructorMarker) null);
            k.e(cause, "cause");
            this.a = cause;
            String message = cause.getMessage();
            this.b = message == null ? "" : message;
        }
    }
}
