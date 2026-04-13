package zendesk.android.internal;

import dagger.internal.b;
import javax.inject.a;

/* compiled from: ZendeskModule_ZendeskEventDispatcher$zendesk_zendesk_androidFactory */
public final class u implements a {
    private final p a;

    public u(p module) {
        this.a = module;
    }

    /* renamed from: b */
    public zendesk.android.events.internal.a get() {
        return c(this.a);
    }

    public static u a(p module) {
        return new u(module);
    }

    public static zendesk.android.events.internal.a c(p instance) {
        return (zendesk.android.events.internal.a) b.c(instance.e());
    }
}
