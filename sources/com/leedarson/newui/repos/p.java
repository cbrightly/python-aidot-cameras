package com.leedarson.newui.repos;

import android.text.TextUtils;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.bean.CalendarData;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.e;
import io.reactivex.f;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: EventsHasVideoCheckRepos */
public class p {
    public static ChangeQuickRedirect changeQuickRedirect;

    public e<String> a(String deviceIds, List<CalendarData> currentWeekDatas, String eventCodes, String eventType) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceIds, currentWeekDatas, eventCodes, eventType}, this, changeQuickRedirect, false, 4449, new Class[]{cls, List.class, cls, cls}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        return e.d(new l(this, currentWeekDatas, deviceIds, eventCodes, eventType), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public /* synthetic */ void d(List list, String str, String str2, String str3, f fVar) {
        JSONObject headerJson;
        JSONObject paramsJson;
        String tempEventCodes;
        String tempEventCodes2;
        Class<String> cls = String.class;
        Class[] clsArr = {List.class, cls, cls, cls, f.class};
        if (!PatchProxy.proxy(new Object[]{list, str, str2, str3, fVar}, this, changeQuickRedirect, false, 4452, clsArr, Void.TYPE).isSupported) {
            String deviceIds = str;
            String eventType = str3;
            List currentWeekDatas = list;
            String eventCodes = str2;
            f emitter = fVar;
            CalendarData startDay = (CalendarData) currentWeekDatas.get(0);
            CalendarData endDay = (CalendarData) currentWeekDatas.get(currentWeekDatas.size() - 1);
            String start = com.leedarson.utils.e.e(startDay);
            String end = com.leedarson.utils.e.e(endDay);
            long startTime = com.leedarson.utils.e.b(start + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            long endTime = com.leedarson.utils.e.b(end + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
            JSONObject headerJson2 = new JSONObject();
            JSONObject paramsJson2 = new JSONObject();
            CalendarData calendarData = startDay;
            CalendarData calendarData2 = endDay;
            String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
            String str4 = start;
            String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
            try {
                if (!TextUtils.isEmpty(accessToken)) {
                    headerJson = headerJson2;
                    try {
                        headerJson.put("owner", (Object) owner);
                        headerJson.put("token", (Object) accessToken);
                    } catch (JSONException e) {
                        e = e;
                        paramsJson = paramsJson2;
                        String str5 = deviceIds;
                    }
                } else {
                    headerJson = headerJson2;
                }
                try {
                    headerJson.put("terminal", (Object) "app");
                    paramsJson = paramsJson2;
                } catch (JSONException e2) {
                    e = e2;
                    paramsJson = paramsJson2;
                    String str6 = deviceIds;
                    e.printStackTrace();
                    b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, baseUrl + "/api/ipc/playback/eventDates", headerJson.toString(), paramsJson.toString(), new a(emitter));
                }
                try {
                    paramsJson.put("startTime", startTime);
                    paramsJson.put("endTime", endTime);
                    paramsJson.put("deviceIds", (Object) deviceIds);
                    String tempEventCodes3 = eventCodes;
                    if (!com.alibaba.android.arouter.utils.e.b(eventType)) {
                        tempEventCodes2 = tempEventCodes3;
                        if ("pet".equals(eventType)) {
                            if (com.alibaba.android.arouter.utils.e.b(eventCodes)) {
                                tempEventCodes = "25";
                                String str7 = deviceIds;
                            } else {
                                StringBuilder sb = new StringBuilder();
                                sb.append(eventCodes);
                                String str8 = deviceIds;
                                try {
                                    sb.append(",25");
                                    tempEventCodes = sb.toString();
                                } catch (JSONException e3) {
                                    e = e3;
                                    e.printStackTrace();
                                    b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, baseUrl + "/api/ipc/playback/eventDates", headerJson.toString(), paramsJson.toString(), new a(emitter));
                                }
                            }
                            paramsJson.put("eventCodes", (Object) tempEventCodes);
                            b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, baseUrl + "/api/ipc/playback/eventDates", headerJson.toString(), paramsJson.toString(), new a(emitter));
                        }
                    } else {
                        tempEventCodes2 = tempEventCodes3;
                        String str9 = deviceIds;
                    }
                    tempEventCodes = tempEventCodes2;
                    paramsJson.put("eventCodes", (Object) tempEventCodes);
                } catch (JSONException e4) {
                    e = e4;
                    String str10 = deviceIds;
                    e.printStackTrace();
                    b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, baseUrl + "/api/ipc/playback/eventDates", headerJson.toString(), paramsJson.toString(), new a(emitter));
                }
            } catch (JSONException e5) {
                e = e5;
                headerJson = headerJson2;
                paramsJson = paramsJson2;
                String str11 = deviceIds;
                e.printStackTrace();
                b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, baseUrl + "/api/ipc/playback/eventDates", headerJson.toString(), paramsJson.toString(), new a(emitter));
            }
            b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, baseUrl + "/api/ipc/playback/eventDates", headerJson.toString(), paramsJson.toString(), new a(emitter));
        }
    }

    /* compiled from: EventsHasVideoCheckRepos */
    public class a extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ f c;

        a(f fVar) {
            this.c = fVar;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4456, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 4453, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getEventDates").a("getEventDates", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 4454, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("getEventDates");
                g.c("error=" + e.getMsg(), new Object[0]);
                this.c.onError(e);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 4455, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getEventDates onSuccess").c(response, new Object[0]);
                this.c.onNext(response);
                this.c.onComplete();
            }
        }
    }

    public e<String> b(String deviceIds, String eventCodes, com.ldf.calendar.model.a seedDate) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceIds, eventCodes, seedDate}, this, changeQuickRedirect, false, 4450, new Class[]{cls, cls, com.ldf.calendar.model.a.class}, e.class);
        return proxy.isSupported ? (e) proxy.result : e.d(new k(this, seedDate, deviceIds, eventCodes), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public /* synthetic */ void f(com.ldf.calendar.model.a aVar, String str, String str2, f fVar) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{aVar, str, str2, fVar}, this, changeQuickRedirect, false, 4451, new Class[]{com.ldf.calendar.model.a.class, cls, cls, f.class}, Void.TYPE).isSupported) {
            String deviceIds = str;
            f emitter = fVar;
            com.ldf.calendar.model.a seedDate = aVar;
            String eventCodes = str2;
            Locale locale = Locale.US;
            String startDay = String.format(locale, "%s-%s-%s", new Object[]{Integer.valueOf(seedDate.year), Integer.valueOf(seedDate.month), "01"});
            String endDay = String.format(locale, "%s-%s-%s", new Object[]{Integer.valueOf(seedDate.year), Integer.valueOf(seedDate.month), "31"});
            long start = com.leedarson.utils.e.b(startDay + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            long end = com.leedarson.utils.e.b(endDay + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
            String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
            JSONObject headerJson = new JSONObject();
            JSONObject paramsJson = new JSONObject();
            String str3 = startDay;
            String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
            String str4 = endDay;
            String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
            try {
                if (!TextUtils.isEmpty(accessToken)) {
                    headerJson.put("owner", (Object) owner);
                    headerJson.put("token", (Object) accessToken);
                }
                headerJson.put("terminal", (Object) "app");
                paramsJson.put("startTime", start);
                paramsJson.put("endTime", end);
                paramsJson.put("deviceIds", (Object) deviceIds);
                paramsJson.put("eventCodes", (Object) eventCodes);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, baseUrl + "/api/ipc/playback/eventDates", headerJson.toString(), paramsJson.toString(), new b(emitter));
        }
    }

    /* compiled from: EventsHasVideoCheckRepos */
    public class b extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ f c;

        b(f fVar) {
            this.c = fVar;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4459, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 4457, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                this.c.onError(e);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 4458, new Class[]{String.class}, Void.TYPE).isSupported) {
                this.c.onNext(response);
                this.c.onComplete();
            }
        }
    }
}
