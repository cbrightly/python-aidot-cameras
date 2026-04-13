package zendesk.conversationkit.android.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@g(generateAdapter = true)
/* compiled from: Author.kt */
public final class Author {
    @NotNull
    private final String a;
    @NotNull
    private final g b;
    @NotNull
    private final String c;
    @Nullable
    private final String d;

    public Author() {
        this((String) null, (g) null, (String) null, (String) null, 15, (DefaultConstructorMarker) null);
    }

    public static /* synthetic */ Author b(Author author, String str, g gVar, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = author.a;
        }
        if ((i & 2) != 0) {
            gVar = author.b;
        }
        if ((i & 4) != 0) {
            str2 = author.c;
        }
        if ((i & 8) != 0) {
            str3 = author.d;
        }
        return author.a(str, gVar, str2, str3);
    }

    @NotNull
    public final Author a(@NotNull String str, @NotNull g gVar, @NotNull String str2, @Nullable String str3) {
        k.e(str, "userId");
        k.e(gVar, IjkMediaMeta.IJKM_KEY_TYPE);
        k.e(str2, "displayName");
        return new Author(str, gVar, str2, str3);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Author)) {
            return false;
        }
        Author author = (Author) obj;
        return k.a(this.a, author.a) && this.b == author.b && k.a(this.c, author.c) && k.a(this.d, author.d);
    }

    public int hashCode() {
        int hashCode = ((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31;
        String str = this.d;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    @NotNull
    public String toString() {
        return "Author(userId=" + this.a + ", type=" + this.b + ", displayName=" + this.c + ", avatarUrl=" + this.d + ')';
    }

    public Author(@NotNull String userId, @NotNull g type, @NotNull String displayName, @Nullable String avatarUrl) {
        k.e(userId, "userId");
        k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
        k.e(displayName, "displayName");
        this.a = userId;
        this.b = type;
        this.c = displayName;
        this.d = avatarUrl;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ Author(java.lang.String r1, zendesk.conversationkit.android.model.g r2, java.lang.String r3, java.lang.String r4, int r5, kotlin.jvm.internal.DefaultConstructorMarker r6) {
        /*
            r0 = this;
            r6 = r5 & 1
            if (r6 == 0) goto L_0x0011
            java.util.UUID r1 = java.util.UUID.randomUUID()
            java.lang.String r1 = r1.toString()
            java.lang.String r6 = "randomUUID().toString()"
            kotlin.jvm.internal.k.d(r1, r6)
        L_0x0011:
            r6 = r5 & 2
            if (r6 == 0) goto L_0x0017
            zendesk.conversationkit.android.model.g r2 = zendesk.conversationkit.android.model.g.USER
        L_0x0017:
            r6 = r5 & 4
            if (r6 == 0) goto L_0x001d
            java.lang.String r3 = ""
        L_0x001d:
            r5 = r5 & 8
            if (r5 == 0) goto L_0x0022
            r4 = 0
        L_0x0022:
            r0.<init>(r1, r2, r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.model.Author.<init>(java.lang.String, zendesk.conversationkit.android.model.g, java.lang.String, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public final String f() {
        return this.a;
    }

    @NotNull
    public final g e() {
        return this.b;
    }

    @NotNull
    public final String d() {
        return this.c;
    }

    @Nullable
    public final String c() {
        return this.d;
    }
}
