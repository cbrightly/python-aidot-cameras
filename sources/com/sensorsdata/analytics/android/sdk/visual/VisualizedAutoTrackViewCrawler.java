package com.sensorsdata.analytics.android.sdk.visual;

import android.app.Activity;
import com.sensorsdata.analytics.android.sdk.SALog;

public class VisualizedAutoTrackViewCrawler extends AbstractViewCrawler {
    private VisualDebugHelper mVisualDebugHelper;

    VisualizedAutoTrackViewCrawler(Activity activity, String resourcePackageName, String featureCode, String postUrl, VisualDebugHelper visualDebugHelper) {
        super(activity, resourcePackageName, featureCode, postUrl, AbstractViewCrawler.TYPE_VISUAL);
        this.mVisualDebugHelper = visualDebugHelper;
    }

    public void startUpdates() {
        try {
            super.startUpdates();
            VisualDebugHelper visualDebugHelper = this.mVisualDebugHelper;
            if (visualDebugHelper != null) {
                visualDebugHelper.stopMonitor();
                this.mVisualDebugHelper.startMonitor();
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void stopUpdates(boolean clear) {
        try {
            super.stopUpdates(clear);
            VisualDebugHelper visualDebugHelper = this.mVisualDebugHelper;
            if (visualDebugHelper != null) {
                visualDebugHelper.stopMonitor();
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }
}
