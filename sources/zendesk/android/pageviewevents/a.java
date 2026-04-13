package zendesk.android.pageviewevents;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.android.internal.h;
import zendesk.android.pageviewevents.internal.b;

/* compiled from: PageViewEvents.kt */
public final class a implements c {
    @NotNull
    private final b a;
    @NotNull
    private final h b;

    public a(@NotNull b pageViewEventsRestClient, @NotNull h dispatchers) {
        k.e(pageViewEventsRestClient, "pageViewEventsRestClient");
        k.e(dispatchers, "dispatchers");
        this.a = pageViewEventsRestClient;
        this.b = dispatchers;
    }
}
