package zendesk.android.pageviewevents.internal;

import javax.inject.a;
import zendesk.android.internal.g;
import zendesk.conversationkit.android.b;

/* compiled from: PageViewModule_PageViewEventsRestClientFactory */
public final class f implements a {
    private final c a;
    private final a<g> b;
    private final a<b> c;
    private final a<h> d;
    private final a<a> e;
    private final a<zendesk.android.internal.network.b> f;

    public f(c module, a<g> componentConfigProvider, a<b> conversationKitAccessProvider, a<h> pageViewStorageProvider, a<a> pageViewEventsApiProvider, a<zendesk.android.internal.network.b> networkDataProvider) {
        this.a = module;
        this.b = componentConfigProvider;
        this.c = conversationKitAccessProvider;
        this.d = pageViewStorageProvider;
        this.e = pageViewEventsApiProvider;
        this.f = networkDataProvider;
    }

    /* renamed from: b */
    public b get() {
        return c(this.a, this.b.get(), this.c.get(), this.d.get(), this.e.get(), this.f.get());
    }

    public static f a(c module, a<g> componentConfigProvider, a<b> conversationKitAccessProvider, a<h> pageViewStorageProvider, a<a> pageViewEventsApiProvider, a<zendesk.android.internal.network.b> networkDataProvider) {
        return new f(module, componentConfigProvider, conversationKitAccessProvider, pageViewStorageProvider, pageViewEventsApiProvider, networkDataProvider);
    }

    public static b c(c instance, g componentConfig, b conversationKitAccess, h pageViewStorage, a pageViewEventsApi, zendesk.android.internal.network.b networkData) {
        return (b) dagger.internal.b.c(instance.c(componentConfig, conversationKitAccess, pageViewStorage, pageViewEventsApi, networkData));
    }
}
