package zendesk.android.internal;

import javax.inject.a;
import zendesk.conversationkit.android.b;

/* compiled from: ZendeskInitializedModule_ConversationKitFactory */
public final class m implements a {
    private final l a;

    public m(l module) {
        this.a = module;
    }

    /* renamed from: c */
    public b get() {
        return a(this.a);
    }

    public static m b(l module) {
        return new m(module);
    }

    public static b a(l instance) {
        return (b) dagger.internal.b.c(instance.a());
    }
}
