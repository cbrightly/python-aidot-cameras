package meshsdk;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.alibaba.android.arouter.launcher.a;
import com.google.android.libraries.places.api.model.PlaceTypes;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.utils.w;
import com.leedarson.bean.IRhyDevice;
import com.leedarson.serviceimpl.base.c;
import com.leedarson.serviceimpl.elkstrays.b;
import com.leedarson.serviceimpl.reporters.k;
import com.leedarson.serviceimpl.strategys.e;
import com.leedarson.serviceinterface.BleMeshService;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.event.EventCheckSystemGPSStatue;
import com.leedarson.serviceinterface.event.IBluetoothEnableStatueChange;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.generic.OnOffGetMessage;
import com.telink.ble.mesh.entity.Scheduler;
import com.telink.ble.mesh.entity.Smart;
import com.telink.ble.mesh.foundation.MeshController;
import com.telink.ble.mesh.foundation.MeshService;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import meshsdk.cache.cachemodule.MultiPropertyCacheInstance;
import meshsdk.callback.MeshBindCallback;
import meshsdk.callback.MeshCustomcmdCallback;
import meshsdk.callback.MeshGlobalCallback;
import meshsdk.callback.MeshGroupCallback;
import meshsdk.callback.MeshGroupCallbackWrapper;
import meshsdk.callback.MeshOTACallback;
import meshsdk.callback.MeshScanCallback;
import meshsdk.callback.MeshSceneCallback;
import meshsdk.callback.MeshScheduleCallback;
import meshsdk.callback.MeshSimpleCmdSetCallback;
import meshsdk.callback.MeshUnbindCallback;
import meshsdk.callback.OnHttpCallback;
import meshsdk.callback.OnPermissionListener;
import meshsdk.ctrl.BindCtrl;
import meshsdk.ctrl.CmdCtrl;
import meshsdk.ctrl.ControlDevIntercepter;
import meshsdk.ctrl.CtrlLifecycle;
import meshsdk.ctrl.DetectModeCtrlAdapter;
import meshsdk.ctrl.EffectLinkageCtrlAdapter;
import meshsdk.ctrl.EffectModeCtrlAdapter;
import meshsdk.ctrl.ExceptionCtrl;
import meshsdk.ctrl.GroupCtrlAdapter;
import meshsdk.ctrl.MeshMessagePool;
import meshsdk.ctrl.OtaCtrl;
import meshsdk.ctrl.PublishCtrl;
import meshsdk.ctrl.ScanCtrl;
import meshsdk.ctrl.SceneCtrlAdapter;
import meshsdk.ctrl.ScheduleCtrlAdapter;
import meshsdk.ctrl.SmartCtrlAdapter;
import meshsdk.ctrl.SyncCtrl;
import meshsdk.datamgr.LDSDeviceApi;
import meshsdk.datamgr.LDSGroupApi;
import meshsdk.model.CustomScene;
import meshsdk.model.GroupInfo;
import meshsdk.model.MeshInfo;
import meshsdk.model.NetworkingDevice;
import meshsdk.model.NodeInfo;
import meshsdk.model.Scene;
import meshsdk.model.json.AddDevicesBean;
import meshsdk.model.json.BatteryPropertyBean;
import meshsdk.model.json.CloudDevice;
import meshsdk.model.json.CloudGroup;
import meshsdk.model.json.CustomEffectMode;
import meshsdk.model.json.DSTRule;
import meshsdk.model.json.DetectMode;
import meshsdk.model.json.EnergySaveMode;
import meshsdk.model.json.MultiPropertyData;
import meshsdk.model.json.RhythmStopAttrBean;
import meshsdk.model.json.RoutineRule;
import meshsdk.model.json.SecuAlarmRule;
import meshsdk.sql.SqlManager;
import meshsdk.strategy.group.CustomGroupStrategy;
import meshsdk.strategy.group.MeshGroupStrategy;
import meshsdk.strategy.scene.CustomSceneStrategy;
import meshsdk.strategy.scene.MeshSceneStrategy;
import meshsdk.util.BleCompat;
import meshsdk.util.LDSMeshUtil;
import meshsdk.util.MeshConstants;
import meshsdk.util.ProcedureCollector;
import meshsdk.util.SharedPreferenceHelper;
import meshsdk.util.TimeRecorder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SIGMesh {
    public static int NEW_PROTOCOL = 2;
    public static int NEW_PROTOCOL3 = 3;
    public static int NEW_PROTOCOL4 = 4;
    public static int NEW_PROTOCOL5 = 5;
    public static int NEW_PROTOCOL6 = 6;
    public static int NEW_PROTOCOL7 = 7;
    public static String localIntVersion = "1013";
    public static String localVersion = "1.0.13_0228";
    private static SIGMesh mInstance = null;
    public final String MAC_KEY = "mac";
    public final String OOBDataKEY = "OOBData";
    private ControlDevIntercepter controlDevIntercepter;
    private CopyOnWriteArrayList<CtrlLifecycle> ctrlList;
    /* access modifiers changed from: private */
    public CustomGroupStrategy customGroupStrategy;
    private CustomSceneStrategy customSceneStrategy;
    private DetectModeCtrlAdapter detectModeCtrlAdapter;
    private EffectModeCtrlAdapter effectModeCtrlAdapter;
    private ExceptionCtrl exceptionCtrl;
    /* access modifiers changed from: private */
    public ExecutorService executorService = l.i(2, "mesh-exe-task", 10);
    private MeshGlobalCallback globalCallback;
    private GroupCtrlAdapter groupCtrlAdapter;
    private Handler handler = new Handler(Looper.getMainLooper());
    private HandlerThread handlerThread;
    private AtomicBoolean hasConnected = new AtomicBoolean(false);
    private AtomicBoolean hasLastConnected = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public boolean isBTOpening = false;
    boolean isNew = true;
    String lastDirectMac;
    public String lastHouseId;
    private EffectLinkageCtrlAdapter linkageCtrlAdapter;
    private BindCtrl mBindCtrl;
    private Cache mCache = new Cache();
    private CmdCtrl mCmdCtrl;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public volatile MeshInfo mMeshInfo;
    private ScanCtrl mScancCtrl;
    private SyncCtrl mSyncCtrl;
    private MeshGroupStrategy meshGroupStrategy;
    private MeshSceneStrategy meshSceneStrategy;
    private final int nodeAckCheck_Time_Limit = 120000;
    /* access modifiers changed from: private */
    public OnPermissionListener onPermissionListener;
    private OtaCtrl otaCtrl;
    private PublishCtrl publishCtrl;
    private SceneCtrlAdapter sceneCtrl;
    byte scheduleIndex = 0;
    private ScheduleCtrlAdapter schedulerCtrl;
    private SmartCtrlAdapter smartCtrlAdapter;
    public Map<String, Boolean> supportAsyncRhyMap = new HashMap();

    public boolean hasConnected() {
        return this.hasConnected.get();
    }

    public void setHasLastConnected(boolean hasLastConnected2) {
        this.hasLastConnected.set(hasLastConnected2);
    }

    public boolean hasLastConnected() {
        return this.hasLastConnected.get();
    }

    public void setConnected(boolean conn) {
        MeshLog.e("SIGMesh#setConnected:" + conn);
        this.hasConnected.set(conn);
    }

    public static SIGMesh getInstance() {
        if (mInstance == null) {
            synchronized (SIGMesh.class) {
                if (mInstance == null) {
                    mInstance = new SIGMesh();
                }
            }
        }
        return mInstance;
    }

    public void setGlobalCallback(MeshGlobalCallback globalCallback2) {
        this.globalCallback = globalCallback2;
        SyncCtrl syncCtrl = this.mSyncCtrl;
        if (syncCtrl != null) {
            syncCtrl.setGlobalCallback(globalCallback2);
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public void executorTask(Runnable runnable) {
        ExecutorService executorService2 = this.executorService;
        if (executorService2 != null && !executorService2.isShutdown()) {
            this.executorService.execute(runnable);
        }
    }

    public void executorTask(final Runnable runnable, long delay) {
        this.handler.postDelayed(new Runnable() {
            public void run() {
                SIGMesh.this.executorService.execute(runnable);
            }
        }, delay);
    }

    public SyncCtrl getSyncCtrl() {
        return this.mSyncCtrl;
    }

    public Cache getCache() {
        return this.mCache;
    }

    public MeshInfo getMeshInfo() {
        if (this.mMeshInfo == null) {
            this.mMeshInfo = MeshInfo.createNewMesh(this.mContext);
            MeshLogNew.meshJsonLog("构建新的meshinfo");
        }
        return this.mMeshInfo;
    }

    public void printNodeInfo() {
        MeshInfo meshInfo = getMeshInfo();
        MeshLogNew.meshJsonLog("上传meshjson node节点数:" + meshInfo.nodes.size());
        for (NodeInfo nodeInfo : meshInfo.nodes) {
            MeshLogNew.meshJsonLog("节点:" + nodeInfo.macAddress);
        }
    }

    public void setMeshInfo(MeshInfo mMeshInfo2) {
        this.mMeshInfo = mMeshInfo2;
    }

    public MeshGlobalCallback getGlobalCallback() {
        return this.globalCallback;
    }

    public HandlerThread getHandlerThread() {
        return this.handlerThread;
    }

    public SIGMesh initSDK(Context ctx) {
        this.mContext = ctx;
        HandlerThread handlerThread2 = new HandlerThread("SIGMeshHandlerThread");
        this.handlerThread = handlerThread2;
        handlerThread2.start();
        c.k().l(this.handlerThread);
        CopyOnWriteArrayList<CtrlLifecycle> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        this.ctrlList = copyOnWriteArrayList;
        PublishCtrl publishCtrl2 = new PublishCtrl(this);
        this.publishCtrl = publishCtrl2;
        copyOnWriteArrayList.add(publishCtrl2);
        CopyOnWriteArrayList<CtrlLifecycle> copyOnWriteArrayList2 = this.ctrlList;
        ScanCtrl scanCtrl = new ScanCtrl(this);
        this.mScancCtrl = scanCtrl;
        copyOnWriteArrayList2.add(scanCtrl);
        CopyOnWriteArrayList<CtrlLifecycle> copyOnWriteArrayList3 = this.ctrlList;
        BindCtrl bindCtrl = new BindCtrl(this.publishCtrl);
        this.mBindCtrl = bindCtrl;
        copyOnWriteArrayList3.add(bindCtrl);
        CopyOnWriteArrayList<CtrlLifecycle> copyOnWriteArrayList4 = this.ctrlList;
        CmdCtrl cmdCtrl = new CmdCtrl(this);
        this.mCmdCtrl = cmdCtrl;
        copyOnWriteArrayList4.add(cmdCtrl);
        CopyOnWriteArrayList<CtrlLifecycle> copyOnWriteArrayList5 = this.ctrlList;
        SyncCtrl syncCtrl = new SyncCtrl(this, this.mCmdCtrl);
        this.mSyncCtrl = syncCtrl;
        copyOnWriteArrayList5.add(syncCtrl);
        CopyOnWriteArrayList<CtrlLifecycle> copyOnWriteArrayList6 = this.ctrlList;
        SceneCtrlAdapter sceneCtrlAdapter = new SceneCtrlAdapter(this);
        this.sceneCtrl = sceneCtrlAdapter;
        copyOnWriteArrayList6.add(sceneCtrlAdapter);
        CopyOnWriteArrayList<CtrlLifecycle> copyOnWriteArrayList7 = this.ctrlList;
        ExceptionCtrl exceptionCtrl2 = new ExceptionCtrl(this);
        this.exceptionCtrl = exceptionCtrl2;
        copyOnWriteArrayList7.add(exceptionCtrl2);
        CopyOnWriteArrayList<CtrlLifecycle> copyOnWriteArrayList8 = this.ctrlList;
        GroupCtrlAdapter groupCtrlAdapter2 = new GroupCtrlAdapter(this, this.handlerThread);
        this.groupCtrlAdapter = groupCtrlAdapter2;
        copyOnWriteArrayList8.add(groupCtrlAdapter2);
        CopyOnWriteArrayList<CtrlLifecycle> copyOnWriteArrayList9 = this.ctrlList;
        SmartCtrlAdapter smartCtrlAdapter2 = new SmartCtrlAdapter(this, this.mCmdCtrl);
        this.smartCtrlAdapter = smartCtrlAdapter2;
        copyOnWriteArrayList9.add(smartCtrlAdapter2);
        this.effectModeCtrlAdapter = new EffectModeCtrlAdapter(this.mCmdCtrl, this.handlerThread);
        this.linkageCtrlAdapter = new EffectLinkageCtrlAdapter(this.mCmdCtrl, this.handlerThread);
        this.controlDevIntercepter = new ControlDevIntercepter(this.mCmdCtrl, this.handlerThread);
        this.detectModeCtrlAdapter = new DetectModeCtrlAdapter(this.mCmdCtrl, this.handlerThread);
        MeshMessagePool.init();
        MeshService.k().o(ctx, MeshEventHandler.getInstance());
        BindCtrl bindCtrl2 = this.mBindCtrl;
        bindCtrl2.mMeshControllerHashCode = MeshService.k().h() + "";
        MeshService.k().s(false);
        k.a("111222应用启动，加载mesh相关配置");
        reloadMeshConfig();
        MeshLogNew.meshJsonLog("111222 检测bluetoothstate");
        MeshService.k().c();
        this.meshSceneStrategy = new MeshSceneStrategy(this.mContext, this.sceneCtrl);
        this.customSceneStrategy = new CustomSceneStrategy(this.mContext, this.mCmdCtrl);
        this.meshGroupStrategy = new MeshGroupStrategy(this.mContext, this.groupCtrlAdapter);
        this.customGroupStrategy = new CustomGroupStrategy(this.mContext);
        PrintStream printStream = System.out;
        printStream.println("SIGMesh init sdk finish" + localVersion);
        return this;
    }

    public void reloadMeshConfig() {
        MeshLog.i("reloadMeshConfig");
        initMesh();
        MeshService.k().w(this.mMeshInfo.convertToConfiguration());
    }

    private void initMesh() {
        String houseId = SharedPreferenceHelper.getHouseId(this.mContext);
        MeshLogNew.meshJsonLog("初始化mesh,houseId:" + houseId);
        Object[] objArr = ConfigUtil.readAsObject(this.mContext, houseId);
        int code = ((Integer) objArr[0]).intValue();
        Object configObj = objArr[1];
        if (configObj == null) {
            String endTxt = code == -1 ? "失败" : "为空";
            k.a("initMesh 读取本地mesh持久化文件:" + endTxt + ",创建一个MeshInfo");
            this.mMeshInfo = MeshInfo.createNewMesh(this.mContext);
            this.mMeshInfo.saveOrUpdate(this.mContext, "SigMesh.java.initMesh");
            if (code == -1) {
                k.a("initMesh 读取本地mesh持久化文件失败 从云端获取meshjson");
                ((BleMeshService) a.c().g(BleMeshService.class)).forceUpdateConfig();
            }
        } else {
            this.mMeshInfo = (MeshInfo) configObj;
            k.a("从持久化文件中，读取meshinfo成功:" + this.mMeshInfo.toString());
        }
        b.a("initMeshInfo:" + this.mMeshInfo.toString());
        for (NodeInfo deviceInfo : this.mMeshInfo.nodes) {
            deviceInfo.setOnOff(-1);
            deviceInfo.lum = 0;
            deviceInfo.temp = 0;
        }
        if (MeshService.k().l() != null) {
            MeshService.k().l().P1();
        }
    }

    public SIGMesh enableLog(boolean enable) {
        MeshLog.isPrint = enable;
        MeshLog.d("is debug?:" + enable);
        return this;
    }

    public JSONObject startScan(MeshScanCallback scanCallback) {
        if (!checkPermission("bleStartScan")) {
            return BaseResp.generatorFailResp(401, BaseResp.DESC_NO_PERMISSION);
        }
        this.mScancCtrl.setScanCallback(scanCallback);
        this.mScancCtrl.scan();
        return BaseResp.generatorSuccessResp();
    }

    public JSONObject stopScan(String fromBz) {
        this.mScancCtrl.stopScan(fromBz);
        return BaseResp.generatorSuccessResp();
    }

    @Deprecated
    public void addDevice(String mac, byte[] OOBData, MeshBindCallback bindCallback) {
        getInstance().stopScan("SIGMesh addDevice");
        if (bindCallback == null) {
            throw new IllegalArgumentException("bindCallback could not be null");
        } else if (MeshService.k().f() == MeshController.Mode.MODE_SCAN) {
            MeshLog.e("add devices fail,you need stop scanning devices before adding device");
            bindCallback.onBindFail(401, "add devices fail,you need stop scanning devices before adding device");
        } else {
            NetworkingDevice dev = getCache().findDevice(mac);
            if (dev == null) {
                String errMsg = String.format(Locale.US, "could not find device by mac->:%s", new Object[]{mac});
                MeshLog.e(errMsg);
                bindCallback.onBindFail(401, errMsg);
                return;
            }
            SqlManager.removeDelCacheNode(mac);
            this.mBindCtrl.setBindCallback(bindCallback);
            this.mBindCtrl.startProvision(dev, OOBData);
        }
    }

    public void addDevices(List<AddDevicesBean> devices, ArrayList<e.b> meshBindCallbacks) {
        HashMap<String, Integer> currentGeneratedMeshAddressMap = new HashMap<>();
        for (int i = 0; i < devices.size(); i++) {
            AddDevicesBean addDeviceBean = devices.get(i);
            preProcess(addDeviceBean.getMac());
            PublishCtrl tempPublishCtrl = new PublishCtrl(this);
            BindCtrl tempBindCtrl = new BindCtrl(tempPublishCtrl);
            addDeviceBean.mBindCtrl = tempBindCtrl;
            Objects.requireNonNull(tempBindCtrl);
            tempBindCtrl.MODE_CONFIG = 1;
            tempBindCtrl.setBindCallback(meshBindCallbacks.get(i));
            MeshLogNew.i("11111-配网前数据准备");
            addDeviceBean.mProvisionParams = tempBindCtrl.generateProvisionParams(addDeviceBean.getTrackReport(), currentGeneratedMeshAddressMap, 40, addDeviceBean.networkingDevice, MeshUtils.p(addDeviceBean.getOObData()));
            addDeviceBean.mPublishCtrl = tempPublishCtrl;
            addDeviceBean.mBindCtrl.USE_FOR_NODE_MAC_ADDRESS = addDeviceBean.networkingDevice.bluetoothDevice.getAddress();
        }
        MeshService.k().n(true, "开始进行配网");
        MeshService.k().A(devices);
    }

    private void preProcess(String tempMac) {
        SqlManager.removeDelCacheNode(tempMac);
        LDSMeshUtil.removeInvalidNode(getInstance().getMeshInfo().nodes, tempMac);
        this.customGroupStrategy.removeMemberByMac(tempMac);
        this.customSceneStrategy.removeMemberByMac(tempMac);
    }

    public void removeDevice(String mac, boolean retryDelete, MeshUnbindCallback unbindCallback) {
        NodeInfo node = null;
        Iterator<NodeInfo> it = getMeshInfo().nodes.iterator();
        while (true) {
            if (it.hasNext()) {
                NodeInfo n = it.next();
                if (n != null && mac.equalsIgnoreCase(n.macAddress)) {
                    node = n;
                    break;
                }
            } else {
                break;
            }
        }
        if (node == null) {
            String errMsg = String.format(Locale.US, "could not find device by mac in meshInfo cache->:%s", new Object[]{mac});
            MeshLog.e(errMsg);
            SqlManager.removeDelCacheNode(mac);
            unbindCallback.onUnBindFail(401, errMsg);
            return;
        }
        this.mBindCtrl.unbind(node, retryDelete, unbindCallback);
    }

    public void autoConnect() {
        if (this.mMeshInfo == null || this.mMeshInfo.nodes == null || this.mMeshInfo.nodes.size() == 0 || !onlyCheckPermission()) {
            MeshLog.e("autoConnect nodes size = 0");
            b.a("执行mesh自动连接失败,mMeshInfo.nodes为空，或者是还未获取定位权限");
        } else if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            autoConnectWithGps();
        } else if (Constans.isDidRender && !this.isBTOpening) {
            new LDSDeviceApi().getCloudDevices(SharedPreferenceHelper.getHouseId(getInstance().getContext()), new OnHttpCallback<List<CloudDevice>>() {
                public void onResult(List<CloudDevice> cloudDevices) {
                    if (cloudDevices != null) {
                        for (CloudDevice cloudDevice : cloudDevices) {
                            if (cloudDevice.isMeshHub()) {
                                MeshLog.i("有mesh网关, 不请求开启ble蓝牙权限");
                                return;
                            }
                        }
                        SIGMesh.this.requestBluetooth();
                        return;
                    }
                    SIGMesh.this.requestBluetooth();
                }
            });
        }
    }

    public void requestBluetooth() {
        NeedPermissionEvent tempPermision = new NeedPermissionEvent(24, "");
        tempPermision.mBluetoothOpenHandler = new IBluetoothEnableStatueChange() {
            public void onOpenSuccess() {
                MeshLog.e("蓝牙开启成功");
                boolean unused = SIGMesh.this.isBTOpening = false;
                b.a("蓝牙权限已打开");
                SIGMesh.this.autoConnectWithGps();
            }

            public void onOpenFail() {
                MeshLog.e("蓝牙开启失败");
                boolean unused = SIGMesh.this.isBTOpening = false;
                b.a("蓝牙服务开启失败");
                OnPermissionListener unused2 = SIGMesh.this.onPermissionListener;
            }
        };
        this.isBTOpening = true;
        org.greenrobot.eventbus.c.c().l(tempPermision);
    }

    /* access modifiers changed from: private */
    public void autoConnectWithGps() {
        MeshLogNew.v("autoConnect request --- 进入autoConnectWithGps");
        List<NodeInfo> list = this.mMeshInfo.nodes;
        b.a("开始检测GPS权限");
        if (!getInstance().hasConnected()) {
            b.a("二次确认Mesh主节点mesh状态:（未连接）");
            ProcedureCollector.startCollect(MeshConstants.TRACE_ID_AUTO_CONNECT, 1);
        }
        if (!w.f(this.mContext)) {
            b.a("发现GPS权限未打开");
        }
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            b.a("发现蓝牙权限未打开");
        }
        EventCheckSystemGPSStatue checkGpsEvent = new EventCheckSystemGPSStatue();
        checkGpsEvent.mHandler = new EventCheckSystemGPSStatue.IOnCheckSystemCheckStatueResultCallback() {
            public void onGPSStatueCallBack(int openStatue) {
                if (openStatue != 1) {
                    b.a("GPS权限打开成功,openStatue=" + openStatue);
                } else if (SIGMesh.this.mMeshInfo.nodes == null || SIGMesh.this.mMeshInfo.nodes.size() <= 0 || !SIGMesh.this.checkPermission("bleMeshConnect")) {
                    b.a("未授权定位权限，无法使用蓝牙功能,openStatue=" + openStatue);
                } else {
                    b.a("GPS权限打开成功, autoConnect request");
                    SIGMesh.this.autoConnectWithoutDialog();
                }
            }
        };
        if (this.mMeshInfo.nodes == null || this.mMeshInfo.nodes.size() <= 0) {
            b.a("mesh设备节点为空，不发起连接");
        } else if (w.R()) {
            checkGpsEvent.mHandler.onGPSStatueCallBack(1);
        } else {
            org.greenrobot.eventbus.c.c().l(checkGpsEvent);
        }
    }

    public void autoConnectWithoutDialog() {
        this.mSyncCtrl.autoConnect(this.globalCallback);
    }

    public void HSLSync(String mac) {
        this.mSyncCtrl.AllStateSync(LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac));
    }

    public JSONObject deviceElementInfo(String mac) {
        NodeInfo findMeshNode = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        return BaseResp.generatorSuccessResp();
    }

    public JSONObject devices() {
        List<NodeInfo> nodes = this.mMeshInfo.nodes;
        JSONArray arr = new JSONArray();
        for (NodeInfo nodeInfo : nodes) {
            JSONObject item = new JSONObject();
            try {
                item.put("name", (Object) "");
                item.put(PlaceTypes.ADDRESS, nodeInfo.meshAddress);
                item.put("mac", (Object) nodeInfo.macAddress);
                item.put(MeshConstants.AC_STATE_LOGIN_SUCCESS, nodeInfo.getOnOff() == -1 ? 0 : 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            arr.put((Object) item);
        }
        return BaseResp.generatorSuccessResp(arr);
    }

    public JSONObject getDeviceStatus(List<String> macList) {
        JSONArray arr = new JSONArray();
        boolean addAll = macList.size() == 0;
        for (NodeInfo nodeInfo : this.mMeshInfo.nodes) {
            if (addAll) {
                arr.put((Object) LDSMeshUtil.createNodeDetailStatusJson(nodeInfo));
            } else if (macList.contains(nodeInfo.macAddress)) {
                arr.put((Object) LDSMeshUtil.createNodeDetailStatusJson(nodeInfo));
            }
        }
        return BaseResp.generatorSuccessResp(arr);
    }

    public JSONObject controlDevice(String mac, int modelId, Object value) {
        getInstance().preStopRhythm(mac, IRhyDevice.TYPE_BLE_MESH, "controlDevice");
        if (TextUtils.isEmpty(mac)) {
            return allControl(modelId, value);
        }
        return controlDevice(LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac), modelId, value);
    }

    public JSONObject controlDevice(NodeInfo node, int modelId, Object value) {
        if (node == null || node.meshAddress == -1) {
            return BaseResp.generatorFailResp(401, "could not find devices node=" + node);
        }
        this.controlDevIntercepter.controlDevice(node, modelId, value, true);
        return BaseResp.generatorSuccessResp();
    }

    public boolean checkPermission(String bleAction) {
        if (BleCompat.checkNeededPermission(this.mContext)) {
            return true;
        }
        org.greenrobot.eventbus.c.c().l(new NeedPermissionEvent(9, bleAction));
        return false;
    }

    public boolean onlyCheckPermission() {
        return BleCompat.checkNeededPermission(this.mContext);
    }

    public void disconnectAndIdle(String reason) {
        MeshService.k().n(true, reason);
    }

    public void release() {
        CopyOnWriteArrayList<CtrlLifecycle> copyOnWriteArrayList = this.ctrlList;
        if (copyOnWriteArrayList != null) {
            Iterator<CtrlLifecycle> iterator = copyOnWriteArrayList.iterator();
            while (iterator.hasNext()) {
                CtrlLifecycle item = iterator.next();
                if (item != null) {
                    item.onDestroy();
                    this.ctrlList.remove(item);
                }
            }
            MeshService.k().d();
            MeshMessagePool.getInstance().shutDown();
            ExecutorService executorService2 = this.executorService;
            if (executorService2 != null && !executorService2.isShutdown()) {
                this.executorService.shutdown();
            }
            HandlerThread handlerThread2 = this.handlerThread;
            if (handlerThread2 != null) {
                handlerThread2.quit();
            }
        }
    }

    public JSONArray groups() {
        List<GroupInfo> groups = this.mMeshInfo.groups;
        JSONArray array = new JSONArray();
        for (GroupInfo g : groups) {
            JSONObject object = new JSONObject();
            try {
                object.put("groupId", g.groupId);
                List<NodeInfo> nodeInfos = LDSMeshUtil.getDevicesInGroup(g.address);
                JSONArray devArr = new JSONArray();
                for (NodeInfo n : nodeInfos) {
                    devArr.put((Object) n.macAddress);
                }
                object.put("devices", (Object) devArr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put((Object) object);
        }
        return array;
    }

    public synchronized int addGroup(int groupId, String groupType) {
        return this.meshGroupStrategy.addGroup(groupId, groupType);
    }

    public synchronized JSONObject removeGroup(int groupId) {
        if (this.customGroupStrategy.isExist(groupId)) {
            this.customGroupStrategy.removeGroup(groupId);
        }
        return this.meshGroupStrategy.removeGroup(groupId);
    }

    public synchronized void addGroupMember(final int groupId, final String macAddress, final MeshGroupCallbackWrapper callbackWrapper) {
        MeshGroupStrategy meshGroupStrategy2 = this.meshGroupStrategy;
        if (meshGroupStrategy2 == null) {
            MeshLog.e("addGroupMember 出问题了,meshGroupStrateg怎么是空的");
        } else {
            meshGroupStrategy2.addGroupMember(groupId, macAddress, new MeshGroupCallback() {
                String failMsg = "";

                public void onSuccess(int meshAddr, int groupAddr, int retryCount) {
                    if (!TextUtils.isEmpty(this.failMsg)) {
                        callbackWrapper.onDegradeToLocalGroup(this.failMsg);
                    }
                    callbackWrapper.onSuccess(meshAddr, groupAddr, retryCount);
                }

                public void onFail(int code, String msg, int meshAddr, int groupAddr, int retryCount) {
                    this.failMsg = msg;
                    GroupInfo group = LDSMeshUtil.findGroup(SIGMesh.this.mMeshInfo.groups, groupId);
                    if (group == null || !GroupInfo.TYPE_MUSIC.equals(group.groupType)) {
                        MeshLog.d("addGroupMember fail:" + code + ",msg:" + msg + ",add to local group");
                        SIGMesh.this.customGroupStrategy.addGroupMember(groupId, macAddress, this);
                        return;
                    }
                    MeshLog.d("addGroupMember music group member fail:" + code + ",msg:" + msg);
                    callbackWrapper.onFail(code, msg, meshAddr, groupAddr, retryCount);
                }
            });
        }
    }

    public synchronized void removeCustomGroupMember(int groupId, String macAddress, MeshGroupCallback groupCallback) {
        this.customGroupStrategy.removeGroupMember(groupId, macAddress, groupCallback);
    }

    public synchronized void removeGroupMember(int groupId, String macAddress, final MeshGroupCallback groupCallback) {
        this.customGroupStrategy.removeGroupMember(groupId, macAddress, (MeshGroupCallback) null);
        this.meshGroupStrategy.removeGroupMember(groupId, macAddress, new MeshGroupCallback() {
            public void onSuccess(int meshAddr, int groupAddr, int retryCount) {
                groupCallback.onSuccess(meshAddr, groupAddr, retryCount);
            }

            public void onFail(int code, String msg, int meshAddr, int groupAddr, int retryCount) {
                groupCallback.onFail(code, msg, meshAddr, groupAddr, retryCount);
            }
        });
    }

    public synchronized void removeGroupMember2(String macAddress, int groupAddress, final MeshGroupCallback groupCallback) {
        this.meshGroupStrategy.removeGroupMember2(macAddress, groupAddress, new MeshGroupCallback() {
            public void onSuccess(int meshAddr, int groupAddr, int retryCount) {
                groupCallback.onSuccess(meshAddr, groupAddr, retryCount);
            }

            public void onFail(int code, String msg, int meshAddr, int groupAddr, int retryCount) {
                groupCallback.onFail(code, msg, meshAddr, groupAddr, retryCount);
            }
        });
    }

    public JSONObject controlGroup(final int groupId, final int modelId, final Object value) {
        List<String> customGroupNodes = LDSMeshUtil.getDevicesInCustomGroup(groupId);
        boolean noGroup = false;
        boolean groupNoDevice = customGroupNodes == null || customGroupNodes.size() == 0;
        StringBuilder sb = new StringBuilder();
        sb.append("controlGroup:");
        sb.append(groupId);
        sb.append(" 本地组下有设备?");
        sb.append(!groupNoDevice);
        MeshLog.e(sb.toString());
        if ((!this.customGroupStrategy.isExist(groupId) || groupNoDevice) && !this.meshGroupStrategy.isExist(groupId)) {
            noGroup = true;
        }
        if (noGroup) {
            MeshLog.e("controlGroup 组丢失:" + groupId + ",尝试从云端中获取组，下发单控指令");
            controlGroupFixLossGroup(groupId, modelId, value);
            return new JSONObject();
        } else if (this.customGroupStrategy.isExist(groupId)) {
            MeshLogNew.i("组控-存在本地组:" + groupId + ",modelId:" + modelId + ",value:" + value);
            this.meshGroupStrategy.controlGroup(groupId, modelId, value);
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    SIGMesh.this.customGroupStrategy.controlGroup(groupId, modelId, value);
                }
            }, 240);
            return BaseResp.generatorSuccessResp();
        } else {
            MeshLogNew.i("组控-走mesh标准组:" + groupId + ",modelId:" + modelId + ",value:" + value);
            return this.meshGroupStrategy.controlGroup(groupId, modelId, value);
        }
    }

    public void controlGroupFixLossGroup(final int groupId, final int modelId, final Object value) {
        new LDSGroupApi().getVisibleGroups().b0(l.g).J(io.reactivex.android.schedulers.a.a()).X(new io.reactivex.functions.e<List<CloudGroup>>() {
            public void accept(List<CloudGroup> cloudGroups) {
                if (cloudGroups != null) {
                    MeshLog.i("组丢失，获取云端组列表成功");
                    for (final CloudGroup cloudGroup : cloudGroups) {
                        if (cloudGroup.getId() == groupId && cloudGroup.getDeviceIds() != null && cloudGroup.getDeviceIds().size() > 0) {
                            String houseId = SharedPreferenceHelper.getHouseId(BaseApplication.b());
                            MeshLog.i("组丢失，获取云端组列表成功，匹配到组，获取家下的设备列表");
                            new LDSDeviceApi().getCloudDevices(houseId, new OnHttpCallback<List<CloudDevice>>() {
                                public void onResult(List<CloudDevice> cloudDevices) {
                                    NodeInfo nodeInfo;
                                    MeshLog.i("组丢失，获取云端组列表成功，匹配到组，获取家下的设备列表-成功");
                                    if (cloudDevices != null) {
                                        for (String deviceId : cloudGroup.getDeviceIds()) {
                                            for (CloudDevice cloudDevice : cloudDevices) {
                                                if (cloudDevice.id.equals(deviceId) && (nodeInfo = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, cloudDevice.mac)) != null && nodeInfo.isOnline()) {
                                                    MeshLog.i("组丢失，设备:" + nodeInfo.macAddress + "加到本地组:" + groupId);
                                                    SIGMesh.this.customGroupStrategy.addGroup(groupId, GroupInfo.TYPE_NORMAL);
                                                    SIGMesh.this.customGroupStrategy.addGroupMember(groupId, nodeInfo.macAddress, (MeshGroupCallback) null);
                                                }
                                            }
                                        }
                                    }
                                    if (SIGMesh.this.customGroupStrategy.isExist(groupId)) {
                                        MeshLogNew.i("组丢失-组控-存在本地组:" + groupId + ",modelId:" + modelId + ",value:" + value);
                                        CustomGroupStrategy access$500 = SIGMesh.this.customGroupStrategy;
                                        AnonymousClass9 r1 = AnonymousClass9.this;
                                        access$500.controlGroup(groupId, modelId, value);
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    public JSONObject allControl(int modelId, Object value) {
        return this.customGroupStrategy.allControl(modelId, value);
    }

    public JSONArray scenes() {
        JSONArray array = new JSONArray();
        try {
            for (Scene scene : this.mMeshInfo.scenes) {
                JSONObject itemObj = new JSONObject();
                JSONArray macArr = new JSONArray();
                for (Scene.SceneState sceneState : scene.states) {
                    macArr.put((Object) sceneState.macAddress.toUpperCase());
                }
                itemObj.put("sceneId", scene.sceneId);
                itemObj.put("devices", (Object) macArr);
                array.put((Object) itemObj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    public synchronized int addScene(int sceneId) {
        return this.meshSceneStrategy.addScene(sceneId);
    }

    public JSONObject removeScene(int sceneId) {
        if (!this.meshSceneStrategy.isExist(sceneId)) {
            return this.customSceneStrategy.removeScene(sceneId);
        }
        this.customSceneStrategy.removeScene(sceneId);
        return this.meshSceneStrategy.removeScene(sceneId);
    }

    public synchronized void removeSceneAction(int sceneId, String macAddress, MeshSceneCallback sceneCallback) {
        if (this.meshSceneStrategy.hasRules(sceneId, macAddress)) {
            this.customSceneStrategy.removeSceneAction(sceneId, macAddress, (MeshSceneCallback) null);
            this.meshSceneStrategy.removeSceneAction(sceneId, macAddress, sceneCallback);
        } else {
            this.customSceneStrategy.removeSceneAction(sceneId, macAddress, sceneCallback);
        }
    }

    public synchronized void addSceneAction(int sceneId, String macAddress, List<CustomScene.SceneRule> ruleList, MeshSceneCallback sceneCallback) {
        if (LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, macAddress).protocolVersion < NEW_PROTOCOL || ruleList == null) {
            this.meshSceneStrategy.addSceneAction(sceneId, macAddress, ruleList, sceneCallback);
        } else {
            MeshLog.d("新版协议addSceneAction");
            this.customSceneStrategy.addSceneAction(sceneId, macAddress, ruleList, sceneCallback);
        }
    }

    public JSONObject runScene(int sceneId, int fading) {
        if (this.meshSceneStrategy.hasRules(sceneId, (String) null)) {
            this.meshSceneStrategy.runScene(sceneId, fading);
        }
        return this.customSceneStrategy.runScene(sceneId, fading);
    }

    public void OTAUpgrade(String url, String mac, MeshOTACallback meshOTACallback) {
        if (this.otaCtrl == null) {
            CopyOnWriteArrayList<CtrlLifecycle> copyOnWriteArrayList = this.ctrlList;
            OtaCtrl otaCtrl2 = new OtaCtrl(this, this.handlerThread);
            this.otaCtrl = otaCtrl2;
            copyOnWriteArrayList.add(otaCtrl2);
        }
        this.otaCtrl.startUpgrade(mac, url, meshOTACallback);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005f, code lost:
        return;
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void setRoutine(meshsdk.model.json.RoutineRule r10, meshsdk.callback.MeshScheduleCallback r11) {
        /*
            r9 = this;
            monitor-enter(r9)
            meshsdk.model.MeshInfo r0 = r9.mMeshInfo     // Catch:{ all -> 0x0060 }
            java.util.List<meshsdk.model.NodeInfo> r0 = r0.nodes     // Catch:{ all -> 0x0060 }
            java.lang.String r1 = r10.mac     // Catch:{ all -> 0x0060 }
            meshsdk.model.NodeInfo r0 = meshsdk.util.LDSMeshUtil.findMeshNode((java.util.List<meshsdk.model.NodeInfo>) r0, (java.lang.String) r1)     // Catch:{ all -> 0x0060 }
            r1 = -1
            if (r0 != 0) goto L_0x0017
            r2 = 418(0x1a2, float:5.86E-43)
            java.lang.String r3 = "node is not exist"
            r11.onFail(r2, r3, r1)     // Catch:{ all -> 0x0060 }
            monitor-exit(r9)
            return
        L_0x0017:
            int r2 = r10.smartId     // Catch:{ all -> 0x0060 }
            com.telink.ble.mesh.entity.Scheduler r2 = r0.getSchedulerBySmartId(r2)     // Catch:{ all -> 0x0060 }
            r7 = r2
            r2 = 1
            r9.isNew = r2     // Catch:{ all -> 0x0060 }
            r2 = 425(0x1a9, float:5.96E-43)
            r3 = 0
            if (r7 != 0) goto L_0x0035
            byte r4 = r0.allocSchedulerIndex()     // Catch:{ all -> 0x0060 }
            if (r4 != r1) goto L_0x0033
            java.lang.String r3 = "allocate schedule index fail"
            r11.onFail(r2, r3, r1)     // Catch:{ all -> 0x0060 }
            monitor-exit(r9)
            return
        L_0x0033:
            r8 = r4
            goto L_0x003d
        L_0x0035:
            byte r1 = r7.getIndex()     // Catch:{ all -> 0x0060 }
            r4 = r1
            r9.isNew = r3     // Catch:{ all -> 0x0060 }
            r8 = r4
        L_0x003d:
            java.lang.String r1 = "timer"
            java.lang.String r4 = r10.triggerType     // Catch:{ all -> 0x0060 }
            boolean r1 = r1.equals(r4)     // Catch:{ all -> 0x0060 }
            if (r1 == 0) goto L_0x0059
            meshsdk.ctrl.ScheduleCtrlAdapter r1 = r9.schedulerCtrl     // Catch:{ all -> 0x0060 }
            r1.setTime(r0, r3)     // Catch:{ all -> 0x0060 }
            meshsdk.ctrl.ScheduleCtrlAdapter r1 = r9.schedulerCtrl     // Catch:{ all -> 0x0060 }
            boolean r2 = r9.isNew     // Catch:{ all -> 0x0060 }
            r3 = r0
            r4 = r8
            r5 = r10
            r6 = r11
            r1.setSchedule(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0060 }
            goto L_0x005e
        L_0x0059:
            java.lang.String r1 = "not support yet"
            r11.onFail(r2, r1, r8)     // Catch:{ all -> 0x0060 }
        L_0x005e:
            monitor-exit(r9)
            return
        L_0x0060:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: meshsdk.SIGMesh.setRoutine(meshsdk.model.json.RoutineRule, meshsdk.callback.MeshScheduleCallback):void");
    }

    @Deprecated
    public synchronized void removeRoutine(int smartId, String mac, final MeshScheduleCallback scheduleCallback) {
        final NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            scheduleCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
            return;
        }
        this.scheduleIndex = 0;
        Scheduler scheduler = node.getSchedulerBySmartId(smartId);
        if (scheduler == null) {
            scheduleCallback.onFail(424, "schedule is not exist", -1);
            return;
        }
        this.scheduleIndex = scheduler.getIndex();
        RoutineRule rule = RoutineRule.Schedule2Routine(scheduler);
        Scene scene = this.mMeshInfo.getSceneById(scheduler.getRegister().getSceneId());
        if (scene != null) {
            this.mMeshInfo.scenes.remove(scene);
            this.mMeshInfo.saveOrUpdate(this.mContext, "移除场景：mMeshInfo.scenes.remove");
        }
        this.schedulerCtrl.setScheduleEnable(false, node, this.scheduleIndex, rule, new MeshScheduleCallback() {
            public void onSuccess(int scheduleIndex) {
                node.removeSchedulerByIndex((byte) scheduleIndex);
                SIGMesh.getInstance().getMeshInfo().saveOrUpdate(SIGMesh.this.mContext, "移除定时：schedulerCtrl.setScheduleEnable.onSuccess");
                scheduleCallback.onSuccess(scheduleIndex);
            }

            public void onFail(int code, String msg, int scheduleIndex) {
                MeshScheduleCallback meshScheduleCallback = scheduleCallback;
                meshScheduleCallback.onFail(424, "remove schedule fail:" + msg, scheduleIndex);
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0054, code lost:
        return;
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void setRoutineEnable(int r18, java.lang.String r19, int r20, meshsdk.callback.MeshScheduleCallback r21) {
        /*
            r17 = this;
            r1 = r17
            r0 = r21
            monitor-enter(r17)
            meshsdk.model.MeshInfo r2 = r1.mMeshInfo     // Catch:{ all -> 0x0055 }
            java.util.List<meshsdk.model.NodeInfo> r2 = r2.nodes     // Catch:{ all -> 0x0055 }
            r13 = r19
            meshsdk.model.NodeInfo r2 = meshsdk.util.LDSMeshUtil.findMeshNode((java.util.List<meshsdk.model.NodeInfo>) r2, (java.lang.String) r13)     // Catch:{ all -> 0x0055 }
            r14 = r2
            r2 = -1
            if (r14 != 0) goto L_0x001c
            r3 = 418(0x1a2, float:5.86E-43)
            java.lang.String r4 = "node is not exist"
            r0.onFail(r3, r4, r2)     // Catch:{ all -> 0x0055 }
            monitor-exit(r17)
            return
        L_0x001c:
            r3 = 0
            r15 = r18
            com.telink.ble.mesh.entity.Scheduler r4 = r14.getSchedulerBySmartId(r15)     // Catch:{ all -> 0x0055 }
            r16 = r4
            if (r16 != 0) goto L_0x0031
            r4 = 424(0x1a8, float:5.94E-43)
            java.lang.String r5 = "schedule is not exist"
            r0.onFail(r4, r5, r2)     // Catch:{ all -> 0x0055 }
            monitor-exit(r17)
            return
        L_0x0031:
            byte r5 = r16.getIndex()     // Catch:{ all -> 0x0055 }
            meshsdk.model.json.RoutineRule r6 = meshsdk.model.json.RoutineRule.Schedule2Routine(r16)     // Catch:{ all -> 0x0055 }
            r2 = 1
            r12 = r20
            if (r12 != r2) goto L_0x0048
            meshsdk.ctrl.ScheduleCtrlAdapter r2 = r1.schedulerCtrl     // Catch:{ all -> 0x0055 }
            r3 = 1
            r4 = r14
            r7 = r21
            r2.setScheduleEnable(r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0055 }
            goto L_0x0053
        L_0x0048:
            meshsdk.ctrl.ScheduleCtrlAdapter r7 = r1.schedulerCtrl     // Catch:{ all -> 0x0055 }
            r8 = 0
            r9 = r14
            r10 = r5
            r11 = r6
            r12 = r21
            r7.setScheduleEnable(r8, r9, r10, r11, r12)     // Catch:{ all -> 0x0055 }
        L_0x0053:
            monitor-exit(r17)
            return
        L_0x0055:
            r0 = move-exception
            monitor-exit(r17)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: meshsdk.SIGMesh.setRoutineEnable(int, java.lang.String, int, meshsdk.callback.MeshScheduleCallback):void");
    }

    public synchronized void setSmartRule(final RoutineRule rule, final MeshCustomcmdCallback customcmdCallback) {
        final byte smartAddress;
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, rule.mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
            return;
        }
        Smart smart = node.getSmartBySmartId(rule.smartId);
        this.isNew = true;
        if (smart == null) {
            smartAddress = node.allocSmartAddress();
            MeshLog.i("allocate mesh smart address:" + smartAddress + ",mac:" + node.macAddress);
            if (smartAddress == -1) {
                customcmdCallback.onFail(425, "allocate smart address fail", -1);
                return;
            }
        } else {
            smartAddress = (byte) smart.smartAddress;
            MeshLog.i("edit mesh smart address:" + smartAddress + ",mac:" + node.macAddress);
            this.isNew = false;
        }
        TimeRecorder.mark("jsbridgeSetSmart");
        this.smartCtrlAdapter.setTime(node, false);
        TimeRecorder.mark("setSmart:" + smartAddress);
        this.smartCtrlAdapter.setSmart(node, smartAddress, rule, new MeshCustomcmdCallback() {
            public void onSuccess(Object data) {
                MeshLog.i("setSmartRule success");
                TimeRecorder.mark("setSmart:" + smartAddress);
                LDSMeshUtil.findMeshNode(SIGMesh.this.mMeshInfo.nodes, rule.mac).saveSmart(new Smart(rule.smartId, smartAddress));
                SIGMesh.this.mMeshInfo.saveOrUpdate(SIGMesh.this.mContext, "smartCtrlAdapter.onSuccess");
                customcmdCallback.onSuccess(data);
                if (rule.enable == 0) {
                    SIGMesh instance = SIGMesh.getInstance();
                    RoutineRule routineRule = rule;
                    instance.setSmartRuleEnable(routineRule.smartId, routineRule.mac, routineRule.enable, new MeshCustomcmdCallback() {
                        public void onSuccess(Object data) {
                            MeshLog.i("设置完smart 补发禁用命令=== onSuccess");
                        }

                        public void onFail(int code, String msg, Object data) {
                            MeshLog.i("设置完smart 补发禁用命令=== onFail");
                        }
                    });
                }
            }

            public void onFail(int code, String msg, Object data) {
                MeshLog.i("setSmartRule onFail");
                customcmdCallback.onFail(code, msg, data);
            }
        });
    }

    public synchronized void removeSmartRule(int smartId, final String mac, final MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
            return;
        }
        final Smart sm = node.getSmartBySmartId(smartId);
        if (sm == null) {
            customcmdCallback.onFail(424, "smart is not exist", -1);
        } else {
            this.smartCtrlAdapter.removeSmart(node, (byte) sm.smartAddress, new RoutineRule(mac), new MeshCustomcmdCallback() {
                public void onSuccess(Object data) {
                    LDSMeshUtil.findMeshNode(SIGMesh.this.mMeshInfo.nodes, mac).removeSmartByAddress((byte) sm.smartAddress);
                    SIGMesh.this.mMeshInfo.saveOrUpdate(SIGMesh.this.mContext, "smartCtrlAdapter.removeSmart");
                    customcmdCallback.onSuccess(data);
                }

                public void onFail(int code, String msg, Object data) {
                    customcmdCallback.onFail(code, msg, data);
                }
            });
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0056, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void setSmartRuleEnable(int r7, java.lang.String r8, int r9, final meshsdk.callback.MeshCustomcmdCallback r10) {
        /*
            r6 = this;
            monitor-enter(r6)
            meshsdk.model.MeshInfo r0 = r6.mMeshInfo     // Catch:{ all -> 0x0057 }
            java.util.List<meshsdk.model.NodeInfo> r0 = r0.nodes     // Catch:{ all -> 0x0057 }
            meshsdk.model.NodeInfo r0 = meshsdk.util.LDSMeshUtil.findMeshNode((java.util.List<meshsdk.model.NodeInfo>) r0, (java.lang.String) r8)     // Catch:{ all -> 0x0057 }
            r1 = -1
            if (r0 != 0) goto L_0x0019
            r2 = 418(0x1a2, float:5.86E-43)
            java.lang.String r3 = "node is not exist"
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ all -> 0x0057 }
            r10.onFail(r2, r3, r1)     // Catch:{ all -> 0x0057 }
            monitor-exit(r6)
            return
        L_0x0019:
            com.telink.ble.mesh.entity.Smart r2 = r0.getSmartBySmartId(r7)     // Catch:{ all -> 0x0057 }
            if (r2 != 0) goto L_0x002d
            r3 = 424(0x1a8, float:5.94E-43)
            java.lang.String r4 = "smart is not exist"
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ all -> 0x0057 }
            r10.onFail(r3, r4, r1)     // Catch:{ all -> 0x0057 }
            monitor-exit(r6)
            return
        L_0x002d:
            r1 = 1
            if (r9 != r1) goto L_0x0043
            meshsdk.ctrl.SmartCtrlAdapter r1 = r6.smartCtrlAdapter     // Catch:{ all -> 0x0057 }
            int r3 = r2.smartAddress     // Catch:{ all -> 0x0057 }
            byte r3 = (byte) r3     // Catch:{ all -> 0x0057 }
            meshsdk.model.json.RoutineRule r4 = new meshsdk.model.json.RoutineRule     // Catch:{ all -> 0x0057 }
            r4.<init>(r8)     // Catch:{ all -> 0x0057 }
            meshsdk.SIGMesh$13 r5 = new meshsdk.SIGMesh$13     // Catch:{ all -> 0x0057 }
            r5.<init>(r10)     // Catch:{ all -> 0x0057 }
            r1.setSmartEnable(r0, r3, r4, r5)     // Catch:{ all -> 0x0057 }
            goto L_0x0055
        L_0x0043:
            meshsdk.ctrl.SmartCtrlAdapter r1 = r6.smartCtrlAdapter     // Catch:{ all -> 0x0057 }
            int r3 = r2.smartAddress     // Catch:{ all -> 0x0057 }
            byte r3 = (byte) r3     // Catch:{ all -> 0x0057 }
            meshsdk.model.json.RoutineRule r4 = new meshsdk.model.json.RoutineRule     // Catch:{ all -> 0x0057 }
            r4.<init>(r8)     // Catch:{ all -> 0x0057 }
            meshsdk.SIGMesh$14 r5 = new meshsdk.SIGMesh$14     // Catch:{ all -> 0x0057 }
            r5.<init>(r10)     // Catch:{ all -> 0x0057 }
            r1.setSmartDisable(r0, r3, r4, r5)     // Catch:{ all -> 0x0057 }
        L_0x0055:
            monitor-exit(r6)
            return
        L_0x0057:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: meshsdk.SIGMesh.setSmartRuleEnable(int, java.lang.String, int, meshsdk.callback.MeshCustomcmdCallback):void");
    }

    public void getDevVersion(String mac, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else if (node.getOnOff() == -1) {
            customcmdCallback.onFail(BaseResp.ERR_MSG_SEND_FAIL, "node is offline", -1);
        } else {
            String meshHWVersion = LDSMeshUtil.getMeshHWVersion(getInstance().getMeshInfo().meshUUID, mac);
            if (!TextUtils.isEmpty(meshHWVersion)) {
                JSONObject json = new JSONObject();
                try {
                    json.put("mac", (Object) mac);
                    json.put(ConfigUtil.VERSION_FILE, (Object) meshHWVersion);
                    json.put("desc", (Object) "从缓存中读取");
                    MeshLog.d("return sharedPreference hw version:" + json.toString());
                    customcmdCallback.onSuccess(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                this.mCmdCtrl.getHwVersion(node.meshAddress, customcmdCallback);
            } else if (!this.mCmdCtrl.getHwVersion(node.meshAddress, customcmdCallback)) {
                customcmdCallback.onFail(BaseResp.ERR_MSG_SEND_FAIL, "message send fail", -1);
            }
        }
    }

    public void getCacheDevVersions(JSONArray macs, MeshCustomcmdCallback customcmdCallback) {
        if (macs == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "param empty", -1);
            return;
        }
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < macs.length(); i++) {
            String mac = macs.optString(i);
            if (LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac) == null) {
                customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
                return;
            }
            MultiPropertyData multiStatusData = (MultiPropertyData) MultiPropertyCacheInstance.getInstance().get(mac);
            if (multiStatusData != null && !TextUtils.isEmpty(multiStatusData.version)) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("mac", (Object) mac);
                    object.put(ConfigUtil.VERSION_FILE, (Object) multiStatusData.version);
                    jsonArray.put((Object) object);
                } catch (Exception e) {
                }
            }
        }
        customcmdCallback.onSuccess(jsonArray);
    }

    public void getDevTime(String mac, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else if (!this.mCmdCtrl.getHwTime(node.meshAddress, customcmdCallback)) {
            customcmdCallback.onFail(BaseResp.ERR_MSG_SEND_FAIL, "message send fail", -1);
        }
    }

    public void getColorMode(String mac, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else if (!this.mCmdCtrl.getColorMode(node.meshAddress, customcmdCallback)) {
            customcmdCallback.onFail(BaseResp.ERR_MSG_SEND_FAIL, "message send fail", -1);
        }
    }

    public void setEffectMode(String groupid, String mac, int effectId, long durationTime, int action, MeshCustomcmdCallback customcmdCallback) {
        int meshAddress;
        String str = mac;
        MeshCustomcmdCallback meshCustomcmdCallback = customcmdCallback;
        if (!TextUtils.isEmpty(mac)) {
            getInstance().preStopRhythm(mac, IRhyDevice.TYPE_BLE_MESH, BleMeshService.ACTION_SET_EFFECT_MODE);
        }
        if (!TextUtils.isEmpty(groupid)) {
            int groupId = Integer.valueOf(groupid).intValue();
            if (this.meshGroupStrategy.isExist(groupId) || this.customGroupStrategy.isExist(groupId)) {
                int i = effectId;
                long j = durationTime;
                int i2 = action;
                String str2 = groupid;
                MeshCustomcmdCallback meshCustomcmdCallback2 = customcmdCallback;
                this.meshGroupStrategy.setEffectMode(this.effectModeCtrlAdapter, i, j, i2, str2, meshCustomcmdCallback2);
                this.customGroupStrategy.setEffectMode(this.effectModeCtrlAdapter, i, j, i2, str2, meshCustomcmdCallback2);
                meshCustomcmdCallback.onSuccess("");
                return;
            }
            meshCustomcmdCallback.onFail(416, "Group is not exist", -1);
            return;
        }
        if (TextUtils.isEmpty(mac)) {
            meshAddress = 65535;
        } else {
            NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
            if (node == null) {
                meshCustomcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
                return;
            }
            meshAddress = node.meshAddress;
        }
        this.effectModeCtrlAdapter.setEffectMode(mac, effectId, durationTime, action, meshAddress, customcmdCallback);
        meshCustomcmdCallback.onSuccess("");
    }

    public void setCustomEffectMode(CustomEffectMode customEffectMode, MeshCustomcmdCallback customcmdCallback) {
        int meshAddress;
        if (!TextUtils.isEmpty(customEffectMode.mac)) {
            getInstance().preStopRhythm(customEffectMode.mac, IRhyDevice.TYPE_BLE_MESH, "setCustomEffectMode");
        }
        if (!TextUtils.isEmpty(customEffectMode.groupId)) {
            int groupId = Integer.valueOf(customEffectMode.groupId).intValue();
            if (this.meshGroupStrategy.isExist(groupId) || this.customGroupStrategy.isExist(groupId)) {
                this.meshGroupStrategy.setCustomEffectMode(this.effectModeCtrlAdapter, customEffectMode, customcmdCallback);
                this.customGroupStrategy.setCustomEffectMode(this.effectModeCtrlAdapter, customEffectMode, customcmdCallback);
                customcmdCallback.onSuccess("");
                return;
            }
            customcmdCallback.onFail(416, "Group is not exist", -1);
            return;
        }
        if (TextUtils.isEmpty(customEffectMode.mac)) {
            meshAddress = 65535;
        } else {
            NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, customEffectMode.mac);
            if (node == null) {
                customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
                return;
            }
            meshAddress = node.meshAddress;
        }
        this.effectModeCtrlAdapter.setCustomEffectMode(customEffectMode.mac, customEffectMode, meshAddress, customcmdCallback);
        customcmdCallback.onSuccess("");
    }

    public void getEffectMode(String mac, int effectId, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else {
            this.mCmdCtrl.getEffectMode(effectId, node.meshAddress, customcmdCallback);
        }
    }

    public void getCurrentCustomEffectMode(String mac, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else {
            this.mCmdCtrl.getCurrentCustomEffectMode(node.meshAddress, customcmdCallback);
        }
    }

    public boolean isBinding() {
        return this.mBindCtrl.isBinding();
    }

    public void setDST(DSTRule dst, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, dst.mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else if (dst.startMonth > 12 || dst.endMonth > 12 || dst.startDay > 31 || dst.endDay > 31 || dst.startHour > 23 || dst.endHour > 23 || dst.startMinute > 60 || dst.endMinute > 60) {
            customcmdCallback.onFail(BaseResp.ERR_PARAM_ERROR, "params error", -1);
        } else if (!this.mCmdCtrl.setDST(node.meshAddress, dst, customcmdCallback)) {
            customcmdCallback.onFail(BaseResp.ERR_MSG_SEND_FAIL, "message send fail", -1);
        }
    }

    @Deprecated
    public void setRhythm(String mac, JSONObject jsonObject) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node != null) {
            RoutineRule.ExecuteAction action = new RoutineRule.ExecuteAction();
            try {
                if (jsonObject.has("fading")) {
                    action.fading = jsonObject.getInt("fading");
                }
                if (jsonObject.has("OnOff")) {
                    action.OnOff = jsonObject.getInt("OnOff");
                } else {
                    action.Dimming = jsonObject.getInt("Dimming");
                    action.HSLHue = (float) jsonObject.getInt("HSLHue");
                    action.HSLSaturation = (float) jsonObject.getInt("HSLSaturation");
                    action.HSLLightness = (float) jsonObject.getInt("HSLLightness");
                }
            } catch (Exception e) {
                MeshLog.e("setRhythm error=>" + e.toString());
                e.printStackTrace();
            }
            this.mCmdCtrl.setRhythm(node.meshAddress, action);
        }
    }

    public void setRhythmV1(String mac, String groupId, JSONObject jsonObject) {
        int meshAddress;
        if (TextUtils.isEmpty(groupId)) {
            NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
            if (node != null) {
                meshAddress = node.meshAddress;
            } else {
                return;
            }
        } else {
            GroupInfo groupInfo = LDSMeshUtil.findGroup(this.mMeshInfo.groups, Integer.valueOf(groupId).intValue());
            if (groupInfo != null) {
                meshAddress = groupInfo.address;
            } else {
                return;
            }
        }
        RoutineRule.ExecuteAction action = new RoutineRule.ExecuteAction();
        try {
            action.mode = jsonObject.getString("mode");
            action.soundWaveType = jsonObject.getInt("soundWaveType");
            if (jsonObject.has("OnOff")) {
                action.OnOff = jsonObject.getInt("OnOff");
            } else {
                action.Dimming = jsonObject.getInt("Dimming");
                action.HSLHue = (float) jsonObject.getInt("HSLHue");
                action.HSLSaturation = (float) jsonObject.getInt("HSLSaturation");
                action.HSLLightness = (float) jsonObject.getInt("HSLLightness");
                int color = jsonObject.getInt(TypedValues.Custom.S_COLOR);
                action.r = Color.red(color);
                action.g = Color.green(color);
                action.b = Color.blue(color);
            }
        } catch (Exception e) {
            MeshLog.logMusicRhythmWarn("setRhythmV1 error=>" + e.getMessage());
            e.printStackTrace();
        }
        this.mCmdCtrl.setRhythmV1(meshAddress, action);
    }

    public void setAsyncRhythmV2(String mac, String groupId, JSONObject jsonObject) {
        int meshAddress;
        if (TextUtils.isEmpty(groupId)) {
            NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
            if (node != null) {
                meshAddress = node.meshAddress;
            } else {
                return;
            }
        } else {
            GroupInfo groupInfo = LDSMeshUtil.findGroup(this.mMeshInfo.groups, Integer.valueOf(groupId).intValue());
            if (groupInfo != null) {
                meshAddress = groupInfo.address;
            } else {
                return;
            }
        }
        this.mCmdCtrl.setAsyncRhythmV2(meshAddress, jsonObject);
    }

    public void setRhythmV3(String mac, String groupId, JSONObject jsonObject) {
        int meshAddress;
        if (TextUtils.isEmpty(groupId)) {
            NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
            if (node != null) {
                meshAddress = node.meshAddress;
            } else {
                return;
            }
        } else {
            GroupInfo groupInfo = LDSMeshUtil.findGroup(this.mMeshInfo.groups, Integer.valueOf(groupId).intValue());
            if (groupInfo == null) {
                MeshLog.logMusicRhythmWarn("v3律动，组地址不存在，发送律动失败");
                return;
            }
            meshAddress = groupInfo.address;
        }
        this.mCmdCtrl.setRhythmV3(meshAddress, jsonObject);
    }

    public void setRhythmEnable(String mac, String groupId, byte able, MeshCustomcmdCallback customcmdCallback) {
        int meshAddress;
        String byMac = "";
        if (TextUtils.isEmpty(groupId)) {
            NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
            if (node == null) {
                customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
                return;
            } else {
                meshAddress = node.meshAddress;
                byMac = mac;
            }
        } else {
            GroupInfo groupInfo = LDSMeshUtil.findGroup(this.mMeshInfo.groups, Integer.parseInt(groupId));
            if (groupInfo == null) {
                customcmdCallback.onFail(416, "Group is not exist", -1);
                return;
            }
            meshAddress = groupInfo.address;
        }
        this.mCmdCtrl.setRhythmEnable(meshAddress, byMac, able, customcmdCallback);
    }

    public void setRhythmTheme(String mac, String groupId, int[] colors, int index) {
        int meshAddress;
        if (TextUtils.isEmpty(groupId)) {
            NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
            if (node != null) {
                meshAddress = node.meshAddress;
            } else {
                return;
            }
        } else {
            GroupInfo groupInfo = LDSMeshUtil.findGroup(this.mMeshInfo.groups, Integer.parseInt(groupId));
            if (groupInfo != null) {
                meshAddress = groupInfo.address;
            } else {
                return;
            }
        }
        this.mCmdCtrl.setRhythmTheme(meshAddress, colors, index);
    }

    public void getLightsRhythmStatus(String mac, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else {
            this.mCmdCtrl.getLightsRhythmStatus(node.meshAddress, customcmdCallback);
        }
    }

    public void getEffectLinkage(String mac, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else {
            this.mCmdCtrl.getEffectLinkage(node.meshAddress, customcmdCallback);
        }
    }

    public void performEffectLinkage(String mac, int action, String groupid, MeshCustomcmdCallback customcmdCallback) {
        MeshCustomcmdCallback meshCustomcmdCallback = customcmdCallback;
        String str = mac;
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            meshCustomcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else if (!TextUtils.isEmpty(groupid)) {
            int groupId = Integer.valueOf(groupid).intValue();
            if (this.meshGroupStrategy.isExist(groupId) || this.customGroupStrategy.isExist(groupId)) {
                int i = action;
                String str2 = groupid;
                MeshCustomcmdCallback meshCustomcmdCallback2 = customcmdCallback;
                this.meshGroupStrategy.setLinkageMode(this.linkageCtrlAdapter, i, node.meshAddress, str2, meshCustomcmdCallback2);
                this.customGroupStrategy.setLinkageMode(this.linkageCtrlAdapter, i, node.meshAddress, str2, meshCustomcmdCallback2);
                meshCustomcmdCallback.onSuccess("");
                return;
            }
            meshCustomcmdCallback.onFail(416, "Group is not exist", -1);
        } else {
            int i2 = action;
            this.linkageCtrlAdapter.setLinkageMode(action, node.meshAddress, 65535, meshCustomcmdCallback);
            meshCustomcmdCallback.onSuccess("");
        }
    }

    public void getMultipleProperties(int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        this.mCmdCtrl.getMultipleProperties(meshAddr, customcmdCallback);
    }

    public void getBattery(String mac, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
            return;
        }
        MultiPropertyData multiStatusData = (MultiPropertyData) MultiPropertyCacheInstance.getInstance().get(mac);
        if (!(multiStatusData == null || multiStatusData.battery == -1)) {
            MeshLog.i("getBattery使用缓存的电量:" + multiStatusData.battery);
            BatteryPropertyBean batteryPropertyBean = new BatteryPropertyBean();
            batteryPropertyBean.setBattery(multiStatusData.battery);
            batteryPropertyBean.setAcState(multiStatusData.acState);
            batteryPropertyBean.setChargeState(multiStatusData.chargeState);
            batteryPropertyBean.setCache(true);
            customcmdCallback.onSuccess(batteryPropertyBean);
        }
        if (multiStatusData == null || multiStatusData.battery == -1 || multiStatusData.isExpire()) {
            MeshLog.i("getBattery刷新调用获取电量接口,mac:" + node.macAddress);
            this.mCmdCtrl.getBattery(node.meshAddress, customcmdCallback);
        }
    }

    public void getCacheBatterys(JSONArray macs, MeshCustomcmdCallback customcmdCallback) {
        if (macs == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "param empty", -1);
            return;
        }
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < macs.length(); i++) {
            String mac = macs.optString(i);
            if (LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac) == null) {
                customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
                return;
            }
            MultiPropertyData multiStatusData = (MultiPropertyData) MultiPropertyCacheInstance.getInstance().get(mac);
            if (!(multiStatusData == null || multiStatusData.battery == -1)) {
                try {
                    JSONObject object = new JSONObject();
                    object.put("mac", (Object) mac);
                    object.put("battery", multiStatusData.battery);
                    object.put("chargeState", multiStatusData.chargeState);
                    object.put("acState", multiStatusData.acState);
                    jsonArray.put((Object) object);
                } catch (Exception e) {
                }
            }
        }
        customcmdCallback.onSuccess(jsonArray);
    }

    public void getSingleCappedAlarm(String mac, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else {
            this.mCmdCtrl.getSingleCappedAlarm(node.meshAddress, customcmdCallback);
        }
    }

    public void setSingleCappedAlarm(String mac, int onOff, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else {
            this.mCmdCtrl.setSingleCappedAlarm(node.meshAddress, onOff, customcmdCallback);
        }
    }

    public void getDetectionModeParams(String mac, int mode, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else if (node.protocolVersion < NEW_PROTOCOL6) {
            MeshLog.i("getDetectionModeParams 走老协议");
            this.mCmdCtrl.getDetectionModeParams(mac, node.meshAddress, mode, customcmdCallback);
        } else {
            MeshLog.i("getDetectionModeParams 新协议");
            this.mCmdCtrl.getDetectionModeParamsNew(mac, node.meshAddress, mode, customcmdCallback);
        }
    }

    public void getCurrentDetectionMode(String mac, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else if (node.protocolVersion < NEW_PROTOCOL6) {
            MeshLog.i("getCurrentDetectionMode 老协议");
            this.mCmdCtrl.getCurrentDetectionMode(mac, node.meshAddress, customcmdCallback);
        } else {
            MeshLog.i("getCurrentDetectionMode 新协议");
            this.mCmdCtrl.getCurrentDetectionModeNew(mac, node.meshAddress, customcmdCallback);
        }
    }

    public void setCurrentDetectionMode(String mac, int mode, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else if (node.protocolVersion < NEW_PROTOCOL6) {
            MeshLog.i("setCurrentDetectionMode 走老协议");
            this.mCmdCtrl.setCurrentDetectionMode(mac, node.meshAddress, mode, customcmdCallback);
        } else {
            MeshLog.i("setCurrentDetectionMode 新协议");
            this.mCmdCtrl.setCurrentDetectionModeNew(mac, node.meshAddress, mode, customcmdCallback);
        }
    }

    public void setAlarmStatus(String mac, int status, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else {
            this.mCmdCtrl.setAlarmStatus(node.meshAddress, status, customcmdCallback);
        }
    }

    public void getDeviceGroups(String mac, MeshCustomcmdCallback callback) {
        NodeInfo nodeInfo = LDSMeshUtil.findMeshNode(getInstance().getMeshInfo().nodes, mac);
        if (nodeInfo == null || nodeInfo.meshAddress <= 0) {
            callback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist or nodeInfo.meshAddress <=0?" + nodeInfo.meshAddress, -1);
            return;
        }
        this.mCmdCtrl.getDeviceGroups(nodeInfo, callback);
    }

    public void getIlluminationState(String mac, MeshCustomcmdCallback callback) {
        NodeInfo nodeInfo = checkAndGetNode(mac, callback);
        if (nodeInfo != null) {
            this.mCmdCtrl.getIlluminationState(nodeInfo.meshAddress, callback);
        }
    }

    public void getPIRConfig(String mac, MeshCustomcmdCallback callback) {
        NodeInfo nodeInfo = checkAndGetNode(mac, callback);
        if (nodeInfo != null) {
            this.mCmdCtrl.getPIRConfig(nodeInfo.meshAddress, callback);
        }
    }

    public void setPIRConfig(String mac, int level, MeshCustomcmdCallback callback) {
        NodeInfo node = checkAndGetNode(mac, callback);
        if (node != null) {
            this.mCmdCtrl.setPIRConfig(node.meshAddress, level, callback);
        }
    }

    public void getEnergyConsumption(String mac, MeshCustomcmdCallback callback) {
        NodeInfo nodeInfo = checkAndGetNode(mac, callback);
        if (nodeInfo != null) {
            this.mCmdCtrl.getEnergyConsumption(nodeInfo.meshAddress, callback);
        }
    }

    public void getWallWasherLightStatus(String mac, MeshCustomcmdCallback callback) {
        NodeInfo nodeInfo = checkAndGetNode(mac, callback);
        if (nodeInfo != null) {
            this.mCmdCtrl.getWallWasherLightStatus(nodeInfo.meshAddress, callback);
        }
    }

    public void controlWallWasherLight(String mac, int onOff, int dimming, String rgb, MeshCustomcmdCallback callback) {
        NodeInfo nodeInfo = checkAndGetNode(mac, callback);
        if (nodeInfo != null) {
            this.mCmdCtrl.controlWallWasherLight(nodeInfo.meshAddress, onOff, dimming, rgb, callback);
        }
    }

    public void getAlarmStatus(String mac, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else {
            this.mCmdCtrl.getAlarmStatus(node.meshAddress, customcmdCallback);
        }
    }

    public void getTemporaryControlDuration(String mac, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else {
            this.mCmdCtrl.getTemporaryControlDuration(node.meshAddress, customcmdCallback);
        }
    }

    public void setDetectionMode(DetectMode detectMode, MeshSimpleCmdSetCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, detectMode.mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
            return;
        }
        if (detectMode.hasGroup()) {
            GroupInfo groupInfo = LDSMeshUtil.findGroup(this.mMeshInfo.groups, detectMode.triggerMotion.groupId);
            if (groupInfo == null) {
                customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "Group is not exist", -1);
                return;
            } else {
                detectMode.triggerMotion.meshGroupId = groupInfo.address;
            }
        }
        this.detectModeCtrlAdapter.setDetectionMode(node.meshAddress, detectMode, customcmdCallback);
    }

    public void setTemporaryControlDuration(String mac, int duration, MeshCustomcmdCallback customcmdCallback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        } else if (!this.mCmdCtrl.setTemporaryControlDuration(node.meshAddress, duration, customcmdCallback)) {
            customcmdCallback.onFail(BaseResp.ERR_MSG_SEND_FAIL, "message send fail", -1);
        } else {
            customcmdCallback.onSuccess("");
        }
    }

    public void preStopRhythm(String mac, String protocolType, String ref) {
        Intent intent = new Intent("com.leedarson.RhythmStatusChangeEvent");
        intent.putExtra("deviceId", mac);
        intent.putExtra("protocolType", protocolType);
        intent.putExtra("ref", ref);
        LocalBroadcastManager.getInstance(getInstance().getContext()).sendBroadcast(intent);
    }

    public void setSupportAsyncRhy(String mac, boolean support) {
        if (!this.supportAsyncRhyMap.containsKey(mac)) {
            this.supportAsyncRhyMap.put(mac, Boolean.valueOf(support));
        }
    }

    public boolean isSupportAsyncRhy(String mac) {
        NodeInfo nodeInfo = LDSMeshUtil.findMeshNode(getInstance().getMeshInfo().nodes, mac);
        if (nodeInfo != null && nodeInfo.protocolVersion >= NEW_PROTOCOL4) {
            return true;
        }
        if (!this.supportAsyncRhyMap.containsKey(mac) || !this.supportAsyncRhyMap.get(mac).booleanValue()) {
            return false;
        }
        return true;
    }

    public void setOfflineCheckEnable(String mac, boolean enable) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            return;
        }
        if (enable) {
            MeshLog.i("查询律动状态true-------->setOfflineCheckEnable.startOfflineCheckTask");
            node.startOfflineCheckTask(NodeInfo.checkOnOffDelayTime);
            return;
        }
        MeshLog.i("查询律动状态false-------->setOfflineCheckEnable.startOfflineCheckTask");
        node.cancelOfflineCheckTask();
    }

    public int getRhythmStatusForMac(String groupId, String mac) {
        GroupInfo groupInfo = LDSMeshUtil.findGroup(this.mMeshInfo.groups, Integer.parseInt(groupId));
        if (groupInfo == null) {
            return 0;
        }
        for (NodeInfo node : LDSMeshUtil.getDevicesInGroup(groupInfo.address)) {
            String macAddress = node.macAddress;
            if (macAddress != null && mac != null && macAddress.toUpperCase().equals(mac.toUpperCase())) {
                return 1;
            }
        }
        return 0;
    }

    public List<String> getNodeAddressByGroupId(String groupId) {
        GroupInfo groupInfo = LDSMeshUtil.findGroup(this.mMeshInfo.groups, Integer.parseInt(groupId));
        List<String> addressList = new ArrayList<>();
        if (groupInfo == null) {
            return addressList;
        }
        for (NodeInfo node : LDSMeshUtil.getDevicesInGroup(groupInfo.address)) {
            addressList.add(node.macAddress);
        }
        return addressList;
    }

    public void setLastDirectMac(String mac) {
        MeshLog.d("set Last Direct Mac:" + mac);
        this.lastDirectMac = mac;
    }

    public boolean hasLastDirectMac() {
        return !TextUtils.isEmpty(this.lastDirectMac);
    }

    public String getLastDirectMac() {
        return this.lastDirectMac;
    }

    private NodeInfo checkAndGetNode(String mac, MeshCustomcmdCallback callback) {
        NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, mac);
        if (node == null) {
            callback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
        }
        return node;
    }

    public void setSecurityAlarm(SecuAlarmRule rule, MeshCustomcmdCallback callback) {
        NodeInfo node = checkAndGetNode(rule.mac, callback);
        if (node != null) {
            this.mCmdCtrl.setSecurityAlarm(node.macAddress, node.meshAddress, rule, callback);
        }
    }

    public void getSecurityAlarm(String mac, MeshCustomcmdCallback callback) {
        NodeInfo node = checkAndGetNode(mac, callback);
        if (node != null) {
            this.mCmdCtrl.getSecurityAlarm(node.meshAddress, callback);
        }
    }

    public void setEnergyMode(EnergySaveMode energySaveMode, MeshCustomcmdCallback callback) {
        NodeInfo node = checkAndGetNode(energySaveMode.mac, callback);
        if (node != null) {
            this.mCmdCtrl.setEnergyMode(node.macAddress, node.meshAddress, energySaveMode.enable, energySaveMode.energySavingFactor, callback);
        }
    }

    public void getEnergyMode(String mac, MeshCustomcmdCallback callback) {
        NodeInfo node = checkAndGetNode(mac, callback);
        if (node != null) {
            this.mCmdCtrl.getEnergyMode(node.meshAddress, callback);
        }
    }

    public void setOnPermissionListener(OnPermissionListener onPermissionListener2) {
        this.onPermissionListener = onPermissionListener2;
    }

    public void heartBeat(NodeInfo nodeInfo) {
        if (nodeInfo.protocolVersion >= NEW_PROTOCOL3) {
            MeshLog.i("heartBeat NEW_PROTOCOL3");
            this.mCmdCtrl.heartbeatVersion3(nodeInfo.meshAddress);
            return;
        }
        MeshMessagePool.getInstance().addAndSend(OnOffGetMessage.I(65535, getInstance().getMeshInfo().getDefaultAppKeyIndex(), getInstance().getMeshInfo().getOnlineCountInAll()));
    }

    public boolean meshGroupExist(int groupId) {
        GroupInfo group = LDSMeshUtil.findGroup(getInstance().getMeshInfo().groups, groupId);
        if (group == null || group.address <= 0) {
            return false;
        }
        return true;
    }

    public void setVoiceRhythmStopAttr(RhythmStopAttrBean bean, MeshCustomcmdCallback customcmdCallback) {
        int meshAddress;
        if (bean == null) {
            customcmdCallback.onFail(-1, "RhythmStopAttrBean is null", 0);
            return;
        }
        if (TextUtils.isEmpty(bean.groupId)) {
            NodeInfo node = LDSMeshUtil.findMeshNode(this.mMeshInfo.nodes, bean.mac);
            if (node == null) {
                customcmdCallback.onFail(BaseResp.ERR_NODE_NOT_EXIST, "node is not exist", -1);
                return;
            }
            meshAddress = node.meshAddress;
        } else {
            GroupInfo groupInfo = LDSMeshUtil.findGroup(this.mMeshInfo.groups, Integer.parseInt(bean.groupId));
            if (groupInfo == null) {
                customcmdCallback.onFail(416, "Group is not exist", -1);
                return;
            }
            meshAddress = groupInfo.address;
        }
        this.mCmdCtrl.setVoiceRhythmStopAttr(meshAddress, bean, customcmdCallback);
    }

    public void cancelSecurityAlarm(String mac, MeshCustomcmdCallback callback) {
        NodeInfo node = checkAndGetNode(mac, callback);
        if (node != null) {
            this.mCmdCtrl.cancelSecurityAlarm(node.meshAddress, callback);
        }
    }
}
