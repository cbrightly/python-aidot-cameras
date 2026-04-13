package meshsdk.model;

import android.os.Handler;
import android.os.Message;
import android.util.SparseBooleanArray;
import com.alibaba.android.arouter.launcher.a;
import com.leedarson.base.utils.e;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;
import com.leedarson.serviceinterface.LightsRhythmService;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.MeshSigModel;
import com.telink.ble.mesh.entity.CompositionData;
import com.telink.ble.mesh.entity.Scheduler;
import com.telink.ble.mesh.entity.Smart;
import com.tutk.IOTC.AVAPIs;
import com.tutk.IOTC.IOTCAPIs;
import java.io.Serializable;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import meshsdk.MeshEventHandler;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;

public class NodeInfo implements Serializable {
    public static final int ON_OFF_STATE_OFF = 0;
    public static final int ON_OFF_STATE_OFFLINE = -1;
    public static final int ON_OFF_STATE_ON = 1;
    public static final int checkOnOffDelayTime = 90000;
    private static final long serialVersionUID = 6088233872225595947L;
    public boolean bound = false;
    public List<BridgingTable> bridgingTableList = new ArrayList();
    public CompositionData compositionData = null;
    private boolean defaultBind = false;
    public byte[] deviceKey;
    public byte[] deviceUUID;
    public int elementCnt = 0;
    public boolean hasSetTime = false;
    public int hue;
    private AtomicBoolean isOnline = new AtomicBoolean(false);
    private long lastActiveTime = 0;
    public int light;
    public int lum = 100;
    public String macAddress;
    public int meshAddress;
    public List<Integer> netKeyIndexes = new ArrayList();
    private AtomicInteger onOff = new AtomicInteger(-1);
    public int protocolVersion;
    private PublishModel publishModel;
    private boolean relayEnable = true;
    private LightsRhythmService rhythmService;
    public int sat;
    public List<Scheduler> schedulers = new ArrayList();
    public boolean selected = false;
    public List<Smart> smarts = new ArrayList();
    public List<Integer> subList = new ArrayList();
    public boolean subnetBridgeEnabled = false;
    public int temp = 0;

    public void offlineTaskExecute() {
        MeshLog.i(this.macAddress + " 检测是否90ms未收到mesh消息");
        long dt = System.currentTimeMillis() - this.lastActiveTime;
        int timeout = checkOnOffDelayTime;
        if (getRhythmService() != null && getRhythmService().isMeshRhythm()) {
            MeshLog.logMusicRhythmWarn("当前正在mesh音乐律动，加大检测设备离线时长为:180000ms");
            timeout = 180000;
        }
        if (dt >= ((long) timeout)) {
            MeshLog.e("检测到设备:" + this.macAddress + "在" + (dt / 1000) + "秒未收到mesh消息，重置为设备离线");
            offlineReset();
            MeshEventHandler.getInstance().dispatchEvent(new NodeStatusChangedEvent(MeshEventHandler.getInstance(), NodeStatusChangedEvent.EVENT_TYPE_NODE_STATUS_CHANGED, this, 10000));
        }
    }

    public void offlineReset() {
        this.onOff.set(-1);
        this.isOnline.set(false);
        this.hasSetTime = false;
        MeshLog.d("mesh offlineReset hasSettime = false,mac:" + this.macAddress);
    }

    public void hearBeatTaskExecute() {
        MeshLog.e(this.macAddress + " 发送心跳包");
        SIGMesh.getInstance().heartBeat(this);
    }

    public int getOnOff() {
        return this.onOff.get();
    }

    public void setOnline(boolean b) {
        setOnline(b, true);
    }

    public LightsRhythmService getRhythmService() {
        if (this.rhythmService == null) {
            this.rhythmService = (LightsRhythmService) a.c().g(LightsRhythmService.class);
        }
        return this.rhythmService;
    }

    public void setOnline(boolean b, boolean isNotify) {
        this.isOnline.set(b);
        if (!b) {
            MeshLog.d("设备:" + this.macAddress + "离线");
            offlineReset();
        } else if (isNotify) {
            MeshLog.d("设备:" + this.macAddress + "在线，开启离线检测定时");
            startOfflineCheckTask(checkOnOffDelayTime);
        }
    }

    public boolean isOnline() {
        return this.isOnline.get();
    }

    public void setLastActiveTime(long lastActiveTime2) {
        this.lastActiveTime = lastActiveTime2;
    }

