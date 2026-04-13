package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class xa1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ xa1 a = new xa1();

    private /* synthetic */ xa1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).writeNullableInt8uAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
