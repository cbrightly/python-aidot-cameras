package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: UpdateAppUserLocaleDto.kt */
public final class UpdateAppUserLocaleDto {
    @NotNull
    private final String a;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof UpdateAppUserLocaleDto) && k.a(this.a, ((UpdateAppUserLocaleDto) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @NotNull
    public String toString() {
        return "UpdateAppUserLocaleDto(locale=" + this.a + ')';
    }

    public UpdateAppUserLocaleDto(@NotNull String locale) {
        k.e(locale, "locale");
        this.a = locale;
    }

    @NotNull
    public final String a() {
        return this.a;
    }
}
