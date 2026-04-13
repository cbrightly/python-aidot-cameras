package meshsdk.ctrl;

import com.leedarson.base.utils.e;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.MeshSigModel;
import com.telink.ble.mesh.core.message.scheduler.SchedulerActionSetMessage;
import com.telink.ble.mesh.core.message.scheduler.SchedulerActionStatusMessage;
import com.telink.ble.mesh.core.message.time.TimeSetMessage;
import com.telink.ble.mesh.core.message.time.TimeStatusMessage;
import com.telink.ble.mesh.entity.Scheduler;
import com.telink.ble.mesh.foundation.Event;
import com.telink.ble.mesh.foundation.EventListener;
import com.telink.ble.mesh.foundation.MeshService;
import com.telink.ble.mesh.foundation.event.StatusNotificationEvent;
import meshsdk.MeshEventHandler;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshScheduleCallback;
import meshsdk.model.MeshInfo;
import meshsdk.model.NodeInfo;
import meshsdk.model.json.RoutineRule;
import meshsdk.util.UnitConvert;

public class SchedulerCtrl extends CtrlLifecycle implements EventListener<String> {
    private final int MODE_ADD = 1;
    private final int MODE_REMOVE = 2;
    private int currentScheduleIndex;
    private NodeInfo mDevice;
    private MeshScheduleCallback scheduleCallback;
    private Scheduler scheduler;

    public SchedulerCtrl(SIGMesh sigMesh) {
        super(sigMesh);
        onCreate();
    }

    public void setSchedule(boolean isNew, NodeInfo mDevice2, byte index, RoutineRule rule, MeshScheduleCallback scheduleCallback2) {
    }

    private void addSceneActionProcedure(int smartId, byte index, int year, int month, int day, int hour, int minute, int second, int week, int action, int transitionTime, int localSceneId) {
    }

    private void scheduleLog(String s) {
        MeshLog.e("schedule->" + s);
    }

    public void setScheduleEnable(boolean enable, NodeInfo mDevice2, byte index, RoutineRule rule, MeshScheduleCallback scheduleCallback2) {
    }

    private void scheduleProcedure(int smartId, byte index, int year, int month, int day, int hour, int minute, int second, int week, int action, int transitionTime, int localSceneId) {
        int i = year;
        int i2 = month;
        int i3 = day;
        int i4 = hour;
        int i5 = minute;
        int i6 = second;
        int i7 = week;
        int i8 = action;
        int i9 = localSceneId;
        MeshLog.e("scheduler params year: " + i + " month: " + i2 + " day: " + i3 + " hour: " + i4 + " minute: " + i5 + " second: " + i6 + " week: " + i7 + " action: " + i8 + " localSceneId：" + i9);
        Scheduler a = new Scheduler.Builder().j(smartId).e(index).m((byte) i).g((short) i2).c((byte) i3).d((byte) i4).f((byte) i5).i((byte) i6).l((byte) i7).b((byte) i8).k((byte) transitionTime).h((short) i9).a();
        this.scheduler = a;
        byte[] schedulerData = a.toBytes();
        StringBuilder sb = new StringBuilder();
        sb.append("scheduler data: ");
        sb.append(e.b(schedulerData, ""));
        MeshLog.d(sb.toString());
        int eleAdr = this.mDevice.getTargetEleAdr(MeshSigModel.SIG_MD_SCHED_S.modelId);
        if (eleAdr == -1) {
            doFail(-1, "scheduler model not found");
            return;
        }
        MeshInfo meshInfo = SIGMesh.getInstance().getMeshInfo();
        MeshInfo meshInfo2 = meshInfo;
        MeshMessagePool.getInstance().addAndSend(SchedulerActionSetMessage.I(eleAdr, meshInfo.getDefaultAppKeyIndex(), this.scheduler, true, 1));
    }

    public void setTime(NodeInfo mDevice2, boolean ack) {
        this.mDevice = mDevice2;
        long time = MeshUtils.j();
        int offset = UnitConvert.getZoneOffset();
        int eleAdr = mDevice2.getTargetEleAdr(MeshSigModel.SIG_MD_TIME_S.modelId);
        if (eleAdr != -1) {
            TimeSetMessage timeSetMessage = TimeSetMessage.I(eleAdr, SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex(), time, offset, 0);
            timeSetMessage.J(ack);
            if (MeshService.k().t(timeSetMessage)) {
                MeshLog.d("setTime time: " + time + " zone " + offset);
                return;
            }
            MeshLog.d("setTime fail");
        }
    }

    public void onCreate() {
        MeshEventHandler.getInstance().addEventListener(SchedulerActionStatusMessage.class.getName(), this);
        MeshEventHandler.getInstance().addEventListener(TimeStatusMessage.class.getName(), this);
    }

    public void onDestroy() {
        MeshEventHandler.getInstance().removeEventListener(this);
    }

    public void performed(Event<String> event) {
        String eventType = event.getType();
        if (eventType.equals(TimeStatusMessage.class.getName())) {
            MeshLog.d("time status: " + ((TimeStatusMessage) ((StatusNotificationEvent) event).a().d()).c());
        } else if (eventType.equals(SchedulerActionStatusMessage.class.getName())) {
            if (this.scheduler.getIndex() == ((SchedulerActionStatusMessage) ((StatusNotificationEvent) event).a().d()).c().getIndex()) {
                this.mDevice.saveScheduler(this.scheduler);
                SIGMesh.getInstance().getMeshInfo().saveOrUpdate(SIGMesh.getInstance().getContext(), "ScheduleCtrl.java SchedulerActionStatusMessage");
                MeshScheduleCallback meshScheduleCallback = this.scheduleCallback;
                if (meshScheduleCallback != null) {
                    meshScheduleCallback.onSuccess(this.scheduler.getIndex());
                }
            }
        }
    }

    private void doFail(int code, String msg) {
        MeshScheduleCallback meshScheduleCallback = this.scheduleCallback;
        if (meshScheduleCallback != null) {
            meshScheduleCallback.onFail(code, msg, this.currentScheduleIndex);
        }
    }
}
