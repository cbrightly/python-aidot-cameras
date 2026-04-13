package zendesk.android.settings.internal;

import dagger.internal.b;
import javax.inject.a;
import retrofit2.t;

/* compiled from: SettingsModule_SettingsApiFactory */
public final class c implements a {
    private final b a;
    private final a<t> b;

    public c(b module, a<t> retrofitProvider) {
        this.a = module;
        this.b = retrofitProvider;
    }

    /* renamed from: b */
    public a get() {
        return c(this.a, this.b.get());
    }

    public static c a(b module, a<t> retrofitProvider) {
        return new c(module, retrofitProvider);
    }

    public static a c(b instance, t retrofit) {
        return (a) b.c(instance.a(retrofit));
    }
}
