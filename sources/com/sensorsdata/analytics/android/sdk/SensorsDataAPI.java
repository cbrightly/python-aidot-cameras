package com.sensorsdata.analytics.android.sdk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import com.sensorsdata.analytics.android.sdk.advert.utils.ChannelUtils;
import com.sensorsdata.analytics.android.sdk.advert.utils.OaidHelper;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import com.sensorsdata.analytics.android.sdk.deeplink.SensorsDataDeepLinkCallback;
import com.sensorsdata.analytics.android.sdk.encrypt.SensorsDataEncrypt;
import com.sensorsdata.analytics.android.sdk.exceptions.InvalidDataException;
import com.sensorsdata.analytics.android.sdk.internal.api.IUserIdentityAPI;
import com.sensorsdata.analytics.android.sdk.internal.beans.EventTimer;
import com.sensorsdata.analytics.android.sdk.internal.beans.EventType;
import com.sensorsdata.analytics.android.sdk.internal.rpc.SensorsDataContentObserver;
import com.sensorsdata.analytics.android.sdk.listener.SAEventListener;
import com.sensorsdata.analytics.android.sdk.listener.SAFunctionListener;
import com.sensorsdata.analytics.android.sdk.listener.SAJSListener;
import com.sensorsdata.analytics.android.sdk.monitor.TrackMonitor;
import com.sensorsdata.analytics.android.sdk.remote.BaseSensorsDataSDKRemoteManager;
import com.sensorsdata.analytics.android.sdk.util.AopUtil;
import com.sensorsdata.analytics.android.sdk.util.AppInfoUtils;
import com.sensorsdata.analytics.android.sdk.util.JSONUtils;
import com.sensorsdata.analytics.android.sdk.util.SAContextManager;
import com.sensorsdata.analytics.android.sdk.util.SADataHelper;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import com.sensorsdata.analytics.android.sdk.util.TimeUtils;
import com.sensorsdata.analytics.android.sdk.visual.model.ViewNode;
import com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SensorsDataAPI extends AbstractSensorsDataAPI {
    static String ANDROID_PLUGIN_VERSION = "3.4.7";
    static final String MIN_PLUGIN_VERSION = "3.4.0";
    static final String VERSION = "6.2.7";
    public static final int VTRACK_SUPPORTED_MIN_API = 16;

    public /* bridge */ /* synthetic */ void addEventListener(SAEventListener sAEventListener) {
        super.addEventListener(sAEventListener);
    }

    public /* bridge */ /* synthetic */ void addFunctionListener(SAFunctionListener sAFunctionListener) {
        super.addFunctionListener(sAFunctionListener);
    }

    public /* bridge */ /* synthetic */ void addSAJSListener(SAJSListener sAJSListener) {
        super.addSAJSListener(sAJSListener);
    }

    public /* bridge */ /* synthetic */ void appBecomeActive() {
        super.appBecomeActive();
    }

    public /* bridge */ /* synthetic */ void appEnterBackground() {
        super.appEnterBackground();
    }

    public /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public /* bridge */ /* synthetic */ DebugMode getDebugMode() {
        return super.getDebugMode();
    }

    public /* bridge */ /* synthetic */ SensorsDataDeepLinkCallback getDeepLinkCallback() {
        return super.getDeepLinkCallback();
    }

    public /* bridge */ /* synthetic */ BaseSensorsDataSDKRemoteManager getRemoteManager() {
        return super.getRemoteManager();
    }

    public /* bridge */ /* synthetic */ SAContextManager getSAContextManager() {
        return super.getSAContextManager();
    }

    public /* bridge */ /* synthetic */ SensorsDataEncrypt getSensorsDataEncrypt() {
        return super.getSensorsDataEncrypt();
    }

    public /* bridge */ /* synthetic */ boolean isDeepLinkInstallSource() {
        return super.isDeepLinkInstallSource();
    }

    public /* bridge */ /* synthetic */ boolean isDisableDefaultRemoteConfig() {
        return super.isDisableDefaultRemoteConfig();
    }

    public /* bridge */ /* synthetic */ void removeEventListener(SAEventListener sAEventListener) {
        super.removeEventListener(sAEventListener);
    }

    public /* bridge */ /* synthetic */ void removeFunctionListener(SAFunctionListener sAFunctionListener) {
        super.removeFunctionListener(sAFunctionListener);
    }

    public /* bridge */ /* synthetic */ void removeSAJSListener(SAJSListener sAJSListener) {
        super.removeSAJSListener(sAJSListener);
    }

    public /* bridge */ /* synthetic */ void setDebugMode(DebugMode debugMode) {
        super.setDebugMode(debugMode);
    }

    public /* bridge */ /* synthetic */ void setRemoteManager(BaseSensorsDataSDKRemoteManager baseSensorsDataSDKRemoteManager) {
        super.setRemoteManager(baseSensorsDataSDKRemoteManager);
    }

    public /* bridge */ /* synthetic */ void trackAutoEvent(String str, JSONObject jSONObject) {
        super.trackAutoEvent(str, jSONObject);
    }

    public /* bridge */ /* synthetic */ void trackInternal(String str, JSONObject jSONObject) {
        super.trackInternal(str, jSONObject);
    }

    public /* bridge */ /* synthetic */ void trackInternal(String str, JSONObject jSONObject, ViewNode viewNode) {
        super.trackInternal(str, jSONObject, viewNode);
    }

    public /* bridge */ /* synthetic */ void transformTaskQueue(Runnable runnable) {
        super.transformTaskQueue(runnable);
    }

    SensorsDataAPI() {
    }

    SensorsDataAPI(Context context, SAConfigOptions configOptions, DebugMode debugMode) {
        super(context, configOptions, debugMode);
    }

    public static SensorsDataAPI sharedInstance(Context context) {
        if (AbstractSensorsDataAPI.isSDKDisabled()) {
            return new SensorsDataAPIEmptyImplementation();
        }
        if (context == null) {
            return new SensorsDataAPIEmptyImplementation();
        }
        Map<Context, SensorsDataAPI> map = AbstractSensorsDataAPI.sInstanceMap;
        synchronized (map) {
            SensorsDataAPI instance = map.get(context.getApplicationContext());
            if (instance != null) {
                return instance;
            }
            SALog.i("SA.SensorsDataAPI", "The static method sharedInstance(context, serverURL, debugMode) should be called before calling sharedInstance()");
            SensorsDataAPIEmptyImplementation sensorsDataAPIEmptyImplementation = new SensorsDataAPIEmptyImplementation();
            return sensorsDataAPIEmptyImplementation;
        }
    }

    public static void startWithConfigOptions(Context context, SAConfigOptions saConfigOptions) {
        if (context == null || saConfigOptions == null) {
            throw new NullPointerException("Context、SAConfigOptions 不可以为 null");
        }
        SensorsDataAPI sensorsDataAPI = getInstance(context, DebugMode.DEBUG_OFF, saConfigOptions);
        if (!sensorsDataAPI.mSDKConfigInit) {
            sensorsDataAPI.applySAConfigOptions();
        }
    }

    private static SensorsDataAPI getInstance(Context context, DebugMode debugMode, SAConfigOptions saConfigOptions) {
        SensorsDataAPI instance;
        if (context == null) {
            return new SensorsDataAPIEmptyImplementation();
        }
        Map<Context, SensorsDataAPI> map = AbstractSensorsDataAPI.sInstanceMap;
        synchronized (map) {
            Context appContext = context.getApplicationContext();
            instance = map.get(appContext);
            if (instance == null) {
                instance = new SensorsDataAPI(appContext, saConfigOptions, debugMode);
                map.put(appContext, instance);
                if (context instanceof Activity) {
                    instance.delayExecution((Activity) context);
                }
            }
        }
        return instance;
    }

    private static SensorsDataAPI getSDKInstance() {
        Map<Context, SensorsDataAPI> map = AbstractSensorsDataAPI.sInstanceMap;
        synchronized (map) {
            if (map.size() > 0) {
                Iterator<SensorsDataAPI> iterator = map.values().iterator();
                if (iterator.hasNext()) {
                    SensorsDataAPI next = iterator.next();
                    return next;
                }
            }
            SensorsDataAPIEmptyImplementation sensorsDataAPIEmptyImplementation = new SensorsDataAPIEmptyImplementation();
            return sensorsDataAPIEmptyImplementation;
        }
    }

    public static SensorsDataAPI sharedInstance() {
        if (AbstractSensorsDataAPI.isSDKDisabled()) {
            return new SensorsDataAPIEmptyImplementation();
        }
        return getSDKInstance();
    }

    public static void disableSDK() {
        SALog.i("SA.SensorsDataAPI", "call static function disableSDK");
        try {
            final SensorsDataAPI sensorsDataAPI = sharedInstance();
            if (!(sensorsDataAPI instanceof SensorsDataAPIEmptyImplementation) && AbstractSensorsDataAPI.getConfigOptions() != null) {
                if (!AbstractSensorsDataAPI.getConfigOptions().isDisableSDK) {
                    final boolean isFromObserver = !SensorsDataContentObserver.isDisableFromObserver;
                    sensorsDataAPI.transformTaskQueue(new Runnable() {
                        public void run() {
                            if (isFromObserver) {
                                sensorsDataAPI.trackInternal("$AppDataTrackingClose", (JSONObject) null);
                            }
                        }
                    });
                    if (sensorsDataAPI.isNetworkRequestEnable()) {
                        sensorsDataAPI.enableNetworkRequest(false);
                        AbstractSensorsDataAPI.isChangeEnableNetworkFlag = true;
                    } else {
                        AbstractSensorsDataAPI.isChangeEnableNetworkFlag = false;
                    }
                    sensorsDataAPI.unregisterNetworkListener();
                    sensorsDataAPI.clearTrackTimer();
                    DbAdapter.getInstance().commitAppStartTime(0);
                    AbstractSensorsDataAPI.getConfigOptions().disableSDK(true);
                    SALog.setDisableSDK(true);
                    if (!SensorsDataContentObserver.isDisableFromObserver) {
                        sensorsDataAPI.getContext().getContentResolver().notifyChange(DbParams.getInstance().getDisableSDKUri(), (ContentObserver) null);
                    }
                    SensorsDataContentObserver.isDisableFromObserver = false;
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public static void enableSDK() {
        SALog.i("SA.SensorsDataAPI", "call static function enableSDK");
        try {
            SensorsDataAPI sensorsDataAPI = getSDKInstance();
            if (!(sensorsDataAPI instanceof SensorsDataAPIEmptyImplementation) && AbstractSensorsDataAPI.getConfigOptions() != null) {
                if (AbstractSensorsDataAPI.getConfigOptions().isDisableSDK) {
                    AbstractSensorsDataAPI.getConfigOptions().disableSDK(false);
                    try {
                        SALog.setDisableSDK(false);
                        sensorsDataAPI.enableLog(SALog.isLogEnabled());
                        SALog.i("SA.SensorsDataAPI", "enableSDK, enable log");
                        if (sensorsDataAPI.mFirstDay.get() == null) {
                            sensorsDataAPI.mFirstDay.commit(TimeUtils.formatTime(System.currentTimeMillis(), TimeUtils.YYYY_MM_DD));
                        }
                        sensorsDataAPI.delayInitTask();
                        if (AbstractSensorsDataAPI.isChangeEnableNetworkFlag) {
                            sensorsDataAPI.enableNetworkRequest(true);
                            AbstractSensorsDataAPI.isChangeEnableNetworkFlag = false;
                        }
                        if (AbstractSensorsDataAPI.getConfigOptions().isVisualizedPropertiesEnabled()) {
                            VisualPropertiesManager.getInstance().requestVisualConfig();
                        }
                        sensorsDataAPI.getRemoteManager().pullSDKConfigFromServer();
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                    if (!SensorsDataContentObserver.isEnableFromObserver) {
                        sensorsDataAPI.getContext().getContentResolver().notifyChange(DbParams.getInstance().getEnableSDKUri(), (ContentObserver) null);
                    }
                    SensorsDataContentObserver.isEnableFromObserver = false;
                }
            }
        } catch (Exception e2) {
            SALog.printStackTrace(e2);
        }
    }

    public JSONObject getPresetProperties() {
        JSONObject properties = new JSONObject();
        try {
            properties = this.mSAContextManager.getPresetProperties();
            properties.put("$is_first_day", isFirstDay(System.currentTimeMillis()));
            return properties;
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return properties;
        }
    }

    public void enableLog(boolean enable) {
        SALog.setEnableLog(enable);
    }

    public long getMaxCacheSize() {
        return AbstractSensorsDataAPI.mSAConfigOptions.mMaxCacheSize;
    }

    public void setMaxCacheSize(long maxCacheSize) {
        AbstractSensorsDataAPI.mSAConfigOptions.setMaxCacheSize(maxCacheSize);
    }

    public void setFlushNetworkPolicy(int networkType) {
        AbstractSensorsDataAPI.mSAConfigOptions.setNetworkTypePolicy(networkType);
    }

    /* access modifiers changed from: package-private */
    public int getFlushNetworkPolicy() {
        return AbstractSensorsDataAPI.mSAConfigOptions.mNetworkTypePolicy;
    }

    public int getFlushInterval() {
        return AbstractSensorsDataAPI.mSAConfigOptions.mFlushInterval;
    }

    public void setFlushInterval(int flushInterval) {
        AbstractSensorsDataAPI.mSAConfigOptions.setFlushInterval(flushInterval);
    }

    public int getFlushBulkSize() {
        return AbstractSensorsDataAPI.mSAConfigOptions.mFlushBulkSize;
    }

    public void setFlushBulkSize(int flushBulkSize) {
        if (flushBulkSize < 0) {
            SALog.i("SA.SensorsDataAPI", "The value of flushBulkSize is invalid");
        }
        AbstractSensorsDataAPI.mSAConfigOptions.setFlushBulkSize(flushBulkSize);
    }

    public int getSessionIntervalTime() {
        return this.mSessionTime;
    }

    public void setSessionIntervalTime(int sessionIntervalTime) {
        if (DbAdapter.getInstance() == null) {
            SALog.i("SA.SensorsDataAPI", "The static method sharedInstance(context, serverURL, debugMode) should be called before calling sharedInstance()");
        } else if (sessionIntervalTime < 10000 || sessionIntervalTime > 300000) {
            SALog.i("SA.SensorsDataAPI", "SessionIntervalTime:" + sessionIntervalTime + " is invalid, session interval time is between 10s and 300s.");
        } else if (sessionIntervalTime != this.mSessionTime) {
            this.mSessionTime = sessionIntervalTime;
            DbAdapter.getInstance().commitSessionIntervalTime(sessionIntervalTime);
        }
    }

    public void setGPSLocation(double latitude, double longitude) {
        setGPSLocation(latitude, longitude, (String) null);
    }

    public void setGPSLocation(double latitude, double longitude, String coordinate) {
        try {
            final double d = latitude;
            final double d2 = longitude;
            final String str = coordinate;
            this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                public void run() {
                    try {
                        if (AbstractSensorsDataAPI.mGPSLocation == null) {
                            AbstractSensorsDataAPI.mGPSLocation = new SensorsDataGPSLocation();
                        }
                        AbstractSensorsDataAPI.mGPSLocation.setLatitude((long) (d * Math.pow(10.0d, 6.0d)));
                        AbstractSensorsDataAPI.mGPSLocation.setLongitude((long) (d2 * Math.pow(10.0d, 6.0d)));
                        AbstractSensorsDataAPI.mGPSLocation.setCoordinate(SADataHelper.assertPropertyValue(str));
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            });
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void clearGPSLocation() {
        try {
            this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                public void run() {
                    AbstractSensorsDataAPI.mGPSLocation = null;
                }
            });
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void enableTrackScreenOrientation(boolean enable) {
        if (enable) {
            try {
                if (this.mOrientationDetector == null) {
                    this.mOrientationDetector = new SensorsDataScreenOrientationDetector(this.mContext, 3);
                }
                this.mOrientationDetector.enable();
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        } else {
            SensorsDataScreenOrientationDetector sensorsDataScreenOrientationDetector = this.mOrientationDetector;
            if (sensorsDataScreenOrientationDetector != null) {
                sensorsDataScreenOrientationDetector.disable();
                this.mOrientationDetector = null;
            }
        }
    }

    public void resumeTrackScreenOrientation() {
        try {
            SensorsDataScreenOrientationDetector sensorsDataScreenOrientationDetector = this.mOrientationDetector;
            if (sensorsDataScreenOrientationDetector != null) {
                sensorsDataScreenOrientationDetector.enable();
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void stopTrackScreenOrientation() {
        try {
            SensorsDataScreenOrientationDetector sensorsDataScreenOrientationDetector = this.mOrientationDetector;
            if (sensorsDataScreenOrientationDetector != null) {
                sensorsDataScreenOrientationDetector.disable();
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public String getScreenOrientation() {
        try {
            SensorsDataScreenOrientationDetector sensorsDataScreenOrientationDetector = this.mOrientationDetector;
            if (sensorsDataScreenOrientationDetector != null) {
                return sensorsDataScreenOrientationDetector.getOrientation();
            }
            return null;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    public void setCookie(String cookie, boolean encode) {
        if (encode) {
            try {
                this.mCookie = URLEncoder.encode(cookie, "UTF-8");
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        } else {
            this.mCookie = cookie;
        }
    }

    public String getCookie(boolean decode) {
        if (!decode) {
            return this.mCookie;
        }
        try {
            return URLDecoder.decode(this.mCookie, "UTF-8");
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    public void enableAutoTrack(List<AutoTrackEventType> eventTypeList) {
        if (eventTypeList != null) {
            try {
                if (!eventTypeList.isEmpty()) {
                    this.mAutoTrack = true;
                    for (AutoTrackEventType autoTrackEventType : eventTypeList) {
                        SAConfigOptions sAConfigOptions = AbstractSensorsDataAPI.mSAConfigOptions;
                        sAConfigOptions.setAutoTrackEventType(sAConfigOptions.mAutoTrackEventType | autoTrackEventType.eventValue);
                    }
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public void disableAutoTrack(List<AutoTrackEventType> eventTypeList) {
        if (eventTypeList != null) {
            try {
                if (AbstractSensorsDataAPI.mSAConfigOptions.mAutoTrackEventType != 0) {
                    for (AutoTrackEventType autoTrackEventType : eventTypeList) {
                        int access$000 = AbstractSensorsDataAPI.mSAConfigOptions.mAutoTrackEventType | autoTrackEventType.eventValue;
                        SAConfigOptions sAConfigOptions = AbstractSensorsDataAPI.mSAConfigOptions;
                        int i = sAConfigOptions.mAutoTrackEventType;
                        if (access$000 == i) {
                            sAConfigOptions.setAutoTrackEventType(autoTrackEventType.eventValue ^ i);
                        }
                    }
                    if (AbstractSensorsDataAPI.mSAConfigOptions.mAutoTrackEventType == 0) {
                        this.mAutoTrack = false;
                    }
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public void disableAutoTrack(AutoTrackEventType autoTrackEventType) {
        if (autoTrackEventType != null) {
            try {
                int i = AbstractSensorsDataAPI.mSAConfigOptions.mAutoTrackEventType;
                if (i != 0) {
                    int union = i | autoTrackEventType.eventValue;
                    if (union == autoTrackEventType.eventValue) {
                        AbstractSensorsDataAPI.mSAConfigOptions.setAutoTrackEventType(0);
                    } else {
                        AbstractSensorsDataAPI.mSAConfigOptions.setAutoTrackEventType(autoTrackEventType.eventValue ^ union);
                    }
                    if (AbstractSensorsDataAPI.mSAConfigOptions.mAutoTrackEventType == 0) {
                        this.mAutoTrack = false;
                    }
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public boolean isAutoTrackEnabled() {
        Boolean isAutoTrackEnabled;
        if (AbstractSensorsDataAPI.isSDKDisabled()) {
            return false;
        }
        BaseSensorsDataSDKRemoteManager baseSensorsDataSDKRemoteManager = this.mRemoteManager;
        if (baseSensorsDataSDKRemoteManager == null || (isAutoTrackEnabled = baseSensorsDataSDKRemoteManager.isAutoTrackEnabled()) == null) {
            return this.mAutoTrack;
        }
        return isAutoTrackEnabled.booleanValue();
    }

    public void trackFragmentAppViewScreen() {
        this.mFragmentAPI.trackFragmentAppViewScreen();
    }

    public boolean isTrackFragmentAppViewScreenEnabled() {
        return this.mFragmentAPI.isTrackFragmentAppViewScreenEnabled();
    }

    public void showUpWebView(WebView webView, boolean isSupportJellyBean) {
        showUpWebView(webView, isSupportJellyBean, (JSONObject) null);
    }

    public void showUpWebView(WebView webView, boolean isSupportJellyBean, boolean enableVerify) {
        showUpWebView(webView, (JSONObject) null, isSupportJellyBean, enableVerify);
    }

    @Deprecated
    public void showUpWebView(WebView webView, JSONObject properties, boolean isSupportJellyBean, boolean enableVerify) {
        if (Build.VERSION.SDK_INT < 17 && !isSupportJellyBean) {
            SALog.d("SA.SensorsDataAPI", "For applications targeted to API level JELLY_BEAN or below, this feature NOT SUPPORTED");
        } else if (webView != null) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.addJavascriptInterface(new AppWebViewInterface(this.mContext, properties, enableVerify, webView), "SensorsData_APP_JS_Bridge");
            SensorsDataAutoTrackHelper.addWebViewVisualInterface(webView);
        }
    }

    @Deprecated
    public void showUpWebView(WebView webView, boolean isSupportJellyBean, JSONObject properties) {
        showUpWebView(webView, properties, isSupportJellyBean, false);
    }

    @Deprecated
    public void showUpX5WebView(Object x5WebView, JSONObject properties, boolean isSupportJellyBean, boolean enableVerify) {
        Method addJavascriptInterface;
        try {
            if (Build.VERSION.SDK_INT < 17 && !isSupportJellyBean) {
                SALog.d("SA.SensorsDataAPI", "For applications targeted to API level JELLY_BEAN or below, this feature NOT SUPPORTED");
            } else if (x5WebView != null && (addJavascriptInterface = x5WebView.getClass().getMethod("addJavascriptInterface", new Class[]{Object.class, String.class})) != null) {
                addJavascriptInterface.invoke(x5WebView, new Object[]{new AppWebViewInterface(this.mContext, properties, enableVerify), "SensorsData_APP_JS_Bridge"});
                SensorsDataAutoTrackHelper.addWebViewVisualInterface((View) x5WebView);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void showUpX5WebView(Object x5WebView, boolean enableVerify) {
        if (x5WebView != null) {
            try {
                Method addJavascriptInterface = x5WebView.getClass().getMethod("addJavascriptInterface", new Class[]{Object.class, String.class});
                if (addJavascriptInterface != null) {
                    addJavascriptInterface.invoke(x5WebView, new Object[]{new AppWebViewInterface(this.mContext, (JSONObject) null, enableVerify, (View) x5WebView), "SensorsData_APP_JS_Bridge"});
                    SensorsDataAutoTrackHelper.addWebViewVisualInterface((View) x5WebView);
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public void showUpX5WebView(Object x5WebView) {
        showUpX5WebView(x5WebView, false);
    }

    public void ignoreAutoTrackActivities(List<Class<?>> activitiesList) {
        if (activitiesList != null && activitiesList.size() != 0) {
            if (this.mAutoTrackIgnoredActivities == null) {
                this.mAutoTrackIgnoredActivities = new ArrayList();
            }
            for (Class<?> activity : activitiesList) {
                if (activity != null) {
                    int hashCode = activity.hashCode();
                    if (!this.mAutoTrackIgnoredActivities.contains(Integer.valueOf(hashCode))) {
                        this.mAutoTrackIgnoredActivities.add(Integer.valueOf(hashCode));
                    }
                }
            }
        }
    }

    public void resumeAutoTrackActivities(List<Class<?>> activitiesList) {
        if (activitiesList != null && activitiesList.size() != 0) {
            if (this.mAutoTrackIgnoredActivities == null) {
                this.mAutoTrackIgnoredActivities = new ArrayList();
            }
            try {
                for (Class activity : activitiesList) {
                    if (activity != null) {
                        int hashCode = activity.hashCode();
                        if (this.mAutoTrackIgnoredActivities.contains(Integer.valueOf(hashCode))) {
                            this.mAutoTrackIgnoredActivities.remove(Integer.valueOf(hashCode));
                        }
                    }
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public void ignoreAutoTrackActivity(Class<?> activity) {
        if (activity != null) {
            if (this.mAutoTrackIgnoredActivities == null) {
                this.mAutoTrackIgnoredActivities = new ArrayList();
            }
            try {
                int hashCode = activity.hashCode();
                if (!this.mAutoTrackIgnoredActivities.contains(Integer.valueOf(hashCode))) {
                    this.mAutoTrackIgnoredActivities.add(Integer.valueOf(hashCode));
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public void resumeAutoTrackActivity(Class<?> activity) {
        if (activity != null) {
            if (this.mAutoTrackIgnoredActivities == null) {
                this.mAutoTrackIgnoredActivities = new ArrayList();
            }
            try {
                int hashCode = activity.hashCode();
                if (this.mAutoTrackIgnoredActivities.contains(Integer.valueOf(hashCode))) {
                    this.mAutoTrackIgnoredActivities.remove(Integer.valueOf(hashCode));
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public void enableAutoTrackFragment(Class<?> fragment) {
        this.mFragmentAPI.enableAutoTrackFragment(fragment);
    }

    public void enableAutoTrackFragments(List<Class<?>> fragmentsList) {
        this.mFragmentAPI.enableAutoTrackFragments(fragmentsList);
    }

    public boolean isActivityAutoTrackAppViewScreenIgnored(Class<?> activity) {
        if (activity == null) {
            return false;
        }
        List<Integer> list = this.mAutoTrackIgnoredActivities;
        if ((list != null && list.contains(Integer.valueOf(activity.hashCode()))) || activity.getAnnotation(SensorsDataIgnoreTrackAppViewScreenAndAppClick.class) != null) {
            return true;
        }
        if (activity.getAnnotation(SensorsDataIgnoreTrackAppViewScreen.class) != null) {
            return true;
        }
        return false;
    }

    public boolean isFragmentAutoTrackAppViewScreen(Class<?> fragment) {
        return this.mFragmentAPI.isFragmentAutoTrackAppViewScreen(fragment);
    }

    public void ignoreAutoTrackFragments(List<Class<?>> fragmentList) {
        this.mFragmentAPI.ignoreAutoTrackFragments(fragmentList);
    }

    public void ignoreAutoTrackFragment(Class<?> fragment) {
        this.mFragmentAPI.ignoreAutoTrackFragment(fragment);
    }

    public void resumeIgnoredAutoTrackFragments(List<Class<?>> fragmentList) {
        this.mFragmentAPI.resumeIgnoredAutoTrackFragments(fragmentList);
    }

    public void resumeIgnoredAutoTrackFragment(Class<?> fragment) {
        this.mFragmentAPI.resumeIgnoredAutoTrackFragment(fragment);
    }

    public boolean isActivityAutoTrackAppClickIgnored(Class<?> activity) {
        if (activity == null) {
            return false;
        }
        List<Integer> list = this.mAutoTrackIgnoredActivities;
        if ((list != null && list.contains(Integer.valueOf(activity.hashCode()))) || activity.getAnnotation(SensorsDataIgnoreTrackAppViewScreenAndAppClick.class) != null) {
            return true;
        }
        if (activity.getAnnotation(SensorsDataIgnoreTrackAppClick.class) != null) {
            return true;
        }
        return false;
    }

    public boolean isAutoTrackEventTypeIgnored(AutoTrackEventType eventType) {
        if (eventType == null) {
            return false;
        }
        return isAutoTrackEventTypeIgnored(eventType.eventValue);
    }

    public boolean isAutoTrackEventTypeIgnored(int autoTrackEventType) {
        Boolean isIgnored;
        BaseSensorsDataSDKRemoteManager baseSensorsDataSDKRemoteManager = this.mRemoteManager;
        if (baseSensorsDataSDKRemoteManager == null || (isIgnored = baseSensorsDataSDKRemoteManager.isAutoTrackEventTypeIgnored(autoTrackEventType)) == null) {
            int i = AbstractSensorsDataAPI.mSAConfigOptions.mAutoTrackEventType;
            return (i | autoTrackEventType) != i;
        }
        if (isIgnored.booleanValue()) {
            SALog.i("SA.SensorsDataAPI", "remote config: " + AutoTrackEventType.autoTrackEventName(autoTrackEventType) + " is ignored by remote config");
        }
        return isIgnored.booleanValue();
    }

    public void setViewID(View view, String viewID) {
        if (view != null && !TextUtils.isEmpty(viewID)) {
            view.setTag(R.id.sensors_analytics_tag_view_id, viewID);
        }
    }

    public void setViewID(Dialog view, String viewID) {
        if (view != null) {
            try {
                if (!TextUtils.isEmpty(viewID) && view.getWindow() != null) {
                    view.getWindow().getDecorView().setTag(R.id.sensors_analytics_tag_view_id, viewID);
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public void setViewID(Object alertDialog, String viewID) {
        Class<?> currentAlertDialogClass;
        Method getWindowMethod;
        Window window;
        if (alertDialog != null) {
            Class<?> supportAlertDialogClass = null;
            Class<?> androidXAlertDialogClass = null;
            try {
                supportAlertDialogClass = Class.forName("androidx.appcompat.app.AlertDialog");
            } catch (Exception e) {
            }
            try {
                androidXAlertDialogClass = Class.forName("androidx.appcompat.app.AlertDialog");
            } catch (Exception e2) {
            }
            if (supportAlertDialogClass != null) {
                currentAlertDialogClass = supportAlertDialogClass;
            } else {
                currentAlertDialogClass = androidXAlertDialogClass;
            }
            if (currentAlertDialogClass != null) {
                try {
                    if (currentAlertDialogClass.isInstance(alertDialog) && !TextUtils.isEmpty(viewID) && (getWindowMethod = alertDialog.getClass().getMethod("getWindow", new Class[0])) != null && (window = (Window) getWindowMethod.invoke(alertDialog, new Object[0])) != null) {
                        window.getDecorView().setTag(R.id.sensors_analytics_tag_view_id, viewID);
                    }
                } catch (Exception e3) {
                    SALog.printStackTrace(e3);
                }
            }
        }
    }

    public void setViewActivity(View view, Activity activity) {
        if (view != null && activity != null) {
            try {
                view.setTag(R.id.sensors_analytics_tag_view_activity, activity);
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public void setViewFragmentName(View view, String fragmentName) {
        if (view != null) {
            try {
                if (!TextUtils.isEmpty(fragmentName)) {
                    view.setTag(R.id.sensors_analytics_tag_view_fragment_name2, fragmentName);
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public void ignoreView(View view) {
        if (view != null) {
            view.setTag(R.id.sensors_analytics_tag_view_ignored, "1");
        }
    }

    public void ignoreView(View view, boolean ignore) {
        if (view != null) {
            view.setTag(R.id.sensors_analytics_tag_view_ignored, ignore ? "1" : "0");
        }
    }

    public void setViewProperties(View view, JSONObject properties) {
        if (view != null && properties != null) {
            view.setTag(R.id.sensors_analytics_tag_view_properties, properties);
        }
    }

    public List<Class> getIgnoredViewTypeList() {
        if (this.mIgnoredViewTypeList == null) {
            this.mIgnoredViewTypeList = new ArrayList();
        }
        return this.mIgnoredViewTypeList;
    }

    public void ignoreViewType(Class viewType) {
        if (viewType != null) {
            if (this.mIgnoredViewTypeList == null) {
                this.mIgnoredViewTypeList = new ArrayList();
            }
            if (!this.mIgnoredViewTypeList.contains(viewType)) {
                this.mIgnoredViewTypeList.add(viewType);
            }
        }
    }

    public boolean isVisualizedAutoTrackActivity(Class<?> activity) {
        if (activity == null) {
            return false;
        }
        try {
            if (this.mVisualizedAutoTrackActivities.size() != 0 && !this.mVisualizedAutoTrackActivities.contains(Integer.valueOf(activity.hashCode()))) {
                return false;
            }
            return true;
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void addVisualizedAutoTrackActivity(Class<?> activity) {
        if (activity != null) {
            try {
                this.mVisualizedAutoTrackActivities.add(Integer.valueOf(activity.hashCode()));
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public void addVisualizedAutoTrackActivities(List<Class<?>> activitiesList) {
        if (activitiesList != null) {
            try {
                if (activitiesList.size() != 0) {
                    for (Class<?> activity : activitiesList) {
                        if (activity != null) {
                            int hashCode = activity.hashCode();
                            if (!this.mVisualizedAutoTrackActivities.contains(Integer.valueOf(hashCode))) {
                                this.mVisualizedAutoTrackActivities.add(Integer.valueOf(hashCode));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public boolean isVisualizedAutoTrackEnabled() {
        SAConfigOptions sAConfigOptions = AbstractSensorsDataAPI.mSAConfigOptions;
        return sAConfigOptions.mVisualizedEnabled || sAConfigOptions.mVisualizedPropertiesEnabled;
    }

    public boolean isHeatMapActivity(Class<?> activity) {
        if (activity == null) {
            return false;
        }
        try {
            if (this.mHeatMapActivities.size() != 0 && !this.mHeatMapActivities.contains(Integer.valueOf(activity.hashCode()))) {
                return false;
            }
            return true;
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void addHeatMapActivity(Class<?> activity) {
        if (activity != null) {
            try {
                this.mHeatMapActivities.add(Integer.valueOf(activity.hashCode()));
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public void addHeatMapActivities(List<Class<?>> activitiesList) {
        if (activitiesList != null) {
            try {
                if (activitiesList.size() != 0) {
                    for (Class<?> activity : activitiesList) {
                        if (activity != null) {
                            int hashCode = activity.hashCode();
                            if (!this.mHeatMapActivities.contains(Integer.valueOf(hashCode))) {
                                this.mHeatMapActivities.add(Integer.valueOf(hashCode));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public boolean isHeatMapEnabled() {
        return AbstractSensorsDataAPI.mSAConfigOptions.mHeatMapEnabled;
    }

    public String getDistinctId() {
        try {
            return this.mUserIdentityAPI.getDistinctId();
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public String getAnonymousId() {
        try {
            return this.mUserIdentityAPI.getAnonymousId();
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public void resetAnonymousId() {
        try {
            this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                public void run() {
                    SensorsDataAPI.this.mUserIdentityAPI.resetAnonymousId();
                }
            });
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public String getLoginId() {
        try {
            return this.mUserIdentityAPI.getLoginId();
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return "";
        }
    }

    public void identify(final String distinctId) {
        try {
            SADataHelper.assertDistinctId(distinctId);
            this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                public void run() {
                    try {
                        SensorsDataAPI.this.mUserIdentityAPI.identify(distinctId);
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            });
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void login(String loginId) {
        login(loginId, (JSONObject) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void login(final java.lang.String r5, org.json.JSONObject r6) {
        /*
            r4 = this;
            org.json.JSONObject r0 = com.sensorsdata.analytics.android.sdk.util.JSONUtils.cloneJsonObject(r6)     // Catch:{ Exception -> 0x0031 }
            com.sensorsdata.analytics.android.sdk.util.SADataHelper.assertDistinctId(r5)     // Catch:{ Exception -> 0x0031 }
            java.lang.Object r1 = r4.mLoginIdLock     // Catch:{ Exception -> 0x0031 }
            monitor-enter(r1)     // Catch:{ Exception -> 0x0031 }
            java.lang.String r2 = r4.getAnonymousId()     // Catch:{ all -> 0x002e }
            boolean r2 = r5.equals(r2)     // Catch:{ all -> 0x002e }
            if (r2 != 0) goto L_0x002c
            com.sensorsdata.analytics.android.sdk.internal.api.UserIdentityAPI r2 = r4.mUserIdentityAPI     // Catch:{ all -> 0x002e }
            r2.updateLoginId(r5)     // Catch:{ all -> 0x002e }
            boolean r2 = com.sensorsdata.analytics.android.sdk.internal.rpc.SensorsDataContentObserver.isLoginFromObserver     // Catch:{ all -> 0x002e }
            if (r2 == 0) goto L_0x0022
            r2 = 0
            com.sensorsdata.analytics.android.sdk.internal.rpc.SensorsDataContentObserver.isLoginFromObserver = r2     // Catch:{ all -> 0x002e }
            monitor-exit(r1)     // Catch:{ all -> 0x002e }
            return
        L_0x0022:
            com.sensorsdata.analytics.android.sdk.TrackTaskManager r2 = r4.mTrackTaskManager     // Catch:{ all -> 0x002e }
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI$6 r3 = new com.sensorsdata.analytics.android.sdk.SensorsDataAPI$6     // Catch:{ all -> 0x002e }
            r3.<init>(r5, r0)     // Catch:{ all -> 0x002e }
            r2.addTrackEventTask(r3)     // Catch:{ all -> 0x002e }
        L_0x002c:
            monitor-exit(r1)     // Catch:{ all -> 0x002e }
            goto L_0x0035
        L_0x002e:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x002e }
            throw r2     // Catch:{ Exception -> 0x0031 }
        L_0x0031:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
        L_0x0035:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.SensorsDataAPI.login(java.lang.String, org.json.JSONObject):void");
    }

    public void logout() {
        try {
            this.mUserIdentityAPI.updateLoginId((String) null);
            this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                public void run() {
                    try {
                        SensorsDataAPI.this.mUserIdentityAPI.logout();
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            });
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void bind(final String key, final String value) {
        try {
            this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                public void run() {
                    try {
                        SensorsDataAPI.this.mUserIdentityAPI.bind(key, value);
                        SensorsDataAPI sensorsDataAPI = SensorsDataAPI.this;
                        sensorsDataAPI.trackEvent(EventType.TRACK_ID_BIND, IUserIdentityAPI.BIND_ID, (JSONObject) null, sensorsDataAPI.getAnonymousId());
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            });
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void unbind(final String key, final String value) {
        try {
            this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                public void run() {
                    try {
                        SensorsDataAPI.this.mUserIdentityAPI.unbind(key, value);
                        SensorsDataAPI sensorsDataAPI = SensorsDataAPI.this;
                        sensorsDataAPI.trackEvent(EventType.TRACK_ID_UNBIND, IUserIdentityAPI.UNBIND_ID, (JSONObject) null, sensorsDataAPI.getAnonymousId());
                    } catch (InvalidDataException e) {
                        SALog.printStackTrace(e);
                    }
                }
            });
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void trackInstallation(String eventName, JSONObject properties, boolean disableCallback) {
        try {
            JSONObject eventProperties = new JSONObject();
            if (properties != null) {
                SensorsDataUtils.mergeJSONObject(JSONUtils.cloneJsonObject(properties), eventProperties);
            }
            addTimeProperty(eventProperties);
            final String loginId = getLoginId();
            final boolean z = disableCallback;
            final JSONObject jSONObject = eventProperties;
            final String str = eventName;
            transformTaskQueue(new Runnable() {
                public void run() {
                    boolean firstTrackInstallation;
                    String installSource;
                    String oaid;
                    if (AbstractSensorsDataAPI.mIsMainProcess) {
                        try {
                            if (z) {
                                firstTrackInstallation = ((Boolean) SensorsDataAPI.this.mFirstTrackInstallationWithCallback.get()).booleanValue();
                            } else {
                                firstTrackInstallation = ((Boolean) SensorsDataAPI.this.mFirstTrackInstallation.get()).booleanValue();
                            }
                            if (firstTrackInstallation) {
                                boolean isCorrectTrackInstallation = false;
                                try {
                                    if (!ChannelUtils.hasUtmProperties(jSONObject)) {
                                        ChannelUtils.mergeUtmByMetaData(SensorsDataAPI.this.mContext, jSONObject);
                                    }
                                    if (!ChannelUtils.hasUtmProperties(jSONObject)) {
                                        String androidId = SensorsDataAPI.this.mSAContextManager.getAndroidId();
                                        if (jSONObject.has("$oaid")) {
                                            oaid = jSONObject.optString("$oaid");
                                            installSource = ChannelUtils.getDeviceInfo(SensorsDataAPI.this.mContext, androidId, oaid);
                                            SALog.i("SA.SensorsDataAPI", "properties has oaid " + oaid);
                                        } else {
                                            oaid = OaidHelper.getOAID(SensorsDataAPI.this.mContext);
                                            installSource = ChannelUtils.getDeviceInfo(SensorsDataAPI.this.mContext, androidId, oaid);
                                        }
                                        if (jSONObject.has("$gaid")) {
                                            installSource = String.format("%s##gaid=%s", new Object[]{installSource, jSONObject.optString("$gaid")});
                                        }
                                        isCorrectTrackInstallation = ChannelUtils.isGetDeviceInfo(SensorsDataAPI.this.mContext, androidId, oaid);
                                        jSONObject.put("$ios_install_source", (Object) installSource);
                                    }
                                    if (jSONObject.has("$oaid")) {
                                        jSONObject.remove("$oaid");
                                    }
                                    if (jSONObject.has("$gaid")) {
                                        jSONObject.remove("$gaid");
                                    }
                                    boolean z = z;
                                    if (z) {
                                        jSONObject.put("$ios_install_disable_callback", z);
                                    }
                                } catch (Exception e) {
                                    SALog.printStackTrace(e);
                                }
                                String distinctId = SensorsDataAPI.this.getDistinctId();
                                SensorsDataAPI.this.trackEvent(EventType.TRACK, str, jSONObject, (JSONObject) null, distinctId, loginId, (String) null);
                                JSONObject profileProperties = new JSONObject();
                                jSONObject.remove("$ios_install_disable_callback");
                                SensorsDataUtils.mergeJSONObject(jSONObject, profileProperties);
                                profileProperties.put("$first_visit_time", (Object) new Date());
                                SensorsDataAPI.this.trackEvent(EventType.PROFILE_SET_ONCE, (String) null, profileProperties, (JSONObject) null, distinctId, loginId, (String) null);
                                if (z) {
                                    SensorsDataAPI.this.mFirstTrackInstallationWithCallback.commit(false);
                                } else {
                                    SensorsDataAPI.this.mFirstTrackInstallation.commit(false);
                                }
                                ChannelUtils.saveCorrectTrackInstallation(SensorsDataAPI.this.mContext, isCorrectTrackInstallation);
                            }
                            SensorsDataAPI.this.flush();
                        } catch (Exception e2) {
                            SALog.printStackTrace(e2);
                        }
                    }
                }
            });
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void trackInstallation(String eventName, JSONObject properties) {
        trackInstallation(eventName, properties, false);
    }

    public void trackInstallation(String eventName) {
        trackInstallation(eventName, (JSONObject) null, false);
    }

    public void trackAppInstall(JSONObject properties, boolean disableCallback) {
        trackInstallation("$AppInstall", properties, disableCallback);
    }

    public void trackAppInstall(JSONObject properties) {
        trackAppInstall(properties, false);
    }

    public void trackAppInstall() {
        trackAppInstall((JSONObject) null, false);
    }

    /* access modifiers changed from: package-private */
    public void trackChannelDebugInstallation() {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                try {
                    JSONObject _properties = new JSONObject();
                    SensorsDataAPI sensorsDataAPI = SensorsDataAPI.this;
                    _properties.put("$ios_install_source", (Object) ChannelUtils.getDeviceInfo(sensorsDataAPI.mContext, sensorsDataAPI.mSAContextManager.getAndroidId(), OaidHelper.getOAID(SensorsDataAPI.this.mContext)));
                    SensorsDataAPI.this.trackEvent(EventType.TRACK, "$ChannelDebugInstall", _properties, (String) null);
                    JSONObject profileProperties = new JSONObject();
                    SensorsDataUtils.mergeJSONObject(_properties, profileProperties);
                    profileProperties.put("$first_visit_time", (Object) new Date());
                    SensorsDataAPI.this.trackEvent(EventType.PROFILE_SET_ONCE, (String) null, profileProperties, (String) null);
                    SensorsDataAPI.this.flush();
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public void trackChannelEvent(String eventName) {
        trackChannelEvent(eventName, (JSONObject) null);
    }

    public void trackChannelEvent(final String eventName, JSONObject properties) {
        if (AbstractSensorsDataAPI.getConfigOptions().isAutoAddChannelCallbackEvent) {
            track(eventName, properties);
            return;
        }
        final JSONObject eventProperties = new JSONObject();
        if (properties != null) {
            SensorsDataUtils.mergeJSONObject(properties, eventProperties);
        }
        addTimeProperty(eventProperties);
        transformTaskQueue(new Runnable() {
            public void run() {
                try {
                    eventProperties.put("$is_channel_callback_event", ChannelUtils.isFirstChannelEvent(eventName));
                    if (!ChannelUtils.hasUtmProperties(eventProperties)) {
                        ChannelUtils.mergeUtmByMetaData(SensorsDataAPI.this.mContext, eventProperties);
                    }
                    if (!ChannelUtils.hasUtmProperties(eventProperties)) {
                        if (eventProperties.has("$oaid")) {
                            String oaid = eventProperties.optString("$oaid");
                            JSONObject jSONObject = eventProperties;
                            SensorsDataAPI sensorsDataAPI = SensorsDataAPI.this;
                            jSONObject.put("$channel_device_info", (Object) ChannelUtils.getDeviceInfo(sensorsDataAPI.mContext, sensorsDataAPI.mSAContextManager.getAndroidId(), oaid));
                            SALog.i("SA.SensorsDataAPI", "properties has oaid " + oaid);
                        } else {
                            JSONObject jSONObject2 = eventProperties;
                            SensorsDataAPI sensorsDataAPI2 = SensorsDataAPI.this;
                            jSONObject2.put("$channel_device_info", (Object) ChannelUtils.getDeviceInfo(sensorsDataAPI2.mContext, sensorsDataAPI2.mSAContextManager.getAndroidId(), OaidHelper.getOAID(SensorsDataAPI.this.mContext)));
                        }
                    }
                    if (eventProperties.has("$oaid")) {
                        eventProperties.remove("$oaid");
                    }
                } catch (Exception e) {
                    try {
                        SALog.printStackTrace(e);
                    } catch (Exception e2) {
                        SALog.printStackTrace(e2);
                        return;
                    }
                }
                SensorsDataAPI.this.trackEvent(EventType.TRACK, eventName, eventProperties, (String) null);
            }
        });
    }

    public void track(final String eventName, JSONObject properties) {
        try {
            final JSONObject cloneProperties = JSONUtils.cloneJsonObject(properties);
            final JSONObject dynamicProperty = getDynamicProperty();
            this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                public void run() {
                    JSONObject _properties = ChannelUtils.checkOrSetChannelCallbackEvent(AbstractSensorsDataAPI.getConfigOptions().isAutoAddChannelCallbackEvent, eventName, cloneProperties, SensorsDataAPI.this.mContext);
                    SensorsDataAPI sensorsDataAPI = SensorsDataAPI.this;
                    sensorsDataAPI.trackEvent(EventType.TRACK, eventName, _properties, dynamicProperty, sensorsDataAPI.getDistinctId(), SensorsDataAPI.this.getLoginId(), (String) null);
                }
            });
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void track(String eventName) {
        track(eventName, (JSONObject) null);
    }

    @Deprecated
    public void trackTimer(String eventName, TimeUnit timeUnit) {
        final String str = eventName;
        final TimeUnit timeUnit2 = timeUnit;
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                try {
                    SADataHelper.assertEventName(str);
                    synchronized (SensorsDataAPI.this.mTrackTimer) {
                        SensorsDataAPI.this.mTrackTimer.put(str, new EventTimer(timeUnit2, elapsedRealtime));
                    }
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public void removeTimer(final String eventName) {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                try {
                    SADataHelper.assertEventName(eventName);
                    synchronized (SensorsDataAPI.this.mTrackTimer) {
                        SensorsDataAPI.this.mTrackTimer.remove(eventName);
                    }
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public String trackTimerStart(String eventName) {
        try {
            String eventNameRegex = String.format("%s_%s_%s", new Object[]{eventName, UUID.randomUUID().toString().replace("-", "_"), "SATimer"});
            TimeUnit timeUnit = TimeUnit.SECONDS;
            trackTimer(eventNameRegex, timeUnit);
            trackTimer(eventName, timeUnit);
            return eventNameRegex;
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
            return "";
        }
    }

    public void trackTimerPause(String eventName) {
        trackTimerState(eventName, true);
    }

    public void trackTimerResume(String eventName) {
        trackTimerState(eventName, false);
    }

    public void trackTimerEnd(String eventName, JSONObject properties) {
        long endTime = SystemClock.elapsedRealtime();
        try {
            final JSONObject cloneProperties = JSONUtils.cloneJsonObject(properties);
            final String str = eventName;
            final long j = endTime;
            this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                public void run() {
                    if (str != null) {
                        synchronized (SensorsDataAPI.this.mTrackTimer) {
                            EventTimer eventTimer = SensorsDataAPI.this.mTrackTimer.get(str);
                            if (eventTimer != null) {
                                eventTimer.setEndTime(j);
                            }
                        }
                    }
                    try {
                        SensorsDataAPI.this.trackEvent(EventType.TRACK, str, ChannelUtils.checkOrSetChannelCallbackEvent(AbstractSensorsDataAPI.getConfigOptions().isAutoAddChannelCallbackEvent, str, cloneProperties, SensorsDataAPI.this.mContext), (String) null);
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            });
        } catch (InvalidDataException e) {
            SALog.printStackTrace(e);
        }
    }

    public void trackTimerEnd(String eventName) {
        trackTimerEnd(eventName, (JSONObject) null);
    }

    public void clearTrackTimer() {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                try {
                    synchronized (SensorsDataAPI.this.mTrackTimer) {
                        SensorsDataAPI.this.mTrackTimer.clear();
                    }
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public String getLastScreenUrl() {
        return this.mLastScreenUrl;
    }

    public void clearReferrerWhenAppEnd() {
        this.mClearReferrerWhenAppEnd = true;
    }

    public void clearLastScreenUrl() {
        if (this.mClearReferrerWhenAppEnd) {
            this.mLastScreenUrl = null;
        }
    }

    public JSONObject getLastScreenTrackProperties() {
        return this.mLastScreenTrackProperties;
    }

    @Deprecated
    public void trackViewScreen(final String url, JSONObject properties) {
        try {
            final JSONObject cloneProperties = JSONUtils.cloneJsonObject(properties);
            this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                public void run() {
                    try {
                        if (!TextUtils.isEmpty(url) || cloneProperties != null) {
                            String currentUrl = url;
                            JSONObject trackProperties = new JSONObject();
                            SensorsDataAPI sensorsDataAPI = SensorsDataAPI.this;
                            sensorsDataAPI.mLastScreenTrackProperties = cloneProperties;
                            String str = sensorsDataAPI.mLastScreenUrl;
                            if (str != null) {
                                trackProperties.put("$referrer", (Object) str);
                            }
                            SensorsDataAPI sensorsDataAPI2 = SensorsDataAPI.this;
                            sensorsDataAPI2.mReferrerScreenTitle = sensorsDataAPI2.mCurrentScreenTitle;
                            JSONObject jSONObject = cloneProperties;
                            if (jSONObject != null) {
                                if (jSONObject.has(AopConstants.TITLE)) {
                                    SensorsDataAPI.this.mCurrentScreenTitle = cloneProperties.getString(AopConstants.TITLE);
                                } else {
                                    SensorsDataAPI.this.mCurrentScreenTitle = null;
                                }
                                if (cloneProperties.has("$url")) {
                                    currentUrl = cloneProperties.optString("$url");
                                }
                            }
                            trackProperties.put("$url", (Object) currentUrl);
                            SensorsDataAPI.this.mLastScreenUrl = currentUrl;
                            JSONObject jSONObject2 = cloneProperties;
                            if (jSONObject2 != null) {
                                SensorsDataUtils.mergeJSONObject(jSONObject2, trackProperties);
                            }
                            SensorsDataAPI.this.trackEvent(EventType.TRACK, "$AppViewScreen", trackProperties, (String) null);
                        }
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            });
        } catch (InvalidDataException e) {
            SALog.printStackTrace(e);
        }
    }

    public void trackViewScreen(final Activity activity) {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                try {
                    Activity activity = activity;
                    if (activity != null) {
                        SensorsDataAPI.this.trackViewScreen(SensorsDataUtils.getScreenUrl(activity), AopUtil.buildTitleAndScreenName(activity));
                    }
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public void trackViewScreen(final Object fragment) {
        if (fragment != null) {
            Class<?> supportFragmentClass = null;
            Class<?> appFragmentClass = null;
            Class<?> androidXFragmentClass = null;
            try {
                supportFragmentClass = Class.forName("androidx.fragment.app.Fragment");
            } catch (Exception e) {
            }
            try {
                appFragmentClass = Class.forName("android.app.Fragment");
            } catch (Exception e2) {
            }
            try {
                androidXFragmentClass = Class.forName("androidx.fragment.app.Fragment");
            } catch (Exception e3) {
            }
            if ((supportFragmentClass != null && supportFragmentClass.isInstance(fragment)) || ((appFragmentClass != null && appFragmentClass.isInstance(fragment)) || (androidXFragmentClass != null && androidXFragmentClass.isInstance(fragment)))) {
                this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                    public void run() {
                        JSONObject otherProperties;
                        SensorsDataFragmentTitle sensorsDataFragmentTitle;
                        try {
                            JSONObject properties = new JSONObject();
                            String screenName = fragment.getClass().getCanonicalName();
                            String title = null;
                            if (fragment.getClass().isAnnotationPresent(SensorsDataFragmentTitle.class) && (sensorsDataFragmentTitle = (SensorsDataFragmentTitle) fragment.getClass().getAnnotation(SensorsDataFragmentTitle.class)) != null) {
                                title = sensorsDataFragmentTitle.title();
                            }
                            if (Build.VERSION.SDK_INT >= 11) {
                                Activity activity = null;
                                try {
                                    Method getActivityMethod = fragment.getClass().getMethod("getActivity", new Class[0]);
                                    if (getActivityMethod != null) {
                                        activity = (Activity) getActivityMethod.invoke(fragment, new Object[0]);
                                    }
                                } catch (Exception e) {
                                }
                                if (activity != null) {
                                    if (TextUtils.isEmpty(title)) {
                                        title = SensorsDataUtils.getActivityTitle(activity);
                                    }
                                    screenName = String.format(Locale.CHINA, "%s|%s", new Object[]{activity.getClass().getCanonicalName(), screenName});
                                }
                            }
                            if (!TextUtils.isEmpty(title)) {
                                properties.put(AopConstants.TITLE, (Object) title);
                            }
                            properties.put(AopConstants.SCREEN_NAME, (Object) screenName);
                            Object obj = fragment;
                            if ((obj instanceof ScreenAutoTracker) && (otherProperties = ((ScreenAutoTracker) obj).getTrackProperties()) != null) {
                                SensorsDataUtils.mergeJSONObject(otherProperties, properties);
                            }
                            SensorsDataAPI.this.trackViewScreen(SensorsDataUtils.getScreenUrl(fragment), properties);
                        } catch (Exception e2) {
                            SALog.printStackTrace(e2);
                        }
                    }
                });
            }
        }
    }

    public void trackViewAppClick(View view) {
        trackViewAppClick(view, (JSONObject) null);
    }

    public void trackViewAppClick(View view, JSONObject properties) {
        if (view != null) {
            try {
                JSONObject cloneProperties = JSONUtils.cloneJsonObject(properties);
                if (cloneProperties == null) {
                    cloneProperties = new JSONObject();
                }
                if (AopUtil.injectClickInfo(view, cloneProperties, true)) {
                    trackInternal(AopConstants.APP_CLICK_EVENT_NAME, cloneProperties, AopUtil.addViewPathProperties(AopUtil.getActivityFromContext(view.getContext(), view), view, cloneProperties));
                }
            } catch (InvalidDataException e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public void flush() {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                try {
                    SensorsDataAPI.this.mMessages.flush();
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public void flushSync() {
        flush();
    }

    public void flushScheduled() {
        try {
            this.mMessages.flushScheduled();
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void registerDynamicSuperProperties(SensorsDataDynamicSuperProperties dynamicSuperProperties) {
        this.mDynamicSuperPropertiesCallBack = dynamicSuperProperties;
    }

    public void setTrackEventCallBack(SensorsDataTrackEventCallBack trackEventCallBack) {
        this.mTrackEventCallBack = trackEventCallBack;
    }

    public void setDeepLinkCallback(SensorsDataDeepLinkCallback deepLinkCallback) {
        this.mDeepLinkCallback = deepLinkCallback;
    }

    public void stopTrackThread() {
        TrackTaskManagerThread trackTaskManagerThread = this.mTrackTaskManagerThread;
        if (trackTaskManagerThread != null && !trackTaskManagerThread.isStopped()) {
            this.mTrackTaskManagerThread.stop();
            SALog.i("SA.SensorsDataAPI", "Data collection thread has been stopped");
        }
    }

    public void startTrackThread() {
        TrackTaskManagerThread trackTaskManagerThread = this.mTrackTaskManagerThread;
        if (trackTaskManagerThread == null || trackTaskManagerThread.isStopped()) {
            this.mTrackTaskManagerThread = new TrackTaskManagerThread();
            new Thread(this.mTrackTaskManagerThread).start();
            SALog.i("SA.SensorsDataAPI", "Data collection thread has been started");
        }
    }

    @Deprecated
    public void enableDataCollect() {
        try {
            this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                public void run() {
                    if (!AbstractSensorsDataAPI.mSAConfigOptions.isDataCollectEnable) {
                        SensorsDataAPI.this.mContext.getContentResolver().notifyChange(DbParams.getInstance().getDataCollectUri(), (ContentObserver) null);
                    }
                    AbstractSensorsDataAPI.mSAConfigOptions.isDataCollectEnable = true;
                    AbstractSensorsDataAPI.mIsMainProcess = AppInfoUtils.isMainProcess(SensorsDataAPI.this.mContext, (Bundle) null);
                    SensorsDataAPI sensorsDataAPI = SensorsDataAPI.this;
                    sensorsDataAPI.mUserIdentityAPI.enableDataCollect(sensorsDataAPI.mSAContextManager.getAndroidId());
                    SensorsDataAPI.this.mTrackTaskManager.setDataCollectEnable(true);
                    if (SensorsDataAPI.this.mFirstDay.get() == null) {
                        SensorsDataAPI.this.mFirstDay.commit(TimeUtils.formatTime(System.currentTimeMillis(), TimeUtils.YYYY_MM_DD));
                    }
                    try {
                        TrackMonitor.getInstance().callEnableDataCollect();
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            });
            flush();
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public void deleteAll() {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                SensorsDataAPI.this.mMessages.deleteAll();
            }
        });
    }

    public JSONObject getSuperProperties() {
        JSONObject jSONObject;
        synchronized (this.mSuperProperties) {
            try {
                jSONObject = new JSONObject(((JSONObject) this.mSuperProperties.get()).toString());
            } catch (JSONException e) {
                SALog.printStackTrace(e);
                return new JSONObject();
            } catch (Throwable th) {
                throw th;
            }
        }
        return jSONObject;
    }

    public void registerSuperProperties(JSONObject superProperties) {
        try {
            final JSONObject cloneSuperProperties = JSONUtils.cloneJsonObject(superProperties);
            this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                public void run() {
                    try {
                        if (cloneSuperProperties != null) {
                            synchronized (SensorsDataAPI.this.mSuperProperties) {
                                SensorsDataAPI.this.mSuperProperties.commit(SensorsDataUtils.mergeSuperJSONObject(cloneSuperProperties, (JSONObject) SensorsDataAPI.this.mSuperProperties.get()));
                            }
                        }
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            });
        } catch (InvalidDataException e) {
            SALog.printStackTrace(e);
        }
    }

    public void unregisterSuperProperty(final String superPropertyName) {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                try {
                    synchronized (SensorsDataAPI.this.mSuperProperties) {
                        JSONObject superProperties = (JSONObject) SensorsDataAPI.this.mSuperProperties.get();
                        superProperties.remove(superPropertyName);
                        SensorsDataAPI.this.mSuperProperties.commit(superProperties);
                    }
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public void clearSuperProperties() {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                synchronized (SensorsDataAPI.this.mSuperProperties) {
                    SensorsDataAPI.this.mSuperProperties.commit(new JSONObject());
                }
            }
        });
    }

    public void profileSet(JSONObject properties) {
        try {
            final JSONObject cloneProperties = JSONUtils.cloneJsonObject(properties);
            this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                public void run() {
                    try {
                        SensorsDataAPI.this.trackEvent(EventType.PROFILE_SET, (String) null, cloneProperties, (String) null);
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            });
        } catch (InvalidDataException e) {
            SALog.printStackTrace(e);
        }
    }

    public void profileSet(final String property, final Object value) {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                try {
                    SensorsDataAPI.this.trackEvent(EventType.PROFILE_SET, (String) null, new JSONObject().put(property, value), (String) null);
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public void profileSetOnce(JSONObject properties) {
        try {
            final JSONObject cloneProperties = JSONUtils.cloneJsonObject(properties);
            this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                public void run() {
                    try {
                        SensorsDataAPI.this.trackEvent(EventType.PROFILE_SET_ONCE, (String) null, cloneProperties, (String) null);
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            });
        } catch (InvalidDataException e) {
            SALog.printStackTrace(e);
        }
    }

    public void profileSetOnce(final String property, final Object value) {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                try {
                    SensorsDataAPI.this.trackEvent(EventType.PROFILE_SET_ONCE, (String) null, new JSONObject().put(property, value), (String) null);
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public void profileIncrement(final Map<String, ? extends Number> properties) {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                try {
                    SensorsDataAPI.this.trackEvent(EventType.PROFILE_INCREMENT, (String) null, new JSONObject((Map<?, ?>) properties), (String) null);
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public void profileIncrement(final String property, final Number value) {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                try {
                    SensorsDataAPI.this.trackEvent(EventType.PROFILE_INCREMENT, (String) null, new JSONObject().put(property, (Object) value), (String) null);
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public void profileAppend(final String property, final String value) {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                try {
                    JSONArray append_values = new JSONArray();
                    append_values.put((Object) value);
                    JSONObject properties = new JSONObject();
                    properties.put(property, (Object) append_values);
                    SensorsDataAPI.this.trackEvent(EventType.PROFILE_APPEND, (String) null, properties, (String) null);
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public void profileAppend(final String property, final Set<String> values) {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                try {
                    JSONArray append_values = new JSONArray();
                    for (String value : values) {
                        append_values.put((Object) value);
                    }
                    JSONObject properties = new JSONObject();
                    properties.put(property, (Object) append_values);
                    SensorsDataAPI.this.trackEvent(EventType.PROFILE_APPEND, (String) null, properties, (String) null);
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public void profileUnset(final String property) {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                try {
                    SensorsDataAPI.this.trackEvent(EventType.PROFILE_UNSET, (String) null, new JSONObject().put(property, true), (String) null);
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public void profileDelete() {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                try {
                    SensorsDataAPI.this.trackEvent(EventType.PROFILE_DELETE, (String) null, (JSONObject) null, (String) null);
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public boolean isDebugMode() {
        return this.mDebugMode.isDebugMode();
    }

    public boolean isNetworkRequestEnable() {
        return this.mEnableNetworkRequest;
    }

    public void enableNetworkRequest(boolean isRequest) {
        this.mEnableNetworkRequest = isRequest;
    }

    public void setServerUrl(String serverUrl) {
        setServerUrl(serverUrl, false);
    }

    public void setServerUrl(final String serverUrl, boolean isRequestRemoteConfig) {
        if (isRequestRemoteConfig) {
            try {
                BaseSensorsDataSDKRemoteManager baseSensorsDataSDKRemoteManager = this.mRemoteManager;
                if (baseSensorsDataSDKRemoteManager != null) {
                    try {
                        baseSensorsDataSDKRemoteManager.requestRemoteConfig(BaseSensorsDataSDKRemoteManager.RandomTimeType.RandomTimeTypeWrite, false);
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            } catch (Exception e2) {
                SALog.printStackTrace(e2);
                return;
            }
        }
        if (!TextUtils.equals(serverUrl, this.mOriginServerUrl) && AbstractSensorsDataAPI.getConfigOptions().isVisualizedPropertiesEnabled()) {
            try {
                VisualPropertiesManager.getInstance().requestVisualConfig();
            } catch (Exception e3) {
                SALog.printStackTrace(e3);
            }
        }
        this.mOriginServerUrl = serverUrl;
        if (TextUtils.isEmpty(serverUrl)) {
            this.mServerUrl = serverUrl;
            SALog.i("SA.SensorsDataAPI", "Server url is null or empty.");
            return;
        }
        final Uri serverURI = Uri.parse(serverUrl);
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                String hostServer = serverURI.getHost();
                if (!TextUtils.isEmpty(hostServer) && hostServer.contains("_")) {
                    SALog.i("SA.SensorsDataAPI", "Server url " + serverUrl + " contains '_' is not recommend，see details: https://en.wikipedia.org/wiki/Hostname");
                }
            }
        });
        if (this.mDebugMode != DebugMode.DEBUG_OFF) {
            String uriPath = serverURI.getPath();
            if (!TextUtils.isEmpty(uriPath)) {
                int pathPrefix = uriPath.lastIndexOf(47);
                if (pathPrefix != -1) {
                    this.mServerUrl = serverURI.buildUpon().path(uriPath.substring(0, pathPrefix) + "/debug").build().toString();
                }
                return;
            }
            return;
        }
        this.mServerUrl = serverUrl;
    }

    public void trackEventFromH5(String eventInfo, boolean enableVerify) {
        try {
            if (!TextUtils.isEmpty(eventInfo)) {
                JSONObject eventObject = new JSONObject(eventInfo);
                if (enableVerify) {
                    String serverUrl = eventObject.optString("server_url");
                    if (TextUtils.isEmpty(serverUrl) || !new ServerUrl(serverUrl).check(new ServerUrl(this.mServerUrl))) {
                        return;
                    }
                }
                trackEventFromH5(eventInfo);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void trackEventFromH5(final String eventInfo) {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                SensorsDataAPI.this.trackEventH5(eventInfo);
            }
        });
    }

    public void profilePushId(final String pushTypeKey, final String pushId) {
        transformTaskQueue(new Runnable() {
            public void run() {
                try {
                    if (SADataHelper.assertPropertyKey(pushTypeKey)) {
                        String distinctPushId = SensorsDataAPI.this.getDistinctId() + pushId;
                        if (!TextUtils.equals(DbAdapter.getInstance().getPushId("distinctId_" + pushTypeKey), distinctPushId)) {
                            SensorsDataAPI.this.profileSet(pushTypeKey, pushId);
                            DbAdapter.getInstance().commitPushID("distinctId_" + pushTypeKey, distinctPushId);
                        }
                    }
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public void profileUnsetPushId(final String pushTypeKey) {
        transformTaskQueue(new Runnable() {
            public void run() {
                try {
                    if (SADataHelper.assertPropertyKey(pushTypeKey)) {
                        String distinctId = SensorsDataAPI.this.getDistinctId();
                        String key = "distinctId_" + pushTypeKey;
                        String spDistinctPushId = DbAdapter.getInstance().getPushId(key);
                        if (!TextUtils.isEmpty(spDistinctPushId) && spDistinctPushId.startsWith(distinctId)) {
                            SensorsDataAPI.this.profileUnset(pushTypeKey);
                            DbAdapter.getInstance().removePushId(key);
                        }
                    }
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public void itemSet(final String itemType, final String itemId, JSONObject properties) {
        try {
            final JSONObject cloneProperties = JSONUtils.cloneJsonObject(properties);
            this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                public void run() {
                    SensorsDataAPI.this.trackItemEvent(itemType, itemId, EventType.ITEM_SET.getEventType(), System.currentTimeMillis(), cloneProperties);
                }
            });
        } catch (InvalidDataException e) {
            SALog.printStackTrace(e);
        }
    }

    public void itemDelete(final String itemType, final String itemId) {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                SensorsDataAPI.this.trackItemEvent(itemType, itemId, EventType.ITEM_DELETE.getEventType(), System.currentTimeMillis(), (JSONObject) null);
            }
        });
    }

    public void enableDeepLinkInstallSource(boolean enable) {
        this.mEnableDeepLinkInstallSource = enable;
    }

    public String getServerUrl() {
        return this.mServerUrl;
    }

    public void trackDeepLinkLaunch(String deepLinkUrl) {
        trackDeepLinkLaunch(deepLinkUrl, (String) null);
    }

    public void trackDeepLinkLaunch(String deepLinkUrl, final String oaid) {
        final JSONObject properties = new JSONObject();
        final boolean isDeepLinkInstallSource = isDeepLinkInstallSource();
        try {
            properties.put("$deeplink_url", (Object) deepLinkUrl);
            properties.put("$time", (Object) new Date(System.currentTimeMillis()));
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        sharedInstance().transformTaskQueue(new Runnable() {
            public void run() {
                if (isDeepLinkInstallSource) {
                    try {
                        JSONObject jSONObject = properties;
                        SensorsDataAPI sensorsDataAPI = SensorsDataAPI.this;
                        Context context = sensorsDataAPI.mContext;
                        String androidId = sensorsDataAPI.mSAContextManager.getAndroidId();
                        String str = oaid;
                        if (str == null) {
                            str = OaidHelper.getOAID(SensorsDataAPI.this.mContext);
                        }
                        jSONObject.put("$ios_install_source", (Object) ChannelUtils.getDeviceInfo(context, androidId, str));
                    } catch (JSONException e) {
                        SALog.printStackTrace(e);
                    }
                }
                SensorsDataAPI.this.trackInternal("$AppDeeplinkLaunch", properties);
            }
        });
    }

    public String getSDKVersion() {
        return "6.2.7";
    }

    public enum DebugMode {
        DEBUG_OFF(false, false),
        DEBUG_ONLY(true, false),
        DEBUG_AND_TRACK(true, true);
        
        private final boolean debugMode;
        private final boolean debugWriteData;

        private DebugMode(boolean debugMode2, boolean debugWriteData2) {
            this.debugMode = debugMode2;
            this.debugWriteData = debugWriteData2;
        }

        /* access modifiers changed from: package-private */
        public boolean isDebugMode() {
            return this.debugMode;
        }

        /* access modifiers changed from: package-private */
        public boolean isDebugWriteData() {
            return this.debugWriteData;
        }
    }

    public enum AutoTrackEventType {
        APP_START(1),
        APP_END(2),
        APP_CLICK(4),
        APP_VIEW_SCREEN(8);
        
        /* access modifiers changed from: private */
        public final int eventValue;

        private AutoTrackEventType(int eventValue2) {
            this.eventValue = eventValue2;
        }

        static AutoTrackEventType autoTrackEventTypeFromEventName(String eventName) {
            if (TextUtils.isEmpty(eventName)) {
                return null;
            }
            char c = 65535;
            switch (eventName.hashCode()) {
                case -618659154:
                    if (eventName.equals("$AppViewScreen")) {
                        c = 3;
                        break;
                    }
                    break;
                case -441870274:
                    if (eventName.equals("$AppEnd")) {
                        c = 1;
                        break;
                    }
                    break;
                case 562530347:
                    if (eventName.equals(AopConstants.APP_CLICK_EVENT_NAME)) {
                        c = 2;
                        break;
                    }
                    break;
                case 577537797:
                    if (eventName.equals("$AppStart")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    return APP_START;
                case 1:
                    return APP_END;
                case 2:
                    return APP_CLICK;
                case 3:
                    return APP_VIEW_SCREEN;
                default:
                    return null;
            }
        }

        static String autoTrackEventName(int eventType) {
            switch (eventType) {
                case 1:
                    return "$AppStart";
                case 2:
                    return "$AppEnd";
                case 4:
                    return AopConstants.APP_CLICK_EVENT_NAME;
                case 8:
                    return "$AppViewScreen";
                default:
                    return "";
            }
        }

        static boolean isAutoTrackType(String eventName) {
            if (!TextUtils.isEmpty(eventName)) {
                char c = 65535;
                switch (eventName.hashCode()) {
                    case -618659154:
                        if (eventName.equals("$AppViewScreen")) {
                            c = 3;
                            break;
                        }
                        break;
                    case -441870274:
                        if (eventName.equals("$AppEnd")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 562530347:
                        if (eventName.equals(AopConstants.APP_CLICK_EVENT_NAME)) {
                            c = 2;
                            break;
                        }
                        break;
                    case 577537797:
                        if (eventName.equals("$AppStart")) {
                            c = 0;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public int getEventValue() {
            return this.eventValue;
        }
    }

    public final class NetworkType {
        public static final int TYPE_2G = 1;
        public static final int TYPE_3G = 2;
        public static final int TYPE_4G = 4;
        public static final int TYPE_5G = 16;
        public static final int TYPE_ALL = 255;
        public static final int TYPE_NONE = 0;
        public static final int TYPE_WIFI = 8;

        public NetworkType() {
        }
    }
}
