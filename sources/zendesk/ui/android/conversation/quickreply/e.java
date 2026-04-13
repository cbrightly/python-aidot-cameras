package zendesk.ui.android.conversation.quickreply;

import androidx.annotation.ColorInt;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: QuickReplyState.kt */
public final class e {
    @NotNull
    private final List<a> a;
    @Nullable
    private final Integer b;

    public e() {
        this((List) null, (Integer) null, 3, (DefaultConstructorMarker) null);
    }

    @NotNull
    public final e a(@NotNull List<a> list, @Nullable @ColorInt Integer num) {
        k.e(list, "quickReplyOptions");
        return new e(list, num);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        return k.a(this.a, eVar.a) && k.a(this.b, eVar.b);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        Integer num = this.b;
        return hashCode + (num == null ? 0 : num.hashCode());
    }

    @NotNull
    public String toString() {
        return "QuickReplyState(quickReplyOptions=" + this.a + ", color=" + this.b + ')';
    }

    public e(@NotNull List<a> quickReplyOptions, @Nullable @ColorInt Integer color) {
        k.e(quickReplyOptions, "quickReplyOptions");
        this.a = quickReplyOptions;
        this.b = color;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ e(List list, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? q.g() : list, (i & 2) != 0 ? null : num);
    }

    @NotNull
    public final List<a> c() {
        return this.a;
    }

    @Nullable
    public final Integer b() {
        return this.b;
    }
}
