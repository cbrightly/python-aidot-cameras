package com.sensorsdata.analytics.android.sdk.visual.model;

import com.sensorsdata.analytics.android.sdk.visual.model.WebNodeInfo;
import java.util.List;

public class SnapInfo {
    public String activityTitle;
    public List<WebNodeInfo.AlertInfo> alertInfos;
    public int elementLevel = -1;
    public boolean hasFragment;
    public boolean isWebView = false;
    public String screenName;
    public String webLibVersion;
    public float webViewScale;
    public String webViewUrl;
}
