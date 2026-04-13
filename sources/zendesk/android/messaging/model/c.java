package zendesk.android.messaging.model;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MessagingSettings.kt */
public final class c {
    @Nullable
    private final String a;
    private final boolean b;
    @NotNull
    private final String c;
    @NotNull
    private final String d;
    @NotNull
    private final String e;
    @NotNull
    private final String f;
    @NotNull
    private final a g;
    @NotNull
    private final a h;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return k.a(this.a, cVar.a) && this.b == cVar.b && k.a(this.c, cVar.c) && k.a(this.d, cVar.d) && k.a(this.e, cVar.e) && k.a(this.f, cVar.f) && k.a(this.g, cVar.g) && k.a(this.h, cVar.h);
    }

    public int hashCode() {
        String str = this.a;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        boolean z = this.b;
        if (z) {
            z = true;
        }
        return ((((((((((((hashCode + (z ? 1 : 0)) * 31) + this.c.hashCode()) * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31) + this.f.hashCode()) * 31) + this.g.hashCode()) * 31) + this.h.hashCode();
    }

    @NotNull
    public String toString() {
        return "MessagingSettings(integrationId=" + this.a + ", enabled=" + this.b + ", brand=" + this.c + ", title=" + this.d + ", description=" + this.e + ", logoUrl=" + this.f + ", lightTheme=" + this.g + ", darkTheme=" + this.h + ')';
    }

    public c(@Nullable String integrationId, boolean enabled, @NotNull String brand, @NotNull String title, @NotNull String description, @NotNull String logoUrl, @NotNull a lightTheme, @NotNull a darkTheme) {
        k.e(brand, "brand");
        k.e(title, "title");
        k.e(description, "description");
        k.e(logoUrl, "logoUrl");
        k.e(lightTheme, "lightTheme");
        k.e(darkTheme, "darkTheme");
        this.a = integrationId;
        this.b = enabled;
        this.c = brand;
        this.d = title;
        this.e = description;
        this.f = logoUrl;
        this.g = lightTheme;
        this.h = darkTheme;
    }

    @Nullable
    public final String c() {
        return this.a;
    }

    @NotNull
    public final String f() {
        return this.d;
    }

    @NotNull
    public final String b() {
        return this.e;
    }

    @NotNull
    public final String e() {
        return this.f;
    }

    @NotNull
    public final a d() {
        return this.g;
    }

    @NotNull
    public final a a() {
        return this.h;
    }
}
