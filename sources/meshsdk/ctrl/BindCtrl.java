package meshsdk.ctrl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import androidx.work.WorkRequest;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.log.elk.a;
import com.leedarson.serviceimpl.reporters.AddDeviceStepBean;
import com.leedarson.serviceimpl.reporters.b;
import com.leedarson.serviceimpl.reporters.c;
import com.leedarson.serviceimpl.strategys.f;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.access.BindingBearer;
import com.telink.ble.mesh.core.message.MeshSigModel;
import com.telink.ble.mesh.core.message.config.ConfigStatus;
import com.telink.ble.mesh.core.message.config.ModelPublicationSetMessage;
import com.telink.ble.mesh.core.message.config.ModelPublicationStatusMessage;
import com.telink.ble.mesh.core.message.config.NodeResetMessage;
import com.telink.ble.mesh.core.message.config.NodeResetStatusMessage;
import com.telink.ble.mesh.entity.BindingDevice;
import com.telink.ble.mesh.entity.CompositionData;
import com.telink.ble.mesh.entity.ModelPublication;
import com.telink.ble.mesh.entity.MsgExtra;
import com.telink.ble.mesh.entity.ProvisioningDevice;
import com.telink.ble.mesh.foundation.Event;
import com.telink.ble.mesh.foundation.EventListener;
import com.telink.ble.mesh.foundation.MeshService;
import com.telink.ble.mesh.foundation.event.BindingEvent;
import com.telink.ble.mesh.foundation.event.ProvisioningEvent;
import com.telink.ble.mesh.foundation.event.StatusNotificationEvent;
import com.telink.ble.mesh.foundation.parameter.BindingParameters;
import com.telink.ble.mesh.foundation.parameter.ProvisioningParameters;
import com.telink.ble.mesh.util.MeshLogger;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import meshsdk.BaseResp;
import meshsdk.MeshEventHandler;
import meshsdk.MeshLog;
import meshsdk.MeshLogNew;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshBindCallback;
import meshsdk.callback.MeshUnbindCallback;
import meshsdk.model.MeshInfo;
import meshsdk.model.NetworkingDevice;
import meshsdk.model.NetworkingState;
import meshsdk.model.NodeInfo;
import meshsdk.model.PrivateDevice;
import meshsdk.sql.SqlManager;
import meshsdk.util.MeshConstants;
import meshsdk.util.SharedPreferenceHelper;
import meshsdk.util.TimeRecorder;

public class BindCtrl extends CtrlLifecycle implements EventListener<String> {
    public static HashMap<String, ProvisionedAndNotKeyBindCacheBean> cacheProvisionedAndNotKeyBindValueMap = new HashMap<>();
    public static int generateMeshAddressRetryCount = 0;
    public final int MAX_ELEMENT = 4;
    public int MODE_CONFIG = 0;
    public final int MODE_CONFIG_MULTI_ELEMENT_ONE = 1;
    public int MODE_CONFIG_SINGAL_DEFAULT = 0;
    public String USE_FOR_NODE_MAC_ADDRESS = "";
    private MeshBindCallback bindCallback;
    private NetworkingDevice currentBindingDevice;
    private NodeInfo currentUnbindDevice;
    private Handler delayHandler = new Handler(Looper.getMainLooper());
    private AtomicBoolean isBinding = new AtomicBoolean(false);
    private boolean isPubSetting = false;
    private boolean kickDirect;
    private Runnable kickoutTimeoutTask = new Runnable() {
        public void run() {
            BindCtrl.this.onKickOutTimeout();
        }
    };
    public String mMeshControllerHashCode = "";
    private boolean offlineDelete;
    private PublishCtrl publishCtrl;
    private volatile BaseResp resp = new BaseResp();
    private MeshUnbindCallback unbindCallback;

    public static class ProvisionedAndNotKeyBindCacheBean {
        public int meshAddress;
    }

    public void setBindCallback(MeshBindCallback bindCallback2) {
        this.bindCallback = bindCallback2;
    }

