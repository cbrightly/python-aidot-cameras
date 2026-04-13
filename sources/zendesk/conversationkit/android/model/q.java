package zendesk.conversationkit.android.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MessageAction.kt */
public enum q {
    BUY("buy"),
    LINK("link"),
    LOCATION_REQUEST("locationRequest"),
    POSTBACK("postback"),
    REPLY("reply"),
    SHARE("share"),
    WEBVIEW("webview");
    
    @NotNull
    public static final a Companion = null;
    @NotNull
    private final String value;

    private q(String value2) {
        this.value = value2;
    }

    @NotNull
    public final String getValue$zendesk_conversationkit_conversationkit_android() {
        return this.value;
    }

    static {
        Companion = new a((DefaultConstructorMarker) null);
    }

    /* compiled from: MessageAction.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        @Nullable
        public final q a(@NotNull String value) {
            k.e(value, "value");
            for (q it : q.values()) {
                if (k.a(it.getValue$zendesk_conversationkit_conversationkit_android(), value)) {
                    return it;
                }
            }
            return null;
        }
    }
}
