package zendesk.ui.android.conversation.quickreply;

import androidx.annotation.ColorInt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: QuickReplyOptionState.kt */
public final class c {
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @Nullable
    private final Integer c;

    public c() {
        this((String) null, (String) null, (Integer) null, 7, (DefaultConstructorMarker) null);
    }

    @NotNull
    public final c a(@NotNull String str, @NotNull String str2, @Nullable @ColorInt Integer num) {
        k.e(str, "id");
        k.e(str2, "text");
        return new c(str, str2, num);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        return k.a(this.a, cVar.a) && k.a(this.b, cVar.b) && k.a(this.c, cVar.c);
    }

    public int hashCode() {
        int hashCode = ((this.a.hashCode() * 31) + this.b.hashCode()) * 31;
        Integer num = this.c;
        return hashCode + (num == null ? 0 : num.hashCode());
    }

    @NotNull
    public String toString() {
        return "QuickReplyOptionState(id=" + this.a + ", text=" + this.b + ", color=" + this.c + ')';
    }

    public c(@NotNull String id, @NotNull String text, @Nullable @ColorInt Integer color) {
        k.e(id, "id");
        k.e(text, "text");
        this.a = id;
        this.b = text;
        this.c = color;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ c(String str, String str2, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? "" : str2, (i & 4) != 0 ? null : num);
    }

    @NotNull
    public final String c() {
        return this.a;
    }

    @NotNull
    public final String d() {
        return this.b;
    }

    @Nullable
    public final Integer b() {
        return this.c;
    }
}
