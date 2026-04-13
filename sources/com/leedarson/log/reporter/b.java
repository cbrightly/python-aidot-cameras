package com.leedarson.log.reporter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.log.h;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.tutk.IOTC.AVIOCTRLDEFs;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: ELKReporter */
public class b implements d {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public final String a = b.class.getSimpleName();
    /* access modifiers changed from: private */
    public Context b;
    private String c;
    private String d;
    private c e;

    /* compiled from: ELKReporter */
    public interface c {
        void onFail();

        void onSuccess();
    }

    static /* synthetic */ void c(b x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, AVIOCTRLDEFs.E_CMD_AVIO_CTRL_RT_RADAR_REPORT, new Class[]{b.class}, Void.TYPE).isSupported) {
            x0.h();
        }
    }

    static /* synthetic */ void e(b x0, h.j x1) {
        Class[] clsArr = {b.class, h.j.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 1325, clsArr, Void.TYPE).isSupported) {
            x0.j(x1);
        }
    }

    static /* synthetic */ void f(b x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, AVIOCTRLDEFs.E_CMD_AVIO_CTRL_RT_RADAR_SDCARD_START_REQ, new Class[]{b.class}, Void.TYPE).isSupported) {
            x0.i();
        }
    }

    public void a(String content) {
        this.d = content;
    }

    public b(Context mContext, String serverUrl) {
        this.c = serverUrl == null ? SharePreferenceUtils.getPrefString(mContext, "reportHttpServer", "") : serverUrl;
        this.b = mContext;
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1318, new Class[0], Void.TYPE).isSupported) {
            if (this.b == null || TextUtils.isEmpty(this.c)) {
                h();
                return;
            }
            JSONObject headerJson = new JSONObject();
            JSONObject bodyContent = new JSONObject();
            String accessToken = SharePreferenceUtils.getPrefString(this.b, "accessToken", "");
            try {
                if (!TextUtils.isEmpty(accessToken)) {
                    headerJson.put("token", (Object) accessToken);
                }
                bodyContent.put("contents", (Object) this.d);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            Log.i("test-abc", "上报elk数据:" + this.c);
            b0.b().N(this.b, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, this.c, headerJson.toString(), bodyContent.toString(), new a());
        }
    }

    /* compiled from: ELKReporter */
    public class a extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1329, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, AVIOCTRLDEFs.E_CMD_AVIO_CTRL_RT_RADAR_SDCARD_START_RESP, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                b.c(b.this);
                timber.log.a.g(b.this.a).d(e);
                if (e.getCode() == 630009) {
                    b.e(b.this, new C0087a());
                } else {
                    b.c(b.this);
                }
            }
        }

        /* renamed from: com.leedarson.log.reporter.b$a$a  reason: collision with other inner class name */
        /* compiled from: ELKReporter */
        public class C0087a implements h.j {
            public static ChangeQuickRedirect changeQuickRedirect;

            C0087a() {
            }

            public void onSuccess() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1330, new Class[0], Void.TYPE).isSupported) {
                    b.this.b();
                }
            }

            public void onError() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1331, new Class[0], Void.TYPE).isSupported) {
                    b.c(b.this);
                }
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1328, new Class[]{String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(b.this.a);
                g.a("uploadLog#onSuccess response=" + response, new Object[0]);
                b.f(b.this);
            }
        }
    }

    public void k(c callback) {
        if (!PatchProxy.proxy(new Object[]{callback}, this, changeQuickRedirect, false, 1319, new Class[]{c.class}, Void.TYPE).isSupported) {
            this.e = callback;
            b();
        }
    }

    private void j(h.j listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, AVIOCTRLDEFs.E_CMD_AVIO_CTRL_RT_RADAR_START_REQ, new Class[]{h.j.class}, Void.TYPE).isSupported) {
            JSONObject headerJson = new JSONObject();
            String accessToken = SharePreferenceUtils.getPrefString(this.b, "accessToken", "");
            try {
                headerJson.put("appId", (Object) SharePreferenceUtils.getPrefString(this.b, "APP_ID", ""));
                if (!TextUtils.isEmpty(accessToken)) {
                    headerJson.put("token", (Object) accessToken);
                }
                headerJson.put("terminal", (Object) "app");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            String base_url = SharePreferenceUtils.getPrefString(this.b, "httpServer", "");
            JSONObject msgObj = new JSONObject();
            try {
                msgObj.put("refreshToken", (Object) SharePreferenceUtils.getPrefString(this.b, "refreshToken", ""));
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
            b0 b2 = b0.b();
            Context applicationContext = this.b.getApplicationContext();
            b2.K(applicationContext, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, base_url + "/user/refreshUserToken", headerJson.toString(), msgObj.toString(), new C0088b(listener));
        }
    }

    /* renamed from: com.leedarson.log.reporter.b$b  reason: collision with other inner class name */
    /* compiled from: ELKReporter */
    public class C0088b extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ h.j c;

        C0088b(h.j jVar) {
            this.c = jVar;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1334, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException apiException) {
            if (!PatchProxy.proxy(new Object[]{apiException}, this, changeQuickRedirect, false, 1332, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                timber.log.a.g(b.this.a).h("refreshToke onError: ", new Object[0]);
                h.j jVar = this.c;
                if (jVar != null) {
                    jVar.onError();
                }
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1333, new Class[]{String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(b.this.a);
                g.h("refreshToke onSuccess: " + response, new Object[0]);
                try {
                    JSONObject dataObj = new JSONObject(response).getJSONObject("data");
                    if (dataObj.has("accessToken")) {
                        SharePreferenceUtils.setPrefString(b.this.b, "accessToken", dataObj.getString("accessToken"));
                    }
                    if (dataObj.has("refreshToken")) {
                        SharePreferenceUtils.setPrefString(b.this.b, "refreshToken", dataObj.getString("refreshToken"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                h.j jVar = this.c;
                if (jVar != null) {
                    jVar.onSuccess();
                }
            }
        }
    }

    private void i() {
        c cVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.E_CMD_AVIO_CTRL_RT_RADAR_STOP_REQ, new Class[0], Void.TYPE).isSupported && (cVar = this.e) != null) {
            cVar.onSuccess();
        }
    }

    private void h() {
        c cVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.E_CMD_AVIO_CTRL_RT_RADAR_STOP_RESP, new Class[0], Void.TYPE).isSupported && (cVar = this.e) != null) {
            cVar.onFail();
        }
    }
}
