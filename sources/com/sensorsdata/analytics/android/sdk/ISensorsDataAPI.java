package com.sensorsdata.analytics.android.sdk;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.webkit.WebView;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.deeplink.SensorsDataDeepLinkCallback;
import com.sensorsdata.analytics.android.sdk.internal.api.IFragmentAPI;
import com.sensorsdata.analytics.android.sdk.internal.api.IUserIdentityAPI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

public interface ISensorsDataAPI extends IFragmentAPI, IUserIdentityAPI {
    void addHeatMapActivities(List<Class<?>> list);

    void addHeatMapActivity(Class<?> cls);

    void addVisualizedAutoTrackActivities(List<Class<?>> list);

    void addVisualizedAutoTrackActivity(Class<?> cls);

    void clearGPSLocation();

    void clearLastScreenUrl();

    void clearReferrerWhenAppEnd();

    void clearSuperProperties();

    void clearTrackTimer();

    void deleteAll();

    void disableAutoTrack(SensorsDataAPI.AutoTrackEventType autoTrackEventType);

    void disableAutoTrack(List<SensorsDataAPI.AutoTrackEventType> list);

    void enableAutoTrack(List<SensorsDataAPI.AutoTrackEventType> list);

    void enableDataCollect();

    void enableDeepLinkInstallSource(boolean z);

    void enableLog(boolean z);

    void enableNetworkRequest(boolean z);

    void enableTrackScreenOrientation(boolean z);

    void flush();

    void flushScheduled();

    void flushSync();

    String getAnonymousId();

    String getCookie(boolean z);

    String getDistinctId();

    int getFlushBulkSize();

    int getFlushInterval();

    List<Class> getIgnoredViewTypeList();

    JSONObject getLastScreenTrackProperties();

    String getLastScreenUrl();

    String getLoginId();

    long getMaxCacheSize();

    JSONObject getPresetProperties();

    String getScreenOrientation();

    String getServerUrl();

    int getSessionIntervalTime();

    JSONObject getSuperProperties();

    void identify(String str);

    void ignoreAutoTrackActivities(List<Class<?>> list);

    void ignoreAutoTrackActivity(Class<?> cls);

    void ignoreView(View view);

    void ignoreView(View view, boolean z);

    void ignoreViewType(Class cls);

    boolean isActivityAutoTrackAppClickIgnored(Class<?> cls);

    boolean isActivityAutoTrackAppViewScreenIgnored(Class<?> cls);

    boolean isAutoTrackEnabled();

    boolean isAutoTrackEventTypeIgnored(int i);

    boolean isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType autoTrackEventType);

    boolean isDebugMode();

    boolean isHeatMapActivity(Class<?> cls);

    boolean isHeatMapEnabled();

    boolean isNetworkRequestEnable();

    boolean isVisualizedAutoTrackActivity(Class<?> cls);

    boolean isVisualizedAutoTrackEnabled();

    void itemDelete(String str, String str2);

    void itemSet(String str, String str2, JSONObject jSONObject);

    void login(String str);

    void login(String str, JSONObject jSONObject);

    void logout();

    void profileAppend(String str, String str2);

    void profileAppend(String str, Set<String> set);

    void profileDelete();

    void profileIncrement(String str, Number number);

    void profileIncrement(Map<String, ? extends Number> map);

    void profilePushId(String str, String str2);

    void profileSet(String str, Object obj);

    void profileSet(JSONObject jSONObject);

    void profileSetOnce(String str, Object obj);

    void profileSetOnce(JSONObject jSONObject);

    void profileUnset(String str);

    void profileUnsetPushId(String str);

    void registerDynamicSuperProperties(SensorsDataDynamicSuperProperties sensorsDataDynamicSuperProperties);

    void registerSuperProperties(JSONObject jSONObject);

    void removeTimer(String str);

    @Deprecated
    void resetAnonymousId();

    void resumeAutoTrackActivities(List<Class<?>> list);

    void resumeAutoTrackActivity(Class<?> cls);

    void resumeTrackScreenOrientation();

    void setCookie(String str, boolean z);

    void setDeepLinkCallback(SensorsDataDeepLinkCallback sensorsDataDeepLinkCallback);

    void setFlushBulkSize(int i);

    void setFlushInterval(int i);

    void setFlushNetworkPolicy(int i);

    void setGPSLocation(double d, double d2);

    void setGPSLocation(double d, double d2, String str);

    void setMaxCacheSize(long j);

    void setServerUrl(String str);

    void setServerUrl(String str, boolean z);

    void setSessionIntervalTime(int i);

    void setTrackEventCallBack(SensorsDataTrackEventCallBack sensorsDataTrackEventCallBack);

    void setViewActivity(View view, Activity activity);

    void setViewFragmentName(View view, String str);

    void setViewID(Dialog dialog, String str);

    void setViewID(View view, String str);

    void setViewID(Object obj, String str);

    void setViewProperties(View view, JSONObject jSONObject);

    @Deprecated
    void showUpWebView(WebView webView, JSONObject jSONObject, boolean z, boolean z2);

    void showUpWebView(WebView webView, boolean z);

    @Deprecated
    void showUpWebView(WebView webView, boolean z, JSONObject jSONObject);

    void showUpWebView(WebView webView, boolean z, boolean z2);

    void showUpX5WebView(Object obj);

    @Deprecated
    void showUpX5WebView(Object obj, JSONObject jSONObject, boolean z, boolean z2);

    void showUpX5WebView(Object obj, boolean z);

    void startTrackThread();

    void stopTrackScreenOrientation();

    void stopTrackThread();

    void track(String str);

    void track(String str, JSONObject jSONObject);

    void trackAppInstall();

    void trackAppInstall(JSONObject jSONObject);

    void trackAppInstall(JSONObject jSONObject, boolean z);

    void trackChannelEvent(String str);

    void trackChannelEvent(String str, JSONObject jSONObject);

    void trackDeepLinkLaunch(String str);

    void trackDeepLinkLaunch(String str, String str2);

    void trackEventFromH5(String str);

    void trackEventFromH5(String str, boolean z);

    void trackInstallation(String str);

    void trackInstallation(String str, JSONObject jSONObject);

    void trackInstallation(String str, JSONObject jSONObject, boolean z);

    @Deprecated
    void trackTimer(String str, TimeUnit timeUnit);

    void trackTimerEnd(String str);

    void trackTimerEnd(String str, JSONObject jSONObject);

    void trackTimerPause(String str);

    void trackTimerResume(String str);

    String trackTimerStart(String str);

    void trackViewAppClick(View view);

    void trackViewAppClick(View view, JSONObject jSONObject);

    void trackViewScreen(Activity activity);

    void trackViewScreen(Object obj);

    void trackViewScreen(String str, JSONObject jSONObject);

    void unregisterSuperProperty(String str);
}
