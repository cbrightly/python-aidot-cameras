package com.sensorsdata.analytics.android.sdk.visual.property;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.AppStateManager;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.util.AopUtil;
import com.sensorsdata.analytics.android.sdk.util.AppInfoUtils;
import com.sensorsdata.analytics.android.sdk.util.ViewUtil;
import com.sensorsdata.analytics.android.sdk.visual.ViewTreeStatusObservable;
import com.sensorsdata.analytics.android.sdk.visual.model.ViewNode;
import com.sensorsdata.analytics.android.sdk.visual.model.VisualConfig;
import com.sensorsdata.analytics.android.sdk.visual.property.VisualConfigRequestHelper;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public class VisualPropertiesManager {
    private static final String PROPERTY_TYPE_NUMBER = "NUMBER";
    private static final String TAG = "SA.VP.VisualPropertiesManager";
    private CollectLogListener mCollectLogListener;
    private VisualPropertiesCache mConfigCache;
    private VisualConfigRequestHelper mRequestHelper;
    private VisualConfig mVisualConfig;
    private VisualPropertiesH5Helper mVisualPropertiesH5Helper;

    public interface CollectLogListener {
        void onCheckEventConfigFailure();

        void onCheckVisualConfigFailure(String str);

        void onFindPropertyElementFailure(String str, String str2, String str3);

        void onOtherError(String str);

        void onParsePropertyContentFailure(String str, String str2, String str3, String str4);

        void onStart(String str, String str2, ViewNode viewNode);

        void onSwitchClose();
    }

    private VisualPropertiesManager() {
        VisualPropertiesCache visualPropertiesCache = new VisualPropertiesCache();
        this.mConfigCache = visualPropertiesCache;
        this.mVisualConfig = visualPropertiesCache.getVisualConfig();
        this.mRequestHelper = new VisualConfigRequestHelper();
        this.mVisualPropertiesH5Helper = new VisualPropertiesH5Helper();
    }

    public static VisualPropertiesManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static class SingletonHolder {
        /* access modifiers changed from: private */
        public static VisualPropertiesManager INSTANCE = new VisualPropertiesManager();

        private SingletonHolder() {
        }
    }

    public void requestVisualConfig(Context context, SensorsDataAPI sensorsDataAPI) {
        if (sensorsDataAPI != null) {
            try {
                if (sensorsDataAPI.isNetworkRequestEnable()) {
                    this.mRequestHelper.requestVisualConfig(context, getVisualConfigVersion(), new VisualConfigRequestHelper.IApiCallback() {
                        public void onSuccess(String message) {
                            VisualPropertiesManager.this.save2Cache(message);
                        }
                    });
                    return;
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
                return;
            }
        }
        SALog.i(TAG, "Close network request");
    }

    public void requestVisualConfig() {
        try {
            Context context = SensorsDataAPI.sharedInstance().getContext();
            if (context != null) {
                requestVisualConfig(context, SensorsDataAPI.sharedInstance());
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public VisualPropertiesH5Helper getVisualPropertiesH5Helper() {
        return this.mVisualPropertiesH5Helper;
    }

    public VisualPropertiesCache getVisualPropertiesCache() {
        return this.mConfigCache;
    }

    public VisualConfig getVisualConfig() {
        return this.mVisualConfig;
    }

    public void save2Cache(String message) {
        this.mConfigCache.save2Cache(message);
        this.mVisualConfig = this.mConfigCache.getVisualConfig();
    }

    public String getVisualConfigVersion() {
        VisualConfig visualConfig = this.mVisualConfig;
        if (visualConfig != null) {
            return visualConfig.version;
        }
        return null;
    }

    public void registerCollectLogListener(CollectLogListener listener) {
        this.mCollectLogListener = listener;
    }

    public void unRegisterCollectLogListener() {
        this.mCollectLogListener = null;
    }

    public enum VisualEventType {
        APP_CLICK("appclick", AopConstants.APP_CLICK_EVENT_NAME),
        WEB_CLICK("appclick", AopConstants.WEB_CLICK_EVENT_NAME);
        
        private String trackEventType;
        /* access modifiers changed from: private */
        public String visualEventType;

        private VisualEventType(String visualEventType2, String trackEventType2) {
            this.visualEventType = visualEventType2;
            this.trackEventType = trackEventType2;
        }

        public String getVisualEventType() {
            return this.visualEventType;
        }

        public static VisualEventType getVisualEventType(String trackEventType2) {
            for (VisualEventType visualEventType2 : values()) {
                if (TextUtils.equals(visualEventType2.trackEventType, trackEventType2)) {
                    return visualEventType2;
                }
            }
            return null;
        }
    }

    public void mergeVisualProperties(VisualEventType eventType, JSONObject srcObject, ViewNode viewNode) {
        Activity activity;
        String elementContent;
        String elementPosition;
        WeakReference<View> view;
        ViewNode viewNode2 = viewNode;
        try {
            String screenName = srcObject.optString(AopConstants.SCREEN_NAME);
            CollectLogListener collectLogListener = this.mCollectLogListener;
            if (collectLogListener != null) {
                collectLogListener.onStart(eventType.visualEventType, screenName, viewNode2);
            }
            SALog.i(TAG, String.format("mergeVisualProperties eventType: %s, screenName:%s ", new Object[]{eventType.getVisualEventType(), screenName}));
            if (TextUtils.isEmpty(screenName)) {
                SALog.i(TAG, "screenName is empty and return");
            } else if (!SensorsDataAPI.sharedInstance().isVisualizedAutoTrackEnabled()) {
                SALog.i(TAG, "you should call 'enableVisualizedAutoTrack(true)' first");
                CollectLogListener collectLogListener2 = this.mCollectLogListener;
                if (collectLogListener2 != null) {
                    collectLogListener2.onSwitchClose();
                }
            } else {
                Activity activity2 = null;
                if (!(viewNode2 == null || (view = viewNode.getView()) == null || view.get() == null)) {
                    activity2 = AopUtil.getActivityFromContext(((View) view.get()).getContext(), (View) view.get());
                }
                if (activity2 == null) {
                    activity = AppStateManager.getInstance().getForegroundActivity();
                } else {
                    activity = activity2;
                }
                if (activity != null) {
                    if (SensorsDataAPI.sharedInstance().isVisualizedAutoTrackActivity(activity.getClass())) {
                        if (this.mVisualConfig == null) {
                            SALog.i(TAG, "visual properties is empty and return");
                            CollectLogListener collectLogListener3 = this.mCollectLogListener;
                            if (collectLogListener3 != null) {
                                collectLogListener3.onCheckVisualConfigFailure("本地缓存无自定义属性配置");
                                return;
                            }
                            return;
                        } else if (!checkAppIdAndProject()) {
                            CollectLogListener collectLogListener4 = this.mCollectLogListener;
                            if (collectLogListener4 != null) {
                                collectLogListener4.onCheckVisualConfigFailure("本地缓存的 AppId 或 Project 与当前项目不一致");
                                return;
                            }
                            return;
                        } else {
                            List<VisualConfig.VisualPropertiesConfig> propertiesConfigs = this.mVisualConfig.events;
                            if (propertiesConfigs != null) {
                                if (propertiesConfigs.size() != 0) {
                                    String elementPath = null;
                                    if (viewNode2 != null) {
                                        elementPath = viewNode.getViewPath();
                                        elementPosition = viewNode.getViewPosition();
                                        elementContent = viewNode.getViewContent();
                                    } else {
                                        elementPosition = null;
                                        elementContent = null;
                                    }
                                    List<VisualConfig.VisualPropertiesConfig> eventConfigList = getMatchEventConfigList(propertiesConfigs, eventType, screenName, elementPath, elementPosition, elementContent);
                                    if (eventConfigList.size() == 0) {
                                        SALog.i(TAG, "event config is empty and return");
                                        CollectLogListener collectLogListener5 = this.mCollectLogListener;
                                        if (collectLogListener5 != null) {
                                            collectLogListener5.onCheckEventConfigFailure();
                                            return;
                                        }
                                        return;
                                    }
                                    for (VisualConfig.VisualPropertiesConfig visualPropertiesConfig : eventConfigList) {
                                        VisualConfig.VisualEvent event = visualPropertiesConfig.event;
                                        if (event == null || !event.isH5) {
                                            List<VisualConfig.VisualProperty> properties = visualPropertiesConfig.properties;
                                            if (properties == null) {
                                                VisualConfig.VisualEvent visualEvent = event;
                                                VisualConfig.VisualPropertiesConfig visualPropertiesConfig2 = visualPropertiesConfig;
                                            } else if (properties.size() == 0) {
                                                VisualConfig.VisualEvent visualEvent2 = event;
                                                VisualConfig.VisualPropertiesConfig visualPropertiesConfig3 = visualPropertiesConfig;
                                            } else {
                                                VisualConfig.VisualEvent visualEvent3 = event;
                                                VisualConfig.VisualPropertiesConfig visualPropertiesConfig4 = visualPropertiesConfig;
                                                mergeVisualProperty(properties, event, srcObject, viewNode, visualPropertiesConfig.eventName);
                                            }
                                            SALog.i(TAG, "properties is empty ");
                                        }
                                    }
                                    return;
                                }
                            }
                            SALog.i(TAG, "propertiesConfigs is empty");
                            CollectLogListener collectLogListener6 = this.mCollectLogListener;
                            if (collectLogListener6 != null) {
                                collectLogListener6.onOtherError("propertiesConfigs is empty");
                                return;
                            }
                            return;
                        }
                    }
                }
                SALog.i(TAG, "activity is null or not in white list and return");
                CollectLogListener collectLogListener7 = this.mCollectLogListener;
                if (collectLogListener7 != null) {
                    collectLogListener7.onOtherError("activity is null or not in white list and return");
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public List<VisualConfig.VisualPropertiesConfig> getMatchEventConfigList(List<VisualConfig.VisualPropertiesConfig> propertiesConfigs, VisualEventType eventType, String screenName, String elementPath, String elementPosition, String elementContent) {
        List<VisualConfig.VisualPropertiesConfig> list = new ArrayList<>();
        try {
            for (VisualConfig.VisualPropertiesConfig visualPropertiesConfig : propertiesConfigs) {
                if (TextUtils.equals(visualPropertiesConfig.eventType, eventType.getVisualEventType())) {
                    VisualConfig.VisualEvent event = visualPropertiesConfig.event;
                    if (TextUtils.isEmpty(screenName) || TextUtils.equals(event.screenName, screenName)) {
                        if (eventType == VisualEventType.APP_CLICK || eventType == VisualEventType.WEB_CLICK) {
                            if (!TextUtils.equals(event.elementPath, elementPath)) {
                                SALog.i(TAG, String.format("event element_path is not match: current element_path is %s, config element_path is %s ", new Object[]{elementPath, event.elementPath}));
                            } else if (event.limitElementPosition && !TextUtils.equals(event.elementPosition, elementPosition)) {
                                SALog.i(TAG, String.format("event element_position is not match: current element_position is %s, config element_position is %s ", new Object[]{elementPosition, event.elementPosition}));
                            } else if (event.limitElementContent && !TextUtils.equals(event.elementContent, elementContent)) {
                                SALog.i(TAG, String.format("event element_content is not match: current element_content is %s, config element_content is %s ", new Object[]{elementContent, event.elementContent}));
                            }
                        }
                        list.add(visualPropertiesConfig);
                    }
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return list;
    }

    public boolean checkAppIdAndProject() {
        String serverUrl = SensorsDataAPI.sharedInstance().getServerUrl();
        if (TextUtils.isEmpty(serverUrl)) {
            SALog.i(TAG, "serverUrl is empty and return");
            return false;
        }
        String project = Uri.parse(serverUrl).getQueryParameter("project");
        String appId = AppInfoUtils.getProcessName(SensorsDataAPI.sharedInstance().getContext());
        if (TextUtils.isEmpty(project) || TextUtils.isEmpty(appId)) {
            SALog.i(TAG, "project or app_id is empty and return");
            return false;
        }
        VisualConfig visualConfig = this.mVisualConfig;
        if (visualConfig == null) {
            SALog.i(TAG, "VisualConfig is null and return");
            return false;
        } else if (!TextUtils.equals(appId, visualConfig.appId)) {
            SALog.i(TAG, String.format("app_id is not equals: current app_id is %s, config app_id is %s ", new Object[]{appId, this.mVisualConfig.appId}));
            return false;
        } else if (TextUtils.equals(project, this.mVisualConfig.project)) {
            return true;
        } else {
            SALog.i(TAG, String.format("project is not equals: current project is %s, config project is %s ", new Object[]{project, this.mVisualConfig.project}));
            return false;
        }
    }

    private void mergeVisualProperty(List<VisualConfig.VisualProperty> properties, VisualConfig.VisualEvent event, JSONObject srcObject, ViewNode clickViewNode, String eventName) {
        try {
            HashSet<String> h5HashSet = new HashSet<>();
            for (VisualConfig.VisualProperty visualProperty : properties) {
                if (!visualProperty.isH5 || TextUtils.isEmpty(visualProperty.webViewElementPath)) {
                    mergeAppVisualProperty(visualProperty, event, srcObject, clickViewNode);
                } else {
                    h5HashSet.add(visualProperty.webViewElementPath + visualProperty.screenName);
                }
            }
            if (h5HashSet.size() > 0) {
                this.mVisualPropertiesH5Helper.mergeJSVisualProperties(srcObject, h5HashSet, eventName);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public void mergeAppVisualProperty(VisualConfig.VisualProperty visualProperty, VisualConfig.VisualEvent event, JSONObject srcObject, ViewNode clickViewNode) {
        try {
            if (TextUtils.isEmpty(visualProperty.name)) {
                SALog.i(TAG, "config visual property name is empty");
            } else if (TextUtils.isEmpty(visualProperty.elementPath)) {
                SALog.i(TAG, "config visual property elementPath is empty");
            } else {
                if (clickViewNode != null && !TextUtils.isEmpty(clickViewNode.getViewPosition()) && event != null && !TextUtils.isEmpty(event.elementPosition) && !event.limitElementPosition && !TextUtils.isEmpty(visualProperty.elementPosition) && TextUtils.equals(visualProperty.elementPath.split("-")[0], event.elementPath.split("-")[0])) {
                    visualProperty.elementPosition = clickViewNode.getViewPosition();
                    SALog.i(TAG, "visualProperty elementPosition replace: " + clickViewNode.getViewPosition());
                }
                String propertyElementContent = null;
                try {
                    ViewNode viewTreeNode = ViewTreeStatusObservable.getInstance().getViewNode(clickViewNode != null ? clickViewNode.getView() : null, visualProperty.elementPath, visualProperty.elementPosition, visualProperty.screenName);
                    if (viewTreeNode != null && TextUtils.equals(visualProperty.elementPath, viewTreeNode.getViewPath()) && (TextUtils.isEmpty(visualProperty.elementPosition) || TextUtils.equals(visualProperty.elementPosition, viewTreeNode.getViewPosition()))) {
                        propertyElementContent = viewTreeNode.getViewContent();
                        WeakReference<View> targetView = null;
                        if (viewTreeNode.getView() != null) {
                            targetView = viewTreeNode.getView();
                        }
                        if (!(targetView == null || targetView.get() == null)) {
                            propertyElementContent = ViewUtil.getViewContentAndType((View) targetView.get(), true).getViewContent();
                        }
                    }
                } catch (Exception e) {
                    SALog.printStackTrace(e);
                }
                if (propertyElementContent != null) {
                    if (!TextUtils.isEmpty(propertyElementContent)) {
                        SALog.i(TAG, String.format("find property target view success, property element_path: %s,element_position: %s,element_content: %s", new Object[]{visualProperty.elementPath, visualProperty.elementPosition, propertyElementContent}));
                        String result = null;
                        if (!TextUtils.isEmpty(visualProperty.regular)) {
                            try {
                                Matcher matcher = Pattern.compile(visualProperty.regular, 40).matcher(propertyElementContent);
                                if (matcher.find()) {
                                    result = matcher.group();
                                    SALog.i(TAG, String.format("propertyValue is: %s", new Object[]{result}));
                                } else {
                                    SALog.i(TAG, "matcher not find continue");
                                    CollectLogListener collectLogListener = this.mCollectLogListener;
                                    if (collectLogListener != null) {
                                        collectLogListener.onParsePropertyContentFailure(visualProperty.name, visualProperty.type, propertyElementContent, visualProperty.regular);
                                        return;
                                    }
                                    return;
                                }
                            } catch (Exception e2) {
                                CollectLogListener collectLogListener2 = this.mCollectLogListener;
                                if (collectLogListener2 != null) {
                                    collectLogListener2.onParsePropertyContentFailure(visualProperty.name, visualProperty.type, propertyElementContent, visualProperty.regular);
                                }
                                SALog.printStackTrace(e2);
                                return;
                            }
                        }
                        if (!TextUtils.isEmpty(result)) {
                            if (!TextUtils.equals(PROPERTY_TYPE_NUMBER, visualProperty.type)) {
                                try {
                                    srcObject.put(visualProperty.name, (Object) result);
                                } catch (JSONException e3) {
                                    CollectLogListener collectLogListener3 = this.mCollectLogListener;
                                    if (collectLogListener3 != null) {
                                        collectLogListener3.onOtherError(e3.getMessage());
                                    }
                                }
                            } else if (result != null) {
                                try {
                                    srcObject.put(visualProperty.name, (Object) NumberFormat.getInstance().parse(result));
                                } catch (Exception e4) {
                                    CollectLogListener collectLogListener4 = this.mCollectLogListener;
                                    if (collectLogListener4 != null) {
                                        collectLogListener4.onOtherError(e4.getMessage());
                                    }
                                }
                            }
                        }
                        return;
                    }
                }
                CollectLogListener collectLogListener5 = this.mCollectLogListener;
                if (collectLogListener5 != null) {
                    collectLogListener5.onFindPropertyElementFailure(visualProperty.name, visualProperty.elementPath, visualProperty.elementPosition);
                }
            }
        } catch (Exception e5) {
            SALog.printStackTrace(e5);
        }
    }
}
