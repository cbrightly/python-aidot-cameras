package zendesk.conversationkit.android.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Field.kt */
public enum n {
    TEXT("text"),
    EMAIL("email"),
    SELECT("select");
    
    @NotNull
    public static final a Companion = null;
    @NotNull
    private final String value;

    private n(String value2) {
        this.value = value2;
    }

    @NotNull
    public final String getValue$zendesk_conversationkit_conversationkit_android() {
        return this.value;
    }

    static {
        Companion = new a((DefaultConstructorMarker) null);
    }

    /* compiled from: Field.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        @Nullable
        public final n a(@NotNull String value) {
            k.e(value, "value");
            for (n it : n.values()) {
                if (k.a(it.getValue$zendesk_conversationkit_conversationkit_android(), value)) {
                    return it;
                }
            }
            return null;
        }
    }
}
