package zendesk.android.settings.internal;

import com.squareup.moshi.r;
import dagger.internal.b;
import javax.inject.a;
import zendesk.android.internal.g;

/* compiled from: SettingsModule_SettingsRestClientFactory */
public final class e implements a {
    private final b a;
    private final a<a> b;
    private final a<r> c;
    private final a<g> d;

    public e(b module, a<a> settingsApiProvider, a<r> moshiProvider, a<g> componentConfigProvider) {
        this.a = module;
        this.b = settingsApiProvider;
        this.c = moshiProvider;
        this.d = componentConfigProvider;
    }

    /* renamed from: b */
    public g get() {
        return c(this.a, this.b.get(), this.c.get(), this.d.get());
    }

    public static e a(b module, a<a> settingsApiProvider, a<r> moshiProvider, a<g> componentConfigProvider) {
        return new e(module, settingsApiProvider, moshiProvider, componentConfigProvider);
    }

    public static g c(b instance, a settingsApi, r moshi, g componentConfig) {
        return (g) b.c(instance.c(settingsApi, moshi, componentConfig));
    }
}
