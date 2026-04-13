package zendesk.conversationkit.android.internal.faye;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: WsFayeMessageDto.kt */
public enum d {
    MESSAGE("message"),
    ACTIVITY("activity");
    
    @NotNull
    public static final a Companion = null;
    @NotNull
    private final String value;

    private d(String value2) {
        this.value = value2;
    }

    @NotNull
    public final String getValue() {
        return this.value;
    }

    static {
        Companion = new a((DefaultConstructorMarker) null);
    }

    /* compiled from: WsFayeMessageDto.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        @Nullable
        public final d a(@NotNull String value) {
            k.e(value, "value");
            d dVar = d.MESSAGE;
            if (k.a(value, dVar.getValue())) {
                return dVar;
            }
            d dVar2 = d.ACTIVITY;
            if (k.a(value, dVar2.getValue())) {
                return dVar2;
            }
            return null;
        }
    }
}
