package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: UserSettingsDto.kt */
public final class RealtimeSettingsDto {
    private final boolean a;
    @NotNull
    private final String b;
    private final int c;
    private final int d;
    private final int e;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RealtimeSettingsDto)) {
            return false;
        }
        RealtimeSettingsDto realtimeSettingsDto = (RealtimeSettingsDto) obj;
        return this.a == realtimeSettingsDto.a && k.a(this.b, realtimeSettingsDto.b) && this.c == realtimeSettingsDto.c && this.d == realtimeSettingsDto.d && this.e == realtimeSettingsDto.e;
    }

    public int hashCode() {
        boolean z = this.a;
        if (z) {
            z = true;
        }
        return ((((((((z ? 1 : 0) * true) + this.b.hashCode()) * 31) + this.c) * 31) + this.d) * 31) + this.e;
    }

    @NotNull
    public String toString() {
        return "RealtimeSettingsDto(enabled=" + this.a + ", baseUrl=" + this.b + ", retryInterval=" + this.c + ", maxConnectionAttempts=" + this.d + ", connectionDelay=" + this.e + ')';
    }

    public RealtimeSettingsDto(boolean enabled, @NotNull String baseUrl, int retryInterval, int maxConnectionAttempts, int connectionDelay) {
        k.e(baseUrl, "baseUrl");
        this.a = enabled;
        this.b = baseUrl;
        this.c = retryInterval;
        this.d = maxConnectionAttempts;
        this.e = connectionDelay;
    }

    public final boolean c() {
        return this.a;
    }

    @NotNull
    public final String a() {
        return this.b;
    }

    public final int e() {
        return this.c;
    }

    public final int d() {
        return this.d;
    }

    public final int b() {
        return this.e;
    }
}
