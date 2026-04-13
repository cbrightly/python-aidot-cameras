package zendesk.ui.android.conversation.textcell;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TextCellState.kt */
public final class b {
    @NotNull
    private final String a;
    @Nullable
    private final Integer b;
    @Nullable
    private final Integer c;
    @Nullable
    private final Integer d;

    public b() {
        this((String) null, (Integer) null, (Integer) null, (Integer) null, 15, (DefaultConstructorMarker) null);
    }

    @NotNull
    public final b a(@NotNull String str, @Nullable @ColorInt Integer num, @Nullable @ColorInt Integer num2, @Nullable @DrawableRes Integer num3) {
        k.e(str, "messageText");
        return new b(str, num, num2, num3);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return k.a(this.a, bVar.a) && k.a(this.b, bVar.b) && k.a(this.c, bVar.c) && k.a(this.d, bVar.d);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        Integer num = this.b;
        int i = 0;
        int hashCode2 = (hashCode + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.c;
        int hashCode3 = (hashCode2 + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.d;
        if (num3 != null) {
            i = num3.hashCode();
        }
        return hashCode3 + i;
    }

    @NotNull
    public String toString() {
        return "TextCellState(messageText=" + this.a + ", textColor=" + this.b + ", backgroundColor=" + this.c + ", backgroundDrawable=" + this.d + ')';
    }

    public b(@NotNull String messageText, @Nullable @ColorInt Integer textColor, @Nullable @ColorInt Integer backgroundColor, @Nullable @DrawableRes Integer backgroundDrawable) {
        k.e(messageText, "messageText");
        this.a = messageText;
        this.b = textColor;
        this.c = backgroundColor;
        this.d = backgroundDrawable;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ b(String str, Integer num, Integer num2, Integer num3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? null : num, (i & 4) != 0 ? null : num2, (i & 8) != 0 ? null : num3);
    }

    @NotNull
    public final String d() {
        return this.a;
    }

    @Nullable
    public final Integer e() {
        return this.b;
    }

    @Nullable
    public final Integer b() {
        return this.c;
    }

    @Nullable
    public final Integer c() {
        return this.d;
    }
}
