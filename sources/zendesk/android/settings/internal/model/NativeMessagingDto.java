package zendesk.android.settings.internal.model;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: NativeMessagingDto.kt */
public final class NativeMessagingDto {
    @Nullable
    private final String a;
    @Nullable
    private final String b;
    private final boolean c;
    @Nullable
    private final BrandDto d;
    @Nullable
    private final String e;
    @Nullable
    private final String f;
    @Nullable
    private final String g;

    @NotNull
    public final NativeMessagingDto copy(@e(name = "integration_id") @Nullable String str, @Nullable String str2, boolean z, @Nullable BrandDto brandDto, @Nullable String str3, @Nullable String str4, @e(name = "logo_url") @Nullable String str5) {
        return new NativeMessagingDto(str, str2, z, brandDto, str3, str4, str5);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NativeMessagingDto)) {
            return false;
        }
        NativeMessagingDto nativeMessagingDto = (NativeMessagingDto) obj;
        return k.a(this.a, nativeMessagingDto.a) && k.a(this.b, nativeMessagingDto.b) && this.c == nativeMessagingDto.c && k.a(this.d, nativeMessagingDto.d) && k.a(this.e, nativeMessagingDto.e) && k.a(this.f, nativeMessagingDto.f) && k.a(this.g, nativeMessagingDto.g);
    }

    public int hashCode() {
        String str = this.a;
        int i = 0;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.b;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        boolean z = this.c;
        if (z) {
            z = true;
        }
        int i2 = (hashCode2 + (z ? 1 : 0)) * 31;
        BrandDto brandDto = this.d;
        int hashCode3 = (i2 + (brandDto == null ? 0 : brandDto.hashCode())) * 31;
        String str3 = this.e;
        int hashCode4 = (hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.f;
        int hashCode5 = (hashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.g;
        if (str5 != null) {
            i = str5.hashCode();
        }
        return hashCode5 + i;
    }

    @NotNull
    public String toString() {
        return "NativeMessagingDto(integrationId=" + this.a + ", platform=" + this.b + ", enabled=" + this.c + ", brand=" + this.d + ", title=" + this.e + ", description=" + this.f + ", logoUrl=" + this.g + ')';
    }

    public NativeMessagingDto(@e(name = "integration_id") @Nullable String integrationId, @Nullable String platform, boolean enabled, @Nullable BrandDto brand, @Nullable String title, @Nullable String description, @e(name = "logo_url") @Nullable String logoUrl) {
        this.a = integrationId;
        this.b = platform;
        this.c = enabled;
        this.d = brand;
        this.e = title;
        this.f = description;
        this.g = logoUrl;
    }

    @Nullable
    public final String d() {
        return this.a;
    }

    @Nullable
    public final String f() {
        return this.b;
    }

    public final boolean c() {
        return this.c;
    }

    @Nullable
    public final BrandDto a() {
        return this.d;
    }

    @Nullable
    public final String g() {
        return this.e;
    }

    @Nullable
    public final String b() {
        return this.f;
    }

    @Nullable
    public final String e() {
        return this.g;
    }
}
