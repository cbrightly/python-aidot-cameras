package zendesk.android.pageviewevents.internal;

import android.content.Context;
import dagger.internal.b;
import javax.inject.a;
import zendesk.android.internal.h;

/* compiled from: PageViewModule_PageViewStorageFactory */
public final class g implements a {
    private final c a;
    private final a<Context> b;
    private final a<h> c;

    public g(c module, a<Context> contextProvider, a<h> dispatchersProvider) {
        this.a = module;
        this.b = contextProvider;
        this.c = dispatchersProvider;
    }

    /* renamed from: b */
    public h get() {
        return c(this.a, this.b.get(), this.c.get());
    }

    public static g a(c module, a<Context> contextProvider, a<h> dispatchersProvider) {
        return new g(module, contextProvider, dispatchersProvider);
    }

    public static h c(c instance, Context context, h dispatchers) {
        return (h) b.c(instance.d(context, dispatchers));
    }
}
