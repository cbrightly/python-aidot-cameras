package com.leedarson.serviceimpl;

import com.leedarson.bean.Constants;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import meshsdk.MeshLog;
import org.greenrobot.eventbus.c;

/* compiled from: BaseMeshService */
public abstract class g {
    public static String KEY_SIGMesh = Constants.SERVICE_SIG_MESH;
    public static ChangeQuickRedirect changeQuickRedirect;

    public void postJsBridgeCallback(String callbackKey, String message, String action) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackKey, message, action}, this, changeQuickRedirect, false, 5709, clsArr, Void.TYPE).isSupported) {
            MeshLog.e("TX==>handleData " + action + ":" + message);
            c.c().l(new JsBridgeCallbackEvent(callbackKey, message));
        }
    }

    public void postJsCallH5ByNative(String callbackkey, String message) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackkey, message}, this, changeQuickRedirect, false, 5710, clsArr, Void.TYPE).isSupported) {
            MeshLog.e("TX==>handleData " + callbackkey + ":" + message);
            c.c().l(new JsCallH5ByNativeEvent(KEY_SIGMesh, callbackkey, message));
        }
    }
}
