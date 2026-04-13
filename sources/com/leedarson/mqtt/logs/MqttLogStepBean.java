package com.leedarson.mqtt.logs;

import com.google.gson.JsonObject;
import com.leedarson.mqtt.beans.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.Date;

public class MqttLogStepBean {
    public static final String STEP_OF_FETCH_MQTT_CONFIG = "fetchMqttConfigApi";
    public static final String STEP_OF_MQTT_ENGINE_CONNECT = "MqttEngineConnect";
    public static final String STEP_OF_MQTT_ENGINE_DISCONNECT = "DisconnectMqttEvent";
    public static final String STEP_OF_MQTT_PUBLISH_REQUEST = "MqttPublishRequest";
    public static final String STEP_OF_MQTT_PUBLISH_REQUEST_ACK = "MqttPublishRequestAck";
    public static final String STEP_OF_MQTT_PUBLISH_REQUEST_PROCESS = "MqttPublishEngineProcess";
    public static final String STEP_OF_MQTT_PUBLISH_REQUEST_RESP = "MqttPublishRequestResp";
    public static final String STEP_OF_MQTT_REQUEST_DISCONNECT = "DisconnectMqttRequest";
    public static final String STEP_OF_MQTT_SUBSCRIBE_PROCESS = "MqttSubscribeRequestProcess";
    public static final String STEP_OF_MQTT_SUBSCRIBE_REQUEST = "MqttSubscribeRequest";
    public static final String STEP_OF_MQTT_UN_SUBSCRIBE_PROCESS = "MqttUnSubscribeRequestProcess";
    public static final String STEP_OF_MQTT_UN_SUBSCRIBE_REQUEST = "MqttUnSubscribeRequest";
    public static ChangeQuickRedirect changeQuickRedirect;
    public int code = 0;
    public long ct = 0;
    public String desc = "";
    public long durationMs = 0;
    public String end = "";
    public long et = 0;
    public JsonObject request = new JsonObject();
    public JsonObject response = new JsonObject();
    public String start = "";
    public String step = "";

    public MqttLogStepBean(String stepName) {
        this.step = stepName;
        this.ct = System.currentTimeMillis();
        this.et = System.currentTimeMillis();
        this.code = 200;
        this.start = a.a().format(new Date());
    }

    public void putRequest(String key, String value) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{key, value}, this, changeQuickRedirect, false, 1707, clsArr, Void.TYPE).isSupported) {
            this.request.addProperty(key, value);
        }
    }

    public void putResponse(String key, String value) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{key, value}, this, changeQuickRedirect, false, 1708, clsArr, Void.TYPE).isSupported) {
            this.response.addProperty(key, value);
        }
    }

    public void endTrace(int code2, String desc2) {
        Object[] objArr = {new Integer(code2), desc2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1709, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            this.code = code2;
            this.desc = desc2;
            this.et = System.currentTimeMillis();
            this.end = a.a().format(new Date());
            this.durationMs = this.et - this.ct;
        }
    }
}
