package zendesk.android;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.model.User;

/* compiled from: ZendeskUser.kt */
public final class h {
    @NotNull
    public static final g a(@NotNull User $this$toZendeskUser) {
        k.e($this$toZendeskUser, "<this>");
        return new g($this$toZendeskUser.h(), $this$toZendeskUser.f());
    }
}
