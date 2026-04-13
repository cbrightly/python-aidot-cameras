package zendesk.conversationkit.android.internal;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.noaccess.a;

/* compiled from: AccessLevel.kt */
public final class a0 extends a {
    @NotNull
    private final a b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof a0) && k.a(this.b, ((a0) obj).b);
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    @NotNull
    public String toString() {
        return "NoAccess(noAccessProcessor=" + this.b + ')';
    }

    @NotNull
    public final a c() {
        return this.b;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public a0(@NotNull a noAccessProcessor) {
        super("NoAccess", (DefaultConstructorMarker) null);
        k.e(noAccessProcessor, "noAccessProcessor");
        this.b = noAccessProcessor;
    }
}
