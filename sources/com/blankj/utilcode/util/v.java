package com.blankj.utilcode.util;

import android.os.Environment;

/* compiled from: SDCardUtils */
public final class v {
    public static boolean a() {
        return "mounted".equals(Environment.getExternalStorageState());
    }
}
