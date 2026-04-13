package com.didichuxing.doraemonkit.config;

import com.didichuxing.doraemonkit.constant.SharedPrefsKey;
import com.didichuxing.doraemonkit.kit.largepicture.LargePictureManager;
import com.didichuxing.doraemonkit.util.SharedPrefsUtil;

public class PerformanceSpInfoConfig {
    public static boolean isLargeImgOpen() {
        return SharedPrefsUtil.getBoolean(SharedPrefsKey.LARGE_IMG_OPEN, false);
    }

    public static void setLargeImgOpen(boolean open) {
        SharedPrefsUtil.putBoolean(SharedPrefsKey.LARGE_IMG_OPEN, open);
    }

    public static void setLargeImgMemoryThreshold(float threshold) {
        SharedPrefsUtil.putFloat(SharedPrefsKey.LARGE_IMG_MEMORY_THRESHOLD, Float.valueOf(threshold));
        LargePictureManager.getInstance().setMemoryThreshold((double) threshold);
    }

    public static double getLargeImgMemoryThreshold(float threshold) {
        return (double) SharedPrefsUtil.getFloat(SharedPrefsKey.LARGE_IMG_MEMORY_THRESHOLD, Float.valueOf(threshold));
    }

    public static void setLargeImgFileThreshold(float threshold) {
        SharedPrefsUtil.putFloat(SharedPrefsKey.LARGE_IMG_FILE_THRESHOLD, Float.valueOf(threshold));
        LargePictureManager.getInstance().setFileThreshold((double) threshold);
    }

    public static double getLargeImgFileThreshold(float threshold) {
        return (double) SharedPrefsUtil.getFloat(SharedPrefsKey.LARGE_IMG_FILE_THRESHOLD, Float.valueOf(threshold));
    }
}
