package com.leedarson.smarthome.robust;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.base.utils.w;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.smarthome.robust.beans.PatchResultConfigBean;
import com.leedarson.smarthome.robust.beans.PatchResultConfigWrapBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.trello.rxlifecycle3.b;
import io.reactivex.f;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.UUID;
import org.json.JSONObject;

/* compiled from: PatchConfigRepos */
public class e {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String a = "device_info";
    private final String b = "device_id";

    public io.reactivex.e<PatchResultConfigBean> b(String index, String status) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{index, status}, this, changeQuickRedirect, false, 10675, new Class[]{cls, cls}, io.reactivex.e.class);
        return proxy.isSupported ? (io.reactivex.e) proxy.result : io.reactivex.e.d(new a(this, index, status), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public /* synthetic */ void g(String index, String status, f emitter) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, f.class};
        if (!PatchProxy.proxy(new Object[]{index, status, emitter}, this, changeQuickRedirect, false, 10680, clsArr, Void.TYPE).isSupported) {
            PatchResultConfigBean data = new PatchResultConfigBean();
            JSONObject paramsObj = new JSONObject();
            paramsObj.put("os", (Object) "Android");
            paramsObj.put("appVersion", (Object) w.H(BaseApplication.b()));
            paramsObj.put("buildNumber", (Object) "2979");
            paramsObj.put(FirebaseAnalytics.Param.INDEX, (Object) index);
            paramsObj.put("status", (Object) status);
            JSONObject headerObj = new JSONObject();
            headerObj.put("mac", (Object) c(BaseApplication.b()));
            b0.b().K(BaseApplication.b(), (b<com.trello.rxlifecycle3.android.a>) null, "/commons/activity", headerObj.toString(), paramsObj.toString(), new a(emitter, data));
        }
    }

    /* compiled from: PatchConfigRepos */
    public class a extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ f c;
        final /* synthetic */ PatchResultConfigBean d;

        a(f fVar, PatchResultConfigBean patchResultConfigBean) {
            this.c = fVar;
            this.d = patchResultConfigBean;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 10683, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException apiException) {
            if (!PatchProxy.proxy(new Object[]{apiException}, this, changeQuickRedirect, false, 10681, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                this.c.onNext(this.d);
                this.c.onComplete();
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 10682, new Class[]{String.class}, Void.TYPE).isSupported) {
                try {
                    PatchResultConfigWrapBean temp = (PatchResultConfigWrapBean) new Gson().fromJson(response, PatchResultConfigWrapBean.class);
                    if (temp.code == 200) {
                        PatchResultConfigBean patchResultConfigBean = temp.data;
                        if (patchResultConfigBean != null) {
                            this.c.onNext(patchResultConfigBean);
                        } else {
                            this.c.onNext(this.d);
                        }
                    } else {
                        this.c.onNext(this.d);
                    }
                    this.c.onComplete();
                } catch (Exception e) {
                    this.c.onNext(this.d);
                    this.c.onComplete();
                }
            }
        }
    }

    public static String d(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 10676, new Class[]{File.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            if (!file.isFile()) {
                return "";
            }
            byte[] buffer = new byte[1024];
            try {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                FileInputStream in = new FileInputStream(file);
                while (true) {
                    int read = in.read(buffer, 0, 1024);
                    int len = read;
                    if (read != -1) {
                        digest.update(buffer, 0, len);
                    } else {
                        in.close();
                        return a(digest.digest());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e2) {
            Log.e("PatchConfigRepos", "getFileMd5  exception   e=" + e2.toString());
            return "";
        }
    }

    private static String a(byte[] src) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{src}, (Object) null, changeQuickRedirect, true, 10677, new Class[]{byte[].class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b2 : src) {
            String hv = Integer.toHexString(b2 & 255);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private String c(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 10678, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("device_info", 0);
        String deviceId = sharedPreferences.getString("device_id", (String) null);
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        String deviceId2 = e(context);
        if (TextUtils.isEmpty(deviceId2)) {
            deviceId2 = UUID.randomUUID().toString().replace("-", "");
        }
        sharedPreferences.edit().putString("device_id", deviceId2).apply();
        return deviceId2;
    }

    private String e(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 10679, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (Build.VERSION.SDK_INT >= 29) {
            return null;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager == null) {
                return null;
            }
            return telephonyManager.getDeviceId();
        } catch (Exception e) {
            return null;
        }
    }
}
