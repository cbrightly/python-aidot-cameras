package com.leedarson.newui.cloud_play_back.repos;

import com.google.gson.Gson;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.newui.cloud_play_back.repos.beans.EventItemDetailBean;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.trello.rxlifecycle3.b;
import io.reactivex.e;
import io.reactivex.f;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: EventDetailRepos */
public class x {
    public static ChangeQuickRedirect changeQuickRedirect;

    public e<EventItemDetailBean> a(String deviceId, String eventUuid, b<com.trello.rxlifecycle3.android.a> lifecycleProvider) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId, eventUuid, lifecycleProvider}, this, changeQuickRedirect, false, 3719, new Class[]{cls, cls, b.class}, e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new b(this, lifecycleProvider, eventUuid, deviceId), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public /* synthetic */ void c(b bVar, String eventUuid, String str, f emitter) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{bVar, eventUuid, str, emitter}, this, changeQuickRedirect, false, 3720, new Class[]{b.class, cls, cls, f.class}, Void.TYPE).isSupported) {
            b lifecycleProvider = bVar;
            String deviceId = str;
            JSONObject paramsJson = new JSONObject();
            String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
            b0 b = b0.b();
            BaseApplication b2 = BaseApplication.b();
            b.K(b2, lifecycleProvider, baseUrl + "/api/ipc/event?eventUuid=" + eventUuid + "&deviceId=" + deviceId, "", paramsJson.toString(), new a(emitter));
        }
    }

    /* compiled from: EventDetailRepos */
    public class a extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ f c;

        a(f fVar) {
            this.c = fVar;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 3724, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 3721, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("featchEventDatqa").a("deleteEvent", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 3722, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("featchEventDatqa");
                g.c("error=" + e.getMsg(), new Object[0]);
                this.c.onError(e);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 3723, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("featchEventDatqa").h(response, new Object[0]);
                try {
                    this.c.onNext((EventItemDetailBean) new Gson().fromJson(response, EventItemDetailBean.class));
                    this.c.onComplete();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
