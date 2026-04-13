package meshsdk.ctrl;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.google.maps.android.BuildConfig;
import com.leedarson.serviceimpl.elkstrays.b;
import com.leedarson.serviceimpl.reporters.c;
import com.leedarson.serviceimpl.strategys.e;
import com.telink.ble.mesh.core.message.MeshSigModel;
import com.telink.ble.mesh.core.message.NotificationMessage;
import com.telink.ble.mesh.core.message.generic.OnOffGetMessage;
import com.telink.ble.mesh.core.message.lighting.CtlGetMessage;
import com.telink.ble.mesh.core.message.lighting.HslGetMessage;
import com.telink.ble.mesh.core.message.lighting.LightnessGetMessage;
import com.telink.ble.mesh.core.message.time.TimeStatusMessage;
import com.telink.ble.mesh.foundation.Event;
import com.telink.ble.mesh.foundation.EventListener;
import com.telink.ble.mesh.foundation.MeshController;
import com.telink.ble.mesh.foundation.MeshService;
import com.telink.ble.mesh.foundation.event.AutoConnectEvent;
import com.telink.ble.mesh.foundation.event.BluetoothEvent;
import com.telink.ble.mesh.foundation.event.MeshEvent;
import com.telink.ble.mesh.foundation.event.StatusNotificationEvent;
import com.telink.ble.mesh.foundation.parameter.AutoConnectParameters;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import meshsdk.MeshEventHandler;
import meshsdk.MeshLog;
import meshsdk.MeshLogNew;
import meshsdk.SIGMesh;
import meshsdk.cache.cachemodule.MultiPropertyCacheInstance;
import meshsdk.callback.MeshCustomcmdCallback;
import meshsdk.callback.MeshGlobalCallback;
import meshsdk.datamgr.GroupFixHelper;
import meshsdk.datamgr.MeshDataManager;
import meshsdk.model.AppSettings;
import meshsdk.model.NodeInfo;
import meshsdk.model.NodeStatusChangedEvent;
import meshsdk.model.json.MultiPropertyData;
import meshsdk.sql.SqlManager;
import meshsdk.util.BleCompat;
import meshsdk.util.LDSMeshUtil;
import meshsdk.util.LDSModel;
import meshsdk.util.MeshConstants;
import meshsdk.util.ProcedureCollector;
import meshsdk.util.RemoveNodeTask;
import org.json.JSONException;
import org.json.JSONObject;

public class SyncCtrl extends CtrlLifecycle implements EventListener<String> {
    private CmdCtrl cmdCtrl;
    /* access modifiers changed from: private */
    public MeshGlobalCallback globalCallback;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler(Looper.getMainLooper());

    public SyncCtrl(SIGMesh sigMesh, CmdCtrl cmdCtrl2) {
        super(sigMesh);
        onCreate();
        this.cmdCtrl = cmdCtrl2;
    }

    public void onCreate() {
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_AUTO_CONNECT_LOGIN", this);
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.MESH_EMPTY", this);
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_DISCONNECTED", this);
        MeshEventHandler.getInstance().addEventListener("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_MESH_RESET", this);
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_BLUETOOTH_STATE_CHANGE", this);
        MeshEventHandler.getInstance().addEventListener(NodeStatusChangedEvent.EVENT_TYPE_NODE_STATUS_CHANGED, this);
        MeshEventHandler.getInstance().addEventListener(TimeStatusMessage.class.getName(), this);
    }

    public void setGlobalCallback(MeshGlobalCallback callback) {
        this.globalCallback = callback;
    }

