package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ke1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ke1 a = new ke1();

    private /* synthetic */ ke1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThermostatCluster) baseChipCluster).writeMinCoolSetpointLimitAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
