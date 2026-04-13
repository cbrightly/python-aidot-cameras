package zendesk.android.internal.network;

import dagger.internal.b;
import javax.inject.a;
import okhttp3.z;
import retrofit2.t;
import zendesk.android.internal.g;

/* compiled from: NetworkModule_RetrofitFactory */
public final class j implements a {
    private final c a;
    private final a<g> b;
    private final a<z> c;
    private final a<retrofit2.converter.moshi.a> d;

    public j(c module, a<g> componentConfigProvider, a<z> okHttpClientProvider, a<retrofit2.converter.moshi.a> moshiConverterFactoryProvider) {
        this.a = module;
        this.b = componentConfigProvider;
        this.c = okHttpClientProvider;
        this.d = moshiConverterFactoryProvider;
    }

    /* renamed from: b */
    public t get() {
        return c(this.a, this.b.get(), this.c.get(), this.d.get());
    }

    public static j a(c module, a<g> componentConfigProvider, a<z> okHttpClientProvider, a<retrofit2.converter.moshi.a> moshiConverterFactoryProvider) {
        return new j(module, componentConfigProvider, okHttpClientProvider, moshiConverterFactoryProvider);
    }

    public static t c(c instance, g componentConfig, z okHttpClient, retrofit2.converter.moshi.a moshiConverterFactory) {
        return (t) b.c(instance.g(componentConfig, okHttpClient, moshiConverterFactory));
    }
}
