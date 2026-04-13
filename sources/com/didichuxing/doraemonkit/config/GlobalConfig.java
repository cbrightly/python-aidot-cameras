package com.didichuxing.doraemonkit.config;

import com.didichuxing.doraemonkit.constant.SharedPrefsKey;
import com.didichuxing.doraemonkit.util.SharedPrefsUtil;

public class GlobalConfig {
    public static void setAppHealth(boolean isRunning) {
        SharedPrefsUtil.putBoolean(SharedPrefsKey.APP_HEALTH, isRunning);
    }

    public static boolean getAppHealth() {
        return SharedPrefsUtil.getBoolean(SharedPrefsKey.APP_HEALTH, false);
    }
}
