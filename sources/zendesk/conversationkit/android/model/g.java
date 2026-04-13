package zendesk.conversationkit.android.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Author.kt */
public enum g {
    USER("appUser"),
    BUSINESS("appMaker");
    
    @NotNull
    public static final a Companion = null;
    @NotNull
    private final String value;

    private g(String value2) {
        this.value = value2;
    }

    @NotNull
    public final String getValue$zendesk_conversationkit_conversationkit_android() {
        return this.value;
    }

    static {
        Companion = new a((DefaultConstructorMarker) null);
    }

    /* compiled from: Author.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        @NotNull
        public final g a(@NotNull String value) {
            g it;
            k.e(value, "value");
            g[] values = g.values();
            int length = values.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    it = null;
                    break;
                }
                it = values[i];
                if (k.a(it.getValue$zendesk_conversationkit_conversationkit_android(), value)) {
                    break;
                }
                i++;
            }
            return it == null ? g.USER : it;
        }
    }
}
