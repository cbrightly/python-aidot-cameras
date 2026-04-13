package com.sensorsdata.analytics.android.sdk;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.webkit.WebView;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.deeplink.SensorsDataDeepLinkCallback;
import com.sensorsdata.analytics.android.sdk.internal.beans.EventType;
import com.sensorsdata.analytics.android.sdk.listener.SAJSListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

public class SensorsDataAPIEmptyImplementation extends SensorsDataAPI {
    SensorsDataAPIEmptyImplementation() {
    }

    public JSONObject getPresetProperties() {
        return new JSONObject();
    }

    public void enableAutoTrackFragment(Class<?> cls) {
    }

    public void enableAutoTrackFragments(List<Class<?>> list) {
    }

    public void ignoreAutoTrackFragments(List<Class<?>> list) {
    }

    public void ignoreAutoTrackFragment(Class<?> cls) {
    }

    public void resumeIgnoredAutoTrackFragments(List<Class<?>> list) {
    }

    public void resumeIgnoredAutoTrackFragment(Class<?> cls) {
    }

    public boolean isFragmentAutoTrackAppViewScreen(Class<?> cls) {
        return false;
    }

    public String getServerUrl() {
        return null;
    }

    public void setServerUrl(String serverUrl) {
    }

    public void setServerUrl(String serverUrl, boolean isRequestRemoteConfig) {
    }

    public void enableLog(boolean enable) {
    }

    public boolean isDebugMode() {
        return false;
    }

    public long getMaxCacheSize() {
        return 33554432;
    }

    public void setMaxCacheSize(long maxCacheSize) {
    }

    public void setFlushNetworkPolicy(int networkType) {
    }

    public int getFlushInterval() {
        return 15000;
    }

    public void setFlushInterval(int flushInterval) {
    }

    public int getFlushBulkSize() {
        return 100;
    }

    public void setFlushBulkSize(int flushBulkSize) {
    }

    public int getSessionIntervalTime() {
        return 30000;
    }

    public void setSessionIntervalTime(int sessionIntervalTime) {
    }

    public void enableAutoTrack(List<SensorsDataAPI.AutoTrackEventType> list) {
    }

    public void disableAutoTrack(List<SensorsDataAPI.AutoTrackEventType> list) {
    }

    public void disableAutoTrack(SensorsDataAPI.AutoTrackEventType autoTrackEventType) {
    }

    public boolean isAutoTrackEnabled() {
        return false;
    }

    public void trackFragmentAppViewScreen() {
    }

    public boolean isTrackFragmentAppViewScreenEnabled() {
        return false;
    }

    public void showUpWebView(WebView webView, boolean isSupportJellyBean) {
    }

    public void showUpWebView(WebView webView, boolean isSupportJellyBean, boolean enableVerify) {
    }

    @Deprecated
    public void showUpWebView(WebView webView, JSONObject properties, boolean isSupportJellyBean, boolean enableVerify) {
    }

    @Deprecated
    public void showUpWebView(WebView webView, boolean isSupportJellyBean, JSONObject properties) {
    }

    @Deprecated
    public void showUpX5WebView(Object x5WebView, JSONObject properties, boolean isSupportJellyBean, boolean enableVerify) {
    }

    public void showUpX5WebView(Object x5WebView, boolean enableVerify) {
    }

    public void showUpX5WebView(Object x5WebView) {
    }

    public void ignoreAutoTrackActivities(List<Class<?>> list) {
    }

    public void resumeAutoTrackActivities(List<Class<?>> list) {
    }

    public void ignoreAutoTrackActivity(Class<?> cls) {
    }

    public void resumeAutoTrackActivity(Class<?> cls) {
    }

    public boolean isActivityAutoTrackAppViewScreenIgnored(Class<?> cls) {
        return true;
    }

    public boolean isActivityAutoTrackAppClickIgnored(Class<?> cls) {
        return true;
    }

