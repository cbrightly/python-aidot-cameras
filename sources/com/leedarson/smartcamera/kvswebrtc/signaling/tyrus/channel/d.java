package com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel;

import com.leedarson.serviceinterface.LDSBaseMqttService;
import com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel.a;
import com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel.b;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.json.JSONObject;

/* compiled from: MqttSignalSwitchChannel */
public class d implements a {
    public static ChangeQuickRedirect changeQuickRedirect;
    LDSBaseMqttService a;

    public d() {
        this.a = null;
        this.a = (LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class);
    }

    public void a(String str, String messageBody, b _handler, LdsSignalSendConfigBean configBean, a.C0173a aVar) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, messageBody, _handler, configBean, aVar}, this, changeQuickRedirect, false, 10076, new Class[]{cls, cls, b.class, LdsSignalSendConfigBean.class, a.C0173a.class}, Void.TYPE).isSupported) {
            try {
                this.a.publish(configBean.topic, configBean, new JSONObject(messageBody), new a(_handler));
            } catch (Exception e) {
                b("sendMqttMessage 数据转化异常==>" + e.toString());
            }
        }
    }

    /* compiled from: MqttSignalSwitchChannel */
    public class a implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ b a;

        a(b bVar) {
            this.a = bVar;
        }

        public void onActionSuccess(String taskId, JSONObject callbackData) {
            Class[] clsArr = {String.class, JSONObject.class};
            if (!PatchProxy.proxy(new Object[]{taskId, callbackData}, this, changeQuickRedirect, false, 10079, clsArr, Void.TYPE).isSupported) {
                this.a.a(taskId, callbackData, b.a.MQTT);
            }
        }

        public void onActionFail(int code, String taskId, String desc) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(code), taskId, desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10080, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                this.a.b(code, taskId, desc, b.a.MQTT);
            }
        }
    }

    private void b(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 10078, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("MqttSignalSwitchChannel").c(msg, new Object[0]);
        }
    }
}
