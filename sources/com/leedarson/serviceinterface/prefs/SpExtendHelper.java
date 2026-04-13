package com.leedarson.serviceinterface.prefs;

import com.leedarson.base.application.BaseApplication;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class SpExtendHelper {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String generateNextSeq() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 9227, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        int nexSeq = SharePreferenceUtils.getPrefInt(BaseApplication.b(), SharePreferenceUtils._spSimpleVersionSeq, 1) + 1;
        if (nexSeq >= 65534) {
            nexSeq = 0;
        }
        SharePreferenceUtils.setPrefInt(BaseApplication.b(), SharePreferenceUtils._spSimpleVersionSeq, nexSeq);
        return "ap01" + String.format("%05d", new Object[]{Integer.valueOf(nexSeq)});
    }

    public static String generateTid() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 9228, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            return SharePreferenceUtils.getPrefString(BaseApplication.b(), SharePreferenceUtils._spSessionKey, "an001x").substring(0, 6);
        } catch (Exception e) {
            return "an001x";
        }
    }
}
