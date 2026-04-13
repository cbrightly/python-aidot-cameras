package zendesk.conversationkit.android;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.g;

/* compiled from: ConversationKitResult.kt */
public final class h {
    public static final <T> T a(@NotNull g<? extends T> $this$getOrThrow) {
        k.e($this$getOrThrow, "<this>");
        if ($this$getOrThrow instanceof g.a) {
            throw ((g.a) $this$getOrThrow).a();
        } else if ($this$getOrThrow instanceof g.b) {
            return ((g.b) $this$getOrThrow).a();
        } else {
            throw new NoWhenBranchMatchedException();
        }
    }
}
