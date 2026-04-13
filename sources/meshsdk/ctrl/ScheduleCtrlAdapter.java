package meshsdk.ctrl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshScheduleCallback;
import meshsdk.model.NodeInfo;
import meshsdk.model.json.RoutineRule;

public class ScheduleCtrlAdapter extends CtrlLifecycle implements MeshScheduleCallback {
    private int ACT_TYPE_ENABLE_SCHEDULE = 1;
    private int ACT_TYPE_SET_SCHEDULE = 0;
    private HashMap<String, MeshScheduleCallback> callbackHashMap;
    private AtomicBoolean isWaiting;
    private final Object mLock = new Object();
    private Queue<RoutineRuleWrap> queue;
    private SchedulerCtrl schedulerCtrl;

    public ScheduleCtrlAdapter(SIGMesh sigMesh) {
        super(sigMesh);
        this.schedulerCtrl = new SchedulerCtrl(sigMesh);
        this.queue = new LinkedList();
        this.isWaiting = new AtomicBoolean(false);
        this.callbackHashMap = new HashMap<>();
    }

    public void onCreate() {
    }

    public void setSchedule(boolean isNew, NodeInfo node, byte index, RoutineRule rule, MeshScheduleCallback scheduleCallback) {
        this.queue.add(new RoutineRuleWrap(node, rule, index, isNew, this.ACT_TYPE_SET_SCHEDULE));
        this.callbackHashMap.put(String.valueOf(index), scheduleCallback);
        processScheduleRule();
    }

    public void setScheduleEnable(boolean enable, NodeInfo node, byte index, RoutineRule rule, MeshScheduleCallback scheduleCallback) {
        RoutineRuleWrap item = new RoutineRuleWrap(node, rule, index, false, this.ACT_TYPE_ENABLE_SCHEDULE);
        item.enable = enable;
        this.queue.add(item);
        this.callbackHashMap.put(String.valueOf(index), scheduleCallback);
        processScheduleRule();
    }

    public void setTime(NodeInfo node, boolean ack) {
        this.schedulerCtrl.setTime(node, false);
    }

    private void processScheduleRule() {
        RoutineRuleWrap wrap;
        if (SIGMesh.getInstance().hasConnected()) {
            synchronized (this.mLock) {
                if (!this.isWaiting.get() && (wrap = this.queue.poll()) != null) {
                    this.isWaiting.compareAndSet(false, true);
                    if (wrap.actType == this.ACT_TYPE_SET_SCHEDULE) {
                        this.schedulerCtrl.setSchedule(wrap.isNew, wrap.nodeInfo, wrap.index, wrap.routineRule, this);
                    } else {
                        this.schedulerCtrl.setScheduleEnable(wrap.enable, wrap.nodeInfo, wrap.index, wrap.routineRule, this);
                    }
                }
            }
        }
    }

    public void onSuccess(int scheduleIndex) {
        HashMap<String, MeshScheduleCallback> hashMap = this.callbackHashMap;
        MeshScheduleCallback callback = hashMap.get(scheduleIndex + "");
        if (callback != null) {
            callback.onSuccess(scheduleIndex);
            this.callbackHashMap.remove(callback);
        }
        this.isWaiting.set(false);
        processScheduleRule();
    }

    public void onFail(int code, String msg, int scheduleIndex) {
        HashMap<String, MeshScheduleCallback> hashMap = this.callbackHashMap;
        MeshScheduleCallback callback = hashMap.get(scheduleIndex + "");
        if (callback != null) {
            callback.onFail(code, msg, scheduleIndex);
            this.callbackHashMap.remove(callback);
        }
        this.isWaiting.set(false);
        processScheduleRule();
    }

    public static class RoutineRuleWrap {
        public int actType;
        public boolean enable;
        public byte index;
        public boolean isNew;
        public NodeInfo nodeInfo;
        public RoutineRule routineRule;

        public RoutineRuleWrap(NodeInfo nodeInfo2, RoutineRule routineRule2, byte index2, boolean isNew2, int actType2) {
            this.nodeInfo = nodeInfo2;
            this.actType = actType2;
            this.routineRule = routineRule2;
            this.index = index2;
            this.isNew = isNew2;
        }
    }
}
