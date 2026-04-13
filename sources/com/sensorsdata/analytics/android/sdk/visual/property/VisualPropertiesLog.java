package com.sensorsdata.analytics.android.sdk.visual.property;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.visual.model.ViewNode;
import com.sensorsdata.analytics.android.sdk.visual.model.VisualConfig;
import com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VisualPropertiesLog implements VisualPropertiesManager.CollectLogListener {
    private Builder mBuilder;
    private JSONArray mJSONArray;
    private final Object object = new Object();

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0016, code lost:
        r1 = th;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.String getVisualPropertiesLog() {
        /*
            r2 = this;
            monitor-enter(r2)
            java.lang.Object r0 = r2.object     // Catch:{ all -> 0x0018 }
            monitor-enter(r0)     // Catch:{ all -> 0x0018 }
            org.json.JSONArray r1 = r2.mJSONArray     // Catch:{ all -> 0x0013 }
            if (r1 == 0) goto L_0x000f
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0013 }
            monitor-exit(r0)     // Catch:{ all -> 0x0013 }
            monitor-exit(r2)
            return r1
        L_0x000f:
            r1 = 0
            monitor-exit(r0)     // Catch:{ all -> 0x0013 }
            monitor-exit(r2)
            return r1
        L_0x0013:
            r1 = move-exception
        L_0x0014:
            monitor-exit(r0)     // Catch:{ all -> 0x0016 }
            throw r1     // Catch:{ all -> 0x0018 }
        L_0x0016:
            r1 = move-exception
            goto L_0x0014
        L_0x0018:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesLog.getVisualPropertiesLog():java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x001a, code lost:
        r1 = th;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void add2JsonArray(org.json.JSONObject r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            java.lang.Object r0 = r2.object     // Catch:{ all -> 0x001c }
            monitor-enter(r0)     // Catch:{ all -> 0x001c }
            org.json.JSONArray r1 = r2.mJSONArray     // Catch:{ all -> 0x0017 }
            if (r1 != 0) goto L_0x000f
            org.json.JSONArray r1 = new org.json.JSONArray     // Catch:{ all -> 0x0017 }
            r1.<init>()     // Catch:{ all -> 0x0017 }
            r2.mJSONArray = r1     // Catch:{ all -> 0x0017 }
        L_0x000f:
            org.json.JSONArray r1 = r2.mJSONArray     // Catch:{ all -> 0x0017 }
            r1.put((java.lang.Object) r3)     // Catch:{ all -> 0x0017 }
            monitor-exit(r0)     // Catch:{ all -> 0x0017 }
            monitor-exit(r2)
            return
        L_0x0017:
            r1 = move-exception
        L_0x0018:
            monitor-exit(r0)     // Catch:{ all -> 0x001a }
            throw r1     // Catch:{ all -> 0x001c }
        L_0x001a:
            r1 = move-exception
            goto L_0x0018
        L_0x001c:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesLog.add2JsonArray(org.json.JSONObject):void");
    }

    public void onStart(String eventType, String screenName, ViewNode viewNode) {
        String elementPath = null;
        String elementPosition = null;
        String elementContent = null;
        if (viewNode != null) {
            elementPath = viewNode.getViewPath();
            elementPosition = viewNode.getViewPosition();
            elementContent = viewNode.getViewContent();
        }
        this.mBuilder = new Builder(eventType, screenName, elementPath, elementPosition, elementContent);
    }

    public void onSwitchClose() {
        this.mBuilder.buildSwitchControl();
        add2JsonArray(this.mBuilder.build());
    }

    public void onCheckVisualConfigFailure(String message) {
        this.mBuilder.buildVisualConfig(message);
        add2JsonArray(this.mBuilder.build());
    }

    public void onCheckEventConfigFailure() {
        this.mBuilder.buildEventConfig();
        add2JsonArray(this.mBuilder.build());
    }

    public void onFindPropertyElementFailure(String propertyName, String propertyElementPath, String propertyElementPosition) {
        this.mBuilder.buildPropertyElement(String.format("%s 属性未找到属性元素，属性元素路径为 %s，属性元素位置为 %s ", new Object[]{propertyName, propertyElementPath, propertyElementPosition}));
        add2JsonArray(this.mBuilder.build());
    }

    public void onParsePropertyContentFailure(String propertyName, String propertyType, String elementContent, String regular) {
        this.mBuilder.buildPropertyContentParse(String.format("%s 属性正则解析失败，元素内容 %s, 正则表达式为 %s,属性类型为 %s", new Object[]{propertyName, elementContent, regular, propertyType}));
        add2JsonArray(this.mBuilder.build());
    }

    public void onOtherError(String message) {
        this.mBuilder.buildOtherError(message);
        add2JsonArray(this.mBuilder.build());
    }

    public static class Builder {
        private String elementContent;
        private String elementPath;
        private String elementPosition;
        private JSONObject eventConfig;
        private String eventType;
        private String localConfig = null;
        private JSONObject otherError;
        private JSONObject propertyContentParse;
        private JSONObject propertyElement;
        private String screenName;
        private JSONObject switchControl;
        private JSONObject visualConfig;

        Builder(String eventType2, String screenName2, String elementPath2, String elementPosition2, String elementContent2) {
            this.eventType = eventType2;
            this.screenName = screenName2;
            this.elementPath = elementPath2;
            this.elementPosition = elementPosition2;
            this.elementContent = elementContent2;
            VisualConfig config = VisualPropertiesManager.getInstance().getVisualConfig();
            if (config != null) {
                this.localConfig = config.toString();
            }
        }

        /* access modifiers changed from: private */
        public void buildSwitchControl() {
            try {
                this.switchControl = new JSONObject().put("title", (Object) "开关控制").put("message", (Object) "自定义属性运维配置开关关闭");
            } catch (JSONException e) {
                SALog.printStackTrace(e);
            }
        }

        /* access modifiers changed from: private */
        public void buildVisualConfig(String message) {
            try {
                this.visualConfig = new JSONObject().put("title", (Object) "获取配置").put("message", (Object) message);
            } catch (JSONException e) {
                SALog.printStackTrace(e);
            }
        }

        /* access modifiers changed from: private */
        public void buildEventConfig() {
            try {
                this.eventConfig = new JSONObject().put("title", (Object) "事件配置").put("message", (Object) "本地缓存不包含该事件配置");
            } catch (JSONException e) {
                SALog.printStackTrace(e);
            }
        }

        /* access modifiers changed from: private */
        public void buildPropertyElement(String message) {
            try {
                this.propertyElement = new JSONObject().put("title", (Object) "获取属性元素").put("message", (Object) message);
            } catch (JSONException e) {
                SALog.printStackTrace(e);
            }
        }

        /* access modifiers changed from: private */
        public void buildPropertyContentParse(String message) {
            try {
                this.propertyContentParse = new JSONObject().put("title", (Object) "解析属性").put("message", (Object) message);
            } catch (JSONException e) {
                SALog.printStackTrace(e);
            }
        }

        /* access modifiers changed from: private */
        public void buildOtherError(String message) {
            try {
                this.otherError = new JSONObject().put("title", (Object) "其他错误").put("message", (Object) message);
            } catch (JSONException e) {
                SALog.printStackTrace(e);
            }
        }

        /* access modifiers changed from: private */
        public JSONObject build() {
            JSONObject object = new JSONObject();
            try {
                object.put("event_type", (Object) this.eventType);
                object.put("element_path", (Object) this.elementPath);
                object.put("element_position", (Object) this.elementPosition);
                object.put("element_content", (Object) this.elementContent);
                object.put(FirebaseAnalytics.Param.SCREEN_NAME, (Object) this.screenName);
                object.put("local_config", (Object) this.localConfig);
                JSONArray message = new JSONArray();
                JSONObject jSONObject = this.switchControl;
                if (jSONObject != null) {
                    message.put((Object) jSONObject);
                }
                JSONObject jSONObject2 = this.visualConfig;
                if (jSONObject2 != null) {
                    message.put((Object) jSONObject2);
                }
                JSONObject jSONObject3 = this.eventConfig;
                if (jSONObject3 != null) {
                    message.put((Object) jSONObject3);
                }
                JSONObject jSONObject4 = this.propertyElement;
                if (jSONObject4 != null) {
                    message.put((Object) jSONObject4);
                }
                JSONObject jSONObject5 = this.propertyContentParse;
                if (jSONObject5 != null) {
                    message.put((Object) jSONObject5);
                }
                JSONObject jSONObject6 = this.otherError;
                if (jSONObject6 != null) {
                    message.put((Object) jSONObject6);
                }
                object.put("message", (Object) message);
            } catch (JSONException e) {
                SALog.printStackTrace(e);
            }
            return object;
        }
    }
}
