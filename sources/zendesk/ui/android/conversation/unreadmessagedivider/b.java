package zendesk.ui.android.conversation.unreadmessagedivider;

import androidx.annotation.ColorInt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: UnreadMessageDividerState.kt */
public final class b {
    @NotNull
    private final String a;
    @Nullable
    private final Integer b;

    public b() {
        this((String) null, (Integer) null, 3, (DefaultConstructorMarker) null);
    }

    @NotNull
    public final b a(@NotNull String str, @Nullable @ColorInt Integer num) {
        k.e(str, "text");
        return new b(str, num);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return k.a(this.a, bVar.a) && k.a(this.b, bVar.b);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        Integer num = this.b;
        return hashCode + (num == null ? 0 : num.hashCode());
    }

    @NotNull
    public String toString() {
        return "UnreadMessageDividerState(text=" + this.a + ", dividerColor=" + this.b + ')';
    }

    public b(@NotNull String text, @Nullable @ColorInt Integer dividerColor) {
        k.e(text, "text");
        this.a = text;
        this.b = dividerColor;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ b(String str, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? null : num);
    }

    @NotNull
    public final String c() {
        return this.a;
    }

    @Nullable
    public final Integer b() {
        return this.b;
    }
}
