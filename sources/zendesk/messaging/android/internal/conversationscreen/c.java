package zendesk.messaging.android.internal.conversationscreen;

import android.content.Context;
import android.content.Intent;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.android.d;

/* compiled from: ConversationActivity.kt */
public final class c {
    @NotNull
    private final Intent a;

    public c(@NotNull Context context, @NotNull d credentials) {
        k.e(context, "context");
        k.e(credentials, "credentials");
        Intent intent = new Intent(context, ConversationActivity.class);
        this.a = intent;
        d.d(intent, d.a.c(credentials));
    }

    @NotNull
    public final c b(int flags) {
        this.a.setFlags(flags);
        return this;
    }

    @NotNull
    public final Intent a() {
        return this.a;
    }
}
