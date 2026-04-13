package com.leedarson.serviceimpl.system.notch;

import android.content.Context;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import timber.log.a;

/* compiled from: VivoNotchHelper */
public class d {
    private static final String a = d.class.getSimpleName();
    public static ChangeQuickRedirect changeQuickRedirect;

    public static boolean b(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 8941, new Class[]{Context.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : a(context, 32);
    }

    private static boolean a(Context context, int feature) {
        Object[] objArr = {context, new Integer(feature)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 8943, new Class[]{Context.class, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            Class FtFeature = context.getClassLoader().loadClass("android.util.FtFeature");
            return ((Boolean) FtFeature.getMethod("isFeatureSupport", new Class[]{cls}).invoke(FtFeature, new Object[]{Integer.valueOf(feature)})).booleanValue();
        } catch (ClassNotFoundException e) {
            a.g(a).c("hasNotchAtVivo ClassNotFoundException", new Object[0]);
        } catch (NoSuchMethodException e2) {
            a.g(a).c("hasNotchAtVivo NoSuchMethodException", new Object[0]);
        } catch (Exception e3) {
            a.g(a).c("hasNotchAtVivo Exception", new Object[0]);
        } catch (Throwable th) {
            return false;
        }
        return false;
    }
}
