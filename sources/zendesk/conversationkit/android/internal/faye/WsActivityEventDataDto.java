package zendesk.conversationkit.android.internal.faye;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: WsFayeMessageDto.kt */
public final class WsActivityEventDataDto {
    @Nullable
    private final String a;
    @Nullable
    private final String b;
    @Nullable
    private final Double c;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WsActivityEventDataDto)) {
            return false;
        }
        WsActivityEventDataDto wsActivityEventDataDto = (WsActivityEventDataDto) obj;
        return k.a(this.a, wsActivityEventDataDto.a) && k.a(this.b, wsActivityEventDataDto.b) && k.a(this.c, wsActivityEventDataDto.c);
    }

    public int hashCode() {
        String str = this.a;
        int i = 0;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.b;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        Double d = this.c;
        if (d != null) {
            i = d.hashCode();
        }
        return hashCode2 + i;
    }

    @NotNull
    public String toString() {
        return "WsActivityEventDataDto(name=" + this.a + ", avatarUrl=" + this.b + ", lastRead=" + this.c + ')';
    }

    public WsActivityEventDataDto(@Nullable String name, @Nullable String avatarUrl, @Nullable Double lastRead) {
        this.a = name;
        this.b = avatarUrl;
        this.c = lastRead;
    }

    @Nullable
    public final String c() {
        return this.a;
    }

    @Nullable
    public final String a() {
        return this.b;
    }

    @Nullable
    public final Double b() {
        return this.c;
    }
}
