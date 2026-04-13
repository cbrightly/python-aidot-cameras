package zendesk.conversationkit.android.internal;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.app.a;

/* compiled from: AccessLevel.kt */
public final class g extends a {
    @NotNull
    private final a b;
    @NotNull
    private final k c;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof g)) {
            return false;
        }
        g gVar = (g) obj;
        return k.a(this.b, gVar.b) && k.a(this.c, gVar.c);
    }

    public int hashCode() {
        return (this.b.hashCode() * 31) + this.c.hashCode();
    }

    @NotNull
    public String toString() {
        return "AppAccess(appProcessor=" + this.b + ", conversationKitStorage=" + this.c + ')';
    }

    @NotNull
    public final a c() {
        return this.b;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public g(@NotNull a appProcessor, @NotNull k conversationKitStorage) {
        super("AppAccess", (DefaultConstructorMarker) null);
        k.e(appProcessor, "appProcessor");
        k.e(conversationKitStorage, "conversationKitStorage");
        this.b = appProcessor;
        this.c = conversationKitStorage;
    }
}
