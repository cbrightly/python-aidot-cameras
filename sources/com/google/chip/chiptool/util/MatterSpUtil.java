package com.google.chip.chiptool.util;

import android.content.Context;
import android.content.SharedPreferences;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MatterSpUtil.kt */
public final class MatterSpUtil {
    private static final long DEFAULT_DEVICE_ID = 1;
    @NotNull
    private static final String DEVICE_ID_PREFS_KEY = "device_id";
    @NotNull
    private static final String HOUSE_ID_PREFS_KEY = "house_id";
    @NotNull
    public static final MatterSpUtil INSTANCE = new MatterSpUtil();
    @NotNull
    private static final String PREFERENCE_FILE_KEY = "com.google.chip.chiptool.PREFERENCE_FILE_KEY";

    private MatterSpUtil() {
    }

    public final long getNextAvailableId(@NotNull Context context) {
        k.e(context, "context");
        SharedPreferences prefs = getPrefs(context);
        if (prefs.contains(DEVICE_ID_PREFS_KEY)) {
            return prefs.getLong(DEVICE_ID_PREFS_KEY, 1);
        }
        prefs.edit().putLong(DEVICE_ID_PREFS_KEY, 1).apply();
        return 1;
    }

    public final void setNextAvailableId(@NotNull Context context, long newId) {
        k.e(context, "context");
        getPrefs(context).edit().putLong(DEVICE_ID_PREFS_KEY, newId).apply();
    }

    public final long getLastDeviceId(@NotNull Context context) {
        k.e(context, "context");
        return getNextAvailableId(context) - 1;
    }

    private final SharedPreferences getPrefs(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_KEY, 0);
        k.d(sharedPreferences, "context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)");
        return sharedPreferences;
    }

    public final void setHouseId(@NotNull Context context, @NotNull String newId) {
        k.e(context, "context");
        k.e(newId, "newId");
        getPrefs(context).edit().putString(HOUSE_ID_PREFS_KEY, newId).apply();
    }

    @Nullable
    public final String getHouseId(@NotNull Context context) {
        k.e(context, "context");
        return getPrefs(context).getString(HOUSE_ID_PREFS_KEY, "");
    }
}
