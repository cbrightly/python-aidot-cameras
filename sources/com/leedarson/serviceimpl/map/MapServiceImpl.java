package com.leedarson.serviceimpl.map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.libraries.places.api.model.PlaceTypes;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.utils.w;
import com.leedarson.serviceimpl.map.b;
import com.leedarson.serviceinterface.MapService;
import com.leedarson.serviceinterface.ThirdMapService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.listener.OnRequestListener;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import okhttp3.b0;
import okhttp3.d0;
import okhttp3.f;
import okhttp3.z;
import org.json.JSONException;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.a;

public class MapServiceImpl implements MapService {
    public static ChangeQuickRedirect changeQuickRedirect;
    Context a;
    /* access modifiers changed from: private */
    public String b;
    /* access modifiers changed from: private */
    public String c;
    private String d;
    /* access modifiers changed from: private */
    public String e = "";
    /* access modifiers changed from: private */
    public String f = "";
    /* access modifiers changed from: private */
    public String g = "";
    private LocationManager h;
    private boolean i = true;
    private ExecutorService j = l.i(1, "mapServiceImpl", 1);
    private Future k;
    private Future l;

    static /* synthetic */ String n(MapServiceImpl x0, double x1, double x2) {
        Object[] objArr = {x0, new Double(x1), new Double(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Double.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 8177, new Class[]{MapServiceImpl.class, cls, cls}, String.class);
        return proxy.isSupported ? (String) proxy.result : x0.w(x1, x2);
    }

    static /* synthetic */ Location o(MapServiceImpl x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 8178, new Class[]{MapServiceImpl.class}, Location.class);
        return proxy.isSupported ? (Location) proxy.result : x0.v();
    }

    static /* synthetic */ void q(MapServiceImpl x0, double x1, double x2) {
        Object[] objArr = {x0, new Double(x1), new Double(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Double.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 8179, new Class[]{MapServiceImpl.class, cls, cls}, Void.TYPE).isSupported) {
            x0.y(x1, x2);
        }
    }

