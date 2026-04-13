package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class t4 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ t4 a = new t4();

    private /* synthetic */ t4() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.DoorLockCluster) baseChipCluster).getWeekDaySchedule((ChipClusters.DoorLockCluster.GetWeekDayScheduleResponseCallback) obj, (Integer) map.get("weekDayIndex"), (Integer) map.get("userIndex"));
    }
}
