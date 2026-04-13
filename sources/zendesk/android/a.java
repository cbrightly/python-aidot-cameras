package zendesk.android;

import java.lang.Throwable;
import org.jetbrains.annotations.NotNull;

/* compiled from: ZendeskCallback.kt */
public interface a<E extends Throwable> {
    void onFailure(@NotNull E e);
}
