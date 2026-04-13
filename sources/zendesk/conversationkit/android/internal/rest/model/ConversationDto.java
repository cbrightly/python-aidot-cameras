package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@g(generateAdapter = true)
/* compiled from: ConversationDto.kt */
public final class ConversationDto {
    @NotNull
    private final String a;
    @Nullable
    private final String b;
    @Nullable
    private final String c;
    @Nullable
    private final String d;
    @NotNull
    private final String e;
    private final boolean f;
    @Nullable
    private final List<String> g;
    @Nullable
    private final Double h;
    @Nullable
    private final Double i;
    @Nullable
    private final List<ParticipantDto> j;
    @Nullable
    private final List<MessageDto> k;

    @NotNull
    public final ConversationDto copy(@e(name = "_id") @NotNull String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @NotNull String str5, boolean z, @Nullable List<String> list, @Nullable Double d2, @Nullable Double d3, @Nullable List<ParticipantDto> list2, @Nullable List<MessageDto> list3) {
        k.e(str, "id");
        k.e(str5, IjkMediaMeta.IJKM_KEY_TYPE);
        return new ConversationDto(str, str2, str3, str4, str5, z, list, d2, d3, list2, list3);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConversationDto)) {
            return false;
        }
        ConversationDto conversationDto = (ConversationDto) obj;
        return k.a(this.a, conversationDto.a) && k.a(this.b, conversationDto.b) && k.a(this.c, conversationDto.c) && k.a(this.d, conversationDto.d) && k.a(this.e, conversationDto.e) && this.f == conversationDto.f && k.a(this.g, conversationDto.g) && k.a(this.h, conversationDto.h) && k.a(this.i, conversationDto.i) && k.a(this.j, conversationDto.j) && k.a(this.k, conversationDto.k);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        String str = this.b;
        int i2 = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.c;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.d;
        int hashCode4 = (((hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31) + this.e.hashCode()) * 31;
        boolean z = this.f;
        if (z) {
            z = true;
        }
        int i3 = (hashCode4 + (z ? 1 : 0)) * 31;
        List<String> list = this.g;
        int hashCode5 = (i3 + (list == null ? 0 : list.hashCode())) * 31;
        Double d2 = this.h;
        int hashCode6 = (hashCode5 + (d2 == null ? 0 : d2.hashCode())) * 31;
        Double d3 = this.i;
        int hashCode7 = (hashCode6 + (d3 == null ? 0 : d3.hashCode())) * 31;
        List<ParticipantDto> list2 = this.j;
        int hashCode8 = (hashCode7 + (list2 == null ? 0 : list2.hashCode())) * 31;
        List<MessageDto> list3 = this.k;
        if (list3 != null) {
            i2 = list3.hashCode();
        }
        return hashCode8 + i2;
    }

    @NotNull
    public String toString() {
        return "ConversationDto(id=" + this.a + ", displayName=" + this.b + ", description=" + this.c + ", iconUrl=" + this.d + ", type=" + this.e + ", isDefault=" + this.f + ", appMakers=" + this.g + ", appMakerLastRead=" + this.h + ", lastUpdatedAt=" + this.i + ", participants=" + this.j + ", messages=" + this.k + ')';
    }

    public ConversationDto(@e(name = "_id") @NotNull String id, @Nullable String displayName, @Nullable String description, @Nullable String iconUrl, @NotNull String type, boolean isDefault, @Nullable List<String> appMakers, @Nullable Double appMakerLastRead, @Nullable Double lastUpdatedAt, @Nullable List<ParticipantDto> participants, @Nullable List<MessageDto> messages) {
        k.e(id, "id");
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        this.a = id;
        this.b = displayName;
        this.c = description;
        this.d = iconUrl;
        this.e = type;
        this.f = isDefault;
        this.g = appMakers;
        this.h = appMakerLastRead;
        this.i = lastUpdatedAt;
        this.j = participants;
        this.k = messages;
    }

    @NotNull
    public final String f() {
        return this.a;
    }

    @Nullable
    public final String d() {
        return this.b;
    }

    @Nullable
    public final String c() {
        return this.c;
    }

    @Nullable
    public final String e() {
        return this.d;
    }

    @NotNull
    public final String j() {
        return this.e;
    }

    public final boolean k() {
        return this.f;
    }

    @Nullable
    public final List<String> b() {
        return this.g;
    }

    @Nullable
    public final Double a() {
        return this.h;
    }

    @Nullable
    public final Double g() {
        return this.i;
    }

    @Nullable
    public final List<ParticipantDto> i() {
        return this.j;
    }

    @Nullable
    public final List<MessageDto> h() {
        return this.k;
    }
}
