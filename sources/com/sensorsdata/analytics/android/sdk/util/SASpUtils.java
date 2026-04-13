package com.sensorsdata.analytics.android.sdk.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.sensorsdata.analytics.android.sdk.SALog;

public class SASpUtils {
    private static final String TAG = "SA.SASpUtils";
    private static ISharedPreferencesProvider mSharedPreferencesProvider;

    public interface ISharedPreferencesProvider {
        SharedPreferences createSharedPreferences(Context context, String str, int i);
    }

    public static void setSharedPreferencesProvider(ISharedPreferencesProvider sharedPreferencesProvider) {
        mSharedPreferencesProvider = sharedPreferencesProvider;
    }

    public static SharedPreferences getSharedPreferences(Context context, String name, int mode) {
        SharedPreferences userDefault;
        ISharedPreferencesProvider iSharedPreferencesProvider = mSharedPreferencesProvider;
        if (iSharedPreferencesProvider == null || (userDefault = iSharedPreferencesProvider.createSharedPreferences(context, name, mode)) == null) {
            return context.getSharedPreferences(name, mode);
        }
        SALog.d(TAG, "create SharedPreferences by user default, file name is: " + name);
        return userDefault;
    }
}
