package com.telink.ble.mesh.util;

import android.os.Build;
import com.meituan.robust.ChangeQuickRedirect;

public class ContextUtil {
    public static final int a = Build.VERSION.SDK_INT;
    public static ChangeQuickRedirect changeQuickRedirect;

    public static boolean a() {
        return a >= 21;
    }
}
