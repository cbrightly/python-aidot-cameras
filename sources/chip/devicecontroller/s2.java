package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.ArrayList;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class s2 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ s2 a = new s2();

    private /* synthetic */ s2() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThermostatCluster) baseChipCluster).setWeeklySchedule((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("numberOfTransitionsForSequence"), (Integer) map.get("dayOfWeekForSequence"), (Integer) map.get("modeForSequence"), (ArrayList) map.get("transitions"));
    }
}
