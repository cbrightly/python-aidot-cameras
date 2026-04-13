package com.leedarson.serviceimpl.map;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.BusinessService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: RadarMapApi */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;
    private BusinessService a = ((BusinessService) com.alibaba.android.arouter.launcher.a.c().g(BusinessService.class));

    /* compiled from: RadarMapApi */
    public interface d {
        void a(Object obj);

        void b(int i, String str);
    }

    public void a(Context context, String deviceId, d listener) {
        Class[] clsArr = {Context.class, String.class, d.class};
        if (!PatchProxy.proxy(new Object[]{context, deviceId, listener}, this, changeQuickRedirect, false, 8282, clsArr, Void.TYPE).isSupported) {
            c.a("deleteRadarMap");
            JSONObject head = new JSONObject();
            try {
                head.put("houseId", (Object) SharePreferenceUtils.getPrefString(context, "houseId", ""));
                head.put("appId", (Object) SharePreferenceUtils.getPrefString(context, "APP_ID", ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            b0 b = b0.b();
            b.J(context, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, "/ipc/radar/devices/" + deviceId, head.toString(), (String) null, new a(context, listener));
        }
    }

    /* compiled from: RadarMapApi */
    public class a extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Context c;
        final /* synthetic */ d d;

        a(Context context, d dVar) {
            this.c = context;
            this.d = dVar;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8288, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException apiException) {
            if (!PatchProxy.proxy(new Object[]{apiException}, this, changeQuickRedirect, false, 8286, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                Toast.makeText(this.c, R$string.delete_failed, 0).show();
            }
        }

        public void onSuccess(String str) {
            if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 8287, new Class[]{String.class}, Void.TYPE).isSupported) {
                d dVar = this.d;
                if (dVar != null) {
                    dVar.a("");
                }
                Toast.makeText(this.c, R$string.delete_success, 0).show();
            }
        }
    }

    public void d(Context context, String deviceId, String filePath, LatLng latLng) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{context, deviceId, filePath, latLng}, this, changeQuickRedirect, false, 8283, new Class[]{Context.class, cls, cls, LatLng.class}, Void.TYPE).isSupported) {
            Log.i("RadarMapApi", "saveRadarMap filePath:" + filePath + ",deviceId:" + deviceId + ",lat:" + latLng.latitude + ",long:" + latLng.longitude);
            JSONArray jsonArray = new JSONArray();
            jsonArray.put((Object) filePath);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("paths", (Object) jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.a.handleData("", (Activity) context, "upload", jsonObject.toString());
        }
    }

    public void c(String fileId, String deviceId, LatLng latLng, d listener) {
        Class<String> cls = String.class;
        String str = "";
        if (!PatchProxy.proxy(new Object[]{fileId, deviceId, latLng, listener}, this, changeQuickRedirect, false, 8284, new Class[]{cls, cls, LatLng.class, d.class}, Void.TYPE).isSupported) {
            JSONObject head = new JSONObject();
            try {
                head.put("houseId", (Object) SharePreferenceUtils.getPrefString(BaseApplication.b(), "houseId", str));
                head.put("appId", (Object) SharePreferenceUtils.getPrefString(BaseApplication.b(), "APP_ID", str));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject body = new JSONObject();
            try {
                body.put("deviceId", (Object) deviceId);
                body.put("longitude", (Object) latLng != null ? String.valueOf(latLng.longitude) : str);
                if (latLng != null) {
                    str = String.valueOf(latLng.latitude);
                }
                body.put("latitude", (Object) str);
                body.put("fileId", (Object) fileId);
                c.a("postRadarData:" + body.toString());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            b0 b = b0.b();
            BaseApplication b2 = BaseApplication.b();
            b.O(b2, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, "/ipc/radar/devices/" + deviceId, head.toString(), body.toString(), new C0142b(listener));
        }
    }

    /* renamed from: com.leedarson.serviceimpl.map.b$b  reason: collision with other inner class name */
    /* compiled from: RadarMapApi */
    public class C0142b extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ d c;

        C0142b(d dVar) {
            this.c = dVar;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8291, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 8289, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                d dVar = this.c;
                if (dVar != null) {
                    dVar.b(e.getCode(), e.getMsg());
                }
                Log.i("RadarMapApi", "saveRadarImage error:" + e.getMessage());
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 8290, new Class[]{String.class}, Void.TYPE).isSupported) {
                try {
                    Log.i("RadarMapApi", "saveRadarImage response:" + response);
                    d dVar = this.c;
                    if (dVar != null) {
                        dVar.a(response);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public void b(String deviceId, d listener) {
        Class[] clsArr = {String.class, d.class};
        if (!PatchProxy.proxy(new Object[]{deviceId, listener}, this, changeQuickRedirect, false, 8285, clsArr, Void.TYPE).isSupported) {
            JSONObject head = new JSONObject();
            try {
                head.put("houseId", (Object) SharePreferenceUtils.getPrefString(BaseApplication.b(), "houseId", ""));
                head.put("appId", (Object) SharePreferenceUtils.getPrefString(BaseApplication.b(), "APP_ID", ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            b0 b = b0.b();
            BaseApplication b2 = BaseApplication.b();
            b.K(b2, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, "/ipc/radar/devices/" + deviceId, head.toString(), (String) null, new c(listener));
        }
    }

    /* compiled from: RadarMapApi */
    public class c extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ d c;

        c(d dVar) {
            this.c = dVar;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8294, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 8292, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                c.a(e.getMsg());
                d dVar = this.c;
                if (dVar != null) {
                    dVar.b(e.getCode(), e.getMsg());
                }
            }
        }

        public void onSuccess(String response) {
            d dVar;
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 8293, new Class[]{String.class}, Void.TYPE).isSupported && (dVar = this.c) != null) {
                dVar.a(response);
            }
        }
    }
}