    public void startOfflineCheckTask(int timeout) {
        Handler handler = MeshEventHandler.getInstance().getOfflineCheckHandler(this.macAddress);
        handler.removeMessages(1);
        handler.removeMessages(0);
        if (getOnOff() == -1) {
            return;
        }
        if (timeout > 30000) {
            handler.sendMessageDelayed(Message.obtain(handler, 1, this.macAddress), (long) (timeout - 30000));
            handler.sendMessageDelayed(Message.obtain(handler, 1, this.macAddress), (long) (timeout + AVAPIs.AV_ER_INVALID_ARG));
            handler.sendMessageDelayed(Message.obtain(handler, 1, this.macAddress), (long) (timeout + IOTCAPIs.API_ER_ANDROID_NULL));
            handler.sendMessageDelayed(Message.obtain(handler, 0, this.macAddress), (long) timeout);
            return;
        }
        MeshLog.w("startOfflineCheckTask time  less than 30s:" + timeout);
    }

    public void cancelOfflineCheckTask() {
        MeshEventHandler.getInstance().getOfflineCheckHandler(this.macAddress).removeCallbacksAndMessages((Object) null);
    }

    public void setOnOff(int onOff2) {
        setOnOff(onOff2, true);
    }

    public void setOnOff(int onOff2, boolean isNotify) {
        setOnline(onOff2 != -1, isNotify);
        this.onOff.set(onOff2);
    }

    public boolean isPubSet() {
        return this.publishModel != null;
    }

    public PublishModel getPublishModel() {
        return this.publishModel;
    }

    public void setPublishModel(PublishModel model) {
        this.publishModel = model;
    }

    public boolean isRelayEnable() {
        return this.relayEnable;
    }

    public void setRelayEnable(boolean relayEnable2) {
        this.relayEnable = relayEnable2;
    }

    public Scheduler getSchedulerByIndex(byte index) {
        List<Scheduler> list = this.schedulers;
        if (list == null || list.size() == 0) {
            return null;
        }
        for (Scheduler scheduler : this.schedulers) {
            if (scheduler.getIndex() == index) {
                return scheduler;
            }
        }
        return null;
    }

