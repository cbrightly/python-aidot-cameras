package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: ConfigResponseDto.kt */
public final class ConfigResponseDto {
    @NotNull
    private final ConfigDto a;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ConfigResponseDto) && k.a(this.a, ((ConfigResponseDto) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @NotNull
    public String toString() {
        return "ConfigResponseDto(config=" + this.a + ')';
    }

    public ConfigResponseDto(@NotNull ConfigDto config) {
        k.e(config, "config");
        this.a = config;
    }

    @NotNull
    public final ConfigDto a() {
        return this.a;
    }
}
