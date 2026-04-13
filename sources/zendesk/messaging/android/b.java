package zendesk.messaging.android;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MessagingError.kt */
public abstract class b extends Throwable {
    @NotNull
    private final String message;

    public /* synthetic */ b(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private b(String message2) {
        super(message2);
        this.message = message2;
    }

    @NotNull
    public String getMessage() {
        return this.message;
    }

    /* compiled from: MessagingError.kt */
    public static final class a extends b {
        @Nullable
        private final Throwable cause;

        public a() {
            this((Throwable) null, 1, (DefaultConstructorMarker) null);
        }

        public static /* synthetic */ a copy$default(a aVar, Throwable th, int i, Object obj) {
            if ((i & 1) != 0) {
                th = aVar.getCause();
            }
            return aVar.copy(th);
        }

        @Nullable
        public final Throwable component1() {
            return getCause();
        }

        @NotNull
        public final a copy(@Nullable Throwable th) {
            return new a(th);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof a) && k.a(getCause(), ((a) obj).getCause());
        }

        public int hashCode() {
            if (getCause() == null) {
                return 0;
            }
            return getCause().hashCode();
        }

        @NotNull
        public String toString() {
            return "FailedToInitialize(cause=" + getCause() + ')';
        }

        public a(@Nullable Throwable cause2) {
            super("Messaging failed to initialize.", (DefaultConstructorMarker) null);
            this.cause = cause2;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ a(Throwable th, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : th);
        }

        @Nullable
        public Throwable getCause() {
            return this.cause;
        }
    }
}
