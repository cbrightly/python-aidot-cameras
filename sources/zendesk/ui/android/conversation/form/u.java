package zendesk.ui.android.conversation.form;

import com.google.firebase.messaging.Constants;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SelectOption.kt */
public final class u {
    @NotNull
    private final String a;
    @NotNull
    private final String b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof u)) {
            return false;
        }
        u uVar = (u) obj;
        return k.a(this.a, uVar.a) && k.a(this.b, uVar.b);
    }

    public int hashCode() {
        return (this.a.hashCode() * 31) + this.b.hashCode();
    }

    @NotNull
    public String toString() {
        return "SelectOption(id=" + this.a + ", label=" + this.b + ')';
    }

    public u(@NotNull String id, @NotNull String label) {
        k.e(id, "id");
        k.e(label, Constants.ScionAnalytics.PARAM_LABEL);
        this.a = id;
        this.b = label;
    }

    @NotNull
    public final String a() {
        return this.a;
    }

    @NotNull
    public final String b() {
        return this.b;
    }
}
