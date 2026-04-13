package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: ClientDto.kt */
public final class ClientInfoDto {
    @Nullable
    private final String a;
    @Nullable
    private final String b;
    @Nullable
    private final String c;
    @Nullable
    private final String d;
    @Nullable
    private final String e;
    @Nullable
    private final String f;
    @Nullable
    private final String g;
    @Nullable
    private final String h;
    @Nullable
    private final String i;
    @Nullable
    private final String j;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClientInfoDto)) {
            return false;
        }
        ClientInfoDto clientInfoDto = (ClientInfoDto) obj;
        return k.a(this.a, clientInfoDto.a) && k.a(this.b, clientInfoDto.b) && k.a(this.c, clientInfoDto.c) && k.a(this.d, clientInfoDto.d) && k.a(this.e, clientInfoDto.e) && k.a(this.f, clientInfoDto.f) && k.a(this.g, clientInfoDto.g) && k.a(this.h, clientInfoDto.h) && k.a(this.i, clientInfoDto.i) && k.a(this.j, clientInfoDto.j);
    }

    public int hashCode() {
        String str = this.a;
        int i2 = 0;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.b;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.c;
        int hashCode3 = (hashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.d;
        int hashCode4 = (hashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.e;
        int hashCode5 = (hashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.f;
        int hashCode6 = (hashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.g;
        int hashCode7 = (hashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.h;
        int hashCode8 = (hashCode7 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.i;
        int hashCode9 = (hashCode8 + (str9 == null ? 0 : str9.hashCode())) * 31;
        String str10 = this.j;
        if (str10 != null) {
            i2 = str10.hashCode();
        }
        return hashCode9 + i2;
    }

    @NotNull
    public String toString() {
        return "ClientInfoDto(appId=" + this.a + ", appName=" + this.b + ", vendor=" + this.c + ", sdkVersion=" + this.d + ", devicePlatform=" + this.e + ", os=" + this.f + ", osVersion=" + this.g + ", installer=" + this.h + ", carrier=" + this.i + ", locale=" + this.j + ')';
    }

    public ClientInfoDto(@Nullable String appId, @Nullable String appName, @Nullable String vendor, @Nullable String sdkVersion, @Nullable String devicePlatform, @Nullable String os, @Nullable String osVersion, @Nullable String installer, @Nullable String carrier, @Nullable String locale) {
        this.a = appId;
        this.b = appName;
        this.c = vendor;
        this.d = sdkVersion;
        this.e = devicePlatform;
        this.f = os;
        this.g = osVersion;
        this.h = installer;
        this.i = carrier;
        this.j = locale;
    }

    @Nullable
    public final String a() {
        return this.a;
    }

    @Nullable
    public final String b() {
        return this.b;
    }

    @Nullable
    public final String j() {
        return this.c;
    }

    @Nullable
    public final String i() {
        return this.d;
    }

    @Nullable
    public final String d() {
        return this.e;
    }

    @Nullable
    public final String g() {
        return this.f;
    }

    @Nullable
    public final String h() {
        return this.g;
    }

    @Nullable
    public final String e() {
        return this.h;
    }

    @Nullable
    public final String c() {
        return this.i;
    }

    @Nullable
    public final String f() {
        return this.j;
    }
}