    public void autoConnect(MeshGlobalCallback statusSyncCallback) {
        if (!BleCompat.checkNeededPermission(SIGMesh.getInstance().getContext())) {
            MeshLog.e("Sync ctrl auto connect, but android12 has no Nearby permission or other sdk int has no location permission");
            b.a("再次确认权限是否满足：Sync ctrl auto connect, but android12 has no Nearby permission or other sdk int has no location permission");
            return;
        }
        this.globalCallback = statusSyncCallback;
        MeshLog.d("Sync ctrl auto connect");
        if (!ProcedureCollector.getRunningFunction().equals(MeshConstants.TRACE_ID_AUTO_CONNECT) && !SIGMesh.getInstance().hasConnected()) {
            b.a("autoConnect埋点：是否放这边初始化");
            ProcedureCollector.startCollect(MeshConstants.TRACE_ID_AUTO_CONNECT, 1);
        }
        if (MeshService.k().f() == MeshController.Mode.MODE_AUTO_CONNECT) {
            b.a("当前已经在auto连接组网中....");
            return;
        }
        b.a("currentMode:" + MeshService.k().f());
        AutoConnectParameters parameters = new AutoConnectParameters();
        if (SIGMesh.getInstance().getMeshInfo().nodes == null || SIGMesh.getInstance().getMeshInfo().nodes.size() != 1) {
            SIGMesh.getInstance().hasLastDirectMac();
        } else {
            String s = SIGMesh.getInstance().getMeshInfo().nodes.get(0).macAddress;
            b.a("确认当前帐号下是否仅有一个设备：是的->" + s);
            if (!SqlManager.delNodesContains(s)) {
                b.a("被选中的目标设备可用（不存在被删除节点缓存数据中):" + s);
            } else {
                b.a("被选中的目标设备（存在被删除节点缓存数据中）");
            }
        }
        if (SIGMesh.getInstance().getMeshInfo().nodes == null || SIGMesh.getInstance().getMeshInfo().nodes.size() == 0) {
            b.a("当前账号下 不存在设备");
        } else if (SIGMesh.getInstance().getMeshInfo().nodes.size() > 1) {
            StringBuffer sb = new StringBuffer("当前账号下存在:" + SIGMesh.getInstance().getMeshInfo().nodes.size() + "个设备[");
            for (NodeInfo nodeInfo : SIGMesh.getInstance().getMeshInfo().nodes) {
                String macAddress = nodeInfo.macAddress;
                if (!SqlManager.delNodesContains(macAddress)) {
                    sb.append(macAddress);
                    sb.append(",");
                } else {
                    sb.append(macAddress);
                    sb.append("(不可用)");
                    sb.append(",");
                }
            }
            sb.append("]");
            b.a(sb.toString());
        }
        MeshService.k().a(parameters);
    }

    @Deprecated
    public void autoConnect(String directMac, MeshGlobalCallback statusSyncCallback) {
        this.globalCallback = statusSyncCallback;
        MeshLog.d("Sync ctrl auto connect with direct Mac:" + directMac);
        MeshService.k().a(new AutoConnectParameters());
    }

    @Deprecated
    public void AllStateSync(NodeInfo deviceInfo) {
        if (!deviceInfo.compositionData.lowPowerSupport()) {
            this.mHandler.post(new SyncStateTask(deviceInfo));
        }
    }

    public class SyncStateTask implements Runnable {
        public int count = 0;
        private NodeInfo dev;

        SyncStateTask(NodeInfo n) {
            this.dev = n;
        }

        public void run() {
            int i = this.count;
            if (i < 4) {
                if (i == 0) {
                    SyncCtrl.this.queryOnOff(this.dev);
                } else if (i == 1) {
                    SyncCtrl.this.queryHsl(this.dev);
                } else if (i == 2) {
                    SyncCtrl.this.queryLightness(this.dev);
                } else if (i == 3) {
                    SyncCtrl.this.queryCtl(this.dev);
                }
                this.count++;
                SyncCtrl.this.mHandler.postDelayed(this, 200);
                return;
            }
            this.count = 0;
            SyncCtrl.this.mHandler.removeCallbacks(this);
        }
    }

    public void queryCtl(NodeInfo deviceInfo) {
        int appKeyIndex = SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex();
        int modelEleAdr = deviceInfo.getTargetEleAdr(MeshSigModel.SIG_MD_LIGHT_CTL_S.modelId);
        if (modelEleAdr != -1) {
            MeshLog.d("query device ctl status(lum & temp):" + deviceInfo.macAddress);
            MeshMessagePool.getInstance().addAndSend(CtlGetMessage.I(modelEleAdr, appKeyIndex, 0));
        }
    }

