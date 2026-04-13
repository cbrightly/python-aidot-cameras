package androidx.core.os;

import android.os.Build;
import android.os.Environment;
import android.util.Log;
import androidx.annotation.NonNull;
import java.io.File;
import java.io.IOException;

public final class EnvironmentCompat {
    public static final String MEDIA_UNKNOWN = "unknown";
    private static final String TAG = "EnvironmentCompat";

    @NonNull
    public static String getStorageState(@NonNull File path) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 21) {
            return Environment.getExternalStorageState(path);
        }
        if (i >= 19) {
            return Environment.getStorageState(path);
        }
        try {
            if (path.getCanonicalPath().startsWith(Environment.getExternalStorageDirectory().getCanonicalPath())) {
                return Environment.getExternalStorageState();
            }
            return "unknown";
        } catch (IOException e) {
            Log.w(TAG, "Failed to resolve canonical path: " + e);
            return "unknown";
        }
    }

    private EnvironmentCompat() {
    }
}
