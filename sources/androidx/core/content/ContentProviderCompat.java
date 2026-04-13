package androidx.core.content;

import android.content.ContentProvider;
import android.content.Context;
import androidx.annotation.NonNull;

public final class ContentProviderCompat {
    private ContentProviderCompat() {
    }

    @NonNull
    public static Context requireContext(@NonNull ContentProvider provider) {
        Context ctx = provider.getContext();
        if (ctx != null) {
            return ctx;
        }
        throw new IllegalStateException("Cannot find context from the provider.");
    }
}
