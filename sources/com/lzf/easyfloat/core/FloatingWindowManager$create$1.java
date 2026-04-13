package com.lzf.easyfloat.core;

import com.lzf.easyfloat.core.FloatingWindowHelper;
import com.lzf.easyfloat.data.FloatConfig;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.jvm.internal.k;

/* compiled from: FloatingWindowManager.kt */
public final class FloatingWindowManager$create$1 implements FloatingWindowHelper.CreateCallback {
    final /* synthetic */ FloatConfig $config;
    final /* synthetic */ FloatingWindowHelper $helper;

    FloatingWindowManager$create$1(FloatConfig $config2, FloatingWindowHelper $helper2) {
        this.$config = $config2;
        this.$helper = $helper2;
    }

    public void onCreate(boolean success) {
        if (success) {
            ConcurrentHashMap<String, FloatingWindowHelper> windowMap = FloatingWindowManager.INSTANCE.getWindowMap();
            String floatTag = this.$config.getFloatTag();
            k.c(floatTag);
            windowMap.put(floatTag, this.$helper);
        }
    }
}
