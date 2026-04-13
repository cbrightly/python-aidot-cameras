package com.sensorsdata.analytics.android.sdk.visual;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesLog;
import com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager;

public class VisualizedAutoTrackService {
    private static final String TAG = "SA.VisualizedAutoTrackService";
    private static VisualizedAutoTrackService instance;
    private static VisualizedAutoTrackViewCrawler mVTrack;
    private boolean mDebugModeEnabled = false;
    private String mLastDebugInfo;
    private VisualDebugHelper mVisualDebugHelper;
    private VisualPropertiesLog mVisualPropertiesLog;

    private VisualizedAutoTrackService() {
    }

    public static VisualizedAutoTrackService getInstance() {
        if (instance == null) {
            instance = new VisualizedAutoTrackService();
        }
        return instance;
    }

    public void stop() {
        try {
            VisualizedAutoTrackViewCrawler visualizedAutoTrackViewCrawler = mVTrack;
            if (visualizedAutoTrackViewCrawler != null) {
                visualizedAutoTrackViewCrawler.stopUpdates(false);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void resume() {
        try {
            VisualizedAutoTrackViewCrawler visualizedAutoTrackViewCrawler = mVTrack;
            if (visualizedAutoTrackViewCrawler != null) {
                visualizedAutoTrackViewCrawler.startUpdates();
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void start(Activity activity, String featureCode, String postUrl) {
        String resourcePackageName;
        try {
            Bundle configBundle = activity.getApplicationContext().getPackageManager().getApplicationInfo(activity.getApplicationContext().getPackageName(), 128).metaData;
            if (configBundle == null) {
                configBundle = new Bundle();
            }
            if (Build.VERSION.SDK_INT >= 16) {
                String resourcePackageName2 = configBundle.getString("com.sensorsdata.analytics.android.ResourcePackageName");
                if (resourcePackageName2 == null) {
                    resourcePackageName = activity.getPackageName();
                } else {
                    resourcePackageName = resourcePackageName2;
                }
                if (this.mVisualDebugHelper == null) {
                    this.mVisualDebugHelper = new VisualDebugHelper();
                }
                VisualizedAutoTrackViewCrawler visualizedAutoTrackViewCrawler = new VisualizedAutoTrackViewCrawler(activity, resourcePackageName, featureCode, postUrl, this.mVisualDebugHelper);
                mVTrack = visualizedAutoTrackViewCrawler;
                visualizedAutoTrackViewCrawler.startUpdates();
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public boolean isServiceRunning() {
        VisualizedAutoTrackViewCrawler visualizedAutoTrackViewCrawler = mVTrack;
        if (visualizedAutoTrackViewCrawler != null) {
            return visualizedAutoTrackViewCrawler.isServiceRunning();
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public String getDebugInfo() {
        try {
            VisualDebugHelper visualDebugHelper = this.mVisualDebugHelper;
            if (visualDebugHelper == null) {
                return null;
            }
            String debugInfo = visualDebugHelper.getDebugInfo();
            this.mLastDebugInfo = debugInfo;
            if (TextUtils.isEmpty(debugInfo)) {
                return null;
            }
            SALog.i(TAG, "visual debug info: " + this.mLastDebugInfo);
            return this.mLastDebugInfo;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public String getLastDebugInfo() {
        try {
            if (TextUtils.isEmpty(this.mLastDebugInfo)) {
                return null;
            }
            SALog.i(TAG, "last debug info: " + this.mLastDebugInfo);
            return this.mLastDebugInfo;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public String getVisualLogInfo() {
        try {
            VisualPropertiesLog visualPropertiesLog = this.mVisualPropertiesLog;
            if (visualPropertiesLog == null) {
                return null;
            }
            String visualPropertiesLog2 = visualPropertiesLog.getVisualPropertiesLog();
            if (TextUtils.isEmpty(visualPropertiesLog2)) {
                return null;
            }
            SALog.i(TAG, "visual log info: " + visualPropertiesLog2);
            return visualPropertiesLog2;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public void setDebugModeEnabled(boolean debugModeEnabled) {
        try {
            if (this.mDebugModeEnabled != debugModeEnabled) {
                if (debugModeEnabled) {
                    this.mVisualPropertiesLog = new VisualPropertiesLog();
                    VisualPropertiesManager.getInstance().registerCollectLogListener(this.mVisualPropertiesLog);
                } else {
                    this.mVisualPropertiesLog = null;
                    VisualPropertiesManager.getInstance().unRegisterCollectLogListener();
                }
            }
            this.mDebugModeEnabled = debugModeEnabled;
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }
}
