package androidx.activity.contextaware;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public final class ContextAwareHelper {
    private volatile Context mContext;
    private final Set<OnContextAvailableListener> mListeners = new CopyOnWriteArraySet();

    @Nullable
    public Context peekAvailableContext() {
        return this.mContext;
    }

    public void addOnContextAvailableListener(@NonNull OnContextAvailableListener listener) {
        if (this.mContext != null) {
            listener.onContextAvailable(this.mContext);
        }
        this.mListeners.add(listener);
    }

    public void removeOnContextAvailableListener(@NonNull OnContextAvailableListener listener) {
        this.mListeners.remove(listener);
    }

    public void dispatchOnContextAvailable(@NonNull Context context) {
        this.mContext = context;
        for (OnContextAvailableListener listener : this.mListeners) {
            listener.onContextAvailable(context);
        }
    }

    public void clearAvailableContext() {
        this.mContext = null;
    }
}
