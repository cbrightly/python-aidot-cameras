package zendesk.ui.android.conversation.form;

import androidx.annotation.ColorInt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FormState.kt */
public final class s {
    @Nullable
    private final Integer a;
    private final boolean b;

    public s() {
        this((Integer) null, false, 3, (DefaultConstructorMarker) null);
    }

    @NotNull
    public final s a(@Nullable @ColorInt Integer num, boolean z) {
        return new s(num, z);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof s)) {
            return false;
        }
        s sVar = (s) obj;
        return k.a(this.a, sVar.a) && this.b == sVar.b;
    }

    public int hashCode() {
        Integer num = this.a;
        int hashCode = (num == null ? 0 : num.hashCode()) * 31;
        boolean z = this.b;
        if (z) {
            z = true;
        }
        return hashCode + (z ? 1 : 0);
    }

    @NotNull
    public String toString() {
        return "FormState(colorAccent=" + this.a + ", pending=" + this.b + ')';
    }

    public s(@Nullable @ColorInt Integer colorAccent, boolean pending) {
        this.a = colorAccent;
        this.b = pending;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ s(Integer num, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : num, (i & 2) != 0 ? false : z);
    }

    @Nullable
    public final Integer b() {
        return this.a;
    }

    public final boolean c() {
        return this.b;
    }
}
