package com.sensorsdata.analytics.android.sdk.visual;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import com.sensorsdata.analytics.android.sdk.SALog;

public class HeatMapService {
    private static HeatMapService instance;
    private static HeatMapViewCrawler mVTrack;

    private HeatMapService() {
    }

    public static HeatMapService getInstance() {
        if (instance == null) {
            instance = new HeatMapService();
        }
        return instance;
    }

    public void stop() {
        try {
            HeatMapViewCrawler heatMapViewCrawler = mVTrack;
            if (heatMapViewCrawler != null) {
                heatMapViewCrawler.stopUpdates(false);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void resume() {
        try {
            HeatMapViewCrawler heatMapViewCrawler = mVTrack;
            if (heatMapViewCrawler != null) {
                heatMapViewCrawler.startUpdates();
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void start(Activity activity, String featureCode, String postUrl) {
        try {
            Bundle configBundle = activity.getApplicationContext().getPackageManager().getApplicationInfo(activity.getApplicationContext().getPackageName(), 128).metaData;
            if (configBundle == null) {
                configBundle = new Bundle();
            }
            if (Build.VERSION.SDK_INT >= 16) {
                String resourcePackageName = configBundle.getString("com.sensorsdata.analytics.android.ResourcePackageName");
                if (resourcePackageName == null) {
                    resourcePackageName = activity.getPackageName();
                }
                HeatMapViewCrawler heatMapViewCrawler = new HeatMapViewCrawler(activity, resourcePackageName, featureCode, postUrl);
                mVTrack = heatMapViewCrawler;
                heatMapViewCrawler.startUpdates();
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public boolean isServiceRunning() {
        HeatMapViewCrawler heatMapViewCrawler = mVTrack;
        if (heatMapViewCrawler != null) {
            return heatMapViewCrawler.isServiceRunning();
        }
        return false;
    }
}
