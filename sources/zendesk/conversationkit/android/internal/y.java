package zendesk.conversationkit.android.internal;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.integration.a;

/* compiled from: AccessLevel.kt */
public final class y extends a {
    @NotNull
    private final a b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof y) && k.a(this.b, ((y) obj).b);
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    @NotNull
    public String toString() {
        return "IntegrationAccess(integrationProcessor=" + this.b + ')';
    }

    @NotNull
    public final a c() {
        return this.b;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public y(@NotNull a integrationProcessor) {
        super("IntegrationAccess", (DefaultConstructorMarker) null);
        k.e(integrationProcessor, "integrationProcessor");
        this.b = integrationProcessor;
    }
}
