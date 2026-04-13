package zendesk.android.settings.internal.model;

import com.squareup.moshi.g;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: SunCoConfigDto.kt */
public final class SunCoConfigDto {
    @NotNull
    private final AppDto a;
    @NotNull
    private final BaseUrlDto b;
    @NotNull
    private final RestRetryPolicyDto c;
    @NotNull
    private final List<ChannelIntegration> d;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SunCoConfigDto)) {
            return false;
        }
        SunCoConfigDto sunCoConfigDto = (SunCoConfigDto) obj;
        return k.a(this.a, sunCoConfigDto.a) && k.a(this.b, sunCoConfigDto.b) && k.a(this.c, sunCoConfigDto.c) && k.a(this.d, sunCoConfigDto.d);
    }

    public int hashCode() {
        return (((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode();
    }

    @NotNull
    public String toString() {
        return "SunCoConfigDto(app=" + this.a + ", baseUrl=" + this.b + ", restRetryPolicy=" + this.c + ", integrations=" + this.d + ')';
    }

    public SunCoConfigDto(@NotNull AppDto app, @NotNull BaseUrlDto baseUrl, @NotNull RestRetryPolicyDto restRetryPolicy, @NotNull List<ChannelIntegration> integrations) {
        k.e(app, "app");
        k.e(baseUrl, "baseUrl");
        k.e(restRetryPolicy, "restRetryPolicy");
        k.e(integrations, "integrations");
        this.a = app;
        this.b = baseUrl;
        this.c = restRetryPolicy;
        this.d = integrations;
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
    public final RestRetryPolicyDto d() {
        return this.c;
    }

    @NotNull
    public final List<ChannelIntegration> c() {
        return this.d;
    }
}
