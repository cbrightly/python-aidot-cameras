package zendesk.android.pageviewevents.internal;

import dagger.internal.b;
import javax.inject.a;
import retrofit2.t;

/* compiled from: PageViewModule_PageViewEventsApiFactory */
public final class d implements a {
    private final c a;
    private final a<t> b;

    public d(c module, a<t> retrofitProvider) {
        this.a = module;
        this.b = retrofitProvider;
    }

    /* renamed from: b */
    public a get() {
        return c(this.a, this.b.get());
    }

    public static d a(c module, a<t> retrofitProvider) {
        return new d(module, retrofitProvider);
    }

    public static a c(c instance, t retrofit) {
        return (a) b.c(instance.b(retrofit));
    }
}
