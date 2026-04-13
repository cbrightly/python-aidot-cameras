package meshsdk.ctrl;

import android.os.Handler;
import android.os.Looper;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.MeshSigModel;
import com.telink.ble.mesh.core.message.time.TimeSetMessage;
import com.telink.ble.mesh.foundation.MeshService;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.callback.CustomSmartCallback;
import meshsdk.callback.MeshCustomcmdCallback;
import meshsdk.model.NodeInfo;
import meshsdk.model.json.RoutineRule;
import meshsdk.util.UnitConvert;

public class SmartCtrlAdapter extends CustomSmartCallback {
    private int CMD_TYPE_DEL_SMART = 0;
    private int CMD_TYPE_DISABLE_SMART = 3;
    private int CMD_TYPE_ENABLE_SMART = 2;
    private int CMD_TYPE_SET_SMART = 1;
    private int TIME_OUT_MILLS = 6000;
    private HashMap<String, MeshCustomcmdCallback> callbackHashMap;
    private CmdCtrl cmdCtrl;
    private String currentMac = "";
    /* access modifiers changed from: private */
    public String debugDesc = "";
    private Handler handler;
    private AtomicBoolean isWaiting;
    private final Object mLock = new Object();
    private Queue<RoutineRuleWrap> queue;
    private TimeOutTask timeOutTask;

    public class TimeOutTask implements Runnable {
        byte smartAddr;

        public TimeOutTask(byte smartAddr2) {
            this.smartAddr = smartAddr2;
        }

        public void run() {
            SmartCtrlAdapter smartCtrlAdapter = SmartCtrlAdapter.this;
            smartCtrlAdapter.onFail(-1, SmartCtrlAdapter.this.debugDesc + " time out", Byte.valueOf(this.smartAddr));
        }
    }

    public SmartCtrlAdapter(SIGMesh sigMesh, CmdCtrl cmdCtrl2) {
        super(sigMesh);
        this.cmdCtrl = cmdCtrl2;
        this.queue = new LinkedList();
        this.isWaiting = new AtomicBoolean(false);
        this.callbackHashMap = new HashMap<>();
        this.handler = new Handler(Looper.getMainLooper());
    }

    public void onCreate() {
    }

    public void setSmart(NodeInfo node, byte smartAddress, RoutineRule rule, MeshCustomcmdCallback customcmdCallback) {
        this.debugDesc = "setSmart";
        this.queue.add(new RoutineRuleWrap(node, rule, smartAddress, this.CMD_TYPE_SET_SMART));
        HashMap<String, MeshCustomcmdCallback> hashMap = this.callbackHashMap;
        hashMap.put(rule.mac + smartAddress, customcmdCallback);
        processScheduleRule();
    }

    public void removeSmart(NodeInfo node, byte smartAddress, RoutineRule rule, MeshCustomcmdCallback customcmdCallback) {
        this.debugDesc = "removeSmart";
        this.queue.add(new RoutineRuleWrap(node, rule, smartAddress, this.CMD_TYPE_DEL_SMART));
        HashMap<String, MeshCustomcmdCallback> hashMap = this.callbackHashMap;
        hashMap.put(rule.mac + smartAddress, customcmdCallback);
        processScheduleRule();
    }

    public void setSmartEnable(NodeInfo node, byte smartAddress, RoutineRule rule, MeshCustomcmdCallback customcmdCallback) {
        this.debugDesc = "setSmartEnable";
        MeshLog.i("=========setSmartEnable============");
        this.queue.add(new RoutineRuleWrap(node, rule, smartAddress, this.CMD_TYPE_ENABLE_SMART));
        HashMap<String, MeshCustomcmdCallback> hashMap = this.callbackHashMap;
        hashMap.put(rule.mac + smartAddress, customcmdCallback);
        processScheduleRule();
    }

    public void setSmartDisable(NodeInfo node, byte smartAddress, RoutineRule rule, MeshCustomcmdCallback customcmdCallback) {
        this.debugDesc = "setSmartDisable";
        MeshLog.i("=========setSmartDisable============");
        this.queue.add(new RoutineRuleWrap(node, rule, smartAddress, this.CMD_TYPE_DISABLE_SMART));
        HashMap<String, MeshCustomcmdCallback> hashMap = this.callbackHashMap;
        hashMap.put(rule.mac + smartAddress, customcmdCallback);
        processScheduleRule();
    }

