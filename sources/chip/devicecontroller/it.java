package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class it implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ it a = new it();

    private /* synthetic */ it() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readNullableRangeRestrictedInt16sAttribute((ChipClusters.TestClusterCluster.NullableRangeRestrictedInt16sAttributeCallback) obj);
    }
}
