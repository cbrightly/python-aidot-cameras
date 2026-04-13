package com.leedarson.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import com.leedarson.R$anim;
import com.leedarson.bean.H5ActionName;
import com.leedarson.newui.MainWebViewShowActivity;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.luck.picture.lib.config.PictureConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.greenrobot.eventbus.c;
import org.json.JSONObject;

/* compiled from: PushToH5Util */
public class k {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void d(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, (Object) null, changeQuickRedirect, true, 11513, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.IPC_HELP);
                JSONObject paramObj = new JSONObject();
                paramObj.put("deviceId", (Object) deviceId);
                jsonObject.put("params", (Object) paramObj);
                c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, jsonObject.toString()));
                i(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void e() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 11514, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.NETWORK_HELP);
                jsonObject.put("params", (Object) new JSONObject());
                c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, jsonObject.toString()));
                i(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void a() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 11515, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.BATTERY_FAQ);
                jsonObject.put("params", (Object) new JSONObject());
                c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, jsonObject.toString()));
                i(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void h(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, (Object) null, changeQuickRedirect, true, 11516, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.LIVE_SETTING);
                JSONObject paramObj = new JSONObject();
                paramObj.put("deviceId", (Object) deviceId);
                jsonObject.put("params", (Object) paramObj);
                c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, jsonObject.toString()));
                i(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void g(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, (Object) null, changeQuickRedirect, true, 11518, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.IPC_SELECT_SERVICE);
                JSONObject paramObj = new JSONObject();
                paramObj.put("deviceId", (Object) deviceId);
                jsonObject.put("params", (Object) paramObj);
                c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, jsonObject.toString()));
                i(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void i(boolean needAnim) {
        Object[] objArr = {new Byte(needAnim ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 11519, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            Activity activity = com.leedarson.base.utils.c.h().c();
            Log.d("TAG", "gotoHelp: " + activity);
            if (activity != null && !com.leedarson.base.utils.c.h().l()) {
                activity.startActivity(new Intent(activity, MainWebViewShowActivity.class));
                if (needAnim) {
                    activity.overridePendingTransition(R$anim.ipc_slide_in_right, R$anim.ipc_slide_out_left);
                } else {
                    activity.overridePendingTransition(R$anim.ipc_slide_in_right_no_anim, R$anim.ipc_slide_out_left);
                }
            }
        }
    }

    public static void c(String productId) {
        if (!PatchProxy.proxy(new Object[]{productId}, (Object) null, changeQuickRedirect, true, 11520, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.IPC_FAQ);
                JSONObject paramObj = new JSONObject();
                paramObj.put("productId", (Object) productId);
                jsonObject.put("params", (Object) paramObj);
                c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, jsonObject.toString()));
                i(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void f(String deviceId, String productId) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, productId}, (Object) null, changeQuickRedirect, true, 11521, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.IPC_ROI_GUIDE);
                JSONObject paramObj = new JSONObject();
                paramObj.put("deviceId", (Object) deviceId);
                paramObj.put("productId", (Object) productId);
                jsonObject.put("params", (Object) paramObj);
                c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, jsonObject.toString()));
                i(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void b(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, (Object) null, changeQuickRedirect, true, 11522, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.CORE_HOME);
                JSONObject paramObj = new JSONObject();
                paramObj.put("deviceId", (Object) deviceId);
                jsonObject.put("params", (Object) paramObj);
                c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, jsonObject.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
