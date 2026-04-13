package zendesk.android.pageviewevents.internal;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.android.internal.g;

/* compiled from: PageViewEventsRestClient.kt */
public final class b {
    @NotNull
    private final a a;
    @NotNull
    private final g b;
    @NotNull
    private final h c;
    @NotNull
    private final zendesk.conversationkit.android.b d;
    @NotNull
    private final zendesk.android.internal.network.b e;

    public b(@NotNull a pageViewEventsApi, @NotNull g zendeskComponentConfig, @NotNull h pageViewStorage, @NotNull zendesk.conversationkit.android.b conversationKit, @NotNull zendesk.android.internal.network.b networkData) {
        k.e(pageViewEventsApi, "pageViewEventsApi");
        k.e(zendeskComponentConfig, "zendeskComponentConfig");
        k.e(pageViewStorage, "pageViewStorage");
        k.e(conversationKit, "conversationKit");
        k.e(networkData, "networkData");
        this.a = pageViewEventsApi;
        this.b = zendeskComponentConfig;
        this.c = pageViewStorage;
        this.d = conversationKit;
        this.e = networkData;
    }
}
