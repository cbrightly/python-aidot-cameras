package zendesk.conversationkit.android.internal.faye;

import com.squareup.moshi.r;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.internal.c0;
import zendesk.conversationkit.android.internal.d;
import zendesk.conversationkit.android.model.RealtimeSettings;
import zendesk.conversationkit.android.model.f;

/* compiled from: SunCoFayeClientFactory.kt */
public final class c {
    @NotNull
    private final o0 a;
    @NotNull
    private d b = new c0();

    public c(@NotNull o0 coroutineScope) {
        k.e(coroutineScope, "coroutineScope");
        this.a = coroutineScope;
    }

    public final void b(@NotNull d dVar) {
        k.e(dVar, "<set-?>");
        this.b = dVar;
    }

    @NotNull
    public final b a(@NotNull RealtimeSettings realtimeSettings, @NotNull f authenticationType) {
        k.e(realtimeSettings, "realtimeSettings");
        k.e(authenticationType, "authenticationType");
        return new a(new zendesk.faye.f(realtimeSettings.b()).a(), realtimeSettings, authenticationType, this.b, this.a, (r) null, 32, (DefaultConstructorMarker) null);
    }
}
