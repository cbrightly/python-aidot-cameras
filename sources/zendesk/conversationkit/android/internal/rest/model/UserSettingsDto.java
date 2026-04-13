package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: UserSettingsDto.kt */
public final class UserSettingsDto {
    @NotNull
    private final RealtimeSettingsDto a;
    @NotNull
    private final TypingSettingsDto b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserSettingsDto)) {
            return false;
        }
        UserSettingsDto userSettingsDto = (UserSettingsDto) obj;
        return k.a(this.a, userSettingsDto.a) && k.a(this.b, userSettingsDto.b);
    }

    public int hashCode() {
        return (this.a.hashCode() * 31) + this.b.hashCode();
    }

    @NotNull
    public String toString() {
        return "UserSettingsDto(realtime=" + this.a + ", typing=" + this.b + ')';
    }

    public UserSettingsDto(@NotNull RealtimeSettingsDto realtime, @NotNull TypingSettingsDto typing) {
        k.e(realtime, "realtime");
        k.e(typing, "typing");
        this.a = realtime;
        this.b = typing;
    }

    @NotNull
    public final RealtimeSettingsDto a() {
        return this.a;
    }

    @NotNull
    public final TypingSettingsDto b() {
        return this.b;
    }
}
