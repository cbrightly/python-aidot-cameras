package zendesk.android.pageviewevents.internal;

import dagger.internal.b;
import javax.inject.a;
import zendesk.android.internal.h;
import zendesk.android.pageviewevents.c;

/* compiled from: PageViewModule_PageViewEventsFactory */
public final class e implements a {
    private final c a;
    private final a<b> b;
    private final a<h> c;

    public e(c module, a<b> restClientProvider, a<h> dispatchersProvider) {
        this.a = module;
        this.b = restClientProvider;
        this.c = dispatchersProvider;
    }

    /* renamed from: b */
    public c get() {
        return c(this.a, this.b.get(), this.c.get());
    }

    public static e a(c module, a<b> restClientProvider, a<h> dispatchersProvider) {
        return new e(module, restClientProvider, dispatchersProvider);
    }

    public static c c(c instance, b restClient, h dispatchers) {
        return (c) b.c(instance.a(restClient, dispatchers));
    }
}
