package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;
import zendesk.conversationkit.android.model.k;

@g(generateAdapter = true)
/* compiled from: CreateConversationRequestDto.kt */
public final class CreateConversationRequestDto {
    @NotNull
    private final k a;
    @NotNull
    private final b b;
    @NotNull
    private final ClientDto c;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CreateConversationRequestDto)) {
            return false;
        }
        CreateConversationRequestDto createConversationRequestDto = (CreateConversationRequestDto) obj;
        return this.a == createConversationRequestDto.a && this.b == createConversationRequestDto.b && kotlin.jvm.internal.k.a(this.c, createConversationRequestDto.c);
    }

    public int hashCode() {
        return (((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
    }

    @NotNull
    public String toString() {
        return "CreateConversationRequestDto(type=" + this.a + ", intent=" + this.b + ", client=" + this.c + ')';
    }

    public CreateConversationRequestDto(@NotNull k type, @NotNull b intent, @NotNull ClientDto client) {
        kotlin.jvm.internal.k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        kotlin.jvm.internal.k.e(intent, "intent");
        kotlin.jvm.internal.k.e(client, "client");
        this.a = type;
        this.b = intent;
        this.c = client;
    }

    @NotNull
    public final k c() {
        return this.a;
    }

    @NotNull
    public final b b() {
        return this.b;
    }

    @NotNull
    public final ClientDto a() {
        return this.c;
    }
}
