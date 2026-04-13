package zendesk.android.internal.network;

import android.content.Context;
import dagger.internal.b;
import java.io.File;
import javax.inject.a;

/* compiled from: NetworkModule_CacheDirFactory */
public final class d implements a {
    private final c a;
    private final a<Context> b;

    public d(c module, a<Context> contextProvider) {
        this.a = module;
        this.b = contextProvider;
    }

    /* renamed from: c */
    public File get() {
        return a(this.a, this.b.get());
    }

    public static d b(c module, a<Context> contextProvider) {
        return new d(module, contextProvider);
    }

    public static File a(c instance, Context context) {
        return (File) b.c(instance.a(context));
    }
}
