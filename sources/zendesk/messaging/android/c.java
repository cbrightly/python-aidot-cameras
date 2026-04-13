package zendesk.messaging.android;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MessagingResult.kt */
public abstract class c<T> {
    public /* synthetic */ c(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private c() {
    }

    /* compiled from: MessagingResult.kt */
    public static final class b<T> extends c<T> {
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

    /* compiled from: MessagingResult.kt */
    public static final class a extends c {
        @Nullable
        private final b a;
        @NotNull
        private final String b;

        public a() {
            this((b) null, 1, (DefaultConstructorMarker) null);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof a) && k.a(this.a, ((a) obj).a);
        }

        public int hashCode() {
            b bVar = this.a;
            if (bVar == null) {
                return 0;
            }
            return bVar.hashCode();
        }

        @NotNull
        public String toString() {
            return "Failure(cause=" + this.a + ')';
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ a(b bVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : bVar);
        }

        public a(@Nullable b cause) {
            super((DefaultConstructorMarker) null);
            String message;
            this.a = cause;
            String str = "";
            if (!(cause == null || (message = cause.getMessage()) == null)) {
                str = message;
            }
            this.b = str;
        }
    }
}
