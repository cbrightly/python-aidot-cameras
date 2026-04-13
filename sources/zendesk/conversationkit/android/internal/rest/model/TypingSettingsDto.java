package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: UserSettingsDto.kt */
public final class TypingSettingsDto {
    private final boolean a;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof TypingSettingsDto) && this.a == ((TypingSettingsDto) obj).a;
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
        return "TypingSettingsDto(enabled=" + this.a + ')';
    }

    public TypingSettingsDto(boolean enabled) {
        this.a = enabled;
    }

    public final boolean a() {
        return this.a;
    }
}
