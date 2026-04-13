package com.leedarson.skiprope.datamgr;

import android.content.Context;
import android.text.TextUtils;
import com.leedarson.base.utils.l;
import com.leedarson.skiprope.util.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import meshsdk.ConfigUtil;

/* compiled from: ConfigUtil */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String a(Context context, String subDir) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, subDir}, (Object) null, changeQuickRedirect2, true, 9533, new Class[]{Context.class, String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("skiprope");
        String str = File.separator;
        sb.append(str);
        sb.append(subDir);
        sb.append(str);
        String configPath = sb.toString();
        File file = new File(context.getFilesDir() + str + configPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static int b(Context context, String subDir) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, subDir}, (Object) null, changeQuickRedirect2, true, 9534, new Class[]{Context.class, String.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        String content = l.d(new File(a(context, subDir), ConfigUtil.VERSION_FILE)).trim();
        int version = 0;
        if (content != null) {
            try {
                version = Integer.parseInt(content);
            } catch (Exception e) {
                a.b("getCurrentVersion error:" + e.toString());
            }
        }
        a.c("getCurrentVersion subDir:" + subDir + ",version:" + version);
        return version;
    }

    public static boolean c(Context context, String subDir, String version) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, subDir, version}, (Object) null, changeQuickRedirect, true, 9535, new Class[]{Context.class, cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (TextUtils.isEmpty(subDir)) {
            a.f("writeCurrentVersion:subDir=null,version:" + version);
            return true;
        }
        a.a("writeCurrentVersion subDir:" + subDir + ",version:" + version);
        l.h(new File(a(context, subDir)), ConfigUtil.VERSION_FILE, version, false);
        return true;
    }
}
