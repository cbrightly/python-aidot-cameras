package zendesk.ui.android.conversation.item;

import androidx.annotation.ColorInt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ItemState.kt */
public final class e {
    @NotNull
    private final a<?> a;
    @Nullable
    private final Integer b;
    @Nullable
    private final Integer c;

    public e() {
        this((a) null, (Integer) null, (Integer) null, 7, (DefaultConstructorMarker) null);
    }

    @NotNull
    public final e a(@NotNull a<?> aVar, @Nullable @ColorInt Integer num, @Nullable @ColorInt Integer num2) {
        k.e(aVar, "item");
        return new e(aVar, num, num2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        return k.a(this.a, eVar.a) && k.a(this.b, eVar.b) && k.a(this.c, eVar.c);
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
        return "ItemState(item=" + this.a + ", titleColor=" + this.b + ", pressedColor=" + this.c + ')';
    }

    public e(@NotNull a<?> item, @Nullable @ColorInt Integer titleColor, @Nullable @ColorInt Integer pressedColor) {
        k.e(item, "item");
        this.a = item;
        this.b = titleColor;
        this.c = pressedColor;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ e(a aVar, Integer num, Integer num2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new a((String) null, (String) null, (Integer) null, (String) null, (Object) null, 31, (DefaultConstructorMarker) null) : aVar, (i & 2) != 0 ? null : num, (i & 4) != 0 ? null : num2);
    }

    @NotNull
    public final a<?> b() {
        return this.a;
    }

    @Nullable
    public final Integer d() {
        return this.b;
    }

    @Nullable
    public final Integer c() {
        return this.c;
    }
}
