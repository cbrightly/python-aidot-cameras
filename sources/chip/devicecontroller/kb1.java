package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class kb1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ kb1 a = new kb1();

    private /* synthetic */ kb1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).writeNullableInt64sAttribute((ChipClusters.DefaultClusterCallback) obj, (Long) map.get("value"));
    }
}