    public void removeSchedulerByIndex(byte index) {
        List<Scheduler> list = this.schedulers;
        if (list != null && list.size() != 0) {
            Iterator<Scheduler> iterator = this.schedulers.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getIndex() == index) {
                    iterator.remove();
                }
            }
        }
    }

    public Scheduler getSchedulerBySmartId(int rouid) {
        List<Scheduler> list = this.schedulers;
        if (list == null || list.size() == 0) {
            return null;
        }
        for (Scheduler scheduler : this.schedulers) {
            if (scheduler.smartId == rouid) {
                return scheduler;
            }
        }
        return null;
    }

    public void saveScheduler(Scheduler scheduler) {
        if (this.schedulers == null) {
            ArrayList arrayList = new ArrayList();
            this.schedulers = arrayList;
            arrayList.add(scheduler);
            return;
        }
        for (int i = 0; i < this.schedulers.size(); i++) {
            if (this.schedulers.get(i).getIndex() == scheduler.getIndex()) {
                this.schedulers.set(i, scheduler);
                return;
            }
        }
        this.schedulers.add(scheduler);
    }

    public byte allocSchedulerIndex() {
        List<Scheduler> list = this.schedulers;
        if (list == null || list.size() == 0) {
            return 0;
        }
        byte i = 0;
        while (i <= 15) {
            for (Scheduler scheduler : this.schedulers) {
                if (scheduler.getIndex() == i) {
                    i = (byte) (i + 1);
                }
            }
            return i;
        }
        return -1;
    }

    public void removeSmartByAddress(byte smartAddress) {
        List<Smart> list = this.smarts;
        if (list != null && list.size() != 0) {
            Iterator<Smart> iterator = this.smarts.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().smartAddress == smartAddress) {
                    iterator.remove();
                }
            }
        }
    }

    public Smart getSmartBySmartId(int smartId) {
        List<Smart> list = this.smarts;
        if (list == null || list.size() == 0) {
            return null;
        }
        for (Smart smart : this.smarts) {
            if (smart.smartId == smartId) {
                return smart;
            }
        }
        return null;
    }

    public void saveSmart(Smart sm) {
        if (this.smarts == null) {
            ArrayList arrayList = new ArrayList();
            this.smarts = arrayList;
            arrayList.add(sm);
            return;
        }
        for (int i = 0; i < this.smarts.size(); i++) {
            if (this.smarts.get(i).smartAddress == sm.smartAddress) {
                this.smarts.set(i, sm);
                return;
            }
        }
        this.smarts.add(sm);
    }

    public byte allocSmartAddress() {
        List<Smart> list = this.smarts;
        if (list == null || list.size() == 0) {
            return 0;
        }
        byte i = 0;
        while (i <= 15) {
            for (Smart scheduler : this.smarts) {
                if (scheduler.smartAddress == i) {
                    i = (byte) (i + 1);
                }
            }
            return i;
        }
        return -1;
    }

    public String getOnOffDesc() {
        if (getOnOff() == 1) {
            return "ON";
        }
        if (getOnOff() == 0) {
            return "OFF";
        }
        if (getOnOff() == -1) {
            return "OFFLINE";
        }
        return LDNetUtil.NETWORKTYPE_INVALID;
    }

    public List<Integer> getOnOffEleAdrList() {
        if (this.compositionData == null) {
            return null;
        }
        List<Integer> addressList = new ArrayList<>();
        int eleAdr = this.meshAddress;
        for (CompositionData.Element element : this.compositionData.elements) {
            List<Integer> list = element.sigModels;
            if (list != null) {
                Iterator<Integer> it = list.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (it.next().intValue() == MeshSigModel.SIG_MD_G_ONOFF_S.modelId) {
                            addressList.add(Integer.valueOf(eleAdr));
                            eleAdr++;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            eleAdr++;
        }
        return addressList;
    }

    public int getTargetEleAdr(int tarModelId) {
        CompositionData compositionData2 = this.compositionData;
        if (compositionData2 == null) {
            return this.meshAddress;
        }
        int eleAdr = this.meshAddress;
        for (CompositionData.Element element : compositionData2.elements) {
            List<Integer> list = element.sigModels;
            if (list != null) {
                for (Integer intValue : list) {
                    if (intValue.intValue() == tarModelId) {
                        return eleAdr;
                    }
                }
            }
            List<Integer> list2 = element.vendorModels;
            if (list2 != null) {
                for (Integer intValue2 : list2) {
                    if (intValue2.intValue() == tarModelId) {
                        return eleAdr;
                    }
                }
                continue;
            }
            eleAdr++;
        }
        return this.meshAddress;
    }

    public SparseBooleanArray getLumEleInfo() {
        if (this.compositionData == null) {
            return null;
        }
        int eleAdr = this.meshAddress;
        SparseBooleanArray result = new SparseBooleanArray();
        for (CompositionData.Element element : this.compositionData.elements) {
            List<Integer> list = element.sigModels;
            if (list != null) {
                boolean levelSupport = false;
                boolean lumSupport = false;
                for (Integer intValue : list) {
                    int modelId = intValue.intValue();
                    if (modelId == MeshSigModel.SIG_MD_LIGHTNESS_S.modelId) {
                        lumSupport = true;
                    }
                    if (modelId == MeshSigModel.SIG_MD_G_LEVEL_S.modelId) {
                        levelSupport = true;
                    }
                }
                if (lumSupport) {
                    result.append(eleAdr, levelSupport);
                    return result;
                }
            }
            eleAdr++;
        }
        return null;
    }

    public SparseBooleanArray getTempEleInfo() {
        if (this.compositionData == null) {
            return null;
        }
        int eleAdr = this.meshAddress;
        SparseBooleanArray result = new SparseBooleanArray();
        for (CompositionData.Element element : this.compositionData.elements) {
            List<Integer> list = element.sigModels;
            if (list != null) {
                boolean levelSupport = false;
                boolean tempSupport = false;
                for (Integer intValue : list) {
                    int modelId = intValue.intValue();
                    if (modelId == MeshSigModel.SIG_MD_LIGHT_CTL_TEMP_S.modelId) {
                        tempSupport = true;
                    }
                    if (modelId == MeshSigModel.SIG_MD_G_LEVEL_S.modelId) {
                        levelSupport = true;
                    }
                }
                if (tempSupport) {
                    result.append(eleAdr, levelSupport);
                    return result;
                }
            }
            eleAdr++;
        }
        return null;
    }

    public String getPidDesc() {
        if (!this.bound || this.compositionData == null) {
            return "(unbound)";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("cid-");
        int i = this.compositionData.cid;
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        sb.append(e.b(MeshUtils.l(i, 2, byteOrder), ""));
        sb.append(" pid-");
        sb.append(e.b(MeshUtils.l(this.compositionData.pid, 2, byteOrder), ""));
        return sb.toString();
    }

    public boolean isDefaultBind() {
        return this.defaultBind;
    }

    public void setDefaultBind(boolean defaultBind2) {
        this.defaultBind = defaultBind2;
    }

    public boolean isLpn() {
        CompositionData compositionData2 = this.compositionData;
        return compositionData2 != null && compositionData2.lowPowerSupport();
    }

    public boolean isOffline() {
        return getOnOff() == -1;
    }
}
