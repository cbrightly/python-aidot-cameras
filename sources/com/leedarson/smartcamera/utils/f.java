package com.leedarson.smartcamera.utils;

import android.content.Context;
import android.os.Environment;
import com.leedarson.base.utils.w;
import com.leedarson.serviceinterface.utils.FileUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;

/* compiled from: SDCardUtils */
public final class f {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static boolean c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 10498, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : "mounted".equals(Environment.getExternalStorageState());
    }

    public static String a(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 10501, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (!c()) {
            return null;
        }
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator;
    }

    public static String b(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 10502, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String dcimPath = a(context);
        if (dcimPath == null) {
            return "";
        }
        if (!FileUtils.createOrExistsDir(dcimPath + w.p(context))) {
            return "";
        }
        return dcimPath + w.p(context) + File.separator;
    }
}
