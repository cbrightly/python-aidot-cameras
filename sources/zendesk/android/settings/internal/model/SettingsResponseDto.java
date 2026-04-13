package zendesk.android.settings.internal.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: SettingsDto.kt */
public final class SettingsResponseDto {
    @NotNull
    private final SettingsDto a;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SettingsResponseDto) && k.a(this.a, ((SettingsResponseDto) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @NotNull
    public String toString() {
        return "SettingsResponseDto(settings=" + this.a + ')';
    }

    public SettingsResponseDto(@NotNull SettingsDto settings) {
        k.e(settings, "settings");
        this.a = settings;
    }

    @NotNull
    public final SettingsDto a() {
        return this.a;
    }
}
