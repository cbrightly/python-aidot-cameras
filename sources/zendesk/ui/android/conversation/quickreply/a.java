package zendesk.ui.android.conversation.quickreply;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: QuickReplyOption.kt */
public final class a {
    @NotNull
    private final String a;
    @NotNull
    private final String b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return k.a(this.a, aVar.a) && k.a(this.b, aVar.b);
    }

    public int hashCode() {
        return (this.a.hashCode() * 31) + this.b.hashCode();
    }

    @NotNull
    public String toString() {
        return "QuickReplyOption(id=" + this.a + ", text=" + this.b + ')';
    }

    public a(@NotNull String id, @NotNull String text) {
        k.e(id, "id");
        k.e(text, "text");
        this.a = id;
        this.b = text;
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
