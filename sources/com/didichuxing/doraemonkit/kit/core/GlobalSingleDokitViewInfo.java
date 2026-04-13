package com.didichuxing.doraemonkit.kit.core;

import android.os.Bundle;

public class GlobalSingleDokitViewInfo {
    private Class<? extends AbsDokitView> mAbsDokitViewClass;
    private Bundle mBundle;
    private int mMode;
    private String mTag;

    GlobalSingleDokitViewInfo(Class<? extends AbsDokitView> absDokitViewClass, String tag, int mode, Bundle bundle) {
        this.mAbsDokitViewClass = absDokitViewClass;
        this.mTag = tag;
        this.mMode = mode;
        this.mBundle = bundle;
    }

    /* access modifiers changed from: package-private */
    public Class<? extends AbsDokitView> getAbsDokitViewClass() {
        return this.mAbsDokitViewClass;
    }

    public String getTag() {
        return this.mTag;
    }

    public int getMode() {
        return this.mMode;
    }

    public Bundle getBundle() {
        return this.mBundle;
    }

    public String toString() {
        return "GlobalSingleDokitViewInfo{absDokitViewClass=" + this.mAbsDokitViewClass + ", tag='" + this.mTag + '\'' + ", mode=" + this.mMode + ", bundle=" + this.mBundle + '}';
    }
}