    public boolean isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType eventType) {
        return true;
    }

    public void setViewID(View view, String viewID) {
    }

    public void setViewID(Dialog view, String viewID) {
    }

    public void setViewID(Object view, String viewID) {
    }

    public void setViewActivity(View view, Activity activity) {
    }

    public void setViewFragmentName(View view, String fragmentName) {
    }

    public void ignoreView(View view) {
    }

    public void ignoreView(View view, boolean ignore) {
    }

    public void setViewProperties(View view, JSONObject properties) {
    }

    public List<Class> getIgnoredViewTypeList() {
        return new ArrayList();
    }

    public void ignoreViewType(Class viewType) {
    }

    public boolean isHeatMapActivity(Class<?> cls) {
        return false;
    }

    public void addHeatMapActivity(Class<?> cls) {
    }

    public void addHeatMapActivities(List<Class<?>> list) {
    }

    public boolean isHeatMapEnabled() {
        return false;
    }

    public String getDistinctId() {
        return null;
    }

    public String getAnonymousId() {
        return null;
    }

    public void resetAnonymousId() {
    }

    public String getLoginId() {
        return null;
    }

    public void identify(String distinctId) {
    }

    public void login(String loginId) {
    }

    public void login(String loginId, JSONObject properties) {
    }

    public void logout() {
    }

    public void trackInstallation(String eventName, JSONObject properties, boolean disableCallback) {
    }

    public void trackInstallation(String eventName, JSONObject properties) {
    }

    public void trackInstallation(String eventName) {
    }

    public void trackAppInstall(JSONObject properties, boolean disableCallback) {
    }

    public void trackAppInstall(JSONObject properties) {
    }

    public void trackAppInstall() {
    }

    public void trackChannelEvent(String eventName) {
    }

    public void trackChannelEvent(String eventName, JSONObject properties) {
    }

    public void track(String eventName, JSONObject properties) {
    }

    public void track(String eventName) {
    }

    public void trackInternal(String eventName, JSONObject properties) {
    }

    @Deprecated
    public void trackTimer(String eventName, TimeUnit timeUnit) {
    }

    public void removeTimer(String eventName) {
    }

    public String trackTimerStart(String eventName) {
        return "";
    }

    public void trackTimerEnd(String eventName, JSONObject properties) {
    }

    public void trackTimerEnd(String eventName) {
    }

    public void clearTrackTimer() {
    }

    public String getLastScreenUrl() {
        return null;
    }

    public void clearReferrerWhenAppEnd() {
    }

    public void clearLastScreenUrl() {
    }

    public JSONObject getLastScreenTrackProperties() {
        return new JSONObject();
    }

    public void trackViewScreen(String url, JSONObject properties) {
    }

    public void trackViewScreen(Activity activity) {
    }

    public void trackViewScreen(Object fragment) {
    }

    public void trackViewAppClick(View view) {
    }

    public void trackViewAppClick(View view, JSONObject properties) {
    }

    public void flush() {
    }

    public void flushSync() {
    }

    public void flushScheduled() {
    }

    public void registerDynamicSuperProperties(SensorsDataDynamicSuperProperties dynamicSuperProperties) {
    }

    public void setTrackEventCallBack(SensorsDataTrackEventCallBack trackEventCallBack) {
    }

    public void setDeepLinkCallback(SensorsDataDeepLinkCallback deepLinkCallback) {
    }

    public void deleteAll() {
    }

    public JSONObject getSuperProperties() {
        return new JSONObject();
    }

    public void registerSuperProperties(JSONObject superProperties) {
    }

    public void unregisterSuperProperty(String superPropertyName) {
    }

    public void clearSuperProperties() {
    }

    public void profileSet(JSONObject properties) {
    }

    public void profileSet(String property, Object value) {
    }

    public void profileSetOnce(JSONObject properties) {
    }

    public void profileSetOnce(String property, Object value) {
    }

    public void profileIncrement(Map<String, ? extends Number> map) {
    }

    public void profileIncrement(String property, Number value) {
    }

    public void profileAppend(String property, String value) {
    }

    public void profileAppend(String property, Set<String> set) {
    }

    public void profileUnset(String property) {
    }

    public void profileDelete() {
    }

    public void trackEventFromH5(String eventInfo, boolean enableVerify) {
    }

    public void trackEventFromH5(String eventInfo) {
    }

    /* access modifiers changed from: protected */
    public void trackEvent(EventType eventType, String eventName, JSONObject properties, String originalDistinctId) {
    }

    public void trackTimerPause(String eventName) {
    }

    public void trackTimerResume(String eventName) {
    }

    public boolean isAutoTrackEventTypeIgnored(int autoTrackEventType) {
        return true;
    }

    public void setDebugMode(SensorsDataAPI.DebugMode debugMode) {
    }

    /* access modifiers changed from: package-private */
    public void enableAutoTrack(int autoTrackEventType) {
    }

    public void appEnterBackground() {
    }

    public void appBecomeActive() {
    }

    public void setGPSLocation(double latitude, double longitude) {
    }

    public void setGPSLocation(double latitude, double longitude, String coordinate) {
    }

    public void clearGPSLocation() {
    }

    public void enableTrackScreenOrientation(boolean enable) {
    }

    public void resumeTrackScreenOrientation() {
    }

    public void stopTrackScreenOrientation() {
    }

    public void setCookie(String cookie, boolean encode) {
    }

    public String getCookie(boolean decode) {
        return null;
    }

    public void profilePushId(String pushTypeKey, String pushId) {
    }

    public void profileUnsetPushId(String pushTypeKey) {
    }

    public boolean isVisualizedAutoTrackActivity(Class<?> cls) {
        return false;
    }

    public void addVisualizedAutoTrackActivity(Class<?> cls) {
    }

    public void addVisualizedAutoTrackActivities(List<Class<?>> list) {
    }

    public boolean isVisualizedAutoTrackEnabled() {
        return false;
    }

    public void itemSet(String itemType, String itemId, JSONObject properties) {
    }

    public void itemDelete(String itemType, String itemId) {
    }

    public void enableNetworkRequest(boolean isRequest) {
    }

    public void startTrackThread() {
    }

    public void stopTrackThread() {
    }

    public void enableDataCollect() {
    }

    public void addSAJSListener(SAJSListener listener) {
    }

    /* access modifiers changed from: package-private */
    public int getFlushNetworkPolicy() {
        return 0;
    }

    public String getScreenOrientation() {
        return "";
    }

    /* access modifiers changed from: package-private */
    public void trackChannelDebugInstallation() {
    }

    public boolean isNetworkRequestEnable() {
        return false;
    }

    public void enableDeepLinkInstallSource(boolean enable) {
    }

    public void trackDeepLinkLaunch(String deepLinkUrl) {
    }

    public void trackDeepLinkLaunch(String deepLinkUrl, String oaid) {
    }
}
