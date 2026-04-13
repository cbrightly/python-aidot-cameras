package zendesk.conversationkit.android.model;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.internal.rest.model.AppDto;

/* compiled from: App.kt */
public final class e {
    @NotNull
    public static final d a(@NotNull AppDto $this$toApp) {
        k.e($this$toApp, "<this>");
        return new d($this$toApp.a(), $this$toApp.c(), $this$toApp.b());
    }
}
