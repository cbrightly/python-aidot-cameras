package com.leedarson.newui.repos;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBasePageBean;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSPageDataWrapBean;
import com.leedarson.newui.door_phone.repos.g;
import com.leedarson.newui.repos.beans.EventListItemBean;
import com.leedarson.newui.repos.beans.EventListRequestParamsBean;
import com.leedarson.newui.repos.beans.EventUrlResponseWrapBean;
import com.leedarson.newui.repos.beans.EventVideoUrlRequestParamBean;
import com.leedarson.serviceimpl.http.manager.b0;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.e;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: EventListRepos */
public class o extends g {
    public static ChangeQuickRedirect changeQuickRedirect;
    Gson b = new Gson();
    private final String c = "EventListRepos";

    public e<LDSBasePageBean<LDSPageDataWrapBean<EventListItemBean>>> e(EventListRequestParamsBean questParams) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{questParams}, this, changeQuickRedirect, false, 4438, new Class[]{EventListRequestParamsBean.class}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        List<String> list = questParams.deviceIds;
        if (list == null || list.size() <= 1) {
            questParams.pageSize = 10;
        } else {
            questParams.pageSize = 5;
        }
        String jsonBody = this.b.toJson((Object) questParams);
        com.leedarson.base.logger.a.c("EventListRepos", "getEventLists---->questParams=" + jsonBody);
        return b0.b().Q((com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, "/api/ipc/playback/eventRecordingList", "", jsonBody).o(new h(this));
    }

    /* compiled from: EventListRepos */
    public class a extends TypeToken<LDSBasePageBean<LDSPageDataWrapBean<EventListItemBean>>> {
        a() {
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: n */
    public /* synthetic */ org.reactivestreams.a o(String response) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 4448, new Class[]{String.class}, org.reactivestreams.a.class);
        if (proxy.isSupported) {
            return (org.reactivestreams.a) proxy.result;
        }
        com.leedarson.base.logger.a.c("EventListRepos", "getEventLists---->response=" + response);
        return e.w((LDSBasePageBean) this.b.fromJson(response, new a().getType()));
    }

    private e<Boolean> c(String deviceId, String eventUuid, int total) {
        Class<String> cls = String.class;
        Object[] objArr = {deviceId, eventUuid, new Integer(total)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 4439, new Class[]{cls, cls, Integer.TYPE}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        try {
            JSONObject requestData = new JSONObject();
            requestData.put("deviceId", (Object) deviceId);
            requestData.put("eventUuid", (Object) eventUuid);
            requestData.put("total", total);
            return b0.b().Q((com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, "/api/ipc/playback/eventVideoPlayCount", "", requestData.toString()).o(new i(this));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: h */
    public /* synthetic */ org.reactivestreams.a i(String response) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 4447, new Class[]{String.class}, org.reactivestreams.a.class);
        if (proxy.isSupported) {
            return (org.reactivestreams.a) proxy.result;
        }
        com.leedarson.base.logger.a.c("EventListRepos", "eventPlayCount---->response=" + response);
        return e.w(true);
    }

    public void d(String deviceId, String eventUuid, int total) {
        Class<String> cls = String.class;
        Object[] objArr = {deviceId, eventUuid, new Integer(total)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4440, new Class[]{cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            a(c(deviceId, eventUuid, total).I(new f(this), new j(this)));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: j */
    public /* synthetic */ void k(Boolean aBoolean) {
        if (!PatchProxy.proxy(new Object[]{aBoolean}, this, changeQuickRedirect, false, 4446, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("EventListRepos", "上报播放次数成功Success" + aBoolean);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: l */
    public /* synthetic */ void m(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 4445, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("EventListRepos", "上报播放次数异常Fail" + throwable.toString());
        }
    }

    public e<LDSBasePageBean<EventUrlResponseWrapBean>> f(EventListItemBean eventListItemBean) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{eventListItemBean}, this, changeQuickRedirect, false, 4441, new Class[]{EventListItemBean.class}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        EventVideoUrlRequestParamBean requestParamBean = new EventVideoUrlRequestParamBean();
        requestParamBean.deviceId = eventListItemBean.getDeviceId();
        requestParamBean.eventList.add(eventListItemBean);
        return b0.b().Q((com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, "/api/ipc/playback/getEventVideoUrl", "", this.b.toJson((Object) requestParamBean)).o(new e(this));
    }

    /* compiled from: EventListRepos */
    public class b extends TypeToken<LDSBasePageBean<EventUrlResponseWrapBean>> {
        b() {
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: p */
    public /* synthetic */ org.reactivestreams.a q(String response) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 4444, new Class[]{String.class}, org.reactivestreams.a.class);
        if (proxy.isSupported) {
            return (org.reactivestreams.a) proxy.result;
        }
        com.leedarson.base.logger.a.c("EventListRepos", "getEventVideoUrl.response=" + response);
        return e.w((LDSBasePageBean) this.b.fromJson(response, new b().getType()));
    }

    public e<LDSBasePageBean<EventUrlResponseWrapBean>> g(String deviceId, String eventUuId) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId, eventUuId}, this, changeQuickRedirect, false, 4442, new Class[]{cls, cls}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("deviceId", (Object) deviceId);
            JSONArray eventList = new JSONArray();
            JSONObject eventItem = new JSONObject();
            eventItem.put("eventUuid", (Object) eventUuId);
            eventList.put(0, (Object) eventItem);
            requestBody.put("eventList", (Object) eventList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return b0.b().Q((com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, "/api/ipc/playback/getEventVideoUrl", "", requestBody.toString()).o(new g(this));
    }

    /* compiled from: EventListRepos */
    public class c extends TypeToken<LDSBasePageBean<EventUrlResponseWrapBean>> {
        c() {
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: r */
    public /* synthetic */ org.reactivestreams.a s(String response) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 4443, new Class[]{String.class}, org.reactivestreams.a.class);
        if (proxy.isSupported) {
            return (org.reactivestreams.a) proxy.result;
        }
        com.leedarson.base.logger.a.c("EventListRepos", "getEventVideoUrl.response=" + response);
        return e.w((LDSBasePageBean) this.b.fromJson(response, new c().getType()));
    }
}
