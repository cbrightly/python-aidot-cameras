package zendesk.android.settings.internal.model;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@g(generateAdapter = true)
/* compiled from: SunCoConfigDto.kt */
public final class ChannelIntegration {
    @NotNull
    private final String a;
    @NotNull
    private final String b;

    @NotNull
    public final ChannelIntegration copy(@e(name = "_id") @NotNull String str, @NotNull String str2) {
        k.e(str, "id");
        k.e(str2, IjkMediaMeta.IJKM_KEY_TYPE);
        return new ChannelIntegration(str, str2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ChannelIntegration)) {
            return false;
        }
        ChannelIntegration channelIntegration = (ChannelIntegration) obj;
        return k.a(this.a, channelIntegration.a) && k.a(this.b, channelIntegration.b);
    }

    public int hashCode() {
        return (this.a.hashCode() * 31) + this.b.hashCode();
    }

    @NotNull
    public String toString() {
        return "ChannelIntegration(id=" + this.a + ", type=" + this.b + ')';
    }

    public ChannelIntegration(@e(name = "_id") @NotNull String id, @NotNull String type) {
        k.e(id, "id");
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        this.a = id;
        this.b = type;
    }

    @NotNull
    public final String a() {
        return this.a;
    }

    @NotNull
    public final String b() {
        return this.b;
    }
}
