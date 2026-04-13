package zendesk.android.settings.internal.model;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: BrandDto.kt */
public final class BrandDto {
    @Nullable
    private final Long a;
    @Nullable
    private final Long b;
    @Nullable
    private final String c;
    @Nullable
    private final Boolean d;
    @Nullable
    private final String e;
    @Nullable
    private final String f;
    @Nullable
    private final String g;
    @Nullable
    private final Long h;
    @Nullable
    private final String i;

    @NotNull
    public final BrandDto copy(@Nullable Long l, @e(name = "account_id") @Nullable Long l2, @Nullable String str, @Nullable Boolean bool, @e(name = "deleted_at") @Nullable String str2, @e(name = "created_at") @Nullable String str3, @e(name = "updated_at") @Nullable String str4, @e(name = "route_id") @Nullable Long l3, @e(name = "signature_template") @Nullable String str5) {
        return new BrandDto(l, l2, str, bool, str2, str3, str4, l3, str5);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BrandDto)) {
            return false;
        }
        BrandDto brandDto = (BrandDto) obj;
        return k.a(this.a, brandDto.a) && k.a(this.b, brandDto.b) && k.a(this.c, brandDto.c) && k.a(this.d, brandDto.d) && k.a(this.e, brandDto.e) && k.a(this.f, brandDto.f) && k.a(this.g, brandDto.g) && k.a(this.h, brandDto.h) && k.a(this.i, brandDto.i);
    }

    public int hashCode() {
        Long l = this.a;
        int i2 = 0;
        int hashCode = (l == null ? 0 : l.hashCode()) * 31;
        Long l2 = this.b;
        int hashCode2 = (hashCode + (l2 == null ? 0 : l2.hashCode())) * 31;
        String str = this.c;
        int hashCode3 = (hashCode2 + (str == null ? 0 : str.hashCode())) * 31;
        Boolean bool = this.d;
        int hashCode4 = (hashCode3 + (bool == null ? 0 : bool.hashCode())) * 31;
        String str2 = this.e;
        int hashCode5 = (hashCode4 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.f;
        int hashCode6 = (hashCode5 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.g;
        int hashCode7 = (hashCode6 + (str4 == null ? 0 : str4.hashCode())) * 31;
        Long l3 = this.h;
        int hashCode8 = (hashCode7 + (l3 == null ? 0 : l3.hashCode())) * 31;
        String str5 = this.i;
        if (str5 != null) {
            i2 = str5.hashCode();
        }
        return hashCode8 + i2;
    }

    @NotNull
    public String toString() {
        return "BrandDto(id=" + this.a + ", accountId=" + this.b + ", name=" + this.c + ", active=" + this.d + ", deletedAt=" + this.e + ", createdAt=" + this.f + ", updatedAt=" + this.g + ", routeId=" + this.h + ", signatureTemplate=" + this.i + ')';
    }

    public BrandDto(@Nullable Long id, @e(name = "account_id") @Nullable Long accountId, @Nullable String name, @Nullable Boolean active, @e(name = "deleted_at") @Nullable String deletedAt, @e(name = "created_at") @Nullable String createdAt, @e(name = "updated_at") @Nullable String updatedAt, @e(name = "route_id") @Nullable Long routeId, @e(name = "signature_template") @Nullable String signatureTemplate) {
        this.a = id;
        this.b = accountId;
        this.c = name;
        this.d = active;
        this.e = deletedAt;
        this.f = createdAt;
        this.g = updatedAt;
        this.h = routeId;
        this.i = signatureTemplate;
    }

    @Nullable
    public final Long e() {
        return this.a;
    }

    @Nullable
    public final Long a() {
        return this.b;
    }

    @Nullable
    public final String f() {
        return this.c;
    }

    @Nullable
    public final Boolean b() {
        return this.d;
    }

    @Nullable
    public final String d() {
        return this.e;
    }

    @Nullable
    public final String c() {
        return this.f;
    }

    @Nullable
    public final String i() {
        return this.g;
    }

    @Nullable
    public final Long g() {
        return this.h;
    }

    @Nullable
    public final String h() {
        return this.i;
    }
}
