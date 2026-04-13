package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class dd1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ dd1 a = new dd1();

    private /* synthetic */ dd1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).writeNullableInt16sAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
