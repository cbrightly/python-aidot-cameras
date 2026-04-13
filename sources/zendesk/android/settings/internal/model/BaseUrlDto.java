package zendesk.android.settings.internal.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: SunCoConfigDto.kt */
public final class BaseUrlDto {
    @NotNull
    private final String a;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof BaseUrlDto) && k.a(this.a, ((BaseUrlDto) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @NotNull
    public String toString() {
        return "BaseUrlDto(android=" + this.a + ')';
    }

    public BaseUrlDto(@NotNull String android2) {
        k.e(android2, "android");
        this.a = android2;
    }

    @NotNull
    public final String a() {
        return this.a;
    }
}
