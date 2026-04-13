package zendesk.conversationkit.android.model;

import java.util.Date;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ActivityData.kt */
public final class c {
    @NotNull
    private final String a;
    @Nullable
    private final a b;
    @Nullable
    private final String c;
    @Nullable
    private final String d;
    @Nullable
    private final String e;
    @Nullable
    private final g f;
    @Nullable
    private final Date g;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return k.a(this.a, cVar.a) && this.b == cVar.b && k.a(this.c, cVar.c) && k.a(this.d, cVar.d) && k.a(this.e, cVar.e) && this.f == cVar.f && k.a(this.g, cVar.g);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        a aVar = this.b;
        int i = 0;
        int hashCode2 = (hashCode + (aVar == null ? 0 : aVar.hashCode())) * 31;
        String str = this.c;
        int hashCode3 = (hashCode2 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.d;
        int hashCode4 = (hashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.e;
        int hashCode5 = (hashCode4 + (str3 == null ? 0 : str3.hashCode())) * 31;
        g gVar = this.f;
        int hashCode6 = (hashCode5 + (gVar == null ? 0 : gVar.hashCode())) * 31;
        Date date = this.g;
        if (date != null) {
            i = date.hashCode();
        }
        return hashCode6 + i;
    }

    @NotNull
    public String toString() {
        return "ActivityEvent(conversationId=" + this.a + ", activityData=" + this.b + ", userId=" + this.c + ", userName=" + this.d + ", userAvatarUrl=" + this.e + ", role=" + this.f + ", lastRead=" + this.g + ')';
    }

    public c(@NotNull String conversationId, @Nullable a activityData, @Nullable String userId, @Nullable String userName, @Nullable String userAvatarUrl, @Nullable g role, @Nullable Date lastRead) {
        k.e(conversationId, "conversationId");
        this.a = conversationId;
        this.b = activityData;
        this.c = userId;
        this.d = userName;
        this.e = userAvatarUrl;
        this.f = role;
        this.g = lastRead;
    }

    @NotNull
    public final String b() {
        return this.a;
    }

    @Nullable
    public final a a() {
        return this.b;
    }

    @Nullable
    public final String f() {
        return this.c;
    }

    @Nullable
    public final String e() {
        return this.e;
    }

    @Nullable
    public final g d() {
        return this.f;
    }

    @Nullable
    public final Date c() {
        return this.g;
    }
}
