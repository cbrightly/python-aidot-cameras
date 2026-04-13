package zendesk.conversationkit.android.model;

import com.squareup.moshi.g;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: User.kt */
public final class TypingSettings {
    private final boolean a;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof TypingSettings) && this.a == ((TypingSettings) obj).a;
    }

    public int hashCode() {
        boolean z = this.a;
        if (z) {
            return 1;
        }
        return z ? 1 : 0;
    }

    @NotNull
    public String toString() {
        return "TypingSettings(enabled=" + this.a + ')';
    }

    public TypingSettings(boolean enabled) {
        this.a = enabled;
    }

    public final boolean a() {
        return this.a;
    }
}
