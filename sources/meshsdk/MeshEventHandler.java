package meshsdk;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import com.leedarson.serviceimpl.elkstrays.b;
import com.telink.ble.mesh.core.message.MeshSigModel;
import com.telink.ble.mesh.core.message.NotificationMessage;
import com.telink.ble.mesh.core.message.StatusMessage;
import com.telink.ble.mesh.core.message.config.ModelAppStatusMessage;
import com.telink.ble.mesh.core.message.generic.LevelStatusMessage;
import com.telink.ble.mesh.core.message.generic.OnOffStatusMessage;
import com.telink.ble.mesh.core.message.lighting.CtlStatusMessage;
import com.telink.ble.mesh.core.message.lighting.CtlTemperatureStatusMessage;
import com.telink.ble.mesh.core.message.lighting.HslStatusMessage;
import com.telink.ble.mesh.core.message.lighting.LightnessStatusMessage;
import com.telink.ble.mesh.entity.OnlineStatusInfo;
import com.telink.ble.mesh.foundation.Event;
import com.telink.ble.mesh.foundation.EventBus;
import com.telink.ble.mesh.foundation.EventHandler;
import com.telink.ble.mesh.foundation.EventListener;
import com.telink.ble.mesh.foundation.MeshController;
import com.telink.ble.mesh.foundation.event.MeshEvent;
import com.telink.ble.mesh.foundation.event.NetworkInfoUpdateEvent;
import com.telink.ble.mesh.foundation.event.OnlineStatusEvent;
import com.telink.ble.mesh.foundation.event.StatusNotificationEvent;
import com.telink.ble.mesh.util.MeshLogger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import meshsdk.model.AppSettings;
import meshsdk.model.MeshInfo;
import meshsdk.model.NodeInfo;
import meshsdk.model.NodeStatusChangedEvent;
import meshsdk.sql.SqlManager;
import meshsdk.util.LDSMeshUtil;
import meshsdk.util.LDSModel;
import meshsdk.util.UnitConvert;

public class MeshEventHandler implements EventHandler {
    public static final int WHAT_HEARTBEAT = 1;
    public static final int WHAT_OFFLINE = 0;
    private static MeshEventHandler mInstance = null;
    private HashMap<String, OfflineHandler> handlerMap = new HashMap<>();
    private EventBus<String> mEventBus = new EventBus<>(Looper.getMainLooper());
    private HandlerThread offlineCheckThread = new HandlerThread("offline check thread");

