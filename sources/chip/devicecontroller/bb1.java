package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class bb1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ bb1 a = new bb1();

    private /* synthetic */ bb1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).writeNullableInt56sAttribute((ChipClusters.DefaultClusterCallback) obj, (Long) map.get("value"));
    }
}
