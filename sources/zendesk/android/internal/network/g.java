package zendesk.android.internal.network;

import com.squareup.moshi.r;
import dagger.internal.b;
import javax.inject.a;

/* compiled from: NetworkModule_MoshiFactory */
public final class g implements a {
    private final c a;

    public g(c module) {
        this.a = module;
    }

    /* renamed from: b */
    public r get() {
        return c(this.a);
    }

    public static g a(c module) {
        return new g(module);
    }

    public static r c(c instance) {
        return (r) b.c(instance.c());
    }
}
