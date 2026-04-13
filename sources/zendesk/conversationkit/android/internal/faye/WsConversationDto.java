package zendesk.conversationkit.android.internal.faye;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: WsFayeMessageDto.kt */
public final class WsConversationDto {
    @NotNull
    private final String a;
    @Nullable
    private final Double b;

    @NotNull
    public final WsConversationDto copy(@e(name = "_id") @NotNull String str, @Nullable Double d) {
        k.e(str, "id");
        return new WsConversationDto(str, d);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WsConversationDto)) {
            return false;
        }
        WsConversationDto wsConversationDto = (WsConversationDto) obj;
        return k.a(this.a, wsConversationDto.a) && k.a(this.b, wsConversationDto.b);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        Double d = this.b;
        return hashCode + (d == null ? 0 : d.hashCode());
    }

    @NotNull
    public String toString() {
        return "WsConversationDto(id=" + this.a + ", appMakerLastRead=" + this.b + ')';
    }

    public WsConversationDto(@e(name = "_id") @NotNull String id, @Nullable Double appMakerLastRead) {
        k.e(id, "id");
        this.a = id;
        this.b = appMakerLastRead;
    }

    @NotNull
    public final String b() {
        return this.a;
    }

    @Nullable
    public final Double a() {
        return this.b;
    }
}