    public boolean isBinding() {
        return this.isBinding.get();
    }

    public BindCtrl(PublishCtrl publishCtrl2) {
        super((SIGMesh) null);
        this.publishCtrl = publishCtrl2;
        onCreate();
    }

    public void onCreate() {
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_PROVISION_BEGIN", this);
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_PROVISION_SUCCESS", this);
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_PROVISION_FAIL", this);
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_BIND_SUCCESS", this);
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_BIND_FAIL", this);
        MeshEventHandler.getInstance().addEventListener(NodeResetStatusMessage.class.getName(), this);
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_DISCONNECTED", this);
    }

    public void startProvision(NetworkingDevice processingDevice, byte[] OOBData) {
        MeshLog.e("11111-startProvision這裏怎麽調用了，有問題吧!!!");
        ProvisioningParameters provisioningParameters = generateProvisionParams((b) null, new HashMap(), 40, processingDevice, OOBData);
        if (provisioningParameters != null) {
            MeshLogger.a("provisioning device: " + processingDevice.toString() + "   macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
            if ("".equals(this.USE_FOR_NODE_MAC_ADDRESS)) {
                MeshService.k().C(provisioningParameters);
            } else {
                MeshService.k().B(this.USE_FOR_NODE_MAC_ADDRESS, provisioningParameters);
            }
        } else {
            MeshLogger.a("startProvision.provisioningParameters create fail " + processingDevice.toString() + "   macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
        }
    }

    public ProvisioningParameters generateProvisionParams(b logReporter, HashMap<String, Integer> currentGeneratedMeshAddressMap, int maxRetry, NetworkingDevice processingDevice, byte[] OOBData) {
        int address;
        ProvisionedAndNotKeyBindCacheBean provisionedAndNotKeyBindCacheBean;
        SIGMesh sigMesh = SIGMesh.getInstance();
        this.isBinding.compareAndSet(false, true);
        this.delayHandler.removeCallbacks(this.kickoutTimeoutTask);
        MeshLog.d("bind ctrl#start provision,thread:" + Thread.currentThread().getName() + "   macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
        TimeRecorder.mark("deviceProvision");
        int address2 = 0;
        if (processingDevice.isProvisionedAndNotKeyBind && (provisionedAndNotKeyBindCacheBean = cacheProvisionedAndNotKeyBindValueMap.get(processingDevice.nodeInfo.macAddress)) != null) {
            address2 = provisionedAndNotKeyBindCacheBean.meshAddress;
            if (logReporter != null) {
                logReporter.a(new AddDeviceStepBean("配网设备已provisionedNotKeybind,使用该设备上次的address:" + address2));
            }
        }
        if (address == 0) {
            address = new f().b(processingDevice.nodeInfo.macAddress);
            MeshLog.i("11111-" + processingDevice.nodeInfo.macAddress + "开始前:" + currentGeneratedMeshAddressMap);
            while (generateMeshAddressRetryCount <= maxRetry && currentGeneratedMeshAddressMap.containsValue(Integer.valueOf(address))) {
                MeshLog.e("11111-设备:" + processingDevice.nodeInfo.macAddress + " 生成的mesh地址:" + address + " 已经存在，重新生成");
                address = new f().b(processingDevice.nodeInfo.macAddress);
                generateMeshAddressRetryCount = generateMeshAddressRetryCount + 1;
            }
        }
        if (!currentGeneratedMeshAddressMap.containsValue(Integer.valueOf(address))) {
            MeshLog.i("11111-设备:" + processingDevice.nodeInfo.macAddress + " 生成的mesh地址:" + address + " 完成");
        }
        currentGeneratedMeshAddressMap.put(processingDevice.nodeInfo.macAddress, Integer.valueOf(address));
        ProvisionedAndNotKeyBindCacheBean provisionedAndNotKeyBindCacheBean2 = new ProvisionedAndNotKeyBindCacheBean();
        provisionedAndNotKeyBindCacheBean2.meshAddress = address;
        cacheProvisionedAndNotKeyBindValueMap.put(processingDevice.nodeInfo.macAddress, provisionedAndNotKeyBindCacheBean2);
        generateMeshAddressRetryCount = 0;
        if (this.MODE_CONFIG == 1) {
            sigMesh.getMeshInfo().increaseProvisionIndex(4);
            MeshInfo meshInfo = sigMesh.getMeshInfo();
            Context context = sigMesh.getContext();
            meshInfo.saveOrUpdate(context, "PRIVISION SUCCESS generateProvisionParams macAddress" + this.USE_FOR_NODE_MAC_ADDRESS);
        }
        MeshLog.d("alloc address: " + address + "   macAddress=" + processingDevice.nodeInfo.macAddress + "，protocolVer:" + processingDevice.nodeInfo.protocolVersion);
        if (!MeshUtils.r(address)) {
            if (logReporter != null) {
                logReporter.a(new AddDeviceStepBean("配网设备分配的短地址无效,address:" + address + ",配网失败"));
            }
            MeshLogger.a("invalid mesh address: " + address + "   macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
            return null;
        }
        this.currentBindingDevice = processingDevice;
        ProvisioningDevice provisioningDevice = new ProvisioningDevice(processingDevice.bluetoothDevice, processingDevice.nodeInfo.deviceUUID, address);
        provisioningDevice.v(processingDevice.oobInfo);
        provisioningDevice.w(processingDevice.rssi);
        provisioningDevice.u(processingDevice);
        processingDevice.state = NetworkingState.PROVISIONING;
        processingDevice.nodeInfo.offlineReset();
        processingDevice.addLog(NetworkingDevice.TAG_PROVISION, "action start -> 0x" + String.format(Locale.US, "%04X", new Object[]{Integer.valueOf(address)}));
        processingDevice.nodeInfo.meshAddress = address;
        b.d("分配短地址成功:" + address);
        byte[] oob = OOBData;
        if (oob != null) {
            provisioningDevice.k(oob);
        } else {
            provisioningDevice.l(SharedPreferenceHelper.isNoOOBEnable(sigMesh.getContext()));
        }
        ProvisioningParameters provisioningParameters = new ProvisioningParameters(provisioningDevice);
        MeshLogger.a("provisioning device: " + provisioningDevice.toString());
        return provisioningParameters;
    }

    public void onDestroy() {
        MeshEventHandler.getInstance().removeEventListener(this);
    }

    public void performed(Event<String> event) {
        String str = this.mMeshControllerHashCode;
        if (str.equals(event.getSender().hashCode() + "")) {
            if (event.getType().equals("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_PROVISION_BEGIN")) {
                onProvisionStart((ProvisioningEvent) event);
            } else if (event.getType().equals("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_PROVISION_SUCCESS")) {
                onProvisionSuccess((ProvisioningEvent) event);
            } else if (event.getType().equals("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_PROVISION_FAIL")) {
                onProvisionFail((ProvisioningEvent) event);
            } else if (event.getType().equals("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_BIND_SUCCESS")) {
                onKeyBindSuccess((BindingEvent) event);
            } else if (event.getType().equals("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_BIND_FAIL")) {
                onKeyBindFail((BindingEvent) event);
            } else if (event.getType().equals(ModelPublicationStatusMessage.class.getName())) {
                MeshLogger.a("pub setting status: " + ModelPublicationStatusMessage.class.getName() + "   macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
                if (!this.isPubSetting) {
                    return;
                }
            }
        }
        if (!event.getType().equals("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_DISCONNECTED")) {
            if (event.getType().equals(ModelPublicationStatusMessage.class.getName())) {
                ModelPublicationStatusMessage statusMessage = (ModelPublicationStatusMessage) ((StatusNotificationEvent) event).a().d();
                if (statusMessage.d() != ConfigStatus.SUCCESS.code) {
                    MeshLogger.d("publication err: " + statusMessage.d() + "   macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
                }
            } else if (event.getType().equals(NodeResetStatusMessage.class.getName())) {
                com.leedarson.serviceimpl.elkstrays.b.a("收到设备删除成功的回调通知:" + this);
                onKickOutFinish();
            }
        }
    }

    private void onProvisionFail(ProvisioningEvent event) {
        MeshLog.e("onProvisionFail:" + event.a() + "   macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
        this.resp.code = 403;
        this.resp.msg = "provision fail";
        this.isBinding.set(false);
        MeshBindCallback meshBindCallback = this.bindCallback;
        if (meshBindCallback != null) {
            meshBindCallback.onBindFail(this.resp.code, event.a());
        }
    }

    private void onProvisionSuccess(ProvisioningEvent event) {
        MeshLog.d("SUFUN onProvisionSuccess:" + event.a() + "   macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
        startBinding(event);
    }

    private void onProvisionStart(ProvisioningEvent event) {
        MeshLog.d("SUFUN.Bind.Ctrl.onProvisionStart:" + event.a() + "   macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
    }

    private void startBinding(ProvisioningEvent event) {
        SIGMesh sigMesh = SIGMesh.getInstance();
        ProvisioningDevice remote = event.b();
        NetworkingDevice pvDevice = this.currentBindingDevice;
        if (pvDevice == null) {
            MeshLogger.a("SUFUN. pv device not found when provision success   macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
            return;
        }
        pvDevice.state = NetworkingState.BINDING;
        pvDevice.addLog(NetworkingDevice.TAG_PROVISION, FirebaseAnalytics.Param.SUCCESS);
        NodeInfo nodeInfo = pvDevice.nodeInfo;
        int elementCnt = remote.d() != null ? remote.d().b : 0;
        int i = this.MODE_CONFIG;
        if (i == this.MODE_CONFIG_SINGAL_DEFAULT) {
            sigMesh.getMeshInfo().increaseProvisionIndex(elementCnt);
        } else if (i == 1) {
            elementCnt = 4;
        }
        nodeInfo.elementCnt = elementCnt;
        nodeInfo.deviceKey = remote.e();
        nodeInfo.netKeyIndexes.add(Integer.valueOf(sigMesh.getMeshInfo().getDefaultNetKey().index));
        synchronized (this) {
            sigMesh.getMeshInfo().insertDevice(nodeInfo);
            MeshInfo meshInfo = sigMesh.getMeshInfo();
            Context context = sigMesh.getContext();
            meshInfo.saveOrUpdate(context, " Starting Binding #insert Device, macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
        }
        boolean privateMode = SharedPreferenceHelper.isPrivateMode(sigMesh.getContext());
        boolean defaultBound = false;
        if (!privateMode || remote.f() == null) {
            b.d("privateMode:" + privateMode + ",uuid:" + remote.f());
        } else {
            PrivateDevice device = PrivateDevice.filter(remote.f());
            if (device != null) {
                MeshLogger.a("SUFUN private device   macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
                nodeInfo.compositionData = CompositionData.from(device.getCpsData());
                defaultBound = true;
            } else {
                MeshLogger.a("SUFUN private device null   macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
            }
        }
        nodeInfo.setDefaultBind(defaultBound);
        pvDevice.addLog(NetworkingDevice.TAG_BIND, "SUFUN action start,protocolVersion:" + nodeInfo.protocolVersion);
        BindingDevice bindingDevice = new BindingDevice(nodeInfo.meshAddress, nodeInfo.deviceUUID, sigMesh.getMeshInfo().getDefaultAppKeyIndex(), nodeInfo.protocolVersion);
        bindingDevice.k(defaultBound);
        bindingDevice.i(BindingBearer.GattOnly);
        if ("".equals(this.USE_FOR_NODE_MAC_ADDRESS)) {
            MeshService.k().x(new BindingParameters(bindingDevice));
        } else {
            MeshService.k().y(this.USE_FOR_NODE_MAC_ADDRESS, new BindingParameters(bindingDevice));
        }
    }

    private void onKeyBindFail(BindingEvent event) {
        NetworkingDevice networkingDevice = this.currentBindingDevice;
        if (networkingDevice == null || networkingDevice.nodeInfo == null) {
            MeshLog.d("bind fail:data: desc=>" + event.b() + "  eventType:" + ((String) event.getType()) + "   macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
        } else {
            MeshLog.d("SUFUN bind fail:" + this.currentBindingDevice.nodeInfo.macAddress + "   macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
        }
        String desc = event.b();
        this.resp.code = 401;
        this.resp.msg = "bind fail";
        if (!TextUtils.isEmpty(desc)) {
            BaseResp baseResp = this.resp;
            baseResp.msg = this.resp.msg + ":" + desc;
        }
        this.isBinding.set(false);
        MeshBindCallback meshBindCallback = this.bindCallback;
        if (meshBindCallback != null) {
            meshBindCallback.onBindFail(this.resp.code, this.resp.msg);
        }
    }

    private void onKeyBindSuccess(BindingEvent event) {
        MeshLog.d("SUFUN: bind onKeyBindSuccess:data: desc=>" + event.b() + "  eventType:" + ((String) event.getType()) + "   macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
        TimeRecorder.mark("deviceProvision");
        BindingDevice remote = event.a();
        NetworkingDevice pvDevice = this.currentBindingDevice;
        pvDevice.addLog(NetworkingDevice.TAG_BIND, FirebaseAnalytics.Param.SUCCESS);
        pvDevice.nodeInfo.bound = true;
        if (!remote.h()) {
            pvDevice.nodeInfo.compositionData = remote.c();
        }
        setAllPublish(pvDevice.nodeInfo);
        setTimePublish(pvDevice);
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        Context context = SIGMesh.getInstance().getContext();
        meshInfo.saveOrUpdate(context, "onKeyBindSuccess  macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
        MeshLog.d("bind success:" + pvDevice.nodeInfo.macAddress);
        this.resp.code = 200;
        this.isBinding.set(false);
        MeshBindCallback meshBindCallback = this.bindCallback;
        if (meshBindCallback != null) {
            meshBindCallback.onBindSuccess(pvDevice.nodeInfo.macAddress, pvDevice, event.b());
        }
    }

    private boolean setTimePublish(NetworkingDevice networkingDevice) {
        int modelId = MeshSigModel.SIG_MD_TIME_S.modelId;
        int pubEleAdr = networkingDevice.nodeInfo.getTargetEleAdr(modelId);
        if (pubEleAdr == -1) {
            return false;
        }
        ModelPublicationSetMessage publicationSetMessage = new ModelPublicationSetMessage(networkingDevice.nodeInfo.meshAddress, ModelPublication.createDefault(pubEleAdr, 65535, SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex(), WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, modelId, true));
        publicationSetMessage.D(-1);
        MeshMessagePool.getInstance().addAndSend(publicationSetMessage);
        return true;
    }

    public void unbind(NodeInfo dev, boolean retryDelete, MeshUnbindCallback unbindCallback2) {
        boolean z = true;
        this.offlineDelete = !SIGMesh.getInstance().hasConnected();
        StringBuilder sb = new StringBuilder();
        sb.append("删除设备 当前mesh网络处于 -- ");
        sb.append(this.offlineDelete ? "离线" : "在线---状态");
        sb.append(",macAddress=");
        sb.append(this.USE_FOR_NODE_MAC_ADDRESS);
        MeshLogNew.e(sb.toString());
        this.unbindCallback = unbindCallback2;
        this.currentUnbindDevice = dev;
        int currentNodeAddress = MeshService.k().i();
        if (!"".equals(this.USE_FOR_NODE_MAC_ADDRESS)) {
            currentNodeAddress = MeshService.k().j(this.USE_FOR_NODE_MAC_ADDRESS);
        }
        if (this.currentUnbindDevice.meshAddress != currentNodeAddress) {
            z = false;
        }
        this.kickDirect = z;
        if (!z) {
            MeshLog.i("删除非直连mesh设备 mac:" + dev.macAddress);
            this.delayHandler.postDelayed(this.kickoutTimeoutTask, KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
        } else {
            MeshLogNew.e("删除 主 节点设备 mac:" + dev.macAddress);
            MeshLog.i("移除直连设备mac:" + dev.macAddress + ",local addr:" + dev.meshAddress + "   macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
            this.delayHandler.postDelayed(this.kickoutTimeoutTask, GroupCtrlAdapter.RETRY_TIMEOUT);
        }
        MeshLogNew.e("发送 NodeResetMessage 删除mesh节点");
        NodeResetMessage nodeResetMessage = new NodeResetMessage(this.currentUnbindDevice.meshAddress);
        if (!retryDelete) {
            nodeResetMessage.B(NeedPermissionEvent.PER_ANDROID_S_BLE);
        }
        nodeResetMessage.D(-1);
        nodeResetMessage.c(new MsgExtra("删除mesh设备 mac:" + dev.macAddress + ",addr:" + dev.meshAddress, MeshConstants.TRACE_ID_CONTROL));
        MeshMessagePool.getInstance().addAndSend(nodeResetMessage);
    }

    private void onKickOutFinish() {
        String str;
        NodeInfo deviceInfo = this.currentUnbindDevice;
        if (deviceInfo != null && (str = deviceInfo.macAddress) != null && !"".equals(str)) {
            SqlManager.removeDelCacheNode(deviceInfo.macAddress);
            this.delayHandler.removeCallbacksAndMessages((Object) null);
            if ("".equals(this.USE_FOR_NODE_MAC_ADDRESS)) {
                MeshService.k().q(deviceInfo.meshAddress);
            } else {
                MeshService.k().r(this.USE_FOR_NODE_MAC_ADDRESS, deviceInfo.meshAddress);
            }
            SIGMesh.getInstance().getMeshInfo().removeDeviceByMeshAddress(deviceInfo.meshAddress);
            MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
            Context context = SIGMesh.getInstance().getContext();
            meshInfo.saveOrUpdate(context, "onKckoutFinish   macAddress=" + this.USE_FOR_NODE_MAC_ADDRESS);
            if (this.unbindCallback != null) {
                com.leedarson.serviceimpl.elkstrays.b.a("删除设备成功 success:" + deviceInfo.macAddress);
                this.unbindCallback.onUnBindSuccess(deviceInfo.macAddress, deviceInfo.meshAddress, true);
            }
        }
    }

    /* access modifiers changed from: private */
    public void onKickOutTimeout() {
        NodeInfo deviceInfo = this.currentUnbindDevice;
        if (deviceInfo != null) {
            SqlManager.saveDelCacheNode(deviceInfo.macAddress);
        }
        if (this.unbindCallback != null) {
            com.leedarson.serviceimpl.elkstrays.b.a("删除设备成功 timeout:" + deviceInfo.macAddress);
            this.unbindCallback.onUnBindSuccess(deviceInfo.macAddress, deviceInfo.meshAddress, false);
        }
        if (this.offlineDelete) {
            this.delayHandler.postDelayed(new Runnable() {
                public void run() {
                    c.f("删除离线设备成功(timeout), autoConnect request");
                    SIGMesh.getInstance().autoConnect();
                }
            }, 200);
        }
        a t = a.y(this).x(MeshConstants.TRACE_ID_REMOVE_DEVICE).u(NotificationCompat.CATEGORY_EVENT, "removeDevice").u("meshstate", this.offlineDelete ? "离线" : "在线").u("mac", deviceInfo.macAddress).c(BindCtrl.class.getSimpleName()).o(FirebaseAnalytics.Param.SUCCESS).t("LdsBleMesh");
        t.p("bleMesh 删除设备:" + deviceInfo.macAddress + ",mesh离线状态").a().b();
    }

    private void setAllPublish(NodeInfo deviceInfo) {
    }
}
