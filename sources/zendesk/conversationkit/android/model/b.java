package zendesk.conversationkit.android.model;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.extension.c;
import zendesk.conversationkit.android.internal.faye.WsActivityEventDto;

/* compiled from: ActivityData.kt */
public final class b {
    @NotNull
    public static final c a(@NotNull WsActivityEventDto $this$toActivityEvent, @NotNull String conversationId, @Nullable Double appMakerLastRead) {
        a aVar;
        g gVar;
        k.e($this$toActivityEvent, "<this>");
        k.e(conversationId, "conversationId");
        a[] values = a.values();
        int length = values.length;
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                aVar = null;
                break;
            }
            a it = values[i2];
            if (k.a(it.getType(), $this$toActivityEvent.d())) {
                aVar = it;
                break;
            }
            i2++;
        }
        String a = $this$toActivityEvent.a();
        String c = $this$toActivityEvent.b().c();
        String a2 = $this$toActivityEvent.b().a();
        g[] values2 = g.values();
        int length2 = values2.length;
        while (true) {
            if (i >= length2) {
                gVar = null;
                break;
            }
            g it2 = values2[i];
            if (k.a(it2.getValue$zendesk_conversationkit_conversationkit_android(), $this$toActivityEvent.c())) {
                gVar = it2;
                break;
            }
            i++;
        }
        return new c(conversationId, aVar, a, c, a2, gVar, k.a(g.BUSINESS.getValue$zendesk_conversationkit_conversationkit_android(), $this$toActivityEvent.c()) ? c.b(appMakerLastRead) : c.b($this$toActivityEvent.b().b()));
    }
}
