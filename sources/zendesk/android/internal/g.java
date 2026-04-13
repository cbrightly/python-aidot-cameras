package zendesk.android.internal;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.android.d;

/* compiled from: ZendeskComponent.kt */
public final class g {
    @NotNull
    private final d a;
    @NotNull
    private final String b;
    @NotNull
    private final String c;
    @NotNull
    private final String d;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof g)) {
            return false;
        }
        g gVar = (g) obj;
        return k.a(this.a, gVar.a) && k.a(this.b, gVar.b) && k.a(this.c, gVar.c) && k.a(this.d, gVar.d);
    }

    public int hashCode() {
        return (((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode();
    }

    @NotNull
    public String toString() {
        return "ZendeskComponentConfig(channelKey=" + this.a + ", baseUrl=" + this.b + ", versionName=" + this.c + ", osVersion=" + this.d + ')';
    }

    public g(@NotNull d channelKey, @NotNull String baseUrl, @NotNull String versionName, @NotNull String osVersion) {
        k.e(channelKey, "channelKey");
        k.e(baseUrl, "baseUrl");
        k.e(versionName, "versionName");
        k.e(osVersion, "osVersion");
        this.a = channelKey;
        this.b = baseUrl;
        this.c = versionName;
        this.d = osVersion;
    }

    @NotNull
    public final d b() {
        return this.a;
    }

    @NotNull
    public final String a() {
        return this.b;
    }

    @NotNull
    public final String d() {
        return this.c;
    }

    @NotNull
    public final String c() {
        return this.d;
    }
}
