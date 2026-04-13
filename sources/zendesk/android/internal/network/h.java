package zendesk.android.internal.network;

import android.content.Context;
import dagger.internal.b;
import javax.inject.a;
import zendesk.android.internal.g;

/* compiled from: NetworkModule_NetworkDataFactory */
public final class h implements a {
    private final c a;
    private final a<g> b;
    private final a<Context> c;

    public h(c module, a<g> componentConfigProvider, a<Context> contextProvider) {
        this.a = module;
        this.b = componentConfigProvider;
        this.c = contextProvider;
    }

    /* renamed from: b */
    public b get() {
        return c(this.a, this.b.get(), this.c.get());
    }

    public static h a(c module, a<g> componentConfigProvider, a<Context> contextProvider) {
        return new h(module, componentConfigProvider, contextProvider);
    }

    public static b c(c instance, g componentConfig, Context context) {
        return (b) b.c(instance.e(componentConfig, context));
    }
}
