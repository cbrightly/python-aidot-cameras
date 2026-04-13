package com.leedarson.newui;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ldf.calendar.view.Calendar;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.beans.FeedbackDoneBean;
import com.leedarson.base.beans.FeedbackDoneParamsBean;
import com.leedarson.base.beans.FeedbackRequestBean;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.utils.w;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.Constants;
import com.leedarson.bean.EventBean;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBasePageBean;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSPageDataWrapBean;
import com.leedarson.newui.repos.beans.BindPackageInfoResponseBean;
import com.leedarson.newui.repos.beans.EventListRequestParamsBean;
import com.leedarson.newui.repos.m;
import com.leedarson.newui.repos.o;
import com.leedarson.newui.repos.p;
import com.leedarson.newui.repoter.beans.ELKStepRecordBean;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.BusinessService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.view.WeekCalendar;
import com.luck.picture.lib.config.PictureConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: CloudPlaybackPresenter */
public class y5 extends com.leedarson.base.presenters.a<z5, CloudPlaybackFragment> {
    public static ChangeQuickRedirect changeQuickRedirect;
    Dialog A;
    long B = System.currentTimeMillis();
    o f = new o();
    com.leedarson.newui.repoter.g g = new com.leedarson.newui.repoter.g();
    private String h;
    private WeekCalendar i;
    private Calendar j;
    m k = new m();
    /* access modifiers changed from: private */
    public ArrayList<io.reactivex.disposables.b> l = new ArrayList<>();
    p m = new p();
    io.reactivex.disposables.b n;
    io.reactivex.disposables.b o;
    private String p;
    private String q;
    private String r;
    private String s;
    private boolean t;
    Gson u = new Gson();
    ELKStepRecordBean v = new ELKStepRecordBean();
    private io.reactivex.disposables.b w;
    private String x = " 00:00:00";
    private String y = " 23:59:59";
    final ELKStepRecordBean z = new ELKStepRecordBean();

    public y5(z5 view, CloudPlaybackFragment fragment) {
        super(view, fragment);
    }

