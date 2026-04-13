package zendesk.faye;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: SubscribeMessage.kt */
public final class j extends a {
    @NotNull
    public static final b a = new b((DefaultConstructorMarker) null);
    @NotNull
    private final String b;
    @NotNull
    private final b c;

    public /* synthetic */ j(String str, b bVar, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, bVar);
    }

    @NotNull
    public final String a() {
        return this.b;
    }

    @NotNull
    public final b b() {
        return this.c;
    }

    private j(String channel, b optionalFields) {
        this.b = channel;
        this.c = optionalFields;
    }

    /* compiled from: SubscribeMessage.kt */
    public static final class a {
        @NotNull
        private final String a;
        @NotNull
        private b b = b.a.a().a();

        public a(@NotNull String channel) {
            k.e(channel, "channel");
            this.a = channel;
        }

        @NotNull
        public final a b(@NotNull b bayeuxOptionalFields) {
            k.e(bayeuxOptionalFields, "bayeuxOptionalFields");
            this.b = bayeuxOptionalFields;
            return this;
        }

        @NotNull
        public final j a() {
            return new j(this.a, this.b, (DefaultConstructorMarker) null);
        }
    }

    /* compiled from: SubscribeMessage.kt */
    public static final class b {
        public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private b() {
        }

        @NotNull
        public final a a(@NotNull String channel) {
            k.e(channel, "channel");
            return new a(channel);
        }
    }
}
