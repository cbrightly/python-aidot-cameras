package zendesk.android.messaging.model;

import android.graphics.Color;
import androidx.annotation.ColorInt;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ColorTheme.kt */
public final class a {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @NotNull
    private final String c;
    @Nullable
    private final String d;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return k.a(this.a, aVar.a) && k.a(this.b, aVar.b) && k.a(this.c, aVar.c) && k.a(this.d, aVar.d);
    }

    public int hashCode() {
        int hashCode = ((((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31;
        String str = this.d;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    @NotNull
    public String toString() {
        return "ColorTheme(primaryColor=" + this.a + ", messageColor=" + this.b + ", actionColor=" + this.c + ", notifyColor=" + this.d + ')';
    }

    public a(@NotNull String primaryColor, @NotNull String messageColor, @NotNull String actionColor, @Nullable String notifyColor) {
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

    @Nullable
    @ColorInt
    public final Integer e(@NotNull String colorCode) {
        k.e(colorCode, "colorCode");
        try {
            return Integer.valueOf(Color.parseColor(colorCode));
        } catch (IllegalArgumentException | StringIndexOutOfBoundsException e) {
            return null;
        }
    }
}
