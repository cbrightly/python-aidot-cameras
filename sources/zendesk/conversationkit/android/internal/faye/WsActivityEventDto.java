package zendesk.conversationkit.android.internal.faye;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@g(generateAdapter = true)
/* compiled from: WsFayeMessageDto.kt */
public final class WsActivityEventDto {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @Nullable
    private final String c;
    @NotNull
    private final WsActivityEventDataDto d;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WsActivityEventDto)) {
            return false;
        }
        WsActivityEventDto wsActivityEventDto = (WsActivityEventDto) obj;
        return k.a(this.a, wsActivityEventDto.a) && k.a(this.b, wsActivityEventDto.b) && k.a(this.c, wsActivityEventDto.c) && k.a(this.d, wsActivityEventDto.d);
    }

    public int hashCode() {
        int hashCode = ((this.a.hashCode() * 31) + this.b.hashCode()) * 31;
        String str = this.c;
        return ((hashCode + (str == null ? 0 : str.hashCode())) * 31) + this.d.hashCode();
    }

    @NotNull
    public String toString() {
        return "WsActivityEventDto(role=" + this.a + ", type=" + this.b + ", appUserId=" + this.c + ", data=" + this.d + ')';
    }

    public WsActivityEventDto(@NotNull String role, @NotNull String type, @Nullable String appUserId, @NotNull WsActivityEventDataDto data) {
        k.e(role, "role");
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        k.e(data, "data");
        this.a = role;
        this.b = type;
        this.c = appUserId;
        this.d = data;
    }

    @NotNull
    public final String c() {
        return this.a;
    }

    @NotNull
    public final String d() {
        return this.b;
    }

    @Nullable
    public final String a() {
        return this.c;
    }

    @NotNull
    public final WsActivityEventDataDto b() {
        return this.d;
    }
}
