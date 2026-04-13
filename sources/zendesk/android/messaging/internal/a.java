package zendesk.android.messaging.internal;

import android.content.Context;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.android.internal.i;
import zendesk.android.messaging.b;

/* compiled from: NotEnabledMessaging.kt */
public final class a implements b {
    @NotNull
    public static final a b = new a();

    private a() {
    }

    public void a(@NotNull Context context) {
        k.e(context, "context");
        i.f fVar = i.f.INSTANCE;
        zendesk.logger.a.c("Zendesk", fVar.getMessage(), fVar, new Object[0]);
    }

    public int b() {
        i.f fVar = i.f.INSTANCE;
        zendesk.logger.a.c("Zendesk", fVar.getMessage(), fVar, new Object[0]);
        return 0;
    }
}
