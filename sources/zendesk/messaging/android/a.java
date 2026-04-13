package zendesk.messaging.android;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.i0;
import org.jetbrains.annotations.NotNull;
import zendesk.android.messaging.MessagingFactory;
import zendesk.android.messaging.b;
import zendesk.messaging.android.internal.ProcessLifecycleObserver;
import zendesk.messaging.android.internal.c;
import zendesk.messaging.android.internal.d;
import zendesk.messaging.android.internal.i;
import zendesk.messaging.android.internal.l;

/* compiled from: DefaultMessagingFactory.kt */
public final class a implements MessagingFactory {
    @NotNull
    public b a(@NotNull MessagingFactory.CreateParams params) {
        k.e(params, "params");
        return new d(params.getCredentials(), params.getMessagingSettings(), params.getConversationKit(), params.getDispatchEvent(), ProcessLifecycleObserver.c.a(), params.getCoroutineScope(), new l(), new c((i0) null, (i0) null, (i0) null, 7, (DefaultConstructorMarker) null), new i());
    }
}
