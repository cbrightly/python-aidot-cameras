package zendesk.android.internal;

import dagger.internal.b;
import javax.inject.a;

/* compiled from: ZendeskModule_ZendeskDispatchers$zendesk_zendesk_androidFactory */
public final class t implements a {
    private final p a;

    public t(p module) {
        this.a = module;
    }

    /* renamed from: b */
    public h get() {
        return c(this.a);
    }

    public static t a(p module) {
        return new t(module);
    }

    public static h c(p instance) {
        return (h) b.c(instance.d());
    }
}
