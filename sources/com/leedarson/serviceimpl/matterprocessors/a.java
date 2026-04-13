package com.leedarson.serviceimpl.matterprocessors;

import android.app.Activity;
import com.leedarson.serviceimpl.k;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.greenrobot.eventbus.c;

/* compiled from: BaseMatterProcessorTask */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;

    public void a(Activity activity, String str, String str2, String str3) {
        throw null;
    }

    public boolean c(String str) {
        throw null;
    }

    public void b(String callbackKey, String message, String action) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackKey, message, action}, this, changeQuickRedirect, false, 8444, clsArr, Void.TYPE).isSupported) {
            k kVar = k.a;
            kVar.c("TX==>handleData " + action + ":" + message, "");
            c.c().l(new JsBridgeCallbackEvent(callbackKey, message));
        }
    }
}
