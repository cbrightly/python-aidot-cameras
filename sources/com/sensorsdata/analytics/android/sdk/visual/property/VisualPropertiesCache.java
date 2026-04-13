package com.sensorsdata.analytics.android.sdk.visual.property;

import android.text.TextUtils;
import android.view.View;
import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentLoader;
import com.sensorsdata.analytics.android.sdk.data.persistent.PersistentVisualConfig;
import com.sensorsdata.analytics.android.sdk.visual.ViewTreeStatusObservable;
import com.sensorsdata.analytics.android.sdk.visual.model.VisualConfig;
import java.util.ArrayList;
import java.util.List;
import meshsdk.ConfigUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class VisualPropertiesCache {
    private static final String TAG = "SA.VP.VisualPropertiesCache";
    private PersistentVisualConfig mPersistentVisualConfig = ((PersistentVisualConfig) PersistentLoader.loadPersistent(DbParams.PersistentName.VISUAL_PROPERTIES));

    public void save2Cache(String config) {
        SALog.i(TAG, "save2Cache config is:" + config);
        this.mPersistentVisualConfig.commit(config);
        doOnSaveCache(config);
    }

    public String getVisualCache() {
        return (String) this.mPersistentVisualConfig.get();
    }

    public VisualConfig getVisualConfig() {
        JSONArray jsonArray;
        JSONObject object;
        String persistentVisualConfig;
        String persistentVisualConfig2 = (String) this.mPersistentVisualConfig.get();
        SALog.i(TAG, "local visual config is :" + persistentVisualConfig2);
        if (TextUtils.isEmpty(persistentVisualConfig2)) {
            return null;
        }
        try {
            VisualConfig config = new VisualConfig();
            JSONObject object2 = new JSONObject(persistentVisualConfig2);
            config.appId = object2.optString("app_id");
            config.os = object2.optString("os");
            config.project = object2.optString("project");
            config.version = object2.optString(ConfigUtil.VERSION_FILE);
            JSONArray jsonArray2 = object2.optJSONArray("events");
            if (jsonArray2 == null || jsonArray2.length() <= 0) {
                JSONObject jSONObject = object2;
                JSONArray jSONArray = jsonArray2;
            } else {
                List<VisualConfig.VisualPropertiesConfig> visualPropertiesConfigs = new ArrayList<>();
                int i = 0;
                while (i < jsonArray2.length()) {
                    JSONObject visualPropertiesObject = jsonArray2.optJSONObject(i);
                    if (visualPropertiesObject == null) {
                        persistentVisualConfig = persistentVisualConfig2;
                        object = object2;
                        jsonArray = jsonArray2;
                    } else {
                        VisualConfig.VisualPropertiesConfig propertiesConfig = new VisualConfig.VisualPropertiesConfig();
                        propertiesConfig.eventName = visualPropertiesObject.optString(DbParams.KEY_CHANNEL_EVENT_NAME);
                        propertiesConfig.eventType = visualPropertiesObject.optString("event_type");
                        JSONObject eventObject = visualPropertiesObject.optJSONObject(NotificationCompat.CATEGORY_EVENT);
                        if (eventObject != null) {
                            try {
                                VisualConfig.VisualEvent event = new VisualConfig.VisualEvent();
                                event.elementPath = eventObject.optString("element_path");
                                event.elementPosition = eventObject.optString("element_position");
                                event.elementContent = eventObject.optString("element_content");
                                event.screenName = eventObject.optString(FirebaseAnalytics.Param.SCREEN_NAME);
                                event.limitElementPosition = eventObject.optBoolean("limit_element_position");
                                event.limitElementContent = eventObject.optBoolean("limit_element_content");
                                event.isH5 = eventObject.optBoolean("h5");
                                propertiesConfig.event = event;
                            } catch (JSONException e) {
                                e = e;
                                String str = persistentVisualConfig2;
                            }
                        }
                        List<VisualConfig.VisualProperty> visualProperties = new ArrayList<>();
                        JSONArray properties = visualPropertiesObject.optJSONArray("properties");
                        if (properties == null || properties.length() <= 0) {
                            persistentVisualConfig = persistentVisualConfig2;
                            object = object2;
                            jsonArray = jsonArray2;
                        } else {
                            int j = 0;
                            while (true) {
                                persistentVisualConfig = persistentVisualConfig2;
                                try {
                                    if (j >= properties.length()) {
                                        break;
                                    }
                                    JSONObject propertyObject = properties.optJSONObject(j);
                                    JSONArray jsonArray3 = jsonArray2;
                                    VisualConfig.VisualProperty visualProperty = new VisualConfig.VisualProperty();
                                    visualProperty.elementPath = propertyObject.optString("element_path");
                                    visualProperty.elementPosition = propertyObject.optString("element_position");
                                    visualProperty.screenName = propertyObject.optString(FirebaseAnalytics.Param.SCREEN_NAME);
                                    visualProperty.name = propertyObject.optString("name");
                                    visualProperty.regular = propertyObject.optString("regular");
                                    visualProperty.isH5 = propertyObject.optBoolean("h5");
                                    visualProperty.type = propertyObject.optString(IjkMediaMeta.IJKM_KEY_TYPE);
                                    visualProperty.webViewElementPath = propertyObject.optString("webview_element_path");
                                    visualProperties.add(visualProperty);
                                    j++;
                                    persistentVisualConfig2 = persistentVisualConfig;
                                    object2 = object2;
                                    jsonArray2 = jsonArray3;
                                } catch (JSONException e2) {
                                    e = e2;
                                    SALog.printStackTrace(e);
                                    return null;
                                }
                            }
                            object = object2;
                            jsonArray = jsonArray2;
                            propertiesConfig.properties = visualProperties;
                        }
                        visualPropertiesConfigs.add(propertiesConfig);
                    }
                    i++;
                    persistentVisualConfig2 = persistentVisualConfig;
                    object2 = object;
                    jsonArray2 = jsonArray;
                }
                JSONObject jSONObject2 = object2;
                JSONArray jSONArray2 = jsonArray2;
                config.events = visualPropertiesConfigs;
            }
            return config;
        } catch (JSONException e3) {
            e = e3;
            String str2 = persistentVisualConfig2;
            SALog.printStackTrace(e);
            return null;
        }
    }

    private void doOnSaveCache(String config) {
        try {
            List<View> viewList = ViewTreeStatusObservable.getInstance().getCurrentWebView();
            if (viewList == null) {
                return;
            }
            if (viewList.size() != 0) {
                for (View view : viewList) {
                    VisualPropertiesManager.getInstance().getVisualPropertiesH5Helper().sendToWeb(view, "updateH5VisualConfig", config);
                }
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public JSONArray getH5JsonArrayFromCache(String eventName, String webViewElementPath) {
        String persistentVisualConfig = (String) this.mPersistentVisualConfig.get();
        if (TextUtils.isEmpty(persistentVisualConfig)) {
            return null;
        }
        try {
            JSONObject object = new JSONObject(persistentVisualConfig);
            JSONArray array = new JSONArray();
            JSONArray jsonArray = object.optJSONArray("events");
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject visualPropertiesObject = jsonArray.optJSONObject(i);
                    if (visualPropertiesObject != null) {
                        VisualConfig.VisualPropertiesConfig propertiesConfig = new VisualConfig.VisualPropertiesConfig();
                        String optString = visualPropertiesObject.optString(DbParams.KEY_CHANNEL_EVENT_NAME);
                        propertiesConfig.eventName = optString;
                        if (TextUtils.equals(optString, eventName)) {
                            JSONArray properties = visualPropertiesObject.optJSONArray("properties");
                            if (properties != null && properties.length() > 0) {
                                for (int j = 0; j < properties.length(); j++) {
                                    JSONObject propertyObject = properties.optJSONObject(j);
                                    VisualConfig.VisualProperty visualProperty = new VisualConfig.VisualProperty();
                                    String optString2 = propertyObject.optString("webview_element_path");
                                    visualProperty.webViewElementPath = optString2;
                                    if (TextUtils.equals(optString2, webViewElementPath)) {
                                        array.put((Object) propertyObject);
                                    }
                                }
                            }
                        }
                    }
                }
                return array;
            }
        } catch (JSONException e) {
            SALog.printStackTrace(e);
        } catch (Exception e2) {
            SALog.printStackTrace(e2);
        }
        return null;
    }
}
