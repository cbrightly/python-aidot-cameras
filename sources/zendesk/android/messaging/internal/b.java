package zendesk.android.messaging.internal;

import android.content.Context;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.android.internal.i;
import zendesk.logger.a;

/* compiled from: NotInitializedMessaging.kt */
public final class b implements zendesk.android.messaging.b {
    @NotNull
    public static final b b = new b();

    private b() {
    }

    public void a(@NotNull Context context) {
        k.e(context, "context");
        i.e eVar = i.e.INSTANCE;
        a.c("Zendesk", eVar.getMessage(), eVar, new Object[0]);
    }

    public int b() {
        i.e eVar = i.e.INSTANCE;
        a.c("Zendesk", eVar.getMessage(), eVar, new Object[0]);
        return 0;
    }
}
