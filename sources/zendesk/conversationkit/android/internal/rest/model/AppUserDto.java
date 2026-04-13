package zendesk.conversationkit.android.internal.rest.model;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: AppUserDto.kt */
public final class AppUserDto {
    @NotNull
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
    @NotNull
    private final List<ClientDto> h;
    @NotNull
    private final List<ClientDto> i;
    @NotNull
    private final Map<String, Object> j;

    @NotNull
    public final AppUserDto copy(@e(name = "_id") @NotNull String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @NotNull List<ClientDto> list, @NotNull List<ClientDto> list2, @NotNull Map<String, ? extends Object> map) {
        k.e(str, "id");
        k.e(list, "clients");
        k.e(list2, "pendingClients");
        k.e(map, "properties");
        return new AppUserDto(str, str2, str3, str4, str5, str6, str7, list, list2, map);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppUserDto)) {
            return false;
        }
        AppUserDto appUserDto = (AppUserDto) obj;
        return k.a(this.a, appUserDto.a) && k.a(this.b, appUserDto.b) && k.a(this.c, appUserDto.c) && k.a(this.d, appUserDto.d) && k.a(this.e, appUserDto.e) && k.a(this.f, appUserDto.f) && k.a(this.g, appUserDto.g) && k.a(this.h, appUserDto.h) && k.a(this.i, appUserDto.i) && k.a(this.j, appUserDto.j);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        String str = this.b;
        int i2 = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.c;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.d;
        int hashCode4 = (hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.e;
        int hashCode5 = (hashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.f;
        int hashCode6 = (hashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.g;
        if (str6 != null) {
            i2 = str6.hashCode();
        }
        return ((((((hashCode6 + i2) * 31) + this.h.hashCode()) * 31) + this.i.hashCode()) * 31) + this.j.hashCode();
    }

    @NotNull
    public String toString() {
        return "AppUserDto(id=" + this.a + ", userId=" + this.b + ", givenName=" + this.c + ", surname=" + this.d + ", email=" + this.e + ", locale=" + this.f + ", signedUpAt=" + this.g + ", clients=" + this.h + ", pendingClients=" + this.i + ", properties=" + this.j + ')';
    }

    public AppUserDto(@e(name = "_id") @NotNull String id, @Nullable String userId, @Nullable String givenName, @Nullable String surname, @Nullable String email, @Nullable String locale, @Nullable String signedUpAt, @NotNull List<ClientDto> clients, @NotNull List<ClientDto> pendingClients, @NotNull Map<String, ? extends Object> properties) {
        k.e(id, "id");
        k.e(clients, "clients");
        k.e(pendingClients, "pendingClients");
        k.e(properties, "properties");
        this.a = id;
        this.b = userId;
        this.c = givenName;
        this.d = surname;
        this.e = email;
        this.f = locale;
        this.g = signedUpAt;
        this.h = clients;
        this.i = pendingClients;
        this.j = properties;
    }

    @NotNull
    public final String d() {
        return this.a;
    }

    @Nullable
    public final String j() {
        return this.b;
    }

    @Nullable
    public final String c() {
        return this.c;
    }

    @Nullable
    public final String i() {
        return this.d;
    }

    @Nullable
    public final String b() {
        return this.e;
    }

    @Nullable
    public final String e() {
        return this.f;
    }

    @Nullable
    public final String h() {
        return this.g;
    }

    @NotNull
    public final List<ClientDto> a() {
        return this.h;
    }

    @NotNull
    public final List<ClientDto> f() {
        return this.i;
    }

    @NotNull
    public final Map<String, Object> g() {
        return this.j;
    }
}
