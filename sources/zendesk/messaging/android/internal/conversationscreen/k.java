package zendesk.messaging.android.internal.conversationscreen;

import android.content.Context;
import android.content.Intent;
import org.jetbrains.annotations.NotNull;

/* compiled from: ImageViewerActivity.kt */
public final class k {
    @NotNull
    private final Intent a;

    public k(@NotNull Context context, @NotNull String credentials) {
        kotlin.jvm.internal.k.e(context, "context");
        kotlin.jvm.internal.k.e(credentials, "credentials");
        Intent intent = new Intent(context, ImageViewerActivity.class);
        this.a = intent;
        l.g(intent, credentials);
    }

    @NotNull
    public final k b(@NotNull String uri) {
        kotlin.jvm.internal.k.e(uri, "uri");
        l.h(this.a, uri);
        return this;
    }

    @NotNull
    public final Intent a() {
        return this.a;
    }
}
