package zendesk.android.internal;

import dagger.internal.b;
import javax.inject.a;
import kotlinx.coroutines.o0;
import zendesk.android.pageviewevents.c;

/* compiled from: ZendeskInitializedModule_ZendeskFactory */
public final class n implements a {
    private final l a;
    private final a<o0> b;
    private final a<zendesk.android.events.internal.a> c;
    private final a<c> d;

    public n(l module, a<o0> scopeProvider, a<zendesk.android.events.internal.a> eventDispatcherProvider, a<c> pageViewEventsProvider) {
        this.a = module;
        this.b = scopeProvider;
        this.c = eventDispatcherProvider;
        this.d = pageViewEventsProvider;
    }

    /* renamed from: b */
    public zendesk.android.c get() {
        return c(this.a, this.b.get(), this.c.get(), this.d.get());
    }

    public static n a(l module, a<o0> scopeProvider, a<zendesk.android.events.internal.a> eventDispatcherProvider, a<c> pageViewEventsProvider) {
        return new n(module, scopeProvider, eventDispatcherProvider, pageViewEventsProvider);
    }

    public static zendesk.android.c c(l instance, o0 scope, zendesk.android.events.internal.a eventDispatcher, c pageViewEvents) {
        return (zendesk.android.c) b.c(instance.b(scope, eventDispatcher, pageViewEvents));
    }
}
