package androidx.work.impl.utils;

import android.content.Context;
import android.os.PowerManager;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class WakeLocks {
    private static final String TAG = Logger.tagWithPrefix("WakeLocks");
    private static final WeakHashMap<PowerManager.WakeLock, String> sWakeLocks = new WeakHashMap<>();

    public static PowerManager.WakeLock newWakeLock(@NonNull Context context, @NonNull String tag) {
        String tagWithPrefix = "WorkManager: " + tag;
        PowerManager.WakeLock wakeLock = ((PowerManager) context.getApplicationContext().getSystemService("power")).newWakeLock(1, tagWithPrefix);
        WeakHashMap<PowerManager.WakeLock, String> weakHashMap = sWakeLocks;
        synchronized (weakHashMap) {
            weakHashMap.put(wakeLock, tagWithPrefix);
        }
        return wakeLock;
    }

    public static void checkWakeLocks() {
        Map<PowerManager.WakeLock, String> wakeLocksCopy = new HashMap<>();
        WeakHashMap<PowerManager.WakeLock, String> weakHashMap = sWakeLocks;
        synchronized (weakHashMap) {
            wakeLocksCopy.putAll(weakHashMap);
        }
        for (PowerManager.WakeLock wakeLock : wakeLocksCopy.keySet()) {
            if (wakeLock != null && wakeLock.isHeld()) {
                Logger.get().warning(TAG, String.format("WakeLock held for %s", new Object[]{wakeLocksCopy.get(wakeLock)}), new Throwable[0]);
            }
        }
    }

    private WakeLocks() {
    }
}
