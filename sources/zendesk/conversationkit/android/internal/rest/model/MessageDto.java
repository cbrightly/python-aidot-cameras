package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@g(generateAdapter = true)
/* compiled from: MessageDto.kt */
public final class MessageDto {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @NotNull
    private final String c;
    @Nullable
    private final String d;
    @Nullable
    private final String e;
    private final double f;
    @NotNull
    private final String g;
    @Nullable
    private final String h;
    @Nullable
    private final String i;
    @Nullable
    private final String j;
    @Nullable
    private final String k;
    @Nullable
    private final Map<String, Object> l;
    @Nullable
    private final String m;
    @Nullable
    private final String n;
    @Nullable
    private final Long o;
    @Nullable
    private final CoordinatesDto p;
    @Nullable
    private final LocationDto q;
    @Nullable
    private final List<MessageActionDto> r;
    @Nullable
    private final List<MessageItemDto> s;
    @Nullable
    private final DisplaySettingsDto t;
    @Nullable
    private final Boolean u;
    @Nullable
    private final List<MessageFieldDto> v;
    @Nullable
    private final String w;
    @Nullable
    private final MessageSourceDto x;

    @NotNull
    public final MessageDto copy(@e(name = "_id") @NotNull String str, @NotNull String str2, @NotNull String str3, @Nullable String str4, @Nullable String str5, double d2, @NotNull String str6, @Nullable String str7, @Nullable String str8, @Nullable String str9, @Nullable String str10, @Nullable Map<String, ? extends Object> map, @Nullable String str11, @Nullable String str12, @Nullable Long l2, @Nullable CoordinatesDto coordinatesDto, @Nullable LocationDto locationDto, @Nullable List<MessageActionDto> list, @Nullable List<MessageItemDto> list2, @Nullable DisplaySettingsDto displaySettingsDto, @Nullable Boolean bool, @Nullable List<MessageFieldDto> list3, @Nullable String str13, @Nullable MessageSourceDto messageSourceDto) {
        String str14 = str;
        k.e(str14, "id");
        k.e(str2, "authorId");
        k.e(str3, "role");
        k.e(str6, IjkMediaMeta.IJKM_KEY_TYPE);
        return new MessageDto(str14, str2, str3, str4, str5, d2, str6, str7, str8, str9, str10, map, str11, str12, l2, coordinatesDto, locationDto, list, list2, displaySettingsDto, bool, list3, str13, messageSourceDto);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MessageDto)) {
            return false;
        }
        MessageDto messageDto = (MessageDto) obj;
        return k.a(this.a, messageDto.a) && k.a(this.b, messageDto.b) && k.a(this.c, messageDto.c) && k.a(this.d, messageDto.d) && k.a(this.e, messageDto.e) && k.a(Double.valueOf(this.f), Double.valueOf(messageDto.f)) && k.a(this.g, messageDto.g) && k.a(this.h, messageDto.h) && k.a(this.i, messageDto.i) && k.a(this.j, messageDto.j) && k.a(this.k, messageDto.k) && k.a(this.l, messageDto.l) && k.a(this.m, messageDto.m) && k.a(this.n, messageDto.n) && k.a(this.o, messageDto.o) && k.a(this.p, messageDto.p) && k.a(this.q, messageDto.q) && k.a(this.r, messageDto.r) && k.a(this.s, messageDto.s) && k.a(this.t, messageDto.t) && k.a(this.u, messageDto.u) && k.a(this.v, messageDto.v) && k.a(this.w, messageDto.w) && k.a(this.x, messageDto.x);
    }

    public int hashCode() {
        int hashCode = ((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31;
        String str = this.d;
        int i2 = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.e;
        int hashCode3 = (((((hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31) + Double.doubleToLongBits(this.f)) * 31) + this.g.hashCode()) * 31;
        String str3 = this.h;
        int hashCode4 = (hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.i;
        int hashCode5 = (hashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.j;
        int hashCode6 = (hashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.k;
        int hashCode7 = (hashCode6 + (str6 == null ? 0 : str6.hashCode())) * 31;
        Map<String, Object> map = this.l;
        int hashCode8 = (hashCode7 + (map == null ? 0 : map.hashCode())) * 31;
        String str7 = this.m;
        int hashCode9 = (hashCode8 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.n;
        int hashCode10 = (hashCode9 + (str8 == null ? 0 : str8.hashCode())) * 31;
        Long l2 = this.o;
        int hashCode11 = (hashCode10 + (l2 == null ? 0 : l2.hashCode())) * 31;
        CoordinatesDto coordinatesDto = this.p;
        int hashCode12 = (hashCode11 + (coordinatesDto == null ? 0 : coordinatesDto.hashCode())) * 31;
        LocationDto locationDto = this.q;
        int hashCode13 = (hashCode12 + (locationDto == null ? 0 : locationDto.hashCode())) * 31;
        List<MessageActionDto> list = this.r;
        int hashCode14 = (hashCode13 + (list == null ? 0 : list.hashCode())) * 31;
        List<MessageItemDto> list2 = this.s;
        int hashCode15 = (hashCode14 + (list2 == null ? 0 : list2.hashCode())) * 31;
        DisplaySettingsDto displaySettingsDto = this.t;
        int hashCode16 = (hashCode15 + (displaySettingsDto == null ? 0 : displaySettingsDto.hashCode())) * 31;
        Boolean bool = this.u;
        int hashCode17 = (hashCode16 + (bool == null ? 0 : bool.hashCode())) * 31;
        List<MessageFieldDto> list3 = this.v;
        int hashCode18 = (hashCode17 + (list3 == null ? 0 : list3.hashCode())) * 31;
        String str9 = this.w;
        int hashCode19 = (hashCode18 + (str9 == null ? 0 : str9.hashCode())) * 31;
        MessageSourceDto messageSourceDto = this.x;
        if (messageSourceDto != null) {
            i2 = messageSourceDto.hashCode();
        }
        return hashCode19 + i2;
    }

    @NotNull
    public String toString() {
        return "MessageDto(id=" + this.a + ", authorId=" + this.b + ", role=" + this.c + ", name=" + this.d + ", avatarUrl=" + this.e + ", received=" + this.f + ", type=" + this.g + ", text=" + this.h + ", textFallback=" + this.i + ", altText=" + this.j + ", payload=" + this.k + ", metadata=" + this.l + ", mediaUrl=" + this.m + ", mediaType=" + this.n + ", mediaSize=" + this.o + ", coordinates=" + this.p + ", location=" + this.q + ", actions=" + this.r + ", items=" + this.s + ", displaySettings=" + this.t + ", blockChatInput=" + this.u + ", fields=" + this.v + ", quotedMessageId=" + this.w + ", source=" + this.x + ')';
    }

    public MessageDto(@e(name = "_id") @NotNull String id, @NotNull String authorId, @NotNull String role, @Nullable String name, @Nullable String avatarUrl, double received, @NotNull String type, @Nullable String text, @Nullable String textFallback, @Nullable String altText, @Nullable String payload, @Nullable Map<String, ? extends Object> metadata, @Nullable String mediaUrl, @Nullable String mediaType, @Nullable Long mediaSize, @Nullable CoordinatesDto coordinates, @Nullable LocationDto location, @Nullable List<MessageActionDto> actions, @Nullable List<MessageItemDto> items, @Nullable DisplaySettingsDto displaySettings, @Nullable Boolean blockChatInput, @Nullable List<MessageFieldDto> fields, @Nullable String quotedMessageId, @Nullable MessageSourceDto source) {
        String str = id;
        String str2 = authorId;
        String str3 = role;
        String str4 = type;
        k.e(str, "id");
        k.e(str2, "authorId");
        k.e(str3, "role");
        k.e(str4, IjkMediaMeta.IJKM_KEY_TYPE);
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = name;
        this.e = avatarUrl;
        this.f = received;
        this.g = str4;
        this.h = text;
        this.i = textFallback;
        this.j = altText;
        this.k = payload;
        this.l = metadata;
        this.m = mediaUrl;
        this.n = mediaType;
        this.o = mediaSize;
        this.p = coordinates;
        this.q = location;
        this.r = actions;
        this.s = items;
        this.t = displaySettings;
        this.u = blockChatInput;
        this.v = fields;
        this.w = quotedMessageId;
        this.x = source;
    }

    @NotNull
    public final String i() {
        return this.a;
    }

    @NotNull
    public final String c() {
        return this.b;
    }

    @NotNull
    public final String t() {
        return this.c;
    }

    @Nullable
    public final String p() {
        return this.d;
    }

    @Nullable
    public final String d() {
        return this.e;
    }

    public final double s() {
        return this.f;
    }

    @NotNull
    public final String x() {
        return this.g;
    }

    @Nullable
    public final String v() {
        return this.h;
    }

    @Nullable
    public final String w() {
        return this.i;
    }

    @Nullable
    public final String b() {
        return this.j;
    }

    @Nullable
    public final String q() {
        return this.k;
    }

    @Nullable
    public final Map<String, Object> o() {
        return this.l;
    }

    @Nullable
    public final String n() {
        return this.m;
    }

    @Nullable
    public final String m() {
        return this.n;
    }

    @Nullable
    public final Long l() {
        return this.o;
    }

    @Nullable
    public final CoordinatesDto f() {
        return this.p;
    }

    @Nullable
    public final LocationDto k() {
        return this.q;
    }

    @Nullable
    public final List<MessageActionDto> a() {
        return this.r;
    }

    @Nullable
    public final List<MessageItemDto> j() {
        return this.s;
    }

    @Nullable
    public final DisplaySettingsDto g() {
        return this.t;
    }

    @Nullable
    public final Boolean e() {
        return this.u;
    }

    @Nullable
    public final List<MessageFieldDto> h() {
        return this.v;
    }

    @Nullable
    public final String r() {
        return this.w;
    }

    @Nullable
    public final MessageSourceDto u() {
        return this.x;
    }
}