    public void queryHsl(NodeInfo deviceInfo) {
        int appKeyIndex = SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex();
        int modelEleAdr = deviceInfo.getTargetEleAdr(MeshSigModel.SIG_MD_LIGHT_HSL_S.modelId);
        if (modelEleAdr != -1) {
            MeshMessagePool.getInstance().addAndSend(HslGetMessage.I(modelEleAdr, appKeyIndex, 0));
            MeshLog.d("query device hsl status:" + deviceInfo.macAddress);
        }
    }

    public void queryLightness(NodeInfo deviceInfo) {
        int appKeyIndex = SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex();
        int modelEleAdr = deviceInfo.getTargetEleAdr(MeshSigModel.SIG_MD_LIGHTNESS_S.modelId);
        if (modelEleAdr != -1) {
            MeshMessagePool.getInstance().addAndSend(LightnessGetMessage.I(modelEleAdr, appKeyIndex, 0));
            MeshLog.d("query device lightness status:" + deviceInfo.macAddress);
        }
    }

    public void queryOnOff(NodeInfo deviceInfo) {
        int appKeyIndex = SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex();
        int modelEleAdr = deviceInfo.getTargetEleAdr(MeshSigModel.SIG_MD_G_ONOFF_S.modelId);
        if (modelEleAdr != -1) {
            OnOffGetMessage onOffGetMessage = OnOffGetMessage.I(modelEleAdr, appKeyIndex, 1);
            onOffGetMessage.d("发送获取开关状态消息:" + deviceInfo.macAddress);
            onOffGetMessage.e(110);
            MeshMessagePool.getInstance().addAndSend(onOffGetMessage);
            MeshLog.d("query device onOff status:" + deviceInfo.macAddress + ",responseCode");
        }
    }

