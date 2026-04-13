package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class hg1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ hg1 a = new hg1();

    private /* synthetic */ hg1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).writeNullableRangeRestrictedInt8sAttribute((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("value"));
    }
}
