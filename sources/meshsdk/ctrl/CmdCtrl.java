package meshsdk.ctrl;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.view.InputDeviceCompat;
import com.alibaba.android.arouter.launcher.a;
import com.leedarson.base.utils.e;
import com.leedarson.base.utils.w;
import com.leedarson.bean.IRhyDevice;
import com.leedarson.serviceimpl.reporters.detectionmode.a;
import com.leedarson.serviceimpl.reporters.preset.b;
import com.leedarson.serviceimpl.reporters.wallLamp.a;
import com.leedarson.serviceimpl.reporters.wallLamp.c;
import com.leedarson.serviceimpl.reporters.wallLamp.d;
import com.leedarson.serviceinterface.BleMeshService;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.luck.picture.lib.camera.CustomCameraView;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.MeshMessage;
import com.telink.ble.mesh.core.message.MeshSigModel;
import com.telink.ble.mesh.core.message.NotificationMessage;
import com.telink.ble.mesh.core.message.config.ConfigStatus;
import com.telink.ble.mesh.core.message.config.ModelSubscriptionGetMessage;
import com.telink.ble.mesh.core.message.config.ModelSubscriptionListStatusMessage;
import com.telink.ble.mesh.core.message.generic.OnOffSetMessage;
import com.telink.ble.mesh.core.message.lighting.CtlTemperatureSetMessage;
import com.telink.ble.mesh.core.message.lighting.HslSetMessage;
import com.telink.ble.mesh.core.message.lighting.LightnessSetMessage;
import com.telink.ble.mesh.core.message.time.TimeGetMessage;
import com.telink.ble.mesh.core.message.time.TimeSetMessage;
import com.telink.ble.mesh.entity.MsgExtra;
import com.telink.ble.mesh.foundation.Event;
import com.telink.ble.mesh.foundation.EventListener;
import com.telink.ble.mesh.foundation.MeshService;
import com.telink.ble.mesh.foundation.event.StatusNotificationEvent;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import meshsdk.BaseResp;
import meshsdk.MeshEventHandler;
import meshsdk.MeshLog;
import meshsdk.MeshLogNew;
import meshsdk.SIGMesh;
import meshsdk.cache.CacheHanderMsgWrapper;
import meshsdk.cache.CacheHandler;
import meshsdk.cache.cachemodule.CurrentDetectionModeCacheInstance;
import meshsdk.cache.cachemodule.DetectionModeDetailCacheInstance;
import meshsdk.callback.CustomSmartCallback;
import meshsdk.callback.MeshCustomcmdCallback;
import meshsdk.callback.MeshGlobalCallback;
import meshsdk.datamgr.LDSDeviceApi;
import meshsdk.model.MeshInfo;
import meshsdk.model.NodeInfo;
import meshsdk.model.SceneRulesWrap;
import meshsdk.model.json.CustomEffectMode;
import meshsdk.model.json.DSTRule;
import meshsdk.model.json.DetectMode;
import meshsdk.model.json.RhythmStopAttrBean;
import meshsdk.model.json.RoutineRule;
import meshsdk.model.json.SecuAlarmRule;
import meshsdk.util.LDSMeshUtil;
import meshsdk.util.LDSModel;
import meshsdk.util.MeshConstants;
import meshsdk.util.UnitConvert;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CmdCtrl extends CtrlLifecycle implements EventListener<String> {
    private static final int CID = 529;
    private static final int ERR = -1;
    private static final int OPCODE_GET = 241;
    private static final int OPCODE_NO_ACK = 243;
    private static final int OPCODE_RESP = 242;
    private static final int OPCODE_SET = 240;
    public static final int PROPERTY_RHYTHM_V1 = 1;
    public static final int PROPERTY_RHYTHM_V2 = 4;
    public static final int PROPERTY_RHYTHM_V3 = 6;
    public static final String TAG = "CmdCtrl";
    private final int PROPERTY_ALARM_STATUS = 521;
    private final int PROPERTY_BATTERY = 4096;
    private final int PROPERTY_CAIGUANLIANDONG = 2;
    private final int PROPERTY_COLOR_MODE = 256;
    private final int PROPERTY_CUSTOMER_CODE = 3;
    private final int PROPERTY_CUSTOM_EFFECT_MODE = 522;
    private final int PROPERTY_CUSTOM_SCENE_CTRL = InputDeviceCompat.SOURCE_DPAD;
    private final int PROPERTY_DATA_CODE = 1;
    private final int PROPERTY_DETECTION_MODE = 265;
    private final int PROPERTY_DETECTION_MODE_NEW = 771;
    private final int PROPERTY_DEVICE_STATUS_NOTIFY = 770;
    private final int PROPERTY_DST = 260;
    private final int PROPERTY_EFFECT_MODE = 257;
    private final int PROPERTY_ENERGY_SAVE_MODE = 515;
    private final int PROPERTY_GET_ENERGY_CONSUMPTION = 773;
    private final int PROPERTY_GET_ILLUMINATIONSTATE = 774;
    private final int PROPERTY_GET_MULTI_STATUS = AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTOP;
    private final int PROPERTY_HEART_BEAT = 517;
    private final int PROPERTY_IPL_LINKAGE = 264;
    private final int PROPERTY_MODEL_ID = 2;
    private final int PROPERTY_PIR_SENSITIVITY = 772;
    private final int PROPERTY_RHYTHM = 262;
    private final int PROPERTY_RHYTHM_STATUS = 263;
    private final int PROPERTY_RHYTHM_STOP_ATTR = 519;
    private final int PROPERTY_SECURITY_ALARM = 514;
    private final int PROPERTY_SECURITY_TRIGGER = 516;
    private final int PROPERTY_SET_RHYTHM_THEME = 5;
    private final int PROPERTY_SINGLE_CAPPED_ALARM = 520;
    private final int PROPERTY_SMART = 261;
    private final int PROPERTY_SMART_V2 = 512;
    private final int PROPERTY_SUNRISE = CustomCameraView.BUTTON_STATE_ONLY_RECORDER;
    private final int PROPERTY_SUNSET = CustomCameraView.BUTTON_STATE_BOTH;
    private final int PROPERTY_SWITCH_DETECTION_MODE = 267;
    private final int PROPERTY_TEMPORARY_CONTROL_DURATION = 266;
    private final int PROPERTY_VERSION = 0;
    private final int PROPERTY_WALL_WASHER_LIGHT_STATUS = 523;
    private final int P_TYPE_ARRAY = 4;
    private final int P_TYPE_BOOLEAN = 0;
    private final int P_TYPE_NUMBER = 1;
    private final int P_TYPE_OBJECT = 3;
    private final int P_TYPE_STRING = 2;
    private final int STATE_CODE_FAIL = 1;
    private final int STATE_CODE_PARAM_ERR = 2;
    private final int STATE_CODE_SUCCESS = 0;
    private final int TID_GET_COLOR_MODE = 2;
    private final int TID_GET_EFFECT_MODE = 4;
    private final int TID_GET_IPL_LINKAGE = 8;
    private final int TID_GET_RHYTHM_STATUS = 7;
    private final int TID_GET_SINGLE_CAPPED_ALARM = 18;
    private final int TID_GET_TEMPORARY_CONTROL_DURATION = 15;
    private final int TID_PPERFORM_IPL_LINKAGE = 9;
    private final int TID_SET_DST = 5;
    private final int TID_SET_RHYTHM = 10;
    private final int TID_SET_RHYTHM_ENABLE = 11;
    private final int TID_SET_SINGLE_CAPPED_ALARM = 19;
    private final int TID_SET_SMART = 6;
    private Set arrayResponseWithTipCallback = new HashSet();
    private Set arrayResponseWithoutStatus = new HashSet();
    private MeshGlobalCallback bleMeshService = ((MeshGlobalCallback) a.c().g(BleMeshService.class));
    private HashMap<Object, MeshCustomcmdCallback> callbackHashMap = new HashMap<>();
    private byte globalTid = 40;
    private LDSDeviceApi ldsDeviceApi = new LDSDeviceApi();

    private byte getAndIncreaseTID() {
        byte temp = this.globalTid;
        byte b = this.globalTid;
        if (b == Byte.MAX_VALUE) {
            this.globalTid = 40;
        } else {
            this.globalTid = (byte) (b + 1);
        }
        return temp;
    }

    public CmdCtrl(SIGMesh sigMesh) {
        super(sigMesh);
        initResponseWithoutStatus();
        initResponseWithTidCallback();
        onCreate();
    }

    public void onCreate() {
        MeshEventHandler.getInstance().addEventListener("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_NOTIFICATION_MESSAGE_UNKNOWN", this);
        MeshEventHandler.getInstance().addEventListener(ModelSubscriptionListStatusMessage.class.getName(), this);
    }

    private void initResponseWithoutStatus() {
        this.arrayResponseWithoutStatus.add(771);
        this.arrayResponseWithoutStatus.add(Integer.valueOf(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTOP));
        this.arrayResponseWithoutStatus.add(770);
        this.arrayResponseWithoutStatus.add(774);
        this.arrayResponseWithoutStatus.add(772);
        this.arrayResponseWithoutStatus.add(773);
    }

    private void initResponseWithTidCallback() {
        this.arrayResponseWithTipCallback.add(11);
    }

    public boolean getHwVersion(int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        return sendCustomMessage(meshAddr, createOpcode(OPCODE_GET), createOpcode(OPCODE_RESP), new byte[]{ldsTID, 0, 0}, 0, 2);
    }

    @Deprecated
    public boolean getHwTime(int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        MeshLog.i("查询 设备时间time status addr:" + meshAddr);
        TimeGetMessage timeGetMessage = TimeGetMessage.I(meshAddr, SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex(), 0);
        timeGetMessage.e(2);
        MeshMessagePool.getInstance().addAndSend(timeGetMessage);
        return true;
    }

    public boolean setHwTime(int meshAddr) {
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        long time = MeshUtils.j();
        int offset = UnitConvert.getZoneOffset();
        TimeSetMessage timeSetMessage = TimeSetMessage.I(meshAddr, meshInfo.getDefaultAppKeyIndex(), time, offset, 0);
        MeshLog.i("TIME_STATUS-mesh上线成功,往0xffff地址广播同步设置时间:" + meshAddr + ",taiSeconds:" + LDSMeshUtil.formatDate(1000 * time) + ",offset:" + offset);
        timeSetMessage.D(-1);
        timeSetMessage.e(NeedPermissionEvent.PER_ANDROID_S_BLE);
        MeshMessagePool.getInstance().addAndSend(timeSetMessage);
        return true;
    }

    public boolean setHwTime(NodeInfo nodeInfo) {
        long time = MeshUtils.j();
        int offset = UnitConvert.getZoneOffset();
        TimeSetMessage timeSetMessage = TimeSetMessage.I(nodeInfo.meshAddress, SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex(), time, offset, 0);
        MeshLogNew.meshMsg("TIME_STATUS-设备上线发送mesh SetTime消息到消息队列 mac:" + nodeInfo.macAddress + ",meshAddress:" + nodeInfo.meshAddress + ",date:" + LDSMeshUtil.formatDate(1000 * time) + ",offset:" + offset + ",同时标志该节点标为时间已同步");
        timeSetMessage.D(-1);
        timeSetMessage.e(NeedPermissionEvent.PER_ANDROID_S_BLE);
        MeshMessagePool.getInstance().addAndSend(timeSetMessage);
        return true;
    }

    public boolean getColorMode(int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        putCustomCallback(meshAddr, (byte) 2, customcmdCallback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(256);
        return sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{2, propertyIdBytes[0], propertyIdBytes[1]}, 256, 2);
    }

    private byte[] convertPropertyBytes(int propertyId) {
        return new byte[]{(byte) (propertyId & 255), (byte) ((propertyId >> 8) & 255)};
    }

    public boolean setEffectMode(String macAddress, int effectId, long durationMill, int action, int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        String str = macAddress;
        int i = effectId;
        int i2 = action;
        int i3 = meshAddr;
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(i3, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] effectIdBytes = int2ByteArr((long) i, 2);
        byte[] durationBytes = int2ByteArr(durationMill, 4);
        byte[] params = new byte[12];
        params[0] = ldsTID;
        params[1] = 1;
        params[2] = 1;
        params[3] = 1;
        params[5] = effectIdBytes[0];
        params[6] = effectIdBytes[1];
        boolean newProtocal = true;
        if (TextUtils.isEmpty(macAddress)) {
            List<NodeInfo> nodeInfos = LDSMeshUtil.getDevicesInGroup(meshAddr);
            if (nodeInfos != null) {
                Iterator<NodeInfo> it = nodeInfos.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    NodeInfo nodeInfo = it.next();
                    byte ldsTID2 = ldsTID;
                    if (nodeInfo.protocolVersion < SIGMesh.NEW_PROTOCOL6) {
                        MeshLog.e("setEffectMode group preset 存在低版本设备:" + nodeInfo.macAddress + ",protocolVersion:" + nodeInfo.protocolVersion);
                        newProtocal = false;
                        break;
                    }
                    ldsTID = ldsTID2;
                }
            }
        } else {
            if (LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, str).protocolVersion < SIGMesh.NEW_PROTOCOL6) {
                newProtocal = false;
            }
        }
        if (!newProtocal) {
            params[4] = 7;
            if (i2 == 0) {
                durationBytes = new byte[]{-1, -1, -1, -1};
            }
            System.arraycopy(durationBytes, 0, params, 7, durationBytes.length);
            params[11] = (byte) i2;
            MeshLog.i("setEffectMode 走旧版本");
        } else {
            params[4] = 2;
            MeshLog.i("setEffectMode 新版本，不需要传duration与action");
        }
        String event = !TextUtils.isEmpty(macAddress) ? MeshConstants.EVENT_DEVICE_PRESET : MeshConstants.EVENT_GROUP_PRESET;
        String key = !TextUtils.isEmpty(macAddress) ? str : String.valueOf(meshAddr);
        if (i2 == 0) {
            b b = b.b();
            StringBuilder sb = new StringBuilder();
            int i4 = rspOpcode;
            sb.append("effectId:");
            sb.append(i);
            sb.append(",action:");
            sb.append(i2);
            b.a(event, key, sb.toString());
        }
        MeshMessage message = createVendorMessage(i3, opcode, -1, params);
        message.G(true);
        message.D(-1);
        message.B(257);
        message.e(NeedPermissionEvent.PER_ANDROID_S_BLE);
        MsgExtra msgExtra = new MsgExtra("preset控制 addr:" + i3 + ",mac:" + str + ",effectId:" + i, MeshConstants.TRACE_ID_CONTROL);
        msgExtra.d = key;
        msgExtra.g = event;
        message.c(msgExtra);
        MeshMessagePool.getInstance().addAndSend(message);
        return true;
    }

    public boolean setCustomEffectMode(String macAddress, CustomEffectMode customEffectMode, int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        CustomEffectMode customEffectMode2 = customEffectMode;
        int i = meshAddr;
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(i, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(522);
        byte[] payloadId = {(byte) customEffectMode2.customizeEffectId};
        List<CustomEffectMode.Effect> list = customEffectMode2.effects;
        int count = list != null ? list.size() : 0;
        byte payloadSetting = toOneByte(customEffectMode2.type, count, 4);
        byte[] payloadTime = {(byte) customEffectMode.getTime()};
        byte[] params = new byte[((count * 2) + 6)];
        params[0] = ldsTID;
        params[1] = propertyIdBytes[0];
        int i2 = 2;
        params[2] = propertyIdBytes[1];
        params[3] = payloadId[0];
        params[4] = payloadSetting;
        params[5] = payloadTime[0];
        List<CustomEffectMode.Effect> list2 = customEffectMode2.effects;
        if (list2 == null || list2.size() <= 0) {
            int i3 = rspOpcode;
        } else {
            ArrayList<byte[]> countArray = new ArrayList<>();
            int desPos = 6;
            for (CustomEffectMode.Effect effect : customEffectMode2.effects) {
                byte ldsTID2 = ldsTID;
                byte[] temp = new byte[i2];
                temp[0] = toOneByte(effect.type, effect.color, 7);
                temp[1] = (byte) effect.dimming;
                countArray.add(temp);
                int desPos2 = desPos;
                System.arraycopy(temp, 0, params, desPos2, temp.length);
                desPos = desPos2 + temp.length;
                CustomEffectMode customEffectMode3 = customEffectMode;
                MeshCustomcmdCallback meshCustomcmdCallback = customcmdCallback;
                ldsTID = ldsTID2;
                rspOpcode = rspOpcode;
                i2 = 2;
            }
            int i4 = rspOpcode;
            int i5 = desPos;
        }
        MeshMessage message = createVendorMessage(i, opcode, -1, params);
        message.D(-1);
        message.B(522);
        message.e(4);
        message.c(new MsgExtra("自定义灯效执行 addr:" + i + ",mac:" + macAddress, MeshConstants.TRACE_ID_CONTROL));
        MeshMessagePool.getInstance().addAndSend(message);
        return true;
    }

    public boolean getCurrentCustomEffectMode(int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(522);
        return sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1]}, 522, 2);
    }

    public boolean getEffectMode(int effectId, int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        putCustomCallback(meshAddr, (byte) 4, customcmdCallback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(257);
        byte[] effectIdBytes = int2ByteArr((long) effectId, 2);
        byte[] params = {4, propertyIdBytes[0], propertyIdBytes[1], effectIdBytes[0], effectIdBytes[1]};
        byte[] bArr = params;
        return sendCustomMessage(meshAddr, opcode, rspOpcode, params, 257, 2);
    }

    public boolean setDST(int meshAddr, DSTRule dst, MeshCustomcmdCallback customcmdCallback) {
        DSTRule dSTRule = dst;
        putCustomCallback(meshAddr, (byte) 5, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(260);
        byte[] params = new byte[15];
        params[0] = 5;
        params[1] = propertyIdBytes[0];
        params[2] = propertyIdBytes[1];
        params[3] = 1;
        params[4] = 10;
        byte[] valueBytes = {(byte) dSTRule.enable, (byte) dSTRule.offsetTime, (byte) dSTRule.startMonth, (byte) dSTRule.startDay, (byte) dSTRule.startHour, (byte) dSTRule.startMinute, (byte) dSTRule.endMonth, (byte) dSTRule.endDay, (byte) dSTRule.endHour, (byte) dSTRule.endMinute};
        System.arraycopy(valueBytes, 0, params, 5, valueBytes.length);
        byte[] bArr = valueBytes;
        return sendCustomMessage(meshAddr, opcode, rspOpcode, params, 260, 4);
    }

    public void setSmart(int meshAddr, int smartId, int cmd, RoutineRule routineRule, CustomSmartCallback customcmdCallback) {
        int actionType2;
        int i = cmd;
        RoutineRule routineRule2 = routineRule;
        putCustomCallback(meshAddr, (byte) 6, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(261);
        RoutineRule.ExecuteAction executeAction = routineRule2.startAction;
        int i2 = 255;
        int actionType1 = executeAction == null ? 255 : executeAction.getActionType();
        int len1 = RoutineRule.ExecuteAction.getActionParamLength(actionType1);
        RoutineRule.ExecuteAction executeAction2 = routineRule2.endAction;
        if (executeAction2 != null) {
            i2 = executeAction2.getActionType();
        }
        int actionType22 = i2;
        int len2 = RoutineRule.ExecuteAction.getActionParamLength(actionType22);
        int protoValueLen = i == 1 ? len1 + 28 + len2 : 2;
        byte[] protoValue = new byte[protoValueLen];
        protoValue[0] = (byte) smartId;
        protoValue[1] = (byte) i;
        if (i == 1) {
            byte[] startTimeAndAction = routineRule2.convertTimeAndAction(actionType1, len1, routineRule2.startTime, routineRule2.startAction);
            int i3 = len1;
            byte[] endTimeAndAction = routineRule2.convertTimeAndAction(actionType22, len2, routineRule2.endTime, routineRule2.endAction);
            int i4 = len2;
            int i5 = actionType22;
            actionType2 = 0;
            System.arraycopy(startTimeAndAction, 0, protoValue, 2, startTimeAndAction.length);
            System.arraycopy(endTimeAndAction, 0, protoValue, startTimeAndAction.length + 2, endTimeAndAction.length);
        } else {
            int i6 = actionType22;
            int i7 = len1;
            actionType2 = 0;
        }
        byte[] params = new byte[(protoValueLen + 5)];
        params[actionType2] = 6;
        params[1] = propertyIdBytes[actionType2];
        params[2] = propertyIdBytes[1];
        params[3] = 1;
        params[4] = (byte) protoValueLen;
        System.arraycopy(protoValue, 0, params, 5, protoValueLen);
        byte[] bArr = protoValue;
        int i8 = protoValueLen;
        int i9 = actionType1;
        sendCustomMessage(meshAddr, opcode, rspOpcode, params, 261, 90);
    }

    public void setSmartV2(int meshAddr, int smartId, int cmd, RoutineRule routineRule, CustomSmartCallback customcmdCallback) {
        byte[] protoValue;
        int i;
        int i2 = cmd;
        RoutineRule routineRule2 = routineRule;
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(512);
        RoutineRule.ExecuteAction executeAction = routineRule2.startAction;
        int actionType1 = executeAction == null ? 255 : executeAction.getActionType();
        int len1 = RoutineRule.ExecuteAction.getActionParamLength(actionType1);
        RoutineRule.ExecuteAction executeAction2 = routineRule2.endAction;
        int actionType2 = executeAction2 == null ? 255 : executeAction2.getActionType();
        int len2 = RoutineRule.ExecuteAction.getActionParamLength(actionType2);
        int protoValueLen = i2 == 1 ? len1 + 9 + len2 : 2;
        byte[] protoValue2 = new byte[protoValueLen];
        protoValue2[0] = (byte) smartId;
        protoValue2[1] = (byte) i2;
        if (i2 == 1) {
            i = 0;
            byte[] startTimeAndAction = routineRule.convertTimeAndActionV2(actionType1, len1, routineRule2.startTime, routineRule2.startAction, true);
            protoValue = protoValue2;
            byte[] endTimeAndAction = routineRule.convertTimeAndActionV2(actionType2, len2, routineRule2.endTime, routineRule2.endAction, false);
            System.arraycopy(startTimeAndAction, 0, protoValue, 2, startTimeAndAction.length);
            System.arraycopy(endTimeAndAction, 0, protoValue, startTimeAndAction.length + 2, endTimeAndAction.length);
        } else {
            i = 0;
            protoValue = protoValue2;
        }
        byte[] params = new byte[(protoValueLen + 3)];
        params[i] = ldsTID;
        params[1] = propertyIdBytes[i];
        params[2] = propertyIdBytes[1];
        timber.log.a.g(TAG).m("setSmartRule params:" + w.b(params), new Object[i]);
        System.arraycopy(protoValue, i, params, 3, protoValueLen);
        int i3 = protoValueLen;
        byte b = ldsTID;
        sendCustomMessage(meshAddr, opcode, rspOpcode, params, 512, 90);
    }

    @Deprecated
    public void setRhythm(int meshAddr, RoutineRule.ExecuteAction action) {
        RoutineRule.ExecuteAction executeAction = action;
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(262);
        int actionType = executeAction == null ? 255 : action.getActionType();
        int protoValueLen = RoutineRule.ExecuteAction.getActionParamLength(actionType) + 6;
        byte[] protoValue = new byte[protoValueLen];
        protoValue[0] = 1;
        protoValue[1] = (byte) actionType;
        byte[] sFading = int2ByteArr((long) executeAction.fading, 4);
        System.arraycopy(sFading, 0, protoValue, 2, sFading.length);
        byte[] actionParam = action.toMeshActionParam();
        if (actionParam.length > 0) {
            System.arraycopy(actionParam, 0, protoValue, 6, actionParam.length);
        }
        byte[] params = new byte[(protoValueLen + 5)];
        params[0] = 10;
        params[1] = propertyIdBytes[0];
        params[2] = propertyIdBytes[1];
        params[3] = 1;
        params[4] = (byte) protoValueLen;
        System.arraycopy(protoValue, 0, params, 5, protoValueLen);
        byte[] bArr = params;
        byte[] bArr2 = actionParam;
        byte[] bArr3 = sFading;
        sendCustomMessage(meshAddr, opcode, rspOpcode, params, 262, 4);
    }

    public void setRhythmV1(int meshAddr, RoutineRule.ExecuteAction action) {
        try {
            int opcode = createOpcode(OPCODE_NO_ACK);
            int rspOpcode = createOpcode(OPCODE_RESP);
            byte type = action.getSimpleActionType();
            int actionLen = 4;
            byte[] actionBytes = action.toMeshActionParamV2();
            if (action.soundWaveType == 2) {
                actionLen = 1;
                actionBytes = action.toMeshActionParamV2Dim();
            }
            byte[] params = new byte[(actionLen + 4)];
            params[0] = 10;
            params[1] = 1;
            params[2] = type;
            params[3] = action.getFlag();
            System.arraycopy(actionBytes, 0, params, 4, actionBytes.length);
            MeshMessage message = createVendorMessage(meshAddr, opcode, rspOpcode, params);
            message.B(1);
            message.e(NeedPermissionEvent.PER_ANDROID_S_BLE);
            message.d("发送-musicRhythm V1,propertyid:1");
            message.D(-1);
            MeshMessagePool.getInstance().addAndSend(message);
        } catch (Exception e) {
        }
    }

    public void setAsyncRhythmV2(int meshAddress, JSONObject jsonObject) {
        int i = meshAddress;
        try {
            byte ldsTID = getAndIncreaseTID();
            int opcode = createOpcode(OPCODE_NO_ACK);
            int rspOpcode = createOpcode(OPCODE_RESP);
            JSONArray effectArray = jsonObject.getJSONArray("effects");
            byte[] effectBytes = new byte[6];
            effectBytes[0] = (byte) effectArray.length();
            if (effectArray.length() >= 0) {
                JSONObject effectObj0 = (JSONObject) effectArray.get(0);
                int endDim = (int) Math.ceil(((double) ((JSONObject) effectArray.get(1)).getInt("dimming")) / 10.0d);
                effectBytes[1] = (byte) ((((int) Math.ceil(((double) effectObj0.getInt("dimming")) / 10.0d)) << 4) | endDim);
                int i2 = endDim;
                JSONObject effectObjRandom = (JSONObject) effectArray.get(new Random().nextInt(2));
                int color = effectObjRandom.getInt(TypedValues.Custom.S_COLOR);
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                JSONObject jSONObject = effectObjRandom;
                int r2 = r;
                JSONArray jSONArray = effectArray;
                effectBytes[2] = (byte) r2;
                int i3 = r2;
                int r3 = g;
                effectBytes[3] = (byte) r3;
                int b2 = b;
                int i4 = r3;
                effectBytes[4] = (byte) b2;
                int fading = effectObj0.getInt("fading");
                effectBytes[5] = (byte) (fading / 100);
                byte[] params = new byte[(6 + 2)];
                int i5 = b2;
                params[0] = ldsTID;
                params[1] = 4;
                byte b3 = ldsTID;
                int i6 = fading;
                System.arraycopy(effectBytes, 0, params, 2, effectBytes.length);
                MeshMessage message = createVendorMessage(i, opcode, rspOpcode, params);
                message.B(4);
                message.e(NeedPermissionEvent.PER_ANDROID_S_BLE);
                byte[] bArr = params;
                MsgExtra msgExtra = new MsgExtra("musicRhythm", MeshConstants.EVENT_MUSIC);
                NodeInfo nodeInfo = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, i);
                msgExtra.d = nodeInfo != null ? nodeInfo.macAddress : String.valueOf(meshAddress);
                String str = "musicRhythm " + msgExtra.d;
                msgExtra.b = str;
                message.d(str);
                message.D(-1);
                MeshMessagePool.getInstance().addAndSend(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRhythmV3(int meshAddress, JSONObject jsonObject) {
        JSONObject jSONObject = jsonObject;
        try {
            byte ldsTID = getAndIncreaseTID();
            int opcode = createOpcode(OPCODE_NO_ACK);
            int rspOpcode = createOpcode(OPCODE_RESP);
            byte[] effectBytes = new byte[6];
            JSONArray selectedMacsRhythmV3 = jSONObject.getJSONArray("selectedMacsRhythmV3");
            float countRate = ((float) selectedMacsRhythmV3.length()) / ((float) jSONObject.getInt("supportV3RhythmDeviceCount"));
            int randomValue = (int) (15.0f * countRate);
            String rhythmType = jSONObject.getString("rhythmType");
            int colorType = 0;
            if (IRhyDevice.RHYTHM_TYPE_ASYNC.equals(rhythmType)) {
                colorType = 1;
            }
            effectBytes[0] = toOneByte(colorType, randomValue, 4);
            JSONArray effectArray = jSONObject.getJSONArray("effects");
            if (effectArray.length() >= 0) {
                JSONObject effectObj0 = (JSONObject) effectArray.get(0);
                float f = countRate;
                String str = rhythmType;
                int colorType2 = colorType;
                int startDim = (int) Math.ceil(((double) effectObj0.getInt("dimming")) / 10.0d);
                JSONObject effectObj1 = (JSONObject) effectArray.get(1);
                JSONObject jSONObject2 = effectObj1;
                int endDim = (int) Math.ceil(((double) effectObj1.getInt("dimming")) / 10.0d);
                int newDim = (startDim << 4) | endDim;
                effectBytes[1] = (byte) newDim;
                int i = endDim;
                int randomIndex = new Random().nextInt(2);
                int i2 = startDim;
                JSONObject effectObjRandom = (JSONObject) effectArray.get(randomIndex);
                int color = effectObjRandom.getInt(TypedValues.Custom.S_COLOR);
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                JSONObject jSONObject3 = effectObjRandom;
                int r2 = r;
                int r3 = newDim;
                effectBytes[2] = (byte) r2;
                int i3 = r2;
                int r4 = g;
                int g2 = randomIndex;
                effectBytes[3] = (byte) r4;
                int b2 = b;
                effectBytes[4] = (byte) b2;
                int i4 = r4;
                effectBytes[5] = (byte) (effectObj0.getInt("fading") / 100);
                byte[] params = new byte[(6 + 2)];
                params[0] = ldsTID;
                params[1] = 6;
                byte b3 = ldsTID;
                int i5 = b2;
                System.arraycopy(effectBytes, 0, params, 2, effectBytes.length);
                try {
                    MeshMessage message = createVendorMessage(meshAddress, opcode, rspOpcode, params);
                    message.e(NeedPermissionEvent.PER_ANDROID_S_BLE);
                    message.B(6);
                    message.D(-1);
                    message.d("发送-musicRhythm V3,propertyid:6");
                    MeshMessagePool.getInstance().addAndSend(message);
                    MeshLog.logMusicRhythm("发送-musicRhythm v3指: ramdom:" + randomValue + ",selectV3:" + selectedMacsRhythmV3.length() + ",supportV3:" + ((float) jSONObject.getInt("supportV3RhythmDeviceCount")) + ",colorType:" + colorType2 + ",color:" + color);
                } catch (Exception e) {
                    e = e;
                }
            }
        } catch (Exception e2) {
            e = e2;
            int i6 = meshAddress;
            MeshLog.logMusicRhythm("发送-musicRhythm v3指令exception:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setRhythmTheme(int meshAddress, int[] colors, int index) {
        try {
            byte ldsTID = getAndIncreaseTID();
            int opcode = createOpcode(OPCODE_NO_ACK);
            int rspOpcode = createOpcode(OPCODE_RESP);
            int color = colors[index];
            MeshMessage message = createVendorMessage(meshAddress, opcode, rspOpcode, new byte[]{ldsTID, 5, (byte) colors.length, (byte) index, (byte) Color.red(color), (byte) Color.green(color), (byte) Color.blue(color)});
            message.B(5);
            message.e(NeedPermissionEvent.PER_ANDROID_S_BLE);
            message.D(-1);
            message.d("musicTheme");
            MeshMessagePool.getInstance().addAndSend(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLightsRhythmStatus(int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        putCustomCallback(meshAddr, (byte) 7, customcmdCallback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(263);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{7, propertyIdBytes[0], propertyIdBytes[1]}, 263, 2);
    }

    public void setRhythmEnable(int meshAddr, String byMac, byte able, MeshCustomcmdCallback customcmdCallback) {
        TextUtils.isEmpty(byMac);
        putCustomCallback(meshAddr, (byte) 11, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(263);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{11, propertyIdBytes[0], propertyIdBytes[1], 1, 2, 1, able}, 263, NeedPermissionEvent.PER_ANDROID_S_BLE);
    }

    public void getEffectLinkage(int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        putCustomCallback(meshAddr, (byte) 8, customcmdCallback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(264);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{8, propertyIdBytes[0], propertyIdBytes[1]}, 264, 2);
    }

    private int createOpcode(int opcode) {
        return 135424 | opcode;
    }

    public boolean performEffectLinkage(int action, int meshAddr, int groupAddress, MeshCustomcmdCallback customcmdCallback) {
        putCustomCallback(meshAddr, (byte) 9, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] addressBytes = int2ByteArr((long) groupAddress, 2);
        return sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{9, 8, 1, 1, 3, (byte) action, addressBytes[0], addressBytes[1]}, 0, 4);
    }

    public void getMultipleProperties(int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTOP);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1]}, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTOP, 135);
    }

    public void getBattery(int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(4096);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1]}, 4096, 2);
    }

    public void getSingleCappedAlarm(int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        putCustomCallback(meshAddr, (byte) 18, customcmdCallback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(520);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{18, propertyIdBytes[0], propertyIdBytes[1]}, 520, 2);
    }

    public void setSingleCappedAlarm(int meshAddr, int onOff, MeshCustomcmdCallback customcmdCallback) {
        putCustomCallback(meshAddr, (byte) 19, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(520);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{19, propertyIdBytes[0], propertyIdBytes[1], 1, (byte) onOff}, 520, 4);
    }

    public void getDetectionModeParams(String mac, int meshAddr, int mode, MeshCustomcmdCallback customcmdCallback) {
        int i = meshAddr;
        MeshCustomcmdCallback meshCustomcmdCallback = customcmdCallback;
        DetectionModeDetailCacheInstance.getInstance().sendWrapperDelay(new CacheHanderMsgWrapper(CacheHandler.WHAT_GET_DETECTION_MDOE_DETAIL, mac + "_" + i + "_getDetectionMode", meshCustomcmdCallback));
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(i, ldsTID, meshCustomcmdCallback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(265);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1], (byte) mode}, 265, NeedPermissionEvent.PER_ANDROID_S_BLE);
    }

    public void getDetectionModeParamsNew(String mac, int meshAddr, int mode, MeshCustomcmdCallback customcmdCallback) {
        int i = meshAddr;
        MeshCustomcmdCallback meshCustomcmdCallback = customcmdCallback;
        DetectionModeDetailCacheInstance.getInstance().sendWrapperDelay(new CacheHanderMsgWrapper(CacheHandler.WHAT_GET_DETECTION_MDOE_DETAIL, mac + "_" + i + "_getDetectionMode", meshCustomcmdCallback));
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(i, ldsTID, meshCustomcmdCallback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(771);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1], (byte) mode}, 771, NeedPermissionEvent.PER_ANDROID_S_BLE);
    }

    public void getCurrentDetectionMode(String mac, int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        CurrentDetectionModeCacheInstance.getInstance().sendWrapperDelay(new CacheHanderMsgWrapper(CacheHandler.WHAT_GET_CURRENT_DETECTION_MODE, mac + "_" + meshAddr + "_getCurrentDetectionMode", customcmdCallback));
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(267);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1]}, 267, NeedPermissionEvent.PER_ANDROID_S_BLE);
    }

    public void getCurrentDetectionModeNew(String mac, int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        CurrentDetectionModeCacheInstance.getInstance().sendWrapperDelay(new CacheHanderMsgWrapper(CacheHandler.WHAT_GET_CURRENT_DETECTION_MODE, mac + "_" + meshAddr + "_getCurrentDetectionMode", customcmdCallback));
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(771);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1], 0}, 771, NeedPermissionEvent.PER_ANDROID_S_BLE);
    }

    public void setCurrentDetectionMode(String mac, int meshAddr, int mode, MeshCustomcmdCallback customcmdCallback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(267);
        byte[] params = {ldsTID, propertyIdBytes[0], propertyIdBytes[1], 1, 1, (byte) mode};
        com.leedarson.serviceimpl.reporters.detectionmode.b.b().a(String.valueOf(meshAddr), mac, Integer.valueOf(mode), MeshConstants.EVENT_SET_CURRENT_DETECTION_MODE);
        sendCustomMessage(meshAddr, opcode, rspOpcode, params, 267, 4);
    }

    public void setCurrentDetectionModeNew(String mac, int meshAddr, int mode, MeshCustomcmdCallback customcmdCallback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(771);
        byte[] params = {ldsTID, propertyIdBytes[0], propertyIdBytes[1], toOneByte(1, mode, 7)};
        com.leedarson.serviceimpl.reporters.detectionmode.b.b().a(String.valueOf(meshAddr), mac, Integer.valueOf(mode), MeshConstants.EVENT_SET_CURRENT_DETECTION_MODE);
        sendCustomMessage(meshAddr, opcode, rspOpcode, params, 771, 4);
    }

    public void setAlarmStatus(int meshAddr, int status, MeshCustomcmdCallback customcmdCallback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(521);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1], (byte) status}, 521, 4);
    }

    public void getAlarmStatus(int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(521);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1]}, 521, 2);
    }

    public void getTemporaryControlDuration(int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        putCustomCallback(meshAddr, (byte) 15, customcmdCallback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(266);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{15, propertyIdBytes[0], propertyIdBytes[1]}, 266, 2);
    }

    public boolean setDetectionMode(int meshAddr, DetectMode detectMode, MeshCustomcmdCallback customcmdCallback) {
        DetectMode detectMode2 = detectMode;
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(265);
        int detectionModeLength = detectMode.getModeParamsLen() + 1;
        byte[] params = new byte[(detectionModeLength + 6)];
        params[0] = ldsTID;
        params[1] = propertyIdBytes[0];
        params[2] = propertyIdBytes[1];
        params[3] = 1;
        params[4] = (byte) detectionModeLength;
        params[5] = (byte) detectMode2.mode;
        byte[] modeParams = detectMode.toModeParams();
        Log.d(TAG, "setDetectionMode: param:" + w.b(modeParams));
        if (modeParams.length > 0) {
            System.arraycopy(modeParams, 0, params, 6, modeParams.length);
        }
        com.leedarson.serviceimpl.reporters.detectionmode.b.b().a(String.valueOf(meshAddr), detectMode2.mac, detectMode2, MeshConstants.EVENT_SET_DETECTION_MODE);
        byte[] bArr = modeParams;
        byte[] bArr2 = params;
        return sendCustomMessage(meshAddr, opcode, rspOpcode, params, 265, NeedPermissionEvent.PER_ANDROID_S_BLE);
    }

    public boolean setDetectionModeNew(int meshAddr, DetectMode detectMode, MeshCustomcmdCallback customcmdCallback) {
        DetectMode detectMode2 = detectMode;
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(771);
        byte[] params = new byte[(detectMode.getModeParamsLen() + 4)];
        params[0] = ldsTID;
        params[1] = propertyIdBytes[0];
        params[2] = propertyIdBytes[1];
        if (detectMode2.needChangeMode == 1) {
            params[3] = toOneByte(1, detectMode2.mode, 7);
        } else {
            params[3] = toOneByte(0, detectMode2.mode, 7);
        }
        byte[] modeParams = detectMode.toModeParams();
        if (modeParams.length > 0) {
            System.arraycopy(modeParams, 0, params, 4, modeParams.length);
        }
        com.leedarson.serviceimpl.reporters.detectionmode.b.b().a(String.valueOf(meshAddr), detectMode2.mac, detectMode2, MeshConstants.EVENT_SET_DETECTION_MODE);
        byte[] bArr = modeParams;
        byte[] bArr2 = params;
        return sendCustomMessage(meshAddr, opcode, rspOpcode, params, 771, NeedPermissionEvent.PER_ANDROID_S_BLE);
    }

    public void customSceneCtrl(NodeInfo nodeInfo, SceneRulesWrap sceneRulesWrap, int sceneId) {
        byte ldsTID = getAndIncreaseTID();
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(InputDeviceCompat.SOURCE_DPAD);
        int type = sceneRulesWrap.getActionType();
        int actionLen = sceneRulesWrap.getActionParamLength(type);
        byte[] actionParams = sceneRulesWrap.toActionParams();
        byte[] params = new byte[(actionLen + 4)];
        params[0] = ldsTID;
        params[1] = propertyIdBytes[0];
        params[2] = propertyIdBytes[1];
        params[3] = (byte) type;
        if (actionLen > 0) {
            System.arraycopy(actionParams, 0, params, 4, actionLen);
        }
        MeshMessage message = createVendorMessage(nodeInfo.meshAddress, opcode, rspOpcode, params);
        message.D(-1);
        message.e(50);
        message.B(InputDeviceCompat.SOURCE_DPAD);
        message.c(new MsgExtra("scene控制 addr:" + nodeInfo.meshAddress + ",mac:" + nodeInfo.macAddress + ",sceneId:" + sceneId, MeshConstants.TRACE_ID_CONTROL));
        MeshMessagePool.getInstance().addAndSend(message);
    }

    public boolean setTemporaryControlDuration(int meshAddr, int duration, MeshCustomcmdCallback customcmdCallback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(266);
        byte[] durationBytes = int2ByteArr((long) duration, 2);
        byte[] params = {ldsTID, propertyIdBytes[0], propertyIdBytes[1], 1, 2, durationBytes[0], durationBytes[1]};
        byte[] bArr = params;
        return sendCustomMessage(meshAddr, opcode, rspOpcode, params, 266, 4);
    }

    public void getDeviceGroups(NodeInfo deviceInfo, MeshCustomcmdCallback customcmdCallback) {
        MeshSigModel[] models = MeshSigModel.getDefaultSubListAboveProtocalVersion5();
        int eleAdr = deviceInfo.getTargetEleAdr(models[0].modelId);
        if (eleAdr == -1) {
            eleAdr = deviceInfo.meshAddress;
        }
        ModelSubscriptionGetMessage modelSubscriptionGetMessage = ModelSubscriptionGetMessage.I(deviceInfo.meshAddress, eleAdr, models[0].modelId, true);
        modelSubscriptionGetMessage.d("查询设备绑定的组");
        modelSubscriptionGetMessage.e(NeedPermissionEvent.PER_ANDROID_S_BLE);
        HashMap<Object, MeshCustomcmdCallback> hashMap = this.callbackHashMap;
        hashMap.put(deviceInfo.meshAddress + "_" + eleAdr, customcmdCallback);
        MeshLogNew.i("getDeviceGroups请求发送查询设备已绑定的组:" + deviceInfo.macAddress + ",callkey:" + deviceInfo.meshAddress + "_" + eleAdr);
        MeshMessagePool.getInstance().addAndSend(modelSubscriptionGetMessage);
    }

    public boolean sendCustomMessage(int meshAddr, int opcode, int rspOpcode, byte[] params, int propertyId, int priority) {
        MeshMessage message = createVendorMessage(meshAddr, opcode, rspOpcode, params);
        message.e(priority);
        message.B(propertyId);
        if (propertyId == 265 || propertyId == 771) {
            MsgExtra msgExtra = new MsgExtra(BleMeshService.ACTION_SET_DETECTION_MODE_PARAMS, MeshConstants.EVENT_SET_DETECTION_MODE);
            msgExtra.g = MeshConstants.EVENT_SET_DETECTION_MODE;
            msgExtra.d = String.valueOf(meshAddr);
            message.d(msgExtra.c());
        } else if (propertyId == 515) {
            MsgExtra msgExtra2 = new MsgExtra("setEnergyMode", MeshConstants.EVENT_SET_ENERGY_MODE);
            msgExtra2.g = MeshConstants.EVENT_SET_ENERGY_MODE;
            msgExtra2.d = String.valueOf(meshAddr);
            message.d(msgExtra2.c());
        } else if (propertyId == 514) {
            MsgExtra msgExtra3 = new MsgExtra("setSecurityAlarm", MeshConstants.EVENT_SET_SECURITY_ALARM);
            msgExtra3.g = MeshConstants.EVENT_SET_SECURITY_ALARM;
            msgExtra3.d = String.valueOf(meshAddr);
            message.d(msgExtra3.c());
        } else if (propertyId == 772) {
            MsgExtra msgExtra4 = new MsgExtra(BleMeshService.ACTION_GET_PIR_CONFIG, "");
            msgExtra4.d = String.valueOf(meshAddr);
            message.d(msgExtra4.c());
        } else if (propertyId == 774) {
            MsgExtra msgExtra5 = new MsgExtra("illuminationState", "");
            msgExtra5.d = String.valueOf(meshAddr);
            message.d(msgExtra5.c());
        }
        MeshMessagePool.getInstance().addAndSend(message);
        return true;
    }

    private MeshMessage createVendorMessage(int meshAddr, int opcode, int rspOpcode, byte[] params) {
        MeshMessage meshMessage = new MeshMessage();
        meshMessage.y(meshAddr);
        meshMessage.z(opcode);
        meshMessage.A(params);
        meshMessage.D(rspOpcode);
        meshMessage.H(-1);
        return meshMessage;
    }

    public void onDestroy() {
        MeshEventHandler.getInstance().removeEventListener(this);
    }

    public JSONObject controlDevice(long taskId, NodeInfo node, int modelId, Object value, boolean useQueue) {
        switch (modelId) {
            case 4096:
                onOffCtrl(taskId, node, ((Integer) value).intValue(), useQueue);
                break;
            case LDSModel.MODEL_BRIGHTNESS_CTRL /*4864*/:
                brightnessCtrl(taskId, node, ((Integer) value).intValue(), useQueue);
                break;
            case LDSModel.MODEL_TEMP_CTRL /*4867*/:
                tempCtrl(taskId, node, ((Integer) value).intValue(), useQueue);
                break;
            case LDSModel.MODEL_HSL_CTRL /*4871*/:
                try {
                    int[] hsl = LDSMeshUtil.json2Hsl(new JSONObject(value.toString()));
                    hslCtrl(taskId, node, hsl[0], hsl[1], hsl[2], useQueue);
                    break;
                } catch (JSONException e) {
                    e.printStackTrace();
                    break;
                }
        }
        return BaseResp.generatorSuccessResp();
    }

    public void onOffCtrl(long taskId, NodeInfo node, int onOff, boolean useQueue) {
        node.setOnOff(onOff, false);
        OnOffSetMessage onOffSetMessage = OnOffSetMessage.I(node.meshAddress, SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex(), onOff, false, 0);
        MsgExtra msgExtra = new MsgExtra("开关控制 addr:" + node.meshAddress + ",mac:" + node.macAddress + ",onOff:" + onOff, MeshConstants.TRACE_ID_CONTROL, node.macAddress, System.currentTimeMillis());
        msgExtra.g = MeshConstants.EVENT_DEVICE_CONTROL;
        msgExtra.h = taskId;
        onOffSetMessage.c(msgExtra);
        if (useQueue) {
            MeshMessagePool.getInstance().addAndSend(onOffSetMessage);
        } else {
            MeshService.k().t(onOffSetMessage);
        }
    }

    public void brightnessCtrl(long taskId, NodeInfo node, int lum, boolean useQueue) {
        node.lum = lum;
        LightnessSetMessage message = LightnessSetMessage.I(node.meshAddress, SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex(), UnitConvert.lum2lightness(lum), false, 0);
        MsgExtra msgExtra = new MsgExtra("亮度控制 addr:" + node.meshAddress + ",mac:" + node.macAddress + ",lum:" + lum, MeshConstants.TRACE_ID_CONTROL, node.macAddress, System.currentTimeMillis());
        msgExtra.g = MeshConstants.EVENT_DEVICE_CONTROL;
        msgExtra.h = taskId;
        message.c(msgExtra);
        if (useQueue) {
            MeshMessagePool.getInstance().addAndSend(message);
        } else {
            MeshService.k().t(message);
        }
    }

    private void tempCtrl(long taskId, NodeInfo node, int temp, boolean useQueue) {
        node.temp = temp;
        int tempEleMeshAddr = node.getTempEleInfo() != null ? node.getTempEleInfo().keyAt(0) : -1;
        int meshAddr = node.meshAddress + 1;
        CtlTemperatureSetMessage temperatureSetMessage = CtlTemperatureSetMessage.I(meshAddr, SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex(), temp, 0, false, 0);
        MsgExtra msgExtra = new MsgExtra("色温控制 addr:" + meshAddr + ",tempEleMeshAddr:" + tempEleMeshAddr + ",mac:" + node.macAddress + ",temp:" + temp, MeshConstants.TRACE_ID_CONTROL, node.macAddress, System.currentTimeMillis());
        msgExtra.g = MeshConstants.EVENT_DEVICE_CONTROL;
        msgExtra.h = taskId;
        temperatureSetMessage.c(msgExtra);
        if (useQueue) {
            MeshMessagePool.getInstance().addAndSend(temperatureSetMessage);
        } else {
            MeshService.k().t(temperatureSetMessage);
        }
    }

    private void hslCtrl(long taskId, NodeInfo node, int hue, int sat, int light, boolean useQueue) {
        NodeInfo nodeInfo = node;
        int i = hue;
        int i2 = sat;
        int i3 = light;
        MeshLog.d("device :" + nodeInfo.meshAddress + ",HSL:" + i + "," + i2 + "," + i3);
        nodeInfo.hue = i;
        nodeInfo.sat = i2;
        nodeInfo.light = i3;
        HslSetMessage hslSetMessage = HslSetMessage.I(nodeInfo.getTargetEleAdr(MeshSigModel.SIG_MD_LIGHT_HSL_S.modelId), SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex(), light, hue, sat, false, 0);
        MsgExtra msgExtra = new MsgExtra("颜色控制 addr:" + nodeInfo.meshAddress + ",mac:" + nodeInfo.macAddress + ",HSL:" + i + "," + i2 + "," + i3, MeshConstants.TRACE_ID_CONTROL, nodeInfo.macAddress, System.currentTimeMillis());
        msgExtra.g = MeshConstants.EVENT_DEVICE_CONTROL;
        msgExtra.h = taskId;
        hslSetMessage.c(msgExtra);
        if (useQueue) {
            MeshMessagePool.getInstance().addAndSend(hslSetMessage);
        } else {
            MeshService.k().t(hslSetMessage);
        }
    }

    public void performed(Event<String> event) {
        if (event.getType().equals("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_NOTIFICATION_MESSAGE_UNKNOWN")) {
            NotificationMessage notificationMessage = ((StatusNotificationEvent) event).a();
            int opcode = notificationMessage.a();
            StringBuilder sb = new StringBuilder();
            Locale locale = Locale.US;
            sb.append(String.format(locale, "Unknown message status notify opcode:%04X", new Object[]{Integer.valueOf(opcode)}));
            sb.append(",src:");
            sb.append(notificationMessage.c());
            sb.append(" -- params:");
            sb.append(e.a(notificationMessage.b()));
            MeshLog.e(sb.toString());
            String opcodeStr = String.format(locale, "%04X", new Object[]{Integer.valueOf(opcode)});
            int code = Integer.parseInt(opcodeStr.substring(opcodeStr.length() - 2, opcodeStr.length()), 16);
            if (code == OPCODE_SET || code == OPCODE_GET || code == OPCODE_RESP || code == OPCODE_NO_ACK) {
                parseRespParams(notificationMessage.b(), notificationMessage.c());
            }
        } else if (ModelSubscriptionListStatusMessage.class.getName().equals(event.getType())) {
            NotificationMessage notificationMessage2 = ((StatusNotificationEvent) event).a();
            ModelSubscriptionListStatusMessage statusMessage = (ModelSubscriptionListStatusMessage) notificationMessage2.d();
            HashMap<Object, MeshCustomcmdCallback> hashMap = this.callbackHashMap;
            MeshCustomcmdCallback callback = hashMap.get(notificationMessage2.c() + "_" + statusMessage.d());
            MeshLog.i("getDeviceGroups resp callback:" + callback + ",key:" + notificationMessage2.c() + "_" + statusMessage.d() + ",status:" + statusMessage.e() + ",bindedAddresses:" + statusMessage.c());
            if (callback != null) {
                HashMap<Object, MeshCustomcmdCallback> hashMap2 = this.callbackHashMap;
                hashMap2.remove(notificationMessage2.c() + "_" + statusMessage.d());
                if (statusMessage.e() == ConfigStatus.SUCCESS.code) {
                    callback.onSuccess(statusMessage.c());
                    return;
                }
                callback.onFail(-1, "getDeviceGroup failed src:" + notificationMessage2.c(), "");
                return;
            }
            MeshLog.e("GroupFixHelper getDeviceGroups callback isEmpty 请检查");
        }
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:81:0x0202=Splitter:B:81:0x0202, B:183:0x0623=Splitter:B:183:0x0623} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void parseRespParams(byte[] r27, int r28) {
        /*
            r26 = this;
            r7 = r26
            r8 = r27
            r9 = r28
            java.lang.String r10 = "CmdCtrl"
            r11 = 0
            meshsdk.SIGMesh r0 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x065c }
            meshsdk.model.MeshInfo r0 = r0.getMeshInfo()     // Catch:{ Exception -> 0x065c }
            java.util.List<meshsdk.model.NodeInfo> r0 = r0.nodes     // Catch:{ Exception -> 0x065c }
            meshsdk.model.NodeInfo r0 = meshsdk.util.LDSMeshUtil.findMeshNode((java.util.List<meshsdk.model.NodeInfo>) r0, (int) r9)     // Catch:{ Exception -> 0x065c }
            r12 = r0
            r0 = 1
            if (r12 == 0) goto L_0x001e
            java.lang.String r1 = r12.macAddress     // Catch:{ Exception -> 0x065c }
            goto L_0x002c
        L_0x001e:
            java.lang.String r1 = "0x%04X"
            java.lang.Object[] r2 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x065c }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r28)     // Catch:{ Exception -> 0x065c }
            r2[r11] = r3     // Catch:{ Exception -> 0x065c }
            java.lang.String r1 = java.lang.String.format(r1, r2)     // Catch:{ Exception -> 0x065c }
        L_0x002c:
            r13 = r1
            int r1 = r8.length     // Catch:{ Exception -> 0x065c }
            r2 = 4
            if (r1 >= r2) goto L_0x0087
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x065c }
            r1.<init>()     // Catch:{ Exception -> 0x065c }
            java.lang.String r2 = "收到设备:"
            r1.append(r2)     // Catch:{ Exception -> 0x065c }
            r1.append(r13)     // Catch:{ Exception -> 0x065c }
            java.lang.String r2 = ",响应,收到错误格式数据 params.length < 4，params:"
            r1.append(r2)     // Catch:{ Exception -> 0x065c }
            java.lang.String r2 = com.leedarson.base.utils.e.a(r27)     // Catch:{ Exception -> 0x065c }
            r1.append(r2)     // Catch:{ Exception -> 0x065c }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x065c }
            meshsdk.MeshLog.i(r1)     // Catch:{ Exception -> 0x065c }
            com.leedarson.log.elk.a r1 = com.leedarson.log.elk.a.y(r26)     // Catch:{ Exception -> 0x065c }
            java.lang.String r2 = "LdsBleMesh"
            com.leedarson.log.elk.a r1 = r1.t(r2)     // Catch:{ Exception -> 0x065c }
            java.lang.String r2 = "info"
            com.leedarson.log.elk.a r1 = r1.o(r2)     // Catch:{ Exception -> 0x065c }
            java.lang.Class<meshsdk.ctrl.CmdCtrl> r2 = meshsdk.ctrl.CmdCtrl.class
            java.lang.String r2 = r2.getSimpleName()     // Catch:{ Exception -> 0x065c }
            com.leedarson.log.elk.a r1 = r1.c(r2)     // Catch:{ Exception -> 0x065c }
            java.lang.String r2 = "收到错误格式数据 params.length < 4,srcAddr:0x%04X，固件问题"
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x065c }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r28)     // Catch:{ Exception -> 0x065c }
            r0[r11] = r3     // Catch:{ Exception -> 0x065c }
            java.lang.String r0 = java.lang.String.format(r2, r0)     // Catch:{ Exception -> 0x065c }
            com.leedarson.log.elk.a r0 = r1.p(r0)     // Catch:{ Exception -> 0x065c }
            com.leedarson.log.reporter.d r0 = r0.a()     // Catch:{ Exception -> 0x065c }
            r0.b()     // Catch:{ Exception -> 0x065c }
            return
        L_0x0087:
            byte r1 = r8[r11]     // Catch:{ Exception -> 0x065c }
            r14 = r1
            r1 = 2
            byte[] r3 = new byte[r1]     // Catch:{ Exception -> 0x065c }
            byte r4 = r8[r0]     // Catch:{ Exception -> 0x065c }
            r3[r11] = r4     // Catch:{ Exception -> 0x065c }
            byte r4 = r8[r1]     // Catch:{ Exception -> 0x065c }
            r3[r0] = r4     // Catch:{ Exception -> 0x065c }
            r15 = r3
            int r3 = getBigHex(r15, r1)     // Catch:{ Exception -> 0x065c }
            r6 = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x065c }
            r3.<init>()     // Catch:{ Exception -> 0x065c }
            java.lang.String r4 = "收到设备:%s propertyId:0x%04X"
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x065c }
            r5[r11] = r13     // Catch:{ Exception -> 0x065c }
            java.lang.Integer r16 = java.lang.Integer.valueOf(r6)     // Catch:{ Exception -> 0x065c }
            r5[r0] = r16     // Catch:{ Exception -> 0x065c }
            java.lang.String r4 = java.lang.String.format(r4, r5)     // Catch:{ Exception -> 0x065c }
            r3.append(r4)     // Catch:{ Exception -> 0x065c }
            java.lang.String r4 = ",的响应,params:"
            r3.append(r4)     // Catch:{ Exception -> 0x065c }
            java.lang.String r4 = com.leedarson.base.utils.e.a(r27)     // Catch:{ Exception -> 0x065c }
            r3.append(r4)     // Catch:{ Exception -> 0x065c }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x065c }
            meshsdk.MeshLog.i(r3)     // Catch:{ Exception -> 0x065c }
            r3 = 3
            byte r4 = r8[r3]     // Catch:{ Exception -> 0x065c }
            r5 = r4
            int r4 = r8.length     // Catch:{ Exception -> 0x065c }
            r3 = 772(0x304, float:1.082E-42)
            r0 = 771(0x303, float:1.08E-42)
            if (r4 != r2) goto L_0x00e5
            if (r6 == r0) goto L_0x00e5
            if (r6 == r3) goto L_0x00e5
            r1 = r26
            r2 = r27
            r3 = r28
            r4 = r6
            r18 = r5
            r11 = r6
            r6 = r14
            r1.checkSimpleResponse(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x065c }
            return
        L_0x00e5:
            r18 = r5
            r11 = r6
            r4 = 514(0x202, float:7.2E-43)
            if (r11 == r4) goto L_0x0636
            r4 = 515(0x203, float:7.22E-43)
            if (r11 == r4) goto L_0x0636
            r4 = 516(0x204, float:7.23E-43)
            if (r11 == r4) goto L_0x0636
            r4 = 517(0x205, float:7.24E-43)
            if (r11 == r4) goto L_0x0636
            r4 = 520(0x208, float:7.29E-43)
            if (r11 == r4) goto L_0x0636
            r4 = 521(0x209, float:7.3E-43)
            if (r11 == r4) goto L_0x0636
            r4 = 522(0x20a, float:7.31E-43)
            if (r11 == r4) goto L_0x0636
            if (r11 == r1) goto L_0x0636
            r4 = 769(0x301, float:1.078E-42)
            if (r11 == r4) goto L_0x0636
            r4 = 770(0x302, float:1.079E-42)
            if (r11 == r4) goto L_0x0636
            if (r11 == r0) goto L_0x0636
            r0 = 523(0x20b, float:7.33E-43)
            if (r11 == r0) goto L_0x0636
            if (r11 == r3) goto L_0x0636
            r0 = 773(0x305, float:1.083E-42)
            if (r11 != r0) goto L_0x0120
            r23 = r13
            r8 = r18
            goto L_0x063a
        L_0x0120:
            byte r0 = r8[r2]     // Catch:{ Exception -> 0x065c }
            r20 = r0
            int r0 = r8.length     // Catch:{ Exception -> 0x065c }
            r3 = 5
            if (r0 != r3) goto L_0x0129
            return
        L_0x0129:
            byte r0 = r8[r3]     // Catch:{ Exception -> 0x065c }
            r6 = r0
            byte[] r0 = new byte[r6]     // Catch:{ Exception -> 0x065c }
            r5 = r0
            r0 = 6
            r3 = 0
            java.lang.System.arraycopy(r8, r0, r5, r3, r6)     // Catch:{ Exception -> 0x065c }
            java.util.HashMap<java.lang.Object, meshsdk.callback.MeshCustomcmdCallback> r0 = r7.callbackHashMap     // Catch:{ Exception -> 0x065c }
            java.lang.String r3 = r7.getCustomCallbackKey(r9, r14)     // Catch:{ Exception -> 0x065c }
            java.lang.Object r0 = r0.get(r3)     // Catch:{ Exception -> 0x065c }
            meshsdk.callback.MeshCustomcmdCallback r0 = (meshsdk.callback.MeshCustomcmdCallback) r0     // Catch:{ Exception -> 0x065c }
            r4 = r0
            java.util.HashMap<java.lang.Object, meshsdk.callback.MeshCustomcmdCallback> r0 = r7.callbackHashMap     // Catch:{ Exception -> 0x065c }
            java.lang.String r3 = r7.getCustomCallbackKey(r9, r14)     // Catch:{ Exception -> 0x065c }
            r0.remove(r3)     // Catch:{ Exception -> 0x065c }
            java.lang.String r0 = "mac"
            java.lang.String r3 = ""
            if (r4 != 0) goto L_0x02de
            r2 = 263(0x107, float:3.69E-43)
            if (r11 != r2) goto L_0x020c
            meshsdk.SIGMesh r2 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x065c }
            meshsdk.model.MeshInfo r2 = r2.getMeshInfo()     // Catch:{ Exception -> 0x065c }
            meshsdk.model.NodeInfo r2 = r2.getDeviceByMeshAddress(r9)     // Catch:{ Exception -> 0x065c }
            r12 = r2
            if (r12 != 0) goto L_0x0164
            goto L_0x0166
        L_0x0164:
            java.lang.String r3 = r12.macAddress     // Catch:{ Exception -> 0x065c }
        L_0x0166:
            r2 = r3
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x01fd }
            r3.<init>()     // Catch:{ Exception -> 0x01fd }
            r3.put((java.lang.String) r0, (java.lang.Object) r2)     // Catch:{ Exception -> 0x01fd }
            r16 = 0
            byte r0 = r5[r16]     // Catch:{ Exception -> 0x01fd }
            r0 = r0 & r1
            if (r0 != r1) goto L_0x0178
            r0 = 1
            goto L_0x0179
        L_0x0178:
            r0 = 0
        L_0x0179:
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x01fd }
            r21 = r4
            java.lang.Boolean r4 = new java.lang.Boolean     // Catch:{ Exception -> 0x01f9 }
            r4.<init>(r0)     // Catch:{ Exception -> 0x01f9 }
            boolean r4 = r4.booleanValue()     // Catch:{ Exception -> 0x01f9 }
            r1.setSupportAsyncRhy(r2, r4)     // Catch:{ Exception -> 0x01f9 }
            java.lang.String r1 = "status"
            r16 = r0
            r4 = 1
            byte r0 = r5[r4]     // Catch:{ Exception -> 0x01f9 }
            r3.put((java.lang.String) r1, (int) r0)     // Catch:{ Exception -> 0x01f9 }
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x01f9 }
            com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent r1 = new com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent     // Catch:{ Exception -> 0x01f9 }
            java.lang.String r4 = "SIGMesh"
            r22 = r6
            java.lang.String r6 = "onVoiceRhythmStatusChange"
            java.lang.String r8 = r3.toString()     // Catch:{ Exception -> 0x01f7 }
            r1.<init>(r4, r6, r8)     // Catch:{ Exception -> 0x01f7 }
            r0.l(r1)     // Catch:{ Exception -> 0x01f7 }
            r0 = 1
            byte r1 = r5[r0]     // Catch:{ Exception -> 0x01f7 }
            java.lang.String r4 = "setRhythmEnable 收到设备:"
            if (r1 != r0) goto L_0x01cd
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01f7 }
            r0.<init>()     // Catch:{ Exception -> 0x01f7 }
            r0.append(r4)     // Catch:{ Exception -> 0x01f7 }
            r0.append(r2)     // Catch:{ Exception -> 0x01f7 }
            java.lang.String r1 = "开启律动成功"
            r0.append(r1)     // Catch:{ Exception -> 0x01f7 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x01f7 }
            meshsdk.MeshLog.logMusicRhythm(r0)     // Catch:{ Exception -> 0x01f7 }
        L_0x01cd:
            r0 = 1
            byte r0 = r5[r0]     // Catch:{ Exception -> 0x01f7 }
            if (r0 != 0) goto L_0x01f6
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01f7 }
            r0.<init>()     // Catch:{ Exception -> 0x01f7 }
            r0.append(r4)     // Catch:{ Exception -> 0x01f7 }
            r0.append(r2)     // Catch:{ Exception -> 0x01f7 }
            java.lang.String r1 = "律动enable状态=disable,停止律动"
            r0.append(r1)     // Catch:{ Exception -> 0x01f7 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x01f7 }
            meshsdk.MeshLog.logMusicRhythm(r0)     // Catch:{ Exception -> 0x01f7 }
            meshsdk.SIGMesh r0 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x01f7 }
            java.lang.String r1 = "BLEMesh"
            java.lang.String r4 = "device notify"
            r0.preStopRhythm(r2, r1, r4)     // Catch:{ Exception -> 0x01f7 }
            return
        L_0x01f6:
            goto L_0x0205
        L_0x01f7:
            r0 = move-exception
            goto L_0x0202
        L_0x01f9:
            r0 = move-exception
            r22 = r6
            goto L_0x0202
        L_0x01fd:
            r0 = move-exception
            r21 = r4
            r22 = r6
        L_0x0202:
            r0.printStackTrace()     // Catch:{ Exception -> 0x065c }
        L_0x0205:
            r8 = r5
            r23 = r13
            r13 = r21
            goto L_0x02dd
        L_0x020c:
            r21 = r4
            r22 = r6
            r0 = 4096(0x1000, float:5.74E-42)
            if (r11 != r0) goto L_0x02b3
            r2 = 0
            byte r0 = r5[r2]     // Catch:{ Exception -> 0x065c }
            r2 = -1
            r4 = 0
            int r6 = r5.length     // Catch:{ Exception -> 0x065c }
            if (r6 < r1) goto L_0x0261
            r1 = 1
            byte r6 = r5[r1]     // Catch:{ Exception -> 0x065c }
            r8 = 0
            int r6 = getValueByBitPosition(r6, r8, r1)     // Catch:{ Exception -> 0x065c }
            r2 = r6
            byte r6 = r5[r1]     // Catch:{ Exception -> 0x065c }
            int r6 = getValueByBitPosition(r6, r1, r1)     // Catch:{ Exception -> 0x065c }
            r4 = r6
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x065c }
            r1.<init>()     // Catch:{ Exception -> 0x065c }
            java.lang.String r6 = "设备电量上报:充电状态:"
            r1.append(r6)     // Catch:{ Exception -> 0x065c }
            r6 = 1
            if (r2 != r6) goto L_0x023e
            java.lang.String r6 = "充电中"
            goto L_0x0241
        L_0x023e:
            java.lang.String r6 = "非充电"
        L_0x0241:
            r1.append(r6)     // Catch:{ Exception -> 0x065c }
            java.lang.String r6 = ",ac电源状态:"
            r1.append(r6)     // Catch:{ Exception -> 0x065c }
            r6 = 1
            if (r4 != r6) goto L_0x0250
            java.lang.String r6 = "电源接入"
            goto L_0x0253
        L_0x0250:
            java.lang.String r6 = "无电源接入"
        L_0x0253:
            r1.append(r6)     // Catch:{ Exception -> 0x065c }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x065c }
            meshsdk.MeshLog.e(r1)     // Catch:{ Exception -> 0x065c }
            r8 = r2
            r16 = r4
            goto L_0x0264
        L_0x0261:
            r8 = r2
            r16 = r4
        L_0x0264:
            meshsdk.SIGMesh r1 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x065c }
            meshsdk.model.MeshInfo r1 = r1.getMeshInfo()     // Catch:{ Exception -> 0x065c }
            meshsdk.model.NodeInfo r1 = r1.getDeviceByMeshAddress(r9)     // Catch:{ Exception -> 0x065c }
            r12 = r1
            if (r12 != 0) goto L_0x0274
            goto L_0x0276
        L_0x0274:
            java.lang.String r3 = r12.macAddress     // Catch:{ Exception -> 0x065c }
        L_0x0276:
            r6 = r3
            meshsdk.model.json.MultiPropertyData r1 = new meshsdk.model.json.MultiPropertyData     // Catch:{ Exception -> 0x065c }
            r1.<init>()     // Catch:{ Exception -> 0x065c }
            r4 = r1
            r4.battery = r0     // Catch:{ Exception -> 0x065c }
            long r1 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x065c }
            r4.timestamp = r1     // Catch:{ Exception -> 0x065c }
            meshsdk.cache.cachemodule.MultiPropertyCacheInstance r1 = meshsdk.cache.cachemodule.MultiPropertyCacheInstance.getInstance()     // Catch:{ Exception -> 0x065c }
            r1.put(r6, r4)     // Catch:{ Exception -> 0x065c }
            com.alibaba.android.arouter.launcher.a r1 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ Exception -> 0x065c }
            java.lang.Class<com.leedarson.serviceinterface.BleMeshService> r2 = com.leedarson.serviceinterface.BleMeshService.class
            java.lang.Object r1 = r1.g(r2)     // Catch:{ Exception -> 0x065c }
            com.leedarson.serviceinterface.BleMeshService r1 = (com.leedarson.serviceinterface.BleMeshService) r1     // Catch:{ Exception -> 0x065c }
            java.lang.String r17 = com.leedarson.base.utils.e.a(r27)     // Catch:{ Exception -> 0x065c }
            r2 = r28
            r3 = r0
            r23 = r13
            r13 = r21
            r21 = r4
            r4 = r8
            r24 = r8
            r8 = r5
            r5 = r16
            r25 = r6
            r6 = r17
            r1.onLowBatteryEvent(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x065c }
            goto L_0x02dd
        L_0x02b3:
            r8 = r5
            r23 = r13
            r13 = r21
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x065c }
            r0.<init>()     // Catch:{ Exception -> 0x065c }
            java.lang.String r1 = "当前cmd ctrl callback 为空，tid="
            r0.append(r1)     // Catch:{ Exception -> 0x065c }
            r0.append(r14)     // Catch:{ Exception -> 0x065c }
            java.lang.String r1 = ",propertyId="
            r0.append(r1)     // Catch:{ Exception -> 0x065c }
            r0.append(r11)     // Catch:{ Exception -> 0x065c }
            java.lang.String r1 = ",srcAddr="
            r0.append(r1)     // Catch:{ Exception -> 0x065c }
            r0.append(r9)     // Catch:{ Exception -> 0x065c }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x065c }
            meshsdk.MeshLog.d(r0)     // Catch:{ Exception -> 0x065c }
        L_0x02dd:
            return
        L_0x02de:
            r8 = r5
            r22 = r6
            r23 = r13
            r13 = r4
            r4 = -1
            r6 = r18
            if (r6 == 0) goto L_0x030a
            int r0 = r8.length     // Catch:{ Exception -> 0x065c }
            if (r0 <= 0) goto L_0x02f0
            r1 = 0
            byte r0 = r8[r1]     // Catch:{ Exception -> 0x065c }
            goto L_0x02f1
        L_0x02f0:
            r0 = r4
        L_0x02f1:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x065c }
            r1.<init>()     // Catch:{ Exception -> 0x065c }
            java.lang.String r2 = "cmd response status is fail,code:"
            r1.append(r2)     // Catch:{ Exception -> 0x065c }
            r1.append(r6)     // Catch:{ Exception -> 0x065c }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x065c }
            java.lang.Byte r2 = java.lang.Byte.valueOf(r0)     // Catch:{ Exception -> 0x065c }
            r13.onFail(r4, r1, r2)     // Catch:{ Exception -> 0x065c }
            return
        L_0x030a:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x065c }
            r5.<init>()     // Catch:{ Exception -> 0x065c }
            java.lang.String r2 = "payload value:"
            r5.append(r2)     // Catch:{ Exception -> 0x065c }
            java.lang.String r2 = com.leedarson.base.utils.e.a(r8)     // Catch:{ Exception -> 0x065c }
            r5.append(r2)     // Catch:{ Exception -> 0x065c }
            java.lang.String r2 = r5.toString()     // Catch:{ Exception -> 0x065c }
            meshsdk.MeshLog.e(r2)     // Catch:{ Exception -> 0x065c }
            java.lang.String r2 = ",valueArr:"
            switch(r11) {
                case 0: goto L_0x0560;
                case 1: goto L_0x055b;
                case 3: goto L_0x0556;
                case 256: goto L_0x04e5;
                case 257: goto L_0x04b6;
                case 261: goto L_0x0488;
                case 263: goto L_0x043e;
                case 264: goto L_0x041d;
                case 265: goto L_0x0375;
                case 266: goto L_0x0359;
                case 267: goto L_0x032d;
                case 4096: goto L_0x04e5;
                default: goto L_0x0328;
            }
        L_0x0328:
            r16 = r8
            r8 = r6
            goto L_0x0634
        L_0x032d:
            int r0 = r8.length     // Catch:{ Exception -> 0x0551 }
            if (r0 != 0) goto L_0x034a
            com.leedarson.serviceimpl.reporters.detectionmode.b r0 = com.leedarson.serviceimpl.reporters.detectionmode.b.b()     // Catch:{ Exception -> 0x0551 }
            com.leedarson.serviceimpl.reporters.detectionmode.a$b r1 = com.leedarson.serviceimpl.reporters.detectionmode.a.b.CODE_SUCCESS     // Catch:{ Exception -> 0x0551 }
            java.lang.String r2 = java.lang.String.valueOf(r28)     // Catch:{ Exception -> 0x0551 }
            r0.e(r1, r2)     // Catch:{ Exception -> 0x0551 }
            r1 = 0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0551 }
            r13.onSuccess(r0)     // Catch:{ Exception -> 0x0551 }
            r16 = r8
            r8 = r6
            goto L_0x0634
        L_0x034a:
            r1 = 0
            byte r0 = r8[r1]     // Catch:{ Exception -> 0x0551 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0551 }
            r13.onSuccess(r1)     // Catch:{ Exception -> 0x0551 }
            r16 = r8
            r8 = r6
            goto L_0x0634
        L_0x0359:
            byte[] r0 = new byte[r1]     // Catch:{ Exception -> 0x0551 }
            r2 = 0
            byte r3 = r8[r2]     // Catch:{ Exception -> 0x0551 }
            r0[r2] = r3     // Catch:{ Exception -> 0x0551 }
            r2 = 1
            byte r3 = r8[r2]     // Catch:{ Exception -> 0x0551 }
            r0[r2] = r3     // Catch:{ Exception -> 0x0551 }
            int r1 = getBigHex(r0, r1)     // Catch:{ Exception -> 0x0551 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0551 }
            r13.onSuccess(r2)     // Catch:{ Exception -> 0x0551 }
            r16 = r8
            r8 = r6
            goto L_0x0634
        L_0x0375:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0551 }
            r0.<init>()     // Catch:{ Exception -> 0x0551 }
            java.lang.String r1 = "PROPERTY_DETECTION_MODE response:"
            r0.append(r1)     // Catch:{ Exception -> 0x0551 }
            java.lang.String r1 = com.leedarson.base.utils.e.a(r27)     // Catch:{ Exception -> 0x0551 }
            r0.append(r1)     // Catch:{ Exception -> 0x0551 }
            java.lang.String r1 = ",valueArr.length:"
            r0.append(r1)     // Catch:{ Exception -> 0x0551 }
            int r1 = r8.length     // Catch:{ Exception -> 0x0551 }
            r0.append(r1)     // Catch:{ Exception -> 0x0551 }
            r0.append(r2)     // Catch:{ Exception -> 0x0551 }
            r0.append(r8)     // Catch:{ Exception -> 0x0551 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0551 }
            meshsdk.MeshLog.i(r0)     // Catch:{ Exception -> 0x0551 }
            int r0 = r8.length     // Catch:{ Exception -> 0x0551 }
            if (r0 != 0) goto L_0x03b9
            r1 = 0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0551 }
            r13.onSuccess(r0)     // Catch:{ Exception -> 0x0551 }
            com.leedarson.serviceimpl.reporters.detectionmode.b r0 = com.leedarson.serviceimpl.reporters.detectionmode.b.b()     // Catch:{ Exception -> 0x0551 }
            com.leedarson.serviceimpl.reporters.detectionmode.a$b r1 = com.leedarson.serviceimpl.reporters.detectionmode.a.b.CODE_SUCCESS     // Catch:{ Exception -> 0x0551 }
            java.lang.String r2 = java.lang.String.valueOf(r28)     // Catch:{ Exception -> 0x0551 }
            r0.e(r1, r2)     // Catch:{ Exception -> 0x0551 }
            r16 = r8
            r8 = r6
            goto L_0x0634
        L_0x03b9:
            meshsdk.model.json.DetectMode r0 = new meshsdk.model.json.DetectMode     // Catch:{ Exception -> 0x0551 }
            r1 = 0
            byte r2 = r8[r1]     // Catch:{ Exception -> 0x0551 }
            r0.<init>(r2)     // Catch:{ Exception -> 0x0551 }
            r1 = r0
            r1.parseModeParams(r8)     // Catch:{ Exception -> 0x03fb }
            timber.log.a$b r0 = timber.log.a.g(r10)     // Catch:{ Exception -> 0x03fb }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x03fb }
            r2.<init>()     // Catch:{ Exception -> 0x03fb }
            java.lang.String r3 = "getDetectionModeParams 底层数据解析:"
            r2.append(r3)     // Catch:{ Exception -> 0x03fb }
            java.lang.String r3 = com.leedarson.base.utils.e.a(r8)     // Catch:{ Exception -> 0x03fb }
            r2.append(r3)     // Catch:{ Exception -> 0x03fb }
            java.lang.String r3 = ",mode:"
            r2.append(r3)     // Catch:{ Exception -> 0x03fb }
            r3 = 0
            byte r5 = r8[r3]     // Catch:{ Exception -> 0x03fb }
            r2.append(r5)     // Catch:{ Exception -> 0x03fb }
            java.lang.String r3 = ",callback:"
            r2.append(r3)     // Catch:{ Exception -> 0x03fb }
            r2.append(r13)     // Catch:{ Exception -> 0x03fb }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x03fb }
            r3 = 0
            java.lang.Object[] r5 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x03fb }
            r0.m(r2, r5)     // Catch:{ Exception -> 0x03fb }
            r13.onSuccess(r1)     // Catch:{ Exception -> 0x03fb }
            goto L_0x0418
        L_0x03fb:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x0551 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0551 }
            r2.<init>()     // Catch:{ Exception -> 0x0551 }
            java.lang.String r3 = "detect mode parse Params error:"
            r2.append(r3)     // Catch:{ Exception -> 0x0551 }
            java.lang.String r3 = r0.toString()     // Catch:{ Exception -> 0x0551 }
            r2.append(r3)     // Catch:{ Exception -> 0x0551 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0551 }
            r3 = 0
            r13.onFail(r4, r2, r3)     // Catch:{ Exception -> 0x0551 }
        L_0x0418:
            r16 = r8
            r8 = r6
            goto L_0x0634
        L_0x041d:
            int[] r0 = new int[r1]     // Catch:{ Exception -> 0x0551 }
            r2 = 0
            byte r3 = r8[r2]     // Catch:{ Exception -> 0x0551 }
            r0[r2] = r3     // Catch:{ Exception -> 0x0551 }
            byte[] r3 = new byte[r1]     // Catch:{ Exception -> 0x0551 }
            r4 = 1
            byte r5 = r8[r4]     // Catch:{ Exception -> 0x0551 }
            r3[r2] = r5     // Catch:{ Exception -> 0x0551 }
            byte r2 = r8[r1]     // Catch:{ Exception -> 0x0551 }
            r3[r4] = r2     // Catch:{ Exception -> 0x0551 }
            r2 = r3
            int r1 = getBigHex(r2, r1)     // Catch:{ Exception -> 0x0551 }
            r0[r4] = r1     // Catch:{ Exception -> 0x0551 }
            r13.onSuccess(r0)     // Catch:{ Exception -> 0x0551 }
            r16 = r8
            r8 = r6
            goto L_0x0634
        L_0x043e:
            int[] r0 = new int[r1]     // Catch:{ Exception -> 0x0551 }
            r1 = 0
            byte r2 = r8[r1]     // Catch:{ Exception -> 0x0551 }
            r0[r1] = r2     // Catch:{ Exception -> 0x0551 }
            r1 = 1
            byte r2 = r8[r1]     // Catch:{ Exception -> 0x0551 }
            r0[r1] = r2     // Catch:{ Exception -> 0x0551 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0551 }
            r1.<init>()     // Catch:{ Exception -> 0x0551 }
            java.lang.String r2 = "setRhythmEnable 设备:"
            r1.append(r2)     // Catch:{ Exception -> 0x0551 }
            if (r12 == 0) goto L_0x045a
            java.lang.String r2 = r12.macAddress     // Catch:{ Exception -> 0x0551 }
            goto L_0x046b
        L_0x045a:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0551 }
            r2.<init>()     // Catch:{ Exception -> 0x0551 }
            java.lang.String r3 = " meshAddress: "
            r2.append(r3)     // Catch:{ Exception -> 0x0551 }
            r2.append(r9)     // Catch:{ Exception -> 0x0551 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0551 }
        L_0x046b:
            r1.append(r2)     // Catch:{ Exception -> 0x0551 }
            java.lang.String r2 = " 响应律动状态成功 enable:"
            r1.append(r2)     // Catch:{ Exception -> 0x0551 }
            r2 = 1
            r2 = r0[r2]     // Catch:{ Exception -> 0x0551 }
            r1.append(r2)     // Catch:{ Exception -> 0x0551 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0551 }
            meshsdk.MeshLog.logMusicRhythm(r1)     // Catch:{ Exception -> 0x0551 }
            r13.onSuccess(r0)     // Catch:{ Exception -> 0x0551 }
            r16 = r8
            r8 = r6
            goto L_0x0634
        L_0x0488:
            int r0 = r8.length     // Catch:{ Exception -> 0x0551 }
            if (r0 <= 0) goto L_0x049a
            r1 = 0
            byte r0 = r8[r1]     // Catch:{ Exception -> 0x0551 }
            java.lang.Byte r1 = java.lang.Byte.valueOf(r0)     // Catch:{ Exception -> 0x0551 }
            r13.onSuccess(r1)     // Catch:{ Exception -> 0x0551 }
            r16 = r8
            r8 = r6
            goto L_0x0634
        L_0x049a:
            boolean r0 = r13 instanceof meshsdk.callback.CustomSmartCallback     // Catch:{ Exception -> 0x0551 }
            if (r0 == 0) goto L_0x04b1
            r0 = r13
            meshsdk.callback.CustomSmartCallback r0 = (meshsdk.callback.CustomSmartCallback) r0     // Catch:{ Exception -> 0x0551 }
            int r1 = r0.getMeshSmartId()     // Catch:{ Exception -> 0x0551 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0551 }
            r13.onSuccess(r1)     // Catch:{ Exception -> 0x0551 }
            r16 = r8
            r8 = r6
            goto L_0x0634
        L_0x04b1:
            r16 = r8
            r8 = r6
            goto L_0x0634
        L_0x04b6:
            meshsdk.model.json.EffectModeConfig r0 = new meshsdk.model.json.EffectModeConfig     // Catch:{ Exception -> 0x0551 }
            r0.<init>()     // Catch:{ Exception -> 0x0551 }
            byte[] r2 = new byte[r1]     // Catch:{ Exception -> 0x0551 }
            r3 = 0
            byte r4 = r8[r3]     // Catch:{ Exception -> 0x0551 }
            r2[r3] = r4     // Catch:{ Exception -> 0x0551 }
            r3 = 1
            byte r4 = r8[r3]     // Catch:{ Exception -> 0x0551 }
            r2[r3] = r4     // Catch:{ Exception -> 0x0551 }
            r3 = 4
            byte[] r4 = new byte[r3]     // Catch:{ Exception -> 0x0551 }
            r3 = r4
            int r4 = r3.length     // Catch:{ Exception -> 0x0551 }
            r5 = 0
            java.lang.System.arraycopy(r8, r1, r3, r5, r4)     // Catch:{ Exception -> 0x0551 }
            int r1 = getBigHex(r2, r1)     // Catch:{ Exception -> 0x0551 }
            r0.effectId = r1     // Catch:{ Exception -> 0x0551 }
            r1 = 4
            int r1 = getBigHex(r3, r1)     // Catch:{ Exception -> 0x0551 }
            r0.durationTime = r1     // Catch:{ Exception -> 0x0551 }
            r13.onSuccess(r0)     // Catch:{ Exception -> 0x0551 }
            r16 = r8
            r8 = r6
            goto L_0x0634
        L_0x04e5:
            r0 = r13
            meshsdk.callback.MeshSimpleCmdCallback r0 = (meshsdk.callback.MeshSimpleCmdCallback) r0     // Catch:{ Exception -> 0x0551 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0551 }
            r3.<init>()     // Catch:{ Exception -> 0x0551 }
            java.lang.String r4 = "getBattery response tid:"
            r3.append(r4)     // Catch:{ Exception -> 0x0551 }
            r3.append(r14)     // Catch:{ Exception -> 0x0551 }
            r3.append(r2)     // Catch:{ Exception -> 0x0551 }
            r2 = 0
            byte r4 = r8[r2]     // Catch:{ Exception -> 0x0551 }
            r3.append(r4)     // Catch:{ Exception -> 0x0551 }
            java.lang.String r2 = r3.toString()     // Catch:{ Exception -> 0x0551 }
            meshsdk.MeshLog.i(r2)     // Catch:{ Exception -> 0x0551 }
            meshsdk.model.json.BatteryPropertyBean r2 = new meshsdk.model.json.BatteryPropertyBean     // Catch:{ Exception -> 0x0551 }
            r2.<init>()     // Catch:{ Exception -> 0x0551 }
            r3 = 0
            byte r4 = r8[r3]     // Catch:{ Exception -> 0x0551 }
            r2.setBattery(r4)     // Catch:{ Exception -> 0x0551 }
            int r3 = r8.length     // Catch:{ Exception -> 0x0551 }
            if (r3 < r1) goto L_0x0527
            r1 = 1
            byte r3 = r8[r1]     // Catch:{ Exception -> 0x0551 }
            r4 = 0
            int r3 = getValueByBitPosition(r3, r4, r1)     // Catch:{ Exception -> 0x0551 }
            byte r4 = r8[r1]     // Catch:{ Exception -> 0x0551 }
            int r1 = getValueByBitPosition(r4, r1, r1)     // Catch:{ Exception -> 0x0551 }
            r2.setChargeState(r3)     // Catch:{ Exception -> 0x0551 }
            r2.setAcState(r1)     // Catch:{ Exception -> 0x0551 }
        L_0x0527:
            meshsdk.model.json.MultiPropertyData r1 = new meshsdk.model.json.MultiPropertyData     // Catch:{ Exception -> 0x0551 }
            r1.<init>()     // Catch:{ Exception -> 0x0551 }
            int r3 = r2.getBattery()     // Catch:{ Exception -> 0x0551 }
            r1.battery = r3     // Catch:{ Exception -> 0x0551 }
            int r3 = r2.getChargeState()     // Catch:{ Exception -> 0x0551 }
            r1.chargeState = r3     // Catch:{ Exception -> 0x0551 }
            int r3 = r2.getAcState()     // Catch:{ Exception -> 0x0551 }
            r1.acState = r3     // Catch:{ Exception -> 0x0551 }
            meshsdk.cache.cachemodule.MultiPropertyCacheInstance r3 = meshsdk.cache.cachemodule.MultiPropertyCacheInstance.getInstance()     // Catch:{ Exception -> 0x0551 }
            java.lang.String r4 = r0.getMac()     // Catch:{ Exception -> 0x0551 }
            r3.put(r4, r1)     // Catch:{ Exception -> 0x0551 }
            r0.onSuccess(r2)     // Catch:{ Exception -> 0x0551 }
            r16 = r8
            r8 = r6
            goto L_0x0634
        L_0x0551:
            r0 = move-exception
            r16 = r8
            goto L_0x0623
        L_0x0556:
            r16 = r8
            r8 = r6
            goto L_0x0634
        L_0x055b:
            r16 = r8
            r8 = r6
            goto L_0x0634
        L_0x0560:
            java.util.Locale r2 = java.util.Locale.US     // Catch:{ Exception -> 0x0620 }
            java.lang.String r4 = "%d.%d.%d"
            r5 = 3
            java.lang.Object[] r1 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x0620 }
            r5 = 0
            byte r19 = r8[r5]     // Catch:{ Exception -> 0x0620 }
            java.lang.Byte r19 = java.lang.Byte.valueOf(r19)     // Catch:{ Exception -> 0x0620 }
            r1[r5] = r19     // Catch:{ Exception -> 0x0620 }
            r5 = 1
            byte r17 = r8[r5]     // Catch:{ Exception -> 0x0620 }
            java.lang.Byte r17 = java.lang.Byte.valueOf(r17)     // Catch:{ Exception -> 0x0620 }
            r1[r5] = r17     // Catch:{ Exception -> 0x0620 }
            r5 = 2
            byte r17 = r8[r5]     // Catch:{ Exception -> 0x0620 }
            java.lang.Byte r17 = java.lang.Byte.valueOf(r17)     // Catch:{ Exception -> 0x0620 }
            r1[r5] = r17     // Catch:{ Exception -> 0x0620 }
            java.lang.String r1 = java.lang.String.format(r2, r4, r1)     // Catch:{ Exception -> 0x0620 }
            r2 = r3
            boolean r4 = r13 instanceof meshsdk.callback.MeshSimpleCmdCallback     // Catch:{ Exception -> 0x0620 }
            if (r4 == 0) goto L_0x0593
            r4 = r13
            meshsdk.callback.MeshSimpleCmdCallback r4 = (meshsdk.callback.MeshSimpleCmdCallback) r4     // Catch:{ Exception -> 0x0551 }
            java.lang.String r5 = r4.getMac()     // Catch:{ Exception -> 0x0551 }
            r2 = r5
        L_0x0593:
            r4 = 0
            int r5 = r8.length     // Catch:{ Exception -> 0x0620 }
            r17 = r3
            r3 = 3
            if (r5 <= r3) goto L_0x05c0
            byte r3 = r8[r3]     // Catch:{ Exception -> 0x0551 }
            r4 = r3
            meshsdk.SIGMesh r3 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x0551 }
            meshsdk.model.MeshInfo r3 = r3.getMeshInfo()     // Catch:{ Exception -> 0x0551 }
            meshsdk.model.NodeInfo r3 = r3.getDeviceByMeshAddress(r9)     // Catch:{ Exception -> 0x0551 }
            r12 = r3
            r12.protocolVersion = r4     // Catch:{ Exception -> 0x0551 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0551 }
            r3.<init>()     // Catch:{ Exception -> 0x0551 }
            java.lang.String r5 = "协议版本:"
            r3.append(r5)     // Catch:{ Exception -> 0x0551 }
            r3.append(r4)     // Catch:{ Exception -> 0x0551 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0551 }
            goto L_0x05c2
        L_0x05c0:
            r3 = r17
        L_0x05c2:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0620 }
            r5.<init>()     // Catch:{ Exception -> 0x0620 }
            r16 = r8
            java.lang.String r8 = "读取设备:"
            r5.append(r8)     // Catch:{ Exception -> 0x061e }
            r5.append(r2)     // Catch:{ Exception -> 0x061e }
            java.lang.String r8 = " 版本号成功,"
            r5.append(r8)     // Catch:{ Exception -> 0x061e }
            r5.append(r3)     // Catch:{ Exception -> 0x061e }
            java.lang.String r8 = " 缓存版本号:"
            r5.append(r8)     // Catch:{ Exception -> 0x061e }
            r5.append(r1)     // Catch:{ Exception -> 0x061e }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x061e }
            meshsdk.MeshLog.i(r5)     // Catch:{ Exception -> 0x061e }
            meshsdk.SIGMesh r5 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x061e }
            meshsdk.model.MeshInfo r5 = r5.getMeshInfo()     // Catch:{ Exception -> 0x061e }
            java.lang.String r5 = r5.meshUUID     // Catch:{ Exception -> 0x061e }
            meshsdk.util.LDSMeshUtil.setMeshHWVersion(r5, r2, r1)     // Catch:{ Exception -> 0x061e }
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Exception -> 0x061e }
            r5.<init>()     // Catch:{ Exception -> 0x061e }
            r5.put((java.lang.String) r0, (java.lang.Object) r2)     // Catch:{ Exception -> 0x061e }
            java.lang.String r0 = "version"
            r5.put((java.lang.String) r0, (java.lang.Object) r1)     // Catch:{ Exception -> 0x061e }
            java.lang.String r0 = "protocolVersion"
            r5.put((java.lang.String) r0, (int) r4)     // Catch:{ Exception -> 0x061e }
            r13.onSuccess(r5)     // Catch:{ Exception -> 0x061e }
            java.util.Set r0 = meshsdk.datamgr.MeshDataManager.otaSuccessList     // Catch:{ Exception -> 0x061e }
            boolean r0 = r0.contains(r2)     // Catch:{ Exception -> 0x061e }
            if (r0 == 0) goto L_0x061c
            if (r4 == 0) goto L_0x061c
            meshsdk.datamgr.LDSDeviceApi r0 = r7.ldsDeviceApi     // Catch:{ Exception -> 0x061e }
            r0.editDevice(r2, r4)     // Catch:{ Exception -> 0x061e }
        L_0x061c:
            r8 = r6
            goto L_0x0634
        L_0x061e:
            r0 = move-exception
            goto L_0x0623
        L_0x0620:
            r0 = move-exception
            r16 = r8
        L_0x0623:
            r0.printStackTrace()     // Catch:{ Exception -> 0x065c }
            r1 = r26
            r2 = r27
            r3 = r28
            r4 = r11
            r5 = r6
            r8 = r6
            r6 = r13
            r1.parseFixLengthValue(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x065c }
            goto L_0x0635
        L_0x0634:
        L_0x0635:
            goto L_0x0689
        L_0x0636:
            r23 = r13
            r8 = r18
        L_0x063a:
            java.util.HashMap<java.lang.Object, meshsdk.callback.MeshCustomcmdCallback> r0 = r7.callbackHashMap     // Catch:{ Exception -> 0x065c }
            java.lang.String r1 = r7.getCustomCallbackKey(r9, r14)     // Catch:{ Exception -> 0x065c }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ Exception -> 0x065c }
            r6 = r0
            meshsdk.callback.MeshCustomcmdCallback r6 = (meshsdk.callback.MeshCustomcmdCallback) r6     // Catch:{ Exception -> 0x065c }
            java.util.HashMap<java.lang.Object, meshsdk.callback.MeshCustomcmdCallback> r0 = r7.callbackHashMap     // Catch:{ Exception -> 0x065c }
            java.lang.String r1 = r7.getCustomCallbackKey(r9, r14)     // Catch:{ Exception -> 0x065c }
            r0.remove(r1)     // Catch:{ Exception -> 0x065c }
            r1 = r26
            r2 = r27
            r3 = r28
            r4 = r11
            r5 = r8
            r1.parseFixLengthValue(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x065c }
            return
        L_0x065c:
            r0 = move-exception
            timber.log.a$b r1 = timber.log.a.g(r10)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "parseRespParams exception:"
            r2.append(r3)
            java.lang.String r3 = r0.getMessage()
            r2.append(r3)
            java.lang.String r3 = ",params:"
            r2.append(r3)
            java.lang.String r3 = com.leedarson.base.utils.e.a(r27)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r1.c(r2, r3)
        L_0x0689:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: meshsdk.ctrl.CmdCtrl.parseRespParams(byte[], int):void");
    }

    private void checkSimpleResponse(byte[] params, int srcAddr, int propertyId, int statusCode, byte ldsTID) {
        MeshCustomcmdCallback customcmdCallback = this.callbackHashMap.get(getCustomCallbackKey(srcAddr, ldsTID));
        this.callbackHashMap.remove(getCustomCallbackKey(srcAddr, ldsTID));
        if (customcmdCallback == null) {
            MeshLog.w("checkSimpleResponse 当前cmd ctrl callback 为空，tid=" + ldsTID + ",srcAddr=" + srcAddr);
            com.leedarson.log.elk.a c = com.leedarson.log.elk.a.y(this).t("LdsBleMesh").o("silly").c(CmdCtrl.class.getSimpleName());
            c.p("checkSimpleResponse 当前cmd ctrl callback 为空，tid=" + ldsTID + ",srcAddr=" + srcAddr).a().b();
        } else if (statusCode != 0) {
            customcmdCallback.onFail(-1, "cmd response status is fail,code:" + statusCode, -1);
        } else {
            switch (propertyId) {
                case 267:
                    com.leedarson.serviceimpl.reporters.detectionmode.b.b().e(a.b.CODE_SUCCESS, String.valueOf(srcAddr));
                    customcmdCallback.onSuccess(0);
                    return;
                case 512:
                    if (customcmdCallback instanceof CustomSmartCallback) {
                        customcmdCallback.onSuccess(Integer.valueOf(((CustomSmartCallback) customcmdCallback).getMeshSmartId()));
                        return;
                    }
                    return;
                case 514:
                    d.b().e(c.b.CODE_SUCCESS, String.valueOf(srcAddr));
                    customcmdCallback.onSuccess("");
                    return;
                case 515:
                case 519:
                    com.leedarson.serviceimpl.reporters.wallLamp.b.b().e(a.b.CODE_SUCCESS, String.valueOf(srcAddr));
                    customcmdCallback.onSuccess("");
                    return;
                case 520:
                    customcmdCallback.onSuccess("");
                    return;
                case 521:
                    customcmdCallback.onSuccess("");
                    return;
                case 523:
                    customcmdCallback.onSuccess("");
                    return;
                default:
                    return;
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v3, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v4, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v6, resolved type: boolean} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x0595  */
    /* JADX WARNING: Removed duplicated region for block: B:118:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x01ac A[Catch:{ Exception -> 0x0592 }] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x02c1 A[Catch:{ Exception -> 0x0592 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void parseFixLengthValue(byte[] r25, int r26, int r27, int r28, meshsdk.callback.MeshCustomcmdCallback r29) {
        /*
            r24 = this;
            r1 = r24
            r2 = r25
            r3 = r26
            r4 = r27
            r5 = r28
            r6 = r29
            java.lang.String r7 = "getDetectionModeParamsNew mode parse Params error:"
            java.lang.String r0 = ","
            java.lang.String r8 = ",params:"
            java.lang.String r9 = ",propertyId="
            java.lang.Class<com.leedarson.serviceinterface.BleMeshService> r10 = com.leedarson.serviceinterface.BleMeshService.class
            r11 = -1
            r12 = 1
            r13 = 0
            if (r6 != 0) goto L_0x009d
            r14 = 516(0x204, float:7.23E-43)
            if (r4 == r14) goto L_0x009d
            r14 = 517(0x205, float:7.24E-43)
            if (r4 == r14) goto L_0x009d
            r14 = 520(0x208, float:7.29E-43)
            if (r4 == r14) goto L_0x009d
            r14 = 521(0x209, float:7.3E-43)
            if (r4 == r14) goto L_0x009d
            r14 = 770(0x302, float:1.079E-42)
            if (r4 == r14) goto L_0x009d
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0592 }
            r0.<init>()     // Catch:{ Exception -> 0x0592 }
            java.lang.String r7 = "parseFixLengthValue 当前cmd ctrl callback 为空,srcAddr="
            r0.append(r7)     // Catch:{ Exception -> 0x0592 }
            r0.append(r3)     // Catch:{ Exception -> 0x0592 }
            r0.append(r9)     // Catch:{ Exception -> 0x0592 }
            r0.append(r4)     // Catch:{ Exception -> 0x0592 }
            r0.append(r8)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r7 = com.leedarson.base.utils.e.a(r25)     // Catch:{ Exception -> 0x0592 }
            r0.append(r7)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0592 }
            meshsdk.MeshLog.w(r0)     // Catch:{ Exception -> 0x0592 }
            com.leedarson.log.elk.a r0 = com.leedarson.log.elk.a.y(r24)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r7 = "LdsBleMesh"
            com.leedarson.log.elk.a r0 = r0.t(r7)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r7 = "silly"
            com.leedarson.log.elk.a r0 = r0.o(r7)     // Catch:{ Exception -> 0x0592 }
            java.lang.Class<meshsdk.ctrl.CmdCtrl> r7 = meshsdk.ctrl.CmdCtrl.class
            java.lang.String r7 = r7.getSimpleName()     // Catch:{ Exception -> 0x0592 }
            com.leedarson.log.elk.a r0 = r0.c(r7)     // Catch:{ Exception -> 0x0592 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0592 }
            r7.<init>()     // Catch:{ Exception -> 0x0592 }
            java.lang.String r10 = "parseFixLengthValue 当前cmd ctrl callback 为空 ,srcAddr="
            r7.append(r10)     // Catch:{ Exception -> 0x0592 }
            r7.append(r3)     // Catch:{ Exception -> 0x0592 }
            r7.append(r9)     // Catch:{ Exception -> 0x0592 }
            r7.append(r4)     // Catch:{ Exception -> 0x0592 }
            r7.append(r8)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r8 = com.leedarson.base.utils.e.a(r25)     // Catch:{ Exception -> 0x0592 }
            r7.append(r8)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0592 }
            com.leedarson.log.elk.a r0 = r0.p(r7)     // Catch:{ Exception -> 0x0592 }
            com.leedarson.log.reporter.d r0 = r0.a()     // Catch:{ Exception -> 0x0592 }
            r0.b()     // Catch:{ Exception -> 0x0592 }
            return
        L_0x009d:
            if (r5 == 0) goto L_0x00c6
            java.util.Set r8 = r1.arrayResponseWithoutStatus     // Catch:{ Exception -> 0x0592 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r27)     // Catch:{ Exception -> 0x0592 }
            boolean r8 = r8.contains(r9)     // Catch:{ Exception -> 0x0592 }
            if (r8 != 0) goto L_0x00c6
            if (r6 == 0) goto L_0x00c5
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0592 }
            r0.<init>()     // Catch:{ Exception -> 0x0592 }
            java.lang.String r7 = "cmd response status is fail,code:"
            r0.append(r7)     // Catch:{ Exception -> 0x0592 }
            r0.append(r5)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0592 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r11)     // Catch:{ Exception -> 0x0592 }
            r6.onFail(r11, r0, r7)     // Catch:{ Exception -> 0x0592 }
        L_0x00c5:
            return
        L_0x00c6:
            int r8 = r1.getPayloadStartPositionWithoutStatus(r4)     // Catch:{ Exception -> 0x0592 }
            int r9 = r2.length     // Catch:{ Exception -> 0x0592 }
            int r9 = r9 - r8
            byte[] r9 = new byte[r9]     // Catch:{ Exception -> 0x0592 }
            int r14 = r9.length     // Catch:{ Exception -> 0x0592 }
            java.lang.System.arraycopy(r2, r8, r9, r13, r14)     // Catch:{ Exception -> 0x0592 }
            r15 = 7
            r11 = 4
            r14 = 2
            switch(r4) {
                case 265: goto L_0x049b;
                case 514: goto L_0x045e;
                case 515: goto L_0x0449;
                case 516: goto L_0x042b;
                case 517: goto L_0x03e4;
                case 520: goto L_0x03c3;
                case 521: goto L_0x03a6;
                case 522: goto L_0x0377;
                case 523: goto L_0x032c;
                case 769: goto L_0x02c9;
                case 770: goto L_0x0140;
                case 771: goto L_0x049b;
                case 772: goto L_0x0133;
                case 773: goto L_0x0110;
                case 774: goto L_0x00da;
                default: goto L_0x00d8;
            }     // Catch:{ Exception -> 0x0592 }
        L_0x00d8:
            goto L_0x0591
        L_0x00da:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0592 }
            r0.<init>()     // Catch:{ Exception -> 0x0592 }
            java.lang.String r7 = "PROPERTY_GET_ILLUMINATIONSTATE response:"
            r0.append(r7)     // Catch:{ Exception -> 0x0592 }
            byte r7 = r9[r13]     // Catch:{ Exception -> 0x0592 }
            r0.append(r7)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0592 }
            meshsdk.MeshLog.i(r0)     // Catch:{ Exception -> 0x0592 }
            byte r0 = r9[r13]     // Catch:{ Exception -> 0x0592 }
            int r0 = getValueByBitPosition(r0, r13, r12)     // Catch:{ Exception -> 0x0592 }
            if (r6 == 0) goto L_0x0101
            java.lang.Integer r7 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0592 }
            r6.onSuccess(r7)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x0101:
            com.alibaba.android.arouter.launcher.a r7 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ Exception -> 0x0592 }
            java.lang.Object r7 = r7.g(r10)     // Catch:{ Exception -> 0x0592 }
            com.leedarson.serviceinterface.BleMeshService r7 = (com.leedarson.serviceinterface.BleMeshService) r7     // Catch:{ Exception -> 0x0592 }
            r7.onIlluminationStateChange(r3, r0)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x0110:
            int r0 = getBigHex(r9, r11)     // Catch:{ Exception -> 0x0592 }
            if (r6 == 0) goto L_0x0591
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0592 }
            r7.<init>()     // Catch:{ Exception -> 0x0592 }
            java.lang.String r10 = "getEnergyConsumption success:"
            r7.append(r10)     // Catch:{ Exception -> 0x0592 }
            r7.append(r0)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0592 }
            meshsdk.MeshLog.i(r7)     // Catch:{ Exception -> 0x0592 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0592 }
            r6.onSuccess(r7)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x0133:
            byte r0 = r9[r13]     // Catch:{ Exception -> 0x0592 }
            if (r6 == 0) goto L_0x0591
            java.lang.Integer r7 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0592 }
            r6.onSuccess(r7)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x0140:
            byte r7 = r9[r13]     // Catch:{ Exception -> 0x0592 }
            byte r10 = r9[r12]     // Catch:{ Exception -> 0x0592 }
            int r16 = getValueByBitPosition(r10, r13, r15)     // Catch:{ Exception -> 0x0592 }
            r17 = r16
            int r15 = getValueByBitPosition(r10, r15, r12)     // Catch:{ Exception -> 0x0592 }
            r16 = -1
            r19 = 0
            if (r7 != r12) goto L_0x0167
            byte[] r11 = new byte[r14]     // Catch:{ Exception -> 0x0592 }
            byte r20 = r9[r14]     // Catch:{ Exception -> 0x0592 }
            r11[r13] = r20     // Catch:{ Exception -> 0x0592 }
            r18 = 3
            byte r18 = r9[r18]     // Catch:{ Exception -> 0x0592 }
            r11[r12] = r18     // Catch:{ Exception -> 0x0592 }
            int r18 = getBigHex(r11, r14)     // Catch:{ Exception -> 0x0592 }
            r16 = r18
            goto L_0x019a
        L_0x0167:
            if (r7 != r14) goto L_0x019a
            byte[] r11 = new byte[r12]     // Catch:{ Exception -> 0x0592 }
            byte r21 = r9[r14]     // Catch:{ Exception -> 0x0592 }
            r11[r13] = r21     // Catch:{ Exception -> 0x0592 }
            java.nio.ByteOrder r14 = java.nio.ByteOrder.LITTLE_ENDIAN     // Catch:{ Exception -> 0x0592 }
            int r11 = com.telink.ble.mesh.core.MeshUtils.c(r11, r14)     // Catch:{ Exception -> 0x0592 }
            byte[] r13 = new byte[r12]     // Catch:{ Exception -> 0x0592 }
            r18 = 3
            byte r18 = r9[r18]     // Catch:{ Exception -> 0x0592 }
            r22 = 0
            r13[r22] = r18     // Catch:{ Exception -> 0x0592 }
            int r13 = com.telink.ble.mesh.core.MeshUtils.c(r13, r14)     // Catch:{ Exception -> 0x0592 }
            byte[] r4 = new byte[r12]     // Catch:{ Exception -> 0x0592 }
            r18 = 4
            byte r18 = r9[r18]     // Catch:{ Exception -> 0x0592 }
            r20 = 0
            r4[r20] = r18     // Catch:{ Exception -> 0x0592 }
            int r4 = com.telink.ble.mesh.core.MeshUtils.c(r4, r14)     // Catch:{ Exception -> 0x0592 }
            float[] r14 = meshsdk.util.LDSMeshUtil.rgbToHsl(r11, r13, r4)     // Catch:{ Exception -> 0x0592 }
            r19 = r14
            r4 = r16
            goto L_0x019c
        L_0x019a:
            r4 = r16
        L_0x019c:
            meshsdk.SIGMesh r11 = meshsdk.SIGMesh.getInstance()     // Catch:{ Exception -> 0x0592 }
            meshsdk.model.MeshInfo r11 = r11.getMeshInfo()     // Catch:{ Exception -> 0x0592 }
            java.util.List<meshsdk.model.NodeInfo> r11 = r11.nodes     // Catch:{ Exception -> 0x0592 }
            meshsdk.model.NodeInfo r11 = meshsdk.util.LDSMeshUtil.findMeshNode((java.util.List<meshsdk.model.NodeInfo>) r11, (int) r3)     // Catch:{ Exception -> 0x0592 }
            if (r11 == 0) goto L_0x02c1
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0592 }
            r13.<init>()     // Catch:{ Exception -> 0x0592 }
            java.lang.String r14 = "0x0302 hue 通知设备:"
            r13.append(r14)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r14 = r11.macAddress     // Catch:{ Exception -> 0x0592 }
            r13.append(r14)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r14 = ",hsl:"
            r13.append(r14)     // Catch:{ Exception -> 0x0592 }
            if (r19 == 0) goto L_0x01e7
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0592 }
            r14.<init>()     // Catch:{ Exception -> 0x0592 }
            r16 = 0
            r12 = r19[r16]     // Catch:{ Exception -> 0x0592 }
            r14.append(r12)     // Catch:{ Exception -> 0x0592 }
            r14.append(r0)     // Catch:{ Exception -> 0x0592 }
            r16 = r7
            r12 = 1
            r7 = r19[r12]     // Catch:{ Exception -> 0x0592 }
            r14.append(r7)     // Catch:{ Exception -> 0x0592 }
            r14.append(r0)     // Catch:{ Exception -> 0x0592 }
            r0 = 2
            r7 = r19[r0]     // Catch:{ Exception -> 0x0592 }
            r14.append(r7)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r0 = r14.toString()     // Catch:{ Exception -> 0x0592 }
            goto L_0x01eb
        L_0x01e7:
            r16 = r7
            java.lang.String r0 = "empty"
        L_0x01eb:
            r13.append(r0)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r0 = ",onOff:"
            r13.append(r0)     // Catch:{ Exception -> 0x0592 }
            r13.append(r15)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r0 = r13.toString()     // Catch:{ Exception -> 0x0592 }
            meshsdk.MeshLog.i(r0)     // Catch:{ Exception -> 0x0592 }
            r11.setOnOff(r15)     // Catch:{ Exception -> 0x0592 }
            r7 = r17
            r11.lum = r7     // Catch:{ Exception -> 0x0592 }
            meshsdk.callback.MeshGlobalCallback r0 = r1.bleMeshService     // Catch:{ Exception -> 0x0592 }
            java.lang.String r12 = r11.macAddress     // Catch:{ Exception -> 0x0592 }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r15)     // Catch:{ Exception -> 0x0592 }
            r14 = 4096(0x1000, float:5.74E-42)
            r0.onDeviceStatusChange(r12, r14, r13)     // Catch:{ Exception -> 0x0592 }
            meshsdk.callback.MeshGlobalCallback r0 = r1.bleMeshService     // Catch:{ Exception -> 0x0592 }
            java.lang.String r12 = r11.macAddress     // Catch:{ Exception -> 0x0592 }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x0592 }
            r14 = 4864(0x1300, float:6.816E-42)
            r0.onDeviceStatusChange(r12, r14, r13)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r12 = "0302 notify"
            r13 = -1
            if (r4 == r13) goto L_0x0245
            r11.temp = r4     // Catch:{ Exception -> 0x0592 }
            com.leedarson.serviceimpl.reporters.deviceControl.b r0 = com.leedarson.serviceimpl.reporters.deviceControl.b.b()     // Catch:{ Exception -> 0x0592 }
            r13 = 4867(0x1303, float:6.82E-42)
            r0.g(r11, r13, r12)     // Catch:{ Exception -> 0x0592 }
            com.leedarson.serviceimpl.reporters.groupControl.b r0 = com.leedarson.serviceimpl.reporters.groupControl.b.b()     // Catch:{ Exception -> 0x0592 }
            r0.h(r11, r13, r12)     // Catch:{ Exception -> 0x0592 }
            meshsdk.callback.MeshGlobalCallback r0 = r1.bleMeshService     // Catch:{ Exception -> 0x0592 }
            java.lang.String r14 = r11.macAddress     // Catch:{ Exception -> 0x0592 }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x0592 }
            r23 = r4
            r4 = 4867(0x1303, float:6.82E-42)
            r0.onDeviceStatusChange(r14, r4, r13)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0247
        L_0x0245:
            r23 = r4
        L_0x0247:
            com.leedarson.serviceimpl.reporters.deviceControl.b r0 = com.leedarson.serviceimpl.reporters.deviceControl.b.b()     // Catch:{ Exception -> 0x0592 }
            r4 = 4096(0x1000, float:5.74E-42)
            r0.g(r11, r4, r12)     // Catch:{ Exception -> 0x0592 }
            com.leedarson.serviceimpl.reporters.deviceControl.b r0 = com.leedarson.serviceimpl.reporters.deviceControl.b.b()     // Catch:{ Exception -> 0x0592 }
            r4 = 4864(0x1300, float:6.816E-42)
            r0.g(r11, r4, r12)     // Catch:{ Exception -> 0x0592 }
            com.leedarson.serviceimpl.reporters.groupControl.b r0 = com.leedarson.serviceimpl.reporters.groupControl.b.b()     // Catch:{ Exception -> 0x0592 }
            r4 = 4096(0x1000, float:5.74E-42)
            r0.h(r11, r4, r12)     // Catch:{ Exception -> 0x0592 }
            com.leedarson.serviceimpl.reporters.groupControl.b r0 = com.leedarson.serviceimpl.reporters.groupControl.b.b()     // Catch:{ Exception -> 0x0592 }
            r4 = 4864(0x1300, float:6.816E-42)
            r0.h(r11, r4, r12)     // Catch:{ Exception -> 0x0592 }
            if (r19 == 0) goto L_0x0591
            r4 = 0
            r0 = r19[r4]     // Catch:{ Exception -> 0x0592 }
            int r0 = java.lang.Math.round(r0)     // Catch:{ Exception -> 0x0592 }
            r11.hue = r0     // Catch:{ Exception -> 0x0592 }
            r4 = 1
            r0 = r19[r4]     // Catch:{ Exception -> 0x0592 }
            int r0 = java.lang.Math.round(r0)     // Catch:{ Exception -> 0x0592 }
            r11.sat = r0     // Catch:{ Exception -> 0x0592 }
            r0 = 2
            r0 = r19[r0]     // Catch:{ Exception -> 0x0592 }
            int r0 = java.lang.Math.round(r0)     // Catch:{ Exception -> 0x0592 }
            r11.light = r0     // Catch:{ Exception -> 0x0592 }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x0592 }
            r0.<init>()     // Catch:{ Exception -> 0x0592 }
            r4 = r0
            java.lang.String r0 = "HSLHue"
            int r13 = r11.hue     // Catch:{ JSONException -> 0x02a4 }
            r4.put((java.lang.String) r0, (int) r13)     // Catch:{ JSONException -> 0x02a4 }
            java.lang.String r0 = "HSLSaturation"
            int r13 = r11.sat     // Catch:{ JSONException -> 0x02a4 }
            r4.put((java.lang.String) r0, (int) r13)     // Catch:{ JSONException -> 0x02a4 }
            java.lang.String r0 = "HSLLightness"
            int r13 = r11.light     // Catch:{ JSONException -> 0x02a4 }
            r4.put((java.lang.String) r0, (int) r13)     // Catch:{ JSONException -> 0x02a4 }
            goto L_0x02a8
        L_0x02a4:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x0592 }
        L_0x02a8:
            meshsdk.callback.MeshGlobalCallback r0 = r1.bleMeshService     // Catch:{ Exception -> 0x0592 }
            java.lang.String r13 = r11.macAddress     // Catch:{ Exception -> 0x0592 }
            r14 = 4871(0x1307, float:6.826E-42)
            r0.onDeviceStatusChange(r13, r14, r4)     // Catch:{ Exception -> 0x0592 }
            com.leedarson.serviceimpl.reporters.deviceControl.b r0 = com.leedarson.serviceimpl.reporters.deviceControl.b.b()     // Catch:{ Exception -> 0x0592 }
            r0.g(r11, r14, r12)     // Catch:{ Exception -> 0x0592 }
            com.leedarson.serviceimpl.reporters.groupControl.b r0 = com.leedarson.serviceimpl.reporters.groupControl.b.b()     // Catch:{ Exception -> 0x0592 }
            r0.h(r11, r14, r12)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x02c1:
            r23 = r4
            r16 = r7
            r7 = r17
            goto L_0x0591
        L_0x02c9:
            r4 = 1
            byte r0 = r9[r4]     // Catch:{ Exception -> 0x0592 }
            r7 = 4
            int r0 = getValueByBitPosition(r0, r7, r7)     // Catch:{ Exception -> 0x0592 }
            byte r10 = r9[r4]     // Catch:{ Exception -> 0x0592 }
            r4 = 0
            int r10 = getValueByBitPosition(r10, r4, r7)     // Catch:{ Exception -> 0x0592 }
            r4 = r10
            r7 = 2
            byte r10 = r9[r7]     // Catch:{ Exception -> 0x0592 }
            r7 = r10
            java.util.Locale r10 = java.util.Locale.US     // Catch:{ Exception -> 0x0592 }
            java.lang.String r11 = "%d.%d.%d"
            r12 = 3
            java.lang.Object[] r13 = new java.lang.Object[r12]     // Catch:{ Exception -> 0x0592 }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0592 }
            r14 = 0
            r13[r14] = r12     // Catch:{ Exception -> 0x0592 }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x0592 }
            r14 = 1
            r13[r14] = r12     // Catch:{ Exception -> 0x0592 }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x0592 }
            r14 = 2
            r13[r14] = r12     // Catch:{ Exception -> 0x0592 }
            java.lang.String r10 = java.lang.String.format(r10, r11, r13)     // Catch:{ Exception -> 0x0592 }
            r11 = 3
            byte r12 = r9[r11]     // Catch:{ Exception -> 0x0592 }
            r13 = 1
            int r12 = getValueByBitPosition(r12, r15, r13)     // Catch:{ Exception -> 0x0592 }
            byte r11 = r9[r11]     // Catch:{ Exception -> 0x0592 }
            r13 = 6
            r14 = 0
            int r11 = getValueByBitPosition(r11, r14, r13)     // Catch:{ Exception -> 0x0592 }
            r13 = 4
            byte r13 = r9[r13]     // Catch:{ Exception -> 0x0592 }
            meshsdk.model.json.MultiPropertyData r14 = new meshsdk.model.json.MultiPropertyData     // Catch:{ Exception -> 0x0592 }
            r14.<init>()     // Catch:{ Exception -> 0x0592 }
            r14.battery = r13     // Catch:{ Exception -> 0x0592 }
            r14.dim = r11     // Catch:{ Exception -> 0x0592 }
            r14.version = r10     // Catch:{ Exception -> 0x0592 }
            r14.onoff = r12     // Catch:{ Exception -> 0x0592 }
            r16 = r0
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0592 }
            r14.timestamp = r0     // Catch:{ Exception -> 0x0592 }
            if (r6 == 0) goto L_0x0591
            r6.onSuccess(r14)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x032c:
            r1 = 0
            byte r0 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            r1 = 1
            int r0 = getValueByBitPosition(r0, r15, r1)     // Catch:{ Exception -> 0x0592 }
            byte[] r4 = new byte[r1]     // Catch:{ Exception -> 0x0592 }
            byte r7 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            r1 = 0
            r4[r1] = r7     // Catch:{ Exception -> 0x0592 }
            java.nio.ByteOrder r1 = java.nio.ByteOrder.LITTLE_ENDIAN     // Catch:{ Exception -> 0x0592 }
            int r4 = com.telink.ble.mesh.core.MeshUtils.c(r4, r1)     // Catch:{ Exception -> 0x0592 }
            r7 = 1
            byte[] r10 = new byte[r7]     // Catch:{ Exception -> 0x0592 }
            r7 = 2
            byte r11 = r9[r7]     // Catch:{ Exception -> 0x0592 }
            r7 = 0
            r10[r7] = r11     // Catch:{ Exception -> 0x0592 }
            int r7 = com.telink.ble.mesh.core.MeshUtils.c(r10, r1)     // Catch:{ Exception -> 0x0592 }
            r10 = 1
            byte[] r11 = new byte[r10]     // Catch:{ Exception -> 0x0592 }
            r10 = 3
            byte r10 = r9[r10]     // Catch:{ Exception -> 0x0592 }
            r12 = 0
            r11[r12] = r10     // Catch:{ Exception -> 0x0592 }
            int r1 = com.telink.ble.mesh.core.MeshUtils.c(r11, r1)     // Catch:{ Exception -> 0x0592 }
            int r10 = android.graphics.Color.rgb(r4, r7, r1)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r10 = com.telink.ble.mesh.core.MeshUtils.e(r10)     // Catch:{ Exception -> 0x0592 }
            if (r6 == 0) goto L_0x0591
            r11 = 2
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x0592 }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0592 }
            r13 = 0
            r11[r13] = r12     // Catch:{ Exception -> 0x0592 }
            r12 = 1
            r11[r12] = r10     // Catch:{ Exception -> 0x0592 }
            r6.onSuccess(r11)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x0377:
            r1 = 0
            byte r0 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            int r0 = meshsdk.ctrl.e.a(r0)     // Catch:{ Exception -> 0x0592 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0592 }
            r1.<init>()     // Catch:{ Exception -> 0x0592 }
            java.lang.String r4 = "0x020A getCurrentCustomEffectMode response:"
            r1.append(r4)     // Catch:{ Exception -> 0x0592 }
            r1.append(r0)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r4 = ",valueArr:"
            r1.append(r4)     // Catch:{ Exception -> 0x0592 }
            r4 = 0
            byte r7 = r9[r4]     // Catch:{ Exception -> 0x0592 }
            r1.append(r7)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0592 }
            meshsdk.MeshLog.i(r1)     // Catch:{ Exception -> 0x0592 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0592 }
            r6.onSuccess(r1)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x03a6:
            r1 = 0
            byte r0 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            if (r6 != 0) goto L_0x03ba
            com.alibaba.android.arouter.launcher.a r1 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ Exception -> 0x0592 }
            java.lang.Object r1 = r1.g(r10)     // Catch:{ Exception -> 0x0592 }
            com.leedarson.serviceinterface.BleMeshService r1 = (com.leedarson.serviceinterface.BleMeshService) r1     // Catch:{ Exception -> 0x0592 }
            r1.onAlarmStatusChangeEvent(r3, r0)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x03ba:
            java.lang.Integer r1 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0592 }
            r6.onSuccess(r1)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x03c3:
            r1 = 0
            byte r0 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            r1 = 1
            byte r4 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            r1 = r4
            if (r6 == 0) goto L_0x03d5
            java.lang.Integer r4 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0592 }
            r6.onSuccess(r4)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x03d5:
            com.alibaba.android.arouter.launcher.a r4 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ Exception -> 0x0592 }
            java.lang.Object r4 = r4.g(r10)     // Catch:{ Exception -> 0x0592 }
            com.leedarson.serviceinterface.BleMeshService r4 = (com.leedarson.serviceinterface.BleMeshService) r4     // Catch:{ Exception -> 0x0592 }
            r4.onSingleCappedAlarmTrigger(r3, r1)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x03e4:
            java.lang.String r0 = com.leedarson.base.utils.w.b(r25)     // Catch:{ Exception -> 0x0410 }
            r1 = 0
            r4 = 5
            byte r4 = r2[r4]     // Catch:{ Exception -> 0x0410 }
            int r7 = r2.length     // Catch:{ Exception -> 0x0410 }
            if (r7 < r15) goto L_0x03fb
            r7 = 6
            byte r7 = r2[r7]     // Catch:{ Exception -> 0x0410 }
            r11 = 1
            r12 = 0
            int r7 = getValueByBitPosition(r7, r12, r11)     // Catch:{ Exception -> 0x0410 }
            if (r7 != r11) goto L_0x03fb
            r1 = 1
        L_0x03fb:
            com.alibaba.android.arouter.launcher.a r7 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ Exception -> 0x0410 }
            java.lang.Object r7 = r7.g(r10)     // Catch:{ Exception -> 0x0410 }
            com.leedarson.serviceinterface.BleMeshService r7 = (com.leedarson.serviceinterface.BleMeshService) r7     // Catch:{ Exception -> 0x0410 }
            if (r1 == 0) goto L_0x040b
            r10 = 1
            r7.onGotoSleepEvent(r3, r10)     // Catch:{ Exception -> 0x0410 }
        L_0x040b:
            r7.onHeartBeat(r3, r4, r1)     // Catch:{ Exception -> 0x0410 }
            goto L_0x0591
        L_0x0410:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0592 }
            r1.<init>()     // Catch:{ Exception -> 0x0592 }
            java.lang.String r4 = "PROPERTY_HEART_BEAT parse error:"
            r1.append(r4)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r4 = r0.getMessage()     // Catch:{ Exception -> 0x0592 }
            r1.append(r4)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0592 }
            meshsdk.MeshLog.e(r1)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x042b:
            r1 = 0
            byte r0 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            if (r6 != 0) goto L_0x043f
            com.alibaba.android.arouter.launcher.a r1 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ Exception -> 0x0592 }
            java.lang.Object r1 = r1.g(r10)     // Catch:{ Exception -> 0x0592 }
            com.leedarson.serviceinterface.BleMeshService r1 = (com.leedarson.serviceinterface.BleMeshService) r1     // Catch:{ Exception -> 0x0592 }
            r1.onSecurityTrigger(r3, r0)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x043f:
            r1 = 0
            java.lang.Integer r4 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0592 }
            r6.onSuccess(r4)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x0449:
            meshsdk.model.json.EnergySaveMode r0 = new meshsdk.model.json.EnergySaveMode     // Catch:{ Exception -> 0x0592 }
            r0.<init>()     // Catch:{ Exception -> 0x0592 }
            r1 = 0
            byte r4 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            r0.enable = r4     // Catch:{ Exception -> 0x0592 }
            r1 = 1
            byte r4 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            float r1 = (float) r4     // Catch:{ Exception -> 0x0592 }
            r0.energySavingFactor = r1     // Catch:{ Exception -> 0x0592 }
            r6.onSuccess(r0)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x045e:
            meshsdk.model.json.SecuAlarmRule r0 = new meshsdk.model.json.SecuAlarmRule     // Catch:{ Exception -> 0x0592 }
            r0.<init>()     // Catch:{ Exception -> 0x0592 }
            r1 = 0
            byte r4 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            r0.enable = r4     // Catch:{ Exception -> 0x0592 }
            r1 = 1
            byte r4 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            r0.repeat = r4     // Catch:{ Exception -> 0x0592 }
            r1 = 2
            byte r4 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            r0.startHour = r4     // Catch:{ Exception -> 0x0592 }
            r1 = 3
            byte r7 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            r0.startMinute = r7     // Catch:{ Exception -> 0x0592 }
            r1 = 4
            byte r10 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            r0.endHour = r10     // Catch:{ Exception -> 0x0592 }
            r1 = 5
            byte r1 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            r0.endMinute = r1     // Catch:{ Exception -> 0x0592 }
            r11 = 4
            int[] r11 = new int[r11]     // Catch:{ Exception -> 0x0592 }
            r12 = 0
            r11[r12] = r4     // Catch:{ Exception -> 0x0592 }
            r4 = 1
            r11[r4] = r7     // Catch:{ Exception -> 0x0592 }
            r4 = 2
            r11[r4] = r10     // Catch:{ Exception -> 0x0592 }
            r4 = 3
            r11[r4] = r1     // Catch:{ Exception -> 0x0592 }
            r0.setTimespan(r11)     // Catch:{ Exception -> 0x0592 }
            r0.fixAnyTime()     // Catch:{ Exception -> 0x0592 }
            r6.onSuccess(r0)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x049b:
            boolean r0 = r6 instanceof meshsdk.callback.MeshSimpleCmdSetCallback     // Catch:{ Exception -> 0x0592 }
            if (r0 == 0) goto L_0x04f3
            r1 = 0
            byte r0 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            if (r0 != 0) goto L_0x04dc
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0592 }
            r0.<init>()     // Catch:{ Exception -> 0x0592 }
            java.lang.String r1 = "status:"
            r0.append(r1)     // Catch:{ Exception -> 0x0592 }
            r0.append(r5)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r1 = ",param:"
            r0.append(r1)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r1 = com.leedarson.base.utils.e.a(r25)     // Catch:{ Exception -> 0x0592 }
            r0.append(r1)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0592 }
            meshsdk.MeshLog.i(r0)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r0 = "fail"
            java.lang.String r1 = ""
            r4 = 0
            r6.onFail(r4, r0, r1)     // Catch:{ Exception -> 0x0592 }
            com.leedarson.serviceimpl.reporters.detectionmode.b r0 = com.leedarson.serviceimpl.reporters.detectionmode.b.b()     // Catch:{ Exception -> 0x0592 }
            com.leedarson.serviceimpl.reporters.detectionmode.a$b r1 = com.leedarson.serviceimpl.reporters.detectionmode.a.b.CODE_RESPONSE_FAIL     // Catch:{ Exception -> 0x0592 }
            java.lang.String r4 = java.lang.String.valueOf(r26)     // Catch:{ Exception -> 0x0592 }
            r0.e(r1, r4)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x04dc:
            r1 = 0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0592 }
            r6.onSuccess(r0)     // Catch:{ Exception -> 0x0592 }
            com.leedarson.serviceimpl.reporters.detectionmode.b r0 = com.leedarson.serviceimpl.reporters.detectionmode.b.b()     // Catch:{ Exception -> 0x0592 }
            com.leedarson.serviceimpl.reporters.detectionmode.a$b r1 = com.leedarson.serviceimpl.reporters.detectionmode.a.b.CODE_SUCCESS     // Catch:{ Exception -> 0x0592 }
            java.lang.String r4 = java.lang.String.valueOf(r26)     // Catch:{ Exception -> 0x0592 }
            r0.e(r1, r4)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x04f3:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0592 }
            r0.<init>()     // Catch:{ Exception -> 0x0592 }
            java.lang.String r1 = "PROPERTY_DETECTION_MODE_NEW response:"
            r0.append(r1)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r1 = com.leedarson.base.utils.e.a(r9)     // Catch:{ Exception -> 0x0592 }
            r0.append(r1)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r1 = ",len:"
            r0.append(r1)     // Catch:{ Exception -> 0x0592 }
            int r1 = r9.length     // Catch:{ Exception -> 0x0592 }
            r0.append(r1)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0592 }
            meshsdk.MeshLog.e(r0)     // Catch:{ Exception -> 0x0592 }
            int r0 = r9.length     // Catch:{ Exception -> 0x0592 }
            r1 = 1
            if (r0 != r1) goto L_0x0523
            r1 = 0
            byte r0 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x0592 }
            r6.onSuccess(r0)     // Catch:{ Exception -> 0x0592 }
            goto L_0x0591
        L_0x0523:
            meshsdk.model.json.DetectMode r0 = new meshsdk.model.json.DetectMode     // Catch:{ Exception -> 0x0592 }
            r1 = 0
            byte r4 = r9[r1]     // Catch:{ Exception -> 0x0592 }
            r0.<init>(r4)     // Catch:{ Exception -> 0x0592 }
            r1 = r0
            r1.parseModeParams(r9)     // Catch:{ Exception -> 0x055e }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x055e }
            r0.<init>()     // Catch:{ Exception -> 0x055e }
            java.lang.String r4 = "getDetectionModeParamsNew 底层数据解析:"
            r0.append(r4)     // Catch:{ Exception -> 0x055e }
            java.lang.String r4 = com.leedarson.base.utils.e.a(r9)     // Catch:{ Exception -> 0x055e }
            r0.append(r4)     // Catch:{ Exception -> 0x055e }
            java.lang.String r4 = ",mode:"
            r0.append(r4)     // Catch:{ Exception -> 0x055e }
            r4 = 0
            byte r10 = r9[r4]     // Catch:{ Exception -> 0x055e }
            r0.append(r10)     // Catch:{ Exception -> 0x055e }
            java.lang.String r4 = ",callback:"
            r0.append(r4)     // Catch:{ Exception -> 0x055e }
            r0.append(r6)     // Catch:{ Exception -> 0x055e }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x055e }
            meshsdk.MeshLog.i(r0)     // Catch:{ Exception -> 0x055e }
            r6.onSuccess(r1)     // Catch:{ Exception -> 0x055e }
            goto L_0x0590
        L_0x055e:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ Exception -> 0x0592 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0592 }
            r4.<init>()     // Catch:{ Exception -> 0x0592 }
            r4.append(r7)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r10 = r0.toString()     // Catch:{ Exception -> 0x0592 }
            r4.append(r10)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0592 }
            meshsdk.MeshLog.e(r4)     // Catch:{ Exception -> 0x0592 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0592 }
            r4.<init>()     // Catch:{ Exception -> 0x0592 }
            r4.append(r7)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r7 = r0.toString()     // Catch:{ Exception -> 0x0592 }
            r4.append(r7)     // Catch:{ Exception -> 0x0592 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0592 }
            r7 = 0
            r10 = -1
            r6.onFail(r10, r4, r7)     // Catch:{ Exception -> 0x0592 }
        L_0x0590:
        L_0x0591:
            goto L_0x05c2
        L_0x0592:
            r0 = move-exception
            if (r6 == 0) goto L_0x05c2
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = "parse params error:"
            r1.append(r4)
            java.lang.String r4 = r0.toString()
            r1.append(r4)
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.Integer r7 = java.lang.Integer.valueOf(r27)
            r8 = 0
            r4[r8] = r7
            java.lang.String r7 = " propertityId:0x%04X"
            java.lang.String r4 = java.lang.String.format(r7, r4)
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            r4 = -1
            r6.onFail(r4, r1, r0)
        L_0x05c2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: meshsdk.ctrl.CmdCtrl.parseFixLengthValue(byte[], int, int, int, meshsdk.callback.MeshCustomcmdCallback):void");
    }

    private int getPayloadStartPositionWithoutStatus(int propertyId) {
        if (this.arrayResponseWithoutStatus.contains(Integer.valueOf(propertyId))) {
            return 3;
        }
        return 4;
    }

    public static int getBigHex(byte[] bytes, int length) {
        if (length == 2) {
            return ((bytes[1] & 255) << 8) | (bytes[0] & 255);
        }
        if (length != 4) {
            return -1;
        }
        return ((bytes[3] & 255) << 24) | ((bytes[2] & 255) << MappingData.PATH) | ((bytes[1] & 255) << 8) | (bytes[0] & 255);
    }

    public static byte[] int2ByteArr(long u, int length) {
        byte[] arr = new byte[length];
        if (length == 2) {
            arr[1] = (byte) ((int) ((u >> 8) & 255));
            arr[0] = (byte) ((int) (u & 255));
        } else if (length == 4) {
            arr[3] = (byte) ((int) ((u >> 24) & 255));
            arr[2] = (byte) ((int) ((u >> 16) & 255));
            arr[1] = (byte) ((int) ((u >> 8) & 255));
            arr[0] = (byte) ((int) (u & 255));
        }
        return arr;
    }

    public void setSecurityAlarm(String macAddress, int meshAddr, SecuAlarmRule rule, MeshCustomcmdCallback customcmdCallback) {
        SecuAlarmRule secuAlarmRule = rule;
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(514);
        byte[] params = new byte[9];
        params[0] = ldsTID;
        params[1] = propertyIdBytes[0];
        params[2] = propertyIdBytes[1];
        params[3] = (byte) secuAlarmRule.enable;
        params[4] = (byte) secuAlarmRule.repeat;
        byte[] timeRange = rule.getTimeRange();
        System.arraycopy(timeRange, 0, params, 5, timeRange.length);
        d.b().a(String.valueOf(meshAddr), macAddress, secuAlarmRule, MeshConstants.EVENT_SET_SECURITY_ALARM);
        byte[] bArr = timeRange;
        sendCustomMessage(meshAddr, opcode, rspOpcode, params, 514, 4);
    }

    public void getSecurityAlarm(int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(514);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1]}, 514, 4);
    }

    public void setEnergyMode(String macAddress, int meshAddr, int enable, float energySavingFactor, MeshCustomcmdCallback customcmdCallback) {
        int i = enable;
        float f = energySavingFactor;
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(515);
        int act = (int) (100.0f * f);
        byte[] params = {ldsTID, propertyIdBytes[0], propertyIdBytes[1], (byte) i, (byte) act};
        com.leedarson.serviceimpl.reporters.wallLamp.b b = com.leedarson.serviceimpl.reporters.wallLamp.b.b();
        String valueOf = String.valueOf(meshAddr);
        b.a(valueOf, macAddress, "enable:" + i + ",energySavingFactor:" + f, MeshConstants.EVENT_SET_ENERGY_MODE);
        int i2 = act;
        byte[] bArr = params;
        sendCustomMessage(meshAddr, opcode, rspOpcode, params, 515, 4);
    }

    public void getEnergyMode(int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(515);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1]}, 515, 4);
    }

    public void heartbeatVersion3(int meshAddr) {
        byte ldsTID = getAndIncreaseTID();
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(517);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1]}, 517, 4);
    }

    public boolean setVoiceRhythmStopAttr(int meshAddr, RhythmStopAttrBean bean, MeshCustomcmdCallback customcmdCallback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(519);
        int n = bean.getActionLen();
        byte[] params = new byte[(n + 4)];
        params[0] = ldsTID;
        params[1] = propertyIdBytes[0];
        params[2] = propertyIdBytes[1];
        params[3] = bean.getAction();
        if (n > 0) {
            byte[] actionBytes = bean.getActionParams();
            System.arraycopy(actionBytes, 0, params, 4, actionBytes.length);
        }
        return sendCustomMessage(meshAddr, opcode, rspOpcode, params, 519, 4);
    }

    public boolean cancelSecurityAlarm(int meshAddr, MeshCustomcmdCallback customcmdCallback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(516);
        return sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1], 1, 0}, 516, 4);
    }

    public void getIlluminationState(int meshAddress, MeshCustomcmdCallback callback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddress, ldsTID, callback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(774);
        byte[] params = {ldsTID, propertyIdBytes[0], propertyIdBytes[1]};
        MeshLog.i("getIlluminationState...");
        sendCustomMessage(meshAddress, opcode, rspOpcode, params, 774, 2);
    }

    public void getPIRConfig(int meshAddress, MeshCustomcmdCallback callback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddress, ldsTID, callback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(772);
        sendCustomMessage(meshAddress, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1]}, 772, 2);
    }

    public void setPIRConfig(int meshAddr, int level, MeshCustomcmdCallback customcmdCallback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddr, ldsTID, customcmdCallback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(772);
        sendCustomMessage(meshAddr, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1], (byte) level}, 772, 4);
    }

    public void getEnergyConsumption(int meshAddress, MeshCustomcmdCallback callback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddress, ldsTID, callback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(773);
        sendCustomMessage(meshAddress, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1]}, 773, 2);
    }

    public void getWallWasherLightStatus(int meshAddress, MeshCustomcmdCallback callback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddress, ldsTID, callback);
        int opcode = createOpcode(OPCODE_GET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(523);
        sendCustomMessage(meshAddress, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1]}, 523, 2);
    }

    public void controlWallWasherLight(int meshAddress, int onOff, int dimming, String rgb, MeshCustomcmdCallback callback) {
        byte ldsTID = getAndIncreaseTID();
        putCustomCallback(meshAddress, ldsTID, callback);
        int opcode = createOpcode(OPCODE_SET);
        int rspOpcode = createOpcode(OPCODE_RESP);
        byte[] propertyIdBytes = convertPropertyBytes(523);
        int rgbValue = Color.parseColor(rgb);
        sendCustomMessage(meshAddress, opcode, rspOpcode, new byte[]{ldsTID, propertyIdBytes[0], propertyIdBytes[1], toOneByte(onOff, 0, 7), (byte) Color.red(rgbValue), (byte) Color.green(rgbValue), (byte) Color.blue(rgbValue)}, 523, 150);
    }

    public static byte toOneByte(int highValue, int lowValue, int lowBit) {
        return (byte) ((highValue << lowBit) | lowValue);
    }

    public static int getValueByBitPosition(byte value, int start, int length) {
        return (value >> start) & (255 >> (8 - length));
    }

    public void putCustomCallback(int meshAddress, byte tid, MeshCustomcmdCallback callback) {
        this.callbackHashMap.put(getCustomCallbackKey(meshAddress, tid), callback);
    }

    public String getCustomCallbackKey(int meshAddress, byte tid) {
        if (this.arrayResponseWithTipCallback.contains(Integer.valueOf(tid))) {
            return String.valueOf(tid);
        }
        return tid + "_" + meshAddress;
    }
}
