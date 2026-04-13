package com.leedarson.serviceimpl.system.notch;

import android.content.Context;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.lang.reflect.Method;

/* compiled from: XiaoMiNotchHelper */
public class e {
    private static final String a = e.class.getSimpleName();
    public static ChangeQuickRedirect changeQuickRedirect;

    public static boolean b(Context context) {
        boolean ret = true;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 8944, new Class[]{Context.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            Class SystemProperties = context.getClassLoader().loadClass("android.os.SystemProperties");
            Method get = SystemProperties.getMethod("getInt", new Class[]{String.class, Integer.TYPE});
            if (get == null) {
                return false;
            }
            if (((Integer) get.invoke(SystemProperties, new Object[]{"ro.miui.notch", 0})).intValue() != 1) {
                ret = false;
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
        }
        return false;
    }

    public static int a(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 8948, new Class[]{Context.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
