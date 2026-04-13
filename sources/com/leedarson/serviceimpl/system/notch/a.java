package com.leedarson.serviceimpl.system.notch;

import android.content.Context;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: HuaWeiNotchHelper */
public class a {
    private static final String a = a.class.getSimpleName();
    public static ChangeQuickRedirect changeQuickRedirect;

    public static boolean b(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 8927, new Class[]{Context.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            Class HwNotchSizeUtil = context.getClassLoader().loadClass("com.huawei.android.util.HwNotchSizeUtil");
            return ((Boolean) HwNotchSizeUtil.getMethod("hasNotchInScreen", new Class[0]).invoke(HwNotchSizeUtil, new Object[0])).booleanValue();
        } catch (ClassNotFoundException e) {
            timber.log.a.g(a).c("hasNotchInScreen ClassNotFoundException", new Object[0]);
            return false;
        } catch (NoSuchMethodException e2) {
            timber.log.a.g(a).c("hasNotchInScreen NoSuchMethodException", new Object[0]);
            return false;
        } catch (Exception e3) {
            timber.log.a.g(a).c("hasNotchInScreen Exception", new Object[0]);
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public static int[] a(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 8928, new Class[]{Context.class}, int[].class);
        if (proxy.isSupported) {
            return (int[]) proxy.result;
        }
        int[] ret = {0, 0};
        try {
            Class HwNotchSizeUtil = context.getClassLoader().loadClass("com.huawei.android.util.HwNotchSizeUtil");
            return (int[]) HwNotchSizeUtil.getMethod("getNotchSize", new Class[0]).invoke(HwNotchSizeUtil, new Object[0]);
        } catch (ClassNotFoundException e) {
            timber.log.a.g(a).c("getNotchSize ClassNotFoundException", new Object[0]);
            return ret;
        } catch (NoSuchMethodException e2) {
            timber.log.a.g(a).c("getNotchSize NoSuchMethodException", new Object[0]);
            return ret;
        } catch (Exception e3) {
            timber.log.a.g(a).c("getNotchSize Exception", new Object[0]);
            return ret;
        } catch (Throwable th) {
            return ret;
        }
    }
}
