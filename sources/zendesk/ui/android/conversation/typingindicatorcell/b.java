package zendesk.ui.android.conversation.typingindicatorcell;

import androidx.annotation.ColorInt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypingIndicatorCellState.kt */
public final class b {
    @Nullable
    private final Integer a;

    public b() {
        this((Integer) null, 1, (DefaultConstructorMarker) null);
    }

    @NotNull
    public final b a(@Nullable @ColorInt Integer num) {
        return new b(num);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof b) && k.a(this.a, ((b) obj).a);
    }

    public int hashCode() {
        Integer num = this.a;
        if (num == null) {
            return 0;
        }
        return num.hashCode();
    }

    @NotNull
    public String toString() {
        return "TypingIndicatorCellState(backgroundColor=" + this.a + ')';
    }

    public b(@Nullable @ColorInt Integer backgroundColor) {
        this.a = backgroundColor;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ b(Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : num);
    }

    @Nullable
    public final Integer b() {
        return this.a;
    }
}
