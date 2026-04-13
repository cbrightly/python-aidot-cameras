package zendesk.conversationkit.android.internal;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.user.a;

/* compiled from: AccessLevel.kt */
public final class d0 extends a {
    @NotNull
    private final a b;
    @NotNull
    private final k c;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof d0)) {
            return false;
        }
        d0 d0Var = (d0) obj;
        return k.a(this.b, d0Var.b) && k.a(this.c, d0Var.c);
    }

    public int hashCode() {
        return (this.b.hashCode() * 31) + this.c.hashCode();
    }

    @NotNull
    public String toString() {
        return "UserAccess(userProcessor=" + this.b + ", conversationKitStorage=" + this.c + ')';
    }

    @NotNull
    public final a c() {
        return this.b;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public d0(@NotNull a userProcessor, @NotNull k conversationKitStorage) {
        super("UserAccess", (DefaultConstructorMarker) null);
        k.e(userProcessor, "userProcessor");
        k.e(conversationKitStorage, "conversationKitStorage");
        this.b = userProcessor;
        this.c = conversationKitStorage;
    }
}
