package zendesk.android.pageviewevents.internal;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import meshsdk.ConfigUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: PageViewEventsDto.kt */
public final class PageViewEventDto {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @NotNull
    private final String c;
    @NotNull
    private final String d;
    @NotNull
    private final String e;
    @NotNull
    private final String f;
    @NotNull
    private final PageViewDto g;

    @NotNull
    public final PageViewEventDto copy(@e(name = "url") @NotNull String str, @e(name = "buid") @NotNull String str2, @e(name = "channel") @NotNull String str3, @e(name = "version") @NotNull String str4, @e(name = "timestamp") @NotNull String str5, @e(name = "suid") @NotNull String str6, @e(name = "pageView") @NotNull PageViewDto pageViewDto) {
        k.e(str, "url");
        k.e(str2, "buid");
        k.e(str3, "channel");
        k.e(str4, ConfigUtil.VERSION_FILE);
        k.e(str5, "timestamp");
        k.e(str6, "suid");
        k.e(pageViewDto, "pageView");
        return new PageViewEventDto(str, str2, str3, str4, str5, str6, pageViewDto);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PageViewEventDto)) {
            return false;
        }
        PageViewEventDto pageViewEventDto = (PageViewEventDto) obj;
        return k.a(this.a, pageViewEventDto.a) && k.a(this.b, pageViewEventDto.b) && k.a(this.c, pageViewEventDto.c) && k.a(this.d, pageViewEventDto.d) && k.a(this.e, pageViewEventDto.e) && k.a(this.f, pageViewEventDto.f) && k.a(this.g, pageViewEventDto.g);
    }

    public int hashCode() {
        return (((((((((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31) + this.f.hashCode()) * 31) + this.g.hashCode();
    }

    @NotNull
    public String toString() {
        return "PageViewEventDto(url=" + this.a + ", buid=" + this.b + ", channel=" + this.c + ", version=" + this.d + ", timestamp=" + this.e + ", suid=" + this.f + ", pageView=" + this.g + ')';
    }

    public PageViewEventDto(@e(name = "url") @NotNull String url, @e(name = "buid") @NotNull String buid, @e(name = "channel") @NotNull String channel, @e(name = "version") @NotNull String version, @e(name = "timestamp") @NotNull String timestamp, @e(name = "suid") @NotNull String suid, @e(name = "pageView") @NotNull PageViewDto pageView) {
        k.e(url, "url");
        k.e(buid, "buid");
        k.e(channel, "channel");
        k.e(version, ConfigUtil.VERSION_FILE);
        k.e(timestamp, "timestamp");
        k.e(suid, "suid");
        k.e(pageView, "pageView");
        this.a = url;
        this.b = buid;
        this.c = channel;
        this.d = version;
        this.e = timestamp;
        this.f = suid;
        this.g = pageView;
    }

    @NotNull
    public final String f() {
        return this.a;
    }

    @NotNull
    public final String a() {
        return this.b;
    }

    @NotNull
    public final String b() {
        return this.c;
    }

    @NotNull
    public final String g() {
        return this.d;
    }

    @NotNull
    public final String e() {
        return this.e;
    }

    @NotNull
    public final String d() {
        return this.f;
    }

    @NotNull
    public final PageViewDto c() {
        return this.g;
    }
}
