package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@g(generateAdapter = true)
/* compiled from: ActivityDataRequestDto.kt */
public final class ActivityDataDto {
    @NotNull
    private final String a;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ActivityDataDto) && k.a(this.a, ((ActivityDataDto) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @NotNull
    public String toString() {
        return "ActivityDataDto(type=" + this.a + ')';
    }

    public ActivityDataDto(@NotNull String type) {
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        this.a = type;
    }

    @NotNull
    public final String a() {
        return this.a;
    }
}
