package com.didichuxing.doraemonkit.kit.core;

import android.app.Activity;
import android.os.Bundle;
import com.blankj.utilcode.util.a;

public class DokitIntent {
    public static final int MODE_ONCE = 2;
    public static final int MODE_SINGLE_INSTANCE = 1;
    public Activity activity = a.b();
    public Bundle bundle;
    public int mode = 1;
    private String tag;
    public Class<? extends AbsDokitView> targetClass;

    public String getTag() {
        return this.tag;
    }

    public DokitIntent(Class<? extends AbsDokitView> targetClass2) {
        this.targetClass = targetClass2;
        this.tag = targetClass2.getSimpleName();
    }
}
