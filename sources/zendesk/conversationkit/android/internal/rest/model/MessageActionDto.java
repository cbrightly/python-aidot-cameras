package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@g(generateAdapter = true)
/* compiled from: MessageDto.kt */
public final class MessageActionDto {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @Nullable
    private final String c;
    @Nullable
    private final String d;
    @Nullable
    private final Boolean e;
    @Nullable
    private final String f;
    @Nullable
    private final String g;
    @Nullable
    private final String h;
    @Nullable
    private final Map<String, Object> i;
    @Nullable
    private final Long j;
    @Nullable
    private final String k;
    @Nullable
    private final String l;

    @NotNull
    public final MessageActionDto copy(@e(name = "_id") @NotNull String str, @NotNull String str2, @Nullable String str3, @Nullable String str4, @Nullable Boolean bool, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable Map<String, ? extends Object> map, @Nullable Long l2, @Nullable String str8, @Nullable String str9) {
        k.e(str, "id");
        k.e(str2, IjkMediaMeta.IJKM_KEY_TYPE);
        return new MessageActionDto(str, str2, str3, str4, bool, str5, str6, str7, map, l2, str8, str9);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MessageActionDto)) {
            return false;
        }
        MessageActionDto messageActionDto = (MessageActionDto) obj;
        return k.a(this.a, messageActionDto.a) && k.a(this.b, messageActionDto.b) && k.a(this.c, messageActionDto.c) && k.a(this.d, messageActionDto.d) && k.a(this.e, messageActionDto.e) && k.a(this.f, messageActionDto.f) && k.a(this.g, messageActionDto.g) && k.a(this.h, messageActionDto.h) && k.a(this.i, messageActionDto.i) && k.a(this.j, messageActionDto.j) && k.a(this.k, messageActionDto.k) && k.a(this.l, messageActionDto.l);
    }

    public int hashCode() {
        int hashCode = ((this.a.hashCode() * 31) + this.b.hashCode()) * 31;
        String str = this.c;
        int i2 = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.d;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Boolean bool = this.e;
        int hashCode4 = (hashCode3 + (bool == null ? 0 : bool.hashCode())) * 31;
        String str3 = this.f;
        int hashCode5 = (hashCode4 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.g;
        int hashCode6 = (hashCode5 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.h;
        int hashCode7 = (hashCode6 + (str5 == null ? 0 : str5.hashCode())) * 31;
        Map<String, Object> map = this.i;
        int hashCode8 = (hashCode7 + (map == null ? 0 : map.hashCode())) * 31;
        Long l2 = this.j;
        int hashCode9 = (hashCode8 + (l2 == null ? 0 : l2.hashCode())) * 31;
        String str6 = this.k;
        int hashCode10 = (hashCode9 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.l;
        if (str7 != null) {
            i2 = str7.hashCode();
        }
        return hashCode10 + i2;
    }

    @NotNull
    public String toString() {
        return "MessageActionDto(id=" + this.a + ", type=" + this.b + ", text=" + this.c + ", uri=" + this.d + ", default=" + this.e + ", iconUrl=" + this.f + ", fallback=" + this.g + ", payload=" + this.h + ", metadata=" + this.i + ", amount=" + this.j + ", currency=" + this.k + ", state=" + this.l + ')';
    }

    public MessageActionDto(@e(name = "_id") @NotNull String id, @NotNull String type, @Nullable String text, @Nullable String uri, @Nullable Boolean bool, @Nullable String iconUrl, @Nullable String fallback, @Nullable String payload, @Nullable Map<String, ? extends Object> metadata, @Nullable Long amount, @Nullable String currency, @Nullable String state) {
        k.e(id, "id");
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        this.a = id;
        this.b = type;
        this.c = text;
        this.d = uri;
        this.e = bool;
        this.f = iconUrl;
        this.g = fallback;
        this.h = payload;
        this.i = metadata;
        this.j = amount;
        this.k = currency;
        this.l = state;
    }

    @NotNull
    public final String f() {
        return this.a;
    }

    @NotNull
    public final String k() {
        return this.b;
    }

    @Nullable
    public final String j() {
        return this.c;
    }

    @Nullable
    public final String l() {
        return this.d;
    }

    @Nullable
    public final Boolean c() {
        return this.e;
    }

    @Nullable
    public final String e() {
        return this.f;
    }

    @Nullable
    public final String d() {
        return this.g;
    }

    @Nullable
    public final String h() {
        return this.h;
    }

    @Nullable
    public final Map<String, Object> g() {
        return this.i;
    }

    @Nullable
    public final Long a() {
        return this.j;
    }

    @Nullable
    public final String b() {
        return this.k;
    }

    @Nullable
    public final String i() {
        return this.l;
    }
}
