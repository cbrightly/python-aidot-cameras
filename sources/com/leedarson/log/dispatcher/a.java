package com.leedarson.log.dispatcher;

import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.greenrobot.eventbus.c;

/* compiled from: AbDispatcher */
public abstract class a {
    public static ChangeQuickRedirect changeQuickRedirect;

    public void a(String callbackKey, String message) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackKey, message}, this, changeQuickRedirect, false, 1221, clsArr, Void.TYPE).isSupported) {
            c.c().l(new JsBridgeCallbackEvent(callbackKey, message));
        }
    }
}
