package zendesk.ui.android.conversation.form;

import com.google.firebase.messaging.Constants;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FieldResponse.kt */
public final class j {
    @NotNull
    private final String a;
    @NotNull
    private final String b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof j)) {
            return false;
        }
        j jVar = (j) obj;
        return k.a(this.a, jVar.a) && k.a(this.b, jVar.b);
    }

    public int hashCode() {
        return (this.a.hashCode() * 31) + this.b.hashCode();
    }

    @NotNull
    public String toString() {
        return "FieldResponse(label=" + this.a + ", response=" + this.b + ')';
    }

    public j(@NotNull String label, @NotNull String response) {
        k.e(label, Constants.ScionAnalytics.PARAM_LABEL);
        k.e(response, "response");
        this.a = label;
        this.b = response;
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
