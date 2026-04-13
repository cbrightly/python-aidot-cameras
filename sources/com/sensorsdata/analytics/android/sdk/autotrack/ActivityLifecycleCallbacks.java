package com.sensorsdata.analytics.android.sdk.autotrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.ScreenAutoTracker;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SensorsDataActivityLifecycleCallbacks;
import com.sensorsdata.analytics.android.sdk.SensorsDataExceptionHandler;
import com.sensorsdata.analytics.android.sdk.SessionRelatedManager;
import com.sensorsdata.analytics.android.sdk.advert.utils.ChannelUtils;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbAdapter;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentFirstDay;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentFirstStart;
import com.sensorsdata.analytics.android.sdk.deeplink.DeepLinkManager;
import com.sensorsdata.analytics.android.sdk.dialog.SensorsDataDialogUtils;
import com.sensorsdata.analytics.android.sdk.util.AopUtil;
import com.sensorsdata.analytics.android.sdk.util.AppInfoUtils;
import com.sensorsdata.analytics.android.sdk.util.SADataHelper;
import com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils;
import com.sensorsdata.analytics.android.sdk.util.TimeUtils;
import com.sensorsdata.analytics.android.sdk.visual.HeatMapService;
import com.sensorsdata.analytics.android.sdk.visual.VisualizedAutoTrackService;
import java.util.HashSet;
import java.util.Set;
import meshsdk.cache.CacheHandler;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;

public class ActivityLifecycleCallbacks implements SensorsDataActivityLifecycleCallbacks.SAActivityLifecycleCallbacks, SensorsDataExceptionHandler.SAExceptionListener {
    private static final String APP_VERSION = "$app_version";
    private static final String EVENT_DURATION = "event_duration";
    private static final String EVENT_TIME = "event_time";
    private static final String LIB_VERSION = "$lib_version";
    private static final String TAG = "SA.ActivityLifecycleCallbacks";
    private static final int TIME_INTERVAL = 2000;
    private final String APP_END_DATA = DbParams.PersistentName.APP_END_DATA;
    private final String APP_RESET_STATE = "app_reset_state";
    private final String APP_START_TIME = DbParams.TABLE_APP_START_TIME;
    private final String ELAPSE_TIME = "elapse_time";
    private final int MESSAGE_CODE_APP_END = 0;
    private final int MESSAGE_CODE_START = 100;
    private final int MESSAGE_CODE_STOP = 200;
    private final int MESSAGE_CODE_TIMER = IjkMediaCodecInfo.RANK_SECURE;
    private final String TIME = "time";
    private JSONObject activityProperty = new JSONObject();
    private final JSONObject endDataProperty = new JSONObject();
    private Set<Integer> hashSet = new HashSet();
    private final Context mContext;
    private boolean mDataCollectState;
    private final DbAdapter mDbAdapter;
    private JSONObject mDeepLinkProperty = new JSONObject();
    private final PersistentFirstDay mFirstDay;
    private final PersistentFirstStart mFirstStart;
    /* access modifiers changed from: private */
    public Handler mHandler;
    /* access modifiers changed from: private */
    public final SensorsDataAPI mSensorsDataInstance;
    private int mStartActivityCount;
    /* access modifiers changed from: private */
    public long mStartTime;
    /* access modifiers changed from: private */
    public int mStartTimerCount;
    /* access modifiers changed from: private */
    public long messageReceiveTime = 0;
    private boolean resumeFromBackground = false;

    public ActivityLifecycleCallbacks(SensorsDataAPI instance, PersistentFirstStart firstStart, PersistentFirstDay firstDay, Context context) {
        this.mSensorsDataInstance = instance;
        this.mFirstStart = firstStart;
        this.mFirstDay = firstDay;
        this.mDbAdapter = DbAdapter.getInstance();
        this.mContext = context;
        this.mDataCollectState = AbstractSensorsDataAPI.getConfigOptions().isDataCollectEnable();
        initHandler();
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (!SensorsDataDialogUtils.isSchemeActivity(activity)) {
            SensorsDataUtils.handleSchemeUrl(activity, activity.getIntent());
        }
    }

    public void onActivityStarted(Activity activity) {
        if (!SensorsDataDialogUtils.isSchemeActivity(activity) && !hasActivity(activity)) {
            if (this.mStartActivityCount == 0) {
                buildScreenProperties(activity);
            }
            sendActivityHandleMessage(100);
            addActivity(activity);
        }
    }

