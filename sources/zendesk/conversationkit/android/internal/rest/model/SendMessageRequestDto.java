package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: SendMessageRequestDto.kt */
public final class SendMessageRequestDto {
    @NotNull
    private final AuthorDto a;
    @NotNull
    private final SendMessageDto b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SendMessageRequestDto)) {
            return false;
        }
        SendMessageRequestDto sendMessageRequestDto = (SendMessageRequestDto) obj;
        return k.a(this.a, sendMessageRequestDto.a) && k.a(this.b, sendMessageRequestDto.b);
    }

    public int hashCode() {
        return (this.a.hashCode() * 31) + this.b.hashCode();
    }

    @NotNull
    public String toString() {
        return "SendMessageRequestDto(author=" + this.a + ", message=" + this.b + ')';
    }

    public SendMessageRequestDto(@NotNull AuthorDto author, @NotNull SendMessageDto message) {
        k.e(author, "author");
        k.e(message, "message");
        this.a = author;
        this.b = message;
    }

    @NotNull
    public final AuthorDto a() {
        return this.a;
    }

    @NotNull
    public final SendMessageDto b() {
        return this.b;
    }
}
