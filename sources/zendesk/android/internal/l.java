package zendesk.android.internal;

import kotlin.jvm.internal.k;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import zendesk.android.c;
import zendesk.android.events.internal.a;
import zendesk.conversationkit.android.b;

/* compiled from: ZendeskInitializedComponent.kt */
public final class l {
    @NotNull
    private final b a;
    @NotNull
    private final zendesk.android.messaging.b b;

    public l(@NotNull b conversationKit, @NotNull zendesk.android.messaging.b messaging) {
        k.e(conversationKit, "conversationKit");
        k.e(messaging, "messaging");
        this.a = conversationKit;
        this.b = messaging;
    }

    @NotNull
    public final c b(@NotNull o0 scope, @NotNull a eventDispatcher, @NotNull zendesk.android.pageviewevents.c pageViewEvents) {
        k.e(scope, "scope");
        k.e(eventDispatcher, "eventDispatcher");
        k.e(pageViewEvents, "pageViewEvents");
        return new c(this.b, scope, eventDispatcher, this.a, pageViewEvents);
    }

    @NotNull
    public final b a() {
        return this.a;
    }
}
