package com.leedarson.mqtt.beans;

import android.text.TextUtils;
import com.leedarson.combeans.MqttMessageConfigBean;
import com.leedarson.serviceinterface.LDSBaseMqttService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import kotlin.n;
import org.eclipse.paho.client.mqttv3.l;

public class MqttServiceRequestTaskBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    public long ackTimeSpan;
    public LDSBaseMqttService.MqttActionHandler callBackToBzHandler;
    public MqttMessageConfigBean config;
    public long createTime;
    public boolean flagEnableDelete;
    public int messageId;
    public long mqttEngineSendTimeSpan;
    public l mqttMessage;
    public String requestParams;
    public MqttRequestType requestType;
    public long respTimeSpan;
    public String seqNum;
    public LDS_MQTT_MESSAGE_DEVELIVERY_STATUE taskDeliveryStatue;
    public int taskId;
    public long timeOutDeadline;
    public String topic;
    public String[] topics;

    public enum LDS_MQTT_MESSAGE_DEVELIVERY_STATUE {
        WAITING_TO_DELIVER,
        MQTT_CLIENT_DELIVERED,
        MQTT_CLIENT_DELIVERED_FAIL,
        BROKER_ACK,
        BROKER_ACK_TIME_OUT,
        BROKER_ACK_WITH_RESP,
        BROKER_ACK_WITH_RESP_TIMEOUT;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public enum MqttRequestType {
        MQTT_SUBSCRIBES,
        MQTT_UN_SUBSCRIBES,
        MQTT_PUBLISH,
        MQTT_PUBLISH_WITH_RESP;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public MqttServiceRequestTaskBean() {
        this.taskId = 0;
        this.mqttEngineSendTimeSpan = 0;
        this.createTime = 0;
        this.timeOutDeadline = 0;
        this.seqNum = "";
        this.messageId = -1;
        this.ackTimeSpan = -1;
        this.respTimeSpan = -1;
        this.taskDeliveryStatue = LDS_MQTT_MESSAGE_DEVELIVERY_STATUE.WAITING_TO_DELIVER;
        this.topic = "";
        this.topics = new String[0];
        this.flagEnableDelete = false;
        this.createTime = System.currentTimeMillis();
    }

    public boolean isSeqMatch(String seq) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{seq}, this, changeQuickRedirect, false, 1510, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(seq);
        sb.append("");
        return sb.toString().equals(this.seqNum) && !TextUtils.isEmpty(this.seqNum);
    }

    public void updateAckTime() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1511, new Class[0], Void.TYPE).isSupported) {
            this.ackTimeSpan = System.currentTimeMillis();
        }
    }

    public void updateRespTime() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1512, new Class[0], Void.TYPE).isSupported) {
            this.respTimeSpan = System.currentTimeMillis();
        }
    }

    /* renamed from: com.leedarson.mqtt.beans.MqttServiceRequestTaskBean$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$leedarson$mqtt$beans$MqttServiceRequestTaskBean$LDS_MQTT_MESSAGE_DEVELIVERY_STATUE;

        static {
            int[] iArr = new int[LDS_MQTT_MESSAGE_DEVELIVERY_STATUE.values().length];
            $SwitchMap$com$leedarson$mqtt$beans$MqttServiceRequestTaskBean$LDS_MQTT_MESSAGE_DEVELIVERY_STATUE = iArr;
            try {
                iArr[LDS_MQTT_MESSAGE_DEVELIVERY_STATUE.WAITING_TO_DELIVER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$leedarson$mqtt$beans$MqttServiceRequestTaskBean$LDS_MQTT_MESSAGE_DEVELIVERY_STATUE[LDS_MQTT_MESSAGE_DEVELIVERY_STATUE.MQTT_CLIENT_DELIVERED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$leedarson$mqtt$beans$MqttServiceRequestTaskBean$LDS_MQTT_MESSAGE_DEVELIVERY_STATUE[LDS_MQTT_MESSAGE_DEVELIVERY_STATUE.MQTT_CLIENT_DELIVERED_FAIL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$leedarson$mqtt$beans$MqttServiceRequestTaskBean$LDS_MQTT_MESSAGE_DEVELIVERY_STATUE[LDS_MQTT_MESSAGE_DEVELIVERY_STATUE.BROKER_ACK.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public n<Integer, String> timeOutToAnylizeReason() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1513, new Class[0], n.class);
        if (proxy.isSupported) {
            return (n) proxy.result;
        }
        switch (AnonymousClass1.$SwitchMap$com$leedarson$mqtt$beans$MqttServiceRequestTaskBean$LDS_MQTT_MESSAGE_DEVELIVERY_STATUE[this.taskDeliveryStatue.ordinal()]) {
            case 1:
                return new n<>(4082, "超时10s，未收到SDK发出标识");
            case 2:
                return new n<>(4083, "超时10s，未收到SDK发出标识");
            case 3:
                return new n<>(4082, "超时10s，未收到SDK发出标识-发布失败");
            case 4:
                return new n<>(4084, "超时10s，未收到response");
            default:
                return new n<>(-4000014, "超时10s，MQTT 未连接，消息未发出 " + this.taskDeliveryStatue);
        }
    }
}
