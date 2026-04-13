package zendesk.faye;

import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import zendesk.faye.b;

/* compiled from: ConnectMessage.kt */
public final class c extends a {
    @NotNull
    public static final b a = new b((DefaultConstructorMarker) null);
    @NotNull
    private final List<String> b;
    @NotNull
    private final b c;
    @NotNull
    private final b d;

    public /* synthetic */ c(List list, b bVar, b bVar2, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, bVar, bVar2);
    }

    @NotNull
    public final List<String> c() {
        return this.b;
    }

    @NotNull
    public final b b() {
        return this.c;
    }

    @NotNull
    public final b a() {
        return this.d;
    }

    private c(List<String> supportedConnectionTypes, b handshakeOptionalFields, b connectOptionalFields) {
        this.b = supportedConnectionTypes;
        this.c = handshakeOptionalFields;
        this.d = connectOptionalFields;
    }

    /* compiled from: ConnectMessage.kt */
    public static final class a {
        @NotNull
        private List<String> a = q.g();
        @NotNull
        private b b;
        @NotNull
        private b c;

        public a() {
            b.C0517b bVar = b.a;
            this.b = bVar.a().a();
            this.c = bVar.a().a();
        }

        @NotNull
        public final c a() {
            return new c(this.a, this.b, this.c, (DefaultConstructorMarker) null);
        }
    }

    /* compiled from: ConnectMessage.kt */
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
