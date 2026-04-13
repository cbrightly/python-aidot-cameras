package zendesk.conversationkit.android.model;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.internal.rest.model.ConfigDto;
import zendesk.conversationkit.android.internal.rest.model.ConfigResponseDto;

/* compiled from: Config.kt */
public final class i {
    @NotNull
    public static final h a(@NotNull ConfigResponseDto $this$toConfig) {
        k.e($this$toConfig, "<this>");
        ConfigDto $this$toConfig_u24lambda_u2d0 = $this$toConfig.a();
        return new h(e.a($this$toConfig_u24lambda_u2d0.a()), $this$toConfig_u24lambda_u2d0.b().a(), y.a($this$toConfig_u24lambda_u2d0.c()));
    }
}
