package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.combeans.MqttMessageConfigBean;
import io.reactivex.e;
import org.json.JSONObject;

public interface LDSBaseMqttService extends c {

    public interface MqttActionHandler {
        void onActionFail(int i, String str, String str2);

        void onActionSuccess(String str, JSONObject jSONObject);
    }

    public interface MqttStateChange {
        void onMessageReceive(String str, String str2);

        void onStatueChange(int i, String str);
    }

    void connect(MqttActionHandler mqttActionHandler);

    void disconnect(MqttActionHandler mqttActionHandler);

    e<Boolean> fullIpcDevicesUpdate(JSONObject jSONObject);

    String getErrorDetailMessage();

    e<JSONObject> getFullIpcDevicesJson();

    int getMqttConnectStatue();

    void handleData(WVJBWebView wVJBWebView, String str, Activity activity, String str2, String str3);

    /* synthetic */ void init(Context context);

    void init(Context context, JSONObject jSONObject, String str);

    void loginIn(MqttActionHandler mqttActionHandler);

    void loginOut(MqttActionHandler mqttActionHandler);

    void publish(String str, MqttMessageConfigBean mqttMessageConfigBean, JSONObject jSONObject, MqttActionHandler mqttActionHandler);

    void subscribeTopic(JSONObject jSONObject, MqttActionHandler mqttActionHandler);

    void unSubscribeTopic(JSONObject jSONObject, MqttActionHandler mqttActionHandler);
}
