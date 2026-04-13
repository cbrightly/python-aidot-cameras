package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@g(generateAdapter = true)
/* compiled from: MessageDto.kt */
public final class MessageSourceDto {
    @Nullable
    private final String a;
    @NotNull
    private final String b;
    @Nullable
    private final String c;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MessageSourceDto)) {
            return false;
        }
        MessageSourceDto messageSourceDto = (MessageSourceDto) obj;
        return k.a(this.a, messageSourceDto.a) && k.a(this.b, messageSourceDto.b) && k.a(this.c, messageSourceDto.c);
    }

    public int hashCode() {
        String str = this.a;
        int i = 0;
        int hashCode = (((str == null ? 0 : str.hashCode()) * 31) + this.b.hashCode()) * 31;
        String str2 = this.c;
        if (str2 != null) {
            i = str2.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "MessageSourceDto(id=" + this.a + ", type=" + this.b + ", sessionId=" + this.c + ')';
    }

    public MessageSourceDto(@Nullable String id, @NotNull String type, @Nullable String sessionId) {
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        this.a = id;
        this.b = type;
        this.c = sessionId;
    }

    @Nullable
    public final String a() {
        return this.a;
    }

    @NotNull
    public final String c() {
        return this.b;
    }

    @Nullable
    public final String b() {
        return this.c;
    }
}
