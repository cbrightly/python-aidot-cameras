package zendesk.ui.android.conversation.form;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FieldResponseState.kt */
public final class l {
    @NotNull
    private final String a;
    @NotNull
    private final String b;

    public l() {
        this((String) null, (String) null, 3, (DefaultConstructorMarker) null);
    }

    @NotNull
    public final l a(@NotNull String str, @NotNull String str2) {
        k.e(str, "title");
        k.e(str2, "response");
        return new l(str, str2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof l)) {
            return false;
        }
        l lVar = (l) obj;
        return k.a(this.a, lVar.a) && k.a(this.b, lVar.b);
    }

    public int hashCode() {
        return (this.a.hashCode() * 31) + this.b.hashCode();
    }

    @NotNull
    public String toString() {
        return "FieldResponseState(title=" + this.a + ", response=" + this.b + ')';
    }

    public l(@NotNull String title, @NotNull String response) {
        k.e(title, "title");
        k.e(response, "response");
        this.a = title;
        this.b = response;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ l(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, (i & 2) != 0 ? "" : str2);
    }

    @NotNull
    public final String c() {
        return this.a;
    }

    @NotNull
    public final String b() {
        return this.b;
    }
}