    public void onActivityResumed(Activity activity) {
        JSONObject otherProperties;
        try {
            buildScreenProperties(activity);
            if (this.mSensorsDataInstance.isAutoTrackEnabled() && !this.mSensorsDataInstance.isActivityAutoTrackAppViewScreenIgnored(activity.getClass()) && !this.mSensorsDataInstance.isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType.APP_VIEW_SCREEN)) {
                JSONObject properties = new JSONObject();
                SensorsDataUtils.mergeJSONObject(this.activityProperty, properties);
                if ((activity instanceof ScreenAutoTracker) && (otherProperties = ((ScreenAutoTracker) activity).getTrackProperties()) != null) {
                    SensorsDataUtils.mergeJSONObject(otherProperties, properties);
                }
                DeepLinkManager.mergeDeepLinkProperty(properties);
                DeepLinkManager.resetDeepLinkProcessor();
                this.mSensorsDataInstance.trackViewScreen(SensorsDataUtils.getScreenUrl(activity), SADataHelper.appendLibMethodAutoTrack(properties));
            }
        } catch (Throwable e) {
            SALog.i(TAG, e);
        }
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStopped(Activity activity) {
        if (!SensorsDataDialogUtils.isSchemeActivity(activity) && hasActivity(activity)) {
            sendActivityHandleMessage(200);
            removeActivity(activity);
        }
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onNewIntent(Intent intent) {
    }

    private void initHandler() {
        try {
            HandlerThread handlerThread = new HandlerThread("SENSORS_DATA_THREAD");
            handlerThread.start();
            this.mHandler = new Handler(handlerThread.getLooper()) {
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 0:
                            if (ActivityLifecycleCallbacks.this.messageReceiveTime == 0 || SystemClock.elapsedRealtime() - ActivityLifecycleCallbacks.this.messageReceiveTime >= ((long) ActivityLifecycleCallbacks.this.mSensorsDataInstance.getSessionIntervalTime())) {
                                long unused = ActivityLifecycleCallbacks.this.messageReceiveTime = SystemClock.elapsedRealtime();
                                Bundle bundle = msg.getData();
                                String endData = bundle.getString(DbParams.PersistentName.APP_END_DATA);
                                if (bundle.getBoolean("app_reset_state")) {
                                    ActivityLifecycleCallbacks.this.resetState();
                                    if (DbAdapter.getInstance().getActivityCount() <= 0) {
                                        ActivityLifecycleCallbacks.this.trackAppEnd(endData);
                                        return;
                                    }
                                    return;
                                }
                                ActivityLifecycleCallbacks.this.trackAppEnd(endData);
                                return;
                            }
                            SALog.i(ActivityLifecycleCallbacks.TAG, "$AppEnd 事件已触发。");
                            return;
                        case 100:
                            ActivityLifecycleCallbacks.this.handleStartedMessage(msg);
                            return;
                        case 200:
                            ActivityLifecycleCallbacks.this.handleStoppedMessage(msg);
                            return;
                        case IjkMediaCodecInfo.RANK_SECURE /*300*/:
                            if (ActivityLifecycleCallbacks.this.mSensorsDataInstance.isAutoTrackEnabled() && ActivityLifecycleCallbacks.this.isAutoTrackAppEnd()) {
                                ActivityLifecycleCallbacks.this.generateAppEndData(System.currentTimeMillis(), SystemClock.elapsedRealtime());
                            } else if (!ActivityLifecycleCallbacks.this.mSensorsDataInstance.isAutoTrackEnabled() && ActivityLifecycleCallbacks.this.mStartTime > 0) {
                                long unused2 = ActivityLifecycleCallbacks.this.mStartTime = 0;
                                DbAdapter.getInstance().commitAppStartTime(ActivityLifecycleCallbacks.this.mStartTime);
                                ActivityLifecycleCallbacks.this.generateAppEndData(System.currentTimeMillis(), SystemClock.elapsedRealtime());
                            }
                            if (ActivityLifecycleCallbacks.this.mStartTimerCount > 0) {
                                ActivityLifecycleCallbacks.this.mHandler.sendEmptyMessageDelayed(IjkMediaCodecInfo.RANK_SECURE, CacheHandler.delayTime);
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }
            };
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    /* access modifiers changed from: private */
    public void handleStartedMessage(Message message) {
        try {
            int activityCount = this.mDbAdapter.getActivityCount();
            this.mStartActivityCount = activityCount;
            DbAdapter dbAdapter = this.mDbAdapter;
            int i = activityCount + 1;
            this.mStartActivityCount = i;
            dbAdapter.commitActivityCount(i);
            if (this.mStartActivityCount == 1) {
                if (AbstractSensorsDataAPI.getConfigOptions().isSaveDeepLinkInfo()) {
                    SensorsDataUtils.mergeJSONObject(ChannelUtils.getLatestUtmProperties(), this.endDataProperty);
                }
                this.mHandler.removeMessages(0);
                if (isSessionTimeOut()) {
                    this.mHandler.sendMessage(obtainAppEndMessage(false));
                    checkFirstDay();
                    boolean firstStart = ((Boolean) this.mFirstStart.get()).booleanValue();
                    try {
                        this.mSensorsDataInstance.appBecomeActive();
                        if (this.resumeFromBackground) {
                            this.mSensorsDataInstance.getRemoteManager().applySDKConfigFromCache();
                            this.mSensorsDataInstance.resumeTrackScreenOrientation();
                        }
                        this.mSensorsDataInstance.getRemoteManager().pullSDKConfigFromServer();
                    } catch (Exception ex) {
                        SALog.printStackTrace(ex);
                    }
                    Bundle bundle = message.getData();
                    try {
                        if (this.mSensorsDataInstance.isAutoTrackEnabled() && !this.mSensorsDataInstance.isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType.APP_START)) {
                            if (firstStart) {
                                this.mFirstStart.commit(false);
                            }
                            JSONObject properties = new JSONObject();
                            properties.put("$resume_from_background", this.resumeFromBackground);
                            properties.put("$is_first_time", firstStart);
                            SensorsDataUtils.mergeJSONObject(this.activityProperty, properties);
                            JSONObject jSONObject = this.mDeepLinkProperty;
                            if (jSONObject != null) {
                                SensorsDataUtils.mergeJSONObject(jSONObject, properties);
                                this.mDeepLinkProperty = null;
                            }
                            long eventTime = bundle.getLong("time");
                            properties.put(EVENT_TIME, eventTime > 0 ? eventTime : System.currentTimeMillis());
                            this.mSensorsDataInstance.trackAutoEvent("$AppStart", properties);
                            SensorsDataAPI.sharedInstance().flush();
                        }
                    } catch (Exception e) {
                        SALog.i(TAG, (Throwable) e);
                    }
                    updateStartTime(bundle.getLong("elapse_time"));
                    if (this.resumeFromBackground) {
                        try {
                            HeatMapService.getInstance().resume();
                            VisualizedAutoTrackService.getInstance().resume();
                        } catch (Exception e2) {
                            SALog.printStackTrace(e2);
                        }
                    }
                    this.resumeFromBackground = true;
                }
            }
        } catch (Exception e3) {
            SALog.printStackTrace(e3);
            updateStartTime(SystemClock.elapsedRealtime());
        }
        try {
            int i2 = this.mStartTimerCount;
            this.mStartTimerCount = i2 + 1;
            if (i2 == 0) {
                this.mHandler.sendEmptyMessage(IjkMediaCodecInfo.RANK_SECURE);
            }
        } catch (Exception exception) {
            SALog.printStackTrace(exception);
        }
    }

    /* access modifiers changed from: private */
    public void handleStoppedMessage(Message message) {
        try {
            int i = this.mStartTimerCount - 1;
            this.mStartTimerCount = i;
            int i2 = 0;
            if (i <= 0) {
                this.mHandler.removeMessages(IjkMediaCodecInfo.RANK_SECURE);
                this.mStartTimerCount = 0;
                this.mStartTime = 0;
            }
            int activityCount = this.mDbAdapter.getActivityCount();
            this.mStartActivityCount = activityCount;
            if (activityCount > 0) {
                i2 = activityCount - 1;
                this.mStartActivityCount = i2;
            }
            this.mStartActivityCount = i2;
            this.mDbAdapter.commitActivityCount(i2);
            if (this.mStartActivityCount <= 0) {
                this.mSensorsDataInstance.flush();
                Bundle bundle = message.getData();
                generateAppEndData(bundle.getLong("time"), bundle.getLong("elapse_time"));
                this.mHandler.sendMessageDelayed(obtainAppEndMessage(true), (long) this.mSensorsDataInstance.getSessionIntervalTime());
            }
        } catch (Exception ex) {
            SALog.printStackTrace(ex);
        }
    }

    /* access modifiers changed from: private */
    public void trackAppEnd(String jsonEndData) {
        try {
            if (this.mSensorsDataInstance.isAutoTrackEnabled() && isAutoTrackAppEnd() && !TextUtils.isEmpty(jsonEndData)) {
                JSONObject property = new JSONObject(jsonEndData);
                if (property.has("track_timer")) {
                    property.put(EVENT_TIME, property.optLong("track_timer") + CacheHandler.delayTime);
                    property.remove("event_timer");
                    property.remove("track_timer");
                }
                property.remove(DbParams.TABLE_APP_START_TIME);
                this.mSensorsDataInstance.trackAutoEvent("$AppEnd", property);
                this.mDbAdapter.commitAppEndData("");
                this.mSensorsDataInstance.flush();
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    /* access modifiers changed from: private */
    public void generateAppEndData(long eventTime, long endElapsedTime) {
        try {
            if (this.mDataCollectState || this.mSensorsDataInstance.getSAContextManager().isAppStartSuccess()) {
                this.mDataCollectState = true;
                if (AbstractSensorsDataAPI.getConfigOptions().isDataCollectEnable()) {
                    if (this.mStartTime == 0) {
                        this.mStartTime = DbAdapter.getInstance().getAppStartTime();
                    }
                    long j = this.mStartTime;
                    if (j != 0) {
                        this.endDataProperty.put(EVENT_DURATION, TimeUtils.duration(j, endElapsedTime));
                    } else {
                        this.endDataProperty.remove(EVENT_DURATION);
                    }
                    this.endDataProperty.put(DbParams.TABLE_APP_START_TIME, this.mStartTime);
                    this.endDataProperty.put(EVENT_TIME, eventTime + CacheHandler.delayTime);
                    if (AbstractSensorsDataAPI.getConfigOptions().isEnableSession()) {
                        SessionRelatedManager.getInstance().refreshSessionByTimer(CacheHandler.delayTime + eventTime);
                        JSONObject jSONObject = this.endDataProperty;
                        SessionRelatedManager.getInstance().getClass();
                        jSONObject.put("$event_session_id", (Object) SessionRelatedManager.getInstance().getSessionID());
                    }
                    this.endDataProperty.put(APP_VERSION, (Object) AppInfoUtils.getAppVersionName(this.mContext));
                    this.endDataProperty.put(LIB_VERSION, (Object) SensorsDataAPI.sharedInstance().getSDKVersion());
                    ChannelUtils.mergeUtmToEndData(ChannelUtils.getLatestUtmProperties(), this.endDataProperty);
                    this.mDbAdapter.commitAppEndData(this.endDataProperty.toString());
                }
            }
        } catch (Throwable e) {
            SALog.d(TAG, e.getMessage());
        }
    }

    private boolean isSessionTimeOut() {
        long currentTime = Math.max(System.currentTimeMillis(), 946656000000L);
        long endTrackTime = 0;
        try {
            String endData = DbAdapter.getInstance().getAppEndData();
            if (!TextUtils.isEmpty(endData)) {
                JSONObject endDataJsonObject = new JSONObject(endData);
                endTrackTime = endDataJsonObject.optLong(EVENT_TIME) - CacheHandler.delayTime;
                if (this.mStartTime == 0) {
                    updateStartTime(endDataJsonObject.optLong(DbParams.TABLE_APP_START_TIME));
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return Math.abs(currentTime - endTrackTime) > ((long) this.mSensorsDataInstance.getSessionIntervalTime());
    }

    private void updateStartTime(long startElapsedTime) {
        try {
            this.mStartTime = startElapsedTime;
            this.mDbAdapter.commitAppStartTime(startElapsedTime > 0 ? startElapsedTime : SystemClock.elapsedRealtime());
        } catch (Exception e) {
            try {
                this.mDbAdapter.commitAppStartTime(startElapsedTime > 0 ? startElapsedTime : SystemClock.elapsedRealtime());
            } catch (Exception e2) {
            }
        }
    }

    private void sendActivityHandleMessage(int type) {
        Message message = this.mHandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putLong("time", System.currentTimeMillis());
        bundle.putLong("elapse_time", SystemClock.elapsedRealtime());
        message.what = type;
        message.setData(bundle);
        this.mHandler.sendMessage(message);
    }

    private Message obtainAppEndMessage(boolean resetState) {
        Message message = Message.obtain(this.mHandler);
        message.what = 0;
        Bundle bundle = new Bundle();
        bundle.putString(DbParams.PersistentName.APP_END_DATA, DbAdapter.getInstance().getAppEndData());
        bundle.putBoolean("app_reset_state", resetState);
        message.setData(bundle);
        return message;
    }

    /* access modifiers changed from: private */
    public void resetState() {
        try {
            this.mSensorsDataInstance.stopTrackScreenOrientation();
            this.mSensorsDataInstance.getRemoteManager().resetPullSDKConfigTimer();
            HeatMapService.getInstance().stop();
            VisualizedAutoTrackService.getInstance().stop();
            this.mSensorsDataInstance.appEnterBackground();
            this.resumeFromBackground = true;
            this.mSensorsDataInstance.clearLastScreenUrl();
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    private void checkFirstDay() {
        if (this.mFirstDay.get() == null && AbstractSensorsDataAPI.getConfigOptions().isDataCollectEnable()) {
            this.mFirstDay.commit(TimeUtils.formatTime(System.currentTimeMillis(), TimeUtils.YYYY_MM_DD));
        }
    }

    /* access modifiers changed from: private */
    public boolean isAutoTrackAppEnd() {
        return !this.mSensorsDataInstance.isAutoTrackEventTypeIgnored(SensorsDataAPI.AutoTrackEventType.APP_END);
    }

    private void buildScreenProperties(Activity activity) {
        JSONObject buildTitleNoAutoTrackerProperties = AopUtil.buildTitleNoAutoTrackerProperties(activity);
        this.activityProperty = buildTitleNoAutoTrackerProperties;
        SensorsDataUtils.mergeJSONObject(buildTitleNoAutoTrackerProperties, this.endDataProperty);
        if (!AbstractSensorsDataAPI.getConfigOptions().isDisableSDK() && isDeepLinkParseSuccess(activity)) {
            ChannelUtils.removeDeepLinkInfo(this.endDataProperty);
            if (this.mDeepLinkProperty == null) {
                this.mDeepLinkProperty = new JSONObject();
            }
            DeepLinkManager.mergeDeepLinkProperty(this.mDeepLinkProperty);
        }
    }

    private boolean isDeepLinkParseSuccess(Activity activity) {
        Intent intent;
        try {
            if ((!SensorsDataUtils.isUniApp() || !ChannelUtils.isDeepLinkBlackList(activity)) && (intent = activity.getIntent()) != null && intent.getData() != null && !intent.getBooleanExtra(DeepLinkManager.IS_ANALYTICS_DEEPLINK, false) && DeepLinkManager.parseDeepLink(activity, AbstractSensorsDataAPI.getConfigOptions().isSaveDeepLinkInfo(), this.mSensorsDataInstance.getDeepLinkCallback())) {
                intent.putExtra(DeepLinkManager.IS_ANALYTICS_DEEPLINK, true);
                return true;
            }
        } catch (Throwable ex) {
            SALog.i(TAG, ex.getMessage());
        }
        return false;
    }

    public void uncaughtException(Thread t, Throwable e) {
        if (TextUtils.isEmpty(DbAdapter.getInstance().getAppEndData())) {
            DbAdapter.getInstance().commitAppStartTime(SystemClock.elapsedRealtime());
        }
        if (AbstractSensorsDataAPI.getConfigOptions().isMultiProcessFlush()) {
            DbAdapter.getInstance().commitSubProcessFlushState(false);
        }
        DbAdapter.getInstance().commitActivityCount(0);
    }

    /* access modifiers changed from: package-private */
    public void addActivity(Activity activity) {
        if (activity != null) {
            this.hashSet.add(Integer.valueOf(activity.hashCode()));
        }
    }

    /* access modifiers changed from: package-private */
    public boolean hasActivity(Activity activity) {
        if (activity != null) {
            return this.hashSet.contains(Integer.valueOf(activity.hashCode()));
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            this.hashSet.remove(Integer.valueOf(activity.hashCode()));
        }
    }
}
