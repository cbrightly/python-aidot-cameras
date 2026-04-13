package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class mg1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ mg1 a = new mg1();

    private /* synthetic */ mg1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThermostatCluster) baseChipCluster).writeMinHeatSetpointLimitAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
