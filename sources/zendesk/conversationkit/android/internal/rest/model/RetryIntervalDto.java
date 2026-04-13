package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: RestRetryPolicyDto.kt */
public final class RetryIntervalDto {
    private final int a;
    private final int b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RetryIntervalDto)) {
            return false;
        }
        RetryIntervalDto retryIntervalDto = (RetryIntervalDto) obj;
        return this.a == retryIntervalDto.a && this.b == retryIntervalDto.b;
    }

    public int hashCode() {
        return (this.a * 31) + this.b;
    }

    @NotNull
    public String toString() {
        return "RetryIntervalDto(regular=" + this.a + ", aggressive=" + this.b + ')';
    }

    public RetryIntervalDto(int regular, int aggressive) {
        this.a = regular;
        this.b = aggressive;
    }

    public final int b() {
        return this.a;
    }

    public final int a() {
        return this.b;
    }
}