    public void performed(Event<String> event) {
        Event<String> event2 = event;
        if (event.getType().equals(TimeStatusMessage.class.getName())) {
            NotificationMessage message = ((StatusNotificationEvent) event2).a();
            TimeStatusMessage timeStatusMessage = (TimeStatusMessage) message.d();
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(1000 * (timeStatusMessage.c() + 946684800)));
            NodeInfo node = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, message.c());
            StringBuilder sb = new StringBuilder();
            sb.append("time status: ");
            sb.append(timeStatusMessage.c());
            sb.append(",date:");
            sb.append(date);
            sb.append(",mac:");
            sb.append(node == null ? BuildConfig.TRAVIS : node.macAddress);
            MeshLog.i(sb.toString());
        } else if (event.getType().equals("com.telink.ble.com.telink.ble.mesh.MESH_EMPTY")) {
            MeshLog.d("#EVENT_TYPE_MESH_EMPTY");
        } else if (event.getType().equals("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_AUTO_CONNECT_LOGIN")) {
            SIGMesh sigMesh = SIGMesh.getInstance();
            boolean reInvoke = sigMesh.hasConnected();
            sigMesh.setConnected(true);
            b.a("SyncCtrl 收到mesh网络连上通知EVENT_TYPE_AUTO_CONNECT_LOGIN, lastConnected:" + reInvoke);
            AppSettings.ONLINE_STATUS_ENABLE = MeshService.k().m();
            if (event2 instanceof AutoConnectEvent) {
                ProcedureCollector.autoConnectState = MeshConstants.AC_STATE_LOGIN_SUCCESS;
            }
            if (!AppSettings.ONLINE_STATUS_ENABLE) {
                getDeviceStatus(reInvoke);
            } else {
                MeshLog.e("出问题啦，上线了，怎么不去查上线状态,就因为ONLINE_STATUS_ENABLE是true?");
            }
            MeshMessagePool.getInstance().handlerCachePoolThread();
            sigMesh.getMeshInfo().saveLocalUUIDAddress(sigMesh.getContext(), 0);
            this.cmdCtrl.setHwTime(65535);
            this.globalCallback.onNetworkStatusChange(1);
            if (!reInvoke) {
                GroupFixHelper.getInstance().startFixCheck();
            }
        } else if (event.getType().equals("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_DISCONNECTED")) {
            this.mHandler.removeCallbacksAndMessages((Object) null);
            boolean isConnected = SIGMesh.getInstance().hasConnected();
            SIGMesh.getInstance().setHasLastConnected(isConnected);
            b.a("setConnected false, lastConnected:" + isConnected);
            SIGMesh.getInstance().setConnected(false);
            if (event2 instanceof MeshEvent) {
                MeshEvent meshEvent = (MeshEvent) event2;
                MeshLog.e("mesh disconnect=====isAutoConnect: " + meshEvent.a());
                if (meshEvent.a() && this.globalCallback != null && !MeshDataManager.flagNetConfingAdddevices) {
                    if (SIGMesh.getInstance().hasLastConnected()) {
                        c.f("收到主节点ble断开连接通知,autoConnect request");
                    }
                    autoConnect(this.globalCallback);
                }
            }
            if (SIGMesh.getInstance().hasLastConnected()) {
                b.b("mesh网络断开了，通知web");
                this.globalCallback.onNetworkStatusChange(0);
            }
        }
        String eventType = event.getType();
        if (!eventType.equals("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_DISCONNECTED") && !eventType.equals("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_MESH_RESET")) {
            if (eventType.equals(NodeStatusChangedEvent.EVENT_TYPE_NODE_STATUS_CHANGED)) {
                if (this.globalCallback != null && (event2 instanceof NodeStatusChangedEvent)) {
                    NodeStatusChangedEvent e = (NodeStatusChangedEvent) event2;
                    int modelId = e.getModelId();
                    Object value = null;
                    switch (modelId) {
                        case 4096:
                            value = Integer.valueOf(e.getNodeInfo().getOnOff());
                            break;
                        case LDSModel.MODEL_BRIGHTNESS_CTRL /*4864*/:
                            value = Integer.valueOf(e.getNodeInfo().lum);
                            break;
                        case LDSModel.MODEL_TEMP_CTRL /*4867*/:
                            value = Integer.valueOf(e.getNodeInfo().temp);
                            break;
                        case LDSModel.MODEL_HSL_CTRL /*4871*/:
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("HSLHue", e.getNodeInfo().hue);
                                jsonObject.put("HSLSaturation", e.getNodeInfo().sat);
                                jsonObject.put("HSLLightness", e.getNodeInfo().light);
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                            }
                            value = jsonObject;
                            break;
                        case 10000:
                            int online = e.getNodeInfo().getOnOff() == -1 ? 0 : 1;
                            MeshLog.i("收到设备上报上线:" + "onff:" + e.getNodeInfo().getOnOff() + ",mac:" + e.getNodeInfo().macAddress);
                            this.globalCallback.onDeviceOnlineChange(e.getNodeInfo().macAddress, online);
                            return;
                    }
                    if (value != null) {
                        MeshLog.d(String.format(Locale.US, "通知设备StatusChange->modelName:%s,mac:%s,value:%s", new Object[]{LDSModel.LdsModelName.modelName(modelId), e.getNodeInfo().macAddress, value.toString()}));
                        this.globalCallback.onDeviceStatusChange(e.getNodeInfo().macAddress, modelId, value);
                    }
                }
            } else if (eventType.equals("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_BLUETOOTH_STATE_CHANGE") && ((BluetoothEvent) event2).a() == 12) {
                c.f("蓝牙打开了，autoConnect request");
                autoConnect(this.globalCallback);
            }
        }
    }

    public void getDeviceStatus(boolean reInvoke) {
        if (reInvoke) {
            MeshLog.e("getDeviceStatus已经调用了,不重复调用...");
            return;
        }
        if (!TextUtils.isEmpty(e.a)) {
            for (NodeInfo nodeInfo : SIGMesh.getInstance().getMeshInfo().nodes) {
                if (nodeInfo.macAddress.contains(e.a) && nodeInfo.protocolVersion < SIGMesh.NEW_PROTOCOL4) {
                    MeshLogNew.meshMsg("protocalVersion 设备协议版本<" + SIGMesh.NEW_PROTOCOL4 + ",mesh组网成功，发送获取设备:" + nodeInfo.macAddress + " 开关状态OnOffGetMessage信息");
                    queryOnOff(nodeInfo);
                }
            }
        }
        List<NodeInfo> oldVerisonBlow4Nodes = new ArrayList<>();
        for (final NodeInfo nodeInfo2 : SIGMesh.getInstance().getMeshInfo().nodes) {
            if (nodeInfo2.protocolVersion >= SIGMesh.NEW_PROTOCOL4) {
                MeshLog.i("mesh上线成功，调用查询设备信息接口: mac:" + nodeInfo2.macAddress);
                SIGMesh.getInstance().getMultipleProperties(nodeInfo2.meshAddress, new MeshCustomcmdCallback() {
                    public void onSuccess(Object data) {
                        if (data instanceof MultiPropertyData) {
                            MeshLog.i("mesh上线成功，设备状态查询成功 mac:" + nodeInfo2.macAddress + ",meshAddress:" + String.format("0x%04X", new Object[]{Integer.valueOf(nodeInfo2.meshAddress)}) + ",data:" + data);
                            MultiPropertyData statusData = (MultiPropertyData) data;
                            LDSMeshUtil.setMeshHWVersion(SIGMesh.getInstance().getMeshInfo().meshUUID, nodeInfo2.macAddress, statusData.version);
                            MultiPropertyCacheInstance.getInstance().put(nodeInfo2.macAddress, statusData);
                            if (SyncCtrl.this.globalCallback != null) {
                                SyncCtrl.this.globalCallback.onDeviceStatusChange(nodeInfo2.macAddress, 4096, Integer.valueOf(statusData.onoff));
                            }
                        }
                    }

                    public void onFail(int code, String msg, Object data) {
                        MeshLog.i("mesh上线成功，设备状态查询失败,mac:" + nodeInfo2.macAddress + ",code:" + code + ",msg:" + msg + ",data:" + data);
                    }
                });
            } else {
                oldVerisonBlow4Nodes.add(nodeInfo2);
            }
        }
        if (oldVerisonBlow4Nodes.size() > 0) {
            MeshLog.i("存在设备协议4.0以下的设备，上线状态还是往0xffff发");
            boolean m = MeshService.k().m();
            MeshMessagePool.getInstance().addAndSend(OnOffGetMessage.I(65535, this.sigMesh.getMeshInfo().getDefaultAppKeyIndex(), SIGMesh.getInstance().getMeshInfo().getOnlineCountInAll()));
        }
    }

    public void sendTimeStatus(NodeInfo nodeInfo) {
        if (nodeInfo.hasSetTime) {
            MeshLog.d("节点上线," + nodeInfo.macAddress + " has already 发送过 SetTime消息,return");
            return;
        }
        nodeInfo.hasSetTime = true;
        MeshLogNew.meshMsg("节点上线,SetTime消息 校准设备时间,mac:" + nodeInfo.macAddress);
        this.cmdCtrl.setHwTime(nodeInfo);
        SIGMesh.getInstance().executorTask(new RemoveNodeTask(nodeInfo.macAddress));
    }

    public void onDestroy() {
        MeshEventHandler.getInstance().removeEventListener(this);
    }

    public synchronized void sendInitConfig(String mac) {
        NodeInfo nodeInfo = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, mac);
        if (nodeInfo != null) {
            final NodeInfo finalNodeInfo = nodeInfo;
            SIGMesh.getInstance().executorTask(new Runnable() {
                public void run() {
                    SyncCtrl.this.sendTimeStatus(finalNodeInfo);
                }
            }, 200);
            SIGMesh.getInstance().executorTask(new Runnable() {
                public void run() {
                    if (MeshService.k().f() == MeshController.Mode.MODE_AUTO_CONNECT) {
                        MeshLog.d("=========executorTask query node status=========");
                        SyncCtrl.this.queryLightness(finalNodeInfo);
                        SyncCtrl.this.threadSleep(200);
                        SyncCtrl.this.queryCtl(finalNodeInfo);
                        SyncCtrl.this.threadSleep(200);
                        SyncCtrl.this.queryHsl(finalNodeInfo);
                    }
                }
            }, GroupCtrlAdapter.RETRY_TIMEOUT);
        }
    }

    /* access modifiers changed from: private */
    public void threadSleep(long mill) {
        try {
            Thread.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setAllPublish(NodeInfo targetNode) {
    }
}
