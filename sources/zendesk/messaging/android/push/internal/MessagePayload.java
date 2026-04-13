package zendesk.messaging.android.push.internal;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@g(generateAdapter = true)
/* compiled from: MessagePayload.kt */
public final class MessagePayload {
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
    private final Long k;

    @NotNull
    public final MessagePayload copy(@e(name = "_id") @NotNull String str, @NotNull String str2, @NotNull String str3, @Nullable String str4, @Nullable String str5, double d2, @NotNull String str6, @Nullable String str7, @Nullable String str8, @Nullable String str9, @Nullable Long l) {
        k.e(str, "id");
        k.e(str2, "authorId");
        k.e(str3, "role");
        k.e(str6, IjkMediaMeta.IJKM_KEY_TYPE);
        return new MessagePayload(str, str2, str3, str4, str5, d2, str6, str7, str8, str9, l);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MessagePayload)) {
            return false;
        }
        MessagePayload messagePayload = (MessagePayload) obj;
        return k.a(this.a, messagePayload.a) && k.a(this.b, messagePayload.b) && k.a(this.c, messagePayload.c) && k.a(this.d, messagePayload.d) && k.a(this.e, messagePayload.e) && k.a(Double.valueOf(this.f), Double.valueOf(messagePayload.f)) && k.a(this.g, messagePayload.g) && k.a(this.h, messagePayload.h) && k.a(this.i, messagePayload.i) && k.a(this.j, messagePayload.j) && k.a(this.k, messagePayload.k);
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
        Long l = this.k;
        if (l != null) {
            i2 = l.hashCode();
        }
        return hashCode6 + i2;
    }

    @NotNull
    public String toString() {
        return "MessagePayload(id=" + this.a + ", authorId=" + this.b + ", role=" + this.c + ", name=" + this.d + ", avatarUrl=" + this.e + ", received=" + this.f + ", type=" + this.g + ", text=" + this.h + ", mediaUrl=" + this.i + ", mediaType=" + this.j + ", mediaSize=" + this.k + ')';
    }

    public MessagePayload(@e(name = "_id") @NotNull String id, @NotNull String authorId, @NotNull String role, @Nullable String name, @Nullable String avatarUrl, double received, @NotNull String type, @Nullable String text, @Nullable String mediaUrl, @Nullable String mediaType, @Nullable Long mediaSize) {
        k.e(id, "id");
        k.e(authorId, "authorId");
        k.e(role, "role");
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        this.a = id;
        this.b = authorId;
        this.c = role;
        this.d = name;
        this.e = avatarUrl;
        this.f = received;
        this.g = type;
        this.h = text;
        this.i = mediaUrl;
        this.j = mediaType;
        this.k = mediaSize;
    }

    @NotNull
    public final String c() {
        return this.a;
    }

    @NotNull
    public final String a() {
        return this.b;
    }

    @NotNull
    public final String i() {
        return this.c;
    }

    @Nullable
    public final String g() {
        return this.d;
    }

    @Nullable
    public final String b() {
        return this.e;
    }

    public final double h() {
        return this.f;
    }

    @NotNull
    public final String k() {
        return this.g;
    }

    @Nullable
    public final String j() {
        return this.h;
    }

    @Nullable
    public final String f() {
        return this.i;
    }

    @Nullable
    public final String e() {
        return this.j;
    }

    @Nullable
    public final Long d() {
        return this.k;
    }
}
