package zendesk.android.internal;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ZendeskError.kt */
public abstract class i extends Throwable {
    @NotNull
    private final String message;

    public /* synthetic */ i(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private i(String message2) {
        super(message2);
        this.message = message2;
    }

    @NotNull
    public String getMessage() {
        return this.message;
    }

    /* compiled from: ZendeskError.kt */
    public static final class c extends i {
        @NotNull
        public static final c INSTANCE = new c();

        private c() {
            super("The provided channelKey is invalid.", (DefaultConstructorMarker) null);
        }
    }

    /* compiled from: ZendeskError.kt */
    public static final class e extends i {
        @NotNull
        public static final e INSTANCE = new e();

        private e() {
            super("Zendesk.instance() was called before initialization was completed.", (DefaultConstructorMarker) null);
        }
    }

    /* compiled from: ZendeskError.kt */
    public static final class b extends i {
        @Nullable
        private final Throwable cause;

        public b() {
            this((Throwable) null, 1, (DefaultConstructorMarker) null);
        }

        public static /* synthetic */ b copy$default(b bVar, Throwable th, int i, Object obj) {
            if ((i & 1) != 0) {
                th = bVar.getCause();
            }
            return bVar.copy(th);
        }

        @Nullable
        public final Throwable component1() {
            return getCause();
        }

        @NotNull
        public final b copy(@Nullable Throwable th) {
            return new b(th);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof b) && k.a(getCause(), ((b) obj).getCause());
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

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ b(Throwable th, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : th);
        }

        @Nullable
        public Throwable getCause() {
            return this.cause;
        }

        public b(@Nullable Throwable cause2) {
            super("Zendesk failed to initialize.", (DefaultConstructorMarker) null);
            this.cause = cause2;
        }
    }

    /* compiled from: ZendeskError.kt */
    public static final class a extends i {
        @NotNull
        public static final a INSTANCE = new a();

        private a() {
            super("No account found for the provided credentials.", (DefaultConstructorMarker) null);
        }
    }

    /* compiled from: ZendeskError.kt */
    public static final class d extends i {
        @NotNull
        public static final d INSTANCE = new d();

        private d() {
            super("The configuration for this Zendesk integration could not be retrieved.", (DefaultConstructorMarker) null);
        }
    }

    /* compiled from: ZendeskError.kt */
    public static final class f extends i {
        @NotNull
        public static final f INSTANCE = new f();

        private f() {
            super("The SDK is not enabled for this integration.", (DefaultConstructorMarker) null);
        }
    }
}
