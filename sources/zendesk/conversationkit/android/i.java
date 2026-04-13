package zendesk.conversationkit.android;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ConversationKitSettings.kt */
public final class i {
    @NotNull
    public static final b a = new b((DefaultConstructorMarker) null);
    @NotNull
    private final String b;
    @NotNull
    private final c c;
    @NotNull
    private final String d;

    public /* synthetic */ i(String str, c cVar, String str2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, cVar, str2);
    }

    private i(String integrationId, c region, String baseUrl) {
        this.b = integrationId;
        this.c = region;
        this.d = baseUrl;
    }

    @NotNull
    public final String b() {
        return this.b;
    }

    @NotNull
    public final c c() {
        return this.c;
    }

    @NotNull
    public final String a() {
        return this.d;
    }

    /* compiled from: ConversationKitSettings.kt */
    public static final class a {
        @NotNull
        private final String a;
        @NotNull
        private c b = c.US;
        @Nullable
        private String c;

        public a(@NotNull String integrationId) {
            k.e(integrationId, "integrationId");
            this.a = integrationId;
        }

        @NotNull
        public final i a() {
            String str = this.a;
            c cVar = this.b;
            String str2 = this.c;
            if (str2 == null) {
                str2 = "";
            }
            return new i(str, cVar, str2, (DefaultConstructorMarker) null);
        }
    }

    /* compiled from: ConversationKitSettings.kt */
    public enum c {
        US(""),
        EU(".eu-1");
        
        @NotNull
        private final String value;

        private c(String value2) {
            this.value = value2;
        }

        @NotNull
        public final String getValue$zendesk_conversationkit_conversationkit_android() {
            return this.value;
        }
    }

    /* compiled from: ConversationKitSettings.kt */
    public static final class b {
        public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private b() {
        }

        @NotNull
        public final a a(@NotNull String integrationId) {
            k.e(integrationId, "integrationId");
            return new a(integrationId);
        }
    }
}