    public void setTime(NodeInfo node, boolean ack) {
        long time = MeshUtils.j();
        int offset = UnitConvert.getZoneOffset();
        int eleAdr = node.getTargetEleAdr(MeshSigModel.SIG_MD_TIME_S.modelId);
        if (eleAdr != -1) {
            TimeSetMessage timeSetMessage = TimeSetMessage.I(eleAdr, SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex(), time, offset, 0);
            timeSetMessage.J(ack);
            if (MeshService.k().t(timeSetMessage)) {
                MeshLog.i("setTime time: " + time + " zone " + offset);
                return;
            }
            MeshLog.d("setTime fail");
        }
    }

    private void processScheduleRule() {
        RoutineRuleWrap wrap;
        if (SIGMesh.getInstance().hasConnected()) {
            synchronized (this.mLock) {
                if (!this.isWaiting.get() && (wrap = this.queue.poll()) != null) {
                    this.isWaiting.compareAndSet(false, true);
                    int cmd = 1;
                    int i = wrap.cmdType;
                    if (i == this.CMD_TYPE_SET_SMART) {
                        cmd = 1;
                    } else if (i == this.CMD_TYPE_DEL_SMART) {
                        cmd = 0;
                    } else if (i == this.CMD_TYPE_ENABLE_SMART) {
                        cmd = 2;
                    } else if (i == this.CMD_TYPE_DISABLE_SMART) {
                        cmd = 3;
                    }
                    this.currentMac = wrap.nodeInfo.macAddress;
                    setMeshSmartId(wrap.smartAddress);
                    RoutineRule routineRule = wrap.routineRule;
                    if (routineRule.protocolVersion >= SIGMesh.NEW_PROTOCOL) {
                        MeshLog.i("调用新版smartV2协议");
                        this.cmdCtrl.setSmartV2(wrap.nodeInfo.meshAddress, wrap.smartAddress, cmd, wrap.routineRule, this);
                    } else {
                        this.cmdCtrl.setSmart(wrap.nodeInfo.meshAddress, wrap.smartAddress, cmd, routineRule, this);
                    }
                    Handler handler2 = this.handler;
                    TimeOutTask timeOutTask2 = new TimeOutTask(wrap.smartAddress);
                    this.timeOutTask = timeOutTask2;
                    handler2.postDelayed(timeOutTask2, (long) this.TIME_OUT_MILLS);
                }
            }
        }
    }

    public void onSmartSuccess(int meshSmartId) {
        MeshLog.i("SmartAdapter " + this.debugDesc + " onSuccess");
        this.handler.removeCallbacks(this.timeOutTask);
        byte smartId = (byte) meshSmartId;
        this.isWaiting.set(false);
        HashMap<String, MeshCustomcmdCallback> hashMap = this.callbackHashMap;
        MeshCustomcmdCallback callback = hashMap.get(this.currentMac + smartId);
        if (callback != null) {
            callback.onSuccess(Byte.valueOf(smartId));
            HashMap<String, MeshCustomcmdCallback> hashMap2 = this.callbackHashMap;
            hashMap2.remove(this.currentMac + smartId);
        }
        processScheduleRule();
    }

    public void onSmartFail(int code, String msg, Object data) {
        MeshLog.i("SmartAdapter " + this.debugDesc + " onFail");
        this.handler.removeCallbacks(this.timeOutTask);
        byte smartId = ((Byte) data).byteValue();
        this.isWaiting.set(false);
        HashMap<String, MeshCustomcmdCallback> hashMap = this.callbackHashMap;
        MeshCustomcmdCallback callback = hashMap.get(this.currentMac + smartId);
        if (callback != null) {
            callback.onFail(code, msg, Byte.valueOf(smartId));
            HashMap<String, MeshCustomcmdCallback> hashMap2 = this.callbackHashMap;
            hashMap2.remove(this.currentMac + smartId);
        }
        processScheduleRule();
    }

    public static class RoutineRuleWrap {
        public int cmdType;
        public NodeInfo nodeInfo;
        public RoutineRule routineRule;
        public byte smartAddress;

        public RoutineRuleWrap(NodeInfo nodeInfo2, RoutineRule routineRule2, byte smartAddress2, int cmdType2) {
            this.nodeInfo = nodeInfo2;
            this.cmdType = cmdType2;
            this.routineRule = routineRule2;
            routineRule2.protocolVersion = nodeInfo2.protocolVersion;
            this.smartAddress = smartAddress2;
        }
    }
}