    public void T(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 1928, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("CloudPlaybackPresenter").a(msg, new Object[0]);
        }
    }

    public void t() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1929, new Class[0], Void.TYPE).isSupported) {
            this.f.b();
            U();
        }
    }

    public void D(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 1930, new Class[]{String.class}, Void.TYPE).isSupported) {
            String deviceId = str;
            String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
            JSONObject headerJson = new JSONObject();
            JSONObject paramsJson = new JSONObject();
            String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
            try {
                if (!TextUtils.isEmpty(accessToken)) {
                    headerJson.put("token", (Object) accessToken);
                }
                headerJson.put("terminal", (Object) "app");
                paramsJson.put("deviceId", (Object) deviceId);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            String url = baseUrl + "/api/ipc/recordPlanController/getPackageInfoByDevId";
            timber.log.a.g("getPackageInfoByDevId").h("getPackageInfoByDevId:request= " + url, new Object[0]);
            b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, headerJson.toString(), paramsJson.toString(), new b());
        }
    }

    /* compiled from: CloudPlaybackPresenter */
    public class b extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1955, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 1952, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getPackageInfoByDevId").a("getPackageInfoByDevId", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 1953, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("getPackageInfoByDevId");
                g.c("error=" + e.getMsg(), new Object[0]);
                ((z5) y5.this.m()).U(e.getMsg());
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1954, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getPackageInfoByDevId").c(response, new Object[0]);
                ((z5) y5.this.m()).t0(response);
            }
        }
    }

    public void y(String deviceIds, WeekCalendar weekCalendar, String eventCodes) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceIds, weekCalendar, eventCodes}, this, changeQuickRedirect, false, 1931, new Class[]{cls, WeekCalendar.class, cls}, Void.TYPE).isSupported) {
            this.p = deviceIds;
            this.g.d(this.h);
            this.i = weekCalendar;
            io.reactivex.disposables.b bVar = this.n;
            if (bVar != null && !bVar.isDisposed()) {
                this.n.dispose();
            }
            io.reactivex.disposables.b I = this.m.a(deviceIds, weekCalendar.getCurrentWeekDatas(), eventCodes, l() != null ? ((CloudPlaybackFragment) l()).b5 : "").c(l.c()).I(new s1(this), n1.c);
            this.n = I;
            b(I);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: J */
    public /* synthetic */ void K(String s2) {
        if (!PatchProxy.proxy(new Object[]{s2}, this, changeQuickRedirect, false, 1951, new Class[]{String.class}, Void.TYPE).isSupported) {
            ((z5) m()).H0(s2);
        }
    }

    static /* synthetic */ void L(Throwable throwable) {
    }

    public void z(String deviceIds, Calendar calendar, String eventCodes) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, Calendar.class, cls};
        if (!PatchProxy.proxy(new Object[]{deviceIds, calendar, eventCodes}, this, changeQuickRedirect, false, 1932, clsArr, Void.TYPE).isSupported) {
            if (calendar != null) {
                this.j = calendar;
                this.p = deviceIds;
                io.reactivex.disposables.b bVar = this.o;
                if (bVar != null && !bVar.isDisposed()) {
                    this.o.dispose();
                }
                io.reactivex.disposables.b I = this.m.b(deviceIds, eventCodes, calendar.getSeedDate()).c(l.c()).I(new r1(this), p1.c);
                this.o = I;
                b(I);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: M */
    public /* synthetic */ void N(String s2) {
        if (!PatchProxy.proxy(new Object[]{s2}, this, changeQuickRedirect, false, 1950, new Class[]{String.class}, Void.TYPE).isSupported) {
            ((z5) m()).H0(s2);
        }
    }

    static /* synthetic */ void O(Throwable throwable) {
    }

    public void X(String curStartTime, String curEndTime) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{curStartTime, curEndTime}, this, changeQuickRedirect, false, 1933, clsArr, Void.TYPE).isSupported) {
            this.x = " " + curStartTime;
            this.y = " " + curEndTime;
        }
    }

    public void B(String str, String str2, String str3, String str4, int i2, int i3, boolean isRefresh) {
        String[] eventCodeStrArr;
        Class<String> cls = String.class;
        Object[] objArr = {str, str2, str3, str4, new Integer(i2), new Integer(i3), new Byte(isRefresh ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1934, new Class[]{cls, cls, cls, cls, cls2, cls2, Boolean.TYPE}, Void.TYPE).isSupported) {
            String areaIds = str2;
            int pageSize = i3;
            String time = str4;
            String deviceIds = str;
            String eventCodes = str3;
            int pageNum = i2;
            this.t = isRefresh;
            if (pageNum == 1) {
                ((z5) m()).j0();
            }
            this.p = deviceIds;
            this.s = areaIds;
            this.g.d(deviceIds);
            if (l() != null && !com.alibaba.android.arouter.utils.e.b(((CloudPlaybackFragment) l()).b5) && "pet".equals(((CloudPlaybackFragment) l()).b5)) {
                if (com.alibaba.android.arouter.utils.e.b(eventCodes)) {
                    eventCodes = "25";
                } else {
                    eventCodes = "25," + eventCodes;
                }
            }
            this.p = deviceIds;
            this.q = eventCodes;
            this.r = time;
            timber.log.a.a("vary done mCurStartTime:" + this.x + " mCurEndTime:" + this.y, new Object[0]);
            StringBuilder sb = new StringBuilder();
            sb.append(time);
            sb.append(this.x);
            long startTime = com.leedarson.utils.e.b(sb.toString(), "yyyy-MM-dd HH:mm:ss");
            long endTime = com.leedarson.utils.e.b(time + this.y, "yyyy-MM-dd HH:mm:ss");
            EventListRequestParamsBean requestParamsBean = new EventListRequestParamsBean();
            requestParamsBean.pageNum = pageNum;
            ArrayList<String> arrayListArea = new ArrayList<>();
            if (!com.alibaba.android.arouter.utils.e.b(areaIds)) {
                String[] areaIdsArr = areaIds.split(",");
                if (areaIdsArr != null) {
                    String str5 = areaIds;
                    int i4 = 0;
                    while (true) {
                        int pageSize2 = pageSize;
                        if (i4 >= areaIdsArr.length) {
                            break;
                        }
                        arrayListArea.add(areaIdsArr[i4]);
                        i4++;
                        pageSize = pageSize2;
                    }
                } else {
                    int i5 = pageSize;
                }
            } else {
                int i6 = pageSize;
            }
            requestParamsBean.areaIds = arrayListArea;
            ArrayList<String> listDevices = new ArrayList<>();
            if (!com.alibaba.android.arouter.utils.e.b(deviceIds)) {
                String[] deviceIdStrArr = deviceIds.split(",");
                if (deviceIdStrArr != null) {
                    int i7 = 0;
                    while (true) {
                        String time2 = time;
                        if (i7 >= deviceIdStrArr.length) {
                            break;
                        }
                        if (!com.alibaba.android.arouter.utils.e.b(deviceIdStrArr[i7])) {
                            listDevices.add(deviceIdStrArr[i7]);
                        }
                        i7++;
                        time = time2;
                    }
                }
            }
            ArrayList<String> listEvents = new ArrayList<>();
            if (!(eventCodes == null || (eventCodeStrArr = eventCodes.split(",")) == null)) {
                for (int i8 = 0; i8 < eventCodeStrArr.length; i8++) {
                    if (!com.alibaba.android.arouter.utils.e.b(eventCodeStrArr[i8])) {
                        listEvents.add(eventCodeStrArr[i8]);
                    }
                }
            }
            requestParamsBean.deviceIds = listDevices;
            requestParamsBean.eventCodes = listEvents;
            requestParamsBean.recordSta = startTime;
            requestParamsBean.recordEnd = endTime;
            com.leedarson.newui.repoter.b eventIPCBuilder = this.g.g();
            ELKStepRecordBean eLKStepRecordBean = new ELKStepRecordBean();
            this.v = eLKStepRecordBean;
            ArrayList<String> arrayList = listDevices;
            eLKStepRecordBean.startRequest(this.u.toJson((Object) requestParamsBean), "getEventList");
            eventIPCBuilder.a(this.v);
            io.reactivex.disposables.b bVar = this.w;
            if (bVar != null && !bVar.isDisposed()) {
                this.w.dispose();
            }
            io.reactivex.disposables.b I = this.f.e(requestParamsBean).c(l.c()).I(new t1(this, eventIPCBuilder), new o1(this, eventIPCBuilder));
            this.w = I;
            b(I);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: P */
    public /* synthetic */ void Q(com.leedarson.newui.repoter.b eventIPCBuilder, LDSBasePageBean eventListItemResponseBean) {
        Class[] clsArr = {com.leedarson.newui.repoter.b.class, LDSBasePageBean.class};
        if (!PatchProxy.proxy(new Object[]{eventIPCBuilder, eventListItemResponseBean}, this, changeQuickRedirect, false, 1949, clsArr, Void.TYPE).isSupported) {
            this.v.endRequest(this.u.toJson((Object) eventListItemResponseBean));
            eventIPCBuilder.b("获取事件列表成功");
            ((z5) m()).i1((LDSPageDataWrapBean) eventListItemResponseBean.data);
            ((z5) m()).h();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: R */
    public /* synthetic */ void S(com.leedarson.newui.repoter.b eventIPCBuilder, Throwable throwable) {
        Class[] clsArr = {com.leedarson.newui.repoter.b.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{eventIPCBuilder, throwable}, this, changeQuickRedirect, false, 1948, clsArr, Void.TYPE).isSupported) {
            this.v.endRequestException(throwable.toString(), 1100);
            eventIPCBuilder.b("获取事件列表异常");
            if (throwable instanceof ApiException) {
                ((z5) m()).N0(((ApiException) throwable).getMsg());
            }
            ((z5) m()).h();
        }
    }

    public void A(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 1935, new Class[]{String.class}, Void.TYPE).isSupported) {
            String deviceId = str;
            String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
            JSONObject headerJson = new JSONObject();
            JSONObject paramsJson = new JSONObject();
            String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
            String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
            try {
                headerJson.put("owner", (Object) owner);
                headerJson.put("token", (Object) accessToken);
                headerJson.put("terminal", (Object) "app");
                paramsJson.put("deviceIds", (Object) deviceId);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            String url = baseUrl + "/api/ipc/playback/eventFilters";
            timber.log.a.g("getEventFilters").h("getEventFilters:request= " + url, new Object[0]);
            b0.b().K(((CloudPlaybackFragment) l()).getContext(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, headerJson.toString(), paramsJson.toString(), new c());
        }
    }

    /* compiled from: CloudPlaybackPresenter */
    public class c extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1961, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 1958, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getEventFilters").a("getEventFilters", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 1959, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("getEventFilters");
                g.c("error=" + e.getMsg() + " code=" + e.getCode(), new Object[0]);
                ((z5) y5.this.m()).showToast(R$string.request_fail);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1960, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getEventFilters ").c(response, new Object[0]);
                ((z5) y5.this.m()).U0(response);
            }
        }
    }

    public void C() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1936, new Class[0], Void.TYPE).isSupported) {
            String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
            JSONObject headerJson = new JSONObject();
            JSONObject paramsJson = new JSONObject();
            String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
            try {
                if (!TextUtils.isEmpty(accessToken)) {
                    headerJson.put("token", (Object) accessToken);
                }
                headerJson.put("locale", (Object) SharePreferenceUtils.getPrefString(((CloudPlaybackFragment) l()).getContext(), IjkMediaMeta.IJKM_KEY_LANGUAGE, ((CloudPlaybackFragment) l()).getContext().getResources().getConfiguration().locale.getLanguage() + "-" + ((CloudPlaybackFragment) l()).getContext().getResources().getConfiguration().locale.getCountry()));
                headerJson.put("terminal", (Object) "app");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            String url = baseUrl + "/api/ipc/playbackController/getEventTags";
            timber.log.a.g("getEventTags").h("getEventTags:request= " + url, new Object[0]);
            b0.b().K(((CloudPlaybackFragment) l()).getContext(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, headerJson.toString(), paramsJson.toString(), new d());
        }
    }

    /* compiled from: CloudPlaybackPresenter */
    public class d extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1965, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 1962, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getEventTags").a("getEventTags", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 1963, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("getEventTags");
                g.c("error=" + e.getCode() + "——" + e.getMsg(), new Object[0]);
                ((z5) y5.this.m()).b1(e.getMsg());
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1964, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getEventTags").c(response, new Object[0]);
                ((z5) y5.this.m()).h();
                ((z5) y5.this.m()).C0(response);
            }
        }
    }

    public void W(EventBean eventBean, boolean z2, String str, String videoPath, int i2, int i3) {
        Class<String> cls = String.class;
        Object[] objArr = {eventBean, new Byte(z2 ? (byte) 1 : 0), str, videoPath, new Integer(i2), new Integer(i3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1937, new Class[]{EventBean.class, Boolean.TYPE, cls, cls, cls2, cls2}, Void.TYPE).isSupported) {
            boolean correct = z2;
            int agreeShareVideoFlag = i3;
            EventBean eventBean2 = eventBean;
            String customEventCode = str;
            int frameworkFlag = i2;
            String str2 = "";
            String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", str2);
            JSONObject headerJson = new JSONObject();
            JSONObject paramsJson = new JSONObject();
            String appId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "APP_ID", str2);
            String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", str2);
            String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", str2);
            String videoPath2 = videoPath;
            com.leedarson.newui.repoter.b eventIPCBuilder = this.g.f();
            try {
                headerJson.put("owner", (Object) owner);
                headerJson.put("token", (Object) accessToken);
                headerJson.put("terminal", (Object) "app");
                headerJson.put("appId", (Object) appId);
                headerJson.put("appVersion", (Object) w.E(BaseApplication.b()));
                paramsJson.put("deviceId", (Object) eventBean2.getDeviceId());
                paramsJson.put("eventUuid", (Object) eventBean2.getEventUuid());
                paramsJson.put("eventCode", (Object) eventBean2.getEventCode());
                paramsJson.put("customEventCode", (Object) customEventCode);
                paramsJson.put("correct", correct ? 1 : 0);
                if (!correct) {
                    str2 = videoPath2;
                }
                paramsJson.put(PictureConfig.EXTRA_VIDEO_PATH, (Object) str2);
                paramsJson.put("frameworkFlag", frameworkFlag);
                paramsJson.put("agreeShareVideoFlag", agreeShareVideoFlag);
                ELKStepRecordBean eLKStepRecordBean = this.z;
                eLKStepRecordBean.step = "aiFeedback";
                eLKStepRecordBean.request = paramsJson.toString();
                EventBean eventBean3 = eventBean2;
                String str3 = customEventCode;
                try {
                    this.z.start = System.currentTimeMillis();
                    eventIPCBuilder.a(this.z);
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Exception e3) {
                e = e3;
                EventBean eventBean4 = eventBean2;
                String str4 = customEventCode;
                e.printStackTrace();
                b0.b().O(((CloudPlaybackFragment) l()).getContext(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, baseUrl + "/api/ipc/playbackController/saveEventFeedback", headerJson.toString(), paramsJson.toString(), new e(eventIPCBuilder, correct));
            }
            b0.b().O(((CloudPlaybackFragment) l()).getContext(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, baseUrl + "/api/ipc/playbackController/saveEventFeedback", headerJson.toString(), paramsJson.toString(), new e(eventIPCBuilder, correct));
        }
    }

    /* compiled from: CloudPlaybackPresenter */
    public class e extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.newui.repoter.b c;
        final /* synthetic */ boolean d;

        e(com.leedarson.newui.repoter.b bVar, boolean z) {
            this.c = bVar;
            this.d = z;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1969, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 1966, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("saveEventFeedback").a("saveEventFeedback", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 1967, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                y5.this.z.end = System.currentTimeMillis();
                y5.this.z.response = e.toString();
                ELKStepRecordBean eLKStepRecordBean = y5.this.z;
                eLKStepRecordBean.code = 1000;
                eLKStepRecordBean.duration = eLKStepRecordBean.end - eLKStepRecordBean.start;
                this.c.a.u("owner", "Cloud");
                this.c.b("云端接口访问异常");
                a.b g = timber.log.a.g("saveEventFeedback");
                g.c("error=" + e.getCode() + "——" + e.getMsg(), new Object[0]);
                ((z5) y5.this.m()).N(this.d, e.getMsg());
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1968, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("saveEventFeedback").c(response, new Object[0]);
                y5.this.z.end = System.currentTimeMillis();
                ELKStepRecordBean eLKStepRecordBean = y5.this.z;
                eLKStepRecordBean.response = response;
                eLKStepRecordBean.duration = eLKStepRecordBean.end - eLKStepRecordBean.start;
                this.c.a.u("owner", "Cloud");
                this.c.b("接口执行成功");
                ((z5) y5.this.m()).s0(this.d, response);
            }
        }
    }

    public void w(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 1938, new Class[]{String.class}, Void.TYPE).isSupported) {
            String deviceId = str;
            String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
            JSONObject headerJson = new JSONObject();
            JSONObject paramsJson = new JSONObject();
            String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
            String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
            try {
                headerJson.put("owner", (Object) owner);
                headerJson.put("token", (Object) accessToken);
                headerJson.put("terminal", (Object) "app");
                paramsJson.put("deviceId", (Object) deviceId);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            String url = baseUrl + "/api/ipc/devices/" + deviceId + "/cloudStorageTrialsRules";
            timber.log.a.g("getCloudTrialsRules").h("getCloudTrialsRules:request= " + url, new Object[0]);
            b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, headerJson.toString(), paramsJson.toString(), new f());
        }
    }

    /* compiled from: CloudPlaybackPresenter */
    public class f extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1973, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 1970, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getCloudTrialsRules").a("getCloudTrialsRules", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 1971, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("getCloudTrialsRules");
                g.c("error=" + e.getMsg() + " code=" + e.getCode(), new Object[0]);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1972, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getCloudTrialsRules ").c(response, new Object[0]);
                ((z5) y5.this.m()).c1(response);
            }
        }
    }

    public void v(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 1939, new Class[]{String.class}, Void.TYPE).isSupported) {
            String deviceId = str;
            String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
            JSONObject headerJson = new JSONObject();
            JSONObject paramsJson = new JSONObject();
            String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
            String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
            try {
                headerJson.put("owner", (Object) owner);
                headerJson.put("token", (Object) accessToken);
                headerJson.put("terminal", (Object) "app");
                paramsJson.put("deviceId", (Object) deviceId);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            String url = baseUrl + "/api/ipc/autoDistribution/" + deviceId;
            timber.log.a.g("getAutoDistribution").h("getAutoDistribution:request= " + url, new Object[0]);
            b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, headerJson.toString(), paramsJson.toString(), new g());
        }
    }

    /* compiled from: CloudPlaybackPresenter */
    public class g extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1977, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 1974, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getAutoDistribution").a("getAutoDistribution", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 1975, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("getAutoDistribution");
                g.c("error=" + e.getMsg() + " code=" + e.getCode(), new Object[0]);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1976, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getAutoDistribution ").c(response, new Object[0]);
                ((z5) y5.this.m()).u0(response);
            }
        }
    }

    public void E() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1940, new Class[0], Void.TYPE).isSupported) {
            String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
            JSONObject headerJson = new JSONObject();
            String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
            String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
            try {
                headerJson.put("owner", (Object) owner);
                headerJson.put("token", (Object) accessToken);
                headerJson.put("terminal", (Object) "app");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            String url = baseUrl + "/api/ipc/playback/getUserIpcDeviceList/";
            timber.log.a.g("getUserIpcDeviceList").h("getUserIpcDeviceList:request= " + url, new Object[0]);
            b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, headerJson.toString(), (String) null, new h());
        }
    }

    /* compiled from: CloudPlaybackPresenter */
    public class h extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1981, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 1978, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getUserIpcDeviceList").a("getUserIpcDeviceList", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 1979, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("getUserIpcDeviceList");
                g.c("error=" + e.getMsg() + " code=" + e.getCode(), new Object[0]);
                ((z5) y5.this.m()).z0(e.getMsg());
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1980, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getUserIpcDeviceList ").c(response, new Object[0]);
                ((z5) y5.this.m()).S0(response);
            }
        }
    }

    /* compiled from: CloudPlaybackPresenter */
    public class i extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1984, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d) {
            if (!PatchProxy.proxy(new Object[]{d}, this, changeQuickRedirect, false, 1982, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                y5.this.l.add(d);
            }
        }

        public void onError(ApiException e) {
        }

        public void onSuccess(String response) {
            String style = "";
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1983, new Class[]{String.class}, Void.TYPE).isSupported) {
                try {
                    JSONObject respObj = new JSONObject(response);
                    String url = style;
                    if (respObj.has("url")) {
                        url = respObj.getString("url");
                    }
                    if (respObj.has(Constants.ACTION_STYLE)) {
                        style = respObj.getString(Constants.ACTION_STYLE);
                    }
                    ((z5) y5.this.m()).n0(url, style);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void u() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1941, new Class[0], Void.TYPE).isSupported) {
            this.k.c(new i());
        }
    }

    /* compiled from: CloudPlaybackPresenter */
    public class j extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1988, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d) {
            if (!PatchProxy.proxy(new Object[]{d}, this, changeQuickRedirect, false, 1985, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                y5.this.l.add(d);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 1986, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                y5 y5Var = y5.this;
                y5Var.T("getDeviceBindPackageInfo error=" + e.getCode() + "——" + e.getMsg());
            }
        }

        /* compiled from: CloudPlaybackPresenter */
        public class a extends TypeToken<LDSBaseBean<BindPackageInfoResponseBean>> {
            a() {
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1987, new Class[]{String.class}, Void.TYPE).isSupported) {
                y5 y5Var = y5.this;
                y5Var.T("getDeviceBindPackageInfo onSuccess:" + response);
                LDSBaseBean<BindPackageInfoResponseBean> resp = (LDSBaseBean) y5.this.u.fromJson(response, new a().getType());
                try {
                    if (resp.checkDataValid()) {
                        boolean hasCanBind = false;
                        if (((BindPackageInfoResponseBean) resp.data).hasCanBindPackage > 0) {
                            hasCanBind = true;
                        }
                        ((z5) y5.this.m()).b0(((BindPackageInfoResponseBean) resp.data).bindPackageIds, hasCanBind);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1942, new Class[0], Void.TYPE).isSupported) {
            this.k.d(SharePreferenceUtils.getPrefString(BaseApplication.b(), "houseId", ""), new j());
        }
    }

    private void U() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1943, new Class[0], Void.TYPE).isSupported) {
            Iterator<io.reactivex.disposables.b> it = this.l.iterator();
            while (it.hasNext()) {
                io.reactivex.disposables.b disposable = it.next();
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
        }
    }

    public void s() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1944, new Class[0], Void.TYPE).isSupported) {
            if (System.currentTimeMillis() - this.B < 500) {
                this.B = System.currentTimeMillis();
                return;
            }
            this.B = System.currentTimeMillis();
            if (l() != null && ((CloudPlaybackFragment) l()).getContext() != null) {
                Dialog dialog = this.A;
                if (dialog != null && dialog.isShowing()) {
                    this.A.dismiss();
                }
                Dialog dialog2 = new Dialog(((CloudPlaybackFragment) l()).getContext(), R$style.Theme_dialog);
                this.A = dialog2;
                dialog2.setContentView(R$layout.del_dialog_layout);
                this.A.setCanceledOnTouchOutside(false);
                ((LDSTextView) this.A.findViewById(R$id.tip_content_tv)).setText(PubUtils.getString(BaseApplication.b(), R$string.lds_report_issue_tip_mssing_events));
                LDSTextView txt_tips_left = (LDSTextView) this.A.findViewById(R$id.left_btn_tv);
                LDSTextView txt_tips_right = (LDSTextView) this.A.findViewById(R$id.right_btn_tv);
                txt_tips_left.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_report_issue_i_see));
                txt_tips_right.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_btn_tip_report_issue));
                txt_tips_left.setOnClickListener(new u1(this));
                txt_tips_right.setOnClickListener(new q1(this));
                this.A.show();
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: F */
    public /* synthetic */ void G(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1947, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.A.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: H */
    public /* synthetic */ void I(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1946, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.A.dismiss();
        V();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void V() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1945, new Class[0], Void.TYPE).isSupported) {
            BusinessService _business = (BusinessService) com.alibaba.android.arouter.launcher.a.c().g(BusinessService.class);
            if (_business != null) {
                FeedbackRequestBean _feedbackRequestData = new FeedbackRequestBean();
                FeedbackDoneParamsBean feedbackDoneParamsBean = _feedbackRequestData.done.params;
                feedbackDoneParamsBean.content = "一键反馈：IPC回放漏报事件";
                feedbackDoneParamsBean.feedbackType = 7;
                feedbackDoneParamsBean.feedbackSecondType = 22;
                feedbackDoneParamsBean.occurredTime = System.currentTimeMillis();
                FeedbackDoneParamsBean feedbackDoneParamsBean2 = _feedbackRequestData.done.params;
                feedbackDoneParamsBean2.prePage = "云回看";
                feedbackDoneParamsBean2.prePageTime = System.currentTimeMillis() + "";
                String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
                FeedbackDoneBean feedbackDoneBean = _feedbackRequestData.done;
                feedbackDoneBean.url = baseUrl + "/feedback";
                ArrayList<String> arrayList = _feedbackRequestData.done.params.deviceIds;
                arrayList.add(this.p + "");
                ((z5) m()).b();
                _business.reportIssues(_feedbackRequestData, new a());
            }
        }
    }

    /* compiled from: CloudPlaybackPresenter */
    public class a implements BusinessService.UploadCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void success(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1956, new Class[]{Object.class}, Void.TYPE).isSupported) {
                ((z5) y5.this.m()).a();
                ((z5) y5.this.m()).showToast(R$string.lds_report_issue_success);
            }
        }

        public void fail(Object obj, Object obj2) {
            Class<Object> cls = Object.class;
            Class[] clsArr = {cls, cls};
            if (!PatchProxy.proxy(new Object[]{obj, obj2}, this, changeQuickRedirect, false, 1957, clsArr, Void.TYPE).isSupported) {
                ((z5) y5.this.m()).a();
                ((z5) y5.this.m()).showToast(R$string.lds_report_issue_success);
            }
        }
    }
}
