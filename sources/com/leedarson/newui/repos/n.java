package com.leedarson.newui.repos;

import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.utils.w;
import com.leedarson.combeans.MqttMessageConfigBean;
import com.leedarson.newui.cloud_play_back.repos.beans.EmptyBean;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import com.leedarson.newui.repos.beans.KvsParmsResponseWrapBean;
import com.leedarson.newui.repoter.d;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.LDSBaseMqttService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.smartcamera.bean.KVSParamBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.e;
import io.reactivex.f;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import meshsdk.BaseResp;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: DeviceWakeUpRepos */
public class n {
    public static ChangeQuickRedirect changeQuickRedirect;
    String a = "";
    SimpleDateFormat b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    d c = new d();
    /* access modifiers changed from: private */
    public String d = "";

    public e<LDSBaseBean> k(String deviceId, boolean isSleep) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId, new Byte(isSleep ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 4422, new Class[]{String.class, Boolean.TYPE}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        if (!isSleep) {
            l(deviceId, isSleep);
        }
        return e.d(new b(this, isSleep, deviceId), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: i */
    public /* synthetic */ void j(boolean z, String str, f fVar) {
        if (!PatchProxy.proxy(new Object[]{new Byte(z ? (byte) 1 : 0), str, fVar}, this, changeQuickRedirect, false, 4429, new Class[]{Boolean.TYPE, String.class, f.class}, Void.TYPE).isSupported) {
            String deviceId = str;
            boolean isSleep = z;
            f emitter = fVar;
            this.a = "";
            this.a = this.b.format(new Date()) + " 开始唤醒  isSleep:=" + isSleep + " devceId=" + deviceId;
            this.c.d(deviceId);
            String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
            JSONObject headerJson = new JSONObject();
            JSONObject paramObj = new JSONObject();
            String appId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "APP_ID", "");
            String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
            String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
            try {
                headerJson.put("owner", (Object) owner);
                headerJson.put("token", (Object) accessToken);
                headerJson.put("terminal", (Object) "app");
                headerJson.put("appId", (Object) appId);
                headerJson.put("appVersion", (Object) w.E(BaseApplication.b()));
                paramObj.put("deviceId", (Object) deviceId);
                paramObj.put("status", (Object) isSleep ? "sleep" : "wakeup");
            } catch (Exception e) {
                e.printStackTrace();
            }
            b0.b().P(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, String.format(Locale.US, "%s/api/ipc/devices/%s/lowPowerActiveState", new Object[]{baseUrl, deviceId}), headerJson.toString(), paramObj.toString(), new a(emitter, deviceId), l.f);
        }
    }

    /* compiled from: DeviceWakeUpRepos */
    public class a extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ f c;
        final /* synthetic */ String d;

        a(f fVar, String str) {
            this.c = fVar;
            this.d = str;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4432, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 4430, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                this.c.onError(e);
                n nVar = n.this;
                nVar.a = n.this.a + "\n" + n.this.b.format(new Date()) + " 唤醒接口调用失败，正在重试";
                n nVar2 = n.this;
                nVar2.c.z(this.d, nVar2.a, BaseResp.ERR_MSG_SEND_FAIL);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 4431, new Class[]{String.class}, Void.TYPE).isSupported) {
                LDSBaseBean data = (LDSBaseBean) new Gson().fromJson(response, LDSBaseBean.class);
                if (data.checkDataValid()) {
                    n nVar = n.this;
                    nVar.a = n.this.a + "\n" + n.this.b.format(new Date()) + " 唤醒接口调用成功 ";
                    n nVar2 = n.this;
                    nVar2.c.z(this.d, nVar2.a, 200);
                } else {
                    n nVar3 = n.this;
                    nVar3.a = n.this.a + "\n" + n.this.b.format(new Date()) + " 唤醒接口调用失败，正在重试";
                    n nVar4 = n.this;
                    nVar4.c.z(this.d, nVar4.a, BaseResp.ERR_MSG_SEND_FAIL);
                }
                this.c.onNext(data);
                this.c.onComplete();
            }
        }
    }

    private void l(String deviceId, boolean isSleep) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Byte(isSleep ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 4423, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            try {
                this.d = "";
                this.d = this.b.format(new Date()) + " 开始唤醒mqtt  isSleep:=" + isSleep + " deviceId=" + deviceId;
                this.c.d(deviceId);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", (Object) deviceId);
                String _userId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", "");
                JSONObject extendsObj = new JSONObject();
                extendsObj.put(FirebaseAnalytics.Param.METHOD, (Object) "lowPowerActiveStateReq");
                extendsObj.put("devId", (Object) deviceId);
                extendsObj.put("userId", (Object) _userId);
                extendsObj.put(NotificationCompat.CATEGORY_SERVICE, (Object) "IPC");
                jsonObject.put("extends", (Object) extendsObj);
                JSONObject wPayloadObj = new JSONObject();
                wPayloadObj.put("devId", (Object) deviceId);
                wPayloadObj.put("status", (Object) isSleep ? "sleep" : "wakeup");
                extendsObj.put("payload", (Object) wPayloadObj);
                LDSBaseMqttService _mqttService = (LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class);
                if (_mqttService != null) {
                    String _topic = "iot/v1/s/userId/IPCAM/lowPowerActiveStateReq".replace("userId", _userId);
                    MqttMessageConfigBean _config = new MqttMessageConfigBean();
                    _config.timeOutLimitOfMs = 10000;
                    _config.onlyPubAck = false;
                    _mqttService.publish(_topic, _config, extendsObj, new b(deviceId));
                }
            } catch (Exception e) {
                timber.log.a.c("wakeupByMqtt  ==" + deviceId + "  exception=" + e.toString(), new Object[0]);
            }
        }
    }

    /* compiled from: DeviceWakeUpRepos */
    public class b implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        b(String str) {
            this.a = str;
        }

        public void onActionSuccess(String str, JSONObject callbackData) {
            Class[] clsArr = {String.class, JSONObject.class};
            if (!PatchProxy.proxy(new Object[]{str, callbackData}, this, changeQuickRedirect, false, 4433, clsArr, Void.TYPE).isSupported) {
                n nVar = n.this;
                String unused = nVar.d = n.this.d + "\n  收到mqtt回执:" + n.this.b.format(new Date()) + "  data=" + callbackData.toString();
                n nVar2 = n.this;
                nVar2.c.z(this.a, nVar2.a, 200);
            }
        }

        public void onActionFail(int i, String str, String desc) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), str, desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4434, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                n nVar = n.this;
                String unused = nVar.d = n.this.d + "\n  收到mqtt回执(消息投递失败):" + n.this.b.format(new Date()) + "  desc=" + desc;
                n nVar2 = n.this;
                nVar2.c.z(this.a, nVar2.a, 200);
            }
        }
    }

    public e<LDSBaseBean> d(String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4424, new Class[]{String.class}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        return e.u(10, TimeUnit.SECONDS).o(new d(deviceId)).x(a.c);
    }

    static /* synthetic */ org.reactivestreams.a g(String deviceId, Long l) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId, l}, (Object) null, changeQuickRedirect, true, 4428, new Class[]{String.class, Long.class}, org.reactivestreams.a.class);
        if (proxy.isSupported) {
            return (org.reactivestreams.a) proxy.result;
        }
        BaseApplication.b();
        if (BaseApplication.d) {
            Gson tempGson = new Gson();
            LDSBaseBean<EmptyBean> temptyBean = new LDSBaseBean<>();
            temptyBean.code = BaseResp.ERR_MSG_SEND_FAIL;
            temptyBean.desc = "应用在后台、不需要去唤醒";
            timber.log.a.g("DeviceWakeUpRepos").a("应用在后台、不需要去唤醒" + deviceId, new Object[0]);
            return e.w(tempGson.toJson((Object) temptyBean));
        }
        JSONObject requestObj = new JSONObject();
        requestObj.put("keepAliveTime", 25);
        String url = "/api/ipc/devices/" + deviceId + "/setKeepAliveTime";
        timber.log.a.g("DeviceWakeUpRepos").a("用户前台，正常唤醒" + deviceId, new Object[0]);
        return b0.b().Q((com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, "", requestObj.toString());
    }

    static /* synthetic */ LDSBaseBean h(String s) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{s}, (Object) null, changeQuickRedirect, true, 4427, new Class[]{String.class}, LDSBaseBean.class);
        if (proxy.isSupported) {
            return (LDSBaseBean) proxy.result;
        }
        return (LDSBaseBean) new Gson().fromJson(s, LDSBaseBean.class);
    }

    public e<KVSParamBean> c(String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4425, new Class[]{String.class}, e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new c(this, deviceId), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public /* synthetic */ void f(String str, f fVar) {
        Class[] clsArr = {String.class, f.class};
        if (!PatchProxy.proxy(new Object[]{str, fVar}, this, changeQuickRedirect, false, 4426, clsArr, Void.TYPE).isSupported) {
            f emitter = fVar;
            String deviceId = str;
            String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
            JSONObject headerJson = new JSONObject();
            JSONArray devicesAr = new JSONArray();
            String appId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "APP_ID", "");
            String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
            String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
            try {
                headerJson.put("owner", (Object) owner);
                headerJson.put("token", (Object) accessToken);
                headerJson.put("terminal", (Object) "app");
                headerJson.put("appId", (Object) appId);
                headerJson.put("appVersion", (Object) w.E(BaseApplication.b()));
                devicesAr.put((Object) deviceId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            b0.b().O(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, baseUrl + "/api/ipc/liveStream/liveStreamParam", headerJson.toString(), devicesAr.toString(), new c(emitter, deviceId));
        }
    }

    /* compiled from: DeviceWakeUpRepos */
    public class c extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ f c;
        final /* synthetic */ String d;

        c(f fVar, String str) {
            this.c = fVar;
            this.d = str;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4437, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 4435, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                this.c.onError(e);
            }
        }

        /* compiled from: DeviceWakeUpRepos */
        public class a extends TypeToken<KvsParmsResponseWrapBean> {
            a() {
            }
        }

        public void onSuccess(String response) {
            T t;
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 4436, new Class[]{String.class}, Void.TYPE).isSupported) {
                try {
                    KvsParmsResponseWrapBean resPonseData = (KvsParmsResponseWrapBean) new Gson().fromJson(response, new a().getType());
                    if (!resPonseData.checkDataValid() || (t = resPonseData.data) == null || ((HashMap) t).get(this.d) == null) {
                        f fVar = this.c;
                        fVar.onError(new ApiException((int) BaseResp.ERR_MSG_SEND_FAIL, "返回的接口 code不等于200" + resPonseData.data));
                    } else if (((KVSParamBean) ((HashMap) resPonseData.data).get(this.d)).requireFiledCheck()) {
                        this.c.onNext((KVSParamBean) ((HashMap) resPonseData.data).get(this.d));
                        this.c.onComplete();
                    } else {
                        this.c.onError(new ApiException((int) BaseResp.ERR_MSG_SEND_FAIL, "接口自检测参数没有通过: resPonseData.requireFiledCheck()="));
                    }
                } catch (Exception e) {
                    this.c.onError(new ApiException((int) BaseResp.ERR_MSG_SEND_FAIL, "返回的接口 code不等于200"));
                }
            }
        }
    }
}
