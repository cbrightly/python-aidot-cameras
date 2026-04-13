package zendesk.android.internal.network;

import com.squareup.moshi.r;
import dagger.internal.b;
import javax.inject.a;

/* compiled from: NetworkModule_MoshiConverterFactoryFactory */
public final class f implements a {
    private final c a;
    private final a<r> b;

    public f(c module, a<r> moshiProvider) {
        this.a = module;
        this.b = moshiProvider;
    }

    /* renamed from: b */
    public retrofit2.converter.moshi.a get() {
        return c(this.a, this.b.get());
    }

    public static f a(c module, a<r> moshiProvider) {
        return new f(module, moshiProvider);
    }

    public static retrofit2.converter.moshi.a c(c instance, r moshi) {
        return (retrofit2.converter.moshi.a) b.c(instance.d(moshi));
    }
}
