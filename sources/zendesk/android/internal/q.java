package zendesk.android.internal;

import dagger.internal.b;
import javax.inject.a;

/* compiled from: ZendeskModule_ComponentData$zendesk_zendesk_androidFactory */
public final class q implements a {
    private final p a;

    public q(p module) {
        this.a = module;
    }

    /* renamed from: c */
    public g get() {
        return a(this.a);
    }

    public static q b(p module) {
        return new q(module);
    }

    public static g a(p instance) {
        return (g) b.c(instance.a());
    }
}
