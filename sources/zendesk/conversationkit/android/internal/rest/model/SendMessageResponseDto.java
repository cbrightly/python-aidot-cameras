package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: SendMessageResponseDto.kt */
public final class SendMessageResponseDto {
    @NotNull
    private final List<MessageDto> a;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SendMessageResponseDto) && k.a(this.a, ((SendMessageResponseDto) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @NotNull
    public String toString() {
        return "SendMessageResponseDto(messages=" + this.a + ')';
    }

    public SendMessageResponseDto(@NotNull List<MessageDto> messages) {
        k.e(messages, "messages");
        this.a = messages;
    }

    @NotNull
    public final List<MessageDto> a() {
        return this.a;
    }
}
