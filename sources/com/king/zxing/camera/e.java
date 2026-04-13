package com.king.zxing.camera;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/* compiled from: FrontLightMode */
public enum e {
    ON,
    AUTO,
    OFF;

    private static e a(String modeString) {
        return modeString == null ? AUTO : valueOf(modeString);
    }

    public static e readPref(SharedPreferences sharedPrefs) {
        return a(sharedPrefs.getString("preferences_front_light_mode", AUTO.toString()));
    }

    public static void put(Context context, e mode) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("preferences_front_light_mode", mode.toString()).apply();
    }
}
