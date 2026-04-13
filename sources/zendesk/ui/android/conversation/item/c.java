package zendesk.ui.android.conversation.item;

import androidx.annotation.ColorInt;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ItemGroupState.kt */
public final class c {
    @NotNull
    private final List<a<?>> a;
    @Nullable
    private final Integer b;
    @Nullable
    private final Integer c;

    public c() {
        this((List) null, (Integer) null, (Integer) null, 7, (DefaultConstructorMarker) null);
    }

    public static /* synthetic */ c b(c cVar, List<a<?>> list, Integer num, Integer num2, int i, Object obj) {
        if ((i & 1) != 0) {
            list = cVar.a;
        }
        if ((i & 2) != 0) {
            num = cVar.b;
        }
        if ((i & 4) != 0) {
            num2 = cVar.c;
        }
        return cVar.a(list, num, num2);
    }

    @NotNull
    public final c a(@NotNull List<? extends a<?>> list, @Nullable @ColorInt Integer num, @Nullable @ColorInt Integer num2) {
        k.e(list, FirebaseAnalytics.Param.ITEMS);
        return new c(list, num, num2);
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
        int hashCode = this.a.hashCode() * 31;
        Integer num = this.b;
        int i = 0;
        int hashCode2 = (hashCode + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.c;
        if (num2 != null) {
            i = num2.hashCode();
        }
        return hashCode2 + i;
    }

    @NotNull
    public String toString() {
        return "ItemGroupState(items=" + this.a + ", titleColor=" + this.b + ", pressedColor=" + this.c + ')';
    }

    public c(@NotNull List<? extends a<?>> items, @Nullable @ColorInt Integer titleColor, @Nullable @ColorInt Integer pressedColor) {
        k.e(items, FirebaseAnalytics.Param.ITEMS);
        this.a = items;
        this.b = titleColor;
        this.c = pressedColor;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ c(List list, Integer num, Integer num2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? q.g() : list, (i & 2) != 0 ? null : num, (i & 4) != 0 ? null : num2);
    }

    @NotNull
    public final List<a<?>> c() {
        return this.a;
    }

    @Nullable
    public final Integer e() {
        return this.b;
    }

    @Nullable
    public final Integer d() {
        return this.c;
    }
}
