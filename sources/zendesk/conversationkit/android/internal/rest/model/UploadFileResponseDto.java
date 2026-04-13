package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: UploadFileDto.kt */
public final class UploadFileResponseDto {
    @NotNull
    private final String a;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof UploadFileResponseDto) && k.a(this.a, ((UploadFileResponseDto) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @NotNull
    public String toString() {
        return "UploadFileResponseDto(messageId=" + this.a + ')';
    }

    public UploadFileResponseDto(@NotNull String messageId) {
        k.e(messageId, "messageId");
        this.a = messageId;
    }

    @NotNull
    public final String a() {
        return this.a;
    }
}
