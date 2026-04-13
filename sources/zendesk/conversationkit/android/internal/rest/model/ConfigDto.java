package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: ConfigDto.kt */
public final class ConfigDto {
    @NotNull
    private final AppDto a;
    @NotNull
    private final BaseUrlDto b;
    @NotNull
    private final RestRetryPolicyDto c;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConfigDto)) {
            return false;
        }
        ConfigDto configDto = (ConfigDto) obj;
        return k.a(this.a, configDto.a) && k.a(this.b, configDto.b) && k.a(this.c, configDto.c);
    }

    public int hashCode() {
        return (((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
    }

    @NotNull
    public String toString() {
        return "ConfigDto(app=" + this.a + ", baseUrl=" + this.b + ", restRetryPolicy=" + this.c + ')';
    }

    public ConfigDto(@NotNull AppDto app, @NotNull BaseUrlDto baseUrl, @NotNull RestRetryPolicyDto restRetryPolicy) {
        k.e(app, "app");
        k.e(baseUrl, "baseUrl");
        k.e(restRetryPolicy, "restRetryPolicy");
        this.a = app;
        this.b = baseUrl;
        this.c = restRetryPolicy;
    }

    @NotNull
    public final AppDto a() {
        return this.a;
    }

    @NotNull
    public final BaseUrlDto b() {
        return this.b;
    }

    @NotNull
    public final RestRetryPolicyDto c() {
        return this.c;
    }
}
