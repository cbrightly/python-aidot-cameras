package com.sensorsdata.analytics.android.sdk.visual;

import android.app.Activity;

public class HeatMapViewCrawler extends AbstractViewCrawler {
    HeatMapViewCrawler(Activity activity, String resourcePackageName, String featureCode, String postUrl) {
        super(activity, resourcePackageName, featureCode, postUrl, AbstractViewCrawler.TYPE_HEAT_MAP);
    }
}
