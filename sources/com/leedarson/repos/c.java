package com.leedarson.repos;

import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.bean.H5ActionName;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.l;
import io.reactivex.m;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: NewLiveRepos */
public class c {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String a = "getRoiHuman";
    private final String b = "getRoiPrivacy";

    /* access modifiers changed from: private */
    /* renamed from: d */
    public /* synthetic */ void e(String deviceId, String password, m emitter) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, m.class};
        if (!PatchProxy.proxy(new Object[]{deviceId, password, emitter}, this, changeQuickRedirect, false, 5656, clsArr, Void.TYPE).isSupported) {
            try {
                JSONObject actionsObj = new JSONObject();
                actionsObj.put("action", (Object) "getRoiHuman");
                actionsObj.put("devId", (Object) deviceId);
                actionsObj.put("userId", (Object) SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", ""));
                actionsObj.put("password", (Object) password);
                JSONArray jsonArray = new JSONArray();
                jsonArray.put((Object) "roiHuman");
                jsonArray.put((Object) "publicZoneFlag");
                actionsObj.put("in", (Object) jsonArray);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", (Object) deviceId);
                jsonObject.put("actions", (Object) actionsObj);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Device", H5ActionName.ACTION_DEVICE_CONTROL, jsonObject.toString(), new a(emitter)));
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onNext(false);
                emitter.onComplete();
            }
        }
    }

    static /* synthetic */ void c(m emitter, String data, WVJBWebView wVJBWebView) {
        boolean isShow = false;
        if (!PatchProxy.proxy(new Object[]{emitter, data, wVJBWebView}, (Object) null, changeQuickRedirect, true, 5657, new Class[]{m.class, String.class, WVJBWebView.class}, Void.TYPE).isSupported) {
            try {
                JSONObject dataObj = new JSONObject(data);
                if (dataObj.getInt("code") == 200) {
                    JSONObject payload = dataObj.getJSONArray("data").getJSONObject(0).getJSONObject("payload");
                    JSONArray jSONArray = null;
                    JSONObject out = payload.has("out") ? payload.getJSONObject("out") : null;
                    if (out != null) {
                        if (out.has("roi")) {
                            jSONArray = out.getJSONArray("roi");
                        }
                        JSONArray roi = jSONArray;
                        if (roi != null) {
                            if (roi.getJSONObject(0).getInt("publicZone") == 1) {
                                isShow = true;
                            }
                            emitter.onNext(Boolean.valueOf(isShow));
                            emitter.onComplete();
                            return;
                        }
                    }
                }
                emitter.onNext(false);
                emitter.onComplete();
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onNext(false);
                emitter.onComplete();
            }
        }
    }

    public l<Boolean> a(boolean configNeedFetchContent, String deviceId, String password) {
        Class<String> cls = String.class;
        Object[] objArr = {new Byte(configNeedFetchContent ? (byte) 1 : 0), deviceId, password};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 5654, new Class[]{Boolean.TYPE, cls, cls}, l.class);
        if (proxy.isSupported) {
            return (l) proxy.result;
        }
        if (configNeedFetchContent) {
            return l.k(new b(this, deviceId, password)).e0(KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS, TimeUnit.MILLISECONDS).L(l.F(false));
        }
        return l.F(false);
    }

    public l<Boolean> b(boolean configNeedFetchContent) {
        Object[] objArr = {new Byte(configNeedFetchContent ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 5655, new Class[]{Boolean.TYPE}, l.class);
        if (proxy.isSupported) {
            return (l) proxy.result;
        }
        return l.F(Boolean.valueOf(configNeedFetchContent));
    }
}
