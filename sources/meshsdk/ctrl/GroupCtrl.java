package meshsdk.ctrl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.alibaba.android.arouter.launcher.a;
import com.leedarson.serviceimpl.reporters.groupControl.b;
import com.leedarson.serviceinterface.BleMeshService;
import com.telink.ble.mesh.core.message.MeshMessage;
import com.telink.ble.mesh.core.message.MeshSigModel;
import com.telink.ble.mesh.core.message.config.ConfigStatus;
import com.telink.ble.mesh.core.message.config.ModelSubscriptionSetMessage;
import com.telink.ble.mesh.core.message.config.ModelSubscriptionStatusMessage;
import com.telink.ble.mesh.core.message.generic.OnOffSetMessage;
import com.telink.ble.mesh.core.message.lighting.CtlTemperatureSetMessage;
import com.telink.ble.mesh.core.message.lighting.HslSetMessage;
import com.telink.ble.mesh.core.message.lighting.LightnessSetMessage;
import com.telink.ble.mesh.entity.MsgExtra;
import com.telink.ble.mesh.foundation.Event;
import com.telink.ble.mesh.foundation.EventListener;
import com.telink.ble.mesh.foundation.MeshService;
import com.telink.ble.mesh.foundation.event.StatusNotificationEvent;
import meshsdk.BaseResp;
import meshsdk.MeshEventHandler;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshGroupCallback;
import meshsdk.model.MeshInfo;
import meshsdk.model.NodeInfo;
import meshsdk.util.LDSMeshUtil;
import meshsdk.util.LDSModel;
import meshsdk.util.MeshConstants;
import meshsdk.util.UnitConvert;
import org.json.JSONException;
import org.json.JSONObject;

public class GroupCtrl extends CtrlLifecycle implements EventListener<String> {
    private static final String TAG = "GroupCtrl";
    private BleMeshService bleMeshService;
    private Runnable clearDelayRhythmRef = new b(this);
    private NodeInfo deviceInfo;
    private Handler handler;
    private int meshAddress;
    private MeshGroupCallback meshGroupCallback;
    private int modelIndex = 0;
    private MeshSigModel[] models;
    private int opGroupAdr;
    private int opType;
    public long time;

    public GroupCtrl(SIGMesh sigMesh) {
        super(sigMesh);
        onCreate();
        this.handler = new Handler(Looper.getMainLooper());
    }

    public void onCreate() {
        MeshEventHandler.getInstance().addEventListener(ModelSubscriptionStatusMessage.class.getName(), this);
    }

    public void onDestroy() {
        MeshEventHandler.getInstance().removeEventListener(this);
    }

    public void registerGroup(NodeInfo node, int groupAddr, int meshAddress2, MeshGroupCallback meshGroupCallback2) {
        if (node == null) {
            logAddGroup("registerGroup node null");
            return;
        }
        this.deviceInfo = node;
        this.meshAddress = meshAddress2;
        this.opGroupAdr = groupAddr;
        this.opType = 0;
        this.meshGroupCallback = meshGroupCallback2;
        this.modelIndex = 0;
        if (node.protocolVersion >= SIGMesh.NEW_PROTOCOL5) {
            this.models = MeshSigModel.getDefaultSubListAboveProtocalVersion5();
        } else {
            this.models = MeshSigModel.getDefaultSubList();
        }
        setNextModel();
    }

    public void unregisterGroup(NodeInfo node, int groupAddr, int meshAddress2, MeshGroupCallback meshGroupCallback2) {
        this.deviceInfo = node;
        this.meshAddress = meshAddress2;
        this.opGroupAdr = groupAddr;
        this.opType = 1;
        this.meshGroupCallback = meshGroupCallback2;
        this.modelIndex = 0;
        if (node.protocolVersion >= SIGMesh.NEW_PROTOCOL5) {
            this.models = MeshSigModel.getDefaultSubListAboveProtocalVersion5();
        } else {
            this.models = MeshSigModel.getDefaultSubList();
        }
        setNextModel();
    }

