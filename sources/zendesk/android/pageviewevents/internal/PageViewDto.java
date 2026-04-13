package zendesk.android.pageviewevents.internal;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: PageViewEventsDto.kt */
public final class PageViewDto {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @NotNull
    private final String c;

    @NotNull
    public final PageViewDto copy(@e(name = "pageTitle") @NotNull String str, @e(name = "navigatorLanguage") @NotNull String str2, @e(name = "userAgent") @NotNull String str3) {
        k.e(str, "pageTitle");
        k.e(str2, "navigatorLanguage");
        k.e(str3, "userAgent");
        return new PageViewDto(str, str2, str3);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PageViewDto)) {
            return false;
        }
        PageViewDto pageViewDto = (PageViewDto) obj;
        return k.a(this.a, pageViewDto.a) && k.a(this.b, pageViewDto.b) && k.a(this.c, pageViewDto.c);
    }

    public int hashCode() {
        return (((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode();
    }

    @NotNull
    public String toString() {
        return "PageViewDto(pageTitle=" + this.a + ", navigatorLanguage=" + this.b + ", userAgent=" + this.c + ')';
    }

    public PageViewDto(@e(name = "pageTitle") @NotNull String pageTitle, @e(name = "navigatorLanguage") @NotNull String navigatorLanguage, @e(name = "userAgent") @NotNull String userAgent) {
        k.e(pageTitle, "pageTitle");
        k.e(navigatorLanguage, "navigatorLanguage");
        k.e(userAgent, "userAgent");
        this.a = pageTitle;
        this.b = navigatorLanguage;
        this.c = userAgent;
    }

    @NotNull
    public final String b() {
        return this.a;
    }

    @NotNull
    public final String a() {
        return this.b;
    }

    @NotNull
    public final String c() {
        return this.c;
    }
}
