package com.sensorsdata.analytics.android.sdk.visual;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.SAConfigOptions;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.util.AppInfoUtils;
import com.sensorsdata.analytics.android.sdk.visual.model.SnapInfo;
import com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager;
import com.sensorsdata.analytics.android.sdk.visual.snap.EditProtocol;
import com.sensorsdata.analytics.android.sdk.visual.snap.EditState;
import com.sensorsdata.analytics.android.sdk.visual.snap.ResourceReader;
import com.yanzhenjie.andserver.util.h;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.glassfish.grizzly.http.server.Constants;
import org.json.JSONObject;

public abstract class AbstractViewCrawler implements VTrack {
    private static final int MESSAGE_SEND_STATE_FOR_EDITING = 1;
    private static final String TAG = "SA.AbstractViewCrawler";
    public static final String TYPE_HEAT_MAP = "heat_map";
    public static final String TYPE_VISUAL = "visual";
    private final Activity mActivity;
    /* access modifiers changed from: private */
    public String mAppVersion;
    /* access modifiers changed from: private */
    public final EditState mEditState;
    /* access modifiers changed from: private */
    public String mFeatureCode;
    private final LifecycleCallbacks mLifecycleCallbacks;
    /* access modifiers changed from: private */
    public final Handler mMainThreadHandler;
    /* access modifiers changed from: private */
    public final ViewCrawlerHandler mMessageThreadHandler;
    /* access modifiers changed from: private */
    public String mPostUrl;
    private boolean mServiceRunning = false;
    /* access modifiers changed from: private */
    public String mType;

