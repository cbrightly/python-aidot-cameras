package com.leedarson.mqtt.utils;

import com.alibaba.android.arouter.utils.e;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.mqtt.logs.b;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.event.PartialUpdateEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import meshsdk.util.MeshConstants;
import org.eclipse.paho.client.mqttv3.l;
import org.greenrobot.eventbus.c;
import org.json.JSONObject;

/* compiled from: MqttBrocastToBzLayout */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void a(String str, l message) {
        boolean z = false;
        if (!PatchProxy.proxy(new Object[]{str, message}, (Object) null, changeQuickRedirect, true, 1733, new Class[]{String.class, l.class}, Void.TYPE).isSupported) {
            String topic = str;
            try {
                JSONObject _data = new JSONObject(new String(message.c()));
                String deviceId = "";
                if (!_data.has("id")) {
                    if (_data.has("srcAddr") && _data.getString("srcAddr").split("\\.").length > 1) {
                        deviceId = _data.getString("srcAddr").split("\\.")[1];
                    }
                    if (e.b(deviceId)) {
                        String[] topicSplitArr = topic.split("/");
                        if (topicSplitArr.length >= 4) {
                            deviceId = topicSplitArr[3];
                        }
                    }
                    _data.put("id", (Object) deviceId);
                } else {
                    deviceId = _data.getString("id");
                }
                if (_data.has("payload")) {
                    if (_data.getJSONObject("payload").has("attr")) {
                        _data.put("props", (Object) _data.getJSONObject("payload").getJSONObject("attr"));
                        if (!_data.getJSONObject("payload").has("props")) {
                            _data.getJSONObject("payload").put("props", (Object) _data.getJSONObject("payload").getJSONObject("attr"));
                        }
                    }
                }
                if (_data.has("payload")) {
                    if (topic.contains("device/connect") || topic.contains("device/disconnect")) {
                        if (!_data.getJSONObject("payload").has("props")) {
                            JSONObject _props = new JSONObject();
                            if (topic.contains("device/connect")) {
                                z = true;
                            }
                            _props.put(MeshConstants.AC_STATE_LOGIN_SUCCESS, z);
                            _data.getJSONObject("payload").put("props", (Object) _props);
                        } else {
                            JSONObject jSONObject = _data.getJSONObject("payload").getJSONObject("props");
                            if (topic.contains("device/connect")) {
                                z = true;
                            }
                            jSONObject.put(MeshConstants.AC_STATE_LOGIN_SUCCESS, z);
                        }
                    }
                    if (topic.contains("devUnbindNotif") && !_data.getJSONObject("payload").has("id") && _data.getJSONObject("payload").has("devId")) {
                        deviceId = _data.getJSONObject("payload").getString("devId");
                        _data.getJSONObject("payload").put("id", (Object) deviceId);
                        JSONObject extensionsObj = new JSONObject();
                        extensionsObj.put("id", (Object) deviceId);
                        extensionsObj.put(FirebaseAnalytics.Param.METHOD, (Object) "devUnbindNotif");
                        _data.getJSONObject("payload").put("extensions", (Object) extensionsObj);
                    }
                }
                if (_data.has("payload") && !_data.getJSONObject("payload").has("id")) {
                    _data.getJSONObject("payload").put("id", (Object) deviceId);
                }
                IpcService ipcService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
                if (ipcService != null) {
                    ipcService.actualUpdatePartialFieldByNativeMqtt(_data);
                }
                b.b("向外广播数据：---> data=" + _data.toString());
                c.c().l(new PartialUpdateEvent(_data.toString()));
            } catch (Exception exception) {
                exception.printStackTrace();
                b.a("onBrocastToBz  --> Exception=" + exception.toString());
            }
        }
    }
}
