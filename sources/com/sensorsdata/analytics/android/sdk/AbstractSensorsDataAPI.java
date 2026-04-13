package com.sensorsdata.analytics.android.sdk;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.advert.utils.ChannelUtils;
import com.sensorsdata.analytics.android.sdk.advert.utils.OaidHelper;
import com.sensorsdata.analytics.android.sdk.aop.push.PushLifecycleCallbacks;
import com.sensorsdata.analytics.android.sdk.autotrack.ActivityLifecycleCallbacks;
import com.sensorsdata.analytics.android.sdk.autotrack.ActivityPageLeaveCallbacks;
import com.sensorsdata.analytics.android.sdk.autotrack.FragmentPageLeaveCallbacks;
import com.sensorsdata.analytics.android.sdk.autotrack.FragmentViewScreenCallbacks;
import com.sensorsdata.analytics.android.sdk.autotrack.aop.FragmentTrackHelper;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentFirstDay;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentFirstStart;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentFirstTrackInstallation;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentFirstTrackInstallationWithCallback;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentLoader;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentSuperProperties;
import com.sensorsdata.analytics.android.sdk.deeplink.SensorsDataDeepLinkCallback;
import com.sensorsdata.analytics.android.sdk.encrypt.SensorsDataEncrypt;
import com.sensorsdata.analytics.android.sdk.exceptions.InvalidDataException;
import com.sensorsdata.analytics.android.sdk.internal.api.FragmentAPI;
import com.sensorsdata.analytics.android.sdk.internal.api.IFragmentAPI;
import com.sensorsdata.analytics.android.sdk.internal.api.UserIdentityAPI;
import com.sensorsdata.analytics.android.sdk.internal.beans.EventTimer;
import com.sensorsdata.analytics.android.sdk.internal.beans.EventType;
import com.sensorsdata.analytics.android.sdk.internal.rpc.SensorsDataContentObserver;
import com.sensorsdata.analytics.android.sdk.listener.SAEventListener;
import com.sensorsdata.analytics.android.sdk.listener.SAFunctionListener;
import com.sensorsdata.analytics.android.sdk.listener.SAJSListener;
import com.sensorsdata.analytics.android.sdk.monitor.TrackMonitor;
import com.sensorsdata.analytics.android.sdk.plugin.encrypt.SAStoreManager;
import com.sensorsdata.analytics.android.sdk.plugin.property.SAPresetPropertyPlugin;
import com.sensorsdata.analytics.android.sdk.plugin.property.SensorsDataPropertyPluginManager;
import com.sensorsdata.analytics.android.sdk.remote.BaseSensorsDataSDKRemoteManager;
import com.sensorsdata.analytics.android.sdk.remote.SensorsDataRemoteManager;
import com.sensorsdata.analytics.android.sdk.util.AppInfoUtils;
import com.sensorsdata.analytics.android.sdk.util.JSONUtils;
import com.sensorsdata.analytics.android.sdk.util.NetworkUtils;
import com.sensorsdata.analytics.android.sdk.util.SAContextManager;
import com.sensorsdata.analytics.android.sdk.util.SADataHelper;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import com.sensorsdata.analytics.android.sdk.util.TimeUtils;
import com.sensorsdata.analytics.android.sdk.util.ToastUtil;
import com.sensorsdata.analytics.android.sdk.visual.model.ViewNode;
import com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager;
import java.lang.ref.WeakReference;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public abstract class AbstractSensorsDataAPI implements ISensorsDataAPI {
    static boolean SHOW_DEBUG_INFO_VIEW = true;
    protected static final String TAG = "SA.SensorsDataAPI";
    static final String VERSION = "6.2.7";
    protected static boolean isChangeEnableNetworkFlag = false;
    protected static SensorsDataGPSLocation mGPSLocation;
    static boolean mIsMainProcess = false;
    protected static SAConfigOptions mSAConfigOptions;
    protected static final Map<Context, SensorsDataAPI> sInstanceMap = new HashMap();
    private boolean isTrackEventWithPluginVersion;
    protected ActivityLifecycleCallbacks mActivityLifecycleCallbacks;
    protected boolean mAutoTrack;
    protected List<Integer> mAutoTrackIgnoredActivities;
    protected boolean mClearReferrerWhenAppEnd;
    protected final Context mContext;
    protected String mCookie;
    protected String mCurrentScreenTitle;
    protected SensorsDataAPI.DebugMode mDebugMode;
    protected SensorsDataDeepLinkCallback mDeepLinkCallback;
    protected boolean mDisableDefaultRemoteConfig;
    protected boolean mDisableTrackDeviceId;
    protected SensorsDataDynamicSuperProperties mDynamicSuperPropertiesCallBack;
    boolean mEnableDeepLinkInstallSource;
    protected boolean mEnableNetworkRequest;
    protected final PersistentFirstDay mFirstDay;
    protected final PersistentFirstStart mFirstStart;
    protected final PersistentFirstTrackInstallation mFirstTrackInstallation;
    protected final PersistentFirstTrackInstallationWithCallback mFirstTrackInstallationWithCallback;
    protected IFragmentAPI mFragmentAPI;
    protected List<Integer> mHeatMapActivities;
    protected List<Class> mIgnoredViewTypeList;
    protected SimpleDateFormat mIsFirstDayDateFormat;
    protected JSONObject mLastScreenTrackProperties;
    protected String mLastScreenUrl;
    protected final Object mLoginIdLock;
    protected AnalyticsMessages mMessages;
    protected SensorsDataScreenOrientationDetector mOrientationDetector;
    protected String mOriginServerUrl;
    protected String mReferrerScreenTitle;
    BaseSensorsDataSDKRemoteManager mRemoteManager;
    protected SAContextManager mSAContextManager;
    private CopyOnWriteArrayList<SAJSListener> mSAJSListeners;
    protected boolean mSDKConfigInit;
    SensorsDataEncrypt mSensorsDataEncrypt;
    protected String mServerUrl;
    protected int mSessionTime;
    protected SAStoreManager mStoreManager;
    protected final PersistentSuperProperties mSuperProperties;
    protected SensorsDataTrackEventCallBack mTrackEventCallBack;
    protected TrackTaskManager mTrackTaskManager;
    protected TrackTaskManagerThread mTrackTaskManagerThread;
    protected final Map<String, EventTimer> mTrackTimer;
    protected UserIdentityAPI mUserIdentityAPI;
    protected List<Integer> mVisualizedAutoTrackActivities;

    public AbstractSensorsDataAPI(Context context, SAConfigOptions configOptions, SensorsDataAPI.DebugMode debugMode) {
        this.mLoginIdLock = new Object();
        this.mIgnoredViewTypeList = new ArrayList();
        SensorsDataAPI.DebugMode debugMode2 = SensorsDataAPI.DebugMode.DEBUG_OFF;
        this.mDebugMode = debugMode2;
        this.mEnableNetworkRequest = true;
        this.mClearReferrerWhenAppEnd = false;
        this.mDisableDefaultRemoteConfig = false;
        this.mDisableTrackDeviceId = false;
        this.mSessionTime = 30000;
        this.mEnableDeepLinkInstallSource = false;
        this.isTrackEventWithPluginVersion = false;
        this.mContext = context;
        setDebugMode(debugMode);
        String packageName = context.getApplicationContext().getPackageName();
        this.mAutoTrackIgnoredActivities = new ArrayList();
        this.mHeatMapActivities = new ArrayList();
        this.mVisualizedAutoTrackActivities = new ArrayList();
        PersistentLoader.initLoader(context);
        this.mSuperProperties = (PersistentSuperProperties) PersistentLoader.loadPersistent(DbParams.PersistentName.SUPER_PROPERTIES);
        this.mFirstStart = (PersistentFirstStart) PersistentLoader.loadPersistent(DbParams.PersistentName.FIRST_START);
        this.mFirstTrackInstallation = (PersistentFirstTrackInstallation) PersistentLoader.loadPersistent(DbParams.PersistentName.FIRST_INSTALL);
        this.mFirstTrackInstallationWithCallback = (PersistentFirstTrackInstallationWithCallback) PersistentLoader.loadPersistent(DbParams.PersistentName.FIRST_INSTALL_CALLBACK);
        this.mFirstDay = (PersistentFirstDay) PersistentLoader.loadPersistent(DbParams.PersistentName.FIRST_DAY);
        this.mTrackTimer = new HashMap();
        this.mFragmentAPI = new FragmentAPI();
        try {
            mSAConfigOptions = configOptions.clone();
            SAStoreManager instance = SAStoreManager.getInstance();
            this.mStoreManager = instance;
            instance.registerPlugins(mSAConfigOptions.getStorePlugins(), context);
            this.mStoreManager.upgrade();
            this.mTrackTaskManager = TrackTaskManager.getInstance();
            this.mTrackTaskManagerThread = new TrackTaskManagerThread();
            new Thread(this.mTrackTaskManagerThread, ThreadNameConstants.THREAD_TASK_QUEUE).start();
            SensorsDataExceptionHandler.init();
            initSAConfig(mSAConfigOptions.mServerUrl, packageName);
            this.mSAContextManager = new SAContextManager(context);
            this.mMessages = AnalyticsMessages.getInstance(context, (SensorsDataAPI) this);
            SensorsDataRemoteManager sensorsDataRemoteManager = new SensorsDataRemoteManager((SensorsDataAPI) this);
            this.mRemoteManager = sensorsDataRemoteManager;
            sensorsDataRemoteManager.applySDKConfigFromCache();
            if (mSAConfigOptions.isVisualizedPropertiesEnabled()) {
                VisualPropertiesManager.getInstance().requestVisualConfig(context, (SensorsDataAPI) this);
            }
            if (this.mDebugMode != debugMode2 && mIsMainProcess && SHOW_DEBUG_INFO_VIEW && !isSDKDisabled()) {
                showDebugModeWarning();
            }
            this.mUserIdentityAPI = new UserIdentityAPI(this.mSAContextManager, mSAConfigOptions);
            registerLifecycleCallbacks();
            registerObserver();
            if (!mSAConfigOptions.isDisableSDK()) {
                delayInitTask();
            }
            if (SALog.isLogEnabled()) {
                SALog.i(TAG, String.format(Locale.CHINA, "Initialized the instance of Sensors Analytics SDK with server url '%s', flush interval %d ms, debugMode: %s", new Object[]{this.mServerUrl, Integer.valueOf(mSAConfigOptions.mFlushInterval), debugMode}));
            }
            SensorsDataUtils.initUniAppStatus();
        } catch (Throwable ex) {
            SALog.d(TAG, ex.getMessage());
        }
        registerDefaultPropertiesPlugin();
    }

    private void registerDefaultPropertiesPlugin() {
        SensorsDataPropertyPluginManager.getInstance().registerPropertyPlugin(new SAPresetPropertyPlugin(this.mContext, this.mDisableTrackDeviceId, mSAConfigOptions.isDisableDeviceId()));
    }

    protected AbstractSensorsDataAPI() {
        this.mLoginIdLock = new Object();
        this.mIgnoredViewTypeList = new ArrayList();
        this.mDebugMode = SensorsDataAPI.DebugMode.DEBUG_OFF;
        this.mEnableNetworkRequest = true;
        this.mClearReferrerWhenAppEnd = false;
        this.mDisableDefaultRemoteConfig = false;
        this.mDisableTrackDeviceId = false;
        this.mSessionTime = 30000;
        this.mEnableDeepLinkInstallSource = false;
        this.isTrackEventWithPluginVersion = false;
        this.mContext = null;
        this.mMessages = null;
        this.mSuperProperties = null;
        this.mFirstStart = null;
        this.mFirstDay = null;
        this.mFirstTrackInstallation = null;
        this.mFirstTrackInstallationWithCallback = null;
        this.mTrackTimer = null;
        this.mSensorsDataEncrypt = null;
    }

    /* access modifiers changed from: protected */
    public void delayExecution(Activity activity) {
        ActivityLifecycleCallbacks activityLifecycleCallbacks = this.mActivityLifecycleCallbacks;
        if (activityLifecycleCallbacks != null) {
            activityLifecycleCallbacks.onActivityCreated(activity, (Bundle) null);
            AppStateManager.getInstance().onActivityCreated(activity, (Bundle) null);
            this.mActivityLifecycleCallbacks.onActivityStarted(activity);
        }
        if (SALog.isLogEnabled()) {
            SALog.i(TAG, "SDK init success by：" + activity.getClass().getName());
        }
    }

    private static boolean isSDKDisabledByRemote() {
        boolean isSDKDisabled = BaseSensorsDataSDKRemoteManager.isSDKDisabledByRemote();
        if (isSDKDisabled) {
            SALog.i(TAG, "remote config: SDK is disabled");
        }
        return isSDKDisabled;
    }

    private static boolean isSDKDisableByLocal() {
        SAConfigOptions sAConfigOptions = mSAConfigOptions;
        if (sAConfigOptions != null) {
            return sAConfigOptions.isDisableSDK;
        }
        SALog.i(TAG, "SAConfigOptions is null");
        return true;
    }

    public static boolean isSDKDisabled() {
        return isSDKDisableByLocal() || isSDKDisabledByRemote();
    }

    public void addEventListener(SAEventListener eventListener) {
        this.mSAContextManager.addEventListener(eventListener);
    }

    public void removeEventListener(SAEventListener eventListener) {
        this.mSAContextManager.removeEventListener(eventListener);
    }

    public void addSAJSListener(SAJSListener listener) {
        try {
            if (this.mSAJSListeners == null) {
                this.mSAJSListeners = new CopyOnWriteArrayList<>();
            }
            if (!this.mSAJSListeners.contains(listener)) {
                this.mSAJSListeners.add(listener);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void removeSAJSListener(SAJSListener listener) {
        try {
            CopyOnWriteArrayList<SAJSListener> copyOnWriteArrayList = this.mSAJSListeners;
            if (copyOnWriteArrayList != null && copyOnWriteArrayList.contains(listener)) {
                this.mSAJSListeners.remove(listener);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    /* access modifiers changed from: package-private */
    public void handleJsMessage(WeakReference<View> view, String message) {
        CopyOnWriteArrayList<SAJSListener> copyOnWriteArrayList = this.mSAJSListeners;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            Iterator<SAJSListener> it = this.mSAJSListeners.iterator();
            while (it.hasNext()) {
                SAJSListener listener = it.next();
                if (listener != null) {
                    try {
                        listener.onReceiveJSMessage(view, message);
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            }
        }
    }

    public void addFunctionListener(final SAFunctionListener functionListener) {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                TrackMonitor.getInstance().addFunctionListener(functionListener);
            }
        });
    }

    public void removeFunctionListener(final SAFunctionListener functionListener) {
        try {
            this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                public void run() {
                    TrackMonitor.getInstance().removeFunctionListener(functionListener);
                }
            });
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    public static SAConfigOptions getConfigOptions() {
        return mSAConfigOptions;
    }

    public Context getContext() {
        return this.mContext;
    }

    /* access modifiers changed from: package-private */
    public boolean isSaveDeepLinkInfo() {
        return mSAConfigOptions.mEnableSaveDeepLinkInfo;
    }

    public SensorsDataDeepLinkCallback getDeepLinkCallback() {
        return this.mDeepLinkCallback;
    }

    /* access modifiers changed from: package-private */
    public boolean _trackEventFromH5(String eventInfo) {
        try {
            if (TextUtils.isEmpty(eventInfo)) {
                return false;
            }
            String serverUrl = new JSONObject(eventInfo).optString("server_url");
            if (TextUtils.isEmpty(serverUrl) || !new ServerUrl(serverUrl).check(new ServerUrl(this.mServerUrl))) {
                return false;
            }
            trackEventFromH5(eventInfo);
            return true;
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return false;
    }

    public void trackInternal(String eventName, JSONObject properties) {
        trackInternal(eventName, properties, (ViewNode) null);
    }

    public void trackInternal(final String eventName, final JSONObject properties, final ViewNode viewNode) {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                try {
                    if (viewNode != null && AbstractSensorsDataAPI.getConfigOptions().isVisualizedPropertiesEnabled()) {
                        VisualPropertiesManager.getInstance().mergeVisualProperties(VisualPropertiesManager.VisualEventType.APP_CLICK, properties, viewNode);
                    }
                    AbstractSensorsDataAPI.this.trackEvent(EventType.TRACK, eventName, properties, (String) null);
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public SensorsDataAPI.DebugMode getDebugMode() {
        return this.mDebugMode;
    }

    public void setDebugMode(SensorsDataAPI.DebugMode debugMode) {
        this.mDebugMode = debugMode;
        if (debugMode == SensorsDataAPI.DebugMode.DEBUG_OFF) {
            enableLog(false);
            SALog.setDebug(false);
            this.mServerUrl = this.mOriginServerUrl;
            return;
        }
        enableLog(true);
        SALog.setDebug(true);
        setServerUrl(this.mOriginServerUrl);
    }

    /* access modifiers changed from: package-private */
    public void enableAutoTrack(int autoTrackEventType) {
        if (autoTrackEventType > 0 && autoTrackEventType <= 15) {
            try {
                this.mAutoTrack = true;
                SAConfigOptions sAConfigOptions = mSAConfigOptions;
                sAConfigOptions.setAutoTrackEventType(sAConfigOptions.mAutoTrackEventType | autoTrackEventType);
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public BaseSensorsDataSDKRemoteManager getRemoteManager() {
        return this.mRemoteManager;
    }

    public void setRemoteManager(BaseSensorsDataSDKRemoteManager remoteManager) {
        this.mRemoteManager = remoteManager;
    }

    public SensorsDataEncrypt getSensorsDataEncrypt() {
        return this.mSensorsDataEncrypt;
    }

    public boolean isDisableDefaultRemoteConfig() {
        return this.mDisableDefaultRemoteConfig;
    }

    public void appBecomeActive() {
        EventTimer eventTimer;
        synchronized (this.mTrackTimer) {
            try {
                for (Map.Entry<String, EventTimer> entry : this.mTrackTimer.entrySet()) {
                    if (!(entry == null || (eventTimer = entry.getValue()) == null)) {
                        eventTimer.setStartTime(SystemClock.elapsedRealtime());
                    }
                }
            } catch (Exception e) {
                SALog.i(TAG, "appBecomeActive error:" + e.getMessage());
            }
        }
    }

    public void appEnterBackground() {
        synchronized (this.mTrackTimer) {
            try {
                for (Map.Entry<String, EventTimer> entry : this.mTrackTimer.entrySet()) {
                    if (entry != null) {
                        if (!"$AppEnd".equals(entry.getKey())) {
                            EventTimer eventTimer = entry.getValue();
                            if (eventTimer != null && !eventTimer.isPaused()) {
                                eventTimer.setEventAccumulatedDuration(((eventTimer.getEventAccumulatedDuration() + SystemClock.elapsedRealtime()) - eventTimer.getStartTime()) - ((long) getSessionIntervalTime()));
                                eventTimer.setStartTime(SystemClock.elapsedRealtime());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                SALog.i(TAG, "appEnterBackground error:" + e.getMessage());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void trackChannelDebugInstallation() {
        final JSONObject _properties = new JSONObject();
        addTimeProperty(_properties);
        transformTaskQueue(new Runnable() {
            public void run() {
                try {
                    JSONObject jSONObject = _properties;
                    AbstractSensorsDataAPI abstractSensorsDataAPI = AbstractSensorsDataAPI.this;
                    jSONObject.put("$ios_install_source", (Object) ChannelUtils.getDeviceInfo(abstractSensorsDataAPI.mContext, abstractSensorsDataAPI.mSAContextManager.getAndroidId(), OaidHelper.getOAID(AbstractSensorsDataAPI.this.mContext)));
                    AbstractSensorsDataAPI.this.trackEvent(EventType.TRACK, "$ChannelDebugInstall", _properties, (String) null);
                    JSONObject profileProperties = new JSONObject();
                    SensorsDataUtils.mergeJSONObject(_properties, profileProperties);
                    profileProperties.put("$first_visit_time", (Object) new Date());
                    AbstractSensorsDataAPI.this.trackEvent(EventType.PROFILE_SET_ONCE, (String) null, profileProperties, (String) null);
                    AbstractSensorsDataAPI.this.flush();
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    public void trackAutoEvent(String eventName, JSONObject properties) {
        trackAutoEvent(eventName, properties, (ViewNode) null);
    }

    /* access modifiers changed from: package-private */
    public void trackAutoEvent(String eventName, JSONObject properties, ViewNode viewNode) {
        trackInternal(eventName, SADataHelper.appendLibMethodAutoTrack(properties), viewNode);
    }

    public SAContextManager getSAContextManager() {
        return this.mSAContextManager;
    }

    /* access modifiers changed from: package-private */
    public void registerNetworkListener() {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                NetworkUtils.registerNetworkListener(AbstractSensorsDataAPI.this.mContext);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void unregisterNetworkListener() {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                NetworkUtils.unregisterNetworkListener(AbstractSensorsDataAPI.this.mContext);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void addTimeProperty(JSONObject jsonObject) {
        if (!jsonObject.has("$time")) {
            try {
                jsonObject.put("$time", (Object) new Date(System.currentTimeMillis()));
            } catch (JSONException e) {
                SALog.printStackTrace(e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isFirstDay(long eventTime) {
        String firstDay = (String) this.mFirstDay.get();
        if (firstDay == null) {
            return true;
        }
        try {
            if (this.mIsFirstDayDateFormat == null) {
                this.mIsFirstDayDateFormat = new SimpleDateFormat(TimeUtils.YYYY_MM_DD, Locale.getDefault());
            }
            return firstDay.equals(this.mIsFirstDayDateFormat.format(Long.valueOf(eventTime)));
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void trackItemEvent(String itemType, String itemId, String eventType, long time, JSONObject properties) {
        String str = eventType;
        JSONObject jSONObject = properties;
        try {
            boolean isItemTypeValid = SADataHelper.assertPropertyKey(itemType);
            SADataHelper.assertPropertyTypes(properties);
            SADataHelper.assertItemId(itemId);
            if (!mSAConfigOptions.isDataCollectEnable) {
                transformItemTaskQueue(itemType, itemId, eventType, time, properties);
                return;
            }
            String eventProject = null;
            if (jSONObject != null && jSONObject.has("$project")) {
                eventProject = (String) jSONObject.get("$project");
                jSONObject.remove("$project");
            }
            JSONObject libProperties = new JSONObject();
            libProperties.put("$lib", (Object) "Android");
            libProperties.put("$lib_version", (Object) "6.2.7");
            libProperties.put("$lib_method", (Object) "code");
            this.mSAContextManager.addKeyIfExist(libProperties, "$app_version");
            JSONObject superProperties = (JSONObject) this.mSuperProperties.get();
            if (superProperties != null && superProperties.has("$app_version")) {
                libProperties.put("$app_version", superProperties.get("$app_version"));
            }
            StackTraceElement[] trace = new Exception().getStackTrace();
            if (trace.length > 1) {
                StackTraceElement traceElement = trace[0];
                String libDetail = String.format("%s##%s##%s##%s", new Object[]{traceElement.getClassName(), traceElement.getMethodName(), traceElement.getFileName(), Integer.valueOf(traceElement.getLineNumber())});
                if (!TextUtils.isEmpty(libDetail)) {
                    libProperties.put("$lib_detail", (Object) libDetail);
                }
            }
            JSONObject eventProperties = new JSONObject();
            if (isItemTypeValid) {
                try {
                    eventProperties.put("item_type", (Object) itemType);
                } catch (Exception e) {
                    ex = e;
                    String str2 = itemId;
                    long j = time;
                    SALog.printStackTrace(ex);
                }
            } else {
                String str3 = itemType;
            }
            try {
                eventProperties.put(FirebaseAnalytics.Param.ITEM_ID, (Object) itemId);
                eventProperties.put(IjkMediaMeta.IJKM_KEY_TYPE, (Object) str);
            } catch (Exception e2) {
                ex = e2;
                long j2 = time;
                SALog.printStackTrace(ex);
            }
            try {
                eventProperties.put("time", time);
                eventProperties.put("properties", (Object) TimeUtils.formatDate(properties));
                eventProperties.put("lib", (Object) libProperties);
                if (!TextUtils.isEmpty(eventProject)) {
                    eventProperties.put("project", (Object) eventProject);
                }
                this.mMessages.enqueueEventMessage(str, eventProperties);
                SALog.i(TAG, "track event:\n" + JSONUtils.formatJson(eventProperties.toString()));
            } catch (Exception e3) {
                ex = e3;
                SALog.printStackTrace(ex);
            }
        } catch (Exception e4) {
            ex = e4;
            String str4 = itemType;
            String str22 = itemId;
            long j22 = time;
            SALog.printStackTrace(ex);
        }
    }

    /* access modifiers changed from: protected */
    public void trackEvent(EventType eventType, String eventName, JSONObject properties, String originalDistinctId) {
        trackEvent(eventType, eventName, properties, (JSONObject) null, getDistinctId(), getLoginId(), originalDistinctId);
    }

    /* access modifiers changed from: protected */
    public void trackEvent(EventType eventType, String eventName, JSONObject properties, JSONObject dynamicProperty, String distinctId, String loginId, String originalDistinctId) {
        EventTimer eventTimer;
        String eventName2;
        JSONObject identitiesJson;
        BaseSensorsDataSDKRemoteManager baseSensorsDataSDKRemoteManager;
        EventTimer eventTimer2;
        EventType eventType2 = eventType;
        String str = eventName;
        try {
            if (!TextUtils.isEmpty(eventName)) {
                synchronized (this.mTrackTimer) {
                    eventTimer2 = this.mTrackTimer.get(str);
                    this.mTrackTimer.remove(str);
                }
                if (!str.endsWith("_SATimer") || eventName.length() <= 45) {
                    eventName2 = str;
                    eventTimer = eventTimer2;
                } else {
                    eventName2 = str.substring(0, eventName.length() - 45);
                    eventTimer = eventTimer2;
                }
            } else {
                eventName2 = str;
                eventTimer = null;
            }
            try {
                if (eventType.isTrack()) {
                    SADataHelper.assertEventName(eventName2);
                    if (!TextUtils.isEmpty(eventName2) && (baseSensorsDataSDKRemoteManager = this.mRemoteManager) != null && baseSensorsDataSDKRemoteManager.ignoreEvent(eventName2)) {
                        return;
                    }
                }
                try {
                    JSONObject sendProperties = new JSONObject();
                    if (eventType.isTrack()) {
                        getCarrier(sendProperties);
                        if (!"$AppEnd".equals(eventName2) && !"$AppDeeplinkLaunch".equals(eventName2)) {
                            SensorsDataUtils.mergeJSONObject(ChannelUtils.getLatestUtmProperties(), sendProperties);
                        }
                    }
                    try {
                        JSONObject sendProperties2 = SensorsDataUtils.mergeSuperJSONObject(SensorsDataPropertyPluginManager.getInstance().properties(eventName2, eventType2, properties), sendProperties);
                        if (eventType.isTrack()) {
                            mergerDynamicAndSuperProperties(sendProperties2, dynamicProperty);
                            String str2 = this.mReferrerScreenTitle;
                            if (str2 != null) {
                                sendProperties2.put("$referrer_title", (Object) str2);
                            }
                            String networkType = NetworkUtils.networkType(this.mContext);
                            sendProperties2.put("$wifi", LDNetUtil.NETWORKTYPE_WIFI.equals(networkType));
                            sendProperties2.put("$network_type", (Object) networkType);
                            try {
                                SensorsDataGPSLocation sensorsDataGPSLocation = mGPSLocation;
                                if (sensorsDataGPSLocation != null) {
                                    sensorsDataGPSLocation.toJSON(sendProperties2);
                                }
                            } catch (Exception e) {
                                SALog.printStackTrace(e);
                            }
                            try {
                                String screenOrientation = getScreenOrientation();
                                if (!TextUtils.isEmpty(screenOrientation)) {
                                    sendProperties2.put("$screen_orientation", (Object) screenOrientation);
                                }
                            } catch (Exception e2) {
                                SALog.printStackTrace(e2);
                            }
                        } else {
                            JSONObject jSONObject = dynamicProperty;
                            if (!eventType.isProfile()) {
                                return;
                            }
                        }
                        if (!mSAConfigOptions.isDataCollectEnable) {
                            if (SALog.isLogEnabled()) {
                                SALog.i(TAG, "track event, isDataCollectEnable = false, eventName = " + eventName2 + ",property = " + JSONUtils.formatJson(sendProperties2.toString()));
                            }
                            try {
                                identitiesJson = new JSONObject(this.mUserIdentityAPI.getIdentities(eventType2).toString());
                            } catch (Exception e3) {
                                SALog.printStackTrace(e3);
                                identitiesJson = null;
                            }
                            JSONObject jSONObject2 = sendProperties2;
                            transformEventTaskQueue(eventType, eventName2, properties, sendProperties2, identitiesJson, distinctId, loginId, originalDistinctId, eventTimer);
                            return;
                        }
                        trackEventInternal(eventType, eventName2, properties, sendProperties2, this.mUserIdentityAPI.getIdentities(eventType2), distinctId, loginId, originalDistinctId, eventTimer);
                    } catch (JSONException e4) {
                        try {
                            throw new InvalidDataException("Unexpected property");
                        } catch (Exception e5) {
                            e = e5;
                            SALog.printStackTrace(e);
                        }
                    }
                } catch (JSONException e6) {
                    JSONObject jSONObject3 = properties;
                    throw new InvalidDataException("Unexpected property");
                }
            } catch (Exception e7) {
                e = e7;
                JSONObject jSONObject4 = properties;
                SALog.printStackTrace(e);
            }
        } catch (Exception e8) {
            e = e8;
            JSONObject jSONObject5 = properties;
            String str3 = str;
        }
    }

    /* access modifiers changed from: protected */
    public void trackEventH5(String eventInfo) {
        try {
            if (!TextUtils.isEmpty(eventInfo)) {
                if (!mSAConfigOptions.isDataCollectEnable) {
                    transformH5TaskQueue(eventInfo);
                    return;
                }
                JSONObject eventObject = new JSONObject(eventInfo);
                eventObject.put("_hybrid_h5", true);
                String type = eventObject.getString(IjkMediaMeta.IJKM_KEY_TYPE);
                EventType eventType = EventType.valueOf(type.toUpperCase(Locale.getDefault()));
                if (eventType == EventType.TRACK_SIGNUP) {
                    eventObject.put("original_id", (Object) getAnonymousId());
                } else if (!TextUtils.isEmpty(getLoginId())) {
                    eventObject.put("distinct_id", (Object) getLoginId());
                } else {
                    eventObject.put("distinct_id", (Object) getAnonymousId());
                }
                eventObject.put("anonymous_id", (Object) getAnonymousId());
                long eventTime = System.currentTimeMillis();
                eventObject.put("time", eventTime);
                try {
                    eventObject.put("_track_id", new SecureRandom().nextInt());
                } catch (Exception e) {
                }
                JSONObject propertiesObject = eventObject.optJSONObject("properties");
                SADataHelper.assertPropertyTypes(propertiesObject);
                if (propertiesObject == null) {
                    propertiesObject = new JSONObject();
                }
                JSONObject libObject = eventObject.optJSONObject("lib");
                if (libObject != null) {
                    this.mSAContextManager.addKeyIfExist(libObject, "$app_version");
                    JSONObject superProperties = (JSONObject) this.mSuperProperties.get();
                    if (superProperties != null && superProperties.has("$app_version")) {
                        libObject.put("$app_version", superProperties.get("$app_version"));
                    }
                }
                String eventName = eventObject.optString(NotificationCompat.CATEGORY_EVENT);
                JSONObject deviceInfo = SensorsDataPropertyPluginManager.getInstance().properties(eventName, eventType, (JSONObject) null);
                if (deviceInfo != null) {
                    Iterator<String> iterator = deviceInfo.keys();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        if (!TextUtils.isEmpty(key)) {
                            if (!"$lib".equals(key)) {
                                if (!"$lib_version".equals(key)) {
                                    propertiesObject.put(key, deviceInfo.opt(key));
                                }
                            }
                        }
                    }
                }
                if (eventType.isTrack()) {
                    getCarrier(propertiesObject);
                    String networkType = NetworkUtils.networkType(this.mContext);
                    propertiesObject.put("$wifi", LDNetUtil.NETWORKTYPE_WIFI.equals(networkType));
                    propertiesObject.put("$network_type", (Object) networkType);
                    mergerDynamicAndSuperProperties(propertiesObject, getDynamicProperty());
                    if (eventType.isTrack()) {
                        propertiesObject.put("$is_first_day", isFirstDay(eventTime));
                    }
                    SensorsDataUtils.mergeJSONObject(ChannelUtils.getLatestUtmProperties(), propertiesObject);
                }
                if (eventObject.has("_nocache")) {
                    eventObject.remove("_nocache");
                }
                if (eventObject.has("server_url")) {
                    eventObject.remove("server_url");
                }
                if (eventObject.has("_flush_time")) {
                    eventObject.remove("_flush_time");
                }
                if (propertiesObject.has("$project")) {
                    eventObject.put("project", (Object) propertiesObject.optString("$project"));
                    propertiesObject.remove("$project");
                }
                if (propertiesObject.has("$token")) {
                    eventObject.put("token", (Object) propertiesObject.optString("$token"));
                    propertiesObject.remove("$token");
                }
                if (propertiesObject.has("$time")) {
                    try {
                        long time = propertiesObject.getLong("$time");
                        if (TimeUtils.isDateValid(time)) {
                            eventTime = time;
                            eventObject.put("time", time);
                        }
                    } catch (Exception ex) {
                        SALog.printStackTrace(ex);
                    }
                    propertiesObject.remove("$time");
                }
                if (eventType.isTrack()) {
                    SADataHelper.assertEventName(eventName);
                    try {
                        SessionRelatedManager.getInstance().handleEventOfSession(eventName, propertiesObject, eventTime);
                    } catch (Exception e2) {
                        SALog.printStackTrace(e2);
                    }
                    if (!isEnterDb(eventName, propertiesObject)) {
                        SALog.d(TAG, eventName + " event can not enter database");
                        return;
                    } else if (!this.isTrackEventWithPluginVersion && !propertiesObject.has("$lib_plugin_version")) {
                        JSONArray libPluginVersion = getPluginVersion();
                        if (libPluginVersion == null) {
                            this.isTrackEventWithPluginVersion = true;
                        } else {
                            try {
                                propertiesObject.put("$lib_plugin_version", (Object) libPluginVersion);
                                this.isTrackEventWithPluginVersion = true;
                            } catch (Exception e3) {
                                SALog.printStackTrace(e3);
                            }
                        }
                    }
                }
                SADataHelper.assertPropertyTypes(propertiesObject);
                eventObject.put("properties", (Object) propertiesObject);
                if (eventType == EventType.TRACK_SIGNUP) {
                    synchronized (this.mLoginIdLock) {
                        this.mUserIdentityAPI.mergeH5Identities(eventType, eventObject);
                        this.mMessages.enqueueEventMessage(type, eventObject);
                        if (SALog.isLogEnabled()) {
                            SALog.i(TAG, "track event:\n" + JSONUtils.formatJson(eventObject.toString()));
                        }
                    }
                    return;
                }
                if (!TextUtils.isEmpty(getLoginId())) {
                    eventObject.put("login_id", (Object) getLoginId());
                }
                this.mUserIdentityAPI.mergeH5Identities(eventType, eventObject);
                this.mMessages.enqueueEventMessage(type, eventObject);
                if (SALog.isLogEnabled()) {
                    SALog.i(TAG, "track event from H5:\n" + JSONUtils.formatJson(eventObject.toString()));
                }
            }
        } catch (Exception e4) {
            SALog.printStackTrace(e4);
        }
    }

    public void transformTaskQueue(final Runnable runnable) {
        if (!mSAConfigOptions.isDataCollectEnable) {
            this.mTrackTaskManager.addTrackEventTask(new Runnable() {
                public void run() {
                    AbstractSensorsDataAPI.this.mTrackTaskManager.transformTaskQueue(runnable);
                }
            });
        } else {
            this.mTrackTaskManager.addTrackEventTask(runnable);
        }
    }

    /* access modifiers changed from: protected */
    public void initSAConfig(String serverURL, String packageName) {
        Bundle configBundle = AppInfoUtils.getAppInfoBundle(this.mContext);
        if (mSAConfigOptions == null) {
            this.mSDKConfigInit = false;
            mSAConfigOptions = new SAConfigOptions(serverURL);
        } else {
            this.mSDKConfigInit = true;
        }
        SAConfigOptions sAConfigOptions = mSAConfigOptions;
        if (sAConfigOptions.mEnableEncrypt) {
            this.mSensorsDataEncrypt = new SensorsDataEncrypt(this.mContext, sAConfigOptions.mPersistentSecretKey, sAConfigOptions.getEncryptors());
        }
        DbAdapter.getInstance(this.mContext, packageName, this.mSensorsDataEncrypt);
        this.mTrackTaskManager.setDataCollectEnable(mSAConfigOptions.isDataCollectEnable);
        SAConfigOptions sAConfigOptions2 = mSAConfigOptions;
        if (sAConfigOptions2.mInvokeLog) {
            enableLog(sAConfigOptions2.mLogEnabled);
        } else {
            enableLog(configBundle.getBoolean("com.sensorsdata.analytics.android.EnableLogging", this.mDebugMode != SensorsDataAPI.DebugMode.DEBUG_OFF));
        }
        SALog.setDisableSDK(mSAConfigOptions.isDisableSDK);
        setServerUrl(serverURL);
        if (mSAConfigOptions.mEnableTrackAppCrash) {
            SensorsDataExceptionHandler.enableAppCrash();
        }
        SAConfigOptions sAConfigOptions3 = mSAConfigOptions;
        if (sAConfigOptions3.mFlushInterval == 0) {
            sAConfigOptions3.setFlushInterval(configBundle.getInt("com.sensorsdata.analytics.android.FlushInterval", 15000));
        }
        SAConfigOptions sAConfigOptions4 = mSAConfigOptions;
        if (sAConfigOptions4.mFlushBulkSize == 0) {
            sAConfigOptions4.setFlushBulkSize(configBundle.getInt("com.sensorsdata.analytics.android.FlushBulkSize", 100));
        }
        SAConfigOptions sAConfigOptions5 = mSAConfigOptions;
        if (sAConfigOptions5.mMaxCacheSize == 0) {
            sAConfigOptions5.setMaxCacheSize(33554432);
        }
        this.mAutoTrack = configBundle.getBoolean("com.sensorsdata.analytics.android.AutoTrack", false);
        int i = mSAConfigOptions.mAutoTrackEventType;
        if (i != 0) {
            enableAutoTrack(i);
            this.mAutoTrack = true;
        }
        SAConfigOptions sAConfigOptions6 = mSAConfigOptions;
        if (!sAConfigOptions6.mInvokeHeatMapEnabled) {
            sAConfigOptions6.mHeatMapEnabled = configBundle.getBoolean("com.sensorsdata.analytics.android.HeatMap", false);
        }
        SAConfigOptions sAConfigOptions7 = mSAConfigOptions;
        if (!sAConfigOptions7.mInvokeVisualizedEnabled) {
            sAConfigOptions7.mVisualizedEnabled = configBundle.getBoolean("com.sensorsdata.analytics.android.VisualizedAutoTrack", false);
        }
        enableTrackScreenOrientation(mSAConfigOptions.mTrackScreenOrientationEnabled);
        if (mSAConfigOptions.isDisableSDK) {
            this.mEnableNetworkRequest = false;
            isChangeEnableNetworkFlag = true;
        }
        SHOW_DEBUG_INFO_VIEW = configBundle.getBoolean("com.sensorsdata.analytics.android.ShowDebugInfoView", true);
        this.mDisableDefaultRemoteConfig = configBundle.getBoolean("com.sensorsdata.analytics.android.DisableDefaultRemoteConfig", false);
        if (mSAConfigOptions.isDataCollectEnable) {
            mIsMainProcess = AppInfoUtils.isMainProcess(this.mContext, configBundle);
        }
        this.mDisableTrackDeviceId = configBundle.getBoolean("com.sensorsdata.analytics.android.DisableTrackDeviceId", false);
    }

    /* access modifiers changed from: protected */
    public void applySAConfigOptions() {
        if (mSAConfigOptions.mEnableTrackAppCrash) {
            SensorsDataExceptionHandler.enableAppCrash();
        }
        SAConfigOptions sAConfigOptions = mSAConfigOptions;
        if (sAConfigOptions.mAutoTrackEventType != 0) {
            this.mAutoTrack = true;
        }
        if (sAConfigOptions.mInvokeLog) {
            enableLog(sAConfigOptions.mLogEnabled);
        }
        enableTrackScreenOrientation(mSAConfigOptions.mTrackScreenOrientationEnabled);
        SAConfigOptions sAConfigOptions2 = mSAConfigOptions;
        if (!sAConfigOptions2.mVisualizedEnabled && sAConfigOptions2.mVisualizedPropertiesEnabled) {
            SALog.i(TAG, "当前已开启可视化全埋点自定义属性（enableVisualizedProperties），可视化全埋点采集开关已失效！");
            mSAConfigOptions.enableVisualizedAutoTrack(true);
        }
    }

    /* access modifiers changed from: protected */
    public void trackTimerState(String eventName, boolean isPause) {
        final String str = eventName;
        final boolean z = isPause;
        final long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                boolean z;
                try {
                    SADataHelper.assertEventName(str);
                    synchronized (AbstractSensorsDataAPI.this.mTrackTimer) {
                        EventTimer eventTimer = AbstractSensorsDataAPI.this.mTrackTimer.get(str);
                        if (!(eventTimer == null || eventTimer.isPaused() == (z = z))) {
                            eventTimer.setTimerState(z, elapsedRealtime);
                        }
                    }
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public JSONObject getDynamicProperty() {
        try {
            SensorsDataDynamicSuperProperties sensorsDataDynamicSuperProperties = this.mDynamicSuperPropertiesCallBack;
            if (sensorsDataDynamicSuperProperties == null) {
                return null;
            }
            JSONObject dynamicProperty = sensorsDataDynamicSuperProperties.getDynamicSuperProperties();
            SADataHelper.assertPropertyTypes(dynamicProperty);
            return dynamicProperty;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    private void mergerDynamicAndSuperProperties(JSONObject eventProperty, JSONObject dynamicProperty) {
        JSONObject superProperties = getSuperProperties();
        if (dynamicProperty == null) {
            dynamicProperty = getDynamicProperty();
        }
        SensorsDataUtils.mergeJSONObject(SensorsDataUtils.mergeSuperJSONObject(dynamicProperty, superProperties), eventProperty);
    }

    private void showDebugModeWarning() {
        try {
            if (this.mDebugMode != SensorsDataAPI.DebugMode.DEBUG_OFF && !TextUtils.isEmpty(this.mServerUrl)) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        String info = null;
                        AbstractSensorsDataAPI abstractSensorsDataAPI = AbstractSensorsDataAPI.this;
                        SensorsDataAPI.DebugMode debugMode = abstractSensorsDataAPI.mDebugMode;
                        if (debugMode == SensorsDataAPI.DebugMode.DEBUG_ONLY) {
                            info = "现在您打开了 SensorsData SDK 的 'DEBUG_ONLY' 模式，此模式下只校验数据但不导入数据，数据出错时会以 Toast 的方式提示开发者，请上线前一定使用 DEBUG_OFF 模式。";
                        } else if (debugMode == SensorsDataAPI.DebugMode.DEBUG_AND_TRACK) {
                            info = "现在您打开了神策 SensorsData SDK 的 'DEBUG_AND_TRACK' 模式，此模式下校验数据并且导入数据，数据出错时会以 Toast 的方式提示开发者，请上线前一定使用 DEBUG_OFF 模式。";
                        }
                        CharSequence appName = AppInfoUtils.getAppName(abstractSensorsDataAPI.mContext);
                        if (!TextUtils.isEmpty(appName)) {
                            info = String.format(Locale.CHINA, "%s：%s", new Object[]{appName, info});
                        }
                        ToastUtil.showLong(AbstractSensorsDataAPI.this.mContext, info);
                    }
                });
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private boolean isEnterDb(String eventName, JSONObject eventProperties) {
        boolean enterDb = true;
        if (this.mTrackEventCallBack != null) {
            SALog.i(TAG, "SDK have set trackEvent callBack");
            try {
                enterDb = this.mTrackEventCallBack.onTrackEvent(eventName, eventProperties);
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
            if (enterDb) {
                try {
                    Iterator<String> it = eventProperties.keys();
                    while (it.hasNext()) {
                        String key = it.next();
                        Object value = eventProperties.opt(key);
                        if (value instanceof Date) {
                            eventProperties.put(key, (Object) TimeUtils.formatDate((Date) value, Locale.CHINA));
                        } else {
                            eventProperties.put(key, value);
                        }
                    }
                } catch (Exception e2) {
                    SALog.printStackTrace(e2);
                }
            }
        }
        return enterDb;
    }

    /* access modifiers changed from: private */
    /*  JADX ERROR: JadxRuntimeException in pass: CodeShrinkVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Don't wrap MOVE or CONST insns: 0x0285: MOVE  (r3v21 org.json.JSONObject) = (r30v0 'properties' org.json.JSONObject)
        	at jadx.core.dex.instructions.args.InsnArg.wrapArg(InsnArg.java:164)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.assignInline(CodeShrinkVisitor.java:133)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.checkInline(CodeShrinkVisitor.java:118)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:65)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:43)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.visit(CodeShrinkVisitor.java:35)
        */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0200  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0224  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x022c  */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x0235  */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x025e  */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x0271  */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x0295  */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x02dc  */
    /* JADX WARNING: Removed duplicated region for block: B:161:0x02e4  */
    /* JADX WARNING: Removed duplicated region for block: B:164:0x0321  */
    /* JADX WARNING: Removed duplicated region for block: B:168:0x0330  */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x03ad  */
    /* JADX WARNING: Removed duplicated region for block: B:209:0x03d6 A[Catch:{ Exception -> 0x03e1 }, LOOP:0: B:207:0x03d0->B:209:0x03d6, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:215:0x03eb A[Catch:{ Exception -> 0x03f3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:220:0x0406  */
    /* JADX WARNING: Removed duplicated region for block: B:223:0x0412  */
    /* JADX WARNING: Removed duplicated region for block: B:225:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00fa A[SYNTHETIC, Splitter:B:49:0x00fa] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0126  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x012c  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0178 A[Catch:{ Exception -> 0x020f }] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0188 A[Catch:{ Exception -> 0x0209 }] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0190 A[Catch:{ Exception -> 0x0209 }] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x01a3 A[SYNTHETIC, Splitter:B:82:0x01a3] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x01d1 A[Catch:{ Exception -> 0x0207 }] */
    public void trackEventInternal(com.sensorsdata.analytics.android.sdk.internal.beans.EventType r28, java.lang.String r29, org.json.JSONObject r30, org.json.JSONObject r31, org.json.JSONObject r32, java.lang.String r33, java.lang.String r34, java.lang.String r35, com.sensorsdata.analytics.android.sdk.internal.beans.EventTimer r36) {
        /*
            r27 = this;
            r1 = r27
            r2 = r28
            r3 = r29
            r4 = r30
            r5 = r31
            java.lang.String r6 = "$sf_internal_login_id"
            java.lang.String r7 = "$sf_internal_anonymous_id"
            java.lang.String r8 = "$time"
            java.lang.String r9 = "$token"
            java.lang.String r10 = "$project"
            r11 = 0
            java.lang.String r12 = "6.2.7"
            r13 = 0
            long r14 = java.lang.System.currentTimeMillis()
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r16 = r0
            r17 = r11
            java.lang.String r11 = "$AppStart"
            r18 = r12
            java.lang.String r12 = "$lib_version"
            r19 = r13
            java.lang.String r13 = "$lib_detail"
            r20 = r14
            java.lang.String r14 = "code"
            java.lang.String r15 = "$app_version"
            java.lang.String r2 = "$lib_method"
            if (r4 == 0) goto L_0x00de
            boolean r0 = r4.has(r13)     // Catch:{ Exception -> 0x0049 }
            if (r0 == 0) goto L_0x0048
            java.lang.String r0 = r4.getString(r13)     // Catch:{ Exception -> 0x0049 }
            r17 = r0
            r4.remove(r13)     // Catch:{ Exception -> 0x0049 }
        L_0x0048:
            goto L_0x004d
        L_0x0049:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
        L_0x004d:
            java.lang.String r0 = "$AppEnd"
            boolean r0 = r0.equals(r3)     // Catch:{ Exception -> 0x00a7 }
            r22 = r13
            java.lang.String r13 = "event_time"
            if (r0 == 0) goto L_0x008a
            long r23 = r4.optLong(r13)     // Catch:{ Exception -> 0x00a5 }
            r25 = 2000(0x7d0, double:9.88E-321)
            int r0 = (r23 > r25 ? 1 : (r23 == r25 ? 0 : -1))
            if (r0 <= 0) goto L_0x0065
            r20 = r23
        L_0x0065:
            java.lang.String r0 = r4.optString(r12)     // Catch:{ Exception -> 0x00a5 }
            java.lang.String r25 = r4.optString(r15)     // Catch:{ Exception -> 0x00a5 }
            r19 = r25
            boolean r25 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x00a5 }
            if (r25 != 0) goto L_0x0078
            r18 = r0
            goto L_0x007b
        L_0x0078:
            r4.remove(r12)     // Catch:{ Exception -> 0x00a5 }
        L_0x007b:
            boolean r25 = android.text.TextUtils.isEmpty(r19)     // Catch:{ Exception -> 0x00a5 }
            if (r25 == 0) goto L_0x0084
            r4.remove(r15)     // Catch:{ Exception -> 0x00a5 }
        L_0x0084:
            r4.remove(r13)     // Catch:{ Exception -> 0x00a5 }
            r13 = r19
            goto L_0x00a4
        L_0x008a:
            boolean r0 = r11.equals(r3)     // Catch:{ Exception -> 0x00a5 }
            if (r0 == 0) goto L_0x00a2
            long r23 = r4.optLong(r13)     // Catch:{ Exception -> 0x00a5 }
            r25 = 0
            int r0 = (r23 > r25 ? 1 : (r23 == r25 ? 0 : -1))
            if (r0 <= 0) goto L_0x009c
            r20 = r23
        L_0x009c:
            r4.remove(r13)     // Catch:{ Exception -> 0x00a5 }
            r13 = r19
            goto L_0x00a4
        L_0x00a2:
            r13 = r19
        L_0x00a4:
            goto L_0x00af
        L_0x00a5:
            r0 = move-exception
            goto L_0x00aa
        L_0x00a7:
            r0 = move-exception
            r22 = r13
        L_0x00aa:
            r13 = r19
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
        L_0x00af:
            com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils.mergeJSONObject(r30, r31)
            boolean r0 = r28.isTrack()
            if (r0 == 0) goto L_0x00d6
            java.lang.String r0 = r4.optString(r2)
            r19 = r13
            java.lang.String r13 = "autoTrack"
            boolean r0 = r13.equals(r0)
            if (r0 == 0) goto L_0x00cd
            r14 = r16
            r14.put((java.lang.String) r2, (java.lang.Object) r13)
            r13 = r14
            goto L_0x00ee
        L_0x00cd:
            r13 = r16
            r13.put((java.lang.String) r2, (java.lang.Object) r14)
            r5.put((java.lang.String) r2, (java.lang.Object) r14)
            goto L_0x00ee
        L_0x00d6:
            r19 = r13
            r13 = r16
            r13.put((java.lang.String) r2, (java.lang.Object) r14)
            goto L_0x00ee
        L_0x00de:
            r22 = r13
            r13 = r16
            r13.put((java.lang.String) r2, (java.lang.Object) r14)
            boolean r0 = r28.isTrack()
            if (r0 == 0) goto L_0x00ee
            r5.put((java.lang.String) r2, (java.lang.Object) r14)
        L_0x00ee:
            r16 = r6
            r2 = r18
            r14 = r19
            r18 = r7
            r6 = r20
            if (r36 == 0) goto L_0x0116
            java.lang.String r0 = r36.duration()     // Catch:{ Exception -> 0x0112 }
            double r19 = java.lang.Double.parseDouble(r0)     // Catch:{ Exception -> 0x0112 }
            r23 = r19
            r19 = 0
            r3 = r23
            int r0 = (r3 > r19 ? 1 : (r3 == r19 ? 0 : -1))
            if (r0 <= 0) goto L_0x0111
            java.lang.String r0 = "event_duration"
            r5.put((java.lang.String) r0, (double) r3)     // Catch:{ Exception -> 0x0112 }
        L_0x0111:
            goto L_0x0116
        L_0x0112:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
        L_0x0116:
            java.lang.String r0 = "$lib"
            java.lang.String r3 = "Android"
            r13.put((java.lang.String) r0, (java.lang.Object) r3)
            r13.put((java.lang.String) r12, (java.lang.Object) r2)
            boolean r0 = android.text.TextUtils.isEmpty(r14)
            if (r0 == 0) goto L_0x012c
            com.sensorsdata.analytics.android.sdk.util.SAContextManager r0 = r1.mSAContextManager
            r0.addKeyIfExist(r13, r15)
            goto L_0x012f
        L_0x012c:
            r13.put((java.lang.String) r15, (java.lang.Object) r14)
        L_0x012f:
            com.sensorsdata.analytics.android.sdk.data.persistent.PersistentSuperProperties r0 = r1.mSuperProperties
            java.lang.Object r0 = r0.get()
            r3 = r0
            org.json.JSONObject r3 = (org.json.JSONObject) r3
            if (r3 == 0) goto L_0x0147
            boolean r0 = r3.has(r15)
            if (r0 == 0) goto L_0x0147
            java.lang.Object r0 = r3.get(r15)
            r13.put((java.lang.String) r15, (java.lang.Object) r0)
        L_0x0147:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r4 = r0
            java.security.SecureRandom r0 = new java.security.SecureRandom     // Catch:{ Exception -> 0x015d }
            r0.<init>()     // Catch:{ Exception -> 0x015d }
            java.lang.String r12 = "_track_id"
            int r15 = r0.nextInt()     // Catch:{ Exception -> 0x015d }
            r4.put((java.lang.String) r12, (int) r15)     // Catch:{ Exception -> 0x015d }
            goto L_0x015e
        L_0x015d:
            r0 = move-exception
        L_0x015e:
            java.lang.String r0 = "time"
            r4.put((java.lang.String) r0, (long) r6)
            java.lang.String r12 = r28.getEventType()
            java.lang.String r15 = "type"
            r4.put((java.lang.String) r15, (java.lang.Object) r12)
            java.lang.String r12 = r27.getAnonymousId()
            boolean r15 = r5.has(r10)     // Catch:{ Exception -> 0x020f }
            if (r15 == 0) goto L_0x0188
            java.lang.String r15 = "project"
            r19 = r2
            java.lang.String r2 = r5.optString(r10)     // Catch:{ Exception -> 0x0209 }
            r4.put((java.lang.String) r15, (java.lang.Object) r2)     // Catch:{ Exception -> 0x0209 }
            r5.remove(r10)     // Catch:{ Exception -> 0x0209 }
            goto L_0x018a
        L_0x0188:
            r19 = r2
        L_0x018a:
            boolean r2 = r5.has(r9)     // Catch:{ Exception -> 0x0209 }
            if (r2 == 0) goto L_0x019d
            java.lang.String r2 = "token"
            java.lang.String r10 = r5.optString(r9)     // Catch:{ Exception -> 0x0209 }
            r4.put((java.lang.String) r2, (java.lang.Object) r10)     // Catch:{ Exception -> 0x0209 }
            r5.remove(r9)     // Catch:{ Exception -> 0x0209 }
        L_0x019d:
            boolean r2 = r5.has(r8)     // Catch:{ Exception -> 0x0209 }
            if (r2 == 0) goto L_0x01c7
            java.lang.Object r2 = r5.opt(r8)     // Catch:{ Exception -> 0x01c0 }
            boolean r9 = r2 instanceof java.util.Date     // Catch:{ Exception -> 0x01c0 }
            if (r9 == 0) goto L_0x01bf
            r9 = r2
            java.util.Date r9 = (java.util.Date) r9     // Catch:{ Exception -> 0x01c0 }
            boolean r9 = com.sensorsdata.analytics.android.sdk.util.TimeUtils.isDateValid((java.util.Date) r9)     // Catch:{ Exception -> 0x01c0 }
            if (r9 == 0) goto L_0x01bf
            r9 = r2
            java.util.Date r9 = (java.util.Date) r9     // Catch:{ Exception -> 0x01c0 }
            long r9 = r9.getTime()     // Catch:{ Exception -> 0x01c0 }
            r6 = r9
            r4.put((java.lang.String) r0, (long) r6)     // Catch:{ Exception -> 0x01c0 }
        L_0x01bf:
            goto L_0x01c4
        L_0x01c0:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)     // Catch:{ Exception -> 0x0209 }
        L_0x01c4:
            r5.remove(r8)     // Catch:{ Exception -> 0x0209 }
        L_0x01c7:
            java.lang.String r0 = "$PlanPopupDisplay"
            r2 = r29
            boolean r0 = r0.equals(r2)     // Catch:{ Exception -> 0x0207 }
            if (r0 == 0) goto L_0x0200
            r8 = r18
            boolean r0 = r5.has(r8)     // Catch:{ Exception -> 0x0207 }
            if (r0 == 0) goto L_0x01e1
            java.lang.String r0 = r5.optString(r8)     // Catch:{ Exception -> 0x0207 }
            r12 = r0
            r5.remove(r8)     // Catch:{ Exception -> 0x0207 }
        L_0x01e1:
            r8 = r16
            boolean r0 = r5.has(r8)     // Catch:{ Exception -> 0x0207 }
            if (r0 == 0) goto L_0x01f2
            java.lang.String r0 = r5.optString(r8)     // Catch:{ Exception -> 0x0207 }
            r9 = r0
            r5.remove(r8)     // Catch:{ Exception -> 0x01fe }
            goto L_0x01f4
        L_0x01f2:
            r9 = r34
        L_0x01f4:
            boolean r0 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Exception -> 0x01fe }
            if (r0 != 0) goto L_0x01fc
            r0 = r9
            goto L_0x0204
        L_0x01fc:
            r0 = r12
            goto L_0x0204
        L_0x01fe:
            r0 = move-exception
            goto L_0x0216
        L_0x0200:
            r0 = r33
            r9 = r34
        L_0x0204:
            r7 = r6
            r6 = r0
            goto L_0x021c
        L_0x0207:
            r0 = move-exception
            goto L_0x020c
        L_0x0209:
            r0 = move-exception
            r2 = r29
        L_0x020c:
            r9 = r34
            goto L_0x0216
        L_0x020f:
            r0 = move-exception
            r19 = r2
            r2 = r29
            r9 = r34
        L_0x0216:
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
            r7 = r6
            r6 = r33
        L_0x021c:
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            java.lang.String r10 = "distinct_id"
            if (r0 == 0) goto L_0x022c
            java.lang.String r0 = r27.getAnonymousId()
            r4.put((java.lang.String) r10, (java.lang.Object) r0)
            goto L_0x022f
        L_0x022c:
            r4.put((java.lang.String) r10, (java.lang.Object) r6)
        L_0x022f:
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            if (r0 != 0) goto L_0x023a
            java.lang.String r0 = "login_id"
            r4.put((java.lang.String) r0, (java.lang.Object) r9)
        L_0x023a:
            java.lang.String r0 = "anonymous_id"
            r4.put((java.lang.String) r0, (java.lang.Object) r12)
            java.lang.String r0 = "identities"
            r10 = r32
            r4.put((java.lang.String) r0, (java.lang.Object) r10)
            java.lang.String r0 = "lib"
            r4.put((java.lang.String) r0, (java.lang.Object) r13)
            com.sensorsdata.analytics.android.sdk.internal.beans.EventType r0 = com.sensorsdata.analytics.android.sdk.internal.beans.EventType.TRACK
            java.lang.String r15 = "event"
            r16 = r3
            r3 = r28
            if (r3 == r0) goto L_0x0271
            com.sensorsdata.analytics.android.sdk.internal.beans.EventType r0 = com.sensorsdata.analytics.android.sdk.internal.beans.EventType.TRACK_ID_BIND
            if (r3 == r0) goto L_0x0271
            com.sensorsdata.analytics.android.sdk.internal.beans.EventType r0 = com.sensorsdata.analytics.android.sdk.internal.beans.EventType.TRACK_ID_UNBIND
            if (r3 != r0) goto L_0x025e
            goto L_0x0271
        L_0x025e:
            com.sensorsdata.analytics.android.sdk.internal.beans.EventType r0 = com.sensorsdata.analytics.android.sdk.internal.beans.EventType.TRACK_SIGNUP
            if (r3 != r0) goto L_0x026e
            r4.put((java.lang.String) r15, (java.lang.Object) r2)
            java.lang.String r0 = "original_id"
            r15 = r35
            r4.put((java.lang.String) r0, (java.lang.Object) r15)
            goto L_0x027d
        L_0x026e:
            r15 = r35
            goto L_0x027d
        L_0x0271:
            r4.put((java.lang.String) r15, (java.lang.Object) r2)
            boolean r0 = r1.isFirstDay(r7)
            java.lang.String r15 = "$is_first_day"
            r5.put((java.lang.String) r15, (boolean) r0)
        L_0x027d:
            boolean r0 = r1.mAutoTrack
            java.lang.String r15 = "%s##%s##%s##%s"
            r18 = 0
            if (r0 == 0) goto L_0x02de
            r3 = r30
            if (r3 == 0) goto L_0x02de
            boolean r0 = com.sensorsdata.analytics.android.sdk.SensorsDataAPI.AutoTrackEventType.isAutoTrackType(r29)
            if (r0 == 0) goto L_0x02de
            com.sensorsdata.analytics.android.sdk.SensorsDataAPI$AutoTrackEventType r0 = com.sensorsdata.analytics.android.sdk.SensorsDataAPI.AutoTrackEventType.autoTrackEventTypeFromEventName(r29)
            if (r0 == 0) goto L_0x02dc
            boolean r20 = r1.isAutoTrackEventTypeIgnored((com.sensorsdata.analytics.android.sdk.SensorsDataAPI.AutoTrackEventType) r0)
            if (r20 != 0) goto L_0x02d9
            r34 = r0
            java.lang.String r0 = "$screen_name"
            boolean r0 = r3.has(r0)
            if (r0 == 0) goto L_0x02de
            java.lang.String r0 = "$screen_name"
            java.lang.String r0 = r3.getString(r0)
            boolean r20 = android.text.TextUtils.isEmpty(r0)
            if (r20 != 0) goto L_0x02d6
            java.lang.String r3 = "\\|"
            java.lang.String[] r3 = r0.split(r3)
            r20 = r0
            int r0 = r3.length
            if (r0 <= 0) goto L_0x02de
            r0 = 4
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r21 = r3[r18]
            r0[r18] = r21
            java.lang.String r21 = ""
            r23 = 1
            r0[r23] = r21
            r23 = 2
            r0[r23] = r21
            r23 = 3
            r0[r23] = r21
            java.lang.String r17 = java.lang.String.format(r15, r0)
            goto L_0x02de
        L_0x02d6:
            r20 = r0
            goto L_0x02de
        L_0x02d9:
            r34 = r0
            goto L_0x02de
        L_0x02dc:
            r34 = r0
        L_0x02de:
            boolean r0 = android.text.TextUtils.isEmpty(r17)
            if (r0 == 0) goto L_0x0321
            java.lang.Exception r0 = new java.lang.Exception
            r0.<init>()
            java.lang.StackTraceElement[] r0 = r0.getStackTrace()
            int r3 = r0.length
            r34 = r6
            r6 = 1
            if (r3 <= r6) goto L_0x0323
            r3 = r0[r18]
            r6 = 4
            java.lang.Object[] r6 = new java.lang.Object[r6]
            java.lang.String r20 = r3.getClassName()
            r6[r18] = r20
            java.lang.String r18 = r3.getMethodName()
            r20 = 1
            r6[r20] = r18
            r18 = 2
            java.lang.String r20 = r3.getFileName()
            r6[r18] = r20
            r18 = 3
            int r20 = r3.getLineNumber()
            java.lang.Integer r20 = java.lang.Integer.valueOf(r20)
            r6[r18] = r20
            java.lang.String r17 = java.lang.String.format(r15, r6)
            r3 = r17
            goto L_0x0325
        L_0x0321:
            r34 = r6
        L_0x0323:
            r3 = r17
        L_0x0325:
            r6 = r22
            r13.put((java.lang.String) r6, (java.lang.Object) r3)
            boolean r0 = r28.isTrack()
            if (r0 == 0) goto L_0x03ad
            com.sensorsdata.analytics.android.sdk.SAConfigOptions r0 = mSAConfigOptions
            boolean r0 = r0.isDisableDeviceId()
            java.lang.String r6 = "$anonymization_id"
            java.lang.String r15 = "$device_id"
            if (r0 == 0) goto L_0x034b
            boolean r0 = r5.has(r6)
            if (r0 == 0) goto L_0x0347
            com.sensorsdata.analytics.android.sdk.util.SAContextManager r0 = r1.mSAContextManager
            r0.addKeyIfExist(r5, r6)
        L_0x0347:
            r5.remove(r15)
            goto L_0x0359
        L_0x034b:
            boolean r0 = r5.has(r15)
            if (r0 == 0) goto L_0x0356
            com.sensorsdata.analytics.android.sdk.util.SAContextManager r0 = r1.mSAContextManager
            r0.addKeyIfExist(r5, r15)
        L_0x0356:
            r5.remove(r6)
        L_0x0359:
            com.sensorsdata.analytics.android.sdk.SessionRelatedManager r0 = com.sensorsdata.analytics.android.sdk.SessionRelatedManager.getInstance()     // Catch:{ Exception -> 0x0361 }
            r0.handleEventOfSession(r2, r5, r7)     // Catch:{ Exception -> 0x0361 }
            goto L_0x0365
        L_0x0361:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
        L_0x0365:
            boolean r6 = r1.isEnterDb(r2, r5)
            if (r6 != 0) goto L_0x0382
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r2)
            java.lang.String r11 = " event can not enter database"
            r0.append(r11)
            java.lang.String r0 = r0.toString()
            java.lang.String r11 = "SA.SensorsDataAPI"
            com.sensorsdata.analytics.android.sdk.SALog.d(r11, r0)
            return
        L_0x0382:
            boolean r0 = r1.isTrackEventWithPluginVersion
            if (r0 != 0) goto L_0x03aa
            java.lang.String r0 = "$lib_plugin_version"
            boolean r0 = r5.has(r0)
            if (r0 != 0) goto L_0x03aa
            org.json.JSONArray r15 = r27.getPluginVersion()
            if (r15 != 0) goto L_0x039a
            r17 = r3
            r3 = 1
            r1.isTrackEventWithPluginVersion = r3
            goto L_0x03af
        L_0x039a:
            r17 = r3
            r3 = 1
            java.lang.String r0 = "$lib_plugin_version"
            r5.put((java.lang.String) r0, (java.lang.Object) r15)     // Catch:{ Exception -> 0x03a5 }
            r1.isTrackEventWithPluginVersion = r3     // Catch:{ Exception -> 0x03a5 }
            goto L_0x03af
        L_0x03a5:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
            goto L_0x03af
        L_0x03aa:
            r17 = r3
            goto L_0x03af
        L_0x03ad:
            r17 = r3
        L_0x03af:
            com.sensorsdata.analytics.android.sdk.util.SADataHelper.assertPropertyTypes(r31)
            java.lang.String r0 = "properties"
            r4.put((java.lang.String) r0, (java.lang.Object) r5)
            com.sensorsdata.analytics.android.sdk.util.SAContextManager r0 = r1.mSAContextManager     // Catch:{ Exception -> 0x03e1 }
            java.util.List r0 = r0.getEventListenerList()     // Catch:{ Exception -> 0x03e1 }
            if (r0 == 0) goto L_0x03e0
            boolean r0 = r28.isTrack()     // Catch:{ Exception -> 0x03e1 }
            if (r0 == 0) goto L_0x03e0
            com.sensorsdata.analytics.android.sdk.util.SAContextManager r0 = r1.mSAContextManager     // Catch:{ Exception -> 0x03e1 }
            java.util.List r0 = r0.getEventListenerList()     // Catch:{ Exception -> 0x03e1 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ Exception -> 0x03e1 }
        L_0x03d0:
            boolean r3 = r0.hasNext()     // Catch:{ Exception -> 0x03e1 }
            if (r3 == 0) goto L_0x03e0
            java.lang.Object r3 = r0.next()     // Catch:{ Exception -> 0x03e1 }
            com.sensorsdata.analytics.android.sdk.listener.SAEventListener r3 = (com.sensorsdata.analytics.android.sdk.listener.SAEventListener) r3     // Catch:{ Exception -> 0x03e1 }
            r3.trackEvent(r4)     // Catch:{ Exception -> 0x03e1 }
            goto L_0x03d0
        L_0x03e0:
            goto L_0x03e5
        L_0x03e1:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
        L_0x03e5:
            boolean r0 = r28.isTrack()     // Catch:{ Exception -> 0x03f3 }
            if (r0 == 0) goto L_0x03f2
            com.sensorsdata.analytics.android.sdk.monitor.TrackMonitor r0 = com.sensorsdata.analytics.android.sdk.monitor.TrackMonitor.getInstance()     // Catch:{ Exception -> 0x03f3 }
            r0.callTrack(r4)     // Catch:{ Exception -> 0x03f3 }
        L_0x03f2:
            goto L_0x03f7
        L_0x03f3:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)
        L_0x03f7:
            com.sensorsdata.analytics.android.sdk.AnalyticsMessages r0 = r1.mMessages
            java.lang.String r3 = r28.getEventType()
            r0.enqueueEventMessage(r3, r4)
            boolean r0 = r11.equals(r2)
            if (r0 == 0) goto L_0x040c
            com.sensorsdata.analytics.android.sdk.util.SAContextManager r0 = r1.mSAContextManager
            r3 = 1
            r0.setAppStartSuccess(r3)
        L_0x040c:
            boolean r0 = com.sensorsdata.analytics.android.sdk.SALog.isLogEnabled()
            if (r0 == 0) goto L_0x0431
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = "track event:\n"
            r0.append(r3)
            java.lang.String r3 = r4.toString()
            java.lang.String r3 = com.sensorsdata.analytics.android.sdk.util.JSONUtils.formatJson(r3)
            r0.append(r3)
            java.lang.String r0 = r0.toString()
            java.lang.String r3 = "SA.SensorsDataAPI"
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r3, (java.lang.String) r0)
        L_0x0431:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI.trackEventInternal(com.sensorsdata.analytics.android.sdk.internal.beans.EventType, java.lang.String, org.json.JSONObject, org.json.JSONObject, org.json.JSONObject, java.lang.String, java.lang.String, java.lang.String, com.sensorsdata.analytics.android.sdk.internal.beans.EventTimer):void");
    }

    private void transformEventTaskQueue(EventType eventType, String eventName, JSONObject properties, JSONObject sendProperties, JSONObject identities, String distinctId, String loginId, String originalDistinctId, EventTimer eventTimer) {
        String str = eventName;
        JSONObject jSONObject = sendProperties;
        try {
            if (!jSONObject.has("$time") && !"$AppStart".equals(str) && !"$AppEnd".equals(str)) {
                jSONObject.put("$time", (Object) new Date(System.currentTimeMillis()));
            }
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        }
        final EventType eventType2 = eventType;
        final String str2 = eventName;
        final JSONObject jSONObject2 = properties;
        final JSONObject jSONObject3 = sendProperties;
        final JSONObject jSONObject4 = identities;
        final String str3 = distinctId;
        final String str4 = loginId;
        final EventTimer eventTimer2 = eventTimer;
        final String str5 = originalDistinctId;
        this.mTrackTaskManager.transformTaskQueue(new Runnable() {
            public void run() {
                try {
                    if (eventType2.isTrack()) {
                        JSONUtils.mergeDistinctProperty(SensorsDataPropertyPluginManager.getInstance().properties(str2, eventType2, jSONObject2), jSONObject3);
                    }
                    JSONObject jsonObject = jSONObject4;
                    if (!(jsonObject == null || eventType2 == EventType.TRACK_ID_UNBIND)) {
                        AbstractSensorsDataAPI.this.mUserIdentityAPI.updateIdentities(jsonObject);
                    }
                    if ("$SignUp".equals(str2)) {
                        AbstractSensorsDataAPI abstractSensorsDataAPI = AbstractSensorsDataAPI.this;
                        abstractSensorsDataAPI.trackEventInternal(eventType2, str2, jSONObject2, jSONObject3, jSONObject4, str3, str4, abstractSensorsDataAPI.getAnonymousId(), eventTimer2);
                        return;
                    }
                    AbstractSensorsDataAPI.this.trackEventInternal(eventType2, str2, jSONObject2, jSONObject3, jSONObject4, str3, str4, str5, eventTimer2);
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    private void transformH5TaskQueue(String eventInfo) {
        try {
            final JSONObject eventObject = new JSONObject(eventInfo);
            JSONObject propertiesObject = eventObject.optJSONObject("properties");
            if (propertiesObject != null && !propertiesObject.has("$time")) {
                propertiesObject.put("$time", System.currentTimeMillis());
            }
            if (SALog.isLogEnabled()) {
                SALog.i(TAG, "track H5, isDataCollectEnable = false, eventInfo = " + JSONUtils.formatJson(eventInfo));
            }
            this.mTrackTaskManager.transformTaskQueue(new Runnable() {
                public void run() {
                    try {
                        AbstractSensorsDataAPI.this.trackEventH5(eventObject.toString());
                    } catch (Exception e) {
                        SALog.printStackTrace(e);
                    }
                }
            });
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    private void transformItemTaskQueue(String itemType, String itemId, String eventType, long time, JSONObject properties) {
        if (SALog.isLogEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("track item, isDataCollectEnable = false, itemType = ");
            String str = itemType;
            sb.append(itemType);
            sb.append(",itemId = ");
            String str2 = itemId;
            sb.append(itemId);
            SALog.i(TAG, sb.toString());
        } else {
            String str3 = itemType;
            String str4 = itemId;
        }
        final String str5 = itemType;
        final String str6 = itemId;
        final String str7 = eventType;
        final long j = time;
        final JSONObject jSONObject = properties;
        this.mTrackTaskManager.transformTaskQueue(new Runnable() {
            public void run() {
                try {
                    AbstractSensorsDataAPI.this.trackItemEvent(str5, str6, str7, j, jSONObject);
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
            }
        });
    }

    private JSONArray getPluginVersion() {
        try {
            if (TextUtils.isEmpty(SensorsDataAPI.ANDROID_PLUGIN_VERSION)) {
                return null;
            }
            SALog.i(TAG, "android plugin version: " + SensorsDataAPI.ANDROID_PLUGIN_VERSION);
            JSONArray libPluginVersion = new JSONArray();
            libPluginVersion.put((Object) "android:" + SensorsDataAPI.ANDROID_PLUGIN_VERSION);
            return libPluginVersion;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    private void registerLifecycleCallbacks() {
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                Application app = (Application) this.mContext.getApplicationContext();
                SensorsDataActivityLifecycleCallbacks lifecycleCallbacks = new SensorsDataActivityLifecycleCallbacks();
                app.registerActivityLifecycleCallbacks(AppStateManager.getInstance());
                ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks((SensorsDataAPI) this, this.mFirstStart, this.mFirstDay, this.mContext);
                this.mActivityLifecycleCallbacks = activityLifecycleCallbacks;
                lifecycleCallbacks.addActivityLifecycleCallbacks(activityLifecycleCallbacks);
                SensorsDataExceptionHandler.addExceptionListener(this.mActivityLifecycleCallbacks);
                FragmentTrackHelper.addFragmentCallbacks(new FragmentViewScreenCallbacks());
                if (mSAConfigOptions.isTrackPageLeave()) {
                    ActivityPageLeaveCallbacks pageLeaveCallbacks = new ActivityPageLeaveCallbacks(mSAConfigOptions.mIgnorePageLeave);
                    lifecycleCallbacks.addActivityLifecycleCallbacks(pageLeaveCallbacks);
                    SensorsDataExceptionHandler.addExceptionListener(pageLeaveCallbacks);
                    if (mSAConfigOptions.isTrackFragmentPageLeave()) {
                        FragmentPageLeaveCallbacks fragmentPageLeaveCallbacks = new FragmentPageLeaveCallbacks(mSAConfigOptions.mIgnorePageLeave);
                        FragmentTrackHelper.addFragmentCallbacks(fragmentPageLeaveCallbacks);
                        SensorsDataExceptionHandler.addExceptionListener(fragmentPageLeaveCallbacks);
                    }
                }
                if (mSAConfigOptions.isEnableTrackPush()) {
                    lifecycleCallbacks.addActivityLifecycleCallbacks(new PushLifecycleCallbacks());
                }
                app.registerActivityLifecycleCallbacks(lifecycleCallbacks);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private void registerObserver() {
        SensorsDataContentObserver contentObserver = new SensorsDataContentObserver(this.mUserIdentityAPI);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        contentResolver.registerContentObserver(DbParams.getInstance().getDataCollectUri(), false, contentObserver);
        contentResolver.registerContentObserver(DbParams.getInstance().getSessionTimeUri(), false, contentObserver);
        contentResolver.registerContentObserver(DbParams.getInstance().getLoginIdUri(), false, contentObserver);
        contentResolver.registerContentObserver(DbParams.getInstance().getDisableSDKUri(), false, contentObserver);
        contentResolver.registerContentObserver(DbParams.getInstance().getEnableSDKUri(), false, contentObserver);
        contentResolver.registerContentObserver(DbParams.getInstance().getUserIdentities(), false, contentObserver);
    }

    public boolean isDeepLinkInstallSource() {
        return this.mEnableDeepLinkInstallSource;
    }

    /* access modifiers changed from: protected */
    public void delayInitTask() {
        this.mTrackTaskManager.addTrackEventTask(new Runnable() {
            public void run() {
                try {
                    if (AbstractSensorsDataAPI.this.isSaveDeepLinkInfo()) {
                        ChannelUtils.loadUtmByLocal(AbstractSensorsDataAPI.this.mContext);
                    } else {
                        ChannelUtils.clearLocalUtm(AbstractSensorsDataAPI.this.mContext);
                    }
                    AbstractSensorsDataAPI.this.registerNetworkListener();
                } catch (Exception ex) {
                    SALog.printStackTrace(ex);
                }
            }
        });
    }

    private void getCarrier(JSONObject property) {
        try {
            if (TextUtils.isEmpty(property.optString("$carrier")) && mSAConfigOptions.isDataCollectEnable) {
                String carrier = SensorsDataUtils.getCarrier(this.mContext);
                if (!TextUtils.isEmpty(carrier)) {
                    property.put("$carrier", (Object) carrier);
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }
}
