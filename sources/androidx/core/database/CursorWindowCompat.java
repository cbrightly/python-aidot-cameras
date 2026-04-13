package androidx.core.database;

import android.database.CursorWindow;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class CursorWindowCompat {
    private CursorWindowCompat() {
    }

    @NonNull
    public static CursorWindow create(@Nullable String name, long windowSizeBytes) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 28) {
            return new CursorWindow(name, windowSizeBytes);
        }
        if (i >= 15) {
            return new CursorWindow(name);
        }
        return new CursorWindow(false);
    }
}
