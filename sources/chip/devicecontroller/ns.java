package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ns implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ns a = new ns();

    private /* synthetic */ ns() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readNullableInt48sAttribute((ChipClusters.TestClusterCluster.NullableInt48sAttributeCallback) obj);
    }
}
