package zendesk.android.internal;

import android.content.Context;
import dagger.internal.b;
import javax.inject.a;

/* compiled from: ZendeskModule_Context$zendesk_zendesk_androidFactory */
public final class r implements a {
    private final p a;

    public r(p module) {
        this.a = module;
    }

    /* renamed from: c */
    public Context get() {
        return a(this.a);
    }

    public static r b(p module) {
        return new r(module);
    }

    public static Context a(p instance) {
        return (Context) b.c(instance.b());
    }
}
