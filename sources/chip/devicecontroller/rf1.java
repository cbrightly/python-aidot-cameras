package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class rf1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ rf1 a = new rf1();

    private /* synthetic */ rf1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).writeNullableInt40uAttribute((ChipClusters.DefaultClusterCallback) obj, (Long) map.get("value"));
    }
}
