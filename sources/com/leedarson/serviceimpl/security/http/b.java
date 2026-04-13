package com.leedarson.serviceimpl.security.http;

import android.content.Context;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.serviceimpl.http.manager.b0;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.l;
import io.reactivex.m;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: AlarmHttpRequest */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;

    public b(Context context) {
        this.a = context;
    }

    public l<String> c(int operateType, String houseId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(operateType), houseId}, this, changeQuickRedirect, false, 8667, new Class[]{Integer.TYPE, String.class}, l.class);
        if (proxy.isSupported) {
            return (l) proxy.result;
        }
        JSONObject joParams = new JSONObject();
        JSONObject headParms = new JSONObject();
        try {
            headParms.put("houseId", (Object) houseId);
            joParams.put("operateType", operateType);
            a.b g = timber.log.a.g("AlarmWindowHelper");
            g.m("请求体:" + joParams.toString() + ",houseid:" + houseId, new Object[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return l.k(new a(this, headParms, joParams, operateType));
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public /* synthetic */ void b(JSONObject headParms, JSONObject joParams, int operateType, m emitter) {
        Class<JSONObject> cls = JSONObject.class;
        Object[] objArr = {headParms, joParams, new Integer(operateType), emitter};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8668, new Class[]{cls, cls, Integer.TYPE, m.class}, Void.TYPE).isSupported) {
            b0.b().P(this.a, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, "/v15/newSecurity/operateSecurityAlarm", headParms.toString(), joParams.toString(), new a(operateType, emitter), io.reactivex.schedulers.a.d());
        }
    }

    /* compiled from: AlarmHttpRequest */
    public class a extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;
        final /* synthetic */ m d;

        a(int i, m mVar) {
            this.c = i;
            this.d = mVar;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8671, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 8669, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("AlarmWindowHelper");
                g.m("postAlarm request operateType: " + this.c + ", fail e:" + e.getMessage(), new Object[0]);
                this.d.onError(e);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 8670, new Class[]{String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("AlarmWindowHelper");
                g.m("postAlarm request success: " + response, new Object[0]);
                this.d.onNext(response);
                this.d.onComplete();
            }
        }
    }
}