    public class OfflineHandler extends Handler {
        public OfflineHandler(@NonNull Looper looper) {
            super(looper);
        }

        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            NodeInfo nodeInfo = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, (String) msg.obj);
            if (nodeInfo != null) {
                switch (msg.what) {
                    case 0:
                        nodeInfo.offlineTaskExecute();
                        return;
                    case 1:
                        nodeInfo.hearBeatTaskExecute();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public static MeshEventHandler getInstance() {
        if (mInstance == null) {
            synchronized (SIGMesh.class) {
                if (mInstance == null) {
                    mInstance = new MeshEventHandler();
                }
            }
        }
        return mInstance;
    }

    public MeshEventHandler() {
        this.offlineCheckThread.start();
    }

    public OfflineHandler getOfflineCheckHandler(String mac) {
        if (this.handlerMap.containsKey(mac)) {
            return this.handlerMap.get(mac);
        }
        OfflineHandler h = new OfflineHandler(this.offlineCheckThread.getLooper());
        this.handlerMap.put(mac, h);
        return h;
    }

    /* access modifiers changed from: protected */
    public void onNetworkInfoUpdate(NetworkInfoUpdateEvent networkInfoUpdateEvent) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        MeshLog.i("onNetworkInfoUpdate meshInfo.seu:" + meshInfo.sequenceNumber + ",onNetworkInfoUpdate.seq:" + networkInfoUpdateEvent.b());
        if (meshInfo.sequenceNumber < networkInfoUpdateEvent.b()) {
            meshInfo.ivIndex = networkInfoUpdateEvent.a();
            meshInfo.sequenceNumber = networkInfoUpdateEvent.b();
            try {
                SqlManager.updateSeqNum(meshInfo.localAddress, networkInfoUpdateEvent.b(), meshInfo.meshUUID);
            } catch (Exception e) {
                MeshLog.e("onNetworkInfoUpdate exception,localAddr:" + meshInfo.localAddress + ",seqNum:" + meshInfo.sequenceNumber + ",");
                e.printStackTrace();
            }
            MeshLog.i("onNetworkInfoUpdate,localAddr:" + meshInfo.localAddress + ",seqNum:" + meshInfo.sequenceNumber);
            saveMeshInfoAndDict();
            if (SIGMesh.getInstance().getGlobalCallback() != null) {
                SIGMesh.getInstance().getGlobalCallback().onNetworkInfoUpdate(networkInfoUpdateEvent.b(), networkInfoUpdateEvent.a());
            }
        }
    }

    private void saveMeshInfoAndDict() {
        SIGMesh.getInstance().executorTask(new Runnable() {
            public void run() {
                SIGMesh.getInstance().getMeshInfo().saveOrUpdate(SIGMesh.getInstance().getContext(), "onNetworkInfoUpdate.MeshEventHandler.saveMeshInfoAndDict");
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onStatusNotificationEvent(StatusNotificationEvent statusNotificationEvent) {
        int onOff;
        NotificationMessage message = statusNotificationEvent.a();
        StatusMessage statusMessage = message.d();
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        if (statusMessage == null) {
            for (NodeInfo nodeInfo : meshInfo.nodes) {
                if (nodeInfo.meshAddress == message.c() && nodeInfo.getOnOff() == -1) {
                    nodeInfo.setOnOff(0);
                    b.a(nodeInfo.macAddress + " 收到EVENT_TYPE_NOTIFICATION_MESSAGE_UNKNOWN,开关状态手动设置为:" + 0 + "(-1->" + 0 + ") 通知设备上线");
                    onNodeInfoStatusChanged(10000, nodeInfo);
                    return;
                }
            }
            return;
        }
        NodeInfo statusChangedNode = null;
        int modelId = 0;
        boolean onlineChange = false;
        Iterator<NodeInfo> it = meshInfo.nodes.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            NodeInfo nodeInfo2 = it.next();
            if (nodeInfo2.meshAddress == message.c()) {
                int srcOnOffState = nodeInfo2.getOnOff();
                if (srcOnOffState == -1 && !(statusMessage instanceof ModelAppStatusMessage)) {
                    onlineChange = true;
                    if (statusMessage instanceof OnOffStatusMessage) {
                        OnOffStatusMessage onOffStatusMessage = (OnOffStatusMessage) statusMessage;
                        onOff = onOffStatusMessage.e() ? onOffStatusMessage.d() : onOffStatusMessage.c();
                        MeshLogNew.meshMsg(nodeInfo2.macAddress + " 收到mesh onOff状态消息,开关状态为:" + onOff + "(-1->" + onOff + ") 通知设备上线");
                    } else {
                        onOff = 0;
                        MeshLogNew.meshMsg(nodeInfo2.macAddress + " 收到mesh,开关状态手动设置为:" + 0 + "(-1->" + 0 + ") 通知设备上线");
                    }
                    nodeInfo2.setOnOff(onOff);
                    onNodeInfoStatusChanged(10000, nodeInfo2);
                    if (srcOnOffState != onOff) {
                        statusChangedNode = nodeInfo2;
                        modelId = 4096;
                    }
                }
            }
        }
        if (message.d() instanceof OnOffStatusMessage) {
            OnOffStatusMessage onOffStatusMessage2 = (OnOffStatusMessage) statusMessage;
            int onOff2 = onOffStatusMessage2.e() ? onOffStatusMessage2.d() : onOffStatusMessage2.c();
            Iterator<NodeInfo> it2 = meshInfo.nodes.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                NodeInfo nodeInfo3 = it2.next();
                if (nodeInfo3.meshAddress == message.c()) {
                    MeshLogNew.meshMsg(nodeInfo3.macAddress + " 收到mesh onOff状态消息上报,开关状态为:" + onOff2);
                    statusChangedNode = nodeInfo3;
                    modelId = 4096;
                    nodeInfo3.setOnOff(onOff2);
                    break;
                }
            }
        } else if (message.d() instanceof LevelStatusMessage) {
            LevelStatusMessage levelStatusMessage = (LevelStatusMessage) statusMessage;
            int srcAdr = message.c();
            int tarVal = UnitConvert.level2lum((short) (levelStatusMessage.e() ? levelStatusMessage.d() : levelStatusMessage.c()));
            for (NodeInfo onlineDevice : meshInfo.nodes) {
                if (onlineDevice.compositionData != null) {
                    if (onlineDevice.getTargetEleAdr(MeshSigModel.SIG_MD_LIGHTNESS_S.modelId) == srcAdr) {
                        if (onLumStatus(onlineDevice, tarVal)) {
                            statusChangedNode = onlineDevice;
                            modelId = LDSModel.MODEL_BRIGHTNESS_CTRL;
                        }
                    } else if (onlineDevice.getTargetEleAdr(MeshSigModel.SIG_MD_LIGHT_CTL_TEMP_S.modelId) == srcAdr && onlineDevice.temp != tarVal) {
                        onlineDevice.temp = tarVal;
                        modelId = 4867;
                        statusChangedNode = onlineDevice;
                    }
                }
            }
        } else if (message.d() instanceof CtlStatusMessage) {
            CtlStatusMessage ctlStatusMessage = (CtlStatusMessage) statusMessage;
            MeshLogger.a("ctl : " + ctlStatusMessage.toString());
            int srcAdr2 = message.c();
            Iterator<NodeInfo> it3 = meshInfo.nodes.iterator();
            while (true) {
                if (!it3.hasNext()) {
                    break;
                }
                NodeInfo onlineDevice2 = it3.next();
                if (onlineDevice2.meshAddress == srcAdr2) {
                    onlineDevice2.lum = UnitConvert.lightness2lum(ctlStatusMessage.g() ? ctlStatusMessage.e() : ctlStatusMessage.c());
                    onNodeInfoStatusChanged(LDSModel.MODEL_BRIGHTNESS_CTRL, onlineDevice2);
                    onlineDevice2.temp = ctlStatusMessage.g() ? ctlStatusMessage.f() : ctlStatusMessage.d();
                    statusChangedNode = onlineDevice2;
                    modelId = LDSModel.MODEL_TEMP_CTRL;
                }
            }
        } else if (message.d() instanceof LightnessStatusMessage) {
            LightnessStatusMessage lightnessStatusMessage = (LightnessStatusMessage) statusMessage;
            int srcAdr3 = message.c();
            Iterator<NodeInfo> it4 = meshInfo.nodes.iterator();
            while (true) {
                if (!it4.hasNext()) {
                    break;
                }
                NodeInfo onlineDevice3 = it4.next();
                if (onlineDevice3.meshAddress == srcAdr3) {
                    onlineDevice3.lum = UnitConvert.lightness2lum(lightnessStatusMessage.e() ? lightnessStatusMessage.d() : lightnessStatusMessage.c());
                    statusChangedNode = onlineDevice3;
                    modelId = LDSModel.MODEL_BRIGHTNESS_CTRL;
                }
            }
        } else if (message.d() instanceof CtlTemperatureStatusMessage) {
            CtlTemperatureStatusMessage ctlTemp = (CtlTemperatureStatusMessage) statusMessage;
            int srcAdr4 = message.c();
            Iterator<NodeInfo> it5 = meshInfo.nodes.iterator();
            while (true) {
                if (!it5.hasNext()) {
                    break;
                }
                NodeInfo onlineDevice4 = it5.next();
                if (onlineDevice4.meshAddress == srcAdr4) {
                    onlineDevice4.temp = UnitConvert.lightness2lum(ctlTemp.e() ? ctlTemp.d() : ctlTemp.c());
                    statusChangedNode = onlineDevice4;
                    modelId = LDSModel.MODEL_TEMP_CTRL;
                }
            }
        } else if (message.d() instanceof HslStatusMessage) {
            HslStatusMessage hslStatusMessage = (HslStatusMessage) statusMessage;
            int srcAdr5 = message.c();
            Iterator<NodeInfo> it6 = meshInfo.nodes.iterator();
            while (true) {
                if (!it6.hasNext()) {
                    break;
                }
                NodeInfo onlineDevice5 = it6.next();
                if (onlineDevice5.meshAddress == srcAdr5) {
                    onlineDevice5.hue = Math.round((((float) hslStatusMessage.c()) * 360.0f) / 65535.0f);
                    onlineDevice5.sat = Math.round((((float) hslStatusMessage.e()) * 100.0f) / 65535.0f);
                    int round = Math.round((((float) hslStatusMessage.d()) * 100.0f) / 65535.0f);
                    onlineDevice5.light = round;
                    if (round == 0) {
                        onlineDevice5.light = 100;
                    }
                    statusChangedNode = onlineDevice5;
                    modelId = LDSModel.MODEL_HSL_CTRL;
                }
            }
        }
        if (statusChangedNode != null) {
            if (onlineChange) {
                onNodeInfoStatusChanged(10000, statusChangedNode);
                if (!(message.d() instanceof OnOffStatusMessage)) {
                    MeshLogNew.meshMsg(statusChangedNode.macAddress + " 收到mesh消息" + message.d().getClass().getSimpleName() + "(之前是离线), 补发开关状态:" + statusChangedNode.getOnOff());
                    onNodeInfoStatusChanged(4096, statusChangedNode);
                }
            }
            MeshLogNew.meshMsg(statusChangedNode.macAddress + " 收到mesh消息->更新:" + LDSModel.LdsModelName.modelName(modelId));
            com.leedarson.serviceimpl.reporters.deviceControl.b.b().g(statusChangedNode, modelId, "onStatusNotificationEvent");
            com.leedarson.serviceimpl.reporters.groupControl.b.b().h(statusChangedNode, modelId, "onStatusNotificationEvent");
            onNodeInfoStatusChanged(modelId, statusChangedNode);
        }
    }

    /* access modifiers changed from: protected */
    public void onOnlineStatusEvent(OnlineStatusEvent onlineStatusEvent) {
        OnlineStatusInfo onlineStatusInfo;
        int onOff;
        List<OnlineStatusInfo> infoList = onlineStatusEvent.a();
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        if (infoList != null && meshInfo != null) {
            NodeInfo statusChangedNode = null;
            Iterator<OnlineStatusInfo> it = infoList.iterator();
            while (it.hasNext() && (r5 = onlineStatusInfo.f) != null && r5.length >= 3) {
                NodeInfo deviceInfo = meshInfo.getDeviceByMeshAddress((onlineStatusInfo = it.next()).c);
                if (deviceInfo != null) {
                    if (onlineStatusInfo.d == 0) {
                        onOff = -1;
                    } else if (onlineStatusInfo.f[0] == 0) {
                        onOff = 0;
                    } else {
                        onOff = 1;
                    }
                    if (deviceInfo.getOnOff() != onOff) {
                        statusChangedNode = deviceInfo;
                        StringBuilder sb = new StringBuilder();
                        sb.append("收到节点:");
                        sb.append(statusChangedNode.macAddress);
                        sb.append(onlineStatusInfo.d != 0 ? "成功上线" : "离线");
                        MeshLogNew.v(sb.toString());
                    }
                    deviceInfo.setOnOff(onOff);
                    int i = deviceInfo.lum;
                    byte[] bArr = onlineStatusInfo.f;
                    if (i != bArr[0]) {
                        statusChangedNode = deviceInfo;
                        deviceInfo.lum = bArr[0];
                    }
                    if (deviceInfo.temp != bArr[1]) {
                        statusChangedNode = deviceInfo;
                        deviceInfo.temp = bArr[1];
                    }
                }
            }
            if (statusChangedNode != null) {
                MeshLog.i("###  MeshEventHandler.java onOnlineStatusEvent  mesh组网中节点主动主报 macAddress=" + statusChangedNode.macAddress + "  meshAddress=" + statusChangedNode.meshAddress);
                onNodeInfoStatusChanged(10000, statusChangedNode);
            }
        }
    }

    private boolean onLumStatus(NodeInfo nodeInfo, int lum) {
        boolean statusChanged = false;
        if (nodeInfo.getOnOff() != (lum > 0 ? 1 : 0)) {
            statusChanged = true;
        }
        if (nodeInfo.lum == lum) {
            return statusChanged;
        }
        nodeInfo.lum = lum;
        return true;
    }

    private boolean onTempStatus(NodeInfo nodeInfo, int temp) {
        if (nodeInfo.temp == temp) {
            return false;
        }
        nodeInfo.temp = temp;
        return true;
    }

    private void onNodeInfoStatusChanged(int modelId, NodeInfo nodeInfo) {
        dispatchEvent(new NodeStatusChangedEvent(this, NodeStatusChangedEvent.EVENT_TYPE_NODE_STATUS_CHANGED, nodeInfo, modelId));
    }

    /* access modifiers changed from: protected */
    public void onMeshEvent(MeshEvent autoConnectEvent) {
        String eventType = (String) autoConnectEvent.getType();
        if ("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_DISCONNECTED".equals(eventType)) {
            AppSettings.ONLINE_STATUS_ENABLE = false;
            if (SIGMesh.getInstance().hasLastConnected()) {
                for (NodeInfo nodeInfo : SIGMesh.getInstance().getMeshInfo().nodes) {
                    nodeInfo.setOnOff(-1);
                }
            }
        } else if ("com.telink.ble.com.telink.ble.mesh.MESH_EMPTY".equals(eventType)) {
            MeshLog.debugInfo("MESH NODE EMPTY");
        }
    }

    public void onEventHandle(Event<String> event) {
        if (event instanceof NetworkInfoUpdateEvent) {
            onNetworkInfoUpdate((NetworkInfoUpdateEvent) event);
        } else if (event instanceof StatusNotificationEvent) {
            onStatusNotificationEvent((StatusNotificationEvent) event);
        } else if (event instanceof OnlineStatusEvent) {
            onOnlineStatusEvent((OnlineStatusEvent) event);
        } else if (event instanceof MeshEvent) {
            onMeshEvent((MeshEvent) event);
        }
        dispatchEvent(event);
    }

    public void addEventListener(String eventType, EventListener<String> listener) {
        this.mEventBus.b(eventType, listener);
    }

    public void removeEventListener(EventListener<String> listener) {
        this.mEventBus.g(listener);
    }

    public void removeEventListener(String eventType, EventListener<String> listener) {
        this.mEventBus.h(eventType, listener);
    }

    public void removeEventListeners() {
        this.mEventBus.i();
    }

    public void dispatchEvent(Event<String> event) {
        this.mEventBus.c(event);
    }

    public void setupMesh(MeshInfo mesh) {
        MeshLogger.a("setup mesh info: " + mesh.toString());
        SIGMesh.getInstance().setMeshInfo(mesh);
        dispatchEvent(new MeshEvent(this, "com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_MESH_RESET", "mesh reset", (MeshController.Mode) null));
    }
}
