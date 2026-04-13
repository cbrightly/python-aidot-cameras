package com.leedarson.mqtt.repos;

import android.text.TextUtils;
import android.util.Log;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.google.gson.Gson;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.mqtt.beans.ApiMqttConfigBean;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.e;
import io.reactivex.f;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Locale;
import java.util.Random;
import kotlin.n;
import org.json.JSONObject;

/* compiled from: FetchMqttConfigRepos */
public class h {
    public static ChangeQuickRedirect changeQuickRedirect;
    String a;
    String b;
    String c;

    /* compiled from: FetchMqttConfigRepos */
    public enum b {
        EMPTY,
        VALID,
        INVALID;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public h() {
        Locale locale = Locale.US;
        this.a = String.format(locale, "%s/mqttConfig", new Object[]{BaseApplication.b().getFilesDir().getPath()});
        this.b = String.format(locale, "%s/mqttConfig/config.txt", new Object[]{BaseApplication.b().getFilesDir().getPath()});
        this.c = String.format(locale, "%s/mqttConfig/ipc_device_list.txt", new Object[]{BaseApplication.b().getFilesDir().getPath()});
    }

    public e<ApiMqttConfigBean> g(boolean forceUpdateRemote) {
        Object[] objArr = {new Byte(forceUpdateRemote ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 1710, new Class[]{Boolean.TYPE}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        if (forceUpdateRemote) {
            File file = new File(this.b);
            if (file.exists()) {
                file.delete();
            }
        }
        return c().o(new a(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: s */
    public /* synthetic */ e t(b mqtt_config_of_local_statue) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mqtt_config_of_local_statue}, this, changeQuickRedirect, false, 1727, new Class[]{b.class}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        if (mqtt_config_of_local_statue == b.VALID) {
            return a();
        }
        return b();
    }

    private e<ApiMqttConfigBean> a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1711, new Class[0], e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new g(this), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: i */
    public /* synthetic */ void j(f emitter) {
        if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 1726, new Class[]{f.class}, Void.TYPE).isSupported) {
            emitter.onNext((ApiMqttConfigBean) new Gson().fromJson(w(this.b), ApiMqttConfigBean.class));
            emitter.onComplete();
        }
    }

    private e<ApiMqttConfigBean> b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1712, new Class[0], e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new e(this), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: k */
    public /* synthetic */ void l(f emitter) {
        if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 1725, new Class[]{f.class}, Void.TYPE).isSupported) {
            JSONObject _bodyJsonObj = new JSONObject();
            _bodyJsonObj.put("sessionType", (Object) "App");
            String tempSessionId = h();
            com.leedarson.mqtt.logs.b.b(" tempSessionId=" + tempSessionId);
            _bodyJsonObj.put("sessionId", (Object) tempSessionId);
            b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, "/v25/commons/mqttConfig2", "", _bodyJsonObj.toString(), new a(emitter));
        }
    }

    /* compiled from: FetchMqttConfigRepos */
    public class a extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ f c;

        a(f fVar) {
            this.c = fVar;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1730, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 1728, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                String tip = "获取Mqtt登陆信息失败（接口报错）e=" + e.toString();
                this.c.onError(new Throwable(tip));
                com.leedarson.mqtt.logs.b.a(tip);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1729, new Class[]{String.class}, Void.TYPE).isSupported) {
                try {
                    Gson gson = new Gson();
                    ApiMqttConfigBean apiMqttConfigBean = (ApiMqttConfigBean) gson.fromJson(response, ApiMqttConfigBean.class);
                    if (!TextUtils.isEmpty(apiMqttConfigBean.host)) {
                        apiMqttConfigBean.userId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", "");
                        File mqttConfigFile = new File(h.this.b);
                        if (mqttConfigFile.exists()) {
                            mqttConfigFile.delete();
                        }
                        h.this.y(gson.toJson((Object) apiMqttConfigBean), h.this.b);
                        this.c.onNext(apiMqttConfigBean);
                        this.c.onComplete();
                        com.leedarson.mqtt.logs.b.b("获取Mqtt登陆信息成功  response=" + response);
                        return;
                    }
                    f fVar = this.c;
                    fVar.onError(new Throwable("获取Mqtt登陆信息失败 e=" + response));
                } catch (Exception e) {
                    f fVar2 = this.c;
                    fVar2.onError(new Throwable("获取Mqtt登陆信息失败： response=" + response + "  ,exception=" + e.toString()));
                }
            }
        }
    }

    private String h() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1713, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String _sessionId = SharePreferenceUtils.getPrefString(BaseApplication.b(), SharePreferenceUtils._spSessionKey, "");
        if (!TextUtils.isEmpty(_sessionId)) {
            return _sessionId;
        }
        String _sessionId2 = d(21);
        SharePreferenceUtils.setPrefString(BaseApplication.b(), SharePreferenceUtils._spSessionKey, _sessionId2);
        return _sessionId2;
    }

    private String d(int length) {
        Object[] objArr = {new Integer(length)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 1714, new Class[]{Integer.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(random.nextInt(62)));
        }
        return stringBuffer.toString();
    }

    /* access modifiers changed from: package-private */
    public String w(String path) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 1715, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String str = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));
            while (true) {
                String readLine = br.readLine();
                String mimeTypeLine = readLine;
                if (readLine == null) {
                    break;
                }
                str = str + mimeTypeLine;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /* access modifiers changed from: package-private */
    public void y(String strcontent, String filePath) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{strcontent, filePath}, this, changeQuickRedirect, false, 1716, clsArr, Void.TYPE).isSupported) {
            String strContent = strcontent;
            try {
                File file = new File(filePath);
                RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                raf.seek(file.length());
                raf.write(strContent.getBytes());
                raf.close();
            } catch (Exception e) {
                Log.e("TestFile", "Error on write File:" + e);
            }
        }
    }

    private e<b> c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1717, new Class[0], e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new b(this), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: m */
    public /* synthetic */ void n(f emitter) {
        if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 1724, new Class[]{f.class}, Void.TYPE).isSupported) {
            File cacheDir = new File(this.a);
            if (!cacheDir.exists()) {
                cacheDir.mkdir();
            }
            File cacheFile = new File(this.b);
            if (!cacheFile.exists()) {
                cacheFile.createNewFile();
                emitter.onNext(b.EMPTY);
                emitter.onComplete();
                return;
            }
            try {
                ApiMqttConfigBean mqttConfigBean = (ApiMqttConfigBean) new Gson().fromJson(w(this.b), ApiMqttConfigBean.class);
                if (!TextUtils.isEmpty(mqttConfigBean.host) && !TextUtils.isEmpty(mqttConfigBean.clientId)) {
                    if (!TextUtils.isEmpty(mqttConfigBean.password)) {
                        if (mqttConfigBean.userId.equals(SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", ""))) {
                            emitter.onNext(b.VALID);
                        } else {
                            emitter.onNext(b.INVALID);
                        }
                        emitter.onComplete();
                    }
                }
                emitter.onNext(b.INVALID);
            } catch (Exception e) {
                emitter.onNext(b.INVALID);
            }
            emitter.onComplete();
        }
    }

    public e<n<Boolean, String>> e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1718, new Class[0], e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new f(this), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: o */
    public /* synthetic */ void p(f emitter) {
        if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 1723, new Class[]{f.class}, Void.TYPE).isSupported) {
            File file = new File(this.b);
            if (file.exists()) {
                file.delete();
            }
            SharePreferenceUtils.setPrefInt(BaseApplication.b(), SharePreferenceUtils._spSimpleVersionSeq, 1);
            SharePreferenceUtils.setPrefString(BaseApplication.b(), SharePreferenceUtils._spSessionKey, "");
            File fileDeviceCache = new File(this.c);
            if (fileDeviceCache.exists()) {
                fileDeviceCache.delete();
            }
            emitter.onNext(new n(true, "删除成功"));
            emitter.onComplete();
        }
    }

    public e<Boolean> x(JSONObject jsonObject) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jsonObject}, this, changeQuickRedirect, false, 1719, new Class[]{JSONObject.class}, e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new d(this, jsonObject), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: u */
    public /* synthetic */ void v(JSONObject jsonObject, f emitter) {
        if (!PatchProxy.proxy(new Object[]{jsonObject, emitter}, this, changeQuickRedirect, false, 1722, new Class[]{JSONObject.class, f.class}, Void.TYPE).isSupported) {
            try {
                File mqttConfigDir = new File(this.a);
                if (!mqttConfigDir.exists()) {
                    mqttConfigDir.mkdir();
                }
                if (!jsonObject.has(CacheEntity.KEY) || !jsonObject.get(CacheEntity.KEY).toString().contains("fullUpdate")) {
                    emitter.onNext(true);
                    emitter.onComplete();
                    return;
                }
                File deviceListFilePath = new File(this.c);
                if (deviceListFilePath.exists()) {
                    deviceListFilePath.delete();
                }
                y(jsonObject.toString(), this.c);
                emitter.onNext(true);
                emitter.onComplete();
            } catch (Exception e) {
                e.printStackTrace();
                timber.log.a.c(" deviceList 数据缓存失败....", new Object[0]);
                emitter.onNext(false);
                emitter.onComplete();
            }
        }
    }

    public e<JSONObject> f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1720, new Class[0], e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new c(this), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: q */
    public /* synthetic */ void r(f emitter) {
        if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 1721, new Class[]{f.class}, Void.TYPE).isSupported) {
            try {
                String _content = w(this.c);
                emitter.onNext(TextUtils.isEmpty(_content) ? new JSONObject() : new JSONObject(_content));
            } catch (Exception e) {
                emitter.onNext(new JSONObject());
            }
            emitter.onComplete();
        }
    }
}
