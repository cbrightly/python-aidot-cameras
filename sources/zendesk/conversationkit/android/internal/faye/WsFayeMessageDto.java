package zendesk.conversationkit.android.internal.faye;

import com.squareup.moshi.g;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;
import zendesk.conversationkit.android.internal.rest.model.MessageDto;

@g(generateAdapter = true)
/* compiled from: WsFayeMessageDto.kt */
public final class WsFayeMessageDto {
    @NotNull
    private final String a;
    @NotNull
    private final WsConversationDto b;
    @Nullable
    private final MessageDto c;
    @Nullable
    private final WsActivityEventDto d;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WsFayeMessageDto)) {
            return false;
        }
        WsFayeMessageDto wsFayeMessageDto = (WsFayeMessageDto) obj;
        return k.a(this.a, wsFayeMessageDto.a) && k.a(this.b, wsFayeMessageDto.b) && k.a(this.c, wsFayeMessageDto.c) && k.a(this.d, wsFayeMessageDto.d);
    }

    public int hashCode() {
        int hashCode = ((this.a.hashCode() * 31) + this.b.hashCode()) * 31;
        MessageDto messageDto = this.c;
        int i = 0;
        int hashCode2 = (hashCode + (messageDto == null ? 0 : messageDto.hashCode())) * 31;
        WsActivityEventDto wsActivityEventDto = this.d;
        if (wsActivityEventDto != null) {
            i = wsActivityEventDto.hashCode();
        }
        return hashCode2 + i;
    }

    @NotNull
    public String toString() {
        return "WsFayeMessageDto(type=" + this.a + ", conversation=" + this.b + ", message=" + this.c + ", activity=" + this.d + ')';
    }

    public WsFayeMessageDto(@NotNull String type, @NotNull WsConversationDto conversation, @Nullable MessageDto message, @Nullable WsActivityEventDto activity) {
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        k.e(conversation, "conversation");
        this.a = type;
        this.b = conversation;
        this.c = message;
        this.d = activity;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ WsFayeMessageDto(String str, WsConversationDto wsConversationDto, MessageDto messageDto, WsActivityEventDto wsActivityEventDto, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, wsConversationDto, (i & 4) != 0 ? null : messageDto, (i & 8) != 0 ? null : wsActivityEventDto);
    }

    @NotNull
    public final String d() {
        return this.a;
    }

    @NotNull
    public final WsConversationDto b() {
        return this.b;
    }

    @Nullable
    public final MessageDto c() {
        return this.c;
    }

    @Nullable
    public final WsActivityEventDto a() {
        return this.d;
    }
}
