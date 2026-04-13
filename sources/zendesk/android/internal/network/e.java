package zendesk.android.internal.network;

import dagger.internal.b;
import javax.inject.a;
import zendesk.android.internal.g;

/* compiled from: NetworkModule_HeaderFactoryFactory */
public final class e implements a {
    private final c a;
    private final a<g> b;
    private final a<b> c;

    public e(c module, a<g> componentConfigProvider, a<b> networkDataProvider) {
        this.a = module;
        this.b = componentConfigProvider;
        this.c = networkDataProvider;
    }

    /* renamed from: b */
    public a get() {
        return c(this.a, this.b.get(), this.c.get());
    }

    public static e a(c module, a<g> componentConfigProvider, a<b> networkDataProvider) {
        return new e(module, componentConfigProvider, networkDataProvider);
    }

    public static a c(c instance, g componentConfig, b networkData) {
        return (a) b.c(instance.b(componentConfig, networkData));
    }
}
