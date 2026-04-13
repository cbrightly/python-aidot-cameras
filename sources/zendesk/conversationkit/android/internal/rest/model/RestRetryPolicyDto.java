package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: RestRetryPolicyDto.kt */
public final class RestRetryPolicyDto {
    @NotNull
    private final RetryIntervalDto a;
    private final int b;
    private final int c;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RestRetryPolicyDto)) {
            return false;
        }
        RestRetryPolicyDto restRetryPolicyDto = (RestRetryPolicyDto) obj;
        return k.a(this.a, restRetryPolicyDto.a) && this.b == restRetryPolicyDto.b && this.c == restRetryPolicyDto.c;
    }

    public int hashCode() {
        return (((this.a.hashCode() * 31) + this.b) * 31) + this.c;
    }

    @NotNull
    public String toString() {
        return "RestRetryPolicyDto(intervals=" + this.a + ", backoffMultiplier=" + this.b + ", maxRetries=" + this.c + ')';
    }

    public RestRetryPolicyDto(@NotNull RetryIntervalDto intervals, int backoffMultiplier, int maxRetries) {
        k.e(intervals, "intervals");
        this.a = intervals;
        this.b = backoffMultiplier;
        this.c = maxRetries;
    }

    @NotNull
    public final RetryIntervalDto b() {
        return this.a;
    }

    public final int a() {
        return this.b;
    }

    public final int c() {
        return this.c;
    }
}
