package zendesk.ui.android.conversation.form;

import androidx.annotation.ColorInt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FormButtonState.kt */
public final class o {
    @NotNull
    private final String a;
    private final boolean b;
    @Nullable
    private final Integer c;

    public o() {
        this((String) null, false, (Integer) null, 7, (DefaultConstructorMarker) null);
    }

    public static /* synthetic */ o b(o oVar, String str, boolean z, Integer num, int i, Object obj) {
        if ((i & 1) != 0) {
            str = oVar.a;
        }
        if ((i & 2) != 0) {
            z = oVar.b;
        }
        if ((i & 4) != 0) {
            num = oVar.c;
        }
        return oVar.a(str, z, num);
    }

    @NotNull
    public final o a(@NotNull String str, boolean z, @Nullable @ColorInt Integer num) {
        k.e(str, "text");
        return new o(str, z, num);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof o)) {
            return false;
        }
        o oVar = (o) obj;
        return k.a(this.a, oVar.a) && this.b == oVar.b && k.a(this.c, oVar.c);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        boolean z = this.b;
        if (z) {
            z = true;
        }
        int i = (hashCode + (z ? 1 : 0)) * 31;
        Integer num = this.c;
        return i + (num == null ? 0 : num.hashCode());
    }

    @NotNull
    public String toString() {
        return "FormButtonState(text=" + this.a + ", isLoading=" + this.b + ", backgroundColor=" + this.c + ')';
    }

    public o(@NotNull String text, boolean isLoading, @Nullable @ColorInt Integer backgroundColor) {
        k.e(text, "text");
        this.a = text;
        this.b = isLoading;
        this.c = backgroundColor;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ o(String str, boolean z, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? false : z, (i & 4) != 0 ? null : num);
    }

    @NotNull
    public final String d() {
        return this.a;
    }

    public final boolean e() {
        return this.b;
    }

    @Nullable
    public final Integer c() {
        return this.c;
    }
}
