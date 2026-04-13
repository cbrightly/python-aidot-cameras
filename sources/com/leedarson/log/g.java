package com.leedarson.log;

import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: LogUtil */
public class g {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void a(String tag, String msg) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{tag, msg}, (Object) null, changeQuickRedirect, true, 1160, new Class[]{cls, cls}, Void.TYPE).isSupported && tag != null && tag.length() != 0 && msg != null && msg.length() != 0) {
            if (((long) msg.length()) <= ((long) 3072)) {
                Log.d(tag, msg);
                return;
            }
            while (msg.length() > 3072) {
                String logContent = msg.substring(0, 3072);
                msg = msg.replace(logContent, "");
                Log.d(tag, logContent);
            }
            Log.d(tag, msg);
        }
    }
}
