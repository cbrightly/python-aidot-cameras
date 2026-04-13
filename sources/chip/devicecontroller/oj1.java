package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class oj1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ oj1 a = new oj1();

    private /* synthetic */ oj1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ThermostatCluster) baseChipCluster).writeMinSetpointDeadBandAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
