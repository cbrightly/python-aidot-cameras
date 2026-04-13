package com.leedarson.newui.cloud_play_back.repos;

import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import com.leedarson.newui.door_phone.repos.g;
import com.leedarson.newui.repos.beans.EventListItemBean;
import com.leedarson.serviceimpl.http.manager.b0;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.trello.rxlifecycle3.b;
import io.reactivex.e;
import io.reactivex.f;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: DeleteRepos */
public class w extends g {
    public static ChangeQuickRedirect changeQuickRedirect;

    public e<LDSBaseBean<Object>> c(String deviceId, String[] eventIds, ArrayList<EventListItemBean> eventList) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId, eventIds, eventList}, this, changeQuickRedirect, false, 3713, new Class[]{String.class, String[].class, ArrayList.class}, e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new a(this, deviceId, eventIds, eventList), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public /* synthetic */ void e(String str, String[] strArr, ArrayList arrayList, f fVar) {
        Class[] clsArr = {String.class, String[].class, ArrayList.class, f.class};
        if (!PatchProxy.proxy(new Object[]{str, strArr, arrayList, fVar}, this, changeQuickRedirect, false, 3714, clsArr, Void.TYPE).isSupported) {
            String[] eventIds = strArr;
            f emitter = fVar;
            String deviceId = str;
            ArrayList eventList = arrayList;
            JSONObject paramsJson = new JSONObject();
            try {
                if (!com.alibaba.android.arouter.utils.e.b(deviceId)) {
                    if (deviceId.split(",").length == 1) {
                        paramsJson.put("deviceId", (Object) deviceId);
                    }
                }
                JSONArray ids = new JSONArray();
                for (int i = 0; i < eventIds.length; i++) {
                    ids.put((Object) eventIds[i] + "");
                }
                paramsJson.put("ids", (Object) ids);
                if (eventList != null) {
                    JSONArray eventListJson = new JSONArray();
                    for (int i2 = 0; i2 < eventList.size(); i2++) {
                        JSONObject item = new JSONObject();
                        item.put("eventUuid", (Object) ((EventListItemBean) eventList.get(i2)).getEventUuid());
                        item.put("frameworkFlag", ((EventListItemBean) eventList.get(i2)).frameworkFlag);
                        item.put("deviceId", (Object) ((EventListItemBean) eventList.get(i2)).getDeviceId());
                        eventListJson.put(i2, (Object) item);
                    }
                    paramsJson.put("eventList", (Object) eventListJson);
                } else {
                    paramsJson.put("eventList", (Object) new JSONArray());
                }
                b0.b().O(BaseApplication.b(), (b<com.trello.rxlifecycle3.android.a>) null, "/api/ipc/recordDeleteController/deleteEvent", "", paramsJson.toString(), new a(emitter));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /* compiled from: DeleteRepos */
    public class a extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ f c;

        a(f fVar) {
            this.c = fVar;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 3718, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 3715, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("deleteEvent").a("deleteEvent", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 3716, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("deleteEvent");
                g.c("error=" + e.getMsg(), new Object[0]);
                this.c.onError(e);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 3717, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("deleteEvent").h(response, new Object[0]);
                LDSBaseBean data = new LDSBaseBean();
                data.code = 200;
                data.desc = "删除成功";
                this.c.onNext(data);
                this.c.onComplete();
            }
        }
    }
}
