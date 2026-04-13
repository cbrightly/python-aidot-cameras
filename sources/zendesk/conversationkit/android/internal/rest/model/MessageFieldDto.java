package zendesk.conversationkit.android.internal.rest.model;

import com.google.firebase.messaging.Constants;
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
public final class MessageFieldDto {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @NotNull
    private final String c;
    @NotNull
    private final String d;
    @Nullable
    private final Map<String, Object> e;
    @Nullable
    private final String f;
    @Nullable
    private final String g;
    @Nullable
    private final Integer h;
    @Nullable
    private final Integer i;
    @Nullable
    private final String j;
    @Nullable
    private final List<MessageFieldOptionDto> k;
    @Nullable
    private final List<MessageFieldOptionDto> l;
    @Nullable
    private final Integer m;

    @NotNull
    public final MessageFieldDto copy(@e(name = "_id") @NotNull String str, @NotNull String str2, @NotNull String str3, @NotNull String str4, @Nullable Map<String, ? extends Object> map, @Nullable String str5, @Nullable String str6, @Nullable Integer num, @Nullable Integer num2, @Nullable String str7, @Nullable List<MessageFieldOptionDto> list, @Nullable List<MessageFieldOptionDto> list2, @Nullable Integer num3) {
        k.e(str, "id");
        k.e(str2, "name");
        k.e(str3, Constants.ScionAnalytics.PARAM_LABEL);
        k.e(str4, IjkMediaMeta.IJKM_KEY_TYPE);
        return new MessageFieldDto(str, str2, str3, str4, map, str5, str6, num, num2, str7, list, list2, num3);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MessageFieldDto)) {
            return false;
        }
        MessageFieldDto messageFieldDto = (MessageFieldDto) obj;
        return k.a(this.a, messageFieldDto.a) && k.a(this.b, messageFieldDto.b) && k.a(this.c, messageFieldDto.c) && k.a(this.d, messageFieldDto.d) && k.a(this.e, messageFieldDto.e) && k.a(this.f, messageFieldDto.f) && k.a(this.g, messageFieldDto.g) && k.a(this.h, messageFieldDto.h) && k.a(this.i, messageFieldDto.i) && k.a(this.j, messageFieldDto.j) && k.a(this.k, messageFieldDto.k) && k.a(this.l, messageFieldDto.l) && k.a(this.m, messageFieldDto.m);
    }

    public int hashCode() {
        int hashCode = ((((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode()) * 31;
        Map<String, Object> map = this.e;
        int i2 = 0;
        int hashCode2 = (hashCode + (map == null ? 0 : map.hashCode())) * 31;
        String str = this.f;
        int hashCode3 = (hashCode2 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.g;
        int hashCode4 = (hashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Integer num = this.h;
        int hashCode5 = (hashCode4 + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.i;
        int hashCode6 = (hashCode5 + (num2 == null ? 0 : num2.hashCode())) * 31;
        String str3 = this.j;
        int hashCode7 = (hashCode6 + (str3 == null ? 0 : str3.hashCode())) * 31;
        List<MessageFieldOptionDto> list = this.k;
        int hashCode8 = (hashCode7 + (list == null ? 0 : list.hashCode())) * 31;
        List<MessageFieldOptionDto> list2 = this.l;
        int hashCode9 = (hashCode8 + (list2 == null ? 0 : list2.hashCode())) * 31;
        Integer num3 = this.m;
        if (num3 != null) {
            i2 = num3.hashCode();
        }
        return hashCode9 + i2;
    }

    @NotNull
    public String toString() {
        return "MessageFieldDto(id=" + this.a + ", name=" + this.b + ", label=" + this.c + ", type=" + this.d + ", metadata=" + this.e + ", placeholder=" + this.f + ", text=" + this.g + ", minSize=" + this.h + ", maxSize=" + this.i + ", email=" + this.j + ", options=" + this.k + ", select=" + this.l + ", selectSize=" + this.m + ')';
    }

    public MessageFieldDto(@e(name = "_id") @NotNull String id, @NotNull String name, @NotNull String label, @NotNull String type, @Nullable Map<String, ? extends Object> metadata, @Nullable String placeholder, @Nullable String text, @Nullable Integer minSize, @Nullable Integer maxSize, @Nullable String email, @Nullable List<MessageFieldOptionDto> options, @Nullable List<MessageFieldOptionDto> select, @Nullable Integer selectSize) {
        k.e(id, "id");
        k.e(name, "name");
        k.e(label, Constants.ScionAnalytics.PARAM_LABEL);
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        this.a = id;
        this.b = name;
        this.c = label;
        this.d = type;
        this.e = metadata;
        this.f = placeholder;
        this.g = text;
        this.h = minSize;
        this.i = maxSize;
        this.j = email;
        this.k = options;
        this.l = select;
        this.m = selectSize;
    }

    @NotNull
    public final String b() {
        return this.a;
    }

    @NotNull
    public final String g() {
        return this.b;
    }

    @NotNull
    public final String c() {
        return this.c;
    }

    @NotNull
    public final String m() {
        return this.d;
    }

    @Nullable
    public final Map<String, Object> e() {
        return this.e;
    }

    @Nullable
    public final String i() {
        return this.f;
    }

    @Nullable
    public final String l() {
        return this.g;
    }

    @Nullable
    public final Integer f() {
        return this.h;
    }

    @Nullable
    public final Integer d() {
        return this.i;
    }

    @Nullable
    public final String a() {
        return this.j;
    }

    @Nullable
    public final List<MessageFieldOptionDto> h() {
        return this.k;
    }

    @Nullable
    public final List<MessageFieldOptionDto> j() {
        return this.l;
    }

    @Nullable
    public final Integer k() {
        return this.m;
    }
}
