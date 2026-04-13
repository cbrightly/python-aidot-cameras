package zendesk.messaging.android.internal.model;

import com.google.firebase.messaging.Constants;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MessageLogEntry.kt */
public final class d {
    @NotNull
    private final String a;
    @NotNull
    private final f b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof d)) {
            return false;
        }
        d dVar = (d) obj;
        return k.a(this.a, dVar.a) && this.b == dVar.b;
    }

    public int hashCode() {
        return (this.a.hashCode() * 31) + this.b.hashCode();
    }

    @NotNull
    public String toString() {
        return "MessageReceipt(label=" + this.a + ", icon=" + this.b + ')';
    }

    public d(@NotNull String label, @NotNull f icon) {
        k.e(label, Constants.ScionAnalytics.PARAM_LABEL);
        k.e(icon, "icon");
        this.a = label;
        this.b = icon;
    }

    @NotNull
    public final String a() {
        return this.a;
    }
}
