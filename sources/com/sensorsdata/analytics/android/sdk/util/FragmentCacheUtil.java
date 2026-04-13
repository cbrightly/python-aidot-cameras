package com.sensorsdata.analytics.android.sdk.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.util.LruCache;
import com.sensorsdata.analytics.android.sdk.SALog;
import java.lang.ref.WeakReference;

public class FragmentCacheUtil {
    @SuppressLint({"NewApi"})
    private static LruCache<String, WeakReference<Object>> sFragmentLruCache = new LruCache<>(Integer.MAX_VALUE);

    public static void setFragmentToCache(String fragmentName, Object object) {
        if (!TextUtils.isEmpty(fragmentName) && object != null && Build.VERSION.SDK_INT >= 12) {
            sFragmentLruCache.put(fragmentName, new WeakReference(object));
        }
    }

    public static Object getFragmentFromCache(String fragmentName) {
        Object object;
        try {
            if (TextUtils.isEmpty(fragmentName)) {
                return null;
            }
            WeakReference<Object> weakReference = null;
            int i = Build.VERSION.SDK_INT;
            if (i >= 12) {
                weakReference = sFragmentLruCache.get(fragmentName);
            }
            if (weakReference != null && (object = weakReference.get()) != null) {
                return object;
            }
            Object object2 = Class.forName(fragmentName).newInstance();
            if (i >= 12) {
                sFragmentLruCache.put(fragmentName, new WeakReference(object2));
            }
            return object2;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }
}
