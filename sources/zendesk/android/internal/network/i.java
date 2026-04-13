package zendesk.android.internal.network;

import dagger.internal.b;
import java.io.File;
import javax.inject.a;
import okhttp3.z;

/* compiled from: NetworkModule_OkHttpClientFactory */
public final class i implements a {
    private final c a;
    private final a<a> b;
    private final a<File> c;

    public i(c module, a<a> headerFactoryProvider, a<File> cacheDirProvider) {
        this.a = module;
        this.b = headerFactoryProvider;
        this.c = cacheDirProvider;
    }

    /* renamed from: b */
    public z get() {
        return c(this.a, this.b.get(), this.c.get());
    }

    public static i a(c module, a<a> headerFactoryProvider, a<File> cacheDirProvider) {
        return new i(module, headerFactoryProvider, cacheDirProvider);
    }

    public static z c(c instance, a headerFactory, File cacheDir) {
        return (z) b.c(instance.f(headerFactory, cacheDir));
    }
}
