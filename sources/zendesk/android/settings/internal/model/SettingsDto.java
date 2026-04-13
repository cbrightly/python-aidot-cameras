package zendesk.android.settings.internal.model;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: SettingsDto.kt */
public final class SettingsDto {
    @Nullable
    private final String a;
    @NotNull
    private final ColorThemeDto b;
    @NotNull
    private final ColorThemeDto c;
    @Nullable
    private final Boolean d;
    @NotNull
    private final NativeMessagingDto e;
    @Nullable
    private final SunCoConfigDto f;

    @NotNull
    public final SettingsDto copy(@Nullable String str, @e(name = "light_theme") @NotNull ColorThemeDto colorThemeDto, @e(name = "dark_theme") @NotNull ColorThemeDto colorThemeDto2, @e(name = "show_zendesk_logo") @Nullable Boolean bool, @e(name = "native_messaging") @NotNull NativeMessagingDto nativeMessagingDto, @e(name = "sunco_config") @Nullable SunCoConfigDto sunCoConfigDto) {
        k.e(colorThemeDto, "lightTheme");
        k.e(colorThemeDto2, "darkTheme");
        k.e(nativeMessagingDto, "nativeMessaging");
        return new SettingsDto(str, colorThemeDto, colorThemeDto2, bool, nativeMessagingDto, sunCoConfigDto);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SettingsDto)) {
            return false;
        }
        SettingsDto settingsDto = (SettingsDto) obj;
        return k.a(this.a, settingsDto.a) && k.a(this.b, settingsDto.b) && k.a(this.c, settingsDto.c) && k.a(this.d, settingsDto.d) && k.a(this.e, settingsDto.e) && k.a(this.f, settingsDto.f);
    }

    public int hashCode() {
        String str = this.a;
        int i = 0;
        int hashCode = (((((str == null ? 0 : str.hashCode()) * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31;
        Boolean bool = this.d;
        int hashCode2 = (((hashCode + (bool == null ? 0 : bool.hashCode())) * 31) + this.e.hashCode()) * 31;
        SunCoConfigDto sunCoConfigDto = this.f;
        if (sunCoConfigDto != null) {
            i = sunCoConfigDto.hashCode();
        }
        return hashCode2 + i;
    }

    @NotNull
    public String toString() {
        return "SettingsDto(identifier=" + this.a + ", lightTheme=" + this.b + ", darkTheme=" + this.c + ", showZendeskLogo=" + this.d + ", nativeMessaging=" + this.e + ", sunCoConfigDto=" + this.f + ')';
    }

    public SettingsDto(@Nullable String identifier, @e(name = "light_theme") @NotNull ColorThemeDto lightTheme, @e(name = "dark_theme") @NotNull ColorThemeDto darkTheme, @e(name = "show_zendesk_logo") @Nullable Boolean showZendeskLogo, @e(name = "native_messaging") @NotNull NativeMessagingDto nativeMessaging, @e(name = "sunco_config") @Nullable SunCoConfigDto sunCoConfigDto) {
        k.e(lightTheme, "lightTheme");
        k.e(darkTheme, "darkTheme");
        k.e(nativeMessaging, "nativeMessaging");
        this.a = identifier;
        this.b = lightTheme;
        this.c = darkTheme;
        this.d = showZendeskLogo;
        this.e = nativeMessaging;
        this.f = sunCoConfigDto;
    }

    @Nullable
    public final String b() {
        return this.a;
    }

    @NotNull
    public final ColorThemeDto c() {
        return this.b;
    }

    @NotNull
    public final ColorThemeDto a() {
        return this.c;
    }

    @Nullable
    public final Boolean e() {
        return this.d;
    }

    @NotNull
    public final NativeMessagingDto d() {
        return this.e;
    }

    @Nullable
    public final SunCoConfigDto f() {
        return this.f;
    }
}
