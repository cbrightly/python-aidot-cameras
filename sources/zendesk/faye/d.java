package zendesk.faye;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: DisconnectMessage.kt */
public final class d extends a {
    @NotNull
    public static final b a = new b((DefaultConstructorMarker) null);
    @NotNull
    private final b b;

    public /* synthetic */ d(b bVar, DefaultConstructorMarker defaultConstructorMarker) {
        this(bVar);
    }

    @NotNull
    public final b a() {
        return this.b;
    }

    private d(b optionalFields) {
        this.b = optionalFields;
    }

    /* compiled from: DisconnectMessage.kt */
    public static final class a {
        @NotNull
        private b a = b.a.a().a();

        @NotNull
        public final d a() {
            return new d(this.a, (DefaultConstructorMarker) null);
        }
    }

    /* compiled from: DisconnectMessage.kt */
    public static final class b {
        public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private b() {
        }

        @NotNull
        public final a a() {
            return new a();
        }
    }
}
