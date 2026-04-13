package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class p2 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ p2 a = new p2();

    private /* synthetic */ p2() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThermostatCluster) baseChipCluster).getWeeklySchedule((ChipClusters.ThermostatCluster.GetWeeklyScheduleResponseCallback) obj, (Integer) map.get("daysToReturn"), (Integer) map.get("modeToReturn"));
    }
}
