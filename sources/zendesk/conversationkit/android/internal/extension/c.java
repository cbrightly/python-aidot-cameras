package zendesk.conversationkit.android.internal.extension;

import java.util.Date;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DoubleKtx.kt */
public final class c {
    @NotNull
    public static final Date a(double $this$toDate) {
        return new Date((long) (((double) 1000) * $this$toDate));
    }

    @Nullable
    public static final Date b(@Nullable Double $this$toDate) {
        if ($this$toDate == null) {
            return null;
        }
        return a($this$toDate.doubleValue());
    }
}
