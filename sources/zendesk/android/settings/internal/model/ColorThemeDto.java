package zendesk.android.settings.internal.model;

import com.squareup.moshi.e;
import com.squareup.moshi.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@g(generateAdapter = true)
/* compiled from: ColorThemeDto.kt */
public final class ColorThemeDto {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @NotNull
    private final String c;
    @Nullable
    private final String d;

    @NotNull
    public final ColorThemeDto copy(@e(name = "primary_color") @NotNull String str, @e(name = "message_color") @NotNull String str2, @e(name = "action_color") @NotNull String str3, @e(name = "notify_color") @Nullable String str4) {
        k.e(str, "primaryColor");
        k.e(str2, "messageColor");
        k.e(str3, "actionColor");
        return new ColorThemeDto(str, str2, str3, str4);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ColorThemeDto)) {
            return false;
        }
        ColorThemeDto colorThemeDto = (ColorThemeDto) obj;
        return k.a(this.a, colorThemeDto.a) && k.a(this.b, colorThemeDto.b) && k.a(this.c, colorThemeDto.c) && k.a(this.d, colorThemeDto.d);
    }

    public int hashCode() {
        int hashCode = ((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31;
        String str = this.d;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    @NotNull
    public String toString() {
        return "ColorThemeDto(primaryColor=" + this.a + ", messageColor=" + this.b + ", actionColor=" + this.c + ", notifyColor=" + this.d + ')';
    }

    public ColorThemeDto(@e(name = "primary_color") @NotNull String primaryColor, @e(name = "message_color") @NotNull String messageColor, @e(name = "action_color") @NotNull String actionColor, @e(name = "notify_color") @Nullable String notifyColor) {
        k.e(primaryColor, "primaryColor");
        k.e(messageColor, "messageColor");
        k.e(actionColor, "actionColor");
        this.a = primaryColor;
        this.b = messageColor;
        this.c = actionColor;
        this.d = notifyColor;
    }

    @NotNull
    public final String d() {
        return this.a;
    }

    @NotNull
    public final String b() {
        return this.b;
    }

    @NotNull
    public final String a() {
        return this.c;
    }

    @Nullable
    public final String c() {
        return this.d;
    }
}
