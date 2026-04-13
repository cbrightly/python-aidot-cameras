package zendesk.android;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ZendeskUser.kt */
public final class g {
    @NotNull
    private final String a;
    @Nullable
    private final String b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof g)) {
            return false;
        }
        g gVar = (g) obj;
        return k.a(this.a, gVar.a) && k.a(this.b, gVar.b);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        String str = this.b;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    @NotNull
    public String toString() {
        return "ZendeskUser(id=" + this.a + ", externalId=" + this.b + ')';
    }

    public g(@NotNull String id, @Nullable String externalId) {
        k.e(id, "id");
        this.a = id;
        this.b = externalId;
    }

    @NotNull
    public final String b() {
        return this.a;
    }

    @Nullable
    public final String a() {
        return this.b;
    }
}
