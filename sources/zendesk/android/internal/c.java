package zendesk.android.internal;

import android.content.Context;
import java.util.concurrent.TimeUnit;
import kotlin.coroutines.d;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.android.internal.i;
import zendesk.android.settings.internal.model.SettingsDto;
import zendesk.android.settings.internal.model.SunCoConfigDto;
import zendesk.conversationkit.android.b;
import zendesk.conversationkit.android.f;
import zendesk.conversationkit.android.g;
import zendesk.conversationkit.android.model.h;
import zendesk.conversationkit.android.model.x;

/* compiled from: ConversationKitProvider.kt */
public final class c {
    @NotNull
    public static final c a = new c();

    private c() {
    }

    @Nullable
    public final Object a(@NotNull SettingsDto settings, @NotNull Context context, @NotNull d<? super g<? extends b>> $completion) {
        SunCoConfigDto sunCoConfig = settings.f();
        String integrationId = zendesk.android.messaging.model.d.a(settings.d(), zendesk.android.messaging.model.b.a(settings.c()), zendesk.android.messaging.model.b.a(settings.a())).c();
        if (sunCoConfig == null || integrationId == null) {
            return new g.a(i.d.INSTANCE);
        }
        h config = b(sunCoConfig);
        return f.a.a(context).a(zendesk.conversationkit.android.i.a.a(integrationId).a(), config, $completion);
    }

    private final h b(SunCoConfigDto $this$toConversationKitConfig) {
        return new h(new zendesk.conversationkit.android.model.d($this$toConversationKitConfig.a().a(), $this$toConversationKitConfig.a().c(), $this$toConversationKitConfig.a().b()), $this$toConversationKitConfig.b().a(), new x($this$toConversationKitConfig.d().b().b(), $this$toConversationKitConfig.d().b().a(), (TimeUnit) null, $this$toConversationKitConfig.d().a(), $this$toConversationKitConfig.d().c(), 4, (DefaultConstructorMarker) null));
    }
}