    public BleMeshService getBleMeshService() {
        if (this.bleMeshService == null) {
            this.bleMeshService = (BleMeshService) a.c().g(BleMeshService.class);
        }
        return this.bleMeshService;
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$new$0 */
    public /* synthetic */ void b() {
        getBleMeshService().clearDelayRhythmRef();
    }

    public JSONObject controlGroup(int groupAddr, int modelId, Object value, int groupId) {
        if (getBleMeshService().setDelayRhythmRef("正在控制指令")) {
            this.handler.removeCallbacks(this.clearDelayRhythmRef);
            this.handler.postDelayed(new a(this, groupAddr, modelId, value, groupId), 200);
            this.handler.postDelayed(this.clearDelayRhythmRef, 500);
        } else {
            lambda$controlGroup$1(groupAddr, modelId, value, groupId);
        }
        return BaseResp.generatorSuccessResp();
    }

    /* renamed from: controlGroupInner */
    public JSONObject lambda$controlGroup$1(int groupAddr, int modelId, Object value, int groupId) {
        int i = modelId;
        Object obj = value;
        long taskId = b.b().a(groupId, groupAddr, i, obj);
        switch (i) {
            case 4096:
                onOffCtrl(taskId, groupAddr, ((Integer) obj).intValue(), groupId);
                break;
            case LDSModel.MODEL_BRIGHTNESS_CTRL /*4864*/:
                brightnessCtrl(taskId, groupAddr, ((Integer) obj).intValue(), groupId);
                break;
            case LDSModel.MODEL_TEMP_CTRL /*4867*/:
                tempCtrl(taskId, groupAddr, ((Integer) obj).intValue(), groupId);
                break;
            case LDSModel.MODEL_HSL_CTRL /*4871*/:
                try {
                    int[] hsl = LDSMeshUtil.json2Hsl(new JSONObject(value.toString()));
                    hslCtrl(taskId, groupAddr, hsl[0], hsl[1], hsl[2], groupId);
                    break;
                } catch (JSONException e) {
                    e.printStackTrace();
                    break;
                }
        }
        return BaseResp.generatorSuccessResp();
    }

    private void hslCtrl(long taskId, int groupAddr, int hue, int sat, int lightness, int groupId) {
        int i = groupAddr;
        int i2 = hue;
        int i3 = sat;
        int i4 = lightness;
        int i5 = groupId;
        MeshLog.d("group :" + i + ",HSL:" + i2 + "," + i3 + "," + i4);
        HslSetMessage hslSetMessage = HslSetMessage.I(groupAddr, SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex(), lightness, hue, sat, false, 0);
        MsgExtra msgExtra = new MsgExtra("组颜色控制 addr:" + i + ",groupId:" + i5 + ",HSL:" + i2 + "," + i3 + "," + i4, MeshConstants.TRACE_ID_CONTROL);
        msgExtra.b(i5);
        msgExtra.g = MeshConstants.EVENT_GROUP_CONTROL;
        msgExtra.d = String.valueOf(groupAddr);
        msgExtra.h = taskId;
        hslSetMessage.c(msgExtra);
        MeshService.k().t(hslSetMessage);
    }

    private void onOffCtrl(long taskId, int groupAddr, int value, int groupId) {
        MeshLog.d("onOffCtrl groupAddr :" + groupAddr + ",value:" + value + ",groupId:" + groupId);
        OnOffSetMessage message = OnOffSetMessage.I(groupAddr, SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex(), value, false, 0);
        MsgExtra msgExtra = new MsgExtra("组开关控制 addr:" + groupAddr + ",groupId:" + groupId + ",onOff:" + value + ",ack:" + false, MeshConstants.TRACE_ID_CONTROL);
        msgExtra.b(groupId);
        msgExtra.g = MeshConstants.EVENT_GROUP_CONTROL;
        msgExtra.d = String.valueOf(groupAddr);
        msgExtra.h = taskId;
        message.c(msgExtra);
        MeshMessage sendingReliableMessage = MeshMessagePool.getInstance().getCurrentSendingReliableMessage();
        if (sendingReliableMessage != null && !(sendingReliableMessage instanceof OnOffSetMessage)) {
            MeshLog.i("发送组控开关消息,取消当前正在进行处理的mesh消息:" + sendingReliableMessage.getClass().getSimpleName() + String.format(" propertityId:0x%04X,opcode:0x%04X", new Object[]{Integer.valueOf(sendingReliableMessage.m()), Integer.valueOf(sendingReliableMessage.k())}));
            MeshMessagePool.getInstance().cancelCurrentSendingReliableMessage();
        }
        MeshService.k().t(message);
    }

    private void tempCtrl(long taskId, int groupAddr, int temp, int groupId) {
        MeshLog.d("group :" + groupAddr + ",tempCtrl:" + temp);
        MeshMessage meshMessage = CtlTemperatureSetMessage.I(groupAddr, SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex(), temp, 0, false, 0);
        MsgExtra msgExtra = new MsgExtra("组色温控制 addr:" + groupAddr + ",groupId:" + groupId + ",temp:" + temp, MeshConstants.TRACE_ID_CONTROL);
        msgExtra.b(groupId);
        msgExtra.g = MeshConstants.EVENT_GROUP_CONTROL;
        msgExtra.d = String.valueOf(groupAddr);
        msgExtra.h = taskId;
        meshMessage.c(msgExtra);
        MeshService.k().t(meshMessage);
    }

    private void brightnessCtrl(long taskId, int groupAddr, int lum, int groupId) {
        MeshLog.d("group :" + groupAddr + ",brightnessCtrl:" + lum);
        int lum2 = Math.max(1, lum);
        MeshMessage meshMessage = LightnessSetMessage.I(groupAddr, SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex(), UnitConvert.lum2lightness(lum2), false, 0);
        MsgExtra msgExtra = new MsgExtra("组亮度控制 addr:" + groupAddr + ",groupId:" + groupId + ",lum:" + lum2, MeshConstants.TRACE_ID_CONTROL);
        msgExtra.b(groupId);
        msgExtra.g = MeshConstants.EVENT_GROUP_CONTROL;
        msgExtra.d = String.valueOf(groupAddr);
        msgExtra.h = taskId;
        meshMessage.c(msgExtra);
        MeshService.k().t(meshMessage);
    }

    private void setNextModel() {
        MeshMessage groupingMessage;
        int i = this.modelIndex;
        MeshSigModel[] meshSigModelArr = this.models;
        if (i > meshSigModelArr.length - 1) {
            if (this.opType == 0) {
                this.deviceInfo.subList.add(Integer.valueOf(this.opGroupAdr));
            } else {
                this.deviceInfo.subList.remove(Integer.valueOf(this.opGroupAdr));
            }
            MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
            Context context = SIGMesh.getInstance().getContext();
            meshInfo.saveOrUpdate(context, "  GroupCtrl.setNextModel  macAddress=" + this.deviceInfo.macAddress);
            if (this.meshGroupCallback != null) {
                logAddGroup("callback group member success 時序耗時onsuccess:" + (System.currentTimeMillis() - this.time));
                this.meshGroupCallback.onSuccess(this.meshAddress, this.opGroupAdr, -1);
                return;
            }
            return;
        }
        int adr = this.deviceInfo.getTargetEleAdr(meshSigModelArr[i].modelId);
        if (adr == -1) {
            adr = this.meshAddress;
        }
        int eleAdr = adr;
        int i2 = this.modelIndex;
        MeshSigModel[] meshSigModelArr2 = this.models;
        if (i2 != meshSigModelArr2.length - 1) {
            int i3 = this.meshAddress;
            groupingMessage = ModelSubscriptionSetMessage.I(i3, this.opType, eleAdr, this.opGroupAdr, meshSigModelArr2[i2].modelId, true);
        } else if (meshSigModelArr2.length == 1) {
            logAddGroup(this.deviceInfo.macAddress + " 只要一条" + getOpTypeStr() + "指令，optype:" + this.opType + ",eleAdr:" + eleAdr + ",opGroupAdr:" + this.opGroupAdr + ",meshAddress:" + this.meshAddress + ",modelid:" + this.models[this.modelIndex].modelId);
            groupingMessage = ModelSubscriptionSetMessage.I(this.meshAddress, this.opType, eleAdr, this.opGroupAdr, this.models[this.modelIndex].modelId, true);
        } else {
            int i4 = this.meshAddress;
            groupingMessage = ModelSubscriptionSetMessage.I(i4, this.opType, eleAdr, this.opGroupAdr, meshSigModelArr2[i2].modelId, false);
        }
        groupingMessage.d("建组/删组");
        MeshMessage sendingReliableMessage = MeshMessagePool.getInstance().getCurrentSendingReliableMessage();
        if (sendingReliableMessage != null && !(sendingReliableMessage instanceof ModelSubscriptionSetMessage)) {
            logAddGroup("时序2: 发送:" + getOpTypeStr() + " 消息(第" + (this.modelIndex + 1) + "条指令前,取消当前正在进行处理的mesh消息:" + sendingReliableMessage.getClass().getSimpleName());
            MeshMessagePool.getInstance().cancelCurrentSendingReliableMessage();
        }
        this.time = System.currentTimeMillis();
        MeshMessagePool.getInstance().addAndSend(groupingMessage);
    }

    public String getOpTypeStr() {
        if (this.opType == 0) {
            return "建组";
        }
        return "删除组" + "组地址:" + this.opGroupAdr;
    }

    public void performed(Event<String> event) {
        logAddGroup("performed modelIndex :" + this.modelIndex);
        if (event.getType().equals(ModelSubscriptionStatusMessage.class.getName())) {
            ModelSubscriptionStatusMessage statusMessage = (ModelSubscriptionStatusMessage) ((StatusNotificationEvent) event).a().d();
            if (statusMessage.c() == ConfigStatus.SUCCESS.code) {
                logAddGroup("performed modelIndex : SUCCESS");
                this.modelIndex++;
                if (this.opType == 0) {
                    logAddGroup("时序耗時:" + (System.currentTimeMillis() - this.time));
                }
                setNextModel();
                return;
            }
            logAddGroup("performed modelIndex  fail：" + this.modelIndex);
            int code = 401;
            String msg = "group model subscribe fail ";
            if (statusMessage.c() == 5) {
                code = BaseResp.ERR_GROUP_LIMIT;
                msg = msg + "组达到上限";
            }
            MeshGroupCallback meshGroupCallback2 = this.meshGroupCallback;
            if (meshGroupCallback2 != null) {
                meshGroupCallback2.onFail(code, msg + ",model meshAddress:" + this.meshAddress + ",model index:" + this.modelIndex + ",status:" + statusMessage.c(), this.meshAddress, this.opGroupAdr, -1);
            }
        }
    }

    public void logAddGroup(String msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("GroupCtrl mac:");
        NodeInfo nodeInfo = this.deviceInfo;
        sb.append(nodeInfo != null ? nodeInfo.macAddress : "");
        sb.append(msg);
        sb.append(",thread:");
        sb.append(Thread.currentThread().getName());
        MeshLog.logAddGroup(sb.toString());
    }
}
