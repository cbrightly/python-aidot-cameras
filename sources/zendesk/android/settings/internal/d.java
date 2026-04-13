package zendesk.android.settings.internal;

import dagger.internal.b;
import javax.inject.a;

/* compiled from: SettingsModule_SettingsRepositoryFactory */
public final class d implements a {
    private final b a;
    private final a<g> b;

    public d(b module, a<g> settingsRestClientProvider) {
        this.a = module;
        this.b = settingsRestClientProvider;
    }

    /* renamed from: b */
    public f get() {
        return c(this.a, this.b.get());
    }

    public static d a(b module, a<g> settingsRestClientProvider) {
        return new d(module, settingsRestClientProvider);
    }

    public static f c(b instance, g settingsRestClient) {
        return (f) b.c(instance.b(settingsRestClient));
    }
}
