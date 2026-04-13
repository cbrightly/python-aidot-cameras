package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: MessageDto.kt */
public final class DisplaySettingsDto {
    @NotNull
    private final String a;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof DisplaySettingsDto) && k.a(this.a, ((DisplaySettingsDto) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @NotNull
    public String toString() {
        return "DisplaySettingsDto(imageAspectRatio=" + this.a + ')';
    }

    public DisplaySettingsDto(@NotNull String imageAspectRatio) {
        k.e(imageAspectRatio, "imageAspectRatio");
        this.a = imageAspectRatio;
    }

    @NotNull
    public final String a() {
        return this.a;
    }
}