    static /* synthetic */ void r(MapServiceImpl x0, Location x1) {
        Class[] clsArr = {MapServiceImpl.class, Location.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 8180, clsArr, Void.TYPE).isSupported) {
            x0.u(x1);
        }
    }

    static /* synthetic */ boolean s(MapServiceImpl x0, Context x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 8181, new Class[]{MapServiceImpl.class, Context.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.t(x1);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00cb, code lost:
        if (r9.equals("getLocatoinInfo") != false) goto L_0x00cf;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(java.lang.String r18, android.app.Activity r19, java.lang.String r20, java.lang.String r21) {
        /*
            r17 = this;
            java.lang.String r0 = "longitude"
            java.lang.String r1 = "latitude"
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r3 = 4
            java.lang.Object[] r4 = new java.lang.Object[r3]
            r11 = 0
            r4[r11] = r18
            r12 = 1
            r4[r12] = r19
            r13 = 2
            r4[r13] = r20
            r14 = 3
            r4[r14] = r21
            com.meituan.robust.ChangeQuickRedirect r6 = changeQuickRedirect
            java.lang.Class[] r9 = new java.lang.Class[r3]
            r9[r11] = r2
            java.lang.Class<android.app.Activity> r5 = android.app.Activity.class
            r9[r12] = r5
            r9[r13] = r2
            r9[r14] = r2
            java.lang.Class r10 = java.lang.Void.TYPE
            r7 = 0
            r8 = 8162(0x1fe2, float:1.1437E-41)
            r5 = r17
            com.meituan.robust.PatchProxyResult r2 = com.meituan.robust.PatchProxy.proxy(r4, r5, r6, r7, r8, r9, r10)
            boolean r2 = r2.isSupported
            if (r2 == 0) goto L_0x0033
            return
        L_0x0033:
            r2 = r17
            r15 = r19
            r10 = r21
            r8 = r18
            r9 = r20
            java.lang.String r4 = "MapServiceImpl"
            timber.log.a$b r4 = timber.log.a.g(r4)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "handleData: "
            r5.append(r6)
            r5.append(r9)
            java.lang.String r6 = " data:"
            r5.append(r6)
            r5.append(r10)
            java.lang.String r6 = " callbackKey:"
            r5.append(r6)
            r5.append(r8)
            java.lang.String r5 = r5.toString()
            java.lang.Object[] r6 = new java.lang.Object[r11]
            r4.h(r5, r6)
            r4 = -1
            int r5 = r9.hashCode()
            r6 = 8
            switch(r5) {
                case -1868793787: goto L_0x00c5;
                case -1263211854: goto L_0x00bb;
                case -924085037: goto L_0x00b1;
                case -815366715: goto L_0x00a7;
                case -632043555: goto L_0x009d;
                case -603780584: goto L_0x0093;
                case -140429234: goto L_0x0089;
                case 1567893625: goto L_0x007f;
                case 1901043637: goto L_0x0075;
                default: goto L_0x0073;
            }
        L_0x0073:
            goto L_0x00ce
        L_0x0075:
            java.lang.String r3 = "location"
            boolean r3 = r9.equals(r3)
            if (r3 == 0) goto L_0x0073
            r3 = r14
            goto L_0x00cf
        L_0x007f:
            java.lang.String r3 = "getLocationInfo"
            boolean r3 = r9.equals(r3)
            if (r3 == 0) goto L_0x0073
            r3 = 5
            goto L_0x00cf
        L_0x0089:
            java.lang.String r3 = "currentLocation"
            boolean r3 = r9.equals(r3)
            if (r3 == 0) goto L_0x0073
            r3 = r12
            goto L_0x00cf
        L_0x0093:
            java.lang.String r3 = "getCurrentLocation"
            boolean r3 = r9.equals(r3)
            if (r3 == 0) goto L_0x0073
            r3 = r11
            goto L_0x00cf
        L_0x009d:
            java.lang.String r3 = "currentCountry"
            boolean r3 = r9.equals(r3)
            if (r3 == 0) goto L_0x0073
            r3 = 7
            goto L_0x00cf
        L_0x00a7:
            java.lang.String r3 = "getPermission"
            boolean r3 = r9.equals(r3)
            if (r3 == 0) goto L_0x0073
            r3 = r6
            goto L_0x00cf
        L_0x00b1:
            java.lang.String r3 = "getCurrentCountry"
            boolean r3 = r9.equals(r3)
            if (r3 == 0) goto L_0x0073
            r3 = 6
            goto L_0x00cf
        L_0x00bb:
            java.lang.String r3 = "openMap"
            boolean r3 = r9.equals(r3)
            if (r3 == 0) goto L_0x0073
            r3 = r13
            goto L_0x00cf
        L_0x00c5:
            java.lang.String r5 = "getLocatoinInfo"
            boolean r5 = r9.equals(r5)
            if (r5 == 0) goto L_0x0073
            goto L_0x00cf
        L_0x00ce:
            r3 = r4
        L_0x00cf:
            java.lang.String r4 = "code"
            java.lang.String r5 = "data"
            java.lang.String r7 = ""
            switch(r3) {
                case 0: goto L_0x0207;
                case 1: goto L_0x0207;
                case 2: goto L_0x01db;
                case 3: goto L_0x01db;
                case 4: goto L_0x016e;
                case 5: goto L_0x016e;
                case 6: goto L_0x0147;
                case 7: goto L_0x0147;
                case 8: goto L_0x00de;
                default: goto L_0x00d8;
            }
        L_0x00d8:
            r1 = r8
            r16 = r9
            r12 = r10
            goto L_0x0250
        L_0x00de:
            r1 = 1
            r3 = -1
            r6 = 1
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00ee }
            r0.<init>((java.lang.String) r10)     // Catch:{ JSONException -> 0x00ee }
            java.lang.String r7 = "autoRequestPermission"
            boolean r7 = r0.optBoolean(r7, r12)     // Catch:{ JSONException -> 0x00ee }
            r1 = r7
            goto L_0x00f2
        L_0x00ee:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00f2:
            if (r1 != 0) goto L_0x0133
            int[] r7 = r2.x(r15)
            r3 = r7[r11]
            r6 = r7[r12]
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x0129 }
            r0.<init>()     // Catch:{ Exception -> 0x0129 }
            r11 = 200(0xc8, float:2.8E-43)
            r0.put((java.lang.String) r4, (int) r11)     // Catch:{ Exception -> 0x0129 }
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x0129 }
            r4.<init>()     // Catch:{ Exception -> 0x0129 }
            java.lang.String r11 = "status"
            r4.put((java.lang.String) r11, (int) r3)     // Catch:{ Exception -> 0x0129 }
            java.lang.String r11 = "precise"
            r4.put((java.lang.String) r11, (int) r6)     // Catch:{ Exception -> 0x0129 }
            r0.put((java.lang.String) r5, (java.lang.Object) r4)     // Catch:{ Exception -> 0x0129 }
            org.greenrobot.eventbus.c r5 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x0129 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r11 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ Exception -> 0x0129 }
            java.lang.String r12 = r0.toString()     // Catch:{ Exception -> 0x0129 }
            r11.<init>(r8, r12)     // Catch:{ Exception -> 0x0129 }
            r5.l(r11)     // Catch:{ Exception -> 0x0129 }
            goto L_0x012d
        L_0x0129:
            r0 = move-exception
            r0.printStackTrace()
        L_0x012d:
            r1 = r8
            r16 = r9
            r12 = r10
            goto L_0x0250
        L_0x0133:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.NeedPermissionEvent r4 = new com.leedarson.serviceinterface.event.NeedPermissionEvent
            r5 = 71
            r4.<init>(r5, r8)
            r0.l(r4)
            r1 = r8
            r16 = r9
            r12 = r10
            goto L_0x0250
        L_0x0147:
            r2.c = r8
            r2.e = r7
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.NeedPermissionEvent r1 = new com.leedarson.serviceinterface.event.NeedPermissionEvent
            java.lang.String r3 = r2.c
            r1.<init>(r6, r3)
            r0.l(r1)
            android.os.Handler r0 = new android.os.Handler
            r0.<init>()
            com.leedarson.serviceimpl.map.MapServiceImpl$a r1 = new com.leedarson.serviceimpl.map.MapServiceImpl$a
            r1.<init>()
            r3 = 3000(0xbb8, double:1.482E-320)
            r0.postDelayed(r1, r3)
            r1 = r8
            r16 = r9
            r12 = r10
            goto L_0x0250
        L_0x016e:
            r3 = 0
            r4 = 0
            com.google.gson.internal.LinkedTreeMap r11 = com.leedarson.serviceimpl.map.e.b(r10)
            if (r11 == 0) goto L_0x01ab
            boolean r5 = r11.containsKey(r1)     // Catch:{ NumberFormatException -> 0x01a5 }
            if (r5 == 0) goto L_0x018d
            java.lang.Object r1 = r11.get(r1)     // Catch:{ NumberFormatException -> 0x01a5 }
            java.lang.String r1 = r1.toString()     // Catch:{ NumberFormatException -> 0x01a5 }
            java.lang.Float r1 = java.lang.Float.valueOf(r1)     // Catch:{ NumberFormatException -> 0x01a5 }
            float r1 = r1.floatValue()     // Catch:{ NumberFormatException -> 0x01a5 }
            r3 = r1
        L_0x018d:
            boolean r1 = r11.containsKey(r0)     // Catch:{ NumberFormatException -> 0x01a5 }
            if (r1 == 0) goto L_0x01ab
            java.lang.Object r0 = r11.get(r0)     // Catch:{ NumberFormatException -> 0x01a5 }
            java.lang.String r0 = r0.toString()     // Catch:{ NumberFormatException -> 0x01a5 }
            java.lang.Float r0 = java.lang.Float.valueOf(r0)     // Catch:{ NumberFormatException -> 0x01a5 }
            float r0 = r0.floatValue()     // Catch:{ NumberFormatException -> 0x01a5 }
            r4 = r0
            goto L_0x01ab
        L_0x01a5:
            r0 = move-exception
            r0.printStackTrace()
            r0 = r4
            goto L_0x01ac
        L_0x01ab:
            r0 = r4
        L_0x01ac:
            boolean r1 = r2.i
            if (r1 == 0) goto L_0x01bf
            double r6 = (double) r3
            double r12 = (double) r0
            r4 = r2
            r5 = r15
            r1 = r8
            r16 = r9
            r8 = r12
            r12 = r10
            r10 = r1
            r4.getLocationInfo(r5, r6, r8, r10)
            goto L_0x0250
        L_0x01bf:
            r1 = r8
            r16 = r9
            r12 = r10
            com.alibaba.android.arouter.launcher.a r4 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.ThirdMapService> r5 = com.leedarson.serviceinterface.ThirdMapService.class
            java.lang.Object r4 = r4.g(r5)
            com.leedarson.serviceinterface.ThirdMapService r4 = (com.leedarson.serviceinterface.ThirdMapService) r4
            if (r4 == 0) goto L_0x01d9
            android.content.Context r5 = r2.a
            r4.init(r5)
            r4.getAddressInfo(r1, r3, r0)
        L_0x01d9:
            goto L_0x0250
        L_0x01db:
            r1 = r8
            r16 = r9
            r12 = r10
            r2.d = r1
            boolean r0 = r2.i
            r3 = 688(0x2b0, float:9.64E-43)
            if (r0 == 0) goto L_0x01f5
            android.content.Intent r0 = new android.content.Intent
            java.lang.Class<com.leedarson.serviceimpl.map.GoogleMapActivity> r4 = com.leedarson.serviceimpl.map.GoogleMapActivity.class
            r0.<init>(r15, r4)
            r0.putExtra(r5, r12)
            r15.startActivityForResult(r0, r3)
            goto L_0x0250
        L_0x01f5:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.String r4 = "/third/thirdmap"
            com.alibaba.android.arouter.facade.a r0 = r0.a(r4)
            com.alibaba.android.arouter.facade.a r0 = r0.T(r5, r12)
            r0.F(r15, r3)
            goto L_0x0250
        L_0x0207:
            r1 = r8
            r16 = r9
            r12 = r10
            r2.b = r1
            r2.e = r7
            java.lang.String r0 = "android.permission.ACCESS_COARSE_LOCATION"
            java.lang.String r3 = "android.permission.ACCESS_FINE_LOCATION"
            java.lang.String[] r0 = new java.lang.String[]{r0, r3}
            r3 = r0
            android.content.Context r0 = r2.a
            boolean r0 = pub.devrel.easypermissions.EasyPermissions.a(r0, r3)
            if (r0 == 0) goto L_0x022d
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.NeedPermissionEvent r4 = new com.leedarson.serviceinterface.event.NeedPermissionEvent
            r4.<init>(r14, r7)
            r0.l(r4)
            goto L_0x0250
        L_0x022d:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r5 = r0
            r0 = 401(0x191, float:5.62E-43)
            r5.put((java.lang.String) r4, (int) r0)     // Catch:{ JSONException -> 0x0239 }
            goto L_0x023f
        L_0x0239:
            r0 = move-exception
            r4 = r0
            r0 = r4
            r0.printStackTrace()
        L_0x023f:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r4 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            java.lang.String r6 = r5.toString()
            r4.<init>(r1, r6)
            r0.l(r4)
        L_0x0250:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.map.MapServiceImpl.handleData(java.lang.String, android.app.Activity, java.lang.String, java.lang.String):void");
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8182, new Class[0], Void.TYPE).isSupported) {
                try {
                    JSONObject obj = new JSONObject();
                    String code = Locale.getDefault().getCountry();
                    String name = Locale.getDefault().getDisplayName();
                    JSONObject joData = new JSONObject();
                    joData.put("countryName", (Object) code);
                    joData.put("countryCode", (Object) name);
                    obj.put("code", 200);
                    obj.put("data", (Object) joData);
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(MapServiceImpl.this.c, obj.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int[] x(Activity activity) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 8163, new Class[]{Activity.class}, int[].class);
        if (proxy.isSupported) {
            return (int[]) proxy.result;
        }
        int[] ret = {-1, 1};
        if (Build.VERSION.SDK_INT < 23) {
            ret[0] = 4;
            return ret;
        } else if (!w.f(this.a)) {
            return ret;
        } else {
            if (EasyPermissions.a(this.a, "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION")) {
                ret[0] = 4;
            } else if (!SharePreferenceUtils.getPrefBoolean(this.a, "location_permission_denied", false)) {
                ret[0] = 1;
            } else if (EasyPermissions.h(activity, "android.permission.ACCESS_COARSE_LOCATION")) {
                ret[0] = 1;
            } else {
                ret[0] = 2;
            }
            return ret;
        }
    }

    public void getLocationInfo(Activity activity, double d2, double d3, String str) {
        Object[] objArr = {activity, new Double(d2), new Double(d3), str};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Double.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8164, new Class[]{Activity.class, cls, cls, String.class}, Void.TYPE).isSupported) {
            double lat = d2;
            String callbackKey = str;
            double lon = d3;
            Geocoder geocoder = new Geocoder(activity);
            Future future = this.l;
            if (future != null && !future.isCancelled()) {
                this.l.cancel(true);
            }
            this.l = this.j.submit(new b(geocoder, lat, lon, callbackKey));
        }
    }

    public class b implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Geocoder c;
        final /* synthetic */ double d;
        final /* synthetic */ double f;
        final /* synthetic */ String q;

        b(Geocoder geocoder, double d2, double d3, String str) {
            this.c = geocoder;
            this.d = d2;
            this.f = d3;
            this.q = str;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8183, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            try {
                List<Address> addresses = this.c.getFromLocation(this.d, this.f, 1);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    String unused = MapServiceImpl.this.e = address.getAddressLine(0);
                    try {
                        MapServiceImpl mapServiceImpl = MapServiceImpl.this;
                        String unused2 = mapServiceImpl.e = mapServiceImpl.e.substring(0, MapServiceImpl.this.e.indexOf("邮政编码"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!(!TextUtils.isEmpty(MapServiceImpl.this.e) || address.getAdminArea() == null || address.getLocality() == null || address.getFeatureName() == null)) {
                        String unused3 = MapServiceImpl.this.e = String.format(Locale.US, "%s, %s, %s", new Object[]{address.getAdminArea(), address.getLocality(), address.getFeatureName()});
                    }
                    if (address.getCountryCode() != null) {
                        String unused4 = MapServiceImpl.this.f = address.getCountryCode();
                    }
                    if (address.getCountryName() != null) {
                        String unused5 = MapServiceImpl.this.g = address.getCountryName();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            if (TextUtils.isEmpty(MapServiceImpl.this.e)) {
                return null;
            }
            String GMT = e.a(this.f);
            if (SharePreferenceUtils.getPrefBoolean(MapServiceImpl.this.a, "is_new_protocol", false)) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("code", 200);
                    JSONObject dataObj = new JSONObject();
                    dataObj.put("timezoneOffset", (Object) GMT);
                    dataObj.put("countryName", (Object) MapServiceImpl.this.g);
                    dataObj.put("countryCode", (Object) MapServiceImpl.this.f);
                    dataObj.put(PlaceTypes.ADDRESS, (Object) MapServiceImpl.this.e);
                    dataObj.put("timezoneName", (Object) MapServiceImpl.n(MapServiceImpl.this, this.d, this.f));
                    jsonObject.put("data", (Object) dataObj);
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.q, jsonObject.toString()));
                    return null;
                } catch (JSONException e3) {
                    e3.printStackTrace();
                    return null;
                }
            } else {
                org.greenrobot.eventbus.c c2 = org.greenrobot.eventbus.c.c();
                String str = this.q;
                c2.l(new JsBridgeCallbackEvent(str, "{\"code\":200,\"latitude\":" + this.d + ",\"countryCode\":\"" + MapServiceImpl.this.f + "\",\"longitude\":" + this.f + ",\"timezone\":\"" + GMT + "\",\"detail\":\"" + MapServiceImpl.this.e + "\"}"));
                return null;
            }
        }
    }

    public void getLocation(Context activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 8165, new Class[]{Context.class}, Void.TYPE).isSupported) {
            if (this.i) {
                Future future = this.k;
                if (future != null && !future.isCancelled()) {
                    this.k.cancel(true);
                }
                this.k = this.j.submit(new c(activity));
                return;
            }
            ThirdMapService thirdMapService = (ThirdMapService) com.alibaba.android.arouter.launcher.a.c().g(ThirdMapService.class);
            if (thirdMapService != null) {
                thirdMapService.init(this.a);
                thirdMapService.locale(this.b);
            }
        }
    }

    public class c implements Callable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Context c;

        c(Context context) {
            this.c = context;
        }

        public Object call() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8184, new Class[0], Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            Location location = MapServiceImpl.o(MapServiceImpl.this);
            if (location != null) {
                Geocoder geocoder = new Geocoder(this.c);
                if (Geocoder.isPresent()) {
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (addresses.size() > 0) {
                            Address address = addresses.get(0);
                            String unused = MapServiceImpl.this.e = address.getAddressLine(0);
                            try {
                                MapServiceImpl mapServiceImpl = MapServiceImpl.this;
                                String unused2 = mapServiceImpl.e = mapServiceImpl.e.substring(0, MapServiceImpl.this.e.indexOf("邮政编码"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (!(!TextUtils.isEmpty(MapServiceImpl.this.e) || address.getAdminArea() == null || address.getLocality() == null || address.getFeatureName() == null)) {
                                String unused3 = MapServiceImpl.this.e = String.format(Locale.US, "%s, %s, %s", new Object[]{address.getAdminArea(), address.getLocality(), address.getFeatureName()});
                            }
                            if (address.getCountryCode() != null) {
                                String unused4 = MapServiceImpl.this.f = address.getCountryCode();
                            }
                            if (address.getCountryName() != null) {
                                String unused5 = MapServiceImpl.this.g = address.getCountryName();
                            }
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    if (!TextUtils.isEmpty(MapServiceImpl.this.e)) {
                        String GMT = e.a(location.getLongitude());
                        if (MapServiceImpl.this.b == null) {
                            return null;
                        }
                        if (SharePreferenceUtils.getPrefBoolean(MapServiceImpl.this.a, "is_new_protocol", false)) {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("code", 200);
                                JSONObject dataObj = new JSONObject();
                                dataObj.put("latitude", location.getLatitude());
                                dataObj.put("longitude", location.getLongitude());
                                dataObj.put("timezoneOffset", (Object) GMT);
                                dataObj.put("countryName", (Object) MapServiceImpl.this.g);
                                dataObj.put("countryCode", (Object) MapServiceImpl.this.f);
                                dataObj.put(PlaceTypes.ADDRESS, (Object) MapServiceImpl.this.e);
                                dataObj.put("timezoneName", (Object) MapServiceImpl.n(MapServiceImpl.this, location.getLatitude(), location.getLongitude()));
                                jsonObject.put("data", (Object) dataObj);
                                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(MapServiceImpl.this.b, jsonObject.toString()));
                                MapServiceImpl.q(MapServiceImpl.this, location.getLongitude(), location.getLatitude());
                            } catch (JSONException e3) {
                                e3.printStackTrace();
                            }
                        } else {
                            org.greenrobot.eventbus.c c2 = org.greenrobot.eventbus.c.c();
                            String p = MapServiceImpl.this.b;
                            c2.l(new JsBridgeCallbackEvent(p, "{\"code\":200,\"desc\":\"\",\"latitude\":" + location.getLatitude() + ",\"countryCode\":\"" + MapServiceImpl.this.f + "\",\"longitude\":" + location.getLongitude() + ",\"timezone\":\"" + GMT + "\",\"detail\":\"" + MapServiceImpl.this.e + "\"}"));
                        }
                    } else {
                        MapServiceImpl.r(MapServiceImpl.this, location);
                    }
                } else {
                    MapServiceImpl.r(MapServiceImpl.this, location);
                }
            } else {
                MapServiceImpl.s(MapServiceImpl.this, this.c);
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(MapServiceImpl.this.b, "{\"code\":1000,\"desc\":\"get location Fail:has no last location\"}"));
            }
            return null;
        }
    }

    public void getCountry(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 8166, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            Activity activity2 = activity;
            Location location = v();
            if (location != null) {
                Geocoder geocoder = new Geocoder(activity2);
                if (Geocoder.isPresent()) {
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (addresses.size() > 0) {
                            Address address = addresses.get(0);
                            String addressLine = address.getAddressLine(0);
                            this.e = addressLine;
                            try {
                                this.e = addressLine.substring(0, addressLine.indexOf("邮政编码"));
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                            if (!(!TextUtils.isEmpty(this.e) || address.getAdminArea() == null || address.getLocality() == null || address.getFeatureName() == null)) {
                                this.e = String.format(Locale.US, "%s, %s, %s", new Object[]{address.getAdminArea(), address.getLocality(), address.getFeatureName()});
                            }
                            if (address.getCountryCode() != null) {
                                this.f = address.getCountryCode();
                            }
                            if (address.getCountryName() != null) {
                                this.g = address.getCountryName();
                            }
                        }
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    if (!TextUtils.isEmpty(this.e)) {
                        org.greenrobot.eventbus.c c2 = org.greenrobot.eventbus.c.c();
                        String str = this.c;
                        c2.l(new JsBridgeCallbackEvent(str, "{\"code\":200,\"countryName\":\"" + this.g + "\",\"countryCode\":\"" + this.f + "\"}"));
                        return;
                    }
                    String code = Locale.getDefault().getCountry();
                    String name = Locale.getDefault().getDisplayName();
                    org.greenrobot.eventbus.c c3 = org.greenrobot.eventbus.c.c();
                    String str2 = this.c;
                    c3.l(new JsBridgeCallbackEvent(str2, "{\"code\":200,\"countryName\":\"" + name + "\",\"countryCode\":\"" + code + "\"}"));
                    return;
                }
                String code2 = Locale.getDefault().getCountry();
                String name2 = Locale.getDefault().getDisplayName();
                org.greenrobot.eventbus.c c4 = org.greenrobot.eventbus.c.c();
                String str3 = this.c;
                c4.l(new JsBridgeCallbackEvent(str3, "{\"code\":200,\"countryName\":\"" + name2 + "\",\"countryCode\":\"" + code2 + "\"}"));
                return;
            }
            String code3 = Locale.getDefault().getCountry();
            String name3 = Locale.getDefault().getDisplayName();
            org.greenrobot.eventbus.c c5 = org.greenrobot.eventbus.c.c();
            String str4 = this.c;
            c5.l(new JsBridgeCallbackEvent(str4, "{\"code\":200,\"countryName\":\"" + name3 + "\",\"countryCode\":\"" + code3 + "\"}"));
        }
    }

    private void u(Location location) {
        if (!PatchProxy.proxy(new Object[]{location}, this, changeQuickRedirect, false, 8167, new Class[]{Location.class}, Void.TYPE).isSupported) {
            String GMT = e.a(location.getLongitude());
            new z.a().c().a(new b0.a().e().p(String.format(Locale.US, "https://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&key=%s&language=en-US", new Object[]{Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude()), this.a.getString(R$string.maps_api_key)})).b()).o0(new d(location, GMT));
        }
    }

    public class d implements f {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Location c;
        final /* synthetic */ String d;

        d(Location location, String str) {
            this.c = location;
            this.d = str;
        }

        public void onFailure(okhttp3.e eVar, IOException iOException) {
            Class[] clsArr = {okhttp3.e.class, IOException.class};
            if (!PatchProxy.proxy(new Object[]{eVar, iOException}, this, changeQuickRedirect, false, 8185, clsArr, Void.TYPE).isSupported) {
                if (MapServiceImpl.this.b != null) {
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(MapServiceImpl.this.b, "{\"code\":1000,\"desc\":\"get location Fail\"}"));
                }
            }
        }

        public void onResponse(okhttp3.e eVar, d0 response) {
            if (!PatchProxy.proxy(new Object[]{eVar, response}, this, changeQuickRedirect, false, 8186, new Class[]{okhttp3.e.class, d0.class}, Void.TYPE).isSupported) {
                try {
                    String s = response.a().string();
                    MapServiceImpl mapServiceImpl = MapServiceImpl.this;
                    String unused = mapServiceImpl.e = ((LinkedTreeMap) ((ArrayList) ((Map) new Gson().fromJson(s, HashMap.class)).get("results")).get(0)).get("formatted_address") + "";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (MapServiceImpl.this.b != null) {
                    if (SharePreferenceUtils.getPrefBoolean(MapServiceImpl.this.a, "is_new_protocol", false)) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("code", 200);
                            JSONObject dataObj = new JSONObject();
                            dataObj.put("latitude", this.c.getLatitude());
                            dataObj.put("longitude", this.c.getLongitude());
                            dataObj.put("timezoneOffset", (Object) this.d);
                            dataObj.put("countryName", (Object) "");
                            dataObj.put("countryCode", (Object) MapServiceImpl.this.f);
                            dataObj.put(PlaceTypes.ADDRESS, (Object) MapServiceImpl.this.e);
                            jsonObject.put("data", (Object) dataObj);
                            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(MapServiceImpl.this.b, jsonObject.toString()));
                            MapServiceImpl.q(MapServiceImpl.this, this.c.getLongitude(), this.c.getLatitude());
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    } else {
                        org.greenrobot.eventbus.c c2 = org.greenrobot.eventbus.c.c();
                        String p = MapServiceImpl.this.b;
                        c2.l(new JsBridgeCallbackEvent(p, "{\"code\":200,\"desc\":\"\",\"latitude\":" + this.c.getLatitude() + ",\"longitude\":" + this.c.getLongitude() + ",\"countryCode\":\"" + MapServiceImpl.this.f + "\",\"timezone\":\"" + this.d + "\",\"detail\":\"" + MapServiceImpl.this.e + "\"}"));
                    }
                }
            }
        }
    }

    private boolean t(Context activity) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 8168, new Class[]{Context.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String provider = Settings.Secure.getString(activity.getContentResolver(), "location_providers_allowed");
        return provider.contains("gps") || provider.contains("network");
    }

    @SuppressLint({"MissingPermission"})
    private Location v() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8169, new Class[0], Location.class);
        if (proxy.isSupported) {
            return (Location) proxy.result;
        }
        Location bestLocation = null;
        try {
            LocationManager locationManager = (LocationManager) this.a.getSystemService(FirebaseAnalytics.Param.LOCATION);
            this.h = locationManager;
            bestLocation = null;
            for (String provider : locationManager.getProviders(true)) {
                Location l2 = this.h.getLastKnownLocation(provider);
                if (l2 != null) {
                    if (bestLocation == null || l2.getAccuracy() < bestLocation.getAccuracy()) {
                        bestLocation = l2;
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return bestLocation;
    }

    public void getPlacePickerData(Intent data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 8170, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            if (data == null) {
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.d, "{\"code\":1000,\"desc\":\"get location Fail\"}"));
            }
        }
    }

    public void getGDPlacePickerData(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 8171, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            Intent data = intent;
            if (data != null) {
                double lon = data.getDoubleExtra("lon", 0.0d);
                double lat = data.getDoubleExtra("lat", 0.0d);
                String address = data.getStringExtra(PlaceTypes.ADDRESS);
                data.getDoubleExtra("lon", 0.0d);
                String GMT = e.a(data.getDoubleExtra("lon", 0.0d));
                if (SharePreferenceUtils.getPrefBoolean(this.a, "is_new_protocol", false)) {
                    Log.i("zqr", "new result return lon:" + lon + ",lat:" + lat);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("code", 200);
                        JSONObject dataObj = new JSONObject();
                        dataObj.put("latitude", lat);
                        dataObj.put("longitude", lon);
                        dataObj.put("timezoneOffset", (Object) GMT);
                        dataObj.put("countryName", (Object) "");
                        dataObj.put("countryCode", (Object) this.f);
                        dataObj.put(PlaceTypes.ADDRESS, (Object) address);
                        dataObj.put("timezoneName", (Object) w(lat, lon));
                        jsonObject.put("data", (Object) dataObj);
                        org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.d, jsonObject.toString()));
                        y(lon, lat);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    Log.i("zqr", "return lon:" + lon + ",lat:" + lat);
                    try {
                        JSONObject responseObj = new JSONObject("{\"code\":200,\"desc\":\"\",\"latitude\":" + lat + ",\"longitude\":" + lon + ",\"countryCode\":\"" + this.f + "\",\"timezone\":\"" + GMT + "\",\"detail\":\"" + address + "\"}");
                        responseObj.put("timezoneName", (Object) w(lat, lon));
                        org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.d, responseObj.toString()));
                    } catch (Exception e3) {
                        timber.log.a.g("MapServiceImpl").c("获取地址异常 ", new Object[0]);
                    }
                }
            } else {
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.d, "{\"code\":1000,\"desc\":\"get location Fail\"}"));
            }
        }
    }

    public void init(Context context) {
        this.a = context;
    }

    public void setSupportGoogle(boolean supportGoogle) {
        this.i = supportGoogle;
    }

    public String getMapKey() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8172, new Class[0], String.class);
        return proxy.isSupported ? (String) proxy.result : this.a.getResources().getString(R$string.maps_api_key);
    }

    public class e implements b.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ OnRequestListener a;

        e(OnRequestListener onRequestListener) {
            this.a = onRequestListener;
        }

        public void a(Object obj) {
            OnRequestListener onRequestListener;
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8187, new Class[]{Object.class}, Void.TYPE).isSupported && (onRequestListener = this.a) != null) {
                onRequestListener.onRequestResult(obj);
            }
        }

        public void b(int code, String msg) {
            OnRequestListener onRequestListener;
            Object[] objArr = {new Integer(code), msg};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8188, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported && (onRequestListener = this.a) != null) {
                onRequestListener.onRequestFail(code, msg);
            }
        }
    }

    public void getRadarMap(String deviceId, OnRequestListener listener) {
        Class[] clsArr = {String.class, OnRequestListener.class};
        if (!PatchProxy.proxy(new Object[]{deviceId, listener}, this, changeQuickRedirect, false, 8173, clsArr, Void.TYPE).isSupported) {
            new b().b(deviceId, new e(listener));
        }
    }

    private void y(double longitude, double latitude) {
        Object[] objArr = {new Double(longitude), new Double(latitude)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Double.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8174, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("MapServiceImpl");
            g2.a("updateLocationSp latitude:" + latitude + ",longitude:" + longitude, new Object[0]);
            SharePreferenceUtils.setPrefFloat(this.a, "latitude", (float) latitude);
            SharePreferenceUtils.setPrefFloat(this.a, "longitude", (float) longitude);
        }
    }

    private String w(double latitude, double longitude) {
        Object[] objArr = {new Double(latitude), new Double(longitude)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Double.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 8176, new Class[]{cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return d.Y(latitude, longitude);
    }
}
