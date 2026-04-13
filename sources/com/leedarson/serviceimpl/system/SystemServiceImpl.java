package com.leedarson.serviceimpl.system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import chip.platform.ConfigurationManager;
import com.alibaba.fastjson.parser.JSONLexer;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.google.android.libraries.places.api.model.PlaceTypes;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.internal.LinkedTreeMap;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.k;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.base.utils.m;
import com.leedarson.base.utils.p;
import com.leedarson.base.utils.t;
import com.leedarson.base.utils.v;
import com.leedarson.base.utils.w;
import com.leedarson.base.utils.x;
import com.leedarson.bean.Constants;
import com.leedarson.bean.H5ActionName;
import com.leedarson.serviceimpl.system.WifiScanner;
import com.leedarson.serviceimpl.system.i;
import com.leedarson.serviceinterface.BleMeshService;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.ExternalService;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.LoggerService;
import com.leedarson.serviceinterface.SystemService;
import com.leedarson.serviceinterface.event.AlertEvent;
import com.leedarson.serviceinterface.event.DidRenderEvent;
import com.leedarson.serviceinterface.event.EnterBackgroundEvent;
import com.leedarson.serviceinterface.event.Event;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.leedarson.serviceinterface.event.UpdateStatusBarEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.luck.picture.lib.config.PictureConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.netty.util.internal.StringUtil;
import java.util.Locale;
import java.util.TimeZone;
import meshsdk.BaseResp;
import net.sqlcipher.database.SQLiteDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;
import tv.danmaku.ijk.media.player.IjkMediaMeta;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class SystemServiceImpl implements SystemService {
    public static ChangeQuickRedirect changeQuickRedirect;
    Context a;
    public final String b = Constants.SERVICE_SYSTEM;

    static /* synthetic */ void a(SystemServiceImpl x0, String x1) {
        Class[] clsArr = {SystemServiceImpl.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 8838, clsArr, Void.TYPE).isSupported) {
            x0.o(x1);
        }
    }

    static /* synthetic */ void h(SystemServiceImpl x0, String x1, int x2, String x3) {
        Class<String> cls = String.class;
        Object[] objArr = {x0, x1, new Integer(x2), x3};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 8839, new Class[]{SystemServiceImpl.class, cls, Integer.TYPE, cls}, Void.TYPE).isSupported) {
            x0.p(x1, x2, x3);
        }
    }

    public void handleData(WVJBWebView wVJBWebView, String str, Activity activity, String str2, String str3) {
        Activity activity2;
        StringBuilder sb;
        String str4;
        Exception e2;
        JSONException e3;
        Activity activity3;
        String salt;
        Exception e4;
        Exception e5;
        String data;
        Activity activity4;
        WVJBWebView webView;
        Exception e6;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{wVJBWebView, str, activity, str2, str3}, this, changeQuickRedirect, false, 8823, new Class[]{WVJBWebView.class, cls, Activity.class, cls, cls}, Void.TYPE).isSupported) {
            String callbackKey = str;
            String action = str2;
            WVJBWebView webView2 = wVJBWebView;
            Activity activity5 = activity;
            String data2 = str3;
            char c2 = 65535;
            switch (action.hashCode()) {
                case -2119780336:
                    if (action.equals("getNotificationPermission")) {
                        c2 = '0';
                        break;
                    }
                    break;
                case -2093552464:
                    if (action.equals("stopVibration")) {
                        c2 = 4;
                        break;
                    }
                    break;
                case -2065238006:
                    if (action.equals("getNetworkStatus")) {
                        c2 = 25;
                        break;
                    }
                    break;
                case -1711610133:
                    if (action.equals("impactFeedback")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case -1607257499:
                    if (action.equals("encrypt")) {
                        c2 = '+';
                        break;
                    }
                    break;
                case -1477938467:
                    if (action.equals("getTimeZoneInfo")) {
                        c2 = 15;
                        break;
                    }
                    break;
                case -1462359519:
                    if (action.equals(H5ActionName.ACTION_ALERT)) {
                        c2 = ' ';
                        break;
                    }
                    break;
                case -1445869708:
                    if (action.equals("getTimeFormatType")) {
                        c2 = 18;
                        break;
                    }
                    break;
                case -1414621704:
                    if (action.equals("getNetwork")) {
                        c2 = 28;
                        break;
                    }
                    break;
                case -1388069597:
                    if (action.equals("copyToPasteboard")) {
                        c2 = '6';
                        break;
                    }
                    break;
                case -1332085432:
                    if (action.equals("dialog")) {
                        c2 = '!';
                        break;
                    }
                    break;
                case -1263172891:
                    if (action.equals("openurl")) {
                        c2 = 23;
                        break;
                    }
                    break;
                case -1243894810:
                    if (action.equals("domainToIp")) {
                        c2 = 17;
                        break;
                    }
                    break;
                case -1199674476:
                    if (action.equals("canOpenThirdApp")) {
                        c2 = 22;
                        break;
                    }
                    break;
                case -1060266576:
                    if (action.equals("callPhone")) {
                        c2 = '%';
                        break;
                    }
                    break;
                case -1045031179:
                    if (action.equals("didRender")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case -1038933882:
                    if (action.equals(Constants.SERVICE_SYSTEM_GETPHONEINFO)) {
                        c2 = '#';
                        break;
                    }
                    break;
                case -890413230:
                    if (action.equals("scanWiFi")) {
                        c2 = '1';
                        break;
                    }
                    break;
                case -833462515:
                    if (action.equals("stopVibrate")) {
                        c2 = 5;
                        break;
                    }
                    break;
                case -712269341:
                    if (action.equals("killApp")) {
                        c2 = 7;
                        break;
                    }
                    break;
                case -668200314:
                    if (action.equals("getGPSStatus")) {
                        c2 = 19;
                        break;
                    }
                    break;
                case -649897046:
                    if (action.equals("openAppStore")) {
                        c2 = '9';
                        break;
                    }
                    break;
                case -578629724:
                    if (action.equals("openThirdApp")) {
                        c2 = 21;
                        break;
                    }
                    break;
                case -179167495:
                    if (action.equals("getWiFiInfo")) {
                        c2 = 12;
                        break;
                    }
                    break;
                case -179082519:
                    if (action.equals("getWiFiList")) {
                        c2 = '2';
                        break;
                    }
                    break;
                case -179030826:
                    if (action.equals("getWiFiName")) {
                        c2 = 11;
                        break;
                    }
                    break;
                case -178896474:
                    if (action.equals("getWiFiSSID")) {
                        c2 = 10;
                        break;
                    }
                    break;
                case -109107853:
                    if (action.equals("gotoOpenByDefaultSetting")) {
                        c2 = ':';
                        break;
                    }
                    break;
                case -97830569:
                    if (action.equals("setWebView")) {
                        c2 = '$';
                        break;
                    }
                    break;
                case -88902479:
                    if (action.equals("openWiFiSetting")) {
                        c2 = 8;
                        break;
                    }
                    break;
                case -58027827:
                    if (action.equals("setNetworkHelp")) {
                        c2 = '=';
                        break;
                    }
                    break;
                case -45886082:
                    if (action.equals("openBrowser")) {
                        c2 = 24;
                        break;
                    }
                    break;
                case 1672559:
                    if (action.equals("getTimezone")) {
                        c2 = 16;
                        break;
                    }
                    break;
                case 70616456:
                    if (action.equals("setAutoLockScreen")) {
                        c2 = ';';
                        break;
                    }
                    break;
                case 145650375:
                    if (action.equals("openUserGuide")) {
                        c2 = ')';
                        break;
                    }
                    break;
                case 208592686:
                    if (action.equals("backUpLogToSD")) {
                        c2 = '>';
                        break;
                    }
                    break;
                case 311724582:
                    if (action.equals("enterBackground")) {
                        c2 = '3';
                        break;
                    }
                    break;
                case 316864173:
                    if (action.equals("startVibrate")) {
                        c2 = 3;
                        break;
                    }
                    break;
                case 375730650:
                    if (action.equals("setLanguage")) {
                        c2 = 29;
                        break;
                    }
                    break;
                case 379274586:
                    if (action.equals("getLocationPermission")) {
                        c2 = 31;
                        break;
                    }
                    break;
                case 451310959:
                    if (action.equals("vibrate")) {
                        c2 = 2;
                        break;
                    }
                    break;
                case 464310478:
                    if (action.equals("getLanguage")) {
                        c2 = JSONLexer.EOI;
                        break;
                    }
                    break;
                case 631266189:
                    if (action.equals("getRouterInfo")) {
                        c2 = 27;
                        break;
                    }
                    break;
                case 658803026:
                    if (action.equals("quitApp")) {
                        c2 = 6;
                        break;
                    }
                    break;
                case 704353848:
                    if (action.equals("jumptoApp")) {
                        c2 = 20;
                        break;
                    }
                    break;
                case 719928902:
                    if (action.equals("getSafeAreaInsets")) {
                        c2 = '4';
                        break;
                    }
                    break;
                case 825366121:
                    if (action.equals("setScreenRotate")) {
                        c2 = '\'';
                        break;
                    }
                    break;
                case 1026644591:
                    if (action.equals("openWebView")) {
                        c2 = '(';
                        break;
                    }
                    break;
                case 1212744893:
                    if (action.equals("setGpsLocation")) {
                        c2 = '7';
                        break;
                    }
                    break;
                case 1229144823:
                    if (action.equals("openSystemSetting")) {
                        c2 = '.';
                        break;
                    }
                    break;
                case 1247233375:
                    if (action.equals("sendMail")) {
                        c2 = '&';
                        break;
                    }
                    break;
                case 1264148472:
                    if (action.equals("gotoWiFiSetting")) {
                        c2 = 9;
                        break;
                    }
                    break;
                case 1303221354:
                    if (action.equals(Constants.ACTION_INIT_LIBS)) {
                        c2 = '5';
                        break;
                    }
                    break;
                case 1437286482:
                    if (action.equals("getLocalHttpURL")) {
                        c2 = '/';
                        break;
                    }
                    break;
                case 1542543757:
                    if (action.equals("decrypt")) {
                        c2 = StringUtil.COMMA;
                        break;
                    }
                    break;
                case 1705367705:
                    if (action.equals("updateStatusBarStyle")) {
                        c2 = '*';
                        break;
                    }
                    break;
                case 1724060479:
                    if (action.equals("connectWiFi")) {
                        c2 = 14;
                        break;
                    }
                    break;
                case 1724061471:
                    if (action.equals("connectWifi")) {
                        c2 = StringUtil.CARRIAGE_RETURN;
                        break;
                    }
                    break;
                case 1774942272:
                    if (action.equals("reloadWebView")) {
                        c2 = StringUtil.DOUBLE_QUOTE;
                        break;
                    }
                    break;
                case 1932827770:
                    if (action.equals("CacheImages")) {
                        c2 = '<';
                        break;
                    }
                    break;
                case 1960834242:
                    if (action.equals("getNotch")) {
                        c2 = 30;
                        break;
                    }
                    break;
                case 1992336882:
                    if (action.equals("notificationControl")) {
                        c2 = '8';
                        break;
                    }
                    break;
                case 2089411289:
                    if (action.equals("openAppSetting")) {
                        c2 = '-';
                        break;
                    }
                    break;
            }
            WVJBWebView webView3 = webView2;
            String str5 = "packName";
            String str6 = "";
            String str7 = IjkMediaMeta.IJKM_KEY_LANGUAGE;
            String str8 = "message";
            switch (c2) {
                case 0:
                    String data3 = data2;
                    activity2 = activity5;
                    timber.log.a.i("BLE.TRV MainActivity.webView.H5DidRender", new Object[0]);
                    BaseApplication.b().p1 = System.currentTimeMillis();
                    Constans.isDidRender = true;
                    v.d("APP#loadH52DidRender", "收到 H5DidRender回执");
                    Log.i("Ghunt", "Web call didRender");
                    DidRenderEvent didRenderEvent = new DidRenderEvent();
                    try {
                        if (!TextUtils.isEmpty(data3)) {
                            JSONObject jsonObject = new JSONObject(data3);
                            if (jsonObject.has("traceId")) {
                                didRenderEvent.traceId = jsonObject.getString("traceId");
                            }
                        }
                    } catch (Exception e7) {
                    }
                    org.greenrobot.eventbus.c.c().l(didRenderEvent);
                    t tVar = t.INSTANCE;
                    tVar.timeArray[7] = w.r();
                    Log.i("GhuntStart", "ja==" + tVar.getStartTimeJson());
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_LOGGER, "onMessage", tVar.getStartTimeJson()));
                    o(callbackKey);
                    LinkedTreeMap dataMap = m.b(data3);
                    if (dataMap != null && dataMap.containsKey("httpBaseUrl")) {
                        String baseurl = dataMap.get("httpBaseUrl").toString();
                        SharePreferenceUtils.setPrefString(activity2, "httpBaseUrl", baseurl);
                        timber.log.a.g(Constants.SERVICE_SYSTEM).h("---------------url:" + baseurl, new Object[0]);
                    }
                    activity2.getWindow().clearFlags(1024);
                    BleMeshService bleMeshService = (BleMeshService) com.alibaba.android.arouter.launcher.a.c().g(BleMeshService.class);
                    if (bleMeshService != null) {
                        bleMeshService.onDidRender();
                        break;
                    }
                    break;
                case 1:
                    activity2 = activity5;
                    o(callbackKey);
                    w.e0(this.a, 80);
                    break;
                case 2:
                case 3:
                    activity2 = activity5;
                    o(callbackKey);
                    LinkedTreeMap linkedTreeMap = m.b(data2);
                    if (linkedTreeMap != null) {
                        double time = 1000.0d;
                        if (linkedTreeMap.containsKey(TypedValues.TransitionType.S_DURATION)) {
                            time = ((Double) linkedTreeMap.get(TypedValues.TransitionType.S_DURATION)).doubleValue();
                        }
                        w.V(this.a, (int) time);
                        break;
                    }
                    break;
                case 4:
                case 5:
                    activity2 = activity5;
                    o(callbackKey);
                    w.d();
                    break;
                case 6:
                case 7:
                    activity2 = activity5;
                    o(callbackKey);
                    n(activity2);
                    break;
                case 8:
                case 9:
                    activity2 = activity5;
                    try {
                        Intent intent = new Intent("android.settings.WIFI_SETTINGS");
                        intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                        this.a.startActivity(intent);
                    } catch (Exception e8) {
                        e8.printStackTrace();
                    }
                    o(callbackKey);
                    break;
                case 10:
                    activity2 = activity5;
                    if (Build.VERSION.SDK_INT < 27) {
                        String currentSSID = w.C(this.a);
                        if (TextUtils.isEmpty(currentSSID)) {
                            if (m(activity2)) {
                                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"ssid\":\"\",\"code\":-32}"));
                                break;
                            } else {
                                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"ssid\":\"\",\"code\":-30}"));
                                break;
                            }
                        } else {
                            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"ssid\":\"" + currentSSID + "\",\"code\":200}"));
                            break;
                        }
                    } else {
                        org.greenrobot.eventbus.c.c().l(new NeedPermissionEvent(5, callbackKey));
                        break;
                    }
                case 11:
                case 12:
                    String str9 = str6;
                    activity2 = activity5;
                    String targetSSID = "";
                    try {
                        targetSSID = new JSONObject(data2).optString("ssid");
                    } catch (Exception e9) {
                        e9.printStackTrace();
                    }
                    if (((WifiManager) this.a.getSystemService("wifi")).isWifiEnabled()) {
                        String[] permsLocation = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
                        if (w.f(this.a)) {
                            if (Build.VERSION.SDK_INT < 27 || k(permsLocation)) {
                                if (m(activity2)) {
                                    String[] currentSSIDFreq = x.g(this.a, targetSSID);
                                    JSONObject obj = new JSONObject();
                                    JSONObject dataObj = new JSONObject();
                                    String str10 = targetSSID;
                                    try {
                                        obj.put("name", (Object) currentSSIDFreq[0]);
                                        obj.put("frequency", (Object) currentSSIDFreq[1]);
                                        if ("getWiFiInfo".equals(action)) {
                                            obj.put("routerBrand", (Object) str9);
                                            obj.put("rssi", (Object) currentSSIDFreq[2]);
                                            obj.put("bssid", (Object) currentSSIDFreq[3]);
                                            obj.put("enctype", (Object) currentSSIDFreq[4]);
                                            obj.put("channel", (Object) currentSSIDFreq[5]);
                                        }
                                        dataObj.put("data", (Object) obj);
                                        dataObj.put("code", 200);
                                    } catch (JSONException e10) {
                                        e10.printStackTrace();
                                    }
                                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, dataObj.toString()));
                                    break;
                                } else {
                                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, p.a(401, "已开启但是未连接wifi").toString()));
                                    break;
                                }
                            } else {
                                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, p.a(403, "未获取定位权限").toString()));
                                break;
                            }
                        } else {
                            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, p.a(BaseResp.ERR_WAIT_RESPONSE, "GPS定位未打开").toString()));
                            break;
                        }
                    } else {
                        org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, p.a(BaseResp.ERR_MSG_SEND_FAIL, "未开启wifi").toString()));
                        return;
                    }
                    break;
                case 13:
                case 14:
                    String str11 = str6;
                    String data4 = data2;
                    activity2 = activity5;
                    o(callbackKey);
                    try {
                        LinkedTreeMap recordLinkedTreeMap = m.b(data4);
                        if (recordLinkedTreeMap != null) {
                            String ssid = recordLinkedTreeMap.get("ssid").toString();
                            if (recordLinkedTreeMap.containsKey("password")) {
                                str11 = recordLinkedTreeMap.get("password").toString();
                            }
                            String str12 = str11;
                            org.greenrobot.eventbus.c.c().l(new NeedPermissionEvent(4, ssid));
                            break;
                        }
                    } catch (Exception e11) {
                        e11.printStackTrace();
                        break;
                    }
                    break;
                case 15:
                    activity2 = activity5;
                    TimeZone tz = TimeZone.getDefault();
                    String GMT = tz.getDisplayName(false, 0, new Locale("en"));
                    String timeZoneOffset = GMT;
                    String[] split = timeZoneOffset.split(":");
                    if (split.length > 0) {
                        timeZoneOffset = split[0];
                    }
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"timeZoneOffset\":\"" + timeZoneOffset + "\",\"GMT\":\"" + GMT + "\",\"timeZoneName\":\"" + tz.getID() + "\"}"));
                    break;
                case 16:
                    activity2 = activity5;
                    TimeZone timeZone = TimeZone.getDefault();
                    int offsetInt = timeZone.getOffset(System.currentTimeMillis()) / 3600000;
                    if (offsetInt >= 0) {
                        str4 = "GMT+";
                    } else {
                        sb = new StringBuilder();
                        str4 = "GMT";
                    }
                    sb.append(str4);
                    sb.append(offsetInt);
                    String timeZoneSet = sb.toString();
                    JSONObject obj2 = new JSONObject();
                    JSONObject dataObj2 = new JSONObject();
                    try {
                        obj2.put("timezoneOffset", (Object) timeZoneSet);
                        obj2.put("timezoneName", (Object) timeZone.getID());
                        dataObj2.put("data", (Object) obj2);
                        dataObj2.put("code", 200);
                    } catch (JSONException e12) {
                        e12.printStackTrace();
                    }
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, dataObj2.toString()));
                    break;
                case 17:
                    activity2 = activity5;
                    try {
                        LinkedTreeMap jsonLinkedTree = m.b(data2);
                        if (jsonLinkedTree != null) {
                            new Thread(new a(jsonLinkedTree.get(SerializableCookie.DOMAIN).toString(), callbackKey)).start();
                            break;
                        }
                    } catch (Exception e13) {
                        e13.printStackTrace();
                        break;
                    }
                    break;
                case 18:
                    activity2 = activity5;
                    int status = 12;
                    if (j(this.a)) {
                        status = 24;
                    }
                    JSONObject timeObject = new JSONObject();
                    JSONObject timeSubObject = new JSONObject();
                    try {
                        timeSubObject.put("status", status);
                        timeObject.put("data", (Object) timeSubObject);
                        timeObject.put("code", 200);
                    } catch (JSONException e14) {
                        e14.printStackTrace();
                    }
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, timeObject.toString()));
                    break;
                case 19:
                    String str13 = data2;
                    activity2 = activity5;
                    org.greenrobot.eventbus.c.c().l(new NeedPermissionEvent(93, str6));
                    break;
                case 20:
                case 21:
                    String str14 = str5;
                    String data5 = data2;
                    activity2 = activity5;
                    o(callbackKey);
                    JSONObject joParams = new JSONObject();
                    try {
                        JSONObject joAlexa = new JSONObject(data5);
                        if (joAlexa.has("url")) {
                            String url = joAlexa.getString("url");
                            String url2 = url.replace("www.amazon.com/ap/oa", "alexa.amazon.com/spa/skill-account-linking-consent") + "&fragment=skill-account-linking-consent&skill_stage=development";
                            String i = i(url2, "redirect_uri", NetworkManager.MOCK_SCHEME_HTTPS + this.a.getPackageName() + "/main");
                            try {
                                Intent i2 = new Intent("android.intent.action.VIEW", Uri.parse(url2));
                                i2.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                                i2.setPackage("com.amazon.dee.app");
                                this.a.startActivity(i2);
                            } catch (Exception e15) {
                                Exception exc = e15;
                                Intent i3 = new Intent("android.intent.action.VIEW", Uri.parse(url2));
                                i3.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                                this.a.startActivity(i3);
                            }
                        }
                    } catch (JSONException e16) {
                        e16.printStackTrace();
                    }
                    try {
                        LinkedTreeMap b2 = m.b(data5);
                        JSONObject objOpenThird = new JSONObject(data5);
                        timber.log.a.a("vary objOpenThird: " + objOpenThird, new Object[0]);
                        String pkgName = str6;
                        String schemeUrl = str6;
                        String marketUrl = str6;
                        if (objOpenThird.has("packageName")) {
                            pkgName = objOpenThird.getString("packageName");
                        }
                        if (objOpenThird.has("schemeUrl")) {
                            schemeUrl = objOpenThird.getString("schemeUrl");
                        }
                        if (objOpenThird.has("marketUrl")) {
                            marketUrl = objOpenThird.getString("marketUrl");
                        }
                        if (objOpenThird.has(str14)) {
                            pkgName = objOpenThird.getString(str14);
                        }
                        if (objOpenThird.has("params")) {
                            joParams = objOpenThird.getJSONObject("params");
                        }
                        timber.log.a.a("vary marketUrl: " + marketUrl + " schemeUrl: " + schemeUrl, new Object[0]);
                        if (!TextUtils.isEmpty(schemeUrl)) {
                            g.d(pkgName, schemeUrl, marketUrl, activity2);
                        } else if (w.g(this.a, pkgName)) {
                            String path = "scheme://iotsolution-scheme/" + pkgName.replaceAll("[.]", str6);
                            if (joParams.has("action") && joParams.get("action").equals("PBA")) {
                                path = path + "?pbaParams=" + joParams.toString();
                            }
                            timber.log.a.i("openThirdPart.path ==> " + path, new Object[0]);
                            Intent i4 = new Intent("android.intent.action.VIEW", Uri.parse(path));
                            i4.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                            activity2.startActivity(i4);
                        } else {
                            w.m(this.a, pkgName);
                        }
                    } catch (Exception e17) {
                        e17.printStackTrace();
                    }
                    o(callbackKey);
                    break;
                case 22:
                    String data6 = data2;
                    activity2 = activity5;
                    JSONObject objCanOpenResponse = new JSONObject();
                    JSONObject objCanOpenState = new JSONObject();
                    Log.i("Ghunt", "ACTION_CAN_OPEN_THIRD_APP" + data6);
                    LinkedTreeMap canOpenDataTree = m.b(data6);
                    String packName = null;
                    Long versionCode = null;
                    String str15 = str5;
                    if (canOpenDataTree.containsKey(str15)) {
                        packName = canOpenDataTree.get(str15).toString();
                    }
                    if (canOpenDataTree.containsKey(" minVersionCode")) {
                        String versioncodeString = canOpenDataTree.get("minVersionCode").toString();
                        if (!TextUtils.isEmpty(versioncodeString)) {
                            versionCode = Long.valueOf(Long.parseLong(versioncodeString));
                        }
                    }
                    if (packName == null) {
                        try {
                            objCanOpenState.put("status", 0);
                            objCanOpenResponse.put("data", (Object) objCanOpenState);
                            objCanOpenResponse.put("code", 200);
                        } catch (JSONException e18) {
                            e18.printStackTrace();
                        }
                    } else {
                        try {
                            objCanOpenState.put("status", (int) w.T(this.a, packName, versionCode));
                            objCanOpenResponse.put("data", (Object) objCanOpenState);
                            objCanOpenResponse.put("code", 200);
                        } catch (JSONException e19) {
                            e19.printStackTrace();
                        }
                    }
                    Log.i("Ghunt", "ACTION_CAN_OPEN_THIRD_APP---response==" + objCanOpenResponse);
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, objCanOpenResponse.toString()));
                    break;
                case 23:
                case 24:
                    activity2 = activity5;
                    o(callbackKey);
                    LinkedTreeMap map = m.b(data2);
                    if (map != null) {
                        this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(map.get("url").toString())).setFlags(SQLiteDatabase.CREATE_IF_NECESSARY));
                        break;
                    }
                    break;
                case 25:
                    String data7 = data2;
                    activity2 = activity5;
                    int netState = w.w(this.a);
                    JSONObject jsonObject2 = new JSONObject();
                    try {
                        jsonObject2.put("code", 200);
                        jsonObject2.put(Constants.ACTION_STATE, netState);
                    } catch (JSONException e20) {
                        e20.printStackTrace();
                    }
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject2.toString()));
                    String str16 = data7;
                    break;
                case 26:
                    String data8 = data2;
                    activity2 = activity5;
                    String data9 = str7;
                    JSONObject objl = new JSONObject();
                    JSONObject objLan = new JSONObject();
                    try {
                        objl.put(data9, (Object) w.u(this.a));
                        objLan.put("data", (Object) objl);
                        objLan.put("code", 200);
                    } catch (JSONException e21) {
                        e21.printStackTrace();
                    }
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, objLan.toString()));
                    String str17 = data8;
                    break;
                case 27:
                    String data10 = data2;
                    activity2 = activity5;
                    try {
                        DhcpInfo dhcpInfo = ((WifiManager) activity2.getApplicationContext().getSystemService("wifi")).getDhcpInfo();
                        if (dhcpInfo != null) {
                            JSONObject objRouter = new JSONObject();
                            try {
                                objRouter.put(IjkMediaPlayer.OnNativeInvokeListener.ARG_IP, (Object) l(dhcpInfo.ipAddress));
                                objRouter.put("netmask", (Object) l(dhcpInfo.netmask));
                                objRouter.put("gateway", (Object) l(dhcpInfo.gateway));
                                int i5 = dhcpInfo.ipAddress;
                                int i6 = dhcpInfo.netmask;
                                objRouter.put("boardCast", (Object) l((i5 & i6) | (~i6)));
                            } catch (JSONException e22) {
                                e22.printStackTrace();
                            }
                            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, objRouter.toString()));
                        }
                        String str18 = data10;
                        break;
                    } catch (Exception e23) {
                        e23.printStackTrace();
                        String str19 = data10;
                        break;
                    }
                case 28:
                    WVJBWebView webView4 = webView3;
                    String data11 = data2;
                    activity2 = activity5;
                    try {
                        int netStatus = w.w(this.a);
                        DhcpInfo dhcpInfo2 = ((WifiManager) activity2.getApplicationContext().getSystemService("wifi")).getDhcpInfo();
                        if (dhcpInfo2 != null) {
                            JSONObject objData = new JSONObject();
                            JSONObject netObj = new JSONObject();
                            try {
                                objData.put("status", netStatus);
                                objData.put(IjkMediaPlayer.OnNativeInvokeListener.ARG_IP, (Object) l(dhcpInfo2.ipAddress));
                                objData.put("subnetMask", (Object) l(dhcpInfo2.netmask));
                                objData.put("router", (Object) l(dhcpInfo2.gateway));
                                int i7 = dhcpInfo2.ipAddress;
                                WVJBWebView wVJBWebView2 = webView4;
                                try {
                                    int i8 = dhcpInfo2.netmask;
                                    objData.put("dns", (Object) l((~i8) | (i7 & i8)));
                                    netObj.put("data", (Object) objData);
                                    netObj.put("code", 200);
                                } catch (JSONException e24) {
                                    e3 = e24;
                                }
                            } catch (JSONException e25) {
                                WVJBWebView wVJBWebView3 = webView4;
                                e3 = e25;
                                try {
                                    e3.printStackTrace();
                                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, netObj.toString()));
                                    String str20 = data11;
                                } catch (Exception e26) {
                                    e2 = e26;
                                    e2.printStackTrace();
                                    String str21 = data11;
                                    Activity activity6 = activity2;
                                }
                                Activity activity62 = activity2;
                            }
                            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, netObj.toString()));
                        }
                        String str202 = data11;
                    } catch (Exception e27) {
                        WVJBWebView wVJBWebView4 = webView4;
                        e2 = e27;
                        e2.printStackTrace();
                        String str212 = data11;
                        Activity activity622 = activity2;
                    }
                case 29:
                    WVJBWebView webView5 = webView3;
                    String data12 = data2;
                    activity2 = activity5;
                    LinkedTreeMap linkedTreeMapl = m.b(data12);
                    if (linkedTreeMapl == null) {
                        WVJBWebView wVJBWebView5 = webView5;
                        String str22 = data12;
                        break;
                    } else {
                        String language = "en_us";
                        String str23 = str7;
                        if (linkedTreeMapl.containsKey(str23)) {
                            language = linkedTreeMapl.get(str23).toString();
                        }
                        SharePreferenceUtils.setPrefString(this.a, str23, language);
                        PubUtils.setLanguage(activity2);
                        JSONObject jsonObject22 = new JSONObject();
                        try {
                            jsonObject22.put("code", 200);
                        } catch (JSONException e28) {
                            e28.printStackTrace();
                        }
                        org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject22.toString()));
                        WVJBWebView wVJBWebView6 = webView5;
                        String str24 = data12;
                        break;
                    }
                case 30:
                    WVJBWebView webView6 = webView3;
                    String data13 = data2;
                    activity2 = activity5;
                    JSONObject jsonObject23 = new JSONObject();
                    JSONObject notchObject = new JSONObject();
                    try {
                        jsonObject23.put("height", k.i(activity2, (float) com.leedarson.serviceimpl.system.notch.b.b(activity2)));
                        notchObject.put("data", (Object) jsonObject23);
                        notchObject.put("code", 200);
                    } catch (JSONException e29) {
                        e29.printStackTrace();
                    }
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, notchObject.toString()));
                    WVJBWebView wVJBWebView7 = webView6;
                    String str25 = data13;
                    break;
                case 31:
                    WVJBWebView webView7 = webView3;
                    String data14 = data2;
                    activity2 = activity5;
                    JSONObject jsonObject3 = new JSONObject();
                    String[] perms = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
                    boolean hasPermissions = EasyPermissions.a(activity2, perms);
                    boolean isDenied = EasyPermissions.i(activity2, perms);
                    if (hasPermissions) {
                        try {
                            jsonObject3.put("code", 200);
                            jsonObject3.put("status", (Object) "AuthorizedWhenInUse");
                        } catch (JSONException e30) {
                            e30.printStackTrace();
                        }
                    } else if (isDenied) {
                        try {
                            jsonObject3.put("code", 200);
                            jsonObject3.put("status", (Object) "Denied");
                        } catch (JSONException e31) {
                            e31.printStackTrace();
                        }
                    } else {
                        try {
                            jsonObject3.put("code", 200);
                            jsonObject3.put("status", (Object) "NotDetermined");
                        } catch (JSONException e32) {
                            e32.printStackTrace();
                        }
                    }
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject3.toString()));
                    WVJBWebView wVJBWebView8 = webView7;
                    String str26 = data14;
                    break;
                case ' ':
                case '!':
                    WVJBWebView webView8 = webView3;
                    String data15 = data2;
                    activity2 = activity5;
                    try {
                        JSONObject jsonObject1 = new JSONObject(data15);
                        if (jsonObject1.has("position")) {
                            jsonObject1.put(Constants.ACTION_STYLE, (Object) jsonObject1.getString("position"));
                            jsonObject1.remove("position");
                        }
                        if (jsonObject1.has(FirebaseAnalytics.Param.CONTENT)) {
                            jsonObject1.put(NotificationCompat.CATEGORY_MESSAGE, (Object) jsonObject1.getString(FirebaseAnalytics.Param.CONTENT));
                            jsonObject1.remove(FirebaseAnalytics.Param.CONTENT);
                        }
                        if (jsonObject1.has("buttonText")) {
                            jsonObject1.put("alerts", (Object) jsonObject1.getJSONArray("buttonText"));
                            jsonObject1.remove("buttonText");
                        }
                        org.greenrobot.eventbus.c.c().l(new AlertEvent(jsonObject1.toString(), callbackKey));
                        WVJBWebView wVJBWebView9 = webView8;
                        String str27 = data15;
                        break;
                    } catch (JSONException e33) {
                        e33.printStackTrace();
                        WVJBWebView wVJBWebView10 = webView8;
                        String str28 = data15;
                        break;
                    }
                case '\"':
                    activity2 = activity5;
                    o(callbackKey);
                    org.greenrobot.eventbus.c.c().l(new l());
                    String str29 = data2;
                    break;
                case '#':
                    WVJBWebView webView9 = webView3;
                    String data16 = data2;
                    activity2 = activity5;
                    String phoneName = Settings.Secure.getString(this.a.getContentResolver(), "bluetooth_name");
                    String phoneModel = Build.MODEL;
                    try {
                        JSONObject jsonObject11 = new JSONObject();
                        jsonObject11.put("code", 200);
                        jsonObject11.put("name", (Object) phoneName);
                        jsonObject11.put("modelName", (Object) phoneModel);
                        org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject11.toString()));
                        WVJBWebView wVJBWebView11 = webView9;
                        String str30 = data16;
                        break;
                    } catch (Exception e34) {
                        e34.printStackTrace();
                        WVJBWebView wVJBWebView12 = webView9;
                        String str31 = data16;
                        break;
                    }
                case '$':
                    WVJBWebView webView10 = webView3;
                    String data17 = data2;
                    activity2 = activity5;
                    o(callbackKey);
                    LinkedTreeMap dataSetWeb = m.b(data17);
                    if (dataSetWeb != null && dataSetWeb.containsKey("url")) {
                        SharePreferenceUtils.setPrefString(activity2, "remoteUrl", dataSetWeb.get("url").toString());
                        WVJBWebView wVJBWebView13 = webView10;
                        String str32 = data17;
                        break;
                    } else {
                        WVJBWebView wVJBWebView14 = webView10;
                        String str33 = data17;
                        break;
                    }
                case '%':
                    WVJBWebView webView11 = webView3;
                    String data18 = data2;
                    activity2 = activity5;
                    o(callbackKey);
                    LinkedTreeMap dataPhone = m.b(data18);
                    if (dataPhone != null && dataPhone.containsKey("phoneNum")) {
                        k.a(activity2, dataPhone.get("phoneNum").toString());
                        WVJBWebView wVJBWebView15 = webView11;
                        String str34 = data18;
                        break;
                    } else {
                        WVJBWebView wVJBWebView16 = webView11;
                        String str35 = data18;
                        break;
                    }
                case '&':
                    WVJBWebView webView12 = webView3;
                    String data19 = data2;
                    activity2 = activity5;
                    o(callbackKey);
                    LinkedTreeMap dataMail = m.b(data19);
                    if (dataMail == null) {
                        WVJBWebView wVJBWebView17 = webView12;
                        String str36 = data19;
                        break;
                    } else {
                        String address = "";
                        if (dataMail.containsKey(PlaceTypes.ADDRESS)) {
                            address = dataMail.get(PlaceTypes.ADDRESS).toString();
                        }
                        if (dataMail.containsKey("email")) {
                            address = dataMail.get("email").toString();
                        }
                        String title = "";
                        if (dataMail.containsKey("title")) {
                            title = dataMail.get("title").toString();
                        }
                        if (dataMail.containsKey("subject")) {
                            title = dataMail.get("subject").toString();
                        }
                        k.j(activity2, address, title, dataMail.containsKey(FirebaseAnalytics.Param.CONTENT) ? dataMail.get(FirebaseAnalytics.Param.CONTENT).toString() : str6);
                        WVJBWebView wVJBWebView18 = webView12;
                        String str37 = data19;
                        break;
                    }
                case '\'':
                    WVJBWebView webView13 = webView3;
                    String data20 = data2;
                    activity2 = activity5;
                    LinkedTreeMap screenMap = m.b(data20);
                    if (screenMap != null) {
                        if (!screenMap.containsKey("orientation")) {
                            WVJBWebView wVJBWebView19 = webView13;
                            String str38 = data20;
                            break;
                        } else {
                            int orientation = (int) Double.parseDouble(screenMap.get("orientation").toString());
                            if (orientation != 0) {
                                if (orientation != 1) {
                                    WVJBWebView wVJBWebView20 = webView13;
                                    String str39 = data20;
                                    break;
                                } else {
                                    activity2.setRequestedOrientation(1);
                                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"code\":200}"));
                                    WVJBWebView wVJBWebView21 = webView13;
                                    String str40 = data20;
                                    break;
                                }
                            } else {
                                activity2.setRequestedOrientation(0);
                                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"code\":200}"));
                                WVJBWebView wVJBWebView22 = webView13;
                                String str41 = data20;
                                break;
                            }
                        }
                    } else {
                        WVJBWebView wVJBWebView23 = webView13;
                        String str42 = data20;
                        break;
                    }
                case '(':
                case ')':
                    WVJBWebView webView14 = webView3;
                    String data21 = data2;
                    Activity activity7 = activity5;
                    if (com.leedarson.base.utils.c.h().c() != null) {
                        activity3 = com.leedarson.base.utils.c.h().c();
                    } else {
                        activity3 = activity7;
                    }
                    ExternalService externalService = (ExternalService) com.alibaba.android.arouter.launcher.a.c().g(ExternalService.class);
                    if (externalService != null) {
                        externalService.handleData(webView14, activity3, callbackKey, action, data21);
                    }
                    Activity activity8 = activity3;
                    WVJBWebView wVJBWebView24 = webView14;
                    String str43 = data21;
                    return;
                case '*':
                    activity2 = activity5;
                    UpdateStatusBarEvent tempUpdateStatue = new UpdateStatusBarEvent();
                    tempUpdateStatue.targetwebView = webView3;
                    org.greenrobot.eventbus.c.c().l(tempUpdateStatue);
                    String str44 = data2;
                    break;
                case '+':
                    WVJBWebView webView15 = webView3;
                    String data22 = data2;
                    activity2 = activity5;
                    String str45 = str8;
                    JSONObject jsonObject12 = new JSONObject();
                    JSONObject jsonObjectData12 = new JSONObject();
                    LinkedTreeMap encryptTreeMap = m.b(data22);
                    if (encryptTreeMap != null) {
                        if (encryptTreeMap.containsKey(ConfigurationManager.kConfigKey_Spake2pSalt)) {
                            salt = encryptTreeMap.get(ConfigurationManager.kConfigKey_Spake2pSalt).toString();
                        } else {
                            salt = "";
                        }
                        if (encryptTreeMap.containsKey(IjkMediaMeta.IJKM_KEY_TYPE)) {
                            String type = encryptTreeMap.get(IjkMediaMeta.IJKM_KEY_TYPE).toString();
                        }
                        String message = "";
                        if (encryptTreeMap.containsKey(str45)) {
                            message = encryptTreeMap.get(str45).toString();
                        }
                        try {
                            String encryptResult = k.e(message, salt);
                            String str46 = salt;
                            try {
                                jsonObject12.put("code", 200);
                                jsonObjectData12.put(str45, (Object) encryptResult);
                                jsonObject12.put("data", (Object) jsonObjectData12);
                            } catch (Exception e35) {
                                e4 = e35;
                            }
                        } catch (Exception e36) {
                            String str47 = salt;
                            e4 = e36;
                            e4.printStackTrace();
                            try {
                                jsonObject12.put("code", -1);
                            } catch (JSONException e37) {
                                e37.printStackTrace();
                            }
                            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject12.toString()));
                            WVJBWebView wVJBWebView25 = webView15;
                            String str48 = data22;
                            Activity activity6222 = activity2;
                        }
                    }
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject12.toString()));
                    WVJBWebView wVJBWebView252 = webView15;
                    String str482 = data22;
                case ',':
                    WVJBWebView webView16 = webView3;
                    String data23 = data2;
                    activity2 = activity5;
                    JSONObject jsonObject13 = new JSONObject();
                    JSONObject jsonObjectData13 = new JSONObject();
                    LinkedTreeMap decryptTreeMap = m.b(data23);
                    if (decryptTreeMap != null) {
                        String salt2 = "";
                        if (decryptTreeMap.containsKey(ConfigurationManager.kConfigKey_Spake2pSalt)) {
                            salt2 = decryptTreeMap.get(ConfigurationManager.kConfigKey_Spake2pSalt).toString();
                        }
                        String type2 = "";
                        if (decryptTreeMap.containsKey(IjkMediaMeta.IJKM_KEY_TYPE)) {
                            type2 = decryptTreeMap.get(IjkMediaMeta.IJKM_KEY_TYPE).toString();
                        }
                        String message2 = "";
                        String decryptResult = "";
                        String decryptResult2 = str8;
                        if (decryptTreeMap.containsKey(decryptResult2)) {
                            message2 = decryptTreeMap.get(decryptResult2).toString();
                        }
                        try {
                            String decryptResult3 = k.c(message2, salt2);
                            String str49 = type2;
                            try {
                                jsonObject13.put("code", 200);
                                String decryptResult4 = decryptResult3;
                                try {
                                    jsonObjectData13.put(decryptResult2, (Object) decryptResult4);
                                    jsonObject13.put("data", (Object) jsonObjectData13);
                                    String str50 = decryptResult4;
                                } catch (Exception e38) {
                                    String str51 = decryptResult4;
                                    e5 = e38;
                                    e5.printStackTrace();
                                    try {
                                        jsonObject13.put("code", -1);
                                    } catch (JSONException e39) {
                                        e39.printStackTrace();
                                    }
                                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject13.toString()));
                                    WVJBWebView wVJBWebView26 = webView16;
                                    String str52 = data23;
                                    Activity activity62222 = activity2;
                                }
                            } catch (Exception e40) {
                                String str53 = decryptResult3;
                                e5 = e40;
                                e5.printStackTrace();
                                jsonObject13.put("code", -1);
                                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject13.toString()));
                                WVJBWebView wVJBWebView262 = webView16;
                                String str522 = data23;
                                Activity activity622222 = activity2;
                            }
                        } catch (Exception e41) {
                            String str54 = type2;
                            String str55 = decryptResult;
                            e5 = e41;
                            e5.printStackTrace();
                            jsonObject13.put("code", -1);
                            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject13.toString()));
                            WVJBWebView wVJBWebView2622 = webView16;
                            String str5222 = data23;
                            Activity activity6222222 = activity2;
                        }
                    } else {
                        Object obj3 = "";
                    }
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject13.toString()));
                    WVJBWebView wVJBWebView26222 = webView16;
                    String str52222 = data23;
                case '-':
                    WVJBWebView webView17 = webView3;
                    String data24 = data2;
                    activity2 = activity5;
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"code\":200}"));
                    try {
                        String flag = new JSONObject(data24).optString("flag");
                        if ("notification".equals(flag)) {
                            j.a(activity2);
                        } else if ("permission".equals(flag)) {
                            w.K(this.a);
                        } else if ("openByDefault".equals(flag)) {
                            w.J(activity2);
                        } else {
                            Context context = this.a;
                            context.startActivity(w.o(context));
                        }
                        WVJBWebView wVJBWebView27 = webView17;
                        String str56 = data24;
                        break;
                    } catch (Exception e42) {
                        e42.printStackTrace();
                        Context context2 = this.a;
                        context2.startActivity(w.o(context2));
                        WVJBWebView wVJBWebView28 = webView17;
                        String str57 = data24;
                        break;
                    }
                case '.':
                    activity2 = activity5;
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"code\":200}"));
                    k.g(this.a);
                    String str58 = data2;
                    break;
                case '/':
                    activity2 = activity5;
                    String str59 = data2;
                    break;
                case '0':
                    WVJBWebView webView18 = webView3;
                    String data25 = data2;
                    activity2 = activity5;
                    int notiPermissionStatus = j.b(this.a);
                    try {
                        JSONObject joNotiPer = new JSONObject();
                        joNotiPer.put("code", 200);
                        JSONObject joNotiPerData = new JSONObject();
                        joNotiPerData.put("status", (int) notiPermissionStatus);
                        joNotiPer.put("data", (Object) joNotiPerData);
                        org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, joNotiPer.toString()));
                        WVJBWebView wVJBWebView29 = webView18;
                        String str60 = data25;
                        break;
                    } catch (Exception e43) {
                        e43.printStackTrace();
                        WVJBWebView wVJBWebView30 = webView18;
                        String str61 = data25;
                        break;
                    }
                case '1':
                case '2':
                    WVJBWebView webView19 = webView3;
                    String data26 = data2;
                    Activity activity9 = activity5;
                    try {
                        new WifiScanner(this.a).i(activity9, new JSONObject(data26).optBoolean("reScan", true), new b(callbackKey));
                        WVJBWebView wVJBWebView31 = webView19;
                        activity2 = activity9;
                        String str62 = data26;
                        break;
                    } catch (Exception e44) {
                        e44.printStackTrace();
                        WVJBWebView wVJBWebView32 = webView19;
                        activity2 = activity9;
                        String str63 = data26;
                        break;
                    }
                case '3':
                    org.greenrobot.eventbus.c.c().l(new EnterBackgroundEvent());
                    activity2 = activity5;
                    String str64 = data2;
                    break;
                case '4':
                    WVJBWebView webView20 = webView3;
                    String data27 = data2;
                    Activity activity10 = activity5;
                    try {
                        JSONObject joSafeArea = new JSONObject();
                        joSafeArea.put("code", 200);
                        JSONObject joInsets = new JSONObject();
                        joInsets.put("bottom", 0);
                        int convertHeight = k.i(activity10, (float) com.leedarson.serviceimpl.system.notch.b.b(activity10));
                        joInsets.put("top", convertHeight < 30 ? 30 : convertHeight);
                        joSafeArea.put("data", (Object) joInsets);
                        org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, joSafeArea.toString()));
                        WVJBWebView wVJBWebView33 = webView20;
                        activity2 = activity10;
                        String str65 = data27;
                        break;
                    } catch (Exception e45) {
                        e45.printStackTrace();
                        WVJBWebView wVJBWebView34 = webView20;
                        activity2 = activity10;
                        String str66 = data27;
                        break;
                    }
                case '5':
                    String data28 = data2;
                    org.greenrobot.eventbus.c.c().l(new Event("SystemEvent", callbackKey, action, data28));
                    activity2 = activity5;
                    String str67 = data28;
                    break;
                case '6':
                    WVJBWebView webView21 = webView3;
                    String data29 = data2;
                    Activity activity11 = activity5;
                    LinkedTreeMap copyToPasteboard = m.b(data29);
                    if (copyToPasteboard != null && copyToPasteboard.containsKey(FirebaseAnalytics.Param.CONTENT)) {
                        k.b(activity11, copyToPasteboard.get(FirebaseAnalytics.Param.CONTENT).toString());
                    }
                    o(callbackKey);
                    WVJBWebView wVJBWebView35 = webView21;
                    activity2 = activity11;
                    String str68 = data29;
                    break;
                case '7':
                    WVJBWebView webView22 = webView3;
                    String data30 = data2;
                    Activity activity12 = activity5;
                    try {
                        Intent settingsIntent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                        settingsIntent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                        activity12.startActivity(settingsIntent);
                        WVJBWebView wVJBWebView36 = webView22;
                        activity2 = activity12;
                        String str69 = data30;
                        break;
                    } catch (Exception e46) {
                        Exception ex = e46;
                        com.leedarson.base.logger.a.c("SystemServiceImpl", "打开gps系统设置权限失败" + ex.toString());
                        ex.printStackTrace();
                        k.g(this.a);
                        WVJBWebView wVJBWebView37 = webView22;
                        activity2 = activity12;
                        String str70 = data30;
                        break;
                    }
                case '8':
                    try {
                        JSONObject conObj = new JSONObject(data2).getJSONObject("data");
                        if ("native".equals(conObj.getString(IjkMediaMeta.IJKM_KEY_TYPE))) {
                            if (!conObj.getString(PictureConfig.EXTRA_PAGE).equals(H5ActionName.LIVE_PAGE)) {
                                try {
                                    if (!conObj.getString(PictureConfig.EXTRA_PAGE).equals(H5ActionName.IPC_SIGNAL_EVENT)) {
                                        webView = webView3;
                                        data = data2;
                                        activity4 = activity5;
                                        o(callbackKey);
                                        WVJBWebView wVJBWebView38 = webView;
                                        activity2 = activity4;
                                        String str71 = data;
                                    }
                                } catch (Exception e47) {
                                    webView = webView3;
                                    e6 = e47;
                                    data = data2;
                                    activity4 = activity5;
                                    e6.printStackTrace();
                                    o(callbackKey);
                                    WVJBWebView wVJBWebView382 = webView;
                                    activity2 = activity4;
                                    String str712 = data;
                                    Activity activity62222222 = activity2;
                                }
                            }
                            if (conObj.getString(PictureConfig.EXTRA_PAGE).equals(H5ActionName.IPC_SIGNAL_EVENT)) {
                                data = data2;
                                activity4 = activity5;
                                webView = webView3;
                                try {
                                    ((IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class)).handleData(activity5, callbackKey, "Navigator", H5ActionName.ACTION_PUSH, conObj.toString());
                                } catch (Exception e48) {
                                    e6 = e48;
                                }
                            } else {
                                webView = webView3;
                                data = data2;
                                activity4 = activity5;
                                if (com.leedarson.base.utils.c.h().c().toString().contains("com.leedarson.newui.IpcMainActivity")) {
                                    org.greenrobot.eventbus.c.c().l(new Event("NotificationEvent", callbackKey, action, conObj.toString()));
                                } else {
                                    com.leedarson.base.utils.c.h().g();
                                    IpcService ipcService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
                                    if (ipcService != null) {
                                        ipcService.handleData(activity4, callbackKey, "Navigator", H5ActionName.ACTION_PUSH, conObj.toString());
                                    }
                                }
                            }
                            o(callbackKey);
                            WVJBWebView wVJBWebView3822 = webView;
                            activity2 = activity4;
                            String str7122 = data;
                        } else {
                            webView = webView3;
                            data = data2;
                            activity4 = activity5;
                            try {
                                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, conObj.toString()));
                                new Handler().postDelayed(new c(), 250);
                            } catch (Exception e49) {
                                e49.printStackTrace();
                            }
                            o(callbackKey);
                            WVJBWebView wVJBWebView38222 = webView;
                            activity2 = activity4;
                            String str71222 = data;
                        }
                    } catch (Exception e50) {
                        webView = webView3;
                        data = data2;
                        activity4 = activity5;
                        e6 = e50;
                        e6.printStackTrace();
                        o(callbackKey);
                        WVJBWebView wVJBWebView382222 = webView;
                        activity2 = activity4;
                        String str712222 = data;
                        Activity activity622222222 = activity2;
                    }
                case '9':
                    try {
                        String url3 = new JSONObject(data2).optString("url");
                        try {
                            w.m(this.a, Uri.parse(url3).getQueryParameter("id"));
                        } catch (Exception e51) {
                            e51.printStackTrace();
                            this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url3)).setFlags(SQLiteDatabase.CREATE_IF_NECESSARY));
                        }
                    } catch (JSONException e52) {
                        e52.printStackTrace();
                    }
                    o(callbackKey);
                    String str72 = data2;
                    activity2 = activity5;
                    break;
                case ':':
                    w.J(activity5);
                    o(callbackKey);
                    String str73 = data2;
                    activity2 = activity5;
                    break;
                case ';':
                    try {
                        if (new JSONObject(data2).optInt("status") == 0) {
                            timber.log.a.g(Constants.SERVICE_SYSTEM).a("设置屏幕常亮", new Object[0]);
                            activity5.getWindow().addFlags(128);
                        } else {
                            timber.log.a.g(Constants.SERVICE_SYSTEM).a("关闭屏幕常亮", new Object[0]);
                            activity5.getWindow().clearFlags(128);
                        }
                    } catch (JSONException e53) {
                        e53.printStackTrace();
                    }
                    o(callbackKey);
                    String str74 = data2;
                    activity2 = activity5;
                    break;
                case '<':
                    i imageDownloader = new i(this.a);
                    imageDownloader.j = new d(callbackKey);
                    imageDownloader.j(data2);
                    String str75 = data2;
                    activity2 = activity5;
                    break;
                case '=':
                    try {
                        JSONObject json = new JSONObject(data2);
                        boolean enableFlag = false;
                        if (json.has("enable")) {
                            enableFlag = json.getBoolean("enable");
                        }
                        IpcService ipcService2 = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
                        if (ipcService2 != null && !enableFlag) {
                            ipcService2.dismissNoNetTipView();
                        }
                        SharePreferenceUtils.setPrefBoolean(BaseApplication.b(), "lsd_enable_tip_in_webpage_flag", enableFlag);
                        String str76 = data2;
                        activity2 = activity5;
                        break;
                    } catch (Exception e54) {
                        e54.printStackTrace();
                        String str77 = data2;
                        activity2 = activity5;
                        break;
                    }
                case '>':
                    startBackUpLog(activity5);
                    String str78 = data2;
                    activity2 = activity5;
                    break;
                default:
                    String str79 = data2;
                    activity2 = activity5;
                    o(callbackKey);
                    break;
            }
            Activity activity6222222222 = activity2;
        }
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ String d;

        a(String str, String str2) {
            this.c = str;
            this.d = str2;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8840, new Class[0], Void.TYPE).isSupported) {
                try {
                    String[] str = w.W(this.c);
                    JSONObject jsonObject = new JSONObject();
                    JSONObject jsonIp = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    if (str != null) {
                        for (String s : str) {
                            jsonArray.put((Object) s);
                        }
                    }
                    jsonObject.put("ips", (Object) jsonArray);
                    jsonIp.put("code", (Object) "200");
                    jsonIp.put("data", (Object) jsonObject);
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.d, jsonIp.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class b implements WifiScanner.c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        b(String str) {
            this.a = str;
        }

        public void a(JSONArray array, int code) {
            if (!PatchProxy.proxy(new Object[]{array, new Integer(code)}, this, changeQuickRedirect, false, 8841, new Class[]{JSONArray.class, Integer.TYPE}, Void.TYPE).isSupported) {
                JSONObject joWifiList = new JSONObject();
                joWifiList.put("data", (Object) array);
                joWifiList.put("code", code);
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.a, joWifiList.toString()));
            }
        }
    }

    public class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8842, new Class[0], Void.TYPE).isSupported) {
                org.greenrobot.eventbus.c.c().l(new Event("ToMainNavigatorEvent", "", "", ""));
            }
        }
    }

    public class d implements i.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        d(String str) {
            this.a = str;
        }

        public void onSuccess(String str) {
            if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 8843, new Class[]{String.class}, Void.TYPE).isSupported) {
                SystemServiceImpl.a(SystemServiceImpl.this, this.a);
            }
        }

        public void onError(String msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 8844, new Class[]{String.class}, Void.TYPE).isSupported) {
                SystemServiceImpl.h(SystemServiceImpl.this, this.a, BaseResp.ERR_MSG_SEND_FAIL, msg);
            }
        }
    }

    private void o(String callbackKey) {
        if (!PatchProxy.proxy(new Object[]{callbackKey}, this, changeQuickRedirect, false, 8824, new Class[]{String.class}, Void.TYPE).isSupported) {
            JSONObject jsonObject1 = new JSONObject();
            try {
                jsonObject1.put("code", 200);
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject1.toString()));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void p(String callbackKey, int i, String str) {
        Class<String> cls = String.class;
        Object[] objArr = {callbackKey, new Integer(i), str};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8825, new Class[]{cls, Integer.TYPE, cls}, Void.TYPE).isSupported) {
            JSONObject jsonObject1 = new JSONObject();
            try {
                jsonObject1.put("code", 200);
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject1.toString()));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void n(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 8826, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            activity.finishAndRemoveTask();
        }
    }

    public void init(Context context) {
        this.a = context;
    }

    private boolean j(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 8827, new Class[]{Context.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return DateFormat.is24HourFormat(context);
    }

    private String l(int paramInt) {
        Object[] objArr = {new Integer(paramInt)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 8829, new Class[]{Integer.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return (paramInt & 255) + "." + ((paramInt >> 8) & 255) + "." + ((paramInt >> 16) & 255) + "." + ((paramInt >> 24) & 255);
    }

    public String i(String url, String key, String value) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{url, key, value}, this, changeQuickRedirect, false, 8830, new Class[]{cls, cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(key)) {
            return url;
        }
        return url.replaceAll("(" + key + "=[^&]*)", key + "=" + value);
    }

    private boolean m(Context mContext) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mContext}, this, changeQuickRedirect, false, 8831, new Class[]{Context.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        NetworkInfo activeNetInfo = ((ConnectivityManager) mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        return (activeNetInfo != null && activeNetInfo.getType() == 1) || ((WifiManager) mContext.getSystemService("wifi")).getConnectionInfo() != null;
    }

    public boolean k(String[] perms) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{perms}, this, changeQuickRedirect, false, 8832, new Class[]{String[].class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : EasyPermissions.a(this.a, perms);
    }

    public void goH5Page(String page, JSONObject paramsJson, long delay) {
        Object[] objArr = {page, paramsJson, new Long(delay)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8834, new Class[]{String.class, JSONObject.class, Long.TYPE}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) page);
                if (paramsJson != null) {
                    jsonObject.put("params", (Object) paramsJson);
                }
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, jsonObject.toString()));
                new Handler(Looper.getMainLooper()).postDelayed(new e(), delay);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class e implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8845, new Class[0], Void.TYPE).isSupported) {
                org.greenrobot.eventbus.c.c().l(new Event("ToMainNavigatorEvent", "", "", ""));
            }
        }
    }

    public void logOutApp(String bz) {
        if (!PatchProxy.proxy(new Object[]{bz}, this, changeQuickRedirect, false, 8835, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.utils.c.h().g();
            ((LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class)).reportELK(k.class, "重新引导用户到登陆页--- bz=" + bz, "info", "tokenExpired");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("ref", (Object) bz);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BUSINESS, "onLogout", jsonObject.toString()));
            } catch (JSONException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public int getStatusBarHeight(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 8836, new Class[]{Context.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return com.leedarson.serviceimpl.system.notch.b.b(context);
    }

    public void startBackUpLog(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 8837, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            h.j(activity, false);
        }
    }
}
