package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class nh1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ nh1 a = new nh1();

    private /* synthetic */ nh1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).writeNullableInt8sAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
