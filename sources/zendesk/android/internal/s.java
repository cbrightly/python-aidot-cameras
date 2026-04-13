package zendesk.android.internal;

import dagger.internal.b;
import javax.inject.a;
import kotlinx.coroutines.o0;

/* compiled from: ZendeskModule_Scope$zendesk_zendesk_androidFactory */
public final class s implements a {
    private final p a;

    public s(p module) {
        this.a = module;
    }

    /* renamed from: b */
    public o0 get() {
        return c(this.a);
    }

    public static s a(p module) {
        return new s(module);
    }

    public static o0 c(p instance) {
        return (o0) b.c(instance.c());
    }
}
