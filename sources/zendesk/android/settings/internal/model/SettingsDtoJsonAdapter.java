package zendesk.android.settings.internal.model;

import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.f;
import com.squareup.moshi.i;
import com.squareup.moshi.internal.b;
import com.squareup.moshi.o;
import com.squareup.moshi.r;
import kotlin.collections.o0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SettingsDtoJsonAdapter.kt */
public final class SettingsDtoJsonAdapter extends f<SettingsDto> {
    @NotNull
    private final i.a a;
    @NotNull
    private final f<String> b;
    @NotNull
    private final f<ColorThemeDto> c;
    @NotNull
    private final f<Boolean> d;
    @NotNull
    private final f<NativeMessagingDto> e;
    @NotNull
    private final f<SunCoConfigDto> f;

    public SettingsDtoJsonAdapter(@NotNull r moshi) {
        k.e(moshi, "moshi");
        i.a a2 = i.a.a("identifier", "light_theme", "dark_theme", "show_zendesk_logo", "native_messaging", "sunco_config");
        k.d(a2, "of(\"identifier\", \"light_…ssaging\", \"sunco_config\")");
        this.a = a2;
        f<String> f2 = moshi.f(String.class, o0.d(), "identifier");
        k.d(f2, "moshi.adapter(String::cl…emptySet(), \"identifier\")");
        this.b = f2;
        f<ColorThemeDto> f3 = moshi.f(ColorThemeDto.class, o0.d(), "lightTheme");
        k.d(f3, "moshi.adapter(ColorTheme…emptySet(), \"lightTheme\")");
        this.c = f3;
        f<Boolean> f4 = moshi.f(Boolean.class, o0.d(), "showZendeskLogo");
        k.d(f4, "moshi.adapter(Boolean::c…Set(), \"showZendeskLogo\")");
        this.d = f4;
        f<NativeMessagingDto> f5 = moshi.f(NativeMessagingDto.class, o0.d(), "nativeMessaging");
        k.d(f5, "moshi.adapter(NativeMess…Set(), \"nativeMessaging\")");
        this.e = f5;
        f<SunCoConfigDto> f6 = moshi.f(SunCoConfigDto.class, o0.d(), "sunCoConfigDto");
        k.d(f6, "moshi.adapter(SunCoConfi…ySet(), \"sunCoConfigDto\")");
        this.f = f6;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder(33);
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("GeneratedJsonAdapter(");
        $this$toString_u24lambda_u2d0.append("SettingsDto");
        $this$toString_u24lambda_u2d0.append(')');
        String sb2 = sb.toString();
        k.d(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    @NotNull
    /* renamed from: k */
    public SettingsDto b(@NotNull i reader) {
        k.e(reader, "reader");
        String identifier = null;
        ColorThemeDto lightTheme = null;
        ColorThemeDto darkTheme = null;
        Boolean showZendeskLogo = null;
        NativeMessagingDto nativeMessaging = null;
        SunCoConfigDto sunCoConfigDto = null;
        reader.c();
        while (reader.l()) {
            switch (reader.J(this.a)) {
                case -1:
                    reader.o0();
                    reader.u0();
                    break;
                case 0:
                    identifier = this.b.b(reader);
                    break;
                case 1:
                    ColorThemeDto b2 = this.c.b(reader);
                    if (b2 != null) {
                        lightTheme = b2;
                        break;
                    } else {
                        JsonDataException u = b.u("lightTheme", "light_theme", reader);
                        k.d(u, "unexpectedNull(\"lightThe…\", \"light_theme\", reader)");
                        throw u;
                    }
                case 2:
                    ColorThemeDto b3 = this.c.b(reader);
                    if (b3 != null) {
                        darkTheme = b3;
                        break;
                    } else {
                        JsonDataException u2 = b.u("darkTheme", "dark_theme", reader);
                        k.d(u2, "unexpectedNull(\"darkTheme\", \"dark_theme\", reader)");
                        throw u2;
                    }
                case 3:
                    showZendeskLogo = this.d.b(reader);
                    break;
                case 4:
                    NativeMessagingDto b4 = this.e.b(reader);
                    if (b4 != null) {
                        nativeMessaging = b4;
                        break;
                    } else {
                        JsonDataException u3 = b.u("nativeMessaging", "native_messaging", reader);
                        k.d(u3, "unexpectedNull(\"nativeMe…ative_messaging\", reader)");
                        throw u3;
                    }
                case 5:
                    sunCoConfigDto = this.f.b(reader);
                    break;
            }
        }
        reader.i();
        if (lightTheme == null) {
            JsonDataException l = b.l("lightTheme", "light_theme", reader);
            k.d(l, "missingProperty(\"lightTh…\", \"light_theme\", reader)");
            throw l;
        } else if (darkTheme == null) {
            JsonDataException l2 = b.l("darkTheme", "dark_theme", reader);
            k.d(l2, "missingProperty(\"darkTheme\", \"dark_theme\", reader)");
            throw l2;
        } else if (nativeMessaging != null) {
            return new SettingsDto(identifier, lightTheme, darkTheme, showZendeskLogo, nativeMessaging, sunCoConfigDto);
        } else {
            JsonDataException l3 = b.l("nativeMessaging", "native_messaging", reader);
            k.d(l3, "missingProperty(\"nativeM…ative_messaging\", reader)");
            throw l3;
        }
    }

    /* renamed from: l */
    public void i(@NotNull o writer, @Nullable SettingsDto value_) {
        k.e(writer, "writer");
        if (value_ != null) {
            writer.g();
            writer.r("identifier");
            this.b.i(writer, value_.b());
            writer.r("light_theme");
            this.c.i(writer, value_.c());
            writer.r("dark_theme");
            this.c.i(writer, value_.a());
            writer.r("show_zendesk_logo");
            this.d.i(writer, value_.e());
            writer.r("native_messaging");
            this.e.i(writer, value_.d());
            writer.r("sunco_config");
            this.f.i(writer, value_.f());
            writer.m();
            return;
        }
        throw new NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.");
    }
}