    AbstractViewCrawler(Activity activity, String resourcePackageName, String featureCode, String postUrl, String type) {
        this.mActivity = activity;
        this.mFeatureCode = featureCode;
        EditState editState = new EditState();
        this.mEditState = editState;
        this.mType = type;
        editState.add(activity);
        this.mLifecycleCallbacks = new LifecycleCallbacks();
        try {
            this.mPostUrl = URLDecoder.decode(postUrl, "UTF-8");
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        Application app = (Application) this.mActivity.getApplicationContext();
        if (Build.VERSION.SDK_INT >= 14) {
            app.registerActivityLifecycleCallbacks(this.mLifecycleCallbacks);
        }
        try {
            this.mAppVersion = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
        } catch (Exception e2) {
            this.mAppVersion = "";
        }
        HandlerThread thread = new HandlerThread(VisualizedAutoTrackViewCrawler.class.getCanonicalName(), 10);
        thread.start();
        this.mMessageThreadHandler = new ViewCrawlerHandler(this.mActivity, thread.getLooper(), resourcePackageName);
        this.mMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    public void startUpdates() {
        try {
            if (!TextUtils.isEmpty(this.mFeatureCode) && !TextUtils.isEmpty(this.mPostUrl)) {
                Application app = (Application) this.mActivity.getApplicationContext();
                if (Build.VERSION.SDK_INT >= 14) {
                    app.registerActivityLifecycleCallbacks(this.mLifecycleCallbacks);
                }
                this.mMessageThreadHandler.start();
                ViewCrawlerHandler viewCrawlerHandler = this.mMessageThreadHandler;
                viewCrawlerHandler.sendMessage(viewCrawlerHandler.obtainMessage(1));
                this.mServiceRunning = true;
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void stopUpdates(boolean clear) {
        if (clear) {
            try {
                this.mFeatureCode = null;
                this.mPostUrl = null;
            } catch (Exception e) {
                SALog.printStackTrace(e);
                return;
            }
        }
        this.mMessageThreadHandler.removeMessages(1);
        Application app = (Application) this.mActivity.getApplicationContext();
        if (Build.VERSION.SDK_INT >= 14) {
            app.unregisterActivityLifecycleCallbacks(this.mLifecycleCallbacks);
        }
        this.mServiceRunning = false;
    }

    public boolean isServiceRunning() {
        return this.mServiceRunning;
    }

    @TargetApi(14)
    public class LifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        private LifecycleCallbacks() {
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
            AbstractViewCrawler.this.mEditState.add(activity);
        }

        public void onActivityPaused(Activity activity) {
            AbstractViewCrawler.this.mEditState.remove(activity);
        }

        public void onActivityStopped(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityDestroyed(Activity activity) {
        }
    }

    public class ViewCrawlerHandler extends Handler {
        private String mAppId;
        private StringBuilder mLastImageHash;
        private final EditProtocol mProtocol;
        private final String mSDKVersion;
        private ViewSnapshot mSnapshot;
        private boolean mUseGzip;

        private ViewCrawlerHandler(Context context, Looper looper, String resourcePackageName) {
            super(looper);
            this.mSnapshot = null;
            this.mProtocol = new EditProtocol(new ResourceReader.Ids(resourcePackageName, context));
            this.mLastImageHash = new StringBuilder();
            this.mUseGzip = true;
            this.mAppId = AppInfoUtils.getProcessName(context);
            this.mSDKVersion = SensorsDataAPI.sharedInstance().getSDKVersion();
        }

        public void start() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    sendSnapshot();
                    return;
                default:
                    return;
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0152, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0153, code lost:
            r2 = r0;
            r3 = "Can't close os.";
            r27 = r10;
            r14 = null;
            r15 = null;
            r6 = r20;
            r5 = "Can't close payload_out.";
            r4 = "Can't close gos.";
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0164, code lost:
            r0 = e;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:104:0x03c1  */
        /* JADX WARNING: Removed duplicated region for block: B:112:0x0411 A[SYNTHETIC, Splitter:B:112:0x0411] */
        /* JADX WARNING: Removed duplicated region for block: B:122:0x045a A[SYNTHETIC, Splitter:B:122:0x045a] */
        /* JADX WARNING: Removed duplicated region for block: B:129:0x0484 A[SYNTHETIC, Splitter:B:129:0x0484] */
        /* JADX WARNING: Removed duplicated region for block: B:134:0x04a7 A[SYNTHETIC, Splitter:B:134:0x04a7] */
        /* JADX WARNING: Removed duplicated region for block: B:139:0x04e6 A[SYNTHETIC, Splitter:B:139:0x04e6] */
        /* JADX WARNING: Removed duplicated region for block: B:144:0x0507 A[SYNTHETIC, Splitter:B:144:0x0507] */
        /* JADX WARNING: Removed duplicated region for block: B:207:0x0759 A[SYNTHETIC, Splitter:B:207:0x0759] */
        /* JADX WARNING: Removed duplicated region for block: B:212:0x0768 A[SYNTHETIC, Splitter:B:212:0x0768] */
        /* JADX WARNING: Removed duplicated region for block: B:217:0x0777 A[SYNTHETIC, Splitter:B:217:0x0777] */
        /* JADX WARNING: Removed duplicated region for block: B:254:0x0878 A[SYNTHETIC, Splitter:B:254:0x0878] */
        /* JADX WARNING: Removed duplicated region for block: B:259:0x0885 A[SYNTHETIC, Splitter:B:259:0x0885] */
        /* JADX WARNING: Removed duplicated region for block: B:264:0x0892 A[SYNTHETIC, Splitter:B:264:0x0892] */
        /* JADX WARNING: Removed duplicated region for block: B:278:0x08b6 A[SYNTHETIC, Splitter:B:278:0x08b6] */
        /* JADX WARNING: Removed duplicated region for block: B:27:0x0152 A[ExcHandler: all (r0v54 'th' java.lang.Throwable A[CUSTOM_DECLARE]), PHI: r20 
          PHI: (r20v5 java.lang.String) = (r20v2 java.lang.String), (r20v2 java.lang.String), (r20v2 java.lang.String), (r20v2 java.lang.String), (r20v6 java.lang.String) binds: [B:47:0x01ca, B:48:?, B:39:0x018f, B:40:?, B:19:0x0114] A[DONT_GENERATE, DONT_INLINE], Splitter:B:19:0x0114] */
        /* JADX WARNING: Removed duplicated region for block: B:283:0x08c3 A[SYNTHETIC, Splitter:B:283:0x08c3] */
        /* JADX WARNING: Removed duplicated region for block: B:288:0x08d0 A[SYNTHETIC, Splitter:B:288:0x08d0] */
        /* JADX WARNING: Removed duplicated region for block: B:39:0x018f A[SYNTHETIC, Splitter:B:39:0x018f] */
        /* JADX WARNING: Removed duplicated region for block: B:47:0x01ca A[SYNTHETIC, Splitter:B:47:0x01ca] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void sendSnapshot() {
            /*
                r32 = this;
                r1 = r32
                java.lang.String r2 = "{"
                java.lang.String r3 = "Can't close writer."
                java.lang.String r4 = "Can't close payload_out."
                java.lang.String r5 = "Can't close gos."
                java.lang.String r6 = "Can't close os."
                java.lang.String r7 = "\","
                java.lang.String r8 = ","
                java.lang.String r9 = "SA.AbstractViewCrawler"
                long r10 = java.lang.System.currentTimeMillis()
                com.sensorsdata.analytics.android.sdk.visual.snap.EditProtocol r12 = r1.mProtocol     // Catch:{ BadInstructionsException -> 0x08e5 }
                com.sensorsdata.analytics.android.sdk.visual.AbstractViewCrawler r13 = com.sensorsdata.analytics.android.sdk.visual.AbstractViewCrawler.this     // Catch:{ BadInstructionsException -> 0x08e5 }
                android.os.Handler r13 = r13.mMainThreadHandler     // Catch:{ BadInstructionsException -> 0x08e5 }
                com.sensorsdata.analytics.android.sdk.visual.ViewSnapshot r12 = r12.readSnapshotConfig(r13)     // Catch:{ BadInstructionsException -> 0x08e5 }
                r1.mSnapshot = r12     // Catch:{ BadInstructionsException -> 0x08e5 }
                if (r12 != 0) goto L_0x0033
                java.lang.String r2 = "Snapshot should be initialize at first calling."
                com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r9, (java.lang.String) r2)     // Catch:{ BadInstructionsException -> 0x002d }
                return
            L_0x002d:
                r0 = move-exception
                r2 = r0
                r27 = r10
                goto L_0x08e9
            L_0x0033:
                java.io.ByteArrayOutputStream r12 = new java.io.ByteArrayOutputStream
                r12.<init>()
                java.io.BufferedOutputStream r13 = new java.io.BufferedOutputStream
                r13.<init>(r12)
                r14 = 0
                r15 = 0
                r16 = 0
                r17 = 0
                r18 = r14
                byte[] r14 = r2.getBytes()     // Catch:{ IOException -> 0x085f, all -> 0x084b }
                r13.write(r14)     // Catch:{ IOException -> 0x085f, all -> 0x084b }
                java.lang.String r14 = "\"type\": \"snapshot_response\","
                byte[] r14 = r14.getBytes()     // Catch:{ IOException -> 0x085f, all -> 0x084b }
                r13.write(r14)     // Catch:{ IOException -> 0x085f, all -> 0x084b }
                java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x085f, all -> 0x084b }
                r14.<init>()     // Catch:{ IOException -> 0x085f, all -> 0x084b }
                r19 = r15
                java.lang.String r15 = "\"feature_code\": \""
                r14.append(r15)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                com.sensorsdata.analytics.android.sdk.visual.AbstractViewCrawler r15 = com.sensorsdata.analytics.android.sdk.visual.AbstractViewCrawler.this     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.String r15 = r15.mFeatureCode     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r14.append(r15)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r14.append(r7)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.String r14 = r14.toString()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                byte[] r14 = r14.getBytes()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r13.write(r14)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r14.<init>()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.String r15 = "\"app_version\": \""
                r14.append(r15)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                com.sensorsdata.analytics.android.sdk.visual.AbstractViewCrawler r15 = com.sensorsdata.analytics.android.sdk.visual.AbstractViewCrawler.this     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.String r15 = r15.mAppVersion     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r14.append(r15)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r14.append(r7)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.String r14 = r14.toString()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                byte[] r14 = r14.getBytes()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r13.write(r14)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r14.<init>()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.String r15 = "\"lib_version\": \""
                r14.append(r15)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.String r15 = r1.mSDKVersion     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r14.append(r15)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r14.append(r7)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.String r14 = r14.toString()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                byte[] r14 = r14.getBytes()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r13.write(r14)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.String r14 = "\"os\": \"Android\","
                byte[] r14 = r14.getBytes()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r13.write(r14)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.String r14 = "\"lib\": \"Android\","
                byte[] r14 = r14.getBytes()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r13.write(r14)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r14.<init>()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.String r15 = "\"app_id\": \""
                r14.append(r15)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.String r15 = r1.mAppId     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r14.append(r15)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r14.append(r7)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.String r14 = r14.toString()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                byte[] r14 = r14.getBytes()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r13.write(r14)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r14.<init>()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.String r15 = "\"app_enablevisualizedproperties\": "
                r14.append(r15)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                com.sensorsdata.analytics.android.sdk.SAConfigOptions r15 = com.sensorsdata.analytics.android.sdk.AbstractSensorsDataAPI.getConfigOptions()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                boolean r15 = r15.isVisualizedPropertiesEnabled()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r14.append(r15)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r14.append(r8)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                java.lang.String r14 = r14.toString()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                byte[] r14 = r14.getBytes()     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                r13.write(r14)     // Catch:{ IOException -> 0x0838, all -> 0x0824 }
                org.json.JSONArray r14 = new org.json.JSONArray     // Catch:{ Exception -> 0x017a, all -> 0x0166 }
                r14.<init>()     // Catch:{ Exception -> 0x017a, all -> 0x0166 }
                com.sensorsdata.analytics.android.sdk.SensorsDataAPI r15 = com.sensorsdata.analytics.android.sdk.SensorsDataAPI.sharedInstance()     // Catch:{ Exception -> 0x017a, all -> 0x0166 }
                r20 = r3
                com.sensorsdata.analytics.android.sdk.SensorsDataAPI$AutoTrackEventType r3 = com.sensorsdata.analytics.android.sdk.SensorsDataAPI.AutoTrackEventType.APP_CLICK     // Catch:{ Exception -> 0x0164, all -> 0x0152 }
                boolean r3 = r15.isAutoTrackEventTypeIgnored((com.sensorsdata.analytics.android.sdk.SensorsDataAPI.AutoTrackEventType) r3)     // Catch:{ Exception -> 0x0164, all -> 0x0152 }
                if (r3 != 0) goto L_0x0121
                java.lang.String r3 = "$AppClick"
                r14.put((java.lang.Object) r3)     // Catch:{ Exception -> 0x0164, all -> 0x0152 }
            L_0x0121:
                com.sensorsdata.analytics.android.sdk.SensorsDataAPI r3 = com.sensorsdata.analytics.android.sdk.SensorsDataAPI.sharedInstance()     // Catch:{ Exception -> 0x0164, all -> 0x0152 }
                com.sensorsdata.analytics.android.sdk.SensorsDataAPI$AutoTrackEventType r15 = com.sensorsdata.analytics.android.sdk.SensorsDataAPI.AutoTrackEventType.APP_VIEW_SCREEN     // Catch:{ Exception -> 0x0164, all -> 0x0152 }
                boolean r3 = r3.isAutoTrackEventTypeIgnored((com.sensorsdata.analytics.android.sdk.SensorsDataAPI.AutoTrackEventType) r15)     // Catch:{ Exception -> 0x0164, all -> 0x0152 }
                if (r3 != 0) goto L_0x0132
                java.lang.String r3 = "$AppViewScreen"
                r14.put((java.lang.Object) r3)     // Catch:{ Exception -> 0x0164, all -> 0x0152 }
            L_0x0132:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0164, all -> 0x0152 }
                r3.<init>()     // Catch:{ Exception -> 0x0164, all -> 0x0152 }
                java.lang.String r15 = "\"app_autotrack\": "
                r3.append(r15)     // Catch:{ Exception -> 0x0164, all -> 0x0152 }
                java.lang.String r15 = r14.toString()     // Catch:{ Exception -> 0x0164, all -> 0x0152 }
                r3.append(r15)     // Catch:{ Exception -> 0x0164, all -> 0x0152 }
                r3.append(r8)     // Catch:{ Exception -> 0x0164, all -> 0x0152 }
                java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0164, all -> 0x0152 }
                byte[] r3 = r3.getBytes()     // Catch:{ Exception -> 0x0164, all -> 0x0152 }
                r13.write(r3)     // Catch:{ Exception -> 0x0164, all -> 0x0152 }
                goto L_0x0181
            L_0x0152:
                r0 = move-exception
                r2 = r0
                r3 = r6
                r27 = r10
                r14 = r18
                r15 = r19
                r6 = r20
                r30 = r5
                r5 = r4
                r4 = r30
                goto L_0x08b4
            L_0x0164:
                r0 = move-exception
                goto L_0x017d
            L_0x0166:
                r0 = move-exception
                r2 = r0
                r27 = r10
                r14 = r18
                r15 = r19
                r30 = r6
                r6 = r3
                r3 = r30
                r31 = r5
                r5 = r4
                r4 = r31
                goto L_0x08b4
            L_0x017a:
                r0 = move-exception
                r20 = r3
            L_0x017d:
                r3 = r0
                com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r3)     // Catch:{ IOException -> 0x0812, all -> 0x0800 }
            L_0x0181:
                com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager r3 = com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager.getInstance()     // Catch:{ IOException -> 0x0812, all -> 0x0800 }
                java.lang.String r3 = r3.getVisualConfigVersion()     // Catch:{ IOException -> 0x0812, all -> 0x0800 }
                boolean r14 = android.text.TextUtils.isEmpty(r3)     // Catch:{ IOException -> 0x0812, all -> 0x0800 }
                if (r14 != 0) goto L_0x01bd
                java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x01ab, all -> 0x0152 }
                r14.<init>()     // Catch:{ IOException -> 0x01ab, all -> 0x0152 }
                java.lang.String r15 = "\"config_version\": \""
                r14.append(r15)     // Catch:{ IOException -> 0x01ab, all -> 0x0152 }
                r14.append(r3)     // Catch:{ IOException -> 0x01ab, all -> 0x0152 }
                r14.append(r7)     // Catch:{ IOException -> 0x01ab, all -> 0x0152 }
                java.lang.String r7 = r14.toString()     // Catch:{ IOException -> 0x01ab, all -> 0x0152 }
                byte[] r7 = r7.getBytes()     // Catch:{ IOException -> 0x01ab, all -> 0x0152 }
                r13.write(r7)     // Catch:{ IOException -> 0x01ab, all -> 0x0152 }
                goto L_0x01bd
            L_0x01ab:
                r0 = move-exception
                r2 = r0
                r3 = r6
                r27 = r10
                r14 = r18
                r15 = r19
                r6 = r20
                r30 = r5
                r5 = r4
                r4 = r30
                goto L_0x0871
            L_0x01bd:
                boolean r7 = r1.mUseGzip     // Catch:{ IOException -> 0x0812, all -> 0x0800 }
                java.lang.String r14 = ",\"snapshot_time_millis\": "
                java.lang.String r15 = "}"
                r21 = r3
                java.lang.String r3 = "\""
                if (r7 == 0) goto L_0x03c1
                java.io.ByteArrayOutputStream r7 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x01ab, all -> 0x0152 }
                r7.<init>()     // Catch:{ IOException -> 0x01ab, all -> 0x0152 }
                r22 = r4
                java.io.BufferedOutputStream r4 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x03b2, all -> 0x03a3 }
                r4.<init>(r7)     // Catch:{ IOException -> 0x03b2, all -> 0x03a3 }
                java.lang.String r18 = "{\"activities\":"
                r23 = r5
                byte[] r5 = r18.getBytes()     // Catch:{ IOException -> 0x0393, all -> 0x0383 }
                r4.write(r5)     // Catch:{ IOException -> 0x0393, all -> 0x0383 }
                r4.flush()     // Catch:{ IOException -> 0x0393, all -> 0x0383 }
                com.sensorsdata.analytics.android.sdk.visual.ViewSnapshot r5 = r1.mSnapshot     // Catch:{ IOException -> 0x0393, all -> 0x0383 }
                r24 = r6
                java.lang.StringBuilder r6 = r1.mLastImageHash     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                com.sensorsdata.analytics.android.sdk.visual.model.SnapInfo r5 = r5.snapshots(r7, r6)     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                r17 = r5
                long r5 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                long r5 = r5 - r10
                byte[] r14 = r14.getBytes()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                r4.write(r14)     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                java.lang.String r14 = java.lang.Long.toString(r5)     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                byte[] r14 = r14.getBytes()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                r4.write(r14)     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                com.sensorsdata.analytics.android.sdk.visual.VisualizedAutoTrackService r14 = com.sensorsdata.analytics.android.sdk.visual.VisualizedAutoTrackService.getInstance()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                java.lang.String r14 = r14.getDebugInfo()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                boolean r18 = android.text.TextUtils.isEmpty(r14)     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                if (r18 != 0) goto L_0x0230
                r25 = r5
                byte[] r5 = r8.getBytes()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                r4.write(r5)     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                java.lang.String r5 = "\"event_debug\": "
                byte[] r5 = r5.getBytes()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                r4.write(r5)     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                byte[] r5 = r14.getBytes()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                r4.write(r5)     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                goto L_0x0232
            L_0x0230:
                r25 = r5
            L_0x0232:
                com.sensorsdata.analytics.android.sdk.visual.VisualizedAutoTrackService r5 = com.sensorsdata.analytics.android.sdk.visual.VisualizedAutoTrackService.getInstance()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                java.lang.String r5 = r5.getVisualLogInfo()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                if (r6 != 0) goto L_0x0257
                byte[] r6 = r8.getBytes()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                r4.write(r6)     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                java.lang.String r6 = "\"log_info\":"
                byte[] r6 = r6.getBytes()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                r4.write(r6)     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                byte[] r6 = r5.getBytes()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                r4.write(r6)     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
            L_0x0257:
                byte[] r6 = r15.getBytes()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                r4.write(r6)     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                r4.flush()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                r7.close()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                java.lang.String r6 = r7.toString()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                byte[] r6 = r6.getBytes()     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                r18 = r4
                java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                r27 = r5
                int r5 = r6.length     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                r4.<init>(r5)     // Catch:{ IOException -> 0x0372, all -> 0x0361 }
                java.util.zip.GZIPOutputStream r5 = new java.util.zip.GZIPOutputStream     // Catch:{ IOException -> 0x034c, all -> 0x0337 }
                r5.<init>(r4)     // Catch:{ IOException -> 0x034c, all -> 0x0337 }
                r5.write(r6)     // Catch:{ IOException -> 0x0320, all -> 0x0309 }
                r5.close()     // Catch:{ IOException -> 0x0320, all -> 0x0309 }
                byte[] r16 = r4.toByteArray()     // Catch:{ IOException -> 0x0320, all -> 0x0309 }
                r4.close()     // Catch:{ IOException -> 0x0320, all -> 0x0309 }
                r28 = r4
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x02f4, all -> 0x02df }
                r4.<init>()     // Catch:{ IOException -> 0x02f4, all -> 0x02df }
                r19 = r5
                java.lang.String r5 = "\"gzip_payload\": \""
                r4.append(r5)     // Catch:{ IOException -> 0x02cc, all -> 0x02b9 }
                java.lang.String r5 = new java.lang.String     // Catch:{ IOException -> 0x02cc, all -> 0x02b9 }
                r29 = r6
                char[] r6 = com.sensorsdata.analytics.android.sdk.util.Base64Coder.encode(r16)     // Catch:{ IOException -> 0x02cc, all -> 0x02b9 }
                r5.<init>(r6)     // Catch:{ IOException -> 0x02cc, all -> 0x02b9 }
                r4.append(r5)     // Catch:{ IOException -> 0x02cc, all -> 0x02b9 }
                r4.append(r3)     // Catch:{ IOException -> 0x02cc, all -> 0x02b9 }
                java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x02cc, all -> 0x02b9 }
                byte[] r4 = r4.getBytes()     // Catch:{ IOException -> 0x02cc, all -> 0x02b9 }
                r13.write(r4)     // Catch:{ IOException -> 0x02cc, all -> 0x02b9 }
                r14 = r7
                r4 = r17
                r16 = r28
                goto L_0x0408
            L_0x02b9:
                r0 = move-exception
                r2 = r0
                r14 = r7
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                r16 = r28
                r27 = r10
                goto L_0x08b4
            L_0x02cc:
                r0 = move-exception
                r2 = r0
                r14 = r7
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                r16 = r28
                r27 = r10
                goto L_0x0871
            L_0x02df:
                r0 = move-exception
                r19 = r5
                r2 = r0
                r14 = r7
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                r16 = r28
                r27 = r10
                goto L_0x08b4
            L_0x02f4:
                r0 = move-exception
                r19 = r5
                r2 = r0
                r14 = r7
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                r16 = r28
                r27 = r10
                goto L_0x0871
            L_0x0309:
                r0 = move-exception
                r28 = r4
                r19 = r5
                r2 = r0
                r14 = r7
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                r16 = r28
                r27 = r10
                goto L_0x08b4
            L_0x0320:
                r0 = move-exception
                r28 = r4
                r19 = r5
                r2 = r0
                r14 = r7
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                r16 = r28
                r27 = r10
                goto L_0x0871
            L_0x0337:
                r0 = move-exception
                r28 = r4
                r2 = r0
                r14 = r7
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                r16 = r28
                r27 = r10
                goto L_0x08b4
            L_0x034c:
                r0 = move-exception
                r28 = r4
                r2 = r0
                r14 = r7
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                r16 = r28
                r27 = r10
                goto L_0x0871
            L_0x0361:
                r0 = move-exception
                r2 = r0
                r14 = r7
                r27 = r10
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                goto L_0x08b4
            L_0x0372:
                r0 = move-exception
                r2 = r0
                r14 = r7
                r27 = r10
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                goto L_0x0871
            L_0x0383:
                r0 = move-exception
                r2 = r0
                r3 = r6
                r14 = r7
                r27 = r10
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                goto L_0x08b4
            L_0x0393:
                r0 = move-exception
                r2 = r0
                r3 = r6
                r14 = r7
                r27 = r10
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                goto L_0x0871
            L_0x03a3:
                r0 = move-exception
                r2 = r0
                r4 = r5
                r3 = r6
                r14 = r7
                r27 = r10
                r15 = r19
                r6 = r20
                r5 = r22
                goto L_0x08b4
            L_0x03b2:
                r0 = move-exception
                r2 = r0
                r4 = r5
                r3 = r6
                r14 = r7
                r27 = r10
                r15 = r19
                r6 = r20
                r5 = r22
                goto L_0x0871
            L_0x03c1:
                r22 = r4
                r23 = r5
                r24 = r6
                java.lang.String r4 = "\"payload\": {"
                byte[] r4 = r4.getBytes()     // Catch:{ IOException -> 0x07ee, all -> 0x07dc }
                r13.write(r4)     // Catch:{ IOException -> 0x07ee, all -> 0x07dc }
                java.lang.String r4 = "\"activities\":"
                byte[] r4 = r4.getBytes()     // Catch:{ IOException -> 0x07ee, all -> 0x07dc }
                r13.write(r4)     // Catch:{ IOException -> 0x07ee, all -> 0x07dc }
                r13.flush()     // Catch:{ IOException -> 0x07ee, all -> 0x07dc }
                com.sensorsdata.analytics.android.sdk.visual.ViewSnapshot r4 = r1.mSnapshot     // Catch:{ IOException -> 0x07ee, all -> 0x07dc }
                java.lang.StringBuilder r5 = r1.mLastImageHash     // Catch:{ IOException -> 0x07ee, all -> 0x07dc }
                com.sensorsdata.analytics.android.sdk.visual.model.SnapInfo r4 = r4.snapshots(r12, r5)     // Catch:{ IOException -> 0x07ee, all -> 0x07dc }
                r17 = r4
                long r4 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x07ee, all -> 0x07dc }
                long r4 = r4 - r10
                byte[] r6 = r14.getBytes()     // Catch:{ IOException -> 0x07ee, all -> 0x07dc }
                r13.write(r6)     // Catch:{ IOException -> 0x07ee, all -> 0x07dc }
                java.lang.String r6 = java.lang.Long.toString(r4)     // Catch:{ IOException -> 0x07ee, all -> 0x07dc }
                byte[] r6 = r6.getBytes()     // Catch:{ IOException -> 0x07ee, all -> 0x07dc }
                r13.write(r6)     // Catch:{ IOException -> 0x07ee, all -> 0x07dc }
                byte[] r6 = r15.getBytes()     // Catch:{ IOException -> 0x07ee, all -> 0x07dc }
                r13.write(r6)     // Catch:{ IOException -> 0x07ee, all -> 0x07dc }
                r4 = r17
                r14 = r18
            L_0x0408:
                r5 = 0
                java.lang.String r6 = r4.screenName     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                if (r6 != 0) goto L_0x0456
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r6.<init>()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r7 = ",\"screen_name\": \""
                r6.append(r7)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r7 = r4.screenName     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r6.append(r7)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r6.append(r3)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                byte[] r6 = r6.getBytes()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r13.write(r6)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r6 = r4.screenName     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r5 = r6
                goto L_0x0456
            L_0x0432:
                r0 = move-exception
                r2 = r0
                r17 = r4
                r27 = r10
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                goto L_0x08b4
            L_0x0444:
                r0 = move-exception
                r2 = r0
                r17 = r4
                r27 = r10
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                goto L_0x0871
            L_0x0456:
                boolean r6 = r4.hasFragment     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                if (r6 == 0) goto L_0x0469
                com.sensorsdata.analytics.android.sdk.AppStateManager r6 = com.sensorsdata.analytics.android.sdk.AppStateManager.getInstance()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r6 = r6.getFragmentScreenName()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                boolean r7 = android.text.TextUtils.isEmpty(r6)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                if (r7 != 0) goto L_0x0469
                r5 = r6
            L_0x0469:
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                r6.<init>()     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                java.lang.String r7 = "page_name： "
                r6.append(r7)     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                r6.append(r5)     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r9, (java.lang.String) r6)     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                if (r6 != 0) goto L_0x049f
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r6.<init>()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r7 = ",\"page_name\": \""
                r6.append(r7)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r6.append(r5)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r6.append(r3)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                byte[] r6 = r6.getBytes()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r13.write(r6)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
            L_0x049f:
                java.lang.String r6 = r4.activityTitle     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                if (r6 != 0) goto L_0x04c4
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r6.<init>()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r7 = ",\"title\": \""
                r6.append(r7)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r7 = r4.activityTitle     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r6.append(r7)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r6.append(r3)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                byte[] r6 = r6.getBytes()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r13.write(r6)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
            L_0x04c4:
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                r6.<init>()     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                java.lang.String r7 = ",\"is_webview\": "
                r6.append(r7)     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                boolean r7 = r4.isWebView     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                r6.append(r7)     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                byte[] r6 = r6.getBytes()     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                r13.write(r6)     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                java.lang.String r6 = r4.webLibVersion     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                if (r6 != 0) goto L_0x0503
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r6.<init>()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r7 = ",\"web_lib_version\": \""
                r6.append(r7)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r7 = r4.webLibVersion     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r6.append(r7)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r6.append(r3)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                byte[] r6 = r6.getBytes()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r13.write(r6)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
            L_0x0503:
                boolean r6 = r4.isWebView     // Catch:{ IOException -> 0x07c8, all -> 0x07b4 }
                if (r6 == 0) goto L_0x0747
                java.lang.String r6 = r4.webViewUrl     // Catch:{ IOException -> 0x0733, all -> 0x071f }
                boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ IOException -> 0x0733, all -> 0x071f }
                if (r6 != 0) goto L_0x0747
                com.sensorsdata.analytics.android.sdk.visual.WebNodesManager r6 = com.sensorsdata.analytics.android.sdk.visual.WebNodesManager.getInstance()     // Catch:{ IOException -> 0x0733, all -> 0x071f }
                java.lang.String r7 = r4.webViewUrl     // Catch:{ IOException -> 0x0733, all -> 0x071f }
                com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo r6 = r6.getWebPageInfo(r7)     // Catch:{ IOException -> 0x0733, all -> 0x071f }
                if (r6 == 0) goto L_0x0573
                java.lang.String r7 = r6.getUrl()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                boolean r7 = android.text.TextUtils.isEmpty(r7)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                if (r7 != 0) goto L_0x0547
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r7.<init>()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r17 = r5
                java.lang.String r5 = ",\"h5_url\": \""
                r7.append(r5)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r5 = r6.getUrl()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r7.append(r5)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r7.append(r3)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r5 = r7.toString()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                byte[] r5 = r5.getBytes()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r13.write(r5)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                goto L_0x0549
            L_0x0547:
                r17 = r5
            L_0x0549:
                java.lang.String r5 = r6.getTitle()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                if (r5 != 0) goto L_0x0575
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r5.<init>()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r7 = ",\"h5_title\": \""
                r5.append(r7)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r7 = r6.getTitle()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r5.append(r7)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r5.append(r3)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                byte[] r5 = r5.getBytes()     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                r13.write(r5)     // Catch:{ IOException -> 0x0444, all -> 0x0432 }
                goto L_0x0575
            L_0x0573:
                r17 = r5
            L_0x0575:
                java.util.List<com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo$AlertInfo> r5 = r4.alertInfos     // Catch:{ IOException -> 0x0733, all -> 0x071f }
                if (r5 == 0) goto L_0x0716
                int r7 = r5.size()     // Catch:{ IOException -> 0x0733, all -> 0x071f }
                if (r7 <= 0) goto L_0x0716
                java.lang.String r7 = ",\"app_alert_infos\":"
                byte[] r7 = r7.getBytes()     // Catch:{ IOException -> 0x0733, all -> 0x071f }
                r13.write(r7)     // Catch:{ IOException -> 0x0733, all -> 0x071f }
                r13.flush()     // Catch:{ IOException -> 0x0733, all -> 0x071f }
                java.lang.String r7 = "["
                byte[] r7 = r7.getBytes()     // Catch:{ IOException -> 0x0733, all -> 0x071f }
                r13.write(r7)     // Catch:{ IOException -> 0x0733, all -> 0x071f }
                r7 = 0
            L_0x0595:
                r18 = r4
                int r4 = r5.size()     // Catch:{ IOException -> 0x0704, all -> 0x06f2 }
                if (r7 >= r4) goto L_0x06be
                if (r7 <= 0) goto L_0x05cb
                byte[] r4 = r8.getBytes()     // Catch:{ IOException -> 0x05b9, all -> 0x05a7 }
                r13.write(r4)     // Catch:{ IOException -> 0x05b9, all -> 0x05a7 }
                goto L_0x05cb
            L_0x05a7:
                r0 = move-exception
                r2 = r0
                r27 = r10
                r17 = r18
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                goto L_0x08b4
            L_0x05b9:
                r0 = move-exception
                r2 = r0
                r27 = r10
                r17 = r18
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                goto L_0x0871
            L_0x05cb:
                java.lang.Object r4 = r5.get(r7)     // Catch:{ IOException -> 0x0704, all -> 0x06f2 }
                com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo$AlertInfo r4 = (com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo.AlertInfo) r4     // Catch:{ IOException -> 0x0704, all -> 0x06f2 }
                if (r4 == 0) goto L_0x06ac
                r25 = r5
                java.lang.String r5 = "heat_map"
                r26 = r6
                com.sensorsdata.analytics.android.sdk.visual.AbstractViewCrawler r6 = com.sensorsdata.analytics.android.sdk.visual.AbstractViewCrawler.this     // Catch:{ IOException -> 0x0704, all -> 0x06f2 }
                java.lang.String r6 = r6.mType     // Catch:{ IOException -> 0x0704, all -> 0x06f2 }
                boolean r5 = android.text.TextUtils.equals(r5, r6)     // Catch:{ IOException -> 0x0704, all -> 0x06f2 }
                if (r5 == 0) goto L_0x05f6
                java.lang.String r5 = r4.title     // Catch:{ IOException -> 0x0704, all -> 0x06f2 }
                java.lang.String r6 = "可视化全埋点"
                r27 = r10
                java.lang.String r10 = "点击分析"
                java.lang.String r5 = r5.replace(r6, r10)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r4.title = r5     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                goto L_0x05f8
            L_0x05f6:
                r27 = r10
            L_0x05f8:
                byte[] r5 = r2.getBytes()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r13.write(r5)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                java.lang.String r5 = "\"title\":"
                byte[] r5 = r5.getBytes()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r13.write(r5)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r5.<init>()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r5.append(r3)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                java.lang.String r6 = r4.title     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r5.append(r6)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r5.append(r3)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                byte[] r5 = r5.getBytes()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r13.write(r5)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                byte[] r5 = r8.getBytes()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r13.write(r5)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                java.lang.String r5 = "\"message\":"
                byte[] r5 = r5.getBytes()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r13.write(r5)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r5.<init>()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r5.append(r3)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                java.lang.String r6 = r4.message     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r5.append(r6)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r5.append(r3)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                byte[] r5 = r5.getBytes()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r13.write(r5)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                byte[] r5 = r8.getBytes()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r13.write(r5)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                java.lang.String r5 = "\"link_text\":"
                byte[] r5 = r5.getBytes()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r13.write(r5)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r5.<init>()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r5.append(r3)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                java.lang.String r6 = r4.linkText     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r5.append(r6)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r5.append(r3)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                byte[] r5 = r5.getBytes()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r13.write(r5)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                byte[] r5 = r8.getBytes()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r13.write(r5)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                java.lang.String r5 = "\"link_url\":"
                byte[] r5 = r5.getBytes()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r13.write(r5)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r5.<init>()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r5.append(r3)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                java.lang.String r6 = r4.linkUrl     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r5.append(r6)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r5.append(r3)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                byte[] r5 = r5.getBytes()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r13.write(r5)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                byte[] r5 = r15.getBytes()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r13.write(r5)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                goto L_0x06b2
            L_0x06ac:
                r25 = r5
                r26 = r6
                r27 = r10
            L_0x06b2:
                int r7 = r7 + 1
                r4 = r18
                r5 = r25
                r6 = r26
                r10 = r27
                goto L_0x0595
            L_0x06be:
                r25 = r5
                r26 = r6
                r27 = r10
                java.lang.String r2 = "]"
                byte[] r2 = r2.getBytes()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r13.write(r2)     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                r13.flush()     // Catch:{ IOException -> 0x06e2, all -> 0x06d2 }
                goto L_0x074d
            L_0x06d2:
                r0 = move-exception
                r2 = r0
                r17 = r18
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                goto L_0x08b4
            L_0x06e2:
                r0 = move-exception
                r2 = r0
                r17 = r18
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                goto L_0x0871
            L_0x06f2:
                r0 = move-exception
                r27 = r10
                r2 = r0
                r17 = r18
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                goto L_0x08b4
            L_0x0704:
                r0 = move-exception
                r27 = r10
                r2 = r0
                r17 = r18
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                goto L_0x0871
            L_0x0716:
                r18 = r4
                r25 = r5
                r26 = r6
                r27 = r10
                goto L_0x074d
            L_0x071f:
                r0 = move-exception
                r18 = r4
                r27 = r10
                r2 = r0
                r17 = r18
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                goto L_0x07c6
            L_0x0733:
                r0 = move-exception
                r18 = r4
                r27 = r10
                r2 = r0
                r17 = r18
                r15 = r19
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                goto L_0x07da
            L_0x0747:
                r18 = r4
                r17 = r5
                r27 = r10
            L_0x074d:
                byte[] r2 = r15.getBytes()     // Catch:{ IOException -> 0x07a4, all -> 0x0794 }
                r13.write(r2)     // Catch:{ IOException -> 0x07a4, all -> 0x0794 }
                r13.flush()     // Catch:{ IOException -> 0x07a4, all -> 0x0794 }
                if (r16 == 0) goto L_0x0765
                r16.close()     // Catch:{ Exception -> 0x075d }
                goto L_0x0765
            L_0x075d:
                r0 = move-exception
                r2 = r0
                r3 = r24
                com.sensorsdata.analytics.android.sdk.SALog.i(r9, r3, r2)
                goto L_0x0766
            L_0x0765:
            L_0x0766:
                if (r19 == 0) goto L_0x0774
                r19.close()     // Catch:{ Exception -> 0x076c }
                goto L_0x0774
            L_0x076c:
                r0 = move-exception
                r2 = r0
                r4 = r23
                com.sensorsdata.analytics.android.sdk.SALog.i(r9, r4, r2)
                goto L_0x0775
            L_0x0774:
            L_0x0775:
                if (r14 == 0) goto L_0x0783
                r14.close()     // Catch:{ Exception -> 0x077b }
                goto L_0x0783
            L_0x077b:
                r0 = move-exception
                r2 = r0
                r5 = r22
                com.sensorsdata.analytics.android.sdk.SALog.i(r9, r5, r2)
                goto L_0x0784
            L_0x0783:
            L_0x0784:
                r13.close()     // Catch:{ IOException -> 0x0788 }
                goto L_0x0790
            L_0x0788:
                r0 = move-exception
                r2 = r0
                r6 = r20
                com.sensorsdata.analytics.android.sdk.SALog.i(r9, r6, r2)
            L_0x0790:
                r4 = r18
                goto L_0x08ab
            L_0x0794:
                r0 = move-exception
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                r2 = r0
                r17 = r18
                r15 = r19
                goto L_0x08b4
            L_0x07a4:
                r0 = move-exception
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                r2 = r0
                r17 = r18
                r15 = r19
                goto L_0x0871
            L_0x07b4:
                r0 = move-exception
                r18 = r4
                r27 = r10
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                r2 = r0
                r17 = r18
                r15 = r19
            L_0x07c6:
                goto L_0x08b4
            L_0x07c8:
                r0 = move-exception
                r18 = r4
                r27 = r10
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                r2 = r0
                r17 = r18
                r15 = r19
            L_0x07da:
                goto L_0x0871
            L_0x07dc:
                r0 = move-exception
                r27 = r10
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                r2 = r0
                r14 = r18
                r15 = r19
                goto L_0x08b4
            L_0x07ee:
                r0 = move-exception
                r27 = r10
                r6 = r20
                r5 = r22
                r4 = r23
                r3 = r24
                r2 = r0
                r14 = r18
                r15 = r19
                goto L_0x0871
            L_0x0800:
                r0 = move-exception
                r3 = r6
                r27 = r10
                r6 = r20
                r30 = r5
                r5 = r4
                r4 = r30
                r2 = r0
                r14 = r18
                r15 = r19
                goto L_0x08b4
            L_0x0812:
                r0 = move-exception
                r3 = r6
                r27 = r10
                r6 = r20
                r30 = r5
                r5 = r4
                r4 = r30
                r2 = r0
                r14 = r18
                r15 = r19
                goto L_0x0871
            L_0x0824:
                r0 = move-exception
                r27 = r10
                r30 = r6
                r6 = r3
                r3 = r30
                r31 = r5
                r5 = r4
                r4 = r31
                r2 = r0
                r14 = r18
                r15 = r19
                goto L_0x08b4
            L_0x0838:
                r0 = move-exception
                r27 = r10
                r30 = r6
                r6 = r3
                r3 = r30
                r31 = r5
                r5 = r4
                r4 = r31
                r2 = r0
                r14 = r18
                r15 = r19
                goto L_0x0871
            L_0x084b:
                r0 = move-exception
                r27 = r10
                r19 = r15
                r30 = r6
                r6 = r3
                r3 = r30
                r31 = r5
                r5 = r4
                r4 = r31
                r2 = r0
                r14 = r18
                goto L_0x08b4
            L_0x085f:
                r0 = move-exception
                r27 = r10
                r19 = r15
                r30 = r6
                r6 = r3
                r3 = r30
                r31 = r5
                r5 = r4
                r4 = r31
                r2 = r0
                r14 = r18
            L_0x0871:
                java.lang.String r7 = "Can't write snapshot request to server"
                com.sensorsdata.analytics.android.sdk.SALog.i(r9, r7, r2)     // Catch:{ all -> 0x08b2 }
                if (r16 == 0) goto L_0x0882
                r16.close()     // Catch:{ Exception -> 0x087c }
                goto L_0x0882
            L_0x087c:
                r0 = move-exception
                r2 = r0
                com.sensorsdata.analytics.android.sdk.SALog.i(r9, r3, r2)
                goto L_0x0883
            L_0x0882:
            L_0x0883:
                if (r15 == 0) goto L_0x088f
                r15.close()     // Catch:{ Exception -> 0x0889 }
                goto L_0x088f
            L_0x0889:
                r0 = move-exception
                r2 = r0
                com.sensorsdata.analytics.android.sdk.SALog.i(r9, r4, r2)
                goto L_0x0890
            L_0x088f:
            L_0x0890:
                if (r14 == 0) goto L_0x089c
                r14.close()     // Catch:{ Exception -> 0x0896 }
                goto L_0x089c
            L_0x0896:
                r0 = move-exception
                r2 = r0
                com.sensorsdata.analytics.android.sdk.SALog.i(r9, r5, r2)
                goto L_0x089d
            L_0x089c:
            L_0x089d:
                r13.close()     // Catch:{ IOException -> 0x08a1 }
                goto L_0x08a7
            L_0x08a1:
                r0 = move-exception
                r2 = r0
                com.sensorsdata.analytics.android.sdk.SALog.i(r9, r6, r2)
            L_0x08a7:
                r19 = r15
                r4 = r17
            L_0x08ab:
                r1.onSnapFinished(r4)
                r1.postSnapshot(r12)
                return
            L_0x08b2:
                r0 = move-exception
                r2 = r0
            L_0x08b4:
                if (r16 == 0) goto L_0x08c0
                r16.close()     // Catch:{ Exception -> 0x08ba }
                goto L_0x08c0
            L_0x08ba:
                r0 = move-exception
                r7 = r0
                com.sensorsdata.analytics.android.sdk.SALog.i(r9, r3, r7)
                goto L_0x08c1
            L_0x08c0:
            L_0x08c1:
                if (r15 == 0) goto L_0x08cd
                r15.close()     // Catch:{ Exception -> 0x08c7 }
                goto L_0x08cd
            L_0x08c7:
                r0 = move-exception
                r3 = r0
                com.sensorsdata.analytics.android.sdk.SALog.i(r9, r4, r3)
                goto L_0x08ce
            L_0x08cd:
            L_0x08ce:
                if (r14 == 0) goto L_0x08da
                r14.close()     // Catch:{ Exception -> 0x08d4 }
                goto L_0x08da
            L_0x08d4:
                r0 = move-exception
                r3 = r0
                com.sensorsdata.analytics.android.sdk.SALog.i(r9, r5, r3)
                goto L_0x08db
            L_0x08da:
            L_0x08db:
                r13.close()     // Catch:{ IOException -> 0x08df }
                goto L_0x08e4
            L_0x08df:
                r0 = move-exception
                r3 = r0
                com.sensorsdata.analytics.android.sdk.SALog.i(r9, r6, r3)
            L_0x08e4:
                throw r2
            L_0x08e5:
                r0 = move-exception
                r27 = r10
                r2 = r0
            L_0x08e9:
                java.lang.String r3 = "VisualizedAutoTrack server sent malformed message with snapshot request"
                com.sensorsdata.analytics.android.sdk.SALog.i(r9, r3, r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.visual.AbstractViewCrawler.ViewCrawlerHandler.sendSnapshot():void");
        }

        private void onSnapFinished(SnapInfo info) {
            if (info != null && !WebNodesManager.getInstance().hasWebView()) {
                WebNodesManager.getInstance().clear();
            }
        }

        private void postSnapshot(ByteArrayOutputStream out) {
            InputStream in;
            boolean rePostSnapshot = true;
            if (!TextUtils.isEmpty(AbstractViewCrawler.this.mFeatureCode) && !TextUtils.isEmpty(AbstractViewCrawler.this.mPostUrl)) {
                InputStream in2 = null;
                OutputStream out2 = null;
                BufferedOutputStream bout = null;
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(AbstractViewCrawler.this.mPostUrl).openConnection();
                    SAConfigOptions configOptions = AbstractSensorsDataAPI.getConfigOptions();
                    if (configOptions != null) {
                        if (configOptions.isDisableSDK()) {
                            AbstractViewCrawler.this.mMessageThreadHandler.sendMessageDelayed(AbstractViewCrawler.this.mMessageThreadHandler.obtainMessage(1), 1000);
                            if (bout != null) {
                                try {
                                    bout.close();
                                } catch (Exception e) {
                                    SALog.printStackTrace(e);
                                }
                            }
                            if (in2 != null) {
                                try {
                                    in2.close();
                                } catch (Exception e2) {
                                    SALog.printStackTrace(e2);
                                }
                            }
                            if (out2 != null) {
                                try {
                                    out2.close();
                                } catch (Exception e3) {
                                    SALog.printStackTrace(e3);
                                }
                            }
                            if (out != null) {
                                try {
                                    out.close();
                                    return;
                                } catch (Exception e4) {
                                    SALog.printStackTrace(e4);
                                    return;
                                }
                            } else {
                                return;
                            }
                        } else {
                            SSLSocketFactory sSLSocketFactory = configOptions.mSSLSocketFactory;
                            if (sSLSocketFactory != null && (connection instanceof HttpsURLConnection)) {
                                ((HttpsURLConnection) connection).setSSLSocketFactory(sSLSocketFactory);
                            }
                        }
                    }
                    connection.setDoOutput(true);
                    connection.setRequestMethod(Constants.POST);
                    connection.setRequestProperty("Content-type", h.TEXT_PLAIN_VALUE);
                    out2 = connection.getOutputStream();
                    bout = new BufferedOutputStream(out2);
                    bout.write(out.toString().getBytes("UTF-8"));
                    bout.flush();
                    int responseCode = connection.getResponseCode();
                    try {
                        in = connection.getInputStream();
                    } catch (FileNotFoundException e5) {
                        FileNotFoundException fileNotFoundException = e5;
                        in = connection.getErrorStream();
                    }
                    byte[] responseBody = slurp(in);
                    String response = new String(responseBody, "UTF-8");
                    SALog.i(AbstractViewCrawler.TAG, "responseCode=" + responseCode);
                    SALog.i(AbstractViewCrawler.TAG, "response=" + response);
                    JSONObject responseJson = new JSONObject(response);
                    if (responseCode == 200) {
                        if (responseJson.getInt("delay") < 0) {
                            rePostSnapshot = false;
                        }
                        String visualizedConfig = responseJson.optString("visualized_sdk_config");
                        boolean visualizedConfigDisabled = responseJson.optBoolean("visualized_config_disabled");
                        if ((!TextUtils.isEmpty(visualizedConfig) || visualizedConfigDisabled) && AbstractSensorsDataAPI.getConfigOptions().isVisualizedPropertiesEnabled()) {
                            VisualPropertiesManager.getInstance().save2Cache(visualizedConfig);
                        }
                        byte[] bArr = responseBody;
                        VisualizedAutoTrackService.getInstance().setDebugModeEnabled(responseJson.optBoolean("visualized_debug_mode_enabled"));
                    }
                    try {
                        bout.close();
                    } catch (Exception e6) {
                        SALog.printStackTrace(e6);
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (Exception e7) {
                            SALog.printStackTrace(e7);
                        }
                    }
                    if (out2 != null) {
                        try {
                            out2.close();
                        } catch (Exception e8) {
                            SALog.printStackTrace(e8);
                        }
                    }
                    try {
                        out.close();
                    } catch (Exception e9) {
                        SALog.printStackTrace(e9);
                    }
                } catch (Exception e10) {
                    SALog.printStackTrace(e10);
                    if (bout != null) {
                        try {
                            bout.close();
                        } catch (Exception e11) {
                            SALog.printStackTrace(e11);
                        }
                    }
                    if (in2 != null) {
                        try {
                            in2.close();
                        } catch (Exception e12) {
                            SALog.printStackTrace(e12);
                        }
                    }
                    if (out2 != null) {
                        try {
                            out2.close();
                        } catch (Exception e13) {
                            SALog.printStackTrace(e13);
                        }
                    }
                    if (out != null) {
                        out.close();
                    }
                } catch (Throwable th) {
                    Throwable th2 = th;
                    if (bout != null) {
                        try {
                            bout.close();
                        } catch (Exception e14) {
                            SALog.printStackTrace(e14);
                        }
                    }
                    if (in2 != null) {
                        try {
                            in2.close();
                        } catch (Exception e15) {
                            SALog.printStackTrace(e15);
                        }
                    }
                    if (out2 != null) {
                        try {
                            out2.close();
                        } catch (Exception e16) {
                            SALog.printStackTrace(e16);
                        }
                    }
                    if (out != null) {
                        try {
                            out.close();
                        } catch (Exception e17) {
                            SALog.printStackTrace(e17);
                        }
                    }
                    throw th2;
                }
                if (rePostSnapshot) {
                    AbstractViewCrawler.this.mMessageThreadHandler.sendMessageDelayed(AbstractViewCrawler.this.mMessageThreadHandler.obtainMessage(1), 1000);
                } else {
                    AbstractViewCrawler.this.stopUpdates(true);
                }
            }
        }

        private byte[] slurp(InputStream inputStream) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[8192];
            while (true) {
                int read = inputStream.read(data, 0, data.length);
                int nRead = read;
                if (read != -1) {
                    buffer.write(data, 0, nRead);
                } else {
                    buffer.flush();
                    return buffer.toByteArray();
                }
            }
        }
    }
}
