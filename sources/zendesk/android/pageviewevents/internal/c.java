package zendesk.android.pageviewevents.internal;

import android.content.Context;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import retrofit2.t;
import zendesk.android.internal.g;
import zendesk.android.internal.h;
import zendesk.android.pageviewevents.a;
import zendesk.conversationkit.android.b;
import zendesk.storage.android.d;
import zendesk.storage.android.e;

/* compiled from: PageViewModule.kt */
public final class c {
    @NotNull
    public final h d(@NotNull Context context, @NotNull h dispatchers) {
        k.e(context, "context");
        k.e(dispatchers, "dispatchers");
        return new h(d.a.a("pageviews", context, e.a.a), dispatchers);
    }

    @NotNull
    public final b c(@NotNull g componentConfig, @NotNull b conversationKitAccess, @NotNull h pageViewStorage, @NotNull a pageViewEventsApi, @NotNull zendesk.android.internal.network.b networkData) {
        k.e(componentConfig, "componentConfig");
        k.e(conversationKitAccess, "conversationKitAccess");
        k.e(pageViewStorage, "pageViewStorage");
        k.e(pageViewEventsApi, "pageViewEventsApi");
        k.e(networkData, "networkData");
        return new b(pageViewEventsApi, componentConfig, pageViewStorage, conversationKitAccess, networkData);
    }

    @NotNull
    public final a b(@NotNull t retrofit) {
        k.e(retrofit, "retrofit");
        Object b = retrofit.b(a.class);
        k.d(b, "retrofit.create(PageViewEventsApi::class.java)");
        return (a) b;
    }

    @NotNull
    public final zendesk.android.pageviewevents.c a(@NotNull b restClient, @NotNull h dispatchers) {
        k.e(restClient, "restClient");
        k.e(dispatchers, "dispatchers");
        return new a(restClient, dispatchers);
    }
}
